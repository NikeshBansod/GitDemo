<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<script type="text/javascript" src="resources/js/wizardJS/manageGoodsCatalogue/wizardManageProduct.js"></script>
<script type="text/javascript" src="resources/js/products/addProducts.js"></script>

	
		<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#mas_management"/>">Home</a> <span>&raquo;</span> Manage Goods Catalogue
		 		</div>
		 		<div class="col-md-12" id="listheader2">
	         		<a href="<spring:url value="/wizardGetProducts"/>">Manage Goods Catalogue</a> <span>&raquo;</span> Manage Goods
	 			</div>
			</div>	
			<div class="container">
				<div class="row text-center">						
		   			<a class="btn-floating btn-large" title="Add Product" id="addProduct" >
				    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
				  	</a>  				
		    	</div>					
				<div class="cust-wrap">		
					<div  id="toggle"><br>	<!-- class="dnynamicCustomerDetails" -->	
						<table class="table table-striped table-bordered table-hover " id="productTab">
							<thead>
								<tr>
									<th style="text-align: center;">Sr. No.</th>
									<!-- <th></th> -->
									<th style="text-align: center;">Name</th>
									<th style="text-align: center;">HSN Code</th>
									<th style="text-align: center;">Goods Rate</th>
									<th style="text-align: center;">Rate of Tax(%)</th>
									<th style="text-align: center;">Edit</th>
									<th style="text-align: center;">Delete</th>
								</tr>
							</thead>
							<tbody>									
								<%-- <c:forEach items="${productsList}" var="productList" varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td>${productList.name}</td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${productList.id})"> <i class="fa fa-eye" aria-hidden="true"></i></a></td>
										<td align="center"><a href="#" onclick="javascript:editRecord(${productList.id});"> <i class="fa fa-pencil" aria-hidden="true"></i></a></td>
										<td align="center"><a href="#" onclick="javascript:deleteRecord(${productList.id});"> <i class="fa fa-trash-o" aria-hidden="true"></i></a></td>										
									</tr>
								</c:forEach> --%>
							</tbody>
						</table>						
					</div>
		      </div>
			</div>		
			
			<br>
			<div id="addProductDetails">
				<form:form commandName="product" method="post" id="prdForm" action="./wizardProductSave">
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">Search By HSN Code / HSN Description</div>
							  	<input type="text" id="search-hsn" maxlength="15"  class="form-control"/>    
							  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By HSN Code / HSN Description.</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text">HSN Description</div>
							  	<input type="text" disabled="disabled" id="hsnDescriptionToShow" required="true" class="form-control"/>
								<form:hidden path="hsnDescription" id="hsnDescription" required="true" />
							  	<span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
							</div> 
							<div class="det-row-col astrick">
								<div class="label-text">HSN Code</div>
							  	<input type="text" disabled="disabled" id="hsnCodeToShow" required="true" class="form-control"/>
								<form:hidden path="hsnCode" id="hsnCode" required="true" />
							  	<span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
							</div> 					
						</div>
						<div class="det-row"> 
							<div class="det-row-col astrick">
								<div class="label-text">Goods Name</div>
							  	<form:input path="name" id="name" maxlength="100" required="true" class="form-control"/>
							  	<span class="text-danger cust-error" id="prod-name">This field is required.</span>
							</div>								
							<div class="det-row-col astrick">
								<div class="label-text">Unit Of Measurement</div>
							 	<select class="form-control" name="unitOfMeasurement" class="form-control" id="unitOfMeasurement">
	                    		</select>
							  	<span class="text-danger cust-error" id="prod-uom">This field is required.</span>
							</div>  	
							<div class="det-row-col astrick"  id="divOtherUnitOfMeasurement">
								<div class="label-text">Please Specify</div>
								<form:input path="otherUOM"  id="otherUOM" maxlength="30" class="form-control"/>
							 	<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
							</div>
							<div class="det-row-col astrick" >
								<div class="label-text">Goods Rate (Rate per unit of measurement)</div> 
								<form:input path="productRate" maxlength="18" required="true" class="form-control"/>
							 	<span class="text-danger cust-error" id="prod-rate">This field is required and should be numeric.</span>
							</div>
						</div>
						<div class="det-row">						
							<div class="det-row-col astrick">
							 	<div class="label-text">Rate of tax (%)</div>              	
							   	<select class="form-control" id="productIgst" name="productIgst" class="form-control">
								</select> 
								<span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
					       </div>
						</div>
						<form:hidden path="hsnCodePkId" id="hsnCodePkId"/>
				  		<input type="hidden" id="editPage" value="false" />
					</div>
			      	<div class="insidebtn"> 	       	
						<input id="prodSubmitBtn" type="button" class="sim-button button5" value="Save" /> 
						<!-- <input id="cancel" formnovalidate="formnovalidate" type="Submit" class="sim-button button5" value="Cancel"  />  -->
					</div>
					<input type="hidden" name="tempUom" id="tempUom">
		  		</form:form>			            
			   </div>
		
				<form name="manageProduct" method="post">
				    <input type="hidden" name="id" value="">
				    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				</form>	
		</section>

<%-- <div class="fixed-action-btn" >
	<a class="btn-floating btn-large" title="Add Product" id="addProduct" >
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div> --%>

<script type="text/javascript" src="resources/js/wizardJS/manageGoodsCatalogue/loadProductMasterDatatable.js"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/products/hsnCodeAjax.js"/>"></script>