<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/uploadToAsp/wizardManageUploadAspDetails.js"/>"></script>

		<section class="insidepages">
		
		<div class="breadcrumbs" id="listHeader">
			<div class="col-md-12" id="listheader1">
		       	<a href="<spring:url value="/wAspMasters"/>">ASP Home</a> <span>&raquo;</span> ASP Login
			</div>			 		
		</div>
				<br>
				
		<div id="aspLoginContainer">

		<form:form commandName="aspUserDetail" id="aspLogin"  autocomplete="false" method="post" action="./wGetJioGstLoginPage" >

			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />

	        <div class="account-det">
	          <div class="det-row">
		            <div class="det-row-col-full astrick">
		            	<div class="label-text">PAN </div>
		              	<input type="text" id="panNumber" disabled="true" class="form-control" value="${loginMaster.panNo}">
							<form:hidden path="panNo" id="panNo" class="form-control"/>  
		            </div>
			  </div>
			  <div class="det-row">
		            <div class="det-row-col-half astrick">
		            	<div class="label-text">JioGST Login Id</div>
		              	<form:input path="userId" autocomplete="false" maxlength="10"  id="userId" required="true" class="form-control" />
		              	<span class="text-danger cust-error" id="userId-req">This field is required. </span>
		            </div>
		            <div class="det-row-col-half astrick">
		            	<div class="label-text">JioGST Password</div>
		              	<form:input path="password" autocomplete="new-password" maxlength="14" type="password" id="password" required="true" class="form-control"/>
		              	 <span class="text-danger cust-error" id="password-req">This field is required. </span>
		            </div>
			  </div>
			  <input type="hidden" id="usrId" value="${aspUserDetailObj.userId}" >
				 		 <input type="hidden" id="pswd"  value="${aspUserDetailObj.password}" >
				 		 <form:hidden path="id" id="uid" value="${aspUserDetailObj.id}" />
			 </div>
			 <div class="insidebtn"> 	        	
					<button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Login</button>
           <!-- <a class="btn btn-secendory"  href="./aspMasters">Cancel</a> -->
				</div>
			  			
		 </form:form>
		 </div>
		 </section>
 		  