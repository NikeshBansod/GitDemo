<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
           
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/uploadToAsp/wizardUploadHistory.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/uploadToAsp/moment.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/accordion/smk-accordion.js" />"></script>


<section class="insidepages">
<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	<div class="inside-cont">
				
				<a href="<spring:url value="/wAspMasters"/>">ASP Home</a> <span>&raquo;</span> ASP Upload History
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-full astrick">
								<div class="label-text">GSTIN</div>
							  	<select  id="gstinId" name="gstinId" class="form-control">	</select>      
							  	<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
							</div>
							
							</div>
							</div>
							<div class="text-center">
     					<div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Show History</button> </div>
      	
      						</div>
							</div>
							<div class="cust-wrap">
		
		<div class="dnynamicHistoryDetails" id="toggle">								
		</div>
      </div>
							</section>
							
        
     