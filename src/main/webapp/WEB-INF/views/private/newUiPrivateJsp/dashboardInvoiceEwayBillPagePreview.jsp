<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
<input type="hidden" name="ewaybillid" id="ewaybillid" value="${ewaybillid}">
<section class="block">
<div class="loader"></div>
	<div class="container">
	
	<!-- <div class="brd-wrap">
	    	<div  id="breadcumheader">
	       		 <a href="#" id="goBackToInvoiceEwaybill"><strong>Eway bill History</strong></a> &raquo; <strong> Details</strong>
	        </div>
	        <div id="headerPreview">
	          <a  href="#" id="goBackToInvoiceEwaybill">E-way bill List</a> &raquo; <strong>Preview E-way bill</strong>
	          </div>
	    </div> -->
	    
	    <div class="page-title">
                    <a href="#" id="goBackToInvoiceEwaybill" class="back"><i class="fa fa-chevron-left"></i></a>Preview E-way bill
              </div>
</br>
    <div id="secondDivId" >
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
	                            <div class= "panel-body" id="userDetailsInPreview"> 
	                             <div>
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
	                        
	                        <div class="panel-heading">
	                        <h3 class="">3. Goods Details</h3>
	                    </div>
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
	             </div>
	             </section>
	
		
		
		
		
		</div>
		</section>
	<!-- </div> -->
	<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardInvoiceEwaybillPreview.js"/>"></script>
	<form name="invoiceEwaybill" method="post">
	<input type="hidden" name="ewaybillNo" value="">
	  <input type="hidden" name="invoiceId" value="">
	  <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="invoiceewaybill">
	<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
      <input type="hidden" name="_csrf_token" value="${_csrf_token}">
     
	</form> 
	<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>

<form name="redirectToBackFrominvoiceEwayBill" method="post">
	    <input type="hidden" name="startdate" value=""> 
	    <input  type="hidden" name="enddate" value="">
		<input type="hidden" name="getInvType" value=""> 
		<input type="hidden" name="onlyMonth" value="">
        <input type="hidden" name="onlyYear" value="">
        <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
	
