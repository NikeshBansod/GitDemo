<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

		<header class="insidehead">
		<!-- 
            <a href="#!" data-activates="slide-out" class="button-collapse">
                <div class="navToggle hamburger">
                    <div class="hamburger-box">
                        <div class="hamburger-inner"></div>
                    </div>
                </div>
            </a> -->
        
         <a href="<spring:url value="/home"/>" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home.main_btn" ><img  src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
			<%-- <a href="#" id="usericon" class="notificationbell" ><i class="fa fa-user"></i></a>
			<a href="<spring:url value="/notificationDetails" />" class="notificationbell" ><i class="fa fa-bell"></i></a> --%>
			
			 <ul class="rightMenu">
				<li><a href="<spring:url value="/notificationDetails" />"><i class="fa fa-bell"></i></a></li>
				<li class="dropdown">
				  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					<i class="fa fa-user"></i><span class="caret" style="display:none;"></span>
				  </a>
				  <ul class="dropdown-menu">
				   <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
					<li><a href="<spring:url value="/editUser" />"><i class="fa fa-user"></i> My Profile</a></li>
					<li><a href="<spring:url value="/changePassword" />"><i class="fa fa-lock"></i> Change MPIN</a></li>
					<li><a class="waves-effect" href="<spring:url value="/deleteUser" />"><i class="fa fa-trash-o"></i> Delete Account</a></li>
					<li role="separator" class="divider"></li>
					<li><a href="<spring:url value="/logout" />"><i class="fa fa-power-off"></i> Logout</a></li>
					 </c:if>
					 
					 <c:if test="${sessionScope.loginUser.userRole eq 'SECONDARY'}">
					<li><a href="<spring:url value="/changePassword" />"><i class="fa fa-lock"></i> Change MPIN</a></li>
					<li role="separator" class="divider"></li>
					<li><a href="<spring:url value="/logout" />"><i class="fa fa-power-off"></i> Logout</a></li>
					 </c:if>
					 
				  </ul>
				</li>
			  </ul>
					   
        </header>
        
        
        <c:if test="${not empty SUCCESS}">
		<script type="text/javascript">
			captureEvent("Login", "Success");
      	</script>
      	</c:if>
      	
        <ul id="slide-out" class="side-nav">
        
        <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
           <%-- <li><a class="waves-effect" href="<spring:url value="/editUser" />"><i class="fa fa-user"></i> My Profile</a></li> 
            <li><a class="waves-effect" href="<spring:url value="/dashboard" />"><i class="fa fa-user"></i> Dashboard</a></li>
            <li><a class="waves-effect" href="<spring:url value="/secondaryUserPage" />"><i class="fa fa-pencil"></i> Manage Employees</a></li>
            <li><a class="waves-effect" href="<spring:url value="/addGstinDetails" />"><i class="fa fa-phone"></i> Manage GSTIN</a></li>
        <li><a class="waves-effect" href="<spring:url value="/getGstinUserMap" />"><i class="fa fa-phone"></i> User - GSTIN Mapping</a></li> 
            <li><a class="waves-effect" href="<spring:url value="/getUserGstinMap" />"><i class="fa fa-phone"></i> GSTIN - User Mapping</a></li>
            <li><a class="waves-effect" href="<spring:url value="/aspMasters" />"><i class="fa fa-pencil"></i> Upload data to JioGST</a></li>
        <li><a class="waves-effect" href="<spring:url value="/confirmInvoice" />"><i class="fa fa-phone"></i> Setup Invoice Details</a></li>
            <li><a class="waves-effect" href="#!"><i class="fa fa-phone"></i> Invoice Management</a></li>
            <li><a class="waves-effect" href="#!"><i class="fa fa-phone"></i> ASP Management</a></li>
            <li><a class="waves-effect" href="<spring:url value="/changePassword" />"><i class="fa fa-lock"></i> Change MPIN</a></li> 
            <li><a class="waves-effect" href="<spring:url value="/deleteUser" />"><i class="fa fa-phone"></i>Delete Account</a></li> --%>
            <li><a class="waves-effect" href="<spring:url value="/getFooter" />"><i class="fa fa-user"></i> Footer</a></li>
            <%--  <li><a class="waves-effect" href="<spring:url value="/addFeedbackDetails" />"><i class="fa fa-user"></i> Feedback</a></li>
              <li><a class="waves-effect" href="<spring:url value="/showFeedbackDetails" />"><i class="fa fa-user"></i> Feedback</a></li> 
             <li><a class="waves-effect" href="<spring:url value="/logout" />"><i class="fa fa-phone"></i>Logout</a></li>   --%>
             
             </c:if>
             <c:if test="${sessionScope.loginUser.userRole eq 'SECONDARY'}">
            <li><a class="waves-effect" href="<spring:url value="/changePassword" />"><i class="fa fa-lock"></i> Change MPIN</a></li>
            <li><a class="waves-effect" href="<spring:url value="/logout" />"><i class="fa fa-phone"></i>Logout</a></li>
             </c:if>
             
        </ul>

        <main>
         <section class="block selectOptions">
                <div class="container">
                   <!--  <ul class="nav nav-tabs nav-justified" role="tablist">
                        <li role="presentation" class="active"><a href="#invoice" aria-controls="invoice" role="tab" data-toggle="tab">Documents</a></li>
                        <li role="presentation"><a href="#ewaybill" aria-controls="ewaybill" role="tab" data-toggle="tab"">E-way Bill</a></li>
                        <li role="presentation"><a href="#master" aria-controls="master" role="tab" data-toggle="tab">Masters</a></li>
                    </ul> -->
                     
                        <div role="tabpanel " class=" tab-content tab-pane active main_btn">
                             <div class="row row-sm">
			                       <div class="col-xs-6">
								           <a class="card panel panel-default"  href="javascript:void(0)" data-id="doc_management" id="document_management">
								           		 <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
							                      <p>Documents</p>
								           </a>
			                      </div>
								  <div class="col-xs-6">
								            <a class="card panel panel-default" href="javascript:void(0)" data-id="eway_management"  id="ewaybill_management" >
								           		 <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_service_catalgue.svg" />"></div>
							                      <p>E-way Bill</p>
								           </a>
								    </div>
								   
								    <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
								      <div class="col-xs-6">
								            <a class="card panel panel-default" href="<spring:url value="/getJioGstLoginPage" />">
								           		 <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
							                      <p>Compliance</p>
								           </a>
								    </div> 
								    </c:if>
								    
								    <div class="col-xs-6">
								            <a class="card panel panel-default" href="javascript:void(0)" data-id="master_management"  id="masterdata_management">
								           		 <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
							                      <p>Masters</p>
								           </a>
								    </div>
								    <div class="col-xs-6">
								            <a class="card panel panel-default"  href="<spring:url value="/dashboard" /> " >
								           		 <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_reports.svg" />"></div>
							                      <p>Dashboard</p>
								           </a>
								    </div>
								    <div class="col-xs-6">
								            <a class="card panel panel-default"  href="<spring:url value="/showFeedbackDetails	" /> " >
								           		 <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg"/>"></div>
							                      <p>Feedback</p>
								           </a>
								    </div>
								   
								 </div>
							</div>
						
                    <div class="tab-content">
                        <div role="tabpanel" class=" tab-pane active doc_management" id="invoice">
									<h4><center><p> Manage Documents</p> </center></h4>
                            <div class="row row-sm">
                                 <!-- <h5><a href="javascript:void(0)" class="backHome"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Back</h5> -->
                                <div class="col-xs-6">
		                            <a class="card panel panel-default"  href="<spring:url value="/generateInvoice" />">
		                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Generate Document</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default"  href="<spring:url value="/getMyInvoices" /> ">
		                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p> Document History </p>
		                            </a>
		                        </div>

                            </div>

 							<div class="row row-sm">
 								<div class="col-xs-6">
		                            <a class="card panel panel-default"  href="<spring:url value="/generatePurchaseEntryInvoice" />">
		                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Purchase Entries</p>
		                            </a>
		                        </div>
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default"  href="<spring:url value="/getMyPurchaseEntryPage" /> ">
		                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p> Purchase Entry History </p>
		                            </a>
		                        </div>
 							</div>  
 								                           
                        </div>
                        <div role="tabpanel" class="tab-pane eway_management" id="ewaybill">
 							<h4><center><p> Manage E-Way Bills</p> </center></h4>
 							<div class="row row-sm">
 							 <!-- <h5><a href="javascript:void(0)" class="backHome"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Back</h5> -->
 								<div class="col-xs-6">
		                            <a class="card panel panel-default"  href="<spring:url value="/generateEwayBills" />">
		                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Generate E-Way Bill</p>
		                            </a>
		                        </div>
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default"  href="<spring:url value="/getGenericEWayBills" /> ">
		                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p> Generic E-Way Bill History </p>
		                            </a>
		                        </div>
 							</div> 
                        </div>
                        <div role="tabpanel" class="tab-pane master_management" id="master">
							<h4><center><p> Manage Masters</p> </center></h4>
                            <div class="row row-sm">
 								<!-- <h5><a href="javascript:void(0)" class="backHome"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Back</h5> -->
                                <div class="col-xs-6">
	                            <a class="card panel panel-default" href="<spring:url value="/customerDetails" />">
	                                <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_customer_management.svg" />"></div>
	                                <p >Customer Details</p>
	                            </a>
	                        </div>
	
							<div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/getProducts" /> ">
	                                <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
	                                <p>Goods Catalogue</p>
	                            </a>
	                        </div>
	                        
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/manageServiceCatalogue" />">
	                                <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_service_catalgue.svg" />"></div>
	                                <p>Services Catalogue</p>
	                            </a>
	                        </div>
	
	                       <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/additionalCharges" />">
	                                <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
	                                <p>Additional Charges</p>
	                            </a>
	                        </div> 
	                         <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
	                        <div class="col-xs-6">
                                   <a class="card panel panel-default"  href="<spring:url value="/secondaryUserPage" />">
                                       <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg"  />"></div>
                                       <p>Employees</p>
                                   </a>
                               </div> 
                               <div class="col-xs-6">
                                   <a class="card panel panel-default"  href="<spring:url value="/addGstinDetails" />">
                                       <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_reports.svg"/>"></div>
                                       <p>GSTIN</p>
                                   </a>
                               </div> 
                               <div class="col-xs-6">
                                   <a class="card panel panel-default"  href="<spring:url value="/getUserGstinMap" />">
                                       <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
                                       <p>GSTIN - User mapping</p>
                                   </a>
                               </div>
	                        </c:if>
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/getFooter" />">
	                                <div class="card-icon-master"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
	                                <p>Footer</p>
	                            </a>
	                        </div> 

                            </div>

                        </div>
                    </div>
                    </div>
            </section>
        </main>
      </div>
<script type="text/javascript" src="resources/js/common_rhs_menu.js"></script>

