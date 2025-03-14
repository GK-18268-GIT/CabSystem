package com.system.model;

import java.sql.Timestamp;

public class SelectedCab {
	private String bookingId;
	private String cabCode;
	private String cabNumber;
	private String model;
	private String brand;
	private int capacity;
	private double basePrice;
	private double costPerKm;
	private String cabImage;
	private Timestamp createdAt;
	
	public SelectedCab(String bookingId, String cabCode, String cabNumber, String model, String brand, int capacity, 
			double basePrice, double costPerKm, String cabImage, Timestamp createdAt) {
		this.bookingId = bookingId;
		this.cabCode = cabCode;
		this.cabNumber = cabNumber;
		this.model = model;
		this.brand = brand;
		this.capacity = capacity;
		this.basePrice = basePrice;
		this.costPerKm = costPerKm;
		this.cabImage = cabImage;
		this.createdAt = createdAt;
	}
	
	public SelectedCab() {
		
	}
	
	public SelectedCab(String cabCode, double basePrice, double costPerKm) {
		this.cabCode = cabCode;
		this.basePrice = basePrice;
		this.costPerKm = costPerKm;
	}
	
	public String getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
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
	
}
