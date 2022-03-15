<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>


<section class="insidepages">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
         	<a href="<spring:url value="/wHome#doc_management"/>"> Home</a> <span>&raquo;</span> My Documents
         	
 		 </header>
	</div>

		<div class="container">
		<div class="row text-center">				   			  			
		    		<a class="btn-floating btn-large" title="Generate Document" id="addinvoice" href="./wGenerateInvoice">
				    	<img src="/gstn/resources/images/home/ic_add_customer.svg">
				  	</a>	
		    	</div>
			<table class="table table-striped table-bordered table-hover " id="invoiceHistoryTab">
				<thead>
					<tr>
						<th style="text-align: center;">Sr. No.</th>
						<th style="text-align: center;">Invoice No.</th>
						<th style="text-align: center;">Created Against</th>
						<th style="text-align: center;">Invoice Value</th>
						<th style="text-align: center;">From</th>
						<th style="text-align: center;">Created Date</th>
					</tr>
				</thead>
				<tbody>			
				
				</tbody>
			</table>
		</div>

	
	<form name="manageInvoice" method="post">
	  <input type="hidden" name="id" value="">
      <input type="hidden" name="nicUserId" value="">
      <input type="hidden" name="nicPwd" value="">
      <input type="hidden" name="gstin" value="">
      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/invoice_list.js"/>"></script>