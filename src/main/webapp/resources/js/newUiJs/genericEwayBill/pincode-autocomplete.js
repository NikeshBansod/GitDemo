
function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

function getInputPincodeDataJson(pincode){
	 var inputData = {
				"key" : pincode
     };
	 return inputData;
}

function loadPincodeNState(pincodeFieldId,pincodeVal,id,optionId,optionValue,spanId){
	var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var appCode = $("#appCode").val(); 
	var ipUsr = $("#ipUsr").val();
	var generateInputJsonData = getInputPincodeDataJson(pincodeVal);
	 $.ajax({
		url : "ewaybill/getewbPinCodeList",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
		data : JSON.stringify(generateInputJsonData),
		async : false,
		success:function(json){ 
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(json.status == 'failure'){ 
				$("#" + pincodeFieldId).css({"border-color" : "#ff0000"});
				$("#" + spanId).text(json.error_desc);
				$("#" + spanId).show();
			}else{
				$("#" + pincodeFieldId).css({"border-color" : "#498648"});
				$("#" + spanId).hide();
				$("#" + id).val(json.stateName);
				$("#" + optionId).val(json.stateId);
				$("#" + optionValue).val(json.stateName);
			}			
	     },
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
	}); 

}


