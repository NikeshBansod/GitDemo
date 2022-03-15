<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
 
<section class="insidepages">
	
	<div class="inside-cont">
		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 		<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="breadcrumbs">
			 <header class="insidehead" id="originalHeader">
	         	<a href="<spring:url value="javascript:backToEWayBillDocs()"/>" >E-Way Bill Documents</a> <span>&raquo;</span> Generate E-Way Bill
	 		 </header>
		</div>
		<div class="card" id="mainPg1">
			<%-- <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> --%>
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
				<div class="det-row" id="gstnStateIdDiv" style="display:none">
					<div class="det-row-col-half ">
		                 <div class="label-text">GSTIN</div>
		                    <select name="gstnStateId" class="form-control" id="gstnStateId"> </select>
			        </div>
		        </div>
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
			    			    
            	<input type="hidden" id="clientId" name="clientId" value="${clientId}">
				<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
				<input type="hidden" id="appCode" name="appCode" value="${appCode}">
				<input type="hidden" id="userId" name="userId" value="${userId}">
				<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
				<input type="hidden" id="email_id" name="email_id" value="${email_id}">
				<input type="hidden" id="mobile_number" name="mobile_number" value="${mobile_number}"> 				
			</div>			
		<div class="insidebtn"> 
			<a id="validateId" href="javascript:void(0)" class="sim-button button5">Validate</a>
	    </div>	
		</div>	    
	</div>	
</section>
 
 <form name="manageInvoice" method="post">
    <input type="hidden" name="nicUserId" value="">
     <input type="hidden" name="nicPwd" value="">
      <input type="hidden" name="gstin" value="">
</form>

 <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBill/general.js"/>"></script>
  <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBillWI/validations.js"/>"></script>