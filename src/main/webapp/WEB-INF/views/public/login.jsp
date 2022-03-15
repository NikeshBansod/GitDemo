<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %>
  

<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>
    <script type="text/javascript" src="<spring:url value="/resources/js/login/additional.js"/>"></script>
<!-- <div class="home-logo"> -->
<header class="insidehead">
	<a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
	</header>
<!-- </div> -->

<form autocomplete="off" class="form-signin" method="POST" action="./loginP">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="login-details">
	
	<c:if test="${not empty loginError}">
	
		<div class="text-center" >
      		<p style="color:red;font-size:15px;margin-top: 15px;margin-bottom: 0px;">${loginError}</p>
      	</div>	

    	<script type="text/javascript">
    		captureEvent("Login", "Failure : ${loginError}");    
      	</script>
      	
    </c:if>
    
    
		<div class="form-group input-field ">
			<label class="label">  
		       <input type="text" id="userId" maxlength="10" name="userId" class="form-control" placeholder="Enter Mobile No" required autofocus>
			</label>
		 </div>
		 <div class="form-group input-field ">
		 	<label class="label">  
		      <input type="password" maxlength="25" id="password" name="password" class="form-control" placeholder="Enter MPIN" required>
		    </label>
		 </div>
		<br/>
		<div class="text-center" ><button class="btn btn-primary" type="submit">Login</button></div>
		
		<!--  <div class="fgt-pwd"><a href="./confirmPassword">Forgot MPIN</a></div> -->
		 <div class="fgt-pwd"><a href="./confirmPassword">Forgot MPIN</a></div>
		 
		 
		 
		
	</div>
	 <footer>
			<div class="container">
				<div class="signup">Not registered? <a href="./register">SignUp</a> now!</div>
			</div>
			<div class="fgt-pwd">
			<a href="contactUs.jsp">Contact Us</a>
			</div>
	 </footer>
</form>
<script type="text/javascript">


</script>
