<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>



 

 <header class="insidehead">
      <a href="<spring:url value="./showFeedbackDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--   <span>Feedback Form<span> --> </a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>


 <div class="container" >
  <div class="box-border" >
  <form:form commandName="feedbackDetails" method="post" id="feedbackFormId" enctype="multipart/form-data" action="./addFeedbackDetails">
  	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
      	<div class="box-content">
              <div class="">
               <span><center><h4><b>Feedback Form</b></h4></center></span>
                <ul>
                 <li>
				  	<div class="form-group input-field mandatory">
						<label class="label"> 
						<select class="form-control" id="masterDesc" name="masterDescDetails" class="form-control">
						</select> 
							<div class="label-text label-text2">Choose page</div>
						</label>
						<span class="text-danger cust-error" id="master-desc">This field is required</span>
					</div>
				  </li>
                 <li>
                 <div class="form-group input-field mandatory">
					<label class="label">  
						<form:textarea path="feedbackDesc" id="feedbackDesc" maxlength="350" required="true" class="form-control"  />
						<div class="label-text label-text2">Feedback</div>						
					</label>
					<span class="text-danger cust-error" id="feedback-query-req">This field is required.</span>
					</div>
				  </li>
				   <li>
						<div class="form-group input-field">
								    <label class="label">
									<div class="label-text label-text2">Upload Screenshot</div>
								    </label>
							  <div class="card">
									<span>* Only upload *.jpg,*.png files <br></span> 
									<span>* Image size should be between 1KB to 500KB<br></span>
									<span>* You can upload maximum 3 images<br></span>
									
                                           
	                                     <div class="controls"> 
                                              <div class="entry"> 
                                                  <div class="input-group">
                                                       <input class="btn btn-primary btn-block"accept="image/*" name="fields[]" type="file" id="file1">
                                                       <span class="input-group-btn">
                                                        <button class="btn btn-success btn-add" type="button" id="button1" disabled="disabled">
                                                       <span class="glyphicon glyphicon-plus"></span>
                                                        </button>
                                                        </span>
                                                 </div><br>	
                                                 	                                                  
                                          </div>                                                     
                                      </div> 
								</div>
							</div>
						</li>
					</ul>
              </div>
            </div>
            <!--content end-->
           
             <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="feedbackSubmit" formnovalidate="formnovalidate">Save</button>
            <a  class="btn btn-secendory" href="showFeedbackDetails" > Cancel</a>
            
            
            </div>
       </form:form>
     
  </div>
</div>


<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/manageFeedbackDetails.js"/>"></script>	


