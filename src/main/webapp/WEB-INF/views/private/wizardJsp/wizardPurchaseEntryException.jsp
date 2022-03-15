<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 

<section class="insidepages">
	<div class="inside-cont">
		<div id="" >
			<div class="breadcrumbs">
				 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
		         	<a href="<spring:url value="/wHome#doc_management"/>">Home</a> <span>&raquo;</span> Generate Purchase Entry
		 		 </header>
		 	</div>
			
			<div class="account-det">
				<div class="card">
					<div style="color:red;font-family:Verdana;font-size:16px; text-align:center;">
		              	<c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
		        	 		<c:if test="${isGstinPresent == false }">
		        	 			<b>Please add atleast one GSTIN in order to proceed to 'GENERATE PURCHASE ENTRY'.</b><br/>
		        	 			<a href="<spring:url value="/wizardAddGstinDetails" />">Click on below link to add GSTIN.</a><br/>
		        	 		</c:if>
		        	 				        	 		
		        	 		<c:if test="${sessionScope.loginUser.termsConditionsFlag ne 'Y'}">
		        	 			<b>Please accept terms and Conditions to proceed to  'GENERATE PURCHASE ENTRY'.</b><br/>
		        	 			<a href="<spring:url value="/wEditUser" />">Click on below link to accept terms and conditions.</a><br/>
		        	 		</c:if>
		        	 		
		        	 	</c:if>
		        	 	<c:if test="${sessionScope.loginUser.userRole eq 'SECONDARY'}">
		        	 		<c:if test="${isGstinPresent == false }">
		        	 			<b>Kindly ask your concerned primary member to map GSTIN to your account in order to proceed to 'GENERATE PURCHASE ENTRY'.</b><br/>
		        	 		</c:if>		        	 		
		        	 		
		        	 		<c:if test="${sessionScope.loginUser.termsConditionsFlag ne 'Y'}">
		        	 			<b>Kindly ask your concerned primary member to accept terms and Conditions to proceed to  'GENERATE PURCHASE ENTRY'.</b><br/>
		        	 		</c:if>
		        	 		<a href="<spring:url value="/wHome" />">Click here to go to home page.</a><br/>
		        	 	</c:if>
					</div>	
				</div>           
			</div>			
		</div>
	</div>
</section>


