<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>
  
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
             
             

<input type="hidden" id="L0_gstin" name="L0_gstin" value="${L0_gstin}">
  <input type="hidden" id="L0_fp" name="L0_fp" value="${L0_fp}">
  <input type="hidden" id="GSTR_type" name="GSTR_type" value="${GSTR_type}">
   <input type="hidden" id="gobacktoasp" name="gobacktoasp" value="${gobacktoasp}">
 
  
  <section class="block">
  <div class="loader"></div>
  <form  method="post" id="uploadAsp" action="" >
  	
  <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
  
    <div class="container">
    
    <%-- <div class="brd-wrap">
             <a href="<spring:url value="/home"/>"><strong>Home</strong></a>  &raquo;<strong>  GST Return Filing</strong>
        </div> --%>
         <div class="page-title">
                        <a href="<spring:url value="/home"/>" class="back"><i class="fa fa-chevron-left"></i></a>GST Return Filing
                    </div>
 
      <div class="form-wrap">
           <span></span> 
                <div class="form-wrap footernote">
                   	<div class="row">
                   	<div class=" col-md-4 form-group input-field mandatory">
						<label>Type of Return<span>  *</span></label>
							<select  id="gstrtype" name="gstrtype" ></select>
					<span class="text-danger cust-error" id="gstr-type">This field is required</span>
					</div>
                   	<div class=" col-md-4 form-group input-field mandatory">
						<label >Select GSTIN<span>  *</span></label>
							<select  id="gstinId" name="gstinId" >
                   			</select>
						<span class="text-danger cust-error" id="selectUser-req">This field is required</span>
					</div>
				  
                   	<div class=" col-md-4 form-group input-field mandatory">
						<label >Select Financial period<span>  *</span></label>
							<select  id="financialPeriod" name="financialPeriod" ></select>
						
						<span class="text-danger cust-error" id="master-desc">This field is required</span>
					</div>
				  </div>
              
          </div>
       
      
      <div class="com-but-wrap">
     		<!-- <button type="submit" class="btn btn-primary" id="userLogin" formnovalidate="formnovalidate">Upload</button> -->
     		 <!--  <button type="submit" class="btn btn-primary" id="L0" formnovalidate="formnovalidate">L0 Data</button>
     		<button type="submit" class="btn btn-primary" id="L2" formnovalidate="formnovalidate">L2 data</button> -->
     		<!-- <a type="button" class="btn btn-primary" id="gstrfileoptions" formnovalidate="formnovalidate">Next</a> -->
     		<div style="margin-top: 4px;padding-top:4px;margin-bottom: 0px;padding-bottom:0px;"><button id="gstrfileoptions" type="button" class="btn btn-success blue-but" formnovalidate="formnovalidate" style="width:auto" >Next</button></div>
      	</div>
      <section class="block selectOptions" style="display:none;">
               <div class=row style="padding-top: 0px;" >
                   <div class=" row text-center" style="padding-top:0px;">	
                                 <div class="col-md-6">
		                            <a class="btn btn-success blue-but btn-block" type="submit" style="margin-top:10px;" id="userLogin" formnovalidate="formnovalidate">
		                                <p>Step 1 - Upload Data to JioGST</p>
		                            </a>
		                          </div>
		                          
		                           <div class="col-md-6"> 
		                            <a class="btn btn-success blue-but btn-block" type="submit"  style="margin-top:10px;" id="saveToGSTN" formnovalidate="formnovalidate" >
		                                <p>Step 2 - Save data to GSTN</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-md-6">
		                            <a class="btn btn-success blue-but btn-block" style="margin-top:10px;" type="submit" id="submitToGSTN" formnovalidate="formnovalidate">
		                               
		                                <p>Step 3 - Submit data to GSTN</p>
		                            </a>
		                        </div>
		                        
		                        <div class="col-md-6">
		                            <a class="btn btn-success blue-but btn-block"  style="margin-top:10px;" type="submit" id="filegstr1" formnovalidate="formnovalidate">
		                                <%-- <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div> --%>
		                                <p>Step 4 - File your return </p>
		                            </a>
		                        </div>	
                                 
                            </div>
                            </div>
                            
                            </section>
                             <section class="block selectOptions selectOptionsgstr2" style="display:none;">               
	                               <div class=row style="padding-top: 0px;" >
                                             <div class=" row text-center" style="padding-top:0px;">	                           
	                                          <div class="col-md-6">
			                            <a class="btn btn-success blue-but btn-block" style="margin-top:10px;" type="submit" id="prepareGstr3B" formnovalidate="formnovalidate">
			                              <%--   <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div> --%>
			                                <p>Prepare GSTR 3B</p>
			                            </a>
			                        </div>
			                        
			                        <div class="col-md-6"> 
			                            <a class="btn btn-success blue-but btn-block" style="margin-top:10px;" type="submit" id="draftGstr3B" formnovalidate="formnovalidate" >
			                              <%--   <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div> --%>
			                                <p>Draft GSTR 3B</p>
			                            </a>
			                        </div>
			                        <div class="col-md-6">
			                            <a class="btn btn-success blue-but btn-block" style="margin-top:10px;" type="submit" id="fetchDownloadFromGstn" formnovalidate="formnovalidate">
			                               <%--  <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_generate_invoice.svg" />"></div> --%>
			                                <p>Fetch & Download from GSTN</p>
			                            </a>
			                        </div>
			                        
			                        <div class="col-md-6">
			                            <a class="btn btn-success blue-but btn-block" style="margin-top:10px;" type="submit" id="OffsettaxLiability" formnovalidate="formnovalidate">
			                              <%--   <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div> --%>
			                                <p>Offset Tax Liability</p>
			                            </a>
			                        </div>
	
			                        <div class="col-md-6">
			                            <a class="btn btn-success blue-but btn-block" style="margin-top:10px;" type="submit" id="historyGstr3b" formnovalidate="formnovalidate">
			                               <%--  <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div> --%>
			                                <p>History GSTR 3B</p>
			                            </a>
			                        </div>
			                        <%-- <div class="col-md-6">
			                            <a class="card panel panel-default card-asp" type="submit" id="historyGstr3b" formnovalidate="formnovalidate">
			                                <div class="card-icon-asp"><img src="<spring:url value="/resources/images/home/ic_product_catalogue.svg" />"></div>
			                                <p>asdada</p>
			                            </a>
			                        </div> --%>
	                            </div>  
	                            </div>                          
                            </section> 
          </form>
         </div>
      </div>
  </section>
  

<br/>

 <form id="gstr3b" name="gstr3b" method="post" action="">
    <input type="hidden" id="gstinId" name="gstinId" value="">
    <input type="hidden" id="financialPeriod" name="financialPeriod" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>

<!--common end-->
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/uploadToAsp/uploadToAsp.js"/>"></script>
             