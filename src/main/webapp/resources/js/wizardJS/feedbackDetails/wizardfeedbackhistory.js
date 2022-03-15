$(document).ready(function() {
    $('#invoiceHistoryTab').DataTable({
    	
    	"aaSorting": [],
    	 
    	
    });
    
    
    function validateMaster(){
		errFlag1 = validateSelectField("masterDesc","master-desc");
		 return errFlag1;

	}
    
    
    
	
    $.ajax({
    		url : "getMasterDescList",
    		headers: {
    			_csrf_token : $("#_csrf_token").val()
    		},
    		type : "POST",
    		dataType : "json",
    		success:function(json,fTextStatus,fRequest){
    			$("#masterDesc").empty();
    			
    			$("#masterDesc").append('<option value="">Select Area of feedback</option>');
    			
    			if (isValidSession(json) == 'No') {
    				window.location.href = getDefaultWizardSessionExpirePage();
    				return;
    			}
    			
    			if(isValidToken(json) == 'No'){
    				window.location.href = getWizardCsrfErrorPage();
    				return;
    			}
    			
    			$.each(json,function(i,value) {
    				$("#masterDesc").append($('<option>').text(value.masterDesc).attr('value',value.id));
    			});
    			
    			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
    			
             },
             error: function (data,status,er) {
            	 
            	 getWizardInternalServerErrorPage();   
            }
    	});
    
  
    
    
} ); 
  
function Details(id,userId,masterDescDetails){ 
	document.manageInvoice.action = "./getFeedbackHistoryDetails";
	document.manageInvoice.id.value = id;
	document.manageInvoice.userId.value = userId;
	document.manageInvoice.masterDescDetails.value = masterDescDetails;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	
		/*
		document.feedbackDetail.action = "./showDetailedFeedback";*/
	/*	document.feedbackDetail.masterDescDetails.value = masterDescDetails;*/
}


/*id="masterDesc" name="masterDescDetails"*/
	
	