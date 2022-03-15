<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 


<section class="block">	
	<div class="container">
		<div class="loader"></div>
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />	
		<%-- <div class="brd-wrap">
			<div>
				<a href="<spring:url value="/home#inventory_management"/>"><strong>Inventory</strong></a> &raquo; <strong>Un-Moved Items Report</strong>
			</div>
		</div> --%>
		 <div class="page-title">
                        <a href="<spring:url value="/home#inventory_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Un-Moved Items Report
                    </div> 
		<div class="form-wrap">
			<div class="row">
		  		<div class="col-md-6">
					<label for="">GSTIN <span>*</span></label>
		  			<select name="gstin" id="gstin"></select>
		  			<span class="text-danger cust-error" id="gstin-csv-id">This field is required.</span>
		  		</div>	  		
		  		<div class="col-md-6">
		  		     <label for="">Location/Store <span>*</span></label>
		  		     <select name="location" id="location"></select>
		  		     <input type="hidden" id="locationStore" name="locationStore">
		             <span class="text-danger cust-error" id="location-csv-id">This field is required.</span>
		  		</div>	  
		    </div>
		    <div class="row">		    	  
		        <div class="col-md-6">
		             <label for="">From <span>*</span></label>
		              <input type="text" id="fromDate" name="fromDate" autocomplete="off">
		             <span class="text-danger cust-error" id="fromDate-csv-id">This field is required.</span>
		        </div>  
		        <div class="col-md-6">
		             <label for="">To <span>*</span></label>
		              <input type="text" id="toDate" name="toDate" autocomplete="off">
		             <span class="text-danger cust-error" id="toDate-csv-id">This field is required.</span>
		        </div>	        
		    </div>
		</div>
		
		<div class="row">
			<div class="col-md-12 button-wrap">
				<button type="button" id="generateReportButton" style="width:auto;" class="btn btn-success blue-but">Generate Report</button>
			</div>
		</div>
		<div id="generateReportGrid">
			<div class="row" id="listTable">
		        <div class="table-wrap">
		            <table id="unMovedItemsTab" class="display nowrap" style="width:100%">
			            <thead>
			                <tr>
			                    <th style="width:20px;">Sr.No.</th>
			                    <th>Product</th>
			                    <th>Quantity</th>
			                    <th>UOM</th>
			                </tr>
			            </thead>
			            <tbody>
			               	
			            </tbody>
		            </table>
		        </div>
		    </div>	
		</div>		
	</div>		
</section>

<script type="text/javascript" src="./resources/js/newUiJs/stockSummaryReport/unMovedItems.js"></script>
