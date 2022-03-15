<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/UserGstinMapping/addUserGstinMapping.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/UserGstinMapping/manageUserGstinMapping.js"/>"></script> 

<header class="insidehead">
      <a href="<spring:url value="home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> </a>  <!-- <span>Add GSTIN User Mapping<span>  -->
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>


<div class="container">
  <div class="box-border">
   <form:form commandName="userGstinMapping" id="saveUserGstin" method="post" action="./addUserGstinMapping"><!--  onsubmit="return val()" -->
		<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition hide hidden">
      
      <div class="accordion_in acc_active">
        <div class="acc_head">Map GSTIN</div>
          <div class="acc_content">
           
            <div class="box-content">
              <div class="comm-input">
                <ul>
				 <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">GSTIN</div>
					</label>
				  </li>				  
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">GSTIN Location</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">User Name</div>
					</label>
				  </li>
                 
                </ul>
              </div>
            </div>
            
            <div class="com-but-wrap"><button type="submit" class="blue-but">Save</button><button class="blue-but white-but">Cancel</button></div>
          </div>
        </div>
      
      </div>

      <div class="addCustomer">
      	<div class="box-content">
      	<span><center><h4><b>Add GSTIN User Mapping</b></h4></center></span>
              <div class="">
                <ul>
                   <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:select path="gstinId" id="gstinId" class="form-control">
                   			</form:select>
							<div class="label-text label-text2">GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
				  <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
						
							<form:select path="gstinAddressMapping.id" id="gstinAddressMapping" class="form-control">
                   			</form:select>
							<div class="label-text label-text2">Store/Location/Channel/Department</div>
						</label>
						<span class="text-danger cust-error" id="selectGSTINLocation-req">This field is required</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
                
						  <form:select path="gstinUserIds" id="gstinUserSet" multiple="true" class="form-control">
                   			</form:select>  
                   			
							<div class="label-text label-text2">Employee</div>
						</label>
						<span class="text-danger cust-error" id="selectGSTINDetail-req"></span>
					</div>
				  </li>
				  <input type="hidden" id="editPage" value="false" />
                </ul>
              </div>
            </div>
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary" id="submitGstinMapping" >Save</button>
            	<a class="btn btn-secendory" href="getUserGstinMap">Cancel</a>  
           <!--  	<button class="btn btn-primary" id="cancel" formnovalidate="formnovalidate" >Cancel</button>  -->  
            </div>
      </div>
       </form:form>
      
      <div class="userGstinValuesTable">	
		<div class="card">
		<h4 style="text-align: center;">GSTIN -User Mapping</h4>
		<table class="table table-striped table-bordered userGstinValues"  id="userGstinValuesTab" >
				<thead>
					<tr>
						<th><center>GSTIN</center></th>
						<th><center>User Name</center></th>
					</tr>
				</thead>
			</table>							
		</div>
		</div>
     
  </div>
</div>

<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add Product" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<!--common end-->

<form name="userGstinMapping" method="post">
    <input type="hidden" name="id" value="">
	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>