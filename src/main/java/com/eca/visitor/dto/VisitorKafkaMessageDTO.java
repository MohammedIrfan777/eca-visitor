package com.eca.visitor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorKafkaMessageDTO {
    private Long visitorId;
    @NotEmpty
    private String visitorFirstName;

    private String visitorLastName;
    private String visitorAddressLine;
    private String visitorCity;
    private String visitorState;

    private String visitorZipCode;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @NotNull
    private Long userPhoneNo;

    private String userFirstName;
    private String userLastName;

    @NotEmpty
    private String userEmailId;
    private String purposeOfVisiting;
    @NotEmpty
    private String visitorRequestId;

}
