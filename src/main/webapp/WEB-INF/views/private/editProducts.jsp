<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/products/manageProduct.js"/>"></script>

 <header class="insidehead">
      <a href="<spring:url value="/getProducts" />" class="btn-back"><i class="fa fa-angle-left"></i> <!-- <span>Edit Goods<span> --> </a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit Goods</div>
       
          <div class="acc_content">
           <form:form commandName="product" method="post" action="./updateProduct">
           	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <!--content-->
            
            <div class="box-content">
              <div class="">
              <span><center><h4><b>Edit Goods</b></h4></center></span>
                <ul>
                  <li>
	                 <div class="form-group input-field">
	                  <label class="label">
	                  	<input type="text" id="search-hsn" maxlength="15" class="form-control"/>                  	
	                  	<div class="label-text label-text2">Search By HSN Code / HSN Description</div>
	                  </label>
	                  <span class="text-danger cust-error" id="reg-gstin-req">Please Search By HSN Code / HSN Description</span>
	                  </div>	
                  </li>
                  <li>
                  	<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" disabled="disabled" value="${productObj.hsnDescription }" id="hsnDescriptionToShow" required class="form-control"/>
							<form:hidden path="hsnDescription" value="${productObj.hsnDescription }" id="hsnDescription" />
							<div class="label-text label-text2">HSN Description</div>
						</label>
						<span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" disabled="disabled" value="${productObj.hsnCode }" id="hsnCodeToShow" required class="form-control"/>
							<form:hidden path="hsnCode" value="${productObj.hsnCode }" id="hsnCode" /> 
							<div class="label-text label-text2">HSN Code</div>
						</label>
						<span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="name" id="name" maxlength="100" value="${productObj.name }" required="true" class="form-control"/> 
							<div class="label-text label-text2">Goods Name</div>
						</label>
						<span class="text-danger cust-error" id="prod-name">This field is required.</span>
					</div>
				  </li>
				    <li>
				  <div class="form-group input-field mandatory">
					<label class="label">  
						<form:select path="unitOfMeasurement" id="unitOfMeasurement" required="true" class="form-control"/> 
						<input type="hidden" id="unitOfMeasurementHidden" value="${productObj.unitOfMeasurement }" />
						<div class="label-text label-text2">Unit Of Measurement</div>
					</label>
					<span class="text-danger cust-error" id="prod-uom">This field is required.</span>
					</div>
				  </li>
				  
				    <li>
                   	<div class="form-group input-field mandatory" id="divOtherUnitOfMeasurement">
                 		 <label class="label">
                  		  	<form:input path="otherUOM" maxlength="30" value="${productObj.otherUOM}" class="form-control"/>
                  			<div class="label-text label-text2">Specified UnitOfMeasurement</div>
                  		</label>
                 		<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
                  	</div>
                  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="productRate" id="productRate" maxlength="18" value="${productObj.productRate }" required="true" class="form-control"/>
							<div class="label-text label-text2">Goods Rate (Rate per unit of measurement)</div>
						</label>
						<span class="text-danger cust-error" id="prod-rate">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">
						<select class="form-control" id="productIgst" name="productIgst" class="form-control">
						</select>   
							<div class="label-text label-text2">Rate of tax (%)</div>
						</label>
						<span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
					</div>
				  </li>
			   <form:hidden path="id" value="${productObj.id }"/>
			   <form:hidden path="referenceId" value="${productObj.referenceId }"/>
			   <form:hidden path="status" value="${productObj.status }"/>
			   <form:hidden path="createdBy" value="${productObj.createdBy }"/>
			   <form:hidden path="createdOn" value="${productObj.createdOn }"/>
			   <input type="hidden" id="rateOfTax" value="${productObj.productIgst}"/>
			   <input type="hidden" id="editPage" value="true" />
			      <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			   <form:hidden path="hsnCodePkId" id="hsnCodePkId" value="${manageServiceCatalogueObj.hsnCodePkId }"/>
			    <input type="hidden" name="tempUom" id="tempUom">
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" id="prodSubmitBtn" class="btn btn-primary">Update</button>
            	<a href="#" onclick="javascript:deleteRecord('${productObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
            </div>
            </form:form>
            </div>
       
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap edit-Page-List">
		
		<div class="dnynamicProducts" id="toggle">								
		</div>
      
   </div>
</div>
  
<form name="manageProduct" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/products/hsnCodeAjax.js"/>"></script>
