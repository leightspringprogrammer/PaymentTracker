<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

	<head>
		<title>Update Payment</title>
		<link type="text/css" rel="stylesheet" href="css/style.css">
		<link type="text/css" rel="stylesheet" href="css/add-payment-style.css">
		</head>
	<body style="background: url(images/brownBG.png) repeat;">
		<div id="wrapper">
			<div id="header" style="background-color:#a85706; border-radius: 25px;">
				<h2>Payment Ledger</h2>
			</div>
		</div>
		
		<div id="container">
			<h3>Update Payment</h3>
			
			<form action="PaymentControllerServlet" method="GET">
				<input type="hidden" name="command" value="UPDATE" />
				
				<input type="hidden" name="paymentId" value="${THE_PAYMENT.id}" />
				
				<table>
					<tbody>
						<tr style="background: #BBBBBB">
							<td><label>Company Name:</label></td>
							<td><input type="text" name="companyName"
										value="${THE_PAYMENT.companyName }" /></td>
						</tr>
						<tr>
							<td><label>Company Phone:</label></td>
							<td><input type="text" name="companyNumber"
										value="${THE_PAYMENT.companyNumber }" /></td>
						</tr>
						<tr style="background: #BBBBBB">
							<td><label>Bill Amount:</label></td>
							<td><input type="text" name="paymentAmount"
										value="${THE_PAYMENT.paymentAmount }" /></td>
						</tr>
						<tr >
							<td><label>Due Date:</label></td>
							<td><input type="date" name="paymentDueDate" 
										value="${THE_PAYMENT.paymentDueDate }"/></td>
						</tr>
						<tr style="background: #BBBBBB">
							<td><label>Payment Date:</label></td>
							<td><input type="date" name="datePaymentMade"
										value="${THE_PAYMENT.datePaymentMade }" /></td>
						</tr>
						
						<tr >
						<td><label>Late Fee?:</label></td>
						<td>
							<c:if test = "${THE_PAYMENT.lateFee == 'true'}">
         						<input type="checkbox" name="lateFee" checked/>
      						</c:if>
      						<c:if test = "${THE_PAYMENT.lateFee == 'false'}">
         						<input type="checkbox" name="lateFee"/>
     						</c:if>
						 </td>
							
						</tr>
						<tr style="background: #BBBBBB">
							<td><label>Payment Made?:</label></td>
						<td>
							<c:if test = "${THE_PAYMENT.paymentMade == 'true'}">
         						<input type="checkbox" name="paymentMade" checked/>
      						</c:if>
      						<c:if test = "${THE_PAYMENT.paymentMade == 'false'}">
         						<input type="checkbox" name="paymentMade"/>
     						</c:if>
						 </td>
						</tr>
						<tr >
							<td><label></label></td>
							<td><input type="submit" value="Save" class="save" /></td>
						</tr>
							
					</tbody>
				</table>			
			</form>
			
			<div style="clear: both;"></div>
			
			<p><a href="PaymentControllerServlet">Back to List</a>
			</p>
		</div>
	
	</body>

</html>