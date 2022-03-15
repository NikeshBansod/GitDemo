$(document).ready(function(){
	var gstinId = $("#gstinId").val();
	var selectedGSTINAddr = $("#selectedGSTINAddr").val();
	var selectedGSTIN = $("#selectedGSTIN").val();
	
    $.ajax({
		url : "getGstinList",
		method : "POST",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$.each(json,function(i,value) {
				if(gstinId == value.id){
					  $('#selectGstinId').val(value.gstinNo); 
				 }
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
        },
			error: function (data,status,er) {
				 
				getWizardInternalServerErrorPage();   
			}
	}); 
 
   $.ajax({
		url : "getGstinLocationDetails",
		type : "POST",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
		data : {gstinId:gstinId},
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$.each(json,function(i,value) {
				var count = 0;
					if(selectedGSTINAddr == value.id){
						  $('#gstinAddressMapping').append($('<option>').text(value.gstinLocation).attr('value', value.id).attr('selected','selected'));
					}else{
						 $('#gstinAddressMapping').append($('<option>').text(value.gstinLocation).attr('value', value.id));
					}
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
        },
			error: function (data,status,er) {
				 
				getWizardInternalServerErrorPage();   
			}
	}); 
    
    
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
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			$("#gstinUserSet").empty();
			$.each(json,function(i,value) {
				selectedGSTIN = selectedGSTIN.replace("[", "").trim();
				selectedGSTIN = selectedGSTIN.replace("]", "").trim();
						var str_array = selectedGSTIN.split(',');
						var isSelected = false;
						 for(var i = 0; i < str_array.length; i++) {							 
							   if(str_array[i] == value.id){
								   isSelected = true;									  
							   }
						 }
						 if(isSelected == true){
							 $('#gstinUserSet').append($('<option>').text(value.userName).attr('value', value.id).attr('selected','selected'));
						 } else {
							 $('#gstinUserSet').append($('<option>').text(value.userName).attr('value', value.id));
						 }
				
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
        },
			error: function (data,status,er) {
				 
				getWizardInternalServerErrorPage();   
			}
	});
           
});