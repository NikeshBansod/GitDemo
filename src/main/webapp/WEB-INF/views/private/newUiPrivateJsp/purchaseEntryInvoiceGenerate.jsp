<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 


<section class="block">
	<div class="loader"></div>
	<div class="container" id="addInvoiceDiv">         
		<%-- <div class="brd-wrap">
			 <div id="generateInvoiceDefaultPageHeader">
	            <a href="<spring:url value="/getMyPurchaseEntryPage"/>"><strong>Purchase Entry History</strong></a> &raquo; <strong>Generate Purchase Entry</strong>
	 		 </div>
	     </div> --%>
	      <div class="page-title">
                        <a href="<spring:url value="getMyPurchaseEntryPage"/>" class="back"><i class="fa fa-chevron-left"></i></a>Generate Purchase Entry
                    </div>
		 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		 <input type="hidden" id="selectedDocType" value="purchaseEntryInvoiceAndBillOfSupply"/>
         <div class="form-wrap">
             <div class="row">
                	
	  		 
	  		
	  		<div class="col-md-4" id="gstnStateIdDiv" style="display:none">
	  		     <label for="">GSTIN <span>*</span></label>
	  		     <select name="gstnStateId" id="gstnStateId">
	             </select>
	             <span class="text-danger cust-error" id="gstnStateId-csv-id">This field is required.</span>
	  		</div>
	  
	        <div class="col-md-4"  id="locationDiv" style="display:none">
	             <label for="">Location <span>*</span></label>
	             <select name="location" id="location">
	             </select>
	             <input type="hidden" name="locationStore" id="locationStore">
	             <input type="hidden" name="locationStorePkId" id="locationStorePkId">
	             <span class="text-danger cust-error" id="location-csv-id">This field is required.</span>
	        </div>
	        
	        <div class="col-md-4" id="invoice_date_datePicker">
                <label for="">Document Date <span>*</span></label>
                <input id="invoice_date" readonly="readonly" required>                
            </div>
	        
	        
	    </div>
	    <!-- End of row 1 -->
	    <!-- Start of row 2 -->
	    <div class="row">
	    	
            
            <div class="col-md-4 diff">
                 <label class="form-section-title-pad15">Document for</label>
                 <div class="radio radio-success radio-inline">
                     <input type="radio" class="styled" name="invoiceFor" value="Product" id="radio1" checked="checked" />
                     <label for="radio1">Goods</label>
                 </div>
                 <div class="radio radio-success radio-inline">
			         <input type="radio" class="styled" name="invoiceFor" value="Service" id="radio2"/>
                     <label for="radio2">Services</label>
                 </div>                                
             </div>
             
             <div class="col-md-4" style="display:none">
                  <label for="" class="dis-none">&nbsp;&nbsp;</label>
                  <div class="checkbox checkbox-success checkbox-inline form-mt">
                      <input class="styled" type="checkbox" name="reverseCharge" id="reverseCharge"/>
                      <label for="reverseCharge" id="reverseCharge-label" >Reverse charge applicable</label>
                      <input type="text" id="reverseChargeYesNo" style="display:none">
                  </div>                                          
              </div> 
              
              <div class="col-md-4" style="display:none">
                 <label for="" class="dis-none">&nbsp;&nbsp;</label>
                 <div class="checkbox checkbox-success checkbox-inline form-mt">
                     <input class="styled" type="checkbox" name="ecommerce" id="ecommerce"/>
                     <label for="ecommerce" id="ecommerce-label"></label>
                 </div>                                          
             </div> 
             
             <div class="col-md-4" style="display:none" id="ecommerceGstinDiv" >
                <label for="ecommerceGstin">GSTIN of Ecommerce operator <span>*</span></label>
                <input type="text" name="ecommerceGstin" id="ecommerceGstin" maxlength="15" required>
                <span class="text-danger cust-error" id="ecommerce-gstin-csv-id"></span>
             </div> 
            
	    </div>
	    <!-- End of row 2 -->

	    <!-- Start of row 4 -->
	   <div class="row" id="<!-- invoicePeriodDivShowHide -->" style="display:none">	 
			 <label for="" style="padding-left: 15px;">Document Period</label>	 
             <div class="col-md-12">   	 
                 <div class="col-md-5">
					 <input type="text" id="invoicePeriodFromDateInString" readonly="readonly"/>
				 </div>	
				 
				 <div class="col-md-2" style="text-align: center;">
					 <span class="">TO</span>
				 </div>
				 
                 <div class="col-md-5">
					 <input type="text" id="invoicePeriodToDateInString" readonly="readonly"/>
                 </div>
		 	</div>
		 </div> 
	    <!-- End of row 4 -->
	    <!-- Start of row 5 -->
	    <div class="row">
	    	<div class="col-md-4">
				<label for="">Supplier Name / Mobile No <span>*</span></label>
				<input type="text" name="customerDetails.custName" id="customer_name" required> 
				<span class="text-danger cust-error" id="empty-message">
		              No results found. <a href="javascript:void(0)" id="dnyAddNewCustomer">Click here to add a new customer.</a>
		        </span>
		        <span class="text-danger cust-error" id="customer-name-csv-id">This field is required.</span>
		        <span class="text-danger cust-error" id="customer-name-rchg-csv-id">Please select a GST-registered customer.</span>
			</div>
			 <!-- hidden fields for customer -->
             <input type="hidden" name="customerDetails.contactNo" id="customer_contactNo" maxlength="10"> 
             <input type="hidden" name="customerDetails.id" id="customer_id">
             <input type="hidden" name="customerDetails.custAddress1" id="customer_custAddress1">
             <input type="hidden" name="customerDetails.custAddress2" id="customer_custAddress2"> 
             <input type="hidden" name="customerDetails.custCity" id="customer_custCity">
             <input type="hidden" name="customerDetails.custState" id="customer_custState">
             <input type="hidden" name="customerDetails.custStateCode" id="customer_custStateCode">
             <input type="hidden" name="customerDetails.custStateCodeId" id="customer_custStateCodeId">
             <input type="hidden" name="customerDetails.custType" id="customer_custType">
             <input type="hidden" name="customerDetails.custStateId" id="customer_custStateId">
             <input type="hidden" name="customerDetails.custGstId" id="customer_custGstId">
             <input type="hidden" name="customerDetails.custEmail" id="customer_custEmail"> 
             
            <!--  Supplier details -->
              <input type="hidden" name="customerDetails.contactNo" id="supplier_contactNo" maxlength="10"> 
             <input type="hidden" name="customerDetails.id" id="customer_id"><!-- mandatory -->
             <input type="hidden" name="customerDetails.custAddress1" id="supplier_address"><!-- optional -->
             <input type="hidden" name="customerDetails.custCity" id="supplier_city"><!-- optional -->
             <input type="hidden" name="customerDetails.custState" id="supplier_state"><!-- optional -->
             <input type="hidden" name="customerDetails.custStateCode" id="supplier_stateCode">
             <input type="hidden" name="customerDetails.custStateCodeId" id="supplier_stateCodeId">
             <input type="hidden" name="customerDetails.pinCode" id="supplier_pincode">
             <input type="hidden" name="customerDetails.custGstId" id="supplier_gstin">
             <input type="hidden" name="customerDetails.custName" id="supplier_name">
               <input type="hidden" name="customerDetails.custType" id="supplier_dealertye">
               <input type="hidden" name="shipaddress" id="shipping_address">
               <input type="hidden" name="customerDetails.custAddress1" id="billing_address">
                <input type="hidden" name="poDetails.poNo" id="poDetails_poNo" />
          
	        <div class="col-md-4" style="display:none">
				<label for="">State Of Supply <span>*</span></label>
				<input type="text" name="serviceCountry" id="customer_country" required> 
			</div> 
			
			<div class="col-md-4" style="display:none">
				<label for="">Billing Address <span>*</span></label>
				<input type="text" name="servicePlace" id="customer_place" required>  
			</div> 
			
			<div class="col-md-4">
                <label for="" class="dis-none">&nbsp;&nbsp;</label>
                <div class="checkbox checkbox-success checkbox-inline form-mt">
                    <input name="shipToBill" id="shipToBill" class="styled" type="checkbox" checked="checked"/>
                    <label for="shipToBill">Make Shipping Address same as Billing Address</label>
                     
                </div>                                 
            </div>             
	                       
	    </div>
	    <!-- End of row 5 -->
	    <!-- Start of row 6 & 7 -->
	    <div id="consignee" style="display: none" >
	    	<div class="row" >
	    		<div class="col-md-4" id="selectCountryShowHide">
					<label for="">Country</label>
					<select class="form-control" name="deliveryCountry" id="selectCountry">
		              <option value="">Select Country</option>
		              <option value="India">India</option>
		              <option value="Other">Other</option>
		           </select>
				</div>
				
				<div class="col-md-3">
					<label for="">Ship To Customer Name <span>*</span></label>
					<input type="text" name="shipTo_customer_name" id="shipTo_customer_name" maxlength="50" required>       
				</div>
				
				<div class="col-md-3">
					<label for="">Ship To Address <span>*</span></label>
					<input type="text" name="shipTo_address" id="shipTo_address" maxlength="500" required>          
				</div>
				
				<div class="col-md-3" style="display:none" id="shipTo-pincode-show-hide">
					<label for="">Ship To PinCode <span>*</span></label>
					<input type="text" name="shipTo_pincode" id="shipTo_pincode" required maxlength="6" autocomplete="off">              
				</div>
				
				<div class="col-md-3" style="display:none" id="shipTo-city-show-hide">
					<label for="">Ship To City <span>*</span></label>
	                <input type="text" name="shipTo_city" id="shipTo_city" readonly="readonly" required>  
					
				</div>
				
				<input type="hidden" name="shipTo_stateCode" id="shipTo_stateCode">
                <input type="hidden" name="shipTo_stateCodeId" id="shipTo_stateCodeId">
                
	    	</div>
	    	
	       <!--  <div class="row">
	        	
            </div> -->
            
         </div>   
	 
	    <!-- End of row 6 & 7-->
	    <!-- Start of row 8 -->
	      <div class="row">
                
                <div class="col-md-4" id="selectState-show-hide">
					<label for="">Place Of Supply <span>*</span></label>
					<select name="deliveryPlace" id="selectState">
                    </select>
				</div>
				
				 <div class="col-md-4" id="pos-show-hide">
					<label for="">Place Of Supply</label>
					<input type="text" id="pos" disabled="disabled"/>
					<input type="hidden" id="posStateName" />
					<input type="hidden" id="posStateCode" />
				</div> 
				<input type="hidden" name="placeOfSupply_Name" id="placeOfSupply_Name"> 
		 			 <input type="hidden" name="placeOfSupply_Code" id="placeOfSupply_Code">
                     <input type="hidden" name="placeOfSupply_Id" id="placeOfSupply_Id">
				
				
				 <div class="col-md-4" >
					<label for="">Invoice number</label>
					 <input id="invoice_number" type="text" id="pos" />
					 <div class="errormsg"><span class="text-danger cust-error" id="invoice_number-csv-id">This field is required.</span></div>
					<input type="hidden" id="posStateName" />
					<input type="hidden" id="posStateCode" /> 
				</div>
				
				<!-- <div class="col-md-4" style="display:none" id="typeOfExport">
					<label for="">Type Of Export</label>
					<select name="typeOfExport" id="exportType">
		              <option value="">Type Of Export</option>
		              <option value="WITH_IGST">With IGST</option>
		              <option value="WITH_BOND">With Bond</option>
		           </select>
				</div> -->
				
				<!-- <div class="col-md-4">
					<label for="">PO/WO RF No</label>
					<input type="text" name="poDetails.poNo" id="poDetails_poNo">
				</div> -->
				
				<div class="col-md-4" style="display:none">
					<label for="">Bill Method <span>*</span></label>
					<select id="calculation_on" name="">
		               	<option value="">Select Type</option>
		               	<option value="Amount">Quantity Based</option>
		               	<option value="Lumpsum">Lumpsum</option>
		            </select>
				</div>
	        
	        
	    	</div>  
            <input type="hidden"  name="sgstTotalTax" id="sgstTotalTax"> 
            <input type="hidden"  name="cgstTotalTax" id="cgstTotalTax"> 
            <input type="hidden"  name="ugstTotalTax" id="ugstTotalTax"> 
            <input type="hidden"  name="igstTotalTax" id="igstTotalTax"> 
            <input type="hidden"  name="cessTotalTax" id="cessTotalTax"> 
            <input type="hidden"  name="totalAmount" id="totalAmount"> 
       		<input type="hidden" name="purchaser_org_name" id="purchaser_org_name" value="${userDetails.organizationMaster.orgName }">
			<input type="hidden" name="purchaser_org_address1" id="purchaser_org_address1" value="${userDetails.organizationMaster.address1 }">
			<input type="hidden" name="purchaser_org_city" id="purchaser_org_city" value="${userDetails.organizationMaster.city }">
			<input type="hidden" name="purchaser_org_pinCode" id="purchaser_org_pinCode" value="${userDetails.organizationMaster.pinCode }">
			<input type="hidden" name="purchaser_org_state" id="purchaser_org_state" value="${userDetails.organizationMaster.state }"> 
			<input type="hidden" name="purchaser_org_stateCode" id="purchaser_org_stateCode" value="${userDetails.organizationMaster.stateCode }"> 
         </div>
         <div class="form-accrod">
	         <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	         	<div class="panel panel-default">
	                <div class="panel-heading" role="tab" id="headingOne">
	                	<h4 class="panel-title">
	                    	<a id="callOnEditId" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse1">
	                      		Add Goods
	                    	</a>
	                	</h4>
	                </div>
	                <div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading1">
	                	<div class="panel-body">
	                	<div id="show-select-gstin-location-msg" style="display:none;color:red">Please select GSTIN & Location before Adding Items</div>
	                		<div class="form-wrap">
	                            <div class="row">
	                                <div class="col-md-4">
	                                    <label id="service_name_label" for=""></label>
                                        <input type="text" id="search-product-autocomplete" placeholder="Search Product" maxlength="15"/>
                                        <select id="service_name" name="service_name" style="display:none">
                                        </select>
                                        <span class="text-danger cust-error" id="search-ser-prod-autocomplete-csv-id">This field is required.</span>
				                        <span class="text-danger cust-error" id="dny-product-no-records-found">
				                           No results found. <a href="javascript:void(0)" id="dnyAddNewProduct">Click here to add a new product.</a>
				                        </span>
	                                </div>
	                                <div id="uom-show-hide">
	                                  	<div class="col-md-4" id="rate-show-hide">
											<label for="rate">Rate</label>
	                                        <input id="rate" type="text">
										</div>
										<div class="col-md-4" id="unitOfMeasurement-show-hide">
											<label for="">Unit Of Measurement</label>
											<input id="uomtoShow" disabled="disabled" type="text"> 
	                                        <input id="uom" type="hidden" name="">
										</div>
	                                </div>
	                           </div>
	                           <div class="row">
	                                <div class="col-md-4" id="quantity-show-hide">
										<div class="form-group">
		                                    <label for="">Quantity <span>*</span><span style="color:black" id="qtyLabel"></span></label>
		                                    <input id="quantity" type="text" maxlength="12"/>
											<div class="errormsg"><span class="text-danger cust-error" id="quantity-csv-id">This field is required.</span></div>
										</div>
	                                </div>             
	                                <div class="col-md-4" id="amount-show-hide">
										<div class="form-group">
		                                    <label for="">Amount</label>
										    <input id="amountToShow" type="text" disabled="disabled" maxlength="18"/>  
										    <input id="amount" type="hidden"/>  
										    <input type="hidden" name="hsnSacCode" id="hsnSacCode"/>
										    <div class="errormsg"><span></span></div>
										</div>
	                                </div>
	                                 
	                         
	                                 <div class="col-md-4" id="discountdiv">
										<div class="form-group">
		                                    <label for="">Discount</label>
										    <input id="offerAmount" type="text" maxlength="18"/>  							    		
											<div class="errormsg"><span class="text-danger cust-error" id="offerAmount-csv-id"></span></div>
										</div>
	                                </div>  
	                           </div>
	                           <div class="row">
	                                <div id="cessdiv">
										<div class="col-md-4">
											<label for="">Cess Advol(%)</label>
											<input id="cess" type="hidden" maxlength="18">
											<select id="cessAdvolId" name=""></select>
											<span class="text-danger cust-error" id="cess-csv-id"></span>
										</div>
										
										<div class="col-md-4">
											<label for="">Cess Non Advol. Amt</label>
											<input id="cessNonAdvolId" name=""></select>
										</div>
									
	                                </div> 
									<input type="hidden" id="unqValId" name="" value="">                                                 
	                            </div>
                                <div class="row">
						            <div class="col-md-12 button-wrap">
						                <button id="service_add" type="button"class="btn btn-success blue-but">Add</button>
							            <button id="service_edit" style="display:none" type="button"class="btn btn-success blue-but">Save</button>
										<button id="service_cancel" onclick="cancel_service_row()" type="button"class="btn btn-success blue-but">Cancel</button>
						            </div>
						        </div>						        						        
                                <div class="row">
						            <div class="cust-wrap">
						            	<div id="toggle"></div>
						       		</div>
						        </div>	
	                        </div>
	                	</div>
	                </div>
	         	</div>
	         	<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingTwo">
						<h4 class="panel-title">
							<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="false" aria-controls="collapse2">
							  Additional Charges
							</a>
						</h4>
					</div>
                     <div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading2" aria-expanded="false" style="height: 0px;">
                         <div class="panel-body">
                             <div class="form-wrap">
                                 <div class="row">                                     
	                                 <div class="col-md-6">
	                                    <label for="">Name</label>
	                                    <select name="additionalChargeName" id="additionalChargeName">
	                                    </select>
	                                </div>
	                                <div class="col-md-6">
	                                    <label for="">Amount</label>									    
					                 	<input type="text" id="additionalChargeAmountToShow" required class="form-control">  
					                 	<input type="hidden" name="additionalChargeAmount" id="additionalChargeAmount" value="">  
	                                </div>   
					       			 <input type="hidden" id="unqAddChgValId" name="" value="">                                              
                                 </div>
                                 <div class="row">
						            <div class="col-md-12 button-wrap">
						                <button id="add_chg_add" type="button"class="btn btn-success blue-but">Add</button>
							            <button id="add_chg_edit" style="display:none" type="button"class="btn btn-success blue-but">Edit</button>
										<button id="add_chg_cancel" onclick="cancel_add_chg_row()" type="button"class="btn btn-success blue-but">Cancel</button>
						            </div>
						        </div>						        
                                <div class="row">
						            <div class="cust-wrap">
						            	<div id="add_chg_toggle"></div>
						       		</div>
						        </div>
                             </div>
                         </div>
                     </div>
                 </div>
	         	<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingThree">
                       <h4 class="panel-title">
                         <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse3" aria-expanded="false" aria-controls="collapse3">
                           Footer Note
                         </a>
                       </h4>
                     </div>
                     <div id="collapse3" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading3" aria-expanded="false" style="height: 0px;">
                         <div class="panel-body">
                             <div class="form-wrap">
                                 <div class="row">                                     
	                                 <div class="col-md-12">
	                                    <label for=""></label>
									    <input type="text" id="footerNote" value="${loginUser.footer}" required class="form-control" maxlength="150" placeholder="Footer Note &#40; max 150 characters &#41;">
	                                </div>                                                 
                                 </div>
                             </div>
                         </div>
                     </div>
                </div>
                
                 <input type="hidden"  name="sgstTotalTax" id="sgstTotalTax"> 
                 <input type="hidden"  name="cgstTotalTax" id="cgstTotalTax"> 
                 <input type="hidden"  name="ugstTotalTax" id="ugstTotalTax"> 
                 <input type="hidden"  name="igstTotalTax" id="igstTotalTax"> 
                 <input type="hidden"  name="cessTotalTax" id="cessTotalTax"> 
                 <input type="hidden"  name="totalAmount" id="totalAmount"> 
                 <input type="hidden" name="purchaser_org_name" id="purchaser_org_name" value="${userDetails.organizationMaster.orgName }">
				 <input type="hidden" name="purchaser_org_address1" id="purchaser_org_address1" value="${userDetails.organizationMaster.address1 }">
				 <input type="hidden" name="purchaser_org_city" id="purchaser_org_city" value="${userDetails.organizationMaster.city }">
				 <input type="hidden" name="purchaser_org_pinCode" id="purchaser_org_pinCode" value="${userDetails.organizationMaster.pinCode }">
				 <input type="hidden" name="purchaser_org_state" id="purchaser_org_state" value="${userDetails.organizationMaster.state }"> 
				 <input type="hidden" name="purchaser_org_stateCode" id="purchaser_org_stateCode" value="${userDetails.organizationMaster.stateCode }">   
	         </div>
	     </div>	
     	<div class="row">
            <div class="col-md-12 button-wrap">
                <button type="button" id="submitId" class="btn btn-success blue-but">Preview Final Invoice</button>
            </div>
        </div>
     </div>
     
     <!-- Add Dynamic Service/Product block starts here -->
	<div class="container" id="dnyServiceProductDiv" style="display:none"> 
		<div id="dnyServiceFormDiv">
			
	    </div> <!-- /dnyServiceFormDiv -->
	    <div id="dnyProductFormDiv">
	    	<div class="brd-wrap">
				<div id="">
		           <a href="javascript:void(0)"><strong>Document History</strong></a> &raquo; <strong>Add Product</strong>
		 		</div>
			</div>
			<div class="form-wrap">
	        <!-- Start of row 1 -->
	        	<div class="row">
	        		<div class="col-md-4">
						<label for="">Search By HSN Code / HSN Description <span>*</span></label>
						<input type="text" id="dny-search-hsn" maxlength="15"/>
						<input type="hidden" id="hsnCodePkId"/>
					    <span class="text-danger cust-error" id="dny-search-hsn-req">Please Search By HSN Code / HSN Description </span>
					</div>
					
					<div class="col-md-4">
						<label for="">HSN Description <span>*</span></label>
						<input type="text" disabled="disabled" id="dny-hsnDescriptionToShow" required="true" />
						<input type="hidden" id="dny-hsnDescription" />
						<span class="text-danger cust-error" id="dny-prod-hsn-desc">This field is required.</span>
					</div>
					
					<div class="col-md-4">
						<label for="">HSN Code <span>*</span></label>
						<input type="text" disabled="disabled" id="dny-hsnCodeToShow" required="true"/>
						<input type="hidden" id="dny-hsnCode" />
						<span class="text-danger cust-error" id="dny-prod-hsn-code">This field is required.</span>
					</div>
	        
	            </div>	        
	        
			<!-- End of row 1 -->
	        <!-- Start of row 2 -->
	        	<div class="row">
	        		<div class="col-md-3">
						<label for="">Goods Name <span>*</span></label>
						<input type="text" id="dny-product-name" maxlength="100" required="true"/>
						<span class="text-danger cust-error" id="dny-prod-name">This field is required.</span>
					</div>
					
					<div class="col-md-3">
						<label for="">Rate of tax (%) <span>*</span></label>
						<select id="dny-productIgst" name="dny-productIgst"></select>
						<input type="hidden" id="dny-productIgst-check" name="dny-productIgst-check"></select>
						<span class="text-danger cust-error" id="dny-prod-igst">This field is required and should be numeric.</span> 
					</div>
					
				   <div class="col-md-3" style="display:none">
                       <label for="">Cess Advol Rate (%) </label>
                       <select id="dny-product-advolCess" name="dny-product-advolCess" ></select>
	   				</div>
	
					<div class="col-md-3" style="display:none">
	                   	<label for=""> Cess Non Advol Rate</label>
	                    <select id="dny-product-nonAdvolCess" name="dny-product-nonAdvolCess" ></select>
	                </div> 
	        
	            </div>	        
	        
	        <!-- End of row 2 -->
	        
	        <!-- Start of row 2.1 -->
	        	<div class="row">
	        		<div class="col-md-4">
						<label for="">Selling Price<span>*</span></label>
						<input type="text" id="dny-productRate" maxlength="18" required="true"/>
						<span class="text-danger cust-error" id="dny-prod-rate">This field is required and should be numeric.</span>
					</div>
					
	        		<div class="col-md-4">
						<label for="">Unit Of Measurement <span>*</span></label>
						<select name="dny-product-unitOfMeasurement" id="dny-product-unitOfMeasurement"></select>
					</div>
					
					<div class="col-md-4" id="dny-product-divOtherUnitOfMeasurement">
						<label for="">Please Specify <span>*</span></label>
						<input type="text"  id="dny-product-otherUOM" maxlength="30"/>
						<input type="hidden" name="dny-product-tempUom" id="dny-product-tempUom">
						<span class="text-danger cust-error" id="dny-product-otherOrgType-req">This field is required.</span>
					</div>
	        	
	        	</div>
	        
	        
	        
	        <!-- End of row 2.1 -->
	        
	        <!-- Start of row 3 -->
	        	<div class="row">
	        		<div class="col-md-4">
						<label for="label"> Purchase Price<!-- Goods Rate (Rate per unit of measurement) --><span> *</span></label>
						<input type="text"  id="dny-product-buyingRate" maxlength="7" required="true"  /><!-- path="purchaseRate" -->
						<span class="text-danger cust-error" id="dny-prod-buy-rate-req">This field is required and should be numeric.</span>
					</div> 
					
					<div class="col-md-4">
						<label for="label"> Secondary Unit Of Measurement<span> </span></label>
					  	<input type="text"  name="dny-product-purchaseUOM" id="dny-product-purchaseUOM" disabled="disabled"></select>
					    <span class="text-danger cust-error" id="dny-purchase-uom-req">This field is required.</span>
					</div>
					
					 <div class="col-md-4" id="dny-product-divPurchaseOtherUOM">
	                 	<label for="label">Please Specify<span> *</span></label>
						<input type="text"  id="dny-product-purchaseOtherUOM" maxlength="30"  />
						<input type="hidden" name="dny-product-tempPurchaseOtherUOM" id="dny-product-tempPurchaseOtherUOM">
					 	<span class="text-danger cust-error" id="dny-product-otherPurchaseUom-req">This field is required.</span>
		          	</div>
	        
	            </div>	        
	        
	        <!-- End of row 3 -->
	        
	        <!-- Start of row 3.1 -->
	         <div class="form-accrod">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                  <div class="panel panel-default" style="border : 0.5px solid lightgrey !important">
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
					            <table id="dny-product-gstin-openingstock" class="display nowrap" style="width:100%">
						            <thead>
						                <tr>
						                	<th style="width:10px">Sr No.</th>
						                	<th>Gstin</th>
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
              </div>
	        
	        <!-- End of row 3.1 -->
	        
	        <!-- Start of row 4 -->
	        	<div class="row">
	        		<div class="col-md-12 button-wrap">
	  					<button id="dnyProductSubmitBtn" class="btn btn-success blue-but" style="width:auto;">Save</button>
	      				<button id="dnyProductCancelBtn" class="btn btn-success blue-but" style="width:auto;">Cancel</button>
	  				</div>
	        
	            </div>	        
	        
	        <!-- End of row 4 -->
			</div><!-- /form-wrap -->
	
	    </div><!-- /dnyProductFormDiv -->
	</div>
    <!-- Add Dynamic Service/Product block ends here -->
     
     <div class="invoice-wrap" id="previewInvoiceDiv">
         <div class="container">
             <div class="row">
                 <div class="col-xs-12">
                 	<div class="text-center">
                        <h2 class="section-title">Purchase Entry Preview</h2>
                    </div>
                    <hr>
                    <div class="row">
	                    <div class="col-xs-12 col-md-4 col-lg-4 pull-left">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Billing Details</div>
	                            <div class="panel-body" id="userDetailsInPreview">
	                                
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDiv"> <!-- customerDetailsInPreview -->
	                               
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Ship To</div>
	                            <div class="panel-body" id="customerDetailsShipToDiv">
	                                
	                            </div>
	                        </div>
	                    </div>
	                </div>
                 </div>
             </div>
             <div class="row">
             	<div class="col-md-12">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="text-center">Purchase Document</h3>
	                    </div>
	                    <div class="table-responsive">
	                    	<table class="table table-condensed" id="mytable2">
	                            <thead>
	                                <tr>
	                                    <td><strong>Description</strong></td>
	                                    <td><strong>SAC/HSN</strong></td>
	                                    <td><strong>Quantity</strong></td>
	                                    <td><strong>UOM</strong></td>
	                                    <td><strong>Price/UOM(Rs)</strong></td>
	                                    <td><strong>Disc(Rs.)</strong></td>
	                                    <td><strong>Total (Rs.) After Disc</strong></td>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            
	                            </tbody>
	                    	</table>
	                    </div>
	                    <div class="form-con">
		                    <!-- <div class="tax-invoice" id="diffPercentShowHide" style="margin:0 10px;">
								<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
					        </div>
					        <hr> -->
	                        <div class="signature-wrap" id="preview_customer_purchase_order">
	                           
	                        </div>
	                        <div class="signature">
	                            <div class="col-md-6">
	                                <h1 class="signature-normal">Receiver Name :</h1>
	                                <h1 class="signature-normal"><strong>Receiver Signature :</strong></h1>
	                            </div>
	                            <div class="col-md-6">
	                                <h1 class="signature-normal">Supplier Name :</h1>
	                                <h1 class="signature-normal"><strong>Authorized Signature :</strong></h1>
	                            </div>                                            
	                        </div>
	                        <div class="declear">
	                        	<div style="text-align:center;" id="footerNoteDiv"></div>
	                        	<p id="userAddressWithDetails"></p>	
	                            <h1 class="black-bold">Declaration</h1>
	                            <ul>
	                                <li>
	                                    Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional considerations directly or indirectly from the Buyer
	                                </li>
	                                <li>
	                                    The normal terms governing the above sale are printed overleaf E&amp;OE
	                                </li>                                            
	                            </ul>
	                        </div>
                        </div>
                    </div>
                </div>
             </div>  
	     	<div class="row">
	            <div class="col-md-12 button-wrap">
	                <button type="button" id="finalSubmitId" class="btn btn-success blue-but">Save & Close</button>
	                <button type="button" id="backToaddInvoiceDiv" class="btn btn-success blue-but">Edit</button>
	            </div>
	        </div>                 
         </div>
    </div>
 </section>
 
  <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateInvoice/invoice.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/purchaseEntryinvoice.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/pincode-autocomplete-purchaseentry.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/service-product-add.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/purchase_service_crud.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/add_charge_crud.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/form_submit.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/dnynamic-add-products.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/otherFunction.js"/>"></script> 

  