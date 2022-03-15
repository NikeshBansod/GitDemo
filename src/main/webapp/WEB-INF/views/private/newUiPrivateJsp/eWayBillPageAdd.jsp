<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="block">
	<div class="loader"></div>
		<div class="container" id="mainPg1">
			<div class="brd-wrap">
				<div id="breadcumheader2" >
				  <a href="#"  onclick="javascript:getEWayBills('${invoiceId }')" ><strong>E-Way Bill List</strong></a> &raquo; <strong>E-Way Bill Details</strong>
		 		</div>
            </div>
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />			
			<div class="form-wrap">
				<div id="nicLoginDetails">
					<div class="row">
                        <div class="col-md-12 genratebill">
                            <h2 class="nicusertitle">Please do this before creating your first EWay Bill:</h2>
                            <ul class="list-display list-checkmarks">
                                <li>Go to NIC website: https://ewaybillgst.gov.in/login.aspx</li>
                                <li>Enter your NIC login Id &amp; Password.</li>
                                <li>After successful login, in the menu click on ‘’Registration’’ &amp; then click on "For GSP".</li>
                                <li>You will see this page: "Register Your GST Suvidha Provider". Choose the "Add/New" option on this page.</li>
                                <li>Click on the dropdown for "GSP name" and choose: "Reliance Corporate IT Park Limited".</li>
                                <li>Re-enter the username &amp; password.</li>
                            </ul>
                            <span>    Click on 'Add' &amp; you're done</span>
                        </div>                        
             		</div>             						
					<div class="row" >
                        <div class="col-md-6" id="nicUserIdDiv">
                        	<label for="">NIC User Id<span> *</span></label>
                            <input type="text" required id="nicUserId" value="${ nicUserDetails.userId}">
                            <span class="text-danger cust-error" id="nicUserId-csv-id">This field is required.</span>
                        </div>
                        <div class="col-md-6" id="nicPwdDiv">
                            <label for="">NIC Password<span> *</span></label>
                            <input type="password" required value="" id="nicPwd">
                           <span class="text-danger cust-error" id="nicPwd-csv-id">This field is required.</span>
                        </div>
	                    <input type="hidden" id="uiInvoice" value="${ invoiceId}">
                   </div>                    
                   <div class="row">
                   	   <div class="col-md-12 button-wrap">
                       		<button type="button" id="validateId" class="btn btn-success blue-but">Validate</button>
                       </div>
               	   </div>                
                   <div style="margin-top:10px;display:none" id="">
                    	<input type="checkbox" name="isCheckNicUserId" value="" id="isCheckNicUserId" checked>
				   </div>
   			 	</div>
            	<div id="openCloseDiv" style="display:block">
		            <div class="row">
			             <div class="col-md-6" >
				             <label for="">Kms To Be Travelled<span> *</span></label>
				             <input type="text" required id="kmsToBeTravelled" value="">
							 <span class="text-danger cust-error" id="kmsToBeTravelled-csv-id">This field is required.</span>
						</div>
		           
			            <div class="col-md-6" >
		               	<label for="">Mode Of Transport</label>
		                    <select id="modeOfTransport" name="" >
		                     	<option value="">Select Mode of Transport<span> *</span></option>
		                     	<option value="1">Road</option>
		                     	<option value="2">Rail</option>
		                     	<option value="3">Air</option>
		                     	<option value="4">Ship</option>
		                    </select>  
		                <span class="text-danger cust-error" id="modeOfTransport-csv-id">This field is required.</span>
		            	</div>
	             	</div>
	             	 <div class="row" id="vehicleTypeDiv"> <!-- id="vehicleTypeNoDiv" -->	             	 
		             	 <div class="col-md-6 diff">
	                     	<label class="form-section-title">Vehicle Type</label>
	                       	<div class="radio radio-success radio-inline">
	                        	<input type="radio" class="styled" name="vehicleType" value="Regular" id="radio2" checked="checked" />
	                         	<label for="radio1">Regular</label>
	                      	</div>
	                      	<div class="radio radio-success radio-inline">
	                          <input type="radio" class="styled" name="vehicleType" value="overDimensionalCargo" id="radio1" >
	                          <label for="radio1">Over Dimensional Cargo</label>
	                      	</div>	                      
		                </div>
		                <div class="col-md-6 diff">
			                <div id="vehicleNoDiv">
			                	<label class="form-section-title">Vehicle No <span>*</span></label>
								<input type="text" required id="vehicleNo" value="">
								<span class="text-danger cust-error" id="vehicleNo-csv-id">This field is required.</span>
							</div>
					    </div>
	             	 </div>
	             	 <div class="row" >
	             	 <div class="col-md-6" id="docNoDiv">
				            	<div class="label-text" id="docNoLabel">Transporter Doc No<span> *</span></div>
				             	<input type="text" id="docNo" required value="">
				              	<span class="text-danger cust-error" id="docNo-csv-id">This field is required.</span>
				            </div>
				            <div class="col-md-6"  id="docDateDiv">
				            	<div class="label-text" id="docDateLabel">Document Date <span> *</span></div>
				              	<input type="text" name="" id="doc_date" readonly="readonly" required >  
				              	<!-- <span class="text-danger cust-error" id="transporterName-csv-id">This field is required.</span> -->
				            </div>
	             	 </div>
	             	 <div class="row" id="transporterNameIdDiv">
				            <div class="col-md-6"  id="transporterNameDiv">
				            	<div class="label-text" id="transporterNameLabel">Transporter Name</div>
				              	<input type="text" id="transporterName"  value="">
				              	<span class="text-danger cust-error" id="transporterName-csv-id">This field is required.</span>
				            </div>
					    	<div class="col-md-6"  id="transporterIdDiv">
				            	<div class="label-text" id="transporterIdLabel">Transporter Id</div>
				             	<input type="text"  id="transporterId"  value="">
				              	<span class="text-danger cust-error" id="transporterId-csv-id">This field is required.</span>
				            </div>
						</div>
						
						<div class="form-group input-field text-center">
							<a id="submitId" href="javascript:void(0)" class="btn btn-primary">Generate E-Way Bill</a>
						</div>
	             	
			</div>
                
		 		<input type="hidden" id="clientId" name="clientId" value="${clientId}">
				<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
				<input type="hidden" id="appCode" name="appCode" value="${appCode}">
				<input type="hidden" name="userId" id="userId" value="${userId }">
				<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">				
				<input type="hidden" id="uiGstnX" value="${ gstn}">	
			</div>
			</div>
       </section>
			
 <form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/eWayBill/general.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/eWayBill/validations.js"/>"></script>
  