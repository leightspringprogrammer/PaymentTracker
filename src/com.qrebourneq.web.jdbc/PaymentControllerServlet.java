package com.qrebourneq.web.jdbc;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class PaymentControllerServlet
 */
@WebServlet("/PaymentControllerServlet")
public class PaymentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PaymentDbUtil paymentDbUtil;
	
	@Resource(name="jdbc/web_payment_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		
		// create our payment db util... and pass in the conn pool / datasource
		try {
			paymentDbUtil = new PaymentDbUtil(dataSource);
		}
		catch(Exception e){
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing payments
			if(theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch(theCommand) {
			case "LIST":
				listPayments(request,response);
				break;
				
			case "ADD":
				addPayment(request,response);
				break;
			
			case "LOAD":
				loadPayment(request,response);
				break;
				
			case "UPDATE":
				updatePayment(request,response);
				break;
			
			case "DELETE":
				deletePayment(request,response);
				break;
			
			default:
				listPayments(request,response);
			}
	}
		catch (Exception e) {
			throw new ServletException(e);
			}
		}

	private void deletePayment(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		
		// read payment if from form data
		String thePaymentId = request.getParameter("paymentId");
		
		// delete payment from database
		paymentDbUtil.deletePayment(thePaymentId);
		
		// send them back to "list payments" page
		listPayments(request,response);
		
		
	}

	private void updatePayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read payment info from form data
		int id = Integer.parseInt(request.getParameter("paymentId"));
		String companyName = request.getParameter("companyName");
		String companyNumber = request.getParameter("companyNumber");
		int amount = Integer.parseInt(request.getParameter("paymentAmount"));
		//need to convert a Date obj to LocalDate
		Date  date1 = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("paymentDueDate"));
		LocalDate dueDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		Date  date2 = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("datePaymentMade"));
		LocalDate paymentDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		Boolean lateFee = false, paymentMade = false;
		
		if(request.getParameter("lateFee") != null){
			lateFee = true;
		}
		if(request.getParameter("paymentMade") != null){
			paymentMade = true;
		}
		
		// create a new payment object
		Payment thePayment = new Payment(id,
				companyName,
				companyNumber,
				amount,
				dueDate,
				paymentDate,
				lateFee,
				paymentMade);
		
		// perform update on database
		paymentDbUtil.updatePayment(thePayment);
		// send them back to the "list payments" page
		listPayments(request,response);
	}

	private void loadPayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read payment id from form data
		String thePaymentId = request.getParameter("paymentId");
		
		// get payment from database (db util)
		Payment thePayment = paymentDbUtil.getPayment(thePaymentId);
		// place payment in the request attribute
		request.setAttribute("THE_PAYMENT", thePayment);
		// send to jsp page: update-payment-form.jsp
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/update-payment-form.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addPayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read payment info from form data
		String companyName = request.getParameter("companyName");
		String companyNumber = request.getParameter("companyNumber");
		int amount = Integer.parseInt(request.getParameter("paymentAmount"));
		
		
		String DueDateInput = request.getParameter("paymentDueDate");
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" ) ;
		LocalDate dueDate = LocalDate.parse( DueDateInput , f ) ;

		String PayDateInput = request.getParameter("datePaymentMade");
		LocalDate paymentDate = LocalDate.parse( PayDateInput , f ) ;
				
		Boolean lateFee = false, paymentMade = false;
		
		if(request.getParameter("lateFee") != null){
			lateFee = true;
		}
		if(request.getParameter("paymentMade") != null){
			paymentMade = true;
		}
		
		
		// create a new payment object
		Payment thePayment = new Payment(companyName,companyNumber,amount,dueDate,paymentDate,lateFee,paymentMade);
		
		// add the payment to the database
		paymentDbUtil.addPayment(thePayment);
		
		// send back to main page(the payment list)
		listPayments(request,response);
		
	}


	private void listPayments(HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		
		// get payments from db util
		List<Payment> payments = paymentDbUtil.getPayments();
		
		// add payments to the request
		request.setAttribute("PAYMENT_LIST",payments);
		
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-payments.jsp");
		dispatcher.forward(request, response);
	}

}
