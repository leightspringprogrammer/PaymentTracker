<!DOCTYPE html>
<html>

	<head>
		<title>Add Payment</title>
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
			<h3>Add Payment</h3>
			
			<form action="PaymentControllerServlet" method="GET">
				<input type="hidden" name="command" value="ADD" />
				
				<table>
					<tbody>
						<tr style="background: #BBBBBB">
							<td><label>Company Name:</label></td>
							<td><input type="text" name="companyName" /></td>
						</tr>
						<tr >
							<td><label>Company Phone:</label></td>
							<td><input type="text" name="companyNumber" /></td>
						</tr>
						<tr style="background: #BBBBBB">
							<td><label>Bill Amount:</label></td>
							<td><input type="text" name="paymentAmount" /></td>
						</tr>
						<tr >
							<td><label>Due Date:</label></td>
							<td><input type="date" name="paymentDueDate" /></td>
						</tr>
						<tr style="background: #BBBBBB">
							<td><label>Payment Date:</label></td>
							<td><input type="date" name="datePaymentMade" /></td>
						</tr>
						<tr >
							<td><label>Late Fee:</label></td>
							<td><input type="checkbox" name="lateFee" value="late" /></td>
						</tr>
						<tr style="background: #BBBBBB">
							<td><label>Payment Made:</label></td>
							<td><input type="checkbox" name="paymentMade" value="paid" /></td>
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