<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
 
<section class="block">
	<div class="loader"></div>
	<input type="hidden" name="" id="documentType" value="${documentType}"/>
	<div class="container" id="divTab">		 
	   <%--  <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/home#doc_management" />"><strong>Document Management</strong></a> &raquo; <strong id="internalBreadCrum">Document History</strong>
	        </div>
	    </div> --%>
	     <div class="page-title">
                        <a href="<spring:url value="/home#doc_management"/>" class="back"><i class="fa fa-chevron-left"></i></a><h3 id="internalBreadCrum">Document History</h3>
         </div>
		<div class="form-wrap">
			<div class="row">
				<div class="col-md-12 button-wrap">
					<c:if test="${documentType == 'invoice'}">
						<a class="btn btn-success blue-but" title="Generate Document" id="addinvoice" href="<spring:url value="./createInvoice" />" style="width: auto;">Add</a>
					</c:if>
					<c:if test="${documentType == 'billOfSupply'}">
						<a class="btn btn-success blue-but" title="Generate Document" id="addinvoice" href="<spring:url value="./createBillOfSupply" />" style="width: auto;">Add</a>
					</c:if>	
					<c:if test="${documentType == 'rcInvoice'}">
						<a class="btn btn-success blue-but" title="Generate Document" id="addinvoice" href="<spring:url value="./createRCInvoice" />" style="width: auto;">Add</a>
					</c:if>	
					<c:if test="${documentType == 'eComInvoice'}">
						<a class="btn btn-success blue-but" title="Generate Document" id="addinvoice" href="<spring:url value="./createEComInvoice" />" style="width: auto;">Add</a>
					</c:if>	
					<c:if test="${documentType == 'eComBillOfSupply'}">
						<a class="btn btn-success blue-but" title="Generate Document" id="addinvoice" href="<spring:url value="./createEComBillOfSupply" />" style="width: auto;">Add</a>
					</c:if>	
				</div>
			</div>
		</div>
	    
  		<div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="invoiceHistoryTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <!-- <th>Sr.No.</th>  -->
	                            <th>Invoice No.</th>
	                             <th>Invoice Date</th>
	                            <th>Created Against</th>
	                            <th>From</th>
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

<form name="manageInvoice" method="post">
    <input type="hidden" name="id" value="">
     <input type="hidden" name="conditionValue" value="${documentType}">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/invoiceHistory/invoice_list.js"/>"></script>