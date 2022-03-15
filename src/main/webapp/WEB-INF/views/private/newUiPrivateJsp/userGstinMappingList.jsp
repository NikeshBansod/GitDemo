<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>

<%@include file="../common-alert.jsp"%>



<div class="loader"></div>
<section class="block">
	<div class="container">
		<%--   <div class="brd-wrap">
	    	<div  id="listheader">
	       		<a href="<spring:url value="/home#master_management"/>"><strong>Master Management</strong></a> &raquo; <strong>GSTIN User Mapping</strong>
	        </div>
	        <div  id="addheader">
	        	<a href="#" id="gobacktolisttable"><strong>GSTIN User Mapping</strong> </a> &raquo; <strong>Add GSTIN User</strong>
	        </div>
	    </div> --%>
		<div class="page-title" id="listheader">
			<a href="<spring:url value="/home#account_management"/>" class="back"><i
				class="fa fa-chevron-left"></i></a>GSTIN User Mapping
		</div>
		<div class="page-title" id="addheader">
			<a href="#" id="gobacktolisttable" class="back" /><i
				class="fa fa-chevron-left"></i></a>Add GSTIN User
		</div>
		<div class="form-wrap" id="addUserGstinButton">
			<div class="row">
				<div class="col-md-12 button-wrap">
					<button id="addUserGstin" type="button"
						class="btn btn-success blue-but" style="width: auto;">Add</button>
				</div>
			</div>
		</div>

		<div class="row" id="listTable">
			<div class="table-wrap">
				<table id="userGstinValuesTab" class="display nowrap"
					style="width: 100%">
					<thead>
						<tr>
							<th>Sr.</th>
							<th>Name</th>
							<th>Edit</th>
							<th>Delete</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>


		<div class="form-wrap" id="addUserGstnMappingDetails">
			<form:form commandName="userGstinMapping" id="saveUserGstin"
				method="post" action="./addUserGstinMapping">
				<input type="hidden" id="_csrf_token" name="_csrf_token"
					value="${_csrf_token}" />
				<div class="row">
					<div class="col-md-4">
						<label for="label">GSTIN<span
							style="font-weight: bold; color: #ff0000;"> *</span></label>
						<form:select path="gstinId" id="gstinId">
						</form:select>
						<span class="text-danger cust-error" id="selectUser-req">This
							field is required</span>
					</div>
					<div class="col-md-4">
						<label for="label">Store/Location/Channel/Department<span
							style="font-weight: bold; color: #ff0000;"> *</span></label>
						<form:select path="gstinAddressMapping.id"
							id="gstinAddressMapping"></form:select>
						<span class="text-danger cust-error" id="selectGSTINLocation-req">This
							field is required</span>
					</div>
					<div class="col-md-4">
						<label for="label">Choose Employee<span
							style="font-weight: bold; color: #ff0000;"> *</span></label>
						<form:select path="gstinUserIds" id="gstinUserSet" multiple="true"></form:select>
						<span class="text-danger cust-error" id="selectGSTINDetail-req"></span>

					</div>
					<input type="hidden" id="editPage" value="false" />
					<div class="row">
						<div class="col-md-12 button-wrap">
							<button type="submit" class="btn btn-success blue-but"
								id="submitGstinMapping" formnovalidate="formnovalidate"
								style="width: auto;">Save</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</section>



<form name="userGstinMapping" method="post">
	<input type="hidden" name="id" value=""> <input type="hidden"
		id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/UserGstinMapping/addUserGstinMapping.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/UserGstinMapping/manageUserGstinMapping.js"/>"></script>







