package com.system.model;

import java.sql.Timestamp;

public class Invoice {
	private String invoiceId;
	private String bookingId;
	private double basePrice;
	private double distance;
	private double costPerKm;
	private double tax;
	private double driverCost;
	private double totalAmount;
	private Timestamp createdAt;
	
	public Invoice(String invoiceId, String bookingId, double basePrice, double distance, double costPerKm,
			double tax, double driverCost, double totalAmount) {
		this.invoiceId = invoiceId;
		this.bookingId = bookingId;
		this.basePrice = basePrice;
		this.distance = distance;
		this.costPerKm = costPerKm;
		this.tax = tax;
		this.driverCost = driverCost;
		this.totalAmount = totalAmount;
	}
	
	public Invoice() {
		
	}
	
	public Invoice(String invoiceId, double totalAmount) {
		this.invoiceId = invoiceId;
		this.totalAmount = totalAmount;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getCostPerKm() {
		return costPerKm;
	}

	public void setCostPerKm(double costPerKm) {
		this.costPerKm = costPerKm;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getDriverCost() {
		return driverCost;
	}

	public void setDriverCost(double driverCost) {
		this.driverCost = driverCost;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	

}
