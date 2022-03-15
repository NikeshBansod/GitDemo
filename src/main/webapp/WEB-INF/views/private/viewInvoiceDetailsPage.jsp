<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">

<!-- Dashboard set variables - End -->
 <header class="insidehead">
 
 <c:set var="conditionValue" value="${dash_conditionValue}"/>
 <c:choose>
    <c:when test="${not empty conditionValue}">
        <a id="goBackToCall" href="#" class="btn-back"><i class="fa fa-angle-left"></i></a>
    </c:when>
    
    <c:otherwise>
        <a href="./getMyInvoices" class="btn-back"><i class="fa fa-angle-left"></i></a>
    </c:otherwise>
</c:choose>

      <a class="logoText" href="./home">
      	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>	
      </a>
      

       
 </header>
 
<main>
	<section class="block ">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id='loadingmessage' style='display:none;' align="middle">
		  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
		  
		</div>
		
		<div class="container" id="divTwo">
			<div class="card">
			
				<div class="invoicePage" >
					
					<div class="invoiceDetail">
						<!-- <img src="images/logo.png"> -->
						  <img alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-fluid img-responsive" src="${pageContext.request.contextPath}/getOrgLogo" />
						  
						  <c:set var="conditionValue" value="${dash_conditionValue}"/>
                    <c:choose>
                   <c:when test="${empty conditionValue}">
						  <a class="downToLast" href="#" onclick="scrollSmoothToBottom()" id="optionsDiv" class="btn-back"><i class="fa fa-angle-double-down"></i></a>
						  
					</c:when>
					</c:choose>
					
					
						<div class="invoiceInfo">
							<h4>${userMaster.organizationMaster.orgName }</h4>
							<p><span>${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}</span></p>
							<p><span>GSTIN :</span>${invoiceDetails.gstnStateIdInString }</p>
							<p><span>PAN :</span>${userMaster.organizationMaster.panNumber }</p>
							<p><span>
							<c:choose>
								<c:when test="${invoiceDetails.documentType == 'invoice'}">Invoice<c:set var = "documentType" value = "Invoice"/></c:when>
								<c:when test="${invoiceDetails.documentType == 'billOfSupply'}">Bill of Supply<c:set var = "documentType" value = "Bill of Supply"/></c:when>
								<c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">Invoice cum Bill of Supply<c:set var = "documentType" value = "Invoice cum Bill of Supply"/></c:when>
								<c:otherwise>TAX INVOICE</c:otherwise>
							</c:choose>
							 No:</span>${invoiceDetails.invoiceNumber }</p>
							<p><span>${documentType} Date:</span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /></p>
							<c:if test="${invoiceDetails.invoiceFor  == 'Service'}">
								<c:if test="${not empty invoiceDetails.invoicePeriodFromDate != 'NULL'}">
									<p><span>Service Period :</span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodFromDate}" /> TO <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodToDate}" /></p>
								</c:if>
								
							</c:if>
							<p><span>Whether tax is payable on reverse charge : </span>${invoiceDetails.reverseCharge}</p>	
						</div>
						
					</div>
					
					
					
					
												
					<div class="row">
						<div class="col-sm-12 text-center">
                             <h3>
                             	
                             	<c:choose>
								  <c:when test="${invoiceDetails.documentType == 'invoice'}">
								   	INVOICE
								  </c:when>
								  <c:when test="${invoiceDetails.documentType == 'billOfSupply'}">
								    BILL OF SUPPLY
								  </c:when>
								  <c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">
								    INVOICE CUM BILL OF SUPPLY
								  </c:when>
								  <c:otherwise>
								   	TAX INVOICE
								  </c:otherwise>
								</c:choose>
                             </h3>
                             <hr>
                        </div>
						<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
							<h4>Bill To</h4>
							<p><span>Name:</span>${invoiceDetails.customerDetails.custName }</p>
							<p><span>Address:</span>${invoiceDetails.customerDetails.custAddress1 }</p>
							<p><span>City:</span>${invoiceDetails.customerDetails.custCity }</p>
							<p><span>State:</span>${invoiceDetails.customerDetails.custState } 
								<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
								[ ${invoiceDetails.customerDetails.custGstinState } ]
								</c:if>
							</p>
							<p><span>State Code:</span>${customerStateCode }</p>
							<p><span>GSTIN/Unique Code:</span>${invoiceDetails.customerDetails.custGstId }</p>
							</div>
						</div>
						<c:if test="${invoiceDetails.invoiceFor  == 'Product'}">
							<c:if test="${invoiceDetails.billToShipIsChecked == 'No' }">
								<div class="col-sm-6">
									<div class="invoiceInfo">
									<h4>Ship To</h4>
									<p><span>Name:</span>${invoiceDetails.shipToCustomerName }</p>
									<p><span>Address:</span>${invoiceDetails.shipToAddress }</p>
									<p><span>State:</span>${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]</p>
									<p><span>State Code:</span>${invoiceDetails.shipToStateCode }</p>
									<!-- <p><span>GSTIN/Unique Code:</span></p> -->
									</div>
								</div>
							</c:if>
							<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes' }">
								<div class="col-sm-6">
									<div class="invoiceInfo">
									<h4>Ship To</h4>
										<p><span>Name:</span>${invoiceDetails.customerDetails.custName }</p>
										<p><span>Address:</span>${invoiceDetails.customerDetails.custAddress1 }</p>
										<p><span>City:</span>${invoiceDetails.customerDetails.custCity }</p>
										<p><span>State:</span>${invoiceDetails.customerDetails.custState } 
											<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
											[ ${invoiceDetails.customerDetails.custGstinState } ]
											</c:if>
										</p>
										<p><span>State Code:</span>${customerStateCode }</p>
										<%-- <p><span>GSTIN/Unique Code:</span>${invoiceDetails.customerDetails.custGstId }</p> --%>
									</div>
								</div>
							</c:if>
							
						</c:if>
						
					</div>
					
					<!-- Place of Supply -->
					<div class="row">
						<div class="col-sm-12">
							<div class="invoiceInfo invoiceFirst"><p>Place Of Supply : 
								<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
									${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
								</c:if>
								<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
									${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
								</c:if>
								</p>
							</div>
						</div>
					</div>
					
					
					<!--  -->
					
					<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          	<ul class="productdet headers">
                               <li>Description</li>
                               <li>HSN/SAC Code</li>
                               <li>Quantity</li>
                               <li>UOM</li>
                               <li class="text-right">Price (Rs.)/UOM</li>
                               <li class="text-right">Discount (Rs.)</li>
                               <li class="text-right">Total (Rs.) after discount</li>
                            </ul>
                          	<c:set var="totalAmount" value="${0}"/>
							<c:set var="cessTotalTax" value="${0}"/>
							
							<c:set var="categoryType" value=""/>
							<c:set var="containsDiffPercentage" value="none"/>
							<c:set var="containsProduct" value="${0}"/>
							
							<c:forEach items="${invoiceDetails.serviceList }" var = "data">
								
								<c:if test="${data.billingFor == 'Product'}">
									<c:set var="containsProduct" value="${containsProduct + 1}"   />
								</c:if>
								<ul class="productdet">
                                   	<li>${data.serviceIdInString }
	                                   	<c:if test="${ data.diffPercent == 'Y'}">
											<span style="color:red">*</span>
										</c:if>
										
									</li>
                                   		
                                   	<li>${data.hsnSacCode }</li>
                                   	<li>${data.quantity }</li>
                                   	<li>${data.unitOfMeasurement }</li>
                                   	<li class="text-right">${data.rate }</li>
                                   	<li class="text-right">${data.offerAmount }</li>
                                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.previousAmount - data.offerAmount}"/></li>
                                </ul>
								<c:set var="totalAmount" value="${totalAmount + (data.previousAmount - data.offerAmount)}" />
								<c:set var="cessTotalTax" value="${cessTotalTax + data.cess}" />
								<c:set var="categoryType" value="${data.categoryType}" />
								
								 
							</c:forEach>
                          
                          </div>
                          
                          <!-- First Table Ends -->	
                          
                          <!-- Second Table Starts -->
                          <div id="">
                          	<ul class="producttot">
                                <li>Total Value(A)</li>
                                <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalAmount}"/></li>                                   
                            </ul>
                            <c:set var="containsAdditionalCharges" value=""/>
                           
                            <c:if test="${not empty invoiceDetails.addChargesList}">
                            	<c:set var="containsAdditionalCharges" value="YES"/>
                            	<ul class="taxtable">
				                   	<li>&nbsp;</li>
				                   	<li class="hlmobil">Add :Additional Charges</li>
				                   	<li>&nbsp;</li>
				                   	<li>&nbsp;</li>
			                    </ul>
			                    
			                    <c:set var="addChargeAmount" value="${0}"/>
			                    <c:forEach items="${invoiceDetails.addChargesList }" var="chg">
				                    <ul class="taxtable">
					                   	<li>&nbsp;</li>
					                   	<li class="hlmobil">${chg.additionalChargeName }</li>
					                   	<li>&nbsp;</li>
					                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${chg.additionalChargeAmount}"/></li>
				                    </ul>
				                    <c:set var="addChargeAmount" value="${addChargeAmount + chg.additionalChargeAmount}" />
			                    </c:forEach>
			                    
			                     <ul class="taxtable">
				                   	<li>&nbsp;</li>
				                   	<li class="hlmobil">Total Additional Charges (B)</li>
				                   	<li>&nbsp;</li>
				                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${addChargeAmount}"/></li>
			                    </ul>
			                    
			                    <ul class="taxtable">
				                   	<li>&nbsp;</li>
				                   	<li class="hlmobil">Total Value (A+B)</li>
				                   	<li>&nbsp;</li>
				                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalAmount + addChargeAmount}"/></li>
			                    </ul>
                            
                            
                            </c:if>
                          </div>
                          <!-- Second Table Ends -->	
						
						  <!-- Third Table Starts -->
						  <c:set var="containsDiffPercentage" value="${0}" />
						  <div id="">	
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' ||  serviceData.categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'cgst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="cgstLoop">
													  <c:if test="${cgstLoop.count eq 1}">
														
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">Central Tax</li>
						                                   	<li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${cgstLoop.count gt 1}">
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">Central Tax<span style="color:red">*</span></li>
						                                   	<li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${cgstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">State Tax</li>
						                                   	<li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${sgstLoop.count gt 1}">
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">State Tax<span style="color:red">*</span></li>
						                                   	<li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${sgstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">Integrated Tax</li>
						                                   	<li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${igstLoop.count gt 1}">
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">Integrated Tax<span style="color:red">*</span></li>
						                                   	<li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${igstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">Union Territory Tax</li>
						                                   	<li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${ugstLoop.count gt 1}">
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                   </ul>
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
														<ul class="taxtable">
						                                   	<li>&nbsp;</li>
						                                   	<li class="hlmobil">Union Territory Tax<span style="color:red">*</span></li>
						                                   	<li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${ugstLoop.count gt 1}">
														 <c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <li>&nbsp;</li>
						                                    <li class="hidemob">&nbsp;</li>
						                                    <li>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></li>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach>
									
									</c:if>
								
									<ul class="taxtable">
	                                   	<li>&nbsp;</li>
	                                   	<li class="hlmobil">Cess</li>
	                                   	<li>&nbsp;</li>
	                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${cessTotalTax}"/></li>
	                                </ul>
	                                
	                                <c:if test="${containsAdditionalCharges == 'YES' }">
	                                	<ul class="taxtable">
				             			  <li>&nbsp;</li>
					  					  <li class="hlmobil">Total Taxes (C)</li>
					  					  <li>&nbsp;</li>
				             			  <li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.totalTax}"/></li>
				             		 	</ul>
	                                </c:if>
	                                
	                                <c:if test="${containsAdditionalCharges == '' }">
	                                	<ul class="taxtable">
				             			  <li>&nbsp;</li>
					  					  <li class="hlmobil">Total Taxes (B)</li>
					  					  <li>&nbsp;</li>
				             			  <li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.totalTax}"/></li>
				             		 	</ul>
	                                
	                                </c:if>
                                </div>
                          <!-- Third Table Ends -->
                                
                          <!-- Fourth Table Starts -->
                          <div id="">
                          	 <c:if test="${containsAdditionalCharges == 'YES' }">
	                          	<ul class="producttotal">
					  				<li>Total ${documentType} Value (A+B+C)</li>
			                        <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValue}"/></li>                                 
			                    </ul>
                          	 </c:if>
                          	 <c:if test="${containsAdditionalCharges == '' }">
                          	 	<ul class="producttotal">
					  				<li>Total ${documentType} Value (A+B)</li>
			                        <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValue}"/></li>                                 
			                    </ul>
                          	 </c:if>
                          	 
                          	
                             <ul class="producttotal">
			                    <li>Round off</li>
			                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValueAfterRoundOff - invoiceDetails.invoiceValue}"/></li>                                  
			                </ul>
			                <ul class="producttotal">
			                    <li>Total ${documentType} Value (After Round off)</li>
			                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${invoiceDetails.invoiceValueAfterRoundOff}"/></li>                                  
			                </ul>
		                    <ul class="producttotal">
		                        <li>Total ${documentType} value Rs.(in words): </li>
		                        <li><strong>${amtInWords }</strong></li>                                 
		                    </ul>
                          	
                          	 
                          </div>
                          <!-- Fourth Table Ends -->
							
							
					</div>
					
					<br>
					<c:if test="${containsDiffPercentage gt 0}">
					<div class="row" id="" style="margin:0 10px;">
						<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
		            </div>
		            <hr>	
		            </c:if>
					
					<div class="row">
					 	<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
							<p><span>Customer Purchase Order:</span>${invoiceDetails.poDetails }</p>
							
							</div>
						</div>
						<div class="col-sm-6">
							<div class="invoiceInfo">
						
							</div>
						</div>
					</div>
					<br>
					
					<div class="row">
						<div class="col-sm-6">
							<div class="invoiceInfo" style="width:49%; float:left;">
							   <p><span>Receiver Name</span></p>
				       		   <br>
				        	   <p>Receiver Signature</p>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="invoiceInfo text-right">
								<p><span>Supplier Name</span></p>
								<br>
								<p>Authorized Signature</p>
							</div>
						</div>
					</div>
					
						
					<hr>
					
					<div class="invoiceInfo">
						
						<h4>Declaration</h4>
						<p><span>I) Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.</span></p>
						<p><span>II) The normal terms governing the above sale are printed overleaf. </span></p>
						<p><span>E&OE</span></p>
					</div>
					<br>
					
					<c:if test="${not empty invoiceDetails.footerNote }">
						<hr>
						<div style="text-align:center">
                            <p><span id="footerNoteDiv">${invoiceDetails.footerNote }</span></p>
                        </div>
						<hr>
					</c:if>	
					
					<c:set var="conditionValue" value="${dash_conditionValue}"/>
                    <c:choose>
                   <c:when test="${empty conditionValue}">
					<div  class="row text-right">
					<a class="downToUp" href="#" onclick="scrollSmoothToTop()" class="btn-back"><i class="fa fa-angle-double-up"></i></a></div>
					
					</c:when>
					</c:choose>
					
					
					<!-- <div class="row text-center" id="options1Div" style="margin:5px 0 0 0;bottom: 0px;">
						 <button class="btn btn-info" style="" value="Options">Options
	          					
	        			</button>
	        			
					</div> -->
					
					<c:set var="conditionValue" value="${dash_conditionValue}"/>
                    <c:choose>
                   <c:when test="${empty conditionValue}">
					<div class="row text-center" id="optionsMultiDiv">
						<a href="#" onclick="javascript:sendMail('${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-envelope"></span> Send PDF 
        				</a>
        				<br/>
        				
        				<!-- <a href="#" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-print"></span> Print Document
        				</a>
        				<br/>
        				
        				<a href="#" onclick="javascript:shareShortURL('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-share-alt"></span> Short URL
        				</a>
        				<br/> -->
        				
        				<a href="#" onclick="javascript:previewCNDN('${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-th-list"></span> View / Create Credit/Debit note
        				</a>
        				<br/>
        				
        				
        				
        				<a href="#" onclick="javascript:deleteInvoice('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-remove"></span> Delete ${documentType}
        				</a>
        				
        				<c:if test="${containsProduct gt 0}">
        					<br/>
        					<!-- <a href="#" onclick="javascript:generateEWayBill('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
	          					<span class="glyphicon glyphicon-file"></span> Generate E-Way Bill
	        				</a> -->
	        				<c:if test="${invoiceDetails.invoiceValueAfterRoundOff gt 50000}">
	        					<a href="#" onclick="javascript:getEWayBills('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
		          					<span class="glyphicon glyphicon-file"></span> View / Generate E-Way Bill
		        				</a>
	        				</c:if>
	        				
        				</c:if>
        				<%-- <a href="#" onclick="javascript:xmlInvoice('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-remove"></span> XML format
        				</a> --%>
        					
        			</div>
        			</c:when>
					</c:choose>
				
				</div><!-- ./invoicePage -->
				
				
				
			</div><!-- ./card -->
		
		</div><!-- ./container -->
	</section>

</main>

<script type="text/javascript" src="<spring:url value="/resources/js/plugins.js" />"></script>
<script type="text/javascript">
	$('.resTable').riltable();
</script>
<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/viewInvoiceDetails.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashboardInvoice.js" />"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
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


