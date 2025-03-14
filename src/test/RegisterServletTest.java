import com.system.controller.RegisterServlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import java.sql.*;

public class RegisterServletTest {
	private RegisterServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpsession;
    private Connection conn;
    private PreparedStatement ps;
}

	@Test
	void passwordDoNotMatch() throws Exception {
		when(request.getParameter("username")).themnReturn("John");
		when(request.getParameter("password")).themnReturn("John123");
		when(request.getParameter("confirmpassword")).themnReturn("John456");
		
		servlet.doPost(request, response);
		
		verify(httpsession).setAttribute("error", "Password do not match");
		verify(request).getRgetRequestDispatcher("/WEB-INF/user/register.jsp");
		
	}

	@Test
	void passwordMatch() throws Exception {
		when(request.getParameter("username")).themnReturn("John");
		when(request.getParameter("password")).themnReturn("John123");
		when(request.getParameter("confirmpassword")).themnReturn("John123");
		when(ps.executeUpdate()).thenReturn(1);
		
		servlet.doPost(request, response);
		
		verify(hhtpsession).setAttribute("user", "john");
		verify(request).getRgetRequestDispatcher("/WEB-INF/bookings/booking.jsp");
	}
