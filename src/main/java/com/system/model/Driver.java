package com.system.model;

import java.sql.Timestamp;

public class Driver {
	private int driverId;
	private String driverCode;
	private String driverName;
	private int driverAge;
	private String driverPhone;
	private String driverEmail;
	private String driverAddress;
	private String driverNIC;
	private String status;
	private Timestamp createdAt;
	
	public Driver(int driverId, String driverCode, String driverName, int driverAge, String driverPhone, String driverEmail, 
			String driverAddress, String driverNIC, String status) {
		this.driverId = driverId;
		this.driverCode = driverCode;
		this.driverName = driverName;
		this.driverAge = driverAge;
		this.driverPhone = driverPhone;
		this.driverEmail = driverEmail;
		this.driverAddress = driverAddress;
		this.driverNIC = driverNIC;
		this.status = status;
	}
	
	public Driver() {
		
	}
	
	public int getDriverId() {
		return driverId;
	}
	
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	
	public String getDriverCode() {
		return driverCode;
	}
	
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public int getDriverAge() {
		return driverAge;
	}
	
	public void setDriverAge(int driverAge) {
		this.driverAge = driverAge;
	}
	
	public String getDriverPhone() {
		return driverPhone;
	}
	
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	
	public String getDriverEmail() {
		return driverEmail;
	}
	
	public void setDriverEmail(String driverEmail) {
		this.driverEmail = driverEmail;
	}
	
	public String getDriverAddress() {
		return driverAddress;
	}
	
	public void setDriverAddress(String driverAddress) {
		this.driverAddress = driverAddress;
	}
	
	public String getDriverNIC() {
		return driverNIC;
	}
	
	public void setDriverNIC(String driverNIC) {
		this.driverNIC = driverNIC;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}
