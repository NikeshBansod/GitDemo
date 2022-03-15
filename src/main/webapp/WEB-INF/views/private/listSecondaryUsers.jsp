<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>


 <link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>



 <header class="insidehead">
      <a href="<spring:url value="/home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
 
<div class="container">
  <div class="box-border">
  <input type="hidden" id="captchaError" value="${captchaError}">
  <form:form commandName="userMaster" id="userMasterForm" method="post" action="./getSecondaryUser">
  	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition hide hidden">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head">Add Employee</div>
          <div class="acc_content">
            <!--content-->
            <div class="box-content">
              <div class="comm-input">
                <ul>
             
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Name</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Email ID</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Mobile Number</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">User ID</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Password</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Confirm Password</div>
					</label>
				  </li>
				  <li>
					<label class="label captchaBox">  
						<input type="text"  />  
						<div class="label-text">Captcha</div>
						<span class="captchaImg"><img src="images/captcha.png"></span>
						<span class="captchaRefresh"><i class="fa fa-refresh"></i></span>
					</label>
				  </li>
                 
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap">
            		<button type="submit" class="blue-but">Save</button>
            		<button class="blue-but white-but">Cancel</button>
            		<a class="blue-but white-but" href="www.google.com" target="blank">Check</a>
            </div>
          </div>
        </div>
      <!--01 end-->
      </div>

      <div class="addCustomer">
      	<div class="box-content">
      	<span><center><h4><b>Manage Employee</b></h4></center></span>
              <div class="">
              
                <ul>
                 <li>
                 	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="userName" id="userName" maxlength="100" required="true" class="form-control"/>
							<div class="label-text label-text2">Name</div>
						</label>
						<span class="text-danger cust-error" id="cust-name-req">This field is required</span>
					</div>
				  </li>
				   <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="contactNo" id="contactNo" maxlength="10" required="true" class="form-control"/>
							<div class="label-text label-text2">Mobile no. (This will be User Id of your Employee)</div>
						</label>
						<span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span>
						<span class="text-danger cust-error" id="contactNo-exist"></span>
					</div>
				  </li>
				   <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:password path="password" id="password" maxlength="25" required="true" class="form-control"/>
							<div class="label-text label-text2">MPIN (Password)</div>
						</label>
						<span class="text-danger cust-error" id="password-req">This field is required</span>
						<!-- <span class="text-danger cust-error" id="password-req_length">Password length should minimum 8 and maximum 25 characters</span> -->
						<span class="text-danger cust-error" id="password-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
						
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="password" name="confPassword" maxlength="25" id="confPasswordSecUser" required class="form-control"/>  
							<div class="label-text label-text2">Confirm MPIN</div>
							
						</label>
						<span class="text-danger cust-error" id="conf-password-req">This field is required & should match with Password</span>
						<span class="text-danger cust-error" id="conf-password-req_length">MPIN length should be minimum 8 and maximum 25 characters</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:input path="emailId" id="emailId" maxlength="100" class="form-control"/> 
							<div class="label-text label-text2">Email ID</div>
						</label>
						<span class="text-danger cust-error" id="reg-email-req">This field should be in a correct format</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:input path="secUserAadhaarNo" id="secUserAadhaarNo" maxlength="12" class="form-control"/>
							<div class="label-text label-text2">Aadhar Number</div>
						</label>
						<span class="text-danger cust-error" id="aadharNo-req">This field should be of 12 digits.</span>
					</div>
				  </li>
				  <form:hidden path="userId"  maxlength="10" id="userId" /> 
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap new-button">
            	<button type="submit" class="btn btn-primary" id="secSubmitBtn" value="add" >Save</button>
            	<!-- <button class="blue-but white-but">Cancel</button> -->
            	<a class="btn btn-secendory"  href="secondaryUserPage">Cancel</a>
            </div>
      </div>
       </form:form>
      <!--customer details-->
      
      <div class="employeeValuesTable">	
		<div class="card">
		<h4 style="text-align: center;">Employees List</h4>
		<table class="table table-striped table-bordered employeeValues"  id="employeeValuesTab" >
				<thead>
							<tr>
								<th><center>Employee Name</center></th>
								<th><center>Mobile No</center></th>
							</tr>
				</thead>
				<tbody>
				
				</tbody>
			</table>							
		</div>
		</div>
     
  </div>
</div>

<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add Customer" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<!--common end-->

<form name="manageSecondaryUser" id="manageSecondaryUser"  method="post">
    <input type="hidden" name="id" value="">
   	<input type="hidden" id="_csrf_token1" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/secondaryUser/manageSecondaryUser.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/secondaryUser/addSecondaryUser.js"/>"></script>