$(document).ready(function() {
    $('#invoiceHistoryTab').DataTable({
    	order: [
  	          [0, 'desc']
  	      ], 
    	
    });
    
    
    
} ); 

$("#gotoFeedbackHistoryLists").click(function(){
	
	Details($("#id").val(),$("#userId").val(),$("#masterDescDetails").val());
	
	
});
/*function goBackFromCNDNToDashboard(){
	
	document.gotoFeedbackHistoryList.action = "./wizardFeedbackHistory";                  
	document.gotoFeedbackHistoryList._csrf_token.value = $("#_csrf_token").val();
	document.gotoFeedbackHistoryList.submit();
}
*/
function Details(){ 
	document.gotoFeedbackHistoryList.action = "./wizardFeedbackHistory";
	/*document.gotoFeedbackHistoryList.id.value = id;
	document.gotoFeedbackHistoryList.userId.value = userId;
	document.gotoFeedbackHistoryList.masterDescDetails.value = masterDescDetails;*/
/*	document.gotoFeedbackHistoryList._csrf_token.value = $("#_csrf_token").val();*/
	document.gotoFeedbackHistoryList.submit();
	
		/*
		document.feedbackDetail.action = "./showDetailedFeedback";*/
	/*	document.feedbackDetail.masterDescDetails.value = masterDescDetails;*/
}