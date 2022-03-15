$(document).ready(function(){
	var iterationNo = $("#iterationNo").val();
	var invoiceid = $("#invoiceid").val();
	var dash_conditionValue = $("#dash_conditionValue").val();
	if(iterationNo && invoiceid){
	 getLatestRRDocumentForInvoice(iterationNo,invoiceid);
	}
	
	
	$("#optionsDivRR").click(function(e){
		$('#optionsMultiDivRR').show();
		$('#optionsDivRR').hide();
		
	});
	
	if(dash_conditionValue == 'RRInvoiceHistoryD'){
		$("#buttonsForRR").show();
		$("#buttonsForInvoice").hide();
	}
	
})


function getLatestRRDocumentForInvoice(iterationNo,invoiceid){
	
	$.ajax({
		url : "getlatestrrdocumnetforinvoice",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
	    /*contentType : "application/json",*/
		data : {iterationNo:iterationNo , invoiceid:invoiceid},		
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
		success:function(json,fTextStatus,fRequest){				
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			if(json != null){
				/*$('.loader').fadeOut("slow");
				bootbox.alert(json);*/
				if(json !=iterationNo ){
					/*$("#conditionValueForRR").val("notLatestRR");*/
					$('#buttonsForInvoice').hide();
					$("#buttonsForRR").show();
				}
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
	
}

function downloadOldRecord(idValue,iterationNo){
	$('.loader').show();
	document.manageInvoice.action = "./downloadOldInvoices";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice.iterationNo.value = iterationNo;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$('.loader').fadeOut("slow");
}


