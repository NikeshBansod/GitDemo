<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


      <div class="loader"></div>
<section class="block">
	<div class="container">
		 
	    <div class="brd-wrap">
	    	<div  id="listheader">
	       	<strong>	<a href="<spring:url value="/secondaryUserPage" />">Employee </a></strong> &raquo; <strong>Edit Employee </strong>
	        </div>
	        
	    </div>



 <div class="form-wrap"  >	    
			<form:form commandName="userMaster" method="post" action="./updateSecondaryUser">
            	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />		    	
				<div class="row">	
					<div class="col-md-6">
						<label for="label">Name<span> *</span></label>
					  	<form:input path="userName" id="userName" maxlength="100" value="${userMasterObj.userName }" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="cust-name-req">This field is required</span> 					
					</div>
					
					<div class="col-md-6">
						<label for="label">Mobile No. (This will be User Id of your Employee)</label>
					 	<form:input path="contactNo" readonly="true" maxlength="10" value="${userMasterObj.contactNo }" required="true" class="form-control"/>
							
						<span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span>
					</div>
					</div>
					
					
					<div class="row">	
					<div class="col-md-6">
						<label for="label">Email Id</label>
						<form:input path="emailId" maxlength="100" value="${userMasterObj.emailId }" class="form-control"/> 
							
						<span class="text-danger cust-error" id="reg-email-req">This field should be in a correct format</span>
					</div>		
					
					
					<div class="col-md-6">
						<label for="label">Aadhar Number </label>
						<form:input path="secUserAadhaarNo" id="secUserAadhaarNo" maxlength="12" value="${userMasterObj.secUserAadhaarNo }" class="form-control"/>
							
						<span class="text-danger cust-error" id="aadharNo-req">This field is required and should be 12 digits.</span>					</div>								
				</div>
				<form:hidden path="id" value="${userMasterObj.id }"/>
			   		<form:hidden path="createdBy" value="${userMasterObj.createdBy }"/>
			  		<form:hidden path="userId" value="${userMasterObj.userId }"/>
			   		<form:hidden path="userRole" value="${userMasterObj.userRole }"/>
			   		 <form:hidden path="password" value="ZZZZZZZZZ1HHHfHHH#@H"/>  <!-- pattern regex in entity does not allow encrypted value -->
			   		<input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
				 <div class="row">
			        <div class="col-md-12 button-wrap">
			            <button  type="submit" style="width: auto;" id="secSubmitBtn" class="btn btn-success blue-but" value="update">Update</button> 
			       		<button id="srvDeleteBtn" type="button" onclick="javascript:deleteRecord('${userMasterObj.id}');" style="width: auto;" class="btn btn-success blue-but" value="Delete">Delete</button>
			        </div>
			    </div>

</form:form>
</div>
</div>
</section>



<form name="manageSecondaryUser" method="post">
    <input type="hidden" name="id" value="">
   <input type="hidden" id="_csrf_token1" name="_csrf_token" value="${_csrf_token}" />
</form>

 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/secondaryUser/manageSecondaryUser.js"/>"></script>