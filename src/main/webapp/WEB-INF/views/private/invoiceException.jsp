<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

 <header class="insidehead">
            <a href="#" onclick="return 'home';" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home">Generate Invoice</a>
 </header>
 
<main>
    <section class="block generateInvoice">
        <div class="container">
        	<div class="text-center">
        		<div style="color:red;font-family:Verdana;font-size:16px">
	        	 	<c:if test="${sessionScope.loginUser.userRole eq 'PRIMARY'}">
	        	 		<c:if test="${isGstinPresent == false }">
	        	 			<b>Please add atleast one GSTIN in order to proceed to 'GENERATE INVOICE'.</b><br/>
	        	 			<a href="<spring:url value="/addGstinDetails" />">Click on below link to add GSTIN.</a><br/>
	        	 		</c:if>
	        	 		
	        	 		
	        	 		<c:if test="${sessionScope.loginUser.termsConditionsFlag ne 'Y'}">
	        	 			<b>Please accept terms and Conditions to proceed to  'GENERATE INVOICE'.</b><br/>
	        	 			<a href="<spring:url value="/editUser" />">Click on below link to accept terms and conditions.</a><br/>
	        	 		</c:if>
	        	 		
	        	 	</c:if>
	        	 	
	        	 	<c:if test="${sessionScope.loginUser.userRole eq 'SECONDARY'}">
	        	 		<c:if test="${isGstinPresent == false }">
	        	 			<b>Kindly ask your concerned primary member to map GSTIN to your account in order to proceed to 'GENERATE INVOICE'.</b><br/>
	        	 		</c:if>
	        	 		
	        	 		
	        	 		<c:if test="${sessionScope.loginUser.termsConditionsFlag ne 'Y'}">
	        	 			<b>Kindly ask your concerned primary member to accept terms and Conditions to proceed to  'GENERATE INVOICE'.</b><br/>
	        	 		</c:if>
	        	 		<a href="<spring:url value="/home" />">Click here to go to home page.</a><br/>
	        	 	</c:if>
        	 	</div>
        	</div>
        
        </div>
    </section>
</main>