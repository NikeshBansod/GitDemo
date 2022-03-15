$(document).ready(function() {
      createDataTable('invoiceHistoryTabInvoice');
      $('.loader').fadeOut("slow");
} );

function viewRecord(idValue,conditionValue){
      $('.loader').show();
      document.invoice.action = "./getInvoiceDetails";
      document.invoice.id.value = idValue;
      document.invoice.conditionValue.value ="invoiced";
      document.invoice._csrf_token.value = $("#_csrf_token").val();
      document.invoice.submit();
      $('.loader').fadeOut("slow");
}

$("#goBackToCall").click(function(){
	goBackFromRespectivePage($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());
});

function goBackFromRespectivePage(startdate,enddate,getInvType,onlyMonth,onlyYear){
      $('.loader').show();
      document.redirectToBack.action = "./showAllRecordsList";
      document.redirectToBack.startdate.value = startdate;
      document.redirectToBack.enddate.value = enddate;
      document.redirectToBack.getInvType.value = getInvType;
      document.redirectToBack.onlyMonth.value = onlyMonth;
      document.redirectToBack.onlyYear.value = onlyYear;
      document.redirectToBack._csrf_token.value = $("#_csrf_token").val();
      document.redirectToBack.submit(); 
      $('.loader').fadeOut("slow");
}

$("#goBackToDashboard").click(function(){
      goBackFromInvoiceToDashboard($("#onlyMonth").val(),$("#onlyYear").val());
});

 

function goBackFromInvoiceToDashboard(onlyMonth,onlyYear){    
	  $('.loader').show();
      document.gotoDashboard.action = "./gotoDashboard";          
      document.gotoDashboard.onlyMonth.value = onlyMonth;
      document.gotoDashboard.onlyYear.value = onlyYear;
      document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
      document.gotoDashboard.submit();
      $('.loader').fadeOut("slow");
}



 

 

 
