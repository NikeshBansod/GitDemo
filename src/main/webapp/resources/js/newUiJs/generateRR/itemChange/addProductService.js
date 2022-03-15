$("#quantity").keyup(function() { 
    var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
	this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
});

$("#rate").keyup(function() {
	var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
	this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
	if($("#quantity").val()){
		 var quantity = $("#quantity").val();
	     var rate = $("#rate").val();
	     var amount = parseFloat(quantity * rate).toFixed(2);
	     $("#amount").val(amount);
	     $("#amountToShow").val(amount);
	}       
});

$("#cessNonAdvolId").keyup(function() { 
    var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
	this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
});

$("#amountToShow").on("keyup",function(){
	var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
	this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1'); 
	$("#amount").val(this.value);
});

$('#quantity').change(function(){
	var billMethod = $('#calculation_on').val();
	if(billMethod == 'Amount'){
	     var quantity = $("#quantity").val();
	     var rate = $("#rate").val();
	     var amount = parseFloat(quantity * rate).toFixed(2);
	     $("#amount").val(amount);
	     $("#amountToShow").val(amount);
	}
     
});

$('input[type=radio][name=cess-applicable]').change(function() {
	 if(this.value == 'No'){
		 $("#cess-applicable-show-hide").hide();
	 }else{
		 $("#cess-applicable-show-hide").show();
	 }
});

$("#description").click(function () {
	 if ($(this).is(":checked")) {
		 $("#descriptionDiv").show();
	 }else{
		 $("#descriptionDiv").hide();	 
	 }
	
});

$("#descriptionTxt").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z0-9.-\s/_]*$/, '');
});

$("#invoiceNumber").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z0-9\s/]*$/, '');
	
	 if ($("#invoiceNumber").val() !== ""){
		 $("#invoiceNumber").addClass("input-correct").removeClass("input-error");
		 $("#invoiceNumber-csv-id").hide();		 
	 }
	 if ($("#invoiceNumber").val() === ""){
		 $("#invoiceNumber").addClass("input-error").removeClass("input-correct");
		 $("#invoiceNumber-csv-id").show();		 
	 } 
	 
	 $("#invoiceNumber-duplicate-csv-id").hide();	
});

function callDefaultOnPageLoad(invoiceFor){
	$("#uom-show-hide").show();
	$("#amount-show-hide").show();
	
	if(invoiceFor == "Service"){
		$("#callOnEditId").text("Add Services");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Search By Service Name");
		$("#search-service-autocomplete").show();
        $("#search-product-autocomplete").hide();
        $("#diffPercentageDiv").show();
		$("#amountToShow").removeAttr('disabled');
		$("#rate").attr('disabled','disabled');
		$("#rate-show-hide").hide();
        $("#quantity-show-hide").hide();
        $("#unitOfMeasurement-show-hide").hide();
        
	}else{
		$("#callOnEditId").html("");
		$("#callOnEditId").text("Add Goods");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Search By Goods Name");
		$("#search-service-autocomplete").hide();
        $("#search-product-autocomplete").show();
        $("#diffPercentageDiv").hide();
		$("#amountToShow").attr('disabled','disabled');
		$("#rate").removeAttr('disabled');
		$("#rate-show-hide").show();
        $("#quantity-show-hide").show();
        $("#unitOfMeasurement-show-hide").show();
        
	}
	
	$("#invoiceNumber").val($("#invNo").val());
	var isFiledToGSTN = $("#invDataFiledToGstn").val();
	if(isFiledToGSTN == "true"){
		var invoiceSequenceType = $("#invoiceSequenceType").val();
		if(invoiceSequenceType != 'Auto'){
			$("#invoiceNumberDiv").show();
			$("#invoiceNumber").val('');
		}
	}
	
}

function loadGenericUserGstin(){
	if(userGstinsJson == ''){
		$.ajax({
			url : "getGSTINListAsPerRole",
			method : "get",
			contentType : "application/json",
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				userGstinsJson = json;
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	         }
		});
	}
	
}

