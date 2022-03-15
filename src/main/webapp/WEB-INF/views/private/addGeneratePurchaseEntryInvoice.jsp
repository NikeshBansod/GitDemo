<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/purchaseEntryinvoice.js"/>"></script>

<header class="insidehead" id="generateInvoiceDefaultPageHeader">
	
	<a href="<spring:url value="/home#doc_management"/>" class="btn-back"><i class="fa fa-angle-left"></i></a>
	<a class="logoText" href="#" id="generateInvoicePageHeader" style="display:none" ></a>
	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
	
</header>
<header class="insidehead" id="generateInvoicePreviewPageHeader" style="display:none">
	<a href="#" onclick="javascript:callInvoicePageOnBackButton()" class="btn-back"><i class="fa fa-angle-left"></i></a>
	<a class="logoText" href="#" id="generateInvoicePageHeaderPreview" style="display:none">Document Preview</a>
	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
</header>
<header class="insidehead" id="generateInvoiceCustomerEmailPageHeader" style="display:none">
	<a class="logoText" href="javascript:void(0)">Customer Email</a>
</header>
<main>
	<section class="block generateInvoice">
		<div class="container" id='loadingmessage' style='display:none;' align="center">
			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id="addInvoiceDiv">
			<!-- General Section Starts -->    
			<div class="card">
			<span><center><h4><b>Generate Purchase Entry</b></h4></center></span>
			
				<div class="form-group input-field mandatory" id="gstnStateIdDiv">
					<label class="label">
					       <select name="gstnStateId" class="form-control" id="gstnStateId"></select>
					       <div class="label-text label-text2">Select your GSTIN</div>
					</label>
				</div>
				
				<div class="form-group input-field mandatory" id="locationDiv" style="display:none">	
					<label class="label">
					       <select name="location" class="form-control" id="location"></select>
					       <div class="label-text label-text2">Select your Location/Store</div>
					</label>
					<input type="hidden" name="locationStore" id="locationStore"> 
				</div>
				   
                <div class="radio-inline mandatory" style="margin-top:10px">
                     <div class="label-text4">Purchase Of</div>
                     <div class="rdio rdio-success">
                         <input type="radio" name="invoiceFor" value="Service" id="radio2" checked="checked">
                         <label for="radio2">Services</label>
                     </div>
                     <div class="rdio rdio-success">                       
                         <input type="radio" name="invoiceFor" value="Product" id="radio1" >
                         <label for="radio1">Goods</label>
                     </div>
               </div>
               <br>
				<div class="radio-inline mandatory" style="margin-top:10px">
					<div class="label-text4">Purchase From</div>
					 <div class="rdio rdio-success">
						<input type="radio" name="custType" value="UnRegistered" id="UnRegistered" checked="checked" >
						<label for="UnRegistered">Unregistered Dealer</label>
					</div>
					 <div class="rdio rdio-success">
						<input type="radio" name="custType" value="Registered" id="Registered" >
						<label for="Registered">Registered Dealer</label>
					</div>
				</div>
         
                <div id="consignee" >    <!--if REGISTERED DEALER id="RegisteredDealer" style="display:none" -->
                		
				</div>	
					<div class="form-group input-field ">	
						<label class="label">
						       <input type="text" name="supplier_gstin" id="supplier_gstin" maxlength="15" required class="form-control">  
						       <div class="label-text label-text2">GSTIN of the Supplier</div>
                           	   <input type="hidden" name="supplierGstnNumber" id="supplierGstnNumber">	
						</label>
					</div>
								
					<div class="form-group input-field mandatory">
	                    <label class="label">
	                        <input type="text" name="supplier_name" id="supplier_name" maxlength="50" required class="form-control">  
	                        <div class="label-text label-text2">Name of the Supplier</div>
	                    </label>
	                     <span class="text-danger cust-error" id="empty-message"></span>
                	</div>                
					<div class="form-group input-field mandatory"  id="shipTo-pincode-show-hide">
						<label class="label">
						    <input type="text" name="supplier_pincode" id="supplier_pincode" required class="form-control" maxlength="6">  
						    <div class="label-text label-text2">Pincode of Supplier</div>
						</label>
	                    <span class="text-danger cust-error" id="empty-message-1"></span>
					</div>                
					<div class="form-group input-field mandatory" id="shipTo-city-show-hide">
						<label class="label">
						    <input type="text" name="supplier_state" id="supplier_state" readonly="readonly" required class="form-control">  
						    <div class="label-text label-text2">State of Supplier</div>
			             	<span class="text-danger cust-error" id="supplierState-req"></span> 
						</label>	
						<input type="hidden" name="shipTo_city" id="shipTo_city">  
						 <input type="hidden" name="shipTo_stateCode" id="supplier_stateCode">
                         <input type="hidden" name="shipTo_stateCodeId" id="supplier_stateCodeId">			    
					</div>					
					<div class="form-group input-field ">
					    <label class="label">
					        <input type="text" name="supplier_address" id="supplier_address" maxlength="500" required class="form-control"> 					        
					        <div class="label-text label-text2">Address of Supplier</div>
					    </label>
					</div>
                
				<!--start of hidden values -->
				
				<input type="hidden" name="customerDetails.id" id="supplier_id"><!-- mandatory -->
				<input type="hidden" name="customerDetails.contactNo" id="customer_contactNo" maxlength="10"> 
				
				<!--end of hidden values -->
				<div class="form-group input-field mandatory">
	                <label class="label">		
	                    <input type="text" id="purchase_date" required class="form-control">  <!-- id="invoice_date" -->
	                    <div class="label-text label-text2">Purchase Date</div>
	                </label>
                </div>
                
				<div class="form-group input-field mandatory">
					<label class="label">
					       <input type="text" name="invoice_number" class="form-control" id="invoice_number" maxlength="16" >
					       <div class="label-text label-text2">Invoice Number</div>
					</label>
				</div>
				
				<div class="form-group input-field " id="invoicePeriodDivShowHide">
					<label class="label">
						<div class="input-group">
							<input type="text" class="form-control" id="invoicePeriodFromDateInString" />
							<span class="input-group-addon">TO</span>
							<input type="text" class="form-control" id="invoicePeriodToDateInString" />
						</div>
						<div class="label-text label-text2">Invoice Period</div>
					</label> 
				<br/>
				</div>
                <div class="form-group input-field mandatory">
                    <label class="label">
                         <select name="placeOfSupply" class="form-control" id="placeOfSupply">
             			 </select>
                         <div class="label-text label-text2">Place Of Supply</div>
                         <input type="hidden" name="placeOfSupply_Name" id="placeOfSupply_Name">
						 <input type="hidden" name="placeOfSupply_Code" id="placeOfSupply_Code">
                         <input type="hidden" name="placeOfSupply_Id" id="placeOfSupply_Id">
                    </label>
                 </div>     
				<div class="form-group checkbox-inline mandatory" style="margin-top:10px">
					<span></span> 
					<div class="checkbox checkbox-success label-text">
						<input type="checkbox" name="reverseCharge" id="reverseCharge" >
						<label for="reverseCharge">Reverse Charge Applicable</label>	 <!-- id="reverseCharge-label" -->
						<input type="text" id="reverseChargeYesNo" style="display:none">
					</div>
				</div>
                
                <div id="SupplierDoc"  style="display:none" >
					<div class="form-group input-field mandatory">
						<label class="label">
						       <input type="text" name="supplierDocInvoiceNo" id="supplierDocInvoiceNo" class="form-control"> 
						       <div class="label-text label-text2">Supplier Document/Invoice No</div>
						</label>
					</div>
					<div class="form-group input-field mandatory">
						<label class="label">
						       <input type="text" class="form-control" id="supplierDocInvoiceDate"  class="form-control"/>
						       <div class="label-text label-text2">Supplier Document/Invoice Date</div>
						</label>
					</div>
                </div>          
									
				<div class="form-group input-field mandatory">
				    <label class="label">
				        <input type="text" name="billing_address" id="billing_address" maxlength="500" required class="form-control" value="${userDetails.organizationMaster.address1}" > 					        
				        <div class="label-text label-text2">Billing Address</div>
				    </label>
				</div>

                <div class="form-group checkbox-inline mandatory" style="margin-top:10px">
                     <span></span> 
                    <div class="checkbox checkbox-success label-text">
                        <input type="checkbox" name="shipToBill" id="shipToBill" >
                        <label for="shipToBill">Shipping Address is same as Billing Address</label>
                    </div>
                </div>
                   					
				<div class="form-group input-field mandatory">
				    <label class="label">
				        <input type="text" name="shipping_address" id="shipping_address" required class="form-control"> 					        
				        <div class="label-text label-text2">Shipping Address</div>
				    </label>
				</div>
                     
                <div class="form-group input-field ">
                   <label class="label">
                      <input type="text" name="poDetails.poNo" id="poDetails_poNo" class="form-control">
                       <div class="label-text label-text2">PO/WO Reference Number</div>
                   </label>
               </div> 
               
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
			</div>
			
			<div class="accordion no-css-transition mb0">
				       <!-- Service Section Starts -->       
					<div class="accordion_in">
					    <div id="callOnEditId" class="acc_head"></div>
					    <div class="acc_content">					        
					        <div class="form-group input-field mandatory">
					            <label class="label">
					                <select id="service_name" name="" class="form-control">
					            
					                </select> 
					                <div id="service_name_label" class="label-text"></div>
					            </label>
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
							
							<div class="form-group input-field">
							    <label class="label">
							        <input id="offerAmount" type="text" maxlength="18" class="form-control">  
							        <div class="label-text label-text2">Discount</div>
							    </label>
							    <span class="text-danger cust-error" id="offerAmount-csv-id"></span>
							</div>
					                        
							<input type="hidden" id="unqValId" name="" value="">
							<div class="btns btns-2 clearfix">
							    <button id="service_add" type="button" class="btn btn-primary" value="Add">Add</button>
							    <button id="service_edit" style="display:none" class="btn btn-primary">Save</button>
								<button id="service_cancel" onclick="cancel_service_row()" class="btn btn-default">Cancel</button>
							</div>
					
					        <div class="cust-wrap">
					            <div id="toggle"></div>
					        </div>					
					    </div>
					</div>					
					<!-- Service Section Ends -->  
					
					<!-- Discount Section Starts --> 
					<!-- <div class="accordion_in" style="display:none">
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
					</div> -->
					<!-- Discount Section Ends -->    
					

					<!-- <div class="acc_content"  style="display:none">
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
					</div>	 -->			
		<!--  -->					
					<div class="accordion_in">
					    <div id="callOnAddChgEditId" class="acc_head">Additional Charges</div>
					    <div class="acc_content">					     
					        <div class="form-group input-field">
					            <label class="label">
					                <select name="additionalChargeName" class="form-control" id="additionalChargeName"></select>  
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
					            <button id="add_chg_edit" style="display:none" class="btn btn-primary">Edit</button>
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
					                <input type="text" id="footerNote" value="${loginUser.footer }" required class="form-control" maxlength="150" placeholder="Footer Note &#40; max 150 characters &#41;">
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
                    
	           <%--  <input type="hidden" name="user_org_name" id="user_org_name" value="${userDetails.organizationMaster.orgName }">
				<input type="hidden" name="user_org_address1" id="user_org_address1" value="${userDetails.organizationMaster.address1 }">
				<input type="hidden" name="user_org_city" id="user_org_city" value="${userDetails.organizationMaster.city }">
				<input type="hidden" name="user_org_pinCode" id="user_org_pinCode" value="${userDetails.organizationMaster.pinCode }">
				<input type="hidden" name="user_org_panNumber" id="user_org_panNumber" value="${userDetails.organizationMaster.panNumber }">  --%>
				
        		<input type="hidden" name="purchaser_org_name" id="purchaser_org_name" value="${userDetails.organizationMaster.orgName }">
				<input type="hidden" name="purchaser_org_address1" id="purchaser_org_address1" value="${userDetails.organizationMaster.address1 }">
				<input type="hidden" name="purchaser_org_city" id="purchaser_org_city" value="${userDetails.organizationMaster.city }">
				<input type="hidden" name="purchaser_org_pinCode" id="purchaser_org_pinCode" value="${userDetails.organizationMaster.pinCode }">
				<input type="hidden" name="purchaser_org_state" id="purchaser_org_state" value="${userDetails.organizationMaster.state }"> 
				<input type="hidden" name="purchaser_org_stateCode" id="purchaser_org_stateCode" value="${userDetails.organizationMaster.stateCode }"> 
			<div class="btns">
				<!--  <a class="btn btn-default btn-block" href="">Preview Final Invoice</a> -->
				<button id="submitId" class="btn btn-success btn-block" value="">Preview Final Document</button>
				<!-- <button id="directId" class="btn btn-success btn-block" value="">Direct Hit</button>   -->
				<!-- <button id="dummySubmId" class="btn btn-success btn-block" value="">Dummy Trial</button> -->
			</div>
			
			
		</div>		
		 <!-- ----------------------------------------------------------------- -->
		
                <div class="container" id="previewInvoiceDiv" style="display:none">
					<!-- Latest Html provided - Start -->
					<div class="card">
					   <div class="invoicePage">
					       <div class="invoiceDetail">
					       		<%-- <img alt="No Logo Uploaded" src="${pageContext.request.contextPath}/getOrgLogo"> --%>
								<div class="invoiceInfo" id="userDetailsInPreview">
							
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
					 		 
					          <div class="row" id="preview_customer_purchase_order">        </div>
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
								<div class="invoiceInfo text-justify" id="">
									<!-- <h4>Footer Note</h4> -->
	                                <p><span id="footerNoteDiv"></span></p>
	                            </div>
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
					    </div>					
					    <div class="btns">
					       <!--  <a class="btn btn-primary btn-threefourth" href="#!">E-Sign Invoice</a> -->
					        <a id="finalSubmitId" class="btn btn-primary btn-half" href="#!">Save & Close</a>
					        <a id="backToaddInvoiceDiv" class="btn btn-primary btn-half" href="#!">Edit</a>
					    </div>
					</div>					
					
					
                	<!-- Latest Html provided - End -->
                </div>
		
			<!-- ------------------------------------------------------------------- -->
			 <!-- Show customer email addresss div - Start -->
                <!-- <div class="container" id="customerEmailDiv" style="display:none;">
			      <form class="form" role="form">
			        <div class="card">

						<div class="row">
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
			    </div> -->
                <!-- End -->
	</section>
</main>

<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/pincode-autocomplete-purchaseentry.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/service-product-add.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/purchase_service_crud.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/add_charge_crud.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/form_submit.js"/>"></script>    


