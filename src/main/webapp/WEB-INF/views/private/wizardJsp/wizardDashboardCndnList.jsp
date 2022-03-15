<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>
<%@include file="../common-alert.jsp"%>
 
 <%@include file="dashboardMonth.jsp" %> 
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
<link
	href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="<spring:url value="/resources/js/DashBoard/wizardDashboardDate.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>


<section class="insidepages">


 
 <%@include file="dashboardMonth.jsp" %> 	


	<div class="breadcrumbs"><div class="col-md-12" id="listheader1">

			<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a
				href="#" id="goBackToDashboard">Dashboard</a> <span> &raquo; </span> CNDN
			List
		</div>
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<header class="insidehead" id="generateInvoiceDefaultPageHeader">
				<h4>
					<b>Generated Credit/Debit Note from ${month} To ${onlyYear} </b>
				</h4>

				<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>
			</header>
		</div>
	</div>


	<div class="cust-wrap">
		<div id="toggle">

			<div class="container">
				<!-- class="dnynamicCustomerDetails" -->
				<table class="table table-striped table-bordered"
					id="invoiceHistoryTab">
					<thead>
						<tr role="row">
							<th style="text-align: center; width: 201px;" class="sorting_asc"
								tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1"
								colspan="1" aria-sort="ascending"
								aria-label="Sr.No.: activate to sort column descending">Sr.No.</th>
							<th style="text-align: center; width: 416px;" class="sorting"
								tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1"
								colspan="1"
								aria-label="Invoice No.: activate to sort column ascending">Cndn
								No.</th>
							<th style="text-align: center; width: 416px;" class="sorting"
								tabindex="0" aria-controls="invoiceHistoryTab" rowspan="1"
								colspan="1"
								aria-label="Invoice No.: activate to sort column ascending">Original
								Invoice No.</th></tr>
					</thead>
					<tbody>
						<c:forEach items="${CNDNList}" var="CNDNList" varStatus="status">
							<tr>
								<td><center>${status.index+1}</center></td>
								<td><center><a href="#"
									onclick="javascript:getCnDnDetails('${CNDNList[3]}','${CNDNList[0]}','${CNDNList[1]}','${CNDNList[2]}');">${CNDNList[2]}</a>
								</center></td>
								<td><center>${CNDNList[4]}</center></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>

			</div>


		</div>
	</div>
	<!-- </div> -->

<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardCNDN.js"/>"></script>
	<%-- <form name="manageInvoice" method="post">
		<input type="hidden" name="id" value=""> <input type="hidden"
			name="iterationNo" value=""> <input type="hidden" name="cId"
			value=""> <input type="hidden" id="_csrf_token"
			name="_csrf_token" value="${_csrf_token}" />
	</form> --%>
	<form name="cndn" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="iterationNo" value="">
    <input type="hidden" name="cId" value="">
    <input type="hidden" name="cndnNumber" value="">
    <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="cndn">
	<input type="hidden" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" name="onlyYear" value="${onlyYear}">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form>
<!-- back from cndnlist to dashboard -->
<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>


</section>

