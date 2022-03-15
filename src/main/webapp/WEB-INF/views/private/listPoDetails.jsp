<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>
 
<script type="text/javascript" src="<spring:url value="/resources/js/maintainPoDetails/managePoDetails.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/maintainPoDetails/addPoDetails.js"/>"></script>


 <header class="insidehead">
      <a href="<spring:url value="/home" />" class="btn-back"><i class="fa fa-angle-left"></i> <span>Manage PO/ WO<span></a>
 </header>
 
 
<div class="container">
  <div class="box-border">
  <form:form commandName="poDetails" method="post" action="./addPoDetails">
    <div class="accordion_example2 no-css-transition hide hidden">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head">Add PO Detail</div>
          <div class="acc_content">
            <!--content-->
            <div class="box-content">
              <div class="comm-input">
                <ul>
             
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">PO No</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">PO Valid From Date</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">PO Valid To Date</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Customer Name</div>
					</label>
				  </li>
				  
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" class="blue-but">Save</button><button class="blue-but white-but">Cancel</button></div>
          </div>
        </div>
      <!--01 end-->
      </div>

      <div class="addCustomer">
      	<div class="box-content">
              <div class="">
                <ul>
                 <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:select path="poCustomerName.id" id="poCustomerName" class="form-control"/>  
							<div class="label-text label-text2">Customer Name</div>
						</label>
						<span class="text-danger cust-error" id="po-cust-name">This field is required.</span>
					</div>
				  </li>
                 <li>
                 	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="poNo" id="poNo" maxlength="40" required="true" class="form-control"/>
							<div class="label-text label-text2">PO/ WO Number</div>
						</label>
						<span class="text-danger cust-error" id="po-no">This field is required.</span>
					</div>
				  </li>
				  <li>
				    <div class="form-group input-field mandatory">
						<label class="label">  
						 	<form:input  path="PoValidFromDateTemp" id="poValidFromDateTemp"  class="form-control datepicker" placeholder="Valid From"/>
							<div class="label-text label-text2">PO/WO Valid From date</div>
						</label>
						<span class="text-danger cust-error" id="po-from-date">This field is required.</span>
					</div>
				  </li> 
				  
				  <li>
				  	<div class="form-group input-field mandatory">
                        <label class="label">
                            <form:input id="poValidToDateTemp" path="PoValidToDateTemp"  class="form-control datepicker" placeholder="Valid To"/>
                            <div class="label-text label-text2">PO/WO Valid To date</div>
                        </label>
                        <span class="text-danger cust-error" id="po-to-date">This field is required.</span>
                    </div>
				  
				  </li>
				 
				  <li>
				    <div class="radio-inline text-center">
                            <span>Type </span>
                            <div class="rdio rdio-success">
                                <input type="radio" name="selType" value="Product" id="Product" checked="checked">
                                <label for="Product">Goods</label>
                            </div>
                            <div class="rdio rdio-success">
                                <input type="radio" name="selType" value="Service" id="Service">
                                <label for="Service">Service</label>
                            </div>                            
                     </div> 
                  	<p class="text-danger cust-error" id="prod-serv-req">This field is required.</p>
				  </li>
				  
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:select path="poAssocServiceName.id"  id="poAssocServiceName" class="form-control">	
							</form:select>
							<div class="label-text"></div>
						</label>
						<span class="text-danger cust-error" id="po-service">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:select path="poAssocProductName.id"  id="poAssocProductName"  class="form-control"/>  
							<div class="label-text"></div>
						</label>
						<span class="text-danger cust-error" id="po-product">This field is required.</span>
					</div>
				  </li>
				 
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" id="poSubmitBtn" class="btn btn-primary" >Save</button>
            <button id="cancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button></div>
      </div>
       </form:form>
      <!--customer details-->
      <div class="cust-wrap">
		
		<div class="dnynamicPODetails" id="toggle">								
		</div>
		
      </div>
     
  </div>
</div>
	 
		  
<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add Po Detail" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<!--common end-->

<form name="poDetail" method="post">
    <input type="hidden" name="id" value="">
</form>
