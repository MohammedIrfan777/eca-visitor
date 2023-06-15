package com.eca.visitor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Entity
@Slf4j
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Visitor implements Serializable {

    private static final AtomicLong counter = new AtomicLong(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitorId")
    private long visitorId;
    @Column(name = "createdOn",nullable = false,updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "updatedOn",nullable = false)
    private  LocalDateTime updatedOn;

    @Version
    private Integer version;

    @Column(name = "visitorFirstName")
    @NotEmpty
    private String visitorFirstName;

    @Column(name = "userFirstName")
    private String userFirstName;

    @Column(name = "userLastName")
    private String userLastName;

    @Column(name = "visitorLastName")
    private String visitorLastName;
    @Column(name = "visitorRequestId",unique = true)
    private String visitorRequestId;
    @Column(name = "visitorAddressLine")
    private String visitorAddressLine;

    @Column(name = "visitorCity")
    private String visitorCity;

    @Column(name = "visitorState")
    private String visitorState;

    @Column(name = "visitorZipCode")
    private String visitorZipCode;

    @Column(name = "userPhoneNo")
    @NotNull
    private Long userPhoneNo;

    @Column(name = "userEmailId")
    private String userEmailId;
    @Column(name = "purposeOfVisiting")
    private String purposeOfVisiting;

    @JsonProperty("apartmentId")
    private int apartmentId;

    @JsonProperty("apartmentName")
    private String apartmentName;

    @PrePersist
    protected void setOnCreation() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
        visitorRequestId = "VREQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @PreUpdate
    protected void setUpdatedOn() {
        updatedOn = LocalDateTime.now();
        visitorRequestId = "VREQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
