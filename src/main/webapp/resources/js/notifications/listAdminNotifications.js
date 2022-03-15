jQuery(document).ready(function($){
	$(".accordion_example2").smk_Accordion({
	    closeAble: true, //boolean
	});
	if($("#actionPerformed").val()){
		 $('#toggle').hide();
	}
	
	$('#addCustomer').on('click', function(e) {
		$(".addCustomer").slideToggle();
		$('#addCustomer').hide();
	 	$('#toggle').hide();
		e.preventDefault();
	})
	
	$.ajax({
		url : "getINotificationList",
	    type : "POST",
	    dataType : "json",
	    headers : {_csrf_token : $("#_csrf_token").val()},
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
	    	$.each(json,function(i,value){	    		
	    		$('.dnynamicNotifications').append(
	    			'<div class="heading">'
	    				+'<div class="cust-con">'
	    					+'<h1>'+value.updatedOn+'</h1>'
	    				+'</div>'
	    				+'<div class="cust-edit">'
						  +'<div class="cust-icon">'
						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						  +'</div>'
						+'</div>'	    				
	    			+'</div>'
	    		
	    			+'<div class="content">'
	    				+'<div class="cust-con">'
	    					+value.notification	    				
	    				+'</div>'
	    			+'</div>'	
	    		 );	    		
	    	});	
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	    },
        error: function (data,status,er) {       	 
       	 getInternalServerErrorPage();   
       }
	}); 
	
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
            
});

function editRecord(idValue){
	document.manageNotification.action = "./editNotification";
	document.manageNotification.id.value = idValue;
	document.manageNotification._csrf_token.value = $("#_csrf_token").val();
	document.manageNotification.submit();
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
	 if (result){
		document.manageNotification.action = "./iDeletenotification";
		document.manageNotification.id.value = idValue;
		document.manageNotification._csrf_token.value = $("#_csrf_token").val();
		document.manageNotification.submit();	
	 }
	});
}


