<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
						
<section class="insidepages">
	<div class="inside-cont">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="breadcrumbs" id="header"><a href="<spring:url value="./wgetmypurchaseentrypage"/>">Purchase Entry History</a> <span>&raquo;</span> Purchase Entry Details</div>
        <div class="account-det"   id="divTwo">
			
			<div class="card">
				<div class="logo-det">
					<div class="print-logo" style="float:right;margin:10px">
					   
					   <!--  <button class="sim-button button5"  id="optionsDiv" style="" value="Options">Action</button> -->
					    <a href="#" onclick="javascript:deleteInvoice('${purchaseEntryDetails.purchaseEntryDetailsId}');" class="sim-button button5" style="margin:5px 0 0 0">
          			<span class="glyphicon glyphicon-remove"></span> Delete Document
        		</a>
					    
					</div><%-- <div class="upload-logo"><img  alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-responsive" src="${pageContext.request.contextPath}/wGetOrgLogo" width="143" height="174"></div> --%>
					<div class="upload-txt">
					
						<strong>${purchaseEntryDetails.supplierName}</strong><br>
						${supplierCompleteAddress}<br>
						<b>GSTIN : </b>${purchaseEntryDetails.supplierGstin}<br>
						<b>Purchase Date : </b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.purchaseDate}" /><br>
						<b>Invoice Number : </b>${purchaseEntryDetails.invoiceNumber}<br>						
						<b>Invoice Period : </b>
							<c:choose>
								<c:when test="${not empty purchaseEntryDetails.invoicePeriodFromDate != 'NULL' && not empty purchaseEntryDetails.invoicePeriodToDate != 'NULL' }">				  	
								  	   <fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.invoicePeriodFromDate}" /> TO 
								  	   <fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.invoicePeriodToDate}" />
								</c:when>
								<c:otherwise>				  	
								  	   NA
								</c:otherwise>
							</c:choose><br>
						
						<b>Reverse Charge Applicable : </b>${purchaseEntryDetails.reverseChargeApplicable}<br>
						<c:if test="${purchaseEntryDetails.reverseChargeApplicable  == 'Yes'}">
							<b>Supplier Document/Invoice No : </b>${purchaseEntryDetails.supplierDocInvoiceNo}<br>
							<b>Supplier Document/Invoice Date : </b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.supplierDocInvoiceDate}" /><br>
						</c:if>
					</div>
					
					<%-- <div class="print-logo" style="float:right;margin:10px">
					    <a href="#" onclick="javascript:downloadRecord('${invoiceDetails.id }');" class="sim-button button5" >
					      <span class="glyphicon glyphicon-download-alt"></span> &nbsp;Download & Print
					    </a>
					</div> --%>
				</div>
				
				<div class="tax-invoice" id="customerDetailsInPreview">
					<h1>Purchase Document</h1>
					<%-- <c:choose>
						<c:when test="${purchaseEntryDetails.invoiceFor  == 'Service'}">
							<div class="invoice-txt">
								<strong><u>Bill To</u></strong><br>
								<b>Name : </b>${userMaster.organizationMaster.orgName}<br>
								<b>Address : </b>${userMaster.organizationMaster.address1}<br>
								<b>City : </b>${userMaster.organizationMaster.city}<br>
								<b>State : </b>${userMaster.organizationMaster.state}<br>
									<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
									[ ${invoiceDetails.customerDetails.custGstinState } ]
									</c:if>
								<b>State Code : </b>${userMaster.organizationMaster.stateCode}<br>
								<b>GSTIN/Unique Code : </b>${gstinDetails.gstinNo}<br>
								<b>Place Of Supply : </b>${purchaseEntryDetails.placeOfSupplyName} [${purchaseEntryDetails.placeOfSupplyCode}]<br>
									<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
										${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
									</c:if>
									<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
										${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
									</c:if>
							</div>
						</c:when>
						<c:otherwise> --%>
							<div class="col-sm-6">
								<div class="invoice-txt">
									<strong><u>Bill To</u></strong><br>
									<b>Name : </b>${userMaster.organizationMaster.orgName}<br>
									<b>Address : </b>${gstinDetails.gstinAddressMapping.address}<br>
									<b>City : </b>${gstinDetails.gstinAddressMapping.city}<br>
									<b>State : </b>${gstinDetails.gstinAddressMapping.state}<br>
									<b>State Code : </b>${supplierStateCode}<br>
									<b>GSTIN/Unique Code : </b>${gstinDetails.gstinNo}<br>
									<b>Place Of Supply : </b>${purchaseEntryDetails.placeOfSupplyName} [${purchaseEntryDetails.placeOfSupplyCode}]<br>
								</div>
							</div>
							<c:choose>
								<c:when test="${purchaseEntryDetails.billToShipIsChecked == 'Yes' }">
									<div class="col-sm-6">
										<div class="invoice-txt">
											<strong><u>Ship To</u></strong><br>
											<b>Name : </b>${userMaster.organizationMaster.orgName}<br>
											<b>Address : </b>${gstinDetails.gstinAddressMapping.address}<br>
											<%-- <b>City : </b>${invoiceDetails.customerDetails.custCity }<br>
											<b>State : </b>${invoiceDetails.customerDetails.custState } 
												<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
												[ ${invoiceDetails.customerDetails.custGstinState } ]
												</c:if><br>
											<b>State Code : </b>${customerStateCode }<br> --%>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-sm-6">
										<div class="invoice-txt">
											<strong><u>Ship To</u></strong><br>
											<b>Name : </b>${userMaster.organizationMaster.orgName}<br>
											<b>Address : </b>${purchaseEntryDetails.purchaserShippingAddress}<br>
											<%-- <b>City : </b>${invoiceDetails.customerDetails.custCity }<br> 
											<b>State : </b>${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]<br>
											<b>State Code : </b>${invoiceDetails.shipToStateCode }<br> --%>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						<%-- </c:otherwise>
					</c:choose>	 --%>				
				</div>
				
				<div class="tablemain2">
					<table id="mytable2">
						<thead>
						  <tr>
							<th>Description</th>
							<th>SAC/HSN</th>
							<th>Quantity</th>
							<th>UOM</th>
							<th>Price/UOM(Rs)</th>
							<th>Disc(Rs.)</th>
							<th>Total (Rs.) After Disc</th>
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
                                   	<td class="text-right">${data.rate }</td>
                                   	<td class="text-right">${data.offerAmount }</td>
                                    <td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.previousAmount - data.offerAmount}"/></td>
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
			    <br/>
		 		
		 		<%-- <c:if test="${containsDiffPercentage gt 0}">
					<div class="tax-invoice" id="" style="margin:0 10px;">
						<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
		        	</div>
		             <hr>	
		        </c:if>	 --%>	 		
		       
		        
		        <div class="tax-invoice" id="preview_customer_purchase_order">
					<b>Customer Purchase Order : </b>${purchaseEntryDetails.poDetails}<br>
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
					I. Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional considerations directly or indirectly from the Buyer<br>
					II. The normal terms governing the above sale are printed overleaf E&OE	
				</div>
				 
			     <div class="invoice-txt">
			    	<c:if test="${not empty purchaseEntryDetails.footerNote}">
					    <div class="add-charges">
			            	<p><span id="footerNoteDiv">${purchaseEntryDetails.footerNote}</span></p>
			            </div>
			        </c:if>
				</div>
				
			</div>        
			<!-- <div class="row text-center" id="optionsDiv" style="margin:5px 0 0 0;bottom: 0px;">
				<button class="sim-button button5" style="" value="Options">Options</button>
			</div> -->
			<%-- <div class="insidebtn" style="display:none;bottom: 0px" id="optionsMultiDiv"> 
				<a href="#" onclick="javascript:sendMail('${invoiceDetails.id }');" class="sim-button button5" style="margin:5px 0 0 0">Send PDF</a>
       			
       			<a href="#" onclick="javascript:previewCNDN('${invoiceDetails.id }');" class="sim-button button5" style="margin:5px 0 0 0">View / Create Credit/Debit note</a>
				
				<a href="#" onclick="javascript:deleteInvoice('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">Delete ${documentType}</a>        				
									
				<c:if test="${containsProduct gt 0}">
   					<!-- <a href="#" onclick="javascript:generateEWayBill('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">Generate E-Way Bill</a> -->
    				<c:if test="${invoiceDetails.invoiceValueAfterRoundOff gt 50000}">
    					<a href="#" onclick="javascript:getEWayBills('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">View / Generate E-Way Bill</a>
    				</c:if>    				
   				</c:if>			
   				
   				<a href="#" onclick="javascript:deleteInvoice('${purchaseEntryDetails.purchaseEntryDetailsId}');" class="sim-button button5" style="margin:5px 0 0 0">
          			<span class="glyphicon glyphicon-remove"></span> Delete Document
        		</a>
			</div>  --%>							 
		</div>
	</div>
</section>	
				

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/purchaseEntryInvoice/wizardViewPurchaseEntryInvoiceDetails.js"/>"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageShortUrl" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="orgId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
