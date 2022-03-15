<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="block">
	<div class="loader"></div>	
	<div class="container" id="mainPg1">		 
	    <div class="brd-wrap">	    	
	      <strong> 	<a href="<spring:url value="javascript:gobacktoEwayBillDetailedPage()"/>">E-Way Bill Details</a> </strong>&raquo; <strong>Cancel Generic E-Way Bill</strong>	        
	    </div>	    
	     <div class="form-wrap">				
			<div class="row">
				<div class="col-md-4"  >
					<label for="label">NIC User Id<span> *</span></label>
				    	<input type="text" required class="form-control" disabled="disabled" id="nicUserId" value="${ nicUserDetails.userId}">
					<span class="text-danger cust-error" id="nicUserId-csv-id">This field is required.</span>
				</div>
				<div class="col-md-4">
					<label for="label">NIC Password<span> *</span></label>
				    	<input type="password" required class="form-control" value="" id="nicPwd">   
					<span class="text-danger cust-error" id="nicPwd-csv-id">This field is required.</span>	
				</div> 
				<div class="col-md-4"  id="remarksDiv">
					<label for="label">Remarks<span> *</span></label>
				    	<input type="text" required class="form-control" id="remarks" value="">
					<span class="text-danger cust-error" id="remarksId">This field is required.</span>
				</div>				
	   			<input type="hidden" id="clientId" name="clientId" value="${clientId}">
				<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
				<input type="hidden" id="appCode" name="appCode" value="${appCode}">
				<input type="hidden" name="userId" id="userId" value="${userId}">
				<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
				<input type="hidden" id="loggedInFrom" name="loggedInFrom" value="${loggedInFrom}">	    
	   			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		 		<input type="hidden" id="uiGstnNo" value="${ gstin}">
				<input type="hidden" id="uiEWayBillNo" value="${ ewaybillno}">	    
	    	</div>
			<div class="row">	
	      		<div class="col-md-12 button-wrap">
	      			<button type="submit" class="btn btn-primary" id="cancelId" >Cancel E-Way Bill</button>                              		
                 </div>
           </div>	    
	    </div>   
	  </div>  
</section>	

<script type="text/javascript" src="<spring:url value="resources/js/newUiJs/eWayBillWI/cancelValidations.js"/>"></script>
   
<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>

<form name="previewInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>