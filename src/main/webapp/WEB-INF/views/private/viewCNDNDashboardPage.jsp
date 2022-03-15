<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 



    <!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">



    <header class="insidehead">
    <a id="goBackToCNDN" href="#" class="btn-back"><i class="fa fa-angle-left"></i></a>
   
     <a class="logoText" href="./home">
      	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>	
      </a>
</header>
<html>

<body>
<main>
	<section class="block ">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container" id='loadingmessage' style='display:none;' align="middle">
		  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		<div class="container" id="divTwo">
			<div class="card">

				<div class="invoicePage"> <!-- id="secondDivId" style="display:none"> -->
				<span>
					<center><h4><b> Preview Credit/Debit Note Details </b></h4></center>
				</span>
				<c:if test="${dash_productDetails[0].cnDnType  == 'creditNote'}">
						<c:set var="conditionValue" value="Credit"/>
						</c:if>
						
						
						<c:if test="${dash_productDetails[0].cnDnType  == 'debitNote'}">
                             <c:set var="conditionValue" value="Debit"/>
                             </c:if>
					<div class="invoiceDetail">
						  <img alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-fluid" src="${pageContext.request.contextPath}/getOrgLogo">
						<div class="invoiceInfo">
							<h4>${userMaster.organizationMaster.orgName }</h4>
							<p><span>${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}</span></p>
							<p><span>GSTIN :</span>${invoiceDetails.gstnStateIdInString }</p>
							<p><span>Original Tax Invoice No:</span>${OrgInvoiceNumber}</p>
							<p><span>Original Tax Invoice Date:</span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /></p>
							<br/>
							<p><span>${conditionValue} Note No. :</span>${invoiceDetails.invoiceNumber }</p>
							<p id="place100"></p>
							
						
						</div>
						
					</div>
					<div class="row">
						<div class="col-sm-12 text-center">
						<c:if test="${dash_productDetails[0].cnDnType  == 'creditNote'}">
						<c:set var="conditionValue" value="Credit"/>
                             <h3 id="place1">CREDIT NOTE</h3>
                             <hr>
                             </c:if>
                             <c:if test="${dash_productDetails[0].cnDnType  == 'debitNote'}">
                             <c:set var="conditionValue" value="Debit"/>
                             <h3 id="place1">DEBIT NOTE</h3>
                             <hr>
                             </c:if>
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
							<p><span>State Code:</span>${customerStateCode}</p>
							<p><span>GSTIN/Unique Code:</span>${invoiceDetails.customerDetails.custGstId }</p>
							</div>
						</div>
						
					</div>
					<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          	<ul class="productdet headers">
                               <li>Description</li>
                               <li>HSN/SAC Code</li>
                               <li>Quantity</li>
                               <li>UOM</li>
                               <li class="text-right">Rate (Rs.)/UOM</li>
                              <li class="text-right">Total (Rs.)</li>
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
                            
                            
                         <c:set var="totalAmount" value="${totalAmount + (data.previousAmount - data.offerAmount)}" />
								<c:set var="cessTotalTax" value="${cessTotalTax + data.cess}" />
								<c:set var="categoryType" value="${data.categoryType}" />
								
								 
							</c:forEach>
							<c:forEach items="${dash_productDetails}" var="dashproductDetails">
                               <ul class="productdet">
                                    <li>${dashproductDetails.serviceIdInString}</li>
                                   	<li>${dashproductDetails.hsnSacCode}</li>
                                   	<li>${dashproductDetails.quantity}</li>
                                   	<li>${dashproductDetails.unitOfMeasurement}</li>
                                    <li class="text-right">${dashproductDetails.rate}</li>
                                    <li class="text-right">${dashproductDetails.amount}</li>
                                   
                              </ul>
                         </c:forEach> 
                        </div>
                        </div>
                        
                        
                        <ul class="taxtable">
	                                   	<li>&nbsp;</li>
	                                   	<li class="hlmobil">Taxable Value</li>
	                                   	<li>&nbsp;</li>
	                                   	<li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].amountAfterDiscount}"/></li>
	                                </ul>
	                                
	                                
	                                
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
	                                   	<li ><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_productDetails[0].cess}"/></li>
	                                </ul>
	                                
	                               
	                                	<ul class="taxtable">
				             			  <li>&nbsp;</li>
					  					  <li class="hlmobil">Total Taxes</li>
					  					  <li>&nbsp;</li>
				             			  <li ><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_productDetails[0].taxAmount}"/></li>
				             		 	</ul>
	                                
	                                
	                             
						  <!-- Fourth Table Starts -->
                          <div id="">
                          	 
	                          	<ul class="producttotal">
					  				<li>Total ${conditionValue } note Value</li>
			                        <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValue}"/></li>                                 
			                    </ul>
                          	 
                           <ul class="producttotal">
			                    <li>Round off</li>
			                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValueAfterRoundOff - dash_invoiceCNDNdetails[0].invoiceValue}"/></li>                                  
			                </ul>
			                <ul class="producttotal">
			                    <li>Total ${conditionValue } note Value (After Round off)</li>
			                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${dash_invoiceCNDNdetails[0].invoiceValueAfterRoundOff}"/></li>                                  
			                </ul>
		                    <ul class="producttotal">
		                        <li>Total ${conditionValue} note value Rs.(in words): </li>
		                        <li><strong>${amtInWords }</strong></li>                                 
		                    </ul>
                          	
                          	 
                          </div>	
                          <!-- Fourth Table Ends -->
						<br>
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
					<br>
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
					<br>
        			 <!-- <div class="btns">
				        <a class="btn btn-primary btn-block" href="#" onclick="javascript:previewCNDNSendMail()">Send Mail</a>				        
				    </div> -->
				
				</div>
				</div>
				</div>
				</section>
				</main>
				



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
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashboardCNDN.js"/>"></script>
