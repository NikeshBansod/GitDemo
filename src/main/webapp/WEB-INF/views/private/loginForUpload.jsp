<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/uploadToAsp/manageUploadAspDetails.js"/>"></script>

 <header class="insidehead">
      <a href="<spring:url value="/aspMasters" />" class="btn-back"><i class="fa fa-angle-left"></i>
      <a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a> 
      </a>
 </header>

<div class="container" >
     
  <div class="box-border">
  <form:form commandName="aspUserDetail" id="aspLogin"  autocomplete="false" method="post" action="./getJioGstLoginPage" >	
 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
          <div class="acc_content">
            <!--content-->
            </div>
            <div class="box-content">
              <div class="card">
              <span><center><h4><b>Login to JioGST</b></h4></center></span>
                <ul>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
						<input type="text" id="panNumber" disabled="true" class="form-control" value="${loginMaster.panNo}">
							<form:hidden path="panNo" id="panNo" class="form-control"/>
							<div class="label-text label-text2">PAN</div>
						</label>
					</div>
				  </li>
                  <li>
					<div class="form-group input-field ">
						<label class="label">  
						<form:input path="userId" autocomplete="false" maxlength="10"  id="userId" required="true" class="form-control" />
					       <div class="label-text label-text2">JioGST Login Id</div>
						</label>
						<span class="text-danger cust-error" id="userId-req">This field is required. </span>
		 			</div>
				  </li>
				   <li>
				  	 <div class="form-group input-field ">
					 	<label class="label">  
					 	<form:input path="password" autocomplete="new-password" maxlength="14" type="password" id="password" required="true" class="form-control"/>
					      <div class="label-text label-text2">JioGST Password</div>
					    </label>
					    <span class="text-danger cust-error" id="password-req">This field is required. </span>
					 </div>
				  </li>
				  
				  <input type="hidden" id="usrId" value="${aspUserDetailObj.userId}" >
				  <input type="hidden" id="pswd"  value="${aspUserDetailObj.password}" >
				  <form:hidden path="id" id="uid" value="${aspUserDetailObj.id}" />
			    </ul>
              </div>
            </div>
     
      	  <div class="com-but-wrap">
            <button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Login</button>
           <a class="btn btn-secendory"  href="./aspMasters">Cancel</a>
            <!-- <button type="button" id="cancel1" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button> -->
            </div>
     
      	
     </form:form>
    
  </div>
</div>
<br/>
<!--common end-->
