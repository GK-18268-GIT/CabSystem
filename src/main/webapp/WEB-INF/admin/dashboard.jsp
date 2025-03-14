<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System- Admin Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

<style>
	body {
        background-color: #f0f8f0; 
        color: #2c5f2d; 
        font-family: 'Montserrat', sans-serif;
        text-align: center;
        margin: 0;
        padding: 0;
    }

    .dashboard-container {
        width: 80%;
        margin: auto;
        padding-top: 50px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 100, 0, 0.2);
        padding: 30px;
    }

    h2 {
        color: #1e4620; 
        margin-bottom: 20px;
    }

    .button {
        display: inline-block;
        padding: 12px 25px;
        background-color: #3aaf50; 
        color: white;
        text-decoration: none;
        margin: 10px;
        border-radius: 8px;
        font-weight: 600;
        transition: background 0.3s, transform 0.2s;
    }

    .button:hover {
        background-color: #2c8f40; 
        transform: translateY(-2px);
    }

    @media (max-width: 600px) {
        .dashboard-container {
            width: 95%;
        }
        .button {
            display: block;
            width: 80%;
            margin: 10px auto;
        }
    }
</style>

</head>
<body>
	<div class ="dashboard-container">
		<h2>Admin Dashboard</h2>
		<p>Welcome <%= session.getAttribute("user") %>!</p>
		<div>
			<a href = "CabControllerServlet?action=add" class ="button">Add a Cab</a>
			<a href = "CabControllerServlet?action=list" class ="button">List Cabs</a>
			<a href = "BookingServlet?action=list" class ="button">List Booking</a>
			<a href = "BookingServlet?action=showSelectedCab" class ="button">List SelectedCabs</a>
			<a href = "DriverServlet?action=add" class ="button">Add New Driver</a>
			<a href = "DriverServlet?action=list" class ="button">List Drivers</a>
		</div>
	</div>

</body>
</html>