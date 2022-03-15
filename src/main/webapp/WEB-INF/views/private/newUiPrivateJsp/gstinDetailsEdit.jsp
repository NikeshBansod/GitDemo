<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/gstinDetails/editGstinDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/gstinDetails/addGstinDetails.js"/>"></script>





<div class="loader"></div>
<section class="block">
	<div class="container">
	<form:form commandName="gstinDetails" method="post" action="./updateGstinDetails">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<%-- <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/addGstinDetails" />"><strong>GSTIN</strong></a> &raquo; <strong>Edit GSTIN</strong>
	        </div>
	 </div> --%>
	 <div class="page-title" id="listheader">
                 <a href="<spring:url value="/addGstinDetails" />" class="back"><i class="fa fa-chevron-left"></i></a>GSTIN
             </div>
	<div class="form-wrap">
	           <div class="row">
	           <div class="col-md-3">
	           <label for="label">GSTN Login Id (As on GSTN)<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	           <form:input path="gstnUserId" maxlength="50" id="gstnUserId"  value="${gstinDetailsObj.gstnUserId }" required="true" />
	           <span class="text-danger cust-error" id="reg-gstin-id-req">This field is required and should be in a proper format</span>
			   <span class="text-danger cust-error" id="reg-gstin-id-back-req"></span>
	           </div>
	           <div class="col-md-3">
	           <label for="label">GSTN Nickname<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	           <form:input path="gstnnickname" id="gstnnickname" maxlength="50" value="${gstinDetailsObj.gstnnickname}" required="true" class="form-control"/>
	           <!-- <span class="text-danger cust-error" id="gross-turnover">This field is required and should be numeric.</span> -->
	           </div>
	           <div class="col-md-3">
	           <label for="label">Gross Turnover<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	           <form:input path="grossTurnover" id="grossTurnover" maxlength="13" value="${gstinDetailsObj.grossTurnover }" required="true" class="form-control"/>
	           <span class="text-danger cust-error" id="gross-turnover">This field is required and should be numeric.</span>
	           </div>
	           <div class="col-md-3">
	           <label for="label">Current Turnover<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	           <form:input path="currentTurnover" id="currentTurnover"  maxlength="13" value="${gstinDetailsObj.currentTurnover }" required="true"/>
	           <span class="text-danger cust-error" id="current-turnover">This field is required and should be numeric.</span>
	           </div>
	           </div>
            <div class="row">
            <div class="col-md-4">
	            <label for="label">GSTIN (As on GSTN)<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	            <form:input path="gstinNo" id="reg-gstin" maxlength="15" value="${gstinDetailsObj.gstinNo }"/>
				<span class="text-danger cust-error" id="reg-gstin-req">This field is required and should be in a proper format</span>
				<span class="text-danger cust-error" id="reg-gstin-back-req"></span>			
            </div>
            <div class="col-md-4">
            <label for="label">GSTIN State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
            <form:select id="reg-gstin-state" path="state" itemValue="${gstinDetailsObj.state }" class="form-control" style="width: 365px;height: 42px;">
			</form:select> 
            </div>
            <div class="col-md-4">
             <label for="label">GSTIN User Name (As on GSTN)<span style="font-weight: bold;color: #ff0000;"> *</span></label>
             <form:input path="gstinUname" value="${gstinDetailsObj.gstinUname }" id="gstinUname" maxlength="200" required="true" class="form-control"/>
             <span class="text-danger cust-error" id="gstinUname-req"></span>
			 <span class="text-danger cust-error" id="gstinUname-back-req"></span>
            </div>
            </div>
             <div class="row">
            <div class="col-md-4">
	            <label for="label">Registered Address of GSTIN (max 500 characters)</label>
	            <form:input path="gstinAddressMapping.address" maxlength="500" id="address1"  value="${gstinDetailsObj.gstinAddressMapping.address }" class="form-control"/>
	             <span class="text-danger cust-error" id="address1-req">This field is required.</span>
            </div>
            
	            <div class="col-md-8" id="dynamicDiv">
		            <div class="row" style="padding:0px"> 
			            	<c:forEach items="${gstinDetailsObj.gstinLocationSet}" var="item" varStatus="loop">
				            <div class="col-md-6 gstinLocation" id="dynamiclocDiv${loop.index}">
					            <label for="label">Store/Location/Channel/Department : ${(loop.index) +1}<span style="font-weight: bold;color: #ff0000;"> *</span></label>
					            <form:input path="gstinLocationSet[${loop.index}].gstinLocation" type="text" id="gstinLocation" class="form-control" value="${item.gstinLocation}"/>
								<form:hidden path="gstinLocationSet[${loop.index}].id"  class="form-control" value="${item.id}"/>
								<form:hidden path="gstinLocationSet[${loop.index}].uniqueSequence"  class="form-control" value="${item.uniqueSequence}"/>
								<form:hidden path="gstinLocationSet[${loop.index}].refGstinId"  class="form-control" value="${item.refGstinId}"/>
								<span class="text-danger cust-error gstinLocationError" id="gstinLocation-empty_${loop.index}"></span>
				            </div>
				            </c:forEach>
				     </div>
				    
		       </div>
		      
		        <div class="row" id="adddynamicloc" style="display: none;">
				</div> 
	              <div class="col-md-3 button-wrap"> 
					<label for="label-text">&nbsp;</label>
					<button type="button" class="btn btn-success blue-but" value='Add New' id='addButton' style="width: auto;">Add New</button>
					<button type="button" class="btn btn-success blue-but" value='Remove' id='removeButtonEdit' style="width: auto;">Remove</button>
				  </div>  
			</div>
              <div class="row">
               <div class="col-md-4">
               <label for="label">Pin Code<span style="font-weight: bold;color: #ff0000;"> *</span></label>
              <form:input id="pinCode" path="gstinAddressMapping.pinCode" maxlength="6" required="true" value="${gstinDetailsObj.gstinAddressMapping.pinCode }" class="form-control"/>
               <span class="text-danger cust-error" id="zip-req">This field is required and should be 6 digits</span>
		        <span class="text-danger cust-error" id="empty-message"></span>
              </div>
              <div class="col-md-4">
              <label for="label">City <span style="font-weight: bold;color: #ff0000;"> *</span></label>
             <form:input id="city" readonly="true" path="gstinAddressMapping.city" required="false" value="${gstinDetailsObj.gstinAddressMapping.city }" class="form-control"/>
              </div>
              <div class="col-md-4" hidden="true" >
				<label for="label" >State <span style="font-weight: bold;color: #ff0000;"> *</span></label>
				<form:hidden id="state" readonly="true" path="gstinAddressMapping.state" value="${gstinDetailsObj.gstinAddressMapping.state }" required="true" class="form-control"/>
			      <span class="text-danger cust-error" id="state-req">This field is required</span>   
				</div>
              
              <div class="col-md-4">
              <label for="label">Country<span style="font-weight: bold;color: #ff0000;"> *</span></label>
              <form:input id="country" readonly="true" path="gstinAddressMapping.country" value="${gstinDetailsObj.gstinAddressMapping.country }" required="true" class="form-control"/>
              </div>
              
              </div>
              </div>
               <form:hidden path="id" value="${gstinDetailsObj.id }"/>
			   <form:hidden path="createdBy" value="${gstinDetailsObj.createdBy }"/>
			   <form:hidden path="createdOn" value="${gstinDetailsObj.createdOn }"/>
			   <form:hidden path="referenceId" value="${gstinDetailsObj.referenceId }"/>
			   <form:hidden path="status" value="${gstinDetailsObj.status }"/>
			   <form:hidden path="uniqueSequence" value="${gstinDetailsObj.uniqueSequence }"/>
			   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			   <form:hidden path="gstinAddressMapping.id" value="${gstinDetailsObj.gstinAddressMapping.id }"/>
			   
			   <div class="row">
	         <div class="col-md-12 button-wrap">
	         <button type="button" class="btn btn-success blue-but" id="deleteGstin" onclick="javascript:deleteRecord('${gstinDetailsObj.id}');"  style="width: auto;">Delete</button>
             <button type="submit" class="btn btn-success blue-but" id="submitGstin" style="width: auto;">Update</button>
            </div>
             </div>
	</form:form>
	<input type="hidden" id="counterButtonValue" >
	<input type="hidden" id="dynamicLocCount">
	</div>
</section>

<form name="manageGstinDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/gstinDetails/pincode-autocomplete-gstn.js"/>"></script>


