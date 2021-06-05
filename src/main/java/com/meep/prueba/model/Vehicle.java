package com.meep.prueba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity  
@Table  
public class Vehicle {
	
	@Id  
	private String id;
	@Column 
	private String name;
	@Column 
	private Double x;
	@Column 
	private Double y;
	@Column 
	private String licencePlate;
	@Column 
	private Integer range;
	@Column 
	private Integer batteryLevel;
	@Column 
	private Integer helmets;
	@Column 
	private String model;
	@Column 
	private String resourceImageId;
	@Column 
	private Boolean realTimeData;
	@Column 
	private String resourceType;
	@Column 
	private Integer companyZoneId;

}
