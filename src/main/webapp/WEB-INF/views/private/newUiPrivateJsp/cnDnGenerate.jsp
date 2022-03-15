<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
  

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/cnDn/cndn.js" />"></script>

<section class="block">

    <%-- <div class="container" id='loadingmessage' style='display:none;' align="center">
		<img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div> --%>
	<div class="loader"></div>

	
	<div class="container" id="addCnDnDiv">  
	  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  <div class="brd-wrap" id="originalHeader">
		  <div id="generateInvoiceDefaultPageHeader">
            <a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')"><strong>CNDN List</strong></a> &raquo; <strong>Create Credit/Debit note</strong>
 		 </div> 
	  </div>
	  
	  <div class="form-wrap">
	  	<div class="row">
	  		 <div class="col-md-4 diff">
                 <h1 class="form-section-title">Choose Type</h1><br><br>
                 <div class="radio radio-success radio-inline">
                     <input type="radio" class="styled" name="cnDnType" value="creditNote" id="radio2" checked="checked" />
                     <label for="radio2">Credit Note</label>
                 </div>
                 <div class="radio radio-success radio-inline">
			         <input type="radio" class="styled" name="cnDnType" value="debitNote" id="radio1"/>
                     <label for="radio1">Debit Note</label>
                 </div>                                
             </div>
             <input type="hidden" name="regime" value="postGst" id="regime" >
             
             <div class="col-md-4">
				<label for="">Reason<span>*</span></label>
				<select name="selectReason" id="selectReason">
			    </select>
			    <span class="text-danger cust-error" id="selectReason-csv-id">This field is required.</span>  
			</div>
	  	</div>
	  	
	  	
	  	
	  	<div class="form-accrod">
	    	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	    		<div class="panel panel-default">
		    		 <div class="panel-heading" role="tab" id="headingOne">
	                	<h4 id="cndn-panel-heading" class="panel-title">
	                    	<a id="callOnEditId" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse1">
	                      		Goods/Services details
	                    	</a>
	                	</h4>
	                </div>
	                <div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading1">
		    			<div class="panel-body">
	                		<div class="form-wrap" id="dnynamicGoodServices">
	                			<div class="row">
	                
	                
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
	    </div>
	    
	     <input id="refInvId" value="${invoiceDetails.id }" type="hidden">
         <input id="fInvValz" value="${invoiceDetails.invoiceValue }" type="hidden"><%-- 
         <input id="gstnStateId" value="${invoiceDetails.gstnStateIdInString }" type="hidden">
         <input id="user_org_name" value="${userMaster.organizationMaster.orgName }" type="hidden">
         <input id="invoiceNumber" value=" ${invoiceDetails.invoiceNumber }" type="hidden">
         <input id="invoiceDate" value="${invoiceDetails.invoiceDate}" type="hidden"> --%>
         
        
         
	  	
	  	<div class="row">
            <div class="col-md-12 button-wrap">
                <button type="button" id="previewSubmit"  class="btn btn-success blue-but" style="width:auto;"></button>
            </div>
        </div>
	  
	  </div><!-- /form-wrap -->
	</div><!-- /addCnDnDiv -->
	
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
	                            <div class="panel-body" id="userDetailsInPreview">
	                                <strong>${userMaster.organizationMaster.orgName }</strong><br>
				  				    ${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}<br>
				  					<strong>GSTIN : </strong>${invoiceDetails.gstnStateIdInString }<br>
				  					<strong>Original Tax Document No : </strong>${invoiceDetails.invoiceNumber }<br/>
				  					<strong>Original Tax Document Date : </strong><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>
	                            </div>
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
	                    <!-- <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Ship To</div>
	                            <div class="panel-body" id="customerDetailsShipToDiv">
	                                
	                            </div>
	                        </div>
	                    </div> -->
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
	                                    <!-- <td><strong>Disc(Rs.)</strong></td> -->
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
	                <button type="button" id="finalSubmitId" class="btn btn-success blue-but" style="width:auto;">Send Invoice</button>
	                <button type="button" id="backToAddCnDnDivDiv" class="btn btn-success blue-but" style="width:auto;">Edit</button>
	            </div>
			 
			 </div>
			<!-- End of row 3 -->
		</div>
	</div>
	
	
	
</section>

 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>