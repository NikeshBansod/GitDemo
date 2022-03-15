<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>



<script type="text/javascript" src="<spring:url value="/resources/js/gstinDetails/editGstinDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/gstinDetails/addGstinDetails.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/addGstinDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> </a>  <!-- <span>Edit GSTIN Details<span> -->
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head" style="display:none;" id="commonEditAccordionId" >Edit GSTIN Details</div> 
       
          <div class="acc_content">
           <form:form commandName="gstinDetails" method="post" action="./updateGstinDetails">
            <!--content-->
            <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <div class="box-content">
            <span><center><h4><b>Edit GSTIN Details</b></h4></center></span>
            
              <div class="">
                <ul>
                 <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="gstnUserId" maxlength="50" id="gstnUserId"  value="${gstinDetailsObj.gstnUserId }" required="true" class="form-control"/>
							<div class="label-text label-text2">GSTN Login Id (As on GSTN)</div>
						</label>
						<span class="text-danger cust-error" id="reg-gstin-id-req">This field is required and should be in a proper format</span>
						<span class="text-danger cust-error" id="reg-gstin-id-back-req"></span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="grossTurnover" id="grossTurnover" maxlength="13" value="${gstinDetailsObj.grossTurnover }" required="true" class="form-control"/>
							<div class="label-text label-text2">Gross Turnover</div>
						</label>
						<span class="text-danger cust-error" id="gross-turnover">This field is required and should be numeric.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="currentTurnover" id="currentTurnover"  maxlength="13" value="${gstinDetailsObj.currentTurnover }" required="true" class="form-control"/>
							<div class="label-text label-text2">Current Turnover</div>
						</label>
						<span class="text-danger cust-error" id="current-turnover">This field is required and should be numeric.</span>
					</div>
				  </li>	
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="gstinNo" id="reg-gstin" maxlength="15" value="${gstinDetailsObj.gstinNo }" class="form-control"/>
							<div class="label-text label-text2">GSTIN </div><!--City  -->
						</label>
						<span class="text-danger cust-error" id="reg-gstin-req"></span>
						<span class="text-danger cust-error" id="reg-gstin-back-req"></span>
					</div>
				  </li> 
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:select id="reg-gstin-state" path="state" itemValue="${gstinDetailsObj.state }" class="form-control">
						</form:select> 
						<div class="label-text label-text2">State</div><!-- State -->
						</label>
					</div>
				  </li>
				  <li>
                 	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="gstinUname" value="${gstinDetailsObj.gstinUname }" id="gstinUname" maxlength="200" required="true" class="form-control"/>
							<div class="label-text label-text2">GSTIN User Name (As on GSTN)</div>
						</label>
						<span class="text-danger cust-error" id="gstinUname-req"></span>
						<span class="text-danger cust-error" id="gstinUname-back-req"></span>
					</div>
				  </li>
				  <li>
				  	 <div class="form-group input-field">
		                  <label class="label">
		                  		<form:input path="gstinAddressMapping.address" maxlength="500" id="address1"  value="${gstinDetailsObj.gstinAddressMapping.address }" class="form-control"/>
		                  		<div class="label-text label-text2">Registered Address of GSTIN (max 500 characters)</div>
		                  </label>
		                  <span class="text-danger cust-error" id="address1-req">This field is required.</span>
		              </div>
		     	 </li>
		     	 <c:forEach items="${gstinDetailsObj.gstinLocationSet}" var="item" varStatus="loop">
		     	 <li>
		              <div class="form-group input-field ">
		               	<label class="label">
					        <div class="control-group ">
					            <%-- <label class="control-label" for="gstinLocation">
					                GSTIN Location ${loop.index}</label> --%>
					            <div class="controls ">
					                <form:input path="gstinLocationSet[${loop.index}].gstinLocation" type="text" id="gstinLocation" class="form-control" value="${item.gstinLocation}"/>
					                <form:hidden path="gstinLocationSet[${loop.index}].id"  class="form-control" value="${item.id}"/>
					                 <form:hidden path="gstinLocationSet[${loop.index}].uniqueSequence"  class="form-control" value="${item.uniqueSequence}"/>
					                 <form:hidden path="gstinLocationSet[${loop.index}].refGstinId"  class="form-control" value="${item.refGstinId}"/>
					             </div>
					        </div>
					        <div class="label-text label-text2">Location/Channel/Department - ${(loop.index) +1} :</div>
					      </label>
					      <span class="text-danger cust-error" id="gstinLocation-empty"></span>
   				 	</div>
   				 	     <%--  <div class="form-group input-field ">
		               	<label class="label">
					            <div class="controls ">
					                <form:input path="gstinLocationSet[${loop.index}].gstinStore" type="text" id="gstinStore" class="form-control" value="${item.gstinStore}"/>
					             </div>
					        </div>
					        <div class="label-text label-text2">Store - ${(loop.index) +1} :</div>
					      </label> --%>
					      
		     	 </li>
		     	 </c:forEach>
		     	 <li>
		     	 </li>
		     	 <li>		
		     		<div class="form-group input-field mandatory">
		                  <label class="label">
		                  			<form:input id="pinCode" path="gstinAddressMapping.pinCode" maxlength="6" required="true" value="${gstinDetailsObj.gstinAddressMapping.pinCode }" class="form-control"/>
		                  			<div class="label-text label-text2">PIN Code</div>
		                  </label>
		                  <span class="text-danger cust-error" id="zip-req">This field is required and should be 6 digits</span>
		                  <span class="text-danger cust-error" id="empty-message"></span>
		            </div>
		          </li>
				  <li>			
		     		<div class="form-group input-field mandatory">
		                  <label class="label">
		                  			<form:input id="city" readonly="true" path="gstinAddressMapping.city" required="false" value="${gstinDetailsObj.gstinAddressMapping.city }" class="form-control"/>
		                  			<div class="label-text label-text2">City</div>
		                  </label>
		            </div>
		          </li>
		     	  <li>			
		     		<div class="form-group input-field mandatory">
			               <label hidden="true" class="label">
			                    <form:hidden id="state" readonly="true" path="gstinAddressMapping.state" value="${gstinDetailsObj.gstinAddressMapping.state }" required="true" class="form-control"/>
			                   	<div hidden="true" class="label-text label-text2">State</div>
			                    	                 
			               </label>
			               <span class="text-danger cust-error" id="state-req">This field is required</span>   
			        </div>
				  </li>
				  <li>			
		     		<div class="form-group input-field">
			               <label class="label">
			                    <form:input id="country" readonly="true" path="gstinAddressMapping.country" value="${gstinDetailsObj.gstinAddressMapping.country }" required="true" class="form-control"/>
			                   	<div class="label-text label-text2">Country</div>
			                    	                 
			               </label>
			               <!-- <span class="text-danger cust-error" id="country-req">This field is required</span> -->   
			        </div>
				  </li>
				 
			   <form:hidden path="id" value="${gstinDetailsObj.id }"/>
			   <form:hidden path="createdBy" value="${gstinDetailsObj.createdBy }"/>
			   <form:hidden path="createdOn" value="${gstinDetailsObj.createdOn }"/>
			   <form:hidden path="referenceId" value="${gstinDetailsObj.referenceId }"/>
			   <form:hidden path="status" value="${gstinDetailsObj.status }"/>
			   <form:hidden path="uniqueSequence" value="${gstinDetailsObj.uniqueSequence }"/>
			   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			   <form:hidden path="gstinAddressMapping.id" value="${gstinDetailsObj.gstinAddressMapping.id }"/>
                </ul>
              </div>
              <br/>
               <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary" id="submitGstin" >Update</button>
            	<a href="#" onclick="javascript:deleteRecord('${gstinDetailsObj.id}');" class="btn btn-secendory" style="margin:5px 0 0 0"> Delete </a>
            </div>
            
            </div>
           
            <!--content end-->
            
           
             </form:form>
            </div>
           
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap edit-Page-List">
		
		<div class="dnynamicGstinDetails" id="toggle">								
		</div>
		
		
   </div>
</div>
  
<form name="manageGstinDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>
