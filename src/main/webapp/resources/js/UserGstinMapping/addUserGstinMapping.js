var gstinUserExist = false;

$(document).ready(function(){
	getEmployeeList();

});

function getEmployeeList(){
	
	 $.ajax({
			url : "getSecondaryUserList",
			method : "POST",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				$("#gstinUserSet").empty();
				$.each(json,function(i,value) {
					$("#gstinUserSet").append($('<option>').text(value.userName).attr('value',value.id));
				});	
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
				error: function (data,status,er) {
					 
					 getInternalServerErrorPage();   
				}
		}); 
}
 
