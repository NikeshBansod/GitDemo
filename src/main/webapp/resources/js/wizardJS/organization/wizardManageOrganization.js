var finalSubmit = false;

$(document).ready(function(){
	
	var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//	var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var gstinRegistered = false;
	var contactNumRegex = /[0-9]{2}\d{8}/;
	var aadharNumRegex = /[0-9]{2}\d{10}/;
	var contactLength = 10, AadharLength = 12;
	var blankMsg="This field is required";
	var lengthMsg = "Minimum length should be ";
	var regMsg = "data should be in proper format";
	/*var natureOfBusinessHidden = $("#natureOfBusinessHidden").val();
	var otherNatureOfBusinessHidden = $("#otherNatureOfBusinessHidden").val();*/
	var termsConditionsStatus=false;
	
	/*loadNatureOfBusiness(natureOfBusinessHidden);
	manageNatureOfBusiness();
	
	if($("#pinCode").val() != ''){
	loadStateByPincode();
	}*/
	manageTermsConditions();
	
	$("#uploadLogo").click(function(e){
		finalSubmit = false;
		$( "#CUpdateBtn" ).click();
		e.preventDefault();	
		if(finalSubmit){
			callAjaxUserUpdate();
			//window.location.href = 'getLogoPage';
		}
	
	});
	
	$("#UpdateBtn").click(function(e){
		finalSubmit = false;
		$( "#CUpdateBtn" ).click();
		e.preventDefault();	
		if(finalSubmit){
			$("#updateUserMasterFormId").submit();
		}else{
			e.preventdefault();
		}
	});
	
	
	$("#CUpdateBtn").click(function(e){
		 var errFlag = validateOrgName(); 
		 var errFlag1 = validatePanNo();
		 var errFlag3 = validateOrgType(); 
		 //var errFlag4 = validateBillingFor(); 
		 /*var errFlag5 = validateAddress();
		 var errFlag6 = validatePinCode();
		 var errFlag7 = validateState(); */
		 var errFlag9 = validateContactNo(); 
		 var errFlag12 = validateUserId();
		 var errFlag14 = validateDefaultMailId();
		 var errFlag15 = validateCIN();
		 var errFlag20 = false;
		 var otpValidated = false;
		 finalSubmit = false;
		 
		 if ($("#orgType option:selected").text() == "Others"){
		 errFlag20 = validateOtherOrg();
		 }
		 
		/* var errFlagNatureOfBusiness=false;
		 if($("#natureOfBusiness option:selected").text()=="OTHERS"){
			 errFlagNatureOfBusiness =validateOtherNatureOfBusiness();
		 }*/
		 
		 var errFlag21 = validateCustomerAadharNo();
		 if($("#termsConditionsFlag").is(":checked")  || $("#termsConditionsFlagHidden").val()=="Y"){
			 termsConditionsStatus=false;
			 $("#divTermsConditions").show();
			 $("#termsConditionsFlagHidden").val("Y")
			$("#divTermsConditions").hide();
		 }else{
			 termsConditionsStatus=true;
			 $("#termsConditionsFlag").val("N");
			 bootbox.alert("Please Accept Terms & Conditions");
		 }
		 if ( (errFlag) || (errFlag1) ||  (errFlag3)  || (errFlag9) //(errFlag5)	|| (errFlag6) || (errFlag7) || (errFlagNatureOfBusiness) || 
				|| (errFlag12)|| (errFlag14) || (errFlag15) || (errFlag20) || (errFlag21) ||(otpValidated) || (termsConditionsStatus)){
			 e.preventDefault();	 
		 }else{
			 finalSubmit = true;
		 }		 
		 
		 if((errFlag)){
			 focusTextBox("org-name");
		 } else if((errFlag1) ){
			 focusTextBox("pan-number");
		 } else if((errFlag3)){
			 focusTextBox("orgType");
		 } else if((errFlag12)){
			 focusTextBox("userId");
		 } else if(errFlag14){
			 focusTextBox("defaultEmailId");
		 } else if(errFlag15){
			 focusTextBox("cin");
		 } else if((errFlag9)){
			 focusTextBox("contactNo");
		 } else if((errFlag20)){ 				
			 focusTextBox("otherOrgType");
		 }else if(errFlag21){
			 focusTextBox("secUserAadhaarNo");
		 }
		 /*else if((errFlag4)){
			 focusTextBox("billing-for");
		 } */
		 /*else if((errFlag5)){
			 focusTextBox("address1");
		 } else if((errFlag6)){				
			 focusTextBox("pinCode");
		 } else if((errFlag7)){ 				
			 focusTextBox("state");
		 } else if(errFlagNatureOfBusiness){
			 focusTextBox("natureOfBusiness");
		 }*/ 		 		 
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
		 }
		 return errFlag1;
	}
		
	function validateOrgType(){
		errFlag3 = validateTextField("orgType","org-type-id",blankMsg);
		 if(!errFlag3){
			 errFlag3=validateSelectField("orgType","org-type-id");
		 }
		 return errFlag3;
	}
	
	function validateEmailId(){
		errFlag8 = validateTextField("emailId","reg-email-req",blankMsg);
		 if(!errFlag8){
			 errFlag8=validateRegexpressions("emailId","reg-email-req",regMsg,emailRegex);
		 }
		 return errFlag8;
	}
	
	function validateContactNo(){
		errFlag9 = validateTextField("contactNo","contactNo-req",blankMsg);
		 if(!errFlag9){
			 errFlag9=validateRegexpressions("contactNo","contactNo-req",regMsg,contactNumRegex);
		 }
		 return errFlag;
	}
	
	function validateGstinNo(){
		errFlag10 = validateTextField("reg-gstin","reg-gstin-req",blankMsg);
		 if(!errFlag10){
			 errFlag10=validateRegexpressions("reg-gstin","reg-gstin-req",regMsg,GstinNumRegex);
		 }
		 return errFlag10;
	}
	
	function valiateGstinState(){
		errFlag11 = validateTextField("reg-gstin-state","reg-gstin-state-reg",blankMsg);
		 if(!errFlag11){
			 errFlag11=validateSelectField("reg-gstin-state","reg-gstin-state-reg");
		 }
		 return errFlag11;
	}
	
	function validateUserId(){
		errFlag12 = validateTextField("userId","userId-req",blankMsg);
		 if(!errFlag12){
			 errFlag12=validateFieldLength("userId","userId-req",lengthMsg,2);
		 }
		 return errFlag12;
	}
	
	function validateAuthName(){
		errFlag13 = validateTextField("userName","ser-userName",blankMsg);
		 if(!errFlag13){
			 errFlag13=validateFieldLength("userName","ser-userName",lengthMsg,2);
		 }
		 return errFlag13;
	}
	
	function validateDefaultMailId(){
		errFlag14 = validateTextField("defaultEmailId","cust-email-format",blankMsg);
		 if(!errFlag14){
			 errFlag14=validateRegexpressions("defaultEmailId","cust-email-format", regMsg, emailRegex);
		 }
		 return errFlag14;
	}
	
	function validateOtherOrg(){
		errFlag20 = validateTextField("otherOrgType","otherOrgType-req",blankMsg);
		 if(!errFlag20){
			 errFlag20=validateFieldLength("otherOrgType","otherOrgType-req",lengthMsg,1);
		 }
		 return errFlag20;
	}
	
	
	function validateCustomerAadharNo(){	 
		errFlag21=validateFieldLength("secUserAadhaarNo","aadharNo-req",lengthMsg,AadharLength);
		if(!errFlag21){
			errFlag21=validateRegexpressions("secUserAadhaarNo","aadharNo-req",regMsg,aadharNumRegex);
		}
		return errFlag21;
	}

	$("#orgType").change(function(){
		if ($("#orgType").val() === ""){
			$("#orgType").addClass("input-error").removeClass("input-correct");
			$("#org-type-id").show();
		}
		if ($("#orgType").val() !== ""){
			$("#orgType").addClass("input-correct").removeClass("input-error");
			$("#org-type-id").hide();
		}
	});
	
	$("#otherOrgType").on("keyup input", function(){
		 this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z-\s]*$/, '');
		if ($("#otherOrgType").val() !== ""){
			$("#otherOrgType").addClass("input-correct").removeClass("input-error");
			$("#otherOrgType-req").hide();
		}
		if ($("#otherOrgType").val() === ""){
			$("#otherOrgType").addClass("input-error").removeClass("input-correct");
			$("#otherOrgType-req").show();
		}
	});

	/*$("#billing-for").change(function(){
		if ($("#billing-for").val() !== ""){
			$("#billing-for").addClass("input-correct").removeClass("input-error");
			$("#billing-for-req").hide();
		}
		if ($("#billing-for").val() === ""){
			$("#billing-for").addClass("input-error").removeClass("input-correct");
			$("#billing-for-req").show();
		}
	});*/

	function manageOrgType(){
		if ($("#orgType option:selected").text() === "Others"){
			$("#divOtherOrgType").show();
		}else{
			$("#divOtherOrgType").hide();
			$("#otherOrgType").val("");
		}
		if ($("#orgType").val() === "" || $("#orgType").val() == "0"){
			$("#orgType").addClass("input-error").removeClass("input-correct");
			$("#org-type-id").show();
		}
		if ($("#orgType").val() !== ""){
			$("#orgType").addClass("input-correct").removeClass("input-error");
			$("#org-type-id").hide();
		}
	}
	
	$("#orgType").change(function(){
		manageOrgType();	
	});

	function manageTermsConditions(){
		if($("#termsConditionsFlagHidden").val()=="Y"){
			 $("#termsConditionsFlagHidden").val("Y")
			$("#divTermsConditions").hide();
		}else{
			//$("#termsAndConditionsId").show();
			//$("#editPageContainer").hide();
			//$("#backToMyProfile").hide();
		}
		
	}

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
		 if ($("#org-name").val().length < 1){
			 $("#org-name").addClass("input-error").removeClass("input-correct");
			 $("#org-name-req").show();	
			 $("#org-name").focus();
		 }
	});

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


	$("#pan-number").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var PanRegex = /[a-zA-z]{5}\d{4}[a-zA-Z]{1}/;
		if ( (PanRegex.test(this.value) === true) ){
			 $("#pan-number").addClass("input-correct").removeClass("input-error");
			 $("#pan-number-req").hide();	
		}
		if ( !(PanRegex.test(this.value) === true) ){
			 $("#pan-number").addClass("input-error").removeClass("input-correct");
			 $("#pan-number-req").show();
			 $("#pan-number").focus();
		}
		if ($("#pan-number").val().length !== 10){
			 $("#pan-number").addClass("input-error").removeClass("input-correct");
			 $("#pan-number-req").show();
			 $("#pan-number").focus();
		}
	});

 
 	$("#contactNo").on("keyup input",function(){
	 this.value = this.value.replace(/^0/, '');
		var contactNumRegex = /[0-9]{2}\d{8}/;
		if(contactNumRegex.test(this.value) !== true){
			this.value = this.value.replace(/[^0-9]+/, '');
			 $("#contactNo").addClass("input-error").removeClass("input-correct");
			 $("#contactNo-req").show();
			 $("#contactNo").focus();
		}
		if (contactNumRegex.test(this.value) === true){
			 $("#contactNo").addClass("input-correct").removeClass("input-error");
			 $("#contactNo-req").hide();
		}
	});
 
 	$("#userName").on("keyup input",function(){
		this.value = this.value.replace(/[^[a-zA-Z-, ]*$/, '');
		 if ($("#userName").val() === ""){
			 $("#userName").addClass("input-error").removeClass("input-correct");
			 $("#ser-userName").show();			 
		 }
		 if ($("#userName").val() !== ""){
			 $("#userName").addClass("input-correct").removeClass("input-error");
			 $("#ser-userName").hide();			 
		 } 
	});

	$("#emailId").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		if(!emailRegex.test($("#emailId").val())){
			$("#reg-email-req").show();
			$("#emailId").addClass("input-error").removeClass("input-correct");
			$("#emailId").focus();
		}
		else{
			$("#reg-email-req").hide();
			$("#emailId").addClass("input-correct").removeClass("input-error");
		}		
		if ($("#emailId").val() == ""){
			$("#reg-email-req").hide();
			$("#emailId").addClass("input-correct").removeClass("input-error");
		 }		
	});
	
	$("#defaultEmailId").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		if ($("#defaultEmailId").val() == ""){
			 $("#cust-email-format").text('This field is required');
			 $("#cust-email-format").show();
				$("#defaultEmailId").addClass("input-error").removeClass("input-correct");
		 } 
		else {
			if(!emailRegex.test($("#defaultEmailId").val())){
				$("#cust-email-format").show();
				$("#cust-email-format").text('This field should be in a correct format');
				$("#defaultEmailId").addClass("input-error").removeClass("input-correct");
				$("#defaultEmailId").focus();
			}
			else{
				$("#cust-email-format").hide();
				$("#defaultEmailId").addClass("input-correct").removeClass("input-error");
			}
		}		 
	});
	
	$("#secUserAadhaarNo").on("keyup, input",function(){	
		if($("#secUserAadhaarNo").val()!="" && aadharNumRegex.test($("#secUserAadhaarNo").val()) !== true){
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
		if ((($("#secUserAadhaarNo").val().length < 12) && ($("#secUserAadhaarNo").val().length > 0))){
			$("#aadharNo-req").show();
			$("#secUserAadhaarNo").addClass("input-error").removeClass("input-correct");
		} else if($("#secUserAadhaarNo").val().length == 12){
			$("#aadharNo-req").hide();
			$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");
		}
		
		if($("#secUserAadhaarNo").val()==""){
			$("#aadharNo-req").hide();
			$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");
		}
	});
		
	$("#termsConditionsFlag").change(function() {
	    if(this.checked) {
	    	$("#termsConditionsFlag").val("Y");
	    	termsConditionsStatus=false;
	    }else{
	    	termsConditionsStatus=false;
	    }
	});	
	
	$("#iAgree").click(function () {
		$("#termsConditionsFlagHidden").val("Y");
		var resp = $("#CUpdateBtn").click();
		if(finalSubmit == false){
			bootbox.alert("Profile is incomplete. Kindly fill up the mandatory fields and then Accept Terms and Conditions.");
			$("#divTermsConditions").show();
			$("#termsAndConditionsId").hide();
			$("#editPageContainer").show();
		}else{
		    $.ajax({
				url : "updateTermsConditions",
				type : "POST",
				dataType : "json",
				headers: {_csrf_token : $("#_csrf_token").val()},
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
					
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));			
					if(jsonVal == true){
						 $("#termsConditionsFlagHidden").val("Y")
							$("#divTermsConditions").hide();
							$("#termsAndConditionsId").hide();
							$("#editPageContainer").show();
					}			
				},
				error: function (data,status,er) {			 
					 getWizardInternalServerErrorPage();   
				}
		    });
		}
	});
	
	/*-- code under dis is not used as html elements are commented which won't triggered any below elements as per business requiremnet --*/
	$("#pinCode").on("keyup input", function(){
		var contactNumRegex = /[0-9]{2}\d{8}/;
		if(contactNumRegex.test(this.value) !== true){
			this.value = this.value.replace(/[^0-9]+/, '');
			 $("#pinCode").addClass("input-error").removeClass("input-correct");
			 $("#zip-req").show();
			 $("#pinCode").focus();
		}
		if ($("#pinCode").val().length === 6){
			 $("#pinCode").addClass("input-correct").removeClass("input-error");
			 $("#zip-req").hide();
			 loadStateByPincode();
		}
	});
	
	$("#address1").on("keyup input",function(){
		this.value = this.value.replace(/[\\[]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		 if ($("#address1").val() !== ""){
			 $("#address1").addClass("input-correct").removeClass("input-error");
			 $("#address1-req").hide();		 
		 }
		 if ($("#address1").val() === ""){
			 $("#address1").addClass("input-error").removeClass("input-correct");
			 $("#address1-req").show();
			 $("#address1").focus();
		 }
	});
	
	$("#state").on("keyup input",function(){
		 if ($("#state").val() !== ""){
			 $("#state").addClass("input-correct").removeClass("input-error");
			 $("#state-req").hide();		 
		 }
		 if ($("#state").val() === ""){
			 $("#state").addClass("input-error").removeClass("input-correct");
			 $("#state-req").show();
			 $("#state").focus();
		 }
	});
	
	$("#natureOfBusiness").change(function(){
		if ($("#natureOfBusiness").val() == "OTHERS"){
			$("#divOtherNatureOfBusiness").show();
		}else{
			$("#divOtherNatureOfBusiness").hide();
			$("#divOtherNatureOfBusiness").val("");
		}
	});
	
	$("#otherNatureOfBusiness").on("keyup input", function(){
		 this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z-\s]*$/, '');
		if ($("#otherNatureOfBusiness").val() !== ""){
			$("#otherNatureOfBusiness").addClass("input-correct").removeClass("input-error");
			$("#otherNatureOfBusiness-req").hide();
		}
		if ($("#otherNatureOfBusiness").val() === ""){
			$("#otherNatureOfBusiness").addClass("input-error").removeClass("input-correct");
			$("#otherNatureOfBusiness-req").show();
		}
	});
	
	$("#regdOfficeDetails").on("keyup input", function(){
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
	});
	
	$("#cin").on("keyup input", function(){
		this.value = this.value.replace(/[^[a-zA-Z0-9-]*$/, '');
		 if ($("#cin").val().length >= 21){
			 $("#cin").addClass("input-correct").removeClass("input-error");
			 $("#cust-cin-format").hide();		 
		 }
		 if ($("#cin").val().length < 21){
			 $("#cin").addClass("input-error").removeClass("input-correct");
			 $("#cust-cin-format").show();	
			 $("#cin").focus();
		 }
	});
	
	$("#principalPlaceOfBusiness").on("keyup input", function(){
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
	});

	function manageNatureOfBusiness(){
		if ($("#natureOfBusiness").val() === "OTHERS"){
			$("#divOtherNatureOfBusiness").show();
		}else{
			$("#divOtherNatureOfBusiness").hide();
			$("#divOtherNatureOfBusiness").val("");
		}
	}
	
	function loadNatureOfBusiness(natureOfBusinessHidden){	
	 $.ajax({
			url : "getNatureOfBusinessList",
			type : "POST",
			dataType : "json",
			async : false,
			headers: {_csrf_token : $("#_csrf_token").val()},
			success:function(json,fTextStatus,fRequest){				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}
				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
				$("#natureOfBusiness").append(
				'<option value="">Select Nature of Business</option>');
				$.each(json,function(i,value) {
					if(value.natureOfBusiness==natureOfBusinessHidden){
						$("#natureOfBusiness").append($('<option>').text(value.natureOfBusiness).attr('value',value.natureOfBusiness).attr('selected', 'selected'));
						if(natureOfBusinessHidden=="OTHERS"){
							
							$("#otherNatureOfBusiness").val($("#otherNatureOfBusinessHidden").val());
						}
					}else{
						$("#natureOfBusiness").append($('<option>').text(value.natureOfBusiness).attr('value',value.natureOfBusiness));
					}
				});
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {	        	 
	        	 getWizardInternalServerErrorPage();   
	        }
		}); 	
	}

	function validateOtherNatureOfBusiness(){
		errFlagNatureOfBusiness = validateTextField("otherNatureOfBusiness","otherNatureOfBusiness-req",blankMsg);
		return errFlagNatureOfBusiness;
	}

	function validateBillingFor(){
		errFlag4 = validateTextField("billing-for","billing-for-req",blankMsg);
		 if(!errFlag4){
			 errFlag4=validateSelectField("billing-for","billing-for-req");
		 }
		 return errFlag4;
	}
	
	function validateAddress(){
		errFlag5 = validateTextField("address1","address1-req",blankMsg);
		 if(!errFlag5){
			 errFlag5=validateFieldLength("address1","address1-req",lengthMsg,2);
		 }
		 return errFlag5;
	}
	
	function validatePinCode(){
		errFlag6 = validateTextField("pinCode","zip-req",blankMsg);
		 if(!errFlag6){
			 errFlag6=validateFieldLength("pinCode","zip-req",lengthMsg,6);
		 }
		 return errFlag6;
	}
	
	function validateState(){
		errFlag7 = validateTextField("state","state-req",blankMsg);
		 if(!errFlag7){
			 errFlag7=validateFieldLength("state","state-req",lengthMsg,1);
		 }
		 return errFlag7;
	}

	function validateCIN(){
		 if ($("#cin").val().length > 0){
			 errFlag15=validateFieldLength("cin","cust-cin-format",lengthMsg,21);
		 } else {
			 errFlag15 = false;
		 }
	return errFlag15;	
	}
	/*-----------------------------------------------------------------------------------*/
});


