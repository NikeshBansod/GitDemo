<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/gstinDetails/wizardListGstinDetails.js"/>"></script> 

	<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Manage GSTIN 
		 		</div>
		 		<%-- <div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardListGstinDetails"/>">Manage GSTIN</a> <span>&raquo;</span> Add GSTIN
	 			</div> --%>
			</div>	
			<div class="container">						
				<div class="row text-center">				   			  			
		    		<a class="btn-floating btn-large" title="Add GSTIN Details" id="addGSTIN" href="<spring:url value="./wizardAddGstinDetails"/>">
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>	
		    	</div>			
				<div class="cust-wrap">		
					<div  id="toggle"><br>	<!-- class="dnynamicCustomerDetails" -->	
						<table class="table table-striped table-bordered" id="gstinDetailsTab">
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
			<form name="manageGstinDetail" method="post">
			    <input type="hidden" name="id" value="">
			    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			</form>				
	</section>

<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add GSTIN Details" id="addGSTIN" href="<spring:url value="./wizardAddGstinDetails"/>">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>
 --%>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/gstinDetails/loadGstinDetailsDatatable.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>