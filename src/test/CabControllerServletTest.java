import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.system.controller.CabControllerServlet;
import com.system.db.CabDao;
import com.system.model.Cab;

import jakarta.servlet.http.*;
import jakarta.servlet.http.Part;

public class CabControllerServletTest {
    private CabControllerServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CabDao cabDao;

    @BeforeEach
    void setUp() {
        servlet = new CabControllerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        cabDao = mock(CabDao.class);
        servlet.setCabDao(cabDao);
        
        servlet.setUploadPath(System.getProperty("java.io.tmpdir"));
        
    }

    @Test
    void addNewCab() throws Exception {
    	when(cabDao.generateNewCabCode()).thenReturn("CAB-1000"); 
    	doNothing().when(cabDao).addCab(any(Cab.class));
    	
        when(request.getParameter("action")).thenReturn("add");
        
        when(request.getParameter("cabNumber")).thenReturn("ABC-1234");
        when(request.getParameter("model")).thenReturn("Toyota");
        when(request.getParameter("brand")).thenReturn("Corolla");
        when(request.getParameter("capacity")).thenReturn("5");
        when(request.getParameter("basePrice")).thenReturn("2500");
        when(request.getParameter("costPerKm")).thenReturn("25");

        Part filePart = mock(Part.class);
        doReturn(filePart).when(request).getPart("cabImage"); 
        when(filePart.getSubmittedFileName()).thenReturn("test.jpg");

        doNothing().when(cabDao).addCab(any(Cab.class));

        servlet.doPost(request, response);

        verify(cabDao).addCab(any(Cab.class));
        verify(response).sendRedirect("CabControllerServlet?action=list");
    }
    
    @Test
    void updateCab() throws Exception {
    	when(cabDao.generateNewCabCode()).thenReturn("CAB-1000"); 
    	doNothing().when(cabDao).addCab(any(Cab.class));
    	
    	when(request.getParameter("action")).thenReturn("add");
    	
    	when(request.getParameter("cabNumber")).thenReturn("ABC-1234");
    	when(request.getParameter("model")).thenReturn("Toyota");
    	when(request.getParameter("brand")).thenReturn("Corolla");
    	when(request.getParameter("capacity")).thenReturn("5");
    	when(request.getParameter("basePrice")).thenReturn("2000");
    	when(request.getParameter("costPerKm")).thenReturn("20");
    	
    	Part filePart = mock(Part.class);
    	doReturn(filePart).when(request).getPart("cabImage"); 
    	when(filePart.getSubmittedFileName()).thenReturn("test.jpg");
    	
    	doNothing().when(cabDao).addCab(any(Cab.class));
    	
    	servlet.doPost(request, response);
    	
    	verify(cabDao).addCab(any(Cab.class));
    	verify(response).sendRedirect("CabControllerServlet?action=list");
    }
    
    @Test
    void deleteCab() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("cabId")).thenReturn("1");

        servlet.doGet(request, response);

        verify(cabDao).deleteCab(1);
        verify(response).sendRedirect("CabControllerServlet?action=list");
    }

}
