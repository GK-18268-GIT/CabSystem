<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mega City Cab System - Add New Cab</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    body {
	  background-color: #f0fdf4; 
	  color: #166534; 
	  font-family: 'Montserrat', sans-serif;
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  height: 100vh;
	  margin: 0;
	}

	.add-container {
	  width: 50%;
	  background-color: #ecf0f1; 
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
	
	input, select {
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
	
	
	input:focus, select:focus {
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
  <div class="add-container">
    <h2>Add New Cab</h2>
    <form id="cabAdd" action="CabControllerServlet" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="action" value="add">
      
      <label for="cabNumber">Cab Number:</label>
      <input type="text" name="cabNumber" id="cabNumber" placeholder="Enter License Plate Number" required>
      
      <label for="model">Cab Model:</label>
      <input type="text" name="model" id="model" placeholder="Enter Cab Model" required>
      
      <label for="brand">Cab Brand:</label>
      <input type="text" name="brand" id="brand" placeholder="Enter Cab Brand" required>
      
      <label for="capacity">Capacity:</label>
      <input type="number" name="capacity" id="capacity" placeholder="Enter Number of Seats" required>
      
      <label for="basePrice">Base Price:</label>
      <input type="text" name="basePrice" id="basePrice" placeholder="Enter Base Price for Cab" required>
      
      <label for="costPerKm">Cost Per Km:</label>
      <input type="text" name="costPerKm" id="costPerKm" placeholder="Enter Cost Per km" required>
      
      <label for="status">Availability:</label>
      <select id="status" name="status">
        <option value="available">Available</option>
        <option value="unavailable">Unavailable</option>
      </select>
      
      <label for="cabImage">Cab Image:</label>
      <input type="file" name="cabImage" id="cabImage" accept="image/*" required>
      
      <button type="submit" onclick ="addCabAlert()">Add Cab</button>
    </form>
  </div>
  <script>
  	function addCabAlert() {
  		alert("Cab added successfully");
  	}
  </script>
  
</body>
</html>
