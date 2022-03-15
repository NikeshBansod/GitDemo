$(document).ready(function () {		
	 /* $("#editAddButton").click(function () {
	        if( ($('.form-horizontal .control-group').length+1) > 6) {
	            alert("Only 6 control-group allowed");
	            return false;
	        }
	        var id = ($('.form-horizontal .control-group').length + 1).toString();
	        $('.form-horizontal').append('<div class="control-group" id="control-group' + id + '"><label class="control-label" for="gstinLocation' + id + '">GSTIN Location' + id + '</label><div class="controls' + id + '"><input type="text" id="gstinLocation' + id + '" name="gstinLocationSet['+(id-1)+'].gstinLocation" placeholder="GSTIN Location"></div></div>');
	    });

	    $("#editRemoveButton").click(function () {
	        if ($('.form-horizontal .control-group').length == 1) {
	            alert("No more textbox to remove");
	            return false;
	        }
	        $(".form-horizontal .control-group:last").remove();
	    });
	*/
	    var gstin = $("#reg-gstin").val();
	    var gstinRegistered = false;
	    var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//	    var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
	    getDataByGstinNumber(gstin);
	/*    
	    function validateGSTINWithRegex(gstin){
			if(GstinNumRegex.test(gstin) === true){				
				 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
				 $("#reg-gstin-req").hide();
				 $("#reg-gstin-back-req").hide();
				 return true;
			}
			if(GstinNumRegex.test(gstin) !== true){
				 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
				 $("#reg-gstin-req").show();
			//	 $("#reg-gstin").focus();
				 $("#reg-gstin-back-req").hide();
				 return false;
			}		
		}*/
	 	
	    
    
    
    if(validateGSTINWithRegex(gstin)){
    	if(gstin != ''){
		    $.ajax({
				url : "checkIfGstinIsRegistered",
				type : "POST",
				dataType : "json",
				data : {gstin : gstin},
				headers: {_csrf_token : $("#_csrf_token").val()},
				async : false,
				success : function(jsonVal,fTextStatus,fRequest) {
					if (isValidSession(jsonVal) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}		
					if(isValidToken(jsonVal) == 'No'){
						window.location.href = getCsrfErrorPage();
						return;
					}
					
					if(jsonVal == true){
						 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
						 $("#reg-gstin-back-req").text('GSTIN  - '+gstin+' already Registered. Try some other GSTIN.');
						 $("#reg-gstin-back-req").show();
						 gstinRegistered = true;
					}else{
						 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
						 $("#reg-gstin-req").hide();
						 $("#reg-gstin-back-req").hide();
						 gstinRegistered = false;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				},
				error: function (data,status,er) {					 
					 getInternalServerErrorPage();   
				}
		    });
    	}
	    if(gstinRegistered!=true){
	    	//getStateByGstinNumber(gstin);
	    	getDataByGstinNumber(gstin);  
	    }		    
	}

 	function getDataByGstinNumber(gstinNumber){
 		$("#reg-gstin-state").empty();
		$.ajax({
			url : "getDataByGstinNumber" ,
			type : "post",
			data : {"gstinNo":gstinNumber},
			headers: {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(response,fTextStatus,fRequest) {
				if (isValidSession(response) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				if(isValidToken(response) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				
				if(response==null){
					$("#state").val("");
				}else{ 								
					var address = "";
					getStateByGstinNumber(gstinNumber);
					if(response.pradr.addr.bno != ""){
						address = response.pradr.addr.bno + ", ";
					}if(response.pradr.addr.bnm != ""){ 
						address = address + response.pradr.addr.bnm + ", ";
					}
					address = address + response.pradr.addr.st + " "+ response.pradr.addr.loc; 
					$("#gstinUname").val(response.lgnm);
					$("#address1").val(address);
					/*$("#pinCode").val(response.pradr.addr.pncd);
					$("#city").val(response.pradr.addr.lgnm);
					$("#state").val(response.pradr.addr.lgnm);*/
					 								
				}
			},
			error: function (data,status,er) { 							 
				 getInternalServerErrorPage();   
			}
		});
 	}
 	
    function getStateByGstinNumber(gstinNumber){
		$.ajax({
			url : "getStateByGstinNumber" ,
			type : "post",
			data : {"id":gstinNumber},
			headers: {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(response,fTextStatus,fRequest) {
				if (isValidSession(response) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				if(isValidToken(response) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				if(response==null){
						$("#state").val("");
				}else{
					$("#reg-gstin-state").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId));					
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {				 
				 getInternalServerErrorPage();   
			}
		});
	}
    
});

function confirmGstinLocationRemove(e,locId){
	bootbox.confirm("Are you sure you want to remove ?", function(result){
		if (result){			
			removeGstinLocationById(locId);
		 }
	});
	 e.preventDefault();
}

function removeGstinLocationById(locId){	
	$.ajax({
		url : "removeGstinLocationById" ,
		type : "POST",
		dataType : "json",
		data : {locId:locId},
		headers: {_csrf_token : $("#_csrf_token").val()},
		success : function(response,fTextStatus,fRequest) {
			if (isValidSession(response) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(response) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if(response=="SUCCESS"){					
				bootbox.alert("SUCCESS");
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) {			 
			 getInternalServerErrorPage();   
		}
	});
}