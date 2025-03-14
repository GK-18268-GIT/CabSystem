package com.system.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.system.model.Cab;


public class CabDao {	
	
    public void addCab(Cab cab) throws Exception {
        String query = "INSERT INTO cab (cab_code, driver_code, cab_number, model, brand, capacity, base_price, cost_per_km, "
        		+ "status, cab_image, created_at) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        	
        	ps.setString(1, cab.getCabCode());
        	ps.setString(2, cab.getDriverCode());
        	ps.setString(3, cab.getCabNumber());
        	ps.setString(4, cab.getModel());
        	ps.setString(5, cab.getBrand());
        	ps.setInt(6, cab.getCapacity());
        	ps.setDouble(7, cab.getBasePrice());
        	ps.setDouble(8, cab.getCostPerKm());
        	ps.setString(9, cab.getStatus());
        	ps.setString(10, cab.getCabImage());
        	ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
        	ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cab> getAllCabs() throws Exception {
        List<Cab> cabs = new ArrayList<>();
        String query = "SELECT * FROM cab ORDER BY cab_code DESC";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
            ps = conn.prepareStatement(query);
        	ResultSet rs = ps.executeQuery(query);
        	while (rs.next()) {
	            int id = rs.getInt("cab_id");
	            String cabCode = rs.getString("cab_code");
	            String cabNumber = rs.getString("cab_number");
	            String model = rs.getString("model");
	            String brand = rs.getString("brand");
	            int capacity = rs.getInt("capacity");
	            double basePrice = rs.getDouble("base_price");
	            double costPerKm = rs.getDouble("cost_per_km");
	            String status = rs.getString("status");
	            String cabImage = rs.getString("cab_image");
	            Cab cab = new Cab(id, cabCode, cabNumber, model, brand, capacity, basePrice, costPerKm, status, cabImage);
	            cabs.add(cab);
	        }   
	    } catch(SQLException e) {
	    	e.printStackTrace();
	    }
        	return cabs;
 }
    
    public List<Cab> searchCabByCabCode(String searchQuery) throws Exception {
        List<Cab> cabs = new ArrayList<>();
        String query = "SELECT * FROM cab WHERE cab_code LIKE ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
            ps.setString(1, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cab cab = new Cab();
                cab.setCabCode(rs.getString("cab_code"));
                cab.setCabNumber(rs.getString("cab_number"));
                cab.setModel(rs.getString("model"));
                cab.setBrand(rs.getString("brand"));
                cab.setCapacity(rs.getInt("capacity"));
                cab.setBasePrice(rs.getDouble("base_price"));
                cab.setCostPerKm(rs.getDouble("cost_per_km"));
                cab.setStatus(rs.getString("status"));
                cab.setCabImage(rs.getString("cab_image"));
                cabs.add(cab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cabs;
    }

    public Cab getCabById(int id) throws Exception {
        Cab cab = null;
        String query = "SELECT * FROM cab WHERE cab_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String cabCode = rs.getString("cab_code");
	            String cabNumber = rs.getString("cab_number");
	            String model = rs.getString("model");
	            String brand = rs.getString("brand");
	            int capacity = rs.getInt("capacity");
	            double basePrice = rs.getDouble("base_price");
	            double costPerKm = rs.getDouble("cost_per_km");
	            String status = rs.getString("status");
	            String cabImage = rs.getString("cab_image");
	            cab = new Cab(id, cabCode, cabNumber, model, brand, capacity, basePrice, costPerKm, status, cabImage);
	        }    
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	        return cab;
}
    
    public Cab getCabByCabCode(String cabCode) throws Exception {
    	Cab cab = null;
    	String query = "SELECT * FROM cab WHERE cab_code = ?";
    	
    	Connection conn = null;
        PreparedStatement ps = null;
    	
    	try{
    		conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
    		ps.setString(1, cabCode);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			cab = new Cab();
    			cab.setCabCode(rs.getString("cab_code"));
    			cab.setCabNumber(rs.getString("cab_number"));
    			cab.setModel(rs.getString("model"));
    			cab.setBrand(rs.getString("brand"));
    			cab.setCapacity(rs.getInt("capacity"));
    			cab.setBasePrice(rs.getDouble("base_price"));
    			cab.setCostPerKm(rs.getDouble("cost_per_km"));
    			cab.setCabImage(rs.getString("cab_image"));
    		}	
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return cab;
    }
    
    public void updateCab(Cab cab) throws Exception {
        String query = "UPDATE cab SET cab_code = ?, driver_code = ?, cab_number = ?, model = ?, brand = ?, capacity = ?, base_price = ?, cost_per_km = ?,"
        		+ " status = ?, cab_image = ? WHERE cab_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
        		
        	ps.setString(1, cab.getCabCode());
        	ps.setString(2, cab.getDriverCode());
        	ps.setString(3, cab.getCabNumber());
        	ps.setString(4, cab.getModel());
        	ps.setString(5, cab.getBrand());
        	ps.setInt(6, cab.getCapacity());
        	ps.setDouble(7, cab.getBasePrice());
        	ps.setDouble(8, cab.getCostPerKm());
        	ps.setString(9, cab.getStatus());
        	ps.setString(10, cab.getCabImage());
        	ps.setInt(11, cab.getCabId());
        	ps.executeUpdate();
    } catch(SQLException e) {
    	e.printStackTrace();
    }
 }

