<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

 <div class="container" >
  <div class="box-border" >
  		
  <form:form commandName="customerDetails" method="post" id="custForm" action="./addCustomerDetails">
  
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  
    <div class="accordion_example2 no-css-transition hide hidden">
      <!--01-->
      <div  class="accordion_in acc_active">
        <div class="acc_head">Add Customer</div>
          <div class="acc_content">
            <!--content-->
            <div class="box-content">
              <div class="comm-input">
                <ul>
             
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Customer Name</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Customer Type</div>
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
						<div class="label-text">GSTIN</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Address1</div>
					</label>
				  </li>
				   <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Address2</div>
					</label>
				  </li>
				   <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Pin Code</div>
					</label>
				  </li>
				   <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">City</div>
					</label>
				  </li>
				   <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">State</div>
					</label>
				  </li>
				   <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Country</div>
					</label>
				  </li>
				                  
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" class="blue-but">Save</button><button  class="blue-but white-but">Cancel</button></div>
          </div>
        </div>
      <!--01 end-->
      </div>

      <div class="addCustomer">
      	<div class="box-content">
      	<span><center><h4><b> Manage Customers </b></h4></center></span>
              <div class="">
                <ul>
				  <li>
					<div class="form-group radio input-field">
                        <span>Customer Type</span>
                        <div class="rdio rdio-success">
                            <input type="radio" name="custType" value="Individual" id="Individual" checked="checked">
                            <label for="Individual">Not Registered Under GST</label>
                        </div>
                        <div class="rdio rdio-success">
                            <input type="radio" name="custType" value="Organization" id="Organization">
                            <label for="Organization">Registered Under GST</label>
                        </div>
                     </div> 
 				     <span class="text-danger cust-error" id="cust-sel-req">This field is required.</span> 

				  </li>
				  <li>
				  <div class="form-group input-field " id="divGstinNo" style="display: none;">
					<label class="label">  
						<form:input path="custGstId" maxlength="15" id="custGstId" class="form-control"/>
						<div class="label-text label-text2">GSTIN</div>
					</label>
					<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
				  </div>
				  </li>
				    <li>
				  <div class="form-group input-field " id="divGstinState" style="display: none;">
					<label class="label">  
						<form:select path="custGstinState"  id="custGstinState" class="form-control"/>
						<div class="label-text label-text2">GSTIN State</div>
					</label>
					<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
				  </div>
				  </li>
				  <li>
                 <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="custName" id="custName" maxlength="100" required="true" class="form-control"/>
						<div class="label-text label-text2">Customer Name</div>						
					</label>
					<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
					</div>
				  </li>
				   <li>
				  <div class="form-group input-field ">
					<label class="label">  
						<form:input path="contactNo" id="contactNo"  maxlength="10" class="form-control"/> 
						<div class="label-text label-text2">Mobile Number</div>
					</label>
					
					<span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span>
						</div>				
				  </li>
				  <li>
				  <div class="form-group input-field">
					<label class="label">  
						<form:input path="custEmail" maxlength="100" id="custEmail" class="form-control"/>
						<div class="label-text label-text2">Email ID</div>
					</label>
					<span class="text-danger cust-error" id="cust-email-format">This field should be in correct format.</span>
				  </div>
				  </li>
				  <li>
				  <div class="form-group input-field mandatory">
					<label class="label">  
						<input type="text" name="pinCode" required="true" id="pinCode" maxlength="6" class="form-control"/> 						
						<div class="label-text label-text2">Pin Code</div> 
					</label>
					<span class="text-danger cust-error" id="empty-message"></span>
					<span class="text-danger cust-error" id="cust-zip-req">Pin Code is required and should be 6 digits.</span>
					</div>
				  </li>
				  <li>
				   <div class="form-group input-field mandatory">
					<label class="label">  
						<input type="text" readonly="readonly" id="custCity" name="custCity" class="form-control" />  
						<div class="label-text label-text2">City</div>
					</label>
					</div>
				  </li>
				   <li>
				    <div class="form-group input-field mandatory">
					<label class="label">  
						<input type="text" readonly="readonly" id="custState" name="custState" class="form-control"/>  
						<div class="label-text label-text2">State</div>
					</label>
					<span class="text-danger cust-error" id="custState-err"></span>
					</div>
				  </li>
				   <li>
				   <div class="form-group input-field mandatory">
					<label class="label">  
						<input type="text" readonly="readonly" name="custCountry" required="true" id="custCountry" class="form-control"/>  
						<div class="label-text label-text2">Country</div>
					</label>
					</div>
				  </li>
				  <li>
				  <div class="form-group input-field mandatory" id="divAddr">
					<label class="label">
						<form:textarea path="custAddress1" id="custAddress1" maxlength="350" required="true" class="form-control"/>  
						<div class="label-text label-text2">Address</div>
					</label>
					<span class="text-danger cust-error" id="address1-req">This field is required </span>
					</div>
				  </li>
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="custSubmitBtn" formnovalidate="formnovalidate">Save</button>
            <button id="cancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button></div>
      </div>
       </form:form>
      <!--customer details-->
      
		
		
     
  </div>
</div>
<div class="customerValuesTable " style="background-color:white; padding:5px;">	
		<h4 style="text-align: center;">Customer Details</h4>
		<table class="table table-responsive table-striped table-bordered customerValues"  id="customerValuesTab"  > 
				<thead>
							<tr>
								<th style="text-align: center;">Customer Name</th>
								<th style="text-align: center;">GSTIN</th>
							</tr>
						</thead>
			</table>							
		
		</div>
 
<div class="fixed-action-btn" >
	<a class="btn btn-floating btn-large" title="Add Customer" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<!--common end-->

<form name="editCustomerDetails" method="post">
 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <input type="hidden" name="id" value="">
     <input type="hidden" name="userId" value="">
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/addCustomerDetails/manageCustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addCustomerDetails/addCustomerDetails.js"/>"></script>
