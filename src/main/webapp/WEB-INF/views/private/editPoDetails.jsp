<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
 
<script type="text/javascript" src="<spring:url value="/resources/js/maintainPoDetails/managePoDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/maintainPoDetails/editPoDetails.js"/>"></script>

 <header class="insidehead">
      <a href="<spring:url value="/poDetails" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Edit PO/ WO<span></a>
 </header>
 

<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div id="commonEditAccordionId" style="display:none;" class="acc_head">Edit PO/ WO</div>
        
          <div class="acc_content">
        	<form:form commandName="poDetails" method="post" action="./updatePoDetails">
            <!--content-->
             	<input type="hidden" id="custName" value="${poDetailsObj.poCustomerName.id }">
		        <input type="hidden" id="prdctName" value="${poDetailsObj.poAssocProductName.id }">
		        <input type="hidden" id="serviceName" value="${poDetailsObj.poAssocServiceName.id }">
				<input type="hidden"  id="selctnType" value="${poDetailsObj.selType}"/>
				  
            <div class="box-content">
              <div class="">
                <ul>
                
				  <li>
				   	<div class="form-group input-field">
						<label class="label">  
							<form:select path="poCustomerName.id" id="poCustomerName"  class="form-control" > 
			      			</form:select>
			      			<div class="label-text label-text2">Customer Name</div>
						</label>
						<span class="text-danger cust-error" id="po-cust-name">This field is required.</span>
					</div>
				  </li>
                  <li>
                 	 <div class="form-group input-field">
						<label class="label">  
						<form:input path="poNo" value="${poDetailsObj.poNo }" required="true" class="form-control"/>
						<div class="label-text label-text2">PO/ WO Number</div>
						</label>
						<span class="text-danger cust-error" id="po-no">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:input  path="poValidFromDateTemp" id="poValidFromDateTemp"  class="form-control datepicker" value="${poDetailsObj.poValidFromDateTemp}" required="true"/>
							<div class="label-text label-text2">PO/WO Valid From Date</div>
						</label>
						<span class="text-danger cust-error" id="po-from-date">This field is required.</span>
					</div>
				  </li> 
				  <li>
				  	<div class="form-group input-field">
						<label class="label">
						<form:input id="poValidToDateTemp" path="poValidToDateTemp"  class="form-control datepicker" value="${poDetailsObj.poValidToDateTemp}" required="true" />  
							<div class="label-text label-text2">PO/WO Valid To Date</div>
						</label>
						<span class="text-danger cust-error" id="po-to-date">This field is required.</span>
					</div>
				  </li>
				  
				  <li>
				  	<div class="form-group input-field">
					   <c:if test="${poDetailsObj.selType == 'Service'}">
	                  	<div class="radio-inline text-center">
	                            <span>Type </span>
	                            <div class="rdio rdio-success">
	                                <input type="radio" name="selType" value="Product" id="Product"  >
	                                <label for="Product">Goods</label>
	                            </div>
	                            <div class="rdio rdio-success">
	                                <input type="radio" name="selType" value="Service" id="Service" checked="checked" >
	                                <label for="Service">Service</label>
	                            </div>
	                    </div> 
	                  </c:if>
					  <c:if test="${poDetailsObj.selType == 'Product'}">
					   <div class="radio-inline text-center">
	                            <span>Type </span>
	                            <div class="rdio rdio-success">
	                                <input type="radio" name="selType" value="Product" id="Product" checked="checked" >
	                                <label for="Product">Goods</label>
	                            </div>
	                            <div class="rdio rdio-success">
	                                <input type="radio" name="selType" value="Service" id="Service"  >
	                                <label for="Service">Service</label>
	                            </div>
	                    </div> 
	                  </c:if>
                	</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:select path="poAssocServiceName.id" id="poAssocServiceName" class="form-control" >
					  		</form:select>
					  		<div class="label-text label-text2"></div>
						</label>
						<span class="text-danger cust-error" id="po-service">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:select path="poAssocProductName.id" id="poAssocProductName" class="form-control" > 
			      		</form:select>
			      		<div class="label-text label-text2"></div>
						</label>
						<span class="text-danger cust-error" id="po-product">This field is required.</span>
					</div>	
				  </li>	
				  
				 
				   <form:hidden path="id" value="${poDetailsObj.id }"/>
				   <form:hidden path="refUserId" value="${poDetailsObj.refUserId }"/>
				   <form:hidden path="createdBy" value="${poDetailsObj.createdBy }"/>
				   <form:hidden path="createdOn" value="${poDetailsObj.createdOn }"/>
				   <form:hidden path="status" value="${poDetailsObj.status }"/>
				   <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
	      	  
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" id="poSubmitBtn"  class="btn btn-primary">Update</button>
            	<button id="editCancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button>
            </div>
            </form:form>
            </div>
            
          </div>
        </div>
      <!--01 end-->
      </div>
      <!--customer details-->
      <div class="cust-wrap edit-Page-List">
		
		<div class="dnynamicPODetails" id="toggle">								
		</div>
		
   </div>
</div>	

<form name="poDetail" method="post">
    <input type="hidden" name="id" value="">
     <input type="hidden" name="refUserId" value="">
</form>