    public void deleteCab(int cabId) throws Exception {
        String query = "DELETE FROM cab WHERE cab_id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {	
        conn = DBConnectionFactory.getConnection();
        ps = conn.prepareStatement(query);
        	
        ps.setInt(1, cabId);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows deleted: " + rowsAffected);
    } catch(SQLException e) {
    	e.printStackTrace();
    }
 }
    
    public List<Cab> getAvailableCabs() throws Exception {
        List<Cab> cabList = new ArrayList<>();
        String query = "SELECT cab_code, cab_number, model, brand, capacity, base_price, cost_per_km, "
        		+ "cab_image, status FROM cab WHERE status = 'available'";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
        	conn = DBConnectionFactory.getConnection();
        	ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
         
            System.out.println("[DEBUG] Executing query: " + query); 
            while (rs.next()) {
                Cab cab = new Cab();
                cab.setCabCode(rs.getString("cab_code"));
                cab.setCabNumber(rs.getString("cab_number"));
                cab.setModel(rs.getString("model"));
                cab.setBrand(rs.getString("brand"));
                cab.setCapacity(rs.getInt("capacity"));
                cab.setBasePrice(rs.getDouble("base_price"));
                cab.setCostPerKm(rs.getDouble("cost_per_km"));
                cab.setCabImage(rs.getString("cab_image"));
                cab.setStatus(rs.getString("status"));
                cabList.add(cab);
                System.out.println("[DEBUG] Found cab: " + cab.getCabCode()); 
            }
        } catch (Exception e) {
            System.out.println("[ERROR] in getAvailableCabs(): " + e.getMessage()); 
            e.printStackTrace();
        }
        System.out.println("[DEBUG] Total available cabs: " + cabList.size()); 
        return cabList;
    }

    public String generateNewCabCode() throws Exception {
        String newCabCode = "CAB-1000"; 
        
        Connection conn = null;
        PreparedStatement ps = null;

        try {
              conn = DBConnectionFactory.getConnection();
              ps = conn.prepareStatement("SELECT cab_code FROM cab ORDER BY cab_id DESC LIMIT 1");
              ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String lastCabCode = resultSet.getString("cab_code");
                System.out.println("Last cab_code from DB: " + lastCabCode); 
                
                if(lastCabCode == null || lastCabCode.isEmpty()) {
                	System.out.println("Last Cab Code is Null!");
                	return newCabCode;
                }
                
                if(lastCabCode.matches("CAB-\\d+")) {
                	int oldNumber = Integer.parseInt(lastCabCode.substring(4));
                	newCabCode = "CAB-" + (oldNumber + 1);
                } else {
                	System.out.println("Invalid cab_code format: " + lastCabCode);
                }
            } else {
            	System.out.println("No records found in database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error!" + e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Error: Invalid cab code format in database." + e.getMessage());
        }

        System.out.println("New generated cab_code: " + newCabCode);
        return newCabCode;
    }
    
}
