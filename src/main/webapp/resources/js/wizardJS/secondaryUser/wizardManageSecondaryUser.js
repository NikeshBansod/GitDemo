
jQuery(document).ready(function($){
    $('#listheader2').hide();         
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });

    //var captchaError= $("#captchaError").val();
    var captchaError= '${captchaError}';
    if(captchaError=="true"){
    	 $('#addEmployee').click();
    }
    
    if($("#actionPerformed").val()){
  		 $('#toggle').hide();
  	}
    
    $("#addEmployeeDetails").hide();   
    
    $('#addEmployee').on('click', function(e) {
    	$("#addEmployeeDetails").slideToggle();
    	$('#addEmployee').hide();
    	$('.container').hide();
     	$('#listheader1').hide();
     	$('#listheader2').show();            	
    	e.preventDefault();
    })
            
});


$(document).ready(function(){
	var captchaError= $("#captchaError").val();
	if(captchaError){
		$("#addEmployeeDetails").slideToggle();
		 $('#addEmployee').hide();
		 $('.container').hide();
	}
	var contactNumRegex = /[0-9]{2}\d{8}/;
	var contactLength = 10;
	var blankMsg="This field is required";
	var lengthMsg = "Minimum length should be ";
	var regMsg = "data should be in proper format";
	var aadharNumRegex = /[0-9]{2}\d{8}/;
	var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,25}$/;
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var userexist = false;
	var length = 2, pinCodeLength = 6, contactLength = 10, AadharLength = 12;
	var pwdMsg = "confirm MPIN should match with MPIN";
	var btnVal = document.getElementById("secSubmitBtn").value;
	var userMobileNoExist= false;
	 
	$("#secSubmitBtn").click(function(e){
		if(btnVal == "add"){
			var errCustContactNo = validateCustomerContactNo();
		    var errAadhar =  validateCustomerAadharNo();
			var errFlag = validateUserName();
			var errFlag1 = validatePwdFormat();
			var errFlag2 = validatePwdWithConfPwd();
			var errFlag3 = validateEmailId();
			var userIdhidden=$("#contactNo").val();
			 $("#userId").val(userIdhidden);			
		
			if ( (errFlag) || (errFlag1) || (errFlag2) || (errFlag3) || (userexist) || (errAadhar) || (errCustContactNo) || (userMobileNoExist)){
				 e.preventDefault();	 
			} 
			
			if((errFlag)){
				 focusTextBox("userName");
			 } else if((errFlag3) ){
				 focusTextBox("emailId");
			 } else if((errCustContactNo)){
				 focusTextBox("contactNo");
			 } else if((errAadhar)){
				 focusTextBox("secUserAadhaarNo");
			 } else if((errFlag1)){
				 focusTextBox("password");
			 } else if(errFlag2){
				 focusTextBox("confPasswordSecUser");
			 } 		
		} else {
			var errCustContactNo = validateCustomerContactNo();
		    var errAadhar =  validateCustomerAadharNo();
			var errFlag = validateUserName();
			var errFlag1 = validateEmailId();
		
			if ( (errFlag) || (errFlag1) || (errAadhar) || (errCustContactNo)){
				 e.preventDefault();	 
			} 			
			
			if((errFlag)){
				 focusTextBox("userName");
			 } else if((errFlag1) ){
				 focusTextBox("emailId");
			 } else if((errCustContactNo)){
				 focusTextBox("contactNo");
			 } else if((errAadhar)){
				 focusTextBox("secUserAadhaarNo");
			 }
		}	
	});

	function validateCustomerContactNo(){
		errCustContactNo = validateTextField("contactNo","contact-no-req",blankMsg);
		 if(!errCustContactNo){
			 errCustContactNo=validateFieldLength("contactNo","contact-no-req",lengthMsg,contactLength);
			  if(!errCustContactNo){
				 errCustContactNo=validateRegexpressions("contactNo","contact-no-req",regMsg,contactNumRegex);
			 }
		 }
		 return errCustContactNo;		
	}
	 
	 
	function validateCustomerAadharNo(){		
			 errAadhar=validateFieldLength("secUserAadhaarNo","aadharNo-req",lengthMsg,AadharLength);
			 if(!errAadhar){
			 errAadhar=validateRegexpressions("secUserAadhaarNo","aadharNo-req",regMsg,aadharNumRegex);
			 }			 
			 return errAadhar;
	}


	function validateUserName(){
		errFlag = validateTextField("userName","cust-name-req",blankMsg);
		 if(!errFlag){
			 errFlag=validateFieldLength("userName","cust-name-req",lengthMsg,length);
		 }
		 return errFlag;
	}

	function validatePwdFormat(){
		errFlag1 = validateTextField("password","password-req",blankMsg);
		 if(!errFlag1){
			 errFlag1=validateRegexpressions("password","password-req",regMsg,pwdRegex);
		 }
		 return errFlag1;
	}

	function validatePwdWithConfPwd(){
		errFlag2 = validateTextField("confPasswordSecUser","conf-password-req",blankMsg);
		 if(!errFlag2){
			errFlag2 = validatePasswordWithConfPwd("password","confPasswordSecUser","conf-password-req",pwdMsg);
		 }
		 return errFlag2
	}

	function validateEmailId(){		
			 errFlag3=validateRegexpressions("emailId","reg-email-req",regMsg,emailRegex);
		 	 return errFlag3;
	}
	 
	 $("#userName").on("keyup input",function(e){		 
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		 }
		 this.value = this.value.replace(/[\\[]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
		 
		 if ($("#userName").val().length !== 2){
			 $("#userName").addClass("input-correct").removeClass("input-error");
			 $("#cust-name-req").hide();
		 }
		 if ($("#userName").val().length < 1){
			 $("#userName").addClass("input-error").removeClass("input-correct");
			 $("#cust-name-req").show();			 
		 }
		  if($("#userName").val() ==""){
			 $("#userName").addClass("input-error").removeClass("input-correct");
			 $("#cust-name-req").show(); 
		 } 
	});
	 

	 $("#confPasswordSecUser").on("keyup input", function(){		
		 if ($("#password").val() != $("#confPasswordSecUser").val()){
			 $("#confPasswordSecUser").addClass("input-error").removeClass("input-correct");
			 $("#conf-password-req").text("Confirm MPIN should be same as MPIN");
			 $("#conf-password-req").show();
			 
		 } else if ($("#password").val() == $("#confPasswordSecUser").val()){
			 $("#confPasswordSecUser").addClass("input-correct").removeClass("input-error");
			 $("#conf-password-req").hide();
		 }
		 /*else {
		 if ($("#confPasswordSecUser").val().length > 7){
			 $("#confPasswordSecUser").addClass("input-correct").removeClass("input-error");
			 $("#conf-password-req_length").hide();
		 }
		 if ($("#confPasswordSecUser").val().length < 7){
			 $("#confPasswordSecUser").addClass("input-error").removeClass("input-correct");
			 $("#conf-password-req_length").show();	 
		 }
		 }*/		
	});
	 
	 
	 $("#password").on("keyup input", function(){		
		 var password = $("#password").val();
		    if(pwdRegex.test(password) == false){
		    	$("#password-format").show();			
				$("#password").addClass("input-error").removeClass("input-correct");	
		    }  if(pwdRegex.test(password)){
				$("#password-format").hide();
				$("#password-req").hide();
				$("#password").addClass("input-correct").removeClass("input-error");
			}
		     /*if(validatePwdWithConfPwd()){
		    	 $("#confPasswordSecUser").addClass("input-error").removeClass("input-correct");
				 $("#conf-password-req").show();
		    } */		 
		});
	 

	 $("#emailId").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		if(!emailRegex.test($("#emailId").val())){
			$("#reg-email-req").show();
			$("#emailId").addClass("input-error").removeClass("input-correct");
		}
		if(emailRegex.test($("#emailId").val())){
			$("#reg-email-req").hide();
			$("#emailId").addClass("input-correct").removeClass("input-error");
		}
		if($("#emailId").val() == ''){
			$("#reg-email-req").hide();
			$("#emailId").addClass("").removeClass("input-error");
		}
	});

	
		$("#contactNo").on("keyup, input",function(){
			this.value = this.value.replace(/^0/, '');
			if(contactNumRegex.test($("#contactNo").val()) !== true){
				$("#contact-no-req").show();			
				$("#contactNo").addClass("input-error").removeClass("input-correct");	
			}
			if(contactNumRegex.test($("#contactNo").val()) === true){
				$("#contact-no-req").hide();
				$("#contactNo").addClass("input-correct").removeClass("input-error");
			}
			if(contactNumRegex.test($("#contactNum").val()) !== true){
				this.value = this.value.replace(/[^0-9]+/, '');
			}
		});
		
		
		$("#secUserAadhaarNo").on("keyup, input",function(){
			if(aadharNumRegex.test($("#secUserAadhaarNo").val()) !== true){
				$("#aadharNo-req").show();			
				$("#secUserAadhaarNo").addClass("input-error").removeClass("input-correct");	
			}  
			if(aadharNumRegex.test($("#secUserAadhaarNo").val()) === true){
				$("#aadharNo-req").hide();
				$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");
			}
			if(aadharNumRegex.test($("#aadharNum").val()) !== true){
				this.value = this.value.replace(/[^0-9]+/, '');
			}
			if(($("#secUserAadhaarNo").val().length > 0) && ($("#secUserAadhaarNo").val().length <12)){
				$("#aadharNo-req").show();			
				$("#secUserAadhaarNo").addClass("input-error").removeClass("input-correct");	
			}
			if(($("#secUserAadhaarNo").val().length == 0) ){
				$("#aadharNo-req").hide();
				$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");	
			}
		});
		
});


$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});

function editRecord(idValue){
	$("#_csrf_token1").val($("#_csrf_token").val());
	document.manageSecondaryUser.action = "./wizardEditSecondaryUser";
	document.manageSecondaryUser.id.value = idValue;
	document.manageSecondaryUser._csrf_token.value = $("#_csrf_token1").val();
	document.manageSecondaryUser.submit();	
}


function deleteRecord(idValue){
	$("#_csrf_token1").val($("#_csrf_token").val());
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
			document.manageSecondaryUser.action = "./wizardDeleteSecondaryUser";
			document.manageSecondaryUser.id.value = idValue;
			document.manageSecondaryUser._csrf_token.value = $("#_csrf_token1").val();
			document.manageSecondaryUser.submit();	
		}
	});
}
