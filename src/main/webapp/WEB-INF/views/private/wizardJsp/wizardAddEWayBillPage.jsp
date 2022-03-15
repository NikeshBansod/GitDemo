<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
 
<section class="insidepages">
	
	<div class="inside-cont">
		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 		<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="breadcrumbs">
			 <header class="insidehead" id="originalHeader">
	         	<a href="<spring:url value="javascript:getWizardEWayBills('${invoiceId }')"/>" >E-Way Bill</a> <span>&raquo;</span> Generate E-Way Bill
	 		 </header>
		</div>
		<div class="card" id="mainPg1">
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		 	<div class="col-md-12">
				<div class="noteEwayBill card" align="center">
						<strong>Please do this before creating your first EWay Bill:</strong><br>
						1. Go to NIC website:&nbsp;<a href="https://ewaybillgst.gov.in/login.aspx" target="_blank">Click Here!</a><br>				
						2. Enter your NIC login Id & Password.<br>					
						3. After successful login, in the menu click on ‘’Registration’’ & then click on "For GSP".<br>		
						4. You will see this page: "Register Your GST Suvidha Provider". Choose the "Add/New" option on this page.<br>					
						5. Click on the dropdown for "GSP name" and choose: "Reliance Corporate IT Park Limited".<br>					
						6. Re-enter the username & password.<br>					
						&nbsp;&nbsp;&nbsp;&nbsp;Click on 'Add' & you're done<br> 							
	            </div>         
	     	</div>      		     	    
			<div class="account-det">
				<input type="hidden" id="uiInvoice" value="${ invoiceId}">
				<input type="hidden" id="uiGstnX" value="${ gstn}">
				<div class="det-row"  id="nicUserIdDiv">
			    	<div class="det-row-col-half astrick">
		            	<div class="label-text">NIC User Id</div>
		              	<input type="text" required class="form-control" id="nicUserId" value="${ nicUserDetails.userId}"> 
		              	<span class="text-danger cust-error" id="nicUserId-csv-id">This field is required.</span>
		            </div>
		            <div class="det-row-col-half astrick">
		            	<div class="label-text">NIC Password</div>
		              	<input type="password" required class="form-control" value="" id="nicPwd">
		              	<span class="text-danger cust-error" id="nicPwd-csv-id">This field is required.</span>
		            </div>
				</div>
				<div class="det-row" style="margin-top:10px;display:none" id="">
		            <div class="det-row-col-full astrick">
		            	<div class="label-text">Save my NIC User Id</div>
		              	 <input type="checkbox" name="isCheckNicUserId" value="" id="isCheckNicUserId" checked>
		            </div>
			    </div>	
				<div class="insidebtn"> 
					<a id="validateId" href="javascript:void(0)" class="sim-button button5">Validate</a>
			    </div>	
			    
			    <div id="openCloseDiv" style="display:block">
			        <div class="account-det">
						<div class="det-row">
					    	<div class="det-row-col-half astrick">
				            	<div class="label-text">Kms To Be Travelled</div>
				             	<input type="text" required class="form-control" value="" id="kmsToBeTravelled">
				              	<span class="text-danger cust-error" id="kmsToBeTravelled-csv-id">This field is required.</span>
				            </div>
				            <div class="det-row-col-half astrick">
				            	<div class="label-text">Mode Of Transport</div>
				              	 <select id="modeOfTransport" name="" class="form-control">
			                     	<option value="">Select Mode of Transport</option>
			                     	<option value="1">Road</option>
			                     	<option value="2">Rail</option>
			                     	<option value="3">Air</option>
			                     	<option value="4">Ship</option>
			                     </select>
			                	<span class="text-danger cust-error" id="modeOfTransport-csv-id">This field is required.</span>
				            </div>	
						</div>
						<div class="det-row" id="vehicleTypeNoDiv">			           	              
		                	<div class="det-row-col-half" style="margin:20px 0 0 0" id="vehicleTypeDiv"> Vehicle Type &nbsp;&nbsp;&nbsp;
				              <input type="radio" name="vehicleType" value="Regular" id="radio2" checked="checked">
				              <label for="radio2"> Regular</label>
				               <input type="radio" name="vehicleType" value="overDimensionalCargo" id="radio1" >
				              <label for="radio1">Over Dimensional Cargo</label>
				            </div>  
					    	<div class="det-row-col-half astrick" id="vehicleNoDiv">
				            	<div class="label-text" id="vehicleNoLabel">Vehicle No</div>
				             	<input type="text" required class="form-control" id="vehicleNo" value="">
				              	<span class="text-danger cust-error" id="vehicleNo-csv-id">This field is required.</span>
				            </div>  
					    </div>	
					    <div class="det-row">
					    	<div class="det-row-col-half astrick" id="docNoDiv">
				            	<div class="label-text" id="docNoLabel">Transporter Doc No</div>
				             	<input type="text" id="docNo" required class="form-control" value="">
				              	<span class="text-danger cust-error" id="docNo-csv-id">This field is required.</span>
				            </div>
				            <div class="det-row-col-half astrick"  id="docDateDiv">
				            	<div class="label-text" id="docDateLabel">Document Date</div>
				              	<input type="text" name="" id="doc_date" readonly="readonly" required class="form-control">  
				              	<!-- <span class="text-danger cust-error" id="transporterName-csv-id">This field is required.</span> -->
				            </div>
						</div>
					   	<div class="det-row" id="transporterNameIdDiv">
				            <div class="det-row-col-half "  id="transporterNameDiv">
				            	<div class="label-text" id="transporterNameLabel">Transporter Name</div>
				              	<input type="text" id="transporterName" class="form-control" value="">
				              	<span class="text-danger cust-error" id="transporterName-csv-id">This field is required.</span>
				            </div>
					    	<div class="det-row-col-half " id="transporterIdDiv">
				            	<div class="label-text" id="transporterIdLabel">Transporter Id</div>
				             	<input type="text"  id="transporterId" class="form-control" value="">
				              	<span class="text-danger cust-error" id="transporterId-csv-id">This field is required.</span>
				            </div>
						</div>
			        </div>
			        <div class="insidebtn"> 
			        	<a id="submitId" href="javascript:void(0)" class="sim-button button5">Generate E-Way Bill</a>
			    	</div>	
				</div>	        
			</div>
		</div>	
		
		 		<input type="hidden" id="clientId" name="clientId" value="${clientId}">
				<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
				<input type="hidden" id="appCode" name="appCode" value="${appCode}">
				<input type="hidden" name="userId" id="userId" value="${userId }">
				<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
				
		<!-- 
		<div class="card" id="mainPg2">
			
		</div> -->
	</div>	
</section>
 
 <form name="manageInvoice" method="post">
    <input type="hidden" name="nicUserId" value="">
     <input type="hidden" name="nicPwd" value="">
      <input type="hidden" name="gstin" value="">
</form>

 <form name="manageInvc" method="post">
    <input type="hidden" name="id" value="">
      <input type="hidden" id="_csrf_token" name="_csrf_token" value="" />
</form>
 <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBill/general.js"/>"></script>
  <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBill/validations.js"/>"></script>