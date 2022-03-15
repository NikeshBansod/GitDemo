<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<%-- <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/organization/wizardUploadLogo.js"/>"></script>	 --%>	
	<section class="insidepages">	
		<header class="insidehead" id="originalHeader">
	       			<div class="breadcrumbs" id="listHeader">
			<div class="col-md-12" id="listheader1">
	         	<a href="<spring:url value="/wHome#master"/>">Home</a><span>  &raquo; </span> <span><a href="<spring:url value="/wizardFeedbackHistory"/>" id="gotoFeedbackHistoryLists">Feedback History List</a></span> <span> &raquo; </span> Create New Feedback
	 		</div>
	 		</div>
	 		</header>
		
		<br>
			
			
		<div id="addEmployeeDetails">
			<form:form commandName="feedbackDetails" method="post" enctype="multipart/form-data" id="fileForm" action="./wAddFeedbackDetails">
			
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
		    	<div class="account-det">
					<div class="det-row">	
						<div class="det-row-col-half astrick">
							<div class="label-text">Choose Page</div>
						  	<select class="form-control" id="masterDesc" name="masterDescDetails" class="form-control"></select>
						  	<span class="text-danger cust-error" id="master-desc">This field is required</span>
						</div>
						<div class="det-row-col-half astrick">
							<div class="label-text">Feedback</div>
						  	<form:textarea path="feedbackDesc" id="feedbackDesc" maxlength="400" required="true" class="form-control"/>
						  	<span class="text-danger cust-error" id="feedback-query-req">This field is required.</span>
						</div> 
					</div>
				</div>

			   		
			   		<div class="account-det">
			   			<div class="det-row-col-half astrick">
			                <span>*  &nbsp; Only upload *.jpg,*.png files <br></span>
			                <span>* &nbsp;  Image size should be between 1KB to 500KB<br></span>
			                <span>*  &nbsp; Add Upto 3 images<br></span>
			                
							<div class="input-group">
							<div class="controls">
							    
									<!-- <button id="fake-file-button-browse" type="button" class="btn btn-default">
										
									</button> -->
								
		
         
          
           
              <div class="entry">
              <div class="input-group">
                
             
                <input class="btn btn-default"  accept="image/*" name="file" multiple="multiple" id="file1" type="file" >
                
                 <span class="input-group-btn">
                			
              <button class="btn btn-success btn-add" type="button"  id="button1" disabled="disabled">
                                <span class="glyphicon glyphicon-plus"></span>
                </button>
                 
                
                </span>
                
                
              </div><br>
           </div></div>
          
          
        
								<!-- <input type="file" accept="image/*" name="file" multiple="multiple" id="files-input-upload" style="display:none">
								<input type="text" id="fake-file-input-name" multiple="multiple" disabled="disabled" placeholder="File not selected" class="form-control">								 -->
								
							</div>   
						</div>	</div>				           
			    
				
				<div class="insidebtn"> 	       	
					<button type="submit" class="sim-button button5" id="feedbackSubmit" formnovalidate="formnovalidate">Save</button> 
				</div><br>
				<br>
				
				</form:form></div>
				  <!--  <form  method="post" id="fileForm" action="./wizardFeedbackHistory">
				<div class="insidebtn"> 	       	
					<button type="submit" class="sim-button button5" id="getFeedBackHistory" formnovalidate="formnovalidate">Feedback History</button> 
				</div>
				</form>   -->
				
				
				
				
				 
			  
				
		
				
				
	
	<!-- <form name="editAdditionalChargeDetails" method="post">
    <input type="hidden" name="id" value="">
     <input type="hidden" name="userId" value="">
</form> -->
	</section>			
			
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/feedbackDetails/wizardManageFeedbackDetails.js"/>"></script>		
          