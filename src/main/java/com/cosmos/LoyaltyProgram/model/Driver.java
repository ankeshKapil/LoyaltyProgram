package com.cosmos.LoyaltyProgram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "card_number", nullable = false,unique=true)
    private Long cardNumber;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "phone_number", length = 10, nullable = false,unique=true)
    private String phoneNumber;

    @JsonFormat(pattern = "d MMMM, yyyy HH:mm")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @JsonFormat(pattern = "d MMMM, yyyy HH:mm")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;

    @Column(name = "id_card_type")
    private String idCardType;

    @Column(name = "id_card_number")
    private String idCardNumber;

    @NotNull
    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "common_routes")
    private String commonRoutes;
    
    @OneToMany(mappedBy = "driver",cascade=CascadeType.ALL)
    private Set<Transaction> transactions;
    


   
    @ManyToOne(optional = false)
    @NotNull
    private Scheme scheme;
    
  
    @Column(name="loyalty_points")
    private long loyaltyPoints;
    
  
    @Column(name="total_fuel")
    private BigDecimal totalFuelVolume;
    
    
    
    public BigDecimal getTotalFuelVolume() {
		return totalFuelVolume;
	}

	public void setTotalFuelVolume(BigDecimal totalFuelVolume) {
		this.totalFuelVolume = totalFuelVolume;
	}

	public Driver scheme(Scheme scheme){
    	this.scheme=scheme;
    	return this;
    }

    public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommonRoutes() {
		return commonRoutes;
	}

	public void setCommonRoutes(String commonRoutes) {
		this.commonRoutes = commonRoutes;
	}

	public long getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(long loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    

  
}
