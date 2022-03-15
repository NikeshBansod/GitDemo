
$(document).ready(function(){
	
 $(".user-drop").hide();
 $("#pwdChange").removeClass("ruGST");
 
var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/ 
var blankMsg="This field is required";
var regMsg = "MPIN should be in proper format Min 1 digit, 1 Uppercase letter, 1 lowercase letter, 1 special character";
var pwdMsg = "confirm MPIN should match with MPIN";
var samePswd = "New MPIN should not be same as Old MPIN";
var Webservicestatus="";
var cacheKey;
var status_msg="";
var password="";
var otp;

 $("#saveBtn").click(function(e){
	 $('.loader').show();
	 var errFlag1 = validatePassword(); 
	 var errFlag2 = validatePwdWithConfPwd();
	 
	 if ( (errFlag1)  || (errFlag2)){
		 $(".loader").fadeOut("slow");
		 e.preventDefault();	 
	 }
	 
	   if((errFlag1) ){
		 focusTextBox("password");
	 } else if((errFlag2)){
		 focusTextBox("conf-password");
	 } 
	   var password= $("#password").val();
	   var otp=$("#otp").val();
	   cacheKey=$("#cachekey").val();
	   verifyOtpApi(cacheKey,password,otp);
	   
	   
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
			url : "loginUnifiedForgotPassword",
			type : "POST",
			data:{"userName":userId},
			async: false,
			
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
				parsedData=JSON.parse(jsonVal);
				Webservicestatus=parsedData.status;
				
				if(Webservicestatus == true){
					cacheKey=parsedData.cachekey;
					document.getElementById("cachekey").value=cacheKey;
					bootbox.alert("OTP has been sent to Registered Mobile No.Kindly Verify to change MPIN", function() {
						
							 $("#userId").attr("disabled", "disabled"); 
							 $("#divOtp").show();
							 $("#pwdChange").show();
							 $("#pwdChangebutton").show();
							 $("#saveBtn").show();
							 $("#verifyUserBtnMobile").hide();
							 
							
							 
							 
						});
					
				}else{
					status_msg=parsedData.status_msg;
					$(".loader").fadeOut("slow");
					bootbox.confirm(status_msg+"&nbsp", function(result){
					 if(result){
						 window.location.href='login';
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



function verifyOtpApi(cacheKey,password,otp){
   	
 	  
 	  
 	    $.ajax({
 			url : "loginUnifiedVerifySavePassword",
 			type : "POST",
			data:{"cacheKey":cacheKey,"otp":otp,"password":password,},
			async: false,
 			beforeSend: function(){
 		         $('.loader').show();
 		     },
 		     complete: function(){
 		         $('.loader').hide();
 		     },

 			success : function(jsonVal,fTextStatus,fRequest) {
 				
 				parsedData=JSON.parse(jsonVal);
				Webservicestatus=parsedData.status;
				status_msg=parsedData.status_msg;
				if(Webservicestatus == true){
 					$(".loader").fadeOut("slow");
 					 bootbox.alert(status_msg, function() {
 						
 						 
 					 });
 					
 				}else{
 					$(".loader").fadeOut("slow");
 					 bootbox.alert(status_msg,function(result){
 						 if(result){	
 						 window.location.href='confirmPasswordLoginUnified';
 						 return;
 					 	}
 					 });
 				}
 				
 			},
 			error: function (data,status,er) {
 				 
 				 getInternalServerErrorPage();   
 			}
     });
 	
 }

