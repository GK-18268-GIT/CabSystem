import com.system.controller.LoginServlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginServletTest {
	private LoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpsession;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
}

	@Test
	void adminLoginTest() throws Exception {
		when(request.getParameter("username")).themnReturn("admin");
		when(request.getParameter("password")).themnReturn("password");
		
		servlet.doPost(request, response);
		
		verify(httpsession).setAttribute("user", "admin");
		verify(httpsession).setAttribute("userRole", "admin");
		verify(request).getRgetRequestDispatcher("/WEB-INF/admin/dashboard.jsp");
		
	}

	@Test
	void userLoginTest() throws Exception {
		when(request.getParameter("username")).themnReturn("John");
		when(request.getParameter("password")).themnReturn("John123");
		when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
		
		servlet.doPost(request, response);
		
		verify(hhtpsession).setAttribute("user", "john");
		verify(request).getRgetRequestDispatcher("/WEB-INF/bookings/booking.jsp");
	}
	
	void userLoginTest() throws Exception {
		when(request.getParameter("username")).themnReturn("John");
		when(request.getParameter("password")).themnReturn("John456");
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);
		
		servlet.doPost(request, response);
		
		verify(hhtpsession).setAttribute("error", "Invalid username or password");
		verify(request).getRgetRequestDispatcher("/WEB-INF/user/login.jsp");
	}
