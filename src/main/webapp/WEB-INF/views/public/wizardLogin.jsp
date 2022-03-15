<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${ response}');
	</script>	
</c:if>

 <script type="text/javascript" src="<spring:url value="/resources/js/login/additional.js"/>"></script>
 
<form autocomplete="off" class="form-signin" method="POST" action="./wloginn">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
<section>
  <div class="home-login">
  <div class="login-container">
    <div class="home-para">
      <div class="para1">
      	<div class="txt1">JioGST Bill Lite</div>
      	<div class="txt1">Effortlessly create GST-compliant Invoices, </div>
        <div class="txt1">Credit & Debit notes, Purchase entries, Bill of Supply, etc.</div>
        <div class="para2">
          <p>
          		JioGST Bill Lite is your one-stop billing solution. Use it for not only creating your GST-compliant documents, but also for downloading & uploading the mentioned 
          		documents to JioGST compliance system for GST return filing.
          </p>
          <p>Bill Lite makes management of your customer master, goods master, services master simple, easy, & quick. So, begin your invoicing right now with Bill Lite.</p>
        </div>
       <!--  <div>
          <div class="sim-button button4"><span>learn more</span></div>
        </div> -->
      </div>
    </div>
    <div class="login">
      <div class="logindetails">
        <div class="loginheader">Login your<span>Bill Lite account</span></div>
    	<c:if test="${not empty loginError}">
			<div class="text-center" >
				<p style="color:red;font-size:15px;margin-top: 0px;margin-bottom: 20px;">${loginError}</p>
      		</div>	
		</c:if>
		<span class="clearfix"></span>
        <label>
        <%-- 	<form:input path="mobileNo" id="mobileNo" type="text" name="mobileNo" maxlength="10" required="true" /> --%>
        	<input type="text" id="userId" maxlength="10" name="userId"  required autofocus>
        	<div class="label-text">Mobile Number </div>
        </label>
        <label>
        	<%-- <form:input path="password" id="password" type="password" name="password" maxlength="25"  required="true" /> --%>
        	 <input type="password" maxlength="25" id="password" name="password"  required>
        	<div class="label-text">MPIN</div>
        </label>
        <div class="insidebtn"> <input type="Submit" class="sim-button button1" value="Login" />
        	 <a class="sim-button button2" href="./wizardConfirmPassword"><span>Forgot MPIN?</span></a> </div>
        <div class="insidebtn"> <a class="sim-button button3" href="./wizardRegistration" ><span>Create Account</span></a> </div>
        <div class="notreg">Not Registered yet?</div>
      </div>
    </div>   
  </div>
</section>
</form>