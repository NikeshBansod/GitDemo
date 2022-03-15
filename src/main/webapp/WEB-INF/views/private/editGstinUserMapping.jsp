<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/gstinUserMapping/manageGstinUserMapping.js"/>"></script> 
 

<header class="insidehead">
      <a href="<spring:url value="/getGstinUserMap" />" class="btn-back"><i class="fa fa-angle-left" id="commonEditAccordionId"></i> <span>Edit GSTIN User Mapping<span></a>
 </header>

<div class="common-wrap">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head" style="display:none;">Edit GSTIN User Mapping</div>
       
          <div class="acc_content">
           <form:form commandName="gstinUserMapping" method="post" action="./updateGstinUserMapping">
            <!--content-->
            
            <div class="box-content">
              <div class="">
                <ul>
                  <li>
                  	<div class="form-group input-field ">
						<label class="label">  
							<form:hidden id="referenceUserId" path="referenceUserId" value="${gstinUserMappingObj.referenceUserId }"/>
						    <input type="text" disabled="true" id="selectGstinUser" class="form-control"> 
                   			 
							<div class="label-text label-text2">Employee</div>
						</label>
					</div>
				  </li>
				  <li>
				 	 <div class="form-group input-field ">
						<label class="label">  
						<input type="hidden" id="selectedGSTIN" value="${gstinUserMappingObj.gstinId }">
	                   		<form:select path="gstinId"  id="selectGstinNoDemo" multiple="true" class="form-control">
                   			</form:select>
							<div class="label-text label-text2">GSTIN's</div>
						</label>
					</div>
				  </li>
				  
			   <form:hidden path="id" value="${gstinUserMappingObj.id }"/>
			   <form:hidden path="referenceId" value="${gstinUserMappingObj.referenceId }"/>
			   <form:hidden path="status" value="${gstinUserMappingObj.status }"/>
			   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary">Update</button>
            	<button id="editCancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button>
            </div>
             </form:form>
            </div>
           
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap">
		
		<div class="dnynamicProducts" id="toggle">								
		</div>
		
   </div>
</div>