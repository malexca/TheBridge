package com.alexander.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "idvehicle")
	    private int vehicleId;

	    @Column(name = "type", nullable = false)
	    private String type;

	    @ManyToOne
	    @JoinColumn(name = "iddriver", nullable = false)
	    private Driver driver;
	   
	    // constructor empty
		public Vehicle() {
			super();
		}

		public Vehicle(String type, Driver driver) {
			super();
			this.type = type;
			this.driver = driver;
		}

		public int getVehicleId() {
			return vehicleId;
		}

		public void setVehicleId(int vehicleId) {
			this.vehicleId = vehicleId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Driver getDriver() {
			return driver;
		}

		public void setDriver(Driver driver) {
			this.driver = driver;
		}

		@Override
		public String toString() {
			return "Vehicle [vehicleId=" + vehicleId + ", type=" + type + ", driver=" + driver + "]";
		}

}
