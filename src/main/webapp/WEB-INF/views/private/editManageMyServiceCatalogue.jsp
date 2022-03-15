<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/manageServiceCatalogue.js"/>"></script>


 <header class="insidehead">
      <a href="<spring:url value="/manageServiceCatalogue" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--  <span>Edit Service<span>  --></a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit Service</div>
       
          <div class="acc_content">
           <form:form commandName="manageServiceCatalogue" method="post" action="./updateManageServiceCatalogue">
           	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <!--content-->
            
            <div class="box-content">
              <div class="">
              <span><center><h4><b>Edit Service</b></h4></center></span>
                <ul>
                 <li>
	                 <div class="form-group input-field">
	                  <label class="label">
	                  	<input type="text" id="search-sac" maxlength="15" class="form-control" />                  	
	                  	<div class="label-text label-text2">Search By SAC Code / SAC Description</div>
	                  </label>
	                  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By SAC Code / SAC Description</span>
	                  </div>	
                  </li>
                  <li>
                  <div class="form-group input-field mandatory">
					<label class="label"> 
						<input type="text" value="${manageServiceCatalogueObj.sacDescription }" readonly="readonly" id="sacDescriptionToShow" class="form-control" /> 
						<form:hidden path="sacDescription" value="${manageServiceCatalogueObj.sacDescription }" id="sacDescription" />
						<div class="label-text label-text2">SAC Description</div>
					</label>
					<span class="text-danger cust-error" id="ser-sac-desc">This field is required.</span>
					</div>
				  </li>
				  <li>
				  <div class="form-group input-field mandatory">
					<label class="label">  
						<input type="text" disabled="disabled" value="${manageServiceCatalogueObj.sacCode }" readonly="readonly" id="sacCodeToShow" class="form-control" />
						<form:hidden path="sacCode" id="sacCode" value="${manageServiceCatalogueObj.sacCode }"/> 
						<div class="label-text label-text2">SAC Code</div>
					</label>
					<span class="text-danger cust-error" id="ser-sac-code">This field is required.</span>
					</div>
				  </li>
				  <li>
				  <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="name" maxlength="100" id="name" value="${manageServiceCatalogueObj.name }" required="true" class="form-control" /> 
						<div class="label-text label-text2">Service Name</div>
					</label>
					<span class="text-danger cust-error" id="ser-name">This field is required.</span>
					</div>
				  </li>
				  <li>
				  <div class="form-group input-field mandatory">
					<label class="label">  
						<form:select path="unitOfMeasurement" id="unitOfMeasurement" required="true" class="form-control"/> 
						<input type="hidden" id="unitOfMeasurementHidden" value="${manageServiceCatalogueObj.unitOfMeasurement }" />
						<div class="label-text label-text2">Unit Of Measurement</div>
					</label>
					<span class="text-danger cust-error" id="ser-uom">This field is required.</span>
					</div>
				  </li>
				   <li>
                   	<div class="form-group input-field mandatory" id="divOtherUnitOfMeasurement">
                 		 <label class="label">
                  		  	<form:input path="otherUOM" maxlength="30" value="${manageServiceCatalogueObj.otherUOM}" class="form-control"/>
                  			<div class="label-text label-text2">Specified Unit Of Measurement</div>
                  		</label>
                 		<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
                  	</div>
                  </li>
				  <li>
				  <div class="form-group input-field mandatory">
					<label class="label">  
						<form:input path="serviceRate" id="serviceRate" maxlength="18" value="${manageServiceCatalogueObj.serviceRate }" required="true" class="form-control" />
						<div class="label-text label-text2">Servcie Rate (Rs.)</div>
					</label>
					<span class="text-danger cust-error" id="ser-rate">This field is required.</span>
					</div>
				  </li>
				   <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
						<form:select path="serviceIgst" id="serviceIgst" required="true" class="form-control"/>
							<div class="label-text">Rate of tax (%)</div>
						</label>
						<span class="text-danger cust-error" id="service-igst">This field is required.</span>
					</div>
				  </li>
			   <form:hidden path="id" value="${manageServiceCatalogueObj.id }"/>
			   <form:hidden path="referenceId" value="${manageServiceCatalogueObj.referenceId }"/>
			   <form:hidden path="status" value="${manageServiceCatalogueObj.status }"/>
			   <form:hidden path="createdBy" value="${manageServiceCatalogueObj.createdBy }"/>
			   <form:hidden path="createdOn" value="${manageServiceCatalogueObj.createdOn }"/>
			   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			   <input type="hidden" id="editPage" value="true" />
			   <input type="hidden" id="serviceRateOfTax" value="${manageServiceCatalogueObj.serviceIgst}"/>
			    <form:hidden path="sacCodePkId" id="sacCodePkId" value="${manageServiceCatalogueObj.sacCodePkId }"/>
			     <input type="hidden" name="tempUom" id="tempUom">
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" id="srvSubmitBtn" class="btn btn-primary">Update</button>
            	<a href="#" onclick="javascript:deleteRecord('${manageServiceCatalogueObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
            </div>
            </div>
            </form:form>
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap edit-Page-List">
		
		<div class="dnynamicServices" id="toggle">								
		</div>
		
   </div>
</div>
  
<form name="manageService" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/sacCodeAjax.js"/>"></script>
