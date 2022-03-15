<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="../private/common-alert.jsp" %>

<header class="insidehead">
   <a href="<spring:url value="/idt/idthome" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Add Notifications<span></a>
</header>


<script type="text/javascript" src="<spring:url value="/resources/js/ckeditor/ckeditor.js" />"></script>

<main>
    <section class="block searchBox">
		<div class="container">
			<form method="post" id="custForm" name="custForm" action="./iSubmitnotifications"><!--  -->
  				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<textarea id="notifyBody" name="notifyBody"></textarea>
				
				<div class="form-group input-field text-center">					
					 <button id="addBtn" class="btn btn-primary" >Save</button>
					 <a id="cancelBtn"  class="btn btn-secendory">Cancel</a>
				</div>
			</form>
		</div>
    </section>
</main>
 <%-- <script type="text/javascript" src="<spring:url value="/resources/js/notifications/addNotifications.js" />"></script>  --%>
<script>
	var editor=CKEDITOR.replace('notifyBody',{
		
	    toolbar: [
	              //{ name: 'document', items: [ 'Source', '-', 'NewPage', '-' ] },      // Defines toolbar group with name (used to create voice label) and items in 3 subgroups.
	              [ 'Cut', 'Copy', 'Paste', '-', 'Undo', 'Redo' ],                  // Defines toolbar group without name.
	              '/',                                                                                                                          // Line break - next group will be placed in new line.
	              { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic' ] },
	              { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList' ] },
	        ] 
	
	});
</script>

