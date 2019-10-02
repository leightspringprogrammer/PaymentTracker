<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>

<head>
	<title>Payment Tracker App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body style="background: url(images/brownBG.png) repeat;">
	<div id="wrapper">
		<div id="header" style="background-color:#a85706; border-radius: 25px;">
			<h2>Payment Ledger</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
		
		<input type="button" value="Add Payment"
			onclick="window.location.href='add-payment-form.jsp'; return false;"
			class="add-student-button"
			/>
			
			<table style="width:100%;">
				<tr>
					<th style="background-color:#a85706;">Company</th>
					<th style="background-color:#a85706;">Phone #</th>
					<th style="background-color:#a85706;">Amount</th>
					<th style="background-color:#a85706;">Due Date</th>
					<th style="background-color:#a85706;">Date Paid</th>
					<th style="background-color:#a85706;">Late Fee</th>
					<th style="background-color:#a85706;">Payment Sent</th>
					<th style="background-color:#a85706;">Action</th>	
				</tr>
			<c:forEach var="tempPayment" items="${PAYMENT_LIST}">
			
			<!-- set up an update link for each payment -->
			<c:url var="updateLink" value="PaymentControllerServlet">
			<c:param name="command" value="LOAD" />
			<c:param name="paymentId" value="${tempPayment.id }" />
			</c:url>
			
			<!-- set up a delete link for each payment -->
			<c:url var="deleteLink" value="PaymentControllerServlet">
			<c:param name="command" value="DELETE" />
			<c:param name="paymentId" value="${tempPayment.id }" />
			</c:url>
					<tr>
						<td> ${ tempPayment.companyName} </td>
						<td> ${ tempPayment.companyNumber} </td>
						<td> ${ tempPayment.paymentAmount} </td>
						<td> ${ tempPayment.paymentDueDate} </td>
						<td> ${ tempPayment.datePaymentMade} </td>
				<!-- For LateFee and PaymentMade fields (two boolean values) -->
				<!-- JSTL evaluates value, and fills div with corresponding checkbox -->
						<td>
							<c:if test = "${tempPayment.lateFee == 'true'}">
         						<input type="checkbox" disabled checked>
      						</c:if>
      						<c:if test = "${tempPayment.lateFee == 'false'}">
         						<input type="checkbox" disabled>
     						</c:if>
						 </td>
						<td>
							<c:if test = "${tempPayment.paymentMade == 'true'}">
         						<input type="checkbox" disabled checked>
      						</c:if>
      						<c:if test = "${tempPayment.paymentMade == 'false'}">
         						<input type="checkbox" disabled>
     						</c:if>
						 </td>
						<td> <a href="${updateLink}">Update</a> | <a href="${deleteLink}"
																	onclick="if (!(confirm('Are you sure you want to delete this payment?'))) return false">Delete</a> 
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>

</html>