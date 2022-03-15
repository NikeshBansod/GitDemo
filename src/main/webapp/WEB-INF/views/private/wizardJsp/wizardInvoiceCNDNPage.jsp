<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/cnDn/wizardCnDn.js" />"></script>


<section class="insidepages">
	
	<div class="container" id="loadingmessage" style="display:none;" align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
           
	<div class="inside-cont">
		<div class="breadcrumbs">
			 <header class="insidehead" id="addCndnHeader">
	         	<a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" >Credit/Debit Note</a> <span>&raquo;</span> Generate Credit / Debit Note
	 		 </header>
	 		 <header class="insidehead" style="display:none" id="previewHeader">
	            <p id="previewHeaderLabel" >Preview Credit/Debit Note Details </p>
			 </header>
		</div>
		
      	<div class="account-det">
      		<div class="container" id="addCnDnDiv">
      		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	    			<div class="card">	    			
     					<div class="det-row">   
     						<div class="det-row-col-half">Choose Note Type &nbsp;&nbsp;&nbsp;
					              <input type="radio"  name="cnDnType" value="creditNote" id="radio2" checked="checked">
					              <label for="goods"> Credit Note</label>
					              <input type="radio" name="cnDnType" value="debitNote" id="radio1" >
					              <label for="services">Debit Note</label>
					        </div>   
					        
                       		<!-- <div class="det-row-col-half">Choose Regime Type &nbsp;&nbsp;&nbsp;
					              <input type="radio" name="regime" value="preGst" id="radio3" checked="checked">
					              <label for="goods">Pre-GST</label>
					               <input type="radio" name="regime" value="postGst" id="radio4" >
					              <label for="services">Post-GST</label>
					        </div>  -->
					          <input type="hidden" name="regime" value="postGst" id="regime" >
					          
                    		<div class="det-row-col-full ">
				            	<div class="label-text">Reason</div>
				              	<select class="form-control" name="selectReason" id="selectReason">
			          
			           			</select>
							  	 <span class="text-danger cust-error" id="selectReason-csv-id">This field is required.</span>  
				            </div>        					
						</div>
						<br>
       				</div>
       				<div class="accordion no-css-transition mb0">
                    	<div class="accordion_in">
                            <div id="" class="acc_head">Goods/Services details</div>
                            <div class="acc_content" id="dnynamicGoodServices">                         	

                            </div>
                       </div>                    
                    </div>
                    
                    <!-- Footer Note - Start -->
  					<div class="accordion no-css-transition mb0">                 
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
                    </div>
                        <!-- Footer Note - End -->
                   
                    <input id="refInvId" value="${invoiceDetails.id }" type="hidden">
                    <input id="fInvValz" value="${invoiceDetails.invoiceValue }" type="hidden">
                    
                    <div class="insidebtn"> 
						<input id="previewSubmit" type="button"  class="sim-button button5" value="Create Credit/Debit note"><%-- onclick="javascript:createCNDN('${invoiceDetails.id }');" --%>
					</div> 
					<br>
       		</div>	
        		
        	<div class="" id="previewCnDnInvoiceDiv" style="display:none">
					<!-- Latest Html provided - Start -->
				<div class="card">	
					
					<div class="logo-det">
						<div class="upload-logo"><img alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-responsive" src="${pageContext.request.contextPath}/getOrgLogo" width="143" height="174"></div>
						<div class="upload-txt">
							<strong>${userMaster.organizationMaster.orgName}</strong><br>
							${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}<br>
							<b>GSTIN  : </b>${invoiceDetails.gstnStateIdInString}<br>
							<%-- <b>PAN  : </b>${userMaster.organizationMaster.panNumber}<br> --%>
							<b>Original Tax Document No : </b>${invoiceDetails.invoiceNumber}<br>
							<b>Original Tax Document Date :</b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>
							<%-- <c:if test="${invoiceDetails.invoiceFor  == 'Service'}">
								<c:if test="${not empty invoiceDetails.invoicePeriodFromDate != 'NULL'}">
									<b>Service Period :</b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodFromDate}" /> TO <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodToDate}" /><br>
								</c:if>								
							</c:if>
							<b>Whether tax is payable on reverse charge : </b>${invoiceDetails.reverseCharge}	 --%>
						</div>
					</div>
					
					<div class="tax-invoice" >
						<h1 id="place1">TAX INVOICE</h1>
						<div class="invoice-txt">
							<strong><u>Bill To</u></strong><br>
							<b>Name : </b>${invoiceDetails.customerDetails.custName}<br>
							<b>Address : </b>${invoiceDetails.customerDetails.custAddress1}<br>
							<b>City : </b>${invoiceDetails.customerDetails.custCity}<br>
							<b>State : </b>${invoiceDetails.customerDetails.custState} 
								<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
								[ ${invoiceDetails.customerDetails.custGstinState} ]
								</c:if><br>
							<b>State Code : </b>${customerStateCode}<br>
							<b>GSTIN/Unique Code : </b>${invoiceDetails.customerDetails.custGstId}<br>								
						</div>
						<%-- <c:if test="${invoiceDetails.invoiceFor  == 'Product'}">
							<c:if test="${invoiceDetails.billToShipIsChecked == 'No' }">
								<div class="col-sm-6">
									<div class="invoice-txt">
										<strong><u>Ship To</u></strong><br>
										<b>Name : </b>${invoiceDetails.shipToCustomerName}<br>
										<b>Address : </b>${invoiceDetails.shipToAddress}<br>
										<b>State : </b>${invoiceDetails.shipToState} [ $invoiceDetails.shipToStateCodeId} ]<br>
										<b>State Code : </b>${invoiceDetails.shipToStateCode}<br>
									</div>
								</div>
							</c:if>
							<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes' }">
								<div class="col-sm-6">
									<div class="invoice-txt">
										<strong><u>Ship To</u></strong><br>
										<b>Name : </b>${invoiceDetails.shipToCustomerName}<br>
										<b>Address : </b>${invoiceDetails.customerDetails.custAddress1}<br>
										<b>City : </b>${invoiceDetails.customerDetails.custCity}<br>
										<b>State : </b>${invoiceDetails.customerDetails.custState}
											<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
											[ ${invoiceDetails.customerDetails.custGstinState } ]
											</c:if><br>
										<b>State Code : </b>${customerStateCode}<br>
										<b>GSTIN/Unique Code : </b>${invoiceDetails.customerDetails.custGstId}<br>
									</div>
								</div>
							</c:if>
						</c:if> --%>
					</div>
					<%-- <div class="placeOfSupply" >
						<div class="invoice-txt">
							<b>Place Of Supply : </b>
							<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
								${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
							</c:if>
							<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
								${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
							</c:if><br>
						</div>
					</div> --%>
				    <div class="tablemain2">
						<table id="mytable2">
							
							
						</table>
					</div> 
				    <br>
			 		 <div class="tax-invoice" id="diffPercentShowHide" style="margin:0 10px;display:none">
						<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
			        </div>
				    <br>
			        <div class="tax-invoice">
						<b>Customer Purchase Order : </b>${invoiceDetails.poDetails}<br>
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
				 
				    <div class="declaration">
						<strong>Declaration</strong><br>
						I. Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.<br>
						II. The normal terms governing the above sale are printed overleaf E&OE
					</div>
					
					
					<div class="invoice-txt" id="footerNoteDiv">
					 	<div class="add-charges" >                        
	                    </div>
					 </div>
						 <br>
					 
				     <div class="insidebtn"> 
						<input type="button" id="finalSubmitId"  class="sim-button button5" value="Send Invoice">
						<input id="backToAddCnDnDivDiv" type="button" class="sim-button button5" value="Edit">
					</div>
				</div>
					<!-- Latest Html provided - End -->
			</div>  
      	
      	</div>	<!-- End of class="account-det" -->		
	</div>
</section> 

 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
