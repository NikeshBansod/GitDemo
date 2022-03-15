<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/gstncommonvalidation.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/password/forgetPassword.js"/>"></script>

 <header class="insidehead">
      <a href="<spring:url value="/login" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<!--comon-->
<div class="container">
  
  <div class="box-border">
  <form class="form-horizontal"  action="./confirmPassword"  method="post">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition">
 
      <div class="accordion_in acc_active">
        <!-- <div class="acc_head">Organization Details</div> -->
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
              <div class="card">
              <span><center><h4><b> Recover Password </b></h4></center></span>
                <ul>
                  <li>
                 <div class="form-group input-field">
					<label class="label">  
						<div class="input-group">
						<input type="text" id="userId" maxlength="10" autocomplete="off"  class="form-control"/> 
						 <span class="input-group-btn">
						<button class="btn btn-primary" id="verifyUserBtn"  >Verify Mobile No</button>
						  </span>
						</div>
						<div class="label-text label-text2">Enter Mobile No </div>
						
					</label>
					<span class="text-danger cust-error" id="userId-req">This field is required</span>
				</div>
				 </li>
				  <div id="divOtp" style="display: none;">
				  <li>
				   <div class="form-group input-field">
					<label class="label"> 
						<div class="input-group"> 
							<input type="text" id="otp" maxlength="6" class="form-control"/> 
						<span class="text-danger cust-error" id="otp-req">This field is required</span>
						<span class="input-group-btn">
						<button class="btn btn-primary" id="verifyOtp"  >Verify OTP</button>
						</span>
						<span class="input-group-btn">
						<button class="btn btn-primary" onclick="verifyUserIdAndSendOtp();" >Resend OTP</button>
						</span>
						</div>
						<div class="label-text label-text2">Enter OTP </div>
					</label>
					</div>
				  </li>
				  </div>
				 <div id="pwdChange" style="display: none;">
				  <li>
				   <div class="form-group input-field">
					<label class="label">  
						<input type="password" name="newPassword" autocomplete="off" id="password" maxlength="25" required="required" class="form-control"/>
						<div class="label-text label-text2">New MPIN</div>
					</label>
					<span class="text-danger cust-error" id="password-req">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
					</div>
					
				  </li>
				   <li>
				   <div class="form-group input-field">
					<label class="label">  
						<input type="password" name="confirmPassword" autocomplete="off" class="form-control" maxlength="25" id="conf-password" required="required"/>  
						<div class="label-text">Confirm New MPIN</div>
					</label>
					<span class="text-danger cust-error" id="conf-password-req">This field is required</span>
					</div>
				  </li>
				   <li>
                     <div class="form-group input-field input-group captchaBox">
                           <input type="text"  id="captchaImgText" maxlength="6" name="captchaImgText" required class="form-control" placeholder="Enter Captcha"/>
                         <span class="input-group-addon captchaImg">
                           <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                           </span>
                            <span class="input-group-btn captchaRefresh">
                          <button class="btn btn-primary"  title="change captcha text" type="button" onclick="ClearCaptchaText();"><i class="fa fa-refresh"></i>
                          </button>
                           </span>
                      	<span class="text-danger cust-error" id="captcha-req">This field is required</span>
                      	</div>
                   </li>
               </div>
                </ul>
                <div class="text-center">
    			 <button type="submit" class="btn btn-primary" id="saveBtn" value="Save">Save</button>
     			</div>
              </div>
            </div>
          </div>
        </div>
     
      </div>
      <!-- <div class="com-btn">Register</div>   -->
      <input type="hidden" name="otpHidden" value="" id="otpHidden"/>
     </form>
  </div>
</div>
<!--common end-->
