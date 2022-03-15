<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
 <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardDate.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardInvoice.js"/>"></script> 




	
	
	 <input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
  <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<section class="insidepages">
		<div class="breadcrumbs" id="header"><a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a href="#" id="goBackToDashboard" >Dashboard</a> <span> &raquo; </span> InvoiceList
		 		</div>
        

	
<%@include file="dashboardMonth.jsp" %> 

	
	<div class="col-md-4">
	</div>
	<div class="col-md-6">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
		 <h4><b>Invoices generated  from ${month} To ${onlyYear}  </b></h4>
		 
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		 </header>
	</div>
	
	
	<div class="cust-wrap">		
					<div  id="toggle"><br>
					<div class="container">
						<!-- class="dnynamicCustomerDetails" -->	
						<table class="table table-striped table-bordered" id="invoiceHistoryTab">
							<thead>
							<tr role="row">
							<th style="text-align: center; width: 201px;" class="sorting_asc" tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Sr.No.: activate to sort column descending">Sr.No.</th>
							<th style="text-align: center; width: 416px;" class="sorting" tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1" colspan="1" aria-label="Invoice No.: activate to sort column ascending">Invoice No.</th>
							
</tr>
								
							</thead>
							<tbody>			
					<c:forEach items="${invoiceList}" var="invoiceList" varStatus="status">
						<tr>
							<td style="text-align: center;">${status.index+1}</td>
							<td style="text-align: center;"><a href="#" onclick="javascript:viewRecord(${invoiceList[0]});">${invoiceList[1]}</a> </td> 
							
						</tr>
					</c:forEach>
					
				</tbody>
						</table>							
					
		      </div>
	
	
		</div>
		</div>
	<!-- </div> -->
	
	
	<%-- <form name="manageInvoice1" method="post">
	  <input type="hidden" name="id" value="">
      <input type="hidden" name="nicUserId" value="">
      <input type="hidden" name="nicPwd" value="">
      <input type="hidden" name="gstin" value="">
      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form> --%>
	<form name="invoice" method="post">
	  <input type="hidden" name="id" value="">
	   <input type="hidden" name="conditionValue" value="invoice">
	   <input type="hidden" name="startdate" value="${startdate}">
	   <input type="hidden" name="enddate" value="${enddate}">
	   <input type="hidden" name="onlyMonth" value="${onlyMonth}">
	   <input type="hidden" name="onlyYear" value="${onlyYear}">
       <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	
	<form name="gotoWizardDashboard" method="post">
	    <input type="hidden" name="onlyMonth" value="">
	    <input type="hidden" name="onlyYear" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	</section>
	
