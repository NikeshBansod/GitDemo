
jQuery(document).ready(function(){
	$(".customerValuesTable").hide();
	var address = $("#custAddress").val();
	$("#custAddress1").text(address);
	
	var custGstinStateHidden= $("#custGstinStateHidden").val();
	var custGstinHidden= $("#custGstinHidden").val();
	if(custGstinHidden != '' && custGstinHidden != ''){
	loadGstinState(custGstinHidden,custGstinStateHidden);
	
	}
			
});

function loadGstinState(custGstinHidden,custGstinStateHidden){
	
	$("#custGstinState").empty();
	$.ajax({
		url : "getStateByGstinNumber" ,
		type : "post",
		data : {"id":custGstinHidden},
		headers: {_csrf_token : $("#_csrf_token").val()},
		dataType : "json",
		async : false,
		success : function(response,fTextStatus,fRequest) {
			if (isValidSession(response) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(response) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if(response==null){
					$("#state").val("");
			}else{
				if(custGstinStateHidden==response[0].stateId){
					
					$("#custGstinState").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId).attr('selected','selected'))
				}else{
					$("#custGstinState").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId))
				}
				
			}
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
		},
		
		error: function(xhr, textStatus, errorThrown){
		bootbox.alert('request failed');
	      
	    }
	});
}

$('#editCancel').on('click', function() {
	$(".customerValuesTable").show();
	
});
