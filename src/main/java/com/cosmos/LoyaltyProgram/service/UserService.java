package com.cosmos.LoyaltyProgram.service;

import com.cosmos.LoyaltyProgram.model.User;

public interface UserService {
	
	User findUserByMobileNumber(String mobileNumber);
	
	public void saveUser(User user);
	

}
