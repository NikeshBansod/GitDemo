<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addadditionalChargeDetails/manageAdditionalChargeDetails.js"/>"></script>
 
<%--  <header class="insidehead">
      <a href="<spring:url value="/additionalChargeDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--  <span>Edit Additional Charge Details<span> --></a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header> --%>
 
 
 <div class="loader"></div>
 <section class="block">
<div class="container">
	<div class="brd-wrap">
	        <a href="<spring:url value="/additionalChargeDetails" />"><strong>Additional Charge </strong></a> &raquo; <strong>Edit Additional Charge </strong>
	   </div>
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit Additional Charge Details</div>
			   
          <div class="acc_content">
          <form:form commandName="additionalChargeDetails"  method="post" action="./updateAdditionalChargeDetails" >
   					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
   			
            <!--content-->
            
            <div class="form-wrap">
              <div class="row">
                  <div class="col-md-6">
					<label for="label">Name<span style="font-weight: bold;color: #ff0000;"> *</span></label>  
					<form:input path="chargeName" id="chargeNameValue" maxlength="100" value="${addChargeDetailsObj.chargeName }" required="true" class="form-control" />
					<span class="text-danger cust-error" id="charge-name-req">This field is required.</span>
					</div>
				  
				   
				  	<div class="col-md-6">
						<label for="label">Value<span style="font-weight: bold;color: #ff0000;"> *</span></label>  
							<form:input path="chargeValue" id="chargeValuedetail" maxlength="18" value="${addChargeDetailsObj.chargeValue }" required="true" class="form-control"/>
						    <span class="text-danger cust-error" id="charge-value-req">This field is required</span>
					</div>
				  
				    
				    
			   <form:hidden path="id" value="${addChargeDetailsObj.id }"/>
			   <input type="hidden" id="editPage" value="true" />
			   <form:hidden path="status" value="${addChargeDetailsObj.status }"/>
	      	   <form:hidden path="createdOn" value="${addChargeDetailsObj.createdOn }"/>
	      	   <form:hidden path="createdBy" value="${addChargeDetailsObj.createdBy }"/>
	      	   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
	      	   
              </div>
            </div>
           
            <!--content end-->
	      	  
			 <div class="col-md-12 button-wrap">
            	<button type="submit" id="chargesSubmitBtn" class="btn btn-success blue-but" style="width: auto;">Update</button>
               <%--  <a href="#" onclick="javascript:deleteRecord('${addChargeDetailsObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a> --%>
            	<button id="srvSubmitBtn" type="button" onclick="javascript:deleteRecord('${addChargeDetailsObj.id}');" style="width: auto;" class="btn btn-success blue-but" value="Delete">Delete</button>
            </div>
            </form:form>
		</div>
			  
			</div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap edit-Page-List">
		
		<div class="dnynamicAdditionalChargeDetails" id="toggle">								
		</div>
	</div>
</div>
</section>

<form name="editAdditionalChargeDetails" method="post">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>

