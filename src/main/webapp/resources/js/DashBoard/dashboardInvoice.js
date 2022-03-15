$(document).ready(function() {
    $('#invoiceHistoryTab').DataTable(
    		{
    			"aaSorting": [],
    			"bLengthChange": false,
    			"pagingType": "full"
    				
    		});
} );

function viewRecord(idValue,conditionValue){
	document.invoice.action = "./getInvoiceDetails";
	document.invoice.id.value = idValue;
	document.invoice.conditionValue.value ="invoice";
	document.invoice._csrf_token.value = $("#_csrf_token").val();
	document.invoice.submit();
}
$("#goBackToCall").click(function(){
	goBackFromRespectivePage($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
	
});

function goBackFromRespectivePage(startdate,enddate,getInvType,onlyMonth,onlyYear){
	/*alert("error: "+startdate+" status: "+enddate+" type: "+getInvType+" onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);*/
	document.redirectToBack.action = "./showAllRecordsList";
	document.redirectToBack.startdate.value = startdate;
	document.redirectToBack.enddate.value = enddate;
	document.redirectToBack.getInvType.value = getInvType;
	document.redirectToBack.onlyMonth.value = onlyMonth;
	document.redirectToBack.onlyYear.value = onlyYear;
	document.redirectToBack._csrf_token.value = $("#_csrf_token").val();
	document.redirectToBack.submit();	
}
$("#goBackToDashboard").click(function(){
	
	goBackFromInvoiceToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
	
});

function goBackFromInvoiceToDashboard(onlyMonth,onlyYear){
	
	document.gotoDashboard.action = "./gotoDashboard";                  
	document.gotoDashboard.onlyMonth.value = onlyMonth;
	document.gotoDashboard.onlyYear.value = onlyYear;
	document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
	document.gotoDashboard.submit();
}
scrollingElement = (document.scrollingElement || document.body)
function scrollSmoothToBottom () {
	   $(scrollingElement).animate({
	      scrollTop: document.body.scrollHeight
	   }, 200);
	}

	//Require jQuery
	function scrollSmoothToTop () {
	   $(scrollingElement).animate({
	      scrollTop: 0
	   }, 200);
	}


