<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%-- <%@include file="../common-alert.jsp" %> --%>
      <div class="loader"></div>
<section class="block"> 
		<div class ="row"> 
         <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="container">
			      <div class="loader"></div>


		<div class="col-xs-12">
			 <div class="brd-wrap" >
			 	<div   id="breadcumheader">
				 <strong> <a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" >Document Details</a></strong> &raquo; <strong>CNDN List</strong>
			 	</div>
			 	<div id="breadcumheader2">
				<strong>  <a   style="display:none" href="#" id="backToPreview">CNDN List</a></strong> &raquo; <strong>Credit/Debit Note Details</strong>
		 		</div>
			</div>
		</div>
	</div>
	
	
	<div class="col-xs-12"> 
		<div class="container" id="firstDivId"> 
			<div class="col-xs-12">       		
        		<div class="row">
        		<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
				<input type="hidden" name="refIterationNo" id="refIterationNo" value="">
				<input type="hidden" name="refCnDnId" id="refCnDnId" value="">
				<c:set var="documentType" value=""/>
				<%-- <c:set var="address" value="${gstinDetails.gstinAddressMapping.address}"/>
				<c:set var="city" value="${gstinDetails.gstinAddressMapping.city}"/>
				<c:set var="state" value="${gstinDetails.gstinAddressMapping.state}"/>
				<c:set var="statecode" value="${gstinDetails.state} "/>
				<c:set var="pincode" value="${gstinDetails.gstinAddressMapping.pinCode}"/> --%>
				
				
				
				
 
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
						<br>
						
						<div class="col-md-12 button-wrap">			
				<%-- <button  onclick="javascript:createNewUiCNDN('${invoiceDetails.id }');" class="btn btn-success blue-but" style="margin:5px 0 0 0" style="width: auto;" type="button" > Create Credit/Debit note</button> --%>
			</div>
			<br>
			<div class="row">						
				<div class="det-row-col-full text-center ">
	                   <strong> Details for ${documentType} Number - ${invoiceDetails.invoiceNumber }</strong>
	                            
	            </div>
	         </div>    
	                    <br>
	                    
	                    
	                    
	                    
	                    
	      <div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="CndnTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                           
	                           <th> Sr No.</th>
	                            <th>Document Number</th>
	                             <th>Note Type</th>
	                            <th>Invoice Date</th>
	                           
	                            
	                        </tr>
	                    </thead>
	                    <tbody>
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
									<c:set var="cnICount" value="${cnICount + 1}" />
								</c:if>							
							</c:forEach>						
							<c:if test="${ cnICount gt 0 || dnICount gt 0}">
								<c:forEach items="${invoiceDetails.cnDnAdditionalList }" var="data">			                           			
						           <c:if test="${data.cnDnType == 'creditNote' || data.cnDnType == 'debitNote'}">
						              <c:set var="cnCount" value="${cnCount + 1}" />	
	                   				  <tr>
										<td> ${cnCount}</td>
										<td><a href="#" onclick="javascript:getCnDnDetails('${invoiceDetails.id }','${data.iterationNo }','${data.id }');">${data.invoiceNumber }</a> </td>
										<c:if test="${data.cnDnType == 'creditNote'  }">
								 			<td> <span class="textColourRed">Credit Note</span> </td>
								 		</c:if>
										<c:if test="${data.cnDnType == 'debitNote' }"> 
										 	<td> <span class="textColourGreen">Debit Note</span> </td>
										</c:if>
										<td> <fmt:formatDate pattern = "dd-MM-yyyy" value = "${data.invoiceDate}" /> </td>
	                      
	                      			  </tr>
	                       			</c:if>
	                       		</c:forEach>
	                       </c:if>
				       </c:if>    
	               </tbody>
	            </table>
	        </div>
	    </div>
	    
	    
        				
        	
	  
        	 </div> 
        	 
        </div>
              		 <br></br>
					  
					<br>
       </div>	 




<br>

