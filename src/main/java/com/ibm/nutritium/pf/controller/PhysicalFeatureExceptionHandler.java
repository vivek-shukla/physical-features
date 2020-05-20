package com.ibm.nutritium.pf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibm.nutritium.pf.exception.PhysicalFeatureException;

/*
 * @author vshukl13
 */
@ControllerAdvice
public class PhysicalFeatureExceptionHandler {
	
	/*
	 * Handles PhysicalFeatureException 
	 * 
	 * @param PhysicalFeatureException
	 * @return ResponseEntity
	 * 
	 */
	@ExceptionHandler(value = PhysicalFeatureException.class)
	public ResponseEntity<?> customException(PhysicalFeatureException p){
		
		return ResponseEntity.status(p.getErrorCode().getErrorStatus()).body(p.getMessage());
		
	}
	
	/*
	 * Handles System Exception
	 * 
	 * @param PhysicalFeatureException
	 * @return ResponseEntity - INTERNAL_SERVER_ERROR
	 * 
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> customException(Exception e){
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		
	}

}
