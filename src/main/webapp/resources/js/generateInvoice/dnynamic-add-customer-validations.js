

$(document).ready(function(){
	
	$("#dnyAddNewCustomer").click(function(e){
		$("#customer_name").addClass("input-correct").removeClass("input-error");
		$("#empty-message").hide();
		showCustomerAddPageHeader();
		
		
		$("#addInvoiceDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		$('#dnyCustomerDiv').scrollTop($(this).position().top);
		
		$("#dnyCustomerDiv").show();
	});
	
	$("#dnyCustCancelBtn").click(function(e){
		clearCustomerFieldsInInvoice();
		$("#dnyCustomerDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		
		$("#addInvoiceDiv").show();
		showDefaultPageHeader();
		
	});
	
	
	$("#dnyCustSubmitBtn").click(function(e){
		var reqAddr = false;
		var errFlag7 = false;
		var errCustName = false;
		var errCustContactNo = false;
		var errCustomerEmail = false;
		var errCustGstin = false;
		var errCustAddr = false;
		var gstinStateValid = false;
		var errCustPIN = false;
		var errCustcity = false;
		var errCustState = false;
		
		if($("#Individual").is(":checked")){
			$("#custGstId").val('');
			$("#custGstinState").val('');
			errFlag7=false;
			reqAddr = true;	
	    } else {
	    	reqAddr = false;
	    	errFlag7 = dny_validateGstinAndStAddrState();
	    }
		
		errCustName = dny_validateCustomerName();
		errCustContactNo = dny_validateCustomerContactNo();
		errCustomerEmail = dny_validateCustomerEmail();
		if(reqAddr != true){
			errCustGstin= dny_validateCustGstId();
			errCustAddr = dny_validateAddress();	
			gstinStateValid = dny_validGstinStateCode();
		
		}
		
		errCustPIN = dny_validateCustPin();
		errCustcity = dny_validateCustCity();
		errCustState = dny_validateCustState();
		
		
		if((errCustName) || (errCustContactNo) || (errCustomerEmail) || (errCustGstin) || (errCustAddr) || (gstinStateValid)
				|| (errCustPIN) || errCustcity || (errCustState) || (errFlag7)){
			e.preventDefault();
		}else{
			callPostMethodAfterSuccessfulSubmit();
		}
		
		if((errCustName)){
			 focusTextBox("dnyCustName");
		}else if((errCustContactNo) ){
			 focusTextBox("dnyContactNo");
		}else if((errCustomerEmail) ){
			 focusTextBox("dnyCustEmail");
		}else if((errCustGstin)){
			 focusTextBox("dnyCustGstId");
		}else if((errCustAddr) ){
			 focusTextBox("dnyCustAddress");
		}else if((errCustPIN) || (errCustcity) || (errCustState)){
			 focusTextBox("dnyPinCode");
		}
	});
	
});

function callPostMethodAfterSuccessfulSubmit(){
	$('#loadingmessage').show();
	
	var inputData = generateCustomerInputJson();
	$.ajax({
		url : "addCustomerDynamically",
		method : "post",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
		data : JSON.stringify(inputData),
		contentType : "application/json",
		dataType : "json",
		async : true,
		success:function(json,fTextStatus,fRequest){
			//alert(json);
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
				//alert("Invalid Token");
			}
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			if(json.status == 'SUCCESS'){
				
				//set customer - start
				customerJson = json.customerDetails;
				 $("#customer_name").val("["+customerJson.contactNo+"] - "+customerJson.custName);
				 $("#customer_contactNo").val(customerJson.contactNo);
				 $("#customer_place").val(customerJson.custAddress1+","+customerJson.custCity);//mandatory
				 $("#customer_country").val(customerJson.custState);//mandatory
				 $("#customer_id").val(customerJson.id);//mandatory
				 $("#customer_custAddress1").val(customerJson.custAddress1);//optional
				 //$("#customer_custAddress2").val(json.custAddress2);//optional
				 $("#customer_custCity").val(customerJson.custCity);//optional
				 $("#customer_custState").val(customerJson.custState);//optional
				 $("#customer_custType").val(customerJson.custType);
				 var stateRespone = getStateCode(customerJson.custState);
				 var values = stateRespone.split('{--}');
				 var stateCode = values[0];
				 var stateCodeId = values[1];
				 var stateId = values[2];
				 $("#customer_custStateCode").val(stateCode);
				 $("#customer_custStateCodeId").val(stateCodeId);
				 $("#customer_custStateId").val(stateId);
				 $("#customer_custGstId").val(customerJson.custGstId);
				 $("#customer_custEmail").val(customerJson.custEmail);
				 //getPoDetails(json.id);
				 autoPopulateStateList(stateId);
				 
				 //set customer - end
				
				bootbox.alert("Customer saved Successfully.", function() {
					clearCustomerFormFields();
					$("#dnyCustomerDiv").hide();
					$("#previewInvoiceDiv").hide(); 
					$("#customerEmailDiv").hide();
					$("#addInvoiceDiv").show();
					showDefaultPageHeader();
					$('#loadingmessage').hide();
					
					
				});
			}
			
			if(json.status == 'FAILURE'){
				bootbox.alert(json.response, function() {
					$('#loadingmessage').hide();
				});
				
			}
			
			if(json.response == 'accessDeny'){
				bootbox.alert("Data is been manipulated.", function() {
					
						$('#loadingmessage').hide();
						window.location.href = getCustomLogoutPage();
						return;
				});
			}
			
			
			if(json.response == 'serverError'){
				bootbox.alert("Error occured while adding customer.", function() {
						$('#loadingmessage').hide();
						window.location.href = 'home#invoice';
						return;
				});
			}
         },
        error:function(data,status,er) { 
        	bootbox.alert("Error occured while generating invoice.", function() {

					$('#loadingmessage').hide();
					window.location.href = 'home#invoice';
					return;
			});
         }
	}); 
		
}

function generateCustomerInputJson(){
	var custName = $("#dnyCustName").val();
	var custType = $('input[name=dnyCustType]').filter(':checked').val();
	var custEmail = $("#dnyCustEmail").val();
	var contactNo = $("#dnyContactNo").val();
	var custAddress1 = $("#dnyCustAddress").val();
	var pinCode = $("#dnyPinCode").val();
	var custCity = $("#dnyCustCity").val();
	var custState = $("#dnyCustState").val();
	var custGstId = $("#dnyCustGstId").val();
	var custCountry = $("#dnyCustCountry").val();
	var custGstinState = $("#dnyCustGstinState").val(); //$('select#dnyCustGstinState option:selected').val()
	
	
	var inputData = {
			"custName": custName,
			"custType": custType,
			"custEmail": custEmail,
			"contactNo": contactNo,
			"custAddress1": custAddress1,
			"pinCode": parseInt(pinCode),
			"custCity": custCity,
			"custState": custState,
			"custGstId": custGstId,
			"custCountry": custCountry,
			"custGstinState": custGstinState
			
			};
	console.log("inputData : "+JSON.stringify(inputData));
	
	return inputData;
}