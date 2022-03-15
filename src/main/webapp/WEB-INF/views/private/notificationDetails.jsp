<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

		<header class="insidehead">
           <a href="<spring:url value="/home#invoice" />" class="btn-back"><i class="fa fa-angle-left"></i></a>
           
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
            
        </header>
        
        <main>
         <section class="block selectOptions">
                <div class="container">
                    <ul class="nav nav-tabs nav-justified" role="tablist">
                         <li role="presentation" class="active"><a href="#notifications" id="showNotification" aria-controls="notifications" role="tab" data-toggle="tab" >Notifications</a></li> 
                    </ul>

                    <div class="tab-content">
                         <div role="tabpanel" class="tab-pane active" id="notifications">

                            <div  class="notifiCations" id="custNotificatn">
		                        
		                    </div>

                        </div> 
                        
                        </div>
                    </div>

            </section>
        </main>
        
<script type="text/javascript" src="resources/js/notifications/listNotifications.js"></script>
