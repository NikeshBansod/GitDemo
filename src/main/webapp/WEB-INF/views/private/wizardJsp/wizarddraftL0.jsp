<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<input type="hidden" id="financialPeriod" name="financialPeriod" value="${financialPeriod}" />
				<input type="hidden" id="gstinId" name="gstinId" value="${gstinId}" />
				<input type="hidden" id="month" name="month" value="${month}" />
				<input type="hidden" id="fpYear" name="fpYear"" value="${fpYear}" />
				
				<input type="hidden" id="return_gstrtype" name="return_gstrtype" value="${return_gstrtype}" />
				<input type="hidden" id="return_gstinId" name="return_gstinId" value="${return_gstinId}" />
				<input type="hidden" id="return_financialperiod" name="return_financialperiod" value="${return_financialperiod}" />
				
				<input type="hidden" id="goback" name="goback" value="${goback}" />


	<section class="insidepages">	
				<div class="breadcrumbs" id="listHeader">
					<div class="col-md-12" id="listheader1">
			         	<a href="#" id="gobacktoasp">ASP Home</a> <span>&raquo;</span> ASP Draft LO
			 		</div>
			 		
				</div>
				
				<br>
				<div class="account-det" >

        <h5 style="padding:5px 5px; text-align: left;"><strong>${gstinId}</strong></br>
        <span class="account-det"><a  href="#"><i class="m-edit-icon" aria-hidden="true" title="Edit"></i><strong> ${financialPeriod}</strong></a></span>
        <span style=" float:right;"><strong>Draft GSTR1</strong></span></h5>
       
    </div>
				 <div class="container" >
				
				<%-- <form:form  method="post" id="uploadAsp" action="./wgetgstr1l0response" > --%>
				
				
				
				<div class="account-det">
					  	        	
								
								
							
							 
							 <div class="col-md-2 col-sm-4 col-xs-12">
							 
							 </div> 
							  <div class="col-md-8 col-sm-8 col-xs-12">
       
            <table class="table table-success" id="countTab">
                           
                           <tbody id="l0draft">                   
                                  
                                  
                                  
                           </tbody>
                     </table>
                     <div class="insidebtn">
                     <div class="com-but-wrap"><button   type="submit" class="sim-button button5" id="proceed" formnovalidate="formnovalidate">Save</button></div></div>
                     </div>
                     
                     
                     </div>
                     </div>
                     
                    
                     <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/draftLoData/wizardDraftLo.js"/>"></script>
                     
                     
 <form name="gobacktoasp" method="post">
    <input type="hidden" id="return_gstrtype" name="return_gstrtype" value="${return_gstrtype}" />
				<input type="hidden" id="return_gstinId" name="return_gstinId" value="${return_gstinId}" />
				<input type="hidden" id="return_financialperiod" name="return_financialperiod" value="${return_financialperiod}" />
				
				<input type="hidden" id="goback" name="goback" value="${goback}" />
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 
</form>
 </section>