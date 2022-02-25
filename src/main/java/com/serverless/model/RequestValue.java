package com.serverless.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class RequestValue {

    @JsonProperty("NOK_per_kWh")
    private double nokPrice;
    @JsonProperty("valid_from")
    private Date validFrom;
    @JsonProperty("valid_to")
    private Date validTo;
}