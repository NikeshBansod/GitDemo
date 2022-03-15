<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %>

<section class="insidepages">
      <div class="inside-cont-invoice">
        <div class="container">
        	<div class="row">
        		<div class="col-md-12">
        			<div class="inv-mgt-cont main_btn">
			           <!-- <a class="blue_tab_bttn " data-id="notification" href="javascript:void(0)">
			            	<i class="fa fa-bell" aria-hidden="true"></i>
			              	<span>Notification</span>
			           </a> -->
			           <a href="javascript:void(0)" data-id="doc_management" class="blue_tab_bttn">
			           		<i class="fa fa-file-text" aria-hidden="true"></i>
			              	<span>Document Management</span>
			           </a>
			           <a href="javascript:void(0)" data-id="eway_management" class="blue_tab_bttn eway_management">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>E-Way Bill</span>
			           </a>
			           <a href="javascript:void(0)" data-id="mas_management" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Masters Management</span>
			          	 </a>
			          	 <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
			          	   <a href="<spring:url value="./wGetJioGstLoginPage" />"  class="blue_tab_bttn">
			           		<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Compliance </span>
			          	 </a> 
			          	 </c:if>
			          	 <a href="<spring:url value="./wizardDashboard" />"  class="blue_tab_bttn">
			           		<i class="fa fa-tachometer" aria-hidden="true"></i>
			              	<span>Dashboard </span>
			          	 </a>
			          	 <a href="<spring:url value="./wizardFeedbackHistory" /> "  class="blue_tab_bttn">
			           		<i class="fa fa-comments" aria-hidden="true"></i>
			              	<span> Feedback </span>
			          	 </a>
			          	  
			        </div>
			        
			        <div class="inv-mgt-contif notification">
			          <h1><a href="javascript:void(0)" class="backHomenotification"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Notification</h1>
			          <!-- 	<div  class="notifiCations" id="custNotificatn">
	                        <div class="notifiCard card">
		                        <a href="#!">
		                            <p>&nbsp;<br>
										<strong>What you can do?</strong><br>
										• Add products, services, customers, additional charges – using their respective master management features<br>
										• Generate invoices (B2B, B2CS, B2CL, Reverse charge, Ecommerce) Credit & Debit notes, Bill of Supply<br>
										• Auto-upload your monthly sales data (invoices) to JioGST for GST return filing<br>
										&nbsp;<br>
										<strong>Points to note:</strong><br>
										• Make sure to add at least 1 GSTIN, 1 Product/Service, 1 Customer - before you start generating Invoices<br>
										• To create a Credit/Debit note for any invoice, find your invoice in the ‘Document history’, click on the same, scroll down to the bottom of your invoice, click on the button titled ‘View/Create Credit/Debit note’<br>
										&nbsp;<br>
										<strong>Currently available features:</strong><br>
										• Add GSTINs &amp; add stores/locations under them<br>
										• Add employees &amp; allocate them to GSTINs/Stores/Locations<br>
										• Track invoice history (Document History)<br>
										• Email invoices (pdf) to your customer<br>
										• Upload your business logo<br>
										&nbsp;<br>
										<strong>Upcoming features:</strong><br>
										• Print Invoice<br>
										• Share invoice via its tiny-URL<br>
										• Auto-upload your monthly Credit/Debit notes data to JioGST system.<br>
										• Generate E-Way Bill<br>
										• Purchase entries
									</p>
		                        </a>
		                    </div>
	                    </div> -->
			        </div>
			        
			        <div class="inv-mgt-cont doc_management">
			          <h1><a href="javascript:void(0)" class="backHome"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Document Management</h1>
			            <%-- <a class="blue_tab_bttn "  href="<spring:url value="/wGenerateInvoice" />">
			            	<i class="fa fa-inr" aria-hidden="true"></i>
			              	<span>Generate Document</span>
			            </a> --%>
			            <a class="blue_tab_bttn "  href="<spring:url value="/getWizardMyInvoices" />">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Create Document</span>
			            </a>
			            <%-- <a class="blue_tab_bttn "  href="<spring:url value="/wgeneratepurchaseentryinvoice" />">
			            	<i class="fa fa-inr" aria-hidden="true"></i>
			              	<span>Purchase Entries</span>
			            </a> --%>
			            <a class="blue_tab_bttn "  href="<spring:url value="/wgetmypurchaseentrypage" />">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Purchase Entry </span>
			            </a>			            
			        </div>
			        
			        <!--  <div class="inv-mgt-cont eway_management">
			          <h1><a href="javascript:void(0)" class="backHome"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> E way Bill Management</h1>			            			            
			        </div> -->
			        
			        <div class="inv-mgt-cont mas_management">
			          <h1><a href="javascript:void(0)" class="backHome"><i class="fa fa-arrow-left" aria-hidden="true"></i></a> Masters Management</h1>
			          	<a class="blue_tab_bttn "  href="<spring:url value="/wizardCustomerDetails" />">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Manage Customer Details</span>
			            </a>
			            <a class="blue_tab_bttn "  href="<spring:url value="/wizardGetProducts" /> ">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Manage Goods Catalogue</span>
			            </a>
			            <a class="blue_tab_bttn "  href="<spring:url value="/wizardManageServiceCatalogue" />">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Manage Service Catalogue</span>
			            </a>
			            <a class="blue_tab_bttn "  href="<spring:url value="/wizardAdditionalCharges" />">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Additional Charges Master</span>
			            </a>
			            
			            <a class="blue_tab_bttn "  href="<spring:url value="/uploadmasterexcel" />">
			            	<i class="fa fa-file-text-o" aria-hidden="true"></i>
			              	<span>Upload Master Excel</span>
			            </a>
			            <br>
			            <c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
			            <a class="blue_tab_bttn "  href="<spring:url value="./wizardListGstinDetails" />">
			            	<i class="fa fa-inr" aria-hidden="true"></i>
			              	<span>Manage GSTIN</span>
			            </a>
			            <a class="blue_tab_bttn "  href="<spring:url value="./wizardSecondaryUserPage" />">
			            	<i class="fa fa-users" aria-hidden="true"></i>
			              	<span>Manage Employee</span>
			            </a>
			             <a class="blue_tab_bttn "  href="<spring:url value="./wizardGetUserGstinMap" />">
			            	<i class="fa fa-cog" aria-hidden="true"></i>
			              	<span>GSTIN - User Mapping</span>
			            </a>
			            </c:if>
			            
			            <a class="blue_tab_bttn "  href="<spring:url value="./wFooter" />">
			            	<i class="fa fa-caret-right" aria-hidden="true"></i>
			              	<span>Footer</span>
			            </a> 
			           
			            
			        </div>
			        
			      
			         
        		</div>
        	</div>
        </div>
        
        
      </div>
