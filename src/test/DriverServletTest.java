import com.system.controller.DriverServlet;
import com.system.db.DriverDao;
import com.system.model.Driver;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;

public class DriverServletTest {
	private DriverServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpsession;
    private DriverDao driverDao;
    
    @BeforeEach
    void setUp() {
        servlet = new DriverServlet();
        driverDao = mock(DriverDao.class);
        
        servlet.setDriverDao(driverDao); 

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        httpsession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpsession);
    }

	@Test
	void addDriver() throws Exception {
		when(driverDao.generateNewDriverCode()).thenReturn("Drv-1000");
		doNothing().when(driverDao).addDriver(any(Driver.class));
		
		when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("driverName")).thenReturn("John Doe");
        when(request.getParameter("driverAge")).thenReturn("28");
        when(request.getParameter("driverPhone")).thenReturn("0789632541");
        when(request.getParameter("driverEmail")).thenReturn("jDoe@gamil.com");
        when(request.getParameter("driverAddress")).thenReturn("123/C, Main Street, Kandy");
        when(request.getParameter("driverNIC")).thenReturn("978511236v");
        when(request.getParameter("status")).thenReturn("available");
        
        servlet.doPost(request, response);
        
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/drivers/addDriver.jsp")).thenReturn(dispatcher);
	}
	
	@Test
	void updateDriver() throws Exception {
		when(driverDao.generateNewDriverCode()).thenReturn("Drv-1000");
		doNothing().when(driverDao).addDriver(any(Driver.class));
		
		when(request.getParameter("action")).thenReturn("add");
		when(request.getParameter("driverName")).thenReturn("John Doe");
		when(request.getParameter("driverAge")).thenReturn("28");
		when(request.getParameter("driverPhone")).thenReturn("0782637541");
		when(request.getParameter("driverEmail")).thenReturn("jDoe@gamil.com");
		when(request.getParameter("driverAddress")).thenReturn("123/C, Main Street, Kandy");
		when(request.getParameter("driverNIC")).thenReturn("978511236v");
		when(request.getParameter("status")).thenReturn("available");
		
		servlet.doPost(request, response);
		
		verify(response).sendRedirect("DriverServlet?action=list");
	}
	
    @Test
    void deleteCab() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("driverId")).thenReturn("1");

        servlet.doGet(request, response);

        verify(driverDao).deleteDriver(1);
        verify(response).sendRedirect("DriverServlet?action=list");
    }


}
