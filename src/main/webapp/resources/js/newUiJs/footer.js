
var blankMsg="This field is required";
var lengthMsg = "Minimum length should be ";

$(document).ready(function(){
	
	
	$("#footerDesc").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		this.value = this.value.replace(/[`~!@#$%^&*()_|+\-=?;:'"<>\{\}\[\]\\\/]/gi, '');
		 if ($("#footerDesc").val().length >= 3){
			 $("#footerDesc").addClass("input-correct").removeClass("input-error");
			 $("#feedback-query-req").hide();		 
		 }
		 if ($("#footerDesc").val().length < 3){
			 $("#footerDesc").addClass("input-error").removeClass("input-correct");
			 $("#feedback-query-req").show();	
			 $("#footerDesc").focus();
		 }
	});
	
	 $('#footerDesc').bind("cut copy paste",function(e) {
	     e.preventDefault();
	 });
	
	$("#footerSubmit").click(function(e){
		$('.loader').show();
		var errFlag = validateFooter();	
		if (errFlag){
			$(".loader").fadeOut("slow");
			e.preventDefault();	 
		}else{			
			$('#addFooterForm').submit();
		}
		
		 if(errFlag){
			 focusTextBox("footerDesc");
		 }
	});
	$(".loader").fadeOut("slow");
});

function validateFooter(){
	errFlag1 = validateTextField("footerDesc","feedback-query-req",blankMsg);
	if(!errFlag1){
		 errFlag1=validateFieldLength("footerDesc","feedback-query-req",lengthMsg,3);
	}
	return errFlag1;
}
