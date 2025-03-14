<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System- Register</title>
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
		    min-height: 100vh;
		    margin: 0;
		}

		.login-container {
		    background-color: #ffffff; 
		    padding: 40px;
		    border-radius: 12px;
		    box-shadow: 0 0px 20px rgba(27, 94, 32, 0.2); 
		    text-align: center;
		    width: 400px;
		}
		
		h2 {
		    margin-bottom: 20px;
		    font-weight: 600;
		    color: #2e7d32; 
		    font-size: 28px;
		    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
		}
		
		.register-details {
		    margin-bottom: 15px;
		    text-align: left;
		}
		
		label {
		    display: block;
		    margin-bottom: 5px;
		    font-size: 14px;
		    font-weight: 500;
		    color: #388e3c; 
		}
		
		input {
		    width: 100%;
		    padding: 10px;
		    border: 1px solid #a5d6a7; 
		    background-color: #ffffff;
		    color: #1b5e20;
		    border-radius: 5px;
		    font-size: 14px;
		    transition: 0.3s;
		    box-sizing: border-box;
		    margin-bottom: 8px;
		}
		
		input:focus {
		    outline: none;
		    border-color: #2e7d32;
		    box-shadow: 0 0 5px #2e7d32;
		}
		
		.btn {
		    width: 100%;
		    padding: 10px;
		    font-size: 16px;
		    font-weight: 600;
		    background-color: #388e3c;
		    color: white;
		    border: none;
		    border-radius: 5px;
		    cursor: pointer;
		    transition: 0.3s;
		    margin-top: 10px;
		    text-transform: uppercase;
		    letter-spacing: 1px;
		}
		
		.btn:hover {
		    background-color: #2e7d32; 
		    box-shadow: 0 4px 15px rgba(46, 125, 50, 0.4); 
		}
		
		.login-link {
		    margin-top: 15px;
		    font-size: 14px;
		    color: #4caf50;
		}
		
		.login-link a {
		    color: #2e7d32;
		    text-decoration: none;
		    font-weight: 500;
		    transition: 0.3s;
		}
		
		.login-link a:hover {
		    color: #1b5e20;
		    text-decoration: none;
		}
  		
  	</style>
  	
</head>

<body>
	<div class ="login-container">
		<h2>Register to Our System</h2>
		<form action ="RegisterServlet" method ="POST">
			<div class ="register-details">
				<label for ="username">Username</label>
				<input type ="text" id ="username" name ="username" placeholder ="Enter Your Username" required>
			</div>
			<div class ="register-details">
				<label for ="email">Email</label>
				<input type ="text" id ="email" name ="email" placeholder ="Enter Your Email" required>
			</div>
			<div class ="register-details">
				<label for ="address">Address</label>
				<input type ="text" id ="address" name ="address" placeholder ="Enter Your Address" required>
			</div>
			<div class ="register-details">
				<label for ="telephone">Telephone</label>
				<input type ="text" id ="telephone" name ="telephone" placeholder ="Enter Your Telephone Number" required>
			</div>
			<div class ="register-details">
				<label for ="nic">NIC</label>
				<input type ="text" id ="nic" name ="nic" placeholder ="Enter Your NIC Number" required>
			</div>
			<div class ="register-details">
				<label for ="password">Password</label>
				<input type ="password" id ="password" name ="password" placeholder ="Enter Your Password" required>
			</div>
			<div class ="register-details">
				<label for ="password">Confirm Password</label>
				<input type ="password" id ="confirmpassword" name ="confirmpassword" placeholder ="Confirm Your Password" required>
			</div>
			<button type ="submit" class ="btn">Sign Up</button>
		</form>
		<p class="login-link">Have an account? <a href="LoginServlet">Sign In</a></p>
	</div>

</body>

</html>