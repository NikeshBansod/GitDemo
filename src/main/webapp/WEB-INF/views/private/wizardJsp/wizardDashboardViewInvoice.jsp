<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
						
<section class="insidepages">
	<div class="inside-cont">
	<!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">

		<div class="container" id='loadingmessage' style='display:none;' align="center">
	 			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="breadcrumbs" id="header"><a href="#" id="goBackToCall">Invoice List</a> <span>&raquo;</span> Invoice
        </div>
        <div class="account-det"   id="divTwo">
			
			<div class="card">
				<div class="logo-det">
					<div class="upload-logo"><img  alt="No Logo Uploaded" src="${pageContext.request.contextPath}/wGetOrgLogo" width="143" height="174"></div>
					<div class="upload-txt">
						<strong>${userMaster.organizationMaster.orgName }</strong><br>
						${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}<br>
						<b>GSTIN  : </b>${invoiceDetails.gstnStateIdInString }<br>
						<b>PAN : </b>${userMaster.organizationMaster.panNumber }<br>
						<b>
						<c:choose>
							<c:when test="${invoiceDetails.documentType == 'invoice'}">Invoice<c:set var = "documentType" value = "Invoice"/></c:when>
							<c:when test="${invoiceDetails.documentType == 'billOfSupply'}">Bill of Supply<c:set var = "documentType" value = "Bill of Supply"/></c:when>
							<c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">Invoice cum Bill of Supply<c:set var = "documentType" value = "Invoice cum Bill of Supply"/></c:when>
							<c:otherwise>TAX INVOICE</c:otherwise>
						</c:choose>
						
						 No :</b>${invoiceDetails.invoiceNumber }<br>
						
						
						<b>${documentType} Date : </b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>
						<c:if test="${invoiceDetails.invoiceFor  == 'Service'}">
							<c:if test="${not empty invoiceDetails.invoicePeriodFromDate != 'NULL'}">
								<b>Service Period : </b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodFromDate}" /> TO <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodToDate}" /><br>
							</c:if>							
						</c:if>
						<b>Whether tax is payable on reverse charge : </b>${invoiceDetails.reverseCharge}<br>
					</div>
					
					
				</div>
				
				<div class="tax-invoice" id="customerDetailsInPreview">
					<h1>
						<c:choose>
							<c:when test="${invoiceDetails.documentType == 'invoice'}">INVOICE</c:when>
							<c:when test="${invoiceDetails.documentType == 'billOfSupply'}">BILL OF SUPPLY</c:when>
							<c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">INVOICE CUM BILL OF SUPPLY</c:when>
							<c:otherwise>TAX INVOICE</c:otherwise>
						</c:choose>
					</h1>
					<c:choose>
						<c:when test="${invoiceDetails.invoiceFor  == 'Service'}">
							<div class="invoice-txt">
								<strong><u>Bill To</u></strong><br>
								<b>Name : </b>${invoiceDetails.customerDetails.custName }<br>
								<b>Address : </b>${invoiceDetails.customerDetails.custAddress1 }<br>
								<b>City : </b>${invoiceDetails.customerDetails.custCity }<br>
								<b>State : </b>${invoiceDetails.customerDetails.custState } 
									<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
									[ ${invoiceDetails.customerDetails.custGstinState } ]
									</c:if><br>
								<b>State Code : </b>${customerStateCode }<br>
								<b>GSTIN/Unique Code : </b>${invoiceDetails.customerDetails.custGstId }<br>
								<b>Place Of Supply : </b>
									<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
										${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
									</c:if>
									<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
										${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
									</c:if><br>
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-sm-6">
								<div class="invoice-txt">
									<strong><u>Bill To</u></strong><br>
									<b>Name : </b>${invoiceDetails.customerDetails.custName }<br>
									<b>Address : </b>${invoiceDetails.customerDetails.custAddress1 }<br>
									<b>City : </b>${invoiceDetails.customerDetails.custCity }<br>
									<b>State : </b>${invoiceDetails.customerDetails.custState } 
										<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
										[ ${invoiceDetails.customerDetails.custGstinState } ]
										</c:if><br>
									<b>State Code : </b>${customerStateCode }<br>
									<b>GSTIN/Unique Code : </b>${invoiceDetails.customerDetails.custGstId }<br>
									<b>Place Of Supply : </b>
										<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
											${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
										</c:if>
										<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
											${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
										</c:if><br>
								</div>
							</div>
							<c:choose>
							<c:when test="${invoiceDetails.billToShipIsChecked  == 'Yes'}">
								<div class="col-sm-6">
									<div class="invoice-txt">
										<strong><u>Ship To</u></strong><br>
										<b>Name : </b>${invoiceDetails.customerDetails.custName }<br>
										<b>Address : </b>${invoiceDetails.customerDetails.custAddress1 }<br>
										<b>City : </b>${invoiceDetails.customerDetails.custCity }<br>
										<b>State : </b>${invoiceDetails.customerDetails.custState } 
											<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
											[ ${invoiceDetails.customerDetails.custGstinState } ]
											</c:if><br>
										<b>State Code : </b>${customerStateCode }<br>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-6">
									<div class="invoice-txt">
										<strong><u>Ship To</u></strong><br>
										<b>Name : </b>${invoiceDetails.shipToCustomerName }<br>
										<b>Address : </b>${invoiceDetails.shipToAddress }<br>
										<%-- <b>City : </b>${invoiceDetails.customerDetails.custCity }<br> --%>
										<b>State : </b>${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]<br>
										<b>State Code : </b>${invoiceDetails.shipToStateCode }<br>
									</div>
								</div>
							</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>					
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
							<th>Disc(Rs)</th>
							<th>Total Rs. After Disc</th>
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
				                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.totalTax}"/></td>
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
				                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.totalTax}"/></td>
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
			                        <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValue}"/></td>                                 
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
			                        <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValue}"/></td>                                 
		                   		</tr>
                         	 </c:if>                      	 
                         	
                            <tr id="producttotalRoundoff">
			                    <td><b>Round off</b></td>
				  				<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
		                        <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValueAfterRoundOff - invoiceDetails.invoiceValue}"/></td>                                  
		                	</tr>
			                <tr id="producttotalTotalDocumentValue(AfterRoundoff)">
			                    <td><b>Total ${documentType} Value (After Round off)</b></td>
				  				<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td>&nbsp;</td>
			                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValueAfterRoundOff}"/></td>                                  
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
			    <br/>
		 		
		 		<c:if test="${containsDiffPercentage gt 0}">
					<div class="tax-invoice" id="" style="margin:0 10px;">
						<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
		        	</div>
		             <hr>	
		        </c:if>		 		
		       
		        
		        <div class="tax-invoice" id="preview_customer_purchase_order">
					<b>Customer Purchase Order : </b>${invoiceDetails.poDetails }<br>
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
			    <div class="declarationInvoice">	
			    	<p id="userAddressWithDetails"></p>
					<strong>Declaration</strong><br>
					I. Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional considerations directly or indirectly from the Buyer<br>
					II. The normal terms governing the above sale are printed overleaf E&OE	
				</div>
				 
			     <div class="invoice-txt">
			    	<c:if test="${not empty invoiceDetails.footerNote }">
					    <div class="add-charges">
			            	<p><span id="footerNoteDiv">${invoiceDetails.footerNote }</span></p>
			            </div>
			        </c:if>
				</div>
				
			</div>        
			
			<div class="insidebtn" style="display:none;bottom: 0px" id="optionsMultiDiv"> 
				<a href="#" onclick="javascript:sendMail('${invoiceDetails.id }');" class="sim-button button5" style="margin:5px 0 0 0">Send PDF</a>
       			
       			<a href="#" onclick="javascript:previewCNDN('${invoiceDetails.id }');" class="sim-button button5" style="margin:5px 0 0 0">View / Create Credit/Debit note</a>
				
				<a href="#" onclick="javascript:deleteInvoice('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">Delete ${documentType}</a>        				
									
				<c:if test="${containsProduct gt 0}">
   					<!-- <a href="#" onclick="javascript:generateEWayBill('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">Generate E-Way Bill</a> -->
    				<c:if test="${invoiceDetails.invoiceValueAfterRoundOff gt 50000}">
    					<a href="#" onclick="javascript:getEWayBills('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">View / Generate E-Way Bill</a>
    				</c:if>    				
   				</c:if>			
			</div> 							 
		</div>
	</div>
</section>	
				
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/viewInvoiceDetails.js" />"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/plugins.js" />"></script>
<script type="text/javascript">
	$('.resTable').riltable();
</script>

<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardInvoice.js" />"></script>



<!-- Dashboard backToCall form -->
<form name="redirectToBack" method="post">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageShortUrl" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="orgId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
				
		    
			            
			    
			    
			    
			
			
		
        		
