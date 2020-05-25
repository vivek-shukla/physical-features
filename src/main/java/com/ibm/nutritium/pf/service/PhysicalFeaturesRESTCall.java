package com.ibm.nutritium.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.nutritium.pf.config.PhysicalFeaturesConfigration;
import com.ibm.nutritium.pf.domain.PhysicalFeature;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhysicalFeaturesRESTCall {
	
	@Autowired
	private PhysicalFeaturesConfigration physicalFeaturesConfigration;
	
	@HystrixCommand(fallbackMethod = "fallbackFeedRecommentationML",commandKey = "feedRecommentationML")
	public void feedRecommentationML(PhysicalFeature physicalFeature) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		String reqBody = mapper.writeValueAsString(physicalFeature);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<String> entity = new HttpEntity<>(reqBody,headers);
		restTemplate.exchange(physicalFeaturesConfigration.getRecommendationUrl(), HttpMethod.POST, entity, String.class);
	}
	
	public void fallbackFeedRecommentationML(PhysicalFeature physicalFeature,Throwable e) {
		log.info("Feeding Recommendation Engine Failed");
		String message = "Feeding Recommendation Engine Failed:: "+e.getMessage();
		throw new RestClientException(message);
	}

}
