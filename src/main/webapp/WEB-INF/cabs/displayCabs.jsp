<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System - Display All Cabs</title>
    
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
    
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100..900&family=Poppins:wght@300;700&display=swap" rel="stylesheet">

<style>
    body {
	    font-family: 'Montserrat', sans-serif;
	    background-color: #e8f5e9; 
	    margin: 0;
	    padding: 20px;
	}

	.header {
	    text-align: center;
	    margin-bottom: 40px;
	    color: #1b5e20; 
	}
	
	.cab-container {
	    max-width: 1200px;
	    margin: 0 auto;
	    display: grid;
	    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
	    gap: 25px;
	    padding: 20px;
	}
	
	.cab-card {
	    background: white;
	    border-radius: 12px;
	    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
	    padding: 20px;
	    position: relative;
	    transition: transform 0.2s;
	    border: 2px solid transparent;
	}
	
	.cab-card.available {
	    cursor: pointer;
	}
	
	.cab-card.unavailable {
	    opacity: 0.7;
	    background-color: #f0f4c3; 
	    filter: grayscale(50%);
	}
	
	.cab-card.available:hover {
	    transform: translateY(-5px);
	    box-shadow: 0 6px 12px rgba(0,0,0,0.15);
	}
	
	.availability-tag {
	    position: absolute;
	    top: 15px;
	    right: 15px;
	    padding: 5px 15px;
	    border-radius: 20px;
	    font-size: 12px;
	    font-weight: 600;
	    text-transform: uppercase;
	}
	
	.available .availability-tag {
	    background-color: #2e7d32; 
	    color: white;
	}
	
	.unavailable .availability-tag {
	    background-color: #d32f2f;
	    color: white;
	}
	
	.cab-image {
	    width: 100%;
	    height: 180px;
	    object-fit: cover;
	    border-radius: 8px;
	    margin-bottom: 15px;
	}
	
	.cab-info {
	    margin-bottom: 15px;
	}
	
	.cab-info-item {
	    margin: 8px 0;
	    color: #1b5e20; 
	    font-size: 14px;
	}
	
	.cab-info-label {
	    font-weight: 600;
	    color: #2e7d32; 
	}
	
	.radio-container {
	    text-align: center;
	    margin-top: 10px;
	}
	
	input[type="radio"] {
	    transform: scale(1.3);
	    accent-color: #388e3c; 
	}
	
	input[type="radio"]:disabled {
	    accent-color: #a5d6a7;
	    cursor: not-allowed;
	}
	
	.submit-btn {
	    padding: 12px 30px;
	    background-color: #388e3c; 
	    color: white;
	    border: none;
	    border-radius: 25px;
	    cursor: pointer;
	    font-size: 16px;
	    font-weight: 600;
	    display: block;
	    margin: 30px auto;
	    transition: background-color 0.3s;
	}
	
	.submit-btn:hover {
	    background-color: #2e7d32; 
	}
	
	.no-cabs {
	    text-align: center;
	    padding: 40px;
	    color: #7f8c8d;
	    font-size: 18px;
	    grid-column: 1 / -1;
	}
	
	.error-message {
	    color: #d32f2f;
	    text-align: center;
	    margin: 20px 0;
	}
</style>
</head>
<body>

<h1 class="header">Display All Cabs</h1>

<form action="CabControllerServlet" method="POST">
<input type="hidden" name="action" value="select">

    <div class="cab-container">
        <c:choose>
            <c:when test="${not empty cabList}">
                <c:forEach var="cab" items="${cabList}">
                    <div class="cab-card">
                        <div class="availability-tag">Available</div>
                        <img src="${pageContext.request.contextPath}/uploads/${cab.cabImage}" alt="${cab.model}" class="cab-image">
                        <div class="cab-info">
                            <div class="cab-info-item"><span class="cab-info-label">Cab Code:</span> ${cab.cabCode}</div>                           
                            <div class="cab-info-item"><span class="cab-info-label">License Plate:</span> ${cab.cabNumber}</div>
                            <div class="cab-info-item"><span class="cab-info-label">Model:</span> ${cab.model}</div>
                            <div class="cab-info-item"><span class="cab-info-label">Brand:</span> ${cab.brand}</div>
                            <div class="cab-info-item"><span class="cab-info-label">Capacity:</span> ${cab.capacity} persons</div>
                            <div class="cab-info-item"><span class="cab-info-label">Price:</span> Rs.${cab.basePrice}/km</div>
                            <div class="cab-info-item"><span class="cab-info-label">Cost Per Km:</span> Rs.${cab.costPerKm}/km</div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-cabs">No cabs to display</div>
            </c:otherwise>
        </c:choose>
    </div>

</form>

</body>
</html>