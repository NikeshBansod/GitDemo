function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

/*$(document).ready(function() {*/
	$( "#search-sac").autocomplete({
	    source: function (request, response) {
	        $.getJSON("getSacCodeList", 
	        		{term: extractLast(request.term)},
	        		function( data, status, xhr ) {
						response(data);
						//setCsrfToken(xhr.getResponseHeader('_csrf_token'));
			        }
	        );
	    },
	    select: function (event, ui) {
	    	var label = ui.item.label;
	        var value = ui.item.value;
	        //alert("Selected value : "+value);
	        var sacCode = (value.split('] - ')[0]).replace("[","").trim();
	        var sacDescription = (value.split('] - ')[1]).trim(); 
	        $("#sacCode").val(sacCode);
	        $("#sacCodeToShow").val(sacCode);
	        $("#sacDescriptionToShow").val(sacDescription);
	        $("#sacDescription").val(sacDescription);
	        $.ajax({
				url : "getIGSTValueBySacCode",
				method : "POST",
				/*contentType : "application/json",*/
				dataType : "json",
				data : { sacCode : sacCode, sacDescription : sacDescription,_csrf_token : $("#_csrf_token").val()},
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
					
					$("#serviceIgst").val(json.igst);
					$("#sacCodePkId").val(json.id);
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

				},
				error: function (data,status,er) {
					 
					 getInternalServerErrorPage();   
				}
	        }); 
	        
	    	if ( $("#sacDescription").val() !== "" && $("#sacCode").val() !== ""){
	    		$("#reg-gstin-req, #ser-sac-desc, #ser-sac-code").hide();
	    		//$("#search-sac").val("");
	    		$("#search-sac").removeClass("input-error");
	    	}
	    }

	});
	
/*});*/


