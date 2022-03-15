<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<section class="block"> 
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="loader"></div>
	<div class="invoice-wrap" id="divTwo">
         <div class="container">
             <div class="row">
                 <div class="col-xs-12">
				    <div class="brd-wrap" id="breadcumheader">
				    	<a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')"> Document Details</a> &raquo; <strong>E-Way Bill</strong>
				    </div>
				    <br>
				    
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
						<div class="col-sm-12 panel-heading">
                             <h5>Details for ${documentType} Number - ${invoiceDetails.invoiceNumber }</h5>
                             <hr>
                        </div>
					</div>
					
                    <div class="row">
						<div class="col-md-12 button-wrap"  id="optionsMultiDiv">
							<div style="float:right;">
							<c:choose>
		  						<c:when test="${loggedInThrough == 'MOBILE'}">	
		  							<button type="button" id="sendMailId" class="btn btn-success blue-but"><span class="glyphicon glyphicon-envelope"></span> Send eWay Bill</button>
		  						</c:when>
		  						<c:otherwise>
		  							<button type="button" onclick="javascript:downloadWIEWayBills('${userId}','${ewaybillNo}','${clientId}','${secretKey}','${appCode}','${ipUsr}');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download"></span> Download E-Way Bill</button>
		  						</c:otherwise>
		  					</c:choose>										                	
			                	<button type="button" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-remove"></span> Cancel E-Way Bill</button>
							</div>
			            </div>
		            </div>
                    <div class="row">
	                    <div class="col-xs-12 col-md-12 col-lg-12 ">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">1. E-Way Bill Details</div>
	                            <div class="panel-body" id="etable">
	                            
	                            </div>
	                        </div>
	                    </div>
	                </div>
                    <div class="row">
	                    <div class="col-xs-12 col-md-12 col-lg-12">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">2. Address Details</div>
	                            <div class="panel-body" id="">
	                            	<div class="col-md-6" id="fromGstin">	 
	                                   
                                     </div>
	                            	<div class="col-md-6" id="toGstin">	 
	                                    
                                     </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                 </div>
             </div>
             <div class="row">
             	<div class="col-md-12">
	                <div class="panel panel-default">
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
									
	                            </tbody>
	                    	</table>
	                    </div>
	                    <div class="form-con">
	                        <div class="signature" id="itemTaxDet">
	                                                 
	                        </div>
                        </div>
                    </div>
                </div>
             </div>  
             <div class="row">             	
				<div class="col-xs-12 col-md-12 col-lg-12">
				    <div class="panel panel-default ">
				        <div class="panel-heading">4. Transportation Details </div>
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
    <div class="form-wrap" id="customerEmailDiv" style="display:none;">
    	<div class="container">
              <div class="col-xs-12">
			    <div class="brd-wrap" id="breadcumheader">
			    	<a href="#" onclick="javascript:backToDocumets();">E-Way Bill History</a> &raquo; <strong>Generic E-Way Bill Detail</strong>
			    </div>
	         	<div class="row">	
					<div class="col-md-12">
						<label for="label">Email Address <span>*</span></label>
					  	<input type="text" id="cust_email_addr" maxlength="100">  
					  	<span class="text-danger cust-error" id="cust-email-format">This field should be in a correct format</span> 					
					</div>									
				</div>	
				<div class="row">	
	      			<div class="col-md-12 button-wrap">
	      				<button type="submit" class="btn btn-success blue-but" id="custEmailSave">Send</button>
	      				<button type="button" class="btn btn-success blue-but" id="backToGenericEwayPreview">Cancel</button>
	                </div>
	           </div>
           </div>
    	</div>
    </div>
   	<input type="hidden" name="userId" id="userId" value="${userId}">
	<input type="hidden" id="ewaybillNo" name="ewaybillNo" value="${ewaybillNo}">	
    <input type="hidden" id="clientId" name="clientId" value="${clientId}">
	<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">	  
</section>
	
<form name="downloadWIEWayBill" id="downloadEWayBill" method="post">
    <input type="hidden" id="client_id" name="client_id" value="">
	<input type="hidden" id="secret_key" name="secret_key" value="">
	<input type="hidden" id="app_code" name="app_code" value="">
	<input type="hidden" id="ip_usr" name="ip_usr" value="">	
	<input type="hidden" id="userId" name="userId" value="">
	<input type="hidden" id="ewaybillno" name="ewaybillno" value="">	
</form>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>

<form name="cancelEwayBill" id="cancelEwayBill" method="post">
    <input type="hidden" name="ewayBillNo" value="">
    <input type="hidden" name="gstin" value="">
</form>
	
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/genericEwayBill/viewGenericEwayBillDetail.js" />"></script>
