<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %>
<script type="text/javascript" src="<spring:url value="./resources/js/wizardJS/registration.js"/>"></script>


<c:if test="${not empty response}">
	<script type="text/javascript">
		bootbox.alert('${ response}', function() {
			var respStatus = '${ status}';
			if(respStatus == 'SUCCESS'){
					window.location.href = "./login";
			}else if(respStatus == 'FAILURE'){
				$("#password").val('');
				$("#conf-password").val('');
			}
		});
	</script>	
</c:if>


<form:form commandName="userMaster" method="post" action="./wizardRegisteringUser">   
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<section class="insidepages">
      <div class="inside-cont">
        <div class="breadcrumbs"><a href="./wLogin">Login</a> <span>&raquo;</span> <img src="./resources/images/wizardImages/headerimg.png" width="29" height="29"> Create your Bill Lite Account</div>
        <!-- <div class="insideheader">
          <div class="insideheader-cont"> 
            <div>Create your Wizard Account</div>
          </div>
        </div> -->
        <div class="account-det">
          <%-- <div class="det-row">
            <div class="det-row-col astrick">
            <div class="label-text">Vendor Code </div>
              <form:input path="vendorCode" name="vendorCode" type="text" id="vendorCode" required="true" onblur="getVendorDetails()" maxlength="10" /> 
              <span class="text-danger cust-error" id="vendorCode-req">This field is required.</span>
            </div>
            
            <form:hidden  path="vendorId" name="" id="vendorId"/>
            
            <div class="det-row-col">
              <div class="label-text">Firm Name </div>
              <form:input path="firmName" name="firmName" type="text" id="firmName" maxlength="100" />
            </div>
             
            <div class="det-row-col astrick">
              <div class="label-text">Firm Type </div>
              <form:select path="organizationType.id" id="firmType" class="form-control" required="true" >
				
			  </form:select>
			  <input  value="${registration.organizationType.id}" type="hidden" id="orgTypeHidden" />
			  <form:input path="organizationType.firmType" value="${registration.organizationType.firmType}" type="hidden" id="firmTypeHidden" />
			  <span class="text-danger cust-error" id="firmType-req">This field is required.</span>
            </div>
            <br>          
            <div class="det-row-col astrick" id="divOtherOrgType" style="float: right;">
            	<br><div class="label-text">Specify Firm Type</div>
          		<form:input path="otherFirmType" id="otherFirmType" maxlength="200" class="form-control" />
           		<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
           	</div>
          </div> --%>
          <div class="det-row">
            <div class="det-row-col astrick">
              <div class="label-text">PAN </div>
              <form:input path="organizationMaster.panNumber" name="panNumber" type="text" id="panNumber"  maxlength="10" class="form-control"/>
              <span class="text-danger cust-error" id="pan-number-req">This field is required.</span>
              <span class="text-danger cust-error" id="pan-number-back-req">Data should be in the correct format.</span>
            </div>
            <%-- <div class="det-row-col astrick">
              <div class="label-text">GSTIN </div>
              <form:input path="gstinNumber" name="gstinNumber" type="text" id="gstinNumber" required="true"  />
              <span class="text-danger cust-error" id="reg-gstin-req" >This field is required.</span>
              <span class="text-danger cust-error" id="reg-gstin-back-req" >Data should be in the correct format.</span>
            </div>
            <div class="det-row-col astrick">
              <div class="label-text">Email ID </div>
              <form:input path="emailId" name="emailId" type="text" class="form-control" id="emailId" required="true" maxlength="100" />
              <span class="text-danger cust-error" id="emailId-format">This field is required.</span>
              <span class="text-danger cust-error" id="emailId-back-req"></span>
            </div>
          </div>
          <div class="det-row"> --%>
            <div class="det-row-col astrick">
              <div class="label-text">Mobile No (User Id) </div>
              <input type="text" class="form-control" id="contactNo" maxlength="10" required="true"  />
              <form:input path="contactNo" name="contactNo" type="hidden" class="form-control" id="hiddenContactNo" maxlength="10" required="true"  />
             <!--  <span class="input-group-btn">
			  	<button class="btn btn-primary" id="verifyUserMobileNoBtn"  >Verify Mobile No</button>
			  </span> -->	
			  <input id="verifyUserMobileNoBtn" type="button" class="sim-button button7" value="Verify Mobile No" /><br>
			  <span class="text-danger cust-error" id="contactNo-req" >This field is required and should be 10 digits.</span>
		      <span class="text-danger cust-error" id="contactNo-exist" ></span>	              
					 
            </div>
          </div>
           <div class="det-row"> 
            <div class="det-row-col astrick">
              <div class="label-text">MPIN (Password) </div>
              <form:input path="password" name="password" type="password" class="form-control" id="password" required="true" maxlength="25" />
              <span class="text-danger cust-error" id="password-req"></span>
			  <span class="text-danger cust-error" id="password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character.</span>
            </div>
            <div class="det-row-col astrick">
              <div class="label-text">Confirm MPIN </div>
              <input name="conf-password" type="password" class="form-control" id="conf-password" required="true"  maxlength="25" />
              <span class="text-danger cust-error" id="conf-password-req"></span>
              <span class="text-danger cust-error" id="conf-password-format">Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character.</span>
             </div>
          </div>       
          <form:hidden path="userId" id="userId"/>   
          <div class="det-row" >
			    <div id="divOtp" class="det-row-col astrick" style="display: none;">
				    <div class="label-text">Enter OTP  </div>
				    <input id="otp" type="text"  maxlength="6" name="otp" class="form-control" /> 
					<!-- <span class="input-group-btn">
						<button class="btn btn-primary" id="verifyOtp" >Verify OTP</button>
					</span> -->
					<input id="verifyOtp" type="button" class="sim-button button7" value="Verify OTP" />
					<input type="button" onclick="verifyUserIdAndSendOtp();" class="sim-button button7" value="Resend OTP" />
					<!-- <span class="input-group-btn">
						<button class="btn btn-primary" onclick="verifyUserIdAndSendOtp();" >Resend OTP</button>
					</span> --><br>
					<span class="text-danger cust-error" id="otp-req">This field is required.</span>
				</div>           		
			</div> 				   
        </div>
        	    <input id="otpVerified" type="hidden"  value="false"/>              	
        <div class="insidebtn"> 
        	<input id="RegisterBtn" type="Submit" class="sim-button button5" value="Create Account" />
        </div>
      </div>
    </section>
</form:form>