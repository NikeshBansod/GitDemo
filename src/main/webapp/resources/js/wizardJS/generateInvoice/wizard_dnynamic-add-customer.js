var reqAddr = false;
var dny_GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//var dny_GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
var dny_emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
var dny_contactNumRegex = /[0-9]{2}\d{8}/;
var dny_blankMsg="This field is required";
var dny_alphnumericRegx =  /^[a-zA-Z0-9.-]+$/;
var dny_pinSelMsg = "Please select pinCode from populated list";
var dny_length = 2, dny_pinCodeLength = 6;
var dny_lengthMsg = "Minimum length should be ";
var dny_regMsg = "data should be in proper format";
var dny_cmpMsg = "The State in Address should be same as GSTIN State";
var dny_gstinStateErrMsg = "Invalid State code for GSTIN entered";
var dny_custContactExists = false; 
var dny_gstinStateValid = false;

$(document).ready(function(){
	//customer name - start
	$("#dnyCustName").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		if ($("#dnyCustName").val().length > 1){
			 $("#dnyCustName").addClass("input-correct").removeClass("input-error");
			 $("#dny-cust-name-req").hide();
			 
		 }
		if ($("#dnyCustName").val().length < 1){
			 $("#dnyCustName").addClass("input-error").removeClass("input-correct");
			 $("#dny-cust-name-req").show();
			 }
		
	});

	$("#dnyCustName").on("keydown input",function(){
		
		if ($("#dnyCustName").val().length == '' ){
			 $("#dnyCustName").addClass("input-error").removeClass("input-correct");
			 $("#dny-cust-name-req").show();
			 }
		
	});
	//customer name - end
	
	//customer type - start
	if($("#Individual").is(":checked")){
		$("#dnyShowDivIfRegistered").hide();
		reqAddr = true;	
		$("#dnyCustAddrDiv").removeClass("mandatory");
	}else{
		$("#dnyShowDivIfRegistered").show();
		reqAddr = false;
		$("#dnyCustAddrDiv").addClass("mandatory");
		
	}
	
	$('input[type=radio][name=dnyCustType]').change(function() {
		if (this.value == 'Individual') {
			$("#dnyShowDivIfRegistered").hide();
			reqAddr = true;	
			$("#dnyCustAddrDiv").removeClass("mandatory");
		}else{
			$("#dnyShowDivIfRegistered").show();
			reqAddr = false;
			$("#dnyCustAddrDiv").addClass("mandatory");
		}
		
	});
	//customer type - end
	
	//customer mobile - start
	$("#dnyContactNo").on("keyup input", function(){
		this.value = this.value.replace(/^0/, '');
		var contactNumRegex = /[0-9]{2}\d{8}/;
		if(contactNumRegex.test($("#dnyContactNo").val()) !== true){
			
			this.value = this.value.replace(/[^0-9]+/, '');
		}
		if(contactNumRegex.test($("#dnyContactNo").val()) === true){
			$("#dny-contact-no-req").hide();
			$("#dnyContactNo").addClass("input-correct").removeClass("input-error");	
		}
		if(contactNumRegex.test($("#dnyContactNo").val()) !== true){
			$("#dny-contact-no-req").show();
			$("#dnyContactNo").addClass("input-error").removeClass("input-correct");	
		}
	});
	//customer type - end
	
	//customer email - start
	$("#dnyCustEmail").on("keyup input", function(e){
    	if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		if((this.value!='') && emailRegex.test($("#dnyCustEmail").val()) === true){
			$("#dny-cust-email-format").hide();
			$("#dnyCustEmail").addClass("input-correct").removeClass("input-error");
			}
		
		if((this.value!='') && !emailRegex.test($("#dnyCustEmail").val())){
		$("#dny-cust-email-format").show();
		$("#dnyCustEmail").addClass("input-error").removeClass("input-correct");
		$("#dnyCustEmail").focus();
		}
		
		if((this.value!='') && emailRegex.test($("#dnyCustEmail").val())){
		$("#dny-cust-email-format").hide();
		$("#dnyCustEmail").addClass("input-correct").removeClass("input-error");
		$("#dnyCustEmail").focus();
		}
		
		if($("#dnyCustEmail").val() == ''){
		$("#dny-cust-email-format").hide();
		$("#dnyCustEmail").removeClass("input-error");
	}
	
	});
	//customer email - end
	
	//customer pincode - start
	
	$("#dnyPinCode").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var pincodeRegex = /[0-9]{6}/;
		if(pincodeRegex.test($("#dnyPinCode").val()) !== true){
			
			this.value = this.value.replace(/[^0-9]+/, '');
		}
		if(pincodeRegex.test($("#dnyPinCode").val()) === true){
			$("#dny-cust-zip-req").hide();
			$("#dnyPinCode").addClass("input-correct").removeClass("input-error");	
		}
		if(pincodeRegex.test($("#dnyPinCode").val()) !== true){
			$("#dny-cust-zip-req").hide();
			$("#dnyPinCode").addClass("input-error").removeClass("input-correct");	
		}
	});
	//customer pincode - end
	
	//customer address - start
	$("#dnyCustAddress").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		 
		 this.value = this.value.replace(/[\\[]*$/, '');
		// this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		 if(reqAddr != true){
			 $("#dnyCustAddress").addClass("");
			 $("#dny-address-req").hide();
		 }
		 if ($("#dnyCustAddress").val().length > 1 && reqAddr != true){
			 $("#dnyCustAddress").addClass("input-correct").removeClass("input-error");
			 $("#dny-address-req").hide();
			 
		 }
		 if ($("#dnyCustAddress").val().length < 1 && reqAddr != true){
			 $("#dnyCustAddress").addClass("input-error").removeClass("input-correct");
			 $("#dny-address-req").show();
			 
		 }
	 });
	//customer address - end
	
	//customer gstin - start 
	$("#dnyCustGstId").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		this.value = this.value.toUpperCase();
		this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
		 this.value = this.value.replace(/^00/, '');
		if((this.value!='') && dny_GstinNumRegex.test($("#dnyCustGstId").val()) === true){				
			$("#dny-custGstId-req").hide(); 
			$("#dnyCustGstId").addClass("input-correct").removeClass("input-error");
			 
		}
		if((this.value!='') && dny_GstinNumRegex.test($("#dnyCustGstId").val()) !== true){
			$("#dny-custGstId-req").show(); 
			$("#dnyCustGstId").addClass("input-error").removeClass("input-correct");
			 	
		}
		if($("#dnyCustGstId").val() == ""){				
			$("#dny-custGstId-req").hide(); 
			$("#dnyCustGstId").addClass("input-correct").removeClass("input-error");
			 
		}
	});
	
	$("#dnyCustGstId").blur(function(){
	    var gstin = $("#dnyCustGstId").val();
	    var gstinRegistered = false;
	    if(gstin == ''){
	    	$("#dnyCustGstinState").empty();
	    }
	    if(gstin != ''){
		    if(dny_validateGSTINWithRegex(gstin)){
			    $.ajax({
					url : "isGstinRegisteredWithOrg",
					type : "POST",
					dataType : "json",
					headers: {_csrf_token : $("#_csrf_token").val()},
					data : {gstin : gstin},
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
							 $("#dnyCustGstId").addClass("input-error").removeClass("input-correct");
							 $("#dny-custGstId-req").text('GSTIN  - '+gstin+' is Registered with your organization.');
							 $("#dny-custGstId-req").show();
							 gstinRegistered = true;
						}else{
							 $("#dnyCustGstId").addClass("input-correct").removeClass("input-error");
							 $("#dny-custGstId-req").hide();
							 $("#dny-custGstId-req").hide();
							 gstinRegistered = false;
						}
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					},
					error: function (data,status,er) {
			        	 
			        	 getInternalServerErrorPage();   
			        }
		        });
			    
			    if(gstinRegistered!=true){
			    	dny_getStateByGstinNumber(gstin);
			    }
		    
		    }
	    }
	    
	});
	//customer gstin - end
	
});

