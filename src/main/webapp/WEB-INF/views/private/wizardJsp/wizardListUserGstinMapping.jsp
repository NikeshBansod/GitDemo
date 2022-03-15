<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/UserGstinMapping/wizardAddUserGstinMapping.js"/>"></script>  
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/UserGstinMapping/wizardManageUserGstinMapping.js"/>"></script>


		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Manage GSTIN User Mapping
		 		</div>
		 		<div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardGetUserGstinMap"/>">Manage GSTIN User Mapping</a> <span>&raquo;</span> Add GSTIN User Mapping
	 			</div>
			</div>	
			<div class="container">	
				<div class="row text-center">						
		   			<a class="btn-floating btn-large" title="Add Gstin-User Mapping" id="addUserGstinMapping" >
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>     				
		    	</div>				
				<div class="cust-wrap">		
					<div  id="toggle"><br>		
						<table class="table table-striped table-bordered" id="userGstinMapTab">
							<thead>
								<tr>
									<th style="text-align: center;">Sr.No.</th>
									<!-- <th></th> -->
									<th style="text-align: center;">Name</th>
									<!-- <th style="text-align: center;">View</th> -->
									<th style="text-align: center;">Edit</th>
									<th style="text-align: center;">Delete</th>
								</tr>
							</thead>
							<tbody>									
								
							</tbody>
						</table>						
					</div>
		      </div>
			</div>		
			
			
			<div id="addUserGstinMappingDetails">
				<form:form commandName="userGstinMapping" id="saveUserGstin" method="post" action="./wizardAddUserGstinMapping">
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">GSTIN</div>
							  	<form:select path="gstinId" id="gstinId" class="form-control">
                   				</form:select>
							  	<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">Store/Location/Channel/Department</div>
							  	<form:select path="gstinAddressMapping.id" id="gstinAddressMapping" class="form-control">
                   				</form:select>
							  	<span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
							</div> 
							<div class="det-row-col astrick">
								<div class="label-text">Choose Employee</div>
							  	<form:select path="gstinUserIds" id="gstinUserSet" multiple="true" class="form-control">
                   				</form:select> 
							  	<span class="text-danger cust-error" id="selectGSTINDetail-req"></span>
							</div> 					
						</div>
												
						<input type="hidden" id="editPage" value="false" />
					</div>
			      	<div class="insidebtn"> 	       	
						<input id="submitGstinMapping" type="Submit" class="sim-button button5" value="Save" /> 
					</div>
					<input type="hidden" name="tempUom" id="tempUom">
		  		</form:form>			            
			   </div>
		
				<form name="userGstinMapping" method="post">
					<input type="hidden" name="id" value="">
					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				</form>
		</section>

<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add Gstin-User Mapping" id="addUserGstinMapping" >
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> --%>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/UserGstinMapping/loadUserGstinMapDatatable.js"/>"></script>