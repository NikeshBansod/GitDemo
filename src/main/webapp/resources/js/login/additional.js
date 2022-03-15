
$(document).ready(function(){
	 $("#userId").on("keyup input",function(){
			
		 this.value = this.value.replace(/^0/, '');
		 this.value = this.value.replace(/[^0-9]+/, '');
			
		});


	
});


function verifyUserIdAndSendOtp(){
	$("#otp").val("");
	  var userId = $("#userId").val();
	    $.ajax({
			url : "sendOtpToRegisteredUser",
			type : "POST",
			dataType : "json",
			data:{"userId":userId},
			async : false,
			success : function(jsonVal) {
				if(jsonVal == true){
					bootbox.alert("OTP has been sent to Mobile No - "+userId+". Kindly Verify to Sign In", function() {
						$("#divOtp").show();
						 $("#verifyUserBtn").hide();
					});
					 
				}else{
					 var response = confirm("User is not registered.Do you want to register ?");
					 if(response){
						 window.location.href='register';
						 return ;
						 }
				}
			}
    });

}


function verifyOtp(){
   	
 	  var password = $("#password").val();
 	    $.ajax({
 			url : "verifyRegistrationOtp",
 			type : "POST",
 			dataType : "json",
 			data:{"otp":password},
 			async : false,
 			success : function(jsonVal) {
 				if(jsonVal == true){
 					bootbox.alert("OTP Verified sucessfully ", function() {
 						$("#divOtp").hide();
 	 					 $("#loginForm").submit();
 					});
 					 
 				}else{
 					$("#password").val("");
 					bootbox.alert("OTP Verification Failed. Try again ");
 				}
 			}
     });
 	
 }