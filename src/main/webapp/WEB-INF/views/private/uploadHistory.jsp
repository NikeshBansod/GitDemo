<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>
           

<script type="text/javascript" src="<spring:url value="/resources/js/uploadToAsp/uploadHistory.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/uploadToAsp/moment.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/accordion/smk-accordion.js" />"></script>

 <header class="insidehead">
      <a href="<spring:url value="/aspMasters" />" class="btn-back"><i class="fa fa-angle-left"></i>
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a> 
     <!--   <span>Upload status<span> -->
      </a>
 </header>
  <main>
         <section class="block selectOptions">
      <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
<div class="container" >
  
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
 
      <!-- <div class="accordion_in acc_active"> -->
      <div class="acc_head"></div>
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
              <div class="card">
              <span><center><h4><b>Upload status</b></h4></center></span>
                <ul>
			    <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<select  id="gstinId" name="gstinId" class="form-control">
                   			</select>
							<div class="label-text label-text2">GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
			    </ul>
              </div>
            </div>
          </div>
     <!--    </div> -->
      </div>
     <div class="text-center">
     	<div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Show History</button>
      	</div>
     </div>
    
  </div>
</div>
	 <div class="cust-wrap">
		
		<div class="dnynamicHistoryDetails" id="toggle">								
		</div>
      </div>
      
</section>
</main>
     
<br/>
<!--common end-->
