<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/UserGstinMapping/editUserGstinMapping.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/UserGstinMapping/manageUserGstinMapping.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/getUserGstinMap" />" class="btn-back"><i class="fa fa-angle-left" id="commonEditAccordionId"></i> </a>  <!-- <span>Edit GSTIN User Mapping<span> -->
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<div class="common-wrap">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head" style="display:none;">Edit GSTIN User Mapping</div>
       
          <div class="acc_content">
           <form:form commandName="userGstinMapping" method="post" action="./updateUserGstinMapping">
            <!--content-->
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            
            <div class="box-content">
            <span><center><h4><b>Edit GSTIN User Mapping</b></h4></center></span>
              <div class="">
                <ul>
                <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
                   			<form:hidden id="gstinId" path="gstinId" value="${userGstinMappingObj.gstinId }"/>
						    <input type="text" disabled="true" id="selectGstinId" class="form-control">
							<div class="label-text label-text2">GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
				  <li>
				 	 <div class="form-group input-field mandatory">
				 	 <label class="label">  
						<input type="hidden" id="selectedGSTINAddr" value="${userGstinMappingObj.gstinAddressMapping.id }">
							<form:select path="gstinAddressMapping.id" id="gstinAddressMapping" class="form-control">
                   			</form:select>
							<div class="label-text label-text2">Store/Location/Channel/Department</div>
						</label>
						<span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
                		<input type="hidden" id="selectedGSTIN" value="${userGstinMappingObj.gstinUserIds }">
						  <form:select path="gstinUserIds" id="gstinUserSet" multiple="true" class="form-control">
                   			</form:select>  
                   			
							<div class="label-text label-text2">Choose Employee</div>
						</label>
						<span class="text-danger cust-error" id="selectGSTINDetail-req"></span>
					</div>
				  </li>
			   <form:hidden path="id" value="${userGstinMappingObj.id }"/>
			   <form:hidden path="referenceUserId" value="${userGstinMappingObj.referenceUserId }"/>
			   <form:hidden path="status" value="${userGstinMappingObj.status }"/>
			   <form:hidden path="createdBy" value="${userGstinMappingObj.createdBy }"/>
			   <form:hidden path="createdOn" value="${userGstinMappingObj.createdOn }"/>
			   <form:hidden path="status" value="${userGstinMappingObj.status }"/>
			   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			    <input type="hidden" id="editPage" value="true" />
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" id="submitGstinMapping" class="btn btn-primary">Update</button>
            	<a href="#" onclick="javascript:deleteRecord('${userGstinMappingObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
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