<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 



<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert(
			{
			    message: '${response}',
			    callback: function () {
			    	if('${status}' == 'SUCCESS'){
			    		window.location.href = "./login"
			    	}
			    }
			}		
	);
	
	</script>	
</c:if>


 <div class="loader"></div>
<!-- Body content/sections Starts here -->
<section class="block">
	<div class="container">
	<form id = "changePassword" action="./changePassword"  method="post">
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
<!-- 	     <div class="brd-wrap">
	         <a href="./home"><strong>Home</strong></a> &raquo; <a href="./changePassword"><strong>Change MPIN</strong></a>
	     </div> -->
	     <div class="page-title" id="listheader">
                 <a href="<spring:url value="/home#account_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Change MPIN
         </div>
	     <div class="form-wrap">
	         <div class="row">  
	             <div class="col-md-4">
	            	
	            	<label for="label">Mobile No<span style="font-weight: bold;color: #ff0000;"> *</span> 
							<input type="text" disabled="disabled" autocomplete="off" value="${loginMaster.userId}" class="form-control"/>  
						</label>
						
	              </div>
	                <div class="col-md-4">
	            	<label for="label">Old MPIN<span style="font-weight: bold;color: #ff0000;"> *</span> 
							<input type="password" id="oldPassword" autocomplete="off" name="oldPassword" maxlength="25" required class="form-control" />    
						</label>
						<span class="text-danger cust-error" id="oldPwd-req">This field is required</span>
						<span class="text-danger cust-error" id="oldPwd-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
					
						
	              </div>
	               <div class="col-md-4">
	            	<label for="label">New MPIN<span style="font-weight: bold;color: #ff0000;"> *</span> 
							<input type="password" name="newPassword" autocomplete="off" id="newPassword" maxlength="25" required class="form-control"/>  
						</label>
						<span class="text-danger cust-error" id="newPwd-req">This field is required</span>
						<span class="text-danger cust-error" id="password-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
					
						
						
	              </div>
	               </div>
	               <div class="row"> 
	               <div class="col-md-4">
	            	<label for="label">Confirm New MPIN<span style="font-weight: bold;color: #ff0000;"> *</span> 
							<input type="password" name="confirmPassword" autocomplete="off" id="confirmPassword" maxlength="25" required class="form-control"/>  
						</label>
						
					<span class="text-danger cust-error" id="confPwd-req">This field is required & should match with new Password</span>
						
	              </div>
	               <div class="col-md-4 button-wrap">
	            	<label for="label">Enter Captcha<span style="font-weight: bold;color: #ff0000;"> *</span> 
							<input type="text" id="captchaImgText" maxlength="6" name="captchaImgText" required class="form-control" > 
						</label>
						 </div>
						
						 <div class="col-md-4"style="padding-top: 12px;width: 350px;">
						<span class="input-group-addon captchaImg"style="width: 326px;height: 54px;">
                                  <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                            </span> 
                             
                             <span class="input-group-addon  button-wrap captchaRefresh" style="width: 100px;height: 54px;">
                                   <button class="btn blue-but btn-primary" onclick="ClearCaptchaText();" type="button"><i class="fa fa-refresh"></i></button>
                            </span>
                            </div> 
						
                             <!-- <span class="input-group-addon captchaImg">
                                  <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                             </span>
                            
                             <span class="input-group-btn captchaRefresh">
                                   <button class="btn blue-but btn-primary" onclick="ClearCaptchaText();" type="button"><i class="fa fa-refresh"></i></button>
                             </span>
 -->                             
						
	             
	              
	             
	              </div>                                                        
            <div class="row"> 
                <div class="col-md-12 button-wrap">
            	<button type="submit" id="saveBtn" class="btn btn-success blue-but" formnovalidate="formnovalidate" value="Save"  style="width: auto;">Save</button>
 </div>
            	
            </div>
         </div>
        </form> 
       </div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/password/managePassword.js"/>"></script>
