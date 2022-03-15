<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
 
<section class="block">
	<div class="loader"></div>
	<div class="container">		 
	    <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/home#eway_management"/>"><strong>E-Way Bill Management</strong></a> &raquo; <strong>Generic E-Way Bill History</strong>
	        </div>
	    </div>
		<div class="form-wrap">
			<div class="row">
				<div class="col-md-12 button-wrap">
					<a class="btn btn-success blue-but" title="Add Generic Eway Bill" id="addGenericEwayBill" href="javascript:generateEWayBill();">Add</a>	
				</div>
			</div>
		</div>
	    
  		<div class="row" id="listTable">
	       	<input type="hidden" id="clientId" name="clientId" value="${clientId}">
			<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
			<input type="hidden" id="appCode" name="appCode" value="${appCode}">
			<input type="hidden" name="userId" id="userId" value="${userId}">
			<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">	
	        <div class="table-wrap">
	            <table id="genericEwayBillTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <th>E-way Bill No.</th>
	                            <th>Date</th>
	                            <th>Customer</th>
	                            <th>Mode</th>
	                            <th>Status</th>
	                            <th>Valid UpTo</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                       
	                    </tbody>
	            </table>
	        </div>
	    </div>		    		
	</div>
</section>

<form name="previewInvoice" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/genericEwayBill/loadWIEwayBillDatatable.js"/>"></script>
