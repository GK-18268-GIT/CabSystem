<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System - Confirm Payment</title>
    
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
    
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100..900&family=Poppins:wght@300;700&display=swap" rel="stylesheet">
	
	<style>
		body {
			font-family: 'Montserrat', sans-serif;
			background-color: #e8f5e9; 
			margin: 0;
			padding: 20px;
			color: #2c5e3f;
		}
		
		.confirm-container {
			max-width: 800px;
			margin: 50px auto;
			padding: 30px;
			background: white;
			border-radius: 10px;
			box-shadow: 0 0 20px rgba(0, 128, 0, 0.1);
			border: 3px solid #4CAF50; 
		}
		
		.cab-info {
			display: flex;
			gap: 20px;
			margin-bottom: 30px;
			padding: 15px;
			background-color: #dcedc8;
			border-radius: 8px;
		}
		
		.cab-image {
			width: 200px;
			height: 150px;
			border-radius: 8px;
			object-fit: cover;
			border: 2px solid #388e3c; 
		}
		
		.details-table {
			width: 100%;
			border-collapse: collapse;
			margin: 20px 0;
		}
		
		.details-table th,
		.details-table td {
			padding: 12px;
			border: 1px solid #81c784; 
			text-align: left;
		}
		
		.details-table th {
			background-color: #a5d6a7;
			color: #1b5e20;
			width: 25%;
		}
		
		.total-section {
			margin-top: 30px;
			padding: 20px;
			background-color: #c8e6c9; 
			border-radius: 8px;
		}
		
		.total-section h3 {
			margin-top: 0;
			color: #1b5e20;
		}
		
		.print-section {
			margin-top: 40px;
			display: flex;
			gap: 20px;
			flex-wrap: wrap;
		}
		
		.print-btn, .home-btn {
			padding: 12px 30px;
			border-radius: 5px;
			font-weight: 500;
			text-decoration: none;
			transition: all 0.3s ease;
			cursor: pointer;
			border: none;
		}
		
		.print-btn {
			background-color: #4CAF50; 
			color: white;
		}
		
		.print-btn:hover {
			background-color: #388e3c; 
			transform: translateY(-1px);
		}
		
		.home-btn {
			background-color: #81c784; 
			color: white;
			display: inline-block;
			text-align: center;
			line-height: 1.5;
		}
		
		.home-btn:hover {
			background-color: #66bb6a; 
			transform: translateY(-1px);
		}
		
		@media (max-width: 480px) {
			.print-section {
				flex-direction: column;
			}
			
			.print-btn, .home-btn {
				width: 100%;
			}
		}
		
		@media print {
			body {
				zoom: 0.85;
				-webkit-print-color-adjust: exact;
			}
			
			.print-btn, .home-btn {
				display: none;
			}
			
			.confirmation-container {
				box-shadow: none;
				border: 2px solid #388e3c; 
			}
		}		
	</style>

</head>
<body>
	<div class ="confirm-container">
		<div class ="cab-info">
			<img class ="cab-image" src ="uploads/${selectedCab.cabImage}" alt ="Cab Image">
			<div>
				<h3>Cab Details</h3>
				<p>Model: ${selectedCab.model}</p>
				<p>Brand: ${selectedCab.brand}</p>
				<p>License Plate: ${selectedCab.cabNumber}</p>
			</div>
		</div>
		
		<table class ="details-table">
			<tr>
				<th>Invoice ID:</th>
				<td>${invoice.invoiceId}</td>
				
				<th>Booking ID:</th>
				<td>${invoice.bookingId}</td>
			</tr>
			
			<tr>
				<th>Base Price:</th>
				<td>Rs.${invoice.basePrice}</td>
				
				<th>Distance:</th>
				<td>${invoice.distance} km</td>
			</tr>
			
			<tr>
				<th>Cost Per Km:</th>
				<td>${invoice.costPerKm} Km</td>
				
				<th>Travel Cost:</th>
				<td>Rs.${invoice.distance * invoice.costPerKm}</td>
			</tr>
		</table>
		
		<div class = "total-section">
			<h3>Fee Details</h3>
			<table class ="details-table">
				<tr>
					<td>Base Price + Travel Cost</td>
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
		</div>
		
		<div class ="payment-section">
			<table class ="details-table">
				<tr>
					<td>Payment ID:</td>
					<td>${payment.paymentId}</td>
				</tr>
				
				<tr>
					<td>Payment Method:</td>
					<td>${payment.paymentMethod}</td>
				</tr>
				
				<tr>
					<td>Payment Date:</td>
					<td>${payment.createdAt}</td>
				</tr>
				
				<c:if test ="${payment.paymentMethod == 'CARD'}">
					<tr>
						<td>Card Number:</td>
						<td>**** **** **** ${fn:substring(payment.cardNumber, 12, 16)}</td>	
				</tr>
				</c:if>
			</table>
		</div>
		
		<div class ="print-section">
			<button class ="print-btn" onclick ="window.print()">Print The Receipt</button>
			<a href ="${pageContext.request.contextPath}/index.jsp" class ="home-btn">Return To Home</a>
		</div>
		
	</div>
</body>
</html>