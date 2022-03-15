<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<header class="insidehead" id="invoiceHeader">
            <a href="home#invoice" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home#invoice" id="generateInvoicePageHeader" >
            	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
            </a>
</header>

<section class="block searchBox">
	
	<div class="container" id='loadingmessage' style='display:none;' align="middle">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
	<div class="container" id="mainPg1">
		<div class="card">
			<h4 class="text-center">Generate E-Way Bill</h4>
			
			
			<div class="text-danger"><b>Please do this before creating your first E-Way Bill:</b></div>
			<ul style="padding:5px 16px">
				<li>Go to NIC website- <a href="https://ewaybillgst.gov.in/login.aspx">click here</a></li>
				<li>Enter your NIC Login-Id & Password.</li>			
				<li>After successful login, in the menu click on <b>Registration</b> & then click on <b>For GSP</b>.</li>
				<li>You will see this page- <b>Register Your GST Suvidha Provider</b>. Choose the "Add/New" option on this page.</li>			
				<li>Click on dropdown for <b>GSP name</b> and choose <b>Reliance Corporate IT Park Limited</b>.</li>
				<li>Re-enter the username & password.</li>
				<li>Click on <b>Add</b> & you're done!!!</li>
			</ul>
			
			<div class="form-group input-field mandatory" id="gstnStateIdDiv" style="display:none">
                 <label class="label">
                        <select name="gstnStateId" class="form-control" id="gstnStateId">
              		  </select>
                        <div class="label-text">Select your GSTIN</div>
                 </label>
	        </div>
			
			<div class="form-group input-field mandatory" id="nicUserIdDiv">
				<label class="label">
					<input type="text" required class="form-control" id="nicUserId" >
					<div class="label-text label-text2">NIC User Id</div>
				</label>
				<span class="text-danger cust-error" id="nicUserId-csv-id">This field is required.</span>
			</div>

			<div class="form-group input-field mandatory" id="nicPwdDiv">
				<label class="label">
					<input type="password" required class="form-control" value="" id="nicPwd">
					<div class="label-text">NIC Password</div>
				</label>
				<span class="text-danger cust-error" id="nicPwd-csv-id">This field is required.</span>
			</div>
			
			<div class="form-group checkbox-inline" style="margin-top:10px;display:none" id="">
                 <span></span> 
                 <div class="checkbox checkbox-success">
                     <input type="checkbox" name="isCheckNicUserId" value="" id="isCheckNicUserId" checked>
                     <label for="isCheckNicUserId" id="" >Save my NIC User Id</label>
                 </div>
            </div>
            	
            	<input type="hidden" id="clientId" name="clientId" value="${clientId}">
				<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
				<input type="hidden" id="appCode" name="appCode" value="${appCode}">
				<input type="hidden" id="userId" name="userId" value="${userId}">
				<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
				<input type="hidden" id="email_id" name="email_id" value="${email_id}">
				<input type="hidden" id="mobile_number" name="mobile_number" value="${mobile_number}"> 
            <div class="form-group input-field text-center">
					<a id="validateId" href="javascript:void(0)" class="btn btn-primary">Validate</a>
			</div>
           
		</div>

	</div>
</section>
 
 <form name="manageInvoice" method="post">
    <input type="hidden" name="nicUserId" value="">
     <input type="hidden" name="nicPwd" value="">
      <input type="hidden" name="gstin" value="">
    <%-- <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> --%>
</form>

 <script type="text/javascript" src="<spring:url value="/resources/js/eWayBill/general.js"/>"></script>
  <script type="text/javascript" src="<spring:url value="/resources/js/eWayBillWI/validations.js"/>"></script>