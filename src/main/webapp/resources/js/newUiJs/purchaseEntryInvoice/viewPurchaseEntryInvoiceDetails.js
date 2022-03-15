$(document).ready(function () {	
	$('#divTwo').hide();
	$('.loader').fadeOut("slow");
	$('#divTwo').show();
});

function redirectToInvoiceHistoryPage(){
	return 'getMyPurchaseEntryPage';
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
				$('.loader').show();
				$("#divTwo").hide();
				  $.ajax({
						url : "delPurchaseEntryInv",
						headers: {
							_csrf_token : $("#_csrf_token").val()
						},
						method : "post",
						data : { "id" : idValue },
						dataType : "json",
						async : false,
						beforeSend: function(){
						    	$('.loader').show();
						    	$('#breadcumheader').hide();
						},
						complete: function(){
						    	$('#breadcumheader').show();
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
							//var json = $.parseJSON(jsonobj);
							if(json.status === 'ACCESS_DENIED'){
								$('.loader').fadeOut("slow");
								bootbox.alert(json.response, function() {
									window.location.href = getDefaultSessionExpirePage();
								});
							}else{								
								if(json.status === 'FAILURE'){
									$("#divTwo").show();
									$('.loader').fadeOut("slow");
									bootbox.alert(json.response);
								}else{
									$('.loader').fadeOut("slow");
									bootbox.alert(json.response, function() {
										window.location.href = redirectToInvoiceHistoryPage();
									});
								}								
							}							
						},
			        error:function(data,status,er) { 
			        	getInternalServerErrorPage();   
			         }
				});
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
		beforeSend: function(){
	    	$('.loader').show();
	    },
	    complete: function(){
	    	$('.loader').hide();
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
			beforeSend: function(){
		    	$('.loader').show();
		    },
		    complete: function(){
		    	$('.loader').hide();
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
				//var json = $.parseJSON(jsonobj);
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				bootbox.alert(json);				
			},
        error:function(data,status,er) { 
          	getInternalServerErrorPage(); 
         }
	});
}

