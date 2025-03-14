<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System- Payment</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
	<style>
			body {
			font-family: 'Poppins', sans-serif;
			background-color: #e8f5e9;
			margin: 0;
			padding: 20px;
			color: #2e7d32;
		}
		
		.payment-container {
			max-width: 600px;
			margin: 50px auto;
			padding: 30px;
			background: white;
			border-radius: 10px;
			box-shadow: 0 0 20px rgba(46, 125, 50, 0.2);
			border: none;
		}
		
		.payment-section {
			margin: 30px 0;
			padding: 20px;
			border: 2px solid #66bb6a;
			border-radius: 8px;
			background: #f1f8e9;
		}
		
		.payment-method {
			margin: 15px 0;
			display: flex;
			gap: 30px;
		}
		
		.payment-method label {
			display: flex;
			align-items: center;
			gap: 8px;
			font-weight: 600;
		}
		
		.form-group {
			margin-bottom: 15px;
		}
		
		.form-group input {
			width: 100%;
			padding: 8px;
			border: 2px solid #81c784;
			border-radius: 4px;
			margin-top: 5px;
			background-color: #e8f5e9;
			color: #2e7d32;
		}
		
		.pay-btn {
			background-color: #388e3c;
			color: white;
			padding: 12px 24px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			margin-top: 15px;
			font-weight: bold;
		}
		
		.pay-btn:hover {
			background-color: #2e7d32;
			box-shadow: 0 0 10px rgba(46, 125, 50, 0.5);
		}
		
		.print-btn {
			background-color: #1b5e20;
			color: white;
			padding: 12px 24px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			margin-top: 20px;
			float: right;
			font-weight: bold;
		}
		
		.print-btn:hover {
			background-color: #2e7d32;
			box-shadow: 0 0 10px rgba(27, 94, 32, 0.5);
		}
		
		
	</style>
	
</head>
<body>
	
	<div class ="payment-container">
		<h2>Payment Gateway</h2>
		<form action = "PaymentControllerServlet" method ="post">
			<input type ="hidden" name ="action" value ="paymentProcess">
			<input type ="hidden" name ="invoiceId" value ="${invoice.invoiceId}">
			<input type ="hidden" name ="totalAmount" value ="${invoice.totalAmount}">
	
		<div class ="payment-section">
			<h3>Payment Method</h3>
				<input type ="hidden" name ="action" value ="payment">
				
				<input type ="hidden" name ="invoiceId" value ="${invoice.invoiceId}">
				<input type ="hidden" name ="totalAmount" value ="${invoice.totalAmount}">
				
				<div class ="payment-method">
					<label>Cash payment</label>
					<input type ="radio" name ="paymentMethod" value ="CASH" required>
					
					<label>Credit/Debit Card</label>
					<input type ="radio" name ="paymentMethod" value ="CARD" required>	
				</div>
				
				<div id ="cardDetails" style ="display:none;">
					<div class ="form-group">
						<label>Card Number: </label>
						<input type ="text" name ="cardNumber" id ="cardNumber" maxlength="19" pattern ="\d{4}-\d{4}-\d{4}-\d{4}" placeholder = "xxxx-xxxx-xxxx-xxxx" title ="16-digit card number">
					</div>
					
					<div class ="form-group">
						<label>Card Holder Name:</label>
						<input type ="text" name ="cardHolder" placeholder ="Enter Your Name" required>	
					</div>
					
					<div class ="form-group">
						<label>Expiry (MM/YY): </label>
						<input type ="text" name ="expiry" id ="expiry" maxlength="5" pattern="(0[1-9]|1[0-2])\/\d{2}" placeholder="MM/YY" title="MM/YY format">
					</div>
					
					<div class ="form-group">
						<label>CVV: </label>
						<input type ="text" name ="cvv" maxlength="3" pattern ="\d{3}" placeholder ="123" title ="3-digit CVV">
					</div>			
				</div>
				
				<button type ="submit" class ="pay-btn" onclick ="paymentAlert()">Payment</button>
		</div>
		</form>
	</div>

<script type="text/javascript">
		document.addEventListener("DOMContentLoaded", function() {
			document.querySelectorAll('input[name="paymentMethod"]').forEach(radio => {
				radio.addEventListener('change', function() {
					const cardDetails = document.getElementById('cardDetails');
					if(this.value === 'CARD') {
						cardDetails.style.display = 'block';
						cardDetails.querySelectorAll('input').forEach(input => input.required = true);
					} else {
						cardDetails.style.display = 'none';
						cardDetails.querySelectorAll('input').forEach(input => input.required = false);
					}
					
					});
				});
			
			document.getElementById('cardNumber').addEventListener('input', function(e) {
				let value = e.target.value.replace(/\D/g, '');
				value = value.substring(0, 16);
				
				let formattedValue = value.match(/.{1,4}/g)?.join('-') || value;
				e.target.value = formattedValue;
			});
			
			document.getElementById('expiry').addEventListener('input', function(e) {
				let value = e.target.value.replace(/\D/g, '');
				value = value.substring(0, 4);
				if(value.length >= 2) {
					value = value.replace(/(\d{2})(\d{0,2})/, '$1/$2');
				}
				e.target.value = value;
			});
			
			document.querySelector('form').addEventListener('submit', function(e) {
				const cardInput = document.getElementById('cardNumber');
				cardInput.value = cardInput.value.replace(/-/g, '');
				
				const expiryInput = document.getElementById('expiry');
				expiryInput.value = expiryInput.value.replace(/\D/g, '');
			});
			
			});
		


</script>

</body>
</html>