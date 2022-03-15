<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/feedbackDetails/wizardfeedbackhistory.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="insidepages">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
         		<a href="<spring:url value="/wHome#master"/>">Home</a><span>  &raquo; </span> Feedback History
 		 </header>
	</div>
	<!-- <div class="inside-cont-invoice"> -->
<!-- 	<a class="btn-floating btn-large" title="addfeedback" id="addfeedback" href="./wAddFeedbackDetails ">
				    	<img src="/gstn/resources/images/home/ic_add_customer.svg">
				  	</a> -->
		<div class="container">
		<div class="row text-center">				   			  			
		    		<a class="btn-floating btn-large" title="Create new feedback" id="addfeedback" href="./wAddFeedbackDetails ">
				    	<img src="/gstn/resources/images/home/ic_add_customer.svg">
				  	</a>	
		    	</div>
			<table class="table table-striped table-bordered table-hover " id="invoiceHistoryTab">
				<thead>
					<tr>
						
						 <th style="text-align: center;">Sr. No.</th>
						<th style="text-align: center;">Feedback On</th>
						<th style="text-align: center;">Created On</th> 
					<!-- 	<th style="text-align: center;">Status</th>  -->
						
					</tr>
				</thead>
				<tbody>			 
					<c:forEach items="${feedbackhistory}" var="feedbackhistory" varStatus="status">
						<tr>
							 <td style="text-align: center;">${status.index+1} </td>   
							<c:if test = "${feedbackhistory.masterDescDetails == 2}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Other</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 3}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">My Profile</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 4}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Manage Employee</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 5}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Manage GSTIN</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 6}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">GSTIN User Mapping</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 7}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Upload Data to Jio-GST</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 8}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Change Password</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 9}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Delete Account</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 10}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Document</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 11}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Customer Details</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 12}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Goods Catalogue</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 13}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Service Catalogue</a></td>
      						</c:if>
      						<c:if test = "${feedbackhistory.masterDescDetails == 14}">
         					<td style="text-align: center;"><a href="#" onclick="javascript:Details('${feedbackhistory.id}','${feedbackhistory.userId}','${feedbackhistory.masterDescDetails}');">Additional Charge</a></td>
      						</c:if>
							 <%-- <td  style="text-align: center;">${feedbackhistory.masterDescDetails}</td>  --%>
							 
							 <td style="text-align: center;"><fmt:formatDate value="${feedbackhistory.createdOn}" pattern="dd-MM-yyyy" /></td>
							<!--  <td style="text-align: center;">Pending</td> -->
							
						</tr>
					</c:forEach>
					
				</tbody> 
			</table>
			<form name="manageInvoice" method="post">
	  <input type="hidden" name="id" value="">
	    <input type="hidden" name="userId" value="">
      <input type="hidden" name="masterDescDetails" value="">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
		</div>   
	
	
	
</section>
