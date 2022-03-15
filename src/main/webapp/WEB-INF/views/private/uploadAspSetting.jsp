<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>
      <c:if test="${apiResponse == 'SUCCESS'}"> 
          <script type="text/javascript">
			bootbox.alert({
				 	message: "Setting for the particular GSTIN Updated", 
				 	callback: function(){ 
				 		window.location.href = "${pageContext.request.contextPath}/aspMasters";
				 	}
			});
		</script>
     </c:if> 
            
      <c:if test="${apiResponse == 'ACCESS_DENIED'}"> 
             <script type="text/javascript">
			bootbox.alert({
				 	message: "Access Denied", 
				 	callback: function(){ 
				 		window.location.href = "${pageContext.request.contextPath}/aspMasters";
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
 <header class="insidehead">
      <a href="<spring:url value="/aspMasters" />" class="btn-back"><i class="fa fa-angle-left"></i> 
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
     <!-- 	<span>Upload Type<span>  --> 
      </a>
 </header>

<div class="container" >
  
  <div class="box-border">
  <form:form  method="post" id="uploadAsp" action="./uploadInvoicesSetting" >
  
  	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition">
 
      <div class="accordion_in acc_active">
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
              <div class="card">
              <span><center><h4><b>Upload Type</b></h4></center></span>
                <ul>
			    <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<select  id="gstinId" name="gstinId" class="form-control">
                   			</select>
							<div class="label-text label-text2">GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
				    <li>
                   	<div class="form-group  input-field">
                   	<div class="label-text "> Choose the way you want to upload data to JioGST</div>
                   	<div class=" rdio-success">
                   	 <input type="radio" name="uploadType" value="Auto" id="Auto" >
                   	 <label for="Auto"><b>Auto upload</b></label>
                   	 <label for="Auto">(Data will be uploaded automatically at 11:59 pm everyday)</label>
                   	 </div>
                   	 <div class=" rdio-success">
                   	 <input type="radio" name="uploadType" value="Manual" id="Manual">
                            <label for="Manual"><b>Manual upload </b></label>
                            <label for="Auto">(You will have to click Upload button to upload data)</label>
                   	 </div>
                   	  <div class=" rdio-success">
                   	  <input type="radio" name="uploadType" value="Both" id="Both">
                            <label for="Both"><b>Both </b></label>
                            <label for="Auto">(You will have to click Upload button to upload data but data will also be uploaded automatically at 11:59 pm everyday)</label>
                            </div>
                   	 </div>
				  </li>
			    </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
     <div class="text-center">
     	<div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Save</button>
      	</div>
     </div>
     </form:form>
    
  </div>
</div>
<br/>
<!--common end-->
