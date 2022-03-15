<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <ul class="rightMenu">
	<li><a href="<spring:url value="/notificationDetails" />"><i class="fa fa-bell"></i></a></li>
	<li class="dropdown">
		  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
			<i class="fa fa-user"></i><span class="caret"></span>
		  </a>
	  <ul class="dropdown-menu">
			<li><a href="<spring:url value="/editUser" />"><i class="fa fa-user"></i> My Profile</a></li>
			<li><a href="<spring:url value="/changePassword" />"><i class="fa fa-lock"></i> Change MPIN</a></li>
			<li><a class="waves-effect" href="<spring:url value="/deleteUser" />"><i class="fa fa-trash-o"></i> Delete Account</a></li>
			<li role="separator" class="divider"></li>
			<li><a href="<spring:url value="/logout" />"><i class="fa fa-power-off"></i> Logout</a></li>
	  </ul>
	</li>
 </ul>
<script type="text/javascript" src="resources/js/common_rhs_menu.js"></script>
