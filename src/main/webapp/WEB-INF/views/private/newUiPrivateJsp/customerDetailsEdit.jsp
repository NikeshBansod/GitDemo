<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


 
<div class="loader"></div>
<section class="block">
	<div class="container">
	<form:form commandName="customerDetails"  method="post" action="./updateCustomerDetails" >
          <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
          
	    <%--<div class="brd-wrap">
	    	 <div  id="listheader">
	       		<a href="<spring:url value="/customerDetails" />"><strong>Customer Details </strong></a> &raquo; <strong> Edit Customer Details</strong>
	        </div> 
	        </div>--%>
	        <div class="page-title" id="listheader">
                <a href="<spring:url value="/customerDetails"/>" class="back"/><i class="fa fa-chevron-left"></i></a>Customer Details
             </div>
	    
	    
	 <div class="form-wrap">
	    <div class="row">
	     <c:if test="${customerDetailsObj.custPartyType == 'Supplier'}">
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
	    </c:if>
	    <c:if test="${customerDetailsObj.custPartyType == 'Customer'}">
	    <div class="col-md-6 diff">
                    <label class="form-section-title">Party Type</label>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custPartyType" value="Supplier" id="Supplier" >
                         <label for="Supplier">Supplier</label>
                     </div>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custPartyType" value="Customer" id="Customer" checked="checked">
 		                  <label for="Customer">Customer</label>
                     </div>   
                 </div>
	    </c:if>
	    <c:if test="${customerDetailsObj.custType == 'Organization'}">
	    <div class="col-md-6 diff">
                     <label class="form-section-title">GST Registration</label>
                     <div class="radio radio-success radio-inline">
                      <input type="radio" name="custType" value="Individual" id="Individual"  >
                         <label for="Individual">Unregistered</label>
                     </div>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Organization" id="Organization" checked="checked" >
                         <label for="Organization">Registered-Normal</label>
                     </div>  
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="OrganizationComposition" id="OrganizationComposition">
                         <label for="OrganizationComposition">Registered-Composition</label>
                     </div>                               
                 </div>
	    </c:if>
	    <c:if test="${customerDetailsObj.custType == 'OrganizationComposition'}">
	    <div class="col-md-6 diff">
                     <label class="form-section-title">Customer Type</label>
                     <div class="radio radio-success radio-inline">
                      <input type="radio" name="custType" value="Individual" id="Individual"  >
                         <label for="Individual">Unregistered</label>
                     </div>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Organization" id="Organization">
                         <label for="Organization">Registered-Normal</label>
                     </div> 
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="OrganizationComposition" id="OrganizationComposition" checked="checked" >
                         <label for="OrganizationComposition">Registered-Composition</label>
                     </div>                                
                 </div>
	    </c:if>
	    <c:if test="${customerDetailsObj.custType == 'Individual'}">
	    <div class="col-md-6 diff">
                     <label class="form-section-title">Customer Type</label>
                     <div class="radio radio-success radio-inline">
                         <input type="radio" name="custType" value="Individual" id="Individual" checked="checked" >
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
	    </c:if>
	    
	    </div>
<div class="row">
	 <div class="col-md-4" id="divGstinNo">
	    <label for="label">GSTIN<span style="font-weight: bold;color: #ff0000;"> *</span></label>
		<form:input maxlength="15"  path="custGstId" value="${customerDetailsObj.custGstId }"  />
		<div class="errormsg"><span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span></div>
		<input type="hidden" id="custGstinHidden" value="${customerDetailsObj.custGstId }">  
	    
	 </div>
	 <div class="col-md-4" id="divGstinState" >
	    
	     <label for="label">GSTIN State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
		 <form:select path="custGstinState"  id="custGstinState"/>
		<div class="errormsg"><span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span></div>
		 <input type="hidden" id="custGstinStateHidden" value="${customerDetailsObj.custGstinState }">  
		
	 </div>
	 <div class="col-md-4">
	  
	    <label for="label">Name <span style="font-weight: bold;color: #ff0000;"> *</span></label>
	    <form:input path="custName" id="custName" maxlength="100" value="${customerDetailsObj.custName }" required="true" />
	   <div class="errormsg"><span class="text-danger cust-error" id="cust-name-req">This field is required.</span></div>
	    
	 </div>
	 <div class="col-md-4">
	    
	    <label for="label">Mobile Number</label>
	    <form:input path="contactNo" id="contactNo" value="${customerDetailsObj.contactNo }"  maxlength="10" />
	    <div class="errormsg"><span class="text-danger cust-error" id="contact-no-req">This field is required.</span></div>
	 
	 </div>
   <div class="col-md-4">
	  
	    <label for="label">Email ID</label>
	    <form:input path="custEmail" maxlength="100" id="custEmail" value="${customerDetailsObj.custEmail }" />
		<div class="errormsg"><span class="text-danger cust-error" id="cust-email-format">Email should be in correct format.</span></div>			
	 
  </div>
</div>
	 <div class="row">
	    <div class="col-md-4">
	    <label for="label">Pin Code<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	    <form:input path="pinCode" id="pinCode" value="${customerDetailsObj.pinCode }" maxlength="6" required="true"/>
		<span class="text-danger cust-error" id="empty-message"></span>
		<span class="text-danger cust-error" id="cust-zip-req">Pin Code is required and should be 6 digits.</span>
	    </div>
	    <div class="col-md-4">
	    <label for="label">City<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	    <form:input path="custCity" readonly="true" id="custCity" value="${customerDetailsObj.custCity }" required="true" /> 
	    </div>
	    <div class="col-md-4">
	    <label for="label">State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	    <form:input path="custState" readonly="true" id="custState" value="${customerDetailsObj.custState }" required="true" />  
		<span class="text-danger cust-error" id="custState-err"></span>
	    </div>
	   </div>
	     <div class="row">
	     <div class="col-md-4">
	     <label for="label">Country<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	     <form:input path="custCountry" readonly="true" value="${customerDetailsObj.custCountry }" required="true"/>
	     </div>
	     <div class="col-md-4" id="divAddr">
	      <label for="label" class="label-adrr">Address</label>
	      <input type="hidden" id="custAddress" value="${customerDetailsObj.custAddress1 }">  
		  <form:textarea path="custAddress1" id="custAddress1" maxlength="350" class="form-control"/>
		  <span class="text-danger cust-error" id="address1-req">This field is required </span>
	     </div>
	     </div>
	  </div>
	           <form:hidden path="id" value="${customerDetailsObj.id }"/>
			   <form:hidden path="status" value="${customerDetailsObj.status }"/>
	      	   <form:hidden path="createdOn" value="${customerDetailsObj.createdOn }"/>
	      	   <form:hidden path="createdBy" value="${customerDetailsObj.createdBy }"/>
	      	   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
	      	   
	    <div class="col-md-12 button-wrap"> 	
	    	<button type="button" class="btn btn-success blue-but" id="custDeleteBtn" formnovalidate="formnovalidate" style="width: auto;" onclick="javascript:deleteRecord('${customerDetailsObj.id}');">Delete</button>          	
			<button type="submit" class="btn btn-success blue-but" id="custSubmitBtn" formnovalidate="formnovalidate" style="width: auto;">Update</button>
		</div>
		
	   </form:form>
	</div>
</section>

 <form name="editCustomerDetails" method="post">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>
 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addCustomerDetails/editcustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addCustomerDetails/manageCustomerDetails.js"/>"></script>
 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addCustomerDetails/pincode-autocomplete-customer.js"/>"></script>