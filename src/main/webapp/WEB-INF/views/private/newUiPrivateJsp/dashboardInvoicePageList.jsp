<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



<%-- <link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script> --%>

<%@include file="dashboardMonth.jsp" %>
<section class="block">
<div class="loader"></div>
	<div class="container">	

 <!-- <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a> -->
 <!--<div class="brd-wrap">
	    	 <div  id="listheader">
	       		 <a href="#" id="goBackToDashboard"><strong>Dashboard</strong></a> &raquo; <strong>Document History</strong>
	        </div>
	    </div> -->
 
              <div class="page-title">
                    <a href="#" id="goBackToDashboard" class="back"><i class="fa fa-chevron-left"></i></a>Generated documents for  ${month}  ${onlyYear}
              </div>

	<%-- 	 <h4 class="viewHeading">Generated documents for  ${month}  ${onlyYear} </h4> --%>
	
	
		<div class="row">
		 <div class="table-wrap">
		
			<table  class="display nowrap" style="width:100%" id="invoiceHistoryTabInvoice">
				<thead>
					<tr>
						<th >Invoice new No.</th>
						<th >Created Against</th>
						<th >From</th>
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${invoiceList}" var="invoiceList" varStatus="status">
						<tr>
							
							<td><a href="#" onclick="javascript:viewRecord(${invoiceList[0]});">${invoiceList[1]}</a> </td> 
							<td>${invoiceList[4]}</td>
							<td>${invoiceList[5]}</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			</div>
			
		</div>
		</div>
		</section>
		
	<!-- </div> -->
	<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardInvoice.js"/>"></script>
	
	<form name="invoice" method="post">
	  <input type="hidden" name="id" value="">
	   <input type="hidden" name="conditionValue" value="invoiced">
	   <input type="hidden" name="startdate" value="${startdate}">
	   <input type="hidden" name="enddate" value="${enddate}">
	   <input type="hidden" id="onlyMonth"  name="onlyMonth" value="${onlyMonth}">
	   <input type="hidden"id="onlyYear" name="onlyYear"  value="${onlyYear}">
       <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	
	<form name="gotoDashboard" method="post">
	    <input type="hidden" name="onlyMonth" value="">
	    <input type="hidden" name="onlyYear" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	