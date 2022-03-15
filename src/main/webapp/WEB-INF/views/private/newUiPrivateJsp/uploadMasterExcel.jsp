<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 



<section class="block">
	<div class="loader"></div>
	<div class="container">		 
	    <div class="page-title">	    	
	       		 <a href="/gstn/home#master_management" class="back"><i class="fa fa-chevron-left"></i></a>Upload Master Through Excel 
	    </div> 
  		<div class="form-wrap" >  
		  <div class="row">	
		  		<div class="col-md-6">  
					<label for="label">Master Type<span > *</span></label>
					<select id="masterType">
						<option value="customermastertemplate">Customer</option>
						<option value="goodsmastertemplate">Goods</option>
						<option value="servicesmastertemplate">Services</option>
					</select><br>
					<span class="text-danger cust-error" style="display: inline;" id="noteMsg"></span> 
					</div>
				<div class="col-md-6" style="padding-top: 28px;">
					<button id="downloadtemplate"  class="btn btn-success blue-but" value="Download Template" onclick="downloadExcelTemplate();">Download Template</button>
				</div>
			</div> 
			<div class="row">	
				<div class="col-md-6">
					<form id="uploadMaster"  enctype="multipart/form-data" method="post"> 
						<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
						<div class="input-group">
							<span class="input-group-btn">   
								<button id="fake-file-button-browse" type="button" class="btn btn-success blue-but">
									<span class="fa fa-file">  </span> Choose
								</button>
							</span>								
							<input type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" id="files-input-upload" style="display:none">							
							<input type="text" id="fake-file-input-name" name="fileName" readonly="readonly"  placeholder="File not selected" class="form-control" style="height: 37px;">
							<span class="input-group-btn">
								<button type="button" class="btn btn-success blue-but" disabled="disabled" id="fake-file-button-upload" onclick="uploadMasterExcel();">
									<span class="fa fa-upload"></span> Upload
								</button>
							</span>
						</div>								
						<input type="hidden" id="hiddenMasterType" name="hiddenMasterType" >
					</form>
				</div>
			</div>
		</div>
	</div>
</section>


<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/uploadmasterexcel/uploadMasterExcel.js"/>"></script>