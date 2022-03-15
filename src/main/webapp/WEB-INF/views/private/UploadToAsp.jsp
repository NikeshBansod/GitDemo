<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 
<%@include file="common-alert.jsp" %>
            <c:if test="${apiResponse == 'SUCCESS'}"> 
             <script type="text/javascript">
				bootbox.alert("${successMsg}");
			 </script>
            </c:if> 
           <c:if test="${apiResponse == 'error'}">
             <script type="text/javascript">
             bootbox.alert('Upload failed, Please try again after sometime.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'alreadysubmitted'}">
             <script type="text/javascript">
             bootbox.alert('Financial period issue, GSTR1 already submitted for given FP.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'invalid'}">
             <script type="text/javascript">
             bootbox.alert('Can not update at this time please try after some time.');
			</script>
             </c:if>
             <c:if test="${apiResponse == 'emptyList'}">
             <script type="text/javascript">
             bootbox.alert('NO Invoice Generated for Selected GSTIN Within Selected Time Period');
			</script>
             </c:if> 


 <header class="insidehead">
      <a href="<spring:url value="/home" />" class="btn-back"><i class="fa fa-angle-left"></i> 
      <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
      	<!--  <span>Upload Data<span> -->
      </a>
 </header>

<div class="container" >
<input type="hidden" id="L0_gstin" name="L0_gstin" value="${L0_gstin}">
  <input type="hidden" id="L0_fp" name="L0_fp" value="${L0_fp}">
  <input type="hidden" id="GSTR_type" name="GSTR_type" value="${GSTR_type}">
   <input type="hidden" id="gobacktoasp" name="gobacktoasp" value="${gobacktoasp}">
 
  
  <div class="box-border">
  <form  method="post" id="uploadAsp" action="" >
  	
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  
    <div class="accordion_example2 no-css-transition">
 
      <div class="accordion_in acc_active">
          <div class="acc_content">
            <!--content-->
            
            <div class="box-content">
            
              <div class="card">
              <span><center><h4><b>GST Return Filing</b></h4></center></span>
                <ul>
                <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<select  id="gstrtype" name="gstrtype" class="form-control">
                   			</select>
							<div class="label-text label-text2">Type of Return
							</div>
						</label>
						<span class="text-danger cust-error" id="gstr-type">This field is required</span>
					</div>
				  </li>
			    <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<select  id="gstinId" name="gstinId" class="form-control">

							
							<option value="27GSPMH0782G1ZJ">27GSPMH0782G1ZJ</option>
                   			</select>
							<div class="label-text label-text2">Choose GSTIN</div>
						</label>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  </li>
				    <li>
                   	<div class="form-group input-field mandatory">
						<label class="label">  
							<select  id="financialPeriod" name="financialPeriod"  class="form-control">
							<!-- <option value="072017">Jul</option>
							<option value="082017">Aug</option>
							<option value="092017">Sept</option>
                   			 -->
                   			 </select>
							<div class="label-text label-text2">Choose Financial period
</div>
						</label>
						<span class="text-danger cust-error" id="master-desc">This field is required</span>
					</div>
				  </li>
			    </ul>
			  
              </div>
              
              
              
            </div>
          </div>
        </div>
      </div>
      <div class="com-but-wrap">
     		<!-- <button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Upload</button> -->
     		 <!--  <button type="submit" class="btn btn-primary" id="L0" formnovalidate="formnovalidate">L0 Data</button>
     		<button type="submit" class="btn btn-primary" id="L2" formnovalidate="formnovalidate">L2 data</button> -->
     		<!-- <a type="button" class="btn btn-primary" id="gstrfileoptions" formnovalidate="formnovalidate">Next</a> -->
     		<div class=" bt nbtn-primary"><button id="gstrfileoptions" type="button" class="btn btn-primary" formnovalidate="formnovalidate" >Next</button></div>
      	</div>
      <section class="block selectOptions" style="display:none;">
               
      <div role="tabpanel " class=" tab-content tab-pane active main_btn">
                                 <div class="col-xs-12">
		                            <a class="btn btn-info btn-block" type="submit" id="userLogin" formnovalidate="formnovalidate">
		                                <p>Step 1-Upload Data to JioGST</p>
		                            </a>
		                          </div>
		                          
		                           <div class="col-xs-12"> 
		                            <a class="btn btn-info btn-block" type="submit" style="margin-top:10px;" id="saveToGSTN" formnovalidate="formnovalidate" >
		                                <p>Step 2-Save data to GSTN</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-xs-12">
		                            <a class="btn btn-info btn-block" style="margin-top:10px;" type="submit" id="submitToGSTN" formnovalidate="formnovalidate">
		                               
		                                <p>Step 3-Submit data to GSTN</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-xs-12">
		                            <a class="btn btn-info btn-block"  style="margin-top:10px;" type="submit" id="filegstr1" formnovalidate="formnovalidate">
		                                <%-- <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div> --%>
		                                <p>Step 4-File your return </p>
		                            </a>
		                        </div>
                                 
                                <%-- <div class="col-xs-6">
		                            <a class="card panel panel-default card-asp" type="submit" id="userLogin" formnovalidate="formnovalidate">
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Upload</p>
		                            </a>
		                        </div> --%>
		                       <%--  
		                        <div class="col-xs-6"> 
		                            <a class="card panel panel-default card-asp" type="submit" id="L0" formnovalidate="formnovalidate" >
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p> Draft to GSTR1</p>
		                            </a>
		                        </div>
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default card-asp" type="submit" id="submitToGSTN" formnovalidate="formnovalidate">
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Submit to GSTN</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default card-asp" type="submit" id="filegstr1" formnovalidate="formnovalidate">
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p>File GSTR1 </p>
		                            </a>
		                        </div> --%>

                            
                            </div>
                            
                            </section>
                            <%--  <section class="block selectOptions selectOptionsgstr2">
               
                               <div role="tabpanel " class=" tab-content tab-pane active main_btn">
      
                                 
                                <div class="col-xs-6">
		                            <a class="card panel panel-default card-asp" type="submit" id="userLogin" formnovalidate="formnovalidate">
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Upload2</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-xs-6"> 
		                            <a class="card panel panel-default card-asp" type="submit" id="L0" formnovalidate="formnovalidate" >
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p> Draft to GSTR2</p>
		                            </a>
		                        </div>
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default card-asp" type="submit" id="L2" formnovalidate="formnovalidate">
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div>
		                                <p> Submit to GSTN</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-xs-6">
		                            <a class="card panel panel-default card-asp" type="submit" id="" formnovalidate="formnovalidate">
		                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
		                                <p>File GSTR2 </p>
		                            </a>
		                        </div>

                            
                            </div>
                            
                            </section>  --%>
     </form>
    
  </div>
  
</div>
<br/>
<!--common end-->
<script type="text/javascript" src="<spring:url value="/resources/js/uploadToAsp/uploadToAsp.js"/>"></script>
