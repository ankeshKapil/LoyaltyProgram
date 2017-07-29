package com.cosmos.LoyaltyProgram.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "prize")
@Entity
public class Prize implements Comparable<Prize>, Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long prizeId;

	@NotEmpty
	@JsonProperty("prizeName")
	private String prizeName;

	
	@JsonProperty("targetFuel")
	private int targetFuel;
	


	
	public long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(long prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public int getTargetFuel() {
		return targetFuel;
	}

	public void setTargetFuel(int targetFuel) {
		this.targetFuel = targetFuel;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Prize [id=").append(prizeId).append(", prizeName=").append(prizeName).append(", targetFuel=")
				.append(targetFuel).append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Prize o) {
		
		return o.targetFuel-this.targetFuel;
	}
	

}
