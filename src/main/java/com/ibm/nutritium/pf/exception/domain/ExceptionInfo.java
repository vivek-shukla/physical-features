package com.ibm.nutritium.pf.exception.domain;

import org.springframework.data.annotation.Id;

import com.ibm.nutritium.pf.domain.PhysicalFeature;

import lombok.Data;

@Data
public class ExceptionInfo {
    
	@Id
	private String exceptionId;
	
	private PhysicalFeature payload;
	
	private String retryLink;
	
	private String errorMessage;
}
