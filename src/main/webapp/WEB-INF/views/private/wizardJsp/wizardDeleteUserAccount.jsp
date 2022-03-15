<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/deleteUserAccount/wizardDeleteUserAccount.js"/>"></script>

<c:choose>
    <c:when test="${empty response}"> 
     
    </c:when> 
    <c:otherwise> 
        <c:choose>
            <c:when test="${response == 'Account Deleted Successfully'}"> 
             <script type="text/javascript">
	bootbox.alert({
		 	message: "${response}", 
		 	callback: function(){ 
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


<section class="insidepages">	
			<div class="breadcrumbs" id="listHeader">
				<div class="col-md-12" id="listheader1">
		         	<a href="<spring:url value="/wHome#master"/>">Home</a> <span>&raquo;</span> Delete Account
		 		</div>
			</div>
			<div id="deleteAcc">
				<form id="deleteAccount"  method="post" action="./wizardDeleteUserAccount">
				
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				
				<br>
			    	<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text">Reason of Account Deletion</div>
							  	<select class="form-control" id="reasonOfDeletion" name="reasonOfDeletion">
									<option value="Select Reason">Select Reason</option>
									<option value="Not a genuine user">Not a genuine user</option>
									<option value="Not Satisfied with Application">Not Satisfied with Application</option>
									<option value="Will use in Future">Will use in Future</option>
									<option value="Other">Other</option>
								</select>
							  	<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
							</div>
							<div class="det-row-col astrick" id="divFeedBack" >	
								<div class="label-text">Feedback</div>							
								<textarea class="form-control" id="userFeedback" name="userFeedback" rows="5"></textarea>  
							 	<span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
							</div>	
						</div>
						</div>
						<div class="insidebtn"> 	       	
							<button type="button" id="deleteBtn" class="btn btn-primary">Delete</button> 
						<!-- 	<a  class="btn btn-secendory" href="<spring:url value="/home" />" > Cancel</a> -->
						</div>
						</form>
						</div>
			
			</section>
