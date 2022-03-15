
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

<header class="insidehead">
   <a href="<spring:url value="/home#doc_management" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
   <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
</header>

<div class="container" style="background-color:white;">
               <h4 style="text-align: center;">Document History</h4>
			<table class="table table-striped table-bordered table-hover " id="invoiceHistoryTab" >
				<thead>
					<tr>
						<th style="text-align: left;">Invoice No.</th>
						<th style="text-align: left;">Created Against</th>
						<th style="text-align: left;">From</th>
					</tr>
				</thead>
				<tbody>			
				
				</tbody>
			</table>
  <!-- <div class="box-border">
    
     <div class="cust-wrap">
        <div class="dnynamicProducts" id="toggle">							
        </div>
    </div>

  </div> -->
 
</div>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/invoice_list.js"/>"></script>
