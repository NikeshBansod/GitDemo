$(document).ready(function() {
    $('#invoiceHistoryTab').DataTable();
    $("#goBackToCNDN").click(function(){
    	
    	
    	goBackFrompreview($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
    	
    });
} );

//for CNDN invoice preview show
function getCnDnDetails(idValue,iterationNo,cId,cndnNumber){
	
	document.cndn.action = "./getWizardDashboardCnDn";
	document.cndn.id.value = idValue;
	document.cndn.iterationNo.value = iterationNo;
	document.cndn.cndnNumber.value = cndnNumber;
	document.cndn.cId.value = cId;
	document.cndn._csrf_token.value = $("#_csrf_token").val();
	document.cndn.submit();
}
//from CNDN invoice preview show to cndn list
$("#goBackToCNDN").click(function(){
	
	
	goBackFrompreview($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
	
});

//from CNDN preview page to CNDN list in dashboard
function goBackFrompreview(startdate,enddate,getInvType,onlyMonth,onlyYear){
	/*alert("error: "+startdate+" status: "+enddate+" type: "+getInvType+" onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);*/
	document.redirectToBackFromCNDN.action ="./showAllWizardRecordsList";
	document.redirectToBackFromCNDN.startdate.value = startdate;
	document.redirectToBackFromCNDN.enddate.value = enddate;
	document.redirectToBackFromCNDN.getInvType.value = getInvType;
	document.redirectToBackFromCNDN.onlyMonth.value = onlyMonth;
	document.redirectToBackFromCNDN.onlyYear.value = onlyYear;
	document.redirectToBackFromCNDN._csrf_token.value = $("#_csrf_token").val();
	document.redirectToBackFromCNDN.submit();	
}

$("#goBackToDashboard").click(function(){
	
	goBackFromCNDNToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
	
});

function goBackFromCNDNToDashboard(onlyMonth,onlyYear){
	
	document.gotoDashboard.action = "./gotoWizardDashboard";                  
	document.gotoDashboard.onlyMonth.value = onlyMonth;
	document.gotoDashboard.onlyYear.value = onlyYear;
	document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
	document.gotoDashboard.submit();
}
