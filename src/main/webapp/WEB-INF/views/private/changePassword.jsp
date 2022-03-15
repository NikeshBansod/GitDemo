<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/password/managePassword.js"/>"></script>

 <header class="insidehead">
      <a href="<spring:url value="/home" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--  <span>Change MPIN<span> --></a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<!--comon-->
<div class="container">
  
  <div class="box-border">
  <form  action="./changePassword"  method="post">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition">
 
      <div class="accordion_in acc_active">
        <!-- <div class="acc_head">Organization Details</div> -->
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
              <div class="card">
              <span><center><h4><b>Change MPIN</b></h4></center></span>
                <ul>
                  <li>
                  	<div class="form-group input-field">
						<label class="label">  
							<input type="text" disabled="disabled" autocomplete="off" value="${loginMaster.userId}" class="form-control"/>  
							<div class="label-text label-text2">Mobile No</div>
						</label>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<input type="password" id="oldPassword" autocomplete="off" name="oldPassword" maxlength="25" required class="form-control"/>  
							<div class="label-text label-text2">Old MPIN</div>
						</label>
						<span class="text-danger cust-error" id="oldPwd-req">This field is required</span>
						<span class="text-danger cust-error" id="oldPwd-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<input type="password" name="newPassword" autocomplete="off" id="newPassword" maxlength="25" required class="form-control"/>  
							<div class="label-text label-text2">New MPIN</div>
						</label>
						<span class="text-danger cust-error" id="newPwd-req">This field is required</span>
						<span class="text-danger cust-error" id="password-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<input type="password" name="confirmPassword" autocomplete="off" id="confirmPassword" maxlength="25" required class="form-control"/>  
							<div class="label-text label-text2">Confirm New MPIN</div>
						</label>
						<span class="text-danger cust-error" id="confPwd-req">This field is required & should match with new Password</span>
					</div>
				  </li>
				  <li>
                      <div class="form-group input-field input-group captchaBox">
                        <label class="label"> 
                             <div class="label-text label-text2">Enter Captcha</div>
                       </label>
                             <input type="text" id="captchaImgText" maxlength="6" name="captchaImgText" required class="form-control" >
                             <span class="input-group-addon captchaImg">
                                  <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                             </span>
                             <span class="input-group-btn captchaRefresh">
                                   <button class="btn btn-primary" onclick="ClearCaptchaText();" type="button"><i class="fa fa-refresh"></i></button>
                             </span>
                            
                      </div>
                   </li>               

                </ul>
                    <div class="text-center">
					     <button type="submit" class="btn btn-primary" formnovalidate="formnovalidate" id="saveBtn" value="Save">Save</button>
					</div>
              </div>
            </div>
            
            
          </div>
        </div>
    
     
      </div>
      <!-- <div class="com-btn">Register</div>   -->
  
     </form>
  </div>
</div>
<!--common end-->
