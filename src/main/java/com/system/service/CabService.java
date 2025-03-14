package com.system.service;

//import java.sql.SQLException;
import java.util.List;
import com.system.db.CabDao;
import com.system.model.Cab;

public class CabService {
    private static CabService instance;
    private CabDao cabDao;
    
    private CabService() {
        this.cabDao = new CabDao();
    }
    
    public static CabService getInstance() {
        if (instance == null) {
            synchronized (CabService.class) {
                if (instance == null) {
                    instance = new CabService();
                }
            }
        }
        return instance;	
    }
    
    // Create
    public void addCab(Cab cab) throws Exception {
        cabDao.addCab(cab);
    }
    
    // Read (Get all cabs)
    public List<Cab> getAllSavedCabs() throws Exception {
        return cabDao.getAllCabs();
    }
    
    // Read (Get cab by ID)
    public Cab getCabById(int cabId) throws Exception {
        return cabDao.getCabById(cabId);
    }
    
    // Update
    public void updateCab(Cab cab) throws Exception {
        cabDao.updateCab(cab);
    }
    
    // Delete
    public void deleteCab(int cabId) throws Exception {
        cabDao.deleteCab(cabId);
    }
    
    // Search by cab code
    public List<Cab> searchCabByCabCode(String searchQuery) throws Exception {
        return cabDao.searchCabByCabCode(searchQuery);
    }
}
