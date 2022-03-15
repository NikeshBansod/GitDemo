<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp" %> 

<tilesx:useAttribute name="current" />

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name='format-detection' content='telephone=no'>
		<link rel="shortcut icon" href="<spring:url value="./resources/images/wizardImages/favicon.png" />">
		<title>Jio GST</title>
		<link href="<spring:url value="./resources/css/wizardCss/normalize.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="./resources/css/wizardCss/master.css" />" rel="stylesheet" type="text/css">
		<!-- apply in tabledata-role="table" class="ui-responsive"-->
		<link href="<spring:url value="./resources/css/wizardCss/media-queries.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="./resources/css/wizardCss/font-awesome.min.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="./resources/css/wizardCss/bootstrap.min.css" />" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<spring:url value="/resources/js/csrf.js" />"></script>
</head>
<body>
<script type="text/javascript" src="<spring:url value="./resources/js/jquery-1.12.4.min.js" />"></script> 
<script type="text/javascript" src="<spring:url value="./resources/js/wizardJS/modernizr-2.8.3.min.js" />"></script>  
<script type="text/javascript" src="<spring:url value="./resources/js/wizardJS/index.js" />"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/wizardCommonValidation.js"/>"></script>
<script src="<spring:url value="./resources/js/bootstrap.min.js" />"></script> 
<script type="text/javascript" src="<spring:url value="./resources/js/bootbox.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/csrf.js" />"></script>
<div class="lk-wrapper">
  <div class="lk-content">
		<div><tiles:insertAttribute name="header" /></div> 
		<div><tiles:insertAttribute name="analytics-header" /></div>
      	<div><tiles:insertAttribute name="analytics-body" /></div>
        <div><tiles:insertAttribute name="body" /></div>  
        <div><tiles:insertAttribute name="footer" /></div>  
  </div>
</div>
</body>
</html>
