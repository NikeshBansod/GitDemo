<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">

<!-- Dashboard set variables - End -->
 
<section class="block"> 
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="loader"></div>
	<div class="invoice-wrap" id="divTwo">
         <div class="container">
             <div class="row">
                 <div class="col-xs-12">
				    <div class="brd-wrap" id="breadcumheader">
				    
				    <c:set var="conditionValue" value="${conditionValue}"/>
						 <c:choose>
						    <c:when test="${not empty conditionValue}">
						      <!--  <a id="goBackToGenericEwaybill" href="#"><strong> History<strong></a> &raquo; <strong>Generic Eway Bill Details</strong> -->
						       <div class="page-title">
                                 <a href="#" id="goBackToGenericEwaybill" class="back"><i class="fa fa-chevron-left"></i></a>Generic Eway Bill Details
                                </div>
						    </c:when>
						    
						    <c:otherwise>
						        <a href="#" onclick="javascript:backToDocumets();"><strong>E-Way Bill History<strong></a> &raquo; <strong>Generic E-Way Bill Details</strong>
						    </c:otherwise>
						 </c:choose>
						 
				    	
				    </div>
				    <br>
				<c:choose>
		            <c:when test="${empty conditionValue}">
                    <div class="row">
						<div class="col-md-12 button-wrap"  id="optionsMultiDiv">
							<div style="float:right;">
							<c:choose>
		  						<c:when test="${loggedInThrough == 'MOBILE'}">	
		  							<button type="button" id="sendMailId" class="btn btn-success blue-but"><span class="glyphicon glyphicon-envelope"></span> Send eWay Bill</button>
		  						</c:when>
		  						<c:otherwise>
		  							<button type="button" onclick="javascript:downloadWIEWayBills('${userId}','${ewaybillNo}','${clientId}','${secretKey}','${appCode}','${ipUsr}');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download"></span> Download E-way bill</button>
		  						</c:otherwise>
		  					</c:choose>										                	
			                	<button type="button" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="btn btn-success blue-but"><span class="glyphicon glyphicon-remove"></span> Cancel E-way bill</button>
							</div>
	        				
			            </div>
		            </div>
		            </c:when>
		            </c:choose>
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

<!-- Dashboard backToCall form -->
<form name="redirectToBackFromGenericEwayBill" method="post">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
	
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/genericEwayBill/viewGenericEwayBillDetail.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardGenericEwaybill.js"/>"></script>
