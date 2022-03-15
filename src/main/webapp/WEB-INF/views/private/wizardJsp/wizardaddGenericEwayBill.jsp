<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

<section class="insidepages">	
	<div class="container" id="loadingmessage" style="display:none;" align="center">
	  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
           
	<div class="inside-cont">
		<div class="breadcrumbs" >
			 <header class="insidehead" id="originalHeader" >
	         	<a href="<spring:url value="/wHome"/>">Home</a> <span>&raquo;</span> E-Way Bill Documents
	 		 </header>
		</div>	
		<div class="row text-center">						
    		<a href="#" onclick="javascript:generateEWayBill();" class="sim-button button5" style="margin:5px 0 0 0">
       			<span class="glyphicon glyphicon-file"></span> Generate E-Way Bill
     		</a>       				
     	</div> 	

	   	<div class="container">	
	       	<input type="hidden" id="clientId" name="clientId" value="${clientId}">
			<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
			<input type="hidden" id="appCode" name="appCode" value="${appCode}">
			<input type="hidden" name="userId" id="userId" value="${userId}">
			<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">				
			<table class="table table-striped table-bordered" id="invoiceHistoryTab">
				<thead>
					<tr>
						<th style="text-align: center;">Sr.No.</th>
						<th style="text-align: center;">E-way Bill No</th>
						<th style="text-align: center;">Created Against</th>
						<th style="text-align: center;">Date</th>
						<th style="text-align: center;">Valid Upto</th>
						<th style="text-align: center;">Status</th>
					</tr>
				</thead>
				<tbody>	
			
				</tbody>
			</table>        	
	    </div>	
	</div>        		
</section> 
     
<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
</form>

<form name="previewInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>
		
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/eWayBillWI/loadWIEwayBillDatatable.js" />"></script>