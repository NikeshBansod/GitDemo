<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

 
<section class="block">	
	<div class="container">
		<div class="loader"></div>
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />	
		<input type="hidden" id="inventoryType" name=inventoryType value="${inventoryType}" />	
		<input type="hidden" id="inventoryTypePage" name=inventoryTypePage value="${inventoryTypePage}" />
		<input type="hidden" id="documentType" value="Inventory_All">	

		<%-- <div class="brd-wrap">
				<a href="<spring:url value="/home#inventory_management"/>"><strong >Inventory</strong></a> &raquo; <strong>${inventoryTypePage} Inventory</strong>
		</div> --%>
		<%-- <div class="brd-wrap" id="increaseInventoryDiv">
				<a href="<spring:url value="/increaseinventoryhistory"/>"><strong >Inventory History </strong></a> &raquo; <strong>${inventoryTypePage} Inventory</strong>
		</div>
		<div class="brd-wrap" id="decreaseInventoryDiv">
				<a href="<spring:url value="/decreaseinventoryhistory"/>"><strong >Inventory History </strong></a> &raquo; <strong>${inventoryTypePage} Inventory</strong>
		</div> --%>

		<div class="page-title" id="increaseInventoryDiv">
                        <a href="<spring:url value="/increaseinventoryhistory"/>" class="back"><i class="fa fa-chevron-left"></i></a>${inventoryTypePage} Inventory
                    </div> 
                    
                    <div class="page-title" id="decreaseInventoryDiv">
                        <a href="<spring:url value="/decreaseinventoryhistory"/>" class="back"><i class="fa fa-chevron-left"></i></a>${inventoryTypePage} Inventory
                    </div>
		<div class="form-wrap">
			<div class="row">
		  		<div class="col-md-3">
					<label for="">GSTIN <span>*</span></label>
				
		  			<select name="gstin" id="gstin"></select>
		  			<span class="text-danger cust-error" id="gstin-csv-id">This field is required.</span>
		  		</div>	  		
		  		<div class="col-md-3">
		  		     <label for="">Location/Store <span>*</span></label>
		  		     <select name="location" id="location"></select>
		  		     <input type="hidden" id="locationStore" name="locationStore">
		  		     <input type="hidden" name="locationId" id="locationId">
		             <span class="text-danger cust-error" id="location-csv-id">This field is required.</span>
		  		</div>  	  
		        <div class="col-md-3">
		             <label for="">Reason <span>*</span></label>
		             <select name="reason" id="reason">
		             	<option value="">Select</option>
		             	
		             </select>
		             <span class="text-danger cust-error" id="reason-csv-id">This field is required.</span>
		        </div>	 
		         <div class="col-md-3">
			             <label for="">Date <span>*</span></label>
			              <input type="text" id="transactionDate" name="transactionDate" autocomplete="off">
			             <span class="text-danger cust-error" id="transactionDate-csv-id">This field is required.</span>
			        </div> 
		    </div>
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
	                <div id="collapse1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading1">
		    			<div class="panel-body">
		    				<div id="show-select-gstin-location-msg" style="display:none;color:red">Please select GSTIN & Location before Adding Items</div>
	                		
							<div class="form-wrap">
	                			
	                			<div class="row">
	                				 <div class="col-md-3">
	                                   <label id="service_name_label" for="">Search By Goods Name`<span>*</span> </label>
	                                   <input type="text" id="search-product-autocomplete" placeholder="Search Product" maxlength="15"/>
	                               	    <select id="service_name" name="service_name" style="display:none">
                                        </select>
	                                   <span class="text-danger cust-error" id="search-ser-prod-autocomplete-csv-id">This field is required.</span>
                                  
	                                </div> 
	                                
		                                <div class="col-md-3" id="quantityDiv">
											<label for="">Quantity <span>*</span></label>
	                                        
	                                        <input id="quantity" type="text" autocomplete="off">
	                                        <input id="oldQuantity" type="hidden" name="">
	                                        <label id="current_quantity" for="quantity">Current Qty</label>
	                                        
	                                        <span class="text-danger cust-error" id="current-quantity-csv-id">This field is required.</span>
										</div>
										<div class="col-md-3" id="unitOfMeasurement">
											<label for="">Unit Of Measurement <span>*</span></label>
											<input id="uomtoShow" disabled="disabled" type="text"> 
	                                        <input id="uom" type="hidden" name="">
	                                        <span class="text-danger cust-error" id="unitOfMeasurement-csv-id">This field is required.</span>
										</div>
										
										<div class="col-md-3" id="stockValueDiv">
											<label for="">Stock Value <span>*</span></label>
	                                        <input id="stockValue" type="text" autocomplete="off">
	                                        <input id="oldStockValue" type="hidden" name="">
	                                        <label for="curr_stockValueLabel" id="curr_stockValueLabel">Current Stock Value </label>
	                                        <span class="text-danger cust-error" id="stockValue-csv-id">This field is required.</span>
										</div>
										  <input type="hidden" id="unqValId" name="" value="">
									</div>
									
									<div class="form-wrap">
									<div class="row">

										<div class="col-md-8">
											<label for="label">Narration</label> <label class="label">
												<textarea placeholder="Max 140 characters" name="narration" id="narration" maxlength="140" rows="2" class="form-control narration"></textarea>
										</div>
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
	                			<div class="col-md-12 button-wrap">
	                				<button id="finalSave" style="width:auto;margin: 2em 0 0 0;" class="btn btn-success blue-but">Submit</button>
	                			</div>
	                			</div>
	                		
							</div>
						</div>
						</div>	
						</div><!-- end of panel panel-defaul -->
						</div><!-- end of panel-group -->
						</div><!-- end of /form-accrod -->	
						</div>
</section>

<script type="text/javascript" src="./resources/js/newUiJs/increaseDecreaseInventory/increaseDecreaseInventoryPopulateData.js"></script>
<script type="text/javascript" src="./resources/js/newUiJs/increaseDecreaseInventory/ProductPopulateData.js"></script>
