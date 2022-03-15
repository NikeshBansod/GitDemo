var serviceTaxRateJson = '';

var dny_ser_length = 2;
var dny_ser_lengthMsg = "Minimum length should be ";
var dny_ser_regMsg = "Service Rate should be in proper format";
var dny_ser_igstMsg = "Rate of tax (%) should be in proper format";
var dny_ser_currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var dny_ser_percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var dny_ser_serviceIgstMsg='Rate of Tax for service should me less than 100';

$(document).ready(function(){
	
	loadUnitOfMeasurement();
	loadRateOfServiceTax();
	
	$("#dnyAddNewService,#dnyAddNewProduct").click(function(e){
		
		var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
		if(invoiceFor == 'Service'){
			$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
			$("#dny-service-no-records-found").hide();
			$("#dnyServiceFormDiv").show();
			$("#dnyProductFormDiv").hide();
		}else{
			$("#search-product-autocomplete").addClass("input-correct").removeClass("input-error");
			$("#dny-product-no-records-found").hide();
			$("#dnyProductFormDiv").show();
			$("#dnyServiceFormDiv").hide();
		}
		
		showCustomerAddPageHeader();
		$("#addInvoiceDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		$("#dnyCustomerDiv").hide();
		
		$('#dnyServiceProductDiv').scrollTop($(this).position().top);
		$("#dnyServiceProductDiv").show();
		
	});
	
	$("#dnyServiceSubmitBtn").click(function(e){
		
		var errsacdesc = dnySacDesc();
		var errsacCode = dnySacCode();
		var errsacServiceName = dnySacServiceName(); 
		var errsacUOM = dnySacUOM();
		var errOtherUOM = false;
		if($("#dny-unitOfMeasurement").val()=="OTHERS"){
			 errOtherUOM=validateOtherUOM();
			 $("#dny-tempUom").val($("#dny-unitOfMeasurement").val()+'-'+'('+$("#dny-otherUOM").val()+')');
		}
		var errsacServiceRate = dnySacServiceRate();
		var errServiceIGST = dnySacServiceIgst();
		
		if( (errsacdesc) || (errsacCode) || (errsacServiceName) || (errsacUOM) || (errOtherUOM)|| (errsacServiceRate)|| (errServiceIGST) /*|| (serviceExists) */){
			 e.preventDefault();	
		}else{
			callPostAddService();
		}
		
		if((errsacdesc) || (errsacCode) ){
			 focusTextBox("dny-search-sac");
		} else if((errsacServiceName) ){
			 focusTextBox("dny-service-name");
		} else if((errsacUOM) ){
			 focusTextBox("dny-unitOfMeasurement");
		} else if((errsacServiceRate)){
			 focusTextBox("dny-serviceRate");
		} else if((errServiceIGST)){
			 focusTextBox("dny-serviceIgst");
		} else if((errOtherUOM)){
			 focusTextBox("dny-otherUOM");
		}
		
		
		
	});
	
	$("#dnyServiceCancelBtn").click(function(e){
		clearServiceFormFields();
		$("#dnyCustomerDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		$("#dnyServiceProductDiv").hide();
		$("#addInvoiceDiv").show();
		$('html, body').animate({
		      scrollTop: $("#callOnEditId").offset().top
		}, 1000);
		showDefaultPageHeader();
		
	});
	
	 $("#dny-search-sac").on("input keyup click", function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		if ( $("#dny-sacDescription").val() === "" || $("#dny-sacCode").val() === ""){
			$("#dny-search-sac-req, #dny-ser-sac-desc, #dny-ser-sac-code").show();
			$("#dny-search-sac").addClass("input-error").removeClass("input-correct");
		}
		
	});
	 
	 $("#dny-service-name").on("keyup input",function(){
		//this.value = removeWhiteSpace(this.value);
		this.value = this.value.replace(/[\\[]*$/, '');
	//	this.value = this.value.replace(/[^[a-zA-Z-, ]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		 if ($("#dny-service-name").val() === ""){
			 $("#dny-service-name").addClass("input-error").removeClass("input-correct");
			 $("#dny-ser-name").show();
			 
		 }
		 if ($("#dny-service-name").val() !== ""){
			 $("#dny-service-name").addClass("input-correct").removeClass("input-error");
			 $("#dny-ser-name").hide();
			 
		 } 
	 }); 
	 
	 $("#dny-divOtherUnitOfMeasurement").hide();
     $("#dny-unitOfMeasurement").change(function(){
		otherUnitOfMeasurement();
		
	 });
     
     $("#dny-unitOfMeasurement").on("keyup click",function(){
		 if ($("#dny-unitOfMeasurement").val() === ""){
			 $("#dny-unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#dny-ser-uom").show();
			 
		 }
		 if ($("#dny-unitOfMeasurement").val() !== ""){
			 $("#dny-unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#dny-ser-uom").hide();
			 
		 } 
	});
	
	
	$("#dny-unitOfMeasurement").change(function(){
		 if ($("#dny-unitOfMeasurement").val() === ""){
			 $("#dny-unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#dny-ser-uom").show();
			 
		 }
		 if ($("#dny-unitOfMeasurement").val() !== ""){
			 $("#dny-unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#dny-ser-uom").hide();
			 
		 } 
	});

	
	$("#dny-otherUOM").on("keyup input",function(){
		 if ($("#dny-otherUOM").val() === ""){
			 $("#dny-otherUOM").addClass("input-error").removeClass("input-correct");
			 $("#dny-otherOrgType-req").show();
			 
		 }
		 if ($("#dny-otherUOM").val() !== ""){
			 $("#dny-otherUOM").addClass("input-correct").removeClass("input-error");
			 $("#dny-otherOrgType-req").hide();
			 
		 } 
	});
	
	$("#dny-serviceRate").on("keyup input",function(){
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(dny_ser_currencyRegex.test($("#dny-serviceRate").val()) === true){
			$("#dny-ser-rate").hide();
			$("#dny-serviceRate").addClass("input-correct").removeClass("input-error");	
		}
		if(dny_ser_currencyRegex.test($("#dny-serviceRate").val()) !== true){
			$("#dny-ser-rate").text(dny_ser_regMsg);
			$("#dny-ser-rate").show();
			$("#dny-serviceRate").addClass("input-error").removeClass("input-correct");	
		}
	});
	
	$("#dny-serviceIgst").change(function(){
	//	this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(dny_ser_percentRegex.test($("#dny-serviceIgst").val()) === true){
			$("#dny-service-igst").hide();
			$("#dny-serviceIgst").addClass("input-correct").removeClass("input-error");	
		}
		
		if(dny_ser_percentRegex.test($("#dny-serviceIgst").val()) !== true){
			$("#dny-service-igst").text(dny_ser_igstMsg);
			$("#dny-service-igst").show();
			$("#dny-serviceIgst").addClass("input-error").removeClass("input-correct");	
		}
	});
	
	
	
});

