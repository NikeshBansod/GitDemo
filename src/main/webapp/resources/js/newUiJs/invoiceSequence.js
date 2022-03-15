$(document).ready(function(){
	var user_inv_seq_type = $("#user_inv_seq_type").val();
	
	if(user_inv_seq_type == 'Auto'){
		$('input:radio[name=invSeqType][id=radio1]').prop('checked', true);
	}else{
		$('input:radio[name=invSeqType][id=radio2]').prop('checked', true);
	}
	
	$("#submitId").click(function(e){
		var previous_inv_seq_type = $("#user_inv_seq_type").val();
		var selected_inv_seq_type = $("input[name='invSeqType']:checked").val();
		if(previous_inv_seq_type != selected_inv_seq_type){
			setInvoiceSequenceType(selected_inv_seq_type);
		}
	
		
	});
	
	$('.loader').hide();
});

function setInvoiceSequenceType(selected_inv_seq_type){
	$.ajax({
		url : "setInvSeqSettings" ,
		type : "post",
		data : {"mode":selected_inv_seq_type},
		headers: {_csrf_token : $("#_csrf_token").val()},
		dataType : "json",
		async : false,
		beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success : function(json,fTextStatus,fRequest) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if(json.status == 'SUCCESS'){
				bootbox.alert("Invoice Sequence updated successfully", function() {
					window.location.href = getHomePage();
					return;
				});
			}else{
				bootbox.alert("Failed to update Invoice Sequence.", function() {
				
				});
			}
			
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) { 							 
			 getInternalServerErrorPage();   
		}
	});
}