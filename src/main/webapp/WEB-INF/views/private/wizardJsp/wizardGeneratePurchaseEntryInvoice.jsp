<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardPurchaseEntryinvoice.js"/>"></script>


<section class="insidepages">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="container" id='loadingmessage' style='display:none;'  align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
           
	<div class="inside-cont">
			<div class="breadcrumbs">
				 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
		         	<%-- <a href="<spring:url value="/wHome#doc_management"/>">Home</a> <span>&raquo;</span> Generate Purchase Entry --%>
		         	<a href="<spring:url value="/wHome#doc_management"/>"> Home</a> <span> &raquo; </span><a href="./wgetmypurchaseentrypage"> Purchase Entry History </a></span> <span> &raquo; </span> Generate Purchase Entry
		 		 </header>
		 		 <header class="insidehead" id="generateInvoicePreviewPageHeader" style="display:none">
		            <p>Purchase Entry Preview</p>
				 </header>
				 <header class="insidehead" id="generateInvoiceCustomerEmailPageHeader" style="display:none">
            		<p  href="javascript:void(0)">Customer Email</p>
 				 </header>
			</div>
        	<div class="account-det">
        		<div class="" id="addInvoiceDiv">
    				<div class="card">
        			 	<div class="det-row">   
	                        <div class="det-row-col astrick">  <!-- id="gstnStateIdDiv" -->
				            	<div class="label-text">GSTIN</div>
				              	<select name="gstnStateId" class="form-control" id="gstnStateId"></select>
				            </div>
				            
				            <div class="det-row-col astrick" id="locationDiv" >	<!-- style="display:none" -->
				            	<div class="label-text">Location/Store</div>
				              	<select name="location" class="form-control" id="location"></select>
	                       		<input type="hidden" name="locationStore" id="locationStore">
				            </div>	
				            			                	
	               			<div class="det-row-col ">  
	               				
				            	<div class="label-text">Purchase Of<br>
					            	<input type="radio" name="invoiceFor" value="Product" id="radio1" checked="checked" >
					                <label for="goods"> Goods&nbsp;&nbsp;&nbsp;</label>
					                <input type="radio" name="invoiceFor" value="Service" id="radio2">
					                <label for="services">Services</label>
								</div>
				            </div>				          		      					
						</div>
						
		                <div class="det-row"> 
	               			<div class="det-row-col"> 
	               				
				            	<div class="label-text">Purchase From<br>
					                <input type="radio" name="custType" value="UnRegistered" id="UnRegistered" checked="checked" >
									<label for="UnRegistered">Unregistered Dealer&nbsp;&nbsp;&nbsp;</label>
					            	<input type="radio" name="custType" value="Registered" id="Registered">
					                <label for="Registered">Registered Dealer</label>
								</div>
				            </div>
				            
							<div class="det-row-col">
					    		<div class="label-text">GSTIN of the Supplier</div>
					    		<input type="text" name="supplier_gstin" id="supplier_gstin" maxlength="15" required class="form-control">  
		                        <input type="hidden" name="supplierGstnNumber" id="supplierGstnNumber">	
					    	</div>
					    	
					    	<div class="det-row-col astrick">
					    		<div class="label-text">Name of the Supplier</div>
					    		<input type="text" name="supplier_name" id="supplier_name" maxlength="50" required class="form-control">  
			                    <span class="text-danger cust-error" id="empty-message"></span>
					    	</div>
				                 
			          	</div>
			          	
			          	<div class="det-row">		                	      
					    	<div class="det-row-col astrick" id="shipTo-pincode-show-hide" >
					    		<div class="label-text">Pincode of Supplier</div>
					    		<input type="text" name="supplier_pincode" id="supplier_pincode" required class="form-control" maxlength="6">
	                    		<span class="text-danger cust-error" id="empty-message-1"></span>
					    	</div>		
					    	
							<div class="det-row-col astrick" id="shipTo-city-show-hide">
					    		<div class="label-text">State of Supplier</div>
					    		<input type="text" name="supplier_state" id="supplier_state" readonly="readonly" required class="form-control">  
			             		<span class="text-danger cust-error" id="supplierState-req"></span> 	
								<input type="hidden" name="shipTo_city" id="shipTo_city">  
						 		<input type="hidden" name="supplier_stateCode" id="supplier_stateCode">
                         		<input type="hidden" name="supplier_stateCodeId" id="supplier_stateCodeId">	
					    	</div>
					    	
					    	<div class="det-row-col ">
					    		<div class="label-text">Address of Supplier</div>
					    		<input type="text" name="supplier_address" id="supplier_address" maxlength="500" required class="form-control">
					    	</div>	
		                	<!--start of hidden values -->     
								<input type="hidden" name="customerDetails.id" id="supplier_id"><!-- mandatory -->
								<input type="hidden" name="customerDetails.contactNo" id="customer_contactNo" maxlength="10"> 
				 			<!--end of hidden values -->
				 						    	
			          	</div>
			          	
			          	<div class="det-row">
					    	
					    	<div class="det-row-col astrick">
					            <div class="label-text">Purchase Date</div>
					            <input type="text" name="purchase_date" id="purchase_date" required class="form-control">
					        </div>		
					        	
				            <div class="det-row-col astrick">
				            	<div class="label-text">Invoice Number</div>
					          	<input type="text" name="invoice_number" class="form-control" id="invoice_number" maxlength="16" >
				            </div>
				            
				            <div class="det-row-col astrick">
	                            <div class="label-text">Place Of Supply</div>
								<select name="placeOfSupply" class="form-control" id="placeOfSupply">
		             			</select>
		                        <input type="hidden" name="placeOfSupply_Name" id="placeOfSupply_Name">  
								<input type="hidden" name="placeOfSupply_Code" id="placeOfSupply_Code">
		                        <input type="hidden" name="placeOfSupply_Id" id="placeOfSupply_Id">
				            </div>				    	
			          	</div>
			          	

			          	<div class="det-row" id="invoicePeriodDivShowHide">
				            <div class="det-row-col-full astrick">Invoice Period </div>
				            <div class="det-row-col-doc_date">
				               	<input type="text" class="form-control" id="invoicePeriodFromDateInString" />	
				            </div>
				            <div class="det-row-col-label">TO</div>
				           	<div class="det-row-col-doc_date">		            
				             	<input type="text" class="form-control" id="invoicePeriodToDateInString" />
				            </div>
			          	</div> 
			          	
			          	<div class="det-row">
							<div class="det-row-col astrick">
					    		<div class="label-text">Billing Address</div>
					    		<input type="text" name="billing_address" id="billing_address" maxlength="500" required class="form-control" value="${userDetails.organizationMaster.address1}">  
					    	</div>
					    	
					    	<div class="det-row-col astrick">
					    		<div >&nbsp;</div>
				            	<input type="checkbox" name="shipToBill" id="shipToBill">
	                            <label for="shipToBill">Make Shipping Address same as Billing Address</label>	
				            </div>	
				            	                	      
					    	<div class="det-row-col astrick" id="shipTo-pincode-show-hide" >
					    		<div class="label-text">Shipping Address</div>
					    		<input type="text" name="shipping_address" id="shipping_address" required class="form-control">
					    	</div>					    	
			          	</div>
		         
			          	<div class="det-row">
							<div class="det-row-col ">
					    		<div class="label-text">PO/WO Reference Number</div>
					    		<input type="text" name="poDetails.poNo" id="poDetails_poNo" class="form-control">  
					    	</div>
					    	 
					    	<div class="det-row-col astrick">
					    		<div class="label-text">Bill Method</div>
					    		<select id="calculation_on" name="calculation_on" class="form-control">
		                          	<option value="">Select</option>
		                          	<option value="Amount">Quantity Based</option>
		                          	<option value="Lumpsum">Lumpsum</option>
		                         </select> 
					    	</div>		
					    					            
				            <div class="det-row-col astrick">
				            	<div >&nbsp;</div>
				            	<input type="checkbox" name="reverseCharge" id="reverseCharge">	
				            	<label for="reverseCharge" id="reverseCharge-label" >Reverse Charge Applicable</label>
	                        	<input type="text" id="reverseChargeYesNo" style="display:none">
				            </div>				            					    	
			          	</div>
			          	
			          	<div class="det-row" id="SupplierDoc" style="display:none">
			          		<div class="det-row-col astrick" >
					    		<div class="label-text">Supplier Document/Invoice No</div>
					    		<input type="text" name="supplierDocInvoiceNo" id="supplierDocInvoiceNo" class="form-control">
					    	</div>
					    	
			          		<div class="det-row-col astrick" >
					    		<div class="label-text">Supplier Document/Invoice Date</div>
					    		<input type="text" class="form-control" id="supplierDocInvoiceDate"  class="form-control"/>
					    	</div>
			          	</div>
			          	
			          	<div class="det-row">	    	
			          	</div>
			          		
        			 </div> 
        			 
        			<div class="accordion no-css-transition mb0">
        			 	<!-- Service Section Starts -->
        			 	<div class="accordion_in">
                            <div id="callOnEditId" class="acc_head"></div>                           
                            <div class="acc_content" style="background:none">  
	                            <div class="det-row">                          	
						            <div class="det-row-col astrick" >
						            	<div id="service_name_label" class="label-text"></div>
						              	<select id="service_name" name="" class="form-control">
	                                    </select>
						            </div>
		        					
	                                <div class="det-row-col" style="display:none" id="uom-show-hide">	
						            	<div class="label-text">Unit Of Measurement</div>
						              	<input id="uomtoShow" disabled="disabled" type="text" class="form-control"> 
	                                    <input id="uom" type="hidden" name=""> 
	                                    <input id="rate" type="hidden">
						            </div>	
	                                
	                                <div class="det-row-col astrick" style="display:none" id="quantity-show-hide">
						            	<div class="label-text">Quantity</div>
						              	<input id="quantity" type="text" maxlength="12" class="form-control"> 
						            </div>	
	                                
	                                <div class="det-row-col" style="display:none" id="amount-show-hide">
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
	                                              
				               <!-- for diff percentage 
				               		<div class="det-row-col">
				                		<div class="label-text">&nbsp;&nbsp;&nbsp;
											<div class="label-text">Discount In&nbsp;&nbsp;&nbsp;
												<input type="radio" name="itemDiscountType" value="Percentage" id="radio23" checked="checked">
												<label for="radio23"> Percentage</label>
												<input type="radio" name="itemDiscountType" value="Value" id="radio13" >
												<label for="radio13">Value</label>
											</div>
										</div>	
						            </div>  --> 
						            <div class="det-row-col">
				                		<div class="label-text">Discount</div>
										<input id="offerAmount" type="text" maxlength="18" class="form-control">											
							   			<span class="text-danger cust-error" id="offerAmount-csv-id"></span>
						            </div>  
	                                <input type="hidden" id="unqValId" name="" value="">
	                               </div>
	                               <!-- <div class="det-row">								                                				           
		                                <div class="det-row-col">
							            	<div class="label-text">Discount % or Value</div>
							              	<input id="offerAmount" type="text" maxlength="15" class="form-control">  
		                                    <span class="text-danger cust-error" id="offerAmount-csv-id"></span>
							            </div>
		                                <div class="det-row-col-full" style="margin-top:10px;display:none;" id="diffPercentageDiv">	
							              	<input type="checkbox" name="diffPercent" value="" id="diffPercent">
							            	<label>Eligible to be taxed at a differential percentage of the existing tax-rate as notified by government.</label>
							            </div>     
	                               </div> -->
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
	                 	
	    				<div class="accordion_in">
                            <div id="callOnAddChgEditId" class="acc_head">Additional Charges</div>
                            <div class="acc_content" style="background:none">
	                           <div class="det-row">
	                           		<div class="det-row-col-half" >
						            	<div class="label-text">Name</div>
						              	<select name="additionalChargeName" class="form-control" id="additionalChargeName"></select>  
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
                 	
                 	 <input type="hidden"  name="sgstTotalTax" id="sgstTotalTax"> 
	                 <input type="hidden"  name="cgstTotalTax" id="cgstTotalTax"> 
	                 <input type="hidden"  name="ugstTotalTax" id="ugstTotalTax"> 
	                 <input type="hidden"  name="igstTotalTax" id="igstTotalTax"> 
	                 <input type="hidden"  name="cessTotalTax" id="cessTotalTax"> 
	                 <input type="hidden"  name="totalAmount" id="totalAmount">
					 <input type="hidden" name="purchaser_org_name" id="purchaser_org_name" value="${userDetails.organizationMaster.orgName }">
					 <input type="hidden" name="purchaser_org_address1" id="purchaser_org_address1" value="">		<%-- ${userDetails.organizationMaster.address1 } --%>
					 <input type="hidden" name="purchaser_org_city" id="purchaser_org_city" value="">	<!-- ${userDetails.organizationMaster.city } -->
					 <input type="hidden" name="purchaser_org_pinCode" id="purchaser_org_pinCode" value="">	<!-- ${userDetails.organizationMaster.pinCode } -->
					 <input type="hidden" name="purchaser_org_state" id="purchaser_org_state" value="">	<!-- ${userDetails.organizationMaster.state } -->
					 <input type="hidden" name="purchaser_org_stateCode" id="purchaser_org_stateCode" value="">	<!-- ${userDetails.organizationMaster.stateCode } --> 
					
					 <div class="insidebtn"> 
						<input id="submitId" type="button" class="sim-button button5" value="Preview Final Invoice">
					</div>     			 	          	
        		</div>		
        		
        		
          <!-- ------------------------------------------------------------------- -->		
        		<div class="" id="previewInvoiceDiv" style="display:none">
					<!-- Latest Html provided - Start -->
					<div class="card">					
						<div class="logo-det">
							<%-- <div class="upload-logo"><img  alt="No Logo Uploaded" src="${pageContext.request.contextPath}/wGetOrgLogo" width="143" height="174"></div> --%>
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
				 		<!-- <div class="tax-invoice" id="diffPercentShowHide" style="margin:0 10px;">
							<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
				        </div>
				        <hr> -->
				        <div class="tax-invoice" id="preview_customer_purchase_order">
							
				        </div>
					    <br>
					            
					    <div class="add-charges">
							<div class="receiver">
								Receiver Name : <br><br>
								<strong>Receiver Signature:</strong>
							</div>
							<div class="supplier">
								Supplier Name : <br><br>
								<strong>Authorized Signature:</strong>
							</div>
						</div>
						<br>
					    <hr>
					    
					    <div class="declarationInvoice">
					    	<p id="userAddressWithDetails"></p>
							<strong>Declaration</strong><br>
							I.  Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.<br>
							II. The normal terms governing the above sale are printed overleaf E&OE. 
						 </div>
						 
						 <div class="invoice-txt">
						 	<div class="add-charges" id="footerNoteDiv">
							<!-- <h4>Footer Note</h4> -->                            
                        	</div>
						 </div><br>
					     <div class="insidebtn"> 
							<input id="finalSubmitId" type="button" class="sim-button button5" value="Save & Close">
							<input id="backToaddInvoiceDiv" type="button" class="sim-button button5" value="Edit">
						</div>
					</div>
					<!-- Latest Html provided - End -->
				</div>
        <!-- ------------------------------------------------------------------- -->
        <!-- Show customer email addresss div - Start -->
        <!--   <div class="container" id="customerEmailDiv" style="display:none;">
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
			    </div> -->
                <!-- End -->
        		       
        	</div>	<!-- End of class="account-det" -->		
	</div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardPincodeAutocompletePurchaseEntry.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardServiceProductAdd.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardPurchaseServiceCrud.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardAddChargeCrud.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardForm_submit.js"/>"></script> 
 