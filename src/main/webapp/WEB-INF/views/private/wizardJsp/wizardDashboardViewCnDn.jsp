<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 




    <!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">



   
<html>

<body>
<main>
	<section class="insidepages">
	<div class="breadcrumbs">
				 <header class="insidehead"  id="previewHeader">
	 					<div class="breadcrumbs" id="listHeader">
			<div class="col-md-12" id="listheader1">
	         	<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a href="#" id="goBackToCNDN" > Credit/Debit Note List </a> <span> &raquo; </span> Credit/Debit Note
	 		</div>
	 		
	 			
		</div>
		</header>
		 		 
			</div>
			
       		<div class="account-det">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id='loadingmessage' style='display:none;' align="middle">
		  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="container" id="divTwo">
			<div class="card">

				<div class="invoicePage"> <!-- id="secondDivId" style="display:none"> -->
				<span>
				<h4><b> Preview Credit/Debit Note Details </b></h4>
				</span>
				<c:if test="${dash_productDetails[0].cnDnType  == 'creditNote'}">
						<c:set var="conditionValue" value="Credit"/>
						</c:if>
						
						
						<c:if test="${dash_productDetails[0].cnDnType  == 'debitNote'}">
                             <c:set var="conditionValue" value="Debit"/>
                             </c:if>
					<div class="invoiceDetail">
						  	<div class="upload-logo"><img  alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-responsive" src="${pageContext.request.contextPath}/wGetOrgLogo" width="143" height="174"></div>
						<div class="invoiceInfo">
							<h4>${userMaster.organizationMaster.orgName }</h4>
							<b><p><span></b>${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}</span></p>
							<b><p><span>GSTIN :</span></b>${invoiceDetails.gstnStateIdInString }</p>
							<b><p><span>Original Tax Invoice No:</span></b>${OrgInvoiceNumber}</p>
							<b><p><span>Original Tax Invoice Date:</span></b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /></p>
							
							<b><p><span>${conditionValue} Note No. :</span>${invoiceDetails.invoiceNumber }</p></b>
							<b><p id="place100"></p></b>
							
						
						</div>
						
					</div>
					<div class="row">
						<div class="col-sm-12 text-center">
						<c:if test="${dash_productDetails[0].cnDnType  == 'creditNote'}">
						<c:set var="conditionValue" value="Credit"/>
                             <h3 id="place1"><font color="blue"><b>CREDIT NOTE</b></font></h3>
                             <hr>
                             </c:if>
                             <c:if test="${dash_productDetails[0].cnDnType  == 'debitNote'}">
                             <c:set var="conditionValue" value="Debit"/>
                             <h3 id="place1"> <font color="blue"><b>DEBIT NOTE</b></font></h3>
                             <hr>
                             </c:if>
                        </div>
						<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
							<strong><u><b>Bill To</b></u></strong><br>
							<b>Name:&nbsp;&nbsp;&nbsp;</b>${invoiceDetails.customerDetails.custName }<br>
							<b>Address:&nbsp;&nbsp;&nbsp;</b>${invoiceDetails.customerDetails.custAddress1 }<br>
							<b>City:&nbsp;&nbsp;&nbsp;</b>${invoiceDetails.customerDetails.custCity }<br>
							<b>State:&nbsp;&nbsp;&nbsp;</b>${invoiceDetails.customerDetails.custState } 
								<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
								[ ${invoiceDetails.customerDetails.custGstinState } ]
								</c:if><br>
							
							<b>State Code:</b>${customerStateCode}<br>
							<br>
							</div>
						</div>
						
					</div>
					<div class="tablemain2">
					<div id="">
						<table id="mytable2">
							 
								<thead>
								  <tr>
									<th>Description</th>
									<th>HSN/SAC Code</th>
									<th>Quantity</th>
									<th>UOM</th>
									<th class="text-right">Rate (Rs.)/UOM</th>
									<th class="text-right">Total (Rs.)</th>
								  </tr>
								  
								</thead>
							
						
						 <c:set var="totalAmount" value="${0}"/>
							<c:set var="cessTotalTax" value="${0}"/>
							
							<c:set var="categoryType" value=""/>
							<c:set var="containsDiffPercentage" value="none"/>
							<c:set var="containsProduct" value="${0}"/>
							<c:forEach items="${invoiceDetails.serviceList }" var = "data">
								
								<c:if test="${data.billingFor == 'Product'}">
									<c:set var="containsProduct" value="${containsProduct + 1}"   />
								</c:if>
                            
                            
                         <c:set var="totalAmount" value="${totalAmount + (data.previousAmount - data.offerAmount)}" />
								<c:set var="cessTotalTax" value="${cessTotalTax + data.cess}" />
								<c:set var="categoryType" value="${data.categoryType}" />
								
								 
							 </c:forEach>
							
                         
						
							<c:forEach items="${dash_productDetails}" var="dashproductDetails" >
							<tbody>
                               <thread class="productdet">
                               <tr>
                                    <td>&nbsp;${dashproductDetails.serviceIdInString}</td>
                                    
                                  	<td>&nbsp;${dashproductDetails.hsnSacCode}</td>
                                   	
                                   	<td>&nbsp;${dashproductDetails.quantity}</td>
                                  	
                                  	<td>&nbsp;${dashproductDetails.unitOfMeasurement}</td>
                                    
                                    <td>&nbsp;${dashproductDetails.rate}</td>
                                   <td>&nbsp;${dashproductDetails.amount}</td>
                                   </tr>
                              </thread></tbody></c:forEach>
                            
                          <tbody>
                          <tr>
                          <td>&nbsp;</td>
	                                   	
	                                   	<td><b>Taxable Value</b></td>
	                                   	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].amountAfterDiscount}"/></td>
	                               
	                                </tr></tbody>
	                                
	                                
	                                
                        <c:set var="containsDiffPercentage" value="${0}" />
						  <div id="">	
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' ||  serviceData.categoryType =='CATEGORY_WITH_UGST_CGST'}">
									<%-- 	<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'cgst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="cgstLoop">
													  <c:if test="${cgstLoop.count eq 1}">
														
														<thead class="taxtable"><tr>
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">Central Tax</td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </tr>
					                                    </thead>
													 </c:if>
													 <c:if test="${cgstLoop.count gt 1}">
														<tbody><thead class="taxtable">
						                                    <tr>
						                                    <td>&nbsp;</td>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr></thead></tbody>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' ||  serviceData.categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<%-- <c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'cgstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="cgstLoop">
													  <c:if test="${cgstLoop.count eq 1}">
														<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<td class="taxtable">
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">Central Tax<span style="color:red">*</span></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></li>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></li>
					                                    </ul>
													 </c:if>
													 <c:if test="${cgstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<td class="taxtable">
						                                    <td>&nbsp;</li>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' }">
										<%-- <c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'sgst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="sgstLoop">
													  <c:if test="${sgstLoop.count eq 1}">
														<td class="taxtable">
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">State Tax</td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </ul>
													 </c:if>
													 <c:if test="${sgstLoop.count gt 1}">
														<ul class="taxtable">
						                                    <td>&nbsp;</td>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_SGST_CSGT' }">
										<%-- <c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'sgstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="sgstLoop">
													  <c:if test="${sgstLoop.count eq 1}">
													  	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">State Tax<span style="color:red">*</span></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                    </ul>
													 </c:if>
													 <c:if test="${sgstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <td>&nbsp;</td>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_IGST' ||  serviceData.categoryType =='CATEGORY_EXPORT_WITH_IGST'}">
										<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'igst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="igstLoop">
													  <c:if test="${igstLoop.count eq 1}">
														<tbody><tr>
						                                   	<td>&nbsp;</td>
						                                   	<td><b>Integrated Tax</b></td>
						                                   	<td>&nbsp;</td>
						                                    <td >&nbsp;</td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </tr></tbody>
													 </c:if>
													 <c:if test="${igstLoop.count gt 1}">
														<tbody><tr>
						                                    <td>&nbsp;</td>
						                                    <td >&nbsp;</td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </tr>
					                                   </tbody>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_IGST' ||  serviceData.categoryType =='CATEGORY_EXPORT_WITH_IGST'}">
										<%-- <c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'igstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="igstLoop">
													  <c:if test="${igstLoop.count eq 1}">
													  	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">Integrated Tax<span style="color:red">*</span></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                    </ul>
													 </c:if>
													 <c:if test="${igstLoop.count gt 1}">
													 	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <td>&nbsp;</td>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${((0.65)*(data.value * data.key)) div (100) }"/></td>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_UGST_CGST'}">
										<%-- <c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'ugst'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="ugstLoop">
													  <c:if test="${ugstLoop.count eq 1}">
														<ul class="taxtable">
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">Union Territory Tax</td>
						                                   	<td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </ul>
													 </c:if>
													 <c:if test="${ugstLoop.count gt 1}">
														<ul class="taxtable">
						                                    <td>&nbsp;</td>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
									<c:if test="${categoryType =='CATEGORY_WITH_UGST_CGST'}">
									<%-- 	<c:forEach var="gstMap" items="${gstMap}">
											<c:if test="${gstMap.key == 'ugstDiffPercent'}">
												<c:forEach items="${gstMap.value }" var="data" varStatus="ugstLoop">
													  <c:if test="${ugstLoop.count eq 1}">
													  	<c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                   	<td>&nbsp;</td>
						                                   	<td class="hlmobil">Union Territory Tax<span style="color:red">*</span></td>
						                                   	<td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                    </ul>
													 </c:if>
													 <c:if test="${ugstLoop.count gt 1}">
														 <c:set var="containsDiffPercentage" value="${containsDiffPercentage + 1}"   />
														<ul class="taxtable">
						                                    <td>&nbsp;</td>
						                                    <td class="hidemob">&nbsp;</td>
						                                    <td>${data.key}% of 65% on <fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.value}"/></td>
						                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${(data.value * data.key) div (100) }"/></td>
					                                   </ul>
													 </c:if> 
												</c:forEach>
											</c:if>
										   
										</c:forEach> --%>
									
									</c:if>
								
									<tbody><tr>
	                                   	<td>&nbsp;</td>
	                                   	<td><b> Cess</b></td>
	                                   	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	                                   	<td ><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_productDetails[0].cess}"/></td>
	                                </tr></tbody>
	                                
	                               
	                                	<tbody><tr>
				             			  <td>&nbsp;</td>
					  					  <td><b>Total Taxes</b></td>
					  					  <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
				             			  <td ><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_productDetails[0].taxAmount}"/></td>
				             		 	</tr>
	                                </tbody>
	                                
	                               <%--  <c:if test="${containsAdditionalCharges == '' }">
	                                	<ul class="taxtable">
				             			  <td>&nbsp;</td>
					  					  <td class="hlmobil">Total Taxes (B)</td>
					  					  <td>&nbsp;</td>
				             			  <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dashproductDetails.taxAmount}"/></td>
				             		 	</ul>
	                                
	                                </c:if> --%>
                                </div>
                          <!-- Third Table Ends -->
                                   <%-- <ul class="taxtable">
	                                   	<td>&nbsp;</td>
	                                   	<td class="hlmobil">Taxable Value</li>
	                                   	<td>&nbsp;</li>
	                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].amountAfterDiscount}"/></li>
	                                </ul>
	                                <ul class="taxtable">
	                                   	<li>&nbsp;</li>
	                                   	<li class="hlmobil">Integrated Tax</li>
	                                   	<li>&nbsp;</li>
	                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValueAfterRoundOff}"/></li>
	                                </ul>
	                                <ul class="taxtable">
	                                   	<li>&nbsp;</li>
	                                   	<li class="hlmobil">Total Taxes</li>
	                                   	<li>&nbsp;</li>
	                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].totalTax}"/></li>
	                                </ul>
	 --%>
						  <!-- Fourth Table Starts -->
                          <div id="">
                          	 
	                          	<tbody class="producttotal"><tr>
	                          	 
					  				<td><b>Total ${conditionValue } note Value</b></td>
					  				   <td>&nbsp;</td> <td>&nbsp;</td>
					  				    <td>&nbsp;</td>
		                         <td>&nbsp;</td>
			                        <td ><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValue}"/></td>                                 
			                    </tr></tbody>
                          	 
                           <tbody class="producttotal"><tr>
			                    <td><b>Round off</b></td>
			                     <td>&nbsp;</td> 
			                     <td>&nbsp;</td>
		                         <td>&nbsp;</td>
		                         <td>&nbsp;</td>
			                    <td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValueAfterRoundOff - dash_invoiceCNDNdetails[0].invoiceValue}"/></td>                                  
			               </tr> </tbody>
			                <tbody class="producttotal"><tr>
			                    <td><b>Total ${conditionValue } note Value (After Round off)</td>
			                     <td>&nbsp;</td>
			                      <td>&nbsp;</td>
		                         <td>&nbsp;</td>
		                         <td>&nbsp;</td>
			                    <td ><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValueAfterRoundOff}"/></td>                                  
			                </tr></tbody>
		                    <tbody class="producttotal"><tr>
		                        <td><b>Total ${conditionValue} note value Rs.(in words): </b></td>
		                         <td><strong>${amtInWords }</strong></td>
		                           <td>&nbsp;</td>
		                             <td>&nbsp;</td>  
		                              <td>&nbsp;</td>
		                                <td>&nbsp;</td>
		                                </tr>                                
		                    </tbody></table></div></div>
                          	
                          	 
                          </div>	<br>
                          
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
						 	<div class="add-charges">
							<!-- <h4>Footer Note</h4> -->                            
                        	</div>
				    </div><br>
        			 <!-- <div class="btns">
				        <a class="btn btn-primary btn-block" href="#" onclick="javascript:previewCNDNSendMail()">Send Mail</a>				        
				    </div> -->
				
				</div>
				</div>
				</div></div>
				</section>
				</main>
				
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashBoardDate.js" />"></script> --%>
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardCNDN.js"/>"></script>

</body>
</html>
<!-- Dashboard backToCall form -->

<form name="redirectToBackFromCNDN" method="post">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
