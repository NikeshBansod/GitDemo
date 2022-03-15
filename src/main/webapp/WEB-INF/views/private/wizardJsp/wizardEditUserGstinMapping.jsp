<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/UserGstinMapping/wizardEditUserGstinMapping.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/UserGstinMapping/wizardManageUserGstinMapping.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="userGstinMapping"  method="post" action="./wizardUpdateUserGstinMapping" >
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<div class="breadcrumbs"><a href="<spring:url value="/wizardGetUserGstinMap"/>">Manage GSTIN User Mapping </a> <span>&raquo;</span> Edit GSTIN User Mapping</div>
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN</div>
							  	<form:hidden id="gstinId" path="gstinId" value="${userGstinMappingObj.gstinId }"/>
						    	<input type="text" disabled="true" id="selectGstinId" class="form-control">
							  	<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">Store/Location/Channel/Department</div>
							  	<input type="hidden" id="selectedGSTINAddr" value="${userGstinMappingObj.gstinAddressMapping.id }">
								<form:select path="gstinAddressMapping.id" id="gstinAddressMapping" class="form-control">
                   				</form:select>
							  	<span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
							</div> 
							<div class="det-row-col astrick">
								<div class="label-text">Choose Employee</div>
							  	<input type="hidden" id="selectedGSTIN" value="${userGstinMappingObj.gstinUserIds }">
						  		<form:select path="gstinUserIds" id="gstinUserSet" multiple="true" class="form-control">
                   				</form:select>  
							  	<span class="text-danger cust-error" id="selectGSTINDetail-req"></span>
							</div> 					
						</div>
						<form:hidden path="id" value="${userGstinMappingObj.id }"/>
					   <form:hidden path="referenceUserId" value="${userGstinMappingObj.referenceUserId }"/>
					   <form:hidden path="status" value="${userGstinMappingObj.status }"/>
					   <form:hidden path="createdBy" value="${userGstinMappingObj.createdBy }"/>
					   <form:hidden path="createdOn" value="${userGstinMappingObj.createdOn }"/>
					   <form:hidden path="status" value="${userGstinMappingObj.status }"/>
					   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					   <input type="hidden" id="editPage" value="true" />
					</div>
				<div class="insidebtn"> 	        	
					<input id="submitGstinMapping" type="Submit" class="sim-button button5" value="Update" /> 
				</div>
           </form:form>
		
	</div>
</section>
<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>