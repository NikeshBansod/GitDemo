var feedbackMasterDescription;
var masterDescription;
var totalMasterDescription;
$(document).ready(function(){
	loadFeedbackDetails();
	createDataTable('feedbackDataTable');
	$(".loader").fadeOut("slow");
	
});

function loadFeedbackDetails(){
    $.ajax({
		url : "getFeedbackDetails",
		method : "POST",
	//	contentType : "application/json",
		dataType : "json",
		async : false,
		headers: {_csrf_token : $("#_csrf_token").val()},
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
			loadMasterDescription();
			
			 $.each(json,function(i,value){
				 feedbackMasterDescription=value.masterDescDetails;
				 
				 $.each(totalMasterDescription,function(i,value){

					 if(feedbackMasterDescription == value.id)
						 {
						 masterDescription=value.masterDesc;
						 }
					 
					 
				 });
				 
				 var now = moment(value.createdOn);
				 var Onlydate= now.format("DD-MM-YYYY");
				 
				 $('#feedbackDataTable tbody:last-child')
					.append('<tr>'
						+'<td>'+(i+1)+'</td>'
						 +'<td ><a href="#" onclick="javascript:showDetailedFeedback('+value.masterDescDetails+','+value.id+','+value.userId+');">'+masterDescription+'</a>'
						 +'<td>'+Onlydate+'</a>'
                   +'</tr>'
	        	);
				 
				 
					 
			});
			 
			 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	}); 
	}
	
	function loadMasterDescription(){
	    $.ajax({
			url : "getMasterDescription",
			method : "POST",
		//	contentType : "application/json",
			dataType : "json",
			async : false,
			beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },
			success:function(json){
				totalMasterDescription=json;
	         },
	        
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
	         
		}); 
		}

          

function showDetailedFeedback(masterDescDetails,id,userId){
	
	document.feedbackDetail.action = "./showDetailedFeedback";
	document.feedbackDetail.masterDescDetails.value = masterDescDetails;
	document.feedbackDetail.id.value = id;
    document.feedbackDetail.userId.value = userId;
    document.feedbackDetail._csrf_token.value = $("#_csrf_token").val();
    document.feedbackDetail.submit();  
	}
 	