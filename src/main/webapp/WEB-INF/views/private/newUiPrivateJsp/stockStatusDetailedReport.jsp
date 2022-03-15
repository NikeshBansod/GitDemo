<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 


<section class="block">	
	<div class="container">
		<div class="loader"></div>
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />	
		<input type="hidden" id="fDate" name="fDate" value="${fDate}">
        <input type="hidden" id="tDate" name="tDate" value="${tDate}">
        <input type="hidden" id="storeId" name="storeId" value="${storeId}">
        <input type="hidden" id="productId" name="productId" value="${productId}">
        <input type="hidden" id="gstin" name="gstin" value="${gstin}">
        <input type="hidden" id="currentOpeningStock" name="currentOpeningStock" value="${currentOpeningStock}">
		<!-- <div class="brd-wrap">
			<div>
				<a id="goBackToSummaryReport" href="javascript:void(0);"><strong>Stock Summary Report</strong></a> &raquo; <strong>Stock Detailed Report</strong>
			</div>
		</div> -->
		 <div class="page-title">
                      <a id="goBackToSummaryReport" href="javascript:void(0);" class="back"><i class="fa fa-chevron-left"></i></a>Stock Detailed Report
                    </div> 
		<div class="form-wrap">
			<div class="row">
		  		<div class="col-md-4">
					<label for="">Product Name <span>*</span></label>
		  			<!-- <select name="gstin" id="gstin"></select> -->
		  			 <input type="text" name="productName" id="productName" value="" disabled>
		  			<span class="text-danger cust-error" id="gstin-csv-id">This field is required.</span>
		  		</div>	  		
		  		<div class="col-md-4">
		  		     <label for="">From Date <span>*</span></label>
		  		   <!--   <select name="location" id="location"></select> -->
		  		     <input type="text"  name="fdate" value="${fDate}" disabled>
		             <span class="text-danger cust-error" id="location-csv-id">This field is required.</span>
		  		</div>	  
		        <div class="col-md-4">
		             <label for="">To Date <span>*</span></label>
		            <!--  <select name="products" id="products">
		             </select> -->
		              <input type="text"  name="todate" value="${tDate}" disabled>
		             <span class="text-danger cust-error" id="products-csv-id">This field is required.</span>
		        </div>	        
		    </div>
		</div>
		
		<!-- <div class="row">
			<div class="col-md-12 button-wrap">
				<button type="button" id="show" style="width:auto;" class="btn btn-success blue-but">Generate Report</button>
			</div>
		</div> -->
		<div id="stockStatusDetailedGrid">
			<div class="row" id="listTable">
		        <div class="table-wrap">
		            <table id="stockStatusDetailedTab" class="display nowrap" style="width:100%">
			            <thead>
			                <tr>
			                    <th style="width:20px;">Sr.No.</th>
			                	<th>Date</th>
			                	<th>Transaction Number</th>
			                    <th>Transaction Type</th>
			                    <th>Increase</th>
			                    <th>Decrease</th>
			                    <th>Balance</th>
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

<script type="text/javascript" src="./resources/js/newUiJs/stockStatusDetailedReport/stockStatusDetailedReportPopulateData.js"></script>

<form name="redirectBackToSummaryReport" id="redirectBackToSummaryReport" method="POST">
    <input type="hidden" name="gstin" value="">
    <input type="hidden" name="location" value="">
    <input type="hidden" name="fromDate" value="">
    <input type="hidden" name="toDate" value="">
    <input type="hidden" name="product_id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    
</form>

