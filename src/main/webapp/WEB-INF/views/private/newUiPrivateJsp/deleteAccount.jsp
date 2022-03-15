<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>
<%@include file="../common-alert.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/deleteUserAccount/deleteUserAccount.js"/>"></script>

<c:choose>
	<c:when test="${empty response}">

	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${response == 'Account Deleted Successfully'}">
				<script type="text/javascript">
					bootbox
							.alert({
								message : "${response}",
								callback : function() {
									window.location.href = "${pageContext.request.contextPath}/logout";
								}
							});
				</script>
			</c:when>
			<c:otherwise>
				<script type="text/javascript">
					bootbox.alert('${response}');
				</script>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<form id="deleteAccount" method="post" action="./deleteUserAccount">
	<input type="hidden" id="_csrf_token" name="_csrf_token"
		value="${_csrf_token}" />

	<div class="loader"></div>
	<!-- Body content/sections Starts here -->
	<section class="block">
		<div class="container">
			<!-- 	     <div class="brd-wrap">
	         <a href="./home"><strong>Home</strong></a> &raquo; <a href="./deleteUserAccount"><strong>Delete Account</strong></a>
	     </div> -->
			<div class="page-title" id="listheader">
				<a href="<spring:url value="/home#account_management"/>" class="back"><i
					class="fa fa-chevron-left"></i></a>Delete Account
			</div>
			<div class="form-wrap">
				<div class="row">
					<div class="col-md-12">

						<label for="label">Reason of Account Deletion<span
							style="font-weight: bold; color: #ff0000;"> *</span> <select
							id="reasonOfDeletion" name="reasonOfDeletion">
								<option value="Select Reason">Select Reason</option>
								<option value="Not a genuine user">Not a genuine user</option>
								<option value="Not Satisfied with Application">Not
									Satisfied with Application</option>
								<option value="Will use in Future">Will use in Future</option>
								<option value="Other">Other</option>
						</select>
						</label>

					</div>
					<div class="col-md-12 input-field" id="divFeedBack">
						<label for="label">Feedback<span
							style="font-weight: bold; color: #ff0000;"> *</span> <textarea
								class="form-control" id="userFeedback" name="userFeedback"
								rows="5" required class="form-control"></textarea>
						</label> <span class="text-danger cust-error" id="selectGSTINLocation-req">Please
							enter feedback details</span>

					</div>

				</div>
			</div>
			<div class="col-md-12 button-wrap">
				<button type="button" id="deleteBtn" class="btn  blue-but"
					style="width: auto;">Delete</button>
			</div>
		</div>


	</section>
</form>