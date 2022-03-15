<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/manageOffers/addManageOffers.js"/>"></script>

 <header class="insidehead">
	 <a href="<spring:url value="/home" />" class="btn-back"><i class="fa fa-angle-left" ></i> <span>Manage Offers</span></a>
 </header>
 

<div class="container">
  <div class="box-border">
  <form:form commandName="manageOffers" id="offerForm" method="post" action="./addManageOffers"><!--  onsubmit="return val()" -->
    <div class="accordion_example2 no-css-transition hide hidden">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head">Add Offer</div>
          <div class="acc_content">
            <!--content-->
            <div class="box-content">
              <div class="comm-input">
                <ul>
             
				  <li>
					<label class="label">  
						<input type="text" />  
						<div class="label-text">Offer Name</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Offer Type</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="number"  />  
						<div class="label-text">Offer Type Id</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Discount In</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Discount Value</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Discount Valid Date</div>
					</label>
				  </li>
                 
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button  class="blue-but" id="saveBtn">Save</button><button class="blue-but white-but">Cancel</button></div>
          </div>
        </div>
      <!--01 end-->
      </div>

      <div class="addCustomer">
      	<div class="box-content">
              <div class="">
                <ul>
                   <li>
                     <div class="form-group input-field">
						<label class="label">  
							<form:input path="offerName"  id="offerNameId" required="true"  class="form-control"/>
							<div class="label-text label-text2">Offer Name</div>
						</label>
						<span class="text-danger cust-error" id="ser-name">This field is required.</span>
					 </div>
				   </li>
				   <li>
                      <div class="radio-inline text-center">
                            <span>Offer for</span>
                            <div class="rdio rdio-success">
                                <input type="radio" name="offerType" value="Product" id="radio1" checked="checked" />
                                <label for="radio1">Goods</label>
                            </div>
                            <div class="rdio rdio-success">
                                <input type="radio" name="offerType" value="Service" id="radio2" />
                                <label for="radio2">Services</label>
                            </div>
                       </div>
                    </li>
                    <li>
                      <div class="form-group input-field">
						<label class="label">  
							<select name="offerTypeId" class="form-control" id="selectOffer"  class="form-control" >
                    		</select>
							<div class="label-text"></div>
						</label>
					  </div>
				  </li>
                  <li>      
				   	<div class="radio-inline text-center">
                            <span>Discount In</span>
                            <div class="rdio rdio-success">
                                <input type="radio" name="discountIn" value="Value" id="radio3" checked="checked" />
                                <label for="radio3">Value</label>
                            </div>
                            <div class="rdio rdio-success">
                                <input type="radio" name="discountIn" value="Percentage" id="radio4"/>
                                <label for="radio4">Percentage</label>
                            </div>
                    </div>
                  </li>
				  <li>
				    <div class="form-group input-field">
						<label class="label">  
							<form:input path="discountValue" id="discountValue" required="true" class="form-control"/>
							<div class="label-text label-text2">Discount</div>
						</label>
						<span class="text-danger cust-error" id="contact-no-req">This field is required</span>
					</div>
				  </li>
                        
                  <li>
                    <div class="form-group input-field">
						<label class="label">  
						 	<form:input  path="discountValidDateInString" id="offerDate" required="true" class="form-control"/>
							<div class="label-text label-text2">Discount Valid Date</div>
						</label>
					</div>
				  </li> 
                        
                </ul>
              </div>
            </div>
            <!--content end-->
            <div class="com-but-wrap"><button type="submit" class="btn btn-primary" id="offerSaveBtn">Save</button>
            <button id="cancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button></div>
      </div>
       </form:form>
      <!--customer details-->
      <div class="cust-wrap">
		
		<div class="dnynamicSecondaryUsers" id="toggle">								
		</div>
      </div>
     
  </div>
</div>

<div class="fixed-action-btn">
	<a class="btn btn-floating btn-large" title="Add Offer" id="addCustomer">
    	<img src="<spring:url value="/resources/images/home/ic_add_customer.svg" />">
  	</a>
</div>

<form name="editManageOffers" id="editManageOffers" method="post">
    <input type="hidden" name="id" value="">
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/manageOffers/manageOffers_UI_Validation.js"/>"></script>

<!--common end-->