$("#dnyPinCode").autocomplete({
    source: function (request, response) {
        $.getJSON("getPinCodeList", {
            term: extractLastRW(request.term)
        }, function( data, status, xhr ){
        	response(data);
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		});
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultWizardSessionExpirePage();
			return;
		}
        
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#dnyPinCode").val();
    	//bootbox.alert"Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	if(zipToShow.length === 6){
	        		 $("#dnyPinCode").addClass("input-error").removeClass("input-correct");
	        		 $("#dny-empty-message").text("No results found for selected pin code : "+zipToShow);
	        		 $("#dny-empty-message").show();
	                 $("#dnyPinCode").val("");
	        	
	        	}
	        } else {
	            $("#dny-empty-message").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var pincode = (value.split('[')[0]).trim();
        var district = value.substring(value.indexOf("[ ") + 1, value.indexOf(" ]")).trim();
     
       
         $.ajax({
        	url : "getPincodeByIdAndDistrict" ,
			type : "post",
			data : {"id" : pincode , "district" : district,_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(json,fTextStatus,fRequest) {
				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}

				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

				if(json==null){
					$("#dnyCustCity").val("");
					$("#dnyCustState").val("");
				}else{
					$("#dnyCustCity").val(json.district);
					$("#dnyCustState").val(json.stateInString);
					$("#dnyPinCode").val(pincode);
			//		$("#zip").val(pincode+"-"+json.district+"-"+json.stateInString);
				//	$("#zipCode,#pinNo").val(pincode);
					$("#dnyPinCode").addClass("input-correct").removeClass("input-error");
					$("#dny-cust-zip-req").hide();
					
				}	
				 
			},
			error: function noData() {
				bootbox.alert("No data found");
			}
        }); 
         return false;
    }
});