$("#search-service-autocomplete").autocomplete({
    source: function (request, response) {
        $.getJSON("getServiceNameList", {
            term: extractLastRW(request.term)
        }, function( data, status, xhr ){
        	response(data);
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		});
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}
        
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#search-service-autocomplete").val();
    	//bootbox.alert"Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	
	        		 $("#search-service-autocomplete").addClass("input-error").removeClass("input-correct");
	        		 //$("#dny-empty-message").text("No results found for selected pin code : "+zipToShow);
	        		 $("#dny-service-no-records-found").show();
	                 $("#search-service-autocomplete").val("");
	        } else {
	            $("#dny-service-no-records-found").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
       
         $.ajax({
        	url : "getManageServiceByServiceName" ,
			type : "post",
			data : {"serviceName" : value.trim() ,_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(json,fTextStatus,fRequest) {
				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}

				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

				if(json){
					changeServiceNameAsPerAutoComplete(json);
					$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-service-no-records-found").hide();
				}
				 
			},
			error: function noData() {
				bootbox.alert("No data found");
			}
        }); 
         return false;
    }
});

$( "#dny-search-sac").autocomplete({
    source: function (request, response) {
        $.getJSON("getSacCodeList", {
            term: extractLast(request.term)
        },  function( data, status, xhr ){
        	response(data);
		//	setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		});
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var sacCode = (value.split('] - ')[0]).replace("[","").trim();
        var sacDescription = (value.split('] - ')[1]).trim();
        
        $("#dny-sacCode").val(sacCode);
        $("#dny-sacCodeToShow").val(sacCode);
        $("#dny-sacDescriptionToShow").val(sacDescription);
        $("#dny-sacDescription").val(sacDescription);
        
        $("#dny-search-sac-req, #dny-ser-sac-desc, #dny-ser-sac-code").hide();
		$("#dny-search-sac").removeClass("input-error");
       
        $.ajax({
			url : "getIGSTValueBySacCode",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			data : { sacCode : sacCode, sacDescription : sacDescription, _csrf_token : $("#_csrf_token").val()},
			async : false,
			success : function(json,fTextStatus,fRequest) {
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
				$("#sacCodePkId").val(json.id);
				if(serviceTaxRateJson == ''){
					loadRateOfServiceTax();
				}
				$("#dny-serviceIgst").empty();
				$.each(serviceTaxRateJson,function(i,value) {
					if(json.igst==value.taxRate){
						$("#dny-serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate).attr('selected','selected')); 
						$("#dny-serviceIgst").addClass("input-correct").removeClass("input-error");
						$("#dny-service-igst").hide();
					 }else{
						 $("#dny-serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
					 }
				});
				
				
				
				 
			}
        }); 
    }

});

function loadUnitOfMeasurement(){
	$.ajax({
		url : "getUnitOfMeasurement",
		type : "POST",
		dataType : "json",
		data:{_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			
			$("#dny-unitOfMeasurement").empty();
			$("#dny-unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			$.each(json,function(i,value) {
				$("#dny-unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	});
		
}//end of loadUnitOfMeasurement method 

function dnySacDesc(){
	
	var errsacdesc = validateTextField("dny-sacDescription","dny-ser-sac-desc",blankMsg);
	 if(!(errsacdesc)){
		 errsacdesc=validateFieldLength("dny-sacDescription","dny-ser-sac-desc",dny_ser_lengthMsg,dny_ser_length);
	 }
 
	 return errsacdesc;
}

function dnySacCode(){
	var errsacCode = validateTextField("dny-sacCode","dny-ser-sac-code",blankMsg);
	 if(!(errsacCode)){
		 errsacCode = validateFieldLength("dny-sacCode","dny-ser-sac-code",dny_ser_lengthMsg,dny_ser_length);
	 }
		
	 return errsacCode;
}

function dnySacServiceName(){
	var errsacServiceName = validateTextField("dny-service-name","dny-ser-name",blankMsg);
	 if(!(errsacServiceName)){
		 errsacServiceName = validateFieldLength("dny-service-name","dny-ser-name",dny_ser_lengthMsg,dny_ser_length);
	 }
	
	 return errsacServiceName;
}

function otherUnitOfMeasurement(){
	if ($("#dny-unitOfMeasurement").val() === "OTHERS"){
		$("#dny-divOtherUnitOfMeasurement").show();
	}else{
		$("#dny-divOtherUnitOfMeasurement").hide();
		$("#dny-otherUOM").val("");
	}
	
}

function dnySacUOM(){
	var errsacUOM = validateTextField("dny-unitOfMeasurement","dny-ser-uom",blankMsg);
	/* if(!(errsacUOM)){
		 errsacUOM = validateFieldLength("unitOfMeasurement","ser-uom",lengthMsg,length);
	 }*/

	 return errsacUOM;
}

function validateOtherUOM(){
	var errOtherUOM = validateTextField("dny-otherUOM","dny-otherOrgType-req",blankMsg);
	 if(!errOtherUOM){
		 errOtherUOM=validateFieldLength("dny-otherUOM","dny-otherOrgType-req",dny_ser_lengthMsg,1);
	 }
	 
	 return errOtherUOM;
}

function dnySacServiceRate(){
	
	var errsacServiceRate = validateTextField("dny-serviceRate","dny-ser-rate",blankMsg);
	if(!errsacServiceRate){
		errsacServiceRate=validateRegexpressions("dny-serviceRate","dny-ser-rate",dny_ser_regMsg,dny_ser_currencyRegex);
	 }
	return errsacServiceRate;
}

function dnySacServiceIgst(){
	
	errServiceIGST = validateTextField("dny-serviceIgst","dny-service-igst",blankMsg);
		if(!errServiceIGST){
			errServiceIGST=validateRegexpressions("dny-serviceIgst","dny-service-igst",dny_ser_serviceIgstMsg,dny_ser_percentRegex);
		 }
		return errServiceIGST;
}

function loadRateOfServiceTax(){
	  $.ajax({
			url : "getServiceRateOfTaxDetails",
			type : "POST",
			dataType : "json",
			data:{_csrf_token : $("#_csrf_token").val()},
			async : false,
			success:function(json,fTextStatus,fRequest){
				$("#dny-serviceIgst").empty();
				$("#dny-serviceIgst").append('<option value="">Select Rate Of Tax</option>');
				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				serviceTaxRateJson = json;
				
				$.each(json,function(i,value) {
					$("#dny-serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
				});
				
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		});
}

function clearServiceFormFields(){
	$("#sacCodePkId").val('');
	$("#dny-search-sac").val('');
	$("#dny-sacDescriptionToShow").val('');
	$("#dny-sacDescription").val('');
	$("#dny-sacCodeToShow").val('');
	$("#dny-sacCode").val('');
	$("#dny-service-name").val('');
	loadUnitOfMeasurement();
	$("#dny-otherUOM").val('');
	$("#dny-tempUom").val('');
	$("#dny-serviceRate").val('');
	loadRateOfServiceTax();
}

function callPostAddService(){
	$("#dnyServiceProductDiv").hide();
	$('#loadingmessage').show();
	var inputData = generateServiceinputJson();
	$.ajax({
		url : "addServiceDynamically",
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
				 //set service data - start 
				 serviceJson = json.manageServiceCatalogue;
				// console.log(JSON.stringify(serviceJson));
				 
				 if(serviceJson){
					changeServiceNameAsPerAutoComplete(serviceJson);
					$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-service-no-records-found").hide();
				 }
				 
				 //set service data - end 
				
				bootbox.alert(json.response, function() {
					clearServiceFormFields();
					$("#dnyCustomerDiv").hide();
					$("#previewInvoiceDiv").hide(); 
					$("#customerEmailDiv").hide();
					
					$("#addInvoiceDiv").show();
					$('html, body').animate({
					      scrollTop: $("#callOnEditId").offset().top
					}, -10);
					showDefaultPageHeader();
					$('#loadingmessage').hide();
					
					
				});
			}
			
			if(json.status == 'FAILURE'){
				bootbox.alert(json.response, function() {
					$("#dnyServiceProductDiv").show();
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
				bootbox.alert("Error occured while adding service.", function() {
					    $("#dnyServiceProductDiv").show();
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

function generateServiceinputJson(){
	var sacDescription = $("#dny-sacDescription").val();
	var sacCode= $("#dny-sacCode").val();
	var serviceName = $("#dny-service-name").val();
	var uom = $('select#dny-unitOfMeasurement option:selected').val();
	var otherUom = $("#dny-tempUom").val();
	var serviceRate = $("#dny-serviceRate").val();
	var serviceIgst = $('select#dny-serviceIgst option:selected').val();
	var sacPkId = $("#sacCodePkId").val();
	
	var inputData = {
			"name": serviceName,
			"sacCode": sacCode,
			"sacDescription": sacDescription,
			"unitOfMeasurement": uom,
			"otherUOM": otherUom,
			"serviceRate": parseFloat(serviceRate),
			"serviceIgst": parseFloat(serviceIgst),
			"sacCodePkId": parseInt(sacPkId)
	};
	
	console.log("inputData : "+JSON.stringify(inputData));
	return inputData;
}

