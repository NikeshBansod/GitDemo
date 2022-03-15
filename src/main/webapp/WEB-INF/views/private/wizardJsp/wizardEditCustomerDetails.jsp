<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/addCustomerDetails/editcustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/manageCustomerDetailsMaster/wizardManageCustomerDetails.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="customerDetails"  method="post" action="./wizardUpdateCustomerDetails" >
			
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			
				<div class="breadcrumbs"><a href="<spring:url value="/wizardCustomerDetails"/>">Manage Customer Details</a> <span>&raquo;</span> Edit Customer Details</div>
				<div class="account-det">
					<div class="det-row">	
						
						<c:if test="${customerDetailsObj.custType == 'Organization'}">
						 	<div class="det-row-col-half">Customer Type <br>
						      <input type="radio" name="custType" value="Individual" id="Individual" >
						      <label for="Individual"> Not Registered Under GST</label>
						      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="custType" value="Organization" id="Organization" checked="checked">
						      <label for="Organization">Registered Under GST</label>
							</div>  
						</c:if>
						<c:if test="${customerDetailsObj.custType == 'Individual'}">
						 	<div class="det-row-col-half">Customer Type <br>
						      <input type="radio" name="custType" value="Individual" id="Individual" checked="checked" >
						      <label for="Individual">Not Registered Under GST</label>
						      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="custType" value="Organization" id="Organization"  >
						      <label for="Organization">Registered Under GST</label>
							</div>  
						</c:if>
					</div> 	
					<div class="det-row">
					<div class="det-row-col astrick" id="divGstinNo" style="">	<!--display: none; float: right;  -->
							<div class="label-text">GSTIN</div>
							<form:input maxlength="15"  path="custGstId" value="${customerDetailsObj.custGstId }" class="form-control"  />
						 	<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
							<input type="hidden" id="custGstinHidden" value="${customerDetailsObj.custGstId }">  
						</div>
						<div class="det-row-col astrick" id="divGstinState" style="">	<!--display: none; float: right;  -->
							<div class="label-text">GSTIN State</div> 
							<form:select path="custGstinState"  id="custGstinState" class="form-control"/>
						 	<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
							<input type="hidden" id="custGstinStateHidden" value="${customerDetailsObj.custGstinState }"> 
						</div>
						<div class="det-row-col astrick">
							<div class="label-text">Customer Name </div>
						  	<form:input path="custName" id="custName" maxlength="100" value="${customerDetailsObj.custName }" required="true" class="form-control" />  
						  	<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
						</div>
						
						<div class="det-row-col">
							<div class="label-text">Mobile Number </div>
						  	<form:input path="contactNo" id="contactNo" value="${customerDetailsObj.contactNo }"  maxlength="10" class="form-control"/>
						  	<span class="text-danger cust-error" id="contact-no-req">This field is required.</span>
						</div>					
						<div class="det-row-col ">
							<div class="label-text">Email ID </div>
						 	<form:input path="custEmail" maxlength="100" id="custEmail" value="${customerDetailsObj.custEmail }" class="form-control"/>
						  	<span class="text-danger cust-error" id="cust-email-format">Email should be in correct format.</span>
						</div>  	
						
					</div>
					<div class="det-row">
						<div class="det-row-col astrick">
						 	<div class="label-text">Pin Code </div>              	
						   	<form:input path="pinCode" id="pinCode" value="${customerDetailsObj.pinCode }" maxlength="6" required="true" class="form-control"/>
						    <span class="text-danger cust-error" id="empty-message"></span>
							<span class="text-danger cust-error" id="cust-zip-req">Pin Code is required and should be 6 digits.</span>
				       </div>
				       <div class="det-row-col">
				        	<div class="label-text astrick">City </div>
				          	<form:input path="custCity" readonly="true" id="custCity" value="${customerDetailsObj.custCity }" required="true" class="form-control"/>
						   	<span class="text-danger cust-error" id="city-req">This field is required</span>  
						</div>
						<div class="det-row-col astrick">
						 	<div class="label-text">State </div>
						   	<form:input path="custState" readonly="true" id="custState" value="${customerDetailsObj.custState }" required="true" class="form-control"/>
						   	<span class="text-danger cust-error" id="custState-err"></span>
						</div>
					</div>
					<div class="det-row">
						<div class="det-row-col astrick">
						 	<div class="label-text">Country </div>
						   	<form:input path="custCountry" readonly="true" value="${customerDetailsObj.custCountry }" required="true" class="form-control"/>
						</div>
						<div class="det-row-col astrick" id="divAddr" >	<!--display: none; float: right;  -->
							<div class="label-text">Address</div>							
							<form:textarea path="custAddress1" id="custAddress1" maxlength="350"  class="form-control"/>
						 	<span class="text-danger cust-error" id="address1-req">This field is required </span>
						 	<input type="hidden" id="custAddress" value="${customerDetailsObj.custAddress1 }">  
						</div>
						
					</div>
					
					<form:hidden path="id" value="${customerDetailsObj.id }"/>
				    <form:hidden path="status" value="${customerDetailsObj.status }"/>
		      	    <form:hidden path="createdOn" value="${customerDetailsObj.createdOn }"/>
		      	    <form:hidden path="createdBy" value="${customerDetailsObj.createdBy }"/>
		      	    <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
				</div>
				<div class="insidebtn"> 	        	
					<input id="custSubmitBtn" type="Submit" class="sim-button button5" value="Update" /> 
					<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
				</div>
           </form:form>
		
	</div>
</section>
<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>