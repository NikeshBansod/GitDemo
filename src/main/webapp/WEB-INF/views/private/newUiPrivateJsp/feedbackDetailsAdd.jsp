<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



<div class="loader"></div>
<section class="block">
	<div class="container">
		   <form:form commandName="feedbackDetails" method="post" id="feedbackFormId" enctype="multipart/form-data" action="./addFeedbackDetails">
  	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  	
  	<div class="brd-wrap" id="listHeader">
	         	<a href="./showFeedbackDetails" id="showfeedbackhistory"><strong>Feedback History</strong></a><span> » </span> <strong>Feedback</strong>
	 		</div>
	   <div class="form-wrap">
	  <div class="row">
	  <div class="col-md-6">
						<label for="label">Choose Page<span style="font-weight: bold;color: #ff0000;"> *</span></label>
						<select class="form-control" id="masterDesc" name="masterDescDetails" class="form-control" style="padding-top: 0px;padding-bottom: 0px;">
						</select> 
						<span class="text-danger cust-error" id="master-desc">This field is required</span>
					</div>
	  
	  <div class="col-md-6">
						<label for="label">Feedback<span style="font-weight: bold;color: #ff0000;"> *</span></label>
						<form:textarea path="feedbackDesc" id="feedbackDesc" maxlength="350" required="true" class="form-control"  /> 
						<span class="text-danger cust-error" id="master-desc">This field is required</span>
						<span class="text-danger cust-error" id="feedback-query-req">This field is required.</span>
	  	</div>
	  </div>
	   <div class="form-wrap">
	   <div class="col-md-6">
	                          <div class="card">
									<span>* Only upload *.jpg,*.png files <br></span> 
									<span>* Image size should be between 1KB to 500KB<br></span>
									<span>* You can upload maximum 3 images<br></span>
                                       <div class="input-group" > 
	                                     <div class="controls"> 
                                              <div class="entry"> 
                                                  <div class="input-group" >
                                                    <input class="btn btn-primary choosefile"  accept="image/*" name="fields[]" type="file" id="file1">
                                                     <span class="input-group-btn"  >
                                                     <button class="btn btn-success btn-add buttonsize" type="button" id="button1" disabled="disabled">
                                                     <span class="glyphicon glyphicon-plus"></span>
                                                </button>
                                           </span>
                                  </div><br>	
                             </div> 
                      </div>                                                    
               </div> 
		</div>
	  </div>
	  </div>
	  <div class="col-md-12 button-wrap">
	  <button type="submit" class="btn btn-success blue-but"  id="feedbackSubmit" formnovalidate="formnovalidate" style="width: auto;height: 46px;">Save</button>
            </div>
	  	</div>
	  	</form:form>
	  	</div>
</section>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/feedbackDetails/manageFeedbackDetails.js"/>"></script>	
