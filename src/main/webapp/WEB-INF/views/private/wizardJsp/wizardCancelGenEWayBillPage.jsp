<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="insidepages">
	<div class="inside-cont">
		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="breadcrumbs">
			 <header class="insidehead" id="originalHeader">
	         	<a href="<spring:url value="javascript:viewWIEwayBillDetailedPage()"/>" >E-Way Bill</a> <span>&raquo;</span> Cancel E-Way Bill
	 		 </header>
		</div>
		<div class="card"  id="mainPg1">
			<div class="account-det">
					<input type="hidden" id="uiGstnNo" value="${ gstin}">
					<input type="hidden" id="uiEWayBillNo" value="${ ewaybillno}">
					
				<div class="det-row">
			    	<div class="det-row-col-full astrick">
		            	<div class="label-text">NIC User Id</div>
		              	<input type="text" required class="form-control" disabled="disabled" id="nicUserId" value="${ nicUserDetails.userId}">  
		              	<span class="text-danger cust-error" id="nicUserId-csv-id">This field is required.</span>
		            </div>
				</div>		            
		        <div class="det-row">
		            <div class="det-row-col-full astrick">
		            	<div class="label-text">NIC Password</div>
		              	<input type="password" required class="form-control" value="" id="nicPwd">  
		              	<span class="text-danger cust-error" id="nicPwd-csv-id">This field is required.</span>
		            </div>
				</div>		            
		        <div class="det-row">
		            <div class="det-row-col-full astrick">
		            	<div class="label-text">Remarks</div>
		              	<input type="text" required class="form-control" id="remarks" value="">  
		              	<span class="text-danger cust-error" id="remarksId">This field is required.</span>
		            </div>
			    </div>
			</div>
			<div class="insidebtn"> 
				<a id="cancelId"  class="sim-button button5">Cancel E-Way Bill</a>
	        </div>
	        		<input type="hidden" id="clientId" name="clientId" value="${clientId}">
					<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
					<input type="hidden" id="appCode" name="appCode" value="${appCode}">
					<input type="hidden" name="userId" id="userId" value="${userId}">
					<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
					<input type="hidden" id="loggedInFrom" name="loggedInFrom" value="${loggedInFrom}">
		</div>		
	</div>
</section>
<script type="text/javascript" src="<spring:url value="resources/js/eWayBillWI/cancelValidations.js"/>"></script>
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBill/validations.js"/>"></script> --%>
  
<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>

<form name="previewInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>