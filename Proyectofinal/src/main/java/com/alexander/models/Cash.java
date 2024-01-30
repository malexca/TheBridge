package com.alexander.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cash")
public class Cash {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcash")
    private int cashId;

    @Column(name = "date")
    private Date date;
    
    @Column(name = "kilometre", nullable = false)
    private int kilometre;
    
    @Column(name = "income", nullable = false)
    private int income;
    
    @Column(name = "expense", nullable = false)
    private int expense;

    @ManyToOne
    @JoinColumn(name = "idvehicle", nullable = false)
    private Vehicle vehicle;
    
    // constructor empty
	public Cash() {
		super();
	}

	public Cash(Date date, int kilometre, int income, int expense, Vehicle vehicle) {
		super();
		this.date = date;
		this.kilometre = kilometre;
		this.income = income;
		this.expense = expense;
		this.vehicle = vehicle;
	}

	public int getCashId() {
		return cashId;
	}

	public void setCashId(int cashId) {
		this.cashId = cashId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getKilometre() {
		return kilometre;
	}

	public void setKilometre(int kilometre) {
		this.kilometre = kilometre;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return "Cash [cashId=" + cashId + ", date=" + date + ", kilometre=" + kilometre + ", income=" + income
				+ ", expense=" + expense + ", vehicle=" + vehicle + "]";
	}
	
	   
}
