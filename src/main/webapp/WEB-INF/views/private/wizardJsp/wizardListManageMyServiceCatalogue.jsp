<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/addServiceCatalogue.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/manageServiceCatalogue/wizardManageServiceCatalogue.js"/>"></script>

				
		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Manage Service Catalogue
		 		</div>
		 		<div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardManageServiceCatalogue"/>">Manage Service Catalogue</a> <span>&raquo;</span> Manage Services
	 			</div>
			</div>	
			<div class="container">	
				<div class="row text-center">						
		   			<a class="btn-floating btn-large" title="Add Servie" id="addService" >
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>    				
		    	</div>								
				<div class="cust-wrap">		
					<div  id="toggle"><br>	<!-- class="dnynamicCustomerDetails" -->	
						<table class="table table-striped table-bordered table-hover " id="serviceTab">
							<thead>
								<tr>
									<th style="text-align: center;">Sr. No.</th>
									<!--<th></th> -->
									<th style="text-align: center;">Name</th>
									<th style="text-align: center;">SAC Code</th>
									<th style="text-align: center;">Service Rate</th>
									<th style="text-align: center;">Rate of Tax(%)</th>
									<th style="text-align: center;">Edit</th>
									<th style="text-align: center;">Delete</th>
								</tr>
							</thead>
							<tbody>									
								<%-- <c:forEach items="${servicesList}" var="servicesList" varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td>${servicesList.name}</td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${servicesList.id})"> <i class="fa fa-eye" aria-hidden="true"></i></a></td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${servicesList.id});"> <i class="fa fa-pencil" aria-hidden="true"></i></a></td>
										<td align="center"><a href="#" onclick="javascript:deleteRecord(${servicesList.id});"> <i class="fa fa-trash-o" aria-hidden="true"></i></a></td>										
									</tr>
								</c:forEach> --%>
							</tbody>
						</table>						
					</div>
		      </div>
			</div>		
			
			<br>
			<div id="addServiceDetails">
				<form:form commandName="manageServiceCatalogue" id="servForm" method="post" action="./wizardAddManageServiceCatalogue ">
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">Search By SAC Code / SAC Description</div>
							  	<input type="text" id="search-sac" maxlength="15" class="form-control"/>  
							  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By SAC Code / SAC Description</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">SAC Description</div>
							  	<input type="text" disabled="disabled"  readonly="readonly" id="sacDescriptionToShow" required class="form-control"/>
								<form:hidden path="sacDescription" id="sacDescription" />
							  	<span class="text-danger cust-error" id="ser-sac-desc">This field is required.</span>
							</div> 
							<div class="det-row-col astrick">
								<div class="label-text">SAC Code</div>
							  	<input type="text" disabled="disabled"  readonly="readonly" id="sacCodeToShow" required class="form-control"/>
								<form:hidden path="sacCode" id="sacCode" />
							  	<span class="text-danger cust-error" id="ser-sac-code">This field is required.</span>
							</div> 					
						</div>
						<div class="det-row"> 
							<div class="det-row-col astrick">
								<div class="label-text">Service Name</div>
							  	<form:input path="name" required="true" maxlength="100" class="form-control" />
							  	<!-- <span class="text-danger cust-error" id="ser-name">This field is required.</span> -->
							</div>								
							<div class="det-row-col astrick">
								<div class="label-text">Unit Of Measurement</div>
							 	<select name="unitOfMeasurement" class="form-control" id="unitOfMeasurement" class="form-control" >
	                       		</select>
							</div>  	
							<div class="det-row-col astrick"  id="divOtherUnitOfMeasurement">
								<div class="label-text">Please Specify</div>
								<form:input path="otherUOM" maxlength="30" id="otherUOM" class="form-control"/>
							 	<!-- <span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span> -->
							</div>
							<div class="det-row-col astrick" >
								<div class="label-text">Service Rate (Rs.)</div> 
								<form:input path="serviceRate" maxlength="18" required="true" class="form-control"/>
							 	<!-- <span class="text-danger cust-error" id="ser-rate">This field is required.</span> -->
							</div>
						</div>
						<div class="det-row">						
							<div class="det-row-col astrick">
							 	<div class="label-text">Rate of tax (%)</div>              	
							   	<select name="serviceIgst" class="form-control" id="serviceIgst" class="form-control" >
	                        	</select>
								<!-- <span class="text-danger cust-error" id="service-igst">This field is required.</span> -->
					       </div>
						</div>
						<form:hidden path="sacCodePkId" id="sacCodePkId"/>
				  		<input type="hidden" id="editPage" value="false" />
					</div>
			      	<div class="insidebtn"> 	       	
						<input id="srvSubmitBtn" type="Submit" class="sim-button button5" value="Save" /> 
						<!-- <input id="cancel" formnovalidate="formnovalidate" type="Submit" class="sim-button button5" value="Cancel"  />  -->
					</div>
					<input type="hidden" name="tempUom" id="tempUom">
		  		</form:form>			            
			   </div>
		
			   <form name="manageService" method="post">
			      <input type="hidden" name="id" value="">
			      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			   </form>
		</section>
	
<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add Servie" id="addService" >
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> --%>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/manageServiceCatalogue/loadServiceMasterDatatable.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/manageServiceCatalogue/sacCodeAjax.js"/>"></script>

<style>
#ui-id-1{width:90% !important;}
</style>
