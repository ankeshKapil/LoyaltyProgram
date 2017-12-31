package com.cosmos.LoyaltyProgram.model;

import java.io.Serializable;
import java.util.Collection;

public class DatatableCollection<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7495696143302825860L;
	private Collection<T> data;

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}
	
	

}
