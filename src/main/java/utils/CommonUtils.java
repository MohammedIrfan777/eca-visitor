package utils;

import com.eca.visitor.dto.*;
import com.eca.visitor.dto.response.VisitorRegistrationResponse;
import com.eca.visitor.entity.Visitor;
import com.eca.visitor.notification.VisitorKafkaNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Configuration
public class CommonUtils {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired(required = false)
    private VisitorKafkaNotificationService visitorKafkaNotificationService;

    public UserDTO parseOwnerDetails(OwnerDTO owner) {

        UserDTO userDto = new UserDTO();
        userDto.setFirstName(owner.getFirstName());
        userDto.setLastName(owner.getLastName());
        userDto.setEmailId(owner.getEmailId());
        userDto.setUserFirstName(owner.getFirstName());
        userDto.setUserLastName(owner.getLastName());
        userDto.setApartmentId(owner.getApartmentId());
        userDto.setApartmentName(owner.getApartmentName());
        userDto.setUserEmailId(owner.getEmailId());
        return userDto;
    }

    public UserDTO parseVendorDetails(VendorDTO vendorDTO) {

        UserDTO userDto = new UserDTO();
        userDto.setFirstName(vendorDTO.getFirstName());
        userDto.setLastName(vendorDTO.getLastName());
        userDto.setEmailId(vendorDTO.getEmailId());
        userDto.setUserFirstName(vendorDTO.getFirstName());
        userDto.setUserLastName(vendorDTO.getLastName());
        userDto.setApartmentId(vendorDTO.getApartmentId());
        userDto.setApartmentName(vendorDTO.getApartmentName());
        userDto.setUserEmailId(vendorDTO.getEmailId());
        return userDto;
    }

    public UserDTO parseTenantDetails(TenantDTO tenantDTO) {

        UserDTO userDto = new UserDTO();
        userDto.setFirstName(tenantDTO.getFirstName());
        userDto.setLastName(tenantDTO.getLastName());
        userDto.setEmailId(tenantDTO.getEmailId());
        userDto.setUserFirstName(tenantDTO.getFirstName());
        userDto.setUserLastName(tenantDTO.getLastName());
        userDto.setApartmentId(tenantDTO.getApartmentId());
        userDto.setApartmentName(tenantDTO.getApartmentName());
        userDto.setUserEmailId(tenantDTO.getEmailId());
        return userDto;
    }

    private ResponseEntity<VisitorRegistrationResponse> toVisitorRegistrationResponse (VisitorDTO visitorDto, UserDTO userDTO, HttpStatus httpStatus){
        var visitorRegistrationResponse = new VisitorRegistrationResponse();
        visitorRegistrationResponse.setResponseDto(visitorDto);
        visitorRegistrationResponse.setUserDTO(userDTO);
        return new ResponseEntity<>(visitorRegistrationResponse, httpStatus);
    }

    public void pushToKafka(VisitorKafkaMessageDTO visitorKafkaMessageDTO) {
        visitorKafkaNotificationService.sendMessage(visitorKafkaMessageDTO);

    }

    public VisitorKafkaMessageDTO createVisitorkafkaMessageDto(Visitor visitor) {
        var visitorKafkaMessageDTO = new VisitorKafkaMessageDTO();
        visitorKafkaMessageDTO.setId(visitor.getId());
        if (StringUtils.isNotBlank(visitor.getVisitorFirstName())) {
            visitorKafkaMessageDTO.setVisitorFirstName(visitor.getVisitorFirstName());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorLastName())) {
            visitorKafkaMessageDTO.setVisitorLastName(visitor.getVisitorLastName());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorAddressLine())) {
            visitorKafkaMessageDTO.setVisitorAddressLine(visitor.getVisitorAddressLine());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorCity())) {
            visitorKafkaMessageDTO.setVisitorCity(visitor.getVisitorCity());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorState())) {
            visitorKafkaMessageDTO.setVisitorState(visitor.getVisitorState());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorZipCode())) {
            visitorKafkaMessageDTO.setVisitorZipCode(visitor.getVisitorZipCode());
        }
        visitorKafkaMessageDTO.setUserPhoneNo(visitor.getUserPhoneNo());
        visitorKafkaMessageDTO.setVisitorId(visitor.getVisitorId());
        visitorKafkaMessageDTO.setVisitorRequestId(visitor.getVisitorRequestId());
        if(StringUtils.isNotBlank(visitor.getPurposeOfVisiting())) {
            visitorKafkaMessageDTO.setPurposeOfVisiting(visitor.getPurposeOfVisiting());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorFirstName())) {
            visitorKafkaMessageDTO.setUserFirstName(visitor.getVisitorFirstName());
        }
        if(StringUtils.isNotBlank(visitor.getVisitorLastName())) {
            visitorKafkaMessageDTO.setUserLastName(visitor.getVisitorLastName());
        }
        visitorKafkaMessageDTO.setUserEmailId(visitor.getUserEmailId());
        return visitorKafkaMessageDTO;
    }



}
