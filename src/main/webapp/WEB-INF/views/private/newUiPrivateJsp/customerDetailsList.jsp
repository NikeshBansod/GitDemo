<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<div class="loader"></div>
<section class="block">
	<div class="container">
	<%--<div class="brd-wrap">
	    	 <div  id="listheader">
	       		<a href="<spring:url value="/home#master_management" />"><strong>Master Management</strong></a> &raquo; <strong> Customer Details</strong>
	        </div> --%>
	         <!-- <div  id="addheader">
	        	<a href="#" id="gobacktolisttable"><strong>Party Details</strong></a> &raquo; <strong>Add Customer Details</strong>
	        </div>
	         </div> -->
	        <div class="page-title" id="listheader">
                 <a href="<spring:url value="/home#master_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Customer Details
             </div>
	         <div class="page-title" id="addheader">
                <a href="#" id="gobacktolisttable" class="back"/><i class="fa fa-chevron-left"></i></a>Add Customer Details
             </div>
	
	
	<div class="form-wrap"  id="addCustomerDetailsButton">
	    <div class="row">
            <div class="col-md-12 button-wrap">			
				<button id="addCustomer"  type="button" class="btn btn-success blue-but" style="width: auto;">Add</button>
			</div>
		</div>
	</div>
	
	<div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="customerValuesTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <th>Name</th>
	                            <th>Type</th>
	                            <th>Party Type</th>
	                            <th>Status</th>
	                            <th>GSTIN</th>
	                            <th>State</th>
	                            <th>Mobile no.</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	            </table>
	        </div>
	    </div>	
	    
	     <div class="form-wrap"  id="addCustomerDetails">
	      <form:form commandName="customerDetails" method="post" id="custForm" action="./addCustomerDetails">
               <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
               <div class="row">
                  <div class="col-md-6 diff">
                    <label class="form-section-title">Party Type</label>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custPartyType" value="Supplier" id="Supplier" checked="checked">
                         <label for="Supplier">Supplier</label>
                     </div>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custPartyType" value="Customer" id="Customer">
 		                  <label for="Customer">Customer</label>
                     </div>   
                 </div>
                  <!-- </div> -->
                <!-- <div class="row"> -->
                  <div class="col-md-6 diff">
                     <!-- <label class="form-section-title">Customer Type</label>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Individual" id="Individual" checked="checked">
                         <label for="Individual">Not Registered Under GST</label>
                     </div>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Organization" id="Organization">
 		                  <label for="Organization">Registered Under GST</label>
                     </div>  -->    
                      <label class="form-section-title">GST Registration</label>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Individual" id="Individual" checked="checked">
                         <label for="Individual">Unregistered</label>
                     </div>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Organization" id="Organization">
 		                  <label for="Organization">Registered-Normal</label>
                     </div>  
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="OrganizationComposition" id="OrganizationComposition">
                         <label for="OrganizationComposition">Registered-Composition</label>
                     </div>                         
                 </div>
                </div> 
           <!-- <div class="row ruGST" id="divGst"> -->
           <div class="row">
          <!--  <div class="form-group"> -->
              <div class="col-md-4" id="divGstinNo" style="display: none;">
                  <label for="label">GSTIN<span style="font-weight: bold;color: #ff0000;"> *</span></label>
                  <form:input path="custGstId" maxlength="15" id="custGstId"/>
                  <div class="errormsg"><span class="text-danger cust-error" id="custGstId-req">This field is required and should be in a proper format.</span> </div>
              <!--  </div> -->
                </div>
              <!--   <div class="form-group"> -->
              <div class="col-md-4" id="divGstinState" style="display: none;">
                  <label for="label">GSTIN State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
                  <form:select path="custGstinState"  id="custGstinState"/>
                  <div class="errormsg"><span class="text-danger cust-error" id="custGstId-req-state">This field is required</span></div>
               <!-- </div> -->
               </div>
              <!--  <div class="form-group"> -->
              <div class="col-md-4">
                  <label for="label">Name <span style="font-weight: bold;color: #ff0000;height:120px;"> *</span></label>
                  <form:input path="custName" id="custName" maxlength="100" required="true"/>
                  <div class="errormsg"><span class="text-danger cust-error" id="cust-name-req">This field is required.</span></div>
              <!-- </div> -->
              </div>
             <!--  <div class="form-group"> -->
              <div class="col-md-4">
                  <label for="label">Mobile Number</label>
                  <form:input path="contactNo" id="contactNo"  maxlength="10"/> 
                  <div class="errormsg"><span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span> </div>
             <!--  </div> -->
               </div>
             <!--   <div class="form-group"> -->
              <div class="col-md-4">
                  <label for="label">Email ID</label>
                  <form:input path="custEmail" maxlength="100" id="custEmail"/>
                  <div class="errormsg"><span class="text-danger cust-error" id="cust-email-format">This field should be in correct format.</span></div>
			<!--  </div> -->
			 </div>
          </div>
          <div class="row">
               <div class="col-md-4">
                  <label for="label">Pin Code<span style="font-weight: bold;color: #ff0000;"> *</span></label>
                  <input type="text" name="pinCode" required="true" id="pinCode" maxlength="6"/> 						
				     <span class="text-danger cust-error" id="empty-message"></span>
					<span class="text-danger cust-error" id="cust-zip-req">This field is required</span>
			 </div>
               <div class="col-md-4">
                  <label for="label">City<span style="font-weight: bold;color: #ff0000;"> *</span></label>
                  <input type="text" readonly="readonly" id="custCity" name="custCity" />
                  <span class="text-danger cust-error" id="cust-city-req">This field is required</span>
               </div>
               <div class="col-md-4">
                  <label for="label">State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
                  <input type="text" readonly="readonly" id="custState" name="custState"/>  
                  <span class="text-danger cust-error" id="custState-err">This field is required</span>
               </div>
            </div>
           <div class="row">
              <div class="col-md-4">
                  <label for="label">Country<span style="font-weight: bold;color: #ff0000;"> *</span></label>
                  <input type="text" readonly="readonly" name="custCountry" required="true" id="custCountry"/>  
              </div>
              <div class="col-md-4" id="divAddr">
                   <label for="label" class="label-adrr">Address</label>
                  <!--  <div class="label-text"><strong>Address</strong></div> -->
                  <form:textarea path="custAddress1" id="custAddress1" maxlength="350" required="true" class="form-control" /> 
                  <span class="text-danger cust-error" id="address1-req">This field is required </span>
               </div>
               
               
           </div>
              <div class="col-md-12 button-wrap"> 	        	
						<button type="submit" class="btn btn-success blue-but" id="custSubmitBtn" formnovalidate="formnovalidate" style="width: auto;">Save</button> 
				</div>
               </form:form>
	     	</div>
		</div>
</section>

<form name="editCustomerDetails" method="post">
 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <input type="hidden" name="id" value="">
     <input type="hidden" name="userId" value="">
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addCustomerDetails/manageCustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addCustomerDetails/addCustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addCustomerDetails/pincode-autocomplete-customer.js"/>"></script>


