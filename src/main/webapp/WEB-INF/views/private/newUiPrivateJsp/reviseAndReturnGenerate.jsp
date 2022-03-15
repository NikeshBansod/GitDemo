<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
  
<section class="block">
	<div class="loader"></div>
	<div class="container" id="firstPage">
	  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  <input type="hidden" id="unqIncDes" name="unqIncDes" value="${refToFind}" />
	  <input type="hidden" id="backRRType" name="backRRType" value="${backRRType}" />
	  <input type="hidden" id="invNo" name="invNo" />
	  <input type="hidden" id="invFor" name="invFor" />
	  <input type="hidden" id="invDate" name="invDate" />
	  <input type="hidden" id="invDocType" name="invDocType" />
	  <input type="hidden" id="invDataFiledToGstn" name="invDataFiledToGstn" />
	  <input type="hidden" id="modeOfCreation" value="EDIT_INVOICE">
	  <input type="hidden" id="firmName" value="${sessionScope.loginUser.firmName}" />
	  <input type="hidden" id="panNumber" value="${sessionScope.loginUser.panNo}" />
	  <input type="hidden" id="iterationNo" />
	  <input type="hidden" id="invoiceSequenceType" value="${sessionScope.loginUser.invoiceSequenceType}">
	  
	  <div class="brd-wrap">
		 <div id="generateInvoiceDefaultPageHeader">
            
 		 </div>
	  </div>
	  
          <div class="form-wrap footernote">
              <div class="row">
              
                   <div class="col-md-6">
                       <label for="">Revision & Return Type:<span>  *</span></label>
                       <select name="rrType" id="rrType"> </select> 
                       <span class="text-danger cust-error" id="rrType-csv-id">This field is required.</span>                    
                  </div>
              </div>
          </div>
                
          <div id="showProductGrid" style="display:none">
			 <div class="row" id="listTable">
	            <div class="table-wrap">
	            <table id="openingstockInventoryTab" class="display nowrap" style="width:100%">
		            <thead>
		                <tr>
		                	<th id="checkboxtd"></th>
		                    <th style="width:20px;">Sr.No.</th>
		                    <th id="tableField1">Goods</th>
		                    <th id="tableField2">Original Quantity</th>
		                    <th id="tableField3">UOM</th>
		                </tr>
		            </thead>
		            <tbody>
		            </tbody>
	             </table>
	            </div>
	        </div>
			<div class="row">
				<div class="col-md-12 button-wrap">
					<button type="button" id="Save" style="width:auto;" class="btn btn-success blue-but">Save</button>
					<!-- <button type="button" id="" onclick="dummyJson();" style="width:auto;" class="btn btn-success blue-but">Dummy</button> -->
					
				</div>
			</div>
		  </div>	
   </div>
   <div class="container" id="secondPage" style="display:none">
   		<div class="form-wrap">
   		 <!-- Start of row 1 -->
	  		<div class="row">
	  			<div class="col-md-4 diff">
	                 <label class="form-section-title-pad15"></label>
	                 <div class="radio radio-success radio-inline" id="creditNoteRadio">
	                     <input type="radio" class="styled" name="createDocType" value="creditNote" id="radio1" checked="checked"/>
	                     <label for="radio1">Create a Credit Note</label>
	                 </div>
	                 <div class="radio radio-success radio-inline" id="debitNoteRadio">
				         <input type="radio" class="styled" name="createDocType" value="debitNote" id="radio2"/>
	                     <label for="radio2">Create a Debit Note</label>
	                 </div>
	                 <div class="radio radio-success radio-inline" id="revisedInvoiceRadio">
				         <input type="radio" class="styled" name="createDocType" value="revisedInvoice" id="radio3"/>
	                     <label for="radio3">Edit Original Invoice</label>
	                 </div>  
	                 <div class="radio radio-success radio-inline" id="deleteInvoiceRadio">
				         <input type="radio" class="styled" name="createDocType" value="deleteInvoice" id="radio4"/>
	                     <label for="radio4">Delete Invoice</label>
	                 </div>  
	                 <div class="radio radio-success radio-inline" id="newInvoiceRadio">
				         <input type="radio" class="styled" name="createDocType" value="newInvoice" id="radio5"/>
	                     <label for="radio5">Create Amendment Invoice</label>
	                 </div>                               
	             </div>
	  		
	  		</div>
	  		
	  		<div class="row">
				<div class="col-md-12 button-wrap">
					<button type="button" id="secondPageSave" style="width:auto;" class="btn btn-success blue-but">OK</button>
					<button type="button" id="secondPageCancel" style="width:auto;" class="btn btn-success blue-but">Cancel</button>
				</div>
			</div>
	  	 
   		</div>
   
   </div>
   <div class="invoice-wrap" id="previewCnDnInvoiceDiv" style="display:none">
		<div class="container">
			<!-- Start of row 1 -->
			 <div class="row">
			 	<div class="col-xs-12">
				    <div class="text-center">
                        <h2 class="section-title" id="previewBreadcrum">Preview Credit Note</h2>
                    </div> 
                    <hr>
                    <div class="row">
	                    <div class="col-xs-12 col-md-6 col-lg-6 pull-left">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Billing Details</div>
	                            <div class="panel-body" id="userDetailsInPreview"></div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-6 col-lg-6">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDiv">
	                                <strong>Name : </strong>${invoiceDetails.customerDetails.custName }<br/>
									<strong>Address : </strong>${invoiceDetails.customerDetails.custAddress1 }<br/>
									<strong>City : </strong>${invoiceDetails.customerDetails.custCity }<br/>
									<strong>State : </strong>${invoiceDetails.customerDetails.custState } 
										<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
										[ ${invoiceDetails.customerDetails.custGstinState } ]
										</c:if>
									<br/>
									<strong>State Code : </strong>${customerStateCode }<br/>
									<strong>GSTIN/Unique Code : </strong>${invoiceDetails.customerDetails.custGstId }<br/>
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
	                                    <td><strong>Total (Rs.)</strong></td>
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
			<!-- End of row 2 -->
			<!-- Start of row 3 -->
			 <div class="row">
			 	<div class="col-md-12 button-wrap">
	                <button type="button" id="finalSubmitCnDnId" class="btn btn-success blue-but" style="width:auto;">Send Invoice</button>
	                <button type="button" id="backToFirstPage" class="btn btn-success blue-but" style="width:auto;">Edit</button>
	            </div>
			 
			 </div>
			<!-- End of row 3 -->
		</div>
	</div><!-- end of previewCnDnInvoiceDiv -->
	
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
	<!-- Preview Invoice Ends Here-->
	
	
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/basic.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/cndn.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/revise.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/salesReturn.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/discountChange.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/salesPriceChange.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/rateChange.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/quantityChange.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/delete.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/client-side-validations.js"/>"></script>

 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" name="conditionValue" id="conditionValue">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>