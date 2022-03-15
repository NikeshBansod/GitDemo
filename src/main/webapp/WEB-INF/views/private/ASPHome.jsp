<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
	 	<c:if test="${apiResponse == 'SUCCESS'}"> 
             <script type="text/javascript">
             bootbox.alert('${successMsg}');
			</script>
           </c:if> 
           <c:if test="${apiResponse == 'error'}">
             <script type="text/javascript">
             bootbox.alert('Upload failed, Please try again after sometime.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'invalid'}">
             <script type="text/javascript">
             bootbox.alert('Can not update at this time please try after some time.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'emptyList'}">
             <script type="text/javascript">
             bootbox.alert('NO Invoice Generated for Selected GSTIN Within Selected Time Period');
			</script>
             </c:if> 
	
	<header class="insidehead">
           <a href="<spring:url value="home" />" class="btn-back"><i class="fa fa-angle-left"></i> </a>
           <a class="logoText" href="home.main_btn"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
        </header>
        <main>
         <section class="block selectOptions">
                <div class="container">
                  
                    <div class="tab-content">
                       
                        <div role="tabpanel" class="tab-pane" id="master">
<!-- 
                            <div class="row row-sm">
								<span><center><h4><b>JioGST data Upload Management</b></h4></center></span>
                                <div class="col-xs-6">
	                            <a class="card panel panel-default" href="<spring:url value="/getJioGstLoginPage" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_upload_asp.svg" />"></div>
	                                <p >Upload </br> (Manual)</p>
	                            </a>
	                        </div>
	                        
	                          <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="getUploadSetting" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
	                                <p>Settings</p>
	                            </a>
	                        </div>  
	
							 <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="getHistory" /> ">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
	                                <p>History</p>
	                            </a>
	                        </div> -->
	                        <%--
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/manageServiceCatalogue" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_service_catalgue.svg" />"></div>
	                                <p>Download</p>
	                            </a>
	                        </div>
	
	                       <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/additionalCharges" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
	                                <p>Setting</p>
	                            </a>
	                        </div> 
	                        
	                        <div class="col-xs-6">
	                            <a class="card panel panel-default"  href="<spring:url value="/getUploadPage" />">
	                                <div class="card-icon"><img src="<spring:url value="/resources/images/home/ic_po_management.svg" />"></div>
	                                <p>Upload Invoices</p>
	                            </a>
	                        </div> --%>

                            </div>

                        </div>
                    </div>
                </div>
            </section>
        </main>
        
<script type="text/javascript">
$(document).ready(function(){
	
$("#master").show();
});

</script>