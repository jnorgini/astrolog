package com.norgini.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResult {
	@JsonProperty("lat")
	private double latitude;

	@JsonProperty("lon")
	private double longitude;
}
