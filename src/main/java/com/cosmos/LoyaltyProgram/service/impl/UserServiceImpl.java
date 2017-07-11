package com.cosmos.LoyaltyProgram.service.impl;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cosmos.LoyaltyProgram.model.Role;
import com.cosmos.LoyaltyProgram.model.User;
import com.cosmos.LoyaltyProgram.repository.RoleRepository;
import com.cosmos.LoyaltyProgram.repository.UserRepository;
import com.cosmos.LoyaltyProgram.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByMobileNumber(String mobileNumber) {
		return userRepository.findBymobileNumber(mobileNumber);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}
