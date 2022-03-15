<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>




 <div class="loader"></div>
<section class="block">
  <div class="container">
    <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/getUserGstinMap"/>"><strong>GSTIN User Mapping </strong></a> &raquo; <strong>Edit GSTIN User</strong>
	        </div>
    </div>
         <div class="form-wrap">
           <form:form commandName="userGstinMapping" method="post" action="./updateUserGstinMapping">
            <!--content-->
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="row">
			<div class="col-md-4">
			<label for="label">GSTIN<span style="font-weight: bold;color: #ff0000;"> *</span></label>
			<form:hidden id="gstinId" path="gstinId" value="${userGstinMappingObj.gstinId }"/>
			<input type="text" disabled="true" id="selectGstinId">
			<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
			</div>
			<div class="col-md-4">
			<label for="label">Store/Location/Channel/Department<span style="font-weight: bold;color: #ff0000;"> *</span></label>
			<input type="hidden" id="selectedGSTINAddr" value="${userGstinMappingObj.gstinAddressMapping.id }">
			<form:select path="gstinAddressMapping.id" id="gstinAddressMapping"> </form:select>
             <span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
			</div>
			<div class="col-md-4">
			<label for="label">Choose Employee<span style="font-weight: bold;color: #ff0000;"> *</span></label>
			<input type="hidden" id="selectedGSTIN" value="${userGstinMappingObj.gstinUserIds }">
						  <form:select path="gstinUserIds" id="gstinUserSet" multiple="true">
                   			</form:select>
            <span class="text-danger cust-error" id="selectGSTINDetail-req"></span>
			</div>
			<form:hidden path="id" value="${userGstinMappingObj.id }"/>
			   <form:hidden path="referenceUserId" value="${userGstinMappingObj.referenceUserId }"/>
			   <form:hidden path="status" value="${userGstinMappingObj.status }"/>
			   <form:hidden path="createdBy" value="${userGstinMappingObj.createdBy }"/>
			   <form:hidden path="createdOn" value="${userGstinMappingObj.createdOn }"/>
			   <form:hidden path="status" value="${userGstinMappingObj.status }"/>
			   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			    <input type="hidden" id="editPage" value="true" />
		</div>
		<div class="row">	
		      		<div class="col-md-12 button-wrap">
		      		<button type="submit" class="btn btn-success blue-but" id="submitGstinMapping" formnovalidate="formnovalidate" style="width: auto;">Update</button>
                      </div>
         </div>
           </form:form>
         </div>
   	</div>
   	 <!--customer details-->
   	 <div class="cust-wrap">
		<div class="dnynamicProducts" id="toggle">								
		</div>
   </div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/UserGstinMapping/editUserGstinMapping.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/UserGstinMapping/manageUserGstinMapping.js"/>"></script>
