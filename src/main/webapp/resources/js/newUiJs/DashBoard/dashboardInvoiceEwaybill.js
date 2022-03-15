var backendResponse="";

$(document).ready(function() {
	$('#headerPreview').hide();
	createDataTable('invoiceHistoryTabInvoiceEway');
	$('.loader').fadeOut("slow");
} );

/*function getPreviewOfInvoiceEwayBillDetails(id,ewaybillNo){
	$('.loader').show();
       document.invoiceEwaybill.action = "./getInvoiceEwayPreviewDetails";
       document.invoiceEwaybill.invoiceId.value = id;
       document.invoiceEwaybill.ewaybillNo.value = ewaybillNo;
       document.invoiceEwaybill._csrf_token.value = $("#_csrf_token").val();
       document.invoiceEwaybill.submit();
       $('.loader').fadeOut("slow");
}
*/

/*	var idValue=$("#ewaybillid").val();
     function getEWayBillDetails(idValue){
	$('.loader').show();
	$('#card').hide();
	$("#firstDivId").hide();
	$("#breadcumheader").hide();
	setValuesInHiddenFields(idValue);
	setTimeout(function(){
		getEwayBillDetailsInPreview(idValue);
		$("#secondDivId").show();
		$("#headerPreview").show();
		$(".viewHeading").hide();
		$("#previewButtons").show();
		$('#card').show();
		$(".loader").fadeOut("slow"); 
	}, 2000);
	
}*/


$("#goBackToInvoiceEwaybill").click(function(){
       
       
goBackFromInvoiceEwayBill($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
       
});

//from invoice eway bill preview page to invoice eway bill list in dashboard
function goBackFromInvoiceEwayBill(startdate,enddate,getInvType,onlyMonth,onlyYear){
	$('.loader').show();
       document.redirectToBackFrominvoiceEwayBill.action ="./showAllRecordsList";
       document.redirectToBackFrominvoiceEwayBill.startdate.value = startdate;
       document.redirectToBackFrominvoiceEwayBill.enddate.value = enddate;
       document.redirectToBackFrominvoiceEwayBill.getInvType.value = getInvType;
       document.redirectToBackFrominvoiceEwayBill.onlyMonth.value = onlyMonth;
       document.redirectToBackFrominvoiceEwayBill.onlyYear.value = onlyYear;
       document.redirectToBackFrominvoiceEwayBill._csrf_token.value = $("#_csrf_token").val();
       document.redirectToBackFrominvoiceEwayBill.submit(); 
       $('.loader').fadeOut("slow");
}


$("#goBackToDashboard").click(function(){
       
       goBackFromEwaybillToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
       
});

function goBackFromEwaybillToDashboard(onlyMonth,onlyYear){
	$('.loader').show();
       document.gotoDashboard.action = "./gotoDashboard";                  
       document.gotoDashboard.onlyMonth.value = onlyMonth;
       document.gotoDashboard.onlyYear.value = onlyYear;
       document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
       document.gotoDashboard.submit();
       $('.loader').fadeOut("slow");
}

var ewaybillNo=null;
var ewayBillid=null;
var ewaybillStatus=null;
var docDate = null;



function getEWayBillDetailsPreview(idValue,ewaybillid){
	$('.loader').show();
	document.manageInvoice.action = "./getEWayBillsInDashboard";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice.ewaybillid.value = ewaybillid;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$(".loader").fadeOut("slow");
}

function backToInvoicePage(idValue){
	$('.loader').show();
	document.manageInvoice.action = "./getInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$(".loader").fadeOut("slow");
}


