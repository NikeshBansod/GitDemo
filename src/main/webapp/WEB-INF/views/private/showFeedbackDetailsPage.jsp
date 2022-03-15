<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/manageFeedbackDetails.js"/>"></script>


<header class="insidehead">
      <a href="<spring:url value="/showFeedbackDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> </a>  <!-- <span>Manage GSTIN<span> -->
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
<%--  <header class="insidehead">
      <a href="<spring:url value="/secondaryUserPage" />" class="btn-back"><i id="commonEditAccordionId" class="fa fa-angle-left"></i> </a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
</header> --%>

<div class="container">
  <div class="box-border">
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  
  
  <div class="card">
  <h4><center>Feedback Details:</center></h4>
  <table class="  table table-striped table-bordered ">
		 <tbody style="height:100px">
                         <tr >
                        <th >Feedback Description:</th>
                        <td>${masterDesc[0].masterDesc}</td>
                        </tr>
                        <tr>
                        <th>Feedback:</th>
                        <td>${feedbackDetails[0].feedbackDesc}</td>
                        </tr>
                        <tr>
                        <th valign="middle">Uploaded Images:</th>
                        <td >
                        
                        <c:if test="${(b641 == null) && (b642 == null )&& (b643 == null) }">
						 <c:out value="-"/>
						</c:if>
						 <c:if test="${b641 != null}">
                                <img class="img-responsive" onerror="this.style.display='none'"  width="100" height="100"  src="data:image/jpg;base64,${b641}"/>
                                <img class="img-responsive" onerror="this.style.display='none'"  width="100" height="100" src="data:image/jpg;base64,${b642}" />
                                <img class="img-responsive" onerror="this.style.display='none'"  width="100" height="100" src="data:image/jpg;base64,${b643}" />
                        </c:if>
                         </td>
                         </tr>
                        <!--  <tr>
                         <th>Status:</th>
                         <td bgcolor="red">pending</td>
                         </tr> -->
            </tbody>
    </table>	
</div>
<div class="com-but-wrap">
            <a  class="btn btn-secendory" href="showFeedbackDetails" > Cancel</a>
</div>
</div>
 

<!--common end-->
<form name="feedbackDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  </form>
  

<script type="text/javascript" src="<spring:url value="/resources/js/feedbackDetails/uploadFiles.js"/>"></script>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/moment.min.js"/>"></script>




     
		