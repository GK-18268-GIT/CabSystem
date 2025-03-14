<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System- Start</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
           body {
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    height: 100vh;
		    background-color: #e8f5e9; 
		    color: #1b5e20; 
		    font-family: 'Montserrat', sans-serif;
		}

		.container {
		    text-align: center;
		    background: #ffffff; 
		    padding: 40px;
		    border-radius: 10px;
		    box-shadow: 0 4px 10px rgba(27, 94, 32, 0.2); 
		}
		
		.header {
		    font-size: 2rem;
		    font-weight: bold;
		    margin-bottom: 20px;
		    color: #2e7d32; 
		}
		
		.subtitle {
		    font-size: 1.2rem;
		    margin-bottom: 30px;
		    color: #388e3c;
		}
		
		.button {
		    background-color: #388e3c; 
		    color: white;
		    font-size: 1rem;
		    font-weight: bold;
		    padding: 12px 24px;
		    border: none;
		    border-radius: 5px;
		    cursor: pointer;
		    text-decoration: none;
		    transition: background 0.3s ease, box-shadow 0.3s ease;
		    box-shadow: 0 4px 10px rgba(56, 142, 60, 0.3); 
		}
		
		.button:hover {
		    background-color: #2e7d32; 
		    box-shadow: 0 4px 15px rgba(46, 125, 50, 0.5); 
		}
    </style>
</head>
<body>
    <div class="container">
        <h1 class="header">Welcome to Mega City Cab System</h1>
        <p class="message">Your trusted ride, anytime, anywhere.</p>
        <a href="LoginServlet" class="button">Get Started</a>
        <a href="guidelines.jsp" class="button">Help</a>
    </div>
</body>
</html>
