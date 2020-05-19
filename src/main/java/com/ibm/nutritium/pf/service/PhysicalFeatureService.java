package com.ibm.nutritium.pf.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.nutritium.pf.domain.Gender;
import com.ibm.nutritium.pf.domain.PhysicalFeature;
import com.ibm.nutritium.pf.dto.PhysicalFeatureDTO;
import com.ibm.nutritium.pf.exception.PhysicalFeatureException;
import com.ibm.nutritium.pf.repositary.PhysicalFeatureRepositary;
import com.ibm.nutritium.pf.util.Constants;
import com.ibm.nutritium.pf.util.ErrorCode;

@Service
public class PhysicalFeatureService {
	
	@Autowired
	private PhysicalFeatureRepositary physicalFeatureRepositary;
	
	public PhysicalFeature save(PhysicalFeatureDTO physicalFeatureDTO,String userId) {
		
		PhysicalFeature physicalFeature = new PhysicalFeature();
		physicalFeature.setUserId(userId);
		physicalFeature.setHeight(physicalFeatureDTO.getHeight());
		physicalFeature.setWeight(physicalFeatureDTO.getWeight());
		physicalFeature.setDateOfBirth(ZonedDateTime.ofInstant(Instant.ofEpochSecond(physicalFeatureDTO.getDateOfBirth()),ZoneId.systemDefault()));
		switch(physicalFeatureDTO.getGenderCode()){
		   case Constants.ZERO:
			   physicalFeature.setGender(Gender.MALE);
			   calculateBMRAndBMI(physicalFeature,Constants.BMR_FACTOR_MALE);
			   break;
		   case Constants.ONE:
			   physicalFeature.setGender(Gender.FEMALE);
			   calculateBMRAndBMI(physicalFeature, Constants.BMR_FACTOR_FEMALE);
			   break;
		   default:
			   physicalFeature.setGender(Gender.MALE);
			   calculateBMRAndBMI(physicalFeature,Constants.BMR_FACTOR_MALE);
			   break;
		}
		
		return this.physicalFeatureRepositary.save(physicalFeature);
	}
	
	private void calculateBMRAndBMI(PhysicalFeature physicalFeature, int bmrFactor) {
		ZonedDateTime dob = physicalFeature.getDateOfBirth();
		Float height = physicalFeature.getHeight();
		Float weight = physicalFeature.getWeight();
		Float bmi =  (weight/(height * height)) * Constants.TEN_THOUSAND;
		Period period = Period.between(LocalDate.now(), dob.toLocalDate());
		int age = period.getYears();
		Float bmr = Constants.FLOAT_TEN * weight + Constants.SIX_POINT_TWO_FIVE * height - Constants.FLOAT_FIVE * age 
				   + bmrFactor;
		physicalFeature.setBmi(bmi);
		physicalFeature.setBmr(bmr);
	}

	public void delete(String physicalFeatureId) {
		this.physicalFeatureRepositary.deleteById(physicalFeatureId);
	}
	
	public PhysicalFeature fetchById(String physicalFeatureId) throws PhysicalFeatureException {
		
		return this.physicalFeatureRepositary.findByPhysicalFeatureId(physicalFeatureId)
				.orElseThrow(()->new PhysicalFeatureException(ErrorCode.FP_NOT_FOUND));
	}
	
	public PhysicalFeature fetchByUserId(String userId) throws PhysicalFeatureException {
		
		return this.physicalFeatureRepositary.findByUserId(userId)
				.orElseThrow(()->new PhysicalFeatureException(ErrorCode.FP_NOT_FOUND));
	}

	public void deleteByUserId(String userId) throws PhysicalFeatureException {
		PhysicalFeature pFeature = this.physicalFeatureRepositary.findByUserId(userId)
				.orElseThrow(()->new PhysicalFeatureException(ErrorCode.FP_NOT_FOUND));
		this.physicalFeatureRepositary.delete(pFeature);
		
	}
	

}
