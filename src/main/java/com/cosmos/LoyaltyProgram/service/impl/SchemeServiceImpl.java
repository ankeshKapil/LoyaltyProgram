package com.cosmos.LoyaltyProgram.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Scheme;
import com.cosmos.LoyaltyProgram.repository.SchemeRepository;
import com.cosmos.LoyaltyProgram.service.SchemeService;

@Service("schemeService")
public class SchemeServiceImpl implements SchemeService{

	@Autowired
	SchemeRepository schemeRepository;
	
	Logger logger=Logger.getLogger(this.getClass());
	
	@Transactional
	public Scheme createScheme(Scheme scheme){
		logger.info("Request to save scheme "+scheme);
		return schemeRepository.save(scheme);
	}
	
	public DatatableCollection<Scheme> findAll(){
		
		DatatableCollection<Scheme> collection=new DatatableCollection<>();
		collection.setData(schemeRepository.findAll());
		return collection;
	}
	
	public void deleteScheme(Long id){
		schemeRepository.delete(id);
	}
}
