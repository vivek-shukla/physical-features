package com.ibm.nutritium.pf.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhysicalFeature {
    
	
	private String physicalFeatureId;
	
    private String userId;
    
    @Min(value = 0)
    private Float height;
    
    @Min(value = 0)
    private Float weight;
    
    private ZonedDateTime dateOfBirth;
    
    private Gender gender;
    
    private Float bmi;
    
    private Float bmr;
    
    private Float caloriesBurn;
    
    public PhysicalFeature() {
    	this.physicalFeatureId = UUID.randomUUID().toString();
    }
}
