<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>

<style>

        
 .custom-bullet li {
    display: block;
}

 .custom-bullet li span{
    float:right;
}

.custom-bullet li:before{
    /*Using a Bootstrap glyphicon as the bullet point*/
    content: "\e080";
    font-family: 'FontAwesome-webfont';
    font-size: 9px;
    float: left;
    margin-top: 4px;
    margin-left: -17px;
}

</style>
<header class="insidehead">
    <a href="<spring:url value="/home" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Notifications<span></a>
</header>
 
<div class="container">
  <div class="box-border">
  
   	  <div class="cust-wrap">
		<div id="toggle">	
			<ul class="custom-bullet">
			
			</ul>
								
		</div>
      </div>
  </div>
</div>

<script type="text/javascript" src="resources/js/notifications/listNotifications.js"></script>
