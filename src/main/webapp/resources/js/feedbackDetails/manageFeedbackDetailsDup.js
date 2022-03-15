 
jQuery(document).ready(function($){
	
	var blankMsg="This field is required";
	var length = 2;
	var lengthMsg = "Minimum length should be ";
	
	
	$("#feedbackSubmit").click(function(e){
		var blankMsg="This field is required";
		 var errFlag1 = validateMaster();
		 var errFlag2 = validateFeedbackQuery();
		
		 if ((errFlag1) || (errFlag2)){
			 e.preventDefault();	 
		 }
		 	
	});

	function validateMaster(){
		errFlag1 = validateSelectField("masterDesc","master-desc");
		 return errFlag1;

	}
	
	function validateFeedbackQuery(){
		
		errFlag2 = validateTextField("feedbackDesc","feedback-query-req",blankMsg);
		if(!errFlag2){
			errFlag2=validateFieldLength("feedbackDesc","feedback-query-req",lengthMsg,length);
		 }
		 return errFlag2;
	 }
	
	
$.ajax({
		url : "./../getMasterDescList",
		type : "POST",
		dataType : "json",
		async : false,
		data : { _csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			//alert("request"+request.getResponseHeader('_csrf_token'));
			$("#masterDesc").empty();
			
			$("#masterDesc").append('<option value="">Select Area of feedback</option>');
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			$.each(json,function(i,value) {
				
					$("#masterDesc").append($('<option>').text(value.masterDesc).attr('value',value.id));
				
			});
				
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
         }
	}); 

});