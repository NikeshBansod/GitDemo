$(document).ready(function(){
	var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/
	var userMobileNoExist = false;
	var panRegistered = false;
	var blankMsg="This field is required.";
	var lengthMsg = "Minimum length should be ";
	var blankPinMsg = "Please select pin code from autopopulated list";
	var contactNumRegex = /[0-9]{2}\d{8}/;
	var contactLength = 10;
	var regMsg = "Data should be in proper format.";
	var pwdMsg = "Confirm MPIN should match with MPIN.";
	var PanRegex = /[a-zA-Z]{5}\d{4}[a-zA-Z]{1}/;
	$("#otp-req").hide();
	//$("#RegisterBtn").attr("disabled", "disabled");
	
    $("#panNumber").on("keyup input",function(e){		
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		 this.value = this.value.toUpperCase();
		 this.value = this.value.replace(/[\\[]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
		var PanRegex = /[a-zA-Z]{5}\d{4}[a-zA-Z]{1}/;
		if($("#panNumber").val().length<10){
			$("#panNumber").focus();
			$("#pan-number-req").show();
		}
		if ( (PanRegex.test(this.value) === true) ){
			 $("#panNumber").addClass("input-correct").removeClass("input-error");
			 $("#pan-number-req").hide();			
			 $("#pan-number-back-req").hide();
		}
		if ( !(PanRegex.test(this.value) === true) ){
			 $("#panNumber").addClass("input-error").removeClass("input-correct");
			 $("#pan-number-req").show();
			 $("#panNumber").focus();
			 $("#pan-number-back-req").show();
		}
		if ($("#panNumber").val().length !== 10){
			 $("#panNumber").addClass("input-error").removeClass("input-correct");
			 $("#pan-number-req").show();
			 $("#panNumber").focus();
			 $("#pan-number-back-req").show();
		}
	});
    
   
	$("#contactNo").on("keyup input",function(){		
		 this.value = this.value.replace(/^0/, '');
			var contactNumRegex = /[0-9]{2}\d{8}/;
			if(contactNumRegex.test(this.value) !== true){
				this.value = this.value.replace(/[^0-9]+/, '');
				 $("#contactNo").addClass("input-error").removeClass("input-correct");
				 var msg= 'This field is required and should be 10 digits.';
					 
				 if(  $("#contactNo").val().length >0 && $("#contactNo").val().length <10){
					 $("#contactNo-req").text(msg); 
				 }
				 $("#contactNo-req").show();
				 $("#contactNo").focus();
			}
			if (contactNumRegex.test(this.value) === true){
				 //$("#contactNo").addClass("input-correct").removeClass("input-error");
				 $("#contactNo-req").hide();
				 $("#contactNo-exist").hide();
				 $("#contactNo").addClass("input-correct").removeClass("input-error");
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
	 
	 /* $("#RegisterBtn").attr("disabled", "disabled"); */
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
		   			e.preventDefault();
		   		 }else{
		   			$("#contactNo").val("");   			
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
    
	$("#password").on("keyup input",function(){
		 if ($("#password").val() !== "" && $("#password").val().length >=8){
			 $("#password").addClass("input-correct").removeClass("input-error");
			 $("#password-req").hide();	
			 $("#password-format").hide();	 
		 }
		 if ($("#password").val().length < 8){
			 $("#password").addClass("input-error").removeClass("input-correct");
			 $("#password-req").show();		 
			 $("#password-format").show();
			 $("#password-req").text('This field is required & should be minimum 8 & maximum 25 characters.');
		 }
	});

	
	$("#conf-password").on("keyup input",function(){
		 if ($("#conf-password").val() !== "" ){
			 $("#conf-password").addClass("input-correct").removeClass("input-error");
			 if ($("#password").val() != $("#conf-password").val()) {
				 $("#conf-password").addClass("input-error").removeClass("input-correct");
				 $("#conf-password-req").text('MPIN & confirm MPIN must be same.'); 
				 $("#conf-password-req").show(); 
			 } else {
				 $("#conf-password-req").hide();
				 $("#conf-password-req").hide();
			 }			 		 
		 }

		 if ($("#conf-password").val() === ""){
			 $("#conf-password").addClass("input-error").removeClass("input-correct");
			 $("#conf-password-req").text('This field is required.'); 
			 $("#conf-password-req").text('MPIN & confirm MPIN must be same.'); 		 
		 }
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
			 $("#panNumber").addClass("input-correct").removeClass("input-error");
			 $("#pan-number-req").hide();
			 $("#pan-number-back-req").hide();
			 return true;
		}
		if ( !(PanRegex.test(panNo) === true) ){
			 $("#panNumber").addClass("input-error").removeClass("input-correct");
			 $("#pan-number-req").show();
		//	 $("#pan-number").focus();
			 $("#pan-number-back-req").hide();
			 return false;
		}
		if ($("#panNumber").val().length !== 10){
			 $("#panNumber").addClass("input-error").removeClass("input-correct");
			 $("#pan-number-req").show();
		//	 $("#pan-number").focus();
			 $("#pan-number-back-req").hide();
			 return false;
		}
	}
	
    $("#RegisterBtn").click(function(e){		 
         	 var errFlag1  = validatePanNo();
         	 var errFlag8  = validateContactNo();
			 var errFlag10 = validatePassword();
			 var errFlag11 = validateRegConfirmPassword();
			 var userIdhidden=$("#contactNo").val();
			 $("#userId").val(userIdhidden); 
			 
			 var otpValidated = false;
			 if($("#otpVerified").val() == 'false'){
				 otpValidated = true;
			 }
			
			 if ( (errFlag1) || (errFlag8) ||  (errFlag10)  || (errFlag11) || (otpValidated)){
				e.preventDefault();		
				if(otpValidated){
					$("#otp").addClass("input-error").removeClass("input-correct");
					$("#otp").focus();
					if($('#divOtp').is(':visible')){
						$("#otp-req").text("Please verify OTP");
				   		$("#otp-req").show();	
					}else{
						$("#contactNo-exist").text("\nPlease verify Mobile No");
		  				 $("#contactNo-exist").show();
					}			   						
				}
			 }
			 
			 if((errFlag1) ){
				 focusTextBox("panNumber");
			 } else if((errFlag8)){
				 focusTextBox("mobileNo");
			 } else if(errFlag10){
				 focusTextBox("password"); 
			 }	 else if(errFlag11){
				 focusTextBox("conf-password");
			 }				 			 
		 });
	
    function validatePanNo(){
	 	errFlag1 = validateTextField("panNumber","pan-number-req",blankMsg);
	 	 if(!errFlag1){
	 		 errFlag1=validateFieldLength("panNumber","pan-number-req",lengthMsg,10);
	 		if(!errFlag1){
				 errFlag1=validateRegexpressions("panNumber","pan-number-req",regMsg,PanRegex);
			 }
	 	 }
	 	 return errFlag1;
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
    
	
    $("#panNumber").blur(function(){
    	
        var panNo = $("#panNumber").val();
        if(panNo != ''){
        if(validatePanWithRegex(panNo)){
        
    		    $.ajax({
    				url : "checkIfpanIsRegistered",
    				type : "POST",
    				dataType : "json",
    				headers: {_csrf_token : $("#_csrf_token").val()},
    				data : {"panNo" : panNo},
    				async : false,
    				success : function(jsonVal,fTextStatus,fRequest) {
    					
    					if (isValidSession(jsonVal) == 'No') {
    						window.location.href = getDefaultWizardSessionExpirePage();
    						return;
    					}

    					if(isValidToken(jsonVal) == 'No'){
    						window.location.href = getWizardCsrfErrorPage();
    						return;
    					}

    					if(jsonVal == true){
    						 $("#panNumber").addClass("input-error").removeClass("input-correct");
    						 $("#pan-number-back-req").text('PAN  - '+panNo+' already Registered. Try some other PAN.');
    						 $("#pan-number-back-req").show();
    						 panRegistered = true;
    					}else{
    						 $("#panNumber").addClass("input-correct").removeClass("input-error");
    						 $("#pan-number-back-req").hide();
    						 panRegistered = false;
    						
    					}
    					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
    				},
    				error: function (data,status,er) {
    					 
    					 getWizardInternalServerErrorPage();   
    				}
    		    });
    		    }
    		    }//IF
    		});
      
});



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
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}

				if(isValidToken(jsonVal) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
				$.each(jsonVal, function(key, val) {
				if(key == 'true'){
					//bootbox.alert("OTP has been sent to Mobile No : "+userMobileNo+". Kindly Verify to complete Registration", function() {
					bootbox.alert( val, function(){
						$("#divOtp").show();
						 //$("#verifyUserBtn").hide();
					});
					 
				} else {
					bootbox.alert(val);
				}
				});
				
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {
				 
				 getWizardInternalServerErrorPage();   
			}

    });

}


function verifyOtp(){
  var otp = $("#otp").val();
  var userMobileNo = $("#contactNo").val();
  var flag = false;
  $.ajax({
     url : "verifyRegistrationOtp",
     type : "POST",
     dataType : "json",
     headers: {_csrf_token : $("#_csrf_token").val()},
     data:{"otp":otp, "userMobileNo":userMobileNo},
     async : false,
     success : function(jsonVal,fTextStatus,fRequest) {
    	 if (isValidSession(jsonVal) == 'No') {
    			window.location.href = getDefaultWizardSessionExpirePage();
    			return;
    		}

    		if(isValidToken(jsonVal) == 'No'){
    			window.location.href = getWizardCsrfErrorPage();
    			return;
    		}
        if(jsonVal == true){
        	bootbox.alert("OTP Verified sucessfully ", function() {
        		$("#divOtp").hide();
        		flag = true;
        		$("#otpVerified").val(flag);
        		$("#contactNo").prop("readonly", true);
        	//	$("#contactNo").attr("disabled", "disabled");
        		$("#hiddenContactNo").val(userMobileNo);
        		//$("#RegisterBtn").removeAttr("disabled");
        		$("#otp").addClass("input-correct").removeClass("input-error");
			});
        }else{
        	$("#otpVerified").val(flag);
        	$("#otp").addClass("input-error").removeClass("input-correct");
        	bootbox.alert("OTP Verification Failed. Try again", function() {
        		$("#otp").val("");
			});
        }
        setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
     },
     error: function (data,status,er) {
    	 
    	 getWizardInternalServerErrorPage();   
    }
     
  });
}

		$("#otp").val("");
		  var userId = $("#userId").val();
		 
		  /* $.ajax({
		              url : "isRegisteredUser",
		              type : "POST",
		              dataType : "json",
		              data:{"userId":userId},
		              async : false,
		              success : function(jsonVal) {
		                    if(jsonVal == true){  
		                           sendotpToRegUser();
		                    }else{
		                         bootbox.confirm("User is not registered.Do you want to register ?", function(result){
		                            if(result){
		                                  window.location.href='register';
		                                  return ;
		                            }
		                         });  
		                    }
		              }
		  });
		  */

		  function checkUserMobileExistence(){
		  	var mobileNo = $("#contactNo").val();
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
		  				window.location.href = getDefaultWizardSessionExpirePage();
		  				return;
		  			}

		  			if(isValidToken(jsonVal) == 'No'){
		  				window.location.href = getWizardCsrfErrorPage();
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
		  			 
		  			 getWizardInternalServerErrorPage();   
		  		}
		  		
		      });
		      }
		      return userMobileNoExist;
		  }

