<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>
<%@include file="../common-alert.jsp"%>
 <div class="loader"></div>
<section class="block">
	<div class="container">
		<input type="hidden" name="user_inv_seq_type" id="user_inv_seq_type" value="${sessionScope.loginUser.invoiceSequenceType }">
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
		<div class="page-title" >
			<a href="<spring:url value="/home"/>" class="back"><i
				class="fa fa-chevron-left"></i></a>INVOICE SEQUENCE GENERATION
		</div>
		
		<div class="form-wrap" id="">
			<div class="row text-center">
				<div class="col-md-12 diff ">
					<h4>Please select a mode of invoice numbering of your choice.</h4><br/><br/>
					<div class="radio radio-success radio-inline">
	                     <input type="radio" class="styled" name="invSeqType" value="Auto" id="radio1" checked="checked" />
	                     <label for="radio1">System Generated Invoice Number</label>
	                 </div>
	                 <div class="radio radio-success radio-inline">
				         <input type="radio" class="styled" name="invSeqType" value="Manual" id="radio2"/>
	                     <label for="radio2">Manually Entered By User</label>
	                 </div> 
				</div>
			</div>
			
			<div class="row">
	            <div class="col-md-12 button-wrap">
	                <button type="button" id="submitId" style="width:auto;" class="btn btn-success blue-but">Save</button>
	            </div>
	        </div>
			
		</div>

		
	</div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/invoiceSequence.js"/>"></script>
