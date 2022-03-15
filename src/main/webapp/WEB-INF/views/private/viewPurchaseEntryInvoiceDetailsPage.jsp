<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
 <header class="insidehead">
      <a href="./getMyPurchaseEntryPage" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
<main>
	<section class="block ">
		<div class="container" id='loadingmessage' style='display:none;' align="center">
		  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		
		<div class="container" id="divTwo">
			<div class="card">
			 <a class="downToLast" href="#" onclick="scrollSmoothToBottom()"  class="btn-back"><i class="fa fa-angle-double-down"></i></a>
			<span><center><h4><b>Purchase Entry Details</b></h4></center></span>
			    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			   
				<div class="invoicePage" >
					
					<div class="invoiceDetail">
						<!-- <img src="images/logo.png"> -->
						 <%--  <img alt="No Logo Uploaded" class="img-fluid" src="${pageContext.request.contextPath}/getOrgLogo"> --%>
						<div class="invoiceInfo">
							  <h4>${purchaseEntryDetails.supplierName}</h4>
							  <p><span>${supplierCompleteAddress}</span></p>
							  <p><span>GSTIN :</span>${purchaseEntryDetails.supplierGstin}</p>
							  <p><span>Purchase Date : </span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.purchaseDate}" /></p>
							  <p><span>Invoice Number : </span>${purchaseEntryDetails.invoiceNumber}</p>
							  <p><span>Invoice Period :</span>
							  	<c:if test="${not empty purchaseEntryDetails.invoicePeriodFromDate != 'NULL' && not empty purchaseEntryDetails.invoicePeriodToDate != 'NULL' }">				  	
							  	   <fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.invoicePeriodFromDate}" /> TO 
							  	   <fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.invoicePeriodToDate}" />
							 	</c:if>
							 	<c:if test="${not empty purchaseEntryDetails.invoicePeriodFromDate == 'NULL' && not empty purchaseEntryDetails.invoicePeriodToDate == 'NULL' }">				  	
							  	   NA
							 	</c:if>
							  </p>			  									
							  <p><span>Reverse Charge Applicable : </span>${purchaseEntryDetails.reverseChargeApplicable}</p>	
							  <c:if test="${purchaseEntryDetails.reverseChargeApplicable  == 'Yes'}">	  
							 	<p><span>Supplier Document/Invoice No : </span>${purchaseEntryDetails.supplierDocInvoiceNo}</p>	
							  	<p><span>Supplier Document/Invoice Date : </span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${purchaseEntryDetails.supplierDocInvoiceDate}" /></p>
							  </c:if>
						</div>						
					</div>
							
					<div class="row">
						<div class="col-sm-12 text-center">
                             <h3>Purchase Document</h3>
                             <hr>
                        </div>
						<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
								<h4>Bill To</h4>
								<p><span>Name:</span>${userMaster.organizationMaster.orgName}</p>
								<p><span>Address:</span>${gstinDetails.gstinAddressMapping.address}</p>
								<p><span>City:</span>${gstinDetails.gstinAddressMapping.city}</p>
								<p><span>State:</span>${gstinDetails.gstinAddressMapping.state}</p>
								<p><span>State Code:</span>${supplierStateCode}</p>
								<p><span>GSTIN/Unique Code:</span>${gstinDetails.gstinNo}</p>
							</div>
						</div>
						<c:if test="${purchaseEntryDetails.invoiceFor  == 'Product'}">
						</c:if>
						<c:if test="${purchaseEntryDetails.billToShipIsChecked == 'No' }">
							<div class="col-sm-6">
									<div class="invoiceInfo">
									<h4>Ship To </h4>
									<p><span>Name:</span>${userMaster.organizationMaster.orgName}</p>
									<p><span>Address:</span>${purchaseEntryDetails.purchaserShippingAddress}</p>
									<%-- <p><span>City:</span>${userMaster.organizationMaster.city}</p>
									<p><span>State:</span>${userMaster.organizationMaster.state}</p>
									<p><span>State Code:</span>${userMaster.organizationMaster.stateCode}</p> --%>
									</div>
							</div>
						</c:if>
						<c:if test="${purchaseEntryDetails.billToShipIsChecked == 'Yes' }">
							<div class="col-sm-6">
								<div class="invoiceInfo">
									<h4>Ship To</h4>
									<p><span>Name:</span>${userMaster.organizationMaster.orgName}</p>
									<p><span>Address:</span>${gstinDetails.gstinAddressMapping.address}</p>
									<%-- <p><span>City:</span>${userMaster.organizationMaster.city}</p>
									<p><span>State:</span>${userMaster.organizationMaster.state}</p>
									<p><span>State Code:</span>${userMaster.organizationMaster.stateCode}</p> --%>
								</div>
							</div>
						</c:if>
					</div>
					
					<!-- Place of Supply -->
					<div class="row">
						<div class="col-sm-12">
							<div class="invoiceInfo invoiceFirst">Place Of Supply : ${purchaseEntryDetails.placeOfSupplyName} [${purchaseEntryDetails.placeOfSupplyCode}]</div>
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
							
							<c:forEach items="${purchaseEntryDetails.serviceList }" var = "data">
								<ul class="productdet">
                                   	<li>${data.serviceIdInString }</li>
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
                           
                            <c:if test="${not empty purchaseEntryDetails.addChargesList}">
                            	<c:set var="containsAdditionalCharges" value="YES"/>
                            	<ul class="taxtable">
				                   	<li>&nbsp;</li>
				                   	<li class="hlmobil">Add :Additional Charges</li>
				                   	<li>&nbsp;</li>
				                   	<li>&nbsp;</li>
			                    </ul>
			                    
			                    <c:set var="addChargeAmount" value="${0}"/>
			                    <c:forEach items="${purchaseEntryDetails.addChargesList }" var="chg">
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
				             			  <li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.totalTax}"/></li>
				             		 	</ul>
	                                </c:if>
	                                
	                                <c:if test="${containsAdditionalCharges == '' }">
	                                	<ul class="taxtable">
				             			  <li>&nbsp;</li>
					  					  <li class="hlmobil">Total Taxes (B)</li>
					  					  <li>&nbsp;</li>
				             			  <li><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.totalTax}"/></li>
				             		 	</ul>
	                                
	                                </c:if>
                                </div>
                          <!-- Third Table Ends -->
                                
                          <!-- Fourth Table Starts -->
                          <div id="">
                          	 <c:if test="${containsAdditionalCharges == 'YES' }">
	                          	<ul class="producttotal">
					  				<li>Total Document Value (A+B+C)</li>
			                        <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValue}"/></li>                                 
			                    </ul>
                          	 </c:if>
                          	 <c:if test="${containsAdditionalCharges == '' }">
                          	 	<ul class="producttotal">
					  				<li>Total Document Value (A+B)</li>
			                        <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValue}"/></li>                                 
			                    </ul>
                          	 </c:if>
                          	 
                          	
                             <ul class="producttotal">
			                    <li>Round off</li>
			                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValueAfterRoundOff - purchaseEntryDetails.invoiceValue}"/></li>                                  
			                </ul>
			                <ul class="producttotal">
			                    <li>Total Document Value (After Round off)</li>
			                    <li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${purchaseEntryDetails.invoiceValueAfterRoundOff}"/></li>                                  
			                </ul>
		                    <ul class="producttotal">
		                        <li>Total Document value Rs.(in words): </li>
		                        <li><strong>${amtInWords }</strong></li>                                 
		                    </ul>
                          	
                          	 
                          </div>
                          <!-- Fourth Table Ends -->
							
							
					</div>
					<br>
						
					
					<div class="row">
					 	<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
							<p><span>Customer Purchase Order:</span>${purchaseEntryDetails.poDetails }</p>
							
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
					
					<c:if test="${not empty purchaseEntryDetails.footerNote }">
						<hr>
						<div class="invoiceInfo text-justify" id="">
                            <p><span id="footerNoteDiv">${purchaseEntryDetails.footerNote }</span></p>
                        </div>
					
					</c:if>	
						
					<hr>
					
					<div class="invoiceInfo">
						
						<h4>Declaration</h4>
						<p><span>I) Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.</span></p>
						<p><span>II) The normal terms governing the above sale are printed overleaf. </span></p>
						<p><span>E&OE</span></p>
					</div>
					<br>
					<!-- <div class="row text-center" id="optionsDiv" style="margin:5px 0 0 0;bottom: 0px;">
						<button class="btn btn-info" style="" value="Options">Options
	          					
	        			</button>
					</div> -->
					<div  class="row text-right">
					<a class="downToUp" href="#" onclick="scrollSmoothToTop()" class="btn-back"><i class="fa fa-angle-double-up"></i></a></div>
					<div class="row text-center"  id="optionsMultiDiv">
						<%-- <a href="#" onclick="javascript:sendMail('${purchaseEntryDetails.purchaseEntryDetailsId }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-envelope"></span> Send PDF 
        				</a>
        				<br/> 
        				
        				<a href="#" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-print"></span> Print Document
        				</a>
        				<br/>
        				
        				
        				 <a href="#" onclick="javascript:previewCNDN('${purchaseEntryDetails.purchaseEntryDetailsId }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-th-list"></span> View / Create Credit/Debit note
        				</a>
        				<br/> 
        				
        				<a href="#" onclick="javascript:shareShortURL('${purchaseEntryDetails.purchaseEntryDetailsId}');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-share-alt"></span> Short URL
        				</a>
        				<br/>--%>
        				
        				<a href="#" onclick="javascript:deleteInvoice('${purchaseEntryDetails.purchaseEntryDetailsId}');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-remove"></span> Delete Document
        				</a>
        					
        			</div>
				
				</div><!-- ./invoicePage -->
				
				
			</div><!-- ./card -->
		
		</div><!-- ./container -->
	</section>

</main>

<script type="text/javascript" src="<spring:url value="/resources/js/plugins.js" />"></script>
<script type="text/javascript">
	$('.resTable').riltable();
</script>
<script type="text/javascript" src="<spring:url value="/resources/js/purchaseEntryInvoice/viewPurchaseEntryInvoiceDetails.js" />"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageShortUrl" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="orgId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>


