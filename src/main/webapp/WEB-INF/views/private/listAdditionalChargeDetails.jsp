<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/addadditionalChargeDetails/manageAdditionalChargeDetails.js"/>"></script>
 <header class="insidehead">
      <a href="<spring:url value="/home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--  <span>Manage Additional Charges<span> --></a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

 <div class="container" >
  <div class="box-border" >
  <form:form commandName="additionalChargeDetails" method="post" id="addChargeForm" action="./addAdditionalCharges">
   	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition hide hidden">
      <!--01-->
      <div  class="accordion_in acc_active">
        <div class="acc_head">Add Additional Charges</div>
          <div class="acc_content">
            <!--content-->
            <div class="box-content">
              <div class="comm-input">
                <ul>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Name of Additional Charge</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Value</div>
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
              <div class="">
              <span><center><h4><b>Manage Additional Charges</b></h4></center></span>
                <ul>
                 <li>
                 <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="chargeName" id="chargeName" maxlength="100" required="true" class="form-control"/>
						<div class="label-text label-text2">Name of Additional Charge</div>						
					</label>
					<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
					</div>
				  </li>
				  <li>
				    <div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="chargeValue" id="chargeValue" maxlength="18" required="true" class="form-control"/>
							<div class="label-text label-text2">Value</div>
						</label>
						<span class="text-danger cust-error" id="contact-no-req">This field is required</span>
					</div>
				  </li>
				    <input type="hidden" id="editPage" value="false" />
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="chargesSubmitBtn" formnovalidate="formnovalidate">Save</button>
            <button id="cancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button></div>
      </div>
       </form:form>
      <!--additional Charge details-->
      <div class="additionalChargesValuesTable">	
		<div class="card">
		<h4 style="text-align: center;">Additional Charges Master</h4>
		<table class="table table-striped table-bordered additionalChargesValues" id="additionalChargesValuesTab" >
				<thead>
							<tr>
								<th><center> Name</center></th>
								<th ><center>Value</center></th>
								
								
							</tr>
						</thead>
			</table>							
		</div>
		</div>
     
  </div>
</div>
 
<div class="fixed-action-btn" >
	<a class="btn btn-floating btn-large" title="Add Additional Charges" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<!--common end-->

<form name="editAdditionalChargeDetails" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
   	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
