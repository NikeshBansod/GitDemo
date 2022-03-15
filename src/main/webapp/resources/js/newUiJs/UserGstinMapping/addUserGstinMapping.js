var gstinUserExist = false;

$(document).ready(function(){
	getEmployeeList();
	$(".loader").fadeOut("slow");

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
			beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },

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


 
