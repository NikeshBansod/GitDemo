<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<div class="loader"></div>
<!-- Body content/sections Starts here -->
<section class="block inside-thumbbg box main_btn">
	<c:if test="${not empty SUCCESS}">
		<script type="text/javascript">
			captureEvent("Login", "Success");
	   	</script>
	</c:if>
    <div class="container">
                    <div class="row mb">
                        <h1 class="box-section-title">Transactions</h1>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                <a href="javascript:void(0);" data-id="doc_management">
                                    <h1 class="app-title">
                                        Sales - Purchase
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                 <a href="<spring:url value="/showrevisedandreturndetails" />">
                                    <h1 class="app-title">
                                        Revisions
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                <a href="javascript:void(0);" data-id="eway_management" class="box">
                                    <h1 class="app-title">
                                        E-way Bill
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important;background-color:#C0C0C0;">
                             <a href="<spring:url value="" />"><!-- / -->
                                    <h1 class="app-title">
                                       <span>Payments & Receipts</span> 
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                <a href="javascript:void(0);" data-id="inventory_management" class="box">
                                    <h1 class="app-title">
                                        Inventory
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                    </div>
                    <div class="row mb">
                        <h1 class="box-section-title">Setup</h1>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                <a href="javascript:void(0);" data-id="master_management">
                                    <h1 class="app-title">
                                        Masters
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                 <a href="javascript:void(0);" data-id="account_management">
                                    <h1 class="app-title">
                                       Account
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                         <div class="col-md-4 col-sm-4 ">
			                <div class="app-box" style="max-height:57px !important">
			                    <a href="<spring:url value="/getInvSeqSettings" /> " data-id="inv_seq_settings">
			                        <h1 class="app-title">
			                            <span>Settings</span>
			                        </h1>
			                    </a>                         
			                </div>
			            </div>
                    </div> 
                       <div class="row mb">
                        <h1 class="box-section-title">Others</h1>
                        
                         <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                               <a href="<spring:url value="./getJioGstLoginPage" />">
                                    <h1 class="app-title">
                                       File GST Returns
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        </c:if>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                 <a href="<spring:url value="./dashboard" />">
                                    <h1 class="app-title">
                                      Dashboard
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="app-box" style="max-height:57px !important">
                                <a href="javascript:void(0);" data-id="help_management">
                                    <h1 class="app-title">
                                        Help
                                    </h1>
                                </a>                         
                            </div>
                        </div>
                </div>
                </div>
</section>

<section class="block inside-thumbbg doc_management">
    <div class="container">
       <!--  <button type="button" id="" class="btn btn-outline-primary box2 back"><i class="fa fa-home" aria-hidden="true"></i></button> -->
       <!--  <div class="submenu-title box-section-title"> &nbsp;&nbsp;Document Management</div> -->
    </div>
    <div class="container ">
        <div class="row mb ">
         <h1 class="box-section-title">Sales</h1>
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important">
                   <%--  <a href="<spring:url value="/getMyInvoices" />"> --%>
                    <a href="<spring:url value="/getInvoices" />">
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Tax Invoice</span>
                         </h1>
                    </a>
                </div>
            </div>
            
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important">
                    <a href="<spring:url value="/getBOS" />">
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Bill Of Supply</span>  
                         </h1>
                    </a>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important">
                    <a href="<spring:url value="/getRCInvoice" />">
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Reverse Charge Tax Invoice</span>  
                         </h1>
                    </a>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important">
                    <a href="<spring:url value="/getEComInvoice" />">
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>e-Commerce Tax Invoice </span> 
                         </h1>
                    </a>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important">
                    <a href="<spring:url value="/getEComBOS" />">
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>E-Commerce Bill Of Supply</span> 
                         </h1>
                    </a>
                </div>
            </div>

           
            
            <div class="col-md-4 col-sm-4 col-xs-6" >
                <div class="app-box" style="max-height:100px !important ;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getSalesEntry -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Sales Entry</span> 
                         </h1>
                    </a>
                </div>
            </div>
            
            
           <%--   <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getDC -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>DC</span> 
                         </h1>
                    </a>
                </div>
            </div>
            
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getAdvances -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Advances</span>
                         </h1>
                    </a>
                </div>
            </div> --%>
            
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getExports -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Exports</span>
                         </h1>
                    </a>
                </div>
            </div>
            
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getISDInvoice -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>ISD Invoice</span> 
                         </h1>
                    </a>
                </div>
            </div>
            
            

        </div>
        <div class="row mb ">
         <h1 class="box-section-title">Purchase</h1>
          <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important">
                    <a href="<spring:url value="/getMyPurchaseEntryPage" />">
                        <!-- <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                            <span>Purchase</span>
                        </h1>
                    </a>
                </div>
            </div>
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getPOWO -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>PO / WO</span>
                         </h1>
                    </a>
                </div>
            </div>
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" />"><!-- /getRCSelfInvoice -->
                       <!--  <div class="app-icon">
                            <i class="fa fa-file-o" aria-hidden="true"></i>
                        </div> -->
                        <h1 class="app-title">
                             <span>Reverse Charge Self Invoice</span> 
                         </h1>
                    </a>
                </div>
            </div>
         </div>
    </div>
