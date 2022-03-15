<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<input type="hidden" id="L0_gstin" name="L0_gstin" value="${gstin}">
<input type="hidden" id="L0_fp" name="L0_fp" value="${fp}">
<input type="hidden" id="GSTR_type" name="GSTR_type" value="${gstrtype}">
<input type="hidden" id="payload_mode" name="payload_mode" value="${payload_mode}">
<input type="hidden" id="monthofFP" name="monthofFP" value="${monthofFP}">
<input type="hidden" id="fpYear" name="fpYear" value="${fpYear}">
 <input type="hidden" id="_csrf_token" name="_csrf_token"value="${_csrf_token}" />
 
 <c:if test="${monthofFP  == '01'}"><c:set var="month" value="January"/></c:if>
  <c:if test="${monthofFP  == '02'}"><c:set var="month" value="February"/></c:if>
  <c:if test="${monthofFP  == '03'}"><c:set var="month" value="March"/></c:if>
  <c:if test="${monthofFP  == '04'}"><c:set var="month" value="April"/></c:if>
  <c:if test="${monthofFP  == '05'}"><c:set var="month" value="May"/></c:if>
  <c:if test="${monthofFP  == '06'}"><c:set var="month" value="June"/></c:if>
  <c:if test="${monthofFP  == '07'}"><c:set var="month" value="July"/></c:if>
  <c:if test="${monthofFP  == '08'}"><c:set var="month" value="August"/></c:if>
  <c:if test="${monthofFP  == '09'}"><c:set var="month" value="September"/></c:if>
  <c:if test="${monthofFP  == '10'}"><c:set var="month" value="October"/></c:if>
  <c:if test="${monthofFP  == '11'}"><c:set var="month" value="November"/></c:if>
  <c:if test="${monthofFP  == '12'}"><c:set var="month" value="December"/></c:if>
  
  <section class="block">
	<div class="container">	

 <!-- <a  href="#" id="goBackToDashboard" class="btn-back"><i class="fa fa-angle-left"></i></a> -->
 <!-- <div class="brd-wrap">
	    	<div  id="listheader">
	       		 <a href="#" id="goBackToAspHomepage"><strong>Compliance</strong></a> &raquo; <strong>GSTR1 FILE</strong>
	        </div>
	    </div> -->
	    <div class="page-title">
             <a href="#" id="goBackToAspHomepage" class="back"><i class="fa fa-chevron-left"></i></a>GSTR1 FILE
          </div>
	    <div class="loader"></div>
	    <%-- 	<div class="container" id='loadingmessage' style='display:none;' align="middle">
					  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div> --%>
<div id="defaultShowDiv" style='display:none;'>
     <div class="m-page-title" style=" background-color:white">

        <h5 style="padding:5px 5px; text-align: left;">${gstin}
        <%-- <span class="m-select-period"> ${month} ${fpYear}</span> --%>
        <span style=" float:right;">${month} ${fpYear}</span></h5>	
    </div>
<div class="row">
		<div class="col-sm">
				
					<div class="mbox draft-tables">
					<table class="table  table-bordered " id="L0Table" style="border:black;"></table>
						</div>
						 <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="fileToGSTN" formnovalidate="formnovalidate">File</button>
						</div>
				
		</div>
		</div>
</div>
<form name="goBackToAsp" method="post">
	    <input type="hidden" name="L0_gstin" value="">
	    <input type="hidden" name="L0_fp" value="">
	    <input type="hidden" name="GSTR_type" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	
	<form name="goBackToAspafterSuccessfulSaveToGSTN" method="post">
	    <input type="hidden" name="L0_gstin" value="">
	    <input type="hidden" name="L0_fp" value="">
	    <input type="hidden" name="GSTR_type" value="">
	    <input type="hidden" name="otp" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	
	<form name="gobacktoasphomeforotperror" method="post">
	    <input type="hidden" name="L0_gstin" value="">
	    <input type="hidden" name="L0_fp" value="">
	    <input type="hidden" name="GSTR_type" value="">
	    <input type="hidden" name="otp" value="">
	     <input type="hidden" name="message" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>



<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/compliancesPayload/viewFileToGstnPayload.js"/>"></script>	    
	    
	    
	    </div>
	    </section>

  