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
	        		 $("#shipTo_pincode").addClass("input-error").removeClass("input-correct");
	        		 $("#empty-message-1").text("No results found for selected pin code : "+zipToShow);
	        		 $("#empty-message-1").show();
	                 $("#shipTo_pincode").val("");
	                 $("#shipTo_city").val("");
	                 $("#pos").val("");
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
					$("#shipTo_pincode").addClass("input-correct").removeClass("input-error");
					$("#shipTo-customer-pincode-csv-id").hide();
					$("#shipTo-customer-city-csv-id").hide();
					
				}	
			},
			error: function noData() {
				bootbox.alert("No data found");
				
			}
        }); 
         return false;
    }
});
