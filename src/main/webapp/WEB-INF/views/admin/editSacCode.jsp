<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="../private/common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/sacCode/sacCode.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/idt/sacCodeDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Edit SAC Code<span></a>
</header>

<div class="container">
   <div class="box-border">
    <div class="accordion_example2 no-css-transition">
     
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit Goods</div>
       
          <div class="acc_content">
           <form:form commandName="SACCode" method="post" action="./updateSacDetails">
            <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <div class="box-content">
              <div class="">
                <ul>
                  <li>
                  	  <div class="form-group input-field mandatory">
	                       <label class="label">
	                           <form:input path="sacCode" value="${SACDetailsObj.sacCode }" id="sacCode" maxlength="30" required="true" class="form-control"/>  
	                           <div class="label-text label-text2">SAC Code</div>
	                       </label>
	                       <span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
                      </div>
	                	
                  </li>
                  <li>
                  	   <div class="form-group input-field mandatory">
	                   	   <label class="label">
	                   	    	<textarea name="sacDescription" value="" id="sacDescription" required="true" class="form-control">${SACDetailsObj.sacDescription }</textarea>
		                         <div class="label-text label-text2">SAC Description</div>
	                       </label>
	                       <span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
	                   </div>
                  	
				  </li>
				  <li>
				  	   <div class="form-group input-field mandatory">
	                       <label class="label">
	                       		<form:select  path="igst" id="igst" required="true" class="form-control"/>
	                            <input type="hidden" value="${SACDetailsObj.igst }" id="igstHidden"/> 
	                           <div class="label-text label-text2">Rate of tax (%)</div>
	                       </label>
	                       <span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
	                   </div>
				  	
				  </li>
				  
			   	<form:hidden path="id" value="${SACDetailsObj.id }"/>
                <form:hidden path="stateId" value="0"/>
			
			   
                </ul>
              </div>
            </div>
           
           
            
            <div class="com-but-wrap">
            	<button type="submit" id="hsnSubmitBtn" class="btn btn-primary">Update</button>
            	<!-- <button id="editCancel" formnovalidate="formnovalidate" class="btn btn-primary">Cancel</button> -->
            	<a href="<spring:url value="/idt/sacCodeDetails" />" class="btn btn-secendory">Cancel</a>
            </div>
            </form:form>
            </div>
       
          </div>
        </div>
     
      </div> 
      
      <div class="cust-wrap edit-Page-List">
		
	<!-- 	<div class="dnynamicHsnCode" id="toggle">								
		</div> -->
      
     </div>
</div>

<script type="text/javascript" src="<spring:url value="/resources/js/sacCode/editSacCode.js"/>"></script>  
