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
					window.location.href = "${pageContext.request.contextPath}/wEditUser";
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



<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/organization/wizardUploadLogo.js"/>"></script>


 <section class="insidepages">
  	<!-- <div class="inside-cont"> -->
  		<div id="editPageContainer">
			<form:form id="fileForm"  enctype="multipart/form-data" method="post" action="./wUploadLogo" > 
		  		<div class="col-md-12">
	          	 	<div class="breadcrumbs">
		          	<a href="./wEditUser">My Profile</a> <span>&raquo;</span>
		           	<img src="./resources/images/wizardImages/headerimg.png" width="29" height="29"> Upload Organization Logo
		           	</div>
		        </div>
				<div class="account-det">
			   		<div class="det-row">
			   			<div class="det-row-col-half astrick">
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
			       </div> 
				</div>
			</form:form>
  		</div>
	<!-- </div> -->
</section>
 
