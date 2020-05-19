package com.ibm.nutritium.pf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.nutritium.pf.util.Constants;
import com.ibm.nutritium.pf.domain.PhysicalFeature;
import com.ibm.nutritium.pf.dto.PhysicalFeatureDTO;
import com.ibm.nutritium.pf.exception.PhysicalFeatureException;
import com.ibm.nutritium.pf.service.PhysicalFeatureService;

@Controller
@RequestMapping(path = Constants.VERSION)
public class PhysicalFeatureController {
	
	@Autowired
	private PhysicalFeatureService physicalFeatureService;
    
	@PostMapping(path = Constants.SAVE_URL,consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhysicalFeature> save(@RequestBody(required = true) @Valid PhysicalFeatureDTO physicalFeatureDTO,
			@PathVariable(required = true,name = "userId") String userId){
		
		return ResponseEntity.ok(this.physicalFeatureService.save(physicalFeatureDTO, userId));
	}
	
	@GetMapping(path = Constants.GET_BY_USER_URL,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhysicalFeature> fetchByUserId(@PathVariable(required = true,name = "userId") String userId) 
			throws PhysicalFeatureException{
		
		return ResponseEntity.ok(this.physicalFeatureService.fetchByUserId(userId));
	}
	
	@GetMapping(path = Constants.DELETE_BY_ID,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhysicalFeature> deleteByUserId(@PathVariable(required = true,name = "userId") String userId) 
			throws PhysicalFeatureException{
		this.physicalFeatureService.deleteByUserId(userId);
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
}