function split2(val) {
    return val.split(/,\s*/);
}

function extractLastRW(term) {
    return split2(term).pop();
}

function dny_validateGSTINWithRegex(gstin){
	if(dny_GstinNumRegex.test(gstin) === true){				
		$("#dnyCustGstId").addClass("input-correct").removeClass("input-error");
		 $("#dny-custGstId-req").hide();
		 $("#dny-custGstId-req").hide();
		 return true;
	}
	if(dny_GstinNumRegex.test(gstin) !== true){
		$("#dnyCustGstId").addClass("input-error").removeClass("input-correct");
		 $("#dny-custGstId-req").show();
		 return false;
	}
	
}

function dny_getStateByGstinNumber(gstinNumber){
	$("#dnyCustGstinState").empty();
	$.ajax({
		url : "getStateByGstinNumber" ,
		type : "post",
		headers: {_csrf_token : $("#_csrf_token").val()},
		data : {"id":gstinNumber},
		dataType : "json",
		async : false,
		success : function(response,fTextStatus,fRequest) {
			
			if (isValidSession(response) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}

			if(isValidToken(response) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			if(response==null || response == ''){
				$("#dnyCustGstinState").val("");
			}else{
				$("#dnyCustGstinState").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId))
				
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		
		error: function(xhr, textStatus, errorThrown){
		bootbox.alert('request failed');
	      
	    }
	});
}

function dny_validateGstinAndStAddrState(){
	errFlag7 = validateTextField("dnyCustGstId","dny-custGstId-req",dny_blankMsg);
	if(!errFlag7){
	 errFlag7 = dny_compareTextFields("dnyCustState","dnyCustGstinState","dny-custState-err",dny_cmpMsg);
	}
	 
	return errFlag7;
}

function dny_validateCustomerName(){		
	errCustName = validateTextField("dnyCustName","dny-cust-name-req",dny_blankMsg);
	 if(!errCustName){
		 errCustName=validateFieldLength("dnyCustName","dny-cust-name-req",dny_lengthMsg,dny_length);
	 }
	 return errCustName;
	
}

function dny_validateCustomerContactNo(){
	errCustContactNo=validateRegexpressions("dnyContactNo","dny-contact-no-req",dny_regMsg,dny_contactNumRegex);
	return errCustContactNo;
}

function dny_validateCustomerEmail(){
	
	 errFlag = validateRegexpressions("dnyCustEmail","dny-cust-email-format",dny_regMsg,dny_emailRegex);
	 return errFlag;

}

function dny_validateCustGstId(){
	errCustGstin = validateTextField("dnyCustGstId","dny-custGstId-req",dny_blankMsg);
	if(!errCustGstin){
		errCustGstin = validateRegexpressions("dnyCustGstId","dny-custGstId-req",dny_regMsg,dny_GstinNumRegex);
	}
    return errCustGstin;
}

function dny_validateAddress(){
	
	errCustAddr1 = validateTextField("dnyCustAddress","dny-address-req",dny_blankMsg);
	if(!errCustAddr1){
		errCustAddr1=validateFieldLength("dnyCustAddress","dny-address-req",dny_lengthMsg,dny_length);
	 }
	 return errCustAddr1;
}

function dny_validGstinStateCode(){
   gstinStateValid=validateGstinStateCode("dnyCustGstId","dny-custGstId-req",dny_gstinStateErrMsg);
 
   return gstinStateValid;
}

function dny_validateCustPin(){
	
	errCustPIN = validateTextField("dnyPinCode","dny-cust-zip-req",dny_blankMsg);
	 if(!errCustPIN){
		 errCustPIN = validateFieldLength("dnyPinCode","dny-cust-zip-req",dny_lengthMsg,dny_pinCodeLength);
	 }
	 return errCustPIN;
	
}

function dny_validateCustCity(){		
	errCustcity = validateTextField("dnyPinCode","dny-cust-zip-req",dny_pinSelMsg);
	return errCustcity;
}

function dny_validateCustState(){		
	errCustState = validateTextField("dnyPinCode","dny-cust-zip-req",dny_pinSelMsg);
	 return errCustState;
}

function dny_compareTextFields(id1, id2, spanid, msg){
	var result=false;
	if ($("#"+id1).val() === $.trim($("#"+id2).text())){
		 $("#"+spanid).hide();
		 result= false;
	 } else {
		 $("#"+spanid).text(msg);
		 $("#"+spanid).show();
		 result= true;
	 }
	 return result;
}

function showCustomerAddPageHeader(){
	$("#generateInvoicePreviewPageHeader").css("display","none");
	$("#generateInvoiceDefaultPageHeader").css("display","none");
	$("#generateInvoiceCustomerEmailPageHeader").css("display","block");
}


function clearCustomerFieldsInInvoice(){
	//reset customer - start
	 $("#customer_name").val("");
	 $("#customer_contactNo").val("");
	 $("#customer_id").val("");
	 $("#customer_custAddress1").val("");
	 $("#customer_custCity").val("");
	 $("#customer_custState").val("");
	 $("#customer_custStateCode").val("");
	 $("#customer_custStateCodeId").val("");
	 $("#customer_custType").val("");
	 $("#customer_custStateId").val("");
	 $("#customer_custGstId").val("");
	 $("#customer_custEmail").val("");
	 $("#customer_place").val("");
	 $("#customer_country").val("");
	 clearCustomerFormFields();
	 //reset customer - end
	
}

function clearCustomerFormFields(){
	$("#dnyCustName").val("");
	$("#dnyContactNo").val("");
	$("#dnyCustEmail").val("");
	$("#dnyCustGstId").val("");
	$("#dnyCustGstinState").val("");
	$("#dnyPinCode").val("");
	$("#dnyCustCity").val("");
	$("#dnyCustState").val("");
	$("#dnyCustCountry").val("India");
	$("#dnyCustAddress").val("");
}