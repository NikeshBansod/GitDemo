var serviceTaxRateJson = '';

var openingStoresRowNum = 0;
var dynamicallyAddedServiceJson = '';

var dny_ser_length = 2;
var dny_ser_lengthMsg = "Minimum length should be ";
var dny_ser_regMsg = "Service Rate should be in proper format";
var dny_ser_igstMsg = "Rate of tax (%) should be in proper format";
var dny_ser_currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var dny_ser_percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var dny_ser_serviceIgstMsg='Rate of Tax for service should me less than 100';
var dynamicServiceExists = '';

$(document).ready(function(){
	
	loadUnitOfMeasurement();
	loadRateOfServiceTax();
	serviceLoadAdvolCessRate();
	serviceLoadNonAdvolCessRate();
	serviceLoadUserGstn();
	
	dnyGstinOpeningStockServiceTable = $('#dny-service-gstin-openingstock').DataTable({
		 rowReorder: {
		        selector: 'td:nth-child(2)'
		 },
		 responsive: true,
		 searching: false,
		 /*bPaginate: false,*/
		 bLengthChange: false,
	});
	setDataInOpeningStockService();
	
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
		var errsacUOM = false;//dnySacUOM();
		var errOtherUOM = false;
		if($("#dny-unitOfMeasurement").val()=="OTHERS"){
			 errOtherUOM=validateOtherUOM();
			 $("#dny-tempUom").val($("#dny-unitOfMeasurement").val()+'-'+'('+$("#dny-otherUOM").val()+')');
		}
		var errsacServiceRate = dnySacServiceRate();
		var errServiceIGST = dnySacServiceIgst();
		dynamicServiceExists = checkIfServiceAlreadyExists($("#dny-service-name").val());
		
		if( (errsacdesc) || (errsacCode) || (errsacServiceName) || (errsacUOM) || (errOtherUOM)|| (errsacServiceRate)|| (errServiceIGST) || (dynamicServiceExists)){
			 e.preventDefault();	
		}else{
			var errCheckDoctypeAndTaxRate = checkDoctypeAndTaxRateInServices();
			if(!errCheckDoctypeAndTaxRate){
				callPostAddService();
				e.preventDefault();
			}	
		}
		
		if((errsacdesc) || (errsacCode) ){
			 focusTextBox("dny-search-sac");
		} else if((errsacServiceName || dynamicServiceExists) ){
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
	
	$("#dnyServiceCancelBtn,#dny-service-cancel-link").click(function(e){
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
	
	$('#dny-service-os-gstin').on("change", function() {
    	var gstinSelectedId = $('#dny-service-os-gstin').val();	
    	if(gstinSelectedId != ''){
    		$('#dny-service-os-gstin').addClass("input-correct").removeClass("input-error");
    		$('#opening-stock-service-gstin-req').hide();
    		$("#dny-service-os-locationStore").empty();
    		$.each(userGstinsJson,function(i,value) {
    			if(value.id == gstinSelectedId ){
    				if(value.gstinLocationSet.length == 1){
    					$.each(value.gstinLocationSet,function(i,value2) {
    						$("#dny-service-os-locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id).attr('selected','selected'));
    						$("#dny-service-os-StoreId").val(value.gstinStore);
    					});
    					
    				}else{
    					
    					$("#dny-service-os-locationStore").append('<option value="">Select</option>');
    					$.each(value.gstinLocationSet,function(i,value2) {
    						$("#dny-service-os-locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
    					});
    				}	
    			}
    		});
    		
    		
    	}else{
    		$('#opening-stock-service-gstin-req').show();
       	  	$('#dny-service-os-gstin').addClass("input-error").removeClass("input-correct");
    	}     
    });
	
	$("#dny-service-os-openingStockCancelBtn").on( "click", function(e){
		resetOpeningStockServiceFormValues();
	});
	
	$("#dny-service-os-openingStockSaveBtn").on( "click", function(e){
		var isValidationDone = false;
		var openingStockGstin = validateSelectField("dny-service-os-gstin","opening-stock-service-gstin-req");
		var openingStockLocation = validateSelectField("dny-service-os-locationStore","opening-stock-service-location-req");
		
		
		if((openingStockGstin) || (openingStockLocation)){
			e.preventDefault();
		}else{
			var openingStockGstinText = $("#dny-service-os-gstin").find("option:selected").text();
			var openingStockLocationText = $("#dny-service-os-locationStore").find("option:selected").text();
			if(checkIfStoreIsAddedForGstinAndLocation()){
				e.preventDefault();
				bootbox.alert("WARNING !!!  GSTIN-"+openingStockGstinText+" and Store-"+openingStockLocationText+" is already added");
			}else{
				constructDynamicDivForStores();
				e.preventDefault();
			}
			
		}
		
	});
	
	$("#dny-service-name").blur(function(){
		var service = $("#dny-service-name").val();
		if(service != ''){
			checkIfServiceAlreadyExists(service);
		}
	});
	
	
	
});

$("#search-service-autocomplete").autocomplete({
    source: function (request, response) {
        $.getJSON("getServiceNameList", {
            term: extractLastRW(request.term),
            gNo : ($("#gstnStateId option:selected").text().split('[')[0]).trim(),
            location :  $('select#location option:selected').val(),
            documentType : $("#selectedDocType").val()
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
        
        dynamicallyAddedServiceJson = fetchDynamicallyAddedService($("#locationStorePkId").val(),value.trim());
		
		if(dynamicallyAddedServiceJson){
			changeServiceNameAsPerAutoComplete(dynamicallyAddedServiceJson);
			$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
    		$("#dny-service-no-records-found").hide();
    		$("#search-ser-prod-autocomplete-csv-id").hide();
		}
	
       /*  $.ajax({
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
	        		$("#search-ser-prod-autocomplete-csv-id").hide();
				}
				 
			},
			error: function noData() {
				bootbox.alert("No data found");
			}
        }); */
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
						$("#dny-serviceIgst-check").val(value.taxRate);
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
	$("#dny-ser-name").hide('');
	dynamicServiceExists = false;
	loadUnitOfMeasurement();
	$("#dny-otherUOM").val('');
	$("#dny-tempUom").val('');
	$("#dny-serviceRate").val('');
	loadRateOfServiceTax();
	dynamicallyAddedServiceJson = '';
	$("#toggleOpeningServiceStock").html('');
}

function callPostAddService(){
	$("#dnyServiceProductDiv").hide();
	$('.loader').show();
	var inputData = generateServiceinputJson();
	$.ajax({
		url : "serviceSaveAjax",
		method : "post",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
		data : JSON.stringify(inputData),
		contentType : "application/json",
		dataType : "json",
		async : false,
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
				 serviceJson = fetchDynamicallyAddedService($("#locationStorePkId").val(),$("#dny-service-name").val());
				 console.log(JSON.stringify(serviceJson));
				 
				 if(serviceJson){
					changeServiceNameAsPerAutoComplete(serviceJson);
					$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-service-no-records-found").hide();
				 }
				 
				 //set service data - end 

				$('.loader').hide();
				bootbox.alert(json.message, function() {
					clearServiceFormFields();
					$("#dnyCustomerDiv").hide();
					$("#previewInvoiceDiv").hide(); 
					$("#customerEmailDiv").hide();
					
					$("#addInvoiceDiv").show();
					$('html, body').animate({
					      scrollTop: $("#callOnEditId").offset().top
					}, -10);
					showDefaultPageHeader();
				});
			}
			
			if(json.status == 'FAILURE'){
				$('.loader').hide();
				bootbox.alert(json.message, function() {
					$("#dnyServiceProductDiv").show();
				});
				
			}
			
			if(json.response == 'accessDeny'){
				$('.loader').hide();
				bootbox.alert("Data is been manipulated.", function() {
					
						window.location.href = getCustomLogoutPage();
						return;
				});
			}
			
			
			if(json.response == 'serverError'){
				$('.loader').hide();
				bootbox.alert("Error occured while adding service.", function() {
					    $("#dnyServiceProductDiv").show();
						window.location.href = 'home#invoice';
						return;
				});
			}
         },
        error:function(data,status,er) { 
			$('.loader').hide();
        	bootbox.alert("Error occured while generating invoice.", function() {

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
	var serviceIgst = $('select#dny-serviceIgst option:selected').val();
	var serviceRate = $("#dny-serviceRate").val();
	var uom = "NA";//$('select#dny-unitOfMeasurement option:selected').val();
	var otherUom = $("#dny-tempUom").val();
	var sacPkId = $("#sacCodePkId").val();
	var advolCess = $('select#advolCess option:selected').val();
	var nonAdvolCess = $('select#nonAdvolCess option:selected').val();
	
	 //get add charges from list in javascript - Start 
	   var jsonObjectAC;
	   var acListArray = new Array();
	
	   dnyGstinOpeningStockServiceTable.rows().every( function ( index, tableLoop, rowLoop ) {
			var rowX = dnyGstinOpeningStockServiceTable.row(index).node();
		    var row = $(rowX);
		    jsonObjectAC = new Object();
		    jsonObjectAC.gstnId = $("#dny-service-opening_stock_gstin_val_"+index).val();
			jsonObjectAC.storeId = $("#dny-service-opening_stock_location_val_"+index).val();
			acListArray.push(jsonObjectAC);
	   });
	  //get add charges from list in javascript - End 
	
	var inputData = {
			"name": serviceName,
			"sacCode": sacCode,
			"sacDescription": sacDescription,
			"unitOfMeasurement": uom,
			"otherUOM": otherUom,
			"serviceRate": parseFloat(serviceRate),
			"serviceIgst": parseFloat(serviceIgst),
			"sacCodePkId": parseInt(sacPkId),
			"advolCess" : advolCess,
			"nonAdvolCess" : nonAdvolCess,
			"storesBean" :  JSON.parse(JSON.stringify(acListArray))
			
	};
	
	console.log("inputData : "+JSON.stringify(inputData));
	return inputData;
}

function serviceLoadAdvolCessRate(){
	 if(serviceAdvolCessRateJson == ''){
         $.ajax({
                 url:"getAdvolCessRate",     
                 type : "POST",
                 dataType: 'json',
                 async : false,
                 data:{_csrf_token : $("#_csrf_token").val()},
                 success:function(json,fTextStatus,fRequest) {
                       
                       if (isValidSession(json) == 'No') {
                                 window.location.href = getDefaultSessionExpirePage();
                                 return;
                       }

                       if(isValidToken(json) == 'No'){
                                 window.location.href = getCsrfErrorPage();
                                 return;
                       }
                       setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                       serviceAdvolCessRateJson = json;
                       
                 },
                 error: function (data,status,er) {
                           
                            getInternalServerErrorPage();   
                 }
         });
   }
   
   if(serviceAdvolCessRateJson != ''){
   	 $('#dny-service-advolCess').empty();
        $.each(serviceAdvolCessRateJson, function(i, value) {
             $('#dny-service-advolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));    
        }); 
   }
}

function serviceLoadNonAdvolCessRate(){
    if(serviceNonAdvolCessRateJson == ''){
          $.ajax({
                  url:"getNonAdvolCessRate", 
                  type : "POST",
                  dataType: 'json',
                  async : false,
                  data:{_csrf_token : $("#_csrf_token").val()},
                  success:function(json,fTextStatus,fRequest) {
                        
                        if (isValidSession(json) == 'No') {
                                  window.location.href = getDefaultSessionExpirePage();
                                  return;
                        }

                        if(isValidToken(json) == 'No'){
                                  window.location.href = getCsrfErrorPage();
                                  return;
                        }
                        setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                        serviceNonAdvolCessRateJson = json;
                        
                  },
                  error: function (data,status,er) {
                            
                             getInternalServerErrorPage();   
                  }
          });
    }
    
    if(serviceNonAdvolCessRateJson != ''){
    	$('#dny-service-nonAdvolCess').empty();
        $.each(serviceNonAdvolCessRateJson, function(i, value) {
             $('#dny-service-nonAdvolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));    
        });    
    }
}

function serviceLoadUserGstn(){
	//console.log("productLoadUserGstn - Start");
	if(userGstinsJson == ''){
		loadGenericUserGstin();
	}else{
		  json = userGstinsJson;
		  $("#dny-service-os-gstin").empty();
		  $("#dny-service-os-locationStore").empty();
		  if(userGstinsJson.length == 1){
			  $.each(userGstinsJson,function(i,value){
				  $("#dny-service-os-gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
				  
			  });
			  
	  			if(userGstinsJson[0].gstinLocationSet.length == 1){
	  				$.each(userGstinsJson[0].gstinLocationSet,function(i,value) {
					  $("#dny-service-os-locationStore").append($('<option>').text(value.gstinLocation).attr('value',value.id));
					  $("#dny-service-os-StoreId").val(value.gstinStore);
	  				});
	  			}
	  			else{
				     $("#dny-service-os-locationStore").append('<option value="">Select</option>');
					 $.each(userGstinsJson[0].gstinLocationSet,function(i,value){
						$("#dny-service-os-locationStore").append($('<option>').text(value.gstinLocation).attr('value',value.id));
					});
	  			}
				
			}else{
				$("#dny-service-os-gstin").append('<option value="">Select</option>');
				$.each(userGstinsJson,function(i,value) {
					$("#dny-service-os-gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
				});	
			}
	}
	
}

function resetOpeningStockServiceFormValues(){
	serviceLoadUserGstn();
	$("#dny-service-os-edtUniq").val("");
	$("#dny-service-os-openingStockSaveBtn").show();
	$("#dny-service-os-openingStockEditBtn").hide();
	
	$("#dny-service-os-gstin").show();
	$("#dny-service-os-gstin-hidden").val('');
	$("#dny-service-os-gstin-hidden").hide();
	$("#dny-service-os-locationStore").show();
	$("#dny-service-os-locationStore-hidden").val('');
	$("#dny-service-os-locationStore-hidden").hide();
}

function checkIfStoreIsAddedForGstinAndLocation(){
	var stockExists = false;
	
	var openingStockGstinValue = $('select#dny-service-os-gstin option:selected').val();
	var openingStockLocationValue = $('select#dny-service-os-locationStore option:selected').val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningServiceStock");
	   var totalACRecordNo = $addChgToggle.children().length;
	   if(totalACRecordNo == 0){
		   stockExists = false;
	   }else{
		   for (i = 0; i < totalACRecordNo; i++) { 
				 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
				 var num2 = $addChgToggle.children()[i].id.substring(index2);
				 num2 = num2.replace("_","-");
				 if(($("#opening_stock_service_gstin_val"+num2).val() == openingStockGstinValue) && ($("#opening_stock_service_location_val"+num2).val() == openingStockLocationValue)){
					 stockExists = true;
					 break;
				 }
		   }
		   
	   }
	  //get add charges from list in javascript - End 
	   return stockExists;
}

function constructDynamicDivForStores(){
	openingStoresRowNum++;
	var openingStockGstinText = $("#dny-service-os-gstin").find("option:selected").text();
	var openingStockGstinValue = $('select#dny-service-os-gstin option:selected').val();
	var openingStockLocationText = $("#dny-service-os-locationStore").find("option:selected").text();
	var openingStockLocationValue = $('select#dny-service-os-locationStore option:selected').val();
	
	//append dynamic div
	var $toggle = $("#toggleOpeningServiceStock");
	var recordNo = openingStoresRowNum;
	$toggle.append('<div class="cust-content" id="opening_stock_service_start_'+recordNo+'">'
			+'<div class="heading">'
                +'<div class="cust-con">'
                    +'<h1 id="opening_stock_service_location_'+recordNo+'">'+openingStockLocationText+'</h1>'
                +'</div>'
                +'<div class="cust-edit">'
                    +'<div class="cust-icon">'
                    	+'<a href="#" onclick="javascript:remove_opening_stock_service_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
                    +'</div>'
                +'</div>'
            +'</div>'
            +'<div class="content">'
                +'<div class="cust-con">'
                    +'<p id="opening_stock_service_gstin_'+recordNo+'" >GSTIN : '+openingStockGstinText+' </p>'
                +'</div>'
                +'<input type="hidden" id="opening_stock_service_gstin-'+recordNo+'" name="" value="'+openingStockGstinText+'">'
                +'<input type="hidden" id="opening_stock_service_gstin_val-'+recordNo+'" name="" value="'+openingStockGstinValue+'">'
                +'<input type="hidden" id="opening_stock_service_location-'+recordNo+'" name="" value="'+openingStockLocationText+'">'
                +'<input type="hidden" id="opening_stock_service_location_val-'+recordNo+'" name="" value="'+openingStockLocationValue+'">'
                
                +'</div>'
		+'</div>');
	openCloseAccordionForDynamicService(recordNo);
	resetOpeningStockServiceFormValues();
}

function openCloseAccordionForDynamicService(rowNum){
	var currId = "/"+rowNum;
	//alert("accordionsId ->"+accordionsId);
	if(dynamicServiceAccordionsId.includes(currId)){
		
	}else{
		$("#opening_stock_service_start_"+rowNum+" .content").hide();
		$("#opening_stock_service_start_"+rowNum+" .heading").click(function () {
			$(this).next(".content").slideToggle();
		});	
		dynamicServiceAccordionsId = dynamicServiceAccordionsId +","+currId;
	}
	
}

function remove_opening_stock_service_row(recordNo){
	bootbox.confirm("Are you sure you want to remove ?", function(result){
		 if (result){
			 $('#opening_stock_service_start_'+recordNo).remove();
			 dynamicServiceAccordionsId = dynamicServiceAccordionsId.replace(",/"+recordNo, "");
		 }
	});	 
	
}

function checkIfInvoiceGstinLocationExistsInChooseStores(){
	var stockExists = false;
	var userLocation = $("#locationStorePkId").val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningServiceStock");
	   var totalACRecordNo = $addChgToggle.children().length;
	  /* var jsonObjectAC;
	   var acListArray = new Array();*/
	   if(totalACRecordNo == 0){
		   stockExists = false;
	   }else{
		   for (i = 0; i < totalACRecordNo; i++) { 
			   //Start
				 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
				 var num2 = $addChgToggle.children()[i].id.substring(index2);
				 num2 = num2.replace("_","-");
				 if($("#opening_stock_service_location_val"+num2).val() == userLocation){
					 stockExists = true;
					 break;
				 }
				//End
		   }
		   
	   }
	  //get add charges from list in javascript - End 
	   return stockExists;
}

function checkDoctypeAndTaxRateInServices(){
	var isErrorInAddingService = true;
	var documentType = $("#selectedDocType").val();
	var serviceTaxRate = $("#dny-serviceIgst-check").val();
	if(documentType == 'invoice' || documentType == 'rcInvoice' || documentType == 'eComInvoice' ){
		if(serviceTaxRate > 0){
			isErrorInAddingService = false;
		}else{
			bootbox.alert("You have selected Document Type as "+documentType +" and the selected SACCode has TaxRate <= 0. Please select other SACCode which has TaxRate > 0.");
		}
	}else if(documentType == 'billOfSupply' || documentType == 'eComBillOfSupply'){
		if(serviceTaxRate == 0){
			isErrorInAddingService = false;
		}else{
			bootbox.alert("You have selected Document Type as "+documentType +" and the selected SACCode has TaxRate > 0. Please select other SACCode which has TaxRate = 0.");
		}
	}
	return isErrorInAddingService;
}

function fetchDynamicallyAddedService(storeId,productName){
	var resp = '';
	  $.ajax({
          url:"getManageServiceByServiceNameAndStoreId",     
          type : "POST",
          data : {"storeId": storeId , "serviceName":productName,_csrf_token : $("#_csrf_token").val()},
          dataType: 'json',
          async : false,
          success:function(json,fTextStatus,fRequest) {
                
                if (isValidSession(json) == 'No') {
                          window.location.href = getDefaultSessionExpirePage();
                          return;
                }

                if(isValidToken(json) == 'No'){
                          window.location.href = getCsrfErrorPage();
                          return;
                }
                setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                dynamicallyAddedServiceJson = json;
                resp = json;
                
                
          },
          error: function (data,status,er) {
                    
                     getInternalServerErrorPage();   
          }
	  });
	return resp;
}

function setDataInOpeningStockService(){
	if(gstinsInventoryJson == ''){
		loadGstinsInventory();
	}
	dnyGstinOpeningStockServiceTable.clear().draw();
	counter = 0;
	$.each(gstinsInventoryJson,function(i,value){
		var gstinNumber = value.gstinNo;
		var gstinId = value.id;
		$.each(value.gstinLocationSet,function(i,value2){	
			
			dnyGstinOpeningStockServiceTable.row.add($(
		 			'<tr id="'+counter+'" >'
		 				+'<td>'+(counter+1)+'<input type="hidden" class="xCount_'+counter+'" value="'+counter+'"></td>'
		 				+'<td>'+gstinNumber+'<input type="hidden" id="dny-service-opening_stock_gstin_val_'+counter+'" value="'+gstinId+'"></td>'
		 				+'<td>'+value2.gstinLocation+'<input type="hidden" id="dny-service-opening_stock_location_val_'+counter+'" value="'+value2.id+'"></td>'
		 			+'</tr>'					 	
			)).draw();
			counter++;
	      });
	   
    });
}

function checkIfServiceAlreadyExists(service){
	$.ajax({
		url : "checkIfServiceExists",
		type: 'POST',
		dataType : "json",
		async : false,
		data : {"service":service,_csrf_token : $("#_csrf_token").val()},
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
				 $("#dny-service-name").addClass("input-error").removeClass("input-correct");
				 $("#dny-ser-name").text('service Name  - '+service+' already Exists for your Organization');
				 $("#dny-ser-name").show();
				 dynamicServiceExists = true;
			}else{
				if(service.length > 0){
				 $("#dny-service-name").addClass("input-correct").removeClass("input-error");
				 $("#dny-ser-name").hide();
				 dynamicServiceExists = false;
				}
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
    });
	$('.loader').fadeOut("slow"); 
	return dynamicServiceExists;
}

