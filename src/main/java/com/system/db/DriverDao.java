package com.system.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.system.model.Driver;

public class DriverDao {
		
		public String generateNewDriverCode() throws Exception {
	        String newDriverCode = "Drv-1000"; 
	        
	        Connection conn = null;
	        PreparedStatement ps = null;

	        try {
	        
	        	 conn = DBConnectionFactory.getConnection();
	              ps = conn.prepareStatement("SELECT driver_code FROM drivers ORDER BY driver_id DESC LIMIT 1");
	             ResultSet resultSet = ps.executeQuery();

	            if (resultSet.next()) {
	                String lastDriverCode = resultSet.getString("driver_code");
	                System.out.println("Last driver_code from DB: " + lastDriverCode); 
	                
	                if(lastDriverCode == null || lastDriverCode.isEmpty()) {
	                	System.out.println("Last Driver Code is Null!");
	                	return newDriverCode;
	                }
	                
	                if(lastDriverCode.matches("Drv-\\d+")) {
	                	int oldNumber = Integer.parseInt(lastDriverCode.substring(4));
	                	newDriverCode = "Drv-" + (oldNumber + 1);
	                } else {
	                	System.out.println("Invalid driver_code format: " + lastDriverCode);
	                }
	            } else {
	            	System.out.println("No records found in database!");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error!" + e.getMessage());
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            System.out.println("Error: Invalid driver code format in database." + e.getMessage());
	        }

	        System.out.println("New generated driver_code: " + newDriverCode);
	        return newDriverCode;
	    }

	
		public void addDriver(Driver driver) throws Exception {
		    String query = "INSERT INTO drivers (driver_code, driver_name, driver_age, driver_phone, driver_email, "
		        + "driver_address, driver_nic, status, created_at) "
		        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    
		    Connection conn = null;
	        PreparedStatement ps = null;
		    
		    try {
		    	conn = DBConnectionFactory.getConnection();
	        	ps = conn.prepareStatement(query);

		        ps.setString(1, driver.getDriverCode());
		        ps.setString(2, driver.getDriverName());
		        ps.setInt(3, driver.getDriverAge());
		        ps.setString(4, driver.getDriverPhone());
		        ps.setString(5, driver.getDriverEmail());
		        ps.setString(6, driver.getDriverAddress());
		        ps.setString(7, driver.getDriverNIC());
		        ps.setString(8, driver.getStatus());
		        ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
		        
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
    
    public List<Driver> getAllDrivers() throws Exception {
        List<Driver> cabs = new ArrayList<>();
        String query = "SELECT * FROM drivers ORDER BY driver_code DESC";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	ResultSet rs = ps.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("driver_id");
            String driverCode = rs.getString("driver_code");
            String driverName = rs.getString("driver_name");
            int driverAge = rs.getInt("driver_age");
            String driverPhone = rs.getString("driver_phone");
            String driverEmail = rs.getString("driver_email");
            String driverAddress = rs.getString("driver_address");
            String driverNIC = rs.getString("driver_nic");
            String status = rs.getString("status");
            Driver driver = new Driver(id, driverCode, driverName, driverAge, driverPhone, driverEmail, driverAddress, driverNIC, status);
            cabs.add(driver);
        }   
    } catch(SQLException e) {
    	e.printStackTrace();
    }
        return cabs;
 }
    
    public List<Driver> searchDriverByDriverCode(String searchQuery) throws Exception {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers WHERE driver_code LIKE ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	
            ps.setString(1, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setDriverCode(rs.getString("driver_code"));
                driver.setDriverName(rs.getString("driver_name"));
                driver.setDriverAge(rs.getInt("driver_age"));
                driver.setDriverPhone(rs.getString("driver_phone"));
                driver.setDriverEmail(rs.getString("driver_email"));
                driver.setDriverAddress(rs.getString("driver_address"));
                driver.setDriverNIC(rs.getString("driver_nic"));
                driver.setStatus(rs.getString("status"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public Driver getDriverById(int id) throws Exception {
        Driver driver = null;
        String query = "SELECT * FROM drivers WHERE driver_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try{
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	ps.setInt(1, id);
        	ResultSet rs = ps.executeQuery();
        	if (rs.next()) {
        	String driverCode = rs.getString("driver_code");
            String driverName = rs.getString("driver_name");
            int driverAge = rs.getInt("driver_age");
            String driverPhone = rs.getString("driver_phone");
            String driverEmail = rs.getString("driver_email");
            String driverAddress = rs.getString("driver_address");
            String driverNIC = rs.getString("driver_nic");
            String status = rs.getString("status");
            driver = new Driver(id, driverCode, driverName, driverAge, driverPhone, driverEmail, driverAddress, driverNIC, status);
        }    
    } catch(SQLException e) {
    	e.printStackTrace();
    }
        return driver;
    }
	
    public Driver getDriverByDriverCode(String driverCode) throws Exception {
    	Driver driver = null;
    	String query = "SELECT * FROM drivers WHERE driver_code = ?";
    	
    	Connection conn = null;
        PreparedStatement ps = null;
        
    	try{
    		conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
    		ps.setString(1, driverCode);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			driver = new Driver();
    			driver.setDriverCode(rs.getString("driver_code"));
    			driver.setDriverName(rs.getString("driver_name"));
    			driver.setDriverAge(rs.getInt("driver_age"));
    			driver.setDriverPhone(rs.getString("driver_phone"));
    			driver.setDriverEmail(rs.getString("driver_email"));
    			driver.setDriverAddress(rs.getString("driver_address"));
    			driver.setStatus(rs.getString("status"));
    		}	
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return driver;
    }
    
    public void updateDriver(Driver driver) throws Exception {
        String query = "UPDATE drivers SET driver_code = ?, driver_name = ?, driver_age = ?, driver_phone = ?, driver_email = ?,"
        		+ " driver_address = ?, driver_nic = ?, status = ? WHERE driver_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        		
        	ps.setString(1, driver.getDriverCode());
        	ps.setString(2, driver.getDriverName());
        	ps.setInt(3, driver.getDriverAge());
        	ps.setString(4, driver.getDriverPhone());
        	ps.setString(5, driver.getDriverEmail());
        	ps.setString(6, driver.getDriverAddress());
        	ps.setString(7, driver.getDriverNIC());
        	ps.setString(8, driver.getStatus());
        	ps.setInt(9, driver.getDriverId());
        	ps.executeUpdate();
    }catch(SQLException e) {
    	e.printStackTrace();
    }
 }

    public void deleteDriver(int driverId) throws Exception {
        String query = "DELETE FROM drivers WHERE driver_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	
        ps.setInt(1, driverId);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows deleted: " + rowsAffected);
    } catch(SQLException e) {
    	e.printStackTrace();
    }
 }
    
    public List<Driver> getAvailableDrivers() throws Exception {
        List<Driver> driverList = new ArrayList<>();
        String query = "SELECT * FROM drivers WHERE status = 'available'";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
         
            System.out.println("[DEBUG] Executing query: " + query); 
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setDriverCode(rs.getString("driver_code"));
                driver.setDriverName(rs.getString("driver_name"));
                driver.setDriverAge(rs.getInt("driver_age"));
                driver.setDriverPhone(rs.getString("driver_phone"));
                driver.setDriverEmail(rs.getString("driver_email"));
                driver.setDriverAddress(rs.getString("driver_address"));
                driver.setDriverNIC(rs.getString("driver_nic"));
                driver.setStatus(rs.getString("status"));
                driverList.add(driver);
                System.out.println("[DEBUG] Found cab: " + driver.getDriverCode()); 
            }
        } catch (Exception e) {
            System.out.println("[ERROR] in getAvailableCabs(): " + e.getMessage()); 
            e.printStackTrace();
        }
        System.out.println("[DEBUG] Total available cabs: " + driverList.size()); 
        return driverList;
    }    
}