function getServiceList(){
	var defaultDocumentType = $("#documentType").val();
	var urlToFetchService = '';
	if(defaultDocumentType == 'invoice' || defaultDocumentType == 'rcInvoice' || defaultDocumentType == 'eComInvoice'){ 
		urlToFetchService = 'invoiceServiceList';
	}else if(defaultDocumentType == 'billOfSupply' || defaultDocumentType == 'eComBillOfSupply'){
		urlToFetchService = 'billOfSupplyServiceList';
	}
	
	 if(checkIfGstinAndLocationIsSelected()){
		 $(document).ready(function(){
			    $.ajax({
					url : urlToFetchService,
					type : "POST",
					/*contentType : "application/json",*/
					dataType : "json",
					data : { _csrf_token : $("#_csrf_token").val(), gNo : $("#gstnStateIdInString").val(), location : $('#location').val()},
					async : false,
					success:function(json,fTextStatus,fRequest){
						if (isValidSession(json) == 'No') {
							window.location.href = getDefaultSessionExpirePage();
							return;
						}
						
						if(isValidToken(json) == 'No'){
							window.location.href = getCsrfErrorPage();
							return;
						}
						
						userServiceList = json;	
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			         },
			         error: function (data,status,er) {
			        	 getInternalServerErrorPage();   
			         }
				}); 
			
			     
			}); 
	 }
	

}

function getProductList(){
	/*if ($("#location").val() === "" || $("#location").val() === null || $("#location").val() == undefined){
		//alert("Please select GSTIN and Location");
	}else{*/
		var defaultDocumentType = $("#documentType").val();
		var urlToFetchProduct = '';
		if(defaultDocumentType == 'invoice' || defaultDocumentType == 'rcInvoice' || defaultDocumentType == 'eComInvoice'){ 
			urlToFetchProduct = 'invoiceProductList';
		}else if(defaultDocumentType == 'billOfSupply' || defaultDocumentType == 'eComBillOfSupply'){
			urlToFetchProduct = 'billOfSupplyProductList';
		}
		 
		 if(checkIfGstinAndLocationIsSelected()){
			 $(document).ready(function(){
				    $.ajax({
						url : urlToFetchProduct,/*"getProductsList",*/
						method : "post",
						/*contentType : "application/json",*/
						dataType : "json",
						data : { _csrf_token : $("#_csrf_token").val(), gNo : $("#gstnStateIdInString").val(), location : $('#location').val()},
						async : false,
						success:function(json,fTextStatus,fRequest){
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultSessionExpirePage();
								return;
							}
							if(isValidToken(json) == 'No'){
								window.location.href = getCsrfErrorPage();
								return;
							}
							userProductList = json;	
							setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				         },
				         error: function (data,status,er) {
				        	 
				        	 getInternalServerErrorPage();   
				        }
					});    
				}); 
		 }
	
}

function checkIfGstinAndLocationIsSelected(){
	var isSelected = false;
	
	//fetch gstin number like 27XXXXX1234A1ZQ[Maharashtra]
	
	 var gstnStateIdInString = $("#gstnStateIdInString").val();
	 var gstnStateId = $('#gstnStateId').val();
	 if(gstnStateIdInString == undefined || gstnStateIdInString == 'Select' || gstnStateId == ''){ 
		   alert("Please Select GSTIN");
	 }else{
		 
		//fetch location
		 var locationVal = $('#location').val();
		 if(locationVal == undefined || locationVal == '' ){ 
			   alert("please select location");
		 }else{
			 isSelected = true;
		 }
		 
	 }
	return isSelected;
}

function changeServiceNameAsPerAutoComplete(selectedJson){
	var invoiceFor = $('#invoiceFor').val(); 
    //var serviceId = $(this).val();
    var quantity = $("#quantity").val();
    var billMethod = $('#calculation_on').val();
    setValueInSelectServiceName(selectedJson,invoiceFor);
    setValueInAddServiceProductFormFields(selectedJson,invoiceFor,quantity,billMethod);
    setValueInSelectCessNonAdvol(selectedJson.advolCess,selectedJson.nonAdvolCess,invoiceFor);
}

function changeServiceNameDropDownAsPerSelectedOne(invoiceFor , serviceName){
	
	var dynamicallyFetchJson = '';
	if(invoiceFor == 'Service'){
		dynamicallyFetchJson = fetchDynamicallyAddedService($("#locationStorePkId").val(),serviceName);
	}else{
		dynamicallyFetchJson = fetchDynamicallyAddedProduct($("#locationStorePkId").val(),serviceName);
	}
	setValueInSelectServiceName(dynamicallyFetchJson,invoiceFor);
	
}

