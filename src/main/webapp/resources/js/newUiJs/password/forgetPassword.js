
$(document).ready(function(){
	
 $(".user-drop").hide();
 $("#pwdChange").removeClass("ruGST");
 
var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/ 
var blankMsg="This field is required";
var regMsg = "MPIN should be in proper format Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character";
var pwdMsg = "confirm MPIN should match with MPIN";
var samePswd = "New MPIN should not be same as Old MPIN";

 $("#saveBtn").click(function(e){
	 $('.loader').show();
	 var errFlag1 = validatePassword(); 
	 var errFlag2 = validatePwdWithConfPwd();
	 var errFlag3 = validateCapta();
	 if ( (errFlag1)  || (errFlag2) || (errFlag3)){
		 $(".loader").fadeOut("slow");
		 e.preventDefault();	 
	 }
	 
	   if((errFlag1) ){
		 focusTextBox("password");
	 } else if((errFlag2)){
		 focusTextBox("conf-password");
	 } 
	 else if((errFlag3)){
		 focusTextBox("captchaImgText");
	 } 
	   
});

 
 $("#verifyUserBtn").click(function(e){
	 $('.loader').show();
	 if($("#userId").val()==""){
		 $("#userId").addClass("input-error").removeClass("input-correct");
		 $("#userId-req").show();
	 }else if($("#userId").val()!=""){
		 $("#userId").addClass("input-correct").removeClass("input-error");
		 $("#userId-req").hide();
		 verifyUserIdAndSendOtp();
	 }
	 $(".loader").fadeOut("slow");
	 e.preventDefault();
 });
 
 $("#verifyOtp").click(function(e){
	 $('.loader').show();
	 if($("#otp").val()==""){
		 $("#otp").addClass("input-error").removeClass("input-correct");
		 $("#otp-req").show();
	 }else if($("#userId").val()!=""){
		 $("#otp").addClass("input-correct").removeClass("input-error");
		 $("#otp-req").hide();
		 verifyOtp();
	 }
	 $(".loader").fadeOut("slow");
	 
 });
 
function validatePassword(){
	errFlag1 = validateTextField("password","password-req",blankMsg);
	 if(!errFlag1){
		 errFlag1=validateRegexpressions("password","password-req",regMsg,pwdRegex);
	 }
	 return errFlag1;
}


function validatePwdWithConfPwd(){
	errFlag2 = validateTextField("conf-password","conf-password-req",blankMsg);
	 if(!errFlag2){
		 errFlag2=validateRegexpressions("conf-password","conf-password-req",regMsg,pwdRegex);
		 if(!errFlag2){
		errFlag2 = validatePasswordWithConfPwd("password","conf-password","conf-password-req",pwdMsg);
		 }
	 }
	 return errFlag2
}

function validateCapta(){
	errFlag3 = validateTextField("captchaImgText","captcha-req",blankMsg);
	 return errFlag3;
}

$("#userId").on("keyup input",function(){
	
	this.value = this.value.replace(/^0/, '');
	 this.value = this.value.replace(/[^0-9]+/, '');

});

$("#password").on("keyup input",function(){
	 if ($("#password").val().length < 1){
		 $("#pwdChange").addClass("ruGST");
		 $("#password").addClass("input-error").removeClass("input-correct");
		 $("#password-req").show();
		 $("#password").focus();
	 }
	 if ($("#password").val().length >= 1){
		 if(validatePassword()){
			 $("#pwdChange").addClass("ruGST");
			  $("#password").addClass("input-error").removeClass("input-correct");
				 $("#password-req").text(regMsg); 
				 $("#password-req").show(); 
		  }
	 }
	 if ($("#password").val().length >= 1){
		 if(!validatePassword()){
			 $("#pwdChange").removeClass("ruGST");
			  $("#password").addClass("input-correct").removeClass("input-error");
				 $("#password-req").text(regMsg); 
				 $("#password-req").hide(); 
		  }
	 }
	 if($("#conf-password").val()!=""){
		 if(validatePwdWithConfPwd()){
			  $("#conf-password").addClass("input-error").removeClass("input-correct");
			  $("#conf-password-req").show();
		  }
	 } 

});

$("#conf-password").on("keyup input",function(){
	 if ($("#conf-password").val().length < 1 ){
		 $("#conf-password").addClass("input-error").removeClass("input-correct");
		 $("#conf-password-req").show();
		 $("#conf-password").focus();
	 }
	 if ($("#conf-password").val().length > 1){
		 $("#conf-password").addClass("input-correct").removeClass("input-error");
		 $("#conf-password-req").hide();		 
	 }
	 
	 if ($("#conf-password").val() == $("#password").val() ){
		 $("#conf-password").addClass("input-correct").removeClass("input-error");
		 $("#conf-password-req").hide();
		 $("#password-req").hide(); 
	 } 

	 if ($("#conf-password").val() != $("#password").val() ){
		 $("#conf-password").addClass("input-error").removeClass("input-correct");
		 $("#conf-password-req").show();
		 $("#conf-password-req").text(pwdMsg);
		 $("#conf-password").focus();
	 } 
});	


$("#captchaImgText").on("keyup input",function(){
	 if ($("#captchaImgText").val().length < 1 ){
		 $("#captchaImgText").addClass("input-error").removeClass("input-correct");
		 $("#captcha-req").show();
		 $("#captchaImgText").focus();
	 }
	 if ($("#captchaImgText").val().length > 1){
		 $("#captchaImgText").addClass("input-correct").removeClass("input-error");
		 $("#captcha-req").hide();		 
	 }
	
});	

$("#otp").on("keyup input",function(){
	this.value = this.value.replace(/[^0-9]+/, '');
	
});

$(".loader").fadeOut("slow");

});


