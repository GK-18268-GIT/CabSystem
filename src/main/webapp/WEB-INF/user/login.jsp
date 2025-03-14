<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System- Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
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

		.login-container {
		    background-color: #ffffff; 
		    padding: 30px;
		    border-radius: 12px;
		    box-shadow: 0 0px 15px rgba(0, 100, 0, 0.2);
		    text-align: center;
		    width: 350px;
		    position: relative;
		}
		
		h2 {
		    margin-bottom: 20px;
		    font-weight: 600;
		    color: #1b5e20; 
		    font-size: 28px;
		    text-shadow: 0 2px 4px rgba(0, 100, 0, 0.2);
		}
		
		.login-details {
		    margin-bottom: 15px;
		    text-align: left;
		}
		
		label {
		    display: block;
		    margin-bottom: 5px;
		    font-size: 14px;
		    font-weight: 500;
		    color: #2e7d32; 
		}
		
		input {
		    width: 100%;
		    padding: 10px;
		    border: 1px solid #66bb6a;
		    background-color: #f1f8e9; 
		    color: #1b5e20;
		    border-radius: 5px;
		    font-size: 14px;
		    transition: 0.3s;
		    box-sizing: border-box;
		}
		
		input:focus {
		    outline: none;
		    border-color: #2e7d32;
		    box-shadow: 0 0 10px rgba(46, 125, 50, 0.5);
		}
		
		.btn {
		    width: 100%;
		    padding: 10px;
		    font-size: 16px;
		    font-weight: 600;
		    background-color: #2e7d32; 
		    color: white;
		    border: none;
		    border-radius: 5px;
		    cursor: pointer;
		    transition: 0.3s;
		    margin-top: 20px;
		    text-transform: uppercase;
		    letter-spacing: 1px;
		}
		
		.btn:hover {
		    background-color: #1b5e20; 
		    box-shadow: 0 4px 15px rgba(46, 125, 50, 0.4);
		}
		
		.register-link {
		    margin-top: 15px;
		    font-size: 14px;
		    color: #388e3c; 
		}
		
		.register-link a {
		    color: #1b5e20;
		    text-decoration: none;
		    font-weight: 600;
		    transition: 0.3s;
		}
		
		.register-link a:hover {
		    color: #388e3c;
		    text-decoration: none;
		}
  	</style>
  	
</head>

<body>
	<div class ="login-container">
		<h2>Login To Continuing</h2>
		<form action ="LoginServlet" method ="POST">
			<div class ="login-details">
				<label for ="username">Username</label>
				<input type ="text" id ="username" name ="username" placeholder ="Enter Your Username" required>
			</div>
			<div class ="login-details">
				<label for ="password">Password</label>
				<input type ="password" id ="password" name ="password" placeholder ="Enter Your Password" required>
			</div>
			<button type ="submit" class ="btn">Sign In</button>
		</form>
		<p class="register-link">Don't have an account? <a href="RegisterServlet">Register here</a></p>
	</div>

</body>

</html>