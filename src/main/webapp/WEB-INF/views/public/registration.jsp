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
      <a href="<spring:url value="/login" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<form:form commandName="userMaster" method="post" action="./registration">
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  <!--comon-->
        <div class="common-wrap">
            <div class="box-border">
            <span><center><h4><b> Registration </b></h4></center></span>
                <div class="accordion_example2 no-css-transition hideIcon">
                    <!--01-->
                    <div class="accordion_in acc_active">
                        <div class="acc_head hidden" id="orgHeader">Firm Details</div>
                        <div class="acc_content" id="orgDetails">
                            <!--content-->
                            		                  	
                            <div class="form-group input-field mandatory">
		                  		<label class="label"> 
		                  		<form:input path="organizationMaster.panNumber"  id="pan-number" maxlength="10" required="true" class="form-control"/>
		                  		<div class="label-text label-text2">PAN</div>
		                  		</label>
		                  		<span class="text-danger cust-error" id="pan-number-req">This field is required and should be in the correct format.</span>
		                  		<span class="text-danger cust-error" id="pan-number-back-req"></span>
		     				</div>
		     				<!--  
		     				 <div class="form-group input-field mandatory">
		                  		<label class="label">  
									<form:input path="organizationMaster.orgName" id="org-name" maxlength="100" required="true" class="form-control"/>
									<div class="label-text label-text2">Firm Name</div>						
								</label>
		                  		<span class="text-danger cust-error" id="org-name-req">This field is required.</span>
		                  	</div>
		     				
		     				<div class="form-group input-field mandatory">
		     					<label class="label">
			                  		<form:select path="organizationMaster.orgType" id="org-type" class="form-control" >
			                    
			                    	</form:select>
			                    	<div class="label-text label-text2">Firm Type</div>
		                    	</label>
		                    	<span class="text-danger cust-error" id="org-type-id">This field is required.</span>
		                    </div>
		     				-->
		     				
		     				 <div class="form-group input-field mandatory" id="divOtherOrgType">
		                  		<label class="label">
		                  			<form:input path="organizationMaster.otherOrgType" maxlength="200"  id="otherOrgType" class="form-control"/>
		                  			<div class="label-text label-text2">Please Specify </div>
		                  		</label>
		                  		<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
		                  	</div>
		     				<div class="form-group input-field mandatory" id="divOtherNatureOfBusiness">
		                  		<label class="label">
		                  			<form:input path="organizationMaster.otherNatureOfBusiness" maxlength="200" id="otherNatureOfBusiness" class="form-control"/>
		                  			<div class="label-text label-text2">Please Specify </div>
		                  		</label>
		                  		<span class="text-danger cust-error" id="otherNatureOfBusiness-req">This field is required.</span>
		                  	</div>
		                  
		     			
                          <div class="form-group input-field mandatory">
		                  		<label class="label">
		                  		<input type="text" class="form-control" id="contactNo" maxlength="10" required="true"  />
              					<form:input path="contactNo" name="contactNo" type="hidden" class="form-control" id="hiddenContactNo" maxlength="10" required="true"  />
		                  			<div class="label-text label-text2">Mobile No. (This will be your User Id)</div>
		                  		</label>
		                  		<span class="text-danger cust-error" id="contactNo-req">This field is required and should be 10 digits</span>
		                  		<span class="text-danger cust-error" id="contactNo-exist"></span>
		                  		 
		                  </div>
		                  
		                  <div id="verifyBtnDiv" class="form-group btns text-center">
		                  <button class="btn btn-primary" id="verifyUserMobileNoBtn" >Verify Mobile No</button>
		                  </div>
		                  
                          <div id="divOtp" style="display: none;">
							   <div class="form-group input-field">
								<label class="label"> 
									<!-- <div class="input-group">  -->
										<input type="text" class="form-control" id="otp" maxlength="6" name="otp" /> 
									<!-- </div> -->
									<span class="text-danger cust-error" id="otp-req">This field is required</span>
									<div class="label-text label-text2">Enter OTP </div>
								</label>
								</div>
								
								 <div class="form-group btns text-center">
									<button class="btn btn-primary" id="verifyOtp"  >Verify OTP</button>
									<button class="btn btn-primary" onclick="verifyUserIdAndSendOtp();" >Resend OTP</button>
								</div>
				  		</div>

                        <!-- /div>
                    </div>
                    
                    <div class="accordion_in  acc_active">
                        <div class="acc_head hidden" id="loginHeader">Login Details</div>
                        <div class="acc_content" id="loginDetails"-->
                           
                            	<%-- <div class="form-group input-field   mandatory">
			                  		<div class="label-text label-text3">User ID</div>
			                  			<div class="input-group">
				                  			<form:input path="userId" maxlength="10" required="true" class="form-control"/>
				                  			<span class="input-group-btn">
		                                        <button type="button" class="btn btn-info" data-toggle="tooltip" data-trigger="hover" data-placement="top" title="(should be combination of alphanumeric characters)"><i class="fa fa-info"></i></button>
		                                        
		                                    </span>
	                                    </div>
			                  		<span class="text-danger cust-error" id="userId-req">This field is required</span>
			                  		<span class="text-danger cust-error" id="userId-back-req"></span>
			                  	</div> --%>
			               
			                <div id="loginDetails" style="display: none;">
			                   	
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
			                  			<div class="label-text label-text2">Confirm MPIN</div>
			                 		 </label>
			                  		<span class="text-danger cust-error" id="conf-password-req">This field is required</span>
			                  		<span class="text-danger cust-error" id="password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
			                  	</div>
			                        
                                <div class="form-group btns text-center">
				                    <button class="btn btn-primary" id="RegisterBtn" type="submit" formnovalidate>Register</button>
				                </div>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
	</div>
</form:form>



