<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/secondaryUser/wizardAddSecondaryUser.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/secondaryUser/wizardManageSecondaryUser.js"/>"></script>

				
		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Manage Employees
		 		</div>
		 		<div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardSecondaryUserPage"/>">Manage Employees</a> <span>&raquo;</span> Manage Employee
	 			</div>
			</div>	
			<div class="container">							
				<div class="row text-center">				   			  			
		    		<a class="btn-floating btn-large" title="Add Employee" id="addEmployee" >
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>	
		    	</div>							
				<div class="cust-wrap">		
					<div  id="toggle"><br>		
						<table class="table table-striped table-bordered" id="secondaryUserTab">
							<thead>
								<tr>
									<th style="text-align: center;">Sr.No.</th>
									<!-- <th></th> -->
									<th style="text-align: center;">Name</th>
									<!-- <th style="text-align: center;">View</th> -->
									<th style="text-align: center;">Edit</th>
									<th style="text-align: center;">Delete</th>
								</tr>
							</thead>
							<tbody>									
								
							</tbody>
						</table>						
					</div>
		      </div>
			</div>		
			
			<br>
			  <input type="hidden" id="captchaError" value="${captchaError}">
			<div id="addEmployeeDetails">
				<form:form commandName="userMaster" id="userMasterForm" method="post" action="./getWizardSecondaryUser ">
				
   					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">Name</div>
							  	<form:input path="userName" id="userName" maxlength="100" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="cust-name-req">This field is required</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">Mobile No. (This will be User Id of your Employee)</div>
							  	<form:input path="contactNo" id="contactNo" maxlength="10" required="true" class="form-control"/>
							  	<!-- <span class="text-danger cust-error" id="ser-sac-desc">This field is required.</span> -->
							</div> 
							<div class="det-row-col astrick">
								<div class="label-text">MPIN (Password)</div>
							  	<form:password path="password" id="password" maxlength="25" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="password-req">This field is required</span>
								<span class="text-danger cust-error" id="password-format">MPIN length should be minimum 8 and maximum 25 characters Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character</span>
							</div> 					
						</div>
						<div class="det-row"> 
							<div class="det-row-col astrick">
								<div class="label-text">Confirm MPIN</div>
							  	<input type="password" name="confPassword" maxlength="25" id="confPasswordSecUser" required class="form-control"/>  
							  	<span class="text-danger cust-error" id="conf-password-req">This field is required & should match with Password</span>
								<span class="text-danger cust-error" id="conf-password-req_length">MPIN length should be minimum 8 and maximum 25 characters</span>
							</div>								
							<div class="det-row-col ">
								<div class="label-text">Email ID</div>
							 	<form:input path="emailId" id="emailId" maxlength="100" class="form-control"/> 
							 	<span class="text-danger cust-error" id="reg-email-req">This field should be in a correct format</span>
							</div>  	
							<div class="det-row-col ">
								<div class="label-text">Aadhar Number</div>
								<form:input path="secUserAadhaarNo" id="secUserAadhaarNo" maxlength="12" class="form-control"/>
							 	<span class="text-danger cust-error" id="aadharNo-req">This field should be of 12 digits.</span>
							</div>
						</div>						
						<form:hidden path="userId"  maxlength="10" id="userId" /> 
					</div>
			      	<div class="insidebtn"> 	       	
						<input id="secSubmitBtn" type="Submit" class="sim-button button5" value="add" /> 
						<%-- <a href="<spring:url value="/wHome#master"/>" type="Submit" class="sim-button button5" >Cancel</a> --%>
					</div>
					<input type="hidden" name="tempUom" id="tempUom">
		  		</form:form>			            
			   </div>
		
		</section>

<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add Employee" id="addEmployee" >
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> --%>

<form name="manageSecondaryUser" method="post">
    <input type="hidden" name="id" value="">
				<input type="hidden" id="_csrf_token1" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/secondaryUser/loadSecondaryUserDatatable.js"/>"></script>