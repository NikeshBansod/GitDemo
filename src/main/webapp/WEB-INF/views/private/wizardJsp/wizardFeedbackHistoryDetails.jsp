<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
 <input type="hidden" name="id" value="${feedbackhistory.id}">
	    <input type="hidden" name="userId" value="${feedbackhistory.userId}">
      <input type="hidden" name="masterDescDetails" value="${feedbackhistory.masterDescDetails}"> 

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/feedbackDetails/wizardfeedbackhistory.js"/>"></script> --%>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="insidepages">
	<div class="breadcrumbs">
		 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
         		<a href="<spring:url value="/wHome#master"/>">Home</a><span>  &raquo; </span> <span><a href="#" id="gotoFeedbackHistoryLists">Feedback </a></span> <span> &raquo; </span> Feedback History Details
 		 </header>
	</div>
	<!-- <div class="inside-cont-invoice"> -->
		<div class="container">
			<table class="table table-striped table-bordered table-hover " id="invoiceHistoryTab">
				<thead>
					<tr>
						
						 <th style="text-align: center;">Feedback Id</th>
						<th style="text-align: center;">Feedback Description</th>
						<!-- <th style="text-align: center;">Created On</th>  -->
						
						<th style="text-align: center;">Images Uploaded</th>
					</tr>
				</thead>
				<tbody>			
					<c:forEach items="${feedbackDetails}" var="feedbackDetails" varStatus="status">
						<tr>
							 <td style="text-align: center;">${feedbackDetails.id}</a> </td>   
							 
         					
							  <td style="text-align: center;">${feedbackDetails.feedbackDesc}</td> 
							 
							 <td > <c:if test="${(b641 == null) && (b642 == null )&& (b643 == null) }">
						 <c:out value="-"/>
						</c:if>
						 <c:if test="${b641 != null}">
                                <img class="img-responsive" onerror="this.style.display='none'"  width="300" height="300"  src="data:image/jpg;base64,${b641}"/><br>
                                <img class="img-responsive" onerror="this.style.display='none'"  width="300" height="300" src="data:image/jpg;base64,${b642}" /><br>
                                <img class="img-responsive" onerror="this.style.display='none'"  width="300" height="300" src="data:image/jpg;base64,${b643}" /><br>
                        </c:if></td>
							</tr>
						
					</c:forEach>
					
				</tbody> 
			</table>
		</div>
	<!-- </div> -->
	

 <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/feedbackDetails/wizardfeedbackhistorydetails.js"/>"></script> 
<%--  <script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>  --%>

	


</section>
<form name="gotoFeedbackHistoryList" method="get">
	
    <!-- <input type="hidden" name="id" value="">
	    <input type="hidden" name="userId" value=" ">
      <input type="hidden" name="masterDescDetails" value=" "> -->
  <%--  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" /> --%>
 
</form>
