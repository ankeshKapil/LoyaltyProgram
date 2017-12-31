package com.cosmos.LoyaltyProgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmos.LoyaltyProgram.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole(String role);

}
