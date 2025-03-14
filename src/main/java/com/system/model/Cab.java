package com.system.model;

import java.sql.Timestamp;

public class Cab {
	private int cabId;
	private String cabCode;
	private String cabNumber;//license plate
	private String model;
	private String brand;
	private int capacity;
	private double basePrice;
	private double costPerKm;
	private String status;
	private String cabImage;
	private Timestamp createdAt;
	private String driverCode;
	private String driverName;


	public Cab(int cabId, String cabCode, String cabNumber, String model, String brand, int capacity,
			double basePrice, double costPerKm, String status, String cabImage) {
		this.cabId = cabId;
		this.cabCode = cabCode;
		this.cabNumber = cabNumber;
		this.model = model;
		this.brand = brand;
		this.capacity = capacity;
		this.basePrice = basePrice;
		this.costPerKm = costPerKm;
		this.status = status;
		this.cabImage = cabImage;
	}

	public Cab() {
		
	}

	public int getCabId() {
		return cabId;
	}
	
	public void setCabId(int cabId) {
		this.cabId = cabId;
	}
	
	public String getCabCode() {
		return cabCode;
	}
	
	public void setCabCode(String cabCode) {
		this.cabCode = cabCode;
	}
	
	public String getCabNumber() {
		return cabNumber;
	}
	
	public void setCabNumber(String cabNumber) {
		this.cabNumber = cabNumber;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public double getBasePrice() {
		return basePrice;
	}
	
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	
	public double getCostPerKm() {
		return costPerKm;
	}
	
	public void setCostPerKm(double costPerKm) {
		this.costPerKm = costPerKm;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCabImage() {
		return cabImage;
	}
	
	public void setCabImage(String cabImage) {
		this.cabImage = cabImage;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
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
	
}




