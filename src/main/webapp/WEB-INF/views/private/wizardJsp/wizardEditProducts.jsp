<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="resources/js/wizardJS/manageGoodsCatalogue/wizardManageProduct.js"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="product" method="post" id="prdForm"  action="./wizardUpdateProduct" >
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<div class="breadcrumbs"><a href="<spring:url value="/wizardGetProducts"/>">Manage Goods Catalogue</a> <span>&raquo;</span> Edit Goods</div>
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">Search By HSN Code / HSN Description</div>
							  	<input type="text" id="search-hsn" maxlength="15" class="form-control"/>      
							  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By HSN Code / HSN Description.</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">HSN Description</div>
							  	<input type="text" disabled="disabled" value="${productObj.hsnDescription }" id="hsnDescriptionToShow" required class="form-control"/>
								<form:hidden path="hsnDescription" value="${productObj.hsnDescription }" id="hsnDescription" />
							  	<span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
							</div> 
							<div class="det-row-col astrick">
								<div class="label-text">HSN Code</div>
							  	<input type="text" disabled="disabled" value="${productObj.hsnCode }" id="hsnCodeToShow" required class="form-control"/>
								<form:hidden path="hsnCode" value="${productObj.hsnCode }" id="hsnCode" /> 
							  	<span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
							</div> 					
						</div>
						<div class="det-row"> 
							<div class="det-row-col astrick">
								<div class="label-text">Goods Name</div>
							  	<form:input path="name" id="name" maxlength="100" value="${productObj.name }" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="prod-name">This field is required.</span>
							</div>								
							<div class="det-row-col astrick">
								<div class="label-text">Unit Of Measurement</div>
							 	<form:select path="unitOfMeasurement" id="unitOfMeasurement" required="true" class="form-control"/> 
								<input type="hidden" id="unitOfMeasurementHidden" value="${productObj.unitOfMeasurement }" />
							  	<span class="text-danger cust-error" id="prod-uom">This field is required.</span>
							</div>  	
							<div class="det-row-col astrick"  id="divOtherUnitOfMeasurement">
								<div class="label-text">Please Specify</div>
								<form:input path="otherUOM" maxlength="30" value="${productObj.otherUOM}" class="form-control"/>
							 	<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
							</div>
							<div class="det-row-col astrick" >
								<div class="label-text">Goods Rate (Rate per unit of measurement)</div> 
								<form:input path="productRate" id="productRate" maxlength="18" value="${productObj.productRate }" required="true" class="form-control"/>
							 	<span class="text-danger cust-error" id="prod-rate">This field is required and should be numeric.</span>
							</div>
						</div>
						<div class="det-row">						
							<div class="det-row-col astrick">
							 	<div class="label-text">Rate of tax (%)</div>              	
							   	<select class="form-control" id="productIgst" name="productIgst" class="form-control">
								</select>  
								<span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
					       </div>
						</div>
					 	<form:hidden path="id" value="${productObj.id }"/>
					    <form:hidden path="referenceId" value="${productObj.referenceId }"/>
					    <form:hidden path="status" value="${productObj.status }"/>
					    <form:hidden path="createdBy" value="${productObj.createdBy }"/>
					    <form:hidden path="createdOn" value="${productObj.createdOn }"/>
					    <input type="hidden" id="rateOfTax" value="${productObj.productIgst}"/>
					    <input type="hidden" id="editPage" value="true" />
					    <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					    <form:hidden path="hsnCodePkId" id="hsnCodePkId" value="${manageServiceCatalogueObj.hsnCodePkId }"/>
					    <input type="hidden" name="tempUom" id="tempUom">
					</div>
				<div class="insidebtn"> 	        	
					<input id="prodSubmitBtn" type="Submit" class="sim-button button5" value="Update" /> 
					<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
				</div>
           </form:form>
		
	</div>
</section>
<script type="text/javascript" src="<spring:url value="/resources/js/products/hsnCodeAjax.js"/>"></script>