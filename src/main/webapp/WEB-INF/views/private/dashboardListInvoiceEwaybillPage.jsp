<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>


<%@include file="dashboardMonth.jsp" %>
 <header class="insidehead">
 <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <%-- <a href="<spring:url value="/dashboard" />" class="btn-back"><i class="fa fa-angle-left"></i>  --%>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      	<!--  <span>Upload Data<span> -->
      </a>
 </header>
<section class="insidepages">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader" style="align:center;padding:5px">
		 <h4>Generated E-way Bills through Invoice for ${month}  ${onlyYear} </h4>
		 
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		 </header>
	</div>
	
		<div class="container">
		<div class="card">
			<table class="table table-striped table-bordered" id="invoiceHistoryTab">
				<thead>
					<tr>
					
						
						<th style="text-align: center;">Eway bill No.</th>
						<th style="text-align: center;">Status.</th>
						
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${invoiceewaybillList}" var="invoiceewaybillList" varStatus="status">
						<tr>
						
						<c:set var="conditionValue" value="${invoiceewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'GENEWAYBILL'}">
                     <td style="text-align: center;"> <a href="#" onclick="javascript:getPreviewOfInvoiceEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a> </td> 
                      <td style="text-align: center;">Active</td> 
                      </c:if>
                      
                      <c:set var="conditionValue" value="${invoiceewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'CANEWB'}">
                       <td style="text-align: center;"> <a href="#" onclick="javascript:getPreviewOfInvoiceEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a> <!-- <span id="check" class="glyphicon glyphicon-remove-circle" style="float:right;color:red"></span> --></td>
                        <td style="text-align: center;">Inactive</span></td> 
                      </c:if>
						
    
                   
							<%-- <td>${status.index+1}</td>
							<td><a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a> </td>  --%>
							
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			</div>
		</div>
	<!-- </div> -->
	<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashboardInvoiceEwaybill.js"/>"></script>
	<form name="invoiceEwaybill" method="post">
	<input type="hidden" name="ewaybillNo" value="">
	  <input type="hidden" name="invoiceId" value="">
	  <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="invoiceewaybill">
	<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
      <input type="hidden" name="_csrf_token" value="${_csrf_token}">
     
	</form> 
	<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>
	</section>
