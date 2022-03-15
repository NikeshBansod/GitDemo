<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/uploadmasterexcel/uploadMasterExcel.js"/>"></script>

<div id="editPageContainer">
	<section class="insidepages">
	 	<div class="container" id='loadingmessage' style='display:none;' align="center">
			<img src='<spring:url value="./resources/images/loading.gif"/>'/>
		</div>
	  	<div class="inside-cont">
	  		<!-- <div class="insideheader">
	          <div class="insideheader-cont"><img src="./resources/images/wizardImages/excel.png" width="29" height="29">
	            <div>Upload Master Excel</div>
	          </div>
	        </div> -->
	        <div class="breadcrumbs">
				<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span><img src="./resources/images/wizardImages/excel.png" width="29" height="29">Upload Master through Excel
			</div>
		  <div class="account-det">
		  	<div class="det-row">
		        <div class="det-row-col-half astrick">
					<div class="label-text">Master Type </div>
					<select id="masterType">
					<option value="customermastertemplate">Customer</option>
					<option value="goodsmastertemplate">Goods</option>
					<option value="servicesmastertemplate">Services</option>
					</select><br><span class="text-danger cust-error" style="display: inline;" id="noteMsg"></span> 
				</div> 
				<div class="det-row-col-half">	
					<div class="label-text">&nbsp;</div>
					<input id="downloadtemplate" type="button" class="sim-button button5" value="Download Template"  formnovalidate="formnovalidate" onclick="downloadExcelTemplate();"/>
				</div>   
		  	</div>
	  
		   	<div class="det-row">
		   		<form id="uploadMaster"  enctype="multipart/form-data" method="post"> 
					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
					<br>
		            <div class="det-row-col astrick">
						<div class="input-group">
						    <span class="input-group-btn">
								<button id="fake-file-button-browse" type="button" class="btn btn-default">
									<span class="fa fa-file"></span> Choose
								</button>
							</span>
							
							<input type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" id="files-input-upload" style="display:none">
							
							<input type="text" id="fake-file-input-name" name="fileName" readonly="readonly"  placeholder="File not selected" class="form-control" style="width:700px;" >
							
							<span class="input-group-btn">
								<button type="button" class="btn btn-default" disabled="disabled" id="fake-file-button-upload" onclick="uploadMasterExcel();">
									<span class="fa fa-upload"></span> Upload
								</button>
								
							</span>
						</div>   
					</div>
					<input type="hidden" id="hiddenMasterType" name="hiddenMasterType" >
				</form>										           
			 </div> 
			</div>
		</div> 
	</section> 
</div>
 
