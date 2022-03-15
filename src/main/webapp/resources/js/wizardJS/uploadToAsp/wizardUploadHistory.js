
jQuery(document).ready(function($){
          
            if($("#actionPerformed").val()){
          		 $('#toggle').hide();
          	}

});


$(document).ready(function(){
	
	loadGstinListRoleWise();
	
	$("#userLogin").click(function (e){
			
		var errGSTIN = false;
		
		errGSTIN= validateGSTIN();
		
		if(errGSTIN){
			e.preventDefault();
		}
		
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else {
		
			loadHistory();
			$(".content").hide();
			$(".heading .cust-con").click(function () {
				$(this).parent().next(".content").slideToggle();
				
			});
		 }
	});
	
	
	function validateGSTIN(){
		errGSTIN = validateSelectField("gstinId","selectUser-req");
		 return errGSTIN;

	} 
	
});



function loadGstinListRoleWise(){
	
    $.ajax({
		url : "getGSTINListAsPerRole",
		method : "get",
	//	contentType : "application/json",
		dataType : "json",
		async : true,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			$("#gstinId").append('<option value="">Select your GSTIN</option>');
			$.each(json,function(i,value) {
				
				$("#gstinId").append($('<option>').text(value.gstinNo).attr('value',value.gstinNo));
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         }
	}); 
	
}

function loadHistory(){
	
	var gstin = $("#gstinId").val();
    if(gstin != ''){
	 $.ajax({
			url : "uploadHistory",
			type : "post",
			dataType : "json",
			data : {gstin : gstin},
			headers: {_csrf_token : $("#_csrf_token").val()},
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
				 $('.dnynamicHistoryDetails').empty();
				 if(json.length == 0){
					 bootbox.alert('No data uploaded for selected GSTIN');
				 } else {
				 $.each(json,function(i,value){
					var fmtMonth = moment(value.fpMonth, 'MM').format('MMMM');
					 $('.dnynamicHistoryDetails').append(
							'<div class="accordion no-css-transition mb0 smk_accordion acc_with_icon">'
		                     +' <div class="accordion_in acc_active">'  
							 +'<div class="heading">'
	    						+'<div class="cust-con fa fa-plus"> '+fmtMonth+' '+value.fpYear+ '</div>'
							  +'</div>'
							  +'<div class="content">'
							  +'<div class="cust-con">'
	    					+'<p> Upload Type : '+value.uploadType+'</p>'
							+'<p> Upload time : '+value.uploadDate+'</p>'	
							+'<p> Status : '+value.status+'</p>'	
							+'</div>'
						+'</div>'
						+'</div>'
						+'</div>'
					 );
				});
				 }
				 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {
	        	 
	        	 getWizardInternalServerErrorPage();   
	        }
		}); 
    }
}


