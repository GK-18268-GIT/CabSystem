package com.system.model;

public class Location {
	private String pickupLocation;
	private String destination;


	public Location(String pickupLocation, String destination) {
		this.pickupLocation = pickupLocation;
		this.destination = destination;
	}
	
	public Location() {
		
	}
	
	public String getPickupLocation() {
		return pickupLocation;
	}
	
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
}	
