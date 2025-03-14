package com.system.model;

import java.sql.Timestamp;

public class Booking {
	private int bookingNumber;
	private String bookingId;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private String pickupLocation;
	private Timestamp pickupDateTime;
	private String destination;
	private int passengers;
	private Timestamp createdAt;
	

	public Booking(int bookingNumber, String bookingId, String customerName, String customerAddress, String customerPhone, String pickupLocation, Timestamp pickupDateTime,
			String destination, int passengers, Timestamp createdAt) {
		this.bookingNumber = bookingNumber;
		this.bookingId = bookingId;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerPhone = customerPhone;
		this.pickupLocation = pickupLocation;
		this.pickupDateTime = pickupDateTime;
		this.destination = destination;
		this.passengers = passengers;
		this.createdAt = createdAt;
		
	}

	public Booking() {
		
	}
	
	public Booking(String bookingId) {
        this.bookingId = bookingId;
    }
	
	public int getBookingNumber() {
		return bookingNumber;
	}
	
	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	
	public String getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerAddress() {
		return customerAddress;
	}
	
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	public String getCustomerPhone() {
		return customerPhone;
	}
	
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	
	public String getPickupLocation() {
		return pickupLocation;
	}
	
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	
	public Timestamp getPickupDateTime() {
		return pickupDateTime;
	}
	
	public void setPickupDateTime(Timestamp pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public int getPassengers() {
		return passengers;
	}
	
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}
