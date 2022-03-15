var productTaxRateJson = '';

var dny_prod_length = 2;
var dny_prod_lengthMsg = "Minimum length should be ";
var dny_prod_regMsg = "Goods Rate should be in proper format";
var dny_prod_igstMsg = "Rate of tax (%) should be in proper format";
var dny_prod_currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var dny_prod_percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var dny_prod_productIgstMsg='Rate of Tax for service should me less than 100';

$(document).ready(function(){
	
	loadUnitOfMeasurementForProducts();
	loadRateOfProductTax();
	
	$("#dnyProductSubmitBtn").click(function(e){
		
		var errHsndesc = dnyHsnDesc();
		var errHsnCode = dnyHsnCode();
		var errhsnProductName = dnyHsnProductName();
		var errhsnUOM = dnyHsnUOM();
		var errOtherUOM = false;
		if($("#dny-product-unitOfMeasurement").val()=="OTHERS"){
			 errOtherUOM=validateProductOtherUOM();
			 $("#dny-product-tempUom").val($("#dny-product-unitOfMeasurement").val()+'-'+'('+$("#dny-product-otherUOM").val()+')');
		 }
		var errhsnProductRate = dnyHsnProductRate();
		var errhsnProductIgst = dnyHsnProductIgst();
		
		if((errHsndesc) || (errHsnCode) || (errhsnProductName) || (errhsnUOM) || (errOtherUOM) || (errhsnProductRate) || (errhsnProductIgst)){
			e.preventDefault();	
		}else{
			callPostAddProduct();
		}
		
		if((errHsndesc) || (errHsnCode) ){
			focusTextBox("dny-search-hsn");
		} else if((errhsnProductName) ){
			focusTextBox("dny-product-name");
		} else if(errhsnUOM){
			focusTextBox("dny-product-unitOfMeasurement");
		}else if(errOtherUOM){
			focusTextBox("dny-product-otherUOM");
		}else if(errhsnProductRate){
			focusTextBox("dny-productRate");
		}else if(errhsnProductIgst){
			focusTextBox("dny-productIgst");
		}
		
		
	});
	
	$("#dnyProductCancelBtn").click(function(e){
		clearProductFormFields();
		$("#dnyCustomerDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		$("#dnyServiceProductDiv").hide();
		$("#addInvoiceDiv").show();
		$('html, body').animate({
		      scrollTop: $("#callOnEditId").offset().top
		}, 1000);
		showDefaultPageHeader();
		$("#generateInvoiceProductDetailsPageHeader").hide();
	});
	
	$("#dny-search-hsn").on("input keyup click", function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		if ( $("#dny-hsnDescription").val() === "" || $("#dny-hsnCode").val() === ""){
			$("#dny-search-hsn-req, #dny-prod-hsn-desc, #dny-prod-hsn-code").show();
			$("#dny-search-hsn").addClass("input-error").removeClass("input-correct");
		}
		
	});
	
	$("#dny-product-name").on("keyup input",function(){
		//this.value = removeWhiteSpace(this.value);
		this.value = this.value.replace(/[\\[]*$/, '');
	//	this.value = this.value.replace(/[^[a-zA-Z-, ]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		 if ($("#dny-product-name").val() === ""){
			 $("#dny-product-name").addClass("input-error").removeClass("input-correct");
			 $("#dny-prod-name").show();
			 
		 }
		 if ($("#dny-product-name").val() !== ""){
			 $("#dny-product-name").addClass("input-correct").removeClass("input-error");
			 $("#dny-prod-name").hide();
			 
		 } 
	 });
	
	$("#dny-product-divOtherUnitOfMeasurement").hide();
    $("#dny-product-unitOfMeasurement").change(function(){
		otherUnitOfMeasurementForProduct();
		
	 });
    
    $("#dny-product-unitOfMeasurement").on("keyup click",function(){
		 if ($("#dny-product-unitOfMeasurement").val() === ""){
			 $("#dny-product-unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#dny-prod-uom").show();
			 
		 }
		 if ($("#dny-product-unitOfMeasurement").val() !== ""){
			 $("#dny-product-unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#dny-prod-uom").hide();
			 
		 } 
	});
	
	
	$("#dny-product-unitOfMeasurement").change(function(){
		 if ($("#dny-product-unitOfMeasurement").val() === ""){
			 $("#dny-product-unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#dny-prod-uom").show();
			 
		 }
		 if ($("#dny-product-unitOfMeasurement").val() !== ""){
			 $("#dny-product-unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#dny-prod-uom").hide();
			 
		 } 
	});

	
	$("#dny-product-otherUOM").on("keyup input",function(){
		 if ($("#dny-product-otherUOM").val() === ""){
			 $("#dny-product-otherUOM").addClass("input-error").removeClass("input-correct");
			 $("#dny-product-otherOrgType-req").show();
			 
		 }
		 if ($("#dny-product-otherUOM").val() !== ""){
			 $("#dny-product-otherUOM").addClass("input-correct").removeClass("input-error");
			 $("#dny-product-otherOrgType-req").hide();
			 
		 } 
	});
	
	$("#dny-productRate").on("keyup input",function(){
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(dny_ser_currencyRegex.test($("#dny-productRate").val()) === true){
			$("#dny-prod-rate").hide();
			$("#dny-productRate").addClass("input-correct").removeClass("input-error");	
		}
		if(dny_ser_currencyRegex.test($("#dny-productRate").val()) !== true){
			$("#dny-prod-rate").text(dny_prod_regMsg);
			$("#dny-prod-rate").show();
			$("#dny-productRate").addClass("input-error").removeClass("input-correct");	
		}
	});
	
	$("#dny-productIgst").change(function(){
	//	this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(dny_ser_percentRegex.test($("#dny-productIgst").val()) === true){
			$("#dny-prod-igst").hide();
			$("#dny-productIgst").addClass("input-correct").removeClass("input-error");	
		}
		
		if(dny_ser_percentRegex.test($("#dny-productIgst").val()) !== true){
			$("#dny-prod-igst").text(dny_prod_igstMsg);
			$("#dny-prod-igst").show();
			$("#dny-productIgst").addClass("input-error").removeClass("input-correct");	
		}
	});
	
});


