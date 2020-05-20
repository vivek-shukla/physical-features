package com.ibm.nutritium.pf.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ibm.nutritium.pf.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalFeatureDTO {
   
	@Min(value = Constants.ZERO)
    private Float height;
    
    @Min(value = Constants.ZERO)
    private Float weight;
    
    @NotNull
    private Long dateOfBirth;
    
    @NotNull
    private Integer genderCode;
    
    @Min(value = Constants.ONE)
    private Float activityFactor;
}
