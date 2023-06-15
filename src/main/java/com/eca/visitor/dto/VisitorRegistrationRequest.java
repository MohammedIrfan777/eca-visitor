package com.eca.visitor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorRegistrationRequest {

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
    private String purposeOfVisiting;



}
