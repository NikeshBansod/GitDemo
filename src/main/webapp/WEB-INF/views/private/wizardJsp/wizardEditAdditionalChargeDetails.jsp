<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/addadditionalChargeDetails/wizardManageAdditionalChargeDetails.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="additionalChargeDetails" method="post" action="./wizardUpdateAdditionalChargeDetails" >
   					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<div class="breadcrumbs"><a href="<spring:url value="/wizardAdditionalCharges"/>">Additional Charges Master</a> <span>&raquo;</span> Edit Additional Charge Details</div>
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-half astrick">
								<div class="label-text">Name of Additional Charge</div>
							  	<form:input path="chargeName" id="chargeName" maxlength="100" value="${addChargeDetailsObj.chargeName }" required="true" class="form-control" />
							  	<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
							</div>
							<div class="det-row-col-half astrick">
								<div class="label-text">Value</div>
							  	<form:input path="chargeValue" id="chargeValue" maxlength="18" value="${addChargeDetailsObj.chargeValue }" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="contact-no-req">This field is required</span>
							</div> 										
						</div>						
						<form:hidden path="id" value="${addChargeDetailsObj.id }"/>
					   <input type="hidden" id="editPage" value="true" />
					   <form:hidden path="status" value="${addChargeDetailsObj.status }"/>
			      	   <form:hidden path="createdOn" value="${addChargeDetailsObj.createdOn }"/>
			      	   <form:hidden path="createdBy" value="${addChargeDetailsObj.createdBy }"/>
			      	   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					</div>
				<div class="insidebtn"> 	        	
					<input id="chargesSubmitBtn" type="Submit" class="sim-button button5" value="Update" /> 
					<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
				</div>
           </form:form>
		
	</div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/sacCodeAjax.js"/>"></script>