<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/secondaryUser/wizardManageSecondaryUser.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				
			<form:form commandName="userMaster" method="post" action="./wizardUpdateSecondaryUser" >
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<div class="breadcrumbs"><a href="<spring:url value="/wizardSecondaryUserPage"/>">Manage Employees</a> <span>&raquo;</span> Edit Employee</div>
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-half astrick">
								<div class="label-text">Name</div>
							  	<form:input path="userName" id="userName" maxlength="100" value="${userMasterObj.userName }" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="cust-name-req">This field is required</span>
							</div>
							<div class="det-row-col-half astrick">
								<div class="label-text">Mobile No. (This will be User Id of your Employee)</div>
							  	<form:input path="contactNo" readonly="true" maxlength="10" value="${userMasterObj.contactNo }" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span>
							</div> 
											
						</div>
						<div class="det-row"> 					
							<div class="det-row-col-half ">
								<div class="label-text">Email ID</div>
							 	<form:input path="emailId" maxlength="100" value="${userMasterObj.emailId }" class="form-control"/> 
							 	<span class="text-danger cust-error" id="reg-email-req">This field should be in a correct format</span>
							</div>  	
							<div class="det-row-col-half ">
								<div class="label-text">Aadhar Number</div>
								<form:input path="secUserAadhaarNo" id="secUserAadhaarNo" maxlength="12" value="${userMasterObj.secUserAadhaarNo }" class="form-control"/>
							 	<span class="text-danger cust-error" id="aadharNo-req">This field should be of 12 digits.</span>
							</div>
						</div>						
						<form:hidden path="id" value="${userMasterObj.id }"/>
				   		<form:hidden path="createdBy" value="${userMasterObj.createdBy }"/>
				  		<form:hidden path="userId" value="${userMasterObj.userId }"/>
				   		<form:hidden path="userRole" value="${userMasterObj.userRole }"/>
				   		<form:hidden path="password" value="ZZZZZZZZZ1HHHfHHH#@H"/>  <!-- pattern regex in entity does not allow encrypted value -->
				   		<input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					</div>
				<div class="insidebtn"> 	        	
					<input id="secSubmitBtn" type="Submit" class="sim-button button5" value="Update" /> 
					<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
				</div>
           </form:form>
		
	</div>
	 
<form name="manageSecondaryUser" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>
	
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/products/hsnCodeAjax.js"/>"></script>