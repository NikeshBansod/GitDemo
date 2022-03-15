<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name='format-detection' content='telephone=no'>
		<link rel="shortcut icon" href="<spring:url value="./resources/images/wizardImages/favicon.png" />">
		<title><tiles:getAsString name="title" /></title>
		
		<link rel="stylesheet" href="<spring:url value="./resources/css/jquery-ui.min.css" />">
		<link href="<spring:url value="./resources/css/wizardCss/normalize.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="./resources/css/wizardCss/master.css" />" rel="stylesheet" type="text/css">
		<!-- apply in tabledata-role="table" class="ui-responsive"-->
		<link href="<spring:url value="./resources/css/wizardCss/media-queries.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="./resources/css/wizardCss/font-awesome.min.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="./resources/css/wizardCss/bootstrap.min.css" />" rel="stylesheet" type="text/css">
		<link href="<spring:url value="/resources/accordion/smk-accordion.css" />" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="<spring:url value="./resources/css/wizardCss/table.css"/>" type="text/css">		
		<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
		
		<script type="text/javascript" src="<spring:url value="./resources/js/jquery-1.12.4.min.js" />"></script> 
		<script type="text/javascript" src="<spring:url value="/resources/accordion/smk-accordion.js" />"></script>
		<script src="<spring:url value="./resources/js/jquery-ui.min.js" />"></script>
		<script src="<spring:url value="/resources/js/plugins.js" />"></script> 
		<script type="text/javascript" src="<spring:url value="/resources/js/main.js" />"></script>
		<script type="text/javascript" src="<spring:url value="./resources/js/wizardJS/wizard-gstn-main.js" />"></script>
		<script type="text/javascript" src="<spring:url value="./resources/js/wizardJS/modernizr-2.8.3.min.js" />"></script>  
		<script type="text/javascript" src="<spring:url value="./resources/js/wizardJS/index.js" />"></script>	
		<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/wizardCommonValidation.js"/>"></script>  
		<script src="<spring:url value="./resources/js/bootstrap.min.js" />"></script> 
		<script type="text/javascript" src="<spring:url value="./resources/js/bootbox.min.js" />"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/csrf.js" />"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
	</head>
	<body>	
		<div class="lk-wrapper">
		  <div class="lk-content">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="analytics-header" />
      		<tiles:insertAttribute name="analytics-body" />
	        <div class="min-height500"><tiles:insertAttribute name="body" /></div>  
			<tiles:insertAttribute name="footer" />
		  </div>  
		</div>  
	</body>
</html>