</section>
<!-- <script type="text/javascript" src="resources/js/notifications/listNotifications.js"></script> -->
<script>
	$(function(){
		$('.main_btn a').on('click', function(){
			$('.main_btn').hide();
			var id = $(this).attr('data-id');
			$('.'+id).show();
		});
		$('a.backHome').on('click', function(){
			$('.inv-mgt-cont').hide();
			$('.main_btn').show();
			
		});
		
		$('.main_btnnotification a').on('click', function(){
			
			var id = $(this).attr('data-id');
			$('.'+id).show();
			$('.inv-mgt-cont').hide();
		});
		$('a.backHomenotification').on('click', function(){
			$('.inv-mgt-contif').hide();
			$('.main_btn').show();
			$('.main_btnnotification').show();
			
			
		});
		
		
		
	});

    $(document).ready(function () {
    	 $("#feedbackanddashboard").hide();
    	
        if(window.location.href.indexOf("doc_management") > -1) 
        {            
			$('.main_btn').hide();
            $('.doc_management').show();
        }
        
        if(window.location.href.indexOf("mas_management") > -1) 
        {
        	$('.main_btn').hide();
            $('.mas_management').show();
        }
       
        if(window.location.href.indexOf("dashboard_feedback") > -1) 
        {
        	$('.main_btn').hide();
            $('.dashboard_feedback').show();
        }
        
       
        $('.eway_management').on('click', function(){
        	 
        	bootbox.prompt({
        	    title: "Select option",
        	    inputType: 'select',
        	    inputOptions: [
					{
					    text: 'Select',
					    value: '',
					},
        	        {
        	            text: 'Generate E-Way Bill Through Invoice',
        	            value: '1',
        	        },
        	        {
        	            text: 'Generic E-Way Bill',
        	            value: '2',
        	        }
        	    ],
        	    callback: function (result) {
        	    	if(result == ''){
        	        	window.location.href = "./wHome";
        	        }if(result == '1'){
        	        	window.location.href = "./getWizardMyInvoices";
        	        } else if(result == '2'){
        	        	window.location.href = "./wizardGetGenericEWayBills";
        	        } else {
        	        	window.location.href = "./wHome";
        	        }
        	    }
        	});
         
        	});
        
    });
</script>