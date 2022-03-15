<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="../private/common-alert.jsp" %>


<header class="insidehead">
      <a href="<spring:url value="/idt/idthome" />" class="btn-back"><i class="fa fa-angle-left"></i> <span> Dashboard<span></a>
 </header>



<div class="container" >
  <input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
  <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
  <input type="hidden" id="traverseFrom" name="traverseFrom" value="${traverseFrom}">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />


  <div class="box-border">
       <div class="accordion_example2 no-css-transition">
             <div class="accordion_in acc_active">
                   <div class="acc_content">
                           <div class="col-md-6 col-sm-6 col-xs-12">
                                            <label >Month:</label>
                                         <select class="form-control"   required="required" id="startmonth"></select>
                          </div>
                                    
                               <div class="col-md-6 col-sm-6 col-xs-12">
                                            <label>Year:</label>
                                         <select class="form-control"  required="required" id="years"></select>
                                       </div>
                                       
                          <div class="col-md-12 col-sm-12 col-xs-12">
                          </br>
                          <div class="com-but-wrap"><button id="calculate" type="button" class="btn btn-primary" >Get Reports</button></div>
                          </div>
                       
                         
                                </div>
                     </div>
                               <div id="radioButtons">             
                         <!-- <input type="radio"   id="chart" name="selection" value="Piechart" checked>Pie Chart -->
                         <input type="radio" id="table" name="selection" value="Table"   >Table
                  <!-- <input type="radio"    id="column" name="selection" value="Barchart" >Bar chart -->
                    </div> 
                    <br>
                    
                        <!--  <div id="piechart"  style="height: 100%; width:100%;"></div> -->
                          
                      <div> <table class="table table-striped table-bordered" id="dashboardreport"></table></div>
                      
                                 <!--  <div id="columnchart"  style="height: 100%; width:100%;"></div> -->
       </div>
   </div>
</div>
<br/>

<!--common end-->
  <form name="invoiceCount" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  </form>
  <script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/adminDashboardDate.js"/>"></script> 
