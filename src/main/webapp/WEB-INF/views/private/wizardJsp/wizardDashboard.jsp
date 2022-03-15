<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript"> </script>





       <section class="insidepages">    
              <div class="breadcrumbs" id="listHeader">
                     <div class="col-md-12" id="listheader1">
                    <a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> Dashboard
                    </div>
                    
              </div>
              
              <br>
              <div class="container" >

  <input type="hidden" id="onlyMonthforback" name="onlyMonth" value="${onlyMonth}">
  <input type="hidden" id="onlyYearforback" name="onlyYear" value="${onlyYear}">
  <input type="hidden" id="traverseFrom" name="traverseFrom" value="${traverseFrom}">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
              <div id="addEmployeeDetails">
              
                     <%-- <form:form commandName="feedbackDetails" method="post" id="feedbackFormId" action="./wAddFeedbackDetails"> --%>
                           <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
                           <br>
                           
                           
                           
                           
                           
                                  
                                  
                           
                     
                     
                     <div class="account-det">
                     
                                  <div class="det-row">     
                                  
                                         <div class="det-row-col-half astrick">
                                         
                                         <div class="label-text">Month</div>
                                              <span class="text-danger cust-error" id="master-desc">This field is required</span>                
                                           
                                            <span class="text-danger cust-error" id="master-desc">This field is required</span>
                                         
                                                       <select class="form-control" required="required" id="startmonth">
                                   
                            </select>  
                                         </div>
                                         <div class="det-row-col-half astrick">
                                                <div class="label-text">Year</div>
                                           
                                                <span class="text-danger cust-error" id="feedback-query-req">This field is required.</span>
                                                <select class="form-control" required="required" id="years">
                                                
                                                  
                                                <%--
                                                 <option value="2016">2016</option>
                                 <option value="2017">2017</option>
                                <option value="2018">2018</option>   --%> 
                                </select>
                                                
                                                
                                         </div> 
                                  </div>
                                  
                           </div>
                     
                           </div>
                           <div class="insidebtn">                
                                  <div class="com-but-wrap"><button id="calculate" type="submit"   class="sim-button button5" id="feedbackSubmit" formnovalidate="formnovalidate">Get Data</button> </div>
                                                
                                                <!-- <a  class="btn btn-secendory" href="<spring:url value="/home" />" > Cancel</a> -->
                           </div>
                     
              <br>
              <br>
              
       <div class="col-md-6 col-sm-4 col-xs-12">
       
       <div id="piechart_3d" style=" height: 220px;"  ></div>
       
       </div>
       

  
              
       <div class="col-md-6 col-sm-4 col-xs-12">
       
            <table class="table table-striped table-bordered" id="countTab">
                           
                           <tbody id="dashboardreport">                   
                                  
                                  
                                  
                           </tbody>
                     </table>
                     
                     </div>
                     
              
                     
                     </div>
                     </section> 

<br/>
<br>
<br>
<!--common end-->
<script type="text/javascript" src="<spring:url value="/resources/js/DashBoard/wizardDashboardDate.js"/>"></script> 

<form name="invoiceCount" method="post">
   <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
   
</form>



        
