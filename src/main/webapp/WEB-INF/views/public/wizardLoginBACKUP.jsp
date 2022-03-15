<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>
<%--     <script type="text/javascript" src="<spring:url value="/resources/js/login/additional.js"/>"></script> 
<div class="home-logo">
	<a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"></a>
</div> --%>

	<div class="row">
			<div class="col-md-12">
				<div class="login">
					<form autocomplete="off" class="form-signin" method="POST" action="./wloginn">
						
						<div class="login-details">
							<div class="form-group">
								<label class="label">  
								   <input type="text" id="userId" maxlength="10" name="userId" class="form-control" placeholder="Enter Mobile No" required autofocus>
								</label>
							 </div>
							 <div class="form-group">
								<label class="label">  
								  <input type="password" maxlength="25" id="password" name="password" class="form-control" placeholder="Enter Password" required>
								</label>
							 </div>
							 <!-- <div class="form-group">
								<label class="label">  
								  <input type="password" maxlength="25" id="authorizationCode" name="authorizationCode" class="form-control" placeholder="Authorization Code" required>
								</label>
							 </div> -->
							 <c:if test="${not empty loginError}">
								<div class="text-center" >
					      			<p style="color:red;font-size:15px;margin-top: 15px;margin-bottom: 0px;">${loginError}</p>
					      		</div>	
					    	</c:if>
							 
							<div class="text-center"><button class="btn btn-primary" type="submit">Login</button></div>
							<div class="signup">Not registered? <a href="./wizardRegistration">SignUp</a> now!</div>
						</div>						
					</form>
				</div>
			</div>
	</div>	

