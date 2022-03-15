$(document).ready(function () {	
	    $("#removeButton").show();
	    var gstin = $("#reg-gstin").val();
	    var gstinRegistered = false;
	    var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
	    getDataByGstinNumber(gstin);
	
	        
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
				 //$("#reg-gstin").focus();
				 $("#reg-gstin-back-req").hide();
				 return false;
			}		
		}
	 	
	    
    
    
    if(validateGSTINWithRegex(gstin)){
    	if(gstin != ''){
		    $.ajax({
				url : "checkIfGstinIsRegistered",
				type : "POST",
				dataType : "json",
				data : {gstin : gstin},
				headers: {_csrf_token : $("#_csrf_token").val()},
				async : false,
				beforeSend: function(){
			         $('.loader').show();
			     },
			     complete: function(){
			         $('.loader').hide();
			     },
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
	    	getDataByGstinNumber(gstin);  
	    }		    
	}

 	function getDataByGstinNumber(gstin){
 		$("#reg-gstin-state").empty();
		$.ajax({
			url : "getDataByGstinNumber" ,
			type : "post",
			data : {"gstinNo":gstin},
			headers: {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },
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
				
				
				if(response.err_cd!=null){
					$('#state').val("");
					$("#gstinUname").val("");
					bootbox.alert("Invalid GSTIN,Please enter valid GSTIN");
				}
				
				else{ 								
					var address = "";
					getStateByGstinNumber(gstin);
					if(response.pradr != "" && response.pradr != undefined){
						if(response.pradr.addr != "" && response.pradr.addr != undefined){
							
							if(response.pradr.addr.bno != "" && response.pradr.addr.bno != undefined){
								address = response.pradr.addr.bno + ", ";
							}
							if(response.pradr.addr.bnm != "" && response.pradr.addr.bnm != undefined){ 
								address = address + response.pradr.addr.bnm + ", ";
							}
						}						
						address = address + response.pradr.addr.st + " "+ response.pradr.addr.loc; 
					}
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
 	
    function getStateByGstinNumber(gstin){
		$.ajax({
			url : "getStateByGstinNumber" ,
			type : "post",
			data : {"id":gstin},
			headers: {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },
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
    $(".loader").fadeOut("slow"); 
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
		beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },
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