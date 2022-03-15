<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="../private/common-alert.jsp" %>

		<header class="insidehead">
            <a href="#!" data-activates="slide-out" class="button-collapse">
                <div class="navToggle hamburger hamburger--spin">
                    <div class="hamburger-box">
                        <div class="hamburger-inner"></div>
                    </div>
                </div>
            </a>
            
            <a class="logoText" href="javascript:void(0)"><img src="<spring:url value="/resources/images/home/gst_logo.svg" />" class="img-responsive"></a>
           
        </header>
        <ul id="slide-out" class="side-nav">
        
        	 <li><a class="waves-effect" href="<spring:url value="/idt/idtlogout" />"><i class="fa fa-phone"></i>Logout</a></li>
        </ul>

        <main>
<br/>
         

            <section class="block selectOptions">
                <div class="container">
                  <ul class="nav nav-tabs nav-justified" role="tablist">
                        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Notifications</a></li>
                        <li role="presentation"><a href="#masters" aria-controls="masters" role="tab" data-toggle="tab">Masters Management</a></li>
                  </ul>
                  <div class="tab-content">
                      <div role="tabpanel" class="tab-pane active" id="home">
	                      <div class="row row-sm">
	                        
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/idt/inotifications" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
	                                <p>View Notifications </p>
	                            </a>
	                        </div>
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/idt/iAddnotifications" /> ">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
	                                <p> Add Notification </p>
	                            </a>
	                        </div>
	                       
	                    </div>
                      
                      </div>
                      
                      <div role="tabpanel" class="tab-pane" id="masters">

                        <div class="row row-sm">
	                        
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/idt/sacCodeDetails" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
	                                <p>SAC Code </p>
	                            </a>
	                        </div>
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/idt/hsnDetails" /> ">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
	                                <p> HSN Code </p>
	                            </a>
	                        </div>
	                       <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/idt/listFeedbackDetails" /> ">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_reports.svg" />"></div>
	                                <p> Feedback Details </p>
	                            </a>
	                        </div>
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/idt/iDashboard"/> ">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_reports.svg" />"></div>
	                                <p> Dashboard </p>
	                            </a>
	                        </div>
	                    </div>

                      </div>
                     
                  </div>


		
            </section>
 

        </main>


