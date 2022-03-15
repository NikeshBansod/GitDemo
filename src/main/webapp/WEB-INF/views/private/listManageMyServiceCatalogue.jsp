<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>




<header class="insidehead">
     <a href="<spring:url value="home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--   <span>Manage Services<span> --></a>
     <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
</header>


<!--comon-->
<div class="container">
  <div class="box-border">
    <form:form commandName="manageServiceCatalogue" id="servForm" method="post" action="./addManageServiceCatalogue">
    	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition hide hidden">
      
      <div class="accordion_in acc_active">
        <div class="acc_head">Add Service</div>
          <div class="acc_content">
            <div class="box-content">
              <div class="comm-input">
            
                <ul>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">SAC Description</div>
					</label>
					
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">SAC Code</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Service Name</div>
					</label>
				  </li>
				  <li>	
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Unit Of Measurement</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Service Rate</div>
					</label>
				  </li>
                </ul>
              </div>
            </div>
            
            <div class="com-but-wrap"><button type="submit" class="blue-but">Save</button><button class="blue-but white-but">Cancel</button></div>
          </div>
        </div>
      </div>

      <div class="addCustomer">
      	<div class="box-content">
              <div class="">
                <span><center><h4><b>Manage Services</b></h4></center></span>
                <ul>
                 <li>
	                <div class="form-group input-field mandatory">
	                  <label class="label">
	                  	<input type="text" id="search-sac" maxlength="15" class="form-control"/>                  	
	                  	<div class="label-text label-text2">Search By SAC Code / SAC Description</div>
	                  </label>
	                  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By SAC Code / SAC Description</span>
	                </div>	
                  </li>
                  <li>
                  	<div class="form-group input-field mandatory">
						<label class="label"> 
					    	<input type="text" disabled="disabled"  readonly="readonly" id="sacDescriptionToShow" required class="form-control"/>
							<form:hidden path="sacDescription" id="sacDescription" />
							<div class="label-text label-text2">SAC Description</div>
						</label>
						<span class="text-danger cust-error" id="ser-sac-desc">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
					 		<input type="text" disabled="disabled"  readonly="readonly" id="sacCodeToShow" required class="form-control"/>
							<form:hidden path="sacCode" id="sacCode" />
							<div class="label-text label-text2">SAC Code</div>
						</label>
						<span class="text-danger cust-error" id="ser-sac-code">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="name" required="true" maxlength="100" class="form-control" />
							<div class="label-text label-text2">Service Name</div>
						</label>
						<span class="text-danger cust-error" id="ser-name">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<select name="unitOfMeasurement" class="form-control" id="unitOfMeasurement" class="form-control" >
	                        </select>
							<div class="label-text label-text2">Unit Of Measurement</div>
						</label>
						<!--<span class="text-danger cust-error" id="ser-uom">This field is required.</span>-->
					</div>
				  </li>
				  <div class="form-group input-field mandatory" id="divOtherUnitOfMeasurement">
		                  		<label class="label">
		                  			<form:input path="otherUOM" maxlength="30" id="otherUOM" class="form-control"/>
		                  			<div class="label-text label-text2">Please Specify </div>
		                  		</label>
		                  		<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
		          </div>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="serviceRate" maxlength="18" required="true" class="form-control"/>
							<div class="label-text label-text2">Service Rate (Rs.)</div>
						</label>
						<span class="text-danger cust-error" id="ser-rate">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">
						  <select name="serviceIgst" class="form-control" id="serviceIgst" class="form-control" >
	                        </select>
							<div class="label-text label-text2">Rate of tax (%)</div>
						</label>
						<span class="text-danger cust-error" id="service-igst">This field is required.</span>
					</div>
				  </li>
				  <form:hidden path="sacCodePkId" id="sacCodePkId"/>
				  <input type="hidden" id="editPage" value="false" />
                </ul>
              </div>
            </div>
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary"  id="srvSubmitBtn">Save</button>
            	<button id="cancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button>
            </div>
      </div>
       <input type="hidden" name="tempUom" id="tempUom">
       </form:form>
      
      <div class="serviceValuesTable">	
		<div class="card">
		<h4 style="text-align: center;">Service Catalogue</h4>
		<table class="table table-striped table-bordered serviceValues" id="serviceValuesTab" >
				<thead>
							<tr>
								<th><center>Service Name</center></th>
								<th><center>Rate of Tax(%)</center></th>
							</tr>
						</thead>
			</table>							
		</div>
		</div>
  </div>
</div>

<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add Servie" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<!--common end-->

<form name="manageService" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/sacCodeAjax.js"/>"></script>

 <script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/addServiceCatalogue.js"/>"></script>
 <script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/manageServiceCatalogue.js"/>"></script>