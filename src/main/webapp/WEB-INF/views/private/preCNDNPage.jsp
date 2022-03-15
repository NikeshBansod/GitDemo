<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<header class="insidehead" id="originalHeader">
	 <a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" class="btn-back"><i class="fa fa-angle-left"></i></a>
     <a class="logoText" href="./home" id="">
     	 <img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
     </a>
</header>

<header class="insidehead" style="display:none" id="previewHeader">
     <a class="logoText" href="./home" id="">
     	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
     </a>
</header>

<main>
	<section class="block ">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="container">
			<div class="card">
				<div class="invoicePage" id="firstDivId">
				<span>
					<center><h4><b> Credit/Debit Note </b></h4></center>
				</span>
				<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
				<input type="hidden" name="refIterationNo" id="refIterationNo" value="">
				<input type="hidden" name="refCnDnId" id="refCnDnId" value="">
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
					
					<c:if test="${empty invoiceDetails.cnDnAdditionalList }">
						<div class="text-center text-danger">
							No Credit or Debit Note is created against this ${documentType}.
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
			                            <div class="acc_content">
			                            <ul class="list-group">
										  
										  <c:forEach items="${invoiceDetails.cnDnAdditionalList }" var="data">
			                           			
				                            	<c:if test="${data.cnDnType == 'creditNote' }">
				                            		<c:set var="cnCount" value="${cnCount + 1}" />
				                            		
				                            		<li class="list-group-item">${cnCount }. <a href="#" onclick="javascript:getCnDnDetails('${invoiceDetails.id }','${data.iterationNo }','${data.id }');">${data.invoiceNumber }</a> </li>
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
			                            <div class="acc_content">
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
					
					<br>
					<div class="row text-center">
						
        				<%-- <a href="#" onclick="javascript:createCNDN('${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-th-list"></span> Create Credit/Debit note -Old
        				</a> --%>
        				
        				<a href="#" onclick="javascript:createNewUiCNDN('${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-th-list"></span> Create Credit/Debit note
        				</a>
        				
        					
        			</div>
				</div>
				
				
				<div class="invoicePage" id="secondDivId" style="display:none">
				<span>
				<a class="downToLast" href="#" onclick="scrollSmoothToBottom()"  class="btn-back"><i class="fa fa-angle-double-down"></i></a>
					<center><h4><b> Preview Credit/Debit Note Details </b></h4></center>
				</span>
					<div class="invoiceDetail">
						  <img alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';" class="img-fluid" src="${pageContext.request.contextPath}/getOrgLogo">
						<div class="invoiceInfo">
							<h4>${userMaster.organizationMaster.orgName }</h4>
							<p><span>${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}</span></p>
							<p><span>GSTIN :</span>${invoiceDetails.gstnStateIdInString }</p>
							<p><span>Original Tax Invoice No:</span>${invoiceDetails.invoiceNumber }</p>
							<p><span>Original Tax Invoice Date:</span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /></p>
							<br/>
							<p id="place100"></p>
							
						
						</div>
						
					</div>
					<div class="row">
						<div class="col-sm-12 text-center">
                             <h3 id="place1">TAX INVOICE</h3>
                             <hr>
                        </div>
						<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
							<h4>Bill To</h4>
							<p><span>Name:</span>${invoiceDetails.customerDetails.custName }</p>
							<p><span>Address:</span>${invoiceDetails.customerDetails.custAddress1 }</p>
							<p><span>City:</span>${invoiceDetails.customerDetails.custCity }</p>
							<p><span>State:</span>${invoiceDetails.customerDetails.custState } 
								<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
								[ ${invoiceDetails.customerDetails.custGstinState } ]
								</c:if>
							</p>
							<p><span>State Code:</span>${customerStateCode }</p>
							<p><span>GSTIN/Unique Code:</span>${invoiceDetails.customerDetails.custGstId }</p>
							</div>
						</div>
						
					</div>
					
						
					<!-- Start -->
					<div class="invoiceTable">
			          	 <!-- FIRST TABLE STARTS -->
				          	<div id="stable">
				          	
				          	
				          	</div>
				          <!-- FIRST TABLE ENDS -->
				          
				          <!-- SECOND TABLE STARTS -->
				          	<div id="stable1">
				          	
				          	
				          	</div>
				          <!-- SECOND TABLE ENDS -->
				          <!-- THIRD TABLE STARTS -->
				             <div id="stable2">
				             
				             
				             </div>
				          <!-- THIRD TABLE ENDS -->
				          <!-- FOURTH TABLE STARTS -->
				          	<div id="stable3">
				          	
				          	
				          	</div>
				          <!-- FOURTH TABLE ENDS -->
			          
			          
			          </div>
					  <br/>
					  <div class="row" id="diffPercentShowHide" style="margin:0 10px;display:none">
							<span style="color:red">*</span>&nbsp;Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.
			          </div>
			          <hr>
					
					<!-- End -->	
						
						
					<div class="row">
					 	<div class="col-sm-6">
							<div class="invoiceInfo invoiceFirst">
							<p><span>Customer Purchase Order:</span>${invoiceDetails.poDetails }</p>
							
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
					<hr>
					
					<div class="invoiceInfo">
						
						<h4>Declaration</h4>
						<p><span>I) Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.</span></p>
						<p><span>II) The normal terms governing the above sale are printed overleaf. </span></p>
						<p><span>E&OE</span></p>
					</div>
					
					 <div id="cndnFooterNoteDiv" style="text-align:center">
					 </div>
					 <div  class="row text-right">
					<a class="downToUp" href="#" onclick="scrollSmoothToTop()" class="btn-back"><i class="fa fa-angle-double-up"></i></a></div>
        			 <div class="btns">
				        <a class="btn btn-primary btn-half" href="#" onclick="javascript:previewCNDNSendMail()">Send Mail</a>
				        <a id="backToPreview" class="btn btn-primary btn-half" href="#!">Back</a>
				    </div>
				
				</div>
				<div class="" id='loadingmessage' style='display:none;' align="middle">
				  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
				</div>
			</div>
		</div>
	</section>
</main>






<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/viewInvoiceDetails.js" />"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form>
 
 