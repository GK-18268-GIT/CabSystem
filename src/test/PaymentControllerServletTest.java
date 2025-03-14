import com.system.controller.PaymentControllerServlet;
import com.system.db.PaymentDao;
import com.system.model.Booking;
import com.system.model.Invoice;
import com.system.model.Payment;
import com.system.model.SelectedCab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;



public class PaymentControllerServletTest {
	private PaymentControllerServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpsession;
    private PaymentDao paymentDao;
    
    @BeforeEach
    void setUp() {
        servlet = new PaymentControllerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        httpsession = mock(HttpSession.class);
        paymentDao = mock(PaymentDao.class);

        servlet.setPaymentDao(paymentDao); 
        
        when(request.getSession()).thenReturn(httpsession);
    }
    
    @Test
    void calculateBill() throws Exception {

        Booking booking = new Booking("B-1000");
        when(httpsession.getAttribute("booking")).thenReturn(booking);
        
        SelectedCab cab = new SelectedCab("CAB-1000", 50.0, 5.0);
        when(paymentDao.getSelectedCabData("B-1000")).thenReturn(cab);
        when(paymentDao.saveInvoiceData(any(Invoice.class))).thenReturn(true);

        when(request.getParameter("action")).thenReturn("calculate");
        when(request.getParameter("distance")).thenReturn("10.5");

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/payments/calculateBill.jsp"))
            .thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(dispatcher).forward(request, response);
    }   
    
    @Test
    void paymentProcess_Card() throws Exception {

        Booking booking = new Booking("B-1000");
        Invoice invoice = new Invoice("INV-1000", 200.0);
        when(httpsession.getAttribute("booking")).thenReturn(booking);
        when(httpsession.getAttribute("invoice")).thenReturn(invoice);
        when(request.getSession()).thenReturn(httpsession);

        when(request.getParameter("action")).thenReturn("paymentProcess");
        when(request.getParameter("paymentMethod")).thenReturn("CARD");
        when(request.getParameter("totalAmount")).thenReturn("200.0");
        when(request.getParameter("cardNumber")).thenReturn("4111-1111-1111-1111");
        when(request.getParameter("cardHolder")).thenReturn("John Doe");
        when(request.getParameter("expiry")).thenReturn("12/25");
        when(request.getParameter("cvv")).thenReturn("123");

        when(paymentDao.savePaymentData(any(Payment.class))).thenReturn(true);
        
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/payments/confirmPayment.jsp"))
            .thenReturn(dispatcher);


        servlet.doPost(request, response);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentDao).savePaymentData(paymentCaptor.capture());
        assertEquals("COMPLETED", paymentCaptor.getValue().getStatus());
    }
    
    @Test
    void paymentProcess_Cash() throws Exception {
    	
    	Booking booking = new Booking("B-1000");
    	Invoice invoice = new Invoice("INV-1000", 200.0);
    	when(httpsession.getAttribute("booking")).thenReturn(booking);
    	when(httpsession.getAttribute("invoice")).thenReturn(invoice);
    	when(request.getSession()).thenReturn(httpsession);
    	
    	when(request.getParameter("action")).thenReturn("paymentProcess");
    	when(request.getParameter("totalAmount")).thenReturn("200.0");
    	when(request.getParameter("paymentMethod")).thenReturn("CASH");
    	
    	when(paymentDao.savePaymentData(any(Payment.class))).thenReturn(true);
    	
    	RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    	when(request.getRequestDispatcher("/WEB-INF/payments/confirmPayment.jsp"))
    	.thenReturn(dispatcher);
    	
    	
    	servlet.doPost(request, response);
    	
    	ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
    	verify(paymentDao).savePaymentData(paymentCaptor.capture());
    	assertEquals("PENDING", paymentCaptor.getValue().getStatus());
    }
}


