package com.system.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.system.db.BookingDao;
import com.system.db.CabDao;
import com.system.model.Booking;
import com.system.model.Cab;
import com.system.model.SelectedCab;
import com.system.model.Location;

public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDao bookingDao;
    
    public void setBookingDao(BookingDao bookingDao) {
    	this.bookingDao = bookingDao;
    }
    
    @Override
    public void init() {
    	if (bookingDao == null) { 
            bookingDao = new BookingDao();
            System.out.println("[DEBUG] BookingDao initialized: " + (bookingDao != null));
        }
    }
    
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	try {
    		if(action == null) {
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
    		} else if(action.equals("location")){
    			locationProcess(request, response);
    		} else if(action.equals("list")) {
    			showListBooking(request, response);
    		} else if(action.equals("add")) {
    			showAddBooking(request, response);
    		} else if(action.equals("update")) {
    			showUpdateBooking(request, response);
    		} else if(action.equals("delete")) {
    			deleteBooking(request, response);
    		} else if(action.equals("showSelectedCab")) {
    			showSelectedCabs(request, response);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
   		 	request.setAttribute("error", "An error occurred while processing your booking!.");
            request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
            return;
    	}
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	try {
        	if(action == null) {
        		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
                return;
        	} else if(action.equals("addBooking")) {
        		addNewBooking(request, response);
        	} else if(action.equals("selectCab")) {
        		savedSelectedCabData(request, response);
        	} else if(action.equals("updateBooking")) {
        		updateBookingData(request, response);
        	} else if(action.equals("location")) {
        		locationProcess(request, response);
        	} else if(action.equals("processBilling")) {
        		processBilling(request, response);
        	} else if(action.equals("list")) {
    			showListBooking(request, response);
    		} else if(action.equals("delete")) {
    			deleteBooking(request, response);
    		} else if(action.equals("update")) {
    			updateBooking(request, response);
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    		 request.setAttribute("error", "An error occurred while processing your booking!.");
             request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
             return;
    	}

    }
    
    private void showListBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setAttribute("bookingList", bookingDao.getAllBooking());
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/listBooking.jsp");
	    dispatcher.forward(request, response);
    }
    
    private void showAddBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    	request.setAttribute("action", "add");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/addBooking.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void showSelectedCabs(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String bookingId = request.getParameter("bookingId");
    	List<SelectedCab> selectedCabs;
    	if(bookingId != null && !bookingId.isEmpty()) {
    		selectedCabs = bookingDao.getSelectedCabByBookingId(bookingId);
    	} else {
    		selectedCabs = bookingDao.getAllSelectedCabs();
    	}
    	
    	
    	request.setAttribute("selectedCabList", selectedCabs);
    	request.getRequestDispatcher("/WEB-INF/cabs/displaySelectedCabs.jsp").forward(request, response);
    	return;
    }
    
    private void showUpdateBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String bookingIdNo = request.getParameter("bookingNumber");
    	if(bookingIdNo == null || bookingIdNo.isEmpty()) {
    		response.sendRedirect("BookingServlet?action=list");
    		return;
    }
    	
    	
    	int bookingNumber = Integer.parseInt(bookingIdNo);
    	Booking booking = bookingDao.getBookingByNumber(bookingNumber);
    	if(booking == null) {
    		response.sendRedirect("BookingServlet?action=list");
    		return;
    	}
    	
    	request.setAttribute("booking", booking);
    	request.setAttribute("action", "updateBooking");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/updateBooking.jsp");
    	dispatcher.forward(request, response);
    	
    }
    
    private void addNewBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String customername = request.getParameter("customername");
        String customeraddress = request.getParameter("customeraddress");
        String customerphone = request.getParameter("customerphone");
        String pickuplocation = request.getParameter("pickuplocation");
        String destination = request.getParameter("destination");

        System.out.println("[DEBUG] Received Parameters:");
        System.out.println("[DEBUG] customername: " + customername);
        System.out.println("[DEBUG] customeraddress: " + customeraddress);
        System.out.println("[DEBUG] customerphone: " + customerphone);
        System.out.println("[DEBUG] pickuplocation: " + pickuplocation);
        System.out.println("[DEBUG] pickupdatetime: " + request.getParameter("pickupdatetime"));
        System.out.println("[DEBUG] destination: " + destination);

        String passengersNo = request.getParameter("passengers");
        int passengers = 0;
        String pickupdatetimeNo = request.getParameter("pickupdatetime");
        Timestamp pickupdatetime = null;
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        
        try {
            if (passengersNo == null || passengersNo.trim().isEmpty()) {
                throw new NumberFormatException("Passengers field is empty or null.");
            }
            if (pickupdatetimeNo == null || pickupdatetimeNo.isEmpty()) {
                throw new IllegalArgumentException("Pick up date and time is requied.");
            } 

            passengers = Integer.parseInt(passengersNo);
            System.out.println("[DEBUG] passengers: " + passengersNo);

            
            pickupdatetimeNo = pickupdatetimeNo.replace("T", " ") + ":00";
            System.out.println("[DEBUG] Pickup date and time: " + pickupdatetimeNo);
            pickupdatetime = Timestamp.valueOf(pickupdatetimeNo);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid number format for capacity or base price.");
            request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
            return; 
        }

        String bookingId = bookingDao.generateNewBookingCode();
        System.out.println("Generated bookingId: " + bookingId);
        if (bookingId == null || bookingId.isEmpty()) {
            request.setAttribute("error", "Error generating booking Id.");
            request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
            return;
        }
        
        Booking booking = new Booking(0, bookingId, customername, customeraddress, customerphone, pickuplocation, pickupdatetime, destination, passengers, createdAt);
        
        boolean isBookingSaved = bookingDao.addBooking(booking) != null;
        if (!isBookingSaved) {
            request.setAttribute("error", "Failed to save booking. Please try again.");
            request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
            return;
        }

        HttpSession htttpsession = request.getSession();
        htttpsession.setAttribute("booking", booking);
        
        response.sendRedirect(request.getContextPath() + "/CabControllerServlet?action=cabList");
        return;
    }  
    
    private void updateBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bookingNumberNo = request.getParameter("bookingNumber");
        if (bookingNumberNo == null || bookingNumberNo.isEmpty()) {
            response.sendRedirect("BookingServlet?action=list");
            return;
        }

        int bookingNumber = Integer.parseInt(bookingNumberNo);
        Booking oldBooking = bookingDao.getBookingByNumber(bookingNumber);
        if (oldBooking == null) {
            response.sendRedirect("BookingServlet?action=list");
            return;
        }

        String customername = request.getParameter("customername");
        String customeraddress = request.getParameter("customeraddress");
        String customerphone = request.getParameter("customerphone");
        String pickuplocation = request.getParameter("pickuplocation");
        String destination = request.getParameter("destination");
        
        String passengersNo = request.getParameter("passengers");
        int passengers = 0;
        String pickupdatetimeNo = request.getParameter("pickupdatetime");
        Timestamp pickupdatetime = null;
        
        String bookingId = oldBooking.getBookingId();
        try {
            if (passengersNo == null || passengersNo.trim().isEmpty()) {
                throw new NumberFormatException("Passengers field is empty or null.");
            }
            if (pickupdatetimeNo == null || pickupdatetimeNo.isEmpty()) {
                throw new IllegalArgumentException("Pick up date and time is requied.");
            } 

            passengers = Integer.parseInt(passengersNo);
            
            pickupdatetimeNo = pickupdatetimeNo.replace("T", " ") + ":00";
            pickupdatetime = Timestamp.valueOf(pickupdatetimeNo);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid number format for capacity or base price.");
            request.getRequestDispatcher("/WEB-INF/admin/addBooking.jsp").forward(request, response);
            return; 
        }

        Booking updatedBooking = new Booking(bookingNumber, bookingId, customername, customeraddress, customerphone, pickuplocation, pickupdatetime, destination, passengers, null);
        bookingDao.updateBooking(updatedBooking);

        response.sendRedirect("BookingServlet?action=list");
    }

    
    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String bookingNumberNo = request.getParameter("bookingNumber");
    	if(bookingNumberNo == null || bookingNumberNo.isEmpty()) {
    		response.sendRedirect("BookingServlet?action=list");
    		return;
    	}
    	
    	int bookingNumber = Integer.parseInt(bookingNumberNo);
    	bookingDao.deleteBooking(bookingNumber);
    	response.sendRedirect("BookingServlet?action=list");    	
    }
    
    private void savedSelectedCabData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession httpsession = request.getSession();
    	Booking booking = (Booking) httpsession.getAttribute("booking");
    	
    	if(booking == null) {
    		request.setAttribute("error", "No available bookings found. Please try again");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	String cabCode = request.getParameter("selectedCab");
    	if(cabCode == null || cabCode.isEmpty()) {
    		request.setAttribute("error", "Select a cab");
    		request.getRequestDispatcher("/WEB-INF/bookings/selectCab.jsp").forward(request, response);
    		return;
    	}
 
    	CabDao cabDao = new CabDao();
    	Cab selectedCabData = cabDao.getCabByCabCode(cabCode);
    	
    	if(selectedCabData == null) {
    		request.setAttribute("error", "Invalid cab data");
    		request.getRequestDispatcher("/WEB-INF/bookings/selectCab.jsp").forward(request, response);
    		return;
    	}
    	
    	SelectedCab selectedCab = new SelectedCab();
    	selectedCab.setCabCode(selectedCabData.getCabCode());
    	selectedCab.setCabNumber(selectedCabData.getCabNumber());
    	selectedCab.setModel(selectedCabData.getModel());
    	selectedCab.setBrand(selectedCabData.getBrand());
    	selectedCab.setCapacity(selectedCabData.getCapacity());
    	selectedCab.setBasePrice(selectedCabData.getBasePrice());
    	selectedCab.setCostPerKm(selectedCabData.getCostPerKm());
    	selectedCab.setCabImage(selectedCabData.getCabImage());
    	selectedCab.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    	
    	boolean isCabSeved = bookingDao.saveSelectedCabData(selectedCab, booking.getBookingId());
    	
    	if(!isCabSeved) {
    		request.setAttribute("error", "failed to save selected cab details. Please try again");
    		request.getRequestDispatcher("/WEB-INF/bookings/selectCab.jsp").forward(request, response);
    		return;
    	}
    	
    	httpsession.setAttribute("selectedCab", selectedCab);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookings/confirmData.jsp");
    	dispatcher.forward(request, response);
    	return;
    }
    
    public void updateBookingData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession httpsession = request.getSession();
    	Booking booking = (Booking) httpsession.getAttribute("booking");
    	
    	if(booking == null) {
    		request.setAttribute("error", "Invalid Booking!");
    		request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
    	}
    	
    	String pickupDateTimeStr = request.getParameter("pickupDateTime");
    	if(pickupDateTimeStr == null || pickupDateTimeStr.isEmpty()) {
    		request.setAttribute("error", "Pick up date and time is empty or null");
    		request.getRequestDispatcher("/WEB-INF/bookings/confirmData.jsp");
    		return;
    	}
    	
    	try {
    		booking.setCustomerName(request.getParameter("customerName"));
        	booking.setCustomerAddress(request.getParameter("customerAddress"));
        	booking.setCustomerPhone(request.getParameter("customerPhone"));
        	booking.setPickupLocation(request.getParameter("pickupLocation"));
        	
        	String pickupDateTimeNo = pickupDateTimeStr.replace("T", " ") + ":00";
        	booking.setPickupDateTime(Timestamp.valueOf(pickupDateTimeNo));

        	booking.setDestination(request.getParameter("destination"));
        	
        	String passengersNo = (request.getParameter("passengers"));
        	booking.setPassengers(Integer.parseInt(passengersNo));
        	
        	boolean isBookingUpdate = bookingDao.updateBookingData(booking);
        	
        	if(!isBookingUpdate) {
        		request.setAttribute("error", "failed to update booking cab details. Please try again");
        		request.getRequestDispatcher("/WEB-INF/bookings/confirmData.jsp").forward(request, response);
        		return;
        	}
        	
        	httpsession.setAttribute("booking", booking);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookings/confirmData.jsp");
        	dispatcher.forward(request, response);

    	}catch(IllegalArgumentException e) {
    		e.printStackTrace();
    		request.setAttribute("error", "Invalid date and time formate");
    		request.getRequestDispatcher("/WEB-INF/bookings/confirmData.jsp").forward(request, response);
    	}   
    }
    
    public void locationProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession httpsession = request.getSession();
    	Booking booking = (Booking) httpsession.getAttribute("booking");
    	
    	if(booking == null) {
    		request.setAttribute("error", "No booking found!");
    		request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
    		return;
    	}
    	
    	String bookingId = booking.getBookingId();
    	Location location = bookingDao.getLocationDetails(bookingId);
    	
    	if(location != null) {
    		request.setAttribute("location", location);
    		request.getRequestDispatcher("/WEB-INF/payments/location.jsp").forward(request, response);
    		return;
    	} else {
    		request.setAttribute("error", "Location details not found!");
    		request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
    		return;
    	}

    }
    
    private void processBilling(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession httpsession = request.getSession();
    	Booking booking = (Booking) httpsession.getAttribute("booking");
    	
    	if(booking == null) {
    		request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
    		return;
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/PaymentControllerServlet?action=calculate");
    	dispatcher.forward(request, response);
    	
    }
    
    
  }   

