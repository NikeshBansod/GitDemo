<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if> 

<main>
	<!-- Body content/sections Starts here -->
	<div class="loader"></div>
	<section class="block" id="loginForm">
              <div class="container">
                  <div class="row">
                      <div class="login-wrap">
                          <div class="col-md-8 col-sm-8">
                              <div class="row">
                                  <div class="login-height login-left">
                                      <div class="login-logo">
                                          <h1 class="logo-title"><img src="./resources/images/newUiPics/logo.png" alt=""><strong>Bill</strong> Lite</h1>
                                      </div>
                                      <div class="white-big-title">
                                          <h1 class="portal-title">JioGST Bill Lite</h1>
                                          <p class="portal-txt">
                                              Effortlessly create GST-compliant Invoices, Credit &amp; Debit notes, Purchase entries, Bill of Supply, etc.
                                          </p>                                        
                                      </div>
                                      <div class="login-footer">
                                          Copyright &copy; 2018 Reliance SMSL Limited. All rights reserved.
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <div class="col-md-4 col-sm-4">
                              <div class="row">
                                  <div class="login-height login-right">
                                      <h1 class="blue-title">
                                          Login
                                      </h1>
                                      <h2 class="gray-title">
                                          Bill Lite Account
                                      </h2>
                                      <form autocomplete="off" class="form-signin" method="POST" action="./loginP">
	                                      <ul>
	                                          <li>
	                                              <input type="text" id="userId" maxlength="10" name="userId" class="form-control" placeholder="Mobile Number" required autofocus>
	                                          </li>
	                                          <li>
	                                               <input type="password" maxlength="25" id="password" name="password" class="form-control" placeholder="Mpin" required>
	                                          </li>
	                                          <li>
	                                             <!--  <a href="./confirmPassword" class="forgot">Forgot Mpin?</a> -->
	                                               <a href="./confirmPassword" class="forgot">Forgot Mpin?</a>
	                                              <button class="btn btn-primary btn-login" type="submit" id="loginToGSTBillLite">LOGIN</button>
	                                          </li>                                                                                
	                                      </ul>
	                                      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	                                      <input type="hidden" id="loggedInThrough" name="loggedInThrough" value="" />
	                                      <input type="hidden" id="IMEINo" name="IMEINo" value="" />
	                                      <input type="hidden" id="dataSend" name="dataSend" value="" />
	                                       <input type="hidden" id="notification-status" name="notificationStatus" value="${notificationStatus}" />
	                                      <c:choose>
	                                       <c:when test="${not empty loginError}">	                                      
												<div class="text-center" >
													<p style="color:red;font-size:15px;margin-top: 15px;margin-bottom: 0px;">${loginError}</p>
												</div>													
												<script type="text/javascript">
													captureEvent("Login", "Failure : ${loginError}");    
												</script>
	                                      </c:when>
	                                      <c:otherwise>
	                                      <c:if test="${notificationStatus eq 'y'}">
	                                     	<script type="text/javascript">
	                                     	/* if(android!=null && !android!="")
                                 			{ */
											 var IMEINo = android.getIMEINO();	              
											 $("#IMEINo").val(IMEINo);													
											 var dataSend=android.getIsDataSent();													
											 $("#dataSend").val(dataSend);
                                 			/* } */
												</script> 
										</c:if>
	                                   	
										  </c:otherwise>
	                                      </c:choose>
	                                      
	                                     
	                                   </form>
	                                   <div class="button-wrap">
	                                      <form method="get" action="./register">
	                                          <button class="btn btn-secondary btn-login btn-block" type="submit">Sign Up</button>
	                                      </form>
                                          <!-- <a href="./register" class="gray-link">Not Register yet?</a>  -->
                                          <a href="showContactUs.jsp" class="gray-link">Contact Us</a> 
	                                   </div>                                      
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
	</section>
	<script type="text/javascript" src="./resources/js/newUiJs/login/additional.js"></script>
</main>