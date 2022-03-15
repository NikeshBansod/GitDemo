
jQuery(document).ready(function($){
   
	var panNo = $("#panNumber").val();
	$("#panNo").val(panNo);
	$("#userId").val('');
	$("#password").val('');
});


function val(){
	bootbox.alert($("#userName").val());
	return true;
}

$(document).ready(function(){
	
	var panNo = $("#panNumber").val();
	var usrId = $("#usrId").val();
	var pswd = $("#pswd").val();
		     
});


$(document).ready(function (){
	
	var blankMsg="This field is required";
	var lengthMsg = "Minimum length should be ";
	var pwdRegex = /^([a-zA-Z0-9]+)$/;
	var regMsg = "data should be in proper format";
	
	$("#userLogin").click(function(e){

		var errFlag1 = validateUserId();
		var errFlag2 = validatePassword();
		
	 	if( (errFlag1) || (errFlag2)){
	 		e.preventDefault();
		}	
	 	
	 	if((errFlag1)){
			 focusTextBox("userId");
		 } else if((errFlag2) ){
			 focusTextBox("password");
		 } 
	 	
	});

		function validateUserId(){
			errFlag1 = validateTextField("userId","userId-req",blankMsg);
			 if(!errFlag1){
				 errFlag1=validateFieldLength("userId","userId-req",lengthMsg,2);
			 }
			 return errFlag1;
		}
		
		function validatePassword(){
			errFlag2 = validateTextField("password","password-req",blankMsg);
			
			 return errFlag2;
		}
		
		
		$("#userId").on("keyup input",function(e){
			
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			
			 if ($("#userId").val().length > 1){
				 $("#userId").addClass("input-error").removeClass("input-correct");
				 $("#userId-req").show();
				 $("#userId").focus();
			 }
			 
			 if ($("#userId").val().length > 1){
				 $("#userId").addClass("input-correct").removeClass("input-error");
				 $("#userId-req").hide();		 
			 }

			 if ($("#userId").val().length < 1){
				 $("#userId").addClass("input-error").removeClass("input-correct");
				 $("#userId-req").show();		 
			 }
		});
		
		
		$("#password").on("keyup input",function(){
			 if ($("#password").val() !== "" ){
				 $("#password").addClass("input-correct").removeClass("input-error");
				 $("#password-req").hide();		 
			 }
						 
		});
				
});


function is_int(value) {
    if ((parseFloat(value) == parseInt(value)) && !isNaN(value)) {
      return true;
    } else {
      return false;
    }
  }

