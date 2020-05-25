package com.ibm.nutritium.pf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class PhysicalFeaturesConfigration {
    
	@Value("${service.url.recommendation}")
	private String recommendationUrl;

	public String getRecommendationUrl() {
		return recommendationUrl;
	}	
	
}
