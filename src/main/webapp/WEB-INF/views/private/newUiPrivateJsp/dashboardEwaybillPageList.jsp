<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>




        <!-- Dashboard set variables - Start -->
        <input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${conditionValue}">
        <input type="hidden" id="dash_startdate" name="dash_startdate" value="${startdate}">
        <input type="hidden" id="dash_enddate" name="dash_enddate" value="${enddate}">
        <input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
        <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
        <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
        <%@include file="dashboardMonth.jsp" %>
       <section class="block">
	<div class="container">	
        <div class="loader"></div>
				 <!-- <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a> -->
				<!--  <div class="brd-wrap">
					    	<div  id="listheader">
					       		 <a href="#" id="goBackToDashboard"><strong>Dashboard</strong></a> &raquo; <strong>Generic E-way bill History</strong>
					        </div>
			     </div> -->
			     <div class="page-title">
                    <a href="#" id="goBackToDashboard" class="back"><i class="fa fa-chevron-left"></i></a>Generated Generic E-way Bills  for ${month} ${onlyYear}
              </div>
			     
   

 <%-- <header class="insidehead">
 <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a>
      
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      	<!--  <span>Upload Data<span> -->
      </a>
 </header> --%>

		<div class="invoicePage" id="firstDivId">
	         <div class="breadcrumbs">
		         <%--  <h4 class="viewHeading">Generated Generic E-way Bills  for ${month} ${onlyYear} </h4> --%>
          	</div>
	<div class="row">
	<div class="table-wrap">
		
			<table class="display nowrap" style="width:100%" id="invoiceHistoryTabInvoiceEway">
				<thead>
					<tr>
						<th >Ewaybill No</th>
						<th >Status</th>
						<!-- <th>Eway bill Status.</th> -->
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${ewaybillList}" var="ewaybillList" varStatus="status">
						<tr>
						
						<c:set var="conditionValue" value="${ewaybillList[2]}"/>
                         <c:if test="${conditionValue == 'GENEWAYBILL'}">
                           <td > <a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a> </td> 
                           <td >Active</td>
                         </c:if>
                      
                       <c:set var="conditionValue" value="${ewaybillList[2]}"/>
                        <c:if test="${conditionValue == 'CANEWB'}">
                           <td > <a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a><!-- <span id="check" class="glyphicon glyphicon-remove-circle"  style="float:right; color:red"></span> --> </td>
                           <td >Inactive</td>
                        </c:if>
							<%-- <td>${status.index+1}</td>
							<td><a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a> </td> 
							 --%>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			</div>
			</div>
			</div>
		
					<input type="hidden" id="clientId" name="clientId" value="${clientId}">
					<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
					<input type="hidden" id="appCode" name="appCode" value="${appCode}">
					<input type="hidden" name="userId" id="userId" value="${userId}">
					<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
			
			
			
		</div>
	<!-- </div> -->
	
	 <form name="previewInvoiceewaybill" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
    <input type="hidden" name="conditionValue" value="ewaybill">
     <input type="hidden" name="startdate" value="${startdate}">
	   <input type="hidden" name="enddate" value="${enddate}">
	   <input type="hidden" id="onlyMonth"  name="onlyMonth" value="${onlyMonth}">
	   <input type="hidden"id="onlyYear" name="onlyYear"  value="${onlyYear}">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
	 
	<form name="Ewaybill" method="post">
	<input type="hidden" name="ewaybillNo" value="">
	  <input type="hidden" name="ewayBillWITransId" value="">
	  <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="ewaybill">
	<input type="hidden" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" name="onlyYear" value="${onlyYear}">
      <input type="hidden" name="_csrf_token" value="${_csrf_token}">
     
	</form> 
	<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>
      
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardGenericEwaybill.js"/>"></script>

<form name="downloadEWayBill" id="downloadEWayBill" method="post">
    <input type="hidden" name="invoiceId" value="">
    <input type="hidden" name="gstin" value="">
    <input type="hidden" name="ewaybillNo" value="">
</form>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>

<form name="cancelEwayBill" id="cancelEwayBill" method="post">
    <input type="hidden" name="ewayBillNo" value="">
    <input type="hidden" name="gstin" value="">
</form>

<!-- Dashboard backToCall form -->
<form name="redirectToBackFromGenericEwayBill" method="post">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
      <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

	</section>
