<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
  
<section class="block">
	<div class="loader"></div>
	<div style="display:none">
	  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  <input type="hidden" id="unqIncDes" name="unqIncDes" value="${refToFind}" />
	  <input type="hidden" id="backRRType" name="backRRType" value="${backRRType}" />
	  <input type="hidden" id="invNo" name="invNo" />
	  <input type="hidden" id="invDate" name="invDate" />
	  <input type="hidden" id="invDocType" name="invDocType" />
	  <input type="hidden" id="selectedDocType" name="selectedDocType" />
	  <input type="hidden" id="invDataFiledToGstn" name="invDataFiledToGstn" />
	  <input type="hidden" id="modeOfCreation" value="EDIT_INVOICE">
	  <input type="hidden" id="firmName" value="${sessionScope.loginUser.firmName}" />
	  <input type="hidden" id="panNumber" value="${sessionScope.loginUser.panNo}" />
	  <input type="hidden" id="iterationNo" />
	  <input type="hidden" id="invoiceSequenceType" value="${sessionScope.loginUser.invoiceSequenceType}">
	
	</div>
	<div class="container" id="partyFirstPage">
		<div class="brd-wrap">
			<div id="generateInvoiceDefaultPageHeader">
	            
	 		 </div>
		</div>
	  
	   <div class="form-wrap footernote">
              <div class="row">
                   <div class="col-md-4">
                       	<label for="">Original Customer <span>*</span></label>
						<input type="text" name="" id="old_customer_name" required readonly="readonly"> 
						<input type="hidden" name="" id="old_customer_id" >
						<input type="hidden" name="" id="old_customer_billToShipIsChecked" >
						<input type="hidden" name="" id="old_customer_shipToCustomerName" value="">
						<input type="hidden" name="" id="old_customer_shipToAddress" value="">
						<input type="hidden" name="" id="old_customer_shipToCity" value="">
						<input type="hidden" name="" id="old_customer_shipToPincode" value="">
						<input type="hidden" name="" id="old_customer_shipToState" value="">
						<input type="hidden" name="" id="old_customer_shipToStateCode" value="">
						<input type="hidden" name="" id="old_customer_shipToStateCodeId" value="">
						                  
                  </div>
                  <div class="col-md-4" style="display:none" id="invoiceNumberDiv" >
	                <label for="invoiceNumber">Invoice Number <span>*</span></label>
	                <input type="text" name="invoiceNumber" id="invoiceNumber" maxlength="20" required>
	                <span class="text-danger cust-error" id="invoiceNumber-csv-id"></span>
	                <span class="text-danger cust-error" id="invoiceNumber-duplicate-csv-id"></span>
	             </div>
              </div>
              
               <div class="row">
                   <div class="col-md-4">
						<label for="">New Customer Name / Mobile No <span>*</span></label>
						<input type="text" name="customerDetails.custName" id="customer_name" required> 
						<span class="text-danger cust-error" id="empty-message">
				              No results found. <!-- <a href="javascript:void(0)" id="dnyAddNewCustomer">Click here to add a new customer.</a> -->
				        </span>
				        <span class="text-danger cust-error" id="customer-name-csv-id">This field is required.</span>
				        <span class="text-danger cust-error" id="customer-name-rchg-csv-id">Please select a GST-registered customer.</span>
					</div>
					<!-- hidden fields for customer - Start -->
					 <input type="hidden" name="customerDetails.contactNo" id="customer_contactNo" maxlength="10"> 
					 <input type="hidden" name="servicePlace" id="customer_place">
					 <input type="hidden" name="serviceCountry" id="customer_country">  
		             <input type="hidden" name="customerDetails.id" id="customer_id"><!-- mandatory -->
		             <input type="hidden" name="customerDetails.custAddress1" id="customer_custAddress1">
		           <!--   <input type="hidden" name="customerDetails.custAddress2" id="customer_custAddress2">  -->
		             <input type="hidden" name="customerDetails.custCity" id="customer_custCity">
		             <input type="hidden" name="customerDetails.custState" id="customer_custState">
		             <input type="hidden" name="customerDetails.custType" id="customer_custType">
		             <input type="hidden" name="customerDetails.custStateCode" id="customer_custStateCode">
		             <input type="hidden" name="customerDetails.custStateCodeId" id="customer_custStateCodeId">
		             <input type="hidden" name="customerDetails.custStateId" id="customer_custStateId">
		             <input type="hidden" name="customerDetails.custGstId" id="customer_custGstId">
		             <input type="hidden" name="customerDetails.custEmail" id="customer_custEmail">
					 
					<!-- hidden fields for customer - End -->
					<div class="col-md-4">
		                <label for="" class="dis-none">&nbsp;&nbsp;</label>
		                <div class="checkbox checkbox-success checkbox-inline form-mt">
		                    <input name="shipToBill" id="shipToBill" class="styled" type="checkbox" checked="checked"/>
		                    <label for="shipToBill">Make Shipping Address same as Billing Address</label>
		                </div>                                 
		            </div>
					
              </div>
              <div id="consignee" style="display: none">
		    	<div class="row" >
					<div class="col-md-3">
						<label for="">Ship To Customer Name <span>*</span></label>
						<input type="text" name="shipTo_customer_name" id="shipTo_customer_name" maxlength="50" required>  
						<span class="text-danger cust-error" id="shipTo-customer-name-csv-id">This field is required.</span>     
					</div>
					
					<div class="col-md-3">
						<label for="">Ship To Address <span>*</span></label>
						<input type="text" name="shipTo_address" id="shipTo_address" maxlength="500" required> 
						<span class="text-danger cust-error" id="shipTo-customer-address-csv-id">This field is required.</span>          
					</div>
					
					<div class="col-md-3" style="" id="shipTo-pincode-show-hide">
						<label for="">Ship To PinCode <span>*</span></label>
						<input type="text" name="shipTo_pincode" id="shipTo_pincode" required maxlength="6" autocomplete="off"> 
						 <span class="text-danger cust-error" id="shipTo-customer-pincode-csv-id">This field is required.</span> 
						 <span class="text-danger cust-error" id="empty-message-1">            
					</div>
					
					<div class="col-md-3" style="" id="shipTo-city-show-hide">
						<label for="">Ship To City <span>*</span></label>
		                <input type="text" name="shipTo_city" id="shipTo_city" readonly="readonly" required>  
		                <span class="text-danger cust-error" id="shipTo-customer-city-csv-id">This field is required.</span>
						
					</div>
					
					<input type="hidden" name="shipTo_stateCode" id="shipTo_stateCode">
	                <input type="hidden" name="shipTo_stateCodeId" id="shipTo_stateCodeId">
	                
		    	</div>
            
         	</div> 
         	<div class="row">
		       	<div class="col-md-4" id="">
					<label for="">Place Of Supply <span></span></label>
					<input type="text" id="pos" readonly="readonly">
					<select name="deliveryPlace" id="selectState" style="display:none;">
		            </select>
		            <!-- <span class="text-danger cust-error" id="">This field is required.</span> --> 
				</div>
	       </div>
       </div><!-- end of form-wrap -->
       
        <div class="row">
		 	<div class="col-md-12 button-wrap">
                <button type="button" id="submitPartyChgBtn" class="btn btn-success blue-but" style="width:auto;">Send Document</button>
                <button type="button" id="backToRRPage" onclick="backToRRPage()" class="btn btn-success blue-but" style="width:auto;">GO BACK</button>
            </div>
		 </div>
	
	
	
	 
	</div><!-- end of partyFirstPage -->
	<div class="invoice-wrap" id="partySecondPage" style="display:none">
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
	                <button type="button" id="backToaddInvoiceDiv" class="btn btn-success blue-but" style="width:auto;">Edit</button>
	            </div>
			 
			 </div>
			<!-- End of row 3 -->
			
	    </div>
	
	</div>

</section>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/partyChange/generalPartyChange.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/partyChange/customer.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/partyChange/revise.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/partyChange/party-validations.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/partyChange/pincode-autocomplete.js"/>"></script>

 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>