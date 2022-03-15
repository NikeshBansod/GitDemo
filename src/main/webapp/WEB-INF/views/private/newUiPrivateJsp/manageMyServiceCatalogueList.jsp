<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<div class="loader"></div>
<section class="block">
	<div class="container">
<%-- 		<div class="brd-wrap">
	    	<div id="listheader">
	       		<strong><a href="<spring:url value="/home#master_management" />">Master Management</a></strong> &raquo; <strong>Service Catalogue</strong>
	        </div>
	        <div id="addheader">
	        	<strong><a href="#" id="gobacktolisttable">Service Catalogue</a></strong> &raquo; <strong>Add Service </strong>
	        </div>
	    </div> --%>
	     <div class="page-title" id="listheader">
                 <a href="<spring:url value="/home#master_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Service Catalogue
         </div>
		 <div class="page-title" id="addheader">
                <a href="#" id="gobacktolisttable" class="back"/><i class="fa fa-chevron-left"></i></a>Add Service
          </div>
	    <div class="form-wrap" id="addServicediv">
		    <div class="row"  >
	            <div class="col-md-12 button-wrap">			
					<button id="addServiceButton" style="width: auto;" type="button" class="btn btn-success blue-but">Add</button>
				</div>
			</div>
		</div>
	    
  		<div class="row" id="listTable">
	        <div class="table-wrap">
	            <table id="ServiceValuesTab" class="display nowrap" style="width:100%">
                    <thead>
                        <tr>
                            <!-- <th>Sr.</th> -->
                            <th>Name</th>
                            <th>Charges</th>
                            <th>SAC Code</th>
                            <th>Tax Rate(%)</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                       
                    </tbody>
	            </table>
	        </div>
	    </div>		    
	    
	    <div class="form-wrap"  id="addServiceDetails">	    
			<form method="post" id="servForm" >
			<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
			      			    	
			<div class="row">	
				<div class="col-md-4">
					<label for="label">Search By SAC Code / SAC Description</label>
				  	<input type="text" id="search-sac" maxlength="15"  />
                  	<span class="text-danger cust-error" id="reg-gstin-req">Please Search By SAC Code / SAC Description</span>			
				</div>
				
				<div class="col-md-4">
					<label for="label">SAC Description<span> *</span></label>
				  	<input type="text" disabled="disabled"  readonly="readonly" id="sacDescriptionToShow" required  />
					<input type="hidden"  id="sacDescription" />
					<span class="text-danger cust-error" id="ser-sac-desc">This field is required.</span>
				</div> 		
				<div class="col-md-4">
					<label for="label">SAC Code<span> *</span></label>
					<input type="text" disabled="disabled"  readonly="readonly" id="sacCodeToShow" required  />
				  	<input type="hidden"  path="sacCode" id="sacCode" />
					<span class="text-danger cust-error" id="ser-sac-code">This field is required.</span>
				</div> 							
			</div>	
				
				
			<div class="row">	
				<div class="col-md-4">
					<label for="label">Service Name<span> *</span></label>
				 	 <input type="text"  id="name" required="true" maxlength="100"/>
					<span class="text-danger cust-error" id="ser-name">This field is required.</span>			
				</div>	
				
				 <div class="col-md-4" style="display:none">
					<label for="label">Unit Of Measurement<span> *</span></label>
				  	<select name="unitOfMeasurement"  id="unitOfMeasurement"  >
                    </select>
                    <span class="text-danger cust-error" id="prod-uom">This field is required.</span>
				</div> 
				
	          	<div class="col-md-4" id="divOtherUnitOfMeasurement" style="display:none">
       		  		<label for="label">Please Specify<span> *</span></label>
       				<input type="text"  maxlength="30" id="otherUOM"  />
       				<span class="text-danger cust-error" id="otherOrgType-req">This field is required.</span>
	           </div>
	          
				<div class="col-md-4">
					<label for="label">Charges<span> *</span></label>
				  	<input type="text"  id="serviceRate" maxlength="18" required="true" />
					<span class="text-danger cust-error" id="ser-rate">This field is required.</span>
				</div> 	
				 								
			</div> 
				
				
			<div class="row">	
				<div class="col-md-4">
					<label for="label">Rate of tax (%)<span> *</span></label>
				    <select name="serviceIgst"  id="serviceIgst"></select>
					<span class="text-danger cust-error" id="service-igst">This field is required.</span>					
				</div>
		
				<!-- <div class="col-md-4">
	                   <label for="label">Cess Advol Rate (%) </label>
	                   <select id="advolCess" name="advolCess" ></select>
	                   <span class="text-danger cust-error" id="prod-advolCess">This field is required and should be numeric.</span>
				</div>
	
				<div class="col-md-4">
                     <label for="label"> Cess Non Advol Rate</label>
                     <select id="nonAdvolCess" name="nonAdvolCess" ></select>
                     <span class="text-danger cust-error" id="prod-nonAdvolCess">This field is required and should be numeric.</span>
           		</div> -->
	       </div> 	
		         	
		   <div class="form-accrod" style="display:none">
	          <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" style="border 1px solid grey">
	             <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                      <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse1">
                         Choose Stores
                        </a>
                      </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="heading1">
                      <div class="panel-body">
                            <div class="" id="listTable">
					           <div class="table-wrap">
					            <table id="dny-service-gstin-openingstock" class="display nowrap" style="width:100%">
						            <thead>
						                <tr>
						                	<th style="width:10px">Sr No.</th>
						                	<th>Gstin</th>
						                    <th>Location/Store</th>
						                </tr>
						            </thead>
						            <tbody>
						            </tbody>
					             </table>
					           </div>
					        </div>
                      </div>
                    </div>
                  </div>                                                                             
                </div>
              </div>					
			  <input type="hidden" id="sacCodePkId" />
			  <input type="hidden" id="editPage" value="false" />
				  
				  				
				
			<div class="row">	
	      		<div class="col-md-12 button-wrap"><button  style="width: auto;" class="btn btn-success blue-but" id="srvSubmitBtn"  value="Save" >Save</button>       
                   </div>
            </div>
				
	  	  </form> 
	   </div>		
	</div>
</section>

<form name="manageService" method="post">
    <input type="hidden" name="id" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form>


<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/manageServiceCatalogue/sacCodeAjax.js"/>"></script>
 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/manageServiceCatalogue/addServiceCatalogue.js"/>"></script>
 <script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/manageServiceCatalogue/manageServiceCatalogue.js"/>"></script>
