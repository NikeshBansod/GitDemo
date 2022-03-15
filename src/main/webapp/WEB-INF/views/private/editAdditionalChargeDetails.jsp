<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addadditionalChargeDetails/manageAdditionalChargeDetails.js"/>"></script>
 
 <header class="insidehead">
      <a href="<spring:url value="/additionalChargeDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--  <span>Edit Additional Charge Details<span> --></a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit Additional Charge Details</div>
			   
          <div class="acc_content">
          <form:form commandName="additionalChargeDetails"  method="post" action="./updateAdditionalChargeDetails" >
   					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <!--content-->
            
            <div class="box-content">
              <div class="">
              <span><center><h4><b>Edit Additional Charge Details</b></h4></center></span>
              <ul>
                  <li>
                  <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="chargeName" id="chargeName" maxlength="100" value="${addChargeDetailsObj.chargeName }" required="true" class="form-control" />
						<div class="label-text label-text2">Charge Name</div>
					</label>
					<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
					</div>
				  </li>
				   <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="chargeValue" id="chargeValue" maxlength="18" value="${addChargeDetailsObj.chargeValue }" required="true" class="form-control"/>
							<div class="label-text label-text2">Value</div>
						</label>
						<span class="text-danger cust-error" id="contact-no-req">This field is required</span>
					</div>
				  </li>
				    
				    
			   <form:hidden path="id" value="${addChargeDetailsObj.id }"/>
			   <input type="hidden" id="editPage" value="true" />
			   <form:hidden path="status" value="${addChargeDetailsObj.status }"/>
	      	   <form:hidden path="createdOn" value="${addChargeDetailsObj.createdOn }"/>
	      	   <form:hidden path="createdBy" value="${addChargeDetailsObj.createdBy }"/>
	      	   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
	      	   
                   </ul>
              </div>
            </div>
           
            <!--content end-->
	      	  
			 <div class="com-but-wrap">
            	<button type="submit" id="chargesSubmitBtn" class="btn btn-primary">Update</button>
                <a href="#" onclick="javascript:deleteRecord('${addChargeDetailsObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
            	
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

<form name="editAdditionalChargeDetails" method="post">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>

