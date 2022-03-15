$(document).ready(function() {
    $('#invoiceHistoryTab').DataTable();
    $("#goBackToDashboard").click(function(){
    	
    	goBackFromInvoiceToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
    	
    });

    


} );

function viewRecord(idValue,conditionValue){
	document.invoice.action = "./getWizardDashboardInvoiceDetails";
	document.invoice.id.value = idValue;
	document.invoice.conditionValue.value ="invoice";
	document.invoice._csrf_token.value = $("#_csrf_token").val();
	document.invoice.submit();
}

/*function viewRecord(idValue,conditionValue){
	document.invoice.action = "./getInvoiceDetails";
	document.invoice.id.value = idValue;
	document.invoice.conditionValue.value ="invoice";
	document.invoice._csrf_token.value = $("#_csrf_token").val();
	document.invoice.submit();
}*/
$("#goBackToCall").click(function(){
	goBackFromRespectivePage($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
	
});
function goBackFromRespectivePage(startdate,enddate,getInvType,onlyMonth,onlyYear){
	/*alert("error: "+startdate+" status: "+enddate+" type: "+getInvType+" onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);*/
	document.redirectToBack.action = "./showAllWizardRecordsList";
	document.redirectToBack.startdate.value = startdate;
	document.redirectToBack.enddate.value = enddate;
	document.redirectToBack.getInvType.value = getInvType;
	document.redirectToBack.onlyMonth.value = onlyMonth;
	document.redirectToBack.onlyYear.value = onlyYear;
	document.redirectToBack._csrf_token.value = $("#_csrf_token").val();
	document.redirectToBack.submit();	
}
function goBackFromInvoiceToDashboard(onlyMonth,onlyYear){
	
	document.gotoWizardDashboard.action = "./gotoWizardDashboard";                  
	document.gotoWizardDashboard.onlyMonth.value = onlyMonth;
	document.gotoWizardDashboard.onlyYear.value = onlyYear;
	document.gotoWizardDashboard._csrf_token.value = $("#_csrf_token").val();
	document.gotoWizardDashboard.submit();
}
function goBackFromRespectivePage(startdate,enddate,getInvType,onlyMonth,onlyYear){
	/*alert("error: "+startdate+" status: "+enddate+" type: "+getInvType+" onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);*/
	document.redirectToBack.action = "./showAllWizardRecordsList";
	document.redirectToBack.startdate.value = startdate;
	document.redirectToBack.enddate.value = enddate;
	document.redirectToBack.getInvType.value = getInvType;
	document.redirectToBack.onlyMonth.value = onlyMonth;
	document.redirectToBack.onlyYear.value = onlyYear;
	document.redirectToBack._csrf_token.value = $("#_csrf_token").val();
	document.redirectToBack.submit();	
}