function setValueInSelectServiceName(selectedJson,invoiceFor){
	var json = '';
	
	if(invoiceFor == 'Service'){
		getServiceList();
		json = userServiceList;
	}else{
		getProductList();
		json = userProductList;
	}
	
	$('#service_name').empty();
	if(invoiceFor == 'Service'){
		$('#service_name').append('<option value="">Select</option>');	 
	}else{
		$('#service_name').append('<option value="">Select</option>');	
	}
	
	$.each(json,function(i,value) {
		if(selectedJson.id == value.id){
			$('#service_name').append($('<option>').text(value.name).attr('value',value.id).attr('selected','selected')); 
		 }else{
			 $('#service_name').append($('<option>').text(value.name).attr('value',value.id));
		 }
		
	});
}

function setValueInAddServiceProductFormFields(json,invoiceFor,quantity,billMethod){
	
	$("#uom").val('');
	$("#uomtoShow").val('');
	$("#rate").val('');
	$("#hsnSacCode").val('');
	$("#amount").val('');
	$("#amountToShow").val('');
	$("#openingStockProduct").val('');
	
	if(invoiceFor == 'Service'){
		 $("#search-service-autocomplete").val(json.name);
		 $("#uom").val(json.unitOfMeasurement);
		 $("#uomtoShow").val(json.serviceRate +" Rs per "+json.unitOfMeasurement);
		 
		 $("#rate").val(json.serviceRate);
		 $("#hsnSacCode").val(json.sacCode);
	
		 $("#amount").val(json.serviceRate);
	     $("#amountToShow").val(json.serviceRate);
		 
	}else{
		$("#search-product-autocomplete").val(json.name);
		$("#uom").val(json.unitOfMeasurement);
		 $("#uomtoShow").val(json.productRate+" Rs per "+json.unitOfMeasurement);
		 
		 $("#rate").val(json.productRate);
		 $("#hsnSacCode").val(json.hsnCode);
		 /*$("#ratetoShow").val(json.productRate);*/
		 $("#openingStockProduct").val(json.currentStock);
		 
		 if(quantity){
			 if(billMethod == 'Amount'){
			     var amount = quantity * json.productRate;
			     $("#amount").val(amount);
			     $("#amountToShow").val(amount);
			}
		 }
		 $("#qtyLabel").text("  [ Current - "+json.currentStock+" ]");
	}
}

