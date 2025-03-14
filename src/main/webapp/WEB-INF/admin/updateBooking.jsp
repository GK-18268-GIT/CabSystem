<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Mega City Cab System - Update Booking</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
<style type="text/css">
    body {
	    background-color: #f0fdf4; 
	    color: #166534; 
	    font-family: 'Montserrat', sans-serif;
	    margin: 0;
	    padding: 0;
}

	.update-container {
	    width: 50%;
	    margin: auto;
	    background-color: #ffffff; 
	    padding: 20px;
	    border-radius: 10px;
	    box-shadow: 0 4px 10px rgba(0, 128, 0, 0.2); 
	}
	
	h2 {
	    text-align: center;
	    color: #166534; 
	}
	
	form {
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	}
	
	label {
	    margin-top: 10px;
	    font-weight: 600;
	    width: 80%;
	    text-align: left;
	    color: #166534; 
	}
	
	input {
	    width: 80%;
	    padding: 10px;
	    margin: 5px 0;
	    background-color: #f5fff5; 
	    border: 2px solid #a7f3d0; 
	    border-radius: 5px;
	    color: #065f46; 
	    text-align: center;
	    font-size: 16px;
	    transition: all 0.3s ease-in-out;
	    outline: none;
	}
	
	
	input:focus {
	    border-color: #10b981; 
	    box-shadow: 0 0 10px #10b981;
	    background-color: #ffffff;
	    transform: scale(1.02);
	}
	
	button {
	    margin-top: 15px;
	    padding: 12px;
	    background-color: #10b981; 
	    color: #ffffff;
	    border: none;
	    border-radius: 5px;
	    cursor: pointer;
	    font-weight: 600;
	    width: 80%;
	    font-size: 16px;
	    transition: background-color 0.3s ease-in-out, transform 0.2s ease-in-out;
	}
	
	button:hover {
	    background-color: #065f46; 
	    transform: scale(1.05);
	}
        
</style>
</head>
<body>

    <div class="update-container">
    <h2>Update Booking Data</h2>
    <form action="BookingServlet" method="POST">
    <input type="hidden" name="action" value="update">
      
      <input type="hidden" name="bookingNumber" value="${booking.bookingNumber}" />
      
      <label for="customername">Customer Name:</label>
      <input type="text" name="customername" id=customername value="${booking.customerName}" placeholder="Enter Customer Name" required>
      
      <label for="customeraddress">Customer Address:</label>
      <input type="text" name="customeraddress" id="customeraddress" value="${booking.customerAddress}" placeholder="Enter Customer Address" required>
      
      <label for="customerphone">Customer Phone:</label>
      <input type="text" name="customerphone" id="customerphone" value="${booking.customerPhone}" placeholder="Enter Customer Phone" required>
      
      <label for="pickuplocation">Pickup Location:</label>
      <input type="text" name="pickuplocation" id="pickuplocation" value="${booking.pickupLocation}" placeholder="Enter Pickup Location" required>
      
      <label for="pickupdatetime">Pickup Date and Time:</label>
      <input type="datetime-local" name="pickupdatetime" id="pickupdatetime" value="${booking.pickupDateTime}" placeholder="Enter Pickup Date and Time" required>
      
      <label for="destination">Destination:</label>
      <input type="text" name="destination" id="destination" value="${booking.destination}" placeholder="Enter Destination" required>
      
      <label for="passengers">Passengers:</label>
      <input type="number" name="passengers" id="passengers" value="${booking.passengers}" placeholder="Enter Number of Passengers" required>
      
      <button type="submit" onclick ="updateBookingAlert()">Update Booking</button>
    </form>
  </div>

    <script>
  	function aupdateBookingAlert() {
  		alert("Booking updated successfully");
  	}
  </script>

</body>
</html>
