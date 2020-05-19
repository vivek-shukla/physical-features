package com.ibm.nutritium.pf.util;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    
	FP_NOT_FOUND(HttpStatus.NO_CONTENT);
	
	private HttpStatus errorStatus;
	
	private ErrorCode(HttpStatus errorStatus) {
		
		this.errorStatus = errorStatus;
	}
}
