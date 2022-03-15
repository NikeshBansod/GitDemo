<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

  <!-- Dashboard set variables - Start -->
<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">


 <%@include file="dashboardMonth.jsp" %> 
 <section class="block">
	<div class="container">	

<div class="brd-wrap" >
<div class="loader"></div>
	    	<div id="breadcumheader">
	    	 
	    	  <div class="page-title">
                    <a href="#" id="goBackToDashboard" class="back"><i class="fa fa-chevron-left"></i></a>Generated Credit/Debit Notes for  ${month}  ${onlyYear}
              </div>
	    	  
	       		 <!-- <a href="#" id="goBackToDashboard"><strong>Dashboard</strong></a> &raquo; <strong>CNDN History</strong> -->
	       		 </div>
	       		 <div id="breadcumheader2">
	         <!--  <a  href="#" id="goBackToCNDN"><strong>CNDN List</strong></a> &raquo; <strong>Preview Credit/Debit Note</strong> -->
	          <div class="page-title">
                    <a href="#" id="goBackToCNDN" class="back"><i class="fa fa-chevron-left"></i></a>Preview Credit/Debit Note
              </div>
	          </div>
	    </div>
	    
	    
		 
		<%--  <h4 class="viewHeading" id="heading">Generated Credit/Debit Notes for  ${month}  ${onlyYear}</h4> --%>
		 
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		
	
	
		<div class="row" id="firstDivId">
		<div class="table-wrap">
			<table class="display nowrap" style="width:100%" id="invoiceHistoryTabCNDN" >
				<thead>
					<tr>
						
						<th >CNDN No.</th>
						<th >Invoice No.</th>
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${CNDNList}" var="CNDNList" varStatus="status">
						<tr>
							
							<td><a href="#" onclick="javascript:getCnDnDetails('${CNDNList[3]}','${CNDNList[0]}' , '${CNDNList[1]}');">${CNDNList[2]}</a> </td> 
							<td >${CNDNList[4]}</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
		</div>
		</div>
		
		<input type="hidden" id="originalInvGstnNo" value="">
		<input type="hidden" id="user_org_name" value="${userMaster.organizationMaster.orgName}">
		<input type="hidden" id="user_org_panNumber" value="${userMaster.organizationMaster.panNumber}">		
		</div>
		</br>
		
 <div class="container" id="secondDivId" style="display:none">

					<!-- Latest Html provided - Start -->
						<div class="invoice-wrap" id="divTwo">
						
					
					
					
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
<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>
 
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/previewCndn/previewCndn.js" />"></script> --%>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form> 

<form name="redirectToBackFromCNDN" method="post">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardCNDN.js"/>"></script>