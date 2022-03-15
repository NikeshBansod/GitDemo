 
jQuery(document).ready(function($){
	
	
$.ajax({
		url : "getAdminMasterDescList",
		type : "POST",
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			$("#masterDesc").empty();
			
			$("#masterDesc").append('<option value="">Select Area of feedback</option>');
			
			$.each(json,function(i,value) {
				
					$("#masterDesc").append($('<option>').text(value.masterDesc).attr('value',value.id));
				
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }

	}); 

});