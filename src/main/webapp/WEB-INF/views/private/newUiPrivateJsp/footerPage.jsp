<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>

<%@include file="../common-alert.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/footer.js"/>"></script>

<%-- <header class="insidehead">
      <a href="<spring:url value="home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--   <span>Feedback Form<span> --> </a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header> --%>


<div class="loader"></div>

<div class="min-height500">
	<section class="block">
		<div class="container">
			<%--  <div class="brd-wrap">
	        <a href="<spring:url value="/home#master_management" />"><strong>Master Management</strong></a> &raquo; <strong>Footer Form</strong>
	     </div> --%>
			<div class="page-title" id="listheader">
				<a href="<spring:url value="/home#account_management"/>" class="back"><i
					class="fa fa-chevron-left"></i></a>Footer Form
			</div>
			<div class="box-border">
				<form action="./addFooter" method="post" id="addFooterForm">
					<!-- ./addFooter -->
					<input type="hidden" id="_csrf_token" name="_csrf_token"
						value="${_csrf_token}" />
					<div class="form-wrap">
						<div class="row">

							<div class="col-md-12">
								<label for="label">Footer<span
									style="font-weight: bold; color: #ff0000;"> *</span>
								</label> <label class="label"> <textarea
										placeholder="Max 150 characters" name="footer" id="footerDesc"
										maxlength="150" rows="2" required class="form-control"
										style="height: 80px;">${fn:escapeXml(loginUser.footer) }</textarea>
								</label> <span class="text-danger cust-error" id="feedback-query-req">This
									field is required.</span>
							</div>


						</div>
					</div>

					<div class="col-md-12 button-wrap">
						<button type="button" class="btn btn-success blue-but"
							id="footerSubmit" formnovalidate="formnovalidate"
							style="width: auto;">Save</button>

					</div>

				</form>

			</div>
		</div>
	</section>

</div>