function setValueInSelectCessNonAdvol(selectedJsonAdvolCess,selectedJsonNonAdvolCess,invoiceFor){
	var cessNonAdvolJson = '';
	var cessAdvolJson = '';
	
	if(invoiceFor == 'Service'){
		cessNonAdvolJson = serviceNonAdvolCessRateJson;
		cessAdvolJson = serviceAdvolCessRateJson;
	}else{
		cessNonAdvolJson = productNonAdvolCessRateJson;
		cessAdvolJson = productAdvolCessRateJson;
	}
	
	$('#cessAdvolId').empty();
	$.each(cessAdvolJson,function(i,value) {
		/*if(selectedJsonAdvolCess == value.cessRate){
			$('#cessAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate).attr('selected','selected')); 
		 }else{*/
			 $('#cessAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate));
		/* }*/
		
	});
	
	//$('#cessNonAdvolId').val(selectedJsonNonAdvolCess);
	$('#cessNonAdvolId').val(0);
}

$(document).ready(function(){
	$("#submitId").click(function(e){
		var goAhead = true;
		var errFlag22 = false;
		var errFlag23 = false;
		
		var isFiledToGSTN = $("#invDataFiledToGstn").val();
		if(isFiledToGSTN == "true"){
			var invoiceSequenceType = $("#invoiceSequenceType").val();
			if(invoiceSequenceType != 'Auto'){
				errFlag22 = validateTextField("invoiceNumber","invoiceNumber-csv-id",blankMsg);
				errFlag23 = checkIfInvoiceNumberAlreadyPresent($("#invoiceNumber").val());
				if(errFlag23){
					$("#invoiceNumber").addClass("input-error").removeClass("input-correct");
					$("#invoiceNumber-duplicate-csv-id").text("Invoice Number Already Present");
					$("#invoiceNumber-duplicate-csv-id").show();
				}
				if(errFlag22 || errFlag23){
					 $("#invoiceNumber").focus();
					 goAhead = false;
				}
			}
		}
		
		if(goAhead){
			var $toggle = $("#toggle");
			var newAddedProductCount = $toggle.children().length;
			var totalCount = 0;
			
			totalCount = parseInt(invoiceItemId.length) + parseInt(newAddedProductCount);
			if(totalCount == 0){
				bootbox.alert("Atleast one item should be present.");
			}else{
				if((parseInt(invoiceItemCount) == parseInt(invoiceItemId.length)) && (parseInt(invoiceItemCount) == parseInt(totalCount))){
					bootbox.alert("Please add or remove invoice items.");
				}else{
					callItemChangePreviewInvoice();
				} 
			}
			
		}
		 
		  
	});
	
	
});

function generateInvoiceInputJson(createDocType){
	 var createDocType = createDocType;//creditNote debitNote revisedInvoice deleteInvoice newInvoice
	 var invoiceNo = $("#unqIncDes").val();
	 var documentType = $("#invDocType").val();
	 var modeOfCreation = $("#modeOfCreation").val();//EDIT_INVOICE
	 var rrType = "itemChange"; //salesReturn discountChange salesPriceChange rateChange quantityChange itemChange partyChange multipleChanges
	 var iterationNo = $("#iterationNo").val();
	 //RND - Start
	 var invoiceFor = "";
	 var customerDetails = {};
	 var customerEmail = "";
	 var isCustomerRegistered = ""; 
	 var servicePlace = "";
	 var serviceCountry = "";
	 var deliveryPlace = "";
	 var deliveryCountry = "";
	 var poDetails = "";
	 var typeOfExport = "";
	 var discountType = "";
	 var discountValue = 0;
	 var discountAmount = 0;
	 var discountRemarks = "";
	 var additionalChargesType = "";
	 var additionalChargesValue = 0;
	 var additonalAmount = 0;
	 var additionalChargesRemark = "";
	 var amountAfterDiscount = 0;
	 var totalTax = 0;
	 var invoiceValue = 0;
	 var gstnStateId = "";
	 var invoiceDate = "";
	 var gstnStateIdInString = "";
	 var invoicePeriodFromDate = "";
	 var invoicePeriodToDate = "";
	 var locationVal  = "0";
	 var billToShipIsChecked = 'No';
	 var shipToCustomerName = '';
	 var shipToAddress = '';
	 var shipToCity = '';
	 var shipToPincode = '';
	 var shipToState = '';
	 var shipToStateCode = '';
	 var shipToStateCodeId = '';
	 var ecommerceGstin = '';
	 var ecommerce = 'No';
	 var reverseCharge = 'No';
	 var serviceListArray = new Array();
	 var acListArray = new Array();  
	 var footerNote = "";
	 var locationStoreId = parseInt(0);
	 var placeOfSupply = '';
	 var invNumber = '';
	 
	 
	  invoiceFor = invoiceJson.invoiceFor;
	  customerDetails = invoiceJson.customerDetails;
	  customerEmail = invoiceJson.customerEmail;
	  isCustomerRegistered = invoiceJson.isCustomerRegistered; 
	  servicePlace = invoiceJson.servicePlace;
	  serviceCountry = invoiceJson.serviceCountry;
	  deliveryPlace = invoiceJson.deliveryPlace;
	  deliveryCountry = invoiceJson.deliveryCountry;
	  poDetails = invoiceJson.poDetails;
	  typeOfExport = invoiceJson.typeOfExport;
	  discountType = invoiceJson.discountType;
	  discountValue = invoiceJson.discountValue;
	  discountAmount = invoiceJson.discountAmount;
	  discountRemarks = invoiceJson.discountRemarks;
	  additionalChargesType = invoiceJson.additionalChargesType;
	  additionalChargesValue = invoiceJson.additionalChargesValue;
	  additonalAmount = invoiceJson.additonalAmount;
	  additionalChargesRemark = invoiceJson.additionalChargesRemark;
	  amountAfterDiscount = invoiceJson.amountAfterDiscount;
	  totalTax = invoiceJson.totalTax;
	  invoiceValue = invoiceJson.invoiceValue;
	  gstnStateId = invoiceJson.gstnStateId;
	  invoiceDate = $("#invDate").val();
	  gstnStateIdInString = invoiceJson.gstnStateIdInString;
	  invoicePeriodFromDate = "";
	  invoicePeriodToDate = "";
	  locationVal  = invoiceJson.location;
	  billToShipIsChecked = invoiceJson.billToShipIsChecked;
	  shipToCustomerName = invoiceJson.shipToCustomerName;
	  shipToAddress = invoiceJson.shipToAddress;
	  shipToCity = invoiceJson.shipToCity;
	  shipToPincode = invoiceJson.shipToPincode;
	  shipToState = invoiceJson.shipToState;
	  shipToStateCode = invoiceJson.shipToStateCode;
	  shipToStateCodeId = invoiceJson.shipToStateCodeId;
	  ecommerceGstin = invoiceJson.ecommerceGstin;
	  ecommerce = invoiceJson.ecommerce;
	  reverseCharge = invoiceJson.reverseCharge;
	  acListArray = new Array();  
	  footerNote = invoiceJson.footerNote;
	  locationStoreId = 0;
	  placeOfSupply = invoiceJson.placeOfSupply;
	  invNumber = $("#invoiceNumber").val();
	   
	 //RND - End
	  
	 //Generate respective json as per item change - Start
	  var $toggle = $("#toggle");
	   var totalRecordNo = $toggle.children().length;
	   var jsonObject;
	   var serviceListArray = new Array();
	   for (i = 0; i < totalRecordNo; i++) { 
		  
			 var index2 = $toggle.children()[i].id.lastIndexOf("_");
			 var num2 = $toggle.children()[i].id.substring(index2);
			 num2 = num2.replace("_","-");
			 jsonObject = new Object();
			 jsonObject.serviceId = $("#service_name"+num2).val();
			 jsonObject.calculationBasedOn = $("#calculation_on"+num2).val();
			 jsonObject.unitOfMeasurement = $("#uom"+num2).val();
			 jsonObject.rate = $("#rate"+num2).val();
			 jsonObject.quantity = $("#quantity"+num2).val();
			 jsonObject.serviceIdInString = $("#service_name_textToShow"+num2).val();
	
			 jsonObject.previousAmount = $("#previousAmount"+num2).val();
	
			 jsonObject.billingFor = $("#billingFor"+num2).val();
		     jsonObject.cess = $("#cess"+num2).val();
		     jsonObject.advolCess = $("#cess_advol_rate"+num2).val();
		     jsonObject.nonAdvolCess = $("#cess_non_advol_rate"+num2).val();
		     jsonObject.discountTypeInItem = $("#discountTypeInItem"+num2).val();
		     jsonObject.offerAmount = $("#offerAmount"+num2).val();
		     jsonObject.hsnSacCode = $("#hsnSacCode"+num2).val();
		     jsonObject.diffPercent = $("#diffPercent"+num2).val();
		     jsonObject.isDescriptionChecked = $("#description_checked"+num2).val();
		     jsonObject.description = $("#description"+num2).val();
		     jsonObject.iterationNo = parseInt(1);
		     jsonObject.isNew = "Y";
			 serviceListArray.push(jsonObject);
		}
	   
	   if(invoiceItemId.length > 0){
		   itemChangeDataTable.rows().every( function ( index, tableLoop, rowLoop ) {
				 var rowX = itemChangeDataTable.row(index).node();
				 var row = $(rowX);
				 var id = row.find('.xId').val();
				 var serviceId = row.find('.xServiceId').val();
				 jsonObject = new Object();
				 jsonObject.id = row.find('.xId').val();
				 jsonObject.serviceId = row.find('.xServiceId').val();
				 jsonObject.billingFor = row.find('.xBillingFor').val();
				 jsonObject.isNew = "N";
				 serviceListArray.push(jsonObject);
		   });
		   
	   }
	 //Generate respective json as per item change - End
	
	 
	 var inputJson = {
	    "id": invoiceNo,       //This value is set 
		"invoiceFor": invoiceFor,
		"customerDetails": customerDetails,
		"servicePlace": servicePlace,
		"serviceCountry": serviceCountry,
		"deliveryPlace": deliveryPlace,
		"deliveryCountry": deliveryCountry,
		"poDetails": poDetails,
		"typeOfExport": typeOfExport,
		"serviceList": JSON.parse(JSON.stringify(serviceListArray)),  //This value is set 
		"discountType": discountType,
		"discountValue": discountValue,
		"discountAmount": discountAmount,
		"discountRemarks": discountRemarks,
		"additionalChargesType": additionalChargesType,
		"additionalChargesValue": additionalChargesValue,
		"additonalAmount": additonalAmount,
		"additionalChargesRemark": additionalChargesRemark,
		"amountAfterDiscount": amountAfterDiscount,
		"totalTax": totalTax,
		"invoiceValue": invoiceValue,
		"gstnStateId": gstnStateId,
		"invoiceDateInString": invoiceDate,
		"gstnStateIdInString": gstnStateIdInString,
		"invoicePeriodFromDateInString": invoicePeriodFromDate,
		"invoicePeriodToDateInString": invoicePeriodToDate,
		"billToShipIsChecked": billToShipIsChecked,
		"shipToCustomerName": shipToCustomerName,
		"shipToAddress": shipToAddress,
		"shipToCity": shipToCity,
		"shipToPincode": shipToPincode,
		"shipToState": shipToState,
		"shipToStateCode": shipToStateCode,
		"shipToStateCodeId": shipToStateCodeId,
		"location": locationVal,
		"customerEmail": customerEmail,
		"ecommerce": ecommerce,
		"ecommerceGstin": ecommerceGstin,
		"reverseCharge": reverseCharge,
		"addChargesList": JSON.parse(JSON.stringify(acListArray)),
		"footerNote": footerNote,
		"documentType": documentType,
		"isCustomerRegistered": isCustomerRegistered,
		"modeOfCreation": modeOfCreation,    //This value is set
		"iterationNo": parseInt(iterationNo+1),
		"lastRRType": "",
		"lastRR": "",
		"lastRRInvoiceNumber": "",
		"locationStoreId" : locationStoreId,
		"rrTypeForCreation" : rrType,    //This value is set
		"createDocType" : createDocType,
		"placeOfSupply" : placeOfSupply,
		"invoiceNumber" : invNumber
	};
	 
	console.log(JSON.stringify(inputJson)); 
	return inputJson;  
}

function callItemChangePreviewInvoice(){
	var isFiledToGSTN = $("#invDataFiledToGstn").val();
	var invoiceInputJson = '';
	if(isFiledToGSTN == "false"){
		invoiceInputJson = generateInvoiceInputJson("revisedInvoice");	
		$("#generateInvoicePageHeaderPreview").text("Preview Revised Note");
		$("#finalRevisedSubmitId").text("Create Revised Note");
	}else if(isFiledToGSTN == "true"){
		invoiceInputJson = generateInvoiceInputJson("newInvoice");
		$("#generateInvoicePageHeaderPreview").text("Preview New Invoice");
		$("#finalRevisedSubmitId").text("Create New Invoice");
	}
	$('.loader').show();
	responseJson = callEditInvoicePost(invoiceInputJson);
	if(responseJson.gstinDetails && responseJson.invoiceDetails ){
		$("#itemFirstPage").hide();
		$("#itemSecondPage").show();
		
		showUserDetailsInInvoicePreview(responseJson);
		showBillToAndShipToInPreviewDiv(responseJson);
		showServiceListDetailsInPreviewInvoiceDiv(responseJson);
		$('.loader').fadeOut("slow");
	}else{
		$('.loader').fadeOut("slow");
		bootbox.alert("You have added the same item again");
	}

}

function callEditInvoicePost(inputJson){
	var responseJson = '';
	 $.ajax({
			url : "generateRevisedInvoiceFromRR",
			method : "post",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
			data : JSON.stringify(inputJson),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				lastAndFinalInvoiceJson = json;
				responseJson = json; 
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		}); 
	 return responseJson;
}

$(document).on("click", "#finalRevisedSubmitId", function () {
	$('.loader').show();
	var isFiledToGSTN = $("#invDataFiledToGstn").val();
	var invoiceInputJson = '';
	setTimeout(function(){
		if(isFiledToGSTN == "false"){
			invoiceInputJson = generateInvoiceInputJson("revisedInvoice");	
			createRevisedInvoiceAjax(invoiceInputJson);
		}else if(isFiledToGSTN == "true"){
			invoiceInputJson = generateInvoiceInputJson("newInvoice");
			createNewInvoiceAjax(invoiceInputJson);
		}
		$('.loader').fadeOut("slow");
	}, 3000);	
});

function checkIfInvoiceNumberAlreadyPresent(invoiceNumber){
	var isPresent = false;
	$.ajax({
		  url:"checkIfInvNumAlreadyPresent", 	
		  type : "POST",
		  dataType: 'json',
		  headers: {_csrf_token : $("#_csrf_token").val()},
          async : false,
		  data:{"invoiceNumber" : invoiceNumber},
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
			  if(json.allowed_access == "TRUE"){
				  isPresent = false;
			  }else if(json.allowed_access == "FALSE"){
				  isPresent = true;
			  }
			 
		  }
	});
	
	return isPresent;
}

function formatDateInDDMMYYYY(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
	var actualDate = (date.getDate() < 10) ? ("0"+date.getDate()) : date.getDate();
	return (actualDate + "-"+month+"-"+date.getFullYear());	
}

