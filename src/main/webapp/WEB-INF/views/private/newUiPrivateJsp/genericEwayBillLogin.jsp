<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>


<section class="block">
	<div class="loader"></div>		
    <div class="container" id="formDiv">
        <!-- <div class="brd-wrap">
            <a href="javascript:backToEWayBillDocs()"><strong>Generic E-Way Bill History</strong></a> &raquo;<strong> NIC User Authentication</strong>
        </div> -->
        		 <div class="page-title" id="listheader">
                 <a href="javascript:backToEWayBillDocs()" class="back"><i class="fa fa-chevron-left"></i></a>Generic E-Way Bill History
         </div>
         		<!--  <div class="page-title" id="addheader">
                <a href="#" id="gobacktolisttable" class="back"/><i class="fa fa-chevron-left"></i></a>Add Customer Details
          </div> -->
        <div class="form-wrap">
            <div class="row">
                <div class="col-md-12 genratebill">
                    <h2 class="nicusertitle">Please do this before creating your first EWay Bill:</h2>
                    <ul class="list-display list-checkmarks">
                        <li>Go to NIC website: <a href="https://ewaybillgst.gov.in/login.aspx" target="_blank">Click Here!</a></li>
                        <li>Enter your NIC login Id & Password.</li>
                        <li>After successful login, in the menu click on ‘’Registration’’ & then click on "For GSP".</li>
                        <li>You will see this page: "Register Your GST Suvidha Provider". Choose the "Add/New" option on this page.</li>
                        <li>Click on the dropdown for "GSP name" and choose: "Reliance Corporate IT Park Limited".</li>
                        <li>Re-enter the username & password.</li>
                    </ul>
                    <span>    Click on 'Add' & you're done</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <label for="">GSTIN</label>
                    <select name="gstnStateId" id="gstnStateId"> </select>
                </div>
                <div class="col-md-4" id="nicUserIdDiv">
                    <label for="">NIC User Id <span>*</span></label>
                    <input type="text" required id="nicUserId" value="${ nicUserDetails.userId}"> 
	              	<span class="text-danger cust-error" id="nicUserId-csv-id" style="display:none;">This field is required.</span>
                </div>
                <div class="col-md-4" id="nicPwdDiv">
                    <label for="">NIC Password <span>*</span></label>                    
	              	<input type="password" required value="" id="nicPwd">
	              	<span class="text-danger cust-error" id="nicPwd-csv-id" style="display:none;">This field is required.</span>
                </div> 
            </div>
            <div class="row" style="display:none;">
                 <div class="col-md-4">
                     <label for="" class="dis-none">&nbsp;&nbsp;</label>
                     <div class="checkbox checkbox-success checkbox-inline form-mt">
                         <input name="isCheckNicUserId" id="isCheckNicUserId" class="styled" type="checkbox" checked/>
                         <label for="isCheckNicUserId">Save my NIC User Id</label>
                     </div>                                 
                 </div>                 
            </div>
           	<input type="hidden" id="clientId" name="clientId" value="${clientId}">
			<input type="hidden" id="secretKey" name="secretKey" value="${secretKey}">
			<input type="hidden" id="appCode" name="appCode" value="${appCode}">
			<input type="hidden" id="userId" name="userId" value="${userId}">
			<input type="hidden" id="ipUsr" name="ipUsr" value="${ipUsr}">
			<input type="hidden" id="email_id" name="email_id" value="${email_id}">
			<input type="hidden" id="mobile_number" name="mobile_number" value="${mobile_number}"> 
        </div>
        <div class="row">
            <div class="col-md-12 button-wrap">
                <button type="button" id="validateId" class="btn btn-success blue-but">Validate</button>
            </div>
        </div>
    </div>
</section>

 <form name="manageInvoice" method="post">
    <input type="hidden" name="nicUserId" value="">
    <input type="hidden" name="nicPwd" value="">
	<input type="hidden" name="gstin" value="">
</form>

<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/eWayBill/general.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/newUiJs/genericEwayBill/validationsNicUserAuth.js"/>"></script>