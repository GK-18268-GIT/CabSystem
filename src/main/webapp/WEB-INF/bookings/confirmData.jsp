<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.system.model.Booking, com.system.model.Cab" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System - Confirm Booking Data</title>
    
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
    
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100..900&family=Poppins:wght@300;700&display=swap" rel="stylesheet">

    <style>
          body { 
          	font-family: 'Montserrat', sans-serif; 
          	background-color: #f8fff9; 
          }
          
        .container { 
         width: 60%; 
       	 margin: auto; 
         padding: 20px; 
         border: 1px solid #c3e6cb; 
         border-radius: 5px; 
         background-color: white; 
        }
        
        table { 
         width: 100%; 
         border-collapse: collapse; 
         margin-top: 10px; 
        }
        
        table, th, td { 
         border: 1px solid #28a745; 
         padding: 10px; 
         text-align: left; 
        }
        
        th { 
         background-color: #28a745; 
         color: white; 
        }
        
        tr:nth-child(even) { 
         background-color: #e8f5e9; 
        }
        
        .actions { 
         margin-top: 20px; 
        }
        
        .btn { 
         padding: 10px 15px; 
         text-decoration: none; 
         color: white; 
         background-color: #28a745; 
         border: none; 
         border-radius: 5px; 
         cursor: pointer; 
        }
        
        .btn:hover { 
         background-color: #218838; 
        }
        
        .edit { 
         background-color: #4CAF50; 
        }
        
        .edit:hover { 
         background-color: #45a049; 
        }
        
        h2, h3 { 
         color: #155724; 
        }
        
        input[type="text"], input[type="datetime-local"], input[type="number"] { 
            padding: 8px;
            border: 1px solid #c3e6cb;
            border-radius: 4px;
            width: 95%;
        }
        input[type="text"]:focus, input[type="datetime-local"]:focus, input[type="number"]:focus {
            border-color: #28a745;
            outline: none;
            box-shadow: 0 0 3px #28a745;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Confirm Your Booking Details</h2>

    <form action="BookingServlet" method="post">
    <input type ="hidden" name ="action" value ="updateBooking">
        <table>
            <tr><th colspan="2">Booking Details</th></tr>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="customerName" value="${booking.customerName}" required></td>
            </tr>
            <tr>
                <td>Address:</td>
                <td><input type="text" name="customerAddress" value="${booking.customerAddress}" required></td>
            </tr>
            <tr>
                <td>Telephone:</td>
                <td><input type="text" name="customerPhone" value="${booking.customerPhone}" required></td>
            </tr>
            <tr>
                <td>Pickup Location:</td>
                <td><input type="text" name="pickupLocation" value="${booking.pickupLocation}" required></td>
            </tr>
            <tr>
                <td>Pickup Date and Time:</td>
                <td><input type="datetime-local" name="pickupDateTime" value="${booking.pickupDateTime}" required></td>
            </tr>
            <tr>
                <td>Destination:</td>
                <td><input type="text" name="destination" value="${booking.destination}" required></td>
            </tr>
            <tr>
                <td>Number of Passengers:</td>
                <td><input type="number" name="passengers" value="${booking.passengers}" min="1" required></td>
            </tr>
        </table>
        <div class="actions">
            <button type="submit" class="btn edit" onclick ="updateAlert()">Update Details</button>
        </div>
    </form>

    <h3>Selected Cab Details</h3>
    <table>
        <tr><th colspan="2">Cab Information</th></tr>
        <tr>
            <td>Cab Code:</td>
            <td>${selectedCab.cabCode}</td>
        </tr>
        
        <tr>
            <td>Cab Number:</td>
            <td>${selectedCab.cabNumber}</td>
        </tr>
        
        <tr>
            <td>Model:</td>
            <td>${selectedCab.model}</td>
        </tr>
        
        <tr>
            <td>Brand:</td>
            <td>${selectedCab.brand}</td>
        </tr>
        
        <tr>
            <td>Capacity:</td>
            <td>${selectedCab.capacity}</td>
        </tr>
        
        <tr>
            <td>Base Price:</td>
            <td>${selectedCab.basePrice}</td>
        </tr>
        
        <tr>
            <td>Cab Image:</td>
            <td><img src="uploads/${selectedCab.cabImage}" alt="Cab Image" width="200px"></td>
        </tr>
    </table>

    <div class="actions">
        <a href="CabControllerServlet?action=cabList" class="btn edit" onclick ="changeCabAlert()">Change Cab</a>
        <a href="BookingServlet?action=location" class="btn">Proceed to Bill Calculation</a>
    </div>
</div>

<script>
	function updateAlert(event) {
		alert("Details Update Successfully");
	}
	
	function changeCabAlert(event) {
		var changeCab = confirm("Are you sure you want to change your selected cab?")
	}
</script>

</body>
</html>
