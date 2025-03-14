package com.system.controller;

import java.io.IOException;

import java.sql.Timestamp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.system.db.PaymentDao;
import com.system.model.Booking;
import com.system.model.Invoice;
import com.system.model.Payment;
import com.system.model.SelectedCab;


public class PaymentControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PaymentDao paymentDao;
	
	public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }
	
    @Override
    public void init() {
        paymentDao = new PaymentDao();
        System.out.println("[DEBUG] PaymentDao initialized: " + (paymentDao !=null));
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	
    	try {
    		if(action == null) {
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
    		} else if(action.equals("invoice")) {
    			showInvoice(request, response);
    		} else if(action.equals("payment")) {
    			showPayment(request, response);
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");
    	try {
    		if(action == null) {
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
    		} else if(action.equals("paymentProcess")) {
    			paymentProcess(request, response);
    		} else if(action.equals("calculate")) {
    			calculateBill(request, response);
    		} else if(action.equals("invoice")) {
    			showInvoice(request, response);
    		} else if(action.equals("payment")) {
    			showPayment(request, response);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public void calculateBill(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession httpsession = request.getSession();
    	Booking booking = (Booking) httpsession.getAttribute("booking");
    	
    	if(booking == null) {
    		request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
    		return;
    	}
    	
    	SelectedCab selectedCab = paymentDao.getSelectedCabData(booking.getBookingId());
    	
    	if(selectedCab == null) {
    		request.setAttribute("error", "Cab data not found!");
    		request.getRequestDispatcher("/WEB-INF/bookings/selectCab.jsp").forward(request, response);
    	}
    	
    	double distance = Double.parseDouble(request.getParameter("distance"));
    	
    	double basePrice = selectedCab.getBasePrice();
    	double costPerKm = selectedCab.getCostPerKm();
    	double taxRate = 0.10;
    	double driverCostRate = 0.10;
    	
    	double driverCost = basePrice * driverCostRate;
    	double fee = (double) Math.round(basePrice + (costPerKm * distance));
    	double tax = (double) Math.round(fee * taxRate);
    	double totalAmount = (double) Math.round(fee + tax + driverCost);
    	
    	Invoice invoice = new Invoice();
    	invoice.setInvoiceId(paymentDao.generateNewInvoiceCode());
    	invoice.setBookingId(booking.getBookingId());
    	invoice.setBasePrice(basePrice);
    	invoice.setDistance(distance);
    	invoice.setCostPerKm(costPerKm);
    	invoice.setTax(tax);
    	invoice.setDriverCost(driverCost);
    	invoice.setTotalAmount(totalAmount);
    	invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    	
    	boolean saveInvoice = paymentDao.saveInvoiceData(invoice);
    	
    	if(!saveInvoice) {
    		request.setAttribute("error", "Failed to save invoice data");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/payments/location.jsp");
    		dispatcher.forward(request, response);
    	}
    	
    	httpsession.setAttribute("invoice", invoice);
    	httpsession.setAttribute("selectedCab", selectedCab);
    	request.getRequestDispatcher("/WEB-INF/payments/calculateBill.jsp").forward(request, response);
    	
    }
    
   	private void paymentProcess(HttpServletRequest request, HttpServletResponse response) throws  Exception {
		HttpSession httpsession = request.getSession();
		Booking booking = (Booking) httpsession.getAttribute("booking");
		Invoice invoice = (Invoice) httpsession.getAttribute("invoice");
		
		if(booking == null) {
			request.setAttribute("error", "No booking information found!");
			request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
    		return;
		}
		
		if(invoice == null) {
			request.setAttribute("error", "No invoice information found!");
			request.getRequestDispatcher("/WEB-INF/payments/calculateBill.jsp").forward(request, response);
    		return;
		}
		
		Payment payment = new Payment();
		payment.setPaymentId(paymentDao.generateNewPaymentCode());
		payment.setBookingId(booking.getBookingId());
		payment.setInvoiceId(invoice.getInvoiceId());
		payment.setTotalAmount(Double.parseDouble(request.getParameter("totalAmount")));
		payment.setPaymentMethod(request.getParameter("paymentMethod"));
		payment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		
		if("CARD".equalsIgnoreCase(payment.getPaymentMethod())) {
			payment.setCardNumber(request.getParameter("cardNumber"));
			payment.setCardHolder(request.getParameter("cardHolder"));
			payment.setExpiry(request.getParameter("expiry"));
			payment.setCvv(request.getParameter("cvv"));
			payment.setStatus("COMPLETED");
		} else {
			payment.setStatus("PENDING");
		}
		
		boolean isPaymentSaved = paymentDao.savePaymentData(payment);
		
		if(isPaymentSaved) {
			invoice = paymentDao.getInvoiceById(invoice.getInvoiceId());
			SelectedCab selectedCab = paymentDao.getSelectedCabData(booking.getBookingId());

			httpsession.setAttribute("payment", payment);
			httpsession.setAttribute("invoice", invoice);
			httpsession.setAttribute("selectedCab", selectedCab);
			request.getRequestDispatcher("/WEB-INF/payments/confirmPayment.jsp").forward(request, response);
			return;
		} else {
			request.setAttribute("error", "Payment failed");
			request.getRequestDispatcher("/WEB-INF/payments/calculateBill.jsp").forward(request, response);
			return;
		}
	
	}
   	
   	private void showInvoice(HttpServletRequest request, HttpServletResponse response) throws  Exception {
   		HttpSession httpsession = request.getSession();
   		Booking booking = (Booking)	httpsession.getAttribute("booking");
   		
   		if(booking == null) {
   			request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp").forward(request, response);
   			return;
   		}
   		
   		Invoice invoice = paymentDao.getInvoiceData(booking.getBookingId());
   		request.setAttribute("invoice", invoice);
   		request.getRequestDispatcher("/WEB-INF/payments/payment.jsp").forward(request, response);
   		
   	}
   	
   	private void showPayment(HttpServletRequest request, HttpServletResponse response) throws  Exception {
   		String invoiceId = request.getParameter("invoiceId");
   		Invoice invoice = paymentDao.getInvoiceById(invoiceId);
   		
   		if(invoice != null) {
   			request.setAttribute("invoice", invoice);
   			request.getRequestDispatcher("/WEB-INF/payments/payment.jsp").forward(request, response);
   		} else {
   			request.getRequestDispatcher("/WEB-INF/payments/calculateBill.jsp").forward(request, response);
   		}

   	}


}
