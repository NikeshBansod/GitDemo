<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addCustomerDetails/editcustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addCustomerDetails/manageCustomerDetails.js"/>"></script>

 
 <header class="insidehead">
      <a href="<spring:url value="/customerDetails" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
       <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

 
<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit Customer Details</div>
			   
			   
          <div class="acc_content">
          <form:form commandName="customerDetails"  method="post" action="./updateCustomerDetails" >
          
          <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
          
            <!--content-->
            
            <div class="box-content">
            
              <div class="">
               <span><center><h4><b>Edit Customer Details</b></h4></center></span>
              <ul>
                 
				  <li>
					<c:if test="${customerDetailsObj.custType == 'Organization'}">
                  <div class="radio-inline text-left">
                            <span style="color: black;">Customer Type</span>
                            <div class="rdio rdio-success">
                                <input type="radio" name="custType" value="Individual" id="Individual"  >
                                <label for="Individual">Not Registered Under GST</label>
                            </div>
                            <div class="rdio rdio-success">
                                <input type="radio" name="custType" value="Organization" id="Organization" checked="checked" >
                                <label for="Organization">Registered Under GST</label>
                            </div>
                        </div> 
                  </c:if>
				  <c:if test="${customerDetailsObj.custType == 'Individual'}">
				   <div class="radio-inline text-left">
                            <span style="color: black;">Customer Type</span>
                            <div class="rdio rdio-success">
                                <input type="radio" name="custType" value="Individual" id="Individual" checked="checked" >
                                <label for="Individual">Not Registered Under GST</label>
                            </div>
                            <div class="rdio rdio-success">
                                <input type="radio" name="custType" value="Organization" id="Organization"  >
                                <label for="Organization">Registered Under GST</label>
                            </div>
                        </div> 
                  </c:if>										
				  </li>
				   <li>
                  <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="custName" id="custName" maxlength="100" value="${customerDetailsObj.custName }" required="true" class="form-control" />
						<div class="label-text label-text2">Customer Name</div>
					</label>
					<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
					</div>
				  </li>
				  <li>
				   <div class="form-group input-field " id="divGstinNo">
					<label class="label">
						<input type="hidden" id="custGstinHidden" value="${customerDetailsObj.custGstId }">  
						<form:input maxlength="15"  path="custGstId" value="${customerDetailsObj.custGstId }" class="form-control"  />
						<div class="label-text label-text2">GSTIN</div>
					</label>
					<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
					</div>
				  </li>
				     <li>
				  <div class="form-group input-field " id="divGstinState">
					<label class="label">
					<input type="hidden" id="custGstinStateHidden" value="${customerDetailsObj.custGstinState }">  
						<form:select path="custGstinState"  id="custGstinState" class="form-control"/>
						<div class="label-text label-text2">GSTIN State</div>
					</label>
					<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
				  </div>
				  </li>
				   <li>
				  <div class="form-group input-field ">
					<label class="label">  
						<form:input path="contactNo" id="contactNo" value="${customerDetailsObj.contactNo }"  maxlength="10" class="form-control"/>
						<div class="label-text label-text2">Mobile Number</div>
					</label>
					<span class="text-danger cust-error" id="contact-no-req">This field is required.</span>
					</div>
				  </li>
				  <li>
				   <div class="form-group input-field ">
					<label class="label">  
						<form:input path="custEmail" maxlength="100" id="custEmail" value="${customerDetailsObj.custEmail }" class="form-control"/>
						<div class="label-text label-text2">Email ID</div>
					</label>
					<span class="text-danger cust-error" id="cust-email-format">Email should be in correct format.</span>
					</div>
				  </li>
				  
				   <li>
				   <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="pinCode" id="pinCode" value="${customerDetailsObj.pinCode }" maxlength="6" required="true" class="form-control"/>
						<div class="label-text label-text2">Pin Code</div>
					</label>
					<span class="text-danger cust-error" id="empty-message"></span>
					<span class="text-danger cust-error" id="cust-zip-req">Pin Code is required and should be 6 digits.</span>
					</div>
				  </li>
				   <li>
				    <div class="form-group input-field mandatory">
					<label class="label">  
					  <form:input path="custCity" readonly="true" id="custCity" value="${customerDetailsObj.custCity }" required="true" class="form-control"/> 
						<div class="label-text label-text2">City</div>
					</label>
					</div>
				  </li>
				   <li>
				    <div class="form-group input-field mandatory">
					<label class="label">  
					 	<form:input path="custState" readonly="true" id="custState" value="${customerDetailsObj.custState }" required="true" class="form-control"/>  
						<div class="label-text label-text2">State</div>
					</label>
					<span class="text-danger cust-error" id="custState-err"></span>
					</div>
				  </li>
				   <li>
				   <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="custCountry" readonly="true" value="${customerDetailsObj.custCountry }" required="true" class="form-control"/>
						<div class="label-text label-text2">Country</div>
					</label>
					</div>
				  </li>
				  <li>
				  <div class="form-group input-field" id="divAddr">
					<label class="label">  
					<!-- 	<form:input path="custAddress1" value="${customerDetailsObj.custAddress1 }"  maxlength="350" class="form-control"/>  -->
							<input type="hidden" id="custAddress" value="${customerDetailsObj.custAddress1 }">  
						<form:textarea path="custAddress1" id="custAddress1" maxlength="350" class="form-control"/>
						<div class="label-text label-text2">Address</div>
					</label>
					<span class="text-danger cust-error" id="address1-req">This field is required </span>
					</div>
				  </li>
				  
			   <form:hidden path="id" value="${customerDetailsObj.id }"/>
			   <form:hidden path="status" value="${customerDetailsObj.status }"/>
	      	   <form:hidden path="createdOn" value="${customerDetailsObj.createdOn }"/>
	      	   <form:hidden path="createdBy" value="${customerDetailsObj.createdBy }"/>
	      	   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
	      	   
                   </ul>
              </div>
            </div>
           
            <!--content end-->
	      	  
			 <div class="com-but-wrap">
            	<button type="submit" id="custSubmitBtn" class="btn btn-primary">Update</button>
            	<a href="#" onclick="javascript:deleteRecord('${customerDetailsObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
            </div>
            </form:form>
		</div>
			  
			</div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      
		
		<!-- <div class="customerValuesTable">	
		<div class="card">
		<table class="table table-striped table-bordered customerValues" id="customerValuesTab" >
				<thead>
							<tr>
								<th><center>Sr.No.</center></th>
								<th><center>Customer Name.</center></th>
							</tr>
						</thead>
			</table>							
		</div>
		</div> -->
		
	
</div>

<form name="editCustomerDetails" method="post">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>