<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardDate.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardGenericEwaybill.js"/>"></script>

<%@include file="dashboardMonth.jsp" %> 
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardviewEwayBills.js"/>"></script> --%>
<input type="hidden" id="clientId" name="clientId" value="${clientId}">
			<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
			<input type="hidden" id="appCode" name="appCode" value="${appCode}">
			<input type="hidden" name="userId" id="userId" value="${userId}">
			<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">	
			<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${conditionValue}">
        <input type="hidden" id="dash_startdate" name="dash_startdate" value="${startdate}">
        <input type="hidden" id="dash_enddate" name="dash_enddate" value="${enddate}">
        <input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
        <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
        <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 			
	 <input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
  <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		
		<section class="insidepages">
			<header class="insidehead" id="originalHeader">
	       			<div class="breadcrumbs" id="listHeader">
			<div class="col-md-12" id="listheader1">
	         	<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a href="#" id="goBackToDashboard" > Dashboard</a> <span> &raquo; </span> E-way Bill List
	 		</div>
	 		</div>
	 		</header>
	 		 <header class="insidehead" style="display:none" id="previewHeader">
	 					<div class="breadcrumbs" id="listHeader">
			<div class="col-md-12" id="listheader1">
	         	<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a href="#" id="goBackToGenericEwaybill" >  E-way Bill List </a> <span> &raquo; </span> Generic E-way Bill
	 		</div>
	 		
	 			
		</div>
		</header>
		<div class="inside-cont">
		
	   
	       			<div class="container" id="firstDivId">
	       		
	       			<div class="breadcrumbs">
	       				<div class="col-md-3"></div>
		<div class="col-md-6">
			<header class="insidehead" id="generateInvoiceDefaultPageHeader">
				<h4>
					<b>Generated Generic E-way Bill from ${month} To ${onlyYear} </b>
				</h4>

				<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>
			</header>
			</div>
		</div>
			<table class="table table-striped table-bordered" id="invoiceHistoryTab">
				<thead>
							<tr role="row">
							<th style="text-align: center; width: 201px;" class="sorting_asc" tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Sr.No.: activate to sort column descending">Sr.No.</th>
							<th style="text-align: center; width: 416px;" class="sorting" tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1" colspan="1" aria-label="Invoice No.: activate to sort column ascending">E-way Bill No.</th>
							<th style="text-align: center; width: 416px;" class="sorting" tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1" colspan="1" aria-label="Invoice No.: activate to sort column ascending">Status</th>
							
							

						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${ewaybillList}" var="ewaybillList" varStatus="status">
						<tr>
						<td><center>${status.index+1}</center></td>
						<c:set var="conditionValue" value="${ewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'GENEWAYBILL'}">
                     
                    <td> <center><a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a></center></td> 
                    <td><center><span style="color:green;font-weight:bold; ">Valid</span></center> </td>
                     <!-- <td></td> -->
                       </c:if>
                      <c:set var="conditionValue" value="${ewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'CANEWB'}">
                     <td><center> <a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${ewaybillList[1]}','${ewaybillList[0]}');">${ewaybillList[1]}</a></center></td>
                      <td> <center><span style="color:red;font-weight:bold ;">Cancelled</span> </center></td>
                       </c:if>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			      	
	    </div>
	    	
	   
        	       	
       <div class="account-det"> 
      
        	<div class="container" id="secondDivId" style="display:none">
        	
        	 <div class="row">
			 
		 		
	    <div class="inside-cont">

     <div class="card">
     <div class="box">
        			<div class="row">
						<div class="invoiceDetail ">
		        			<div id="etable">
								<strong>1. E-Way Bill Details</strong><br><br>						
							</div>
						</div>
					</div>        		
					
					<div class="row">
						<div class="invoiceDetail">
							<strong>2. Address Details </strong><br><br>
							<div class="col-sm-6">
								<div class="invoiceInfo" id="fromGstin">								 
																	
								</div>
							</div>
							<div class="col-sm-6">
								<div class="invoiceInfo" id="toGstin">
									
								</div>
							</div>												
						</div>
					</div>
					
					<div class="row">
				<!-- 	<div class="invoiceTable"> -->
			          	 
			          	 <div class="invoiceDetail">
								<!-- <div id="invoiceTable"> -->
								<strong>3. Goods Details</strong><br><br>	
							
						
                          <!-- First Table Starts -->

                          
                          	
                               <table id="mytable2" class="e-productdet headers">
								<thead>
								  <tr>
									<th>HSN Code</th>
									<th>Product Descripition</th>
									<th>Quantity</th>
									<th>Taxable Amount Rs.</th>
									<th>Tax Rate</th>
								  </tr>
								</thead>
								<tbody>									 
									 
								</tbody>
							</table>
                            
                           	<div class="col-sm-12">
								<div class="invoiceInfo " id="itemTaxDet">
	                            	
	                            </div>
	                        </div> 
                            	  
                            	
                            	
                            </div>
                           
						
			         <!--  	</div> -->
			          	 </div>
        			
        			<div class="row">
						<div class="invoiceDetail">
							<strong> 4. Transportation Details </strong><br><br>
						
								<div class="col-sm-12">
							<div class="invoiceInfo "  id="transportTable">
								
								 </div>
								 </div>
							</div>							
						</div>
					
					
        			<div class="row">
        				<div id="vehicleTable">
        					<strong> 5. Vehicle Details </strong>  <br><br> 
        					<div id="vehicleDet">	
        					<table id="vehicleDetTable" class="transportdet"> 					
			            	
                          
								<thead >
								  <tr >
									<th>Mode</th>
									<th>Vehicle / Trans<br>Doc No & Dt.</th>
									<th>From</th>
									<th>Entered Date</th>
									<th>Entered By</th>
									<th>CEWB No.<br>(If any)</th>
								  </tr>
								  
								</thead>
								<tbody >
								
			            		</tbody>
								
								
								
							</table></div>
        				</div>
        				
				</div>
				</div>
				
        	</div>        	
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
<%--  <script type="text/javascript" src="<spring:url value="/resources/js/eWayBillWI/viewEWayBills.js" />"></script> --%>
 <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardviewEwayBills.js"/>"></script>

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

