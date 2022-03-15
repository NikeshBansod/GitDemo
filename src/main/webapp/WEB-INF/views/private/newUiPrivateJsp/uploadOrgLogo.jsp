<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<c:choose>
    <c:when test="${empty response}"> 
     
    </c:when>   
    <c:otherwise> 
        <c:choose>
            <c:when test="${response == 'Logo Uploaded Successfully'}"> 
             <script type="text/javascript">
             bootbox.alert('${response}', function() {					
					window.location.href = "${pageContext.request.contextPath}/editUser";
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



<section class="block">
<div class="container">
<div class="brd-wrap">
                        <strong><a href="./editUser">My Profile</a></strong> »  <strong>Upload Organization's Logo</strong>
                                             
 </div>
 
  <div class="uploadPage">
                      <form:form id="fileForm"  enctype="multipart/form-data" method="post" action="./uploadLogo" >	
                      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
                      

<br>
                        
                        <br>
                        <br>
                        
                            <span>* Only upload *.jpg,*.png files <br></span>
                            
                            <span>* Image size should be between 1KB to 100KB</span>
                            <br><br>
                            
                            <div class="input-group">
							    <span class="input-group-btn">
									<button id="fake-file-button-browse" type="button" class="btn btn-success blue-but">
										<span class="fa fa-file"></span> Choose
									</button>
								</span>
								
								<input type="file" accept="image/*" name="file" id="files-input-upload" style="display:none">
								<input type="text" id="fake-file-input-name" disabled="disabled" placeholder="File not selected" class="form-control"style=" height: 37px;">
								
								<span class="input-group-btn">
									<button type="submit" class="btn btn-success blue-but" style="width: auto;"disabled="disabled" id="fake-file-button-upload">
										<span class="fa fa-upload"></span> Upload
									</button>
									
								</span></div>
							
                        
                   </form:form>
                    </div>
 
 
 
 
 
 
 
 
 
 
 
 
 </div>
 
 
 </section>
 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/organization/uploadLogo.js"/>"></script>