<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
 
<section class="block">
    <div class="loader"></div>
	<div class="container" id="">
	  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  <input type="hidden" id="unqIncDes" name="unqIncDes" value="${refToFind}" />
	  <input type="hidden" id="backRRType" name="backRRType" value="${backRRType}" />
	  <input type="hidden" id="invNo" name="invNo" />
	  <input type="hidden" id="selectedDocType" name="selectedDocType" />
	  <input type="hidden" id="invDataFiledToGstn" name="invDataFiledToGstn" />
	 
	  <input type="hidden" id="firmName" value="${sessionScope.loginUser.firmName}" />
	  <input type="hidden" id="panNumber" value="${sessionScope.loginUser.panNo}" />
	  <input type="hidden" id="iterationNo" />
	  <input type="hidden" id="old_invoiceFor" />
	  <input type="hidden" id="old_gstnStateId" />
	  <input type="hidden" id="old_gstnStateIdInString" />
	  <input type="hidden" id="old_location" />
	  <input type="hidden" id="old_locationStoreId" />
	  <input type="hidden" id="old_invoiceDate" />
	</div>
	
	<div class="container" id="addInvoiceDiv">  
	  <div class="brd-wrap">
		 <div id="generateInvoiceDefaultPageHeader">
            
 		 </div>
	  </div>
	  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  <div class="form-wrap">
	  <!-- Start of row 1 -->
	  	<div class="row">
	  		<div class="col-md-4" style="display:block" id="documentType-div">
	  			<label for="">Document type <span>*</span></label>
	  			<input type="hidden" id="selectedDocType" value="${ documentType}"/>
	  			<input type="hidden" id="modeOfCreation" value="EDIT_INVOICE">
	  			<input type="hidden" id="invoiceSequenceType" value="${sessionScope.loginUser.invoiceSequenceType}">
	  			<select name="documentType" id="documentType">
                    <option value="">Select</option>
                    <option value="invoice">Invoice</option>
					<option value="billOfSupply">Bill of Supply</option>
					<option value="rcInvoice">Reverse Charge Invoice</option>
					<option value="eComInvoice">E-Commerce Invoice</option>
					<option value="eComBillOfSupply">E-Commerce Bill Of Supply</option>
					<!-- <option value="invoiceCumBillOfSupply">Invoice cum Bill of Supply</option> -->
          		</select>
	  		</div>
	  		
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
             
             <div class="col-md-4" style="display:none" id="invoiceNumberDiv" >
                <label for="invoiceNumber">Invoice Number <span>*</span></label>
                <input type="text" name="invoiceNumber" id="invoiceNumber" maxlength="20" required>
                <span class="text-danger cust-error" id="invoiceNumber-csv-id"></span>
                <span class="text-danger cust-error" id="invoiceNumber-duplicate-csv-id"></span>
             </div> 
            
	    </div>
	    <!-- End of row 2 -->

	    <!-- Start of row 4 -->
	    <div class="row" id="invoicePeriodDivShowHide" style="display:none">	 
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
				<label for="">Customer Name / Mobile No <span>*</span></label>
				<input type="text" name="customerDetails.custName" id="customer_name" required> 
				<span class="text-danger cust-error" id="empty-message">
		              No results found. <a href="javascript:void(0)" id="dnyAddNewCustomer">Click here to add a new customer.</a>
		        </span>
		        <span class="text-danger cust-error" id="customer-name-csv-id">This field is required.</span>
		        <span class="text-danger cust-error" id="customer-name-rchg-csv-id">Please select a GST-registered customer.</span>
			</div>
			 <!-- hidden fields for customer -->
             <input type="hidden" name="customerDetails.contactNo" id="customer_contactNo" maxlength="10"> 
             <input type="hidden" name="customerDetails.id" id="customer_id"><!-- mandatory -->
             <input type="hidden" name="customerDetails.custAddress1" id="customer_custAddress1"><!-- optional -->
           <!--   <input type="hidden" name="customerDetails.custAddress2" id="customer_custAddress2">  -->
             <input type="hidden" name="customerDetails.custCity" id="customer_custCity"><!-- optional -->
             <input type="hidden" name="customerDetails.custState" id="customer_custState"><!-- optional -->
             <input type="hidden" name="customerDetails.custStateCode" id="customer_custStateCode">
             <input type="hidden" name="customerDetails.custStateCodeId" id="customer_custStateCodeId">
             <input type="hidden" name="customerDetails.custType" id="customer_custType">
             <input type="hidden" name="customerDetails.custStateId" id="customer_custStateId">
             <input type="hidden" name="customerDetails.custGstId" id="customer_custGstId">
             <input type="hidden" name="customerDetails.custEmail" id="customer_custEmail">
          
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
		              <option value="India" selected="selected">India</option>
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
					<input type="text" name="shipTo_pincode" id="shipTo_pincode" required maxlength="6">              
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
				
				<div class="col-md-4" style="display:none" id="typeOfExport">
					<label for="">Type Of Export</label>
					<select name="typeOfExport" id="exportType">
		              <option value="">Type Of Export</option>
		              <option value="WITH_IGST">With IGST</option>
		              <option value="WITH_BOND">With Bond</option>
		           </select>
				</div>
				
				<div class="col-md-4">
					<label for="">PO/WO RF No</label>
					<input type="text" name="poDetails.poNo" id="poDetails_poNo">
				</div>
				
				<div class="col-md-4" style="display:none">
					<label for="">Bill Method <span>*</span></label>
					<select id="calculation_on" name="">
		               	<option value="">Select Type</option>
		               	<option value="Amount" selected="selected">Quantity Based</option>
		               	<option value="Lumpsum">Lumpsum</option>
		            </select>
				</div>
	        
	        
	    	</div> 
	    
	    <!-- End of row 8-->
        <div class="form-accrod">
	    	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	         
			    <!-- Start of row 9 -->
			    <!-- Service Section Starts --> 
         		<div class="panel panel-default">
		    		 <div class="panel-heading" role="tab" id="headingOne">
	                	<h4 class="panel-title">
	                    	<a id="callOnEditId" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse1">
	                      		Add Goods
	                    	</a>
	                	</h4>
	                </div>
	                <div id="collapse1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading1">
		    			<div class="panel-body">
		    				<div id="show-select-gstin-location-msg" style="display:none;color:red">Please select GSTIN & Location before Adding Items</div>
	                		<div class="form-wrap">
	                			<div class="row">
	                			
	                				<div class="col-md-4">
	                                    <label id="service_name_label" for=""></label>
	                                    <input type="text" id="search-service-autocomplete" placeholder="Search Service" maxlength="15"/>
                                        <input type="text" id="search-product-autocomplete" placeholder="Search Product" maxlength="15"/>
                                        <select id="service_name" name="service_name" style="display:none">
                                        </select>
                                        <span class="text-danger cust-error" id="search-ser-prod-autocomplete-csv-id">This field is required.</span>
                                        <span class="text-danger cust-error" id="dny-service-no-records-found">
				                           No results found. <a href="javascript:void(0)" id="dnyAddNewService">Click here to add a new service.</a>
				                       </span>
				                       <span class="text-danger cust-error" id="dny-product-no-records-found">
				                           No results found. <a href="javascript:void(0)" id="dnyAddNewProduct">Click here to add a new product.</a>
				                       </span>
	                                </div>
	                                
	                                <div id="uom-show-hide" style="display:none">
		                                <div class="col-md-4" id="rate-show-hide">
											<label for="rate">Rate</label>
	                                        <input id="rate" type="text" maxlength="8">
										</div>
										<div class="col-md-4" id="unitOfMeasurement-show-hide">
											<label for="">Unit Of Measurement</label>
											<input id="uomtoShow" disabled="disabled" type="text"> 
	                                        <input id="uom" type="hidden" name="">
										</div>
									</div>
	                			
	                			</div>
	                			<div class="row">
		                			<div class="col-md-3" style="display:none" id="quantity-show-hide">
											<label for="">Quantity <span>*</span><span style="color:black" id="qtyLabel"></span></label>
											<input id="quantity" type="text" maxlength="12">
	                                        <span class="text-danger cust-error" id="quantity-csv-id">This field is required.</span>
									</div>
	                				<div class="col-md-3" style="display:none" id="amount-show-hide">
										<label for="">Amount</label>
										<input id="amountToShow" type="text" maxlength="10">  
                                        <input id="amount" type="hidden">  
                                        <input type="hidden" name="hsnSacCode" id="hsnSacCode">
                                        <input type="hidden" name="openingStockProduct" id="openingStockProduct">
									</div>
									<div class="col-md-3 diff">
						                 <h1 class="form-section-title">Discount In</h1><br><br>
						                 <div class="radio radio-success radio-inline">
						                     <input type="radio" class="styled" name="itemDiscountType" value="Percentage" id="radio23" checked="checked" />
						                     <label for="radio23">Percentage</label>
						                 </div>
						                 <div class="radio radio-success radio-inline">
									         <input type="radio" class="styled" name="itemDiscountType" value="Value" id="radio13"/>
						                     <label for="radio13">Value</label>
						                 </div>                                
						             </div>
						             
						             <div class="col-md-3">
										<label for="">Discount % or Value</label>
										<input id="offerAmount" type="text" maxlength="15"> 
										<span class="text-danger cust-error" id="offerAmount-csv-id"></span>
									</div>
	                			</div>
	                			<div class="row">
	                				<div class="col-md-3 diff">
						                 <h1 class="form-section-title">Cess Applicable</h1><br><br>
						                 <div class="radio radio-success radio-inline">
						                     <input type="radio" class="styled" name="cess-applicable" value="No" id="radio83" checked="checked" />
						                     <label for="radio83">No</label>
						                 </div>
						                 <div class="radio radio-success radio-inline">
									         <input type="radio" class="styled" name="cess-applicable" value="Yes" id="radio93"/>
						                     <label for="radio93">Yes</label>
						                 </div>                                
						             </div>
		                			<div id="cess-applicable-show-hide" style="display:none">
		                				<div class="col-md-3">
											<label for="">Cess Advol(%)</label>
											<input id="cess" type="hidden" maxlength="18">
											<select id="cessAdvolId" type="" name=""></select>
											<span class="text-danger cust-error" id="cess-csv-id"></span>
										</div>
										
										<div class="col-md-3">
											<label for="">Cess Non Advol. Amt</label>
											<!-- <select id="cessNonAdvolId" type="" name=""></select> -->
											<input id="cessNonAdvolId" type="text" maxlength="6">
										</div>
		                			</div>
									
									<div class="col-md-3" id="diffPercentageDiv">
					                  <label for="" class="dis-none">&nbsp;&nbsp;</label>
					                  <div class="checkbox checkbox-success checkbox-inline form-mt">
					                      <input class="styled" type="checkbox" name="diffPercent" id="diffPercent"/>
					                      <label for="diffPercent" id="diffPercent-label" >Eligible to be taxed at a differential percentage of the existing tax-rate as notified by government.</label>
					                  </div>                                          
					               </div> 
	                		       <input type="hidden" id="unqValId" name="" value="">
	                		    </div>
	                		    <div class="row">
	                		    	 <div class="col-md-3" style="display:block">
						                 <label for="" class="dis-none">&nbsp;</label>
						                 <div class="checkbox checkbox-success checkbox-inline form-mt">
						                     <input class="styled" type="checkbox" name="description" id="description"/>
						                     <label for="description" id="description-label">Add Description</label>
						                 </div>                                          
						             </div> 
						             
						             <div class="col-md-3" style="display:none" id="descriptionDiv" >
						                <label for="descriptionTxt">Description</label>
						                <textarea rows="3" id="descriptionTxt" maxlength="50" required></textarea>
						              <!--   <span class="text-danger cust-error" id="descriptionTxt-csv-id"></span> -->
						             </div>
	                		    
	                		    </div>
	                		    <div class="row">
	                				<div class="col-md-12 button-wrap">
	                					  <button id="service_add" class="btn btn-success blue-but" style="width:auto;">Add</button>
							              <button id="service_edit" style="display:none;width:auto;" class="btn btn-success blue-but">Save</button>
										  <button id="service_cancel" onclick="cancel_service_row()" style="width:auto;" class="btn btn-success blue-but">Cancel</button>
	                				</div>
	                		    </div>
	                		    <div class="row">
	                				<div class="cust-wrap">
	                                    <div id="toggle">
	                                    </div>
	                                </div>
	                		
	                		
	                		
	                		
	                		    </div>
		    				</div>
		    			</div>
		    		</div>
		    	</div>
			    <!-- Service Section Ends --> 
			    <!-- End of row 9-->
			    
			    <!-- Start of row 10 -->
			    <!-- Additional charges Section Starts here-->
			    <div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingTwo">
						<h4 class="panel-title">
							<a class="collapsed" role="button" id="callOnEditAdditionalChargeId" data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="false" aria-controls="collapse2">
							  Additional Charges
							</a>
						</h4>
					</div>
		            <div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading2" aria-expanded="false" style="height: 0px;">
		                <div class="panel-body">
		                    <div class="form-wrap">
		                        <div class="row">                                     
			                         <div class="col-md-6">
			                            <label id="additional_charge_name_label" for="">Search Additional Charge</label>
	                                    <input type="text" id="search-additional-charge-autocomplete" placeholder="Search Additional Charge" maxlength="15"/>
                                        <select name="additionalChargeName" id="additionalChargeName" style="display:none">
			                            </select>
                                        <span class="text-danger cust-error" id="search-additional-charge-autocomplete-csv-id">This field is required.</span>
                                        <span class="text-danger cust-error" id="dny-additional-charge-no-records-found">
				                           No results found. <a href="javascript:void(0)" id="dnyAddNewAdditionalCharge">Click here to add a new Additional Charge.</a>
				                        </span>
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
							             <button id="add_chg_add" type="button" class="btn btn-success blue-but" style="width:auto;">Add</button>
							          	 <button id="add_chg_edit" style="display:none;width:auto;" type="button" class="btn btn-success blue-but">Edit</button>
								         <button id="add_chg_cancel" onclick="cancel_add_chg_row()" style="width:auto;" type="button" class="btn btn-success blue-but">Cancel</button>
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
		         <!-- Additional charges Section Ends here--> 
	     		 <!-- End of row 10-->
	     		 <!-- Start of row 11 -->
	     		 <!-- Footer Note Section starts here -->
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
                <!-- Footer Note Section ends here -->
	     		<!-- End of row 11-->
	     		
	    	</div><!-- end of /panel-group -->
	    </div><!-- end of /form-accrod -->
	    
	    <!-- Start of row 12-->
	     		<!-- Redundant Code Starts here -->
			    <div class="accordion_in" style="display:none">
                   <div class="acc_head">Discounts</div>
                      <div class="acc_content">
                      
                        <div class="radio-inline text-center">
	                        <span>Discount In</span>
	                        <div class="rdio rdio-success">
	                            <input type="radio" name="discountType" value="Percentage" id="radio3" checked="checked">
	                            <label for="radio3">Percentage</label>
	                        </div>
	                        <div class="rdio rdio-success">
	                            <input type="radio" name="discountType" value="Value" id="radio4">
	                            <label for="radio4">Value</label>
	                        </div>
	                    </div>
                        <div class="form-group input-field mandatory">
                            <label class="label">
                                <input type="text" name="discountValue" id="discountValue" required class="form-control">  
                                <div class="label-text label-text2">Discount % or Value</div>
                            </label>
                        </div>
                        <div class="form-group input-field">
                           <label class="label">
                               <input type="text" disabled = "disabled" id="discountAmountToShow" class="form-control">  
                               <input type="hidden" name="discountAmount" id="discountAmount" value="">  
                               <div class="label-text label-text2">Amount</div>
                           </label>
                       </div>
                       <div class="form-group input-field">
                          <label class="label">
                              <input type="text" name="discountRemarks" id="discountRemarks"  required class="form-control">  
                              <div class="label-text label-text2">Remarks</div>
                          </label>
                      </div>
                   </div>
                </div>
			    <div class="acc_content"  style="display:none">
			       <div class="radio-inline text-center">
					    <span>Additional Charges In</span>
					    <div class="rdio rdio-success">
					        <input type="radio" name="additionalChargesType" value="Percentage" id="radio5" checked="checked">
					        <label for="radio5">Percentage</label>
					    </div>
					    <div class="rdio rdio-success">
					        <input type="radio" name="additionalChargesType" value="Value" id="radio6">
					        <label for="radio6">Value</label>
					    </div>
					</div>
			        <div class="form-group input-field mandatory">
						<label class="label">
						    <input type="text" name="additionalChargesValue" id="additionalChargesValue" required class="form-control">  
						    <div class="label-text label-text2">Additional Charge % or Value</div>
						</label>
			        </div>
			        <div class="form-group input-field">
						<label class="label">
						      <input type="text" disabled = "disabled" id="additionalAmountToShow" required class="form-control">  
						      <input type="hidden" name="additonalAmount" id="additonalAmount" value="">  
						    <div class="label-text label-text2">Amount</div>
						</label>
			        </div>
			        <div class="form-group input-field">
						<label class="label">
						    <input type="text" name="additionalChargesRemark"  id="additionalChargesRemark" class="form-control">  
						    <div class="label-text label-text2">Remarks</div>
						</label>
			        </div>
   				</div>
   				<div class="card" style="display:none;margin-bottom:-5px" >
                    <div class="form-group input-field ">
                        <label class="label">
                            <input type="text" disabled="disabled" id="amountAfterDiscountToShow" class="form-control">  
                             <input type="hidden" name="amountAfterDiscount" id="amountAfterDiscount">  
                            <div class="label-text label-text2">Sub Total ( After Discount and Additional Charges )<span style="color:red">*</span></div>
                        </label>
                    </div>

                    <div class="form-group input-field ">
                        <label class="label">
                            <input type="text" disabled="disabled" id="totalTaxToShow" class="form-control"> 
                             <input type="hidden"  name="totalTax" id="totalTax">  
                            <div class="label-text label-text2">Total Tax</div>
                        </label>
                    </div>

                    <div class="form-group input-field"><!-- has-error -->
                        <label class="label">
                            <input type="text" disabled="disabled" id="invoiceValueToShow" class="form-control"> 
                            <input type="hidden"  name="invoiceValue" id="invoiceValue" value="">  
                            <div class="label-text label-text2">Invoice Value</div>
                        </label>
                        <!-- <span class="text-danger error">This field is required</span> -->
                    </div>
                </div>
                
			    <!-- Redundant Code Ends here -->
			    
			    <input type="hidden"  name="sgstTotalTax" id="sgstTotalTax"> 
                <input type="hidden"  name="cgstTotalTax" id="cgstTotalTax"> 
                <input type="hidden"  name="ugstTotalTax" id="ugstTotalTax"> 
                <input type="hidden"  name="igstTotalTax" id="igstTotalTax"> 
                <input type="hidden"  name="cessTotalTax" id="cessTotalTax"> 
                <input type="hidden"  name="totalAmount" id="totalAmount"> 
                
	            <input type="hidden" name="user_org_name" id="user_org_name" value="${userDetails.organizationMaster.orgName }">
				<input type="hidden" name="user_org_address1" id="user_org_address1" value="${userDetails.organizationMaster.address1 }">
				<input type="hidden" name="user_org_city" id="user_org_city" value="${userDetails.organizationMaster.city }">
				<input type="hidden" name="user_org_pinCode" id="user_org_pinCode" value="${userDetails.organizationMaster.pinCode }">
				<input type="hidden" name="user_org_panNumber" id="user_org_panNumber" value="${userDetails.organizationMaster.panNumber }">
	    <!-- End of row 12 -->
	    
	    <!-- Start 0f row 13 -->
	    <div class="row">
            <div class="col-md-12 button-wrap">
                <button type="button" id="submitId" style="width:auto;" class="btn btn-success blue-but">Preview Final Document</button>
                <button type="button" id="backToRRPage" onclick="backToRRPage()" class="btn btn-success blue-but" style="width:auto;">GO BACK</button>
            </div>
        </div>
	    <!-- end of row 13 -->
	    
	  </div>
	</div><!-- end of #addInvoiceDiv -->
	
	<!-- Add Dynamic Service/Product block starts here -->
	<div class="container" id="dnyServiceProductDiv" style="display:none"> 
		<div id="dnyServiceFormDiv">
			<div class="brd-wrap">
				<div id="">
		           <a href="javascript:void(0)" id="dny-service-cancel-link"><strong>Document History</strong></a> &raquo; <strong>Add Service</strong>
		 		</div>
			</div>
			<div class="form-wrap">
			<!-- Start of row 1 -->
			    <div class="row">
					<div class="col-md-4">
						<label for="">Search By SAC Code / SAC Description <span>*</span></label>
			            <input type="text" id="dny-search-sac" maxlength="15"/> 
			            <input type="hidden" id="sacCodePkId"/>
			            <span class="text-danger cust-error" id="dny-search-sac-req">Please Search By SAC Code / SAC Description</span>
					</div>
					
					<div class="col-md-4">
						<label for="">SAC Description <span>*</span></label>
						<input type="text" disabled="disabled"  readonly="readonly" id="dny-sacDescriptionToShow" required/>
					    <input type="hidden" id="dny-sacDescription" />
					    <span class="text-danger cust-error" id="dny-ser-sac-desc">This field is required.</span>
					</div>
					
					<div class="col-md-4">
						<label for="">SAC Code <span>*</span></label>
						<input type="text" disabled="disabled"  readonly="readonly" id="dny-sacCodeToShow" required/>
						<input type="hidden" id="dny-sacCode" />
						<span class="text-danger cust-error" id="dny-ser-sac-code">This field is required.</span>
					</div>
				</div>
			
			<!-- End of row 1 -->
	        <!-- Start of row 2 -->
	        	<div class="row">
	        		<div class="col-md-4">
						<label for="">Service Name <span>*</span></label>
						<input type="text" id="dny-service-name" required="true" maxlength="100" />
						<span class="text-danger cust-error" id="dny-ser-name">This field is required.</span>
					</div>
					
					<div class="col-md-4" style="display:none">
						<label for="">Unit Of Measurement <span>*</span></label>
						<select name="dny-unitOfMeasurement" id="dny-unitOfMeasurement" >
		                </select>
					</div>
					
					<div class="col-md-4" id="dny-divOtherUnitOfMeasurement" style="display:none">
						<label for="">Please Specify <span>*</span></label>
						<input type="text" maxlength="30" id="dny-otherUOM"/>
	                  	<input type="hidden" name="dny-tempUom" id="dny-tempUom">
	                  	<span class="text-danger cust-error" id="dny-otherOrgType-req">This field is required.</span>
					</div>
					
					<div class="col-md-4">
						<label for="">Charges <span>*</span></label>
						<input type="text" id="dny-serviceRate" maxlength="18" required="true" />
						<span class="text-danger cust-error" id="dny-ser-rate">This field is required.</span>
					</div>
					
					<div class="col-md-4">
						<label for="">Rate of tax (%) <span>*</span></label>
						<select name="dny-serviceIgst" id="dny-serviceIgst"></select>
						<input type="hidden" id="dny-serviceIgst-check" name="dny-serviceIgst-check"></select>
	                    <span class="text-danger cust-error" id="dny-service-igst">This field is required.</span>
					</div>
	        
	            </div>
	        <!-- End of row 2 -->
	        <!-- Start of row 3 -->
	        	<div class="row">
	        		
				
					
					<div class="col-md-4">
                       <label for="">Cess Advol Rate (%) </label>
                       <select id="dny-service-advolCess" name="dny-service-advolCess" ></select>
                      <!-- <span class="text-danger cust-error" id="prod-advolCess">This field is required and should be numeric.</span> -->
	   				</div>
	
					<div class="col-md-4">
	                   	<label for=""> Cess Non Advol Rate</label>
	                    <select id="dny-service-nonAdvolCess" name="dny-service-nonAdvolCess" ></select>
	                      <!-- <span class="text-danger cust-error" id="prod-nonAdvolCess">This field is required and should be numeric.</span> -->
	                </div>
	            </div>	        
	        <!-- End of row 3 -->
	        
	        <!-- Start of row 3.1 -->
	        <div class="form-accrod" style="display:none">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                  <div class="panel panel-default" style="border : 0.5px solid lightgrey !important">
                    <div class="panel-heading" role="tab" id="headingOne">
                      <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse19">
                         Choose Stores
                        </a>
                      </h4>
                    </div>
                    <div id="collapse19" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="heading1">
                      <div class="panel-body">
                          <div class="" id="listTable">
					           <div class="table-wrap">
					            <table id="dny-service-gstin-openingstock" class="display nowrap" style="width:100%">
						            <thead>
						                <tr>
						                	<th style="width:10px">Sr No.</th>
						                	<th>Gstin</th>
						                    <th>Location/Store</th>
						                </tr>
						            </thead>
						            <tbody>
						            </tbody>
					             </table>
					           </div>
					        </div>
                         
                      </div>
                    </div>
                  </div>
                 
                                                                                                 
                </div>
              </div>
	        
	        <!-- End of row 3.1 -->
	        
	        <!-- Start of row 4 -->
	        	<div class="row">
	        		<div class="col-md-12 button-wrap">
	  					<button id="dnyServiceSubmitBtn" class="btn btn-success blue-but" style="width:auto;">Save</button>
	      				<button id="dnyServiceCancelBtn" class="btn btn-success blue-but" style="width:auto;">Cancel</button>
	  				</div>
	            </div>	        
	        
	        <!-- End of row 4 -->
			</div> <!-- /form-wrap -->
	    </div> <!-- /dnyServiceFormDiv -->
	    <div id="dnyProductFormDiv">
	    	<div class="brd-wrap">
				<div id="">
		           <a href="javascript:void(0)" id="dny-product-cancel-link"><strong>Document History</strong></a> &raquo; <strong>Add Product</strong>
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
                      <!-- <span class="text-danger cust-error" id="prod-advolCess">This field is required and should be numeric.</span> -->
	   				</div>
	
					<div class="col-md-3" style="display:none">
	                   	<label for=""> Cess Non Advol Rate</label>
	                    <select id="dny-product-nonAdvolCess" name="dny-product-nonAdvolCess" ></select>
	                      <!-- <span class="text-danger cust-error" id="prod-nonAdvolCess">This field is required and should be numeric.</span> -->
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
						<label for="label"> Secondary Unit Of Measurement<span> *</span></label>
					  	<input type="text" name="dny-product-purchaseUOM" id="dny-product-purchaseUOM" ></select>
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
    
        <!-- Add Dynamic Additional Charge block starts here -->
	<div class="container" id="dnyAdditionalChargeDiv" style="display:none">
		<div class="brd-wrap">
			<div id="">
	            <a href="javascript:void(0)" id="dny-additional-charge-cancel-link"> <strong>Document History</strong></a> &raquo; <strong>Add Additional Charge</strong>
	 		</div>
		</div>
		<div class="form-wrap">
        <!-- Start of row 1 -->
        	<div class="row">
				<div class="col-md-6">
					<label for="label">Name<span style="font-weight: bold;color: #ff0000;"> *</span></label>
				  	<input type="text" id="dny-additional-charge-chargeNameValue" maxlength="50"/>
				  	<span class="text-danger cust-error" id="dny-additional-charge-charge-name-req">This field is required.</span> 					
				</div>
				
				<div class="col-md-6">
					<label for="label">Value<span style="font-weight: bold;color: #ff0000;"> *</span></label>
				  	<input type="text" id="dny-additional-charge-chargeValuedetail" maxlength="18" />
				  	 <span class="text-danger cust-error" id="dny-additional-charge-charge-value-req">This field is required</span> 
				</div>
        	</div>
        <!-- End of row 1 -->
         <!-- Start of row 4 -->
        	<div class="row">
        		<div class="col-md-12 button-wrap">
  					<button id="dnyAdditionalChargeSubmitBtn" class="btn btn-success blue-but" style="width:auto;">Save</button>
      				<button id="dnyAdditionalChargeCancelBtn" class="btn btn-success blue-but" style="width:auto;">Cancel</button>
  				</div>
        
            </div>	        
        
        <!-- End of row 4 -->
        </div>
	
	</div>
    <!-- Add Dynamic Additional Charge block ends here -->
    
    <!-- Add Dynamic customer block starts here -->
	<div class="container" id="dnyCustomerDiv" style="display:none">
		<div class="brd-wrap">
			<div id="">
	           <a href="javascript:void(0)"><strong>Document History</strong></a> &raquo; <strong>Add Customer</strong>
	 		</div>
		</div>
	   <div class="form-wrap">
	  	    <!-- Start of row 1 -->
	  		<div class="row">
		  		 <div class="col-md-4 diff">
	                 <h1 class="form-section-title">Customer Registered Under GST</h1><br><br>
	                 <div class="radio radio-success radio-inline">
	                     <input type="radio" class="styled" name="dnyCustType" value="Individual" id="Individual" checked="checked">
                         <label for="Individual">No </label>
	                 </div>
	                 <div class="radio radio-success radio-inline">
	                     <input type="radio" class="styled" name="dnyCustType" value="Organization" id="Organization">
                         <label for="Organization">Yes</label>
	                 </div>                                
	             </div>
	             <div id="dnyShowDivIfRegistered" >
		             <div class="col-md-4">
						<label for="">GSTIN</label>
						<input type="text"  maxlength="15" id="dnyCustGstId"/>
						<span class="text-danger cust-error" id="dny-custGstId-req">GSTIN should be in a proper format.</span>
					</div>
					<div class="col-md-4">
						<label for="">GSTIN State</label>
						<select id="dnyCustGstinState" name="dnyCustGstinState"></select>
					</div>
				</div>
	  		</div>
	        <!-- End of row 1 -->
	        <!-- Start of row 2 -->
	  		<div class="row">
	  		    <div class="col-md-4">
					<label for="">Customer Name <span>*</span></label>
					<input type="text" id="dnyCustName" maxlength="100" required="true"/>
					<span class="text-danger cust-error" id="dny-cust-name-req">This field is required.</span>
				</div>
				<div class="col-md-4">
					<label for="">Mobile Number</label>
					<input type="text" id="dnyContactNo"  maxlength="10"/> 
					<span class="text-danger cust-error" id="dny-contact-no-req">This field is required and should be 10 digits.</span>
				</div>
				<div class="col-md-4">
					<label for="">Email ID</label>
					<input type="text" maxlength="100" id="dnyCustEmail" />
					<span class="text-danger cust-error" id="dny-cust-email-format">This field should be in correct format.</span>
				</div>
	  		</div>
	        <!-- End of row 2 -->
	        <!-- Start of row 3 -->
	  		<div class="row">
		  		<div class="col-md-4">
					<label for="">Pin Code <span>*</span></label>
					<input type="text" name="dnyPinCode" required="true" id="dnyPinCode" maxlength="6"/> 
					<span class="text-danger cust-error" id="dny-empty-message"></span>
					<span class="text-danger cust-error" id="dny-cust-zip-req">Pin Code is required and should be 6 digits.</span>
				</div>
	  			<div class="col-md-4">
					<label for="">City <span>*</span></label>
					<input type="text" readonly="readonly" id="dnyCustCity" name="dnyCustCity" />
				</div>
				<div class="col-md-4">
					<label for="">State <span>*</span></label>
					<input type="text" readonly="readonly" id="dnyCustState" name="dnyCustState"/> 
					<span class="text-danger cust-error" id="dny-custState-err"></span>
				</div>
				<div class="col-md-4" style="display:none">
					<label for="">Country</label>
					<input type="text" readonly="readonly" value="India" name="dnyCustCountry" required="true" id="dnyCustCountry"/>
				</div>
	  		</div>
	        <!-- End of row 3 -->
	        <!-- Start of row 4 -->
	  		<div class="row" >
		  		<div class="col-md-4" id="dnyCustAddrDiv">
					<label for="dnyCustAddress">Address <span id="dnyCustAddrSpan" style="display:block">*</span></label>
					<textarea id="dnyCustAddress" maxlength="350" required="true" ></textarea> 
					<span class="text-danger cust-error" id="dny-address-req">This field is required </span>
				</div>
	  		
	  		</div>
	        <!-- End of row 4 -->
	        <!-- Start of row 5 -->
	  		<div class="row">
	  			<div class="col-md-12 button-wrap">
  					<button id="dnyCustSubmitBtn" class="btn btn-success blue-but" style="width:auto;">Save</button>
      				<button id="dnyCustCancelBtn" class="btn btn-success blue-but" style="width:auto;">Cancel</button>
  				</div>
	  		</div>
	        <!-- End of row 5 -->
	       
	     </div><!-- /form-wrap -->
	</div>
	<!-- Add Dynamic customer block ends here -->
	
	<!-- Preview Invoice Starts Here-->
	<div class="invoice-wrap" id="previewInvoiceDiv" style="display:none">
		<div class="container">
			<!-- Start of row 1 -->
			 <div class="row">
			 	<div class="col-xs-12">
				    <div class="text-center" ><!-- style="margin:8px 0" -->
                        <h2 class="section-title" id="generateInvoicePageHeaderPreview">Preview Invoice</h2>
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
	                            <div class="panel-body" id="customerDetailsBillToDiv">
	                               
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4" id="customerDetailsShipToUpperDiv">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Ship To</div>
	                            <div class="panel-body" id="customerDetailsShipToDiv">
	                                
	                            </div>
	                        </div>
	                    </div>
	                </div>
                 </div>
			 </div>
			<!-- End of row 1 -->
			<!-- Start of row 2 -->
			 <div class="row">
             	<div class="col-md-12">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="text-center" id="mytable2Header">Purchase Document</h3>
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
					        <div class="signature-wrap" id="diffPercentShowHide" style="display:none">
								<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
				        	</div>	
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
	                        	<p id="userAddressWithDetails"></p>	<br/>
	                        	<p><span style=""><b>*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'.</b></span></p>
						
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
			<!-- End of row 2 -->
			<!-- Start of row 3 -->
			 <div class="row">
			 	<div class="col-md-12 button-wrap">
	                <button type="button" id="finalSubmitId" class="btn btn-success blue-but" style="width:auto;">Send Document</button>
	                <button type="button" id="backToaddInvoiceDiv" class="btn btn-success blue-but" style="width:auto;">Edit</button>
	            </div>
			 
			 </div>
			<!-- End of row 3 -->
			
	    </div>
	</div>
	<!-- Preview Invoice Ends Here-->
	
	<!-- Show customer email addresss div - Start -->
	<div class="container" id="customerEmailDiv" style="display:none;">
		<div class="brd-wrap">
			<div id="">
	           <a href="javascript:void(0)"><strong>Document History</strong></a> &raquo; <strong>Add Customer Email</strong>
	 		</div>
		</div>
	   <div class="form-wrap">
	  	 <!-- Start of row 1 -->
	  	    <div class="row" style="margin:0 10px;color:red">
				Customer Email address is not present. Kindly Enter email address to send email. 
			</div>
		 <!-- End of row 1 -->
		 <!-- Start of row 2 -->
	  		<div class="row">
	  			<div class="col-md-4">
					<label for="">Email Address <span>*</span></label>
					<input type="text" id="cust_email_addr" maxlength="100" required="true" >
					<span class="text-danger cust-error" id="cust-email-format">This field should be in a correct format</span>
				</div>
	  		</div>
	  	 <!-- End of row 2 -->
	  	 <!-- Start of row 3 -->
        	<div class="row">
        		<div class="col-md-12 button-wrap">
  					<button id="custEmailSave" class="btn btn-success blue-but" style="width:auto;">Save</button>
      				<button id="custEmailCancel" class="btn btn-success blue-but" style="width:auto;">Cancel</button>
  				</div>
            </div>	 
	     <!-- End of row 3 -->
	  </div>
    </div>
	<!-- Show customer email addresss div - End -->




</section>

 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" name="conditionValue" id="conditionValue">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/generalMultipleChanges.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/invoice.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/form_submit.js"/>"></script>     
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/service_crud.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/add_charge_crud.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/service-product-client.js"/>"></script>       
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/otherFunctions.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/dnynamic-add-customer.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/dnynamic-add-customer-validations.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/dnynamic-add-services.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/dnynamic-add-products.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/multipleChanges/pincode-autocomplete.js"/>"></script>