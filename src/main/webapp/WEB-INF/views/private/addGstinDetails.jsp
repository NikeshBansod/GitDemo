<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/gstinDetails/addGstinDetails.js"/>"></script> 

<header class="insidehead">
      <a href="<spring:url value="home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> </a>  <!-- <span>Manage GSTIN<span> -->
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>

<div class="container">
  <div class="box-border">
  <form:form commandName="gstinDetails" id="saveGstin" method="post" action="./addGstinDetails" ><!--  onsubmit="return val()" -->
  
  	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  	
    <div class="accordion_example2 no-css-transition hide hidden">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head">Add GSTIN Details</div>
          <div class="acc_content">
            <!--content-->
            <div class="box-content">
              <div class="comm-input">
                <ul>
             
                  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">GSTIN User Name (Issued by GSTN)</div>
					</label>
				  </li>
             
				  <li>
					<label class="label">  
						<input type="text" maxlength="15" />  
						<div class="label-text">GSTIN</div>
					</label>
					<span class="text-danger cust-error" >This field is required and should be in a proper format</span>
				  </li>
				  <li>
				  
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">State</div>
					</label>
				  </li>
				 				 
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" class="blue-but">Save</button><button class="blue-but white-but ">Cancel</button></div>
          </div>
        </div>
      <!--01 end-->
      </div>

      <div class="addCustomer">
      	<div class="box-content">
      	<span><center><h4><b>Manage GSTIN</b></h4></center></span>
              <div class="">
                <ul>
                 <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="gstnUserId" maxlength="50" id="gstnUserId" required="true" class="form-control"/>
							<div class="label-text label-text2">GSTN Login Id (As on GSTN)</div>
						</label>
						<span class="text-danger cust-error" id="reg-gstin-id-req">This field is required and should be in a proper format</span>
						<span class="text-danger cust-error" id="reg-gstin-id-back-req"></span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="grossTurnover" id="grossTurnover" maxlength="13" required="true" class="form-control"/>
							<div class="label-text label-text2">Gross Turnover</div>
						</label>
						<span class="text-danger cust-error" id="gross-turnover">This field is required and should be numeric.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="currentTurnover" id="currentTurnover" maxlength="13" required="true" class="form-control"/>
							<div class="label-text label-text2">Current Turnover</div>
						</label>
						<span class="text-danger cust-error" id="current-turnover">This field is required and should be numeric.</span>
					</div>
				  </li>				  
                  <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="gstinNo" maxlength="15" id="reg-gstin" required="true" class="form-control"/>
							<div class="label-text label-text2">GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="reg-gstin-req">This field is required and should be in a proper format</span>
						<span class="text-danger cust-error" id="reg-gstin-back-req"></span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label"> 
							<form:select id="reg-gstin-state"  path="state" class="form-control">
							</form:select>						
							<div class="label-text label-text2">GSTIN State</div><!-- State -->
						</label>
						<span class="text-danger cust-error" id="reg-gstin-state-reg">This field is required</span>
					</div>
				  </li>
				  <li>
                 	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="gstinUname" id="gstinUname" maxlength="200" required="true" class="form-control"/>
							<div class="label-text label-text2">GSTIN User Name (As on GSTN)</div>						
						</label>
						<span class="text-danger cust-error" id="reg-gstin-user-req">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	 <div class="form-group input-field ">
		                  <label class="label">
		                  		<form:input path="gstinAddressMapping.address" maxlength="500"  id="address1" class="form-control"/>
		                  		<div class="label-text label-text2">Registered Address of GSTIN (max 500 characters)</div>
		                  </label>
		                  <span class="text-danger cust-error" id="address1-req">This field is required.</span>
		              </div>
		     	 </li>
		     	 <li id="dynamicList">
				 <!--  	 <div class="form-group input-field ">
		                  <label class="label">
		                  		 <div id="field">
		                  		 <form:input path="gstinLocationSet[0].gstinLocation" autocomplete="off" class="form-control" id="field1" name="prof1" type="text"  data-items="8"/>
		                  		 <button id="b1" class="btn add-more" type="button">+</button></div>
		                  		<div class="label-text label-text2">GSTIN Location</div>
		                  		<span class="input-group-btn">
		                  </label>
		                  <span class="text-danger cust-error" id="address1-req">This field is required.</span>
		              </div> -->
		              
		              <!-- <div class="form-group input-field form-horizontal">
		               	<label class="label">
					        <div class="control-group">
					            <label class="control-label" for="gstinLocation">
					               Location/Channel/Department</label>
					            <div class="controls">
					                <form:input path="gstinLocationSet[0].gstinLocation" type="text" id="gstinLocation" class="form-control" />
					                </div>
					        </div>
					        <div class="label-text label-text2">Location/Channel/Department</div>
					      </label>
					       <span class="text-danger cust-error" id="gstinLocation-empty"></span>
   				 	</div> -->
   				 	
   				 	
   				 	<div id="dynamicDiv">
   				 	<div class="form-group input-field gstinLocation mandatory" id="dynamiclocDiv0">
		                  <label class="label">
		                <!--   <div class="input-group input-field ">  -->
		                  		<form:input path="gstinLocationSet[0].gstinLocation" type="text" id="gstinLocation" class="form-control" />
		                  		<div class="label-text label-text2">Store/Location/Channel/Department: 1</div>
		                  </label>
		                  <span class="text-danger cust-error" id="gstinLocation-empty"></span>
		              </div>
		               <div hidden="true" class="form-group input-field gstinStore mandatory" id="dynamicStoreDiv0">
		                  <label class="label">
		                  		<form:hidden path="gstinLocationSet[0].gstinStore"  id="gstinStore" class="form-control" />
		                  		<div hidden="true" class="label-text label-text2">Store</div>
		                  </label>
		                  <span class="text-danger cust-error" id="gstinStore-empty"></span>
		              </div> 
   				 	</div>
   				 	<br/>
   				 		<span>
		   				  	 <input type='button' class="btn btn-primary" value='Add New' id='addButton' />
		   					 <input type='button' class="btn btn-primary" value='Remove' id='removeButton' />  
   					 	</span>
		     	 </li>
		     	 <li>		
		     		<div class="form-group input-field mandatory">
		                  <label class="label">
		                  			<form:input id="pinCode" path="gstinAddressMapping.pinCode" maxlength="6" required="true" class="form-control"/>
		                  			<div class="label-text label-text2">PIN Code</div>
		                  </label>
		                  <span class="text-danger cust-error" id="zip-req">This field is required and should be 6 digits</span>
		                  <span class="text-danger cust-error" id="empty-message"></span>
		            </div>
		          </li>
		     	  <li>			
		     		<div class="form-group input-field mandatory">
		                  <label class="label">
		                  			<form:input id="city" readonly="true" path="gstinAddressMapping.city" required="false" class="form-control"/>
		                  			<div class="label-text label-text2">City</div>
		                  </label>
		            </div>
		          </li>
		     	  <li>			
		     		<div class="form-group input-field mandatory">
			               <label class="label">
			                    <form:hidden id="state"  path="gstinAddressMapping.state" class="form-control"/>
			                   	<div hidden="true" class="label-text label-text2">State</div>
			                    	                 
			               </label>
			               <span class="text-danger cust-error" id="state-req">This field is required</span>   
			        </div>
				  </li>
				  <li>			
		     		<div class="form-group input-field">
			               <label class="label">
			                    <form:input id="country" value="India" readonly="true" path="gstinAddressMapping.country"  required="true" class="form-control"/>
			                   	<div class="label-text label-text2">Country</div>
			                    	                 
			               </label>
			               <!-- <span class="text-danger cust-error" id="country-req">This field is required</span> -->   
			        </div>
				  </li>
				  
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap">
            	<button type="submit" id="submitGstin" class="btn btn-primary">Save</button>
            	<a class="btn btn-secendory" href="addGstinDetails">Cancel</a>  
        <!--     	<button id="cancel" formnovalidate="formnovalidate" class="btn btn-primary">Cancel</button>  -->
            </div>
      </div>
       </form:form>
      <!--customer details-->
     
     
  </div>
</div>
 <div class="gstinValuesTable" style="background-color:white; padding:5px;">	
		<h4 style="text-align: center;">GSTIN List</h4>
		<table class="table table-striped table-bordered gstinValues"  id="gstinValuesTab" >
				<thead>
							<tr>
								<th><center>GSTIN no</center></th>
								<th><center>GSTIN State</center></th>
							</tr>
						</thead>
			</table>							
		
		</div>

<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add Gstin Details" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<form name="manageGstinDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<!--common end-->

<script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>