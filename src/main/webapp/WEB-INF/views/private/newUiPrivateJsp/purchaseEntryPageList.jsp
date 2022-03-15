<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
  
<section class="block">
	<div class="loader"></div>
	<div class="container">		 
	    <%-- <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/home#doc_management" />"><strong>Document1 Management</strong></a> &raquo; <strong>Purchase Entry History</strong>
	        </div>
	    </div> --%>
	    	     <div class="page-title">
                        <a href="<spring:url value="/home#doc_management"/>" class="back"><i class="fa fa-chevron-left"></i></a><h3 id="internalBreadCrum">Purchase Entry History</h3>
         </div>
		<div class="form-wrap">
			<div class="row">
				<div class="col-md-12 button-wrap">	
					<a id="addpurchaseentries" href="./generatePurchaseEntryInvoice" class="btn btn-success blue-but" style="width: auto;"><strong>Add</strong></a>
				</div>
			</div>
		</div>
	    
  		<div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="purchaseHistoryTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                           <!--  <th>Sr.No.</th> -->
	                            <th>Invoice No.</th>
	                            <th>Transaction Date</th>
	                            <th>Created Against</th>
	                            <th>Invoice Value</th>
	                            
	                        </tr>
	                    </thead>
	                    <tbody>
	                       
	                    </tbody>
	            </table>
	        </div>
	    </div>		    		
	</div>
</section>

<form name="managePurchaseEntries" method="post">
    <input type="hidden" name="id" value="">    
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/purchaseEntryInvoice/purchaseEntryList.js"/>"></script>
