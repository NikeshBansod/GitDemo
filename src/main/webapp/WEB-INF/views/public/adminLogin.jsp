<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>
    <script type="text/javascript" src="<spring:url value="/resources/js/login/additional.js"/>"></script>
<div class="home-logo">
	<a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"></a>
</div>

<form autocomplete="off" class="form-signin" method="POST" action="./idtLogin">
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	
	<div class="login-details">
	
	<c:if test="${not empty loginError}">
		<div class="text-center" >
      		<p style="color:red;font-size:15px;margin-top: 15px;margin-bottom: 0px;">${loginError}</p>
      	</div>	
    </c:if>
		<div class="form-group input-field ">
			<label class="label">  
		       <input type="text" id="uNameRd" maxlength="10" name="uNameRd" class="form-control" placeholder="Enter Username" required autofocus>
			</label>
		 </div>
		 <div class="form-group input-field ">
		 	<label class="label">  
		      <input type="password" maxlength="25" id="nPassTowd" name="nPassTowd" class="form-control" placeholder="Enter Password" required>
		    </label>
		 </div>
		<br/>
		<div class="text-center" ><button class="btn btn-primary" type="submit">Login</button></div>
			
	</div>
</form>

