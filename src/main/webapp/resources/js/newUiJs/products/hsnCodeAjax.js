function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

var cache = {},
lastXhr;
/*$(document).ready(function() {*/
	$( "#search-hsn").autocomplete({
	    source: function (request, response) {
			 $.getJSON("getHSNCodeList", 
					 { term: extractLast(request.term)}, 
					 function( data, status, xhr ) {
							response(data);
							//setCsrfToken(xhr.getResponseHeader('_csrf_token'));
					 }
			);
	    },
	    select: function (event, ui) {
	    	var label = ui.item.label;
	        var value = ui.item.value;
	        
	        var hsnCode = $.trim(value.split('] - ')[0].replace("[",""));
	        var hsnDescription = $.trim(value.split('] - ')[1]);     
	        
	        $("#hsnCode").val(hsnCode);
	        $("#hsnCodeToShow").val(hsnCode);
	        $("#hsnDescriptionToShow").val(hsnDescription);
	        $("#hsnDescription").val(hsnDescription);
	        
	    	if ($("#hsnDescription").val() !== "" && $("#hsnCode").val() !== ""){
	    		$("#prod-hsn-desc, #prod-hsn-code, #reg-gstin-req").hide();
	    		$("#search-hsn").removeClass("input-error");
	    	}
	        
	        $.ajax({
				url : "getIGSTValueByHsnCode",
				method : "POST",
				//contentType : "application/json",
				dataType : "json",
				data : { hsnCode : hsnCode, hsnDescription : hsnDescription, _csrf_token : $("#_csrf_token").val()},
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
					
					$("#productIgst").val(json.igst);
					$("#hsnCodePkId").val(json.id);
					
					if (json.igst !== ""){
						$("#prod-igst").hide();
			    		$("#productIgst").removeClass("input-error");
			    	}
					
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

				},
				error: function (data,status,er) {
					 
					 getInternalServerErrorPage();   
				}
	        }); 
	    }
	});
	
/*});*/