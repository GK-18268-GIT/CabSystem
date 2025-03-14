package com.system.controller;
		
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 	private static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycabservice";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "Gk18268@h*421";
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/register.jsp");
	   dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String telephone = request.getParameter("telephone");
		String nic = request.getParameter("nic");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		
		if(!password.equals(confirmpassword)) {
			request.setAttribute("error", "Passwords do not match");
			request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);
			return;
		}
		
		String hashedPassword = hashedPassword(password);
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, email,address, telephone, nic, password, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)");   
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, address);
			ps.setString(4, telephone);
			ps.setString(5, nic);
			ps.setString(6, hashedPassword);
			ps.setTimestamp(7, Timestamp.from(Instant.now()));
			ps.executeUpdate();
			
			HttpSession httpsession = request.getSession();
			httpsession.setAttribute("user", username);
			request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "error=Registration failed");
			request.getRequestDispatcher("/WEB-INF/user/register.jsp?").forward(request, response);
		}
	}
	
	private Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
	
	private String hashedPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();	
			for(byte b: bytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
