<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>

<c:if test="${not empty response}">
	<script type="text/javascript">
		bootbox.alert('${response}', function() {
			window.location.href = "./homeUnifi";
			/* var respStatus = '${status}';
			if (respStatus == 'SUCCESS') {
				window.location.href = "./home";
			} */
		});
	</script>
</c:if>
<c:if test="${empty response}">
	<script type="text/javascript">
	bootbox.alert('You’re almost there!  <br>  Please help us with just a few more profile details.');
	</script>	
</c:if>

<div class="loader"></div>

<section class="block">
	<form:form modelAttribute="userMasterUnification" method="post"  action="./edituserprofileunificationsave">
		<div class="container">
		<input type="hidden" id="_csrf_token" name="_csrf_token"
				value="${_csrf_token}" />
				
				<div class="page-title" id="listheader">
					<a href="<spring:url value="/homeUnifi"/>" class="back"><!-- <i
						class="fa fa-chevron-left"></i> --></a>My Profile
				</div>
		<div class="form-wrap">
			<div class="row">
			 <h1 class="box-section-title">Organization details</h1>
				<div class="form-group">
				<div class="col-md-4">
								<label for="">PAN<span> *</span> <input type="text"
									disabled="true"
									value="${userMaster.organizationMasterUnification.panNumber }"
									class="form-control" /> <form:hidden
										path="organizationMasterUnification.panNumber" id="pan-number"
										maxlength="10"
										value="${userMaster.organizationMasterUnification.panNumber }" />
								</label> <span class="text-danger cust-error" id="pan-number-req">This
									field is required and should be in the correct format.</span>
					</div>
					
					<div class="col-md-4">
								<label for="">Firm Name<span> *</span> <form:input
										path="organizationMasterUnification.orgName" id="org-name"
										maxlength="100"
										value=""
										class="form-control" />
								</label>

					</div>
					<div class="col-md-4" style="display: none;">
								<label for="">Firm Type <span> *</span> <input
									type="hidden" id="orgTypeHidden"
									value="" /> <form:select
										id="orgType" path="organizationMasterUnification.orgType"
										value=""
										class="form-control" style="height: 42px;"></form:select>
								</label>

					</div>
					<div class="col-md-4" style="display: none;" id="divOtherOrgType"
								style="padding-bottom: 0px;padding-top: 0px;width: 362px;height: 42px;">
								<label for="">Specify Firm Type <span> *</span> <input
									type="hidden" id="orgTypeHidden"
									value="" /> <form:input
										path="organizationMasterUnification.otherOrgType" maxlength="200"
										id="otherOrgType"
										value=""
										class="form-control" />
								</label>
					</div>
			
				<div class="col-md-4">
								<label for="">Email Id<span> *</span> 
									<form:input
										path="organizationMasterUnification.orgEmailId" readonly="true" id="orgEmailId"
										value="${userMaster.defaultEmailId }"
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 

				</div>
							 <div class="col-md-4">
								<label for="">Aadhar No.for e-Sign <form:input
										path="organizationMasterUnification.adharEsign" id="adharEsign"
										value=""
										class="form-control" maxlength="12" />
									<!-- name ="aadharNoForEsign" -->
								</label> <span class="text-danger cust-error" id="aadharNo-req">This
									field should be 12 digits.</span>

							</div> 
							<div class="col-md-4">
								<label for="">Mobile No. (This will be your User Id) <span>
										*</span> <form:input path="organizationMasterUnification.orgContactNo" readonly="true" maxlength="10"
										value="${userMaster.contactNo}" class="form-control" />
								</label> <span class="text-danger cust-error" id="contactNo-req">This
									field should be 10 digits</span>
							</div>
				 <div class="col-md-4">
								<label for="">CIN (Corporate Identity Number) <form:input
										path="organizationMasterUnification.cin" id="cin" maxlength="21"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> <span class="text-danger cust-error" id="cust-cin-format">CIN
									should be in proper format</span>
							</div> 
							<div class="col-md-4">
								<label for="">Pin Code:<span> *</span> <form:input
										path="organizationMasterUnification.pinCode" id="pin" maxlength="21"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> <!-- <span class="text-danger cust-error" id="cust-pin-format"></span> -->
							</div>
							<div class="col-md-4">
								<label for="">Landline No: <form:input
										path="organizationMasterUnification.landlineNo" id="landlineNo" maxlength="21"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 
							</div>
							<div class="col-md-4">
								<label for="">Address Line1:<span> *</span> <form:input
										path="organizationMasterUnification.address1" id="address1" maxlength="21"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 
							</div>
							<div class="col-md-4">
								<label for="">Address Line2: <form:input
										path="organizationMasterUnification.address2" id="address2" maxlength="21"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 
							</div>
							<%-- <div class="col-md-4">
								<label for="">STD Code: <form:input
										path="organizationMasterUnification.stdCode" id="landlineNo" maxlength="21"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 
							</div> --%>
					</div>
			</div>
		</div>
	
		<div class="form-wrap">
		<div class="row">
		<h1 class="box-section-title">User details</h1>
		<div class="form-group">
		                    <div class="col-md-4">
								<label for="">prefix:<span> *</span> 
								<form:select path="prefix"  id="prefix">
								<form:option value="" >Select </form:option>
								<form:option value = "Mr.">Mr.</form:option>
								<form:option value = "Ms.">Ms.</form:option>
								<form:option value = "Mrs.">Mrs.</form:option>
								</form:select>
								</label> 
							</div>
							<div class="col-md-4">
								<label for="">First Name:<span> *</span>
								 <form:input path="firstName" id="firstName"
										maxlength="100"
										value=""
										class="form-control" />
								</label>

							</div>
							<div class="col-md-4">
								<label for="">Last Name :<span> *</span> 
								<form:input	path="lastName" id="lastName"
										maxlength="100"
										value=""
										class="form-control" />
								</label>

							</div>
							<div class="col-md-4">
								<label for="">Gender: <span> *</span> 
							    <form:select path="gender"  id="gender">
							    <form:option value="" >Select </form:option> 
								<form:option value = "M">Male</form:option> 
								<form:option value = "F">Female</form:option> 
						       </form:select>
								</label>

							</div>
							<div class="col-md-4">
								<label for="">Department:<span> *</span> 
								<form:input path="department" id="department"
										maxlength="100"
										value=""
										class="form-control" />
								</label> 

							</div>
							<div class="col-md-4">
								<label for="">Designation: 
								<form:input path="designation" id="designation"
										maxlength="100"
										value=""
										class="form-control" />
								</label> 
							</div>

							<div class="col-md-4">
								<label for="">Landline No:
								 <form:input path="userLandlineNo" id="userLandlineNo"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 
							</div>
							<%-- <div class="col-md-4">
								<label for="">STD Code:
								 <form:input path="userStdCode" id="userStdCode"
										value=""
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> 
							</div> --%>
							
							<div class="col-md-4">
								<label for="">Mobile Number:<span> *</span>
								 <form:input path="contactNo" id="usercontactNo" value="${userMaster.contactNo}" class="form-control" maxlength="10" />
								 
		                  		 </label>
							</div>
							<div class="col-md-4">
								<label for="">Email Id:<span> *</span>
								 <form:input path="emailId" id="UseremailId"
										value="${userMaster.defaultEmailId }"
										class="form-control" />
								</label> 
							</div>
							<%-- <div class="col-md-4">
								<label for="">UserName:
								 <form:input path="userName" id="userName"
										value=""
										class="form-control" />
								</label> 
							</div> --%>
		
		
		</div>
		</div>
		</div>
		
		<form:hidden path="id" value="${userMaster.id }" />
					<form:hidden path="userId" value="${userMaster.userId }" />
					<form:hidden path="userRole" value="${userMaster.userRole }" />
					<form:hidden path="password" value="ZZZZZZZZZ1HHHfHHH#@H" />
					<form:hidden path="userIdUnifi" value="${userMaster.userIdUnifi }" />
					<!-- pattern regex in entity does not allow encrypted value -->
					<form:hidden path="organizationMasterUnification.id"
						value="${userMaster.organizationMasterUnification.id }" />
					<form:hidden path="status" value="${userMaster.status }" />
					<form:hidden path="organizationMasterUnification.createdOn"
						value="${userMaster.organizationMasterUnification.createdOn }" />
					<form:hidden path="organizationMasterUnification.createdBy"
						value="${userMaster.organizationMasterUnification.createdBy }" />
					<form:hidden path="createdOn" value="${userMaster.createdOn }" />
					<form:hidden path="createdBy" value="${userMaster.createdBy }" />
					<input type="hidden" id="termsConditionsFlagHidden"
						name="organizationMasterUnification.termsConditionsFlagHidden"
						value="${userMaster.organizationMasterUnification.termsConditionsFlagHidden}" />
					<form:hidden path="uniqueSequence"
						value="${userMaster.uniqueSequence }" />
					<input type="hidden" id="logoUploadFlag"
						name="organizationMasterUnification.logoUploadFlag"
						value="${userMaster.organizationMasterUnification.logoUploadFlag}">
					<input type="hidden" id="logoImagePath"
						name="organizationMasterUnification.logoImagePath"
						value="${userMaster.organizationMasterUnification.logoImagePath}">
					<input type="hidden" id="companyId"
						name="organizationMasterUnification.companyId"
						value="${userMaster.organizationMasterUnification.companyId}">
			
			</div>
			 <div class="col-md-12 button-wrap">
					<button type="submit" class="btn btn-success blue-but"
						formnovalidate="formnovalidate" style="width: auto;"
						id="UpdateBtn" value="Submit">Save</button>
					 <!-- <button id="CUpdateBtn" type="button" hidden="true" />  -->
				</div> 
			
	</form:form>	
</section>


<!-- </section> -->
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/registrationunifi/manageOrganization.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/registrationunifi/client.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/registrationunifi/registration_additional.js"/>"></script>
