<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
 
<!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">

<!-- Dashboard set variables - End -->

<!--  for latest RR of invoice -->
<input type="hidden" id="iterationNo" name="iterationNo" value="${iterationNo}">
<input type="hidden" id="invoiceid" name="invoiceid" value="${invoiceid}">


<section class="block"> 
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="loader"></div>
	<div class="invoice-wrap" id="divTwo">
         <div class="container">
             <div class="row">
                 <div class="col-xs-12">
				   <%--  <div class="brd-wrap" id="breadcumheader">
				    	<c:set var="conditionValue" value="${dash_conditionValue}"/>
						 <c:choose>
						    <c:when test="${conditionValue == 'RRInvoiceHistoryD'}">
								 <a href="<spring:url value="./showrevisedandreturndetails"/>"><strong>RR History</strong></a> &raquo; <strong>Document Details</strong>
							</c:when> 
							<c:when test="${conditionValue == 'RRInvoiceHistory'}">
								 <a href="<spring:url value="./showrevisedandreturndetails"/>"><strong>RR History</strong></a> &raquo; <strong>Document Details</strong>
							</c:when>	
						    <c:when test="${conditionValue == 'invoiced'}">
						       <a id="goBackToCall" href="#"><strong>Document History</strong></a> &raquo; <strong>Document Details</strong>
						    </c:when>
						    <c:when test="${conditionValue == 'invoice' || invoiceDetails.documentType == 'invoice'}">
						       <a href="<spring:url value="./getInvoices"/>"><strong>Invoice History</strong></a> &raquo; <strong>Document Details</strong>
					        </c:when>
							<c:when test="${conditionValue == 'billOfSupply' || invoiceDetails.documentType == 'billOfSupply'}">
								 <a href="<spring:url value="./getBOS"/>"><strong>BillOfSupply History</strong></a> &raquo; <strong>Document Details</strong>
							</c:when>	
							<c:when test="${conditionValue == 'rcInvoice' || invoiceDetails.documentType == 'rcInvoice'}">
								 <a href="<spring:url value="./getRCInvoice"/>"><strong>RC invoice History</strong></a> &raquo; <strong>Document Details</strong>
							</c:when>	
							<c:when test="${conditionValue == 'eComInvoice' || invoiceDetails.documentType == 'eComInvoice'}">
								 <a href="<spring:url value="./getEComInvoice"/>"><strong>ECom Invoice History</strong></a> &raquo; <strong>Document Details</strong>
							</c:when>	
							<c:when test="${conditionValue == 'eComBillOfSupply' || invoiceDetails.documentType == 'eComBillOfSupply'}">
								 <a href="<spring:url value="./getEComBOS"/>"><strong>ECom BOS History</strong></a> &raquo; <strong>Document Details</strong>
							</c:when>
							
						    <c:otherwise>
						        <a href="<spring:url value="./getMyInvoices"/>"><strong>Document History</strong></a> &raquo; <strong>Document Details</strong>
						    </c:otherwise>
						 </c:choose>
				    </div> --%>
				    
				     <div class="brd-wrap page-title" id="breadcumheader">
				    	<c:set var="conditionValue" value="${dash_conditionValue}"/>
						 <c:choose>
						    <c:when test="${conditionValue == 'RRInvoiceHistoryD'}">
						         <a href="<spring:url value="./showrevisedandreturndetails"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
								
							</c:when> 
							<c:when test="${conditionValue == 'RRInvoiceHistory'}">
							     <a href="<spring:url value="./showrevisedandreturndetails"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
								
							</c:when>	
						    <c:when test="${conditionValue == 'invoiced'}">
						       <a id="goBackToCall" href="#" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
						     
						    </c:when>
						    <c:when test="${conditionValue == 'invoice' || invoiceDetails.documentType == 'invoice'}">
						       <a href="<spring:url value="./getInvoices"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
						      
					        </c:when>
							<c:when test="${conditionValue == 'billOfSupply' || invoiceDetails.documentType == 'billOfSupply'}">
							     <a href="<spring:url value="./getBOS"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
								
							</c:when>	
							<c:when test="${conditionValue == 'rcInvoice' || invoiceDetails.documentType == 'rcInvoice'}">
							     <a href="<spring:url value="./getRCInvoice"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
								
							</c:when>	
							<c:when test="${conditionValue == 'eComInvoice' || invoiceDetails.documentType == 'eComInvoice'}">
							    <a href="<spring:url value="./getEComInvoice"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
								
							</c:when>	
							<c:when test="${conditionValue == 'eComBillOfSupply' || invoiceDetails.documentType == 'eComBillOfSupply'}">
							     <a href="<spring:url value="./getEComBOS"/>" class="back"><i class="fa fa-chevron-left"></i></a>Document Details
								
							</c:when>
							
						    <c:otherwise>
						        <a href="<spring:url value="./getMyInvoices"/>" class="back"><i class="fa fa-chevron-left"></i></a><h3 id="internalBreadCrum">Document History</h3>
						       
						    </c:otherwise>
						 </c:choose>
				    </div>
		                
		             <c:set var="conditionValue" value="${dash_conditionValue}"/>
		            <%-- <c:set var="conditionValueForRR" value="${conditionValueForRR}"/> --%>
		             <c:choose>
		                   <c:when test="${conditionValue != 'invoiced'}" >
						     	<div class="row">
						            <div class="col-md-12 button-wrap" id='buttonsForInvoice'>
						            	<div class="print-logo" style="float:right;margin:10px">
						            		<button type="button" id="optionsDiv" class="btn btn-success blue-but" value="Options">Action</button>
										</div>
						            	<div class="insidebtn" style="display:none;float:right;margin:10px" id="optionsMultiDiv">
											<c:if test="${loggedInThrough == 'WIZARD'}">	
					  							<button type="button" onclick="javascript:downloadRecord('${invoiceDetails.id }');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download-alt"></span>&nbsp;Download & Print</button>
					  						</c:if>	
					  						<c:if test="${loggedInThrough == 'MOBILE'}">											                
							                	<button type="button" onclick="javascript:sendMail('${invoiceDetails.id }');" class="btn btn-success blue-but">Send PDF</button>
					  						</c:if>	
							                <button type="button" onclick="javascript:previewCNDN('${invoiceDetails.id }');" class="btn btn-success blue-but">View Credit/Debit note</button>
							                <button type="button" onclick="javascript:getRevAndRet('${invoiceDetails.id }');" class="btn btn-success blue-but">Revisions & Returns</button>
							                <c:if test="${invoiceDetails.invoiceValueAfterRoundOff gt 50000}"> 
							                	<button type="button" onclick="javascript:getEWayBills('${invoiceDetails.id}');" class="btn btn-success blue-but">View / Generate E-Way Bill</button>
							   				</c:if> 
							               <%--  <button type="button" onclick="javascript:deleteInvoice('${invoiceDetails.id}');" class="btn btn-success blue-but">Delete ${documentType}</button> --%>
										</div>
						            </div>
						            <div class="col-md-12 button-wrap" id='buttonsForRR' style="display:none">
						            	<div class="print-logo" style="float:right;margin:10px">
						            		<button type="button" id="optionsDivRR" class="btn btn-success blue-but" value="Options">Action</button>
										</div>
						            	<div class="insidebtn" style="display:none;float:right;margin:10px" id="optionsMultiDivRR">
											<%-- <c:if test="${loggedInThrough == 'WIZARD'}"> --%>	
					  							<button type="button" onclick="javascript:downloadOldRecord('${invoiceDetails.id }','${ iterationNo}');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download-alt"></span>&nbsp;Download & Print</button>
					  					<%-- 	</c:if>	
					  						<c:if test="${loggedInThrough == 'MOBILE'}">											                
							                	<button type="button" onclick="javascript:sendMail('${invoiceDetails.id }');" class="btn btn-success blue-but">Send PDF</button>
					  						</c:if>	 --%>
							               
										</div>
						            </div>
						        </div>  
			        	</c:when>
			        </c:choose> 
    				<br>
                    <div class="row">
	                    <div class="col-xs-12 col-md-4 col-lg-4 pull-left">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Billing Details</div>
	                            <div class="panel-body" id="userDetailsInPreview">
                                     <strong>${userMaster.organizationMaster.orgName}</strong><br>
                                     ${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}<br>
                                     <strong>GSTIN :</strong> ${invoiceDetails.gstnStateIdInString}<br>
                                     <strong>PAN :</strong> ${userMaster.organizationMaster.panNumber}<br>
                                     <strong>
	                                     <c:choose>
											<c:when test="${invoiceDetails.documentType == 'invoice'}">Invoice<c:set var = "documentType" value = "Invoice"/></c:when>
											<c:when test="${invoiceDetails.documentType == 'billOfSupply'}">Bill of Supply<c:set var = "documentType" value = "Bill of Supply"/></c:when>
											<c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">Invoice cum Bill of Supply<c:set var = "documentType" value = "Invoice cum Bill of Supply"/></c:when>
											<c:otherwise>TAX INVOICE</c:otherwise>
										</c:choose>
										 No :</strong> ${invoiceDetails.invoiceNumber}<br>
                                     <strong>${documentType} Date :</strong> <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>                                     
									 <c:if test="${invoiceDetails.invoiceFor  == 'Service'}">
										<c:if test="${not empty invoiceDetails.invoicePeriodFromDate != 'NULL'}">
											<strong>Service Period :</strong> <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodFromDate}" /> TO <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodToDate}" /><br>
										</c:if>										
									 </c:if>
                                     <strong>Whether tax is payable on reverse charge :</strong> ${invoiceDetails.reverseCharge}
	                            </div>
	                        </div>
	                    </div>
	                    <div class="col-xs-12 col-md-4 col-lg-4">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDiv">
                                     <strong>Name :</strong> ${invoiceDetails.customerDetails.custName}<br>
                                     <strong>Address :</strong> ${invoiceDetails.customerDetails.custAddress1}<br>
                                     <strong>City :</strong> ${invoiceDetails.customerDetails.custCity}<br>
                                     <strong>State :</strong> ${invoiceDetails.customerDetails.custState}
                                     	<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
											[ ${invoiceDetails.customerDetails.custGstinState } ]
										</c:if>  <br>
                                     <strong>State Code :</strong> ${customerStateCode}<br>
                                     <strong>GSTIN/Unique Code :</strong> ${invoiceDetails.customerDetails.custGstId}<br>
                                     <strong>Place Of Supply :</strong> 
                                     	<%-- <c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
											${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
										</c:if>
										<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
											${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
										</c:if> --%>
										${invoiceDetails.placeOfSupply }
	                            </div>
	                        </div>
	                    </div>
	                    <c:choose>
							<c:when test="${invoiceDetails.invoiceFor  == 'Product'}">	
			                    <div class="col-xs-12 col-md-4 col-lg-4">
			                        <div class="panel panel-default ">
			                            <div class="panel-heading">Ship To</div>
			                            <div class="panel-body" id="customerDetailsShipToDiv">
			                            	<c:choose>
												<c:when test="${invoiceDetails.billToShipIsChecked  == 'Yes'}">
													<strong>Name :</strong> ${invoiceDetails.customerDetails.custName}<br>
													<strong>Address :</strong> ${invoiceDetails.customerDetails.custAddress1}<br>
													<strong>City :</strong> ${invoiceDetails.customerDetails.custCity}<br>
													<strong>State :</strong> ${invoiceDetails.customerDetails.custState} 
														<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
															[ ${invoiceDetails.customerDetails.custGstinState } ]
														</c:if><br>
													<strong>State Code :</strong> ${customerStateCode }
												</c:when>
												<c:otherwise>
													<strong>Name :</strong> ${invoiceDetails.shipToCustomerName}<br>
													<strong>Address :</strong> ${invoiceDetails.shipToAddress}<br>
													<%-- <strong>City :</strong> ${invoiceDetails.customerDetails.custCity }<br>  --%>
													<strong>State :</strong> ${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]<br>
													<strong>State Code :</strong> ${invoiceDetails.shipToStateCode}
												</c:otherwise>
											</c:choose>
			                            </div>
			                        </div>
			                    </div>
							</c:when>						
						</c:choose>		
	                </div>
                 </div>
             </div>
             <div class="row">
             	<div class="col-md-12">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="text-center">
		                        <c:choose>
									<c:when test="${invoiceDetails.documentType == 'invoice'}">TAX INVOICE</c:when>
									<c:when test="${invoiceDetails.documentType == 'billOfSupply'}">BILL OF SUPPLY</c:when>
									<c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">INVOICE CUM BILL OF SUPPLY</c:when>
									<c:when test="${invoiceDetails.documentType == 'rcInvoice'}">REVERSE CHARGE TAX INVOICE</c:when>
									<c:when test="${invoiceDetails.documentType == 'eComInvoice'}">E-COMMERCE TAX INVOICE</c:when>
									<c:when test="${invoiceDetails.documentType == 'eComBillOfSupply'}">E-COMMERCE BILL OF SUPPLY</c:when>
									<c:otherwise>TAX INVOICE</c:otherwise>
								</c:choose>
	                        </h3>
	                    </div>
	                    <div class="table-responsive">
	                    	<table class="table table-condensed" id="mytable2">
	                            <thead>
	                                <tr>
	                                    <td><strong>Description</strong></td>
	                                    <td><strong>SAC/HSN</strong></td>
	                                    <td><strong>Quantity</strong></td>
	                                    <td><strong>UOM</strong> </td>
	                                    <td class="text-right"><strong>Price/UOM(Rs)</strong></td>
	                                    <td class="text-right"><strong>Disc(Rs.)</strong></td>
	                                    <td class="text-right"><strong>Total (Rs.) After Disc</strong></td>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	<c:set var="totalAmount" value="${0}"/>
									<c:set var="cessTotalTax" value="${0}"/>							
									<c:set var="categoryType" value=""/>
									<c:set var="containsDiffPercentage" value="none"/>
									<c:set var="containsProduct" value="${0}"/>
									
									 <c:forEach items="${invoiceDetails.serviceList }" var = "data">								
										<c:if test="${data.billingFor == 'Product'}">
											<c:set var="containsProduct" value="${containsProduct + 1}"   />
										</c:if>
										<tr id="productdet">
		                                   	<td>${data.serviceIdInString }
			                                   	<c:if test="${ data.diffPercent == 'Y'}">
													<span style="color:red">*</span>
												</c:if>
												<c:if test="${ data.isDescriptionChecked == 'Yes'}">
													&nbsp; - ${ data.description}
												</c:if>
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
		                                <td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalAmount}"/></td>                                   
		                            </tr>
		                            <c:set var="containsAdditionalCharges" value=""/>
		                           
			                        <c:if test="${not empty invoiceDetails.addChargesList}">
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
					                    <c:forEach items="${invoiceDetails.addChargesList }" var="chg">
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
						                   	<td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${addChargeAmount}"/></td>
					                    </tr>
					                    
					                    <tr id="taxtableTotalValue(A+B)">
						                   	<td>&nbsp;</td>
						                   	<td><b>Total Value (A+B)</b></td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td>&nbsp;</td>
						                   	<td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalAmount + addChargeAmount}"/></td>
					                    </tr>
		                            </c:if> 
		                          <!-- Second Table Ends -->	
		                          <!-- Third Table Starts -->
									<c:set var="containsDiffPercentage" value="${0}" />
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
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' ||  serviceData.categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'cgstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="cgstLoop">
													  <c:if test="${cgstLoop.count eq 1}">
														<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>Central Tax</b><span style="color:red">*</span></td>
						                                   	<td></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${cgstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
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
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' }">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'sgstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="sgstLoop">
													  <c:if test="${sgstLoop.count eq 1}">
													  	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>State Tax</b><span style="color:red">*</span></td>
						                                   	<td></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${sgstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
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
									<c:if test="${categoryType =='CATEGORY_WITH_IGST' ||  serviceData.categoryType =='CATEGORY_EXPORT_WITH_IGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'igstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="igstLoop">
													  <c:if test="${igstLoop.count eq 1}">
													  	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>Integrated Tax</b><span style="color:red">*</span></td>
						                                   	<td></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                    </tr>
													 </c:if>
													 <c:if test="${igstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
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
									<c:if test="${categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'ugstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="ugstLoop">
													  <c:if test="${ugstLoop.count eq 1}">
													  	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                   	<td></td>
						                                   	<td><b>Union Territory Tax</b><span style="color:red">*</span></td>
						                                   	<td></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td></td>
						                                   	<td></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr>
													 </c:if>
													 <c:if test="${ugstLoop.count gt 1}">
														 <c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<tr id="taxtable">
						                                    <td></td>
						                                    <td></td>
						                                   	<td></td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
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
						                   	<td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.totalTax}"/></td>
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
						                   	<td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.totalTax}"/></td>
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
					                        <td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValue}"/></td>                                 
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
					                        <td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValue}"/></td>                                 
				                   		</tr>
		                         	 </c:if>                      	 
		                         	
		                            <tr id="producttotalRoundoff">
					                    <td><b>Round off</b></td>
						  				<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
				                        <td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValueAfterRoundOff - invoiceDetails.invoiceValue}"/></td>                                  
				                	</tr>
					                <tr id="producttotalTotalDocumentValue(AfterRoundoff)">
					                    <td><b>Total ${documentType} Value (After Round off)</b></td>
						  				<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td>&nbsp;</td>
					                   	<td class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValueAfterRoundOff}"/></td>                                  
					                </tr>
				                    <tr id="producttotalTotalDocumentvalueRs(inwords)">
				                        <td><b>Total ${documentType} value Rs.(in words): </b></td>
				                        <td><strong>${amtInWords }</strong></td> 
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
		                    <c:if test="${containsDiffPercentage gt 0}">
								<div class="signature-wrap">
									<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
					        	</div>	
					        </c:if>
	                        <div class="signature-wrap" id="preview_customer_purchase_order">
	                           <h1 class="black-bold">Customer Purchase Order :</h1>${invoiceDetails.poDetails}
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
	                        	<c:if test="${not empty invoiceDetails.footerNote}">
	                        		<div style="text-align:center;" id="footerNoteDiv">${invoiceDetails.footerNote}</div>
	                        	</c:if>
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
                           
         </div>
    </div>	  
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateInvoice/viewInvoiceDetails.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardInvoice.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/invoiceHistory/latestRRForInvoice.js" />"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageShortUrl" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="orgId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />    
</form>

<!-- Dashboard backToCall form -->
<form name="redirectToBack" method="post">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
		