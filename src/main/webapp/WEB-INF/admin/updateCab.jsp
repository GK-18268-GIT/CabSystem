<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mega City Cab System - Update Cab</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
	    body {
		  background-color: #e8f5e9; 
		  color: #1b5e20; 
		  font-family: 'Montserrat', sans-serif;
		  display: flex;
		  justify-content: center;
		  align-items: center;
		  height: 100vh;
		  margin: 0;
	}
	
	.update-container {
	  width: 50%;
	  background-color: #ffffff; 
	  padding: 20px;
	  border-radius: 10px;
	  box-shadow: 0 0 10px rgba(27, 94, 32, 0.2); 
	}
	
	h2 {
	  text-align: center;
	  color: #2e7d32; 
	}
	
	form {
	  display: flex;
	  flex-direction: column;
	}
	
	label {
	  margin-top: 10px;
	  font-weight: 600;
	  color: #1b5e20;
	}
	
	input, select {
	  width: 100%;
	  padding: 8px;
	  margin: 5px 0;
	  background-color: #e0f2f1; 
	  border: 1px solid #81c784; 
	  border-radius: 5px;
	  color: #1b5e20; 
	  transition: border-color 0.3s, box-shadow 0.3s;
	}
	
	input:focus, select:focus {
	  outline: none;
	  border-color: #2e7d32; 
	  box-shadow: 0 0 5px rgba(46, 125, 50, 0.5); 
	}
	
	button {
	  margin-top: 15px;
	  padding: 10px;
	  background-color: #388e3c; 
	  color: white;
	  border: none;
	  border-radius: 5px;
	  cursor: pointer;
	  font-weight: 600;
	}
	
	button:hover {
	  background-color: #2e7d32;
	}

  </style>
</head>
<body>
  <div class="update-container">
    <h2>Update Cab</h2>
    <form id="cabUpdate" action="CabControllerServlet" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="action" value="update">
      
      <input type="hidden" name="cabId" value="${cab.cabId}" />
      
      <label for="cabNumber">Cab Number:</label>
      <input type="text" name="cabNumber" id="cabNumber" value="${cab.cabNumber}" placeholder="Enter License Plate Number" required>
      
      <label for="model">Cab Model:</label>
      <input type="text" name="model" id="model" value="${cab.model}" placeholder="Enter Cab Model" required>
      
      <label for="brand">Cab Brand:</label>
      <input type="text" name="brand" id="brand" value="${cab.brand}" placeholder="Enter Cab Brand" required>
      
      <label for="capacity">Capacity:</label>
      <input type="number" name="capacity" id="capacity" value="${cab.capacity}" placeholder="Enter Number of Seats" required>
      
      <label for="basePrice">Base Price:</label>
      <input type="text" name="basePrice" id="basePrice" value="${cab.basePrice}" placeholder="Enter Base Price for Cab" required>
      
      <label for="costPerKm">Cost Per Km:</label>
      <input type="text" name="costPerKm" id="costPerKm" value="${cab.costPerKm}" placeholder="Enter Base Price for Cab" required>
      
      <label for="status">Availability:</label>
      <select id="status" name="status">
        <option value="available" <c:if test="${cab.status == 'available'}">selected</c:if>>Available</option>
        <option value="unavailable" <c:if test="${cab.status == 'unavailable'}">selected</c:if>>Unavailable</option>
      </select>
      
      <label for="cabImage">Cab Image:</label>
  
      <input type="file" name="cabImage" id="cabImage" accept="image/*">
      
      
      <button type="submit" onclick ="updateCabAlert()">Update Cab</button>
    </form>
  </div>
  
  <script>
  	function updateCabAlert() {
  		alert("Cab updated successfully");
  	}
  </script>
  
</body>
</html>
