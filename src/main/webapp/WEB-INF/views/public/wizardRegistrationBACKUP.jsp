<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/registration/registration.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/registration/client.min.js"/>"></script>

<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>

<header class="insidehead">
      <a href="<spring:url value="/wlogin" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Registration<span></a>
 </header>
 
	<div class="row">
		<div class="col-md-12">
			<div class="bg_shadow">
			<form:form commandName="userMaster" method="post" action="./wizardRegisteringUser">
				<div class="common-wrap">
					<div class="box-border">
						<div class="accordion_example2 no-css-transition hideIcon smk_accordion acc_with_icon">
							  <div class="accordion_in acc_active">
								  <div class="acc_head hidden" id="orgHeader"><div class="acc_icon_expand"></div>Firm Details</div>
								  <div class="acc_content" id="orgDetails">
										<!--content-->
										<!-- <div class="form-group input-field mandatory">
											<label class="label">  
												<input id="vendor-code" name="vendorCode" class="form-control" required="true" onblur="getVendorDetails()" type="text" value="" maxlength="10">
												<div class="label-text label-text2">Vendor Code</div>						
											</label>
											<span class="text-danger cust-error" id="vendor-code-req">This field is required.</span>
										</div>
										<div class="form-group input-field mandatory">
											<label class="label">  
												<input id="firm-name" name="firmName" class="form-control" required="true" type="text" value="" maxlength="40">
												<div class="label-text label-text2">Firm Name</div>						
											</label>
											<span class="text-danger cust-error" id="firm-name-req">This field is required.</span>
										</div>
										<div class="form-group input-field mandatory">
											<label class="label">
												<select id="firm-type" name="firmType" class="form-control">
												<option value="FType0">FType0</option><option value="FType1">FType1</option><option value="FType2">FType2</option><option value="FType3">FType3</option><option value="FType4">FType4</option><option value="FType5">FType5</option><option value="FType6">FType6</option></select>
												<div class="label-text label-text2">Firm Type</div>
											</label>
											<span class="text-danger cust-error" id="firm-type-req">This field is required.</span>
										</div> -->
							
										<div class="form-group input-field mandatory">
											<label class="label"> 
												<form:input path="organizationMaster.panNumber"  id="pan-number" maxlength="10" required="true" class="form-control"/>
												<div class="label-text label-text2">PAN</div>
											</label>
											<span class="text-danger cust-error" id="pan-number-req">This field is required and should be in the correct format.</span>
											<span class="text-danger cust-error" id="pan-number-back-req"></span>
										</div>
										<!-- <div class="form-group input-field mandatory">
											<label class="label">
												<input id="gstin-number" name="gstinNumber" class="form-control" type="text" value="" maxlength="15">
												<div class="label-text label-text2">GSTIN</div>
											</label>
											<span class="text-danger cust-error" id="gstin-number-req">This field is required.</span>
										</div>
										<div class="form-group input-field mandatory">
											<label class="label">  
												<input id="emailId" name="emailId" class="form-control" type="text" value="" maxlength="40">
												<div class="label-text label-text2">Email ID</div>
											</label>
											<span class="text-danger cust-error" id="cust-email-format">Email should be in correct format.</span>
										</div> -->
									                  
										<div class="form-group input-field mandatory">
											<label class="label">
												<div class="input-group">
													<form:input path="contactNo" id="contactNo" maxlength="10" required="true" class="form-control" />													
													<span class="input-group-btn">
														<button class="btn btn-primary" id="verifyUserMobileNoBtn" style="padding-top: 4px;" >Verify Mobile No</button>
											  		</span>
											  	</div>
											  	<div class="label-text label-text2">Mobile No. (This will be your User Id)</div>
											</label>
											<span class="text-danger cust-error" id="contactNo-req">This field is required and should be 10 digits</span>
		                  					<span class="text-danger cust-error" id="contactNo-exist"></span>	  		 
										</div>
										
				                          <div id="divOtp" style="display: none;">
											   <div class="form-group input-field">
												<label class="label"> 
													<div class="input-group"> 
														<input type="text" id="otp" maxlength="6" name="otp" class="form-control"/> 
														<span class="input-group-btn">
															<button class="btn btn-primary" id="verifyOtp"  >Verify OTP</button>
														</span>
														<span class="input-group-btn">
														<button class="btn btn-primary" onclick="verifyUserIdAndSendOtp();" >Resend OTP</button>
														</span>													
													</div>
													<div class="label-text label-text2">Enter OTP </div>
												</label>
													<span class="text-danger cust-error" id="otp-req">This field is required</span>	
												</div>
								  			</div>
								  			
										<div id="loginDetails">  											
											<form:hidden path="userId" id="userId"/>
								          
											<div class="form-group input-field mandatory">
												<label class="label">	
													<form:input path="password" maxlength="25" type="password"  id="password" required="true" class="form-control"/>
													<div class="label-text label-text2">MPIN (Password)</div>
												</label>
												<span class="text-danger cust-error" id="password-req">This field is required. </span>
												<span class="text-danger cust-error" id="password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character. </span>
											</div>
												
											<div class="form-group input-field mandatory">
												<label class="label">
													<input type="password" maxlength="25" required="true" id="conf-password" class="form-control">
													<div class="label-text label-text2">Retype MPIN</div>
												 </label>
												<span class="text-danger cust-error" id="conf-password-req">This field is required</span>
												<span class="text-danger cust-error" id="conf-password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
											</div>
										</div>
										<div class="form-group btns text-center">
											<button class="btn btn-primary" id="RegisterBtn" type="submit" formnovalidate="">Register</button>
										</div>
									</div>
								</div>
							</div>
							
						</div>
					</div>
			</form:form>
			</div>
		</div>
	</div>

