<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> 
<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
<input type="hidden" name="refIterationNo" id="refIterationNo" value="">
<input type="hidden" name="refCnDnId" id="refCnDnId" value="">
 
<div class="loader"></div>
<section class="block">
	<div class="container" >
    <%--   <div class="brd-wrap">
	    	<div  id="breadcumheader">
	       		<a href="<spring:url value="/home" />"><strong>Home</strong></a> &raquo; <strong> RR History</strong>
	        </div>
	       	<div id="breadcumheader2">
	          <a  href="<spring:url value="/showrevisedandreturndetails"/>"><strong>RR History</strong></a> &raquo; <strong>Preview Credit/Debit Note</strong>
	          </div>
	    </div> --%>
	     <div class="page-title" id="breadcumheader">
                        <a href="<spring:url value="/home"/>" class="back"><i class="fa fa-chevron-left"></i></a>RR History
                    </div>
                    
                     <div class="page-title" id="breadcumheader2">
                        <a href="<spring:url value="/showrevisedandreturndetails"/>" class="back"><i class="fa fa-chevron-left"></i></a>Preview Credit/Debit Note
                    </div>
                    
	   
	   
	    <div class="form-wrap">
	    <!-- <div class="row"> 
            <div class="col-md-12 button-wrap">
            <a href="" class="btn btn-success blue-but" style="width: auto;">Create</a>			
		</div>
		</div>-->
		</div> 
	    
	    
	   	<div class="row" id="firstDivId">
	        <div class="table-wrap">
	            <table id="revisionAndReturnDataTable" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <!-- <th>Sr.No</th> -->
	                            <th>Document Number</th>
	                            <th>Transaction Date</th>
	                             <th>Customer</th>
	                            <th>Document Type</th>
	                            <th>Reason</th>
	                            <th>Original Document</th>
	                            
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                  
	            </table>
	        </div>
	        
	    </div> 
	    
	    
	    
    </div>
    
    <input type="hidden" id="originalInvGstnNo" value="">
		<input type="hidden" id="user_org_name" value="${userMaster.organizationMaster.orgName}">
		<input type="hidden" id="user_org_panNumber" value="${userMaster.organizationMaster.panNumber}">
    
     <div class="container" id="secondDivId" style="display:none">

					<!-- Latest Html provided - Start -->
						<div class="invoice-wrap" id="divTwo">
						          <div class="col-md-12 button-wrap" id='buttonsForInvoice'>
						            	<!-- <div class="print-logo" style="float:right;margin:10px">
						            		<button type="button" id="optionsDiv" class="btn btn-success blue-but" value="Options">Action</button>
										</div> -->
						            	<div class="insidebtn" style="float:right;margin:10px" id="optionsMultiDiv">
											<%-- <c:if test="${loggedInThrough == 'WIZARD'}"> --%>	
					  							<button type="button" onclick="javascript:downloadRecordForCNDN();" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download-alt"></span>&nbsp;Download & Print</button>
					  					<%-- 	</c:if>	 --%>
					  						<c:if test="${loggedInThrough == 'MOBILE'}">											                
							                	<button type="button" onclick="javascript:sendMail('${invoiceDetails.id }');" class="btn btn-success blue-but">Send PDF</button>
					  						</c:if>	
							              
										</div>
						            </div>
					
					
					
	                    <div class="col-xs-12 col-md-6 col-lg-6 ">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Billing Details</div>
	                            <div class="panel-body" id="userDetailsInPreview" >
							
						</div>
						
						
						
					</div></div>
					 <div class="col-xs-12 col-md-6 col-lg-6">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDiv">
	                          
                                     
                                     </div>
                                     </div>
                                     </div>
					
					
					<!-- work in progress in upper part  -->
					<div class="col-xs-12">
	                <div class="panel panel-default">
	                    <div class="panel-heading" id="customerDetailsInPreview">
	                        <h3 class="text-center" id="place1">
		                        TAX INVOICE
	                        </h3>
	                    </div>
					
					
				    <div class=" table-responsive">
						<table class="table table-condensed" id="mytable2">
							
							
						</table>
					</div>
					
				    
				     <div class="form-con">
		                    <c:if test="${containsDiffPercentage gt 0}">
								<div class="signature-wrap">
									<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
					        	</div>	
					        </c:if>
	                        <div class="signature-wrap" id="preview_customer_purchase_order">
	                           <h1 class="black-bold">Customer Purchase Order :</h1>${invoiceDetails.poDetails}
	                        </div>
	                        <div class="signature">
	                            <div class="col-md-6">
	                                <h1 class="signature-normal">Receiver Name :</h1>
	                                <h1 class="signature-normal"><strong>Receiver Signature :</strong></h1>
	                            </div>
	                            <div class="col-md-6">
	                                <h1 class="signature-normal">Supplier Name :</h1>
	                                <h1 class="signature-normal"><strong>Authorized Signature :</strong></h1>
	                            </div>                                            
	                        </div>
	                        <div class="declear">
	                        	<c:if test="${not empty invoiceDetails.footerNote}">
	                        		<div style="text-align:center;" id="footerNoteDiv">${invoiceDetails.footerNote}</div>
	                        	</c:if>
	                        	<p id="userAddressWithDetails"></p>	
	                            <h1 class="black-bold">Declaration</h1>
	                            <ul>
	                                <li>
	                                    Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional considerations directly or indirectly from the Buyer
	                                </li>
	                                <li>
	                                    The normal terms governing the above sale are printed overleaf E&amp;OE
	                                </li>                                            
	                            </ul>
	                        </div>
	        
	        
	        
	        
					
					
					
				</div>
					<!-- Latest Html provided - End -->
			</div>  
			
			<div class="row">
	            <div class="col-md-12 button-wrap">
	            	
	            	
		                <button type="button" onclick="javascript:previewCNDNSendMail()"  style="width: auto;" class="btn btn-success blue-but" value="Send Mail">Send Mail</button>
		               <!--  <button type="button"  id="backToPreview" value="Back" class="btn btn-success blue-but">Back</button> -->
		               
					
	            </div>
	        </div> 
			  
			</div>
			</div> 
			
			</div> 
			
		
		</section>

	
	
<form name="cndn" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" name="cndnNumber" value="">
    <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="cndn">
	<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form>
<!-- back from cndnlist to dashboard -->

 
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/previewCndn/previewCndn.js" />"></script> --%>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form> 

</section>



<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/generateRR/revisionAndReturnList.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardCNDN.js"/>"></script>


<form name="manageRR" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="documentPkId" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" name="conditionValue" value=""> 
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

