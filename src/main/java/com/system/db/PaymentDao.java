package com.system.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.system.model.SelectedCab;
import com.system.model.Payment;
import com.system.model.Invoice;


public class PaymentDao {
	 
	 //Insert Invoice data to the database
	    public boolean saveInvoiceData(Invoice invoice) throws Exception {
	    	String query = "INSERT INTO invoice(invoice_id, booking_id, base_price, distance, cost_per_km, tax, driver_cost,"
	    			+ " total_amount, created_at)"
	    			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    	
	    	Connection conn = null;
	        PreparedStatement ps = null;
	    	
	    	try {
	    		conn = DBConnectionFactory.getConnection();
	        	ps = conn.prepareStatement(query);
	    		
	    		ps.setString(1, invoice.getInvoiceId());
	    		ps.setString(2, invoice.getBookingId());
	    		ps.setDouble(3, invoice.getBasePrice());
	    		ps.setDouble(4, invoice.getDistance());
	    		ps.setDouble(5, invoice.getCostPerKm());
	    		ps.setDouble(6, invoice.getTax());
	    		ps.setDouble(7, invoice.getDriverCost());
	    		ps.setDouble(8, invoice.getTotalAmount());
	    		ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
	    		
	    		int i = ps.executeUpdate();
	    		return i > 0;
	    		
	    	} catch(SQLException e) {
	    		e.printStackTrace();
	    		System.out.println("[DEBUG]: Failed to sent data to the database!");
	    	}
	    	return false;
	    }
	    
	    //Generate Invoice Id
	    public String generateNewInvoiceCode() throws Exception {
	        String newInvoiceCode = "INV-1000";
	        
	        Connection conn = null;
	        PreparedStatement ps = null;

	        try {
	        	 conn = DBConnectionFactory.getConnection(); 
	             ps = conn.prepareStatement("SELECT invoice_id FROM invoice ORDER BY invoice_id DESC LIMIT 1");
	             ResultSet resultSet = ps.executeQuery();

	            if (resultSet.next()) {
	                String lastInvoiceCode = resultSet.getString("invoice_id");
	                System.out.println("Last invoice_Id from DB: " + lastInvoiceCode);

	                if (lastInvoiceCode != null && lastInvoiceCode.matches("INV-\\d+")) {
	                    int oldNumber = Integer.parseInt(lastInvoiceCode.substring(4));
	                    newInvoiceCode = "INV-" + (oldNumber + 1);
	                } else {
	                    System.out.println("[DEBUG] Invalid or missing Invoice_Id, using default: " + newInvoiceCode);
	                }
	            } else {
	                System.out.println("[DEBUG] No previous records found. Using default Invoice ID: " + newInvoiceCode);
	            }
	        } catch (SQLException | NumberFormatException e) {
	            e.printStackTrace();
	            System.out.println("[DEBUG] Error generating new Invoice ID: " + e.getMessage());
	        }

	        System.out.println("[DEBUG] Generated Invoice ID: " + newInvoiceCode);
	        return newInvoiceCode;
	    }

