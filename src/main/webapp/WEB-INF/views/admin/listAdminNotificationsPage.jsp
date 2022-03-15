<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="../private/common-alert.jsp" %>

<header class="insidehead">
    <a href="<spring:url value="/idt/idthome" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Notifications<span></a>
</header>
 
<div class="container">
  <div class="box-border">
  		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
   	  <div class="cust-wrap">
		<div class="dnynamicNotifications" id="toggle">	
								
		</div>
      </div>
  </div>
</div>

<form name="manageNotification" method="post">
    <input type="hidden" name="id" value="">
  	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/notifications/listAdminNotifications.js" />"></script>
