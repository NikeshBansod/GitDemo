<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
 
 
<section class="block"> 
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="loader"></div>
	<div class="invoice-wrap" id="divTwo">
         <div class="container">
             <div class="row">
                 <div class="col-xs-12">
                 	<!-- <div class="text-center">
                        <h2 class="section-title">Purchase Entry Preview</h2>
                    </div> <hr> -->
				   <%--  <div class="brd-wrap" id="breadcumheader">
				    	<a href="<spring:url value="./getMyPurchaseEntryPage"/>"><strong>Purchase1 Entry History</strong></a> &raquo; <strong>Purchase Entry Details</strong>
				    </div> --%>
				      <div class="page-title">
                        <a href="<spring:url value="./getMyPurchaseEntryPage"/>" class="back"><i class="fa fa-chevron-left"></i></a><h3 id="internalBreadCrum">Purchase Entry Details</h3>
         </div>
				    <br>
                    <div class="row">
	                    <div class="col-xs-12 col-md-4 col-lg-4 pull-left">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Billing Details</div>
	                            <div class="panel-body" id="userDetailsInPreview">
                                     <strong>${purchaseEntryDetails.supplierName}</strong><br>
                                     ${supplierCompleteAddress}<br>
                                     <strong>GSTIN :</strong> ${purchaseEntryDetails.supplierGstin}<br>
                                     <strong>Purchase Date :</strong> <fmt:formatDate pattern="dd-MM-yyyy" value="${purchaseEntryDetails.purchaseDate}"/><br>
                                     <strong>Invoice Number :</strong> ${purchaseEntryDetails.invoiceNumber}<br>
                                     <strong>Invoice Period :</strong> 
                                     	<c:choose>
											<c:when test="${not empty purchaseEntryDetails.invoicePeriodFromDate != 'NULL' && not empty purchaseEntryDetails.invoicePeriodToDate != 'NULL' }">				  	
											  	   <fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.invoicePeriodFromDate}" /> TO 
											  	   <fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.invoicePeriodToDate}" />
											</c:when>
											<c:otherwise>				  	
											  	   NA
											</c:otherwise>
										</c:choose><br>
                                     <strong>Reverse Charge Applicable :</strong> ${purchaseEntryDetails.reverseChargeApplicable}
                                     <c:if test="${purchaseEntryDetails.reverseChargeApplicable  == 'Yes'}">
										<br>
										<strong>Supplier Document/Invoice No : </strong>${purchaseEntryDetails.supplierDocInvoiceNo}<br>
										<strong>Supplier Document/Invoice Date : </strong><fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.supplierDocInvoiceDate}" />
									</c:if>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDiv">
                                     <strong>Name :</strong> ${userMaster.organizationMaster.orgName}<br>
                                     <strong>Address :</strong> ${gstinDetails.gstinAddressMapping.address}<br>
                                     <strong>City :</strong> ${gstinDetails.gstinAddressMapping.city}<br>
                                     <strong>State :</strong>${gstinDetails.gstinAddressMapping.state}<br>
                                     <strong>State Code :</strong> ${supplierStateCode}<br>
                                     <strong>GSTIN/Unique Code :</strong> ${gstinDetails.gstinNo}<br>
                                     <strong>Place Of Supply :</strong> ${purchaseEntryDetails.placeOfSupplyName} [${purchaseEntryDetails.placeOfSupplyCode}]
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Ship To</div>
	                            <div class="panel-body" id="customerDetailsShipToDiv">
	                            	<c:choose>
										<c:when test="${purchaseEntryDetails.billToShipIsChecked == 'Yes' }">
											<strong>Name :</strong> ${userMaster.organizationMaster.orgName}<br>
											<strong>Address :</strong> ${gstinDetails.gstinAddressMapping.address}
											<%-- <strong>City :</strong> ${invoiceDetails.customerDetails.custCity }<br>
											<strong>State : </strong> ${invoiceDetails.customerDetails.custState } 
												<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
												[ ${invoiceDetails.customerDetails.custGstinState } ]
												</c:if><br>
											<strong>State Code :</strong> ${customerStateCode } --%>
										</c:when>
										<c:otherwise>
											<strong>Name :</strong> ${userMaster.organizationMaster.orgName}<br>
											<strong>Address :</strong> ${purchaseEntryDetails.purchaserShippingAddress}
											<%-- <strong>City :</strong> ${invoiceDetails.customerDetails.custCity }<br> 
											<strong>State : </strong> ${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]<br>
											<strong>State Code :</strong> ${invoiceDetails.shipToStateCode } --%>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                 </div>
             </div>
             <div class="row">
             	<div class="col-md-12">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="text-center">Purchase Document</h3>
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
	                                    <td><strong>Total (Rs.) After Disc</strong></td>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	<c:set var="totalAmount" value="${0}"/>
									<c:set var="cessTotalTax" value="${0}"/>						
									<c:set var="categoryType" value=""/>							
									<%-- <c:set var="containsDiffPercentage" value="none"/>
									<c:set var="containsProduct" value="${0}"/> --%>
									
									 <c:forEach items="${purchaseEntryDetails.serviceList}" var = "data">								
										<%-- <c:if test="${data.billingFor == 'Product'}">
											<c:set var="containsProduct" value="${containsProduct + 1}"   />
										</c:if> --%>
										<tr id="productdet">
		                                   	<td>${data.serviceIdInString }
			                                   	<%-- <c:if test="${ data.diffPercent == 'Y'}">
													<span style="color:red">*</span>
												</c:if> --%>
											</td>                                   		
		                                   	<td>${data.hsnSacCode }</td>
		                                   	<td>${data.quantity }</td>
		                                   	<td>${data.unitOfMeasurement }</td>
		                                   	<td>${data.rate }</td>
		                                   	<td>${data.offerAmount }</td>
		                                    <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.previousAmount - data.offerAmount}"/></td>
		                                </tr>
										<c:set var="totalAmount" value="${totalAmount + (data.previousAmount - data.offerAmount)}" />
										<c:set var="cessTotalTax" value="${cessTotalTax + data.cess}" />
										<c:set var="categoryType" value="${data.categoryType}" />	
									</c:forEach> 
									<!-- First Table Ends -->
									<!-- Second Table Starts -->
									<tr id="producttotTotalValue(A)">
		                          		<td>&nbsp;</td>
		                                <td><b>Total Value (A)</b></td>
				                     	<td>&nbsp;</td>
				                     	<td>&nbsp;</td>
				                     	<td>&nbsp;</td>
				                     	<td>&nbsp;</td>
		                                <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalAmount}"/></td>                                   
		                            </tr>
		                            <c:set var="containsAdditionalCharges" value=""/>
		                           
			                        <c:if test="${not empty purchaseEntryDetails.addChargesList}">
										<c:set var="containsAdditionalCharges" value="YES"/>
			                           	<tr id="taxtableAdditionalCharges">
						                   	<td>&nbsp;</td>
						                   	<td><b>Add : Additional Charges</b></td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
					                     	<td>&nbsp;</td>
					                     	<td>&nbsp;</td>
					                     	<td>&nbsp;</td>
					                    </tr>				                    
					                    <c:set var="addChargeAmount" value="${0}"/>
					                    <c:forEach items="${purchaseEntryDetails.addChargesList }" var="chg">
						                    <tr id="taxtableAdditionalChargeName">
							                   	<td>&nbsp;</td>
							                   	<td>${chg.additionalChargeName }</td>
							                   	<td>&nbsp;</td>
							                   	<td>&nbsp;</td>
						                     	<td>&nbsp;</td>
						                     	<td>&nbsp;</td>
							                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${chg.additionalChargeAmount}"/></td>
						                    </tr>
						                    <c:set var="addChargeAmount" value="${addChargeAmount + chg.additionalChargeAmount}" />
					                    </c:forEach>
					                    
					                     <tr id="taxtableTotalAdditionalCharges(B)">
						                   	<td>&nbsp;</td>
						                   	<td><b>Total Additional Charges (B)</b></td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${addChargeAmount}"/></td>
					                    </tr>
					                    
					                    <tr id="taxtableTotalValue(A+B)">
						                   	<td>&nbsp;</td>
						                   	<td><b>Total Value (A+B)</b></td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalAmount + addChargeAmount}"/></td>
					                    </tr>
		                            </c:if> 
		                          <!-- Second Table Ends -->	
		                          <!-- Third Table Starts -->
									<%-- <c:set var="containsDiffPercentage" value="${0}" /> --%>
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' ||  serviceData.categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'cgst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="cgstLoop">
													  <c:if test="${cgstLoop.count eq 1}">												
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>Central Tax</b></td>
						                                   	<td></td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${cgstLoop.count gt 1}">
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr>
													 </c:if> 
												</c:forEach>
											</c:if>										   
										</c:forEach>									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' }">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'sgst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="sgstLoop">
													  <c:if test="${sgstLoop.count eq 1}">
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>State Tax</b></td>
						                                   	<td></td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${sgstLoop.count gt 1}">
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr>
													 </c:if> 
												</c:forEach>
											</c:if>								   
										</c:forEach>							
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_IGST' ||  serviceData.categoryType =='CATEGORY_EXPORT_WITH_IGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'igst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="igstLoop">
													  <c:if test="${igstLoop.count eq 1}">
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>Integrated Tax</b></td>
						                                   	<td></td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${igstLoop.count gt 1}">
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr>
													 </c:if> 
												</c:forEach>
											</c:if>								   
										</c:forEach>							
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'ugst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="ugstLoop">
													  <c:if test="${ugstLoop.count eq 1}">
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>Union Territory Tax</td>
						                                   	<td></td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${ugstLoop.count gt 1}">
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr>
													 </c:if> 
												</c:forEach>
											</c:if>								   
										</c:forEach>							
									</c:if>
											
									<tr id="taxtableCess">
		                              	<td></td>
		                                <td><b>Cess</b></td>
					                	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${cessTotalTax}"/></td>
		                           </tr>	
		                           
		                           <c:if test="${containsAdditionalCharges == 'YES' }">
								   		<tr id="taxtableTotalTaxes(C)">
											<td></td>
			                                <td><b>Total Taxes (C)</b></td>
						                	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.totalTax}"/></td>
										</tr>
									</c:if>
									
									<c:if test="${containsAdditionalCharges == '' }">
										<tr id="taxtableTotalTaxes(B)">
											<td></td>
		                                	<td><b>Total Taxes (B)</b></td>
						                	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.totalTax}"/></td>
										</tr>
									</c:if>	
								  	<!-- Third Table Ends -->
								  	<!-- Fourth Table Starts -->
		                        	<c:if test="${containsAdditionalCharges == 'YES' }">
			                          	<tr id="producttotal">
			                          		<td><b>Total ${documentType} Value (A+B+C)</b></td>
							  				<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
					                        <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValue}"/></td>                                 
					                    </tr>
		                         	 </c:if>
		                         	 <c:if test="${containsAdditionalCharges == '' }">
		                         	 	<tr id="producttotal">
							  				<td><b>Total ${documentType} Value (A+B)</b></td>
							  				<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
					                        <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValue}"/></td>                                 
				                   		</tr>
		                         	 </c:if>                      	 
		                         	
		                            <tr id="producttotalRoundoff">
					                    <td><b>Round off</b></td>
						  				<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
				                        <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValueAfterRoundOff - purchaseEntryDetails.invoiceValue}"/></td>                                  
				                	</tr>
					                <tr id="producttotalTotalDocumentValue(AfterRoundoff)">
					                    <td><b>Total Document Value (After Round off)</b></td>
						  				<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValueAfterRoundOff}"/></td>                                  
					                </tr>
				                    <tr id="producttotalTotalDocumentvalueRs(inwords)">
				                        <td><b>Total Document value Rs.(in words): </b></td>
				                        <td><strong>${amtInWords}</strong></td> 
				                        <td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>                                
				                    </tr>  
		                          	<!-- Fourth Table Ends -->
	                            </tbody>
	                    	</table>
	                    </div>
	                    <div class="form-con">
		                    <!-- <div class="tax-invoice" id="diffPercentShowHide" style="margin:0 10px;">
								<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
					        </div>
					        <hr> -->
	                        <div class="signature-wrap" id="preview_customer_purchase_order">
	                           <h1 class="black-bold">Customer Purchase Order :</h1>${purchaseEntryDetails.poDetails}
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
	                        	<c:if test="${not empty purchaseEntryDetails.footerNote}">
	                        		<div style="text-align:center;" id="footerNoteDiv">${purchaseEntryDetails.footerNote}</div>
	                        	</c:if>
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
	     	<div class="row">
	            <div class="col-md-12 button-wrap">
	                <button type="button" onclick="javascript:deleteInvoice('${purchaseEntryDetails.purchaseEntryDetailsId}');"  class="btn btn-success blue-but"><span class="glyphicon glyphicon-remove"></span> Delete Document</button>
	            </div>
	        </div>                 
         </div>
    </div>	  
</section>
	    
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/viewPurchaseEntryInvoiceDetails.js" />"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageShortUrl" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="orgId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
