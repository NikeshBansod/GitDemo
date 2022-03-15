<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/wizardJS/invoiceHistory/dataTables.bootstrap.min.js"/>"></script>
<link href="<spring:url value="./resources/css/wizardCss/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css">


 <header class="insidehead">
      <a href="<spring:url value="/home#master_management" />" class="btn-back"><i class="fa fa-angle-left"></i> <!--   <span>Manage Goods<span> --></a>
      <a class="logoText" href="home#master"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
 
<div class="container">
  <div class="box-border">
   <form:form commandName="product" method="post" id="prdForm" action="./addProduct">
   	<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
    <div class="accordion_example2 no-css-transition hide hidden">
      
      <div class="accordion_in acc_active">
        <div class="acc_head">Add Goods</div>
          <div class="acc_content">
           
            <div class="box-content">
              <div class="comm-input">
                <ul>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">HSN Description</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">HSN Code</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Goods Name</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Unit Of Measurement</div>
					</label>
				  </li>
				  <li>
					<label class="label">  
						<input type="text"  />  
						<div class="label-text">Rate</div>
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
              <span><center><h4><b>Manage Goods</b></h4></center></span>
                <ul>
                 <li>
	                <div class="form-group input-field mandatory">
	                  <label class="label">
	                  	<input type="text" id="search-hsn" maxlength="15"  class="form-control"/>                  	
	                  	<div class="label-text label-text2">Search By HSN Code / HSN Description</div>
	                  </label>
	                  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By HSN Code / HSN Description </span>
	                </div>	
                  </li>

				<!-- <li>
					<div class="com-but-wrap">
						<button type="submit" class="btn btn-primary"
							onclick="doFunction();" id="scanQrCode">Scan</button>
					</div>
					<div id="ScanResult"></div>
				</li> -->

				  <li>
                  	<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" disabled="disabled" id="hsnDescriptionToShow" required="true" class="form-control"/>
							<form:hidden path="hsnDescription" id="hsnDescription" required="true" />
							<div class="label-text label-text2">HSN Description</div>
						</label>
						<span class="text-danger cust-error" id="prod-hsn-desc">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<input type="text" disabled="disabled" id="hsnCodeToShow" required="true" class="form-control"/>
							<form:hidden path="hsnCode" id="hsnCode" required="true" />
							<div class="label-text label-text2">HSN Code</div>
						</label>
						<span class="text-danger cust-error" id="prod-hsn-code">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="name" id="name" maxlength="100" required="true" class="form-control"/>
							<div class="label-text label-text2">Goods Name</div>
						</label>
						<span class="text-danger cust-error" id="prod-name">This field is required.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<select class="form-control" name="unitOfMeasurement" class="form-control" id="unitOfMeasurement">
	                    	</select>
	                        <div class="label-text label-text2">Unit Of Measurement</div>
	                       </label>
						<span class="text-danger cust-error" id="prod-uom">This field is required.</span>
					</div>
				  </li>
				  <div class="form-group input-field mandatory" id="divOtherUnitOfMeasurement">
		                  		<label class="label">
		                  			<form:input path="otherUOM"  id="otherUOM" maxlength="30" class="form-control"/>
		                  			<div class="label-text label-text2">Please Specify </div>
		                  		</label>
		                  		<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
		          </div>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label">  
							<form:input path="productRate" maxlength="18" required="true" class="form-control"/>
							<div class="label-text label-text2">Goods Rate (Rate per unit of measurement)</div>
						</label>
						<span class="text-danger cust-error" id="prod-rate">This field is required and should be numeric.</span>
					</div>
				  </li>
				  <li>
				  	<div class="form-group input-field mandatory">
						<label class="label"> 
						<select class="form-control" id="productIgst" name="productIgst" class="form-control">
						</select> 
							<div class="label-text label-text2">Rate of tax (%)</div>
						</label>
						<span class="text-danger cust-error" id="prod-igst">This field is required and should be numeric.</span>
					</div>
				  </li>
				  <form:hidden path="hsnCodePkId" id="hsnCodePkId"/>
				  <input type="hidden" id="editPage" value="false" />
				  
                </ul>
              </div>
            </div>
            
            <div class="com-but-wrap">
            	<button type="submit" class="btn btn-primary"  id="prodSubmitBtn">Save</button>
            	<button id="cancel" formnovalidate="formnovalidate" class="btn btn-secendory">Cancel</button>
            </div>
      </div>
      <input type="hidden" name="tempUom" id="tempUom">
       </form:form>
     
      <div class="productValuesTable">	
		<div class="card">
		<h4 style="text-align: center;">Goods Details</h4>
		<table class="table table-striped table-bordered dnynamicProducts" id="productValuesTab" >
				<thead>
							<tr>
								<th><center>Product Name</center></th>
								<th width="70%"><center>Rate of tax(%)</center></th>
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

<form name="manageProduct" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/products/hsnCodeAjax.js"/>"></script>

<script type="text/javascript">
function doFunction()
{
	
    var message = "calling amazon";
    var lengthLong = "kumarlength ";          
    var retunoutput = "..";
    window.app.AndroidMethodQRScan(message);


   // alert("hi anish");
    return false;


}


function qrScan(result) {
alert("result:: "+result);
    var dd = result;
    $("#ScanResult").append(dd);
}


</script>
<script type="text/javascript" src="resources/js/products/manageProduct.js"></script>
<script type="text/javascript" src="resources/js/products/addProducts.js"></script>