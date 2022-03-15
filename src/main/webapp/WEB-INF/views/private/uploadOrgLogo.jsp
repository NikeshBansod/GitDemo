<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<c:choose>
    <c:when test="${empty response}"> 
     
    </c:when> 
    <c:otherwise> 
        <c:choose>
            <c:when test="${response == 'Logo Uploaded Sucessfully'}"> 
             <script type="text/javascript">
	bootbox.alert({
		 	message: "${response}", 
		 	callback: function(){ 
		 		window.location.href = "${pageContext.request.contextPath}/editUser";
		 	}
	});
	</script>
            </c:when> 
            <c:otherwise> 
             <script type="text/javascript">
             bootbox.alert('${response}');
			</script>
            </c:otherwise>
        </c:choose>
    </c:otherwise> 
</c:choose>



<script type="text/javascript" src="<spring:url value="/resources/js/organization/uploadLogo.js"/>"></script>

<div id="editPageContainer">
 <header class="insidehead">
      <a href="<spring:url value="/editUser" />" class="btn-back"><i class="fa fa-angle-left"></i> 
      	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      </a>
 </header>

<%-- <div class="container" >
  
  <div class="box-border">
  <form:form id="fileForm"  enctype="multipart/form-data" method="post" action="./uploadLogo" >	
    <div class="accordion_example2 no-css-transition">
 
      <div class="accordion_in acc_active">
        <!-- <div class="acc_head">Organization Details</div> -->
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
              <div class="card">
                <ul>
                  <li>
				    <div class="form-group input-field">
                            <label class="label">
                             <img alt="No Logo Uploaded" src="${pageContext.request.contextPath}/getOrgLogo">
                                <input type="file"   accept="image/*" name="file" class="form-control"  id="file"/>
                                <span >Please upload *.jpg,*.png files only within size of 10KB.</span>
                                <!-- <button type="button" id="btnUpload" class="btn btn-primary" value="Upload"/>Upload</button> -->
                                  <button type="button" id="btnClear" class="btn btn-primary" value="Clear">Clear</button>
                                <div class="label-text label-text2">Logo</div>
                            </label>
                        </div> 
				  
				    </li>
				  
                </ul>
              </div>
            </div>
            
            
          </div>
        </div>
    
     
      </div>
      <!-- <div class="com-btn">Register</div>   -->
     <!-- <input type="submit" class="btn btn-large btn-primary" value="Save"/> -->
     <div class="text-center">
     	<button type="submit" class="btn btn-primary" formnovalidate="formnovalidate" id="btnUpload" value="Upload">Upload</button>
     </div>
     </form:form>
    
  </div>
</div> --%>

     <div class="container">
                    <div class="uploadPage">
                      <form:form id="fileForm"  enctype="multipart/form-data" method="post" action="./uploadLogo" >	
                      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
                        <div class="card">
                        <span><center><h4><b> Upload Organization's Logo </b></h4></center></span>
                            <span>* Only upload *.jpg,*.png files <br></span>
                            <span>* Image size should be between 1KB to 100KB</span>
                            
                            <div class="input-group">
							    <span class="input-group-btn">
									<button id="fake-file-button-browse" type="button" class="btn btn-default">
										<span class="fa fa-file"></span> Choose
									</button>
								</span>
								<input type="file" accept="image/*" name="file" id="files-input-upload" style="display:none">
								<input type="text" id="fake-file-input-name" disabled="disabled" placeholder="File not selected" class="form-control">
								<span class="input-group-btn">
									<button type="submit" class="btn btn-default" disabled="disabled" id="fake-file-button-upload">
										<span class="fa fa-upload"></span> Upload
									</button>
									
								</span>
							</div>
                        </div>
                   </form:form>
                    </div>
                </div>

<br/>
</div>

<!--common end-->

