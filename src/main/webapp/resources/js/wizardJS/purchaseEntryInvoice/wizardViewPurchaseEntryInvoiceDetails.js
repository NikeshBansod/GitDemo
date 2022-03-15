$(document).ready(function () {	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#optionsDiv').hide();	
	});
});

function redirectToPurchaseEntryInvoiceHistoryPage(){
	return 'wgetmypurchaseentrypage';
}

function deleteInvoice(idValue){	
	bootbox.confirm({
		message: "Do you really want to delete the document ? ",
	    buttons: {
	        confirm: {
	            label: 'Delete this document',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: 'Cancel',
	            className: 'btn-danger'
	        }
	    },
	    callback: function (result) {
		   if (result){ 
			    $('#loadingmessage').show();
			   	$('.lk-toggle-nav').hide();
			   	$("#header").hide();
				$("#divTwo").hide();
				setTimeout(function(){
				  $.ajax({
						url : "delPurchaseEntryInv",
						headers: {
							_csrf_token : $("#_csrf_token").val()
						},
						method : "post",
						data : { "id" : idValue },
						dataType : "json",
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
							
							setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
							//var json = $.parseJSON(jsonobj);
							if(json.status === 'ACCESS_DENIED'){
								bootbox.alert(json.response, function() {
									setTimeout(function(){
										window.location.href = getDefaultSessionExpirePage();
									}, 500);
								});
							}else{		
								 $('#loadingmessage').hide();
								if(json.status === 'FAILURE'){
									$("#divTwo").show();
									bootbox.alert(json.response);
									$('.lk-toggle-nav').show();
								   	$("#header").show();
								}else{
									bootbox.alert(json.response, function() {
										setTimeout(function(){
											bootbox.alert(json.response);
											window.location.href = redirectToPurchaseEntryInvoiceHistoryPage();
										}, 500);
									});
								}								
							}							
						},
						error:function(data,status,er) { 
							getWizardInternalServerErrorPage();   
						}
					});
				}, 3000);
		   }
		}		
	});	
}

function shareShortURL(invId) {
	 $.ajax({
			url : "genTinyUrl",
			method : "post",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			data : {invId : invId},
			dataType : "json",
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
				
				var response = JSON.parse(json);	
				if(response.shorturl){
					//window.app.ShareInvoice(response.shorturl);
				}else{
					bootbox.alert("Something went wrong");
				}				
	         },
	         error:function(data,status,er) { 
	        	 getInternalServerErrorPage();    
	         }
	});    
} 

function sendMail(idValue){	
	  $.ajax({
			url : "sendMailToCustomerFromPreview",
			method : "post",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
		    /*contentType : "application/json",*/
			dataType : "json",
			data : { "id" : idValue },
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
				//var json = $.parseJSON(jsonobj);
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				bootbox.alert(json);				
			},
        error:function(data,status,er) { 
            //alert("error: "+data+" status: "+status+" er:"+er);
        	console.log("Error in sendMailToCustomerFromPreview");
          	getInternalServerErrorPage(); 
         }
		});
}