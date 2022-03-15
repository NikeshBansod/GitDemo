
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="insidepages">	
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="container" id="loadingmessage" style="display:none;" align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
           
	<div class="inside-cont">
		<div class="breadcrumbs">
			 <header class="insidehead" id="originalHeader">
	         	<a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" >Document History</a> <span>&raquo;</span> E-Way Bill
	 		 </header>
	 		 <header class="insidehead" id="previewHeader" style="display:none">
	            <a href="#" id="backToPreview" onclick="javascript:getEWayBills('${invoiceDetails.id }')" >E-Way Bill</a> <span>&raquo;</span> Preview E-Way Bill Details
			 </header>
		</div>
		<div class="account-det">
			<div class="container" id="firstDivId">        		
        		<div class="card">
        			<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
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
					<div class="det-row">						
						<div class="det-row-col-full text-center ">
                        	Details for ${documentType} Number - ${invoiceDetails.invoiceNumber}
                        </div>
                    </div>    
                    
					<c:if test="${empty ewaybillList}">
						<div class="det-row">
							<div class="det-row-col-full text-center text-danger">
								No E-Way Bill is created against this ${documentType}.
							</div>
						</div>
					</c:if>
					
					<div class="row text-center">						
        				<a href="#" onclick="javascript:generateEWayBill('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">
	          					<span class="glyphicon glyphicon-file"></span> Generate E-Way Bill
	        			</a>       				
        			</div>
					<br>
				
					<c:set var="cnCount" value="${0}"/>
					<c:set var="dnCount" value="${0}"/>
					<c:if test="${not empty ewaybillList}">
						<div class="accordion no-css-transition mb0">
	                        <div class="accordion_in acc_active">
	                            <div class="acc_head">E-Way Bills</div>
	                            <div class="acc_content" >
		                            <ul class="list-group">
										<c:forEach items="${ewaybillList}" var="data">	
											<c:set var="cnCount" value="${cnCount + 1}" />	
											<li class="list-group-item">${cnCount }. <a href="#" onclick="javascript:getEWayBillDetails('${data.id }');">${data.ewaybillNo }</a>	                           			
				                           	<c:if test="${data.ewaybillStatus == 'CANEWB'}">
		                            			<span id="check" class="glyphicon glyphicon-remove-circle" style="float:right;"></span>
		                            		</c:if>
			                            </c:forEach>
									</ul>
	                            </div>
	                        </div>
	                    </div>
					</c:if>
        		</div>
				
        	</div>
        	       	
        	<div class="container" id="secondDivId" style="display:none">
        		<div class="card">
        			<div class="row">
						<div class="invoiceDetail ">
		        			<div id="etable">
								<strong>1. E-Way Bill Details</strong><br><br>						
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
								<tbody>
								
			            		</tbody>
							</table>
        				</div>
        			</div>
        			<br>
        			<div class="row text-center" id="optionsMultiDiv">
	        			<a href="#" onclick="javascript:downloadEWayBills('${invoiceDetails.gstnStateIdInString }', '${invoiceDetails.id }');" class="sim-button button5" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-download"></span> Download
        				</a>
        				
        				<a href="#" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">
	          				<span class="glyphicon glyphicon-remove"></span> Cancel
	        			</a>	      				
				    </div>
			
				</div>
        	</div>
		</div>
	</div>
</section> 
	
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/generateInvoice/viewEWayBills.js" />"></script>

<form name="downloadEWayBill" id="downloadEWayBill" method="post">
    <input type="hidden" name="invoiceId" value="">
    <input type="hidden" name="gstin" value="">
    <input type="hidden" name="ewaybillNo" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="cancelEwayBill" id="cancelEwayBill" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="ewayBillId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
	
<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
		