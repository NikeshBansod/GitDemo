
function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

$("#shipTo_pincode").autocomplete({
    source: function (request, response) {
        $.getJSON("getPinCodeList", {term: extractLast(request.term)}, 
        		function( data, status, xhr ){
		        	response(data);
					setCsrfToken(xhr.getResponseHeader('_csrf_token'));
        		}
        );
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}    
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#shipTo_pincode").val();
        if (ui.content.length === 0) {
        	if(zipToShow.length === 6){
        		 $("#empty-message-1").text("No results found for selected Pin code : "+zipToShow);
        		 $("#empty-message-1").show();
                 $("#zip,#pinCode").val("");
                 $("#shipTo_city").val("");
        	}
        } else {
            $("#empty-message-1").hide();
            $("#supplier_state").addClass("input-correct").removeClass("input-error");
        } 
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var pincode = (value.split('[')[0]).trim();
        var district = value.substring(value.indexOf("[ ") + 1, value.indexOf(" ]")).trim();
     
         $.ajax({
        	url : "getPincodeByIdAndDistrict",
			type : "post",
			data : {"id" : pincode , "district" : district, _csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(json,fTextStatus,fRequest) {
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				if(json==null){
					$("#supplier_state").val("");
				}else{
					$("#shipTo_pincode").val(pincode);
					$("#shipTo_city").val(json.district);
					autoPopulateStateList(json.stateId);
					/*var stateRespone = getStateCode(json.stateInString);
					var values = stateRespone.split('{--}');					
					var stateCode = values[0];
					var stateCodeId = values[1];
					var stateId = values[2];					
					$("#supplier_stateCode").val(stateCode);
					$("#supplier_stateCodeId").val(stateCodeId);*/
					$("#supplier_pincode").addClass("input-correct").removeClass("input-error");					
				}					
			},
			error: function noData() {
				bootbox.alert("No data found");
			}
        }); 
         return false;
    }
});

function autoPopulateStateList(stateId){
	$.ajax({
		url : "getStatesList",
		method : "POST",
		dataType : "json",
		data : {_csrf_token : $("#_csrf_token").val() },
		async : false,
		  success:function(json,fTextStatus,fRequest) {
			  if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
			  }
			  $.each(json, function(i, value) {				   
				  if(stateId == value.id){
					  $("#supplier_state").val(value.stateName);
					  $("#supplier_stateCode").val(value.stateCode);
					  $("#supplier_stateCodeId").val(value.stateId);				  
				  }
			  });	
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		  }
	});	
}

/*function getStateCode(stateName){
	var response = "";
	var stateCode = "";
	var stateCodeId = 0;
	//alert("stateName : "+stateName);
	$.ajax({
		url : "getStateCodeByStateName",
		method : "POST",
		contentType : "application/json",
		dataType : "json",
		data : { stateName : stateName},
		//async : false,
		success : function(json) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			//var json = $.parseJSON(jsonobj);
			response = json.stateCode+"{--}"+json.stateId+"{--}"+json.id
		}
    }); 
	return response;
}*/
	
