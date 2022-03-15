<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>


        <!-- Dashboard set variables - Start -->
        <input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${conditionValue}">
        <input type="hidden" id="dash_startdate" name="dash_startdate" value="${startdate}">
        <input type="hidden" id="dash_enddate" name="dash_enddate" value="${enddate}">
        <input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
        <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
        <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
        <%@include file="dashboardMonth.jsp" %>
        <header class="insidehead" id="originalHeader">
       
      <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="./home">
      	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>	
      </a>
 </header> 
   
   <header class="insidehead" style="display:none" id="previewHeader">
 
  <%--  <a href="<spring:url value="/showAllRecordsList" /> " id="backToPreview" class="btn-back"><i class="fa fa-angle-left"></i></a> --%>
    <a id="goBackToGenericEwaybill" href="#" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="./home">
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      </a>
    
	</header>
 <%-- <header class="insidehead">
 <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a>
      
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      	<!--  <span>Upload Data<span> -->
      </a>
 </header> --%>
<section class="insidepages">
<div class="container">
		<div class="invoicePage" id="firstDivId">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader" style="align:center;padding:5px">
		 <h4>Generated Generic E-way Bills  for ${month} ${onlyYear} </h4>
		
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		 </header>
	</div>
	<div class="card">
		
			<table class="table table-striped table-bordered" id="invoiceHistoryTab">
				<thead>
					<tr>
						
						<th style="text-align: center;">Ewaybill No</th>
						<th style="text-align: center;">Status</th>
						
						<!-- <th>Eway bill Status.</th> -->
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${ewaybillList}" var="ewaybillList" varStatus="status">
						<tr>
						
						<c:set var="conditionValue" value="${ewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'GENEWAYBILL'}">
                     <td style="text-align: center;"> <a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a> </td> 
                      <td style="text-align: center;">Active</td>
                      </c:if>
                      
                      <c:set var="conditionValue" value="${ewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'CANEWB'}">
                       <td style="text-align: center;"> <a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a><!-- <span id="check" class="glyphicon glyphicon-remove-circle"  style="float:right; color:red"></span> --> </td>
                        <td style="text-align: center;">Inactive</td>
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
			<div class="invoicePage" id="secondDivId" style="display:none">
				<div class="row">
				<div class="card">
				<div class="box">
					<div class="invoiceDetail">
							
								<div id="etable">
								 <h5> 1. E-Way Bill Details </h5> <br>
								</div>
					</div>
				
						<div class="row">
						<div class="invoiceDetail">
						<h5> 2. Address Details </h5>  <br>
						<div class="col-sm-6">
							<div class="invoiceInfo " id="fromGstin">
							
							</div>
						</div>
								<div class="col-sm-6">
									<div class="invoiceInfo " id="toGstin">
									
									</div>
								</div>
						</div>
					</div>
						
					<!-- Start -->
					<div class="row">
				<!-- 	<div class="invoiceTable"> -->
			          	 
			          	 <div class="invoiceDetail">
								<!-- <div id="invoiceTable"> -->
								<h5> 3. Goods Details </h5>  <br>
								<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          
                          	<ul class="e-productdet headers">
                               <li>HSN Code</li>
                               <li>Product Name</li>
                               <li>Quantity</li>
                               <li>Taxable Amount Rs.</li>
                               <li>Tax Rate</li>
                            </ul>
                           	 <div id="itemDet">
                           	 	
                           	 </div>
                            	  
                            	<br>
                            	 </div>
                            </div>
                            <!-- </div>
                            </div>
                            	<div class="invoiceDetail">  -->
                            	<div class="col-sm-6">
							<div class="invoiceInfo " id="itemTaxDet">
                          
                            </div>
                            </div> 
                             </div>	
						
			         <!--  	</div> -->
			          	 </div>
					 
					  <div class="row">
							<div class="invoiceDetail">
							 <h5> 4. Transportation Details </h5> <br>
							<div class="col-sm-6">
							<div class="invoiceInfo "  id="transportTable">
								
								 </div>
								 </div>
								
							</div>
						</div>
						 <div class="row">
						<!-- 	<div class="invoiceTable">	 -->				
						<div id="vehicleTable">
								
								<h5> 5. Vehicle Details </h5>
								<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          
                          	<ul class="transportdet headers">
                               <li>Mode</li>
                               <li>Vehicle / Trans <br> Doc No & Dt.</li>
                               <li>From</li>
                               <li>Entered Date</li>
                               <li>Entered By</li>
                               <li>CEWB No.<br> (If any)</li>
                            </ul>
                            	 <ul class="transportdet" id="vehicleDet">
                            	 </ul>
                            </div>
                            </div>
						</div>
					<!-- 	</div> -->
						</div>
					<!-- End -->	
					
					<br>
			
							
			</div>
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
<!--  <div class="cust-wrap">
		
		<div class="dnynamicewaybillDetails" id="toggle">								
		</div>
      </div> -->
      
<script type="text/javascript" src="<spring:url value="/resources/js/eWayBillWI/viewEWayBills.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashboardGenericEwaybill.js"/>"></script>

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
