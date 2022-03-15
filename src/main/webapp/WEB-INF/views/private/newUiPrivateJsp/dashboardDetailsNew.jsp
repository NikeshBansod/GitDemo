<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
           
<input type="hidden" id="onlyMonth" name="onlyMonth" value="${onlyMonth}">
  <input type="hidden" id="onlyYear" name="onlyYear" value="${onlyYear}">
  <input type="hidden" id="traverseFrom" name="traverseFrom" value="${traverseFrom}">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />


<section class="block">
  <div class="loader"></div>
            <div class="container">
               <%--  <div class="brd-wrap" id="breadcumheader">
			    	 <a href="<spring:url value="/home"/>"><strong>Home</strong></a> &raquo; <strong>Dashboard</strong>
				</div> --%>
				  <div class="page-title">
                        <a href="<spring:url value="/home"/>" class="back"><i class="fa fa-chevron-left"></i></a>Dashboard
                    </div>
                <div class="form-wrap footernote">
                    <div class="row">
                        <div class="col-md-6">
                            <label for="">Month<span>  *</span></label>
                            <select required="required" id="startmonth"></select>                          
                        </div>
                        <div class="col-md-6">
                            <label for="">Year<span>  *</span></label>
                             <select  required="required" id="years"></select>                         
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                          </br>
                          <div class="col-md-12 button-wrap"><button id="calculate" type="button" class="btn btn-success blue-but" style="width: auto;">Get Reports</button></div>
                          </div>
                    </div>
                    <!--  <div id="radioButtons">             
                         <input type="radio"   id="chart" name="selection" value="Piechart" checked>Pie Chart
                         <input type="radio"    id="table" name="selection" value="Table"   >Table
                         <input type="radio"    id="column" name="selection" value="Barchart" >Bar chart
                    </div>  -->
                </div>
			
	                    <div class="row ">
	                        <div class="col-md-6">
	                            <div id="piechart"  style="height:100%; width:100%;"></div>
	                        </div>
	                        <br>
	                        <div class="col-md-6">
	                            	<table id="dashboardreport" class="display nowrap table table-striped table-bordered" style="width:100%" >
	                                </table>
	                        </div>
	                    </div>
					<div class="form-wrap footernote" >
	                    <div class="row">
	                        <div class="col-md-12">
	                            <div id="columnchart" style="height:100%; width: 100%;"></div>
	                        </div>
	                    </div>
	                </div>
				
           </div> 
        </section>
        <form name="invoiceCount" method="post">
    <input type="hidden" name="onlyMonth" value="">
    <input type="hidden" name="onlyYear" value="">
    <input type="hidden" name="startdate" value="">
    <input type="hidden" name="enddate" value="">
    <input type="hidden" name="getInvType" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  </form>
        <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/DashBoard/dashBoardDate.js"/>"></script> 
        <script type="text/javascript" src="<spring:url value="/resources/js/charts-loader.js"/>"></script>
    