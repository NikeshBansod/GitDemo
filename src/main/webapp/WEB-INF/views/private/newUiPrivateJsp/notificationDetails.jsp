<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<%@include file="../common-alert.jsp" %>


<div class="loader"></div>
<section class="block">
	<div class="container">
	<div class="page-title">
                        <a href="<spring:url value="/home#help_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Notifications
                    </div>
	 </br>
	   <div class="row">
              <div class="tab-content">
                   <div role="tabpanel" class="tab-pane active" id="notifications">
                      <div class="notifiCations" id="custNotificatn" style="padding-left: 15px;padding-right: 15px;"> </div>
                   </div> 
               </div>
            <%-- <div class="modal-footer p-xs">
                <a href="<spring:url value="/home" />"><button class="btn btn-primary m-r-12" id="okButton" type="button" data-dismiss="modal">Ok</button></a>
            </div> --%>
        </div>
     </div>
</section>
    
    <script type="text/javascript" src="resources/js/newUiJs/notifications/listNotifications.js"></script>