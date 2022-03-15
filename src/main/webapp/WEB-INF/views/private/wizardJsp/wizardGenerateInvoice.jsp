<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/invoice.js"/>"></script>
 

<section class="insidepages">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="container" id='loadingmessage' style='display:none;'  align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
           
	<div class="inside-cont">
			<div class="breadcrumbs">
				 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
		         	<%-- <a href="<spring:url value="/wHome#doc_management"/>">Home</a> <span>&raquo;</span> Generate Document --%>
		         	<a href="<spring:url value="/wHome#doc_management"/>"> Home</a> <span> &raquo; </span><a href="./getWizardMyInvoices"> Document History </a></span> <span> &raquo; </span> Generate Document
		 		 </header>
		 		 <header class="insidehead" id="generateInvoicePreviewPageHeader" style="display:none">
		            <p id="generateInvoicePageHeaderPreview" > </p>
				 </header>
				 <header class="insidehead" id="generateInvoiceCustomerEmailPageHeader" style="display:none">
            		<p  href="javascript:void(0)">Customer Email</p>
 				 </header>
 				  <header class="insidehead" id="generateInvoiceCustomerDetailsPageHeader" style="display:none">
            		<p  href="javascript:void(0)">Home  <span>&raquo;</span> Generate Document <span>&raquo;</span> Add Customer Details</p>
 				 </header>
 				  <header class="insidehead" id="generateInvoiceProductDetailsPageHeader" style="display:none">
            		<p  href="javascript:void(0)">Home  <span>&raquo;</span> Generate Document <span>&raquo;</span> Add Product Details</p>
 				 </header>
 				  <header class="insidehead" id="generateInvoiceServiceDetailsPageHeader" style="display:none">
            		<p  href="javascript:void(0)">Home  <span>&raquo;</span> Generate Document <span>&raquo;</span> Add Service Details</p>
 				 </header>
			</div>
        	<div class="" id="addInvoiceDiv">
        		<div class="account-det">
        			 <div class="card">
	        			 	<div class="det-row">        			      
		                       <div class="det-row-col astrick">
					            	<div class="label-text">Document type</div>
					              	<select name="documentType" class="form-control" id="documentType">
		                              	  <option value="">Select</option>
			                              <option value="invoice">Invoice</option>
										  <option value="billOfSupply">Bill of Supply</option>
										  <option value="invoiceCumBillOfSupply">Invoice cum Bill of Supply</option>
		                    		</select>
								  	<!-- <span class="text-danger cust-error" id="documentType-req">This field is required.</span> -->
					            </div>
					            
					            <div class="det-row-col astrick" id="gstnStateIdDiv" style="display:none">	<!--  -->
					            	<div class="label-text">GSTIN</div>
					              	<select name="gstnStateId" class="form-control" id="gstnStateId">
		                    		</select>
								  	<!-- <span class="text-danger cust-error" id="gstnStateId-req">This field is required.</span> -->
					            </div>
					            
					            <div class="det-row-col astrick" id="locationDiv" style="display:none">	<!--  -->
					            	<div class="label-text">Location/Store</div>
					              	<select name="location" class="form-control" id="location">
		                    		</select>
		                       		<input type="hidden" name="locationStore" id="locationStore">
								  	<!-- <span class="text-danger cust-error" id="location-req">This field is required.</span> -->
					            </div>
	        			
					            <div class="det-row-col astrick">
					            	<div class="label-text">Document Date</div>
					              	<input type="text" name="invoice_date" id="invoice_date" readonly="readonly" required class="form-control">
								  	<!-- <span class="text-danger cust-error" id="invoice_date-req">This field is required.</span> -->
					            </div>	        					
							</div>
							
			                <div class="det-row">  
			                	<div class="det-row-col-half"> Document for &nbsp;&nbsp;&nbsp;
					              <input type="radio" name="invoiceFor" value="Product" id="radio1" checked="checked" >
					              <label for="goods"> Goods</label>
					              <input type="radio" name="invoiceFor" value="Service" id="radio2">
					              <label for="services">Services</label>
					            </div>                       
		                      
		                     	<div class="det-row-col-half ">
					              <input type="checkbox" name="reverseCharge" id="reverseCharge">	
					              <label for="reverseCharge" id="reverseCharge-label" >Reverse charge applicable</label>		<!-- for="rev" -->
		                          <input type="text" id="reverseChargeYesNo" style="display:none">
					            </div>
				          	</div>
				          	
				          	<div class="det-row">
						          <div class="det-row-col-half">
						              <input type="checkbox" name="ecommerce" value="" id="ecommerce">
						              <label for="ecommerce" id="ecommerce-label"> Goods are sold through E-commerce operator</label>		<!-- for="ec" -->
						          </div>
						       
						          <div class="det-row-col-half astrick" style="display:none" id="ecommerceGstinDiv" >
						          		<div class="label-text">GSTIN of Ecommerce operator</div>
						          		<input type="text" name="ecommerceGstin" id="ecommerceGstin" maxlength="15" required class="form-control"> 
								  		<!-- <span class="text-danger cust-error" id="ecommerceGstin-req">This field is required.</span>  -->
						          </div>
				          	</div>
				          	
				          	<div class="det-row" id="invoicePeriodDivShowHide" style="display:none" >
					            <div class="det-row-col-full astrick">Invoice Period </div>
					            <div class="det-row-col-doc_date">
					               	<input type="text" class="form-control" id="invoicePeriodFromDateInString" readonly="readonly"/>
								  	<!-- <span class="text-danger cust-error" id="invoicePeriodFromDateInString-req">This field is required.</span> --> 	
					            </div>
					            <div class="det-row-col-label">TO</div>
					           	<div class="det-row-col-doc_date">		            
					             	<input type="text" class="form-control" id="invoicePeriodToDateInString" readonly="readonly"/>
								  	<!-- <span class="text-danger cust-error" id="invoicePeriodToDateInString-req">This field is required.</span> --> 
					            </div>
				          	</div> 
				          	
				          	<div class="det-row">		          		
					            <div class="det-row-col-half astrick">
					            	<div class="label-text">Customer Name / Mobile No</div>
						          	<input type="text" name="customerDetails.custName" id="customer_name" required class="form-control"> 
		                            <span class="text-danger cust-error" id="empty-message">
		                            	No results found. <a href="javascript:void(0)" id="dnyAddNewCustomer">Click here to add a new customer.</a>
		                            </span> 
					            </div>
					            
					            <div class="det-row-col-half ">
					            	<div class="label-text"></div>
					            	<input type="checkbox" name="shipToBill" value="" id="shipToBill" checked="checked">
		                            <label for="shipToBill">Make Shipping Address same as Billing Address</label>	<!-- for="add" -->
					            </div>
				          	</div>
			          	 	 
			          	 	 <!-- hidden fields for customer -->
	                         <input type="hidden" name="customerDetails.contactNo" id="customer_contactNo" maxlength="10"> 
	                         <input type="hidden" name="customerDetails.id" id="customer_id"><!-- mandatory -->
	                         <input type="hidden" name="customerDetails.custAddress1" id="customer_custAddress1"><!-- optional -->
	                       	 <!-- <input type="hidden" name="customerDetails.custAddress2" id="customer_custAddress2">  -->
	                         <input type="hidden" name="customerDetails.custCity" id="customer_custCity"><!-- optional -->
	                         <input type="hidden" name="customerDetails.custState" id="customer_custState"><!-- optional -->
	                         <input type="hidden" name="customerDetails.custStateCode" id="customer_custStateCode">
	                         <input type="hidden" name="customerDetails.custStateCodeId" id="customer_custStateCodeId">
	                         <input type="hidden" name="customerDetails.custType" id="customer_custType">
	                         <input type="hidden" name="customerDetails.custStateId" id="customer_custStateId">
	                         <input type="hidden" name="customerDetails.custGstId" id="customer_custGstId">
	                         <input type="hidden" name="customerDetails.custEmail" id="customer_custEmail">			          	
			          	
		                    <div class="det-row" style="display:none">
		                    	<div class="det-row-col" style="display:none">
		                    		<div class="label-text">State Of Supply</div>
						          	<input type="text" name="serviceCountry" id="customer_country" required class="form-control">
		                            <!-- <label for="shipToBill">State Of Supply</label> -->
		                    	</div>
		                    	<div class="det-row-col" style="display:none">
		                    		<div class="label-text">Billing Address</div>
						          	<input type="text" name="servicePlace" id="customer_place" required class="form-control">
		                            <!-- <label for="shipToBill">Billing Address</label> -->
		                    	</div>
		                    </div>    
	                    
				          	<div id="consignee" >
			                    <div class="det-row">
			                        <div class="det-row-col astrick">
			                    		<div class="label-text">Ship To Customer Name</div>
							          	<input type="text" name="shipTo_customer_name" id="shipTo_customer_name" maxlength="50" required class="form-control">
								  		<!-- <span class="text-danger cust-error" id="shipTo_customer_name-req">This field is required.</span>  -->
		                    		</div>
			                       
			                         <div class="det-row-col astrick">
			                    		<div class="label-text">Ship To Address</div>
							          	<input type="text" name="shipTo_address" id="shipTo_address" maxlength="500" required class="form-control">
								  		<!-- <span class="text-danger cust-error" id="shipTo_address-req">This field is required.</span> --> 
		                    		</div>
		                    			                    	
		                        	<div class="det-row-col astrick" style="display: none" id="shipTo-pincode-show-hide">
		                    			<div class="label-text">Ship To PinCode</div>
						          		<input type="text" name="shipTo_pincode" id="shipTo_pincode" required class="form-control" maxlength="6">
			                            <span class="text-danger cust-error" id="empty-message-1"></span> 
		                   			</div>					         	
			                    </div> 
			                    <div class="det-row">                        	
			                        <div class="det-row-col astrick" style="display: none" id="shipTo-city-show-hide">
		                    			<div class="label-text">Ship To City</div>
						          		<input type="text" name="shipTo_city" id="shipTo_city" readonly="readonly" required class="form-control">  
								  		<!-- <span class="text-danger cust-error" id="shipTo_city-req">This field is required.</span>  -->
		                   			</div>
		                   				
			                    	<div class="det-row-col astrick" id="selectCountryShowHide">
						            	<div class="label-text">Country</div>
						              	<select class="form-control" name="deliveryCountry" id="selectCountry">
								              <option value="">Select</option>
								              <option value="India">India</option>
								              <option value="Other">Other</option>
								         </select>
								  		<!-- <span class="text-danger cust-error" id="selectCountry-req">This field is required.</span> 	 -->							         
						         	</div>  
						            <div class="det-row-col astrick" id="selectState-show-hide">
						            	<div class="label-text">Place Of Supply</div>
						              	<select name="deliveryPlace" class="form-control" id="selectState">
			                    		</select>
								  		<!-- <span class="text-danger cust-error" id="selectState-req">This field is required.</span>  -->
							        </div>				         	
			                    </div>   
			                      
				          		<input type="hidden" name="shipTo_stateCode" id="shipTo_stateCode">
		                        <input type="hidden" name="shipTo_stateCodeId" id="shipTo_stateCodeId">
				          	</div>
			          	
				          	<div class="det-row">
				          		<div class="det-row-col" id="pos-show-hide">
					            	<div class="label-text">Place Of Supply</div>
					              	<input type="text" class="form-control" id="pos" disabled="disabled"/>
						        </div>
						        					        
		                        <div class="det-row-col">
					            	<div class="label-text">PO/WO RF No</div>
					              	<input type="text" name="poDetails.poNo" id="poDetails_poNo" class="form-control">
						        </div> 	
						       
		                         <div class="det-row-col astrick">
					            	<div class="label-text">Bill Method</div>
					              	<select id="calculation_on" name="" class="form-control">
		                              	<option value="">Select</option>
		                              	<option value="Amount">Quantity Based</option>
		                              	<option value="Lumpsum">Lumpsum</option>
		                            </select> 
								  	<!-- <span class="text-danger cust-error" id="calculation_on-req">This field is required.</span>  -->
							     </div>
				          	</div>
			          	
		                     <div class="det-row">	
		                     	<div class="det-row-col" style="display:none" id="typeOfExport">
					            	<div class="label-text">Type Of Export</div>
					              	<select class="form-control" name="typeOfExport" id="exportType">
							              <option value="">Type Of Export</option>
							              <option value="WITH_IGST">With IGST</option>
							              <option value="WITH_BOND">With Bond</option>
							           </select>
							     </div>
		                     </div>
        			 </div> 
        			 
        			<div class="accordion no-css-transition mb0">
        			 	<!-- Service Section Starts -->
        			 	<div class="accordion_in" >
                            <div id="callOnEditId" class="acc_head"></div>
                           
                            <div class="acc_content" style="background:none">  
	                            <div class="det-row">                          	
						            <div class="det-row-col astrick">
						            	<div id="service_name_label" class="label-text"></div>
                                        <input type="text" id="search-service-autocomplete" placeholder="Search" maxlength="15"  class="form-control"/>
                                        <input type="text" id="search-product-autocomplete" placeholder="Search" maxlength="15"  class="form-control"/>
						              	<select id="service_name" name="" class="form-control" style="display:none">
	                                    </select> 
									  	<span class="text-danger cust-error" id="dny-service-no-records-found">
				                           No results found. <a href="javascript:void(0)" id="dnyAddNewService">Click here to add a new service.</a>
				                       	</span>
				                       	<span class="text-danger cust-error" id="dny-product-no-records-found">
				                           No results found. <a href="javascript:void(0)" id="dnyAddNewProduct">Click here to add a new product.</a>
				                       	</span>
						            </div>
		        					
	                                <div class="det-row-col" style="display:none;padding-bottom: 15px;" id="uom-show-hide" >	
						            	<div class="label-text">Unit Of Measurement</div>
						              	<input id="uomtoShow" disabled="disabled" type="text" class="form-control"> 
	                                    <input id="uom" type="hidden" name=""> 
	                                    <input id="rate" type="hidden">
						            </div>	
	                                
	                                <div class="det-row-col astrick" style="display:none;padding-bottom: 15px;" id="quantity-show-hide">
						            	<div class="label-text">Quantity</div>
						              	<input id="quantity" type="text" maxlength="12" class="form-control">  
								  		<!-- <span class="text-danger cust-error" id="quantity-req">This field is required.</span>  -->
						            </div>	
	                                
	                                <div class="det-row-col" style="display:none;padding-bottom: 15px;" id="amount-show-hide">	<!--  -->
						            	<div class="label-text">Amount</div>
						              	<input id="amountToShow" type="text" disabled="disabled" maxlength="18" class="form-control">  
	                                    <input id="amount" type="hidden">  
	                                    <input type="hidden" name="hsnSacCode" id="hsnSacCode">
						            </div>		
						            
						            <div class="det-row-col">
						            	<div class="label-text">Cess</div>
						              	<input id="cess" type="text" maxlength="18" class="form-control">
	                                    <span class="text-danger cust-error" id="cess-csv-id"></span>
						            </div>							           
	                                    
	                                <input type="hidden" id="unqValId" name="" value="">
                               </div>
                               <div class="det-row">	            
				                	<div class="det-row-col">
				                		<div class="label-text">&nbsp;&nbsp;&nbsp;
											<div class="label-text">Discount In&nbsp;&nbsp;&nbsp;
												<input type="radio" name="itemDiscountType" value="Percentage" id="radio23" checked="checked">
												<label for="radio23"> Percentage</label>
												<input type="radio" name="itemDiscountType" value="Value" id="radio13" >
												<label for="radio13">Value</label>
											</div>
										</div>	
						            </div>  							                                				           
	                                <div class="det-row-col">
						            	<div class="label-text">Discount % or Value</div>
						              	<input id="offerAmount" type="text" maxlength="15" class="form-control">  
	                                    <span class="text-danger cust-error" id="offerAmount-csv-id"></span>
						            </div>
	                                <div class="det-row-col-full" style="margin-top:10px;display:none;" id="diffPercentageDiv">	<!--  -->
						              	<input type="checkbox" name="diffPercent" value="" id="diffPercent">
						            	<label>Eligible to be taxed at a differential percentage of the existing tax-rate as notified by government.</label>
						            </div>     
                               </div>
                               <br>
                               <div class="insidebtn"> 
                                	 <input id="service_add" type="button" class="sim-button button5" value="Add">
                                	 <input id="service_edit" type="button" class="sim-button button5" value="Save" style="display:none" >
                                	 <input id="service_cancel" type="button" class="sim-button button5" value="Cancel" onclick="cancel_service_row()">
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
	                            <div class="acc_content" style="background:none">
	                            	<div class="det-row">
	                            		<div class="det-row-col"> Discount In &nbsp;&nbsp;&nbsp;
								            <input type="radio" name="discountType" value="Percentage" id="radio3" checked="checked">
								            <label for="radio3"> Percentage</label>
								            <input type="radio" name="discountType" value="Value" id="radio4">
								            <label for="radio4">Value</label>
							            </div>
	                            		
	                            		<div class="det-row-col astrick">
								            <div class="label-text">Discount % or Value</div>
								             <input type="text" name="discountValue" id="discountValue" required class="form-control">  
								        </div>
					              
							            <div class="det-row-col ">
								            <div class="label-text">Amount</div>
								            <input type="text" disabled = "disabled" id="discountAmountToShow" class="form-control">  
	                                        <input type="hidden" name="discountAmount" id="discountAmount" value=""> 
								        </div>
						        		
						        		<div class="det-row-col ">
								            <div class="label-text">Remarks</div>
								            <input type="text" name="discountRemarks" id="discountRemarks"  required class="form-control">   
								        </div>
	                            	</div>
	                            </div>
	                        </div>
	                 	<!-- Discount Section Ends -->
	                 	
	                 	<div class="acc_content"  style="display:none" style="background:none">
	        			 	<div class="det-row">
	        			 		<div class="det-row-col"> Additional Charges In &nbsp;&nbsp;&nbsp;
						            <input type="radio" name="additionalChargesType" value="Percentage" id="radio5" checked="checked">
						            <label for="radio5"> Percentage</label>
						            <input type="radio" name="additionalChargesType" value="Value" id="radio6">
						            <label for="radio6">Value</label>
					            </div>  
					            
					            <div class="det-row-col astrick">
						            <div class="label-text">Additional Charge % or Value</div>
						             <input type="text" name="additionalChargesValue" id="additionalChargesValue" required class="form-control">
						        </div>
					              
					            <div class="det-row-col ">
						            <div class="label-text">Amount</div>
						            <input type="text" disabled = "disabled" id="additionalAmountToShow" required class="form-control">  
								    <input type="hidden" name="additonalAmount" id="additonalAmount" value="">
						        </div>
						       
						        <div class="det-row-col ">
						            <div class="label-text">Remarks</div>
						            <input type="text" name="additionalChargesRemark"  id="additionalChargesRemark" class="form-control">  
						        </div>
	        			 	</div>
	    				</div>
	    				
	    				<div class="accordion_in">
                            <div id="callOnAddChgEditId" class="acc_head">Additional Charges</div>
                            <div class="acc_content" style="background:none">
	                           <div class="det-row">
	                           		<div class="det-row-col-half" >
						            	<div class="label-text">Name</div>
						              	<select name="additionalChargeName" class="form-control" id="additionalChargeName">
	                    		  		</select>  
							        </div>
                                
	                                 <div class="det-row-col-half ">
							            <div class="label-text">Amount</div>
							            <input type="text" id="additionalChargeAmountToShow" required class="form-control">  
	                                    <input type="hidden" name="additionalChargeAmount" id="additionalChargeAmount" value="">  
							        </div>	                                 
	                                <input type="hidden" id="unqAddChgValId" name="" value="">
	                           </div>                              	
                                <br>
                                <div class="insidebtn"> 
                                	 <input id="add_chg_add" type="button" class="sim-button button5" value="Add">
                                	 <input id="add_chg_edit" type="button" class="sim-button button5" value="Save" style="display:none" >
                                	 <input id="add_chg_cancel" type="button" class="sim-button button5" value="Cancel" onclick="cancel_add_chg_row()">
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
                            <div class="acc_content" style="background:none">
                                <div class="det-row">
	                            	<div class="det-row-col-full ">
							            <input type="text" id="footerNote" value="${loginUser.footer }" required class="form-control" maxlength="150" placeholder="Footer Note &#40; max 150 characters &#41;">  
							        </div>	               
	                           </div>        
                            </div>
                        </div>
                        <!-- Footer Note - End -->
        			 </div>
        			 
                 	 <div class="card" style="display:none;margin-bottom:-5px" >
                 	  	<div class="det-row">
                       		<div class="det-row-col ">
					            <div class="label-text">Sub Total ( After Discount and Additional Charges )</div>
								<input type="text" disabled="disabled" id="amountAfterDiscountToShow" class="form-control">  
                                <input type="hidden" name="amountAfterDiscount" id="amountAfterDiscount">  
					        </div>
					        
					        <div class="det-row-col ">
					            <div class="label-text">Total Tax</div>
								<input type="text" disabled="disabled" id="totalTaxToShow" class="form-control"> 
                                <input type="hidden"  name="totalTax" id="totalTax">  
					        </div>
					        
					        <div class="det-row-col">
					            <div class="label-text">Invoice Value</div>
								<input type="text" disabled="disabled" id="invoiceValueToShow" class="form-control"> 
                                <input type="hidden"  name="invoiceValue" id="invoiceValue" value=""> 
					        </div>
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
					 
       			       
	        		</div>	<!-- End of class="account-det" -->	
					 <div class="insidebtn"> 
						<input id="submitId" type="button" class="sim-button button5" value="Preview Final Invoice">
						<!-- <a class="sim-button button5" href="inside4.html"><span>Preview Final Invoice</a> -->
					</div>     			 	          	
        		</div>		
        		<!-- --------------------------------------- Add Dynamic customer block start ----------------------------------------- -->
        		<div id="dnyCustomerDiv" style="display:none">
        			<div class="account-det">
	        			<div class="card">
	        				<div class="det-row">  
					            <div class="det-row-col astrick">
					            	<div class="label-text">Customer Name</div>
					              	<input type="text" id="dnyCustName" maxlength="100" required="true" class="form-control"/>
								  	<span class="text-danger cust-error" id="dny-cust-name-req">This field is required.</span>
					            </div> 
					            
	                    			<div class="det-row-col">Customer Type<br>
					              <input type="radio" name="dnyCustType" value="Individual" id="Individual" checked="checked">
					              <label for="Individual">Not Registered Under GST</label><br>
					              <input type="radio" name="dnyCustType" value="Organization" id="Organization">
					              <label for="Organization">Registered Under GST</label>
					            </div> 
					         					
					            <div class="det-row-col">
					            	<div class="label-text">Mobile Number</div>
					              	<input type="text" id="dnyContactNo"  maxlength="10" class="form-control"/> 
								  	<span class="text-danger cust-error" id="dny-contact-no-req">This field is required and should be 10 digits.</span>
					            </div>	
				            </div>
				            
				            <div class="det-row" id="dnyShowDivIfRegistered" >			                 			        					
					            <div class="det-row-col-half astrick">
					            	<div class="label-text">GSTIN</div>
					              	<input type="text"  maxlength="15" id="dnyCustGstId" class="form-control"/>
								    <span class="text-danger cust-error" id="dny-custGstId-req">GSTIN should be in a proper format.</span>
					            </div>	
					                 			        					
					            <div class="det-row-col-half astrick">
					            	<div class="label-text">GSTIN State</div>
					              	<select id="dnyCustGstinState" name="dnyCustGstinState" class="form-control"></select>
								  	<span class="text-danger cust-error" id="dny-custGstId-req">GSTIN should be in a proper format.</span>
					            </div>	
				            </div>	
				            
	      			        <div class="det-row">      			        					
					            <div class="det-row-col">
					            	<div class="label-text">Email ID</div>
					              	<input type="text" maxlength="100" id="dnyCustEmail" class="form-control"/>
								  	<span class="text-danger cust-error" id="dny-cust-email-format">This field should be in correct format.</span>
					            </div>	
	           								     			        					
					            <div class="det-row-col astrick">
					            	<div class="label-text">Pin Code</div>
					              	<input type="text" name="dnyPinCode" required="true" id="dnyPinCode" maxlength="6" class="form-control"/> 	
								  	<span class="text-danger cust-error" id="dny-empty-message"></span>
									<span class="text-danger cust-error" id="dny-cust-zip-req">Pin Code is required and should be 6 digits.</span>
					            </div>	
				   				     			        					
					            <div class="det-row-col astrick">
					            	<div class="label-text">City</div>
					              	<input type="text" readonly="readonly" id="dnyCustCity" name="dnyCustCity" class="form-control" />  
					            </div>				            				
							</div>
							
	      			        <div class="det-row">  			
					            <div class="det-row-col astrick">
					            	<div class="label-text">State</div>
					              	<input type="text" readonly="readonly" id="dnyCustState" name="dnyCustState" class="form-control"/>  
								  	<span class="text-danger cust-error" id="dny-custState-err"></span>
					            </div>	
				            				        					
					            <div class="det-row-col astrick">
					            	<div class="label-text">Country</div>
					              	<input type="text" readonly="readonly" value="India" name="dnyCustCountry" required="true" id="dnyCustCountry" class="form-control"/>
								  	<span class="text-danger cust-error" id="dny-cust-email-format">This field should be in correct format.</span>
					            </div>	
				            		     			        					
					            <div class="det-row-col" id="dnyCustAddrDiv">
					            	<div class="label-text">Address</div>
					              	<textarea id="dnyCustAddress" maxlength="350" required="true" class="form-control"></textarea>  
								  	<span class="text-danger cust-error" id="dny-address-req">This field is required </span>
					            </div>				            				
							</div>        			
	        			</div>        			       
        			</div>	<!-- End of class="account-det" -->		
					 <div class="insidebtn"> 
						<input id="dnyCustSubmitBtn" type="button" class="sim-button button5" value="Save">
                        	<input id="dnyCustCancelBtn" type="button" class="sim-button button5" value="Cancel">
					</div>  
        		</div>
        		<!-- --------------------------------------- Add Dynamic customer block ends here ----------------------------------------- -->
        			
        		<!-- --------------------------------------- Add Dynamic Service/Product block start ----------------------------------------- -->
        		<div id="dnyServiceProductDiv" style="display:none">
        			<div id="dnyServiceFormDiv">
	        			<div class="account-det">
	        				<div class="card">
	        					<div class="det-row">  
						            <div class="det-row-col astrick">
						            	<div class="label-text">Search By SAC Code / SAC Description</div>
						              	<input type="text" id="dny-search-sac" maxlength="15" class="form-control"/>     
						              	<input type="hidden" id="sacCodePkId"/>
		                  				<span class="text-danger cust-error" id="dny-search-sac-req">Please Search By SAC Code / SAC Description</span>
						            </div> 
						            
		                    		<div class="det-row-col astrick">
		                    			<div class="label-text">SAC Description</div>
						            	<input type="text" disabled="disabled"  readonly="readonly" id="dny-sacDescriptionToShow" required class="form-control"/>
										<input type="hidden" id="dny-sacDescription" />
										<span class="text-danger cust-error" id="dny-ser-sac-desc">This field is required.</span>
						            </div> 
						         					
						            <div class="det-row-col astrick">
						            	<div class="label-text">SAC Code</div>
						              	<input type="text" disabled="disabled"  readonly="readonly" id="dny-sacCodeToShow" required class="form-control"/>
										<input type="hidden" id="dny-sacCode" />
									  	<span class="text-danger cust-error" id="dny-ser-sac-code">This field is required.</span>
						            </div>	
					            </div>
					            <div class="det-row">  
						            <div class="det-row-col astrick">
						            	<div class="label-text">Service Name</div>
						              	<input type="text" id="dny-service-name" required="true" maxlength="100" class="form-control" />
		                  				<span class="text-danger cust-error" id="dny-ser-name">This field is required.</span>
						            </div> 
						            
		                    		<div class="det-row-col astrick">
		                    			<div class="label-text">Unit Of Measurement</div>
						            	<select name="dny-unitOfMeasurement" class="form-control" id="dny-unitOfMeasurement" class="form-control" >
		                        		</select>
						            </div> 
						         		
		                    		<div class="det-row-col astrick" id="dny-divOtherUnitOfMeasurement">
		                    			<div class="label-text">Please Specify</div>
						            	<input type="text" maxlength="30" id="dny-otherUOM" class="form-control"/>
	                  					<input type="hidden" name="dny-tempUom" id="dny-tempUom">
	                  					<span class="text-danger cust-error" id="dny-otherOrgType-req">This field is required.</span>
						            </div> 
						         						
						            <div class="det-row-col astrick">
						            	<div class="label-text">Service Rate (Rs.)</div>
						              	<input type="text" id="dny-serviceRate" maxlength="18" required="true" class="form-control"/>
									  	<span class="text-danger cust-error" id="dny-ser-rate">This field is required.</span>
						            </div>	
					            </div>
					            <div class="det-row">					            				
						            <div class="det-row-col astrick">
						            	<div class="label-text">Rate of tax (%)</div>
						              	<select name="dny-serviceIgst" class="form-control" id="dny-serviceIgst" class="form-control" >
	                      				</select>
										<span class="text-danger cust-error" id="dny-service-igst">This field is required.</span>
						            </div>	
					            </div>
	        				</div>
	        			</div>      <!-- End of class="account-det" -->	  	
					    <div class="insidebtn"> 
							<input id="dnyServiceSubmitBtn" type="button" class="sim-button button5" value="Save">
	                       	<input id="dnyServiceCancelBtn" type="button" class="sim-button button5" value="Cancel">
						</div>  			       
        			</div> 
        			<div id="dnyProductFormDiv">
	        			<div class="account-det">
	        				<div class="card">
	        					<div class="det-row">  
						            <div class="det-row-col astrick">
						            	<div class="label-text">Search By HSN Code / HSN Description</div>
						              	<input type="text" id="dny-search-hsn" maxlength="15" class="form-control"/>     
						              	<input type="hidden" id="hsnCodePkId"/>
		                  				<span class="text-danger cust-error" id="dny-search-hsn-req">Please Search By HSN Code / HSN Description</span>
						            </div> 
						            
		                    		<div class="det-row-col astrick">
		                    			<div class="label-text">HSN Description</div>
						            	<input type="text" disabled="disabled" id="dny-hsnDescriptionToShow" required="true" class="form-control"/>
										<input type="hidden" id="dny-hsnDescription" />
										<span class="text-danger cust-error" id="dny-prod-hsn-desc">This field is required.</span>
						            </div> 
						         					
						            <div class="det-row-col astrick">
						            	<div class="label-text">HSN Code</div>
						              	<input type="text" disabled="disabled" id="dny-hsnCodeToShow" required="true" class="form-control"/>
										<input type="hidden" id="dny-hsnCode" />
									  	<span class="text-danger cust-error" id="dny-prod-hsn-code">This field is required.</span>
						            </div>	
					            </div>
					            <div class="det-row">  
						            <div class="det-row-col astrick">
						            	<div class="label-text">Goods Name</div>
						              	<input type="text" id="dny-product-name" maxlength="100" required="true" class="form-control"/>
		                  				<span class="text-danger cust-error" id="dny-prod-name">This field is required.</span>
						            </div> 
						            
		                    		<div class="det-row-col astrick">
		                    			<div class="label-text">Unit Of Measurement</div>
						            	<select class="form-control" name="dny-product-unitOfMeasurement" class="form-control" id="dny-product-unitOfMeasurement">
										</select>
						            </div> 
						         		
		                    		<div class="det-row-col astrick" id="dny-product-divOtherUnitOfMeasurement">
		                    			<div class="label-text">Please Specify</div>
						            	<input type="text"  id="dny-product-otherUOM" maxlength="30" class="form-control"/>
										<input type="hidden" name="dny-product-tempUom" id="dny-product-tempUom">
	                  					<span class="text-danger cust-error" id="dny-product-otherOrgType-req">This field is required.</span>
						            </div> 
						         						
						            <div class="det-row-col astrick">
						            	<div class="label-text">Goods Rate (Rate per unit of measurement)</div>
						              	<input type="text" id="dny-productRate" maxlength="18" required="true" class="form-control"/>
									  	<span class="text-danger cust-error" id="dny-prod-rate">This field is required and should be numeric.</span>
						            </div>	
					            </div>
					            <div class="det-row">					            				
						            <div class="det-row-col">
						            	<div class="label-text">Rate of tax (%)</div>
						              	<select class="form-control" id="dny-productIgst" name="dny-productIgst" class="form-control">
										</select> 
										<span class="text-danger cust-error" id="dny-prod-igst">This field is required and should be numeric.</span>
						            </div>	
					            </div>
	        				</div>
	        			</div>      <!-- End of class="account-det" -->	  	
					    <div class="insidebtn"> 
							<input id="dnyProductSubmitBtn" type="button" class="sim-button button5" value="Save">
	                       	<input id="dnyProductCancelBtn" type="button" class="sim-button button5" value="Cancel">
						</div>  			       
        			</div>     
        		</div>
        		<!-- --------------------------------------- Add Dynamic Service/Product block ends here ----------------------------------------- -->
        		
        		<div id="previewInvoiceDiv" style="display:none">
        			<div class="account-det">
						<!-- Latest Html provided - Start -->
						<div class="card">						
							<div class="logo-det">
								<div class="upload-logo"><img  alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-responsive" src="${pageContext.request.contextPath}/wGetOrgLogo" width="143" height="174"></div>
								<div class="upload-txt"  id="userDetailsInPreview">									
								</div>
							</div>
							
							<div class="tax-invoice" id="customerDetailsInPreview">							
							</div>
							
						    <div class="tablemain2">
								<table id="mytable2">
								</table>
							</div> 
						    <br/>
					 		<div class="tax-invoice" id="diffPercentShowHide" style="margin:0 10px;">
								<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
					        </div>
					        <hr>
					        <div class="tax-invoice" id="preview_customer_purchase_order">								
					        </div>
						    <br>						            
						    <div class="add-charges">
								<div class="receiver">
									Receiver Name:<br><br>
									<strong>Receiver Signature:</strong>
								</div>
								<div class="supplier">
									Supplier Name:<br><br>
									<strong>Authorized Signature:</strong>
								</div>
							</div>
							<br>
						    <hr>
						    
						    <div class="declarationInvoice">
						    	<p id="userAddressWithDetails"></p>
								<strong>Declaration</strong><br>
								I. Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional considerations directly or indirectly from the Buyer<br>
								II. The normal terms governing the above sale are printed overleaf E&OE
							 </div>
							 
							 <div class="invoice-txt">
							 	<div class="add-charges" id="footerNoteDiv">
								<!-- <h4>Footer Note</h4> -->                            
	                        	</div>
							 </div><br> 
						       
        				</div>	<!-- End of class="account-det" -->		
					    <div class="insidebtn"> 
							<input id="finalSubmitId" type="button" class="sim-button button5" value="Send Invoice">
							<input id="backToaddInvoiceDiv" type="button" class="sim-button button5" value="Edit">
							<!-- <a id="finalSubmitId" class="btn btn-primary btn-half" href="#!">Send Document</a>
					        <a id="backToaddInvoiceDiv" class="btn btn-primary btn-half" href="#!">Edit</a> -->
						</div>
					</div>
					<!-- Latest Html provided - End -->
				</div>
        		
        		 <!-- Show customer email addresss div - Start -->
                <div class="" id="customerEmailDiv" style="display:none;">
        			<div class="account-det">
					      <form class="form" role="form">
					        <div class="card">
								<div class="add-charges">
									Customer Email address is not present.<br/>Kindly Enter email address to send email. 
								</div>
								<div class="det-row">                        	
			                        <div class="det-row-col-full astrick">
		                    			<div class="label-text">Email Address</div>
						          		<input type="text" id="cust_email_addr" class="form-control" maxlength="100"  autocomplete="off">    
								  		 <span class="text-danger cust-error" id="cust-email-format">This field should be in a correct format</span>
		                   			</div>
				                </div>                    
		
		                        <div class="insidebtn">
		                        	<input id="custEmailSave" type="button" class="sim-button button5" value="Save">
		                        	<input id="custEmailCancel" type="button" class="sim-button button5" value="Cancel">
		                        </div>		
		                    </div>
					      </form>  
        			</div>	<!-- End of class="account-det" -->	
			    </div>
                <!-- End -->
        		
	</div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/form_submit.js"/>"></script>     
   
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/service_crud.js"/>"></script>   

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/add_charge_crud.js"/>"></script> 

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/service-product-client.js"/>"></script>       

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/otherFunctions.js"/>"></script> 

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/wizard_dnynamic-add-customer.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/wizard_dnynamic-add-customer-validations.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/wizard_dnynamic-add-services.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/wizard_dnynamic-add-products.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/pincode-autocomplete.js"/>"></script>