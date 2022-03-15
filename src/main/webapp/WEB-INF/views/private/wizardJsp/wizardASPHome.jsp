<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %>


<%@include file="../common-alert.jsp" %>
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
	
	
	<section class="insidepages">
	  <div class="breadcrumbs" id="listHeader">
				<a href="<spring:url value="/wHome"/>">Home</a> <span>&raquo;</span> Compliance
			</div>
      <div class="inside-cont-invoice">
        <div class="container">
        	<div class="row">
        		<div class="col-md-12">
        			<div class="inv-mgt-cont main_btn">
			           <a class="blue_tab_bttn " data-id="notification" href="<spring:url value="/wGetJioGstLoginPage" />">
			            	<i class="fa fa-upload" aria-hidden="true"></i>
			              	<span>Upload </br> (Manual)</span>
			           </a>
			           
			            <a href="<spring:url value="/wizardaspdraft" />" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Draft</span>
			           </a> 
			           
			           <a href="<spring:url value="wGetUploadSetting" />" data-id="doc_management" class="blue_tab_bttn">
			           		<i class="fa fa-cog" aria-hidden="true"></i>
			              	<span>Settings</span>
			           </a>
	
			           <a href="<spring:url value="wGetHistory" /> " data-id="mas_management" class="blue_tab_bttn">
			           		<i class="fa fa-history" aria-hidden="true"></i>
			              	<span>History</span>
			           </a>
			        </div>
        		</div>
        	</div>
        </div>
        
      </div>
</section>
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
	});
</script>
	