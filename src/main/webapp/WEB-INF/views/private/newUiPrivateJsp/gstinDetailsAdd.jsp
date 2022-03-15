<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



<div class="loader"></div>
<section class="block">
	<div class="container">
	 	<%--<div class="brd-wrap">
	    	 <div  id="listheader">
	       		<a href="<spring:url value="home#master_management"  />"><strong>Master Management</strong></a> &raquo; <strong>GSTIN </strong>
	        </div>
	        <div  id="addheader">
	        	<a href="#" id="gobacktolisttable"><strong>GSTIN </strong></a> &raquo; <strong>Add GSTIN</strong>
	        </div> 
	        </div>--%>
	        <div class="page-title" id="listheader">
                 <a href="<spring:url value="/home#account_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>GSTIN
             </div>
	         <div class="page-title" id="addheader">
                <a href="#" id="gobacktolisttable" class="back"/><i class="fa fa-chevron-left"></i></a>Add GSTIN
             </div>
	    
	    <div class="form-wrap"  id="addGstnButton">
	     <div class="row">
            <div class="col-md-12 button-wrap">			
				<button id="addGstnDetailsbutton"  type="button" class="btn btn-success blue-but" style="width: auto;">Add</button>
			</div>
		</div>
	    </div>
	    
	    <div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="gstinValuesTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <th>GSTIN</th>
	                            <th>Nick Name</th>
	                            <th>State</th>
	                            <th>City</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                       
	                    </tbody>
	            </table>
	        </div>
	    </div>	
	    
	    
	    <div class="form-wrap"  id="addGstnDetails">	    
			      <form:form commandName="gstinDetails" id="saveGstin" method="post" action="./addGstinDetails" ><!--  onsubmit="return val()" -->
	                   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	                   <div class="row">
	                      <div class="col-md-3">
	                      <label for="label">GSTN Login Id (As on GSTN)<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	                         <form:input path="gstnUserId" maxlength="50" id="gstnUserId" required="true"/>
	                         <span class="text-danger cust-error" id="reg-gstin-id-req">This field is required and should be in a proper format</span>
						     <span class="text-danger cust-error" id="reg-gstin-id-back-req"></span>
	                      </div>
	                      <div class="col-md-3">
	                      <!--  <label for="label">GSTN Nickname<span style="font-weight: bold;color: #ff0000;"> *</span></label> -->
	                      <label for="label">GSTN Nickname</label>
	                      <form:input path="gstnnickname" id="gstnnickname" maxlength="50" required="true"/>
	                     <!--  <span class="text-danger cust-error" id="gstn-nickname">This field is required and should be numeric.</span> -->
	                      </div>
	                      <div class="col-md-3">
	                       <label for="label">Gross Turnover<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	                      <form:input path="grossTurnover" id="grossTurnover" maxlength="13" required="true"/>
	                      <span class="text-danger cust-error" id="gross-turnover">This field is required and should be numeric.</span>
	                      </div>
	                      <div class="col-md-3">
	                      <label for="label">Current Turnover<span style="font-weight: bold;color: #ff0000;"> *</span></label>
	                      <form:input path="currentTurnover" id="currentTurnover" maxlength="13" required="true"/>
	                      <span class="text-danger cust-error" id="current-turnover">This field is required and should be numeric.</span>
	                      </div>
	                   </div>
						<div class="row">
							<div class="col-md-4">
							<label for="label">GSTIN (As on GSTN)<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:input path="gstinNo" maxlength="15" id="reg-gstin" class="form-control"/>
							<span class="text-danger cust-error" id="reg-gstin-req">This field is required and should be in a proper format</span>
						   <span class="text-danger cust-error" id="reg-gstin-back-req"></span>
							</div>
							<div class="col-md-4">
							<label for="label">GSTIN State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:select id="reg-gstin-state"  path="state" class="form-control" style="width: 365px;height: 42px;">
							</form:select>
							<span class="text-danger cust-error" id="reg-gstin-state-reg">This field is required</span>
							</div>
							<div class="col-md-4">
							<label for="label">GSTIN User Name (As on GSTN)<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:input path="gstinUname" id="gstinUname" maxlength="200"  class="form-control"/>
						    <span class="text-danger cust-error" id="reg-gstin-user-req">This field is required.</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
							<label for="label">Registered Address of GSTIN (max 500 characters)</label>
							<form:input path="gstinAddressMapping.address" maxlength="500"  id="address1" class="form-control"/>
		                  	<span class="text-danger cust-error" id="address1-req">This field is required.</span>
							</div>
							
						<div id="dynamicDiv">
							<div class="col-md-4 gstinLocation" id="dynamiclocDiv0">
							<label for="label">Store/Location/Channel/Department:1<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:input path="gstinLocationSet[0].gstinLocation" type="text" id="gstinLocation" />
		                  	<span class="text-danger cust-error" id="gstinLocation-empty"></span>
							 </div>
							 <div hidden="true"  class="col-md-4 gstinStore mandatory" id="dynamicStoreDiv0">
		                        <label for="label">Store<span style="font-weight: bold;color: #ff0000;"> *</span></label>
		                  		<form:hidden path="gstinLocationSet[0].gstinStore"  id="gstinStore" class="form-control" />
		                        <span class="text-danger cust-error" id="gstinStore-empty"></span>
		                      </div>
		                        <div class="col-md-3 button-wrap"> 
							         <label for="label-text">&nbsp;</label>
							          <button class="btn btn-success blue-but" value='Add New' id='addButton' style="width: auto;">Add New</button>
							          <button class="btn btn-success blue-but" value='Remove' id='removeButton' style="width: auto;">Remove</button>
					          </div> 
						</div>
		              </div>
						<div class="row" id="adddynamicloc" style="display: none;">
						</div>
						<div class="row">
							<div class="col-md-4">
							<label for="label">Pin Code<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:input id="pinCode" path="gstinAddressMapping.pinCode" maxlength="6" required="true" />
							<span class="text-danger cust-error" id="zip-req">This field is required and should be 6 digits</span>
		                    <span class="text-danger cust-error" id="empty-message"></span>
							</div>
							<div class="col-md-4">
							<label for="label">City<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:input id="city" readonly="true" path="gstinAddressMapping.city" required="false" class="form-control"/>
							</div>
							<div class="col-md-4">
							<label for="label">Country<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							<form:input id="country" value="India" readonly="true" path="gstinAddressMapping.country"  required="true" class="form-control"/>
			                </div>
			                 <div class="col-md-4"  hidden="true">
							 	<label for="label" >State<span style="font-weight: bold;color: #ff0000;"> *</span></label>
							    <form:hidden id="state"  path="gstinAddressMapping.state" class="form-control"/>
							   	<span class="text-danger cust-error" id="state-req">This field is required</span> 
							</div>
			                
						</div>
						<div class="row">
							<div class="col-md-12 button-wrap">
            	            <button type="submit" id="submitGstin" class="btn btn-success blue-but" style="width: auto;">Save</button>
            	            </div>
						</div>
	  		 </form:form>
	  		  <input type="hidden" id="counterButtonValue" >
			 <input type="hidden" id="dynamicLocCount"> 
	    </div>
		</div>
</section>


<form name="manageGstinDetail" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/gstinDetails/addGstinDetails.js"/>"></script> 
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/gstinDetails/pincode-autocomplete-gstn.js"/>"></script>
