$(document).ready(function() {
	
    $('#invoiceHistoryTab').DataTable({
    	"aaSorting": [],
        "bLengthChange": false,
        "pagingType": "full"
    	
    });
    
    $("#goBackToDashboard").click(function(){
    	
    	goBackFromEwaybillToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
    	
    	
    });
   
} );

function getWizardDashboardPreviewOfInvoiceEwayBillDetails(ewaybillNo,invoiceId){
	
	document.invoiceEwaybill.action = "./getWizardDashboardInvoiceEwayPreviewDetails";                  
	document.invoiceEwaybill.ewaybillNo.value = ewaybillNo;
	document.invoiceEwaybill.invoiceId.value = invoiceId;
	document.invoiceEwaybill._csrf_token.value = $("#_csrf_token").val();
	document.invoiceEwaybill.submit();
}
 


$("#goBackToInvoiceEwaybill").click(function(){
	
	
	goBackFromInvoiceEwayBill($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
	
});

//from invoice eway bill preview page to invoice eway bill list in dashboard
function goBackFromInvoiceEwayBill(startdate,enddate,getInvType,onlyMonth,onlyYear){
	
	document.redirectToBackFrominvoiceEwayBill.action ="./showAllWizardRecordsList";
	document.redirectToBackFrominvoiceEwayBill.startdate.value = startdate;
	document.redirectToBackFrominvoiceEwayBill.enddate.value = enddate;
	document.redirectToBackFrominvoiceEwayBill.getInvType.value = getInvType;
	document.redirectToBackFrominvoiceEwayBill.onlyMonth.value = onlyMonth;
	document.redirectToBackFrominvoiceEwayBill.onlyYear.value = onlyYear;
	document.redirectToBackFrominvoiceEwayBill._csrf_token.value = $("#_csrf_token").val();
	document.redirectToBackFrominvoiceEwayBill.submit();	
}




function goBackFromEwaybillToDashboard(onlyMonth,onlyYear){
	
	document.gotoDashboard.action = "./gotoWizardDashboard";                  
	document.gotoDashboard.onlyMonth.value = onlyMonth;
	document.gotoDashboard.onlyYear.value = onlyYear;
	document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
	document.gotoDashboard.submit();
}
