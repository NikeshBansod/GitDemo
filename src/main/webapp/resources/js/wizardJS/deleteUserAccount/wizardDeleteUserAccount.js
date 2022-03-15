$(document).ready(function(){
	$("#divFeedBack").hide();
	$("#deleteBtn").prop('disabled', true);
	
	$("#reasonOfDeletion").change(function(){
		if($("#reasonOfDeletion").val()=="Select Reason"){
			$("#deleteBtn").prop('disabled', true);
		}else {
			$("#deleteBtn").prop('disabled', false);
		}
		if($("#reasonOfDeletion").val()=="Other"){
			$("#divFeedBack").show();
		}else{
			$("#divFeedBack").hide();
		}
			
	});
	
	
	$("#deleteBtn").click(function(e){
		
		bootbox.confirm("Are you sure you want to delete Your Account Permanently ?", function(result){
			if (result){
				$("#deleteAccount").submit();	
			 }
		});
		
	});
	
	
});

