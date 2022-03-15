<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<%@include file="../common-alert.jsp" %>



<div class="loader"></div>
<section class="block">
	<div class="container">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />

    <div class="form-wrap">
       <div class="brd-wrap" id="listHeader">
	 		<a href="./showFeedbackDetails" id="showfeedbackhistory"><strong>Feedback History</strong></a><span> » </span> <strong>Feedback History Details</strong>
       </div>
	 </div>

	<div class="table-wrap">
	  <table class="  table table-striped table-bordered "> 
		 <tbody style="height:100px">
                   <div class="row">
                         <th ><strong>Feedback Description:</strong></th>
                     </div>
                <tr>
                    <div class="row">
                        <td>${masterDesc[0].masterDesc}</td>
                     </div>
                </tr>
                        <div class="row" >
                        <th><strong>Feedback:</strong></th>
                        </div>
                 <div class="row">
                   <tr>
                        <td>${feedbackDetails[0].feedbackDesc}</td>
                   </tr>
                 </div>
            <div class="row">
                        <th valign="middle"><strong>Uploaded Images:</strong></th>
            </div>
             <div class="row">
                        <tr>
                        <td >
                        <c:if test="${(b641 == null) && (b642 == null )&& (b643 == null) }">
						 <c:out value="-"/>
						</c:if>
						 <c:if test="${b641 != null}">
						 <div class="col-md-4">
                                <img class="img-responsive" onerror="this.style.display='none'"  width="300" height="200"  src="data:image/jpg;base64,${b641}"/>
                                </div>
                                <div class="col-md-4">
                                <img class="img-responsive" onerror="this.style.display='none'"  width="300" height="200" src="data:image/jpg;base64,${b642}" />
                                </div>
                                <div class="col-md-4">
                                <img class="img-responsive" onerror="this.style.display='none'"  width="300" height="200" src="data:image/jpg;base64,${b643}" />
                                 </div>
                        </c:if>
                         </td>
                         </tr>
                         </div>
            </tbody>
     </table> 
     </div>
</div>
</section>


<!--common end-->
<form name="feedbackDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  </form>






<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/feedbackDetails/manageFeedbackDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/feedbackDetails/uploadFiles.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/moment.min.js"/>"></script>

