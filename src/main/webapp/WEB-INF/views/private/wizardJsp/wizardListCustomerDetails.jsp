<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/addCustomerDetails/addCustomerDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/manageCustomerDetailsMaster/wizardManageCustomerDetails.js"/>"></script>


		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Manage Customer Details
		 		</div>
		 		<div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardCustomerDetails"/>">Manage Customer Details</a> <span>&raquo;</span> Manage Customer
	 			</div>
			</div>	
			<div class="container">							
				<div class="row text-center">				   			  			
		    		<a class="btn-floating btn-large" title="Add Customer" id="addCustomer" href="<spring:url value="/wHome#master"/>">
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>	
		    	</div>				
					<br>
				<div class="cust-wrap">		
					<div  id="toggle">
		      			<table class="table table-striped table-bordered table-hover " id="custTab">
							<thead>
								<tr>
									<th style="text-align: center;">Sr. No.</th>
									<!-- <th></th> -->
									<th style="text-align: center;">Name</th>
									<th style="text-align: center;">GSTIN</th>
									<th style="text-align: center;">Edit</th>
									<th style="text-align: center;">Delete</th>
								</tr>
							</thead>
							<tbody>									
								<%-- <c:forEach items="${manageCustomerDetailsList}" var="custList" varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td>${custList.custName}</td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${custList.id})"> <i class="fa fa-eye" aria-hidden="true"></a></td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${custList.id});"> <i class="fa fa-pencil" aria-hidden="true"></a></td>
										<td align="center"><a href="#" onclick="javascript:deleteRecord(${custList.id});"> <i class="fa fa-trash-o" aria-hidden="true"></a></td>										
									</tr>
								</c:forEach> --%>
							</tbody>
						</table>					
					</div>		
		     	</div>
		      	
			</div>		
			
			
			<div id="addCustomerDetails">
				<form:form commandName="customerDetails" method="post" id="custForm" action="./wizardAddCustomerDetails">
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />	
			    	<br>
					<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col-half">Customer Type <br>
							      <input type="radio" name="custType" value="Individual" id="Individual" checked="checked">
							      <label for="Individual"> Not Registered Under GST</label>
							      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="custType" value="Organization" id="Organization">
							      <label for="Organization">Registered Under GST</label>
							</div> 
						</div>
						
						<div class="det-row"> 
							<div class="det-row-col astrick" id="divGstinNo" style="">	<!--display: none; -->
								<div class="label-text">GSTIN</div>
								<form:input path="custGstId" maxlength="15" id="custGstId" class="form-control"/>
							 	<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
							</div>
							<div class="det-row-col astrick" id="divGstinState" style="">	<!--display: none;  -->
								<div class="label-text">GSTIN State</div> 
								<form:select path="custGstinState"  id="custGstinState" class="form-control"/>
							 	<span class="text-danger cust-error" id="custGstId-req">GSTIN should be in a proper format.</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">Customer Name </div>
							  	<form:input path="custName" id="custName" maxlength="100" required="true" class="form-control"/>  
							  	<span class="text-danger cust-error" id="cust-name-req">This field is required.</span>
							</div>
							<div class="det-row-col">
								<div class="label-text">Mobile Number </div>
							  	<form:input path="contactNo" id="contactNo"  maxlength="10" class="form-control"/>
							  	<span class="text-danger cust-error" id="contact-no-req">This field is required and should be 10 digits.</span>
							</div>	
							<div class="det-row-col ">
								<div class="label-text">Email ID </div>
							 	<form:input path="custEmail" maxlength="100" id="custEmail" class="form-control"/>
							  	<span class="text-danger cust-error" id="cust-email-format">Email should be in correct format.</span>
							</div>  	
							
						</div>
						<div class="det-row">
							<div class="det-row-col astrick">
							 	<div class="label-text">Pin Code </div>              	
							   	<input type="text" name="pinCode" required="true" id="pinCode" maxlength="6" class="form-control"/> 
							    <span class="text-danger cust-error" id="empty-message"></span>
								<span class="text-danger cust-error" id="cust-zip-req">Pin Code is required and should be 6 digits.</span>
					       </div>
					       <div class="det-row-col astrick">
					        	<div class="label-text ">City </div>
					          	<input type="text" readonly="readonly" id="custCity" name="custCity" class="form-control" /> 
							</div>
							<div class="det-row-col astrick">
							 	<div class="label-text">State </div>
							    <input type="text" readonly="readonly" id="custState" name="custState" class="form-control"/>  
							   	<span class="text-danger cust-error" id="custState-err"></span>
							</div>
						</div>
						<div class="det-row">
							<div class="det-row-col astrick">
							 	<div class="label-text">Country </div>
							   	<input type="text" readonly="readonly" name="custCountry" required="true" id="custCountry" class="form-control"/> 
							</div>
							<div class="det-row-col " id="divAddr" >	
								<div class="label-text">Address</div>							
								<form:textarea path="custAddress1" id="custAddress1" maxlength="350"  class="form-control"/>  
							 	<!-- <span class="text-danger cust-error" id="address1-req">This field is required </span> -->
							</div>							
						</div>
					</div>
			      	<div class="insidebtn"> 	        	
						<input id="custSubmitBtn" type="Submit" class="sim-button button5" value="Save" /> 
						<!-- <input id="editCancel" type="Submit" class="sim-button button5" value="Cancel"  formnovalidate="formnovalidate" />  -->
					</div>
		  		</form:form>			            
			   </div>
		
			<form name="editCustomerDetails" method="post">
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			    <input type="hidden" name="id" value="">
			     <input type="hidden" name="userId" value="">
			</form>	
		</section>

	
<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add Customer" id="addCustomer" href="<spring:url value="/wHome#master"/>">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> --%>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/manageCustomerDetailsMaster/loadCustomerMasterDatatable.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>