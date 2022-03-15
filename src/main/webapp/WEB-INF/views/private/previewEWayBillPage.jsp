<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
 
 <header class="insidehead" id="originalHeader">
      <a  onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="./home">
      	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>	
      </a>
 </header>
   
   <header class="insidehead" style="display:none" id="previewHeader">
   <a href="#" id="backToPreview" onclick="javascript:getEWayBills('${invoiceDetails.id }')" class="btn-back"><i class="fa fa-angle-left"></i></a>
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
			<div class="card" id="card" >
				<div class="invoicePage" id="firstDivId">
				<span><center><h4><b>E-Way Bill </b></h4></center></span>
				
				<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
				<c:set var="documentType" value=""/>
				
								<c:choose>
								  <c:when test="${invoiceDetails.documentType == 'invoice'}">
								   	<c:set var="documentType" value="Invoice" />
								  </c:when>
								  <c:when test="${invoiceDetails.documentType == 'billOfSupply'}">
								   <c:set var="documentType" value="Bill Of Supply" />
								  </c:when>
								  <c:when test="${invoiceDetails.documentType == 'invoiceCumBillOfSupply'}">
								   	<c:set var="documentType" value="Invoice cum Bill Of Supply" />
								  </c:when>
								  <c:otherwise>
								   	<c:set var="documentType" value="DOCUMENT" />
								  </c:otherwise>
								</c:choose>
					<div class="row">
						<div class="col-sm-12 text-center">
                             <h5>Details for ${documentType} Number - ${invoiceDetails.invoiceNumber }</h5>
                             <hr>
                        </div>
					</div>
					
					<div class="row text-center">
						
        				<a href="#" onclick="javascript:generateEWayBill('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
	          					<span class="glyphicon glyphicon-file"></span> Generate E-Way Bill
	        				</a>
       				
        			</div>
        			<br>
        			
					<c:if test="${empty ewaybillList }">
						<div class="text-center text-danger">
							No E-Way Bill is created against this ${documentType}.
						</div>
					</c:if>
					
					<c:set var="cnCount" value="${0}"/>
					<c:set var="dnCount" value="${0}"/>
					<c:if test="${not empty ewaybillList }">
						
						
								<div class="accordion no-css-transition mb0">
			                        <div class="accordion_in acc_active">
			                            <div class="acc_head">E-Way Bills</div>
			                            <div class="acc_content">
			                            <ul class="list-group">
										  
										   <c:forEach items="${ewaybillList }" var="data">
			                           			
				                            		<c:set var="cnCount" value="${cnCount + 1}" />
				                            		
				                            		<li class="list-group-item">${cnCount }. <a href="#" onclick="javascript:getEWayBillDetails('${data.id }');">${data.ewaybillNo }</a>  
				                            		<c:if test="${data.ewaybillStatus == 'CANEWB'}">
				                            		<span id="check" class="glyphicon glyphicon-remove-circle" style="float:right;"></span>
				                            		</c:if>
				                            		</li>
				                            </c:forEach> 
										</ul>
			                            
			                            </div>
			                        </div>
			                    </div>
							
					</c:if> 
					
					
				</div>
	
				<div class="invoicePage" id="secondDivId" style="display:none">
				<div class="row">
					<div class="invoiceDetail">
							
								<div id="etable">
								 <h5> 1. E-Way Bill Details </h5> <br>
								</div>
					</div>
				</div>
						<div class="row">
						<div class="invoiceDetail">
						<h5> 2. Address Details </h5>  <br>
						<div class="col-sm-6">
							<div class="invoiceInfo ">
							 
							<h6>From</h6>
							<p><span>GSTIN:</span>${gstinDetails.gstinNo }</p>
							<p><span>Address:</span>${gstinDetails.gstinAddressMapping.address }</p>
							<p><span></span>${gstinDetails.gstinAddressMapping.city }</p>
							<p><span></span>${gstinDetails.gstinAddressMapping.state } </p>
							<p><span></span>${gstinDetails.gstinAddressMapping.pinCode }</p>
							
							</div>
						</div>
						<c:if test="${invoiceDetails.invoiceFor  == 'Product'}">
							<c:if test="${invoiceDetails.billToShipIsChecked == 'No' }">
								<div class="col-sm-6">
									<div class="invoiceInfo ">
									<h6>To</h6>
									<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
									<p><span>GSTIN:</span>${invoiceDetails.customerDetails.custGstId }</p>
									</c:if>
									<p><span>Address:</span>${invoiceDetails.shipToAddress }</p>
									<p><span></span>${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]</p>
									<p><span></span>${invoiceDetails.customerDetails.pinCode }</p>
									</div>
								</div>
							</c:if>
							<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes' }">
								<div class="col-sm-6">
									<div class="invoiceInfo">
									<h6>To</h6>
									<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
										<p><span>GSTIN:</span>${invoiceDetails.customerDetails.custGstId }</p>
										</c:if>
										<p><span>Address:</span>${invoiceDetails.customerDetails.custAddress1 }</p>
										<p><span></span>${invoiceDetails.customerDetails.custCity }</p>
										<p><span></span>${invoiceDetails.customerDetails.custState } </p>
										<p><span></span>${invoiceDetails.customerDetails.pinCode }</p>
										
									</div>
								</div>
							</c:if>
						</c:if>
						</div>
					</div>
						
					<!-- Start -->
					<div class="row">
				<!-- 	<div class="invoiceTable"> -->
			          	 
			          	 <div class="invoiceDetail">
								<!-- <div id="invoiceTable"> -->
								<h5> 3. Goods Details </h5>  <br>
								<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          
                          	<ul class="e-productdet headers">
                               <li>HSN Code</li>
                               <li>Product Descripition</li>
                               <li>Quantity</li>
                               <li>Taxable Amount Rs.</li>
                               <li>Tax Rate</li>
                            </ul>
                            <c:set var="totalAmount" value="${0}"/>
							<c:set var="cessTotalTax" value="${0}"/>
							<c:set var="categoryType" value=""/>
                            	 
                            	   <c:forEach items="${invoiceDetails.serviceList }" var = "data">
										<c:set var="totalTaxRate" value="${data.sgstPercentage + data.cgstPercentage + data.igstPercentage}" />
										<ul class="e-productdet" id="invoiceDet">
											<li>${data.hsnSacCode }</li>
		                                  	<li>${data.serviceIdInString }</li>
		                                   	<li>${data.quantity }</li>
		                                   	<li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.previousAmount - data.offerAmount + data.additionalAmount}"/></li>
		                                   	<li class="text-right"><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalTaxRate }"/></li>
		                                   	                                   
		                                </ul>
		                                
		                                <c:set var="cessTotalTax" value="${cessTotalTax + data.cess}" />
										<c:set var="totalAmount" value="${totalAmount + (data.previousAmount - data.offerAmount + data.additionalAmount )}" />
										<c:set var="sgstAmount" value="${sgstAmount + data.sgstAmount}" />
										<c:set var="cgstAmount" value="${cgstAmount + data.cgstAmount}" />
										<c:set var="igstAmount" value="${igstAmount + data.igstAmount}" />
										
									</c:forEach>
                            	<br>
                            	 </div>
                            </div>
                            <!-- </div>
                            </div>
                            	<div class="invoiceDetail">  -->
                            	<div class="col-sm-6">
							<div class="invoiceInfo ">
                            <p><span>Total Tax'ble Amount : </span>${totalAmount } <br/> <span>Total Document Amount : </span>${invoiceDetails.invoiceValueAfterRoundOff } <br/> <span> CGST Amount : </span> ${cgstAmount } <br/> 
                            <span> SGST Amount : </span> ${sgstAmount } <br/> <span> IGST Amount : </span>${igstAmount } <br/> <span> CESS Amount : </span> ${cessTotalTax }</p>
                            </div>
                            </div> 
                             </div>	
						
			         <!--  	</div> -->
			          	 </div>
					 
					  <div class="row">
							<div class="invoiceDetail">
							 <h5> 4. Transportation Details </h5> <br>
							<div class="col-sm-6">
							<div class="invoiceInfo "  id="transportTable">
								
								 </div>
								 </div>
								
							</div>
						</div>
						 <div class="row">
						<!-- 	<div class="invoiceTable">	 -->				
						<div id="vehicleTable">
								
								<h5> 5. Vehicle Details </h5>
								<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          <input type="hidden" name="location" id="location" value="${gstinDetails.gstinAddressMapping.state }">
                          
                          	<ul class="transportdet headers">
                               <li>Mode</li>
                               <li>Vehicle / Trans <br> Doc No & Dt.</li>
                               <li>From</li>
                               <li>Entered Date</li>
                               <li>Entered By</li>
                               <li>CEWB No.<br> (If any)</li>
                            </ul>
                            	 <ul class="transportdet" id="vehicleDet">
                            	 </ul>
                            </div>
                            </div>
						</div>
					<!-- 	</div> -->
						</div>
					<!-- End -->	
					
					<br>
					
					<div class="row text-center" id="optionsMultiDiv">
        			<!--  <div class="btns"> 
				        <a href="#" onclick="javascript:sendEwayBillPdf('${invoiceDetails.gstnStateIdInString }', '${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-envelope"></span> Send eWay Bill
        				</a> -->
        				 <a href="#" onclick="javascript:sendEwayBillPdf('${invoiceDetails.gstnStateIdInString }', '${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-envelope"></span> Send eWay Bill
        				</a>
        				
        				 
        				<a href="#" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="btn btn-info" style="margin:5px 0 0 0">
	          					<span class="glyphicon glyphicon-remove"></span> Cancel
	        				</a>
	        			
        				<br/>
        				
				    </div>
							
			</div>
			</div><!-- ./card -->
		
		</div><!-- ./container -->
	</section>
</main>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/viewEWayBills.js" />"></script>

<form name="downloadEWayBill" id="downloadEWayBill" method="post">
    <input type="hidden" name="invoiceId" value="">
    <input type="hidden" name="gstin" value="">
    <input type="hidden" name="ewaybillNo" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="cancelEwayBill" id="cancelEwayBill" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="ewayBillId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

