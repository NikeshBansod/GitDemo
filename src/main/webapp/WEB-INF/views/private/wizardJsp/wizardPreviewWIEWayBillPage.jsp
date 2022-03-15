<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="insidepages">	
	<%-- <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> --%>
	<div class="container" id="loadingmessage" style="display:none;" align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>		
	<div class="inside-cont">
		<div class="breadcrumbs">
			 <header class="insidehead" id="originalHeader">
	         	<a href="#" onclick="javascript:backToDocumets()" >E-Way Bill Documents</a> <span>&raquo;</span> E-Way Bill History
	 		 </header>
		</div>
		
		<div class="account-det">        	       	
        	<div class="container" id="secondDivId">
        		<div class="card">
        			<div class="row">
						<div class="invoiceDetail ">
					<!-- 	<div class="print-logo" style="float:right;margin:10px">
					   
					    <button class="sim-button button5"  id="optionsDiv" style="" value="Options">Action</button>
					    
					</div> -->
					<div class="insidebtn" style=";float:right;" id="optionsMultiDiv"> 
					
			<%-- 	<a href="#" onclick="javascript:downloadRecord('${invoiceDetails.id }');" class="sim-button button5" >
					      <span class="glyphicon glyphicon-download-alt"></span> &nbsp;Download & Print
					    </a>
					    	<br>
				<a href="#" onclick="javascript:sendMail('${invoiceDetails.id }');" class="sim-button button5" style="margin:5px 0 0 0"> Send PDF</a> --%>
				
				<a href="#" onclick="javascript:downloadWIEWayBills('${userId}','${ewaybillNo}','${clientId}','${secretKey}','${appCode}','${ipUsr}');" class="sim-button button5" style="margin:5px 0 0 0">
         					<span class="glyphicon glyphicon-download"></span> Download
       				</a>
       				
       				<a href="#" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">
          				<span class="glyphicon glyphicon-remove"></span> Cancel
        			</a>	      			
       			<br> 
       					
			</div>
		        			<div id="etable">
								<strong>1. E-Way Bill Details</strong><br><br>						
							</div>
							
						</div><br>
					</div>        		
					
					<div class="row">
						<div class="invoiceDetail">
							<strong>2. Address Details </strong><br><br>
							<div class="col-sm-6">
								<div class="invoiceInfo" id="fromGstin">								 
																	
								</div>
							</div>
							<div class="col-sm-6">
								<div class="invoiceInfo" id="toGstin">
									
								</div>
							</div>												
						</div>
					</div>
					
					<div class="row">
						<div class="invoiceDetail">
							<strong>3. Goods Details</strong><br><br>							
                            <table id="mytable2">
								<thead>
								  <tr>
									<th>HSN Code</th>
									<th>Product Descripition</th>
									<th>Quantity</th>
									<th>Taxable Amount Rs.</th>
									<th>Tax Rate</th>
								  </tr>
								</thead>
								<tbody>									 
									 
								</tbody>
							</table>
							<div class="col-sm-12">
								<div class="invoiceInfo" id="itemTaxDet">
	                            	
	                            </div>
	                        </div> 
						</div>					
        			</div>
        			
        			<div class="row">
						<div class="invoiceDetail">
							<strong> 4. Transportation Details </strong><br><br>
							<div class="col-sm-12">
								<div class="invoiceInfo "  id="transportTable">
									
								</div>
							</div>							
						</div>
					</div>
					
        			<div class="row">
        				<div id="vehicleTable">
        					<strong> 5. Vehicle Details </strong>  <br><br>  					
			            	 <table id="vehicleDetTable">		
								<thead>
								  <tr>
									<th>Mode</th>
									<th>Vehicle / Trans<br>Doc No & Dt.</th>
									<th>From</th>
									<th>Entered Date</th>
									<th>Entered By</th>
									<th>CEWB No.<br>(If any)</th>
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
		
       			<%-- <div class="row text-center" id="optionsMultiDiv">
        			<a href="#" onclick="javascript:downloadWIEWayBills('${userId}','${ewaybillNo}','${clientId}','${secretKey}','${appCode}','${ipUsr}');" class="sim-button button5" style="margin:5px 0 0 0">
         					<span class="glyphicon glyphicon-download"></span> Download
       				</a>
       				
       				<a href="#" onclick="javascript:canceleWayBillPage('${invoiceDetails.id}');" class="sim-button button5" style="margin:5px 0 0 0">
          				<span class="glyphicon glyphicon-remove"></span> Cancel
        			</a>	      				
			    </div> --%>
			    
		<input type="hidden" name="userId" id="userId" value="${userId}">
		<input type="hidden" id="ewaybillNo" name="ewaybillNo" value="${ewaybillNo}">	
        <input type="hidden" id="clientId" name="clientId" value="${clientId}">
		<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
		<input type="hidden" id="appCode" name="appCode" value="${appCode}">
		<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">	
			
	</div>
</section> 
	
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBillWI/viewEWayBills.js" />"></script>

<form name="downloadWIEWayBill" id="downloadEWayBill" method="post">
<%-- <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> --%>
    <input type="hidden" id="client_id" name="client_id" value="">
	<input type="hidden" id="secret_key" name="secret_key" value="">
	<input type="hidden" id="app_code" name="app_code" value="">
	<input type="hidden" id="ip_usr" name="ip_usr" value="">	
	<input type="hidden" id="userId" name="userId" value="">
	<input type="hidden" id="ewaybillno" name="ewaybillno" value="">	
</form>

<form name="cancelEwayBill" id="cancelEwayBill" method="post">
    <input type="hidden" name="ewayBillNo" value="">
    <input type="hidden" name="gstin" value="">
</form>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
		