<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/gstinDetails/wizardAddGstinDetails.js"/>"></script> 

<!-- <div class="lk-wrapper">
	<div class="lk-content"> -->
				
		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<a href="<spring:url value="/wizardListGstinDetails"/>">Manage GSTIN</a> <span>&raquo;</span> Add GSTIN
			</div>	
					
			<div id="addGSTINDetails">
				<form:form commandName="gstinDetails" id="saveGstin" method="post" action="./wizardSaveGstinDetails" >	
				
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			    	<br>
					<div class="account-det">
						<div class="det-row">
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN (As on GSTN)</div>
							  	<form:input path="gstinNo" maxlength="15" id="reg-gstin" class="form-control"/>
							  	<span class="text-danger cust-error" id="reg-gstin-req">This field is required and should be in a proper format</span>
								<span class="text-danger cust-error" id="reg-gstin-back-req"></span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN State</div>
							  	<form:select id="reg-gstin-state"  path="state" class="form-control">
								</form:select>	
							  	<span class="text-danger cust-error" id="reg-gstin-state-reg">This field is required</span>
							</div>	
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN User Name (As on GSTN)</div>
							  	<form:input path="gstinUname" id="gstinUname" maxlength="200" class="form-control"/>
							  	<span class="text-danger cust-error" id="reg-gstin-user-req">This field is required.</span>
							</div>
						</div>
						<div class="det-row"> 
							<div class="det-row-col">
								<div class="label-text">Registered Address of GSTIN (max 500 characters)</div>
							  	<form:textarea path="gstinAddressMapping.address" maxlength="500"  id="address1" class="form-control"/>
							  	<span class="text-danger cust-error" id="address1-req">This field is required.</span>
							</div>	
							<div id="dynamicDiv">
								<div class="det-row-col astrick gstinLocation" id="dynamiclocDiv0">
									<div class="label-text">Store/Location/Channel/Department: 1</div>
								 	<form:input path="gstinLocationSet[0].gstinLocation" type="text" id="gstinLocation" class="form-control" />
								  	<span class="text-danger cust-error" id="gstinLocation-empty"></span>
								</div>  	
								<div hidden="true" class="det-row-col astrick gstinStore" id="dynamicStoreDiv0">
									<div class="label-text">Store</div>
									<form:hidden path="gstinLocationSet[0].gstinStore"  id="gstinStore" class="form-control" />
								 	<span class="text-danger cust-error" id="gstinStore-empty"></span>
								</div>
								<div class="det-row-col"> 	
									<div class="label-text">&nbsp;</div>        	
									<input id="addButton" type="button" class="sim-button button5" value="Add New" /> 
									<input id="removeButton" type="button" class="sim-button button5" value="Remove" /> 
								</div>
							</div>							
						</div>
						<div class="det-row" id="adddynamicloc" style="display: none;">
						
						</div>
						<div class="det-row">
							<div class="det-row-col astrick">
							 	<div class="label-text">Pin Code </div>              	
							   	<form:input id="pinCode" path="gstinAddressMapping.pinCode" maxlength="6" class="form-control"/>
							    <span class="text-danger cust-error" id="zip-req">This field is required and should be 6 digits</span>
		                  		<span class="text-danger cust-error" id="empty-message"></span>
					       </div>
					       <div class="det-row-col astrick">
					        	<div class="label-text ">City </div>
					          	<form:input id="city" readonly="true" path="gstinAddressMapping.city" class="form-control"/>
							</div>
							<div class="det-row-col astrick">
							 	<div class="label-text">Country </div>
							   	 <form:input id="country" value="India" readonly="true" path="gstinAddressMapping.country"  class="form-control"/>
							</div>	
							<div class="det-row-col astrick"  hidden="true" >
							 	<div class="label-text">State </div>
							    <form:hidden id="state"  path="gstinAddressMapping.state" class="form-control"/>
							   	<span class="text-danger cust-error" id="state-req">This field is required</span>
							</div>
						</div>
						<input type="hidden" id="counterButtonValue" >
						<input type="hidden" id="dynamicLocCount">
					</div>
			      	<div class="insidebtn"> 	        	
						<input id="submitGstin" type="Submit" class="sim-button button5" value="Save" /> 
					</div>
					
		  		</form:form>			            
			   </div>
		
			<form name="manageGstinDetail" method="post">
			    <input type="hidden" name="id" value="">
			    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			</form>	
		</section>
	<!-- </div>
</div>  -->

<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>