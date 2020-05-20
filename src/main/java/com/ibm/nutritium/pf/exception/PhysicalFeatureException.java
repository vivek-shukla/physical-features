package com.ibm.nutritium.pf.exception;

import com.ibm.nutritium.pf.util.ErrorCode;

public class PhysicalFeatureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216347813289142867L;

	private ErrorCode errorCode;
	
	private String message;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PhysicalFeatureException(ErrorCode errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	public PhysicalFeatureException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
