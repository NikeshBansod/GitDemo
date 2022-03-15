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
			$("#userFeedback").blur(function(){
			    var feedback = $("#userFeedback").val();
			    if(!$('#userFeedback').val()){
					$("#userFeedback").addClass("input-error").removeClass("input-correct");	
				} 
			    else{
					$("#userFeedback").addClass("input-correct").removeClass("input-error");
				}
			});
		}else{
			$("#divFeedBack").hide();
		}
	});
	$("#deleteBtn").click(function(e){
		if($("#reasonOfDeletion").val()=="Other"){
			$('.loader').show();
			    if(!$('#userFeedback').val()){
					$("#userFeedback").addClass("input-error").removeClass("input-correct");
					$("#selectGSTINLocation-req").show();
					$(".loader").fadeOut("slow");
				} 
			    else{
			    	$('.loader').show();
					$("#userFeedback").addClass("input-correct").removeClass("input-error");
					bootbox.confirm("Are you sure you want to delete Your Account Permanently ?", function(result){
						if (result){
							$("#deleteAccount").submit();	
						 }
					});
				}
			    $(".loader").fadeOut("slow");
			}
		else{
			$("#userFeedback").addClass("input-correct").removeClass("input-error");
			$('.loader').show();
			bootbox.confirm("Are you sure you want to delete Your Account Permanently ?", function(result){
				if (result){
					$("#deleteAccount").submit();
				 }
			});
		}
		$(".loader").fadeOut("slow");
	});
	function valateOldPassword(){
		errFlag = validateSelectField("userFeedback","selectGSTINLocation-req",blankMsg);
		 return errFlag;
	}
	
	$("#userFeedback").on("keyup input",function(){
		 if ($("#userFeedback").val().length < 1){
			 $("#userFeedback").addClass("input-error").removeClass("input-correct");
			 $("#selectGSTINLocation-req").show();
			 $("#userFeedback").focus();
		 }
		 if ($("#userFeedback").val().length > 1){
			 $("#userFeedback").addClass("input-correct").removeClass("input-error");
			 $("#selectGSTINLocation-req").hide();		 
		 }
	});
	$(".loader").fadeOut("slow");
	
});





