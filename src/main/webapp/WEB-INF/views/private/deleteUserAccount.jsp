<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/deleteUserAccount/deleteUserAccount.js"/>"></script>

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

<header class="insidehead">
      <a href="<spring:url value="/home" />" class="btn-back"><i class="fa fa-angle-left" id="commonEditAccordionId"></i> <!-- <span>Delete User Account<span> --> </a>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<div class="common-wrap">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head" style="display:none;">Delete User Account</div>
       
          <div class="acc_content">
           <form id="deleteAccount"  method="post" action="./deleteUserAccount">
           
           <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <!--content-->
            
            <div class="box-content">
              <div class="">
              <span><center><h4><b>Delete User Account</b></h4></center></span>
                <ul>
                <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
						<select class="form-control" id="reasonOfDeletion" name="reasonOfDeletion">
						<option value="Select Reason">Select Reason</option>
						<option value="Not a genuine user">Not a genuine user</option>
						<option value="Not Satisfied with Application">Not Satisfied with Application</option>
						<option value="Will use in Future">Will use in Future</option>
						<option value="Other">Other</option>
						</select>
                   			<div class="label-text label-text2">Reason of Account Deletion</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
				  <li>
				 	 <div class="form-group input-field " id="divFeedBack">
				 	 <label class="label">  
						<textarea class="form-control" id="userFeedback" name="userFeedback" rows="5"></textarea>
							<div class="label-text label-text2">Feedback</div>
						</label>
						<span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
					</div>
				  </li>
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="button" id="deleteBtn" class="btn btn-primary">Delete</button>
            	<a  class="btn btn-secendory" href="<spring:url value="/home" />" > Cancel</a>
            </div>
             </form>
            </div>
           
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap">
		
		<div class="dnynamicProducts" id="toggle">								
		</div>
		
   </div>
</div>