$("#search-product-autocomplete").autocomplete({
    source: function (request, response) {
        $.post("getProductNameList", {
            term: extractLastRW(request.term)
        }, function( data, status, xhr ){
        	response(data);
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		},"json");
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}
        
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#search-product-autocomplete").val();
    	//bootbox.alert"Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	
	        		 $("#search-product-autocomplete").addClass("input-error").removeClass("input-correct");
	        		 //$("#dny-empty-message").text("No results found for selected pin code : "+zipToShow);
	        		 $("#dny-product-no-records-found").show();
	                 $("#search-product-autocomplete").val("");
	        } else {
	            $("#dny-product-no-records-found").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
       
         $.ajax({
        	url : "getProductByProductName" ,
			type : "post",
			data : {"productName" : value.trim() ,_csrf_token : $("#_csrf_token").val()},
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
					$("#search-product-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-product-no-records-found").hide();
				}
				 
			},
			error: function noData() {
				bootbox.alert("No data found");
			}
        }); 
         return false;
    }
});

$( "#dny-search-hsn").autocomplete({
    source: function (request, response) {
        $.getJSON("getHSNCodeList", {
            term: extractLast(request.term)
        },  function( data, status, xhr ){
        	response(data);
		//	setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		});
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var hsnCode = (value.split('] - ')[0]).replace("[","").trim();
        var hsnDescription = (value.split('] - ')[1]).trim();
        
        $("#dny-hsnCode").val(hsnCode);
        $("#dny-hsnCodeToShow").val(hsnCode);
        $("#dny-hsnDescriptionToShow").val(hsnDescription);
        $("#dny-hsnDescription").val(hsnDescription);
        
        $("#dny-search-hsn-req, #dny-prod-hsn-desc, #dny-prod-hsn-code").hide();
		$("#dny-search-hsn").removeClass("input-error");
       
        $.ajax({
			url : "getIGSTValueByHsnCode",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			data : { hsnCode : hsnCode, hsnDescription : hsnDescription, _csrf_token : $("#_csrf_token").val()},
			async : false,
			success : function(json,fTextStatus,fRequest) {
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
				$("#hsnCodePkId").val(json.id);
				if(productTaxRateJson == ''){
					loadRateOfProductTax();
				}
				$("#dny-productIgst").empty();
				$.each(productTaxRateJson,function(i,value) {
					if(json.igst==value.taxRate){
						$("#dny-productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate).attr('selected','selected')); 
						$("#dny-productIgst").addClass("input-correct").removeClass("input-error");
						$("#dny-prod-igst").hide();
					 }else{
						 $("#dny-productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
					 }
				}); 
			}
        }); 
    }

});

function loadUnitOfMeasurementForProducts(){
	$.ajax({
		url : "getUnitOfMeasurement",
		type : "POST",
		dataType : "json",
		data:{_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			
			$("#dny-product-unitOfMeasurement").empty();
			$("#dny-product-unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			$.each(json,function(i,value) {
				$("#dny-product-unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
			});
			
			
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	});
}

function dnyHsnDesc(){
	
	var errHsndesc = validateTextField("dny-hsnDescription","dny-prod-hsn-desc",blankMsg);
	 if(!errHsndesc){
		 errHsndesc=validateFieldLength("dny-hsnDescription","dny-prod-hsn-desc",dny_prod_lengthMsg,dny_prod_length);
	 }
 
	 return errHsndesc;
}

function dnyHsnCode(){
	 var errHsnCode = validateTextField("dny-hsnCode","dny-prod-hsn-code",blankMsg);
	 if(!(errHsnCode)){
		 errHsnCode = validateFieldLength("dny-hsnCode","dny-prod-hsn-code",dny_prod_lengthMsg,dny_prod_length);
	 }
		
	 return errHsnCode;
}

function dnyHsnProductName(){
	var errhsnProductName = validateTextField("dny-product-name","dny-prod-name",blankMsg);
	 if(!(errhsnProductName)){
		 errhsnProductName = validateFieldLength("dny-product-name","dny-prod-name",dny_prod_lengthMsg,dny_prod_length);
	 }
	
	 return errhsnProductName;
}

function otherUnitOfMeasurementForProduct(){
	if ($("#dny-product-unitOfMeasurement").val() === "OTHERS"){
		$("#dny-product-divOtherUnitOfMeasurement").show();
	}else{
		$("#dny-product-divOtherUnitOfMeasurement").hide();
		$("#dny-product-otherUOM").val("");
	}
}

function dnyHsnUOM(){
	var errhsnUOM = validateTextField("dny-product-unitOfMeasurement","dny-prod-uom",blankMsg);
	/* if(!errhsnUOM){
		 errhsnUOM = validateFieldLength("unitOfMeasurement","prod-uom",lengthMsg,length);
	 }*/

	 return errhsnUOM;
}

function validateProductOtherUOM(){
	var errOtherUOM = validateTextField("dny-product-otherUOM","dny-product-otherOrgType-req",blankMsg);
	 if(!errOtherUOM){
		 errOtherUOM=validateFieldLength("dny-product-otherUOM","dny-product-otherOrgType-req",dny_prod_lengthMsg,1);
	 }
	 
	 return errOtherUOM;
}

function dnyHsnProductRate(){
	
	var errhsnProductRate = validateTextField("dny-productRate","dny-prod-rate",blankMsg);
	if(!errhsnProductRate){
		errhsnProductRate=validateRegexpressions("dny-productRate","dny-prod-rate",dny_prod_regMsg,dny_prod_currencyRegex);
	 }
	return errhsnProductRate;
}

function dnyHsnProductIgst(){
	
	var errhsnProductIgst = validateTextField("dny-productIgst","dny-prod-igst",blankMsg);
	if(!errhsnProductIgst){
		errhsnProductIgst=validateRegexpressions("dny-productIgst","dny-prod-igst",dny_prod_productIgstMsg,dny_prod_percentRegex);
	 }
	return errhsnProductIgst;
}

function loadRateOfProductTax(){
	$.ajax({
		url : "getProductRateOfTaxDetails",
		type : "POST",
		dataType : "json",
		data:{_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#dny-productIgst").empty();
			$("#dny-productIgst").append('<option value="">Select Rate Of Tax</option>');
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			productTaxRateJson = json;
			
			$.each(json,function(i,value) {
				$("#dny-productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
			});
			
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	});
}

function clearProductFormFields(){
	$("#hsnCodePkId").val('');
	$("#dny-search-hsn").val('');
	$("#dny-hsnDescriptionToShow").val('');
	$("#dny-hsnDescription").val('');
	$("#dny-hsnCodeToShow").val('');
	$("#dny-hsnCode").val('');
	$("#dny-product-name").val('');
	loadUnitOfMeasurementForProducts();
	$("#dny-product-otherUOM").val('');
	$("#dny-product-tempUom").val('');
	$("#dny-productRate").val('');
	loadRateOfProductTax();
}

function callPostAddProduct(){
	$("#dnyServiceProductDiv").hide();
	$('#loadingmessage').show();
	var inputData = generateProductInputJson();
	$.ajax({
		url : "addProductDynamically",
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
				 productJson = json.product;
				// console.log(JSON.stringify(serviceJson));
				 
				 if(productJson){
					changeServiceNameAsPerAutoComplete(productJson);
					$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-service-no-records-found").hide();
				 }
				 
				 //set service data - end 
				
				bootbox.alert(json.response, function() {
					clearProductFormFields();
					$("#dnyCustomerDiv").hide();
					$("#previewInvoiceDiv").hide(); 
					$("#customerEmailDiv").hide();
					
					$("#addInvoiceDiv").show();
					$('html, body').animate({
					      scrollTop: $("#callOnEditId").offset().top
					},0);
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

function generateProductInputJson(){
	var hsnDescription = $("#dny-hsnDescription").val();
	var hsnCode= $("#dny-hsnCode").val();
	var productName = $("#dny-product-name").val();
	var uom = $('select#dny-product-unitOfMeasurement option:selected').val();
	var otherUom = $("#dny-product-tempUom").val();
	var productRate = $("#dny-productRate").val();
	var productIgst = $('select#dny-productIgst option:selected').val();
	var hsnPkId = $("#sacCodePkId").val();
	
	var inputData = {
			"name": productName,
			"hsnCode": hsnCode,
			"hsnDescription": hsnDescription,
			"unitOfMeasurement": uom,
			"otherUOM": otherUom,
			"productRate": parseFloat(productRate),
			"productIgst": parseFloat(productIgst),
			"hsnCodePkId": parseInt(hsnPkId)
	};
	
	console.log("inputData : "+JSON.stringify(inputData));
	return inputData;
}