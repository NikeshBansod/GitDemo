<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<header class="insidehead" id="">
            <a href="#" onclick="javascript:getEWayBills('${invoiceId }')" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home#invoice" id="generateInvoicePageHeader" >
            	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
            </a>
</header>

<section class="block searchBox">
	
	<div class="container" id='loadingmessage' style='display:none;' align="middle">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
	<div class="container" id="mainPg1">
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
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
			
			<input type="hidden" id="uiInvoice" value="${ invoiceId}">
			<input type="hidden" id="uiGstnX" value="${ gstn}">
			<div class="form-group input-field mandatory" id="nicUserIdDiv">
				<label class="label">
					<input type="text" required class="form-control" id="nicUserId" value="${ nicUserDetails.userId}">
					<div class="label-text">NIC User Id</div>
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
            
            <div class="form-group input-field text-center">
					<a id="validateId" href="javascript:void(0)" class="btn btn-primary">Validate</a>
			</div>
            
            <div id="openCloseDiv" style="display:block">

				<div class="form-group input-field mandatory">
					<label class="label">
					 	<input type="text" required class="form-control" value="" id="kmsToBeTravelled">
						<div class="label-text">Kms To Be Travelled</div>
					</label>
					<span class="text-danger cust-error" id="kmsToBeTravelled-csv-id">This field is required.</span>
				</div>
				
			    <div class="form-group input-field mandatory">
	               <label class="label">
	                    <select id="modeOfTransport" name="" class="form-control">
	                     	<option value="">Select Mode of Transport</option>
	                     	<option value="1">Road</option>
	                     	<option value="2">Rail</option>
	                     	<option value="3">Air</option>
	                     	<option value="4">Ship</option>
	                    </select>  
	                <div class="label-text label-text2">Mode Of Transport</div>
	                </label>
	                <span class="text-danger cust-error" id="modeOfTransport-csv-id">This field is required.</span>
	            </div>
	            
                <div class="radio-inline text-left" style="margin:20px 0 0 0" id="vehicleTypeDiv">
                      <span>Vehicle Type</span>
                      <div class="rdio rdio-success">
                          <input type="radio" name="vehicleType" value="Regular" id="radio2" checked="checked">
                          <label for="radio2">Regular</label>
                      </div>
                      <div class="rdio rdio-success">
                        
                          <input type="radio" name="vehicleType" value="overDimensionalCargo" id="radio1" >
                          <label for="radio1">Over Dimensional Cargo</label>
                      </div>
                </div>
                <br/>
	
				<div class="form-group input-field mandatory" id="vehicleNoDiv">
					<label class="label"> 
						<input type="text" required class="form-control" id="vehicleNo" value="">
						<div class="label-text" id="vehicleNoLabel">Vehicle No</div>
					</label>
					<span class="text-danger cust-error" id="vehicleNo-csv-id">This field is required.</span>
				</div>
				
				<div class="form-group input-field " id="transporterNameDiv">
					<label class="label"> 
						<input type="text" id="transporterName" class="form-control" value="">
						<div class="label-text" id="transporterNameLabel">Transporter Name</div>
					</label>
					<span class="text-danger cust-error" id="transporterName-csv-id">This field is required.</span>
				</div>
				
				<div class="form-group input-field mandatory" id="docNoDiv">
					<label class="label"> 
						<input type="text" id="docNo" required class="form-control" value="">
						<div class="label-text" id="docNoLabel">Transporter Doc No</div>
					</label>
					<span class="text-danger cust-error" id="docNo-csv-id">This field is required.</span>
				</div>
				
				<div class="form-group input-field mandatory" id="docDateDiv">
	                <label class="label">
	                    <input type="text" name="" id="doc_date" readonly="readonly" required class="form-control">  
	                    <div class="label-text label-text2" id="docDateLabel">Document Date</div>
	                </label>
	                <!-- <span class="text-danger cust-error" id="invoice-date-csv-id">This field is required.</span> -->
	            </div>
				
				<div class="form-group input-field " id="transporterIdDiv">
					<label class="label"> 
						<input type="text"  id="transporterId" class="form-control" value="">
						<div class="label-text" id="transporterIdLabel">Transporter Id</div>
					</label>
					<span class="text-danger cust-error" id="transporterId-csv-id">This field is required.</span>
				</div>
				
				
	
				<div class="form-group input-field text-center">
					<a id="submitId" href="javascript:void(0)" class="btn btn-primary">Generate E-Way Bill</a>
					
				</div>
			
			</div>

		</div>

	</div>
</section>
 
 <form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

 <script type="text/javascript" src="<spring:url value="/resources/js/eWayBill/general.js"/>"></script>
  <script type="text/javascript" src="<spring:url value="/resources/js/eWayBill/validations.js"/>"></script>