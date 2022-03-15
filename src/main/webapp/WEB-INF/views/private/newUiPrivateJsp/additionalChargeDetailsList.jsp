<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



<div class="loader"></div>
<section class="block">
	<div class="container">
	    <div class="brd-wrap">
	    	<div class="page-title" id="listheader">
                 <a href="/gstn/home#master_management" class="back"><i class="fa fa-chevron-left"></i></a>Additional Charges
           </div>
	       <div class="page-title" id="addheader" style="display: none;">
                <a href="#" id="gobacktolisttable" class="back"><i class="fa fa-chevron-left"></i></a>Add Additional Charges
           </div>
	    </div>
	   <div class="form-wrap" id="addAdditionalChargeButton">
	    <div class="row">
            <div class="col-md-12 button-wrap">			
				<button id="addAdditionalCharge"  type="button" class="btn btn-success blue-but" style="width: auto;">Add</button>
			</div>
		</div>
		</div>
	    
  		<div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="additionalChargesValuesTab" class="display nowrap" style="width:100%">
	                    <thead>
	                        <tr>
	                            <th>Name</th>
	                            <th>Charges</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                       
	                    </tbody>
	            </table>
	        </div>
	    </div>		    
	    
	    <div class="form-wrap"  id="addAdditionalChargeDetails">
			<form:form commandName="additionalChargeDetails" method="post" id="addChargeForm" action="./addAdditionalCharges">
				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />			    	
				<div class="row">	
					<div class="col-md-6">
						<label for="label">Name<span style="font-weight: bold;color: #ff0000;"> *</span></label>
					  	<form:input path="chargeName" id="chargeNameValue" maxlength="100"/>
					  	<span class="text-danger cust-error" id="charge-name-req">This field is required.</span> 					
					</div>
					
					<div class="col-md-6">
						<label for="label">Value<span style="font-weight: bold;color: #ff0000;"> *</span></label>
					  	<form:input path="chargeValue" id="chargeValuedetail" maxlength="18" value="0"/>
					  	 <span class="text-danger cust-error" id="charge-value-req">This field is required</span> 
					</div> 										
				</div>						
				<input type="hidden" id="editPage" value="false" />
				<input type="hidden" name="tempUom" id="tempUom">
				<div class="row">	
		      		<div class="col-md-12 button-wrap">
		      			<button type="submit" class="btn btn-success blue-but" id="chargesSubmitBtn" formnovalidate="formnovalidate" style="width: auto;">Save</button>
                    </div>
                </div>
	  		</form:form> 
	    </div>
	</div>
</section>

<form name="editAdditionalChargeDetails" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
   	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/addadditionalChargeDetails/manageAdditionalChargeDetails.js"/>"></script>