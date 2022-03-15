<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/secondaryUser/manageSecondaryUser.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/secondaryUserPage" />" class="btn-back"><i id="commonEditAccordionId" class="fa fa-angle-left"></i> </a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
</header>
 
<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" class="acc_head" style="display:none;">Edit Employee</div>
        <div class="acc_content">
        <form:form commandName="userMaster" method="post" action="./updateSecondaryUser">
            	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <!--content-->
            
            <div class="box-content">
            <span><center><h4><b>Edit Employee</b></h4></center></span>
              <div class="">
                <ul>
                  <li>
                  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="userName" id="userName" maxlength="100" value="${userMasterObj.userName }" required="true" class="form-control"/>
							<div class="label-text label-text2">Name</div>
						</label>
						<span class="text-danger cust-error" id="cust-name-req">This field is required</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="contactNo" readonly="true" maxlength="10" value="${userMasterObj.contactNo }" required="true" class="form-control"/>
							<div class="label-text label-text2">Mobile no. (This will be your User Id of Employee)</div>
						</label>
						<span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:input path="emailId" maxlength="100" value="${userMasterObj.emailId }" class="form-control"/> 
							<div class="label-text label-text2">Email ID</div>
						</label>
						<span class="text-danger cust-error" id="reg-email-req">This field should be in a correct format</span>
					</div>
				  </li>
				  
				  <li>
				  	<div class="form-group input-field ">
						<label class="label">  
							<form:input path="secUserAadhaarNo" id="secUserAadhaarNo" maxlength="12" value="${userMasterObj.secUserAadhaarNo }" class="form-control"/>
							<div class="label-text label-text2">Aadhar Number</div>
						</label>
						<span class="text-danger cust-error" id="aadharNo-req">This field is required and should be 12 digits.</span>
					</div>
				  </li>
			   		<form:hidden path="id" value="${userMasterObj.id }"/>
			   		<form:hidden path="createdBy" value="${userMasterObj.createdBy }"/>
			  		<form:hidden path="userId" value="${userMasterObj.userId }"/>
			   		<form:hidden path="userRole" value="${userMasterObj.userRole }"/>
			   		 <form:hidden path="password" value="ZZZZZZZZZ1HHHfHHH#@H"/>  <!-- pattern regex in entity does not allow encrypted value -->
			   		<input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary" id="secSubmitBtn" value="update" >Update</button>
            	<a href="#" onclick="javascript:deleteRecord('${userMasterObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
            </div>
            
            </form:form>
            </div>
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap edit-Page-List">
		
		<div class="dnynamicSecondaryUsers" id="toggle">								
		</div>
		
   </div>
</div>
  
<form name="manageSecondaryUser" method="post">
    <input type="hidden" name="id" value="">
   <input type="hidden" id="_csrf_token1" name="_csrf_token" value="${_csrf_token}" />
</form>
