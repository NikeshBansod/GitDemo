<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

 <%@include file="dashboardMonth.jsp" %> 


 <header class="insidehead">

 <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a>
  
       <%-- <a href="<spring:url value="/dashboard" />" class="btn-back"><i class="fa fa-angle-left"></i> --%>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      	<!--  <span>Upload Data<span> -->
      </a>
 </header>
<section class="insidepages">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader" style="align:center;padding:5px">
		 
		 <h4>Generated Credit/Debit Notes for</br> ${month}  ${onlyYear}</h4>
		 
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		 </header>
	</div>
	
		<div class="container">
		<div class="card">
			<table class="table table-striped table-bordered" id="invoiceHistoryTab" >
				<thead>
					<tr>
						<th style="text-align: center;"> Sr.No.</th>
						<th style="text-align: center;">CNDN No.</th>
						<th style="text-align: center;">Invoice No.</th>
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${CNDNList}" var="CNDNList" varStatus="status">
						<tr>
							<td style="text-align: center;">${status.index+1}</td>
							<td style="text-align: center;"><a href="#" onclick="javascript:getCnDnDetails('${CNDNList[3]}','${CNDNList[0]}','${CNDNList[1]}','${CNDNList[2]}');">${CNDNList[2]}</a> </td> 
							<td style="text-align: center;">${CNDNList[4]}</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
		</div>
		</div>
	<!-- </div> -->
	<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashboardCNDN.js"/>"></script>
	
<form name="cndn" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" name="cndnNumber" value="">
    <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="cndn">
	<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form>
<!-- back from cndnlist to dashboard -->
<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>
	</section>