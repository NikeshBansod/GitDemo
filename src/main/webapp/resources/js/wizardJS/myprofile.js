$(document).ready(function(){
	var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//	var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var gstinRegistered = false;
	var contactNumRegex = /[0-9]{2}\d{8}/;
	var aadharNumRegex = /[0-9]{2}\d{10}/;
	var contactLength = 10; 
	var AadharLength = 12;
	var blankMsg="This field is required";
	var lengthMsg = "Minimum length should be ";
	var regMsg = "Data should be in proper format";
	
	var natureOfBusinessIdHidden = $('#natureOfBusinessIdHidden').val();
	var natureOfBusinessTypeHidden = $("#natureOfBusinessTypeHidden").val();
	loadNatureOfBusiness(natureOfBusinessIdHidden,natureOfBusinessTypeHidden);
	
	var selOrgType = $("#orgTypeHidden").val();	
	var firmTypeHidden = $('#firmTypeHidden').val();
	loadOrgTypeList(selOrgType,firmTypeHidden);
	
	var billingForId = $("#billingForHidden").val();
	loadBillingforList(billingForId);
	
	if($("#pincode").val() != ''){
		loadStateByPincode();
	}
		
	$("#firmName").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		 }
		 this.value = this.value.replace(/[[]*$/, '');
		 this.value = this.value.replace(/[^[\\a-zA-Z0-9\s- &/.@'()!]*$/, '');
		 if ($("#firmName").val().length > 2){
			 //$("#firmName").addClass("input-correct").removeClass("input-error");
			 $("#firmName-req").hide();		 
		 }
		 if ($("#firmName").val().length == 1 || $("#firmName").val().length >100){
			 //$("#firmName").addClass("input-error").removeClass("input-correct");
			 $("#firmName-req").text('Firm Name length should minimum 2 and maximum 100 characters.');	
			 $("#firmName-req").show();	
			 $("#firmName").focus();
		 }
		 
		 if ($("#firmName").val().length < 1 ){
			 //$("#firmName").addClass("input-error").removeClass("input-correct");
			 $("#firmName-req").show();	
			 $("#firmName").focus();
		 }
	});	

	$("#firmTypes").change(function(){
		manageOrgType();
		
	});

	$("#otherFirmType").on("keyup input", function(){
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z-\s]*$/, '');
		if ($("#otherFirmType").val() !== ""){
			//$("#otherOrgType").addClass("input-correct").removeClass("input-error");
			$("#otherOrgType-req").hide();
		}
		if ($("#otherFirmType").val() === ""){
			//$("#otherOrgType").addClass("input-error").removeClass("input-correct");
			$("#otherOrgType-req").show();
		}
	});

	

	$("#aadharNo").on("keyup, input",function(){		
		if($("#aadharNo").val()!="" && aadharNumRegex.test($("#aadharNo").val()) !== true){
			$("#aadharNo-req").show();	
			//$("#aadharNo-back-req").show();		
			//$("#secUserAadhaarNo").addClass("input-error").removeClass("input-correct");	
		}
		if(aadharNumRegex.test($("#aadharNo").val()) === true){
			$("#aadharNo-req").hide();
			//$("#aadharNo-back-req").hide();
			//$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");
		}
		/*if(aadharNumRegex.test($("#aadharNum").val()) !== true){
			this.value = this.value.replace(/[^0-9]+/, '');
		}*/
		if ((($("#aadharNo").val().length < 12) && ($("#aadharNo").val().length > 0))){
			$("#aadharNo-req").show();
			//$("#aadharNo-back-req").show();
			//$("#secUserAadhaarNo").addClass("input-error").removeClass("input-correct");
		} else if($("#aadharNo").val().length == 12){
			$("#aadharNo-req").hide();
			$("#aadharNo-back-req").hide();
			//$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");
		}		
		if($("#aadharNo").val()==""){
			$("#aadharNo-req").show();
			//$("#secUserAadhaarNo").addClass("input-correct").removeClass("input-error");
		}
	});

	$("#natureOfBusiness").change(function(){
		manageNatureOfBusiness();
	});
	
	$("#otherNatureOfBusiness").on("keyup input", function(){
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z-\s]*$/, '');
		if ($("#otherNatureOfBusiness").val() !== ""){
			//$("#natureOfBusiness").addClass("input-correct").removeClass("input-error");
			$("#otherNatureOfBusiness-req").hide();
		}
		if ($("#otherNatureOfBusiness").val() === ""){
			//$("#natureOfBusiness").addClass("input-error").removeClass("input-correct");
			$("#otherNatureOfBusiness-req").show();
		}
	});

	$("#billingFor").change(function(){
		if ($("#billingFor").val() !== ""){
			//$("#billingFor").addClass("input-correct").removeClass("input-error");
			$("#billing-for-req").hide();
		}
		if ($("#billingFor").val() === ""  || $("#billingFor").text() === "---Select---" ){
			//$("#billingFor").addClass("input-error").removeClass("input-correct");
			$("#billing-for-req").show();
		}
	});

	$("#cin").on("keyup input", function(){
		this.value = this.value.replace(/[^[a-zA-Z0-9-]*$/, '');
		 if ($("#cin").val().length >= 21){
			 //$("#cin").addClass("input-correct").removeClass("input-error");
			 $("#cin-format").hide();		 
		 }
		 if ($("#cin").val().length < 21){
			 //$("#cin").addClass("input-error").removeClass("input-correct");
			 $("#cin-format").show();	
			 $("#cin").focus();
		 }
	});

	$("#address").on("keyup input",function(){
		 this.value = this.value.replace(/[\\[]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		 if ($("#address").val() !== ""){
			 //$("#address").addClass("input-correct").removeClass("input-error");
			 $("#address-req").hide();		 
		 }
		 if ($("#address").val() === ""){
			 //$("#address").addClass("input-error").removeClass("input-correct");
			 $("#address-req").show();
			 $("#address").focus();
		 }
	});
	 
	$("#pincode").on("keyup input", function(){
		var contactNumRegex = /[0-9]{2}\d{8}/;
		if(contactNumRegex.test(this.value) !== true){
			this.value = this.value.replace(/[^0-9]+/, '');
			 //$("#pinCode").addClass("input-error").removeClass("input-correct");
			 $("#zip-req").show();
			 $("#pincode").focus();
		}
		if ($("#pincode").val().length === 6){
			 //$("#pinCode").addClass("input-correct").removeClass("input-error");
			 $("#zip-req").hide();
			 loadStateByPincode();
		}
	});

	$("#city").on("keyup input",function(){
		 if ($("#stateName").val() !== ""){
			 //$("#city").addClass("input-correct").removeClass("input-error");
			 $("#city-req").hide();		 
		 }
		 if ($("#city").val() === ""){
			 //$("#city").addClass("input-error").removeClass("input-correct");
			 $("#city-req").show();
			 $("#city").focus();
		 }
	});

	$("#stateName").on("keyup input",function(){
		 if ($("#stateName").val() !== ""){
			 //$("#stateName").addClass("input-correct").removeClass("input-error");
			 $("#state-req").hide();		 
		 }
		 if ($("#stateName").val() === ""){
			 //$("#stateName").addClass("input-error").removeClass("input-correct");
			 $("#state-req").show();
			 $("#stateName").focus();
		 }
	});

	 $("#landlineNo").on("keyup input",function(){
		 this.value = this.value.replace(/^0/, '');
			var contactNumRegex = /[0-9]{2}\d{8}/;
			if(contactNumRegex.test(this.value) !== true){
				this.value = this.value.replace(/[^0-9]+/, '');
				 //$("#landlineNo").addClass("input-error").removeClass("input-correct");
				 $("#landlineNo-req").show();
				 $("#landlineNo").focus();
			}
			if (contactNumRegex.test(this.value) === true){
				 //$("#landlineNo").addClass("input-correct").removeClass("input-error");
				 $("#landlineNo-req").hide();
			}
		});

		$("#registeresOfficeDetails").on("keyup input", function(){
			 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		});

		$("#principalPlaceOfBusiness").on("keyup input", function(){
			 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		});

	$("#UpdateBtn").click(function(e){
		 var errFlagVendorCode = validateVendorCode(); 
		 var errFlag = validateOrgName(); 
		 var errFlag1 = validatePanNo();
		 var errFlag3 = validateOrgType(); 
		 var errFlag4 = validateBillingFor(); 
		 var errFlag5 = validateAddress();
		 var errFlag6 = validatePinCode();
		 var errFlag7 = validateState(); 
		 var errFlag9 = validateMobileNo(); 
		 var errFlag14 = validateMailId();
		 var errFlag15 = validateCIN();
		 var errFlag20 = false;
		 var otpValidated = false;
		 var errFlagAadharNo = validateAadharNo();
		 var errFlagGstinNo = validateGstinNumber();
		 var errFlagLandlineNo = validateLandLineNo();
		 var errFlagCity = validateCity();
		 
		 if ($("#firmTypes option:selected").text() == "Others"){
			 $("#divOtherOrgType").show();
			 errFlag20 = validateOtherOrg();			 
		 }
		 
		 var errFlagNatureOfBusiness=false;
		 if($("#natureOfBusiness option:selected").val() == "" || $("#natureOfBusiness option:selected").text() === "---Select---" ){
			 errFlagNatureOfBusiness = true;
			 $("#natureOfBusiness-req").show();
		 }
		 else if($("#natureOfBusiness option:selected").text()=="OTHERS" || $("#natureOfBusiness option:selected").text()=="Others"){
			errFlagNatureOfBusiness =validateOtherNatureOfBusiness();
			$("#natureOfBusiness-req").hide();
		 }
				 
		 if((errFlagVendorCode) || (errFlag) || (errFlag1) || (errFlag3) || (errFlag4) || (errFlag5) || (errFlag6) || (errFlag7) ||  (errFlag9) || (errFlag14) || 
			(errFlag15) || (errFlag20) || (otpValidated) || (errFlagGstinNo) || (errFlagAadharNo) || (errFlagLandlineNo) || (errFlagCity) || (errFlagNatureOfBusiness) ){ 
			 e.preventDefault();	 
		 }
		 
		 if((errFlag)){
			 focusTextBox("vendorCode");
		 } else if((errFlag)){
			 focusTextBox("firmName");
		 } else if((errFlag3)){
			 focusTextBox("firmTypes");
		 } else if((errFlag20)){ 				
			 focusTextBox("otherFirmType");
		 } else if((errFlag1) ){
			 focusTextBox("panNumber");
		 } else if(errFlagAadharNo){
			 focusTextBox("aadharNo");
		 } else if(errFlagGstinNo){
			 focusTextBox("gstinNumber");
		 } else if(errFlagNatureOfBusiness){
			 focusTextBox("natureOfBusiness");
		 } else if((errFlag4)){
			 focusTextBox("billingFor");
		 } else if(errFlag15){
			 focusTextBox("cin");
		 } else if((errFlag5)){
			 focusTextBox("address");
		 } else if((errFlag6)){				
			 focusTextBox("pincode");
		 } else if(errFlagCity){
			 focusTextBox("city");
		 } else if((errFlag7)){ 				
			 focusTextBox("stateName");
		 } else if((errFlag9)){
			 focusTextBox("mobileNo");
		 } else if(errFlag14){
			 focusTextBox("emailId");
		 } else if(errFlagLandlineNo){
			 focusTextBox("landlineNo");
		 }   
		 
	});

	function loadNatureOfBusiness(natureOfBusinessIdHidden,natureOfBusinessTypeHidden){
	 $.ajax({
			url : "getNatureOfBusinessList",
			type : "POST",
			dataType : "json",
			//async : false,
			success:function(json){
				$("#natureOfBusiness").append('<option value="">---Select---</option>');
				$.each(json,function(i,value){
					if(natureOfBusinessIdHidden==value.id){
						$("#natureOfBusiness").append($('<option>').text(value.natureOfBusinessType).attr('value',value.id).attr('selected', 'selected'));
						if(natureOfBusinessTypeHidden=="OTHERS" || natureOfBusinessTypeHidden=="Others"){							
							$("#divOtherNatureOfBusiness").show();
						}
					}else{
						$("#natureOfBusiness").append($('<option>').text(value.natureOfBusinessType).attr('value',value.id));
					}
				});
	         }
		}); 		
	}
	
	function manageNatureOfBusiness(){
		if($("#natureOfBusiness").val() === ""  || $("#natureOfBusiness").text() === "---Select---" ){
			$("#natureOfBusiness-req").show();		
		}else{
			$("#natureOfBusiness-req").hide();	
		}
		
		if ($("#natureOfBusiness option:selected").text() === "OTHERS" || $("#natureOfBusiness option:selected").text() === "Others" ){
			$("#divOtherNatureOfBusiness").show();
		}else{
			$("#divOtherNatureOfBusiness").hide();
			$("#otherNatureOfBusiness").val("");
		}
	}

	function manageOrgType(){
		if ($("#firmTypes option:selected").text() === "Others"){
			$("#divOtherOrgType").show();
		}else{
			$("#divOtherOrgType").hide();
			$("#otherFirmType").val("");
		}
		if ($("#firmTypes").val() === "" || $("#firmTypes").text() ===  "---Select---"){
			$("#firmTypes-req").show();
		}
		if ($("#firmTypes").val() !== ""){
			$("#firmTypes-req").hide();
		}
	}	
	
   function validateVendorCode(){
	   errFlagVendorCode = validateTextField("vendorCode","vendorCode-req",blankMsg);
   	 if(!errFlagVendorCode){
   		errFlagVendorCode=validateFieldLength("vendorCode","vendorCode-req",lengthMsg,1);
   	 }
   	 return errFlagVendorCode;
   }

	function validateOrgName(){
		errFlag = validateTextField("firmName","firmName-req",blankMsg);
		 if(!errFlag){
			 errFlag=validateFieldLength("firmName","firmName-req",lengthMsg,1);
		 }
		 return errFlag;
	}

	function validateOrgType(){
		errFlag3 = validateTextField("firmTypes","firmTypes-req",blankMsg);
		 if(!errFlag3){
			 errFlag3=validateSelectField("firmTypes","firmTypes-req");
		 }
		 return errFlag3;
	}

	function validateOtherOrg(){
		errFlag20 = validateTextField("otherFirmType","otherOrgType-req",blankMsg);
		 if(!errFlag20){
			 errFlag20=validateFieldLength("otherFirmType","otherOrgType-req",lengthMsg,1);
		 }
		 return errFlag20;
	}

	function validatePanNo(){
		errFlag1 = validateTextField("panNumber","pan-number-req",blankMsg);
		 if(!errFlag1){
			 errFlag1=validateFieldLength("panNumber","pan-number-req",lengthMsg,10);
		 }
		 return errFlag1;
	}

	function validateAadharNo(){		 
		errFlagAadharNo=validateTextField("aadharNo","aadharNo-req",blankMsg);
		if(!errFlagAadharNo){
			errFlagAadharNo=validateFieldLength("aadharNo","aadharNo-back-req",lengthMsg,AadharLength);
		}			
		if(!errFlagAadharNo){
			errFlagAadharNo=validateRegexpressions("aadharNo","aadharNo-req",regMsg,aadharNumRegex);
		}
		return errFlagAadharNo;
	}

    function validateGstinNumber(){
    	errFlagGstinNo = validateTextField("gstinNumber","reg-gstin-req",blankMsg);
		if(!errFlagGstinNo){
			errFlagGstinNo=validateRegexpressions("gstinNumber","reg-gstin-req",regMsg,GstinNumRegex);
		}
		return errFlagGstinNo;
	}
    
	function validateOtherNatureOfBusiness(){
		errFlagNatureOfBusiness = validateTextField("otherNatureOfBusiness","otherNatureOfBusiness-req",blankMsg);
	 	return errFlagNatureOfBusiness;
	 }

	function validateBillingFor(){
		 errFlag4=validateSelectField("billingFor","billing-for-req");
		 return errFlag4;
	}

	function validateCIN(){
		 if ($("#cin").val().length > 0){
		 errFlag15=validateFieldLength("cin","cin-format",lengthMsg,21);
		 } else {
			 errFlag15 = false;
		 }
		 return errFlag15;
	}
	
	function validateAddress(){
		errFlag5 = validateTextField("address","address-req",blankMsg);
		 if(!errFlag5){
			 errFlag5=validateFieldLength("address","address-req",lengthMsg,2);
		 }
		 return errFlag5;
	}

	function validatePinCode(){
		errFlag6 = validateTextField("pincode","zip-req",blankMsg);
		 if(!errFlag6){
			 errFlag6=validateFieldLength("pincode","zip-req",lengthMsg,6);
		 }
		 return errFlag6;
	}
	
	function validateCity(){
		errFlagCity = validateTextField("city","city-req",blankMsg);
		if(!errFlagCity){
			errFlagCity=validateFieldLength("city","city-req",lengthMsg,1);
		 }
		 return errFlagCity;
	}

	function validateState(){
		errFlag7 = validateTextField("stateName","state-req",blankMsg);
		 if(!errFlag7){
			 errFlag7=validateFieldLength("stateName","state-req",lengthMsg,1);
		 }
		 return errFlag7;
	}

	function validateMobileNo(){
		errFlag9 = validateTextField("mobileNo","mobileNo-req",blankMsg);
		 if(!errFlag9){
			 errFlag9=validateRegexpressions("mobileNo","mobileNo-req",regMsg,contactNumRegex);
		 }
		 return errFlag;
	}

	function validateMailId(){
		errFlag14 = validateTextField("emailId","emailId-format",blankMsg);
    	 if(!errFlag14){
    		 errFlag14=validateRegexpressions("emailId","emailId-format", regMsg, emailRegex);
    	 }
    	 return errFlag14;
    }

	function validateLandLineNo(){
		errFlagLandlineNo = validateTextField("landlineNo","landlineNo-req",blankMsg);
		 if(!errFlagLandlineNo){
			 errFlagLandlineNo=validateRegexpressions("landlineNo","landlineNo-req",regMsg,contactNumRegex);
		 }
		 return errFlagLandlineNo;
	}	
	
});


function loadOrgTypeList(selOrgType,firmTypeHidden){
	$.ajax({
	  method:"GET",
	  url:"getOrganizationTypeList", 	
	  contentType:'application/json',
	  //async : false,
	  dataType: 'json',
	  success:function(json){
		$("#firmTypes").append('<option value="">---Select---</option>');
		$.each(json,function(i,value){
			 if(selOrgType==value.id){
				 $('#firmTypes').append($('<option>').text(value.firmType).attr('value',value.id).attr('selected','selected'));
				 if("Others"==value.firmType){
					 $("#divOtherOrgType").show();
				 }
			 }else {
				 $('#firmTypes').append($('<option>').text(value.firmType).attr('value',value.id));
			 }		 	   
		});
	  }
	});			
}

function loadBillingforList(billingForId){
	$.ajax({
		method:"GET",
		url:"getBillingFor",
		contentType:"application/json",
		dataType:"json",
		success:function(result){
			$("#billingFor").append('<option value="">---Select---</option>');
			$.each(result,function(i,value){
				if(billingForId==value.id){
					$("#billingFor").append($('<option>').text(value.billingType).attr('value',value.id).attr('selected','selected'));
				}
				else{
					$("#billingFor").append($('<option>').text(value.billingType).attr('value',value.id));
				}
			})
		}
	})
}
