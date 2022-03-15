<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/gstinDetails/wizardAddGstinDetails.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/gstinDetails/editGstinDetails.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="gstinDetails"  method="post" action="./wizardUpdateGstinDetails" >
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />

				<div class="breadcrumbs"><a href="<spring:url value="/wizardListGstinDetails"/>">Manage GSTIN </a> <span>&raquo;</span> Edit GSTIN Details</div>
				<div class="account-det">
					<div class="det-row">
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN (As on GSTN)</div>
							  	<form:input path="gstinNo" id="reg-gstin" maxlength="15" value="${gstinDetailsObj.gstinNo }" class="form-control"/>
							  	<span class="text-danger cust-error" id="reg-gstin-req"></span>
								<span class="text-danger cust-error" id="reg-gstin-back-req"></span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN State</div>
							  	<form:select id="reg-gstin-state" path="state" itemValue="${gstinDetailsObj.state }" class="form-control" >
								</form:select> 
							</div>	
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN User Name (As on GSTN)</div>
							  	<form:input path="gstinUname" value="${gstinDetailsObj.gstinUname }" id="gstinUname" maxlength="200" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="gstinUname-req"></span>
								<span class="text-danger cust-error" id="gstinUname-back-req"></span>
							</div>
						</div>
						<div class="det-row"> 
							<div class="det-row-col">
								<div class="label-text">Registered Address of GSTIN (max 500 characters)</div>
							  	<form:textarea path="gstinAddressMapping.address" maxlength="500" id="address1"  value="${gstinDetailsObj.gstinAddressMapping.address }" class="form-control"/>
							    <span class="text-danger cust-error" id="address1-req">This field is required.</span>
							</div>	
							<c:forEach items="${gstinDetailsObj.gstinLocationSet}" var="item" varStatus="loop">
								<div class="det-row-col astrick gstinLocation" id="dynamiclocDiv0">
									<div class="label-text">Location/Channel/Department - ${(loop.index) +1} :</div>
								 	<form:input path="gstinLocationSet[${loop.index}].gstinLocation" type="text" id="gstinLocation" class="form-control" value="${item.gstinLocation}"/>
					                <form:hidden path="gstinLocationSet[${loop.index}].id"  class="form-control" value="${item.id}"/>
					                <form:hidden path="gstinLocationSet[${loop.index}].uniqueSequence"  class="form-control" value="${item.uniqueSequence}"/>
					                <form:hidden path="gstinLocationSet[${loop.index}].refGstinId"  class="form-control" value="${item.refGstinId}"/>
								  	<span class="text-danger cust-error" id="gstinLocation-empty"></span>
								</div>  	
								<%-- <div hidden="true" class="det-row-col astrick gstinStore" id="dynamicStoreDiv0">
									<div class="label-text">Store</div>
									<form:hidden path="gstinLocationSet[0].gstinStore"  id="gstinStore" class="form-control" />
								 	<span class="text-danger cust-error" id="gstinStore-empty"></span>
								</div> --%>
							</c:forEach>						
						</div>
						<div class="det-row">
							<div class="det-row-col astrick">
							 	<div class="label-text">Pin Code </div>              	
							   	<form:input id="pinCode" path="gstinAddressMapping.pinCode" maxlength="6" required="true" value="${gstinDetailsObj.gstinAddressMapping.pinCode }" class="form-control"/>
							    <span class="text-danger cust-error" id="zip-req">This field is required and should be 6 digits</span>
		                        <span class="text-danger cust-error" id="empty-message"></span>
					       </div>
					       <div class="det-row-col astrick">
					        	<div class="label-text ">City </div>
					          	<form:input id="city" readonly="true" path="gstinAddressMapping.city" required="false" value="${gstinDetailsObj.gstinAddressMapping.city }" class="form-control"/>
							</div>
							<div class="det-row-col astrick">
							 	<div class="label-text">Country </div>
							   	 <form:input id="country" readonly="true" path="gstinAddressMapping.country" value="${gstinDetailsObj.gstinAddressMapping.country }" required="true" class="form-control"/>
							</div>	
							<div class="det-row-col astrick"  hidden="true" >
							 	<div class="label-text">State </div>
							    <form:hidden id="state" readonly="true" path="gstinAddressMapping.state" value="${gstinDetailsObj.gstinAddressMapping.state }" required="true" class="form-control"/>
							   	<span class="text-danger cust-error" id="state-req">This field is required</span>   
							</div>
						</div>
					
					<form:hidden path="id" value="${gstinDetailsObj.id }"/>
				    <form:hidden path="createdBy" value="${gstinDetailsObj.createdBy }"/>
				    <form:hidden path="createdOn" value="${gstinDetailsObj.createdOn }"/>
				    <form:hidden path="referenceId" value="${gstinDetailsObj.referenceId }"/>
				    <form:hidden path="status" value="${gstinDetailsObj.status }"/>
				    <form:hidden path="uniqueSequence" value="${gstinDetailsObj.uniqueSequence }"/>
				    <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
				    <form:hidden path="gstinAddressMapping.id" value="${gstinDetailsObj.gstinAddressMapping.id }"/>
				</div>
				<div class="insidebtn"> 	        	
					<input id="submitGstin" type="Submit" class="sim-button button5" value="Update" /> 
				</div>
           </form:form>
		
	</div>
</section>
<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>