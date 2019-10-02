package com.qrebourneq.web.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class PaymentDbUtil {
	
	private DataSource dataSource;
	
	public PaymentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Payment> getPayments() throws Exception{
		
		List<Payment> payments = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
		// get a connection
			myConn = dataSource.getConnection();
		
		// create sql statement
		String sql = "select * from payment order by amount";
		myStmt = myConn.createStatement();
		
		// execute query
		myRs = myStmt.executeQuery(sql);
		
		// process result set
		while(myRs.next()) {
			
			// retrieve data from result set row
			int id = myRs.getInt("id");
			String companyName = myRs.getString("company_name");
			String companyNumber = myRs.getString("phone_number");
			
			//Convert number into (###)###-#### format
			companyNumber = companyNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
			
			
			
			int amount = myRs.getInt("amount");
				
			java.util.Date  date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myRs.getString("due_date"));
			LocalDate dueDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			java.util.Date  date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myRs.getString("payment_date"));
			LocalDate datePaymentMade = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			
			
			Boolean lateFee = myRs.getBoolean("late_fee");
			Boolean paymentMade = myRs.getBoolean("payment_made");
			
					
			// create new payment object
			Payment tempPayment = new Payment(
										id,
										companyName,
										companyNumber,
										amount,
										dueDate,
										datePaymentMade,
										lateFee,
										paymentMade);
			
			// add it to the list of payments
			payments.add(tempPayment);
		}
		
			return payments;
		}
		finally {
			// close JDBC objects
			close(myConn,myStmt,myRs);
			
		}
		
		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		
		try {
			if(myRs != null) {
				myRs.close();
			}
			if(myStmt != null) {
				myStmt.close();
			}
			if(myConn != null) {
				myConn.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPayment(Payment thePayment) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
		// get db connection
			myConn = dataSource.getConnection();
		// create sql for insert
			String sql = "insert into payment "
						+ "(company_name, phone_number, amount, due_date, payment_date, late_fee, payment_made) "
						+ "values (?, ?, ?, ?, ?, ?, ?)";
			myStmt = myConn.prepareStatement(sql);
			// set the param values for the payment
			myStmt.setString(1, thePayment.getCompanyName());
			myStmt.setString(2, thePayment.getCompanyNumber());
			myStmt.setInt(3, thePayment.getPaymentAmount());
			myStmt.setDate(4, Date.valueOf(thePayment.getPaymentDueDate()));
			myStmt.setDate(5, Date.valueOf(thePayment.getDatePaymentMade()));
			myStmt.setBoolean(6, thePayment.getLateFee());
			myStmt.setBoolean(7, thePayment.getPaymentMade());
			
		// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Payment getPayment(String thePaymentId) throws Exception {
		
		Payment thePayment = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		int paymentId;
		
		try {
			// convert payment id to int
			paymentId = Integer.parseInt(thePaymentId);
			// get connection to databse
			myConn = dataSource.getConnection();
			// create sql to get selected payment
			String sql = "select * from payment where id=?";
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			// set params
			myStmt.setInt(1,paymentId);
			// execute statement
			myRs = myStmt.executeQuery();
			// retrieve data from result set row
			if(myRs.next()) {
			// convert from default string to constructor parameter types
				
				String companyName = myRs.getString("company_name");
				String companyNumber = myRs.getString("phone_number");
				int amount = Integer.parseInt(myRs.getString("amount"));
				
				java.util.Date  date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myRs.getString("due_date"));
				LocalDate dueDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				java.util.Date  date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myRs.getString("payment_date"));
				LocalDate paymentDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Boolean lateFee = false, paymentMade = false;
				
				if(myRs.getString("late_fee") != "0"){
					lateFee = true;
				}
				if(myRs.getString("payment_made") != "0"){
					paymentMade = true;
				}
				
				// use the paymentId during construction
				thePayment = new Payment(paymentId,companyName,companyNumber, amount, dueDate,paymentDate, lateFee, paymentMade);
			}
			else {
				throw new Exception("Could not find payment id: " + paymentId);
			}
			return thePayment;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
			
		}

	public void updatePayment(Payment thePayment) throws Exception {
		// 
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		// get db connection
		try {
		myConn = dataSource.getConnection();
		
		// create SQL update statement
		String sql = "update payment "
					+ "set company_name=?, phone_number=?, amount=?, due_date=?, payment_date=?, late_fee=?, payment_made=? "
					+ "where id =?";
		
		// prepare statement
		myStmt = myConn.prepareStatement(sql);
		
		// set params
		myStmt.setString(1, thePayment.getCompanyName());
		myStmt.setString(2, thePayment.getCompanyNumber());
		myStmt.setInt(3, thePayment.getPaymentAmount());
		myStmt.setDate(4, Date.valueOf(thePayment.getPaymentDueDate()));
		myStmt.setDate(5, Date.valueOf(thePayment.getDatePaymentMade()));
		myStmt.setBoolean(6, thePayment.getLateFee());
		myStmt.setBoolean(7, thePayment.getPaymentMade());
		myStmt.setInt(8, thePayment.getId());
		
		// execute SQL statement
		myStmt.execute();
		}
		finally {
			close(myConn,myStmt,null);
			
		}
	}

	public void deletePayment(String thePaymentId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert payment id to int
			int paymentId = Integer.parseInt(thePaymentId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete payment
			String sql = "delete from payment where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, paymentId);
			
			// execute sql statement
			myStmt.execute();
			
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
			
		}
		
	}

}


