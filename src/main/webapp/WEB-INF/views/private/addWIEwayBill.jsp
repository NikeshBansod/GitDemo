<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 


<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>

<header class="insidehead" id=""> 
     <c:choose>
		  <c:when test="${loggedInFrom == 'MOBILE'}">
		   	  <a href="#" onclick="javascript:backToValidatePage()" class="btn-back"><i class="fa fa-angle-left"></i></a>
     		  <a class="logoText" href="home#invoice" id="generateEwayWIPageHeader" >
		     	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
		      </a>
		  </c:when>
		  <c:otherwise>
		      <a href="#" onclick="javascript:backToLoginValidate();" class="btn-back"><i class="fa fa-angle-left"></i></a>					   	
		   	  <a class="logoText" href="wHome" id="generateEwayWIPageHeader" >
		     	<img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1>
		      </a>
		  </c:otherwise>
	</c:choose>
    
</header>

<section class="container-fluid e-WayBill">
		<div class="container" id='loadingmessage' style='display:none;' align="center">
			<img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
		
		<div class="row" id="DivWIEwayBill">
		
		<div class="row">
			<div class="col-md-12">
				<h3 class="light-blue">e-WayBill Entry Form</h3>
				<p class="text-right">[<span class="red-text">&bull;</span> Indicates mandatory fields for E-Way Bill] <!-- and 
										<span class="green-txt fs24">&bull;</span> indicates mandatory fields for GSTR-1  -->
				</p>
			</div>
		</div> 
			<div class="col-md-12">		
				<div class="panel-group" id="ewaybill-form" role="tablist" aria-multiselectable="true">
				  <div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv1">
					  <h4 class="panel-title">
						<a role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content1" aria-expanded="true" aria-controls="Content1">
						  Transaction Details
						</a>
					  </h4>
					</div>
					<div id="Content1" class="panel-  in" role="tabpanel" aria-labelledby="headingdiv1">
					  <div class="panel-body">
							<div class="row">
								<div class="col-md-2">
									<label>Transaction Type <span class="red-text ">&bull;</span></label>
									<select class="form-control require forItemRequire" id="transctionType" name="transctionType">
									</select>
								</div>								
								<div class="col-md-2">
									<label>Sub Type <span class="red-text">&bull;</span></label>
									<select class="form-control require forItemRequire"  data-msg="This field is required." id="subType" name="subType">										
									</select>								  
									  <span class="text-danger cust-error" id="subType-req" style="display:none;"></span>
								</div>
								<div class="col-md-2" id="otherSubType">
									<label>Specify Others<span class="red-text">•</span></label>
									<input type="text" class="form-control require forItemRequire" data-msg="This field is required." id="otherSubTypes" name="otherSubType" maxlength="16">	
									</div>
								<div class="col-md-2">
									<label>Document Type <span class="red-text">&bull;</span></label>
									<select  class="form-control require forItemRequire"  data-msg="This field is required." id="documentType" name="documentType">
									</select>								  
									  <span class="text-danger cust-error" id="documentType-req" style="display:none;"></span>
								</div>
								<div class="col-md-2">
									<label>Document No <span class="red-text">&bull;</span></label>
									<input type="text" class="form-control require forItemRequire"  data-msg="This field is required." id="documentNo" name="documentNo" maxlength="16">								  
									  <span class="text-danger cust-error" id="documentNo-req" style="display:none;"></span>
								</div>
								<div class="col-md-2">
									<label>Document Date <span class="red-text">&bull;</span></label>
									<div class="input-group">
									  <div class="input-group-addon minusdate">-</div>
									  <input type="text" class="form-control transdocDate" readonly="readonly" placeholder="DD/MM/YY" id="documentDate" name="documentDate">
									  <div class="input-group-addon plusdate">+</div>
									</div>
								</div>
							</div><br>
							<div class="row" id="buySelTypeDiv">
								<div class="col-md-12" >
									<label id="buySelTypeText">Buyer/Seller Type</label><span class="red-text">&bull;</span>
									<c:choose>
										<c:when test="${loggedInFrom == 'MOBILE'}">
											<br>
										</c:when>
									</c:choose>
									<label class="noBold">
										<input type="radio" name="buyerSellerType"  value="R" > Registered
									</label>
									<label class="noBold">
										<input type="radio" name="buyerSellerType"  value="UR"> Unregistered
									</label>
									<i class="clearfix"></i>	
									<span class="text-danger cust-error" id="buyerSellerType-req" style="display:none;"></span>
								</div>
							</div>
						 </div>
					  </div>
					</div>
				 
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv2">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content2" aria-expanded="false" aria-controls="Content2">
						  Bill From
						</a>
					  </h4>
					</div>
					<div id="Content2" class="panel- " role="tabpanel" aria-labelledby="headingdiv2">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4">
									<label>GSTIN <span class="red-text">&bull;</span></label>
									<div class="input-group">
									  <input type="text" class="form-control require forItemRequire" data-msg="This field is required and should be in a proper format." id="billFromGstin" name="billFromGstin" maxlength="15" >
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
												data-content="Enter Consignor GSTIN or URP for Unregistered Person.">
										</span>
									  </div>
									</div>									  
									  <span class="text-danger cust-error" id="billFromGstin-back-req" style="display:none;"></span>
								</div>
								<div class="col-md-4">
									<label>Name <span class="red-text">&bull;</span></label>
									<input type="text" class="form-control require" data-msg="This field is required." id="billFromName" name="billFromName" maxlength="50" readonly>
									 <!-- <div class="input-group">
									 	<input type="text" class="form-control " data-msg="This field is required." id="billFromName" name="billFromName" maxlength="50" readonly>
										  <div class="input-group-addon">
											<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom"
												 data-content="If addl. places of business are there for your GSTIN, please enter 2-3 char of name to pop up a list OR enter 2-3 char of consignor name to pop up from your supplier master list.">
											</span> 
											<span class="fa fa-times-circle"></span>
										  </div>
									</div> -->
								</div>
								<div class="col-md-4" id="billFromStateDiv">
									<label>State <span class="red-text">&bull;</span></label>
									<select class="form-control" id="billFromState" name="billFromState" disabled="true">
										<option>Select State</option>
									</select>
									 <span class="text-danger cust-error" id="billFromState-req" style="display:none;"></span>										
									<input type="hidden" class="form-control billFromStateId" id="billFromStateId" name="billFromStateId" value="">
									<input type="hidden" class="form-control billFromStateValue" id="billFromStateValue" name="billFromStateValue" value="">
								</div>
							</div>
						</div>
				  </div>
				</div>  
				
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv3">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content3" aria-expanded="false" aria-controls="Content3">
						  Dispatch From
						</a>
					  </h4>
					</div>
					<div id="Content3" class="panel- " role="tabpanel" aria-labelledby="headingdiv3">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4">
									<label>Address <span class="red-text"></span></label>
									<input type="text" class="form-control " data-msg="This field is required." id="dispatchFromAddress" name="dispatchFromAddress" maxlength="500" >							  
									  <span class="text-danger cust-error" id="dispatchFromAddress-req" style="display:none;"></span>
								</div>	
								<div class="col-md-4">
									<label>Place <span class="red-text"></span></label>
									<input type="text" class="form-control " data-msg="This field is required." id="dispatchFromPlace" name="dispatchFromPlace" maxlength="100" >								  
									  <span class="text-danger cust-error" id="dispatchFromPlace-req" style="display:none;"></span>
								</div>
								<div class="col-md-4">

									  <div class="row">
										<div class="col-md-4">
											 <label>Pin Code <span class="red-text">&bull;</span></label>
											<input type="text" class="form-control mb10 require forItemRequire" data-msg="This field is required." id="dispatchFromPincode" name="dispatchFromPincode" maxlength="6" >								  
									  		<span class="text-danger cust-error" id="dispatchFromPincode-back-req" style="display:none;"></span>
										</div>	
										<div class="col-md-8">
										 	   <label>State <span class="red-text">&bull;</span></label>
										 	   <input type="text" class="form-control " data-msg="This field is required." id="dispatchFromState" name="dispatchFromState"  readonly>
											 <!--  <select class="form-control mb10" id="dispatchFromState" name="dispatchFromState" >
												<option>Select State</option>
											  </select>	 -->							
												<input type="hidden" class="form-control " id="dispatchFromStateId" name="dispatchFromStateId" value="">
												<input type="hidden" class="form-control " id="dispatchFromStateValue" name="dispatchFromStateValue" value="">
											<!-- <div class="input-group">
											  <select class="form-control mb10" id="dispatchFromState" name="dispatchFromState" disabled="true">
												<option>Select State</option>
											  </select>								
												<input type="hidden" class="form-control " id="dispatchFromStateId" name="dispatchFromStateId" value="">
												<input type="hidden" class="form-control " id="dispatchFromStateValue" name="dispatchFromStateValue" value="">
											  <div class="input-group-addon">
												<span class="fa fa-question-circle" data-container="body" data-toggle="popover" data-trigger="hover"  data-placement="bottom" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus. Vivamus sagittis lacus vel augue laoreet rutrum faucibus."></span> 
											  </div>
											</div> -->
										</div>
									  </div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv4">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content4" aria-expanded="false" aria-controls="Content4">
						  Bill To
						</a>
					  </h4>
					</div>
					<div id="Content4" class="panel- " role="tabpanel" aria-labelledby="headingdiv4">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4">
									<label>GSTIN <span class="red-text">&bull;</span></label>
									<div class="input-group">
									  <input type="text" class="form-control require forItemRequire" data-msg="This field is required and should be in a proper format." id="billToGstin" name="billToGstin" maxlength="15" >
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="Enter Consignee GSTIN or URP for Unregistered Person.">
										</span>
									  </div>
									</div>									  
									  <span class="text-danger cust-error" id="billToGstin-back-req" style="display:none;"></span>
								</div>
								<div class="col-md-4">
									<label>Name <span class="red-text">&bull;</span></label>
									 <input type="text" class="form-control require" data-msg="This field is required." id="billToName" name="billToName" maxlength="50" readonly>
									 <!-- <div class="input-group">
									  <input type="text" class="form-control " data-msg="This field is required." id="billToName" name="billToName" maxlength="50" readonly>
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="If addl. places of business are there for your GSTIN, please enter 2-3 char of name to pop up a list or enter 2-3 char of cosnginee name to pop up from your client master list.">
										</span> 
										<span class="fa fa-times-circle"></span>
									  </div>
									</div> -->
								</div>
								<div class="col-md-4" id="billToStateDiv">
									<label for="exampleInputName2">State <span class="red-text">&bull;</span></label>
									<select class="form-control" id="billToState" name="billToState" disabled="true">
										<option>Select State</option>
									</select>									  
									  <span class="text-danger cust-error" id="billToState-req" style="display:none;"></span>					
									<input type="hidden" class="form-control " id="billToStateId" name="billToStateId" value="">
									<input type="hidden" class="form-control " id="billToStateValue" name="billToStateValue" value="">
								</div>
							</div>
						</div>
				  </div>
				</div>  
				
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv5">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content5" aria-expanded="false" aria-controls="Content5">
						  Ship To
						</a>
					  </h4>
					</div>
					<div id="Content5" class="panel- " role="tabpanel" aria-labelledby="headingdiv5">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4">
									<label>Address <span class="red-text"></span></label>
									<input type="text" class="form-control " data-msg="This field is required." id="shipToAddress" name="shipToAddress"  maxlength="500" >							  
									  <span class="text-danger cust-error" id="shipToAddress-req" style="display:none;"></span>
								</div>
								<div class="col-md-4">
									<label>Place <span class="red-text"></span></label>
									<input type="text" class="form-control " data-msg="This field is required." id="shipToPlace" name="shipToPlace" maxlength="100" >								  
									  <span class="text-danger cust-error" id="shipToPlace-req" style="display:none;"></span>
								</div>
								<div class="col-md-4">									  
									  <i class="clearfix"></i>
									  <div class="row">
										<div class="col-md-4">
										<label>Pin Code <span class="red-text">&bull;</span></label>
											<input type="text" class="form-control mb10 require forItemRequire" data-msg="This field is required." id="shipToPincode" name="shipToPincode" maxlength="6" >								  
									  		<span class="text-danger cust-error" id="shipToPincode-req" style="display:none;"></span>
										</div>
										<div class="col-md-8">
										 <label>State <span class="red-text">&bull;</span></label>
											 <!--  <select class="form-control" id="shipToState" name="shipToState" >
												<option>Select State</option>
											  </select>	 -->
											   <input type="text" class="form-control " data-msg="This field is required." id="shipToState" name="shipToState"  readonly>						
												<input type="hidden" class="form-control " id="shipToStateId" name="shipToStateId" value="">
												<input type="hidden" class="form-control " id="shipToStateValue" name="shipToStateValue" value="">
											  <!-- <div class="input-group">
											  <select class="form-control" id="shipToState" name="shipToState" disabled="true">
												<option>Select State</option>
											  </select>							
												<input type="hidden" class="form-control " id="shipToStateId" name="shipToStateId" value="">
												<input type="hidden" class="form-control " id="shipToStateValue" name="shipToStateValue" value="">
											  <div class="input-group-addon">
												<span class="fa fa-question-circle" data-container="body" data-toggle="popover" data-trigger="hover"  data-placement="bottom" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus. Vivamus sagittis lacus vel augue laoreet rutrum faucibus."></span> 
											  </div>
											</div> -->
										</div>
									  </div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv6">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content6" aria-expanded="false" aria-controls="Content6"> Item Details </a>
					  </h4>
					</div>
					<div id="Content6" class="panel- " role="tabpanel" aria-labelledby="headingdiv6">
						<div class="panel-body">
							<div class="row">
								<div class="addrow">
									<div class="rows">
										<div class="col-md-2">
											<label>Product by HSN/Description</label>
										</div>
										<div class="col-md-2">
											<label>Product Name<span class="red-text">&bull;</span></label>
										</div>
										<div class="col-md-1">
											<label>HSN <span class="red-text">&bull;</span></label>
											
										</div>
										<div class="col-md-1">
											<label>Quantity <span class="red-text">&bull;</span></label>
											
										</div>
										<div class="col-md-1">
											<label>Unit <span class="red-text">&bull;</span></label>
										   
										</div>
										<div class="col-md-2">
										   <div class="tax_row">	
												<div class="col-xs-4">
													<label>Rate<span class="red-text">&bull;</span></label>
												</div>
												<div class="col-xs-8">
													<label>Taxable value (RS)<span class="red-text">&bull;</span></label>
												</div>
											</div><i class="clearfix"></i>
										</div>
										<div class="col-md-3">
											<div class="row">                      	
												<div class="col-xs-3">              				
													<label>CGST+ SGST Rate(%) <span class="green-txt fs24">•</span> </label>              				
												</div>                          
												<div class="col-xs-3">              				
													<label>IGST Rate(%)<span class="green-txt fs24">•</span> </label>              				
												</div>                          
												<div class="col-xs-3">      
													<label>CESS Advol Rate(%)<span class="green-txt fs24">•</span> </label>              				
												</div>                          
												<div class="col-xs-3">              				
													<label>CESS non.Advol. Rate<span class="green-txt fs24">•</span> </label>              				
												</div>            	
											</div>
										</div>   
										<i class="clearfix"></i>	
									</div>
									
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 text-center"> 
									<!-- <p>Note : Incase of no cess enter "<span class="red-text">0</span>" in cess box.</p> -->
									<button class="btn add_row" type="button" onclick="EwayBill.itemvalidation()"> <i class="fa fa-plus"></i> Add Rows</button> 
									<button class="btn calculateallrow" type="button" onclick="EwayBill.calculate()">Calculate</button> 
								</div>
							</div>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-sm-3 col-xs-6">
		              				<label>Total Tax'ble Amount<span class="green-txt fs24">&bull;</span> </label>
									<input type="text" class="form-control mb10" id="totaltaxableamt" name="totaltaxableamt" readonly="readonly" value="0">
								</div>
								<div class="col-sm-3 col-xs-6">
		              				<label>CGST Amount<span class="green-txt fs24">&bull;</span> </label>
									<input type="text" class="form-control mb10" id="cgstamt" name="cgstamt" readonly="readonly" value="0">
								</div>
								<div class="col-sm-3 col-xs-6">
		              				<label>SGST Amount<span class="green-txt fs24">&bull;</span> </label>
									<input type="text" class="form-control mb10" id="sgstamt" name="sgstamt" readonly="readonly" value="0">
								</div>
								<div class="col-sm-3 col-xs-6">
		              				<label>IGST Amount<span class="green-txt fs24">&bull;</span> </label>
									<input type="text" class="form-control mb10" id="igstamt" name="igstamt" readonly="readonly" value="0">
								</div>
								<div class="col-sm-3 col-xs-6">
		              				<label>CESS Advol Amount<span class="green-txt fs24">&bull;</span> </label>
									<input type="text" class="form-control mb10" id="cessadvolamt" name="cessadvolamt" readonly="readonly" value="0">
								</div>
								<div class="col-sm-3 col-xs-6">
									<label>CESS Non Advol Amount<span class="green-txt fs24">&bull;</span> </label>
									<div class="input-group">
									  <input type="text" class="form-control mb10" id="cessnonadvolamt" name="cessnonadvolamt" value="0">
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="Please calculate & enter the total Cess Non Advalorem amount">
										</span>
									  </div>
									</div>	
								</div>
								<div class="col-sm-3 col-xs-6">
		              				<label>Other Amount(+/-)<span class="fs24"></span></label>
									<div class="input-group">
									  <input type="text" class="form-control mb10" id="otheramt" name="otheramt" value="0">
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="Enter any other Charges or Amount or Discount mentioned in invoice. In case of discount give with the -ve amount.">
										</span>
									  </div>
									</div>
								</div>
								<div class="col-sm-3 col-xs-6">
		              				<label>Total Inv. Amount<span class="green-txt fs24">&bull;</span> </label>
									<div class="input-group">
									  <input type="text" class="form-control mb10" id="totalinvamt" name="totalinvamt" value="0">
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="Total Amount(Tot Taxable Amt+CGST+SGST+IGST+CESS Advol+CESS Non Advol+Other Amt)">
										</span>
									  </div>
									</div>
								</div>
							</div>	
						</div>
					</div>
				</div>
				
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv7">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content7" aria-expanded="false" aria-controls="Content7"> Transportation Details </a>
					  </h4>
					</div>
					<div id="Content7" class="panel- " role="tabpanel" aria-labelledby="headingdiv7">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4">
									<label>Approximate distance (in KM) <span class="red-text">•</span></label>
									<div class="input-group">
									  <input type="text" class="form-control require mb10" data-msg="This field is required." id="approxDistance" name="approxDistance">
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="Approximate Distance between consignor and consignee place." data-original-title="" title="">
										</span>
									  </div>
									</div>
								</div>
								<div class="col-md-4">
									<label>Transporter Name <span class="red-text"></span></label>
									<input type="text" class="form-control " data-msg="This field is required." id="transporterName" name="transporterName" maxlength="50" >
								</div>
								<div class="col-md-4">
									<label>Transporter ID  <span class="red-text"></span></label>
									<div class="input-group">
									  <input type="text" class="form-control " data-msg="This field is required." id="transporterId" name="transporterId" maxlength="20" >
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-container="body" data-toggle="popover" data-placement="bottom" 
											data-content="Enter Transporter GSTIN or Transporter Enrolment ID(TRANSIN)." data-original-title="" title="">
										</span>
									  </div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel">
					<div class="panel-heading" role="tab" id="headingdiv8">
					  <h4 class="panel-title">
						<a class="d" role="button" data-toggle="" data-parent="#ewaybill-form" href="#Content8" aria-expanded="false" aria-controls="Content7"> Part B </a>
					  </h4>
					</div>
					<div id="Content8" class="panel- " role="tabpanel" aria-labelledby="headingdiv8">
						<div class="panel-body">
							<div class="row">
								<div class="col-md-3">
									<label>Mode </label>
									<i class="clearfix"></i>	
									<label class="noBold">
										<input type="radio" name="mode"  value="1" checked> Road
									</label>
									<label class="noBold">
										<input type="radio" name="mode"  value="2"> Rail
									</label>
									<label class="noBold">
										<input type="radio" name="mode"  value="3"> Air
									</label>
									<label class="noBold">
										<input type="radio" name="mode" value="4"> Ship
									</label>
								</div>
								<div class="col-md-3">
									<label>Vehicle Type</label>
									<i class="clearfix"></i>	
									<label class="noBold">
										<input type="radio" name="vechicle_type"  value="R" checked> Regular 
									</label>
									<label class="noBold">
										<input type="radio" name="vechicle_type"  value="O"> Over Dimensional Cargo
									</label>
								</div>
								<div class="col-md-2">
									<label>Vehicle No </label>
									<div class="input-group">
									  <input type="text" class="form-control" id="vechicleNo" name="vechicleNo" maxlength="10" >
									  <div class="input-group-addon">
										<span class="fa fa-question-circle" data-trigger="hover" data-html="true" data-container="body" data-toggle="popover" data-placement="top" 
											  data-content="Format of Vehicle No.<br/>
															AB121234&nbsp;&nbsp;(or)
															AB12A1234&nbsp;&nbsp;(or)
															AB12AB1234&nbsp;&nbsp;(or)
															ABC1234&nbsp;&nbsp;(or)
															AB123A1234&nbsp;&nbsp;(or)
															AB12ABC1234&nbsp;&nbsp;(or)
															DFXXXXXX&nbsp;(for Defence Vehicle)&nbsp;&nbsp;&nbsp;(or)
															TRXXXXXX&nbsp;(for Temporary RC)&nbsp;&nbsp;&nbsp;(or)
															BPXXXXXX&nbsp;(for Bhutan)&nbsp;&nbsp;&nbsp;(or)
															NPXXXXXX&nbsp;(for Nepal)&nbsp;"
											   data-original-title="" title="">
										</span>
									  </div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="row">
										<div class="col-md-5">
										<label id="transporterDocNo">Transporter Doc No.<span class="red-text">&bull;</span></label>
										<label id="rrNo" style="display: none;">RR No.<span class="red-text">&bull;</span></label>
										<label id="airwayNo" style="display: none;">Airway Bill No.<span class="red-text">&bull;</span></label>
										<label id="ladingNo" style="display: none;">Bill of lading No.<span class="red-text">&bull;</span></label>
											<input type="text" class="form-control mb10 require" id="transporterDocumentNo" name="transporterDocumentNo" maxlength="10">
										</div>
										<div class="col-md-7">
										<label id="transporterDate"> </label>
											<div class="input-group">
											  <div class="input-group-addon minusdate">-</div>
											  <input type="text" class="form-control transdocDate" placeholder="DD/MM/YY" id="transporterDocDate" name="transporterDocDate">
											  <div class="input-group-addon plusdate">+</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id="calculateFlagForNoOfProducts" name="calculateFlagForNoOfProducts" value="0">
				<input type="hidden" id="itemCounter" name="itemCounter" value="0">
				<input type="hidden" id="clientId" name="clientId" value="${clientId}">
				<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
				<input type="hidden" id="appCode" name="appCode" value="${appCode}">
				<input type="hidden" id="sgstTotalAmount" name="sgstTotalAmount" value="0">
				<input type="hidden" id="cgstTotalAmount" name="cgstTotalAmount" value="0">
				<input type="hidden" id="igstTotalAmount" name="igstTotalAmount" value="0">
				<input type="hidden" id="totalAmount" name="totalAmount" value="0">
				<input type="hidden" id="totalcessAmount" name="totalcessAmount" value="0">
				<input type="hidden" id="nicId" name="nicId" value="${nicId}">
				<input type="hidden" id="nicPassword" name="nicPassword" value="${nicPassword}">
				<input type="hidden" id="gstin" name="gstin" value="${gstin}">
				<input type="hidden" id="userId" name="userId" value="${userId}">
				<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">				
				<input type="hidden" id="loggedInFrom" name="loggedInFrom" value="${loggedInFrom}">
				
				<div class="text-center" id="myBttn">
				</div>
			</div>
		</div>
	</div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/addEwayBillWI/populateData.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addEwayBillWI/loadEwayBillWIGrid.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addEwayBillWI/validateEwayBillWI.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/addEwayBillWI/pincode-autocomplete.js"/>"></script>