<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>

<section class="block">
	<div class="container">
	   <div class="loader"></div>
	 <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
<%-- 	<div class="brd-wrap">
	
	 <a href="<spring:url value="/home#inventory_management"/>"><strong >Inventory</strong></a>  &raquo; <strong>Opening Stock</strong>
   </div> --%>
    <div class="page-title">
                        <a href="<spring:url value="/home#inventory_management"/>" class="back"><i class="fa fa-chevron-left"></i></a>Opening Stock
                    </div> 
          <div class="form-wrap footernote">
                    <div class="row">
                    
                         <div class="col-md-6">
                             <label for="">GSTIN:<span>  *</span></label>
                             <select required="required" name="gstnStateId" id="gstnStateId"></select>   
                             <span class="text-danger cust-error" id="gstnStateId-csv-id">This field is required.</span>                       
                        </div>
                        
                        <div class="col-md-6" id="locationDiv" 	>
                             <label for="">Location/Store:<span>  *</span></label>
                             <select required="required" name="location" id="location"></select> 
                             <span class="text-danger cust-error" id="location-csv-id">This field is required.</span> 
                             <input type="hidden" name="locationStore" id="locationStore">                         
                        </div>
                      
                        <div class="col-md-12 col-sm-12 col-xs-12"></br>
                            <div class="col-md-12 button-wrap"><button id="calculateStock" type="button" class="btn btn-success blue-but" style="width: auto;">Get Stock</button></div>
                         </div>
                        <div class="col-md-12 col-sm-12 col-xs-12"><h5 style="color:red"> Note : You can not change opening stock once Inventory is updated for goods.</h5></div>
                        
                    </div>
                    
                </div>
                
                <div id="showProductGrid">
			     <div class="row" id="listTable">
		           <div class="table-wrap">
		            <table id="openingstockInventoryTab" class="display nowrap" style="width:100%">
			            <thead>
			                <tr>
			                	<th id="checkboxtd"></th>
			                    <th>Sr.No.</th>
			                    <th>Goods</th>
			                    <th>Quantity</th>
			                    <th>UOM</th>
			                    <th>Stock Value</th>
			                </tr>
			            </thead>
			            <tbody>
			            </tbody>
		             </table>
		           </div>
		        </div>
				<div class="row">
					<div class="col-md-12 button-wrap">
						<button type="button" id="Save" style="width:auto;" class="btn btn-success blue-but">Save</button>
					</div>
				</div>
			  </div>	
   </div>
</section>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/inventory/openingStock.js"/>"></script> 
