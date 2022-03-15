<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
<div class="min-height500">
<div class="loader"></div>
<section class="block">
	<div class="container">	
		<div class="brd-wrap">
			<a href="<spring:url value="/login" />"><strong>Login</strong></a> <span> » </span> <strong>Forgot MPIN</strong>
		</div>	 		
		<div class="form-wrap">
			<form class="form-horizontal"  action="./confirmPasswordLoginUnified"  method="post" >
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			  	 <div class="row">
				  	 <div class="col-md-6"> 
				  	 	<label for="label">Mobile No<span style="font-weight: bold;color: #ff0000;"> *</span></label>
						<input type="text" id="userId" maxlength="10" autocomplete="off" /> 
						<span class="text-danger cust-error" id="userId-req">This field is required</span>
						   <div class="button-wrap" id="verifyUserBtnMobile"> 
					          <label for="label"></label>
					          <button class="btn btn-success blue-but" id="verifyUserBtn" style="margin-top: 5px; width: auto;" style="width: auto;">Verify Mobile No</button>
				           </div>
					</div>
					
					<div class="col-md-6" id="divOtp" style="display: none;">
					  	 <label for="label">Enter OTP<span style="font-weight: bold;color: #ff0000;"> *</span>	</label>
						<input type="text" id="otp" maxlength="6"/>
						<span class="text-danger cust-error" id="otp-req">This field is required</span>
						<div class="col-md-12 button-wrap"> 
							<label for="label"></label>
							<!-- <button class="btn btn-success blue-but" id="verifyOtp"style="margin-top: 10px; width: auto;" >Verify OTP</button> -->
							<button class="btn btn-success blue-but" onclick="verifyUserIdAndSendOtp();"style="margin-top: 10px; width: auto;" >Resend OTP</button>
					   </div>
					</div>
			  	 </div> 
			    <div class="row ruGST" id="pwdChange" style="display: none;">
					<div class="col-md-6"> 
				  	  <label for="label">New MPIN<span style="font-weight: bold;color: #ff0000;"> *</span>	</label>
				  	  <input type="password" name="newPassword" autocomplete="off" id="password" maxlength="25" required="required"/>
				  	  <span class="text-danger cust-error" id="password-req">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
			  	    </div>
			  	    <div class="col-md-6"> 
				  	    <label for="label">Confirm New MPIN<span style="font-weight: bold;color: #ff0000;"> *</span>	</label>
				  	 	<input type="password" name="confirmPassword" autocomplete="off"  maxlength="25" id="conf-password" required="required"/>  
				  	 	<input type="hidden" name="cachekey" autocomplete="off" id="cachekey" required="required"/>
				  	  	<span class="text-danger cust-error" id="conf-password-req">This field is required</span>
			  	    </div>
					
				</div>
				 <div class="row" id="pwdChangebutton" style="display: none;">	
		      			<div class="col-md-12 button-wrap">
						<button type="submit" class="btn btn-success blue-but" id="saveBtn" value="Save" style="width: 134px;height: 38px; width: auto;">Save</button>
					</div>
		      		</div>
				<!-- <div class="button-wrap"> 
					<label for="label"></label>
					<button class="btn btn-primary" id="verifyUserBtn"style="margin-top: 20px;" >Verify Mobile No</button>
				</div> -->
		  		<input type="hidden" name="otpHidden" value="" id="otpHidden"/>
		    </form>
		</div>
	</div>
</section>

</div>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/loginUnified/forgetPassword.js"/>"></script>
