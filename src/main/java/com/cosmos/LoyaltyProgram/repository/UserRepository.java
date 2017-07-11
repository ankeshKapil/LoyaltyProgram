package com.cosmos.LoyaltyProgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosmos.LoyaltyProgram.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findBymobileNumber(String mobileNumber);

}
