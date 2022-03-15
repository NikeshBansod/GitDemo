
jQuery(document).ready(function($) {
	$(".accordion_example2").smk_Accordion({
		closeAble : false, // boolean
	});
	
	$("form").attr('autocomplete', 'off');

	$("#stdCode").val("0"+$("#stdCode").val()); //Appending Zero as being a Int type ModelAttribute Removes leading zeroes
	$("#divOtherOrgType").hide();
	$("#divOtherNatureOfBusiness").hide();
	initUI();
});

function initUI(){
	$("#zip").val($("#zipCode").val());
}

function is_int(value) {
	if ((parseFloat(value) == parseInt(value)) && !isNaN(value)) {
		return true;
	} else {
		return false;
	}
}

function verifyGstin() {
	var location=$("#city").val();
	var stateCode=$("#state").val();
	var deviceOS = getDeviceOs();
	var deviceType = getClientDeviceType();
	
		$.ajax({
			url : "verifyGstin",
			type : "GET",
		//	contentType : "application/json",
			//data :JSON.stringify(datad),
			data :{deviceOS : deviceOS,
				deviceType : deviceType,
				location : location,
				stateCode : stateCode
			},
			dataType: "json",
			success : function(response,fTextStatus,fRequest) {
				if(response.status_cd=="1"){
					bootbox.alert("OTP SENT", function() {
						$("#div-otp").show();
					});
					
				}else{
					bootbox.alert("Invalid GSTIN");
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			}
		});
	

}

function getDeviceOs(){
	var deviceOS;
	var client = new ClientJS(); 
	// Create A New Client Object 
	var browser = client.getBrowser();
	var isMobileAndroid = client.isMobileAndroid();
	//alert("browser :"+browser);
	//alert("isMobileAndroid :"+isMobileAndroid);
	var isMobileWindows = client.isMobileWindows();
	//alert("isMobileWindows :"+isMobileWindows);
	var isMobileIOS = client.isMobileIOS();
	//alert("isMobileIOS :"+isMobileIOS);
	var isIpad = client.isIpad();
	var isIphone = client.isIphone();
	
	if(isMobileAndroid){
		deviceOS="Android"
	}
	else if(isMobileWindows){
		deviceOS="Windows"
	}
	else if(isMobileIOS){
		deviceType="IOS"
	}
	else if(isIpad){
		deviceOS="IPAD"
	}
	else if(isIphone){
		deviceOS="isIphone"
	}
	return deviceOS;
}

function getClientDeviceType(){
	var client = new ClientJS(); 
	var deviceType = client.getDeviceType(); 
	if(deviceType==null){
		deviceType="Desktop";
	}
	return deviceType;
}

$(document).ready(function(){
	
	var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/
		var userMobileNoExist = false;
		var panRegistered = false;
		var blankMsg="This field is required";
		var lengthMsg = "Minimum length should be ";
		var blankPinMsg = "Please select pin code from autopopulated list";
		var contactNumRegex = /[0-9]{2}\d{8}/;
		var contactLength = 10;
		var regMsg = "data should be in proper format";
		var pwdMsg = "confirm MPIN should match with MPIN";
		var PanRegex = /[a-zA-Z]{5}\d{4}[a-zA-Z]{1}/;
		

		 $("#RegisterBtn").click(function(e){
			 
		//	 var errFlag   = validateOrgName(); 
			 var errFlag1  = validatePanNo();
			// var errFlag3  = validateOrgType();
			 var errFlag8  = validateContactNo();
			 var errFlag10 = validatePassword();
			 var errFlag11 = validateRegConfirmPassword();
			 var errFlag20 = false;
			 var userIdhidden=$("#contactNo").val();
			 $("#userId").val(userIdhidden);
			/* 
			 if ($("#org-type option:selected").text() === "Others"){
			 errFlag20 = validateOtherOrg();
				
			 }*/
			 
			
			 var otpValidated = false;
			
			 if (  (errFlag1) || (errFlag8)  || (userMobileNoExist) || (panRegistered) || (errFlag20) || (errFlag10) || (errFlag11)){
				// (errFlag) ||  (errFlag3) || 
				 e.preventDefault();
				
			 }
			 
			 /*if((errFlag)){
				 focusTextBox("org-name");
			 } else*/ if((errFlag1) ){
				 focusTextBox("pan-number");
			 } /*else if((errFlag3)){
				 focusTextBox("org-type");
			 } else if(errFlag20){
				 focusTextBox("otherOrgType");
			 } */else if((errFlag8)){
				 focusTextBox("contactNo");
			 } else if(errFlag10){
				 focusTextBox("password"); 
			 }	 else if(errFlag11){
				 focusTextBox("conf-password");
			 } 
			 
			 
		 });
		 

			 function validateOrgName(){
			 	errFlag = validateTextField("org-name","org-name-req",blankMsg);
			 	 if(!errFlag){
			 		 errFlag=validateFieldLength("org-name","org-name-req",lengthMsg,1);
			 	 }
			 	 return errFlag;
			 }
			 
			 function validatePanNo(){
			 	errFlag1 = validateTextField("pan-number","pan-number-req",blankMsg);
			 	 if(!errFlag1){
			 		 errFlag1=validateFieldLength("pan-number","pan-number-req",lengthMsg,10);
			 		if(!errFlag1){
						 errFlag1=validateRegexpressions("pan-number","pan-number-req",regMsg,PanRegex);
					 }
			 	 }
			 	 return errFlag1;
			 }
			
			 function validateOrgType(){
			 	errFlag3 = validateTextField("org-type","org-type-id",blankMsg);
			 	 if(!errFlag3){
			 		 errFlag3=validateSelectField("org-type","org-type-id");
			 	 }
			
			 	 return errFlag3;
			 }


		 	function validateOtherOrg(){
				errFlag20 = validateTextField("otherOrgType","otherOrgType-req",blankMsg);
				 
				 
				 return errFlag20;
			}
		 	
		 	
			function validateContactNo(){
				errFlag8 = validateTextField("contactNo","contactNo-req",blankMsg);
				 if(!errFlag8){
					 errFlag8=validateRegexpressions("contactNo","contactNo-req",regMsg,contactNumRegex);
				 }
				 return errFlag8;
			}		
			
			
			function validatePassword(){
				errFlag10 = validateTextField("password","password-req",blankMsg);
				 if(!errFlag10){
					 errFlag10=validateRegexpressions("password","password-req",regMsg,pwdRegex);
				 }
				 return errFlag10;
			}

			function validateRegConfirmPassword(){
				errFlag11 = validateTextField("conf-password","conf-password-req",blankMsg);
				 if(!errFlag11){
					 errFlag11 = validatePasswordWithConfPwd("password","conf-password","conf-password-req",pwdMsg);
				 }
				 return errFlag11
			}
			
			/*
			
		$("#org-type").on("keyup click", function(){
			if ($("#org-type").val() === "" || $("#org-type").val() == "0" ){
				$("#org-type").addClass("input-error").removeClass("input-correct");
				$("#org-type-id").show();
			} else if ($("#org-type").val() !== ""){
				$("#org-type").addClass("input-correct").removeClass("input-error");
				$("#org-type-id").hide();
			}
		});

		$("#org-type").change(function(){
			if ($("#org-type option:selected").text() === "Others"){
				$("#divOtherOrgType").show();
			}else{
				$("#divOtherOrgType").hide();
				$("#otherOrgType").val("");
			}
			
			if ($("#org-type").val() === "" || $("#org-type").val() == "0" ){
				$("#org-type").addClass("input-error").removeClass("input-correct");
				$("#org-type-id").show();
			}else if ($("#org-type").val() !== "" ){
				$("#org-type").addClass("input-correct").removeClass("input-error");
				$("#org-type-id").hide();
			}
			
		});
		
		
		
		$("#otherOrgType").on("keyup input", function(){
			 this.value = this.value.replace(/[\\[]*$/, '');
			this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
			if ($("#otherOrgType").val() !== ""){
				$("#otherOrgType").addClass("input-correct").removeClass("input-error");
				$("#otherOrgType-req").hide();
			}
			if ($("#otherOrgType").val() === ""){
				$("#otherOrgType").addClass("input-error").removeClass("input-correct");
				$("#otherOrgType-req").show();
			}
		});
		
		
		$("#org-name").on("keyup input",function(e){
			   if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			   this.value = this.value.replace(/[[]*$/, '');
			   this.value = this.value.replace(/[^[\\a-zA-Z0-9\s- &/.@'()!]*$/, '');
			 if ($("#org-name").val().length > 1){
				 $("#org-name").addClass("input-correct").removeClass("input-error");
				 $("#org-name-req").hide();		 
			 }
			 if ($("#org-name").val().length == 1 || $("#org-name").val().length >100){
				 $("#org-name").addClass("input-error").removeClass("input-correct");
				 $("#org-name-req").text('Firm Name length should minimum 2 and maximum 100 characters');	
				 $("#org-name-req").show();	
				 $("#org-name").focus();
			 }
			 
			 if ($("#org-name").val().length < 1 ){
				 $("#org-name").addClass("input-error").removeClass("input-correct");
				 $("#org-name-req").text('This field is required');	
				 $("#org-name-req").show();	
				 $("#org-name").focus();
			 }
		});*/

		
				
		$("#password").on("keyup input",function(){
			 if ($("#password").val() !== "" && $("#password").val().length ==8){
				 $("#password").addClass("input-correct").removeClass("input-error");
				 $("#password-format").hide();
				 $("#password-req").hide();		 
			 }
			  if ($("#password").val().length < 8){
				 $("#password").addClass("input-error").removeClass("input-correct");
				 $("#password-format").show();
				 $("#password-req").text('This field is required & should be minimum 8 & maximum 25 characters');
				 $("#password-req").show();		 
			 }
			  if(validateRegConfirmPassword()){
				  $("#conf-password").addClass("input-error").removeClass("input-correct");
					 $("#conf-password-req").text('MPIN & confirm MPIN must be same'); 
					 $("#conf-password-req").show(); 
			  }
		});

		
		$("#conf-password").on("keyup input",function(){
			 if ($("#conf-password").val() !== "" ){
				 $("#conf-password").addClass("input-correct").removeClass("input-error");
				 if ($("#password").val() != $("#conf-password").val()) {
					 $("#conf-password").addClass("input-error").removeClass("input-correct");
					 $("#conf-password-req").text('MPIN & confirm MPIN must be same'); 
					 $("#conf-password-req").show(); 
				 } else {
					 $("#conf-password-req").hide();
				 }
				 		 
			 }

			 if ($("#conf-password").val() === ""){
				 $("#conf-password").addClass("input-error").removeClass("input-correct");
				 $("#conf-password-req").text('This field is required'); 
				 $("#conf-password-req").show();		 
			 }
		});

		$("#pan-number").on("keyup input",function(e){
			
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			 this.value = this.value.toUpperCase();
			 this.value = this.value.replace(/[\\[]*$/, '');
			 this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
			var PanRegex = /[a-zA-Z]{5}\d{4}[a-zA-Z]{1}/;
			if ( (PanRegex.test(this.value) === true) ){
				 $("#pan-number").addClass("input-correct").removeClass("input-error");
				 $("#pan-number-req").hide();
				 $("#pan-number-back-req").hide();
			}
			if ( !(PanRegex.test(this.value) === true) ){
				 $("#pan-number").addClass("input-error").removeClass("input-correct");
				 $("#pan-number-req").show();
				 $("#pan-number").focus();
				 $("#pan-number-back-req").hide();
			}
			if ($("#pan-number").val().length !== 10){
				 $("#pan-number").addClass("input-error").removeClass("input-correct");
				 $("#pan-number-req").show();
				 $("#pan-number").focus();
				 $("#pan-number-back-req").hide();
			}
		});

		
		 $("#contactNo").on("keyup input",function(){
			
			 this.value = this.value.replace(/^0/, '');
				var contactNumRegex = /[0-9]{2}\d{8}/;
				if(contactNumRegex.test(this.value) !== true){
					this.value = this.value.replace(/[^0-9]+/, '');
					 $("#contactNo").addClass("input-error").removeClass("input-correct");
					 var msg= 'This field is required and should be 10 digits';
						 
					 if(  $("#contactNo").val().length >0 && $("#contactNo").val().length <10){
						 $("#contactNo-req").text(msg); 
					 }
					 $("#contactNo-req").show();
					 $("#contactNo").focus();
				}
				if (contactNumRegex.test(this.value) === true){
					 $("#contactNo").addClass("input-correct").removeClass("input-error");
					 $("#contactNo-req").hide();
				}
				
				if ($("#contactNo").val().length == 10){
					checkUserMobileExistence();
				}
			});

		 $("#otp").on("keyup input",function(){
				
				var contactNumRegex = /^[0-9]{6}$/;
				if(contactNumRegex.test(this.value) !== true){
					 
					this.value = this.value.replace(/[^0-9]+/, '');
					 $("#otp").addClass("input-error").removeClass("input-correct");
					 
					 $("#otp-req").show();
					 $("#otp").focus();
				}
				if (contactNumRegex.test(this.value) === true){
					 $("#otp").addClass("input-correct").removeClass("input-error");
					 $("#otp-req").hide();
				}
			});
		 

		$(document).ready(function(){
		/*	
			$("#contactNo").blur(function(){
				checkUserMobileExistence();
			   
			});
			*/
			
			
			$("#pan-number").blur(function(){
				
			    var panNo = $("#pan-number").val();
			    if(panNo != ''){
			    if(validatePanWithRegex(panNo)){
			    
			    $.ajax({
					url : "checkIfpanIsRegistered",
					type : "POST",
					headers: {_csrf_token : $("#_csrf_token").val()},
					dataType : "json",
					async : false,					
					data : {"panNo" : panNo},
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
							 $("#pan-number").addClass("input-error").removeClass("input-correct");
							 $("#pan-number-back-req").text('PAN  - '+panNo+' already Registered. Try some other PAN.');
							 $("#pan-number-back-req").show();
							 panRegistered = true;
						}else{
							 $("#pan-number").addClass("input-correct").removeClass("input-error");
							 $("#pan-number-back-req").hide();
							 panRegistered = false;
							
						}
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
					},
					error: function (data,status,er) {
						 
						 getInternalServerErrorPage();   
					}
		        });
			    }
			    }//IF
			});
			
			$("#password").blur(function(){
			    var password = $("#password").val();
			    if(pwdRegex.test(password) == false){
					$("#password-format").show();			
					$("#password").addClass("input-error").removeClass("input-correct");	
				} 
			    
			    if(pwdRegex.test(password)){
					$("#password-format").hide();
					$("#password").addClass("input-correct").removeClass("input-error");
				}
			});
			
			function validatePanWithRegex(panNo){
				
				var PanRegex = /[a-zA-z]{5}\d{4}[a-zA-Z]{1}/;
				if ( (PanRegex.test(panNo) === true) ){
					 $("#pan-number").addClass("input-correct").removeClass("input-error");
					 $("#pan-number-req").hide();
					 $("#pan-number-back-req").hide();
					 return true;
				}
				if ( !(PanRegex.test(panNo) === true) ){
					 $("#pan-number").addClass("input-error").removeClass("input-correct");
					 $("#pan-number-req").show();
				//	 $("#pan-number").focus();
					 $("#pan-number-back-req").hide();
					 return false;
				}
				if ($("#pan-number").val().length !== 10){
					 $("#pan-number").addClass("input-error").removeClass("input-correct");
					 $("#pan-number-req").show();
				//	 $("#pan-number").focus();
					 $("#pan-number-back-req").hide();
					 return false;
				}
			}
			
			
		});


		$("#userName").on("keyup", function(){
			 restrictNumberTyping(this);
		});
		     
});


