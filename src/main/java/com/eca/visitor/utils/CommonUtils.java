package com.eca.visitor.utils;

import com.eca.visitor.dto.*;
import com.eca.visitor.entity.Visitor;
import com.eca.visitor.service.notification.VisitorKafkaNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonUtils {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired(required = false)
    private VisitorKafkaNotificationService visitorKafkaNotificationService;

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO parseOwnerDetails(OwnerDTO owner) {

        UserDTO userDto = new UserDTO();
        userDto.setUserFirstName(owner.getFirstName());
        userDto.setUserLastName(owner.getLastName());
        userDto.setApartmentId(owner.getApartmentId());
        userDto.setApartmentName(owner.getApartmentName());
        userDto.setUserEmailId(owner.getEmailId());
        return userDto;
    }

    public UserDTO parseVendorDetails(VendorDTO vendorDTO) {

        UserDTO userDto = new UserDTO();
        userDto.setUserFirstName(vendorDTO.getFirstName());
        userDto.setUserLastName(vendorDTO.getLastName());
        userDto.setApartmentId(vendorDTO.getApartmentId());
        userDto.setApartmentName(vendorDTO.getApartmentName());
        userDto.setUserEmailId(vendorDTO.getEmailId());
        return userDto;
    }

    public UserDTO parseTenantDetails(TenantDTO tenantDTO) {

        UserDTO userDto = new UserDTO();
        userDto.setUserFirstName(tenantDTO.getFirstName());
        userDto.setUserLastName(tenantDTO.getLastName());
        userDto.setApartmentId(tenantDTO.getApartmentId());
        userDto.setApartmentName(tenantDTO.getApartmentName());
        userDto.setUserEmailId(tenantDTO.getEmailId());
        return userDto;
    }

    public void pushToKafka(VisitorKafkaMessageDTO visitorKafkaMessageDTO) {
        visitorKafkaNotificationService.sendNotification(jsonUtils.toJson(visitorKafkaMessageDTO));

    }
    public VisitorKafkaMessageDTO createVisitorkafkaMessageDto(Visitor visitor) {
        VisitorKafkaMessageDTO visitorKafkaMessageDTO = modelMapper.map(visitor, VisitorKafkaMessageDTO.class);
        visitorKafkaMessageDTO.setUserFirstName(visitor.getVisitorFirstName());
        visitorKafkaMessageDTO.setUserLastName(visitor.getVisitorLastName());
        return visitorKafkaMessageDTO;
    }



}
