
$(document).ready(function(){
	var ewaybillId = $("#uiEWayBillId").val();
	
	$("#submitId").click(function(e){
		   
		if($("#radio2").is(":checked")){
			window.location.href = redirectToInvoiceHistoryPage();
	    } else {
	    	window.location.href = "./loginGenerateEwayBill";
	    }
		
	});
	
	 
});



function redirectToInvoiceHistoryPage(){
	return 'getMyInvoices';
}



function getEWayBillsByInvoice(idValue){
	document.manageInvoice.action = "./getEWayBills";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}
