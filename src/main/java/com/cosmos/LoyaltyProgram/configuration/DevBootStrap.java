package com.cosmos.LoyaltyProgram.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.cosmos.LoyaltyProgram.model.Role;
import com.cosmos.LoyaltyProgram.repository.RoleRepository;
import com.cosmos.LoyaltyProgram.repository.UserRepository;

@Component
public class DevBootStrap implements ApplicationListener<ContextRefreshedEvent>{
	
	private RoleRepository roleRepository;

	
	public DevBootStrap(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		initData();
		
	}

	private void initData(){
		Role role=new Role();
		role.setId(1);
		role.setRole("ADMIN");
		roleRepository.save(role);
	}
}
