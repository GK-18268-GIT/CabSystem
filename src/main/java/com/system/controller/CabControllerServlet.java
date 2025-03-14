package com.system.controller;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.system.db.CabDao;
import com.system.model.Cab;


@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 50
	)

	public class CabControllerServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private CabDao cabDao;    
	    private String UPLOAD_PATH;
	    
	    public void setCabDao(CabDao cabDao) {
			this.cabDao = cabDao;
		}
	    
	    public void setUploadPath(String uploadPath) {
	    	this.UPLOAD_PATH = uploadPath;
	    }
	    
	    @Override
	    public void init() throws ServletException {
	        cabDao = new CabDao();
	        System.out.println("[DEBUG] CabDao initialized: " + (cabDao !=null));
	        
	        UPLOAD_PATH = "E:\\\\Eclipse-Projects\\\\CabSystemUploads";
	        System.out.println("[DEBUG] Upload Path: " + UPLOAD_PATH);
	    }
	    
	    @Override
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String action = request.getParameter("action");
	    	try {
	    		if(action == null || action.equals("list")) {
	        		showListCab(request, response);
	        	} else if(action.equals("add")) {
	        		showAddCab(request, response);
	        	} else if(action.equals("update")) {
	        		showUpdateCab(request, response);
	        	} else if(action.equals("delete")) {
	        		deleteCab(request, response);
	        	} else if(action.equals("select")) {
	        		showAllCab(request, response);
	        	} else if(action.equals("cabList")) {
	        		showAvailableCabs(request, response);
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
	    			showListCab(request, response);
	    		} else if(action.equals("add")) {
	    			addNewCab(request, response);
	    		} else if(action.equals("update")) {
	    			updateCab(request, response);
	    		} else if(action.equals("delete")) {
	    			deleteCab(request, response);
	    		} 
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    		System.out.println("[DEBU] Error!");
	    	}
	    }
	    
	    private void showListCab(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	request.setAttribute("cabList", cabDao.getAllCabs());
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/listCab.jsp");
	    	dispatcher.forward(request, response);
	    }
	    
	    private void showAddCab(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	request.setAttribute("action", "add");
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cabs/addCab.jsp");
	    	dispatcher.forward(request, response);
	    }
	    
	    private void showUpdateCab(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	String cabIdNo = request.getParameter("cabId");
	    	if(cabIdNo == null || cabIdNo.isEmpty()) {
	    		response.sendRedirect("CabControllerServlet?action=list");
	    		return;
	    	}
	    	
	    	int cabId = Integer.parseInt(cabIdNo);
	    	Cab cab = cabDao.getCabById(cabId);
	    	if(cab == null) {
	    		response.sendRedirect("CabControllerServlet?action=list");
	    		return;
	    	}
	    	
	    	request.setAttribute("cab", cab);
	    	request.setAttribute("action", "update");
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/updateCab.jsp");
	    	dispatcher.forward(request, response);
	    	
	    }
	    
	    private void showAllCab(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	List<Cab> cabList = cabDao.getAllCabs();
	    	System.out.println("[DEBUG] CabList size in showAvailableCabs(): " + cabList.size());
	    	request.setAttribute("cabList", cabList);
	    	request.getRequestDispatcher("/WEB-INF/cabs/displayCabs.jsp").forward(request, response);
	    }
	    
	    private void showAvailableCabs(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	List<Cab> cabList = cabDao.getAvailableCabs();
	    	System.out.println("[DEBUG] CabList size in showAvailableCabs(): " + cabList.size());
	    	request.setAttribute("cabList", cabList);
	    	request.getRequestDispatcher("/WEB-INF/bookings/selectCab.jsp").forward(request, response);
	    }

	    
	    private void addNewCab(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        String cabNumber = request.getParameter("cabNumber");
	        String model = request.getParameter("model");
	        String brand = request.getParameter("brand");

	        int capacity = Integer.parseInt(request.getParameter("capacity"));
	        double basePrice = Double.parseDouble(request.getParameter("basePrice"));
	        double costPerKm = Double.parseDouble(request.getParameter("costPerKm"));
	        String status = request.getParameter("status");

	        String cabCode = cabDao.generateNewCabCode();
	        if (cabCode == null || cabCode.isEmpty()) {
	            request.setAttribute("error", "Error generating cab code.");
	            request.getRequestDispatcher("/WEB-INF/cabs/addCab.jsp").forward(request, response);
	            return;
	        }

	        Part filePart = request.getPart("cabImage");
	        String fileName = "";
	        
	        File uploadPath = new File(UPLOAD_PATH);
	        if(!uploadPath.exists()) {
	        	if(!uploadPath.mkdirs()) {
	        		throw new IOException("Failed to create path:" + UPLOAD_PATH);
	        	}
	        }

	        if (filePart != null && filePart.getSize() > 0) {
	            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	            
	            File file = new File(UPLOAD_PATH + File.separator + fileName);
	            
	            filePart.write(file.getAbsolutePath());

	        }

	        Cab newCab = new Cab(0, cabCode, cabNumber, model, brand, capacity, basePrice, costPerKm, status, fileName);
	        cabDao.addCab(newCab);

	        response.sendRedirect("CabControllerServlet?action=list");
	    }

	    
	    private void updateCab(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        String cabIdNo = request.getParameter("cabId");
	        if (cabIdNo == null || cabIdNo.isEmpty()) {
	            response.sendRedirect("CabControllerServlet?action=list");
	            return;
	        }

	        int cabId = Integer.parseInt(cabIdNo);
	        Cab oldCab = cabDao.getCabById(cabId);
	        if (oldCab == null) {
	            response.sendRedirect("CabControllerServlet?action=list");
	            return;
	        }

	        String cabNumber = request.getParameter("cabNumber");
	        String model = request.getParameter("model");
	        String brand = request.getParameter("brand");
	        int capacity = Integer.parseInt(request.getParameter("capacity"));
	        double basePrice = Double.parseDouble(request.getParameter("basePrice"));
	        double costPerKm = Double.parseDouble(request.getParameter("costPerKm"));
	        String status = request.getParameter("status");
	        
	        String cabCode = oldCab.getCabCode();

	        Part filePart = request.getPart("cabImage");
	        String fileName = oldCab.getCabImage();
	        
	        File uploadPath = new File(UPLOAD_PATH);
	        if(!uploadPath.exists()) {
	        	if(!uploadPath.mkdirs()) {
	        		throw new IOException("Failed to create path:" + UPLOAD_PATH);
	        	}
	        }

	        if (filePart != null && filePart.getSize() > 0) {
	        	if(oldCab.getCabImage() != null && !oldCab.getCabImage().isEmpty()) {
	        		File oldFile = new File(uploadPath, oldCab.getCabImage());
	        		if(oldFile.exists()) {
	        			Files.deleteIfExists(oldFile.toPath());
	        		}
	            
	            }

	            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	            File newFile = new File(uploadPath, fileName);
	            filePart.write(newFile.getAbsolutePath());
	        }

	        Cab updatedCab = new Cab(cabId, cabCode, cabNumber, model, brand, capacity, basePrice, costPerKm, status, fileName);
	        cabDao.updateCab(updatedCab);

	        response.sendRedirect("CabControllerServlet?action=list");
	    }
	    
	    private void deleteCab(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	String cabIdNo = request.getParameter("cabId");
	    	if(cabIdNo == null || cabIdNo.isEmpty()) {
	    		response.sendRedirect("CabControllerServlet?action=list");
	    		return;
	    	}
	    	
	    	int cabId = Integer.parseInt(cabIdNo);
	    	cabDao.deleteCab(cabId);
	    	response.sendRedirect("CabControllerServlet?action=list");    	
	    }
	        
	}

