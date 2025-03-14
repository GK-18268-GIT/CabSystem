<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mega City Cab System - Guidelines</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<style>
	body {
    font-family: 'Montserrat', sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f4fff4; 
    color: #155724; 
}

	.container {
	    max-width: 800px;
	    margin: 40px auto;
	    padding: 25px;
	    background: #ffffff; 
	    border-radius: 10px;
	    box-shadow: 2px 2px 15px rgba(21, 87, 36, 0.2); 
	    border-left: 5px solid #28a745; 
	}
	
	h2 {
	    color: #218838; 
	    text-align: center;
	}
	
	h3 {
	    color: #1e7e34; 
	}
	
	ul {
	    margin-left: 20px;
	}
	
	ul li {
	    line-height: 1.8;
	}
	
	.back-link {
	    display: inline-block;
	    margin-top: 20px;
	    padding: 10px 15px;
	    text-decoration: none;
	    color: white;
	    background-color: #28a745; 
	    border-radius: 5px;
	    font-weight: bold;
	    transition: 0.3s;
	}
	
	.back-link:hover {
	    background-color: #218838; 
	}

</style>
</head>

<body>
	<div class="container">
    <h2>System Usage Guidelines</h2>
    <p>Welcome to the Mega city cab System! Please follow these guidelines to use the system efficiently.</p>
    
    <h3>1. Registration and Login</h3>
    <ul>
        <li>New users must register using a username, email, address, nic, password.</li>
        <li>Afer register you will directly go to the booking page</li>
    </ul>

    <h3>2. Booking a Cab</h3>
    <ul>
        <li>Go to the <b>New Booking</b> page to create a booking.</li>
        <li>Enter your name, address, telephone number, pickup location, pickup date and time, destination, and number of passegers</li>
        <li>Then click the "Next" button and choose a suitable cab from the available options.</li>
    </ul>

    <h3>3. Confirming Booking Details</h3>
    <ul>
        <li>Review your booking details before proceeding.</li>
        <li>You can edit passenger details and change the selected cab if necessary.</li>
    </ul>

    <h3>4. Billing and Payment</h3>
    <ul>
    	<li>After confirm your booking details, you will redirect to the distance calculate bage. Click "calculate distance" button and after click "Proceed to Billing" </li>
    	<li>The you will see invoice about you booking </li>
        <li>The fare is calculated based on distance, base price, and additional costs.</li>
        <li>Review the total fare before the payment.</li>
        <li>Then click payment button and select the payment option.</li>
        <li>After payment you can see your bill and you can print it. Also you can make another booking by clicking "Return to Home" button</li>
        <li>If you want to make another booking click "Get Started" button and enter your username and password. Then you can make a new booking.</li>
    </ul>

    <h3>5. Admin Guidelines</h3>
    <ul>
        <li>Admins can add, update, and remove cabs.</li>
        <li>Drivers can be assigned to available cabs.</li>
    </ul>

    <a href="index.jsp" class="back-link">Back to Home</a>
</div>

</body>
</html>













