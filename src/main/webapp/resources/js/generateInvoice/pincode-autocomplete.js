$("#shipTo_pincode").autocomplete({
    source: function (request, response) {
        $.getJSON("getPinCodeList", {
            term: extractLast(request.term)
        }, function( data, status, xhr ) {
			response(data);
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
        });
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}    
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#shipTo_pincode").val();
    	//alert("Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	if(zipToShow.length === 6){
	        		 $("#zip").addClass("input-error").removeClass("input-correct");
	        		 $("#empty-message-1").text("No results found for selected pin code : "+zipToShow);
	        		 $("#empty-message-1").show();
	                 $("#zip,#pinCode").val("");
	                 $("#shipTo_city").val("");
	                 loadStateList();
	        	}
	        } else {
	            $("#empty-message-1").hide();
	            $("#shipTo_city").addClass("input-correct").removeClass("input-error");
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
			data : {"id" : pincode , "district" : district,_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(json,fTextStatus,fRequest) {
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				
				if(json==null){
					$("#shipTo_city").val("");
					$("#selectState").val("");
				}else{
					$("#shipTo_pincode").val(pincode);
					$("#shipTo_city").val(json.district);
					autoPopulateStateList(json.stateId);
					var stateRespone = getStateCode(json.stateInString);
					var values = stateRespone.split('{--}');
					var stateCode = values[0];
					var stateCodeId = values[1];
					var stateId = values[2];
					$("#shipTo_stateCode").val(stateCode);
					$("#shipTo_stateCodeId").val(stateCodeId);
			/*		$("#state,#custState").val(json.stateInString);
					$("#shipTo_pincode").val(pincode);
					$("#zip,#pinCode").addClass("input-correct").removeClass("input-error");
					$("#zip-req,#cust-zip-req").hide();*/
					$("#shipTo_pincode").addClass("input-correct").removeClass("input-error");
					
				}	
			},
			error: function noData() {
				bootbox.alert("No data found");
				
			}
        }); 
         return false;
    }
});

function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

function loadStateByPincode(){

	var pincode = $("#zip").val();
	var district = $("#city").val();
	
	  $.ajax({
      	url : "getPincodeByIdAndDistrict" ,
			type : "post",
			data : {"id" : pincode , "district" : district, _csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			success : function(json,fTextStatus,fRequest) {
				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}

				if(json==null){
					$("#city").val("");
					$("#state").val("");
				}else{
					$("#city").val(json.district);
					$("#cityToShow").val(json.district);
					$("#state").val(json.stateInString);
					$("#stateToShow").val(json.stateInString);
					
					$("#zip").val(pincode);
				
				}
				
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			},
			error: function noData() {
				bootbox.alert("No data found");
				
			}
      }); 

}

function validatePinCodeAndCity(pincode,district){
	var isError = false;
	 $.ajax({
     	url : "getPincodeByIdAndDistrict",
			type : "post",
			data : {"id" : pincode , "district" : district,_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(json,fTextStatus,fRequest) {
				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				if(json==null){
					$("#shipTo_pincode").val("");
					$("#shipTo_city").val("");
					$("#selectState").val("");
					$("#shipTo_pincode").addClass("input-error").removeClass("input-correct");
					$("#shipTo_city").addClass("input-error").removeClass("input-correct");
					$("#selectState").addClass("input-error").removeClass("input-correct");
					isError = true;
				}	
			},
			error: function noData() {
				bootbox.alert("No data found");
				
			}
     }); 
      return isError;
}