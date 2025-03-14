package com.system.model;

import java.sql.Timestamp;

public class Payment {
	private String  paymentId;
	private String  bookingId;
	private String  invoiceId;
	private double  totalAmount;
	private String  paymentMethod;
	private String  cardNumber;
	private String  cardHolder;
	private String  expiry;
	private String  cvv;
	private String  status;
	private Timestamp createdAt;
	
	public Payment(String paymentId, String bookingId, String invoiceId, double totalAmount, String paymentMethod, String cardNumber, String cardHolder,
			String expiry, String cvv, String status) {
		this.paymentId = paymentId;
		this.bookingId = bookingId;
		this.invoiceId = invoiceId;
		this.totalAmount = totalAmount;
		this.paymentMethod = paymentMethod;
		this.cardNumber = cardNumber;
		this.cardHolder = cardHolder;
		this.expiry = expiry;
		this.cvv = cvv;
		this.status = status;
	}
	
	public Payment() {
		
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	
	public String getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
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
