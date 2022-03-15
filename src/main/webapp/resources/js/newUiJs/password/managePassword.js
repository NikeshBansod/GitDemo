
$(document).ready(function(){
	
var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/ 
var blankMsg="This field is required";
var lengthMsg = "Minimum length should be ";
var regMsg = "data should be in proper format";
var pwdMsg = "confirm MPIN should match with MPIN";
var samePswd = "New MPIN should not be same as Old MPIN";

 $("#saveBtn").click(function(e){
	 $('.loader').show();
	 var errFlag = validateOldPassword();
	 var errFlag1 = validateNewPassword(); 
	 var errFlag2 = validatePwdWithConfPwd(); 
	 var errFlag3 = validateCpatchaText(); 
	 if ( (errFlag)  || (errFlag1) || (errFlag2) || (errFlag3) ){
		 $(".loader").fadeOut("slow");
		 e.preventDefault();	 
	 }
	 
	 if((errFlag)){
		 focusTextBox("oldPassword");
	 } else if((errFlag1) ){
		 focusTextBox("newPassword");
	 } else if((errFlag2)){
		 focusTextBox("confirmPassword");
	 } else if((errFlag3)){
		 focusTextBox("captchaImgText");
	 }
	 
});


function validateOldPassword(){
	errFlag = validateTextField("oldPassword","oldPwd-req",blankMsg);
	 if(!errFlag){
		 errFlag=validateRegexpressions("oldPassword","oldPwd-req",regMsg,pwdRegex);
	 }
	 return errFlag;
}

function validateNewPassword(){
	errFlag1 = validateTextField("newPassword","newPwd-req",blankMsg);
	 if(!errFlag1){
		 errFlag1=validateRegexpressions("newPassword","newPwd-req",regMsg,pwdRegex);
		 if(!errFlag1){
			 errFlag1=validateOldAndNewpassword("oldPassword","newPassword","newPwd-req",samePswd);
			 
		 }
	 }
	 return errFlag1;
}



function validatePwdWithConfPwd(){
	errFlag2 = validateTextField("confirmPassword","confPwd-req",blankMsg);
	 if(!errFlag2){
		 errFlag2=validateRegexpressions("confirmPassword","confPwd-req",regMsg,pwdRegex);
		 if(!errFlag2){
		errFlag2 = validatePasswordWithConfPwd("newPassword","confirmPassword","confPwd-req",pwdMsg);
		 }
	 }
	 return errFlag2
}



function validateCpatchaText(){
	errFlag3 = validateTextField("captchaImgText","captcha-req",blankMsg);
	 if(!errFlag3){
		 errFlag3=validateFieldLength("captchaImgText","captcha-req",lengthMsg,6);
	 }
	 return errFlag3;
}



$("#oldPassword").on("keyup input",function(){
	 var password = $("#oldPassword").val();
	 if ($("#oldPassword").val().length < 1){
		 $("#oldPassword").addClass("input-error").removeClass("input-correct");
		 $("#oldPwd-req").show();
		 $("#oldPassword").focus();
	 }
	 if ($("#oldPassword").val().length > 1){
		 $("#oldPassword").addClass("input-correct").removeClass("input-error");
		 $("#oldPwd-req").hide();		 
	 }

});




$("#newPassword").on("keyup input",function(){
	var newpassword = $("#newPassword").val();
	 if ($("#newPassword").val().length < 1){
		 $("#newPassword").addClass("input-error").removeClass("input-correct");
		 $("#newPwd-req").show();
		 $("#newPassword").focus();
	 }
	 if ($("#newPassword").val().length > 1){
		 if(validateNewPassword()){
			  $("#newPassword").addClass("input-error").removeClass("input-correct");
			  $("#newPwd-req").text(samePswd);
				 $("#newPwd-req").show(); 
		  }
	 }
	 if ($("#newPassword").val().length > 1){
		 if(!validateNewPassword()){
			  $("#newPassword").addClass("input-correct").removeClass("input-error");
			  $("#newPwd-req").text(samePswd); 
			  $("#newPwd-req").hide(); 
		  }
	 }
	 
});

$("#confirmPassword").on("keyup input",function(){
	 if ($("#confirmPassword").val().length < 1 ){
		 $("#confirmPassword").addClass("input-error").removeClass("input-correct");
		 $("#confPwd-req").show();
		 $("#confirmPassword").focus();
	 }
	 if ($("#confirmPassword").val().length > 1){
		 $("#confirmPassword").addClass("input-correct").removeClass("input-error");
		 $("#confPwd-req").hide();		 
	 }
	 
	 if ($("#confirmPassword").val() == $("#newPassword").val() ){
		 $("#confirmPassword").addClass("input-correct").removeClass("input-error");
		 $("#confPwd-req").hide();
	 } 
 
	 if ($("#confirmPassword").val() != $("#newPassword").val() ){
		 $("#confirmPassword").addClass("input-error").removeClass("input-correct");
		 $("#confPwd-req").show();
		 $("#confPwd-req").text(pwdMsg);
		 $("#confirmPassword").focus();
	 } 
	
});

$("#captchaImgText").on("keyup input",function(){
	 if ($("#captchaImgText").val().length > 1){
		 $("#captchaImgText").addClass("input-error").removeClass("input-correct");
		 $("#captcha-req").show();
		 $("#captchaImgText").focus();
	 }
	 if ($("#captchaImgText").val().length > 5){
		 $("#captchaImgText").addClass("input-correct").removeClass("input-error");
		 $("#captcha-req").hide();		 
	 }

	 if ($("#captchaImgText").val().length < 1){
		 $("#captchaImgText").addClass("input-error").removeClass("input-correct");
		 $("#captcha-req").show();		 
	 }
	
});



	
	$("#newPassword").blur(function(){
	    var password = $("#newPassword").val();
	    if(pwdRegex.test(password) == false){
			$("#password-format").show();			
			$("#newPassword").addClass("input-error").removeClass("input-correct");	
		} 
	    
	    if(pwdRegex.test(password)){
			$("#password-format").hide();
			$("#newPassword").addClass("input-correct").removeClass("input-error");
		}
	    
	    if ($("#newPassword").val().length > 1){
			 if(validateNewPassword()){
				  $("#newPassword").addClass("input-error").removeClass("input-correct");
				  $("#newPwd-req").text(samePswd);
					 $("#newPwd-req").show(); 
			  }
		 }
		 if ($("#newPassword").val().length > 1){
			 if(!validateNewPassword()){
				  $("#newPassword").addClass("input-correct").removeClass("input-error");
				  $("#newPwd-req").text(samePswd); 
				  $("#newPwd-req").hide(); 
			  }
		 }
	   
	});
	
	$("#oldPassword").blur(function(){
	    var password = $("#oldPassword").val();
	    if(pwdRegex.test(password) == false){
			$("#oldPwd-format").show();			
			$("#oldPassword").addClass("input-error").removeClass("input-correct");	
		} 
	    
	    if(pwdRegex.test(password)){
			$("#oldPwd-format").hide();
			$("#oldPassword").addClass("input-correct").removeClass("input-error");
		}
	});
	$(".loader").fadeOut("slow");
});
