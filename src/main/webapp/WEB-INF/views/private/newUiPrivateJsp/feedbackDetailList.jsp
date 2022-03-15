<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<%@include file="../common-alert.jsp" %>


<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> 


<div class="loader"></div>
<section class="block">
	<div class="container">
     <%--  <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/home" />"><strong>Home</strong></a> &raquo; <strong> Feedback History</strong>
	        </div>
	    </div> --%>
	    
	    <div class="page-title">
                        <a href="<spring:url value="/home#help_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Feedback History
         </div>
	    <div class="form-wrap">
	    <div class="row"> 
            <div class="col-md-12 button-wrap">
            <a href="./addFeedbackDetails" class="btn btn-success blue-but" style="width: auto;">Add</a>			
		</div>
		</div>
		</div>
	    
	    
	   	<div class="row" id="histable">
	        <div class="table-wrap">
	            <table id="feedbackDataTable" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <th>Sr.</th>
	                            <th>Feedback On</th>
	                            <th>Created On</th>
	                            
	                        </tr>
	                    </thead>
	                    <tbody>
	                    </tbody>
	                  
	            </table>
	        </div>
	        
	    </div> 
	    
	    
	    
    </div>
</section>

<!--common end-->
<form name="feedbackDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
      <input type="hidden" name="masterDescDetails" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  </form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/feedbackDetails/manageFeedbackDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/feedbackDetails/uploadFiles.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/moment.min.js"/>"></script>


