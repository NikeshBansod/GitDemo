<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/dashBoardDate.js"/>"></script>
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
<input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">

       <%@include file="dashboardMonth.jsp" %> 
<section class="insidepages">
<div class="breadcrumbs"><div class="col-md-12" id="listheader1">

                     <a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> <a
                           href="#" id="goBackToDashboard">Dashboard</a><span>&raquo;</span> Invoice E-way Bill List
              </div>
              <div class="col-md-3"></div>
              <div class="col-md-6">
                     <header class="insidehead" id="generateInvoiceDefaultPageHeader">
                           <h3>
                                  
               <h4><b> Generated E-waybill Through Invoice for  ${month}  ${onlyYear}</b> </h4>
                           </h3>

                           <%-- <a href="<spring:url value="/dashboard"/>">Dashboard</a> <span>&raquo;</span> --%>
                     </header>
              </div>
       </div>

       
       <div class="cust-wrap">          
                                  <div  id="toggle"><br>
                           <div class="container">
                     <table class="table table-striped table-bordered" id="invoiceHistoryTab">
                           <thead>
                                  <tr>
                                  
                                         <th><center>Sr.No.</center></th>
                                         <th><center>E-way bill No.</center></th>
                                         <th><center>Status</center></th>
                                         </tr>
                           </thead>
                           <tbody>                    
                                  <c:forEach items="${invoiceewaybillList}" var="invoiceewaybillList" varStatus="status">
                                         <tr>
                                         <td><center>${status.index+1}</center></td>
                                         <c:set var="conditionValue" value="${invoiceewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'GENEWAYBILL'}">
                     <td><center> <a href="#" onclick="javascript:getWizardDashboardPreviewOfInvoiceEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a></center> </td> 
                     <td> <center><span style="color:green;font-weight:bold; ">Valid</span></center></td>
                     <!-- <td></td> -->
                      </c:if>
                      
                      <c:set var="conditionValue" value="${invoiceewaybillList[2]}"/>
                       <c:if test="${conditionValue == 'CANEWB'}">
                       <td> <center><a href="#" onclick="javascript:getWizardDashboardPreviewOfInvoiceEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a></center> </td>
                       <td><center><span style="color:red;font-weight:bold;" >Cancelled</span></center></td>
                       <!-- <td><span id="check" class="glyphicon glyphicon-remove-circle" style="float:right;"></span></td> -->
                      </c:if>
                                         
    
                   
                                                <%-- <td>${status.index+1}</td>
                                                <td><a href="#" onclick="javascript:getPreviewOfEwayBillDetails('${invoiceewaybillList[1]}','${invoiceewaybillList[0]}');">${invoiceewaybillList[1]}</a> </td>  --%>
                                                
                                         </tr>
                                  </c:forEach>
                                  
                                  
                           </tbody>
                     </table>
              </div>
       
       
              </div>
              </div>
       <!-- </div> -->
       
       <%-- <form name="invoice" method="post">
         <input type="hidden" name="id" value="">
          <input type="hidden" name="conditionValue" value="invoice">
          <input type="hidden" name="startdate" value="${startdate}">
          <input type="hidden" name="enddate" value="${enddate}">
      <input type="hidden" name="_csrf_token" value="${_csrf_token}">
     
       </form> --%>
       <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardInvoiceEwaybill.js"/>"></script>
       <form name="invoiceEwaybill" method="post">
       <input type="hidden" name="ewaybillNo" value="">
         <input type="hidden" name="invoiceId" value="">
         <input type="hidden" name="startdate" value="${startdate}">
       <input type="hidden" name="enddate" value="${enddate}">
       <input type="hidden" name="conditionValue" value="invoiceewaybill">
       <input type="hidden" name="onlyMonth" value="${onlyMonth}">
       <input type="hidden" name="onlyYear" value="${onlyYear}">
      <input type="hidden" name="_csrf_token" value="${_csrf_token}">
     
       </form> 
       <form name="gotoDashboard" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />

</form>
       </section>
       

