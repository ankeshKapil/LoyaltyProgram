package com.cosmos.LoyaltyProgram.service;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Scheme;

public interface SchemeService {
	
	public Scheme createScheme(Scheme scheme);
	
	public DatatableCollection<Scheme> findAll();
	
	public void deleteScheme(Long id);

}