</section>

<section class="block inside-thumbbg eway_management">
    <div class="container">        
       <!--  <button type="button" id="" class="btn btn-outline-primary box2 back"><i class="fa fa-home" aria-hidden="true"></i></button> -->
        <div class="submenu-title box-section-title"> &nbsp;&nbsp;E-Way Bill Management</div>
    </div>
    <div class="container ">
        <div class="row mb ">
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/getMyInvoices" />">
                        <h1 class="app-title">
                             <span>E-Way Bill Through Invoice</span> 
                         </h1>
                    </a>
                </div>
            </div>

            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/getGenericEWayBills" />">
                        <h1 class="app-title">
                            <span>Generic E-Way Bill</span> 
                        </h1>
                    </a>
                </div>
            </div>

        </div>
    </div>
</section>

<section class="block inside-thumbbg master_management">
    <div class="container">
<!--         <button type="button" id="" class="btn btn-outline-primary box2 back"><i class="fa fa-home" aria-hidden="true"></i></button>
 -->        <div class="submenu-title box-section-title">&nbsp;&nbsp;Master Management</div>
    </div>
    <div class="container ">
        <div class="row mb ">
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/customerDetails" />">
                        <h1 class="app-title">
                            <span>Party Master</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/getProducts" /> ">
                        <h1 class="app-title">
                            <span>Goods Master</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/manageServiceCatalogue" />">
                        <h1 class="app-title">
                            <span>Service Master</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/additionalCharges" /> ">
                        <h1 class="app-title">
                            <span>Additional Charge</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" /> ">
                        <h1 class="app-title">
                            <span>Cash & Bank</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            
            
            <c:if test="${loggedInThrough == 'WIZARD'}">	
				<div class="col-md-4 col-sm-4 col-xs-6">
	                <div class="app-box">
	                    <a href="<spring:url value="/uploadmasterexcel" /> ">
	                        <h1 class="app-title">
	                            <span>Upload Master Excel</span>
	                        </h1>
	                    </a>                         
	                </div>
            	</div>
			</c:if>	             
        </div>
    </div>
</section>
<section class="block inside-thumbbg account_management">
    <div class="container">
       <!--  <button type="button" id="" class="btn btn-outline-primary box2 back"><i class="fa fa-home" aria-hidden="true"></i></button> -->
        <div class="submenu-title box-section-title">&nbsp;&nbsp;Account Management</div>
    </div>
    <div class="container ">
        <div class="row mb ">
            
             <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/secondaryUserPage" /> ">
                        <h1 class="app-title">
                            <span>Employee Master</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            </c:if>
             <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/addGstinDetails" /> ">
                        <h1 class="app-title">
                            <span>GSTIN Master</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            </c:if>
             <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/getUserGstinMap" /> ">
                        <h1 class="app-title">
                            <span>GSTIN User Mapping</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            </c:if>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/getFooter" /> ">
                        <h1 class="app-title">
                            <span>Footer</span>
                        </h1>
                    </a>                         
                </div>
            </div>
             <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="./editUser" /> ">
                        <h1 class="app-title">
                            <span>My Profile</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            </c:if>
           
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="./changePassword" /> ">
                        <h1 class="app-title">
                            <span>Change MPIN</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="./deleteUser" /> ">
                        <h1 class="app-title">
                            <span>Delete Account</span>
                        </h1>
                    </a>                         
                </div>
            </div>
        </div>
    </div>
