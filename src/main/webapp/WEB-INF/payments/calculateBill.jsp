<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System- Calculate Bill</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
	<style>
		body {
		font-family: 'Poppins', sans-serif;
		background-color: #f0fdf4; 
		margin: 0;
		padding: 20px;
		color: #065f46; 
	}

	.bill-container {
		max-width: 800px;
		margin: 20px auto;
		background: #ffffff; 
		padding: 30px;
		border-radius: 10px;
		box-shadow: 0 0 20px rgba(0, 128, 0, 0.2); 
	}
	
	.header {
		text-align: center;
		border-bottom: 2px solid #a7f3d0; 
		padding-bottom: 20px;
		margin-bottom: 30px;
		color: #166534; 
	}
	
	.cab-info {
		display: flex;
		gap: 20px;
		margin-bottom: 30px;
	}
	
	.cab-image {
		width: 200px;
		height: 150px;
		border-radius: 8px;
		object-fit: cover;
		border: 3px solid #10b981;
	}
	
	.details-table {
		width: 100%;
		border-collapse: collapse;
		margin: 20px 0;
		background-color: #f5fff5; 
	}
	
	.details-table td, .details-table th {
		padding: 12px;
		border: 1px solid #a7f3d0; 
		text-align: left;
		color: #065f46; 
	}
	
	.details-table th {
		background-color: #d1fae5; 
	}
	
	.total-section {
		margin-top: 30px;
		padding: 20px;
		background-color: #ecfdf5; 
		border-radius: 8px;
	}
	
	.pay-btn {
		background-color: #10b981; 
		color: white;
		padding: 12px 24px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		margin-top: 15px;
		font-size: 16px;
		transition: background-color 0.3s ease-in-out, transform 0.2s ease-in-out;
	}
	
	.pay-btn:hover {
		background-color: #065f46; 
		transform: scale(1.05);
	}
	
	tr[style*="font-weight: bold"] {
		background-color: #d1fae5 !important; 
		font-weight: bold;
	}

		
	</style>
	
</head>
<body>
<div class = "bill-container">
	<div class ="header">
		<h1>Mega City Cab Service</h1>
		<h3>Booking Invoice</h3>
	</div>
	
	<div class ="cab-info">
		<img src ="uploads/${selectedCab.cabImage}" alt ="Cab Image" class ="cab-image">
		<div>
			<h3>Cab Details</h3>
			<p>Model: ${selectedCab.model}</p>
			<p>Brand: ${selectedCab.brand}</p>
			<p>License Plate: ${selectedCab.cabNumber}</p>
		</div>
	</div>
	
	<table class ="details-table">
	
		<tr>
			<th>Invoice ID</th>
			<td>${invoice.invoiceId}</td>
			<th>Booking ID</th>
			<td>${invoice.bookingId}</td>
		</tr>
			
		<tr>
			<th>Base Price</th>
			<td>Rs.${invoice.basePrice}</td>
			<th>Distance</th>
			<td>${invoice.distance} Km</td>
		</tr>
		
		<tr>
			<th>Cost Per Km</th>
			<td>${invoice.costPerKm} Km</td>
			<th>Travel Cost</th>
			<td>Rs.${invoice.distance * invoice.costPerKm}</td>
		</tr>	
	</table>
	
	<div class ="total-section">
		<h3>Fee Details</h3>
		<table class ="details-table">
			<tr>
				<td>Base Fee + Travel Cost</td>
				<td>Rs.${invoice.basePrice + (invoice.distance * invoice.costPerKm)}</td>
			</tr>
			
			<tr>
				<td>Tax</td>
				<td>Rs.${invoice.tax}</td>
			</tr>
			
			<tr>
				<td>Driver Cost</td>
				<td>Rs.${invoice.driverCost}</td>
			</tr>
			
			<tr style ="font-weight: bold; background-color: #e9ecef">
			
				<td>Total Amount</td>
				<td>Rs.${invoice.totalAmount}</td>
			</tr>
		</table>
		
		<div style ="text-align: center; margin-top: 30px;">
			<form action ="PaymentControllerServlet" method ="get">
				<input type ="hidden" name= "action" value ="invoice">
				<input type ="hidden" name ="invoiceId" value ="${invoice.invoiceId}">
				<button type ="submit" class ="pay-btn">Payment</button>
			</form>
		
		</div>
		
	</div>
</div>
</body>
</html>