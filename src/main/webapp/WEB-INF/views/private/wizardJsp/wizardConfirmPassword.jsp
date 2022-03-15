<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/gstncommonvalidation.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/password/wizardForgetPassword.js"/>"></script>

<section class="insidepages">	
	<div class="breadcrumbs" id="listHeader">
		<div class="col-md-12" id="listheader1">
         	<a href="<spring:url value="/wLogin"/>">Home</a> <span>&raquo;</span>  Forgot MPIN 
 		</div>
 		
	</div>
	
	<div id="forgotPassword">
	 <form class="form-horizontal"  action="./wizardConfirmPassword"  method="post">
	 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	 <br>
			<div class="account-det">
				<div class="det-row">	
					<div class="det-row-col-half astrick">
		              <div class="label-text">Mobile No</div>
		              	<input type="text" id="userId" maxlength="10" autocomplete="off"  class="form-control"/> 
		              </div>
		              <div class="det-row-col-half ">
			              <div class="label-text">&nbsp;</div>
			              <span class="input-group-btn">
						  	<button class="btn btn-primary" id="verifyUserBtn"  >Verify Mobile No</button>
						  </span>	
						 <span class="text-danger cust-error" id="userId-req">This field is required</span>
		            </div> 
				</div>
			
				 <div class="det-row" >
				    <div id="divOtp" class="det-row-col astrick" style="display: none;">
					    <div class="label-text">Enter OTP  </div>
					    <input type="text" id="otp" name="otp" maxlength="6" class="form-control"/> 
						<span class="input-group-btn">
							<button class="btn btn-primary" id="verifyOtp" >Verify OTP</button>
						</span>
						<span class="input-group-btn">
							<button class="btn btn-primary" onclick="verifyUserIdAndSendOtp();" >Resend OTP</button>
						</span>
						<span class="text-danger cust-error" id="otp-req">This field is required.</span>
						</div>           		
					</div>
				<div id="pwdChange" style="display: none;">
				<div class="det-row" > 
	            <div class="det-row-col astrick">
	              <div class="label-text">New MPIN</div>
	              <input type="password" name="newPassword" autocomplete="off" id="password" maxlength="25" required="required" class="form-control"/>
	              <span class="text-danger cust-error" id="password-req">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
	            </div>
	            <div class="det-row-col astrick">
	              <div class="label-text">Confirm MPIN </div>
	              <input type="password" name="confirmPassword" autocomplete="off" class="form-control" maxlength="25" id="conf-password" required="required"/>
	              <span class="text-danger cust-error" id="conf-password-req">This field is required</span>
	            </div>
	          </div> 
			<div class="det-row">	
					<div class="det-row-col-half astrick ">
						<div class="label-text">Enter Captcha</div>
						 <div class="form-group input-field input-group captchaBox">
                     		<input type="text"  id="captchaImgText" maxlength="6" name="captchaImgText" required class="form-control" placeholder="Enter Captcha"/>
                     		<span class="text-danger cust-error" id="captcha-req">This field is required</span>
                           <span class="input-group-addon captchaImg">
                                <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                           </span>
                           <span class="input-group-btn ">
                                 <button class="btn btn-primary" type="button" title="change captcha text"  onclick="ClearCaptchaText();" ><i class="fa fa-refresh"></i></button>
                           </span>
                    				</div>
					</div> 	
					</div>	
				</div>
				</div>
				
				<div class="insidebtn"> 	        	
				<input id="saveBtn" type="submit" class="sim-button button5" value="Save" /> 
				<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
			</div>
			<input type="hidden" name="otpHidden" value="" id="otpHidden"/>
	 </form>
	</div>			
</section>
               
            