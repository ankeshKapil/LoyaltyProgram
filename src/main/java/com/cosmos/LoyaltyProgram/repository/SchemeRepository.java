package com.cosmos.LoyaltyProgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosmos.LoyaltyProgram.model.Scheme;

@Repository("schemeRepository")
public interface SchemeRepository  extends JpaRepository<Scheme, Long>{

}
