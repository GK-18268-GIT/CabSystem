package com.system.db;

import com.system.model.Booking;
import com.system.model.Location;
import com.system.model.SelectedCab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {

  //Add new Booking
    public String addBooking(Booking booking) throws Exception {
    	String query = "INSERT INTO booking (booking_id, customername, customeraddress, customerphone, pickuplocation, pickupdatetime, destination, passengers, created_at) "
    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	Connection conn = null;
    	PreparedStatement ps = null;

        String bookingId = null;
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	
            bookingId = generateNewBookingCode();
            ps.setString(1, bookingId);
            ps.setString(2, booking.getCustomerName());
            ps.setString(3, booking.getCustomerAddress());
            ps.setString(4, booking.getCustomerPhone());
            ps.setString(5, booking.getPickupLocation());
            ps.setTimestamp(6, booking.getPickupDateTime());
            ps.setString(7, booking.getDestination());
            ps.setInt(8, booking.getPassengers());
            ps.setTimestamp(9, booking.getCreatedAt());

            int i = ps.executeUpdate();

            if (i == 0) {
                bookingId = null; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingId;
    }
  //Generate Booking Id
    public String generateNewBookingCode() throws Exception {
        String newBookingCode = "B-1000";
        
        Connection conn = null;
        PreparedStatement ps = null;

        try { 
        	  conn   = DBConnectionFactory.getConnection(); 
              ps = conn.prepareStatement("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1");
             ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String lastBookingCode = resultSet.getString("booking_id");
                System.out.println("Last Booking_Id from DB: " + lastBookingCode);

                if (lastBookingCode != null && lastBookingCode.matches("B-\\d+")) {
                    int oldNumber = Integer.parseInt(lastBookingCode.substring(2));
                    newBookingCode = "B-" + (oldNumber + 1);
                } else {
                    System.out.println("[DEBUG] Invalid or missing Booking_Id, using default: " + newBookingCode);
                }
            } else {
                System.out.println("[DEBUG] No previous records found. Using default Booking ID: " + newBookingCode);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("[DEBUG] Error generating new Booking ID: " + e.getMessage());
        }

        System.out.println("[DEBUG] Generated Booking ID: " + newBookingCode);
        return newBookingCode;
    }
    //Save select cab data
    public boolean saveSelectedCabData(SelectedCab selectedCab, String bookingId) throws Exception {
        String query = "INSERT INTO selectcab (booking_id, cab_code, cab_number, model, brand, capacity, base_price, cost_per_km, cab_image, created_at) " 
                       	  +	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" 
                          + "ON DUPLICATE KEY UPDATE " 
                          + "cab_code = VALUES(cab_code), " 
                          + "cab_number = VALUES(cab_number), " 
                          + "model = VALUES(model), " 
                       	  +	"brand = VALUES(brand), " 
                       	  +	"capacity = VALUES(capacity), " 
                       	  +	"base_price = VALUES(base_price), " 
                       	  +	"cost_per_km = VALUES(cost_per_km), " 
                       	  +	"cab_image = VALUES(cab_image), " 
                       	  +	"created_at = VALUES(created_at)";
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	
        	ps.setString(1, bookingId);
            ps.setString(2, selectedCab.getCabCode());
            ps.setString(3, selectedCab.getCabNumber());
            ps.setString(4, selectedCab.getModel());
            ps.setString(5, selectedCab.getBrand());
            ps.setInt(6, selectedCab.getCapacity());
            ps.setDouble(7, selectedCab.getBasePrice());
            ps.setDouble(8, selectedCab.getCostPerKm());
            ps.setString(9, selectedCab.getCabImage());
            ps.setTimestamp(10, selectedCab.getCreatedAt());

            int i = ps.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[DEBUG] Select cab data not saved!");
            return false;
        }
    }
    
    //Update Booking Data
    public boolean updateBookingData(Booking booking) throws Exception {
        String query = "UPDATE booking SET customername = ?, customeraddress = ?, customerphone = ?, pickuplocation = ?, "
        		+ "pickupdatetime = ?, destination = ?, passengers = ?  WHERE booking_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        		
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	
            ps.setString(1, booking.getCustomerName());
            ps.setString(2, booking.getCustomerAddress());
            ps.setString(3, booking.getCustomerPhone());
            ps.setString(4, booking.getPickupLocation());
            ps.setTimestamp(5, booking.getPickupDateTime());
            ps.setString(6, booking.getDestination());
            ps.setInt(7, booking.getPassengers());
            ps.setString(8, booking.getBookingId());

            int i = ps.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[DEBUG] Booking Data not updated!");
            return false;
        }
    }
    
    public Location getLocationDetails(String bookingId) throws Exception {
    	Location location = null;
    	String query = "SELECT pickuplocation, destination FROM booking WHERE booking_id = ?";
    	
    	Connection conn = null;
    	PreparedStatement ps = null;
    	
    	try {
    		conn = DBConnectionFactory.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setString(1, bookingId);
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			location = new Location();
    			location.setPickupLocation(rs.getString("pickuplocation"));
    			location.setDestination(rs.getString("destination"));
    		}
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return location;	
    }
    
    public List<Booking> getAllBooking() throws Exception {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking ORDER BY booking_id DESC";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
            ps = conn.prepareStatement(query);
        	ResultSet rs = ps.executeQuery(query);
        	while (rs.next()) {
	            int bookingNumber = rs.getInt("booking_number");
	            String bookingId = rs.getString("booking_id");
	            String customerName = rs.getString("customername");
	            String customerAddress = rs.getString("customeraddress");
	            String customerPhone = rs.getString("customerphone");
	            String pickupLocation = rs.getString("pickuplocation");
	            Timestamp pickupDateTime = rs.getTimestamp("pickupdatetime");
	            String destination = rs.getString("destination");
	            int passengers = rs.getInt("passengers");
	            Timestamp createdAt = rs.getTimestamp("created_at");
	            Booking booking = new Booking(bookingNumber, bookingId, customerName, customerAddress, customerPhone, pickupLocation, pickupDateTime, destination, passengers, createdAt);
	            bookings.add(booking);
	        }   
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    }
        	return bookings;
 }
    
    public List<Booking> searchBookingByBookingId(String searchQuery) throws Exception {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE booking_id LIKE ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
            ps.setString(1, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Booking booking = new Booking();
            	booking.setBookingId(rs.getString("booking_id"));
            	booking.setCustomerName(rs.getString("customername"));
            	booking.setCustomerAddress(rs.getString("customeraddress"));
            	booking.setCustomerPhone(rs.getString("customerphone"));
            	booking.setPickupLocation(rs.getString("pickuplocation"));
            	booking.setPickupDateTime(rs.getTimestamp("pickupdatetime"));
            	booking.setDestination(rs.getString("destination"));
            	booking.setPassengers(rs.getInt("passengers"));
            	bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public Booking getBookingByNumber(int bookingNumber) throws Exception {
    	Booking booking = null;
        String query = "SELECT * FROM booking WHERE booking_number = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
	        ps.setInt(1, bookingNumber);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String bookingId = rs.getString("booking_id");
	            String customerName = rs.getString("customername");
	            String customerAddress = rs.getString("customeraddress");
	            String customerPhone = rs.getString("customerphone");
	            String pickupLocation = rs.getString("pickuplocation");
	            Timestamp pickupDateTime = rs.getTimestamp("pickupdatetime");
	            String destination = rs.getString("destination");
	            int passengers = rs.getInt("passengers");
	            booking = new Booking(bookingNumber, bookingId, customerName, customerAddress, customerPhone, pickupLocation, pickupDateTime, destination, passengers, null);
	        }    
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	        return booking;
}
    
    public Booking getBookinByBookingId(String bookingId) throws Exception {
    	Booking booking = null;
    	String query = "SELECT * FROM booking WHERE booking_id = ?";
    	
    	Connection conn = null;
        PreparedStatement ps = null;
    	
    	try{
    		conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
    		ps.setString(1, bookingId);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			booking = new Booking();
    			booking.setBookingId(rs.getString("booking_id"));
            	booking.setCustomerName(rs.getString("customername"));
            	booking.setCustomerAddress(rs.getString("customeraddress"));
            	booking.setCustomerPhone(rs.getString("customerphone"));
            	booking.setPickupLocation(rs.getString("pickuplocation"));
            	booking.setPickupDateTime(rs.getTimestamp("pickupdatetime"));
            	booking.setDestination(rs.getString("destination"));
            	booking.setPassengers(rs.getInt("passengers"));
    		}	
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return booking;
    } 
    
    public void updateBooking(Booking booking) throws Exception {
        String query = "UPDATE booking SET customername = ?, customeraddress = ?, customerphone = ?, pickuplocation = ?, "
        		+ "pickupdatetime = ?, destination = ?, passengers = ? WHERE booking_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        		
        	ps.setString(1, booking.getCustomerName());
        	ps.setString(2, booking.getCustomerAddress());
        	ps.setString(3, booking.getCustomerPhone());
        	ps.setString(4, booking.getPickupLocation());
        	ps.setTimestamp(5, booking.getPickupDateTime());
        	ps.setString(6, booking.getDestination());
        	ps.setInt(7, booking.getPassengers());
        	ps.setString(8, booking.getBookingId());
        	ps.executeUpdate();
    } catch(SQLException e) {
    	e.printStackTrace();
    }
 }
    
    public void deleteBooking(int bookingNumber) throws Exception {
        String query = "DELETE FROM booking WHERE booking_number = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {	
        conn = DBConnectionFactory.getConnection();
        ps = conn.prepareStatement(query);
        	
        ps.setInt(1, bookingNumber);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows deleted: " + rowsAffected);
    } catch(SQLException e) {
    	e.printStackTrace();
    }
   }
    
   public List<SelectedCab> getSelectedCabByBookingId(String bookingId) throws Exception {
	   List<SelectedCab> selectedCabs = new ArrayList<>();
	   String query = "SELECT * FROM selectcab WHERE booking_id = ?";
	   
	   Connection conn = null;
	   PreparedStatement ps = null;
	   
	   try {
		   conn = DBConnectionFactory.getConnection();
		   ps = conn.prepareStatement(query);
		   ps.setString(1, bookingId);
		   ResultSet rs = ps.executeQuery();
		   
		   while(rs.next()) {
			   SelectedCab selectedCab = new SelectedCab();
			   selectedCab.setCabCode(rs.getString("cab_code"));
			   selectedCab.setCabNumber(rs.getString("cab_number"));
			   selectedCab.setModel(rs.getString("model"));
			   selectedCab.setBrand(rs.getString("brand"));
			   selectedCab.setCapacity(rs.getInt("capacity"));
			   selectedCab.setBasePrice(rs.getDouble("base_price"));
			   selectedCab.setCostPerKm(rs.getDouble("cost_per_km"));
			   selectedCab.setCabImage(rs.getString("cab_image"));
			   selectedCabs.add(selectedCab);
		   }
		   
	   } catch(SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return selectedCabs;
   }
   
   public List<SelectedCab> searchSelectedCabByBookingId(String searchQuery) throws Exception {
	   List<SelectedCab> selectedCabs = new ArrayList<>();
	   String query = "SELECT * FROM selectcab WHERE booking_id = ?";
	   
	   Connection conn = null;
	   PreparedStatement ps = null;
	   
	   try {
		   conn = DBConnectionFactory.getConnection();
		   ps = conn.prepareStatement(query);
		   ps.setString(1, "%" + searchQuery + "%");
		   ResultSet rs = ps.executeQuery();
		   
		   while(rs.next()) {
			   SelectedCab selectedCab = new SelectedCab();
			   selectedCab.setCabCode(rs.getString("cab_code"));
			   selectedCab.setCabNumber(rs.getString("cab_number"));
			   selectedCab.setModel(rs.getString("model"));
			   selectedCab.setBrand(rs.getString("brand"));
			   selectedCab.setCapacity(rs.getInt("capacity"));
			   selectedCab.setBasePrice(rs.getDouble("base_price"));
			   selectedCab.setCostPerKm(rs.getDouble("cost_per_km"));
			   selectedCab.setCabImage(rs.getString("cab_image"));
			   selectedCabs.add(selectedCab);
		   }
		   
	   } catch(SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return selectedCabs;
   }
   
   public List<SelectedCab> getAllSelectedCabs() throws Exception {
	   List<SelectedCab> selectedCabs = new ArrayList<>();
	   String query = "SELECT * FROM selectcab";
	   
	   Connection conn = null;
	   PreparedStatement ps = null;
	   
	   try {
		   conn = DBConnectionFactory.getConnection();
		   ps = conn.prepareStatement(query);
		   ResultSet rs = ps.executeQuery();
		   
		   while(rs.next()) {
			   SelectedCab selectedCab = new SelectedCab();
			   selectedCab.setBookingId(rs.getString("booking_id"));
			   selectedCab.setCabCode(rs.getString("cab_code"));
			   selectedCab.setCabNumber(rs.getString("cab_number"));
			   selectedCab.setModel(rs.getString("model"));
			   selectedCab.setBrand(rs.getString("brand"));
			   selectedCab.setCapacity(rs.getInt("capacity"));
			   selectedCab.setBasePrice(rs.getDouble("base_price"));
			   selectedCab.setCostPerKm(rs.getDouble("cost_per_km"));
			   selectedCab.setCabImage(rs.getString("cab_image"));
			   selectedCabs.add(selectedCab);
		   }
		   
	   } catch(SQLException e) {
		   e.printStackTrace();
	   }
	return selectedCabs;
	   
   }
   
   public void updateSelectedCab(SelectedCab selectedCab) throws Exception {
       String query = "UPDATE selectecab SET cab_number = ?, model = ?, brand = ?, capacity = ?, base_price = ?, cost_per_km = ?,"
       		+ " status = ?, cab_image = ? WHERE cab_code = ?";
       
       Connection conn = null;
       PreparedStatement ps = null;
       
       try {
       	conn = DBConnectionFactory.getConnection();
       	ps = conn.prepareStatement(query);
       		
       	ps.setString(1, selectedCab.getCabNumber());
       	ps.setString(2, selectedCab.getModel());
       	ps.setString(3, selectedCab.getBrand());
       	ps.setInt(4, selectedCab.getCapacity());
       	ps.setDouble(5, selectedCab.getBasePrice());
       	ps.setDouble(6, selectedCab.getCostPerKm());
       	ps.setString(7, selectedCab.getCabImage());
       	ps.setString(8, selectedCab.getCabCode());
       	ps.executeUpdate();
   } catch(SQLException e) {
   	e.printStackTrace();
   }
}

   public void deleteSelectedCab(String cabCode) throws Exception {
       String query = "DELETE FROM selectcab WHERE cab_code = ?";
       
       Connection conn = null;
       PreparedStatement ps = null;
       
       try {	
       conn = DBConnectionFactory.getConnection();
       ps = conn.prepareStatement(query);
       	
       ps.setString(1, cabCode);
       int rowsAffected = ps.executeUpdate();
       System.out.println("Rows deleted: " + rowsAffected);
   } catch(SQLException e) {
   	e.printStackTrace();
   }
}

   
}
