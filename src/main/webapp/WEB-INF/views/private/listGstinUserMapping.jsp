<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/gstinUserMapping/addGstinUserMapping.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/gstinUserMapping/manageGstinUserMapping.js"/>"></script> 
<header class="insidehead">
      <a href="<spring:url value="home" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Add GSTIN User Mapping<span></a>
 </header>


<div class="container">
  <div class="box-border">
   <form:form commandName="gstinUserMapping" id="saveUserGstin" method="post" action="./addGstinUserMapping"><!--  onsubmit="return val()" -->
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
						<div class="label-text">User Name</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">GSTIN Number</div>
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
              <div class="">
                <ul>
                   <li>
                   	<div class="form-group input-field ">
						<label class="label">  
							<form:select path="referenceUserId" id="selectUser" class="form-control">
							
                   			</form:select>
							<div class="label-text label-text2">Choose Employee</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field ">
						<label class="label">  
							<form:select path="gstinId" id="selectGSTINDetail" multiple="true" class="form-control">
							
                   			</form:select> 
							<div class="label-text label-text2">GSTIN's</div>
						</label>
						<span class="text-danger cust-error" id="selectGSTINDetail-req"></span>
					</div>
				  </li>
				  
                </ul>
              </div>
            </div>
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary" id="submitGstinMapping" >Save</button>
            	<a class="btn btn-secendory" href="getGstinUserMap">Cancel</a>  
           <!--  	<button class="btn btn-primary" id="cancel" formnovalidate="formnovalidate" >Cancel</button>  -->  
            </div>
      </div>
       </form:form>
      
      <div class="cust-wrap">
		
		<div class="dnynamicGstinUserMap" id="toggle">							
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

<form name="gstinUserMapping" method="post">
    <input type="hidden" name="id" value="">
</form>