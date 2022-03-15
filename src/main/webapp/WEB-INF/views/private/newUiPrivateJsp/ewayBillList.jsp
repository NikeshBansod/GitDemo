<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<section class="block">
	<div class="container">	
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="loader"></div>	 	   
	   	<div class="col-xs-12">
			 <div class="brd-wrap" id="breadcumheader">
				 <a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" ><strong>Document Details</strong></a> &raquo; <strong>E-Way Bill List</strong>
			 </div>
			 	
		 	<div class="brd-wrap" id="headerPreview" style="display:none">
			  <a  href="#" id="backToPreview"><strong>E-Way Bill List</strong></a> &raquo; <strong>E-Way Bill Details</strong>
	 		</div>
	 		<div id="previewButtons" style="display:none" >
		 		<br>
				<div class="row">
					<div class="col-md-12 button-wrap"  id="optionsMultiDiv">
						<div style="float:right;">
							<c:choose>
								<c:when test="${loggedInThrough == 'MOBILE'}">	
									<button type="button" onclick="javascript:sendEwayBillPdf('${invoiceDetails.gstnStateIdInString }', '${invoiceDetails.id }');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-envelope"></span> Send eWay Bill</button>
								</c:when>
								<c:otherwise>
									<button type="button" onclick="javascript:downloadEWayBills('${invoiceDetails.gstnStateIdInString }', '${invoiceDetails.id }');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download"></span> Download</button>
								</c:otherwise>
							</c:choose>										                	
							<button type="button" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
						</div>
					</div>
				</div>		            
			</div>      
		</div>		
	</div>		
	<!-- <div class="col-xs-12"> -->
		<div class="container" id="firstDivId"> 
			<!-- <div class="col-xs-12">      -->   		
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
				<br/>
       			<div class="row">						
					<div class="det-row-col-full text-center ">
                		<strong>Details for ${documentType} Number - ${invoiceDetails.invoiceNumber }</strong>       
            		</div>
         		</div>    
                <br>
                <div class="row">
	               <div class="col-md-12 button-wrap">		
						<button  onclick="javascript:generateEWayBill('${invoiceDetails.id}');" class="btn btn-success blue-but" style="margin:5px 0 0 0" type="button" >Generate E-Way Bill</button>
				   </div>
	       	 	</div>
        	  	<div class="row" id="listTable">
		        	<div class="table-wrap">
		            	<table id="throughInvEwayBillTab" class="display nowrap" style="width:100%">
		                    <thead>
		                       <tr>
		                            <th>Number</th>
		                            <th>Date</th>
		                            <th>Customer</th>
		                            <th>Mode</th>
		                            <th>Status</th>
		                            <th>Valid Upto</th>
		                        </tr>
		                    </thead>
		                    <tbody>	
			        	  		<div class="det-row" id="listEmptyError" style="display:none;">
									<div class="det-row-col-full text-center text-danger">
										No E-Way Bill is created against this ${documentType}.
									</div>
								</div>
        	  				 <%--  		<c:if test="${not empty ewaybillList }">	
        	  						                     					
									<c:set var="ewayCount" value="${0}"/>																		
															
									<c:forEach items="${ewaybillList }" var="edata">
			                       		<c:set var="ewayCount" value="${ewayCount + 1}" />
			                       		<td> ${ewayCount}</td>
			                       		<td><a href="#" onclick="javascript:getEWayBillDetails('${edata.id }');">${edata.ewaybillNo }</a> </td>
			                       		<td> ${edata.ewaybill_date} </td>
			                       		<td> ${edata.ewaybillStatus}</td>
							         </c:forEach>
							      </c:if>  --%>	
		                    </tbody>
		            	</table>
		        	</div>
	    		</div>	
	       	 <input type="hidden" id="numberinvoice" value="${invoiceDetails.id}"/>     
	       	 <input type="hidden" id="isInvoiceAllowed" value="${isInvoiceAllowed}"/> 			             
      		<!-- </div> -->
   		</div>
  <!--   </div>	 -->
	<br/>        			
   	<div class="container" id="secondDivId" style="display:none">
       	<div class="invoice-wrap" id="divTwo">
			<div class="row">
				<div class="col-xs-12">
					<div class="panel panel-default ">
						<div class="panel-heading" >
							<h3 class="">1. E-Way Bill Details</h3>
						</div>
						<div class="panel-body" id="etable"></div>
					</div>
            	</div>
	        </div>
            <div class="row">
                <div class="col-xs-12">
                	<div class="panel panel-default ">
                        <div class="panel-heading">2. Address Details</div>
                       	<div class= "panel-body"> 
                            <div id="userDetailsInPreview" >
	                            <div class="col-xs-12 col-md-6 col-lg-6 ">
		                            <strong>From</strong>
		                            <strong>GSTIN:</strong>${invoiceDetails.gstnStateIdInString}<br>
		                            <strong>Address:</strong>${gstinDetails.gstinAddressMapping.address }<br>
		                            <strong></strong>${gstinDetails.gstinAddressMapping.city }<br>
		                            <strong></strong>${gstinDetails.gstinAddressMapping.state }<br>
		                            <strong></strong>${gstinDetails.gstinAddressMapping.pinCode }                            
									<b id="place100"> </b>
								</div>
							</div>					
							<c:if test="${invoiceDetails.invoiceFor  == 'Product'}">
								<c:if test="${invoiceDetails.billToShipIsChecked == 'No' }">					 
	                       			<div  id="customerDetailsBillToDiv">	
			                       		<div class="col-xs-12 col-md-6 col-lg-6">
				                            <strong>To</strong>	                            
				                            <c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
				                            	<strong>GSTIN :</strong>${invoiceDetails.customerDetails.custGstId }<br>
				                            </c:if>	                            
		                                     <strong>Address:</strong>${invoiceDetails.shipToAddress }<br>
		                                     <strong>City :</strong> ${invoiceDetails.customerDetails.custCity}<br>
		                                     <strong></strong>${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
		                                     <strong></strong>${invoiceDetails.customerDetails.pinCode }<br>
		                                 </div>                             
                      				</div>
                     			</c:if>                                     
                     			<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes' }">					 		    
		                            <div  id="customerDetailsBillToDiv">
			                            <div class="col-xs-12 col-md-6 col-lg-6">
				                            <strong>To</strong>		                            
				                            <c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
												<strong>GSTIN :</strong> ${invoiceDetails.customerDetails.custGstId }<br>
											</c:if>		                            
		                                     <strong>Address :</strong>${invoiceDetails.customerDetails.custAddress1 }<br>
		                                     <strong></strong>${invoiceDetails.customerDetails.custCity }<br>
		                                     <strong></strong>${invoiceDetails.customerDetails.custState }
		                                     <strong></strong>${invoiceDetails.customerDetails.pinCode }<br>	                                     
		                                 </div>
	                            	</div>
                      			</c:if>
							</c:if>
                          	</div>
                	</div>
                </div>
               </div>                    
               <div class="row">
				 <div class="col-xs-12">
                      <div class="panel panel-default ">	                        
                        <div class="panel-heading"><h3 class="">3. Goods Details</h3></div>
	                    <div class="table-responsive">
	                    	<table class="table table-condensed" id="mytable2">
	                            <thead>
	                                <tr>
	                                    <td><strong>HSN Code</strong></td>
	                                    <td><strong>Product Descripition</strong></td>
	                                    <td><strong>Quantity</strong></td>
	                                    <td><strong>Taxable Amount Rs.</strong></td>
	                                    <td><strong>Tax Rate</strong></td>
	                                </tr>
	                            </thead>
	                            <tbody>
									<c:forEach items="${invoiceDetails.serviceList }" var = "idata">
										<c:set var="totalTaxRate" value="${idata.sgstPercentage + idata.cgstPercentage + idata.igstPercentage}" />
										<tr>
											<td>${idata.hsnSacCode } </td>
											<td>${idata.serviceIdInString } </td>
											<td>${idata.quantity } </td>
											<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${idata.previousAmount - idata.offerAmount + idata.additionalAmount}"/> </td>
											<td><fmt:formatNumber minFractionDigits="2" type="number" maxFractionDigits="2" value="${totalTaxRate }"/></td>										
											<c:set var="cessTotalTax" value="${cessTotalTax + idata.cess}" />
											<c:set var="totalAmount" value="${totalAmount + (idata.previousAmount - idata.offerAmount + idata.additionalAmount )}" />
											<c:set var="sgstAmount" value="${sgstAmount + idata.sgstAmount}" />
											<c:set var="cgstAmount" value="${cgstAmount + idata.cgstAmount}" />
											<c:set var="igstAmount" value="${igstAmount + idata.igstAmount}" />
											<%-- <c:set var="cessadvolAmount" value="${cessadvolAmount + idata.cessadvolAmount}" />
											<c:set var="cessnonadvolAmount" value="${cessnonadvolAmount + idata.cessnonadvolAmount}" /> 
											<c:set var="otherValue" value="${otherValue + idata.otherValue}" /> --%>
											<c:set var="cessadvolAmount" value="${0}" />
											<c:set var="cessnonadvolAmount" value="${0}" />
											<c:set var="otherValue" value="${0}" />											
										</tr>
									</c:forEach>
	                            </tbody>
	                    	</table>
	                    </div>
	                    <div class="form-con">
	                        <div class="signature" id="itemTaxDet">
	                             <div class="col-md-3"><strong>Total Taxable Amt : </strong>${totalAmount }</div>
								 <div class="col-md-3"><strong> CGST Amt : </strong>${cgstAmount }</div>
								 <div class="col-md-3"><strong> SGST Amt : </strong>${sgstAmount }</div>
				                 <div class="col-md-3"><strong> IGST Amt : </strong>${igstAmount }</div>
				                 <div class="col-md-3"><strong> CESS Advol Amt : </strong>${cessadvolAmount }</div>
				                 <div class="col-md-3"><strong>CESS Non. Advol Amt : </strong>${cessnonadvolAmount }</div>
				                 <div class="col-md-3"><strong>Other Amt : </strong>${otherValue }</div>
				                 <div class="col-md-3"><strong>Total Inv. Amt : </strong>${invoiceDetails.invoiceValueAfterRoundOff }</div>                   
	                        </div>
                        </div>                       
                       </div>
				 </div>
               </div>	                
			<div class="row">
				<div class="col-xs-12">
					<div class="panel panel-default ">
						<div class="panel-heading">
							<h5>4. Transportation Details</h5>
						</div>
						<div class="panel-body" id="transportTable">
						</div>
					</div>
				</div>
			</div> 	
			<div class="row">
           		<div class="col-md-12">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="">5. Vehicle Details </h3>
	                    </div>                    	
	                    <div class="table-responsive">
	                    	<input type="hidden" name="location" id="location" value="${gstinDetails.gstinAddressMapping.state }">
	                    	<table class="table table-condensed" id="vehicleDetTable">
	                            <thead>
	                                <tr>
	                                    <td><strong>Mode</strong></td>
	                                    <td><strong>Vehicle / Trans Doc No & Dt.</strong></td>
	                                    <td><strong>From</strong></td>
	                                    <td><strong>Entered Date</strong></td>
	                                    <td><strong>Entered By</strong></td>
	                                    <td><strong>CEWB No.(If any)</strong></td>
	                                </tr>
	                            </thead>
	                            <tbody>
									
	                            </tbody>
	                    	</table>
	                    </div>	
            		</div>
				</div>
			</div>	                    
		</div> 
	</div>  
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateInvoice/viewEWayBills.js" />"></script>

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