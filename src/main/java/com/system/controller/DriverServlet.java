package com.system.controller;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.system.db.DriverDao;
import com.system.model.Driver;

public class DriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DriverDao driverDao;
	
	public void setDriverDao(DriverDao driverDao) {
		this.driverDao = driverDao;
		
	}
     
	@Override
    public void init() {
    	driverDao = new DriverDao();
        System.out.println("[DEBUG] DriverDao initialized: " + (driverDao !=null));
       
    }

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
    	try {
    		if(action == null || action.equals("list")) {
        		showListDriver(request, response);
        	} else if(action.equals("add")) {
        		showAddDriver(request, response);
        	} else if(action.equals("update")) {
        		showUpdateDriver(request, response);
        	} else if(action.equals("delete")) {
        		deleteDriver(request, response);
        	} else if(action.equals("driverList")) {
        		showAvailableDrivers(request, response); 	
        	} 

    	} catch(SQLException e) {
    		e.printStackTrace();
    		System.out.println("[DEBUG] Error!");
    	} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
    	try {
    		if(action == null || action.equals("list")) {
    			showListDriver(request, response);
    		} else if(action.equals("add")) {
    			addNewDriver(request, response);
    		} else if(action.equals("update")) {
    			updateDriver(request, response);
    		} else if(action.equals("delete")) {
    			deleteDriver(request, response);
    		} 
    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("[DEBUG] Error!");
    	}
	}
	
	private void showListDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.setAttribute("driverList", driverDao.getAllDrivers());
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/listDriver.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void showAddDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    	request.setAttribute("action", "add");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/drivers/addDriver.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void showUpdateDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String driverIdNo = request.getParameter("driverId");
    	if(driverIdNo == null || driverIdNo.isEmpty()) {
    		response.sendRedirect("DriverServlet?action=list");
    		return;
    	}
    	
    	int driverId = Integer.parseInt(driverIdNo);
    	Driver driver = driverDao.getDriverById(driverId);
    	if(driver == null) {
    		response.sendRedirect("DriverServlet?action=list");
    		return;
    	}
    	
    	request.setAttribute("driver", driver);
    	request.setAttribute("action", "update");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/updateDriver.jsp");
    	dispatcher.forward(request, response);
    	
    }
    
    private void showAvailableDrivers(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<Driver> driverList = driverDao.getAvailableDrivers();
    	System.out.println("[DEBUG] CabList size in showAvailableCabs(): " + driverList.size());
    	request.setAttribute("driverList", driverList);
    	
    }
    
    
    private void addNewDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String driverName = request.getParameter("driverName");
        int driverAge = Integer.parseInt(request.getParameter("driverAge"));
        String driverPhone = request.getParameter("driverPhone");
        String driverEmail = request.getParameter("driverEmail");
        String driverAddress = request.getParameter("driverAddress");
        String driverNIC = request.getParameter("driverNIC");
        String status = request.getParameter("status");
        
        System.out.println("[DEBUG] Received Parameters:");
        System.out.println("[DEBUG] Driver name: " + driverName);
        System.out.println("[DEBUG] Driver age: " + (request.getParameter("driverAge")));
        System.out.println("[DEBUG] Driver Telephone: " + driverPhone);
        System.out.println("[DEBUG] Driver Email: " + driverEmail);
        System.out.println("[DEBUG] Driver Address: " + driverAddress);
        System.out.println("[DEBUG] Driver NIC: " + driverNIC);

        String driverCode = driverDao.generateNewDriverCode();
        if (driverCode == null || driverCode.isEmpty()) {
            request.setAttribute("error", "Error generating cab code.");
            request.getRequestDispatcher("/WEB-INF/drivers/addDriver.jsp").forward(request, response);
            return;
        }

        Driver newDriver = new Driver(0, driverCode, driverName, driverAge, driverPhone, driverEmail, driverAddress, driverNIC, status);
        driverDao.addDriver(newDriver);

        response.sendRedirect("DriverServlet?action=list");
    }

    
    private void updateDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String driverIdNo = request.getParameter("driverId");
        if (driverIdNo == null || driverIdNo.isEmpty()) {
            response.sendRedirect("DriverServlet?action=list");
            return;
        }

        int driverId = Integer.parseInt(driverIdNo);
        Driver oldDriver = driverDao.getDriverById(driverId);
        if (oldDriver == null) {
            response.sendRedirect("DriverServlet?action=list");
            return;
        }

        String driverName = request.getParameter("cabNumber");
        int driverAge = Integer.parseInt(request.getParameter("driverAge"));
        String driverPhone = request.getParameter("driverPhone");
        String driverEmail = request.getParameter("driverEmail");
        String driverAddress = request.getParameter("driverAddress");
        String driverNIC = request.getParameter("driverNIC");
        String status = request.getParameter("status");    
        
        String driverCode = oldDriver.getDriverCode();


        Driver updatedDriver = new Driver(driverId, driverCode, driverName, driverAge, driverPhone, driverEmail, driverAddress, driverNIC, status);
        driverDao.updateDriver(updatedDriver);

        response.sendRedirect("DriverServlet?action=list");
    }
    
    private void deleteDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String driverIdNo = request.getParameter("driverId");
    	if(driverIdNo == null || driverIdNo.isEmpty()) {
    		response.sendRedirect("DriverServlet?action=list");
    		return;
    	}
    	
    	int driverId = Integer.parseInt(driverIdNo);
    	driverDao.deleteDriver(driverId);
    	response.sendRedirect("DriverServlet?action=list");    	
    }
}
