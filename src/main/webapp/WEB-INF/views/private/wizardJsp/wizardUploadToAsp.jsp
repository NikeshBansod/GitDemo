<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

            <c:if test="${apiResponse == 'SUCCESS'}"> 
             <script type="text/javascript">
				bootbox.alert('${successMsg}');
			 </script>
            </c:if> 
           <c:if test="${apiResponse == 'error'}">
             <script type="text/javascript">
             bootbox.alert('Upload failed, Please try again after sometime.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'invalid'}">
             <script type="text/javascript">
             bootbox.alert('Can not update at this time please try after some time.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'emptyList'}">
             <script type="text/javascript">
             bootbox.alert('NO Invoice Generated for Selected GSTIN Within Selected Time Period');
			</script>
             </c:if> 

	


	<section class="insidepages">	
				<div class="breadcrumbs" id="listHeader">
					<div class="col-md-12" id="listheader1">
			         	<a href="<spring:url value="/wHome"/>">Home</a> <span>&raquo;</span> Compliance
			 		</div>
			 		
				</div>
				
				<br>
				<div id="wizardUploadData">
				<form  method="post" id="uploadAsp" action="" >
				
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
				<input type="hidden" id="return_gstrtype" name="return_gstrtype" value="${return_gstrtype}" />
				<input type="hidden" id="return_gstinId" name="return_gstinId" value="${return_gstinId}" />
				<input type="hidden" id="return_financialperiod" name="return_financialperiod" value="${return_financialperiod}" />
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
					<input type="hidden" id="goback" name="goback" value="${goback}" />
				
				<div class="account-det">
						<div class="det-row">	
							<div class="det-row-col astrick">
								<div class="label-text"align="center">Choose GSTIN</div>
							  	<select  id="gstinId" name="gstinId" class="form-control"></select>      
							  	<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
							</div>
							<div class="det-row-col astrick">
								<div class="label-text"align="center">Select Financial Period</div>
							  	<select  id="financialPeriod" name="financialPeriod"  class="form-control">   </select>   
							  	<span class="text-danger cust-error" id="master-desc">This field is required</span>
							</div>
							
							<div class="det-row-col astrick" align="center">
								<div class="label-text">Return Type</div>
							  	<select  id="gstrtype" name="gstrtype"  class="form-control" >  </select>
							  	<span class="text-danger cust-error" id="master-desc">This field is required</span>
							  	<!-- <option  value="volvo">GSTR-1</option>
  								<option disabled value="saab">GSTR-2</option>
  								<option disabled value="mercedes">GSTR-3B</option> -->
    
							  	<span class="text-danger cust-error" id="gsttype"hidden=true>This field is required</span>
							</div>
							</div>
						
							</div>
							<div class="inv-mgt-cont main_btn" aria-hidden="true" id="gstr1" style="display:none">
			           <a class="blue_tab_bttn " data-id="notification" id="userLogin" >
			            	<i class="fa fa-upload" aria-hidden="true" ></i>
			              	<span>Upload </br> </span>
			           </a>
			           
			            <a id="L0" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Draft</span>
			           </a> 
			             <a id="submit" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Submit</span>
			           </a>
			             <a id="File" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>File</span>
			           </a>
			           
			           
			        </div>
			       <!--  <div class="inv-mgt-cont main_btn" aria-hidden="true" id="gstr3" style="display:none">
			           <a class="blue_tab_bttn " data-id="notification" id="userLogin" >
			            	<i class="fa fa-upload" aria-hidden="true" ></i>
			              	<span>Prepare </br> </span>
			           </a>
			           
			            <a id="L0" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Draft</span>
			           </a> 
			           <a id="L0" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Fetch and Download</span>
			           </a> 
			           <a id="L0" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Offset Tax Liability</span>
			           </a>
			           <a id="L0" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>File to GSTN</span>
			           </a>
			           <a id="L0" data-id="notification" class="blue_tab_bttn">
			           		<i class="fa fa-file" aria-hidden="true"></i>
			              	<span>Sync with GSTN</span>
			           </a>
			           
			           
			        </div> -->
							 
							</form>
							
							</div>
							
							<div class="insidebtn"> 	        	
								<div class="com-but-wrap"><button  class="sim-button button5" id="proceed" >Proceed</button></div>
								
							 </div>
							  
							</section>
							
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/uploadToAsp/wizardUploadToAsp.js"/>"></script>