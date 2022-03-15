var productTaxRateJson = '';
var productUnitOfMeasurementJson = '';

var openingStockRowNum = 0;
var dynamicallyAddedProductJson = '';

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
	productLoadAdvolCessRate();
	productLoadNonAdvolCessRate();
	productLoadUserGstn();
	
	dnyGstinOpeningstockProductTable = $('#dny-product-gstin-openingstock').DataTable({
		 rowReorder: {
		        selector: 'td:nth-child(2)'
		 },
		 responsive: true,
		 searching: false,
		 /*bPaginate: false,*/
		 bLengthChange: false,
	});
	
	setDataInOpeningStockProduct();
	
	$("#dnyProductSubmitBtn").click(function(e){
		
		var errHsndesc = dnyHsnDesc();
		var errHsnCode = dnyHsnCode();
		var errhsnProductName = dnyHsnProductName();
		var errhsnProductIgst = dnyHsnProductIgst();
		
		var errhsnProductRate = dnyHsnProductRate();
		var errhsnUOM = dnyHsnUOM();
		var errOtherUOM = false;
		if($("#dny-product-unitOfMeasurement").val()=="OTHERS"){
			 errOtherUOM = validateProductOtherUOM();
			 $("#dny-product-tempUom").val($("#dny-product-unitOfMeasurement").val()+'-'+'('+$("#dny-product-otherUOM").val()+')');
		}
		
		var errhsnProductBuyingRate = hsnProductBuyingRate();
		var errpurchaseUOM = false;
		var errotherPurchaseUOM = false;
	    if($("#purchaseUOM").val() == "OTHERS"){
		    errotherPurchaseUOM = validateOtherPurchaseUOM();
		    $("#dny-product-tempPurchaseOtherUOM").val($("#purchaseUOM").val()+'-'+'('+$("#purchaseOtherUOM").val()+')');
	    }
	   

		if((errHsndesc) || (errHsnCode) || (errhsnProductName) || (errhsnUOM) || (errOtherUOM) || (errhsnProductRate) || (errhsnProductIgst) || (errhsnProductBuyingRate) || (errpurchaseUOM) || (errotherPurchaseUOM)){
			e.preventDefault();	
		}else{
			 var errCheckDoctypeAndTaxRate = checkDoctypeAndTaxRate();
			 if(!errCheckDoctypeAndTaxRate){
				errorStatus = false;
				callPostAddProduct();
				e.preventDefault();
			 }
			
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
		}else if(errhsnProductBuyingRate){
			focusTextBox("dny-product-buyingRate");
		}else if(errpurchaseUOM){
			focusTextBox("dny-product-purchaseUOM");
		}else if(errotherPurchaseUOM){
			focusTextBox("purchaseOtherUOM");
		}
		
		
	});
	
	$("#dnyProductCancelBtn,#dny-product-cancel-link").click(function(e){
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
    
    $("#dny-product-unitOfMeasurement,#dny-product-purchaseUOM").on("keyup click",function(){
    	if(this.id == 'dny-product-unitOfMeasurement'){
    		inputId = 'dny-product-unitOfMeasurement';
    		spanId = 'dny-prod-uom';
    	}else{
    		inputId = 'dny-product-purchaseUOM';
    		spanId = 'dny-purchase-uom-req';
    	}
		 if ($("#"+inputId).val() === ""){
			 $("#"+inputId).addClass("input-error").removeClass("input-correct");
			 $("#"+spanId).show();
			 
		 }
		 if ($("#"+inputId).val() !== ""){
			 $("#"+inputId).addClass("input-correct").removeClass("input-error");
			 $("#"+spanId).hide();
			 
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
		 if ($("#dny-product-unitOfMeasurement").val()=="OTHERS"){
			 $("#dny-product-purchaseUOM").val($('#dny-product-otherUOM').val());
		 }
		 else{
			 $("#dny-product-purchaseUOM").val($('select#dny-product-unitOfMeasurement option:selected').val());
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
			 $("#dny-product-purchaseUOM").val($('#dny-product-otherUOM').val());
		 } 
	});
	
	$("#dny-productRate,#dny-product-buyingRate").on("keyup input",function(){
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		if(this.id == 'dny-productRate'){
			inputId = 'dny-productRate';
			spanId = 'dny-prod-rate';
		}else{
			inputId = 'dny-product-buyingRate';
			spanId = 'dny-prod-buy-rate-req';
			dny_prod_regMsg = "Purchase Price should be in proper format";
		}
		
		if(dny_ser_currencyRegex.test($("#"+inputId).val()) === true){
			$("#"+spanId).hide();
			$("#"+inputId).addClass("input-correct").removeClass("input-error");	
		}
		if(dny_ser_currencyRegex.test($("#"+inputId).val()) !== true){
			$("#"+spanId).text(dny_prod_regMsg);
			$("#"+spanId).show();
			$("#"+inputId).addClass("input-error").removeClass("input-correct");	
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
	
	
	$('#dny-product-os-gstin').on("change", function() {
    	var gstinSelectedId = $('#dny-product-os-gstin').val();	
    	if(gstinSelectedId != ''){
    		$('#dny-product-os-gstin').addClass("input-correct").removeClass("input-error");
    		$('#opening-stock-gstin-req').hide();
    		$("#dny-product-os-locationStore").empty();
    		$.each(userGstinsJson,function(i,value) {
    			if(value.id == gstinSelectedId ){
    				if(value.gstinLocationSet.length == 1){
    					$.each(value.gstinLocationSet,function(i,value2) {
    						$("#dny-product-os-locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id).attr('selected','selected'));
    						$("#dny-product-os-StoreId").val(value.gstinStore);
    					});
    					
    				}else{
    					
    					$("#dny-product-os-locationStore").append('<option value="">Select</option>');
    					$.each(value.gstinLocationSet,function(i,value2) {
    						$("#dny-product-os-locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
    					});
    				}	
    			}
    		});
    		
    		
    	}else{
    		$('#opening-stock-gstin-req').show();
       	  	$('#dny-product-os-gstin').addClass("input-error").removeClass("input-correct");
    	}     
    });
	
	
	$("#dny-product-os-qty").on("keyup input",function(){
		var purchasePrice = 0;
		$("#dny-product-os-value").val(0);
    	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#dny-product-os-qty").val()) === true){
			$("#opening-stock-quantity-req").hide();
			$("#dny-product-os-qty").addClass("input-correct").removeClass("input-error");	
			
			if($("#dny-product-buyingRate").val().length != 0) {
				
					$("#dny-product-os-value").val($("#dny-product-buyingRate").val() * $("#dny-product-os-qty").val());
					$("#dny-product-os-value").removeClass("input-error");
					$("#opening-stock-value-req").hide();
				
			}
		}
    	
		if(currencyRegex.test($("#dny-product-os-qty").val()) !== true){
			$("#opening-stock-quantity-req").text("Quantity should be numeric");
			$("#opening-stock-quantity-req").show();
			$("#dny-product-os-qty").addClass("input-error").removeClass("input-correct");	
		}    	
    });
	
	$("#dny-product-os-value").on("keyup input",function(){
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#dny-product-os-value").val()) === true){
			$("#opening-stock-value-req").hide();
			$("#dny-product-os-value").addClass("input-correct").removeClass("input-error");	
			
		}
    	
		if(currencyRegex.test($("#dny-product-os-value").val()) !== true){
			$("#opening-stock-value-req").text("Quantity should be numeric");
			$("#opening-stock-value-req").show();
			$("#dny-product-os-value").addClass("input-error").removeClass("input-correct");	
		}    	
    });
	
	$("#dny-product-os-openingStockCancelBtn").on( "click", function(e){
		resetOpeningStockFormValues();
	});
	
	$("#dny-product-os-openingStockSaveBtn,#dny-product-os-openingStockEditBtn").on( "click", function(e){
		var isValidationDone = false;
		var openingStockGstin = validateSelectField("dny-product-os-gstin","opening-stock-gstin-req");
		var openingStockLocation = validateSelectField("dny-product-os-locationStore","opening-stock-location-req");
	
		if((openingStockGstin) || (openingStockLocation)){
			e.preventDefault();
		}else{
			//alert("Validation complete");
			var openingStockGstinText = $("#dny-product-os-gstin").find("option:selected").text();
			var openingStockLocationText = $("#dny-product-os-locationStore").find("option:selected").text();
			if(checkIfStockExistsForGstinAndLocation()){
				if(this.id == 'dny-product-os-openingStockSaveBtn'){
					e.preventDefault();
					bootbox.confirm("Opening Stock already Added for GSTIN-"+openingStockGstinText+" and Store-"+openingStockLocationText+". Do you want to change it ?", function(result){
						 if (result){
							//override the values in opening stock	
							 overrideExsitingStockvalue();
							 resetOpeningStockFormValues();
							 e.preventDefault();
						 }else{
							 //donot override and discard
							 resetOpeningStockFormValues();
							 e.preventDefault();
						 }
					});
				}else{ 
					//bootbox.alert("WARNING !!! You cannot change Opening Stock for GSTIN-"+openingStockGstinText+" and Store-"+openingStockLocationText +" as record already exists.");
					updateExistingRecord();
					resetOpeningStockFormValues();
					e.preventDefault();
				}	
			}else{
				if(this.id == 'dny-product-os-openingStockSaveBtn'){
					constructDynamicDivForOpeningStock();
				}else{
					updateExistingRecord();
					resetOpeningStockFormValues();
				}
				
				e.preventDefault();
			}	
		}	
	});
	
	 $(".dny-product-opn-stock-qty,.dny-product-opn-stock-value").on("keyup input",function(){
     	//this.value = this.value.replace(/[^[0-9.]*$/, '');
     	 var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
     	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
     });
	
});


$("#search-product-autocomplete").autocomplete({
    source: function (request, response) {
        $.post("getProductNameList", {
            term: extractLastRW(request.term),
            gNo : ($("#gstnStateId option:selected").text().split('[')[0]).trim(),
            location :  $('select#location option:selected').val(),
            documentType : $("#selectedDocType").val()
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
        
        dynamicallyAddedProductJson = fetchDynamicallyAddedProduct($("#locationStorePkId").val(),value.trim());
		
		if(dynamicallyAddedProductJson){
			changeServiceNameAsPerAutoComplete(dynamicallyAddedProductJson);
			$("#search-product-autocomplete").addClass("input-correct").removeClass("input-error");
    		$("#dny-product-no-records-found").hide();
    		$("#search-ser-prod-autocomplete-csv-id").hide();
		}
       
       /*  $.ajax({
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
						$("#dny-productIgst-check").val(value.taxRate);
					 }else{
						 $("#dny-productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
					 }
				}); 
			}
        }); 
    }

});

function loadUnitOfMeasurementForProducts(){
	if(productUnitOfMeasurementJson == ''){
		$.ajax({
			url : "getUnitOfMeasurement",
			type : "POST",
			dataType : "json",
			data:{_csrf_token : $("#_csrf_token").val()},
			async : false,
			success:function(json,fTextStatus,fRequest){
				
				$("#dny-product-unitOfMeasurement").empty();
				$("#dny-product-purchaseUOM").empty();
				$("#dny-product-unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
				$("#dny-product-purchaseUOM").append('<option value="">Select Unit Of Measurement</option>');
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				productUnitOfMeasurementJson = json;
				$.each(json,function(i,value) {
					$("#dny-product-unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
					$("#dny-product-purchaseUOM").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
				});
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		});
	}else{
		//First set Unit Of Measurement dropdown
		$("#dny-product-unitOfMeasurement").empty();
		$("#dny-product-purchaseUOM").empty();
		$("#dny-product-unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
		$("#dny-product-purchaseUOM").append('<option value="">Select Unit Of Measurement</option>');
		$.each(productUnitOfMeasurementJson,function(i,value) {
			$("#dny-product-unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
			$("#dny-product-purchaseUOM").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
			
		});
		
	}
	
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
	
	$("#dny-product-buyingRate").val('');
	$("#dny-product-purchaseOtherUOM").val('');
	$("#dny-product-tempPurchaseOtherUOM").val('');
	
	loadRateOfProductTax();
	dynamicallyAddedProductJson = '';
	$("#toggleOpeningStock").html('');
}

function callPostAddProduct(){
	$("#dnyServiceProductDiv").hide();
	$('.loader').show();
	var inputData = generateProductInputJson();
	$.ajax({
		url : "saveProductWithOpeningStockAjax",
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
				//bootbox.alert(json.message);
				 //set service data - start 
				productJson = fetchDynamicallyAddedProduct($("#locationStorePkId").val(),$("#dny-product-name").val());
				 //productJson = json.product;
				 console.log(JSON.stringify(productJson));
				 
				 if(productJson){
					changeServiceNameAsPerAutoComplete(productJson);
					$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-service-no-records-found").hide();
				 }
				 
				 //set service data - end 
				 $('.loader').hide();
				bootbox.alert(json.message, function() {
					clearProductFormFields();
					$("#dnyCustomerDiv").hide();
					$("#previewInvoiceDiv").hide(); 
					$("#customerEmailDiv").hide();
					
					$("#addInvoiceDiv").show();
					$('html, body').animate({
					      scrollTop: $("#callOnEditId").offset().top
					},0);
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

function generateProductInputJson(){
	var hsnDescription = $("#dny-hsnDescription").val();
	var hsnCode= $("#dny-hsnCode").val();
	var productName = $("#dny-product-name").val();
	var productIgst = $('select#dny-productIgst option:selected').val();
	var productRate = $("#dny-productRate").val();
	var uom = $('select#dny-product-unitOfMeasurement option:selected').val();
	var otherUom = $("#dny-product-tempUom").val();
	var purchaseRate = $("#dny-product-buyingRate").val();
	var purchaseUOM = $('#dny-product-unitOfMeasurement option:selected').val();
	var purchaseOtherUOM = $("#dny-product-tempUom").val();
	var hsnPkId = $("#hsnCodePkId").val();
	var advolCess = $('select#dny-product-advolCess option:selected').val();
	var nonAdvolCess = $('select#dny-product-nonAdvolCess option:selected').val();
	
	 //get add charges from list in javascript - Start 
	  var jsonObjectAC;
	  var acListArray = new Array();
	  
	   dnyGstinOpeningstockProductTable.rows().every( function ( index, tableLoop, rowLoop ) {
			var rowX = dnyGstinOpeningstockProductTable.row(index).node();
		    var row = $(rowX);
		    var currentQty = row.find(".dny-product-quantity_"+index).val();
		    var currentStockValue = row.find(".dny-product-stockvalue_"+index).val();
		    jsonObjectAC = new Object();
		    jsonObjectAC.gstnId = $("#dny-product-opening_stock_gstin_val_"+index).val();
			jsonObjectAC.storeId = $("#dny-product-opening_stock_location_val_"+index).val();
			jsonObjectAC.openingStock = parseFloat(currentQty).toFixed(2);
			jsonObjectAC.openingStockValue = parseFloat(currentStockValue).toFixed(2);
			jsonObjectAC.currentStock = parseFloat(currentQty).toFixed(2);
			jsonObjectAC.currentStockValue = parseFloat(currentStockValue).toFixed(2);
			jsonObjectAC.inventoryUpdate = "N";
			acListArray.push(jsonObjectAC);
	   });
	  //get add charges from list in javascript - End 
	
	var inputData = {
			"name": productName,
			"hsnCode": hsnCode,
			"hsnDescription": hsnDescription,
			"unitOfMeasurement": uom,
			"otherUOM": otherUom,
			"productRate": parseFloat(productRate),
			"productIgst": parseFloat(productIgst),
			"hsnCodePkId": parseInt(hsnPkId),
			"advolCess" : advolCess,
			"nonAdvolCess" : nonAdvolCess,
			"purchaseRate" : purchaseRate,
			"purchaseUOM" : purchaseUOM,
			"purchaseOtherUOM" : purchaseOtherUOM,
			"openingStockBean" :  JSON.parse(JSON.stringify(acListArray))
	};
	
	console.log("inputData : "+JSON.stringify(inputData));
	return inputData;
}

function productLoadAdvolCessRate(){
    if(productAdvolCessRateJson == ''){
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
                        productAdvolCessRateJson = json;
                        
                  },
                  error: function (data,status,er) {
                            
                             getInternalServerErrorPage();   
                  }
          });
    }
    
    if(productAdvolCessRateJson != ''){
    	 $('#dny-product-advolCess').empty();
         $.each(productAdvolCessRateJson, function(i, value) {
              $('#dny-product-advolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));    
         }); 
    }
}


function productLoadNonAdvolCessRate(){
    if(productNonAdvolCessRateJson == ''){
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
                        productNonAdvolCessRateJson = json;
                        
                  },
                  error: function (data,status,er) {
                            
                             getInternalServerErrorPage();   
                  }
          });
    }
    
    if(productNonAdvolCessRateJson != ''){
    	$('#dny-product-nonAdvolCess').empty();
        $.each(productNonAdvolCessRateJson, function(i, value) {
             $('#dny-product-nonAdvolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));    
        });    
    }
}

function hsnProductBuyingRate(){
	
	errhsnProductBuyingRate = validateTextField("dny-product-buyingRate","dny-prod-buy-rate-req",blankMsg);
	if(!errhsnProductBuyingRate){
		errhsnProductBuyingRate=validateRegexpressions("dny-product-buyingRate","dny-prod-buy-rate-req",regMsg,currencyRegex);
	 }
	return errhsnProductBuyingRate;
}

function purchaseUOM(){
	 errpurchaseUOM = validateTextField("dny-product-purchaseUOM","dny-purchase-uom-req",blankMsg);
	 return errpurchaseUOM;
}

function validateOtherPurchaseUOM(){
	errotherPurchaseUOM = validateTextField("dny-product-purchaseOtherUOM","dny-product-otherPurchaseUom-req",blankMsg);
	 if(!errotherPurchaseUOM){
		 errotherPurchaseUOM=validateFieldLength("dny-product-purchaseOtherUOM","dny-product-otherPurchaseUom-req",lengthMsg,1);
	 }
	 
	 return errotherPurchaseUOM;
}

function productLoadUserGstn(){
	//console.log("productLoadUserGstn - Start");
	if(userGstinsJson == ''){
		loadGenericUserGstin();
	}else{
		  json = userGstinsJson;
		  $("#dny-product-os-gstin").empty();
		  $("#dny-product-os-locationStore").empty();
		  if(userGstinsJson.length == 1){
			  $.each(userGstinsJson,function(i,value){
				  $("#dny-product-os-gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
				  
			  });
			  
	  			if(userGstinsJson[0].gstinLocationSet.length == 1){
	  				$.each(userGstinsJson[0].gstinLocationSet,function(i,value) {
					  $("#dny-product-os-locationStore").append($('<option>').text(value.gstinLocation).attr('value',value.id));
					  $("#dny-product-os-StoreId").val(value.gstinStore);
	  				});
	  			}
	  			else{
				     $("#dny-product-os-locationStore").append('<option value="">Select</option>');
					 $.each(userGstinsJson[0].gstinLocationSet,function(i,value){
						$("#dny-product-os-locationStore").append($('<option>').text(value.gstinLocation).attr('value',value.id));
					});
	  			}
				
			}else{
				$("#dny-product-os-gstin").append('<option value="">Select</option>');
				$.each(userGstinsJson,function(i,value) {
					$("#dny-product-os-gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
				});	
			}
	}
	
}

function checkForZeroQuantity(id,span,msg){
	var resp = false;
	if($("#"+id).val() == 0 ){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+span).text(msg);
		$("#"+span).show();
		resp = true;
	}	
	return resp;
}

function resetOpeningStockFormValues(){
	productLoadUserGstn();
	$('#dny-product-os-qty').val("");
	$('#dny-product-os-value').val("");
	$("#dny-product-os-edtUniq").val("");
	$("#dny-product-os-openingStockSaveBtn").show();
	$("#dny-product-os-openingStockEditBtn").hide();
	
	$("#dny-product-os-gstin").show();
	$("#dny-product-os-gstin-hidden").val('');
	$("#dny-product-os-gstin-hidden").hide();
	$("#dny-product-os-locationStore").show();
	$("#dny-product-os-locationStore-hidden").val('');
	$("#dny-product-os-locationStore-hidden").hide();
}

function overrideExsitingStockvalue(){
	var openingStockGstinValue = $('select#dny-product-os-gstin option:selected').val();
	var openingStockLocationValue = $('select#dny-product-os-locationStore option:selected').val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningStock");
	   var totalACRecordNo = $addChgToggle.children().length;
	   for (i = 0; i < totalACRecordNo; i++) { 
		   //Start
			 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
			 var num2 = $addChgToggle.children()[i].id.substring(index2);
			 num2 = num2.replace("_","-");
			 var recordNo = num2.replace("-","");
			 if(($("#opening_stock_gstin_val"+num2).val() == openingStockGstinValue) && ($("#opening_stock_location_val"+num2).val() == openingStockLocationValue)){
				 $('#opening_stock_quantity_'+recordNo).text("Quantity : "+$('#dny-product-os-qty').val());
				 $('#opening_stock_quantity-'+recordNo).val($('#dny-product-os-qty').val());
				 $('#opening_stock_value_'+recordNo).text("Stock Value : " +$('#dny-product-os-value').val());
				 $('#opening_stock_value-'+recordNo).text( $('#dny-product-os-value').val());
				break; 
			 }
			//End
	   }   
	  //get add charges from list in javascript - End
}

function constructDynamicDivForOpeningStock(){
	openingStockRowNum++;
	var openingStockGstinText = $("#dny-product-os-gstin").find("option:selected").text();
	var openingStockGstinValue = $('select#dny-product-os-gstin option:selected').val();
	var openingStockLocationText = $("#dny-product-os-locationStore").find("option:selected").text();
	var openingStockLocationValue = $('select#dny-product-os-locationStore option:selected').val();
	var openingStockQuantity = '';
	var openingStockValue =  '';
	if (($('#dny-product-os-qty').val() === "")) {
		openingStockQuantity=0;
	}
	else{
		openingStockQuantity= $('#dny-product-os-qty').val();
	}
	
	if (($('#dny-product-os-value').val() === "")) {
		openingStockValue=0;
	}
	else{
		openingStockValue=$('#dny-product-os-value').val();
	}
	//append dynamic div
	var $toggle = $("#toggleOpeningStock");
	var recordNo = openingStockRowNum;
	$toggle.append('<div class="cust-content" id="opening_stock__start_'+recordNo+'">'
			+'<div class="heading">'
                +'<div class="cust-con">'
                    +'<h1 id="opening_stock_location_'+recordNo+'">'+openingStockLocationText+'</h1>'
                +'</div>'
                +'<div class="cust-edit">'
                    +'<div class="cust-icon">'
                    	+'<a href="#callOnEditId" onclick="javascript:edit_opening_stock_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
                    	+'<a href="#" onclick="javascript:remove_opening_stock_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
                    +'</div>'
                +'</div>'
            +'</div>'
            +'<div class="content">'
                +'<div class="cust-con">'
                    +'<p id="opening_stock_gstin_'+recordNo+'" >GSTIN : '+openingStockGstinText+' </p>'
                    +'<p id="opening_stock_quantity_'+recordNo+'" >Quantity : '+openingStockQuantity+' </p>'
                    +'<p id="opening_stock_value_'+recordNo+'" >Stock Value : '+openingStockValue+' </p>'
                +'</div>'
                +'<input type="hidden" id="opening_stock_gstin-'+recordNo+'" name="" value="'+openingStockGstinText+'">'
                +'<input type="hidden" id="opening_stock_gstin_val-'+recordNo+'" name="" value="'+openingStockGstinValue+'">'
                +'<input type="hidden" id="opening_stock_quantity-'+recordNo+'" name="" value="'+openingStockQuantity+'">'
                +'<input type="hidden" id="opening_stock_value-'+recordNo+'" name="" value="'+openingStockValue+'">'
                +'<input type="hidden" id="opening_stock_location-'+recordNo+'" name="" value="'+openingStockLocationText+'">'
                +'<input type="hidden" id="opening_stock_location_val-'+recordNo+'" name="" value="'+openingStockLocationValue+'">'
                
                +'</div>'
		+'</div>');
	openCloseAccordionForDynamicProduct(recordNo);
	resetOpeningStockFormValues();
	
}

function openCloseAccordionForDynamicProduct(rowNum){
	var currId = "/"+rowNum;
	//alert("accordionsId ->"+accordionsId);
	if(dynamicProductAccordionsId.includes(currId)){
		
	}else{
		$("#opening_stock__start_"+rowNum+" .content").hide();
		$("#opening_stock__start_"+rowNum+" .heading").click(function () {
			$(this).next(".content").slideToggle();
		});	
		dynamicProductAccordionsId = dynamicProductAccordionsId +","+currId;
	}
	
}

function remove_opening_stock_row(recordNo){
	bootbox.confirm("Are you sure you want to remove ?", function(result){
		 if (result){
			 $('#opening_stock__start_'+recordNo).remove();
			 dynamicProductAccordionsId = dynamicProductAccordionsId.replace(",/"+recordNo, "");
		 }
	});	 
	
}

function updateExistingRecord(){
	
	var recordNo = $("#dny-product-os-edtUniq").val();
	var openingStockGstinText = $("#dny-product-os-gstin").find("option:selected").text();
	var openingStockGstinValue = $('select#dny-product-os-gstin option:selected').val();
	var openingStockLocationText = $("#dny-product-os-locationStore").find("option:selected").text();
	var openingStockLocationValue = $('select#dny-product-os-locationStore option:selected').val();
	var openingStockQuantity = $('#dny-product-os-qty').val();
	var openingStockValue =  $('#dny-product-os-value').val();
	
	$("#opening_stock_gstin_"+recordNo).html('');
	$("#opening_stock_quantity_"+recordNo).html('');
	$("#opening_stock_value_"+recordNo).html('');
	$("#opening_stock_location_"+recordNo).html('');
	
	$("#opening_stock_location_"+recordNo).text(openingStockLocationText);
	$("#opening_stock_gstin_"+recordNo).text("GSTIN : "+openingStockGstinText);
	$("#opening_stock_quantity_"+recordNo).text("Quantity : "+openingStockQuantity);
	$("#opening_stock_value_"+recordNo).text("Stock Value : "+openingStockValue);
    
    $("#opening_stock_gstin-"+recordNo).val(openingStockGstinText);
	$("#opening_stock_gstin_val-"+recordNo).val(openingStockGstinValue);
	$("#opening_stock_quantity-"+recordNo).val(openingStockQuantity);
	$("#opening_stock_value-"+recordNo).val(openingStockValue);
	$("#opening_stock_location-"+recordNo).val(openingStockLocationText);
	$("#opening_stock_location_val-"+recordNo).val(openingStockLocationValue); 
}

function checkIfStockExistsForGstinAndLocation(){
	var stockExists = false;
	
	var openingStockGstinValue = $('select#dny-product-os-gstin option:selected').val();
	var openingStockLocationValue = $('select#dny-product-os-locationStore option:selected').val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningStock");
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
				 if(($("#opening_stock_gstin_val"+num2).val() == openingStockGstinValue) && ($("#opening_stock_location_val"+num2).val() == openingStockLocationValue)){
					 stockExists = true;
					 break;
				 }
				//End
		   }
		   
	   }
	  //get add charges from list in javascript - End 
	   return stockExists;
}

function edit_opening_stock_row(recordNo){
	$("#dny-product-os-openingStockSaveBtn").hide();
	$("#dny-product-os-openingStockEditBtn").show();
	var openingStockGstin = $("#opening_stock_gstin_val-"+recordNo).val();
	var openingStockQuantity = $("#opening_stock_quantity-"+recordNo).val();
	var openingStockValue = $("#opening_stock_value-"+recordNo).val();
	var openingStockLocation = $("#opening_stock_location_val-"+recordNo).val();
	
	changeValueInGstinAndStoreDropdown(openingStockGstin,openingStockLocation);
	$("#dny-product-os-edtUniq").val(recordNo);
	$("#dny-product-os-qty").val(openingStockQuantity);
	$("#dny-product-os-value").val(openingStockValue);
	
	//hide the gstin dropdown and show input field - Start
	$("#dny-product-os-gstin-hidden").val($("#opening_stock_gstin-"+recordNo).val());
	$("#dny-product-os-gstin-hidden").show();
	$("#dny-product-os-gstin").hide();
	
	$("#dny-product-os-locationStore-hidden").val($("#opening_stock_location-"+recordNo).val());
	$("#dny-product-os-locationStore-hidden").show();
	$("#dny-product-os-locationStore").hide();
	//hide the gstin dropdown and show input field - End
	
}

function changeValueInGstinAndStoreDropdown(openingStockGstin,openingStockLocation){
	if(userGstinsJson == ''){
		productLoadUserGstn();
	}
	 $("#dny-product-os-gstin").empty();
	 $("#dny-product-os-locationStore").empty();
	 $.each(userGstinsJson, function(i, value) {
         if(openingStockGstin==value.id){
        	 $("#dny-product-os-gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id).attr('selected','selected'));
        	 $("#dny-product-os-locationStore").append('<option value="">Select</option>');
			 $.each(value.gstinLocationSet,function(i2,value2) {
				 if(value2.id == openingStockLocation){
					 $("#dny-product-os-locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id).attr('selected','selected'));
				 }else{
					 $("#dny-product-os-locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
				 }
				
			 });				
     			
     		 
         }else{
        	 $("#dny-product-os-gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
         }       
   });
	
}


function checkIfInvoiceGstinLocationExistsInOpeningStockGstinAndLocation(){
	var stockExists = false;
	var userLocation = $("#locationStorePkId").val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningStock");
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
				 if($("#opening_stock_location_val"+num2).val() == userLocation){
					 stockExists = true;
					 break;
				 }
				//End
		   }
		   
	   }
	  //get add charges from list in javascript - End 
	   return stockExists;
}

function fetchDynamicallyAddedProduct(storeId,productName){
	var resp = '';
	  $.ajax({
          url:"getProductByProductNameAndStoreId",     
          type : "POST",
          data : {"storeId": storeId , "productName":productName,_csrf_token : $("#_csrf_token").val()},
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
                dynamicallyAddedProductJson = json;
                resp = json;
                
                
          },
          error: function (data,status,er) {
                    
                     getInternalServerErrorPage();   
          }
	  });
   return resp;
}

function checkDoctypeAndTaxRate(){
	var isErrorInAddingProduct = true;
	var documentType = $("#selectedDocType").val();
	var productTaxRate = $("#dny-productIgst-check").val();
	if(documentType == 'invoice' || documentType == 'rcInvoice' || documentType == 'eComInvoice' ){
		if(productTaxRate > 0){
			isErrorInAddingProduct = false;
		}else{
			bootbox.alert("You have selected Document Type as "+documentType +" and the selected HSNCode has TaxRate <= 0. Please select other hsnCode which has TaxRate > 0.");
		}
	}else if(documentType == 'billOfSupply' || documentType == 'eComBillOfSupply'){
		if(productTaxRate == 0){
			isErrorInAddingProduct = false;
		}else{
			bootbox.alert("You have selected Document Type as "+documentType +" and the selected HSNCode has TaxRate > 0. Please select other hsnCode which has TaxRate = 0.");
		}
	}
	return isErrorInAddingProduct;
}

function setDataInOpeningStockProduct(){
	if(gstinsInventoryJson == ''){
		loadGstinsInventory();
	}
	dnyGstinOpeningstockProductTable.clear().draw();
	counter = 0;
	$.each(gstinsInventoryJson,function(i,value){
		var gstinNumber = value.gstinNo;
		var gstinId = value.id;
		$.each(value.gstinLocationSet,function(i,value2){	
			
			dnyGstinOpeningstockProductTable.row.add($(
		 			'<tr id="'+counter+'" >'
		 				+'<td>'+(counter+1)+'<input type="hidden" class="xCount_'+counter+'" value="'+counter+'"></td>'
		 				+'<td>'+gstinNumber+'</td>'
		 				+'<td>'+value2.gstinLocation+'</td>'
		 				+'<td><input type="hidden" id="dny-product-opening_stock_gstin_val_'+counter+'" value="'+gstinId+'"><input type="hidden" id="dny-product-opening_stock_location_val_'+counter+'" value="'+value2.id+'"><input type="text" value="0" class="dny-product-opn-stock-qty dny-product-editQty dny-product-quantity_'+counter+'" maxlength="8"></td>'
		 				+'<td><input type="text" value="0" class="dny-product-opn-stock-value dny-product-editStkVal dny-product-stockvalue_'+counter+'" maxlength="8"></td>'
		 			+'</tr>'					 	
			)).draw();
			counter++;
	      });
	   
    });
}

$("#dny-product-gstin-openingstock").on("change",".dny-product-editQty", function () {
	  $(this).val(roundToTwoDecimal(this.value));
	if(!isNaN($(this).val())){
		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
       var id=parents.parent().prev('tr').attr('id');
  	  if(id == undefined){                       // for desktop view ,this condition works
  		  var $row = $(this).closest("tr");
            var id = $row.attr('id');
  	  }
  	  
        	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
        	var quantity = $(this).val();
        	 $(".dny-product-quantity_"+rowNo).val(quantity);
        	var purchaseRate = $('#dny-product-buyingRate').val();
        	
        	 var amount = quantity * purchaseRate;
        	 $(".dny-product-stockvalue_"+rowNo).val(amount.toFixed(2));
	}else{
		$(this).val('').focus();
		
	}
	
});

$("#dny-product-gstin-openingstock").on("change",".dny-product-editStkVal", function () {
	  $(this).val(roundToTwoDecimal(this.value));
	if(!isNaN($(this).val())){
		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
     var id=parents.parent().prev('tr').attr('id');
	  if(id == undefined){                       // for desktop view ,this condition works
		  var $row = $(this).closest("tr");
          var id = $row.attr('id');
	  }
	  
      	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
      	var stkVal = $(this).val();
      	 $(".dny-product-stockvalue_"+rowNo).val(stkVal);
	}else{
		$(this).val('').focus();
		
	}
	
});
