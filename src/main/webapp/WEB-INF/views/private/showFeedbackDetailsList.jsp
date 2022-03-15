<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/manageFeedbackDetails.js"/>"></script>

<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> 
<header class="insidehead">
      <a href="<spring:url value="home" />" class="btn-back"><i class="fa fa-angle-left"></i> </a>  <!-- <span>Manage GSTIN<span> -->
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<div class="container">
  <div class="box-border">
		<div class="card feedbackList" >
		       <h4><center>Feedback</center></h4>
		       <table class="  table table-striped table-bordered " id="feedbackDataTable">
		         <thead>
                     <tr>
                       <th><center>Description</center></th>
                        <th><center>Date</center></th>
                        
                      </tr>
                  </thead>
            <tbody class="dnynamicGstinDetails"></tbody> 
               </table>	
          </div> 
	</div>
  </div>						
	
<div class="fixed-action-btn">
	<a href="./addFeedbackDetails" class="btn btn-floating btn-large " title="Add Feedback" >
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> 



<!--common end-->
<form name="feedbackDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
      <input type="hidden" name="masterDescDetails" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  </form>
  

<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/uploadFiles.js"/>"></script>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/moment.min.js"/>"></script>

