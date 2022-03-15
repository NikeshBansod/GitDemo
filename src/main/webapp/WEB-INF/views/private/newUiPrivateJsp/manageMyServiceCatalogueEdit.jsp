<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



      <div class="loader"></div>
<section class="block">
	<div class="container">
		 
	    <div class="brd-wrap">
	    	<div  id="listheader">
	       		<strong><a href="<spring:url value="/manageServiceCatalogue" />">Service Catalogue</a></strong> &raquo; <strong>Edit Service </strong>
	        </div>
	        
	    </div>   



 <div class="form-wrap"  >	    
			<form:form commandName="manageServiceCatalogue" id="servForm" method="post" action="./updateManageServiceCatalogue" >
            		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />	    	
				<div class="row">	
					<%-- <div class="col-md-4">
						<label for="label">Search By SAC Code / SAC Description</label>
						  	<input type="text" id="search-sac" maxlength="15" class="form-control" /> 
						  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By SAC Code / SAC Description</span>				
					</div>
					--%>
					<div class="col-md-4">
						<label for="label">SAC Description<span> *</span></label>
					 	<input type="text" value="${manageServiceCatalogueObj.sacDescription }" readonly="readonly" id="sacDescriptionToShow" class="form-control" /> 
							<form:hidden path="sacDescription" value="${manageServiceCatalogueObj.sacDescription }" id="sacDescription" />
					</div>
					 
					<div class="col-md-4">
						<label for="label">SAC Code<span> *</span></label>
						  	
						  	<input type="text" disabled="disabled" value="${manageServiceCatalogueObj.sacCode }" readonly="readonly" id="sacCodeToShow" class="form-control" />
							<form:hidden path="sacCode" id="sacCode" value="${manageServiceCatalogueObj.sacCode }"/> 		
					</div>
					
					</div>
					
					
					<div class="row">	
					<div class="col-md-4">
						<label for="label">Service Name<span> *</span></label>
						
						  	<form:input path="name" maxlength="100" id="name" value="${manageServiceCatalogueObj.name }" required="true" class="form-control" /> 
					</div>		
					
					
					<%--  <div class="col-md-4">
						<label for="label">Unit Of Measurement <span> *</span></label>
						 	<form:select path="unitOfMeasurement" id="unitOfMeasurement" required="true" class="form-control" style=" height: 39px;"/> 
							<input type="hidden" id="unitOfMeasurementHidden" value="${manageServiceCatalogueObj.unitOfMeasurement }" />	
					</div> --%> 
					
					<div class="col-md-4" id="divOtherUnitOfMeasurement">
						<label for="label">Please Specify <span> *</span></label>
						 	<form:input path="otherUOM" maxlength="30" value="${manageServiceCatalogueObj.otherUOM}" class="form-control"/>	
					</div>
					
					<div class="col-md-4">
						<label for="label">Charge<span> *</span></label>
						 	<form:input path="serviceRate" id="serviceRate" maxlength="18" value="${manageServiceCatalogueObj.serviceRate }" required="true" class="form-control" />	
					</div>							
				</div>
				
				
				<div class="row">	
					<div class="col-md-4">
						<label for="label">Rate of tax (%)<span> *</span></label>
						  	<form:select path="serviceIgst" id="serviceIgst" required="true" class="form-control" style=" height: 39px;"/>
					</div>
					
					<!-- <div class="col-md-4">
						<label for="label">Cess Advol Rate (%)<span> *</span></label>
						<select class="form-control" id="advolCess"  name="advolCess" class="form-control" style=" height: 39px;">
						</select>  
					</div>
					<div class="col-md-4">
						<label for="label">Cess Non Advol Rate<span> *</span></label>
						<select class="form-control" id="nonAdvolCess" name="nonAdvolCess" class="form-control" style=" height: 39px;">
						</select>  
					</div> -->
				</div>
				
			   
				<form:hidden path="id" value="${manageServiceCatalogueObj.id }"/>
					<form:hidden path="referenceId" value="${manageServiceCatalogueObj.referenceId }"/>
					<input type="hidden" id="unitOfMeasurementHidden" value="NA" />
					<input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
					<input type="hidden" id="editPage" value="true" />
					<input type="hidden" id="serviceRateOfTax" value="${manageServiceCatalogueObj.serviceIgst}"/>
					<form:hidden path="existingName" id="existingname" value="${manageServiceCatalogueObj.name }"/>
					<input type="hidden" name="tempUom" id="tempUom">
				 <div class="row">
			        <div class="col-md-12 button-wrap">
			            <button id="srvSubmitBtn" type="submit"  style="width: auto;" class="btn btn-success blue-but" value="Update">Update</button> 
			            <button id="srvDeleteBtn" type="button" onclick="javascript:deleteRecord('${manageServiceCatalogueObj.id}');" style="width: auto;" class="btn btn-success blue-but" value="Delete">Delete</button>
			                    
			        </div>
			    </div>

</form:form>
</div>
</div>
</section>























<form name="manageService" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>










 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/manageServiceCatalogue/manageServiceCatalogue.js"/>"></script>
 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/manageServiceCatalogue/sacCodeAjax.js"/>"></script>