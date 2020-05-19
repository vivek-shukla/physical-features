package com.ibm.nutritium.pf.repositary;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.nutritium.pf.domain.PhysicalFeature;

@Repository
public interface PhysicalFeatureRepositary extends MongoRepository<PhysicalFeature, String> {
   
	public Optional<PhysicalFeature> findByPhysicalFeatureId(String physicalFeatureId);
	
	public Optional<PhysicalFeature> findByUserId(String userId);
	
	
}