function verifyUserIdAndSendOtp(){
	$("#otp").val("");
	  var userId = $("#userId").val();
	  
	  $.ajax({
			url : "isRegisteredUser",
			type : "POST",
			dataType : "json",
			data:{"userId":userId,_csrf_token : $("#_csrf_token").val()},
			async : false,
			beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },

			success : function(jsonVal,fTextStatus,fRequest) {
				
				if (isValidSession(jsonVal) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(jsonVal) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				if(jsonVal == true){
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));						
					sendotpToRegUser();
					
				}else{
					$(".loader").fadeOut("slow");
					bootbox.confirm("User is not registered.Do you want to register ?", function(result){
					 if(result){
						 window.location.href='register';
						 return ;
						 }
					});	
				}
				
			},
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
			
  });
	 	   

}

function sendotpToRegUser(){
	$("#otp").val("");
	  var userId = $("#userId").val();
	
	$.ajax({
		url : "sendOtpToRegisteredUser",
		type : "POST",
		dataType : "json",
		data:{"userId":userId,_csrf_token : $("#_csrf_token").val()},
		async : false,
		beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success : function(jsonVal,fTextStatus,fRequest) {
			
			if (isValidSession(jsonVal) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(jsonVal) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			$.each(jsonVal, function(key, val) {
				if(key == 'true'){
				//	bootbox.alert("OTP has been sent to Registered Mobile No.Kindly Verify to change MPIN", function() {
					bootbox.alert(val, function() {
						 $("#userId").attr("disabled", "disabled"); 
						 $("#divOtp").show();
						 $("#verifyUserBtnMobile").hide();
					});
					
				}else{
					bootbox.alert(val, function() {
				//	bootbox.alert("you will not receive any OTP. Please try after 30 minutes");	
					$("#divOtp").hide();
					});
				}
				
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
});
			
}

function verifyOtp(){
   	
 	  var otp = $("#otp").val();
 	  var userMobileNo = $("#userId").val();
 	    $.ajax({
 			url : "verifyPwdRecoveryOtp",
 			headers: {
 				_csrf_token : $("#_csrf_token").val()
 			},
 			type : "POST",
 			dataType : "json",
 			data:{"otp":otp, "userMobileNo":userMobileNo,_csrf_token : $("#_csrf_token").val()},
 			async : false,
 			beforeSend: function(){
 		         $('.loader').show();
 		     },
 		     complete: function(){
 		         $('.loader').hide();
 		     },

 			success : function(jsonVal,fTextStatus,fRequest) {
 				if (isValidSession(jsonVal) == 'No') {
 					window.location.href = getDefaultSessionExpirePage();
 					return;
 				}

 				if(isValidToken(jsonVal) == 'No'){
 					window.location.href = getCsrfErrorPage();
 					return;
 				}
 				
 				if(jsonVal == true){
 					$(".loader").fadeOut("slow");
 					$("#otpHidden").val(otp);
 					 bootbox.alert("OTP Verified sucessfully ", function() {
 						 $("#divOtp").hide();
 	 					 $("#pwdChange").show();
 	 					$("#pwdChangebutton").show();
 	 					 $("#saveBtn").show();
 					 });
 					
 				}else{
 					$(".loader").fadeOut("slow");
 					$("#otp").val("");
 					 bootbox.alert("OTP Verification Failed. Try again ");
 				}
 				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
 			},
 			error: function (data,status,er) {
 				 
 				 getInternalServerErrorPage();   
 			}
     });
 	
 }

