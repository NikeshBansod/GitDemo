<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/cnDn/cndn.js" />"></script>

 
<header class="insidehead" id="addCndnHeader">
     <a href="#" onclick="javascript:backToInvoicePage('${invoiceDetails.id }')" class="btn-back"><i class="fa fa-angle-left"></i></a>
     <a class="logoText" href="./home">
     	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
     </a>
</header>

<header class="insidehead" style="display:none" id="previewHeader">
     <a class="logoText" href="./home" id="previewHeaderLabel">
     	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
     </a>
</header>
 
<main>
    <section class="block generateInvoice">
    	<div class="container" id="addCnDnDiv">
    	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    		  <div class="card">
    		  	<span>
					<center><h4><b> Generate Credit / Debit Note </b></h4></center>
				</span>
                  
                  <div class="radio-inline text-left">
                       <span >Choose Note Type </span>
                       <div class="rdio rdio-success m-l-23">
                           <input type="radio"  name="cnDnType" value="creditNote" id="radio2" checked="checked">
                           <label for="radio2" style="margin-left : 12px">Credit Note</label>
                       </div>
                       <div class="rdio rdio-success cndnRadio">
                         
                           <input type="radio" name="cnDnType" value="debitNote" id="radio1" >
                           <label for="radio1" style="margin-left : 12px">Debit Note</label>
                       </div>
                   </div>
                   </br>
                  <!--  <div class="radio-inline text-left">
                       <span class="m-l-9">Choose Regime Type </span>
                       <div class="rdio rdio-success cndnRadio">
                           <input type="radio" name="regime" value="preGst" id="radio3" checked="checked">
                           <label for="radio3" style="margin-left : 12px">Pre-GST</label>
                       </div>
                       <div class="rdio rdio-success cndnRadio m-l-27">
                         
                           <input type="radio" name="regime" value="postGst" id="radio4" >
                           <label for="radio4" style="margin-left : 12px">Post-GST</label>
                       </div>
                   </div> -->
                    <input type="hidden" name="regime" value="postGst" id="regime" >
                    
                   <div class="form-group input-field" id="">
                       <label class="label">
                       <select class="form-control" name="selectReason" id="selectReason">
			              <!-- <option value="">--Select reason --</option>
			              <option value="XXXXXX">XXXXXXXXX</option>
			              <option value="YYYYYY">YYYYYYYYY</option> -->
			           </select>
                       <div class="label-text">Select a reason</div>
                       </label>
                       <span class="text-danger cust-error" id="selectReason-csv-id">This field is required.</span>  
                    </div>
                    
        	</div>
        	<br/>
                    
                    <div class="accordion no-css-transition mb0">
                    	<div class="accordion_in">
                            <div id="" class="acc_head">Goods/Services details</div>
                            <div class="acc_content" id="dnynamicGoodServices">
                            	

                            </div>
                       </div>
                    
                    </div>
                    
                 <!-- Footer Note - Start -->
                 <div class="accordion no-css-transition mb0">
                    <div class="accordion_in">
                       <div id="callOnFooterNote" class="acc_head">Footer Note</div>
                       <div class="acc_content">
                        
                           <div class="form-group input-field">
                               <label class="label">
                                   <input type="text" id="footerNote" required class="form-control" maxlength="150" value="${loginUser.footer }" placeholder="Footer Note &#40; max 150 characters &#41;">
                                   <div class="label-text label-text2"></div>
                               </label>
                           </div>
                        
                       </div>
                   </div>
                 </div>
                   <!-- Footer Note - End -->
                    
                    
                    <input id="refInvId" value="${invoiceDetails.id }" type="hidden">
                    <input id="fInvValz" value="${invoiceDetails.invoiceValue }" type="hidden">
					 <div class="btns">
                        <button id="previewSubmit" class="btn btn-success btn-block" value=""></button>
                    </div>
                    
                    
	         
    	
    	</div>
    	
    	<div class="container" id='loadingmessage' style='display:none;' align="middle">
		  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
    	
    	<div class="container" id="previewCnDnInvoiceDiv" style="display:none">
    		<div class="card">
				<span>
					<center><h4><b> Preview Credit/Debit Note Details </b></h4></center>
				</span>
				<div class="invoicePage">
					
					<div class="invoiceDetail">
						<!-- <img src="images/logo.png"> -->
						  <img alt="No Logo Uploaded" onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';"  class="img-fluid" src="${pageContext.request.contextPath}/getOrgLogo">
						<div class="invoiceInfo">
							<h4>${userMaster.organizationMaster.orgName }</h4>
							<p><span>${gstinDetails.gstinAddressMapping.address} , ${gstinDetails.gstinAddressMapping.city} , ${gstinDetails.gstinAddressMapping.state} [ ${gstinDetails.state}  ] , ${gstinDetails.gstinAddressMapping.pinCode}</span></p>
							<p><span>GSTIN :</span>${invoiceDetails.gstnStateIdInString }</p>
							<%-- <p><span>PAN :</span>${userMaster.organizationMaster.panNumber }</p> --%>
							<p><span>Original Tax Document No:</span>${invoiceDetails.invoiceNumber }</p>
							<p><span>Original Tax Document Date:</span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /></p>
							<%-- <c:if test="${invoiceDetails.invoiceFor  == 'Service'}">
								<c:if test="${not empty invoiceDetails.invoicePeriodFromDate != 'NULL'}">
									<p><span>Service Period :</span><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodFromDate}" /> TO <fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoicePeriodToDate}" /></p>
								</c:if>
								
							</c:if>
							<p><span>Whether tax is payable on reverse charge : </span>${invoiceDetails.reverseCharge}</p>	 --%>
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
					<%-- 	<c:if test="${invoiceDetails.invoiceFor  == 'Product'}">
							<c:if test="${invoiceDetails.billToShipIsChecked == 'No' }">
								<div class="col-sm-6">
									<div class="invoiceInfo">
									<h4>Ship To</h4>
									<p><span>Name:</span>${invoiceDetails.shipToCustomerName }</p>
									<p><span>Address:</span>${invoiceDetails.shipToAddress }</p>
									<p><span>State:</span>${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]</p>
									<p><span>State Code:</span>${invoiceDetails.shipToStateCode }</p>
									<!-- <p><span>GSTIN/Unique Code:</span></p> -->
									</div>
								</div>
							</c:if>
							<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes' }">
								<div class="col-sm-6">
									<div class="invoiceInfo">
									<h4>Ship To</h4>
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
							</c:if>
							
						</c:if> --%>
						
					</div>
					
					<!-- Place of Supply -->
				<%-- 	<div class="row">
						<div class="col-sm-12">
							<div class="invoiceInfo invoiceFirst">Place Of Supply : 
								<c:if test="${invoiceDetails.billToShipIsChecked == 'Yes'}">
									${invoiceDetails.customerDetails.custState } [ ${customerStateCode } ]
								</c:if>
								<c:if test="${invoiceDetails.billToShipIsChecked == 'No'}">
									${invoiceDetails.shipToState } [ ${invoiceDetails.shipToStateCodeId } ]
								</c:if>
								
							</div>
						</div>
					</div> --%>
					
					
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
					<br>
					
					<div id="footerNoteDiv" style="text-align:center">
					</div>
					
        			 <div class="btns">
        			  <!--  <a class="btn btn-primary btn-threefourth" href="#!">E-Sign Invoice</a> -->
				         <a id="finalSubmitId" class="btn btn-primary btn-half" href="#!">Send Invoice</a>
				         <a id="backToAddCnDnDivDiv" class="btn btn-primary btn-half" href="#!">Edit</a>
				    </div>
				      
				
				</div><!-- ./invoicePage -->
				
			</div><!-- ./card -->
    	
    	</div>
    
    </section>
 </main>
 
 <form name="previewInvoice" method="post">
     <input type="hidden" name="id" value="">
     <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>