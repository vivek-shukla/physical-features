package com.ibm.nutritium.pf.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.nutritium.pf.exception.domain.ExceptionInfo;

@Repository
public interface ExceptionRepository extends MongoRepository<ExceptionInfo, String> {
	

}
