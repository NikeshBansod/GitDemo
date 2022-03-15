<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<div class="loader"></div>
<section class="block">
	<div class="container">
		 
	  <%--  <div class="brd-wrap">
	    	<div id="listheader">
	       		<strong><a href="<spring:url value="/home#master_management" />">Master Management</a> </strong>&raquo; <strong>Goods Catalogue</strong>
	        </div>
	        <div id="addheader">
	        	<strong><a href="<spring:url value="/getProducts"/>">Goods Catalogue</a></strong> &raquo; <strong> Add Goods  </strong>
	        </div>
	   </div> --%>
	    <div class="page-title" id="listheader">
                 <a href="<spring:url value="/home#master_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>GOODS MASTER
        </div>
         <div class="page-title" id="addheader">
                <a href="<spring:url value="/getProducts"/>" id="gobacktolisttable" class="back"/><i class="fa fa-chevron-left"></i></a>Add Goods
             </div>
	   <div class="form-wrap" id="addProductdiv">
		    <div class="row"  >
	            <div class="col-md-12 button-wrap">			
					<button id="addProductButton" style="width: auto;" type="button" class="btn btn-success blue-but">Add</button>
				</div>
			</div>
	   </div>
	    
  	   <div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="ProductValuesTab" class="display nowrap" style="width:100%">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Selling Price</th>
                            <th>UOM</th>
                            <th>HSN Code</th>
                            <th>Tax Rate(%)</th>
                            <th>Purchase Price</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                       
                    </tbody>
	            </table>
	        </div>
	    </div>		    
	    
	    <div class="form-wrap"  id="addProductDetails">	    
			<form method="post" id="prdForm" >
   			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
					    	
			<div class="row">	
				<div class="col-md-4">
					<label for="label">Search By HSN Code / HSN Description</label>
				    <input type="text" id="search-hsn" maxlength="15"   />    
					<span class="text-danger cust-error" id="reg-gstin-req">Please Search By HSN Code / HSN Description.</span>	
				</div>
				
				<div class="col-md-4">
					<label for="label">HSN Description<span> *</span></label>
					<input type="text" disabled="disabled" id="hsnDescriptionToShow" required="true"  />
					<input type="hidden" id="hsnDescription" required="true" />
					<span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
				</div> 		
				<div class="col-md-4">
					<label for="label">HSN Code<span> *</span></label>
					<input type="text" disabled="disabled" id="hsnCodeToShow" required="true"  />
					<input type="hidden" id="hsnCode" required="true" />
					<span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
				</div> 						
			</div>	
				
				
			<div class="row">	
				<div class="col-md-4">
					<label for="label">Goods Name<span> *</span></label>
				  	<input type="text" id="name" maxlength="100" required="true"  />
				  	<span class="text-danger cust-error" id="prod-name">This field is required.</span>
				</div>			
	          	
	          	<div class="col-md-4">
					<label for="label">Rate of tax (%)<span> *</span></label>
				    <select id="productIgst" name="productIgst" ></select> 
					<span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>				
				</div>
					
			  </div>							
			 <div class="row">	
				<div class="col-md-4">
					<label for="label"> Selling Price<!-- Goods Rate (Rate per unit of measurement) --><span> *</span></label>
					<input type="text" id="productSellingRate" maxlength="7" required="true"  /><!-- path="productRate"  -->
					<span class="text-danger cust-error" id="prod-rate">This field is required and should be numeric.</span>
				</div> 
				<div class="col-md-4">
					<label for="label">Unit Of Measurement<span> *</span></label>
				  	<select name="unitOfMeasurement" id="unitOfMeasurement" ></select>
				    <span class="text-danger cust-error" id="prod-uom">This field is required.</span>
				</div>
				
				<div class="col-md-4" id="divOtherUnitOfMeasurement">
                 	<label for="label">Please Specify<span> *</span></label>
					<input type="text" id="otherUOM" maxlength="30"  />
				 	<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
	          	</div>										
			 </div> 	
				
			<div class="row">	
				
				<div class="col-md-4">
					<label for="label"> Purchase Price<!-- Goods Rate (Rate per unit of measurement) --><span> *</span></label>
					<input type="text"  id="productBuyingRate" maxlength="7" required="true"  /><!-- path="purchaseRate" -->
					<span class="text-danger cust-error" id="prod-buy-rate">This field is required and should be numeric.</span>
				</div> 
				
				<div class="col-md-4">
					<label for="label"> Secondary Unit Of Measurement<span> </span></label>
					<input type="text"  id="purchaseUOM" disabled="disabled" maxlength="7"  /><!-- path="purchaseRate" -->
				    <span class="text-danger cust-error" id="purchase-uom">This field is required.</span>
				</div>
				
				 <!-- <div class="col-md-4" id="divPurchaseOtherUOM">
                 	<label for="label">Please Specify<span> *</span></label>
					<input type="text"  id="purchaseOtherUOM" maxlength="30"  />
				 	<span class="text-danger cust-error" id="otherPurchaseOrgType-req">This field is required.</span>
	          	</div> --> 										
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
								
							
                      </div>
                    </div> 
                  </div>
                 
                                                                                                 
                </div>
             </div> <!-- /end of .form-accrod -->
                    
				<input type="hidden" id="hsnCodePkId"/>
				<input type="hidden" id="editPage" value="false" />
				  
				<div class="row">	
		      		<div class="col-md-12 button-wrap">
		      			<button class="btn btn-success blue-but" id="prodSubmitBtn" style="width: auto;" >Save</button>
	                   </div>
	            </div>  				
				
			   
				
	  		</form> 
	    </div><!-- end of /.form-wrap -->
	     
	     
	</div>
</section>

<form name="manageProduct" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="resources/js/newUiJs/products/manageProduct.js"></script>
<script type="text/javascript" src="resources/js/newUiJs/products/addProducts.js"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/products/hsnCodeAjax.js"/>"></script>
<script type="text/javascript">
function doFunction()
{
	
    var message = "calling amazon";
    var lengthLong = "kumarlength ";          
    var retunoutput = "..";
    window.app.AndroidMethodQRScan(message);


   // alert("hi anish");
    return false;


}


function qrScan(result) {
alert("result:: "+result);
    var dd = result;
    $("#ScanResult").append(dd);
}


</script>