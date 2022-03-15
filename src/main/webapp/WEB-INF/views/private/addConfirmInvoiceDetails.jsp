<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/manageSetupInvoice.js"/>"></script>
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/invoice.js"/>"></script> --%>

<c:if test="${not empty response}">
	<script type="text/javascript">
		alert('${ response}');
	</script>	
</c:if>

 <header class="insidehead">
            <a href="./home" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home.html">Confirm Invoice Details</a>
 </header>
<form:form modelAttribute="userMaster" class="form-horizontal" id="UpdateBtn" method="post" action="./updateDefaultEmailId">	
   <main>
            <section class="block generateInvoice">
                <div class="container">
                
                  <!-- General Section Starts -->          
                    <div class="card">
                       
                        <div class="form-group input-field mandatory">
                            <label class="label">
                            <div class="label-text label-text2">Organization Name</div>
                                <input type="text" name="customerDetails.custName" id="customer_name" value="${userMasterObj.organizationMaster.orgName }" required class="form-control" readonly="readonly" >  
                            </label>
                        </div>
                        
                         <div class="form-group input-field">
                            <label class="label">
                                <input type="text" name="servicePlace" id="customer_place" value="${userMasterObj.organizationMaster.address1 }" required class="form-control" readonly="readonly">  
                                
                                <div class="label-text label-text2">Address1</div>
                            </label>
                        </div>
                                                
                        <div class="form-group input-field">
                            <label class="label">
                                <input type="text" name="pincode" id="customer_place" value="${userMasterObj.organizationMaster.pinCode }" required class="form-control" readonly="readonly">  
                                
                                <div class="label-text label-text2">Pincode</div>
                            </label>
                        </div>
                        
                        <div class="form-group input-field mandatory">
                           <label class="label">
                                        <input name="deliveryPlace" class="form-control" value="${userMasterObj.organizationMaster.state }" id="selectState" readonly="true">
                    					</input>
                                       <div class="label-text label-text2">State</div>
                           </label>
                        </div> 
                        
                        <div class="form-group input-field mandatory">
                            <label class="label">
                                <input type="text" name="serviceCountry" id="customer_country" value="${userMasterObj.organizationMaster.city }" required class="form-control" readonly="readonly">  
                                 <div class="label-text label-text2">City</div>
                            </label>
                        </div>
                       
                        <div class="form-group input-field mandatory">
                           <label class="label">
                               <select class="form-control" name="deliveryCountry" id="selectCountry" readonly="readonly">
					              <option value="">Country</option>
					              <option value="India">India</option>
					              <option value="Other">Other</option>
					           </select>
                               <div class="label-text label-text2">Country</div>
                           </label>
                        </div>
                        
                       <div class="form-group input-field">
                            <label class="label">
                                <input type="text" name="landLineNumber" id="customer_place" value="${userMasterObj.organizationMaster.landlineNo }" required class="form-control" readonly="readonly">  
                                
                                <div class="label-text label-text2">Landline Number</div>
                            </label>
                        </div>
                       
                        <div class="com-but-wrap">
                        	<button type="button" class="btn btn-primary" onclick="document.location.href='./editUser'"  id="changeInvoiceBtn">Change</button>
                        </div>
                         <br>
                         
                        <div class="form-group input-field mandatory">
                            <label class="label">
                                <input type="text" name="defaultMailId" id="custEmail" value="${userMasterObj.defaultEmailId }" required class="form-control">  
                                
                                <div class="label-text label-text2">Default Mail Id</div>
                            </label>
                            <span class="text-danger cust-error" id="cust-email-format">This field is required and should be in a correct format</span>
                        </div>
                        
                     <!--  <div class="form-group input-field">
                            <label class="label">
                                <input type="file" name="logo" id="logo" accept=".jpg .png .jpeg" style="width: 500px;" /> 
                                <div class="label-text label-text2">Logo</div>
                            </label>
                        </div> 
                                   -->                                                      
                        <div class="com-but-wrap">
                        	<button type="submit" class="btn btn-primary" id="invoiceUpdateBtn">Update</button>
                        </div>
                 
                    </div>
                 <!-- General Section Ends -->    
                </div>
            </section>

        </main>
</form:form>	
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/form_submit.js"/>"></script>     
<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/service_crud.js"/>"></script>         
<script type="text/javascript" src="<spring:url value="/resources/js/generateInvoice/otherFunctions.js"/>"></script>  --%>