$(document).ready(function(){
   

 //   $("#RegisterBtn").attr("disabled", "disabled"); 
    $("#verifyUserMobileNoBtn").click(function(e){
   	 
   	 if($("#contactNo").val()==""){
   		 $("#contactNo").addClass("input-error").removeClass("input-correct");
   		 $("#contactNo-req").show();
   	 }else if($("#contactNo").val()!=""){
   		 $("#contactNo").addClass("input-correct").removeClass("input-error");
   		 $("#contactNo-req").hide();
   		 var result = checkUserMobileExistence();
   		 if(!result){
   			 verifyUserIdAndSendOtp();
   		 }else{
   			 bootbox.alert("Mobile No : "+$("#contactNo").val()+" already registered. Try Another Mobile No.", function() {
   				$("#contactNo").val("");
   			 });
   			
   		 }
   	 }
   	 
   	e.preventDefault();
   	 
    });
    
    $("#verifyOtp").click(function(e){
   	 if($("#otp").val()==""){
   		 $("#otp").addClass("input-error").removeClass("input-correct");
   		 $("#otp-req").show();
   	 }else if($("#contactNo").val()!=""){
   		 $("#otp").addClass("input-correct").removeClass("input-error");
   		 $("#otp-req").hide();
   		 verifyOtp();
   	 }
   	 
   	e.preventDefault();
   	 
    });
});

