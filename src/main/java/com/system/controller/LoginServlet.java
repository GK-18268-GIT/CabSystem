package com.system.controller;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycabservice";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Gk18268@h*421";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/login.jsp");
		 dispatcher.forward(request, response);
		}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if("admin".equals(username) && "password".equals(password)) {
			HttpSession httpsession = request.getSession();
			httpsession .setAttribute("user", username);
			httpsession .setAttribute("userRole", "admin");
			request.getRequestDispatcher("WEB-INF/admin/dashboard.jsp").forward(request, response);
			return;
		}
		
		String hashedPassword = hashedPassword(password);
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
			ps.setString(1, username);
			ps.setString(2, hashedPassword);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				HttpSession httpsession = request.getSession();
				httpsession .setAttribute("user", username);
				request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
				return;
			} else {
				request.setAttribute("error", "Invalid username or password");
				request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
				return;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "login failed!");
			request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
		}
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
		
				private Connection getConnection() throws Exception {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
		}
		

}

