package com.eca.visitor.service.impl;

import com.eca.visitor.constants.VisitorConstants;
import com.eca.visitor.dto.*;
import com.eca.visitor.dto.response.VisitorRegistrationResponse;
import com.eca.visitor.entity.Visitor;
import com.eca.visitor.exception.UserNotFoundException;
import com.eca.visitor.exception.VisitorManagementException;
import com.eca.visitor.repository.VisitorRepository;
import com.eca.visitor.dto.response.VisitorResponse;
import com.eca.visitor.service.VisitorRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.eca.visitor.utils.CommonUtils;
import com.eca.visitor.utils.JsonUtils;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class VisitorRegistrationServiceImpl implements VisitorRegistrationService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private JsonUtils jsonUtils;

    @Value("${microservice.user-service.endpoints.endpoint.uri}")
    private String endpointUrl;

    @Value("${app.user-mgmt.authTkn}")
    private String usermgmtToken;

    @Value("${app.visitor.kafka.enabled}")
    private boolean kafkaEnabled;

    @Override
    public ResponseEntity<VisitorRegistrationResponse> visitorRegistration(VisitorRegistrationRequest requestDto)  {

        Visitor visitorEntity = requestToEntity(requestDto);
        var userDto = getUserDetailsForVisitor(visitorEntity.getUserPhoneNo());
        var visitorToSave = parseUserDetailsForvisitor(visitorEntity,userDto);
        saveVisitor(visitorToSave);
        if (kafkaEnabled) {
            sendVisitorToKafka(visitorToSave);
        }
        return createVisitorRegistrationResponse(visitorToSave,HttpStatus.CREATED);
    }

    private Visitor saveVisitor(Visitor visitor) {
       var save = visitorRepository.save(visitor);
        return Optional.of(save)
                .orElseThrow(() -> new VisitorManagementException("Visitor RegistrationFailed"));
    }

    private void sendVisitorToKafka(Visitor visitor) {
        VisitorKafkaMessageDTO visitorKafkaMessageDto = commonUtils.createVisitorkafkaMessageDto(visitor);
        commonUtils.pushToKafka(visitorKafkaMessageDto);
    }
    private Visitor parseUserDetailsForvisitor(Visitor visitorEntity, UserDTO userDto) {
        visitorEntity.setUserFirstName(userDto.getUserFirstName());
        visitorEntity.setUserLastName(userDto.getUserLastName());
        visitorEntity.setApartmentId(userDto.getApartmentId());
        visitorEntity.setApartmentName(userDto.getApartmentName());
        visitorEntity.setUserEmailId(userDto.getUserEmailId());
        return visitorEntity;
    }
   private UserDTO getUserDetailsForVisitor(Long userPhoneNo) {
       HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());
       String uri = UriComponentsBuilder.fromHttpUrl(endpointUrl)
               .buildAndExpand(userPhoneNo)
               .toUriString();
       try {
           ResponseEntity<UserRespDTO> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, UserRespDTO.class);
           UserRespDTO userResponse = responseEntity.getBody();
           log.info("User management service response - Status: {}, Body: {}", responseEntity.getStatusCodeValue(), userResponse);
           Optional<DataItem> userDetails = getUserDetails(userResponse);
           return userDetails.map(this::parseUserResponse)
                   .orElseThrow(() -> new VisitorManagementException("Unable to find details with: " + userPhoneNo));
       } catch (HttpClientErrorException | HttpServerErrorException e) {
           log.error("Error retrieving user details for phone number {}: {}", userPhoneNo, e.getMessage());
           throw new UserNotFoundException("Failed to retrieve user details for phone number: " + userPhoneNo);
       }
   }

    private Optional<DataItem> getUserDetails(UserRespDTO userResponse) {
        return Optional.ofNullable(userResponse)
                .map(UserRespDTO::getData)
                .flatMap(data -> data.stream().findFirst());
    }

    private UserDTO parseUserResponse(DataItem userdata) {
        if (userdata.getOwner() != null) {
            return commonUtils.parseOwnerDetails(userdata.getOwner());
        } else if (userdata.getVendor() != null) {
            return commonUtils.parseVendorDetails(userdata.getVendor());
        } else if (userdata.getTenant() != null) {
            return commonUtils.parseTenantDetails(userdata.getTenant());
        }
        else {
            log.error(VisitorConstants.USER_DETAILS_FOR_VISITOR_NOT_FOUND);
            throw new VisitorManagementException(VisitorConstants.USER_DETAILS_FOR_VISITOR_NOT_FOUND);
        }
    }


    private Visitor requestToEntity(VisitorRegistrationRequest requestDto) {
        return modelMapper.map(requestDto, Visitor.class);
    }

    private ResponseEntity<VisitorRegistrationResponse> createVisitorRegistrationResponse (Visitor visitor, HttpStatus httpStatus){
        VisitorResponse visitorResponse = modelMapper.map(visitor, VisitorResponse.class);
        VisitorRegistrationResponse visitorRegistrationResponse = new VisitorRegistrationResponse(visitorResponse, LocalDateTime.now());
        return new ResponseEntity<>(visitorRegistrationResponse, httpStatus);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", org.apache.commons.lang3.StringUtils.join("Bearer ", usermgmtToken));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}

