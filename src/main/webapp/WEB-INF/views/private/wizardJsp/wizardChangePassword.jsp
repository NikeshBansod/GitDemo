<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/password/wizardManagePassword.js"/>"></script>


<section class="insidepages">	
 <div class="inside-cont">
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> Change MPIN
		 		</div>
		 		
			</div>
			<br>
			<div id="changePassword">
			<form  action="./wizardChangePassword"  method="post">
    			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-half ">
								<div class="label-text">Mobile Number </div>
							  	<input type="text" disabled="disabled" autocomplete="off" value="${loginMaster.userId}" class="form-control"/>
							</div>	
							<div class="det-row-col-half astrick">
								<div class="label-text">Old MPIN</div>
								<input type="password" id="oldPassword" autocomplete="off" name="oldPassword" maxlength="25" required="true" class="form-control"/>
								
								<span class="text-danger cust-error" id="oldPwd-req">This field is required</span>
								<span class="text-danger cust-error" id="oldPwd-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>  
							</div>
						</div>
						<div class="det-row"> 
           						<div class="det-row-col-half astrick">
								<div class="label-text">New MPIN</div>
							  	<input type="password" name="newPassword" autocomplete="off" id="newPassword" maxlength="25" class="form-control"/>
						
								<span class="text-danger cust-error" id="newPwd-req">This field is required</span>
								<span class="text-danger cust-error" id="password-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
							</div>
							<div class="det-row-col-half astrick">
								<div class="label-text">Confirm New MPIN</div>
							  	<input type="password" name="confirmPassword" autocomplete="off" id="confirmPassword" maxlength="25" required="true" class="form-control"/>  

								<span class="text-danger cust-error" id="confPwd-req">This field is required & should match with new Password</span>
							</div>
						</div>
						<div class="det-row">	
							<div class="det-row-col-half astrick ">
								<div class="label-text">Enter Captcha</div>
								 <input type="text" id="captchaImgText" maxlength="6" name="captchaImgText"  class="form-control" >
							</div> 
							<div class="det-row-col-half"> 	
								<div class="label-text">&nbsp;</div>	
								<div class="input-group captchaBox"> 
									 <span class="input-group-addon captchaImg">
		                                  <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
		                             </span>&nbsp;&nbsp;&nbsp;&nbsp;
		                             <button class="btn btn-primary" onclick="ClearCaptchaText();" ><i class="fa fa-refresh"></i></button>
								</div>								        	
							</div>	
						</div>									
											
					</div>
			      	<div class="insidebtn"> 	       	
						<input id="saveBtn" type="Submit" class="sim-button button5" value="Save" formnovalidate="formnovalidate"/> 
					</div>
					</form>
					</div>		
			</div>
			
			</section>
		
<!--common end-->
