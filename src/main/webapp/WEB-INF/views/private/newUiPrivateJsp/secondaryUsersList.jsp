<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>
<%@include file="../common-alert.jsp"%>
<div class="loader"></div>
<section class="block">
	<div class="container">

		<%-- 	    <div class="brd-wrap">
	    	<div  id="listheader">
	       		<strong><a href="<spring:url value="/home#master_management" />">Master Management</a></strong> &raquo; <strong>Employee</strong>
	        </div>
	        <div  id="addheader">
	        	<strong><a href="#" id="gobacktolisttable">Employee</a></strong> &raquo; <strong>Add Employee</strong>
	        </div>
	    </div> --%>
		<div class="page-title" id="listheader">
			<a href="<spring:url value="/home#account_management"/>" class="back"><i
				class="fa fa-chevron-left"></i></a>Employee
		</div>
		<div class="page-title" id="addheader">
			<a href="#" id="gobacktolisttable" class="back" /><i
				class="fa fa-chevron-left"></i></a>Add Employee
		</div>
		<div class="form-wrap" id="addEmployeeButton">
			<div class="row">
				<div class="col-md-12 button-wrap">
					<button id="addEmployee" type="button" style="width: auto;"
						class="btn btn-success blue-but">Add</button>
				</div>
			</div>
		</div>

		<div class="row" id="listTable">
			<div class="table-wrap">
				<table id="employeeTab" class="display nowrap" style="width: 100%">
					<thead>
						<tr>
							<th>Name</th>
							<th>Mobile</th>
							<th>Email</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		<input type="hidden" id="captchaError" value="${captchaError}">
		<div class="form-wrap" id="addEmployeeDetails">
			<form:form commandName="userMaster" id="userMasterForm" method="post"
				action="./getSecondaryUser">
				<input type="hidden" id="_csrf_token" name="_csrf_token"
					value="${_csrf_token}" />
				<div class="row">
					<div class="col-md-4">
						<label for="label">Name <span>*</span></label>
						<form:input path="userName" id="userName" maxlength="100"
							required="true" class="form-control" />
						<span class="text-danger cust-error" id="cust-name-req">This
							field is required</span>
					</div>

					<div class="col-md-4">
						<label for="label">Mobile No. (This will be User Id of
							your Employee)<span>*</span>
						</label>
						<form:input path="contactNo" id="contactNo" maxlength="10"
							required="true" class="form-control" />
						<span class="text-danger cust-error" id="contact-no-req">This
							field is required and should be 10 digits.</span> <span
							class="text-danger cust-error" id="contactNo-exist"></span>
					</div>
					<div class="col-md-4">
						<label for="label">MPIN (Password)<span>*</span></label>
						<form:password path="password" id="password" maxlength="25"
							required="true" class="form-control" />
						<span class="text-danger cust-error" id="password-req">This
							field is required</span>
						<!-- <span class="text-danger cust-error" id="password-req_length">Password length should minimum 8 and maximum 25 characters</span> -->
						<span class="text-danger cust-error" id="password-format">MPIN
							length should be minimum 8 and maximum 25 characters Min 1 digit,
							1 Uppercase letter, 1 lowercase letter, 1 special character</span>
					</div>
				</div>





				<div class="row">
					<div class="col-md-4">
						<label for="label">Confirm MPIN <span>*</span></label> <input
							type="password" name="confPassword" maxlength="25"
							id="confPasswordSecUser" required class="form-control" /> <span
							class="text-danger cust-error" id="conf-password-req">This
							field is required & should match with Password</span> <span
							class="text-danger cust-error" id="conf-password-req_length">MPIN
							length should be minimum 8 and maximum 25 characters</span>
					</div>

					<div class="col-md-4">
						<label for="label">Email ID</label>
						<form:input path="emailId" id="emailId" maxlength="100"
							class="form-control" />
						<span class="text-danger cust-error" id="reg-email-req">This
							field should be in a correct format</span>
					</div>
					<div class="col-md-4">
						<label for="label">Aadhar Number</label>
						<form:input path="secUserAadhaarNo" id="secUserAadhaarNo"
							maxlength="12" class="form-control" />
						<span class="text-danger cust-error" id="aadharNo-req">This
							field should be of 12 digits.</span>
					</div>
				</div>
				<form:hidden path="userId" maxlength="10" id="userId" />
				<div class="row">
					<div class="col-md-12 button-wrap">
						<button type="submit" id="secSubmitBtn" style="width: auto;"
							class="btn btn-success blue-but" value="add">Save</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</section>

<form name="manageSecondaryUser" method="post">
	<input type="hidden" name="id" value=""> <input type="hidden"
		id="_csrf_token1" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/secondaryUser/manageSecondaryUser.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/secondaryUser/addSecondaryUser.js"/>"></script>
