$(document).ready(function(){
	
	loadFP();
	
	$("#userLogin").click(function (e){
		
		var errFp = false;
		errFp = validateFinPeriod();
		
		if(errFp){
			e.preventDefault();
		}
		
		if((errFp)){
			 focusTextBox("financialPeriod");
		 } else{
		$("#userLogin").attr("disabled", "disabled");
		$("#userLogin").text('Uploading');
		$("#uploadAsp").submit();
		 }
	});
	
	
	function validateFinPeriod(){
		errGSTIN = validateSelectField("financialPeriod","master-desc");
		 return errGSTIN;

	} 
		  	
});


function loadFP(){
	
    $.ajax({
		url : "getFpMonthsArray",
		method : "POST",
	//	contentType : "application/json",
		dataType : "json",
	//	headers: {_csrf_token : $("#_csrf_token").val()},
		async : true,
		success:function(json){
						
			$("#financialPeriod").append('<option value="">Select </option>');
			$.each(json,function(key,value) {
				$("#financialPeriod").append($('<option>').text(key).attr('value',value));
			});
			
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }

	}); 
	
}