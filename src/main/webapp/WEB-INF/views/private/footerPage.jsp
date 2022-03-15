<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/organization/footer.js"/>"></script>

 <header class="insidehead">
      <a href="<spring:url value="home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--   <span>Feedback Form<span> --> </a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

 <div class="container" >
  <div class="box-border" >
  	<form action="./addFooter" method="post" id="addFooterForm"><!-- ./addFooter -->
  		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  		<div class="box-content">
            <div class="">
             <span><center><h4><b>Footer Form</b></h4></center></span>
              <ul>
               <li>
		            <div class="form-group input-field mandatory">
					<label class="label">  
						<textarea placeholder="Max 150 characters" name="footer" id="footerDesc" maxlength="150" rows="2" class="form-control">${fn:escapeXml(loginUser.footer) }</textarea>
						<div class="label-text label-text2">Footer</div>						
					</label>
					<span class="text-danger cust-error" id="feedback-query-req">This field is required.</span>
					</div>
		  		</li>
              </ul>
            </div>
          </div>
          
          <div class="com-but-wrap">
          	<button type="button" class="btn btn-primary" id="footerSubmit" formnovalidate="formnovalidate">Save</button>
            <a  class="btn btn-secendory" href="<spring:url value="/home" />" > Cancel</a>
          </div>
  	
  	</form>
     
  </div>
</div>