function preventOTPRecords(){
	var otp = $("#otp").val();
	  var userMobileNo = $("#contactNo").val();
	  var otpTime = new Date();
	  
	  
}

function verifyUserIdAndSendOtp(){
	$("#otp").val("");
	  var userMobileNo = $("#contactNo").val();
	    $.ajax({
			url : "sendOtpOnRegistration",
			type : "POST",
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
			data:{"userMobileNo":userMobileNo},
			async : false,
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
					//bootbox.alert("OTP has been sent to Mobile No : "+userMobileNo+". Kindly Verify to complete Registration", function() {
					bootbox.alert( val, function(){
						$("#divOtp").show();
					//	$("#verifyUserBtn").hide();
						$("#verifyBtnDiv").hide();
					});
					 
				} else {
					bootbox.alert(val);
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
	  var userMobileNo = $("#contactNo").val();
	    $.ajax({
			url : "verifyRegistrationOtp",
			type : "POST",
			dataType : "json",
			data:{"otp":otp, "userMobileNo":userMobileNo},
			headers: {_csrf_token : $("#_csrf_token").val()},
			async : false,
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
					bootbox.alert("OTP Verified sucessfully ", function() {
						 $("#divOtp").hide();
						 $("#verifyBtnDiv").hide();
						 $("#loginDetails").show();
						 $("#contactNo").prop("readonly", true);
 		        		 $("#hiddenContactNo").val(userMobileNo);
					//	  $("#RegisterBtn").removeAttr("disabled");
						  
					});
					
				}else{
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


function checkUserMobileExistence(){
	var mobileNo = $("#contactNo").val();
	var userMobileNoExist = false;
	
    if(mobileNo != ''){
    $.ajax({
		url : "checkIfUserMobileNoExists",
		//method : "GET",
		//contentType : "application/json",
		type : "POST",
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		data : {"mobileNo":mobileNo},
		async : false,
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
				 $("#contactNo").addClass("input-error").removeClass("input-correct");
				 $("#contactNo-exist").text('Mobile No - '+mobileNo+' already exists. Try some other Mobile No.');
				 $("#contactNo-exist").show();
				 userMobileNoExist = true;
				// $("#userId").val("");
				// $("#userId").focus();
			}else{
				 $("#contactNo").addClass("input-correct").removeClass("input-error");
				 $("#contactNo-exist").hide();
				 userMobileNoExist = false;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}

    });
    }
    return userMobileNoExist;
}
