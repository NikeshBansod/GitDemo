<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
  
<section class="block">
	<div class="loader"></div> 
	<div class="container" style="display:none">
	  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  <input type="hidden" id="unqIncDes" name="unqIncDes" value="${refToFind}" />
	  <input type="hidden" id="backRRType" name="backRRType" value="${backRRType}" />
	  <input type="hidden" id="invNo" name="invNo" />
	  <input type="hidden" id="invDate" name="invDate" />
	  <input type="hidden" id="invDocType" name="invDocType" />
	  <input type="hidden" id="invDataFiledToGstn" name="invDataFiledToGstn" />
	  <input type="hidden" id="modeOfCreation" value="EDIT_INVOICE">
	  <input type="hidden" id="firmName" value="${sessionScope.loginUser.firmName}" />
	  <input type="hidden" id="panNumber" value="${sessionScope.loginUser.panNo}" />
	  <input type="hidden" id="iterationNo" />
	  <input type="hidden" id="invoiceSequenceType" value="${sessionScope.loginUser.invoiceSequenceType}">
	  
	  <input type="hidden" id="invoiceFor" name="invoiceFor"/>
	  <input type="hidden" id="gstnStateId" name=""/>
	  <input type="hidden" id="gstnStateIdInString" name=""/>
	  <input type="hidden" id="location" name=""/>
	  <input type="hidden" id="documentType" name=""/>
	  <input type="hidden" id="locationStorePkId" name=""/>
	  <input type="hidden" id="calculation_on" value="Amount"/>
	  
	 
	  </div>
	  <div class="container" id="itemFirstPage">
		 <div class="brd-wrap">
			 <div id="generateInvoiceDefaultPageHeader">
	            
	 		 </div>
		 </div>
		 <div class="form-wrap footernote" style="display:none" id="invoiceNumberDiv">
              <div class="row">
                 <div class="col-md-4"  >
	                <label for="invoiceNumber">Invoice Number <span>*</span></label>
	                <input type="text" name="invoiceNumber" id="invoiceNumber" maxlength="20" required>
	                <span class="text-danger cust-error" id="invoiceNumber-csv-id"></span>
	                <span class="text-danger cust-error" id="invoiceNumber-duplicate-csv-id"></span>
	             </div> 
              </div>
         </div>
		 <div class="row" id="listTable">
		 	<div class="table-wrap">
	            <table id="itemChangeTable" class="display nowrap" style="width:100%">
		            <thead>
		                <tr>
		                    <th style="width:20px;">Sr.No.</th>
		                    <th id="tableField1">Goods</th>
		                    <th id="tableField2">Delete</th>
		                </tr>
		            </thead>
		            <tbody>
		            </tbody>
	             </table>
	        </div>
		 
		 </div>
		 <br/>
		 <!-- dynamic accordion starts here  -->
		 <div class="form-accrod row">
	    	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	    	  <!-- Service Section Starts --> 
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
	    	
	    	
			</div>
		</div>
	     <!-- dynamic accordion ends here  -->
	      <div class="row">
            <div class="col-md-12 button-wrap">
                <button type="button" id="submitId" style="width:auto;" class="btn btn-success blue-but">Preview Final Document</button>
                <button type="button" id="backToRRPage" onclick="backToRRPage()" class="btn btn-success blue-but" style="width:auto;">GO BACK</button>
            </div>
        </div>
	 
	</div>
	<div class="invoice-wrap" id="itemSecondPage" style="display:none">
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
	                            <div class="panel-body" id="userDetailsInInvoicePreview">
	                                
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDivInInvoicePreview">
	                               
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Ship To</div>
	                            <div class="panel-body" id="customerDetailsShipToDivInInvoicePreview">
	                                
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
	                        <h3 class="text-center" id="mytable2HeaderInInvoicePreview">Purchase Document</h3>
	                    </div>
	                    <div class="table-responsive">
	                    	<table class="table table-condensed" id="mytable2InInvoicePreview">
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
	                <button type="button" id="finalRevisedSubmitId" class="btn btn-success blue-but" style="width:auto;">Create Revised Invoice</button>
	                <button type="button" id="backToFirstPage" class="btn btn-success blue-but" style="width:auto;">Edit</button>
	            </div>
			 
			 </div>
			<!-- End of row 3 -->
			
	    </div>
	</div>

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
					  	<input type="text" name="dny-product-purchaseUOM" id="dny-product-purchaseUOM" disabled="disabled">
					    <span class="text-danger cust-error" id="dny-purchase-uom-req">This field is required.</span>
					</div>
					
					 <div class="col-md-4" id="dny-product-divPurchaseOtherUOM">
	                 	<label for="label">Please Specify<span> *</span></label>
						<input type="text"  id="dny-product-purchaseOtherUOM" maxlength="30"  />
						<input type="hidden" name="dny-product-tempPurchaseOtherUOM" id="dny-product-tempPurchaseOtherUOM" disabled="disabled">
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


</section>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/generalItemChange.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/addProductService.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/revise.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/service_crud.js"/>"></script>   
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/add_charge_crud.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/service-product-client.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/dnynamic-add-services.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/dnynamic-add-products.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/itemChange/pincode-autocomplete.js"/>"></script>


 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>