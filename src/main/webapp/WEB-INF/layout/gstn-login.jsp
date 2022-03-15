<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp" %> 

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
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title><tiles:getAsString name="title" /></title>


<link href="<spring:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">
<link href="<spring:url value="/resources/css/master.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<spring:url value="/resources/css/jquery-ui.min.css" />">
<link href="<spring:url value="/resources/css/media-queries.css" />" rel="stylesheet" type="text/css">
<link href="<spring:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<spring:url value="/resources/js/jquery-1.12.4.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/bootstrap.min.js" />"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/bootbox.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/csrf.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/analytics.js" />"></script> 
</head>  
<body>


	  <tiles:insertAttribute name="analytics-header" />
      <tiles:insertAttribute name="analytics-body" />
      <tiles:insertAttribute name="body" />
      
      


	<!-- <script type="text/javascript">
	$(document).ready(function() {
	
	    $(document)[0].oncontextmenu = function() { return false; }
	
	    $(document).mousedown(function(e) {
	        if( e.button == 2 ) {
	            alert('Sorry, this functionality is disabled!');
	            return false;
	        } else {
	            return true;
	        }
	    });
	});
	</script> -->

</body>

</html>