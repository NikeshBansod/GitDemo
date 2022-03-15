
function loadIgst(){
	var selectType =$("#igstHidden").val();
	$.ajax({
		  url:"getiServiceRateOfTaxDetails", 	
		  type : "POST",
		  headers : {_csrf_token : $("#_csrf_token").val()},
		  dataType: 'json',
		  async:false,
		  success:function(json,fTextStatus,fRequest) {
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			  $("#igst").empty();
			  $("#igst").append($('<option>').text("Select Rate of tax ").attr('value',""));
			  $.each(json, function(i, value) {				  
				if(selectType==value.taxRate){
					 $('#igst').append($('<option>').text(value.taxRate).attr('value', value.taxRate).attr('selected','selected'));
				 }else{
					 $('#igst').append($('<option>').text(value.taxRate).attr('value', value.taxRate));
				 }
				    
				});
		  },
          error: function (data,status,er) {       	 
           	 getInternalServerErrorPage();   
          }
	});
}

$(document).ready(function(){
	loadIgst();
});