function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

var cache = {},
lastXhr;
var clientId = $("#clientId").val(); 
var secretKey = $("#secretKey").val(); 
var appCode = $("#appCode").val();

$( ".description").autocomplete({
    source: function (request, response) {
    	$.ajaxSetup({
    		  headers : {
    			  clientId : 'clientId',
    			  secretKey : 'secretKey',
    			  appCode : 'appCode'
    		  }
    		});
		$.getJSON("getewbHSNCodeList", 
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
        
       /* $("#hsn").val(hsnCode);
        $("#description").val(hsnDescription);*/    
        $(this).parent().parent().find('.hsn').val(hsnCode);
        $(this).val(hsnDescription);
    } 
    
});


/*
var counter = $("#itemCounter").val();
	$( "#description_"+counter).autocomplete({
	    source: function (request, response) {			   	
	    	var clientId = $("#clientId").val(); 
	    	var secretKey = $("#secretKey").val(); 
	    	var appCode = $("#appCode").val();
	    	var generateInputJsonData = getInputHSNDataJson(counter);
	    	 $.ajax({
	    		url : "getewbHSNCodeList",
	    		type : "POST",
	    		contentType : "application/json",
	    		dataType : "json",
	    		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode},
	    		data : JSON.stringify(generateInputJsonData),
	    		async : false,
	    		beforeSend: function(){
	    	    	$("#loadingmessage").show();
	    	    },
	    	    complete: function(){
	    	    	$("#loadingmessage").hide();
	    	    },
	    		success:function(json){ 
	    			$("#hsn_"+counter).val(hsnCode);
	    	        $("#description_"+counter).val(hsnDescription);    	    			
	             }
	    	}); 
	    }
	});
	
	function getInputHSNDataJson(counter){
		var hsn = $("#hsn_"+counter).val();
		var inputData = {
					"key" : hsn
	     };
		 return inputData;
	}
*/