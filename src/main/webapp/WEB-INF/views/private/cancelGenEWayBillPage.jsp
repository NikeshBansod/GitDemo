<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %>  

<header class="insidehead" id="">
            <a href="<spring:url value="/getGenericEWayBills" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
</header>

<section class="block searchBox">
	<div class="container" id='loadingmessage' style='display:none;' align="middle">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
	<div class="container" id="mainPg1">

		<div class="card">
			<input type="hidden" id="uiGstnNo" value="${ gstin}">
			<input type="hidden" id="uiEWayBillNo" value="${ ewaybillno}">
			
			<div class="form-group input-field mandatory">
				<label class="label">
					<input type="text" required class="form-control" disabled="disabled" id="nicUserId" value="${ nicUserDetails.userId}">
					<div class="label-text label-text2">NIC User Id</div>	
				</label>
				<span class="text-danger cust-error" id="nicUserId-csv-id">This field is required.</span>
			</div>

			<div class="form-group input-field mandatory">
				<label class="label">
					<input type="password" required class="form-control" value="" id="nicPwd">
					<div class="label-text">NIC Password</div>
				</label>
				<span class="text-danger cust-error" id="nicPwd-csv-id">This field is required.</span>
			</div>
			
			<div class="form-group input-field mandatory" id="remarksDiv">
					<label class="label"> 
						<input type="text" required class="form-control" id="remarks" value="">
						<div class="label-text" id="remarks">Remarks</div>
					</label>
					<span class="text-danger cust-error" id="remarksId">This field is required.</span>
				</div>
					<input type="hidden" id="clientId" name="clientId" value="${clientId}">
					<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
					<input type="hidden" id="appCode" name="appCode" value="${appCode}">
					<input type="hidden" name="userId" id="userId" value="${userId}">
					<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
					<input type="hidden" id="loggedInFrom" name="loggedInFrom" value="${loggedInFrom}">
				
            <div class="form-group input-field text-center">
					<a id="cancelId"  class="btn btn-primary">Cancel E-Way Bill</a>
			</div>
            
		</div>
	</div>
</section>
 
  <script type="text/javascript" src="<spring:url value="resources/js/eWayBillWI/cancelValidations.js"/>"></script>
  
  
<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>