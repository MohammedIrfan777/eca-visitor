package com.eca.visitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    @JsonProperty("type")
    private String type;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNo")
    private long phoneNo;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("addressLine")
    private String addressLine;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("apartmentId")
    private int apartmentId;

    @JsonProperty("apartmentName")
    private String apartmentName;

    @JsonProperty("id")
    private int id;

    // Getter and setter methods
}
