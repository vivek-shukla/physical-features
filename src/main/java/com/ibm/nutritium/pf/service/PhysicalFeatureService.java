package com.ibm.nutritium.pf.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.nutritium.pf.domain.Gender;
import com.ibm.nutritium.pf.domain.PhysicalFeature;
import com.ibm.nutritium.pf.dto.PhysicalFeatureDTO;
import com.ibm.nutritium.pf.exception.ExceptionProcessor;
import com.ibm.nutritium.pf.exception.PhysicalFeatureException;
import com.ibm.nutritium.pf.repository.PhysicalFeatureRepository;
import com.ibm.nutritium.pf.util.Constants;
import com.ibm.nutritium.pf.util.ErrorCode;

import lombok.extern.slf4j.Slf4j;

/*
 * @author vshukl13
 */

@Service
@Slf4j
public class PhysicalFeatureService {
	
	@Autowired
	private PhysicalFeatureRepository physicalFeatureRepositary;
	
	@Autowired
	private PhysicalFeaturesRESTCall physicalFeaturesRESTCall;
	
	@Autowired
	private ExceptionProcessor exceptionProcessor;
	
	/*
	 * Create Physical Feature if userId not present in database else create
	 * @param physicalFeatureDTO
	 * @param userId
	 * 
	 * @return physicalFeatureDTO
	 */
	public PhysicalFeature save(PhysicalFeatureDTO physicalFeatureDTO,String userId) {
		
		
		PhysicalFeature physicalFeature = this.physicalFeatureRepositary
				.findByUserId(userId)
				.orElse(new PhysicalFeature());
		physicalFeature.setUserId(userId);
		physicalFeature.setHeight(physicalFeatureDTO.getHeight());
		physicalFeature.setWeight(physicalFeatureDTO.getWeight());
		physicalFeature.setDateOfBirth(
		        LocalDateTime.ofInstant(Instant.ofEpochMilli(physicalFeatureDTO.getDateOfBirth()), 
                        TimeZone.getDefault().toZoneId()));
		Float activityFactor = physicalFeatureDTO.getActivityFactor();
		switch(physicalFeatureDTO.getGenderCode()){
		   case Constants.ZERO:
			   physicalFeature.setGender(Gender.MALE);
			   calculateBMRAndBMI(physicalFeature,Constants.BMR_FACTOR_MALE,activityFactor);
			   break;
		   case Constants.ONE:
			   physicalFeature.setGender(Gender.FEMALE);
			   calculateBMRAndBMI(physicalFeature, Constants.BMR_FACTOR_FEMALE,activityFactor);
			   break;
		   default:
			   physicalFeature.setGender(Gender.MALE);
			   calculateBMRAndBMI(physicalFeature,Constants.BMR_FACTOR_MALE,activityFactor);
			   break;
		}
		
		PhysicalFeature pf = this.physicalFeatureRepositary.save(physicalFeature);
		try {
			log.info("Feeding Recommendation Engine for:: ",pf.getPhysicalFeatureId());
			physicalFeaturesRESTCall.feedRecommentationML(pf);
		} catch (Exception e) {
			log.error("Exception While Feeding Recommendation engine");
			exceptionProcessor.processExcetion(e, pf);	
		}
		return pf;
	}
	
	/*
	 * Calculate BMR,BMI,and TDEE
	 * @param physicalFeature
	 * @param bmrFactor
	 * @param activityFactor
	 * 
	 * @return
	 */
	private void calculateBMRAndBMI(PhysicalFeature physicalFeature, int bmrFactor,float activityFactor) {
		LocalDateTime dob = physicalFeature.getDateOfBirth();
		Float height = physicalFeature.getHeight();
		Float weight = physicalFeature.getWeight();
		Float bmi =  (weight/(height * height)) * Constants.TEN_THOUSAND;
		Period period = Period.between(LocalDate.now(), dob.toLocalDate());
		int age = period.getYears();
		Float bmr = Constants.FLOAT_TEN * weight + Constants.SIX_POINT_TWO_FIVE * height - Constants.FLOAT_FIVE * age 
				   + bmrFactor;
		physicalFeature.setBmi(bmi);
		physicalFeature.setBmr(bmr);
		physicalFeature.setTdee(bmr*activityFactor);
	}
    
	/*
	 * Delete PhysicalFeature based on physicalFeatureId
	 * @param physicalFeatureId
	 * 
	 * @return
	 */
	public void delete(String physicalFeatureId) {
		this.physicalFeatureRepositary.deleteById(physicalFeatureId);
	}
	
	/*
	 * Fetch PhysicalFeature based on physicalFeatureId
	 * @param physicalFeatureId
	 * @throws PhysicalFeatureException
	 * @return PhysicalFeature
	 */
	public PhysicalFeature fetchById(String physicalFeatureId) throws PhysicalFeatureException {
		
		return this.physicalFeatureRepositary.findByPhysicalFeatureId(physicalFeatureId)
				.orElseThrow(()->new PhysicalFeatureException(ErrorCode.FP_NOT_FOUND));
	}
	
	/*
	 * Fetch PhysicalFeature based on userId
	 * @param userId
	 * @throws PhysicalFeatureException
	 * @return PhysicalFeature
	 */
	public PhysicalFeature fetchByUserId(String userId) throws PhysicalFeatureException {
		
		return this.physicalFeatureRepositary.findByUserId(userId)
				.orElseThrow(()->new PhysicalFeatureException(ErrorCode.FP_NOT_FOUND));
	}
    
	/*
	 * Delete PhysicalFeature based on userId
	 * @param userId
	 * @throws PhysicalFeatureException
	 * @return 
	 */
	public void deleteByUserId(String userId) throws PhysicalFeatureException {
		PhysicalFeature pFeature = this.physicalFeatureRepositary.findByUserId(userId)
				.orElseThrow(()->new PhysicalFeatureException(ErrorCode.FP_NOT_FOUND));
		this.physicalFeatureRepositary.delete(pFeature);
		
	}
	

}
