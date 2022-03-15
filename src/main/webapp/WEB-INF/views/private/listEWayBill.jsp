<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
 
 <link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>


 <header class="insidehead" id="originalHeader">
      <a  href="home#eway_management" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="./home">
      	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>	
      </a>
 </header>
   
   <header class="insidehead" style="display:none" id="previewHeader">
   <a href="<spring:url value="/getGenericEWayBills" /> " id="backToPreview" class="btn-back"><i class="fa fa-angle-left"></i></a>
      <a class="logoText" href="./home">
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      </a>
    
	</header>
	
	<header class="insidehead" id="customerEmailPageHeader" style="display:none">
	<a  href="<spring:url value="/getGenericEWayBills" /> " class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
            </header>
	<main>
	<section class="block ">
		<div class="container" id='loadingmessage' style='display:none;' align="middle">
		  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
		</div>
	<!-- 	<div class="container" id="divTwo">  -->
			<div class="card ewaybillDetailPage" id="card " >
				<div class="invoicePage" id="firstDivId">
				<span><center><h5><b></b></h5></center></span>
					 
					<input type="hidden" id="clientId" name="clientId" value="${clientId}">
					<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
					<input type="hidden" id="appCode" name="appCode" value="${appCode}">
					<input type="hidden" name="userId" id="userId" value="${userId}">
					<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
					
				</div>
	
				 <div class="invoicePage" id="secondDivId" style="display:none">
				<div class="row">
				<a class="downToLast" href="#" onclick="scrollSmoothToBottom()"  class="btn-back"><i class="fa fa-angle-double-down"></i></a>
					<div class="invoiceDetail">
							
								<div id="etable">
								 <h5> 1. E-Way Bill Details </h5> <br>
								</div>
					</div>
				</div>
						<div class="row">
						<div class="invoiceDetail">
						<h5> 2. Address Details </h5>  <br>
						<div class="col-sm-6">
							<div class="invoiceInfo " id="fromGstin">
							
							</div>
						</div>
								<div class="col-sm-6">
									<div class="invoiceInfo " id="toGstin">
									
									</div>
								</div>
						</div>
					</div>
						
					<!-- Start -->
					<div class="row">
				<!-- 	<div class="invoiceTable"> -->
			          	 
			          	 <div class="invoiceDetail">
								<!-- <div id="invoiceTable"> -->
								<h5> 3. Goods Details </h5>  <br>
								<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          
                          	<ul class="e-productdet headers">
                               <li>HSN Code</li>
                               <li>Product Name</li>
                               <li>Quantity</li>
                               <li>Taxable Amount Rs.</li>
                               <li>Tax Rate</li>
                            </ul>
                           	 <div id="itemDet">
                           	 	
                           	 </div>
                            	  
                            	<br>
                            	 </div>
                            </div>
                            <!-- </div>
                            </div>
                            	<div class="invoiceDetail">  -->
                            	<div class="col-sm-6">
							<div class="invoiceInfo " id="itemTaxDet">
                          
                            </div>
                            </div> 
                             </div>	
						
			         <!--  	</div> -->
			          	 </div>
					 
					  <div class="row">
							<div class="invoiceDetail">
							 <h5> 4. Transportation Details </h5> <br>
							<div class="col-sm-6">
							<div class="invoiceInfo "  id="transportTable">
								
								 </div>
								 </div>
								
							</div>
						</div>
						 <div class="row">
						<!-- 	<div class="invoiceTable">	 -->				
						<div id="vehicleTable">
								
								<h5> 5. Vehicle Details </h5>
								<div class="invoiceTable">
						
                          <!-- First Table Starts -->
                          <div id="">
                          
                          	<ul class="transportdet headers">
                               <li>Mode</li>
                               <li>Vehicle / Trans <br> Doc No & Dt.</li>
                               <li>From</li>
                               <li>Entered Date</li>
                               <li>Entered By</li>
                               <li>CEWB No.<br> (If any)</li>
                            </ul>
                            	 <ul class="transportdet" id="vehicleDet">
                            	 </ul>
                            </div>
                            </div>
						</div>
					<!-- 	</div> -->
						</div>
					<!-- End -->	
					
					<br>
					<a class="downToUp" href="#" onclick="scrollSmoothToTop()" class="btn-back"><i class="fa fa-angle-double-up"></i></a></div>
					<div class="row text-center" id="optionsMultiDiv">
        			<!--  <div class="btns"> 
				        <a href="#" onclick="javascript:sendEwayBillPdf('${invoiceDetails.gstnStateIdInString }', '${invoiceDetails.id }');" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-envelope"></span> Send eWay Bill
        				</a> -->
        				 <a href="#" id="sendMailId" class="btn btn-info" style="margin:5px 0 0 0">
          					<span class="glyphicon glyphicon-envelope"></span> Send eWay Bill
        				</a>
        				
        				 
        				<a href="#" onclick="javascript:canceleWayBillPage();" class="btn btn-info" style="margin:5px 0 0 0">
	          					<span class="glyphicon glyphicon-remove"></span> Cancel
	        				</a>
	        				
	        				<div  class="row text-right">
					
	        			
        				<br/>
        				
				    </div>
							
			</div>
			 </div><!-- ./card -->
		
	<!--	</div> ./container -->
	
				<div class="container" id="customerEmailDiv" style="display:none;">
			      <form class="form" role="form">
			        <div class="card">

                        <div class="form-group input-field mandatory">
                            <label class="label">
                                <input type="text" id="cust_email_addr" class="form-control" maxlength="100">  
                                <div class="label-text">Email Address</div>
                            </label>
                            <span class="text-danger cust-error" id="cust-email-format">This field should be in a correct format</span>
                        </div>

                        <div class="form-group input-field text-center">
                            <a href="#" id="custEmailSave" class="btn btn-primary">Send</a>
                            <a href="#" id="backToGenericEwayPreview" class="btn btn-secondary">Cancle</a>
                        </div>

                    </div>
			      </form>
			    </div>
	</section>
</main>

 <!-- <div class="cust-wrap">
		
		<div class="dnynamicewaybillDetails" id="toggle">								
		</div>
      </div> -->
      <div class="ewaybillTable">	
		<div class="card">
		<h4 style="text-align: center;">Generic EwayBill History</h4>
		<table class="table table-striped table-bordered ewaybillValues"  id="ewaybillTab" >
				<thead>
							<tr>
								<th style="text-align: center;">From</th>
								<th style="text-align: center;">Ewaybill No</th>
								
							</tr>
						</thead>
			</table>							
		</div>
		</div>
      
<script type="text/javascript" src="<spring:url value="/resources/js/eWayBillWI/viewEWayBills.js" />"></script>

<form name="downloadEWayBill" id="downloadEWayBill" method="post">
    <input type="hidden" name="invoiceId" value="">
    <input type="hidden" name="gstin" value="">
    <input type="hidden" name="ewaybillNo" value="">
</form>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>

<form name="cancelEwayBill" id="cancelEwayBill" method="post">
    <input type="hidden" name="ewayBillNo" value="">
    <input type="hidden" name="gstin" value="">
</form>

