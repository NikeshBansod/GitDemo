<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp" %>

<tilesx:useAttribute name="current" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name='format-detection' content='telephone=no'>
<link rel="shortcut icon" href="<spring:url value="/resources/images/favicon.ico" />">

<title><tiles:getAsString name="title" /></title>

 <link href="<spring:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">
 <link href="<spring:url value="/resources/css/master.css" />" rel="stylesheet" type="text/css">

 <link href="<spring:url value="/resources/css/media-queries.css" />" rel="stylesheet" type="text/css">  

 <script src="<spring:url value="/resources/js/jquery-1.12.4.min.js" />"></script>
 <script src="<spring:url value="/resources/js/plugins.js" />"></script>
 <script src="<spring:url value="/resources/js/main.js" />"></script>
  <script src="<spring:url value="/resources/js/commingSoon.js" />"></script>
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/bootbox.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/csrf.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/analytics.js" />"></script>
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
	 <tiles:insertAttribute name="analytics-header" />
</head>
<body>
	 
      <tiles:insertAttribute name="analytics-body" />
      <tiles:insertAttribute name="body" />


</body>

</html>