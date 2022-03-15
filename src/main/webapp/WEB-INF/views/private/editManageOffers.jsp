<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<%@include file="common-alert.jsp" %>

<script type="text/javascript" src="<spring:url value="/resources/js/manageOffers/editManageOffers.js"/>"></script>

<header class="insidehead">
      <a href="<spring:url value="/addManageOffers" />" class="btn-back"><i class="fa fa-angle-left" ></i> <span>Edit Manage Offers<span></a>
 </header>

<div class="container">
  <div class="box-border">
    <div class="accordion_example2 no-css-transition">
      <!--01-->
      <div class="accordion_in acc_active">
        <div class="acc_head" style="display:none;" id="commonEditAccordionId">Edit Manage Offer</div>
       
          <div class="acc_content">
           <form:form commandName="manageOffers" method="post" action="./updateManageOffers">
            <!--content-->
            <input type="hidden"  id="selctnType" value="${manageOffersObj.offerType}"/>
            <input type="hidden"  id="offerId" value="${manageOffersObj.offerTypeId}"/>
            <div class="box-content">
              <div class="">
                <ul>
                  <li>
                  	<div class="form-group input-field">
						<label class="label">  
							<form:input path="offerName" value="${manageOffersObj.offerName }" required="true" class="form-control"/>
							<div class="label-text label-text2">Offer Name</div>
						</label>
					</div>
				  </li>
				  <li>
					<div class="form-group input-field">
						 <c:if test="${manageOffersObj.offerType == 'Service'}">
		                  <div class="radio-inline text-center">
		                            <span>Type </span>
		                            <div class="rdio rdio-success">
		                                <input type="radio" name="offerType" value="Product" id="radio1" />
		                                <label for="Product">Goods</label>
		                            </div>
		                            <div class="rdio rdio-success">
		                                <input type="radio" name="offerType" value="Service" id="radio2" checked="checked"/>
		                                <label for="Service">Service</label>
		                            </div>
		                        </div> 
		                  </c:if>
						  <c:if test="${manageOffersObj.offerType == 'Product'}">
						   <div class="radio-inline text-center">
		                            <span>Type </span>
		                            <div class="rdio rdio-success">
		                                <input type="radio" name="offerType" value="Product" id="radio1" checked="checked"/>
		                                <label for="radio1">Goods</label>
		                            </div>
		                            <div class="rdio rdio-success">
		                                <input type="radio" name="offerType" value="Service" id="radio2"/>
		                                <label for="radio2">Service</label>
		                            </div>
		                        </div> 
		                  </c:if>
	                  </div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
				  		<label class="label">  
							<form:select path="offerTypeId" class="form-control" id="selectOffer">
                    		</form:select>
						</label>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
				  		<c:if test="${manageOffersObj.discountIn == 'Value'}">
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
                        </c:if>
                        <c:if test="${manageOffersObj.discountIn == 'Percentage'}">
	                        <div class="radio-inline text-center">
	                            <span>Discount In</span>
	                            <div class="rdio rdio-success">
	                                <input type="radio" name="discountIn" value="Value" id="radio3" />
	                                <label for="radio3">Value</label>
	                            </div>
	                            <div class="rdio rdio-success">
	                                <input type="radio" name="discountIn" value="Percentage" id="radio4" checked="checked"  />
	                                <label for="radio4">Percentage</label>
	                            </div>
	                        </div>
                        </c:if>
                     </div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:input path="discountValue" value="${manageOffersObj.discountValue}" required="true" class="form-control"/>
							<div class="label-text label-text2">Discount</div>
						</label>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field">
						<label class="label">  
							<form:input  path="discountValidDateInString" id="offerDate" value="${manageOffersObj.discountValidDateInString}" required="true" class="form-control"/>
							<div class="label-text label-text2">Discount Valid Date</div>
						</label>
					</div>
				  </li> 
				  
			    <form:hidden path="id" value="${manageOffersObj.id }"/>
			    <form:hidden path="referenceId" value="${manageOffersObj.referenceId }"/>
			    <form:hidden path="createdOn" value="${manageOffersObj.createdOn }"/>
			    <form:hidden path="createdBy" value="${manageOffersObj.createdBy }"/>
			    <form:hidden path="discountValidDate" value="${manageOffersObj.discountValidDate }"/>
			    <form:hidden path="status" value="${manageOffersObj.status }"/>
			    <input type="hidden" id="actionPerformed" value="${editActionPerformed}"/>
			  
                </ul>
              </div>
            </div>
           
            <!--content end-->
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary">Update</button>
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
		
		<div class="dnynamicSecondaryUsers" id="toggle">								
		</div>
		
   </div>
</div>
  
<form name="manageOffersedit" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="userId" value="">
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/manageOffers/manageOffers_UI_Validation.js"/>"></script>
