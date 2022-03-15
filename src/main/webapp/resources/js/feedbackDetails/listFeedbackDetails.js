
jQuery(document).ready(function($){
          
            if($("#actionPerformed").val()){
          		 $('#toggle').hide();
          	}

});


$(document).ready(function(){
	
	
	$("#userLogin").click(function (e){
		var masterDesc = $("#masterDesc").val();
		 var errFlag1 = validateMaster();
		
		 if ((errFlag1)){
			 e.preventDefault();	 
		 } else {
		
			loadHistory();
			$(".content").hide();
			$(".heading .cust-con").click(function () {
				$(this).parent().next(".content").slideToggle();
				
			});
		 }
	});
		
	function validateMaster(){
		errFlag1 = validateSelectField("masterDesc","master-desc");
		 return errFlag1;

	}
	
});


function loadHistory(){
	
	var masterDesc = $("#masterDesc").val();
    if(masterDesc != ''){
	 $.ajax({
			url : "./../listFeedbackDetailsForm",
			type : "post",
			dataType : "json",
			async : false,
			data : {masterDesc : masterDesc},
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
				
				 $('.dnynamicFeedbackDetails').empty();
				 if(json.length == 0){
					 bootbox.alert('No Feedback available for selected module');
				 } else {
				 $.each(json,function(i,value){
					 $('.dnynamicFeedbackDetails').append(
							'<div class="accordion no-css-transition mb0 smk_accordion acc_with_icon">'
		                     +' <div class="accordion_in acc_active">'  
							 +'<div class="heading">'
	    						+'<div class="cust-con fa fa-plus"> '+value.userId + '</div>'
							  +'</div>'
							  +'<div class="content">'
							  +'<div class="cust-con">'
	    					+'<p> Organization : '+value.orgniazationName+'</p>'
	    					+'<p> PAN : '+value.pan+'</p>'
	    					+'<p> Feedback : '+value.feedbackDesc+'</p>'
							+'<p> Feedback given on : '+value.createdDate+'</p>'	
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
	        	 
	        	 getInternalServerErrorPage();   
	        }

		}); 
    }
}


