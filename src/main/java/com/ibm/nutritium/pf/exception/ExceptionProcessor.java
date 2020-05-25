package com.ibm.nutritium.pf.exception;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ibm.nutritium.pf.domain.PhysicalFeature;
import com.ibm.nutritium.pf.exception.domain.ExceptionInfo;
import com.ibm.nutritium.pf.repository.ExceptionRepository;
import com.ibm.nutritium.pf.service.PhysicalFeaturesRESTCall;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExceptionProcessor {
	
	@Autowired
	private ExceptionRepository exceptionRepository;
	
	@Autowired
    private PhysicalFeaturesRESTCall physicalFeaturesRESTCall;
	
	//@Scheduled(fixedDelay = 3000)
	public void retryScheduler() { 
		log.info("Starting ExceptionProcessor => retryScheduler() at:: {}",LocalDate.now());
		List<ExceptionInfo> info = this.exceptionRepository.findAll();
		info.stream().forEach(retryRecommendationFeed());
		log.info("Completed ExceptionProcessor => retryScheduler() at:: {}",LocalDate.now());
		
	}

	private Consumer<ExceptionInfo> retryRecommendationFeed() {
		
		return exInfo -> {
			log.info("Starting Remediation for exception:: {}",exInfo.getExceptionId());
			try {
				this.physicalFeaturesRESTCall.feedRecommentationML(exInfo.getPayload());
				log.info("Successful Remediation for:: {}",exInfo.getExceptionId());
				this.exceptionRepository.delete(exInfo);
			} catch (Exception e) {
				log.info("Failed Remediation for:: {}",exInfo.getExceptionId());
			}
			log.error("Completed Remediation for exception:: {}",exInfo.getExceptionId());
		};
	}
	
	public void processExcetion(Exception ex,PhysicalFeature paylod) {
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		exceptionInfo.setExceptionId(UUID.randomUUID().toString());
		exceptionInfo.setErrorMessage(ex.getMessage());
		exceptionInfo.setPayload(paylod);
	    this.exceptionRepository.save(exceptionInfo);	
	}
}
