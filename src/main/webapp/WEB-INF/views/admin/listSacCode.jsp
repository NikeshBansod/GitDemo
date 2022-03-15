<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %>
<%@include file="../private/common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/sacCode/sacCode.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/idt/idthome" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Add SAC Code<span></a>
 </header>
 
   <main>
      <section class="block searchBox">
           <div class="container">
               
               <div class="card">
                   <div class="form-group input-field search-wrap">
                       <label class="label">
                           <input class="search-input form-control" id="search-sac" type="text" required />  
                           <div class="label-text search">Search By SAC Code / SAC Description</div>
                       </label>
                   </div>
               </div>

           </div>
       </section>

       <section class="block searchBox">
           <div class="container">
			 <form:form commandName="SACCode" method="post" id="prdForm" action="./addSACDetails"><!--  -->
			 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
               <div class="card addCustomer">
               
                   <div class="form-group input-field mandatory">
                       <label class="label">
                           <form:input path="sacCode" id="sacCode" maxlength="30" required="true" class="form-control"/>  
                           <div class="label-text label-text2">SAC Code</div>
                       </label>
                       <span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
                   </div>
                
                   <div class="form-group input-field mandatory">
                   	   <label class="label">
                   	    	<form:textarea path="sacDescription" id="sacDescription" required="true" class="form-control"/> 
	                         <div class="label-text label-text2">SAC Description</div>
                       </label>
                       <span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
                   </div>
                   
                   <div class="form-group input-field mandatory">
                       <label class="label">
                           <form:select path="igst" class="form-control" id="igst"/>
                           <div class="label-text label-text2">Rate of tax (%)</div>
                       </label>
                       <span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
                   </div>
                   
                    
                    <form:hidden path="stateId" value="0" required="true"/>

                   <div class="form-group input-field text-center">
                       <button type="submit" class="btn btn-primary" id="sacSubmitBtn">Save</button>
                       <!-- <button id="cancel" formnovalidate="formnovalidate" class="btn btn-primary">Cancel</button> -->
                       <a href="sacCodeDetails" class="btn btn-secendory">Cancel</a>
                   </div>

               </div>
			 </form:form>
           </div>
       </section>

    </main>

<div class="common-wrap container">
   <div class="box-border">
      
      <div class="cust-wrap">
		<!-- <div class="dnynamicSACCode" id="toggle">							
		</div> -->
		<div class="currentServiceSAC" id="toggleServiceSAC">							
		</div>
      </div>
     
  </div>
</div>

<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add SAC Code" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<form name="manageSacCode" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/sacCode/searchSAC.js"/>"></script>