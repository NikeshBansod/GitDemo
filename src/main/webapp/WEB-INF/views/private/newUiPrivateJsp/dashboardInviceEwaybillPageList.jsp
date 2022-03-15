<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
<%@include file="dashboardMonth.jsp" %>

<input type="hidden" id="dash_conditionValue" name="dash_conditionValue" value="${dash_conditionValue}">
<input type="hidden" id="dash_startdate" name="dash_startdate" value="${dash_startdate}">
<input type="hidden" id="dash_enddate" name="dash_enddate" value="${dash_enddate}">
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
<input type="hidden" name="refInvoiceId" id="refInvoiceId" value="">
<section class="block">
<div class="loader"></div>
	<div class="container">
	
	<!-- <div class="brd-wrap">
	    	<div  id="breadcumheader">
	       		 <a href="#" id="goBackToDashboard"><strong>Dashboard</strong></a> &raquo; <strong>Eway bill History</strong>
	        </div>
	        <div id="headerPreview">
	          <a  href="#" id="goBackToInvoiceEwaybill">E-way bill List</a> &raquo; <strong>Preview E-way bill</strong>
	          </div>
	    </div> -->
	    
	       <div class="page-title">
                    <a href="#" id="goBackToDashboard" class="back"><i class="fa fa-chevron-left"></i></a>Generated E-way Bills through Invoice for ${month}  ${onlyYear}
              </div>

		<%--  <h4 class="viewHeading">Generated E-way Bills through Invoice for ${month}  ${onlyYear} </h4> --%>
		 
         	<%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>	
 		 

	
		<div class="row" id="firstDivId">
		<div class="table-wrap">
			<table class="display nowrap" style="width:100%"id="invoiceHistoryTabInvoiceEway">
				<thead>
					<tr>
					
						
						<th>Eway bill No.</th>
						<th>Status.</th>
						
						</tr>
				</thead>
				<tbody>			
					<c:forEach items="${invoiceewaybillList}" var="invoiceewaybillList" varStatus="status">
						<tr>
						
						<c:set var="conditionValue" value="${invoiceewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'GENEWAYBILL'}">
                     <td > <a href="#" onclick="javascript:getEWayBillDetailsPreview('${invoiceewaybillList[0]}','${invoiceewaybillList[3]}');">${invoiceewaybillList[1]}</a> </td> 
                      <td >Active</td> 
                      </c:if>
                      
                      <c:set var="conditionValue" value="${invoiceewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'CANEWB'}">
                       <td > <a href="#" onclick="javascript:getEWayBillDetailsPreview('${invoiceewaybillList[0]}','${invoiceewaybillList[3]}');">${invoiceewaybillList[1]}</a> <!-- <span id="check" class="glyphicon glyphicon-remove-circle" style="float:right;color:red"></span> --></td>
                        <td >Inactive</span></td> 
                      </c:if>
						
    
                   
							<%-- <td>${status.index+1}</td>
							<td><a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a> </td>  --%>
							
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
			</div>
		</div>
		<input type="hidden" id="numberinvoice" value="${invoiceDetails.id}"/>     
        	 <input type="hidden" id="isInvoiceAllowed" value="${isInvoiceAllowed}"/>
		</br>
		</div>
		</section>
	<!-- </div> -->
	<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashboardInvoiceEwaybill.js"/>"></script>
	<form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>

<form name="redirectToBackFrominvoiceEwayBill" method="post">
	    <input type="hidden" name="startdate" value=""> 
	    <input  type="hidden" name="enddate" value="">
		<input type="hidden" name="getInvType" value=""> 
		<input type="hidden" name="onlyMonth" value="">
        <input type="hidden" name="onlyYear" value="">
        <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="ewaybillid" value="">
     <input type="hidden" name="startdate" value="${startdate}">
	<input type="hidden" name="enddate" value="${enddate}">
	<input type="hidden" name="conditionValue" value="invoiceewaybill">
	<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
	<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
	
