<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="insidepages">
	
	<div class="container" id="loadingmessage" style="display:none;" align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
           
	<div class="inside-cont">
			<div class="breadcrumbs">
				 <header class="insidehead" id="originalHeader">
		         	<a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" >Document History</a> <span>&raquo;</span> Credit/Debit Note
		 		 </header>
		 		 <header class="insidehead" id="previewHeader" style="display:none">
		            <p >Preview Credit/Debit Note Details </p>
				 </header>
			</div>
			
       		<div class="account-det">
       		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
        		<div class="container" id="firstDivId">        		
        			 <div class="card">
        			 	<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
						<input type="hidden" name="refIterationNo" id="refIterationNo" value="">
						<input type="hidden" name="refCnDnId" id="refCnDnId" value="">
						
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
	                             Details for ${documentType} Number - ${invoiceDetails.invoiceNumber }
	                        </div>
	                    </div>    
	                    
						<c:if test="${empty invoiceDetails.cnDnAdditionalList }">
							<div class="det-row">
								<div class="det-row-col-full text-center text-danger">
									No Credit or Debit Note is created against this ${documentType}.
								</div>
							</div>
						</c:if>
						<c:set var="cnICount" value="${0}"/>
						<c:set var="dnICount" value="${0}"/>
						<c:set var="cnCount" value="${0}"/>
						<c:set var="dnCount" value="${0}"/>
						
						<c:if test="${not empty invoiceDetails.cnDnAdditionalList }">
							<c:forEach  items="${invoiceDetails.cnDnAdditionalList }" var="data">						
								<c:if test="${data.cnDnType == 'creditNote' }">
									<c:set var="cnICount" value="${cnICount + 1}" />								
								</c:if>
								<c:if test="${data.cnDnType == 'debitNote' }">
									<c:set var="dnICount" value="${dnICount + 1}" />
								</c:if>							
							</c:forEach>						
							<c:if test="${ cnICount gt 0}">
								<div class="accordion no-css-transition mb0">
			                        <div class="accordion_in acc_active">
			                            <div class="acc_head">Credit Notes</div>
			                            <div class="acc_content" >
				                            <ul class="list-group">
												<c:forEach items="${invoiceDetails.cnDnAdditionalList }" var="data">			                           			
						                           	<c:if test="${data.cnDnType == 'creditNote' }">
						                            	<c:set var="cnCount" value="${cnCount + 1}" />				                            		
						                            	<li class="list-group-item" >${cnCount }. <a href="#" onclick="javascript:getCnDnDetails('${invoiceDetails.id }','${data.iterationNo }','${data.id }');">${data.invoiceNumber }</a> </li>
						                           	</c:if>
					                            </c:forEach>
											</ul>
			                            </div>
			                        </div>
			                    </div>
							</c:if>
							<c:if test="${ dnICount gt 0}">
								<div class="accordion no-css-transition mb0">
			                        <div class="accordion_in acc_active">
			                            <div class="acc_head">Debit Notes</div>
			                            <div class="acc_content" >
			                            	<ul class="list-group">										 
											 	<c:forEach items="${invoiceDetails.cnDnAdditionalList }" var="data">			                           			
					                            	<c:if test="${data.cnDnType == 'debitNote' }">
					                            		<c:set var="dnCount" value="${dnCount + 1}" />				                            		
					                            		 <li class="list-group-item">${dnCount}. <a href="#" onclick="javascript:getCnDnDetails('${invoiceDetails.id }','${data.iterationNo }','${data.id }');">${data.invoiceNumber }</a></li>
					                            	</c:if>
				                            	</c:forEach>
											</ul>
			                            </div>
			                        </div>
			                    </div>							
							</c:if>
						</c:if>
        			 </div> 
              		 <br></br>
					 <div class="insidebtn"> 
						<input id="submitId" type="button" onclick="javascript:createCNDN('${invoiceDetails.id }');" class="sim-button button5" value="Create Credit/Debit note">
					</div> 
					<br>
        		</div>		
        		
        		<div class="" id="secondDivId" style="display:none">
					<!-- Latest Html provided - Start -->
					<div class="card">
					
					<div class="logo-det">
						<div class="upload-logo"><img  alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-responsive" src="${pageContext.request.contextPath}/wGetOrgLogo" width="143" height="174"></div>
						<div class="upload-txt">
							<strong>${userMaster.organizationMaster.orgName}</strong><br>
							${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}<br>
							<b>GSTIN  : </b>${invoiceDetails.gstnStateIdInString}<br>
							<b>Original Tax Invoice No : </b>${invoiceDetails.invoiceNumber}<br>
							<b>Original Tax Invoice Date :</b><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>
							<b id="place100"> </b>
						</div>
						
						<div class="print-logo" style="float:right;margin:10px">
					   <%--  <a href="#" onclick="javascript:downloadRecord('${invoiceDetails.id }');" class="sim-button button5" >
					      <span class="glyphicon glyphicon-download-alt"></span> &nbsp;Download & Print
					    </a><br><br> --%>
					  <input type="button" onclick="javascript:previewCNDNSendMail()" class="sim-button button5" value="Send Mail">
						<input id="backToPreview" type="button" class="sim-button button5" value="Back">
					    
					</div>
						
					</div>
					
					<div class="tax-invoice" id="customerDetailsInPreview">
						<h1 id="place1">TAX INVOICE</h1>
						<div class="invoice-txt">
							<strong><u>Bill To</u></strong><br>
							<b>Name : </b>${invoiceDetails.customerDetails.custName}<br>
							<b>Address : </b>${invoiceDetails.customerDetails.custAddress1}<br>
							<b>City : </b>${invoiceDetails.customerDetails.custCity}<br>
							<b>State : </b>${invoiceDetails.customerDetails.custState} 
								<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
								[ ${invoiceDetails.customerDetails.custGstinState } ]
								</c:if><br>
							<b>State Code : </b>${customerStateCode}<br>
							<b>GSTIN/Unique Code : </b>${invoiceDetails.customerDetails.custGstId}<br>								
						</div>
					</div>
					
				    <div class="tablemain2">
						<table id="mytable2">
							
							
						</table>
					</div> 
				    <br>
			 		<div class="tax-invoice" id="diffPercentShowHide" style="margin:0 10px;display:none">
						<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
			        </div>
				    <br>
			        <div class="tax-invoice">
						<b>Customer Purchase Order : </b>${invoiceDetails.poDetails}<br>
			        </div>
				    <br>
				            
				    <div class="add-charges">
						<div class="receiver">
							Receiver Name:<br><br>
							<strong>Receiver Signature:</strong>
						</div>
						<div class="supplier">
							Supplier Name:<br><br>
							<strong>Authorized Signature:</strong>
						</div>
					</div>
					<br>
				    <hr>
				 
				    <div class="declaration">
						<strong>Declaration</strong><br>
						I. Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.<br>
						II. The normal terms governing the above sale are printed overleaf E&OE
					</div>
					
					<div class="invoice-txt" id="footerNoteDiv">
						 	<div class="add-charges">
							<!-- <h4>Footer Note</h4> -->                            
                        	</div>
				    </div><br>
					 
			<!-- 	     <div class="insidebtn"> 
						<input type="button" onclick="javascript:previewCNDNSendMail()" class="sim-button button5" value="Send Mail">
						<input id="backToPreview" type="button" class="sim-button button5" value="Back">
					</div> -->
					<br/>
				</div>
					<!-- Latest Html provided - End -->
			</div>        		       
        </div>	<!-- End of class="account-det" -->		
	</div>
</section> 

 <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/viewInvoiceDetails.js" />"></script>
 
 <form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
 