</section>
<section class="block inside-thumbbg help_management">
    <div class="container">
       <!--  <button type="button" id="" class="btn btn-outline-primary box2 back"><i class="fa fa-home" aria-hidden="true"></i></button> -->
        <div class="submenu-title box-section-title">&nbsp;&nbsp;Help Management</div>
    </div>
    <div class="container ">
        <div class="row mb ">
        
        
        
        
        
        
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" /> ">
                        <h1 class="app-title">
                            <span>Chatbot</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" /> ">
                        <h1 class="app-title">
                            <span>FAQ</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" /> ">
                        <h1 class="app-title">
                            <span>Video tutorials</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" /> ">
                        <h1 class="app-title">
                            <span>User Manual</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" >
                    <a href="<spring:url value="/notificationDetails" /> ">
                        <h1 class="app-title">
                            <span>Notifications</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/showFeedbackDetails" /> ">
                        <h1 class="app-title">
                            <span>Contact us</span>
                        </h1>
                    </a>                         
                </div>
            </div>
            
        </div>
    </div>
</section>

<section class="block inside-thumbbg inventory_management">
    <div class="container">
        <!-- <button type="button" id="" class="btn btn-outline-primary box2 back" style="padding-top: 45px"><i class="fa fa-home" aria-hidden="true"></i></button> -->
        <!-- <div class="submenu-title box-section-title">&nbsp;&nbsp;Inventory Management</div> -->
    </div>
    <div class="container ">
        <div class="row mb ">
          <h1 class="box-section-title">Entries</h1>
            <%-- <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/increaseinventory" />">
                        <h1 class="app-title">
                            <span>Increase Inventory</span> 
                        </h1>
                    </a>                         
                </div>
            </div> --%>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/increaseinventoryhistory" />">
                        <h1 class="app-title">
                            <span>Increase Inventory</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
           
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/decreaseinventoryhistory" />">
                        <h1 class="app-title">
                            <span>Decrease Inventory</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
          
           <%--  <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/decreaseinventory" /> ">
                        <h1 class="app-title">
                            <span>Decrease Inventory</span> 
                        </h1>
                    </a>                         
                </div>
            </div> --%>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/movementbetweenstores" />">
                        <h1 class="app-title">
                            <span>Movement Between stores</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/getopeningstock" /> ">
                        <h1 class="app-title">
                            <span>Opening stock</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box" style="max-height:100px !important ;background-color:#C0C0C0;">
                    <a href="<spring:url value="#" /> ">
                        <h1 class="app-title">
                            <span>Stock Journal</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            </div>
            <div class="row mb">
                 <h1 class="box-section-title">Reports</h1>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/stocksummaryreport" />">
                        <h1 class="app-title">
                            <span>Stock Status</span>  
                        </h1>
                    </a>                         
                </div>
            </div>
            
             <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/unmoveditems" />">
                        <h1 class="app-title">
                            <span>Unmoved Items</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
            <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/negetivestocks" />">
                        <h1 class="app-title">
                            <span>Out Of Stock </span> 
                        </h1>
                    </a>                         
                </div>
            </div>
          <div class="col-md-4 col-sm-4 col-xs-6">
                <div class="app-box">
                    <a href="<spring:url value="/balancecheckreport" />">
                        <h1 class="app-title">
                            <span>Balance Check</span> 
                        </h1>
                    </a>                         
                </div>
            </div>
            
                      
        </div>
    </div>
   <input type="hidden" id="user-profile-data" name="user-profile" value="${pushNotificationProfile}"/>
   <input type="hidden" id="notification-status" name="notificationStatus" value="${notificationStatus}"/>
</section>
<script type="text/javascript" src="resources/js/common_rhs_menu.js"></script>
<script type="text/javascript">
$(document).ready(function (e) {
    $(".loader").fadeOut("slow");
   
   
    
    $('.main_btn a').on('click', function(){
		$('.main_btn').hide();
		var id = $(this).attr('data-id');
		$('.'+id).show();
        $(".box2").show();
	});

	/* $('.back').on('click', function(){
		$('.doc_management, .eway_management, .master_management ,.inventory_management').hide();
		$('.main_btn').show();
        $(".box2").hide();			
	});	 */
});
 </script> 
	
 <c:if test="${notificationStatus eq 'y'}"> 
 	<script>
	var data1 = {
			    "CUSTOMER_TYPE":'${pushNotificationProfile.customerType}',
			    "EWAY_BILL":'${pushNotificationProfile.ewayBill}',
			    "VERTICAL_TYPE":'${pushNotificationProfile.verticalType}'
	};
	var stateData= {"STATE":'${pushNotificationProfile.state}'}; 
	if(data1!=""){ 
		var data2=JSON.stringify(data1);
		var stateData2=JSON.stringify(stateData);
		android.fetchUserProfile(data2,stateData2);
	}

	</script>
 </c:if>
