
$(document).ready(function(){
	var deviceResponse = detectDevice();
	$('#loggedInThrough').val(deviceResponse);
	$('.loader').fadeOut("slow");	
	
/*	$("#userId").on("keyup input",function(){		
		this.value = this.value.replace(/^0/, '');
		//this.value = this.value.replace(/[^0-9]+/, '');			
	});	*/
		
});

function detectDevice(){
	var device='';
	if ( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ){
	    device = 'MOBILE';
	}else{
		device = 'WIZARD';
	}
	
	return device;	
}

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

/*$('#loginToGSTBillLite').on('click',function(e){
	  var password = $("#password").val();
	  var username = $("#userId").val();
	    $.ajax({
			url : "loginunifiP",
			type : "POST",
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
			data:{"username":username,"password":password},
			async : false,
			success : function(jsonVal) {
				
				if(jsonVal.msg){
					if(jsonVal.msg.status_cd){
						if(jsonVal.msg.status_cd == "1"){
							bootbox.alert("loged in successfully");
						}
						else if(jsonVal.msg.status_cd == "0"){
							if(jsonVal.msg.status_msg){
								bootbox.alert(jsonVal.msg.dev_msg);
							}
							
						}
					}
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			},
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
   });
	
});*/

//$('#loginToGSTBillLite').on('click',function(e){
//	
//	//var URL="fetchprofiledetails";
//	//var contactNo=$("#userId").val();
//	
//	fetchprofiledetails()
//	
//});
//function fetchprofiledetails(){
//	var URL="fetchprofiledetails";
//	var imeiNo=$("#IMEINo").val();
//	$.ajax({        
//	    url: URL,
//	    headers: {
//			_csrf_token : $("#_csrf_token").val()
//	    },
//	    data: {imeiNo:imeiNo},
//	    type: "POST",
//	    dataType : "json",
//	    contentType : "application/json",
//	    async : false,
//	    success:function(json){	
//		if (isValidSession(json) == 'No') {
//			alert("failed");
//		}
//    	alert(json);
//	    	//android.fetchUserProfile(json);
//		}
//		
//	});
//	
//	
//}
