<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 


<section class="insidepages">	
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<input type="hidden" id="dash_conditionValue" name="dash_conditionValue"
	value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate"
	value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate"
	value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth"
	value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">

           
	<div class="inside-cont">
		
			 	 <header class="insidehead"  id="previewHeader">
	 					<div class="breadcrumbs" id="listHeader">
			<div class="col-md-12" id="listheader1">
	         	<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a href="#" id="goBackToInvoiceEwaybill" >  E-way Bill List </a> <span> &raquo; </span> E-way Bill Through Invoice 
	 		</div>
	 		
	 			
		</div>
		</header><br>
	 		 <div class="account-det">
	 		 <br>
	 		 <div class="container" id="secondDivId">
	 		 <div class="card">
        			<div class="row">
						<div class="invoiceDetail ">
		        				<strong>1. E-Way Bill Details</strong>
									<br><br>
									<div class="col-sm-6">
										<div class="invoiceInfo ">
											<p>
												<span><b>eWay Bill No:</b> </span>${invoiceEWayBill[0].ewaybillNo}</p>
											<p>
												<span><b>Generated By: </b></span>${invoiceEWayBill[0].gstin}</p>
											<p>
												<span><b>Mode:</b> </span>${invoiceEWayBill[0].modeOfTransportDesc}</p>
											<p>
												<span><b>Type: </b></span>${invoiceEWayBill[0].supplyType}-${invoiceEWayBill[0].subSupplyType}</p>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="invoiceInfo ">
											<p>
												<span><b>Generated Date:</b> </span>${invoiceEWayBill[0].ewaybill_date}</p>
											<p>
												<span><b>Valid Upto:</b> </span>${invoiceEWayBill[0].ewaybill_valid_upto}</p>
											<p>
												<span><b>Approx Distance: </b></span>${invoiceEWayBill[0].kmsToBeTravelled}</p>
											<p>
												<span><b>Document Details:</b> </span>${invoiceEWayBill[0].docType}-${invoiceEWayBill[0].docNo}-<fmt:formatDate
													pattern="dd/MM/yyyy" value="${invoiceEWayBill[0].docDate}" />
											</p>
										</div>
									</div>
								</div>
						</div>
					</div>        		
					
					<div class="row">
						<div class="invoiceDetail">
							<strong>2. Address Details </strong><br><br>
							<div class="col-sm-6">
								<div class="invoiceInfo ">								 
									<strong>From</strong><br>
									<b>GSTIN:</b>${gstinDetails.gstinNo }<br>
									<b>Address:</b>${gstinDetails.gstinAddressMapping.address }<br>
									${gstinDetails.gstinAddressMapping.city }<br>
									${gstinDetails.gstinAddressMapping.state } <br>
									${gstinDetails.gstinAddressMapping.pinCode }<br>								
								</div>
							</div>
							<c:if test="${invoiceDetails.invoiceFor  == 'Product'}">
								<c:if test="${invoiceDetails.billToShipIsChecked == 'No' }">
									<div class="col-sm-6">
										<div class="invoiceInfo ">
											<strong>To</strong><br>
											<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
												<b>GSTIN:</b>${invoiceDetails.customerDetails.custGstId }<br>
											</c:if>
											<b>Address:</b>${invoiceDetails.shipToAddress }<br>
											${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]<br>
											${invoiceDetails.customerDetails.pinCode }<br>
										</div>
									</div>
								</c:if>
								<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes' }">
									<div class="col-sm-6">
										<div class="invoiceInfo">
											<strong>To</strong><br>
											<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
												<b>GSTIN:</b>${invoiceDetails.customerDetails.custGstId }<br>
											</c:if>
												<b>Address:</b>${invoiceDetails.customerDetails.custAddress1 }<br>
												${invoiceDetails.customerDetails.custCity }<br>
												${invoiceDetails.customerDetails.custState }<br>
												${invoiceDetails.customerDetails.pinCode }<br>											
										</div>
									</div>
								</c:if>
							</c:if>							
						</div>
					</div>
					
					<div class="row">
						<div class="invoiceDetail">
							<strong>3. Goods Details</strong><br><br>							
                            <table id="mytable2">
								<thead>
								  <tr>
									<th>HSN Code</th>
									<th>Product Descripition</th>
									<th>Quantity</th>
									<th>Taxable Amount Rs.</th>
									<th>Tax Rate</th>
								  </tr>
								</thead>
								<tbody>
									 <c:set var="totalAmount" value="${0}"/>
									 <c:set var="cessTotalTax" value="${0}"/>
									 <c:set var="categoryType" value=""/>
									 
									 <c:forEach items="${invoiceDetails.serviceList}" var = "data">								
										<c:set var="totalTaxRate" value="${data.sgstPercentage + data.cgstPercentage + data.igstPercentage}" />
										<tr id="invoiceDet">
		                                   	<td>${data.hsnSacCode }</td>                                   		
		                                   	<td>${data.serviceIdInString }</td>
		                                   	<td>${data.quantity }</td>
		                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${data.previousAmount - data.offerAmount + data.additionalAmount}"/></td>
		                                   	<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalTaxRate }"/></td>
		                                </tr>
										<c:set var="cessTotalTax" value="${cessTotalTax + data.cess}" />
										<c:set var="totalAmount" value="${totalAmount + (data.previousAmount - data.offerAmount + data.additionalAmount )}" />
										<c:set var="sgstAmount" value="${sgstAmount + data.sgstAmount}" />
										<c:set var="cgstAmount" value="${cgstAmount + data.cgstAmount}" />
										<c:set var="igstAmount" value="${igstAmount + data.igstAmount}" />
									</c:forEach> 
								</tbody>
							</table>
							<div class="col-sm-12">
								<br>
								<!-- <div class="invoiceInfo "> -->
									<div style="width:100%">
										<div style="float:left;width:50%;">
	                            			<b>Total Tax'ble Amount : </b>${totalAmount }&nbsp;&nbsp;&nbsp;&nbsp;
	                            		</div>
	                            		<div style="float:left;width:50%;">
	                            			<b>Total Document Amount : </b>${invoiceDetails.invoiceValueAfterRoundOff }&nbsp;&nbsp;&nbsp;&nbsp;
	                            		</div>
	                            	</div><br>
	                            	<div style="width:100%">
	                            		<div style="float:left;width:25%;">
	                            			<b> CGST Amount : </b> ${cgstAmount }&nbsp;&nbsp;&nbsp;&nbsp;
	                            		</div>
		                            	<div style="float:left;width:25%;">
	                            			<b> SGST Amount : </b> ${sgstAmount }&nbsp;&nbsp;&nbsp;&nbsp;
	                            		</div>
		                            	<div style="float:left;width:25%;">
	                            			<b> IGST Amount : </b> ${igstAmount }&nbsp;&nbsp;&nbsp;&nbsp;
	                            		</div>
		                            	<div style="float:left;width:25%;">
	                            			<b> CESS Amount : </b> ${cessTotalTax }<br> 
	                            		</div>
	                            	</div>
	                            <!-- </div> -->
	                        </div> 
						</div>					
        			</div>
        			
        			<div class="row">
						<div class="invoiceDetail">
							<strong> 4. Transportation Details </strong><br><br>
							<div class="col-sm-12">
								<div class="invoiceInfo "  id="transportTable">
									<c:if
											test="${invoiceEWayBill[0].modeOfTransportDesc  == 'Rail'}">
											<c:set var="conditionValue" value="RR" />
										</c:if>
										<c:if
											test="${invoiceEWayBill[0].modeOfTransportDesc == 'Road'}">
											<c:set var="conditionValue" value="Transporter Doc" />
										</c:if>
										<c:if
											test="${invoiceEWayBill[0].modeOfTransportDesc  == 'Air'}">
											<c:set var="conditionValue" value="Airway Bill" />
										</c:if>
										<c:if
											test="${invoiceEWayBill[0].modeOfTransportDesc  == 'Ship'}">
											<c:set var="conditionValue" value="Bill of lading" />
										</c:if>

										<p>
											<span><b>${conditionValue} No: </b></span>${invoiceEWayBill[0].docNo}&nbsp;&nbsp;&nbsp;&nbsp;
											<span><b> ${conditionValue} Date :</b> </span>
											<fmt:formatDate pattern="dd/MM/yyyy" value="${invoiceEWayBill[0].docDate}" />&nbsp;&nbsp;&nbsp;&nbsp;
											 <span> <b>Transporter ID: </b></span> ${invoiceEWayBill[0].transporterId} &nbsp;&nbsp;&nbsp;&nbsp;
											 <span><b> Transporter Name : </b></span>${invoiceEWayBill[0].transporterName}
										</p>
								</div>
							</div>							
						</div>
					</div>
					
        			<div class="row">
        				<div id="vehicleTable">
        					<strong> 5. Vehicle Details </strong>  <br><br>  
        					<input type="hidden" name="location" id="location" value="${gstinDetails.gstinAddressMapping.state }">    					
			            	 <table id="vehicleDetTable">		
								<thead>
								  <tr>
									<th>Mode</th>
									<th>Vehicle / Trans<br>Doc No & Dt.</th>
									<th>From</th>
									<th>Entered Date</th>
									<th>Entered By</th>
									<th>CEWB No.<br>(If any)</th>
								  </tr>
								</thead>
								<tbody  id="vehicleDetTable">
								<tr>
											<td>${invoiceEWayBill[0].modeOfTransportDesc}</td>
											<td>${invoiceEWayBill[0].vehicleNo}</td>
											<td>${gstinDetails.gstinAddressMapping.state }</td>
											<td>${invoiceEWayBill[0].ewaybill_date}</td>
											<td>${ewaybillList[0].gstin}</td>
											<td>-</td>
											</tr>
										
			            		</tbody>
							</table>
        				</div>
        			</div>
        			<br>
        			
			
				</div>
	 		 
	 		 
	 		 
	 		 
	 		 </div></div></section>
	 		 
	 		 	<script type="text/javascript"
		src="<spring:url value="/resources/js/DashBoard/wizardDashboardInvoiceEwaybill.js" />"></script>
		 <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardviewEwayBills.js"/>"></script>
		
		
		<form name="redirectToBackFrominvoiceEwayBill" method="post">
	<input type="hidden" name="startdate" value=""> 
	<input
		type="hidden" name="enddate" value=""> 
		<input type="hidden" name="getInvType" value=""> 
		<input type="hidden" name="onlyMonth" value="">
		 <input type="hidden" name="onlyYear" value="">
		 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>