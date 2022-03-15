<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/organization/footer.js"/>"></script>
<section class="insidepages">
	<div class="inside-cont">
		<div class="breadcrumbs"><a href="./wHome">Home</a> <span>&raquo;</span> Footer Form</div>
		<form action="./wAddFooter" method="post" id="addFooterForm">
 			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			<div class="account-det">
				<div class="det-row">
					<div class="det-row-col-full astrick">
		            	<div class="label-text">Footer </div>
		              	<textarea placeholder="Max 150 characters" name="footer" id="footerDesc" maxlength="150" rows="2" class="form-control">${fn:escapeXml(loginUser.footer) }</textarea>  
		              	<span class="text-danger cust-error" id="feedback-query-req">This field is required.</span>
					</div>
				</div>
			</div>	  
        	<div class="insidebtn"> 	        	
        		<button type="button" class="sim-button button5" id="footerSubmit" formnovalidate="formnovalidate">Save</button>
        	</div>
		</form>
	</div>
</section>


