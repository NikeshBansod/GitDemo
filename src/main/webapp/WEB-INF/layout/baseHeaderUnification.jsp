<%@ include file="./../layout/taglib.jsp" %> 
<header>
    <div class="container">
          
         <a href="<spring:url value="/homeUnifi"/>"><button type="button" id="" class="btn btn-outline-primary box2 "><i class="fa fa-home" aria-hidden="true"></i></button></a>
    
        <div class="inside-logo">
        	<a href="<spring:url value="/homeUnifi"/>">
            	<img src="./resources/images/newUiPics/inside-logo.png" alt="JioGst BillLite">
            </a>
        </div>
        <div class="user-drop">
            <div class="drop-block">
                <div class="dropdown">
                  <button class="btn drop-logout dropdown-toggle" type="button" data-toggle="dropdown">${sessionScope.loginUser.firmName}
                  <span class="caret"></span></button>
                  <ul class="dropdown-menu">
                    <li><a href="<spring:url value="./edituserprofileunification"/>"><i class="fa fa-user" aria-hidden="true"></i> My Profile</a></li>
                    <li><a href="<spring:url value="./changePassword"/>"><i class="fa fa-key" aria-hidden="true"></i> Change MPIN</a></li>
                    <li><a href="<spring:url value="./deleteUser"/>"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete Account</a></li>
                   <%--  <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
                    	 <li><a href="<spring:url value="./getFooter" />"><i class="fa fa-caret-right" aria-hidden="true"></i> Footer</a></li>
                    </c:if>  --%>
                    <li><a href="<spring:url value="./logout"/>"><i class="fa fa-power-off" aria-hidden="true"></i> Logout</a></li>
                  </ul>
                </div>
            </div>   
            <!-- <div class="noti">
                <a href="./notificationDetails"/><i class="fa fa-bell-o" aria-hidden="true"></i></a>
            </div>  -->                    
        </div>
    </div>
</header>  