<div class="container" id="secondDivId" style="display:none">
					<!-- Latest Html provided - Start -->
						<div class="invoice-wrap" id="divTwo">
						
					<div class="row">
					 
						            <div class="col-md-12 button-wrap" id='buttonsForInvoice'>
						            	<!-- <div class="print-logo" style="float:right;margin:10px">
						            		<button type="button" id="optionsDiv" class="btn btn-success blue-but" value="Options">Action</button>
										</div> -->
						            	<div class="insidebtn" style="display:none;float:right;margin:10px" id="optionsMultiDiv">
											<%-- <c:if test="${loggedInThrough == 'WIZARD'}"> --%>	
					  							<button type="button" onclick="javascript:downloadRecordForCNDN();" class="btn btn-success blue-but"><span class="glyphicon glyphicon-download-alt"></span>&nbsp;Download & Print</button>
					  					<%-- 	</c:if>	 --%>
					  						<c:if test="${loggedInThrough == 'MOBILE'}">											                
							                	<button type="button" onclick="javascript:sendMail('${invoiceDetails.id }');" class="btn btn-success blue-but">Send PDF</button>
					  						</c:if>	
							              
										</div>
						            </div>
						         
					 <div class="col-xs-12">
					
	                    <div class="col-xs-12 col-md-6 col-lg-6 ">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Billing Details</div>
	                            <div class="panel-body" id="userDetailsInPreview" >
							<strong>${userMaster.organizationMaster.orgName}</strong><br>
							
							${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}<br>
							<strong>GSTIN  : </strong>${invoiceDetails.gstnStateIdInString}<br>
							<strong>Original Tax Invoice No : </strong>${invoiceDetails.invoiceNumber}<br>
							<strong>Original Tax Invoice Date :</strong><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>
							<b id="place100"> </b>
						</div>
						
						
						
							</div>
					</div>
					 <div class="col-xs-12 col-md-6 col-lg-6">
	                        <div class="panel panel-default ">
	                            <div class="panel-heading">Bill To</div>
	                            <div class="panel-body" id="customerDetailsBillToDiv">
	                            
	                            <strong>Name :</strong> ${invoiceDetails.customerDetails.custName}<br>
                                     <strong>Address :</strong> ${invoiceDetails.customerDetails.custAddress1}<br>
                                     <strong>City :</strong> ${invoiceDetails.customerDetails.custCity}<br>
                                     <strong>State :</strong> ${invoiceDetails.customerDetails.custState}
                                     	<c:if test="${not empty invoiceDetails.customerDetails.custGstinState != 'NULL'}">
											[ ${invoiceDetails.customerDetails.custGstinState } ]
										</c:if>  <br>
                                     <strong>State Code :</strong> ${customerStateCode}<br>
                                     
                                  </div>
                              </div>
                     </div>
					
					</div>
					</div>
					<!-- work in progress in upper part  -->
					<div class="row">
					<div class="col-md-12">
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
		                    
	                        <div class="signature-wrap" id="preview_customer_purchase_order">
	                           <h1 class="black-bold">Customer Purchase Order :</h1>
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
                        </div>
                        </div>
			<!-- 	     <div class="insidebtn"> 
						<input type="button" onclick="javascript:previewCNDNSendMail()" class="sim-button button5" value="Send Mail">
						<input id="backToPreview" type="button" class="sim-button button5" value="Back">
					</div> -->
					<br/>
					
					
					
					<div class="row">
	            <div class="col-md-12 button-wrap">
	            	
	            	
		                <button type="button" onclick="javascript:previewCNDNSendMail()"  style="width: auto;" class="btn btn-success blue-but" value="Send Mail">Send Mail</button>
		               <!--  <button type="button"  id="backToPreview" value="Back" class="btn btn-success blue-but">Back</button> -->
		               
					
	            </div>
	        </div> 
	        
	        
	        
	        
					
					
					
				</div>
					<!-- Latest Html provided - End -->
			</div>  
			
			
			</div>   
			</div>
			</div>
			
	
</section>
			
			
			 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/previewCndn/previewCndn.js" />"></script>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form>
