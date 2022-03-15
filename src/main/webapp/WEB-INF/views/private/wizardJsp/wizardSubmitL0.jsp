<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<input type="hidden" id="fp" name="fp" value="${financialPeriod}" />
				<input type="hidden" id="gstin" name="gstin" value="${gstin}" />
				<input type="hidden" id="GSTR_type" name="GSTR_type" value="${gstrtype}">
				<input type="hidden" id="return_gstrtype" name="return_gstrtype" value="${return_gstrtype}" />
				<input type="hidden" id="return_gstinId" name="return_gstinId" value="${return_gstinId}" />
				<input type="hidden" id="return_financialperiod" name="return_financialperiod" value="${return_financialperiod}" />
				<input type="hidden" id="goback" name="goback" value="${goback}" />
				<input type="hidden" id="payload_mode" name="payload_mode" value="${payload_mode}">
				



<input type="hidden" id="payload_mode" name="payload_mode" value="${payload_mode}">
<input type="hidden" id="monthofFP" name="monthofFP" value="${monthofFP}">
<input type="hidden" id="fpYear" name="fpYear" value="${fpYear}">

	<section class="insidepages">	
				<div class="breadcrumbs" id="listHeader">
					<div class="col-md-12" id="listheader1">
			         	<a href="#" id="gobacktoasp">ASP Home</a> <span>&raquo;</span> GSTN SUBMIT
			 		</div>
			 		
				</div>
				
				<br>
				<div class="container" id='loadingmessage' style='display:none;' align="middle">
					  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
	</div>
<div id="defaultShowDiv" style='display:none;'>
				<div class="account-det" >

        <h5 style="padding:5px 5px; text-align: left;"><strong>${gstin}</strong></br>
        <span class="account-det"><a  href="#"><i class="m-edit-icon" aria-hidden="true" title="Edit"></i><strong> ${financialPeriod}</strong></a></span>
        <span style=" float:right;"><strong>Submit GSTR1</strong></span></h5>
       
    </div>
				 <div class="container" >
				
				<%-- <form:form  method="post" id="uploadAsp" action="./wgetgstr1l0response" > --%>
				
				
				
				<div class="account-det">
					  	        	
								
								
							
							 
							 <div class="col-md-2 col-sm-4 col-xs-12">
							 
							 </div> 
							  <div class="col-md-8 col-sm-8 col-xs-12">
       
            <div class="mbox draft-tables">
	<table class="table  table-bordered " id="L0Table" style="border:black;">
	
</table>
<div class="insidebtn">
                      <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="saveToGSTN" formnovalidate="formnovalidate">Submit</button>
                     
                     </div>
                     </div>
</div>
                     
                     </div>
                     <!-- <div class="col-md-2 col-sm-2 col-xs-2">
							 
							 </div>
                     <div class="insidebtn">
                      <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="saveToGSTN" formnovalidate="formnovalidate">Submit</button>
                     
                     </div>
                     </div> -->
                     </div>
                     </div>
                    </div>
                     
                     
                     
 <form name="gobacktoasp" method="post">
    <input type="hidden" id="return_gstrtype" name="return_gstrtype" value="${return_gstrtype}" />
				<input type="hidden" id="return_gstinId" name="return_gstinId" value="${return_gstinId}" />
				<input type="hidden" id="return_financialperiod" name="return_financialperiod" value="${return_financialperiod}" />
				
				<input type="hidden" id="goback" name="goback" value="${goback}" />
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>
<form name="goBackToAspafterSuccessfulSaveToGSTN" method="post">
	   <input type="hidden" name="gstin" value="${return_gstrtype}">
	    <input type="hidden" name="fp" value="${return_financialperiod}">
	    <input type="hidden" name="GSTR_type" value="${return_gstrtype}">
	    <input type="hidden" name="otp" value="">
	    <input type="hidden" id="goback" name="goback" value="${goback}" />
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
	<%-- <form name="submitToGstn" method="post">
	    <input type="hidden" name="gstin" value="${gstin}">
	    <input type="hidden" name="fp" value="${financialPeriod}">
	    <input type="hidden" name="GSTR_type" value="${return_gstrtype}">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form> --%>
	<form name="gobacktoasphomeforotperror" method="post">
	  <input type="hidden" name="gstin" value="${return_gstrtype}">
	    <input type="hidden" name="fp" value="${return_financialperiod}">
	    <input type="hidden" name="GSTR_type" value="${return_gstrtype}">
	    <input type="hidden" name="otp" value="">
	     <input type="hidden" name="message" value="">
		<input type="hidden" id="goback" name="goback" value="${goback}" />
	     <input type="hidden" name="message" value="">
	    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	</form>
</section> 
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/uploadToAsp/wizardSubmitL0.js"/>"></script>