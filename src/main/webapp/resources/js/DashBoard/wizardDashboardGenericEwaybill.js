$(document).ready(function() {
    $('#invoiceHistoryTab').DataTable(
    		{
    			
    			
    			
    		});
    $("#goBackToDashboard").click(function(){
    	
    	goBackFromGenericEwaybillToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
    	
    });
    $("#goBackToGenericEwaybill").click(function(){
    	
    	
    	goBackFromGenericEwayBill($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
    	
    });
} );


/*function getPreviewOfEwayBillDetails(ewaybillNo,ewayBillWITransId){
	
	document.Ewaybill.action = "./getEwayPreviewDetails";                  
	document.Ewaybill.ewaybillNo.value = ewaybillNo;
	document.Ewaybill.ewayBillWITransId.value = ewayBillWITransId;
	document.Ewaybill._csrf_token.value = $("#_csrf_token").val();
	document.Ewaybill.submit();
}*/




//from generic eway bill preview page to generic eway bill list in dashboard
function goBackFromGenericEwayBill(startdate,enddate,getInvType,onlyMonth,onlyYear){
	
	document.redirectToBackFromGenericEwayBill.action ="./showAllWizardRecordsList";
	document.redirectToBackFromGenericEwayBill.startdate.value = startdate;
	document.redirectToBackFromGenericEwayBill.enddate.value = enddate;
	document.redirectToBackFromGenericEwayBill.getInvType.value = getInvType;
	document.redirectToBackFromGenericEwayBill.onlyMonth.value = onlyMonth;
	document.redirectToBackFromGenericEwayBill.onlyYear.value = onlyYear;
	document.redirectToBackFromGenericEwayBill._csrf_token.value = $("#_csrf_token").val();
	document.redirectToBackFromGenericEwayBill.submit();	
}



function goBackFromGenericEwaybillToDashboard(onlyMonth,onlyYear){
	
	document.gotoDashboard.action = "./gotoWizardDashboard";                  
	document.gotoDashboard.onlyMonth.value = onlyMonth;
	document.gotoDashboard.onlyYear.value = onlyYear;
	document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
	document.gotoDashboard.submit();
}
