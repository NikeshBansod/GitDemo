<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

	<div class="modal-dialog modal-lg" id="notificationDiv">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close_btn" data-dismiss="modal">X</button>
                <h3 class="m-n">Notification</h3>
            </div>
            <div class="modal-body">
              <div class="tab-content">
                   <div role="tabpanel" class="tab-pane active" id="notifications">
                      <div class="notifiCations" id="custNotificatn"> </div>
                   </div> 
               </div>
             </div>
            <div class="modal-footer p-xs">
                <button class="btn btn-primary m-r-12" id="okButton" type="button" data-dismiss="modal">Ok</button>
            </div>
        </div>
    </div>
        
<script type="text/javascript" src="resources/js/notifications/listNotifications.js"></script>
