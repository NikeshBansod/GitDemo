 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 


<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/registration/registration.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/registration/client.min.js"/>"></script>
      <div class="loader"></div>
<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>


<div class="min-height500">
<section class="block">
<div class="container">
<!-- <div class="brd-wrap">
                <strong>        <a href="./login">Login</a></strong> »  <strong>Create your Bill Lite Account</strong>
                                             
 </div> -->
 
 <div class="page-title">
                        <a href="./login" class="back"><i class="fa fa-chevron-left"></i></a>Create your Bill Lite Account
                    </div>
 
 <form:form commandName="userMaster" method="post" action="./registration">
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />

<div class="form-wrap">

<div class="row">

                            <div class="col-md-6">
                            <div class="form-group input-field mandatory">
                                <label for="">PAN <span>*</span>
                                <form:input path="organizationMaster.panNumber"  id="pan-number" maxlength="10" required="true" class="form-control"/>
                                
                                
                                <span class="text-danger cust-error" id="pan-number-req">This field is required and should be in the correct format.</span>
		                  		<span class="text-danger cust-error" id="pan-number-back-req"></span></label>
		                  		</div>
                            </div>
                            
                            
		                  	
		                  	
		                  	
		                  	
                            <div class="col-md-6">
                            <div class="form-group input-field mandatory">
                                <label for="">Mobile Number <span>*</span>
                                <input type="text" class="form-control" id="contactNo" maxlength="10" required="true"  />
              					<form:input path="contactNo" name="contactNo" type="hidden" class="form-control" id="hiddenContactNo" maxlength="10" required="true"  />
                                
                                
                                
                                <span class="text-danger cust-error" id="contactNo-req">This field is required and should be 10 digits</span>
		                  		<span class="text-danger cust-error" id="contactNo-exist"></span></label>
		                  		</div>
                            </div>
                            
                            <div class="col-md-12 button-wrap" id="verifyBtnDiv">
                            <div class="form-group ">
                            <button type="button" class="btn btn-success blue-but" id="verifyUserMobileNoBtn" >Verify Mobile No.</button>
                            </div>
                        </div>
                           
                           </div>
                           
                           
                           <div id="divOtp" style="display: none;">
                           <div class="row">
                            <div class="col-md-6">
							   <div class="form-group input-field mandatory">
							  
								 <label for="">Enter OTP <span>*</span>
									
										<input type="text" class="form-control" id="otp" maxlength="6" name="otp" /> 
									
									<span class="text-danger cust-error" id="otp-req">This field is required</span>
									
								</label>
								</div>
								
								 <div class="form-group ">
									<button class="btn btn-success blue-but" id="verifyOtp"  >Verify OTP</button>
									<button class="btn btn-success blue-but" onclick="verifyUserIdAndSendOtp();" >Resend OTP</button>
								</div>
				  		</div>
				  		</div>
				  		</div>
				  		
				  		
				  		  <div class="row">
				  		  <div id="loginDetails" style="display: none;">
			                   	
			                  	<form:hidden path="userId" id="userId"/>
			                  	
			                        <div class="col-md-6">    
	                            <div class="form-group input-field mandatory">
			                  		<label for="">MPIN (Password) <span>*</span>
			                  			<form:input path="password" maxlength="25" type="password"  id="password" required="true" class="form-control"/>
			                  			
			                  		</label>
			                  		<span class="text-danger cust-error" id="password-req">This field is required. </span>
			                  		<span class="text-danger cust-error" id="password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character. </span>
			                	 </div>
                            	</div>
                            	
                            	
                            	<div class="col-md-6">
                            	<div class="form-group input-field mandatory">
			                  		<label for="">Confirm MPIN <span>*</span>
			                  			<input type="password" maxlength="25" required="true" id="conf-password" class="form-control">
			                  			
			                 		 </label>
			                 		 
			                 		 
			                  		<span class="text-danger cust-error" id="conf-password-req">This field is required</span>
			                  		<span class="text-danger cust-error" id="password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
			                  	</div>
			                       
			                      
			                      </div>
			                      
                                <div class="col-md-12 button-wrap">
				                    <button class="btn btn-success blue-but" style="width: auto;" id="RegisterBtn" type="submit" formnovalidate>Register</button>
				                </div>
				                
				                
                        </div>                                                       
                        </div>


</div>

</form:form>

</div>

</section>
  
</div>
  
            
             