	    public SelectedCab getSelectedCabData(String bookingId) throws Exception {
	    	String query = "SELECT * FROM selectcab WHERE booking_id = ?";
	    	SelectedCab selectedCab = null;
	    	
	    	Connection conn = null;
	        PreparedStatement ps = null;
	    	
	    	try {
	    		conn = DBConnectionFactory.getConnection();
	        	ps = conn.prepareStatement(query);
	    		
	    		ps.setString(1, bookingId);
	    		ResultSet rs = ps.executeQuery();
	    		
	    		if(rs.next()) {
	    			selectedCab = new SelectedCab();
	    			selectedCab.setCabCode(rs.getString("cab_code"));
	    			selectedCab.setCabNumber(rs.getString("cab_number"));
	    			selectedCab.setModel(rs.getString("model"));
	    			selectedCab.setBrand(rs.getString("brand"));
	    			selectedCab.setCapacity(rs.getInt("capacity"));
	    			selectedCab.setBasePrice(rs.getDouble("base_price"));
	    			selectedCab.setCostPerKm(rs.getDouble("cost_per_km"));
	    			selectedCab.setCabImage(rs.getString("cab_image"));
	    			selectedCab.setCreatedAt(rs.getTimestamp("created_at"));
	    		} 
	    	}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Cant't take data from the database");
				
			}
	    	return selectedCab;
	    }
	    
	    public boolean savePaymentData(Payment payment) throws Exception {
	    	String query = "INSERT INTO payment (payment_id, booking_id, invoice_id, total_amount, payment_method, card_number, card_holder,"
	    			+ "expiry, cvv, status, created_at) "
	    	
	    			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    	Connection conn = null;
	        PreparedStatement ps = null;

	    	try {
	    		conn = DBConnectionFactory.getConnection();
	        	ps = conn.prepareStatement(query);
	        	
	    		ps.setString(1, payment.getPaymentId());
	    		ps.setString(2, payment.getBookingId());
	    		ps.setString(3, payment.getInvoiceId());
	    		ps.setDouble(4, payment.getTotalAmount());
	    		ps.setString(5, payment.getPaymentMethod());
	    		ps.setString(6, payment.getCardNumber());
	    		ps.setString(7, payment.getCardHolder());
	    		ps.setString(8, payment.getExpiry());
	    		ps.setString(9, payment.getCvv());
	    		ps.setString(10, payment.getStatus());
	    		ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
	    		
	    		int i = ps.executeUpdate();
	    		return i > 0;
	    	} catch(SQLException e) {
	    		e.printStackTrace();
	    		System.out.println("Payment data not saved!");
	    	}
			return false;		
	    }
	    
	  //Generate Invoice Id
	    public String generateNewPaymentCode() throws Exception {
	        String newPaymentCode = "PMT-1000";
	        
	        Connection conn = null;
	        PreparedStatement ps = null;

	        try {
	        	 conn = DBConnectionFactory.getConnection(); 
	             ps = conn.prepareStatement("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");
	             ResultSet resultSet = ps.executeQuery();

	            if (resultSet.next()) {
	                String lastPaymenteCode = resultSet.getString("payment_id");
	                System.out.println("Last payment_Id from DB: " + lastPaymenteCode);

	                if (lastPaymenteCode != null && lastPaymenteCode.matches("PMT-\\d+")) {
	                    int oldNumber = Integer.parseInt(lastPaymenteCode.substring(4));
	                    newPaymentCode = "PMT-" + (oldNumber + 1);
	                } else {
	                    System.out.println("[DEBUG] Invalid or missing Payment_Id, using default: " + newPaymentCode);
	                }
	            } else {
	                System.out.println("[DEBUG] No previous records found. Using default Payment ID: " + newPaymentCode);
	            }
	        } catch (SQLException | NumberFormatException e) {
	            e.printStackTrace();
	            System.out.println("[DEBUG] Error generating new Payment ID: " + e.getMessage());
	        }

	        System.out.println("[DEBUG] Generated Payment ID: " + newPaymentCode);
	        return newPaymentCode;
	    } 
	    
	    public Invoice getInvoiceData(String bookingId) throws Exception {
	    	String query = "SELECT * FROM invoice WHERE booking_id = ?";
	    	Invoice invoice = null;
	    	
	    	Connection conn = null;
	        PreparedStatement ps = null;
	        
	    	try{
	    		conn = DBConnectionFactory.getConnection();
	        	ps = conn.prepareStatement(query);

	    		ps.setString(1, bookingId);
	    		ResultSet rs = ps.executeQuery();
	    		
	    		if(rs.next()) {
	    			invoice = new Invoice();
	    			invoice.setInvoiceId(rs.getString("invoice_id"));
	    			invoice.setBookingId(rs.getString("booking_id"));
	    			invoice.setBasePrice(rs.getDouble("base_price"));
	    			invoice.setDistance(rs.getDouble("distance"));
	    			invoice.setCostPerKm(rs.getDouble("cost_per_km"));
	    			invoice.setTax(rs.getDouble("tax"));
	    			invoice.setDriverCost(rs.getDouble("driver_cost"));
	    			invoice.setTotalAmount(rs.getDouble("total_amount"));
	    			invoice.setCreatedAt(rs.getTimestamp("created_at"));
	    		}
	    	} catch(SQLException e) {
	    		e.printStackTrace();
	    		System.out.println("Can't take data from the database");
	    	}
	    	return invoice;
	    }
	    
	    public Invoice getInvoiceById(String invoiceId) throws Exception {
	    	Invoice invoice = null;
	    	String query = "SELECT * FROM invoice WHERE invoice_id = ?";
	    	
	    	Connection conn = null;
	        PreparedStatement ps = null;
	    	
	    	try{
	    		conn = DBConnectionFactory.getConnection();
	        	ps = conn.prepareStatement(query);
	    		ps.setString(1, invoiceId);
	    		ResultSet rs = ps.executeQuery();
	    		if(rs.next()) {
	    			invoice = new Invoice();
	    			invoice.setInvoiceId(rs.getString("invoice_id"));
	    			invoice.setBookingId(rs.getString("booking_id"));
	    			invoice.setBasePrice(rs.getDouble("base_price"));
	    			invoice.setDistance(rs.getDouble("distance"));
	    			invoice.setCostPerKm(rs.getDouble("cost_per_km"));
	    			invoice.setTax(rs.getDouble("tax"));
	    			invoice.setDriverCost(rs.getDouble("driver_cost"));
	    			invoice.setTotalAmount(rs.getDouble("total_amount"));
	    		}	
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    	
	    	return invoice;
	    }
	
}
