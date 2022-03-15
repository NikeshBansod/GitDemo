var feedbackMasterDescription;
var masterDescription;
var totalMasterDescription;
$(document).ready(function(){
	loadFeedbackDetails();
	
});

function loadFeedbackDetails(){
    $.ajax({
		url : "getFeedbackDetails",
		method : "POST",
	//	contentType : "application/json",
		dataType : "json",
		async : false,
		
		headers: {_csrf_token : $("#_csrf_token").val()},
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
				 
				 $(".dnynamicGstinDetails").append(
						  '<tr>'
                         /* +'<td><center><a href="#" onclick="javascript:showDetailedFeedback('+value.masterDescDetails+','+value.id+','+value.userId+');">'+value.id+'<center></td>'*/
						 +'<td ><center><a href="#" onclick="javascript:showDetailedFeedback('+value.masterDescDetails+','+value.id+','+value.userId+');">'+masterDescription+'<center></a>'
						 +'<td><center>'+Onlydate+'<center></a>'
                       /* +'<td ><center>'+value.feedbackDesc+'<center></a>'*/
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
			success:function(json){
				totalMasterDescription=json;
	         },
	        
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
	         
		}); 
		}
	
	
	
	

    
$(document).ready(function() {
    $('#feedbackDataTable').DataTable(
              {
            	  "aaSorting": [],
            	  "bLengthChange": false,
            	  "pagingType": "simple",
            	  
                    
              });
} );
          

function showDetailedFeedback(masterDescDetails,id,userId){
	
	document.feedbackDetail.action = "./showDetailedFeedback";
	document.feedbackDetail.masterDescDetails.value = masterDescDetails;
	document.feedbackDetail.id.value = id;
    document.feedbackDetail.userId.value = userId;
    document.feedbackDetail._csrf_token.value = $("#_csrf_token").val();
    document.feedbackDetail.submit();  
	}
 	