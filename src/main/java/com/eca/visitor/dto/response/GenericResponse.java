package com.eca.visitor.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected LocalDateTime timestamp;
    @JsonProperty(value="genericHeader")
    protected String genericHeader;
}
