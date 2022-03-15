<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="taglib.jsp" %>

<%-- <nav class="lk-nav">
	<p class="lk-close">&times;</p>
	<div class="menulist">
		<ul>
			<c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
				<li><a href="<spring:url value="./wEditUser" />"><i
						class="fa fa-user" aria-hidden="true"></i> My Profile </a></li>
				<li><a href="<spring:url value="./wizardDashboard" />"><i
						class="fa fa-tachometer" aria-hidden="true"></i> Dashboard</a></li>
				<li><a
					href="<spring:url value="./wizardSecondaryUserPage" 
/>"><i
						class="fa fa-users" aria-hidden="true"></i> Manage Employees</a></li>
				<li><a href="<spring:url value="./wizardListGstinDetails" 
/>"><i
						class="fa fa-inr" aria-hidden="true"></i> Manage GSTIN</a></li>

				<li><a href="<spring:url value="./wizardGetUserGstinMap" 
/>"><i
						class="fa fa-cog" aria-hidden="true"></i> GSTIN - User Mapping</a></li>
				<li><a href="<spring:url value="./wAspMasters" />"><i
						class="fa fa-file-text-o" aria-hidden="true"></i> Upload Data to
						JioGST</a></li>
				<li><a href="<spring:url value="./wizardDeleteUser" />"><i
						class="fa fa-trash-o" aria-hidden="true"></i> Delete Account</a></li>
				 <li><a href="<spring:url value="./wFooter" />"><i
						class="fa fa-caret-right" aria-hidden="true"></i> Footer</a></li> 
				<li><a href="<spring:url value="/wizardFeedbackHistory"/>"><i class="fa fa-comments" aria-hidden="true"></i> Feedback</a></li>
			</c:if>
			<a href="<spring:url value="/wizardFeedbackHistory"/>">
			<!--  /wAddFeedbackDetails -->
			
			<li><a href="<spring:url value="/wizardChangePassword"/>"><i class="fa fa-key" aria-hidden="true"></i> Change MPIN</a></li>
			<li><a href="<spring:url value="./wlogout" />"><i
					class="fa fa-power-off" aria-hidden="true"></i> Logout</a></li>
		</ul>
	</div>
</nav> --%>
<!-- <a href="#" class="lk-toggle-nav">
	<div class="navToggle hamburger">
		<div class="hamburger-box">
			<div class="hamburger-inner"></div>
		</div>
	</div>
</a> -->
<div class="header-inside">
	<div class="header-inside-cont">
		<a href="<spring:url value="/wHome"/>"><img
			src="./resources/images/wizardImages/insidelogo.jpg"></a>
			
		
		<!-- <a href="/gstn/notificationDetails" class="notificationbell"style= "float:right"><i class="fa fa-bell"></i></a> -->
		
		<div class="inv-mgt-conti main_btnnotification">
			<a class="notificationbell" data-id="notification" href="./wizardnotification">
            	<i class="fa fa-bell" aria-hidden="true"></i>
              	<!-- <span>Notification</span> -->
           </a>
		</div>
		<div class="user-mob">
		<!-- <a href="/gstn/notificationDetails" class="notificationbell  "><i class="fa fa-bell"></i></a> -->
		
		
			<div class="user-name-image">
				<%-- <div class="user-name">${sessionScope.loginUser.userId}
					<div>${sessionScope.loginUser.firmName}</div>
				</div> --%>
				<div class="fa fa-user" id="a"><!-- user-image -->
					
					<!-- <img src="./resources/images/wizardImages/droparrow.png"
						class="droparrow"> -->
				</div>
			</div>
			<div class="user-edit">
				<div class="user-name-mobile">
					<div class="dropdown-user">
						<div class="arrow_box">
							<ul>
							<li><a href="<spring:url value="./wEditUser" />"><i
						class="fa fa-user" aria-hidden="true"></i> My Profile </a></li>
								<li><a href="<spring:url value="/wizardChangePassword" />"><i class="fa fa-key" aria-hidden="true"></i>Change
										MPIN</a></li>
								
					<li><a href="<spring:url value="./wizardDeleteUser" />"><i
						class="fa fa-trash-o" aria-hidden="true"></i> Delete Account</a></li>
						<li><a href="./wlogout"><i
					class="fa fa-power-off" aria-hidden="true"></i>Logout</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div><!-- <a href="/gstn/notificationDetails" class="notificationbell"><i class="fa fa-bell"></i></a> -->
	</div>
</div>
<div class="menu-search">
	<div class="searchbar" style="display: none;">
		<input type="text" name="search" placeholder="Search..">
	</div>
</div>


