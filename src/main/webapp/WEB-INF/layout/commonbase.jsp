<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp" %> 
 
<tilesx:useAttribute name="current" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name='format-detection' content='telephone=no'>
    <meta name="description" content="">
	
	<title><tiles:getAsString name="title" /></title>	
	
	<link rel="apple-touch-icon" href="<spring:url value="/resources/images/newUiPics/apple-touch-icon.png" />">
    <link rel="shortcut icon" href="<spring:url value="/resources/images/newUiPics/favicon.png" />">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/bootstrap.min.css" />">
    <link rel="stylesheet" href="<spring:url value="/resources/css/jquery-ui.min.css" />">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/font-awesome.css" />">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/animate.css" />">

    <!-- Bootstrap Theme: Buttons styles, colors, etc. -->
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/bootstrap-theme.css" />">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/table.css" />">

    <!-- Main CSS -->
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/master.css" />">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/media-queries.css" />">	
    
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/jquery.dataTables.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/rowReorder.dataTables.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/responsive.dataTables.min.css"/>">
    <link rel="stylesheet" href="<spring:url value="/resources/css/newUiCss/dataTables.checkboxes.css"/>">
    	
	<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/jquery-1.9.1.js" />"></script>	
  	<script type="text/javascript"  src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script> 
	<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/plugins.js" />"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/main.js" />"></script>
  	<script type="text/javascript"  src="<spring:url value="/resources/js/gstncommonvalidation.js"/>"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/bootbox.min.js" />"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/csrf.js" />"></script>
	<script type="text/javascript" src="<spring:url value="/resources/js/analytics.js" />"></script>
  	<script type="text/javascript"  src="<spring:url value="/resources/js/gstn-main.js"/>"></script>
</head>  
<body>
      <main>
      	<div id="top"></div>
		<tiles:insertAttribute name="header" />
	  	<tiles:insertAttribute name="analytics-header" />
      	<tiles:insertAttribute name="analytics-body" />
      	<tiles:insertAttribute name="body" />
      	<p id="back-top">
	        <a href="#top"><span></span></a>
	    </p>
      </main>
      <tiles:insertAttribute name="footer" />
      
        <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/jquery.dataTables.min.js"/>"></script> 
        <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/dataTables.rowReorder.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/dataTables.responsive.min.js"/>"></script>   
        <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/dataTables.checkboxes.min.js"/>"></script>   
</body>

</html>