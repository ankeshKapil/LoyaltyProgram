package com.cosmos.LoyaltyProgram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "d MMMM, yyyy HH:mm")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "trx_time", nullable = false)
    private LocalDateTime trxTime;

    @NotNull
    @Column(name = "fuel_volume", nullable = false)
    private BigDecimal fuelVolume;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @JoinColumn(name="card_number", referencedColumnName="card_number")
    @NotNull
    private Driver driver;
    @Column(name = "redeem")
    private boolean redeem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public BigDecimal getFuelVolume() {
        return fuelVolume;
    }

    public Transaction fuelVolume(BigDecimal fuelVolume) {
        this.fuelVolume = fuelVolume;
        return this;
    }

    public void setFuelVolume(BigDecimal fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Driver getDriver() {
        return driver;
    }

    public Transaction driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LocalDateTime getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(LocalDateTime trxTime) {
		this.trxTime = trxTime;
	}

	public boolean isRedeem() {
		return redeem;
	}

	public void setRedeem(boolean redeem) {
		this.redeem = redeem;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", time='" + getTrxTime() + "'" +
            ", fuelVolume='" + getFuelVolume() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }
}
