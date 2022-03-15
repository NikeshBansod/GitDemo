<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/manageServiceCatalogue/wizardManageServiceCatalogue.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="manageServiceCatalogue" id="servForm" method="post" action="./wizardUpdateManageServiceCatalogue" >
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<div class="breadcrumbs"><a href="<spring:url value="/wizardManageServiceCatalogue"/>">Manage Service Catalogue</a> <span>&raquo;</span> Edit Service</div>
				<div class="account-det">
					<div class="det-row">	
						<div class="det-row-col astrick">
							<div class="label-text">Search By SAC Code / SAC Description</div>
						  	<input type="text" id="search-sac" maxlength="15" class="form-control" /> 
						  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By SAC Code / SAC Description</span>
						</div>
						<div class="det-row-col astrick">
							<div class="label-text">SAC Description</div>
						  	<input type="text" value="${manageServiceCatalogueObj.sacDescription }" readonly="readonly" id="sacDescriptionToShow" class="form-control" /> 
							<form:hidden path="sacDescription" value="${manageServiceCatalogueObj.sacDescription }" id="sacDescription" />
						  	<!-- <span class="text-danger cust-error" id="ser-sac-desc">This field is required.</span> -->
						</div> 
						<div class="det-row-col astrick">
							<div class="label-text">SAC Code</div>
						  	<input type="text" disabled="disabled" value="${manageServiceCatalogueObj.sacCode }" readonly="readonly" id="sacCodeToShow" class="form-control" />
							<form:hidden path="sacCode" id="sacCode" value="${manageServiceCatalogueObj.sacCode }"/> 
						  	<!-- <span class="text-danger cust-error" id="ser-sac-code">This field is required.</span> -->
						</div> 					
					</div>
					<div class="det-row"> 
						<div class="det-row-col astrick">
							<div class="label-text">Service Name</div>
						  	<form:input path="name" maxlength="100" id="name" value="${manageServiceCatalogueObj.name }" required="true" class="form-control" /> 
						  	<!-- <span class="text-danger cust-error" id="ser-name">This field is required.</span> -->
						</div>								
						<div class="det-row-col astrick">
							<div class="label-text">Unit Of Measurement</div>
						 	<form:select path="unitOfMeasurement" id="unitOfMeasurement" required="true" class="form-control"/> 
							<input type="hidden" id="unitOfMeasurementHidden" value="${manageServiceCatalogueObj.unitOfMeasurement }" />
						</div>  	
						<div class="det-row-col astrick"  id="divOtherUnitOfMeasurement">
							<div class="label-text">Please Specify</div>
							<form:input path="otherUOM" maxlength="30" value="${manageServiceCatalogueObj.otherUOM}" class="form-control"/>
						 	<!-- <span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span> -->
						</div>
						<div class="det-row-col astrick" >
							<div class="label-text">Service Rate (Rs.)</div> 
							<form:input path="serviceRate" id="serviceRate" maxlength="18" value="${manageServiceCatalogueObj.serviceRate }" required="true" class="form-control" />
						 	<!-- <span class="text-danger cust-error" id="ser-rate">This field is required.</span> -->
						</div>
					</div>
					<div class="det-row">						
						<div class="det-row-col astrick">
						 	<div class="label-text">Rate of tax (%)</div>              	
						   	<form:select path="serviceIgst" id="serviceIgst" required="true" class="form-control"/>
							<!-- <span class="text-danger cust-error" id="service-igst">This field is required.</span> -->
				       </div>
					</div>
					<form:hidden path="id" value="${manageServiceCatalogueObj.id }"/>
					<form:hidden path="referenceId" value="${manageServiceCatalogueObj.referenceId }"/>
					<form:hidden path="status" value="${manageServiceCatalogueObj.status }"/>
					<form:hidden path="createdBy" value="${manageServiceCatalogueObj.createdBy }"/>
					<form:hidden path="createdOn" value="${manageServiceCatalogueObj.createdOn }"/>
					<input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					<input type="hidden" id="editPage" value="true" />
					<input type="hidden" id="serviceRateOfTax" value="${manageServiceCatalogueObj.serviceIgst}"/>
					<form:hidden path="sacCodePkId" id="sacCodePkId" value="${manageServiceCatalogueObj.sacCodePkId }"/>
					<input type="hidden" name="tempUom" id="tempUom">
				</div>
				<div class="insidebtn"> 	        	
					<input id="srvSubmitBtn" type="Submit" class="sim-button button5" value="Update" /> 
					<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
				</div>
           </form:form>
		
	</div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/sacCodeAjax.js"/>"></script>
<style>
#ui-id-1{width:90% !important;}
</style>