<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp" %>

<tilesx:useAttribute name="current" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

<title><tiles:getAsString name="title" /></title>

  <link href="<spring:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />" rel="stylesheet">
  <link href="<spring:url value="/resources/css/wizardCss/custom.css" />" rel="stylesheet">
  <link rel="stylesheet" href="<spring:url value="/resources/accordion/smk-accordion.css" />" type="text/css"/>   

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<spring:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js" />"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<spring:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/bootbox.min.js" />"></script>
  <script src="<spring:url value="/resources/js/gstncommonvalidation.js"/>"></script>
  <script type="text/javascript" src="<spring:url value="/resources/accordion/smk-accordion.js" />"></script>
</head> 
<body>


      <tiles:insertAttribute name="body" />


</body>

</html>