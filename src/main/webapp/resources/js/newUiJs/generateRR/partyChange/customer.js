function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

function getStateCode(stateName){
	var response = "";
	if(stateListJson == ''){
		loadStateList();
	}
	$.each(stateListJson,function(i,value) {
		if(value.stateName == stateName){
			response = value.stateCode+"{--}"+value.stateId+"{--}"+value.id;
		}
		
	});
	return response;
}

function autoPopulateStateList(stateId){
	
	if(stateListJson == ''){
		loadStateList();
	}
	$('#selectState').empty();
	$('#selectState').append('<option value="">--Select reason --</option>');
	$.each(stateListJson,function(i,value) {
		if(stateId == value.id){
			  $('#selectState').append($('<option>').text(value.stateName).attr('value', value.stateId).attr('selected','selected')); 
			  $("#pos").val(value.stateName);
		 }else{
			 $('#selectState').append($('<option>').text(value.stateName).attr('value', value.stateId));
		 }
	});
	
}

function loadStateList(){
	if(stateListJson == ''){
		 $.ajax({
		    	url : "getStatesList",
		    	method : "POST",
				dataType : "json",
				headers: {_csrf_token : $("#_csrf_token").val()},
				async : false,
				success:function(json,fTextStatus,fRequest){
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}
					
					if(isValidToken(json) == 'No'){
						window.location.href = getCsrfErrorPage();
						return;
					}
					
					$("#selectState").empty();
					$("#selectState").append('<option value="">Select Place Of Supply</option>');
					$.each(json,function(i,value) {
						
						$("#selectState").append($('<option>').text(value.stateName).attr('value',value.id));
					});
					stateListJson = json;
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		         },
		         error: function (data,status,er) {
		        	 
		        	 getInternalServerErrorPage();   
		         }
			});
	}
    
}

$("#customer_name").autocomplete({
    source: function (request, response) {
        $.getJSON("getCustomerDetailsForAutoCompleteList", {
            term: extractLast(request.term),
            documentType : $("#selectedDocType").val()
        }, function( data, status, xhr ) {
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
			response(data);
        });
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}

        
    },
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#customer_name").val();
    	//alert("Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	if(zipToShow.length >= 3){
	        		 $("#customer_name").addClass("input-error").removeClass("input-correct");
	        		 /*$("#empty-message").text("No results found for selected value : "+zipToShow);*/
	        		 $("#empty-message").show();
	                 $("#customer_name").val("");
	                 loadStateList();
	        	}
	        	
	        } else {
	            $("#empty-message").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var contactNo = (value.split('] - ')[0]).replace("[","").trim();
        var custName = (value.split('] - ')[1]).trim(); 
        $.ajax({
			url : "getCustomerDetailByCustNameAndContactNo",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
			data : { custName : custName, contactNo : contactNo},
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
				 $("#customer_contactNo").val(contactNo);
				 $("#customer_place").val(json.custAddress1+","+json.custCity);//mandatory
				 $("#customer_country").val(json.custState);//mandatory
				 $("#customer_id").val(json.id);//mandatory
				 $("#customer_custAddress1").val(json.custAddress1);//optional
				 //$("#customer_custAddress2").val(json.custAddress2);//optional
				 $("#customer_custCity").val(json.custCity);//optional
				 $("#customer_custState").val(json.custState);//optional
				 $("#customer_custType").val(json.custType);
				 var stateRespone = getStateCode(json.custState);
				 var values = stateRespone.split('{--}');
				 var stateCode = values[0];
				 var stateCodeId = values[1];
				 var stateId = values[2];
				 $("#customer_custStateCode").val(stateCode);
				 $("#customer_custStateCodeId").val(stateCodeId);
				 $("#customer_custStateId").val(stateId);
				 $("#customer_custGstId").val(json.custGstId);
				 $("#customer_custEmail").val(json.custEmail);
				 //getPoDetails(json.id);
				 autoPopulateStateList(stateId);
			}
        }); 
    }
});