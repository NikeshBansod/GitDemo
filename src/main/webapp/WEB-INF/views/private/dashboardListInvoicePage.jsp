<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

<%@include file="dashboardMonth.jsp" %>

 <header class="insidehead">
 <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a>
 
 
     <%--  <a href="<spring:url value="/dashboard" />" class="btn-back"><i class="fa fa-angle-left"></i>  --%>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      	<!--  <span>Upload Data<span> -->
      </a>
 </header>
<section class="insidepages">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader" style="align:center;padding:5px">
		 <h4>Generated documents for </br> ${month}  ${onlyYear} </h4>
		 
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		 </header>
	</div>
	
		<div class="container" style="background-color:white;">
		
			<table class="table table-striped table-bordered" id="invoiceHistoryTab">
				<thead>
					<tr>
						<th style="text-align: center;">Invoice No.</th>
						<th style="text-align: center;">Created Against</th>
						<th style="text-align: center;">From</th>
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${invoiceList}" var="invoiceList" varStatus="status">
						<tr>
							
							<td><a href="#" onclick="javascript:viewRecord(${invoiceList[0]});">${invoiceList[1]}</a> </td> 
							<td>${invoiceList[4]}</td>
							<td>${invoiceList[5]}</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			
			
		</div>
	<!-- </div> -->
	<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashboardInvoice.js"/>"></script>
	
	<form name="invoice" method="post">
	  <input type="hidden" name="id" value="">
	   <input type="hidden" name="conditionValue" value="invoice">
	   <input type="hidden" name="startdate" value="${startdate}">
	   <input type="hidden" name="enddate" value="${enddate}">
	   <input type="hidden" id="onlyMonth"  name="onlyMonth" value="${onlyMonth}">
	   <input type="hidden"id="onlyYear" name="onlyYear"  value="${onlyYear}">
       <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	
	<form name="gotoDashboard" method="post">
	    <input type="hidden" name="onlyMonth" value="">
	    <input type="hidden" name="onlyYear" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	</section>