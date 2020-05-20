package com.ibm.nutritium.pf.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhysicalFeature {
    
	@Id
	private String physicalFeatureId;
	
	@UniqueElements
    private String userId;
    
    @Min(value = 0)
    private Float height;
    
    @Min(value = 0)
    private Float weight;
    
    private LocalDateTime dateOfBirth;
    
    private Gender gender;
    
    private Float bmi;
    
    private Float bmr;
    
    private Float tdee;
    
    public PhysicalFeature() {
    	this.physicalFeatureId = UUID.randomUUID().toString();
    }
}
