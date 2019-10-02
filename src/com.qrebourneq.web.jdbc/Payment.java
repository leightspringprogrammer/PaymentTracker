package com.qrebourneq.web.jdbc;

import java.time.LocalDate;



public class Payment {
	
	private int id;
	private String companyName;
	private String companyNumber;
	private int paymentAmount;
	private LocalDate paymentDueDate;
	private LocalDate datePaymentMade;
	private Boolean lateFee;
	private Boolean paymentMade;
	
	
	public Payment(int id, String companyName, String companyNumber, int paymentAmount, LocalDate paymentDueDate,
			LocalDate datePaymentMade, Boolean lateFee, Boolean paymentMade) {
		this.id = id;
		this.companyName = companyName;
		this.companyNumber = companyNumber;
		this.paymentAmount = paymentAmount;
		this.paymentDueDate = paymentDueDate;
		this.datePaymentMade = datePaymentMade;
		this.lateFee = lateFee;
		this.paymentMade = paymentMade;
	}


	public Payment(String companyName, String companyNumber, int paymentAmount, LocalDate paymentDueDate,
			LocalDate datePaymentMade, Boolean lateFee, Boolean paymentMade) {
		
		this.companyName = companyName;
		this.companyNumber = companyNumber;
		this.paymentAmount = paymentAmount;
		this.paymentDueDate = paymentDueDate;
		this.datePaymentMade = datePaymentMade;
		this.lateFee = lateFee;
		this.paymentMade = paymentMade;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyNumber() {
		return companyNumber;
	}


	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}


	public int getPaymentAmount() {
		return paymentAmount;
	}


	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}


	public LocalDate getPaymentDueDate() {
		return paymentDueDate;
	}


	public void setPaymentDueDate(LocalDate paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}


	public LocalDate getDatePaymentMade() {
		return datePaymentMade;
	}


	public void setDatePaymentMade(LocalDate datePaymentMade) {
		this.datePaymentMade = datePaymentMade;
	}


	public Boolean getLateFee() {
		return lateFee;
	}


	public void setLateFee(Boolean lateFee) {
		this.lateFee = lateFee;
	}


	public Boolean getPaymentMade() {
		return paymentMade;
	}


	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}


	@Override
	public String toString() {
		return "Payment [id=" + id + ", companyName=" + companyName + ", companyNumber=" + companyNumber
				+ ", paymentAmount=" + paymentAmount + ", paymentDueDate=" + paymentDueDate + ", datePaymentMade="
				+ datePaymentMade + ", lateFee=" + lateFee + ", paymentMade=" + paymentMade + "]";
	}
	
	
	
	
	
	

}
