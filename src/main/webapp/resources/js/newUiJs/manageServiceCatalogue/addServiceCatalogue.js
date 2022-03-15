
$("#name").blur(function(){
	
	var service = $("#name").val();
	if(service != ''){
		checkIfServiceAlreadyExists(service);
	}
	$('.loader').fadeOut("slow"); 
});

function checkIfServiceAlreadyExists(service){
	$.ajax({
		url : "checkIfServiceExists",
		type: 'POST',
		dataType : "json",
		async : false,
		data : {"service":service,_csrf_token : $("#_csrf_token").val()},
		success : function(jsonVal,fTextStatus,fRequest) {
			if (isValidSession(jsonVal) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(jsonVal) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if(jsonVal == true){
				 $("#name").addClass("input-error").removeClass("input-correct");
				 $("#ser-name").text('service Name  - '+service+' already Exists for your Organization');
				 $("#ser-name").show();
				 serviceExists = true;
			}else{
				if(service.length > 0){
				 $("#ser-name").addClass("input-correct").removeClass("input-error");
				 $("#ser-name").hide();
				 serviceExists = false;
				}
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
    });
	return serviceExists;
}
