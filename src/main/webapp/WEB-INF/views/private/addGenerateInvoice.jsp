<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/invoice.js"/>"></script>

 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
            <a href="<spring:url value="/home#doc_management"/>" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 <header class="insidehead" id="generateInvoicePreviewPageHeader" style="display:none">
            <!-- <a href="#" onclick="javascript:callInvoicePageOnBackButton()" class="btn-back"><i class="fa fa-angle-left"></i></a> -->
            <a hidden="true" style="display:none" class="logoText" href="#" id="generateInvoicePageHeaderPreview" ></a>
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 <header class="insidehead" id="generateInvoiceCustomerEmailPageHeader" style="display:none">
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

   <main>
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <section class="block generateInvoice">
            
             	<div class="container" id='loadingmessage' style='display:none;' align="middle">
				  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
				</div>
           
                <div class="container" id="addInvoiceDiv">
                <!--<form id="invoice_form_id" > -->
                  <!-- General Section Starts -->    
                      <div class="card">
                      <span><center><h4><b> Generate Document </b></h4></center></span>
                       <div class="form-group input-field mandatory">
	                       <label class="label">
	                              <select name="documentType" class="form-control" id="documentType">
	                              	  <option value="">Select Document type</option>
		                              <option value="invoice">Invoice</option>
									  <option value="billOfSupply">Bill of Supply</option>
									  <option value="invoiceCumBillOfSupply">Invoice cum Bill of Supply</option>
	                    		  </select>
	                              <div class="label-text">Select your Document type</div>
	                       </label>
	                       <!-- <span class="text-danger cust-error" id="documentType-csv-id">This field is required.</span>  -->
	                  </div>
	                  
	                  
	                 <!--  <input type="hidden" id="gstnStateId" value="27"/> -->
	                  <div class="form-group input-field mandatory" id="gstnStateIdDiv" style="display:none">
	                       <label class="label">
	                              <select name="gstnStateId" class="form-control" id="gstnStateId">
	                    		  </select>
	                              <div class="label-text">Select your GSTIN</div>
	                       </label>
	                       <!-- <span class="text-danger cust-error" id="gstnStateId-csv-id">This field is required.</span>  -->
	                  </div>
	                  
	                  <div class="form-group input-field mandatory" id="locationDiv" style="display:none">
	                       <label class="label">
	                              <select name="location" class="form-control" id="location">
	                    		  </select>
	                              <div class="label-text">Select your Location</div>
	                       </label>
	                       <input type="hidden" name="locationStore" id="locationStore"> 
	                       <!-- <span class="text-danger cust-error" id="gstnStateId-csv-id">This field is required.</span>  -->
	                  </div>
	                  
	                  <div class="form-group input-field mandatory">
                            <label class="label">
                                <input type="text" name="customerDetails.custName" id="invoice_date" readonly="readonly" required class="form-control">  
                                <div class="label-text label-text2">Document Date</div>
                            </label>
                            <!-- <span class="text-danger cust-error" id="invoice-date-csv-id">This field is required.</span> -->
                      </div>
                      
                       <div class="radio-inline text-center" style="margin-top:10px">
                            <span>Document for</span>
                            <div class="rdio rdio-success">
                                <input type="radio" name="invoiceFor" value="Service" id="radio2" checked="checked">
                                <label for="radio2" style="margin-left : 12px">Services</label>
                            </div>
                            <div class="rdio rdio-success">
                              
                                <input type="radio" name="invoiceFor" value="Product" id="radio1" >
                                <label for="radio1">Goods</label>
                            </div>
                      </div>
                     <br/>
                      <div class="form-group checkbox-inline" style="margin-top:10px">
                            <span></span> 
                            <div class="checkbox checkbox-success">
                                <input type="checkbox" name="ecommerce" value="" id="ecommerce">
                                <label for="ecommerce" id="ecommerce-label" ></label>
                            </div>
                       </div>
                       
                      <div class="form-group input-field mandatory" style="display:none" id="ecommerceGstinDiv" >
                            <label class="label">
                                <input type="text" name="ecommerceGstin" id="ecommerceGstin" maxlength="15" required class="form-control">  
                                <div class="label-text label-text2">GSTIN of Ecommerce operator</div>
                            </label>
                            <!-- <span class="text-danger cust-error" id="ecommerce-gstin-csv-id">This field is required.</span> -->
                      </div>
                      <br/>
                      <div class="form-group checkbox-inline" style="margin-top:10px">
                            <span></span> 
                            <div class="checkbox checkbox-success">
                                <input type="checkbox" name="reverseCharge" id="reverseCharge">
                                
                                <label for="reverseCharge" id="reverseCharge-label" >Reverse charge applicable</label>
                                <input type="text" id="reverseChargeYesNo" style="display:none">
                            </div>
                      </div>
                      
                      <div class="form-group input-field " id="invoicePeriodDivShowHide" style="display:none">
                     	 <label class="label">
						    <div class="input-group">
						    	<input type="text" class="form-control" id="invoicePeriodFromDateInString" readonly="readonly"/>
						    	<span class="input-group-addon">TO</span>
						    	<input type="text" class="form-control" id="invoicePeriodToDateInString" readonly="readonly"/>
						    </div>
						  	<div class="label-text label-text2">Document Period</div>
						 </label> 
						 <br/>
					  </div>
                        
                        
                      <div class="form-group input-field mandatory">
                      	   <label class="label">
                               <input type="text" name="customerDetails.custName" id="customer_name" required class="form-control">  
                               <div class="label-text label-text2">Customer Name / Mobile No</div>
                           </label>
	                           <!--  <span class="text-danger cust-error" id="customer-name-csv-id">This field is required.</span> -->
	                       <span class="text-danger cust-error" id="empty-message">
	                           No results found. <a href="javascript:void(0)" id="dnyAddNewCustomer">Click here to add a new customer.</a>
	                       </span>
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
                         
                         

                        <div class="form-group input-field" style="display:none">
                            <label class="label">
                                <input type="text" name="serviceCountry" id="customer_country" required class="form-control">  
                                <div class="label-text label-text2">State Of Supply</div>
                            </label>
                        </div>
                        
                        <div class="form-group input-field" style="display:none">
                            <label class="label">
                                <input type="text" name="servicePlace" id="customer_place" required class="form-control">  
                                
                                <div class="label-text label-text2">Billing Address</div>
                            </label>
                        </div>
                        
                        <div class="checkbox-inline" style="margin-top:10px">
                             <span></span> 
                            <div class="checkbox checkbox-success">
                                <input type="checkbox" name="shipToBill" value="" id="shipToBill" checked="checked">
                                <label for="shipToBill">Make Shipping Address same as Billing Address</label>
                            </div>
                        </div>
                        
                        <div id="consignee" style="display: none" >
                        
	                        <div class="form-group input-field" id="selectCountryShowHide">
	                           <label class="label">
	                               <select class="form-control" name="deliveryCountry" id="selectCountry">
						              <option value="">Select Country</option>
						              <option value="India">India</option>
						              <option value="Other">Other</option>
						           </select>
	                               <div class="label-text">Country</div>
	                           </label>
	                           <!-- <span class="text-danger cust-error" id="selectCountry-csv-id">This field is required.</span>  -->
	                        </div>
	                        <div class="form-group input-field mandatory">
	                            <label class="label">
	                                <input type="text" name="shipTo_customer_name" id="shipTo_customer_name" maxlength="50" required class="form-control">  
	                                <div class="label-text label-text2">Ship To Customer Name</div>
	                            </label>
	                            <!-- <span class="text-danger cust-error" id="shipTo-customer-name-csv-id">This field is required.</span> -->
	                        </div>
	                        
	                         <div class="form-group input-field mandatory">
	                            <label class="label">
	                                <input type="text" name="shipTo_address" id="shipTo_address" maxlength="500" required class="form-control">  
	                                
	                                <div class="label-text label-text2">Ship To Address</div>
	                            </label>
	                            <!-- <span class="text-danger cust-error" id="shipTo-customer-address-csv-id">This field is required.</span> -->
	                        </div>
	                        
	                       <div class="form-group input-field mandatory" style="display:none" id="shipTo-pincode-show-hide">
	                            <label class="label">
	                                <input type="text" name="shipTo_pincode" id="shipTo_pincode" required class="form-control" maxlength="6">  
	                                <div class="label-text label-text2">Ship To PinCode</div>
	                            </label>
	                            <!-- <span class="text-danger cust-error" id="shipTo-customer-pincode-csv-id">This field is required.</span> -->
	                            <span class="text-danger cust-error" id="empty-message-1"></span>
	                        </div>
	                        
	                        <div class="form-group input-field mandatory" style="display:none" id="shipTo-city-show-hide">
	                            <label class="label">
	                                <input type="text" name="shipTo_city" id="shipTo_city" readonly="readonly" required class="form-control">  
	                                <div class="label-text label-text2">Ship To City</div>
	                            </label>
	                            <!-- <span class="text-danger cust-error" id="shipTo-customer-city-csv-id">This field is required.</span> -->
	                            
	                        </div>
	                        
	                     <input type="hidden" name="shipTo_stateCode" id="shipTo_stateCode">
                         <input type="hidden" name="shipTo_stateCodeId" id="shipTo_stateCodeId">
                        
                        </div> 
                        
                        <div class="form-group input-field mandatory" id="selectState-show-hide">
                           <label class="label">
                                 <select name="deliveryPlace" class="form-control" id="selectState">
                    			 </select>
                                 <div class="label-text label-text2">Place Of Supply</div>
                           </label>
                           <!-- <span class="text-danger cust-error" id="selectState-csv-id">This field is required.</span>  -->
                        </div> 
                        
                        <div class="form-group input-field" id="pos-show-hide">
                           <label class="label">
                                 <input type="text" class="form-control" id="pos" disabled="disabled"/>
                                 <div class="label-text label-text2">Place Of Supply</div>
                           </label>
                           <!-- <span class="text-danger cust-error" id="selectState-csv-id">This field is required.</span>  -->
                        </div> 
                        
                        <div class="form-group input-field" style="display:none" id="typeOfExport">
                            <label class="label">
                                <select class="form-control" name="typeOfExport" id="exportType">
					              <option value="">Type Of Export</option>
					              <option value="WITH_IGST">With IGST</option>
					              <option value="WITH_BOND">With Bond</option>
					           </select> 
                                <div class="label-text" >Type Of Export</div>
                            </label>
                            <!-- <span class="text-danger cust-error" id="exportType-csv-id">This field is required.</span>  -->
                        </div> 
                        
                         <div class="form-group input-field ">
                            <label class="label">
                               <input type="text" name="poDetails.poNo" id="poDetails_poNo" class="form-control">
                               <!--  <select name="poDetails.poNo" class="form-control" id="poDetails_poNo"></select>   -->
                                <div class="label-text label-text2">PO/WO RF No</div>
                            </label>
                        </div>
                     
                        
                        <!-- Bill Method - Start  -->
                         <div class="form-group input-field mandatory">
                              <label class="label">
                                   <select id="calculation_on" name="" class="form-control">
                                    	<option value="">Select Type</option>
                                    	<option value="Amount">Quantity Based</option>
                                    	<option value="Lumpsum">Lumpsum</option>
                                   </select>  
                               <div class="label-text label-text2">Bill Method</div>
                               </label>
                               <!-- <span class="text-danger cust-error" id="calculation-on-csv-id">This field is required.</span> -->
                          </div>
                          <!-- Bill Method - End  -->

                       
                    </div>
                 <!-- General Section Ends -->    
                     
 			
                    <div class="accordion no-css-transition mb0">
                    
                    <!-- Service Section Starts -->       
                        <div class="accordion_in">
                            <div id="callOnEditId" class="acc_head"></div>
                            <div class="acc_content">
                                
                                <div class="form-group input-field mandatory">
                                    <label class="label">
                                        <input type="text" id="search-service-autocomplete" placeholder="Search Service" maxlength="15"  class="form-control"/>
                                        <input type="text" id="search-product-autocomplete" placeholder="Search Product" maxlength="15"  class="form-control"/>
                                        <select id="service_name" name="" class="form-control" style="display:none">
                                    
                                        </select> 
                                        <div id="service_name_label" class="label-text"></div>
                                    </label>
                                    <span class="text-danger cust-error" id="dny-service-no-records-found">
			                           No results found. <a href="javascript:void(0)" id="dnyAddNewService">Click here to add a new service.</a>
			                       </span>
			                       <span class="text-danger cust-error" id="dny-product-no-records-found">
			                           No results found. <a href="javascript:void(0)" id="dnyAddNewProduct">Click here to add a new product.</a>
			                       </span>
                                   <!--  <span class="text-danger cust-error" id="service-name-csv-id">This field is required.</span> -->
                                </div>

                                <div class="form-group input-field" style="display:none" id="uom-show-hide">
                                    <label class="label">
                                     	<input id="uomtoShow" disabled="disabled" type="text" class="form-control"> 
                                        <input id="uom" type="hidden" name=""> 
                                       <!--  <input id="ratetoShow" disabled="disabled" type="text" class="form-control">  -->
                                        <input id="rate" type="hidden">  
                                        <div class="label-text label-text2">Unit Of Measurement</div>
                                    </label>
                                </div>

                                <div class="form-group input-field mandatory" style="display:none" id="quantity-show-hide">
                                    <label class="label">
                                        <input id="quantity" type="text" maxlength="12" class="form-control">  
                                        <div class="label-text label-text2">Quantity</div>
                                    </label>
                                    <span class="text-danger cust-error" id="quantity-csv-id">This field is required.</span>
                                </div>
                                
                                <div class="form-group input-field" style="display:none" id="amount-show-hide">
                                    <label class="label">
                                        <input id="amountToShow" type="text" disabled="disabled" maxlength="18" class="form-control">  
                                        <input id="amount" type="hidden">  
                                        <input type="hidden" name="hsnSacCode" id="hsnSacCode">
                                        <div class="label-text label-text2">Amount</div>
                                    </label>
                                   <!--  <span class="text-danger cust-error" id="amountToShow-csv-id">This field is required.</span> -->
                                </div>
                                
                                <div class="form-group input-field">
                                    <label class="label">
                                        <input id="cess" type="text" maxlength="18" class="form-control">  
                                        <div class="label-text label-text2">Cess</div>
                                    </label>
                                    <span class="text-danger cust-error" id="cess-csv-id"></span>
                                </div>
		                        
		                        <div class="radio-inline text-center" style="margin-top:10px">
		                        	<span></span>
		                            
		                           
		                        </div>
		                        <div class="radio-inline text-left" style="margin-top:10px">
		                            <span style="color:black">Discount In</span>
		                            <div class="rdio rdio-success">
		                                <input type="radio" name="itemDiscountType" value="Percentage" id="radio23" checked="checked">
		                                <label for="radio23" style="margin-left : 12px">Percentage</label>
		                            </div>
		                            <div class="rdio rdio-success">
		                                <input type="radio" name="itemDiscountType" value="Value" id="radio13" >
		                                <label for="radio13" style="margin-left : 12px">Value</label>
		                            </div>
		                      	</div>
                                
                                <div class="form-group input-field">
                                    <label class="label">
                                        <input id="offerAmount" type="text" maxlength="15" class="form-control">  
                                        <div class="label-text label-text2">Discount % or Value</div>
                                    </label>
                                    <span class="text-danger cust-error" id="offerAmount-csv-id"></span>
                                </div>
                                
                                <div class="form-group checkbox-inline" style="margin-top:10px;display:none;" id="diffPercentageDiv">
		                            <span></span> 
		                            <div class="checkbox checkbox-success">
		                                <input type="checkbox" name="diffPercent" value="" id="diffPercent">
		                                <label for="diffPercent" id="diffPercent-label" >Eligible to be taxed at a differential percentage of the existing tax-rate as notified by government.</label>
		                            </div>
		                       </div>
                                
								<input type="hidden" id="unqValId" name="" value="">
                                <div class="btns btns-2 clearfix">
                                    <button id="service_add" type="button" class="btn btn-primary" value="Add">Add</button>
                                    <button id="service_edit" style="display:none" class="btn btn-primary">Save</button>
                                    <button id="service_cancel" onclick="cancel_service_row()" class="btn btn-default">Cancel</button>
                                </div>

                                <div class="cust-wrap">
                                    <div id="toggle">
                                    </div>
                                </div>

                            </div>
                        </div>
                        
                 <!-- Service Section Ends -->  
                      
                 <!-- Discount Section Starts -->           
                        
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
                        
                        
                 <!-- Discount Section Ends -->    
                 
                 <!--  -->  
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

                 <!--  -->
                        
                        <div class="accordion_in">
                            <div id="callOnAddChgEditId" class="acc_head">Additional Charges</div>
                            <div class="acc_content">
                             
                                <div class="form-group input-field">
                                    <label class="label">
                                        <select name="additionalChargeName" class="form-control" id="additionalChargeName">
	                    		  		</select>  
                                        <div class="label-text label-text2">Name</div>
                                    </label>
                                </div>
                                 <div class="form-group input-field">
                                    <label class="label">
                                          <input type="text" id="additionalChargeAmountToShow" required class="form-control">  
                                          <input type="hidden" name="additionalChargeAmount" id="additionalChargeAmount" value="">  
                                        <div class="label-text label-text2">Amount</div>
                                    </label>
                                </div>
                                <input type="hidden" id="unqAddChgValId" name="" value="">
                                
                                <div class="btns btns-2 clearfix">
                                    <button id="add_chg_add" class="btn btn-primary">Add</button>
                                    <button id="add_chg_edit" style="display:none" class="btn btn-primary">Save</button>
                                    <button id="add_chg_cancel" onclick="cancel_add_chg_row()" class="btn btn-default">Cancel</button>
                                </div>

                                <div class="cust-wrap">
                                    <div id="add_chg_toggle">
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Footer Note - Start -->
                         <div class="accordion_in">
                            <div id="callOnFooterNote" class="acc_head">Footer Note</div>
                            <div class="acc_content">
                             
                                <div class="form-group input-field">
                                    <label class="label">
                                        <input type="text" id="footerNote" required class="form-control" maxlength="150" value="${loginUser.footer }" placeholder="Footer Note &#40; max 150 characters &#41;">
                                        <div class="label-text label-text2"></div>
                                    </label>
                                </div>
                             
                            </div>
                        </div>
                        <!-- Footer Note - End -->
                        
                        
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
                    

                    <div class="btns">
                       <!--  <a class="btn btn-default btn-block" href="">Preview Final Invoice</a> -->
                        <button id="submitId" class="btn btn-success btn-block" value="">Preview Final Document</button>
                        <!-- <button id="directId" class="btn btn-success btn-block" value="">Direct Hit</button>   -->
                        <!-- <button id="dummySubmId" class="btn btn-success btn-block" value="">Dummy Trial</button> -->
                    </div>
				 <!--</form> -->
                </div>
                
                <!-- Add Dynamic Service/Product block starts here -->
                <div class="container" id="dnyServiceProductDiv" style="display:none">
               	   <div class="card">
                     
                      <div id="dnyServiceFormDiv">
                       <span><center><h4><b >Add Service</b></h4></center></span>
                       
                        <div class="form-group input-field mandatory">
		                  <label class="label">
		                  	<input type="text" id="dny-search-sac" maxlength="15" class="form-control"/>                  	
		                  	<div class="label-text label-text2">Search By SAC Code / SAC Description</div>
		                  </label>
		                   <input type="hidden" id="sacCodePkId"/>
		                  	<span class="text-danger cust-error" id="dny-search-sac-req">Please Search By SAC Code / SAC Description</span>
		                </div>	
                       
                       <div class="form-group input-field mandatory">
							<label class="label"> 
						    	<input type="text" disabled="disabled"  readonly="readonly" id="dny-sacDescriptionToShow" required class="form-control"/>
								<input type="hidden" id="dny-sacDescription" />
								<div class="label-text label-text2">SAC Description</div>
							</label>
							<span class="text-danger cust-error" id="dny-ser-sac-desc">This field is required.</span>
					   </div>
                       
                       <div class="form-group input-field mandatory">
							<label class="label">  
						 		<input type="text" disabled="disabled"  readonly="readonly" id="dny-sacCodeToShow" required class="form-control"/>
								<input type="hidden" id="dny-sacCode" />
								<div class="label-text label-text2">SAC Code</div>
							</label>
							<span class="text-danger cust-error" id="dny-ser-sac-code">This field is required.</span>
					   </div>
					   
					   <div class="form-group input-field mandatory">
							<label class="label">  
								<input type="text" id="dny-service-name" required="true" maxlength="100" class="form-control" />
								<div class="label-text label-text2">Service Name</div>
							</label>
							<span class="text-danger cust-error" id="dny-ser-name">This field is required.</span>
					  </div>
					   
					  <div class="form-group input-field mandatory">
							<label class="label">  
								<select name="dny-unitOfMeasurement" class="form-control" id="dny-unitOfMeasurement" class="form-control" >
		                        </select>
								<div class="label-text label-text2">Unit Of Measurement</div>
							</label>
							<!--<span class="text-danger cust-error" id="ser-uom">This field is required.</span>-->
					  </div> 
					   
					  <div class="form-group input-field mandatory" id="dny-divOtherUnitOfMeasurement">
		                    <label class="label">
	                  			<input type="text" maxlength="30" id="dny-otherUOM" class="form-control"/>
	                  			<input type="hidden" name="dny-tempUom" id="dny-tempUom">
	                  			<div class="label-text label-text2">Please Specify </div>
	                  		</label>
	                  		<span class="text-danger cust-error" id="dny-otherOrgType-req">This field is required.</span>
		          	  </div> 
		          	  
		          	  <div class="form-group input-field mandatory">
							<label class="label">  
								<input type="text" id="dny-serviceRate" maxlength="18" required="true" class="form-control"/>
								<div class="label-text label-text2">Service Rate (Rs.)</div>
							</label>
							<span class="text-danger cust-error" id="dny-ser-rate">This field is required.</span>
					  </div>
					  
					  <div class="form-group input-field mandatory">
						<label class="label">
						  <select name="dny-serviceIgst" class="form-control" id="dny-serviceIgst" class="form-control" >
	                        </select>
							<div class="label-text label-text2">Rate of tax (%)</div>
						</label>
						<span class="text-danger cust-error" id="dny-service-igst">This field is required.</span>
					  </div>
					  
                        <br/>
						<div class="com-but-wrap">
			              	<button type="submit" class="btn btn-primary" id="dnyServiceSubmitBtn">Save</button>
		            	    <button class="btn btn-secendory" id="dnyServiceCancelBtn">Cancel</button>
		            	</div>
                      </div>
                      
                      <div id="dnyProductFormDiv">
                       <span><center><h4><b >Add Product</b></h4></center></span>
                       
                       <div class="form-group input-field mandatory">
						  <label class="label">
							<input type="text" id="dny-search-hsn" maxlength="15"  class="form-control"/>                  	
							<div class="label-text label-text2">Search By HSN Code / HSN Description</div>
						  </label>
						  <input type="hidden" id="hsnCodePkId"/>
							<span class="text-danger cust-error" id="dny-search-hsn-req">Please Search By HSN Code / HSN Description </span>
						</div>
						
						<div class="form-group input-field mandatory">
							<label class="label">  
								<input type="text" disabled="disabled" id="dny-hsnDescriptionToShow" required="true" class="form-control"/>
								<input type="hidden" id="dny-hsnDescription" />
								<div class="label-text label-text2">HSN Description</div>
							</label>
							<span class="text-danger cust-error" id="dny-prod-hsn-desc">This field is required.</span>
						</div>
						
						<div class="form-group input-field mandatory">
							<label class="label">  
								<input type="text" disabled="disabled" id="dny-hsnCodeToShow" required="true" class="form-control"/>
								<input type="hidden" id="dny-hsnCode" />
								<div class="label-text label-text2">HSN Code</div>
							</label>
							<span class="text-danger cust-error" id="dny-prod-hsn-code">This field is required.</span>
						</div>
						
						<div class="form-group input-field mandatory">
							<label class="label">  
								<input type="text" id="dny-product-name" maxlength="100" required="true" class="form-control"/>
								<div class="label-text label-text2">Goods Name</div>
							</label>
							<span class="text-danger cust-error" id="dny-prod-name">This field is required.</span>
						</div>
						
						<div class="form-group input-field mandatory">
							<label class="label">  
								<select class="form-control" name="dny-product-unitOfMeasurement" class="form-control" id="dny-product-unitOfMeasurement">
								</select>
								<div class="label-text label-text2">Unit Of Measurement</div>
							   </label>
							<!--<span class="text-danger cust-error" id="dny-prod-uom">This field is required.</span>-->
						</div>
						
						<div class="form-group input-field mandatory" id="dny-product-divOtherUnitOfMeasurement">
							<label class="label">
								<input type="text"  id="dny-product-otherUOM" maxlength="30" class="form-control"/>
								<input type="hidden" name="dny-product-tempUom" id="dny-product-tempUom">
								<div class="label-text label-text2">Please Specify </div>
							</label>
							<span class="text-danger cust-error" id="dny-product-otherOrgType-req">This field is required.</span>
						</div>
						
						<div class="form-group input-field mandatory">
							<label class="label">  
								<input type="text" id="dny-productRate" maxlength="18" required="true" class="form-control"/>
								<div class="label-text label-text2">Goods Rate (Rate per unit of measurement)</div>
							</label>
							<span class="text-danger cust-error" id="dny-prod-rate">This field is required and should be numeric.</span>
						</div>
						
						<div class="form-group input-field mandatory">
							<label class="label"> 
							<select class="form-control" id="dny-productIgst" name="dny-productIgst" class="form-control">
							</select> 
								<div class="label-text label-text2">Rate of tax (%)</div>
							</label>
							<span class="text-danger cust-error" id="dny-prod-igst">This field is required and should be numeric.</span>
						</div>
                       
                       
                       
                       
                       
                       <br/>
						<div class="com-but-wrap">
			              	<button type="submit" class="btn btn-primary" id="dnyProductSubmitBtn">Save</button>
		            	    <button class="btn btn-secendory" id="dnyProductCancelBtn">Cancel</button>
		            	</div>
                       
                      </div>
                      
                  
                   
                   </div>
                </div>
                
                <!-- Add Dynamic Service/Product block ends here -->
                
                <!-- Add Dynamic customer block starts here -->
			   <div class="container" id="dnyCustomerDiv" style="display:none">
               	   <div class="card">
                      <span><center><h4><b> Add Customer </b></h4></center></span>
                      
                    <div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" id="dnyCustName" maxlength="100" required="true" class="form-control"/>
							<div class="label-text label-text2">Customer Name</div>						
						</label>
						<span class="text-danger cust-error" id="dny-cust-name-req">This field is required.</span>
					</div>
					
					<div class="form-group radio input-field">
                        <span>Customer Type</span>
                        <div class="rdio rdio-success">
                            <input type="radio" name="dnyCustType" value="Individual" id="Individual" checked="checked">
                            <label for="Individual">Not Registered Under GST</label>
                        </div>
                        <div class="rdio rdio-success">
                            <input type="radio" name="dnyCustType" value="Organization" id="Organization">
                            <label for="Organization">Registered Under GST</label>
                        </div>
                     </div> 
                     
                     <div class="form-group input-field ">
						<label class="label">  
							<input type="text" id="dnyContactNo"  maxlength="10" class="form-control"/> 
							<div class="label-text label-text2">Mobile Number</div>
						</label>
						
						<span class="text-danger cust-error" id="dny-contact-no-req">This field is required and should be 10 digits.</span>
					</div>	
					
					<div class="form-group input-field">
						<label class="label">  
							<input type="text" maxlength="100" id="dnyCustEmail" class="form-control"/>
							<div class="label-text label-text2">Email ID</div>
						</label>
						<span class="text-danger cust-error" id="dny-cust-email-format">This field should be in correct format.</span>
				    </div>
				    
				    <div id="dnyShowDivIfRegistered" >
				    <div class="form-group input-field ">
						<label class="label">  
							<input type="text"  maxlength="15" id="dnyCustGstId" class="form-control"/>
							<div class="label-text label-text2">GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="dny-custGstId-req">GSTIN should be in a proper format.</span>
					</div>
					
					<div class="form-group input-field " id="" style="">
						<label class="label">  
							<select id="dnyCustGstinState" name="dnyCustGstinState" class="form-control"></select>
							<div class="label-text label-text2">GSTIN State</div>
						</label>
						<span class="text-danger cust-error" id="dny-custGstId-req">GSTIN should be in a proper format.</span>
					</div>
					</div>
					
					<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" name="dnyPinCode" required="true" id="dnyPinCode" maxlength="6" class="form-control"/> 						
							<div class="label-text label-text2">Pin Code</div> 
						</label>
						<span class="text-danger cust-error" id="dny-empty-message"></span>
						<span class="text-danger cust-error" id="dny-cust-zip-req">Pin Code is required and should be 6 digits.</span>
					</div>
					
					<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" readonly="readonly" id="dnyCustCity" name="dnyCustCity" class="form-control" />  
							<div class="label-text label-text2">City</div>
						</label>
					</div>
					
					<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" readonly="readonly" id="dnyCustState" name="dnyCustState" class="form-control"/>  
							<div class="label-text label-text2">State</div>
						</label>
						<span class="text-danger cust-error" id="dny-custState-err"></span>
					</div>
					
					<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" readonly="readonly" value="India" name="dnyCustCountry" required="true" id="dnyCustCountry" class="form-control"/>  
							<div class="label-text label-text2">Country</div>
						</label>
					</div>
					
					<div class="form-group input-field mandatory" id="dnyCustAddrDiv">
						<label class="label">
							<textarea id="dnyCustAddress" maxlength="350" required="true" class="form-control"></textarea>  
							<div class="label-text label-text2">Address</div>
						</label>
						<span class="text-danger cust-error" id="dny-address-req">This field is required </span>
					</div>
					<br/>
					<div class="com-but-wrap">
		              	<button type="submit" class="btn btn-primary" id="dnyCustSubmitBtn">Save</button>
	            	    <button class="btn btn-secendory" id="dnyCustCancelBtn">Cancel</button>
	            	</div>
                       
	              </div>
	              
               </div>
               <!-- Add Dynamic customer block ends here -->
                
                <div class="container" id="previewInvoiceDiv" style="display:none">
					<!-- Latest Html provided - Start -->
					<div class="card">
					   <div class="invoicePage">
					       <div class="invoiceDetail">
					       <img alt="No Logo Uploaded" src="${pageContext.request.contextPath}/getOrgLogo">
								<%-- <img src="<spring:url value="/resources/images/logo.png"/>"> --%>
								<div class="invoiceInfo" id="userDetailsInPreview">
								
								<%--     <h4>${userDetails.organizationMaster.orgName }</h4>
								    <p><span>${userDetails.organizationMaster.address1 },${userDetails.organizationMaster.address2 },${userDetails.organizationMaster.city } - ${userDetails.organizationMaster.pinCode }</span></p>
								    <p><span>GSTIN No.:</span></p>
								    <p><span>PAN No.:</span>${userDetails.organizationMaster.panNumber }</p>
								    <p><span>Invoice No:</span></p>
								    <p><span>Invoice Date:</span></p>
								    <p><span>DCPI No.:</span></p> --%>
								</div>
					         </div>
					
					         <div id="customerDetailsInPreview" class="row" >
									
					         </div>
					         
					         <div class="row" id="placeOfSupplyPreview">
					         
					         </div>
					          
					          <div class="invoiceTable">
					          	 <!-- FIRST TABLE STARTS -->
						          	<div id="stable">
						          	
						          	
						          	</div>
						          <!-- FIRST TABLE ENDS -->
						          
						          <!-- SECOND TABLE STARTS -->
						          	<div id="stable1">
						          	
						          	
						          	</div>
						          <!-- SECOND TABLE ENDS -->
						          <!-- THIRD TABLE STARTS -->
						             <div id="stable2">
						             
						             
						             </div>
						          <!-- THIRD TABLE ENDS -->
						          <!-- FOURTH TABLE STARTS -->
						          	<div id="stable3">
						          	
						          	
						          	</div>
						          <!-- FOURTH TABLE ENDS -->
					          
					          
					          </div>
							  <br/>
					 		 <div class="row" id="diffPercentShowHide" style="margin:0 10px;display:none">
									<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
					          </div>
					          <hr>
					          <div class="row" id="preview_customer_purchase_order">
									
					          </div>
					          <br>
					
					            <div class="row">
									<div class="col-sm-6">
									    <div class="invoiceInfo" style="width:49%; float:left;">
									        <p><span>Receiver Name</span></p>
									        <br>
									        <p>Receiver Signature</p>
									    </div>
									</div>
									<div class="col-sm-6">
									    <div class="invoiceInfo text-right">
									        <p><span>For Supplier Name</span></p>
									        <br>
									        <p>Authorized Signature</p>
									    </div>
									</div>
					            </div>
					            <br>
					            <hr>
							
								
					
					            <div class="invoiceInfo">
									<!-- <p><span>Address of Principal Place of Business of the Supplying State </span></p> -->
									<p id="userAddressWithDetails"></p>
									<br>
									
									<h4>Declaration</h4>
									<p><span>I) Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.</span></p>
									<p><span>II) The normal terms governing the above sale are printed overleaf. </span></p>
									<p><span>E&OE</span></p>
					            </div>
					            <br>
					            
					            <div id="footerNoteDiv" style="text-align:center">
					            </div>
					
					    </div>
					
					    <div class="btns">
					       <!--  <a class="btn btn-primary btn-threefourth" href="#!">E-Sign Invoice</a> -->
					        <a id="finalSubmitId" class="btn btn-primary btn-half" href="#!" style="">Send Document</a>
					        <a id="backToaddInvoiceDiv" class="btn btn-primary btn-half" href="#!">Edit</a>
					    </div>
					</div>					
					
					
                	<!-- Latest Html provided - End -->
                </div>
                
                <!-- Show customer email addresss div - Start -->
                <div class="container" id="customerEmailDiv" style="display:none;">
			      <form class="form" role="form">
			        <div class="card">
						<span><center><h4><b> Add Customer Email</b></h4></center></span>
						<div class="row" style="margin:0 10px">
							Customer Email address is not present.<br/>Kindly Enter email address to send email. 
						</div>
                        <div class="form-group input-field mandatory">
                            <label class="label">
                                <input type="text" id="cust_email_addr" class="form-control" maxlength="100">  
                                <div class="label-text">Email Address</div>
                            </label>
                            <span class="text-danger cust-error" id="cust-email-format">This field should be in a correct format</span>
                        </div>

                        <div class="form-group input-field text-center">
                            <a href="#" id="custEmailSave" class="btn btn-primary">Save</a>
                            <a href="#" id="custEmailCancel" class="btn btn-primary">Cancel</a>
                        </div>

                    </div>
			      </form>
			    </div>
                <!-- End -->
                
                
           <!--    </form>   -->
            </section>

        </main>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/form_submit.js"/>"></script>     
   
<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/service_crud.js"/>"></script>   

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/add_charge_crud.js"/>"></script> 

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/service-product-client.js"/>"></script>       

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/otherFunctions.js"/>"></script> 

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/dnynamic-add-customer.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/dnynamic-add-customer-validations.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/dnynamic-add-services.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/dnynamic-add-products.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/pincode-autocomplete.js"/>"></script>
