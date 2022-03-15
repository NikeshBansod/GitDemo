<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="../private/common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/listFeedbackDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/manageAdminFeedbackDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/accordion/smk-accordion.js" />"></script>

 <header class="insidehead">
      <a href="<spring:url value="/idt/idthome" />" class="btn-back"><i class="fa fa-angle-left"></i>
      <a class="logoText" href="<spring:url value="idthome" />"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a> 
     <!--   <span>Upload status<span> -->
      </a>
 </header>
  <main>
         <section class="block selectOptions">
      
<div class="container" >
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
 
      <div class="acc_head"></div>
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
              <div class="card">
              <span><center><h4><b>Feedback List</b></h4></center></span>
              <ul>
                 <li>
				  	<div class="form-group input-field mandatory">
						<label class="label"> 
						<select class="form-control" id="masterDesc" name="masterDescDetails" class="form-control">
						</select> 
							<div class="label-text label-text2">Master Description</div>
						</label>
						<span class="text-danger cust-error" id="master-desc">This field is required</span>
					</div>
				  </li>
				  </ul>
				  
				    <div class="text-center">
		     		<div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Show Details</button>
		      		</div>
     				</div>
     
              </div>
            </div>
          </div>
      </div>
  </div>
</div>
	 <div class="cust-wrap">
		
		<div class="dnynamicFeedbackDetails" id="toggle">								
		</div>
      </div>
      
</section>
</main>
     
<br/>
<!--common end-->

