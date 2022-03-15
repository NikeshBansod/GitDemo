<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/addadditionalChargeDetails/wizardManageAdditionalChargeDetails.js"/>"></script>


		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Additional Charges Master
		 		</div>
		 		<div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardAdditionalCharges"/>">Additional Charges Master</a> <span>&raquo;</span> Manage Additional Charges
	 			</div>
			</div>	
			<div class="container">								
				<div class="row text-center">				   			  			
		    		<a class="btn-floating btn-large" title="Add Additional Charge" id="addAdditionalCharge" >
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>	
		    	</div>							
				<div class="cust-wrap">		
					<div  id="toggle"><br>	
						<table class="table table-striped table-bordered table-hover " id="addChargeTab">
							<thead>
								<tr>
									<th style="text-align: center;">Sr. No.</th>
									<!-- <th></th> -->
									<th style="text-align: center;">Name</th>
									<!-- <th style="text-align: center;">View</th> -->
									<th style="text-align: center;">Edit</th>
									<th style="text-align: center;">Delete</th>
								</tr>
							</thead>
							<tbody>									
								<%-- <c:forEach items="${additionalChargeDetailsList}" var="addChargeList" varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td>${addChargeList.chargeName}</td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${addChargeList.id})"> <i class="fa fa-eye" aria-hidden="true"></i></a></td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${addChargeList.id});"> <i class="fa fa-pencil" aria-hidden="true"></i></a></td>
										<td align="center"><a href="#" onclick="javascript:deleteRecord(${addChargeList.id});"> <i class="fa fa-trash-o" aria-hidden="true"></i></a></td>										
									</tr>
								</c:forEach> --%>
							</tbody>
						</table>						
					</div>
		      </div>
			</div>		
			
			<br>
			<div id="addAdditionalChargeDetails">
				<form:form commandName="additionalChargeDetails" method="post" id="addChargeForm" action="./wizardSaveAdditionalCharges">
   					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-half astrick">
								<div class="label-text">Name of Additional Charge</div>
							  	<form:input path="chargeName" id="chargeName" maxlength="100" required="true" class="form-control"/>
							  <!-- 	<span class="text-danger cust-error" id="cust-name-req">This field is required.</span> -->
							</div>
							<div class="det-row-col-half astrick">
								<div class="label-text">Value</div>
							  	<form:input path="chargeValue" id="chargeValue" maxlength="18" required="true" class="form-control"/>
							  	<!-- <span class="text-danger cust-error" id="contact-no-req">This field is required</span> -->
							</div> 										
						</div>						
						<input type="hidden" id="editPage" value="false" />
					</div>
			      	<div class="insidebtn"> 	       	
						<input id="chargesSubmitBtn" type="Submit" class="sim-button button5" value="Save" formnovalidate="formnovalidate"/> 
						<!-- <input id="cancel" formnovalidate="formnovalidate" type="Submit" class="sim-button button5" value="Cancel"  />  -->
					</div>
					<input type="hidden" name="tempUom" id="tempUom">
		  		</form:form>			            
			   </div>
		
			   <form name="editAdditionalChargeDetails" method="post">
				    <input type="hidden" name="id" value="">
				     <input type="hidden" name="userId" value="">
   					<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				</form>
		</section>

<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add Additional Charge" id="addAdditionalCharge" >
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> --%>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/addadditionalChargeDetails/loadAdditionalChargeDatatable.js"/>"></script>