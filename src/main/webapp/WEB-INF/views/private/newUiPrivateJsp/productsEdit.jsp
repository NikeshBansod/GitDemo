<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



 <div class="loader"></div>
<section class="block">
	<div class="container">
		 
	    <div class="brd-wrap">
	    	<div  id="listheader">
	       	<strong>	<a href="<spring:url value="/getProducts" />">Goods Catalogue</a></strong> &raquo; <strong>Edit Goods  </strong>
	        </div>
	        
	    </div>



 <div class="form-wrap"  >	    
			 <form:form commandName="product" method="post" action="./updateProduct">
              	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />    	
				<div class="row">	
					<!-- <div class="col-md-4">
						<label for="label">Search By HSN Code / HSN Description</label>
						<input type="text" id="search-hsn" maxlength="15" class="form-control"/>                  	
	                  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By HSN Code / HSN Description</span>		
					</div> -->
					
					<div class="col-md-4">
						<label for="label">HSN Description<span> *</span></label>
					 	<input type="text" disabled="disabled" value="${productObj.hsnDescription }" id="hsnDescriptionToShow" required class="form-control"/>
						<form:hidden path="hsnDescription" value="${productObj.hsnDescription }" id="hsnDescription" />
						<span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
					</div>
					
					<div class="col-md-4">
						<label for="label">HSN Code<span> *</span></label>
						<input type="text" disabled="disabled" value="${productObj.hsnCode }" id="hsnCodeToShow" required class="form-control"/>
						<form:hidden path="hsnCode" value="${productObj.hsnCode }" id="hsnCode" /> 
						<span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
					</div>
				</div>
					
					
				<div class="row">	
					<div class="col-md-4">
						<label for="label">Goods Name<span> *</span></label>
						<form:input path="name" id="name" maxlength="100" value="${productObj.name }" required="true" class="form-control"/>
						<span class="text-danger cust-error" id="prod-name">This field is required.</span>
					</div>		
					
					<div class="col-md-4">
						<label for="label">Rate of tax (%)<span> *</span></label>
						<select class="form-control" id="productIgst" name="productIgst" class="form-control" style=" height: 39px;">
						</select>  
						<span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
					</div>
				</div>	
				
				
				<div class="row">	
					<div class="col-md-4">
						<label for="label">Selling Price <span> *</span></label>
						<form:input path="productRate" id="productSellingRate" maxlength="18" value="${productObj.productRate }" required="true" class="form-control"/>
						<span class="text-danger cust-error" id="prod-rate">This field is required and should be numeric.</span>
					</div> 
					
					<div class="col-md-4">
						<label for="label">Unit Of Measurement <span> *</span></label>
						<form:select path="unitOfMeasurement" value="${productObj.unitOfMeasurement }" id="unitOfMeasurement" required="true" class="form-control" style=" height: 39px;"/> 
						<input type="hidden" id="unitOfMeasurementHidden" value="${productObj.unitOfMeasurement }" />
						<span class="text-danger cust-error" id="prod-uom">This field is required.</span>
					</div>	
					
					<div class="col-md-4" id="divOtherUnitOfMeasurement">
						<label for="label">Please Specify <span> *</span></label>
						<form:input path="otherUOM" maxlength="30" value="${productObj.otherUOM}" class="form-control"/>
						<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
					</div>
				</div>
				
				<div class="row">	
					<div class="col-md-4">
						<label for="label">Purchase Price <span> *</span></label>
						<form:input path="purchaseRate" id="productBuyingRate" maxlength="18" value="${productObj.purchaseRate }" required="true" class="form-control"/>
						<span class="text-danger cust-error" id="prod-buy-rate">This field is required and should be numeric.</span>
					</div> 
					
					<div class="col-md-4">
						<label for="label"> Secondary Unit Of Measurement <span> *</span></label>
						<form:input path="purchaseUOM" id="purchaseUOM" value="${productObj.unitOfMeasurement }" readonly="true" required="true" class="form-control" style=" height: 39px;"/> 
						<input type="hidden" id="purchaseUOM" value="${productObj.unitOfMeasurement }" />
						<span class="text-danger cust-error" id="purchase-uom">This field is required.</span>
					</div>	
					
					<div class="col-md-4" id="divPurchaseOtherUOM">
						<label for="label">Please Specify <span> *</span></label>
						<form:input path="purchaseOtherUOM" id="purchaseOtherUOM" maxlength="30" value="${productObj.otherUOM}" readonly="true" class="form-control"/>
						<span class="text-danger cust-error" id="otherPurchaseOrgType-req">This field is required.</span>
					</div>
				</div>
				
				
				
				
				
				<div class="form-accrod">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" style="border 1px solid grey">
                  <div class="panel panel-default">
			   			<div class="panel-heading" role="tab" id="headingOne">
	                      <h4 class="panel-title">
	                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse1">
	                         Opening Stock
	                        </a>
	                      </h4>
	                    </div>		
				
						<div id="collapse1" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="heading1">
			               <div class="panel-body">
			                      <!-- R&D Opening Data table Start -->
         					
				     		<div class="" id="listTable">
					           <div class="table-wrap">
					            <table id="dny-gstin-openingstock-product" class="display nowrap" style="width:100%">
						            <thead>
						                <tr>
						                	<th style="width:10px">Sr No.</th>
						                	<th>GSTIN</th>
						                    <th>Location/Store</th>
						                    <th>Opening Stock Quantity</th>
						                    <th>Opening Stock Value</th>
						                </tr>
						            </thead>
						            <tbody>
						            </tbody>
					             </table>
					           </div>
					        </div>
				     					
				     		<!-- End -->  
				     		<div class="col-md-12 col-sm-12 col-xs-12"><h5 style="color:red"> Note : You can change opening stock from the Inventory Tab, Inventory ----> <a href="./getopeningstock">Opening Stock.</a> </h5></div>           
			                </div>               
						   </div>
			   
			   		</div>
			  	 </div>
			   </div>
			   
				<form:hidden path="id" id="prod" value="${productObj.id }"/>
					    <form:hidden path="referenceId" value="${productObj.referenceId }"/>
					    <form:hidden path="status" value="${productObj.status }"/>
					    <form:hidden path="createdBy" value="${productObj.createdBy }"/>
					    <form:hidden path="createdOn" value="${productObj.createdOn }"/>
					    <form:hidden path="hsnCodePkId" value="${productObj.hsnCodePkId }"/>
					    <form:hidden path="inventoryUpdateFlag" value="${productObj.inventoryUpdateFlag }"/>
					    <form:hidden path="existingName" id="existingname" value="${productObj.name }"/>
					    <input type="hidden" id="rateOfTax" value="${productObj.productIgst}"/>
					  <%--   <input type="hidden" id="cessAdvolTaxRateId" value="${productObj.advolCess}"/>
					    <input type="hidden" id="cessNonAdvolTaxRateId" value="${productObj.nonAdvolCess}"/> --%>
					    <input type="hidden" id="editPage" value="true" />
					    <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					   <%--  <form:hidden path="hsnCodePkId" id="hsnCodePkId" value="${manageServiceCatalogueObj.hsnCodePkId }"/> --%>
					    <input type="hidden" name="tempUom" id="tempUom">
				 <div class="row">
			        <div class="col-md-12 button-wrap">
			            <button id="prodSubmitBtn" type="submit"  style="width: auto;" class="btn btn-success blue-but" value="Update">Update</button> 
			           	<button id="prodDeleteBtn" type="button"  style="width: auto;" class="btn btn-success blue-but" value="Delete">Delete</button>
			                    
			        </div>
			    </div>

</form:form>
</div>


</div>
</section>


<form name="manageProduct" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="resources/js/newUiJs/products/manageProduct.js"></script>
<script type="text/javascript" src="resources/js/newUiJs/products/addProducts.js"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/products/hsnCodeAjax.js"/>"></script>