$(document).ready(function(){
	$("#termsConditions").click(function(e){
		e.preventDefault();	
		$("#termsAndConditionsId").show();
		$("#editPageContainer").hide();
		$('html, body').animate({ scrollTop: 0 }, 'fast');
	});
	
	/*$("#backToMyProfile").click(function(e){
		e.preventDefault();	
		$("#termsAndConditionsId").hide();
		$("#editPageContainer").show();
	});*/		
});

function callAjaxUserUpdate(){
	$.ajax({
		  method:"POST",
		  url:"wupdateUserAjax",
		  dataType : "json",
		  async : false,
		  headers: {_csrf_token : $("#_csrf_token").val()},
		  data : $("#updateUserMasterFormId").serialize(), 
		  success:function(json,fTextStatus,fRequest) {
			  if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}
				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
			if(json.response == 'FAILURE'){
				bootbox.alert(json.message);
			}else{
				window.location.href = "wGetLogoPage";
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		  },
		  error: function (data,status,er) {				 
				 getWizardInternalServerErrorPage();   
			}
	});
}


/*
	 $("#reg-gstin").on("keyup input",function(){
		//this.value = removeWhiteSpace(this.value);
		this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
		if(GstinNumRegex.test(this.value) === true){				
			 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
			 $("#reg-gstin-req").hide();
		}
		if(GstinNumRegex.test(this.value) !== true){
			 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
			 $("#reg-gstin-req").show();
			 $("#reg-gstin").focus();
		}
		if((this.value) === ''){
			$("#reg-gstin-state").empty(); 
		}
	});
	 
	 
	$("#reg-gstin").blur(function(){
	    var gstin = $("#reg-gstin").val();
	    if(gstin != ''){
		    if(validateGSTINWithRegex(gstin)){
			    $.ajax({
					url : "checkIfGstinIsRegistered",
					type : "POST",
					//method : "GET",
					//contentType : "application/json",
					dataType : "json",
					data : {gstin : gstin},
					async : false,
					success : function(jsonVal) {
						if(jsonVal == true){
							 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
							 $("#reg-gstin-back-req").text('GSTIN  - '+gstin+' already Registered. Try some other GSTIN.');
							 $("#reg-gstin-back-req").show();
							 gstinRegistered = true;
						}else{
							 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
							 $("#reg-gstin-req").hide();
							 $("#reg-gstin-back-req").hide();
							 gstinRegistered = false;
						}
					}
			    });
			    if(gstinRegistered!=true){
			    	//getStateByGstinNumber(gstin);
			    }
			}
	    }    
	});
	
	function validateGSTINWithRegex(gstin){
	if(GstinNumRegex.test(gstin) === true){				
		 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
		 $("#reg-gstin-req").hide();
		 $("#reg-gstin-back-req").hide();
		 return true;
	}
	if(GstinNumRegex.test(gstin) !== true){
		 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
		 $("#reg-gstin-req").show();
	//	 $("#reg-gstin").focus();
		 $("#reg-gstin-back-req").hide();
		 return false;
	}
	
}
*/

