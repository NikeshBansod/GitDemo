<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

     <c:if test="${apiResponse == 'SUCCESS'}"> 
          <script type="text/javascript">
			bootbox.alert({
				 	message: "Setting for the particular GSTIN Updated", 
				 	callback: function(){ 
				 		window.location.href = "${pageContext.request.contextPath}/wAspMasters";
				 	}
			});
		</script>
     </c:if> 
            
      <c:if test="${apiResponse == 'ACCESS_DENIED'}"> 
             <script type="text/javascript">
			bootbox.alert({
				 	message: "Access Denied", 
				 	callback: function(){ 
				 		window.location.href = "${pageContext.request.contextPath}/wAspMasters";
				 	}
			});
	</script>
            </c:if> 
                  
           <c:if test="${apiResponse == 'FAILURE'}">
             <script type="text/javascript">
             bootbox.alert('Setting for the particular GSTIN failed');
			</script>
             </c:if>

<script type="text/javascript" src="<spring:url value="/resources/js/uploadToAsp/uploadToAsp.js"/>"></script>

<section class="insidepages">
	<div class="inside-cont">
				<form:form  method="post" id="uploadAsp" action="./wUploadInvoicesSetting" >
				
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				
				<a href="<spring:url value="/wAspMasters"/>">ASP Home</a> <span>&raquo;</span> ASP Upload Setting
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-full astrick">
								<div class="label-text">GSTIN</div>
							  	<select  id="gstinId" name="gstinId" class="form-control"></select>      
							  	<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
							</div>
						</div>
						<div class="det-row">	
							<div class="det-row-col-full">Choose the way you want to upload data to JioGST
							</div>
                   	 			   <div class="det-row-col-full">
							      <input type="radio" name="uploadType" value="Auto" id="Auto" >
							       <label for="Auto"><b>Auto upload</b></label>
                   	 			   <label for="Auto">(Data will be uploaded automatically at 11:59 pm everyday)</label>
                   	 			   </div>
                   	 			   <div class="det-row-col-full">
							      	<input type="radio" name="uploadType" value="Manual" id="Manual">
		                            <label for="Manual"><b>Manual upload </b></label>
		                            <label for="Auto">(You will have to click Upload button to upload data)</label>
                           			 </div>
                   	 			   <div class="det-row-col-full">
                             		<input type="radio" name="uploadType" value="Both" id="Both">
		                            <label for="Both"><b>Both </b></label>
		                            <label for="Auto">(You will have to click Upload button to upload data but data will also be uploaded automatically at 11:59 pm everyday)</label>
                         		</div>
							</div> 
						</div>
							
							<div class="insidebtn"> 	        	
								<div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Save</button> </div>
							</div>
							</form:form>
			</div>
							
							</section>
					