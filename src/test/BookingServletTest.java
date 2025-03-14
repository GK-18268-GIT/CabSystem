import com.system.controller.BookingServlet;
import com.system.db.BookingDao;
import com.system.model.Booking;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;


public class BookingServletTest {
	private BookingServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpsession;
    private BookingDao bookingDao;
    
    @BeforeEach
    void setUp() {
        servlet = new BookingServlet();
        bookingDao = mock(BookingDao.class);
        
        servlet.setBookingDao(bookingDao); 

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        httpsession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpsession);
    }
    
    @Test
    void addNewBooking_Insert() throws Exception {
    	when(bookingDao.generateNewBookingCode()).thenReturn("B-1000"); 
        when(bookingDao.addBooking(any(Booking.class))).thenReturn("B-1000");
        
        when(request.getParameter("action")).thenReturn("addBooking");
        when(request.getParameter("customername")).thenReturn("John Doe");
        when(request.getParameter("customeraddress")).thenReturn("123/A,Main Street,Kandy");
        when(request.getParameter("customerphone")).thenReturn("0771234567");
        when(request.getParameter("pickuplocation")).thenReturn("Kandy");
        when(request.getParameter("destination")).thenReturn("Colombo");
        when(request.getParameter("passengers")).thenReturn("4");
        when(request.getParameter("pickupdatetime")).thenReturn("2024-01-01T12:00");

        servlet.doPost(request, response);
        
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp")).thenReturn(dispatcher);

        verify(httpsession).setAttribute(eq("booking"), any(Booking.class));
        verify(response).sendRedirect(contains("/CabControllerServlet"));
    } 
    
    @Test
    void updateBooking_Update() throws Exception {
    	when(bookingDao.generateNewBookingCode()).thenReturn("B-1000"); 
        when(bookingDao.addBooking(any(Booking.class))).thenReturn("B-1000");
        
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("customername")).thenReturn("John Doe");
        when(request.getParameter("customeraddress")).thenReturn("123/A,Main Street,Kandy");
        when(request.getParameter("customerphone")).thenReturn("0771434577");
        when(request.getParameter("pickuplocation")).thenReturn("Kandy");
        when(request.getParameter("destination")).thenReturn("Kadawatha");
        when(request.getParameter("passengers")).thenReturn("4");
        when(request.getParameter("pickupdatetime")).thenReturn("2024-01-01T12:00");

        servlet.doPost(request, response);
        
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/bookings/booking.jsp")).thenReturn(dispatcher);

        verify(httpsession).setAttribute(eq("booking"), any(Booking.class));
        verify(response).sendRedirect(contains("/CabControllerServlet"));
    } 
    
    @Test
    void deleteBooking() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("bookingNumber")).thenReturn("1");

        servlet.doGet(request, response);

        verify(bookingDao).deleteBooking(1);
        verify(response).sendRedirect("BookingServlet?action=list");
    }
    
    
    
}
