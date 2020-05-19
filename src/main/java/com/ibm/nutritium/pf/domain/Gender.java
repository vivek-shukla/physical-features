package com.ibm.nutritium.pf.domain;

import com.ibm.nutritium.pf.util.Constants;
import java.lang.Integer;
public enum Gender {
    
	MALE(Constants.ZERO),
	FEMALE(Constants.ONE);
	
	private Integer genderCode;
	
	private Gender(Integer code) {
		this.setGenderCode(code);
	}

	public void setGenderCode(Integer genderCode) {
		this.genderCode = genderCode;
	}

	public Integer getGenderCode() {
		return genderCode;
	}
	
	
}
