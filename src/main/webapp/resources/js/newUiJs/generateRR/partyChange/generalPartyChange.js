var invoiceJson = '';
var stateListJson = '';
var blankMsg="This field is required";

$(document).ready(function () {
	fetchInvoiceJson($("#unqIncDes").val());
	setBreadCrumHeaders();
	loadStateList();
	showHideInvoiceNumber();
	
	var isChecked = $('#shipToBill').is(':checked'); 

	if(isChecked == true){
		$("#consignee").hide();
	}else{
		$("#pos").val('');
		$("#consignee").show();
	}
	
	$("#shipToBill").click(function () {
        if ($(this).is(":checked")) {
        	$("#consignee").hide();
        	autoPopulateStateList($("#customer_custStateId").val());
        } else {
        	$("#pos").val('');
            $("#consignee").show();
            loadStateList();
        }
	});
	
});

function backToRRPage(){
	var idValue = $("#unqIncDes").val();
	$('.loader').show();
	document.previewInvoice.action = "./getRevAndRet";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
	$('.loader').fadeOut("slow");
}

function fetchInvoiceJson(invoiceId){
	if(invoiceJson == ''){
		 $.ajax({
			url : "getInvoiceDetailsForCnDn",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
			data : { "invIdt" : invoiceId },
			/*contentType : "application/json",*/
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
				invoiceJson = json;
				if(invoiceJson){
					$("#invDocType").val(json.documentType);
					$("#selectedDocType").val(json.documentType);
					$("#invDataFiledToGstn").val(json.fileToGstn);
					$("#iterationNo").val(json.iterationNo);
					$("#invNo").val(json.invoiceNumber);
					$("#invoiceNumber").val(json.invoiceNumber);
					$("#invDate").val(formatDateInDDMMYYYY(json.invoiceDate));
					$("#old_customer_name").val(json.customerDetails.custName);
					$("#old_customer_id").val(json.customerDetails.id);
					$("#old_customer_billToShipIsChecked").val(json.billToShipIsChecked);
					if(json.billToShipIsChecked == 'No'){
						$("#old_customer_shipToCustomerName").val(json.shipToCustomerName);
						$("#old_customer_shipToAddress").val(json.shipToAddress);
						$("#old_customer_shipToCity").val(json.shipToCity);
						$("#old_customer_shipToPincode").val(json.shipToPincode);
						$("#old_customer_shipToState").val(json.shipToState);
						$("#old_customer_shipToStateCode").val(json.shipToStateCode);
						$("#old_customer_shipToStateCodeId").val(json.shipToStateCodeId);
					}
					
				}
				$('.loader').fadeOut("slow");
			},
			error: function (data,status,er) {
		        getInternalServerErrorPage();   
		    }
			
		 });
	}
	
}

function setBreadCrumHeaders(){
	var documentType = $("#invDocType").val();
	var invoiceNumber = $("#invNo").val();
	$("#generateInvoiceDefaultPageHeader").html("");
	var breadCrumHeader = '';
	
	if(documentType == 'invoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Party Change</strong>';
	}else if(documentType == 'billOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Party Change </strong>';
	}else if(documentType == 'rcInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Reverse Charge Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Party Change</strong>';
	}else if(documentType == 'eComInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Party Change</strong>';
	}else if(documentType == 'eComBillOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Party Change</strong>';
	}
	
	
	$("#generateInvoiceDefaultPageHeader").append(
			breadCrumHeader
	);
}

function gotoBackHistoryListPage(){
	var documentType = $("#invDocType").val();
	var redirectTo = '';
    if(documentType == 'invoice'){
    	redirectTo = "./getInvoices";
	}else if(documentType == 'billOfSupply'){
		redirectTo = "./getBOS";
	}else if(documentType == 'rcInvoice'){
		redirectTo = "./getRCInvoice";
	}else if(documentType == 'eComInvoice'){
		redirectTo = "./getEComInvoice";
	}else if(documentType == 'eComBillOfSupply'){
		redirectTo = "./getEComBOS";
	}  
    window.location.href = redirectTo;
}

function gotoActualInvoicePage(){
	var documentType = $("#invDocType").val();
	var idValue = $("#unqIncDes").val();
	document.previewInvoice.action = "./getInvoiceDetails";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
}

function callPostPartyChange(){
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
	$("#partyFirstPage").hide();
	$("#partySecondPage").show();
	
	$('.loader').show();
	responseJson = callEditInvoicePost(invoiceInputJson);
	showUserDetailsInInvoicePreview(responseJson);
	showBillToAndShipToInPreviewDiv(responseJson);
	showServiceListDetailsInPreviewInvoiceDiv(responseJson);
	$('.loader').fadeOut("slow");
}

function generateInvoiceInputJson(createDocType){
	 var createDocType = createDocType;//creditNote debitNote revisedInvoice deleteInvoice newInvoice
	 var invoiceNo = $("#unqIncDes").val();
	 var documentType = $("#invDocType").val();
	 var modeOfCreation = $("#modeOfCreation").val();//EDIT_INVOICE
	 var rrType = "partyChange"; //salesReturn discountChange salesPriceChange rateChange quantityChange itemChange partyChange multipleChanges
	 var iterationNo = $("#iterationNo").val();
	 var invNumber = $("#invNo").val();
	 var invoiceDate = $("#invDate").val();
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
	 
	 
	  invoiceFor = invoiceJson.invoiceFor;
	  //customerDetails = invoiceJson.customerDetails;
	  //customerEmail = invoiceJson.customerEmail;
	  //isCustomerRegistered = invoiceJson.isCustomerRegistered; 
	  //servicePlace = invoiceJson.servicePlace;
	  //serviceCountry = invoiceJson.serviceCountry;
	  //deliveryPlace = invoiceJson.deliveryPlace;
	  //deliveryCountry = invoiceJson.deliveryCountry;
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
	  //invoiceDate = "";
	  gstnStateIdInString = invoiceJson.gstnStateIdInString;
	  invoicePeriodFromDate = "";
	  invoicePeriodToDate = "";
	  locationVal  = invoiceJson.location;
	  //billToShipIsChecked = invoiceJson.billToShipIsChecked;
	  //shipToCustomerName = invoiceJson.shipToCustomerName;
	  //shipToAddress = invoiceJson.shipToAddress;
	  //shipToCity = invoiceJson.shipToCity;
	  //shipToPincode = invoiceJson.shipToPincode;
	  //shipToState = invoiceJson.shipToState;
	  //shipToStateCode = invoiceJson.shipToStateCode;
	  //shipToStateCodeId = invoiceJson.shipToStateCodeId;
	  ecommerceGstin = invoiceJson.ecommerceGstin;
	  ecommerce = invoiceJson.ecommerce;
	  reverseCharge = invoiceJson.reverseCharge;
	  serviceListArray = new Array();
	  acListArray = new Array();  
	  footerNote = invoiceJson.footerNote;
	  locationStoreId = invoiceJson.locationStoreId;
	  placeOfSupply = invoiceJson.placeOfSupply;
	  if($("#invoiceNumber").val()){
		  invNumber = $("#invoiceNumber").val();
	  }
	   
	 //RND - End
	  
	 //Generate respective json as per party change  - Start
	  customerDetails = { 
					"id" : $("#customer_id").val() ,
   					"custName": $("#customer_name").val(),
   					"custAddress1" : $("#customer_custAddress1").val(),
   					"custAddress2" : '',
   					"custCity" : $("#customer_custCity").val(),
   					"custState": $("#customer_custState").val()
   				 };
	   customerEmail = $("#customer_custEmail").val();
	   isCustomerRegistered = "No";
	   if(typeof $("#customer_custGstId").val() != 'undefined' && $("#customer_custGstId").val() != ''){
			isCustomerRegistered = "Yes";
	   }
	   servicePlace = $("#customer_custAddress1").val()+","+$("#customer_custCity").val();
	   serviceCountry = 'India';
	   deliveryPlace = $('select#selectState option:selected').val();
	   deliveryCountry = 'India';
	   if(deliveryCountry == '' || deliveryCountry == 'Other'){
		   deliveryPlace = 0;
	   }
	   if(isCustomerRegistered == "Yes"){
		   deliveryPlace = $("#customer_custStateCodeId").val();    //deliveryPlace = $("#customer_custStateId").val();
	   }
	  
	    var isChecked = $('#shipToBill').is(':checked'); 
		billToShipIsChecked = 'No';
		shipToCustomerName = '';
		shipToAddress = '';
		shipToCity = '';
		shipToPincode = '';
		shipToState = '';
		shipToStateCode = '';
		shipToStateCodeId = '';
		if(isChecked == true){
			 billToShipIsChecked = 'Yes';
		}else{
			 shipToCustomerName = $("#shipTo_customer_name").val();
			 shipToAddress = $("#shipTo_address").val();
			 shipToCity = $("#shipTo_city").val();
			 shipToPincode = $("#shipTo_pincode").val();
			 shipToState = $("#selectState option:selected").text();
			 shipToStateCode = $("#shipTo_stateCode").val();
			 shipToStateCodeId = $("#shipTo_stateCodeId").val();
		}
		
	  //Generate respective json as per party change - End
	
	 
	 var inputJson = {
	    "id": invoiceNo,       //This value is set 
		"invoiceFor": invoiceFor,
		"customerDetails": customerDetails, //This value is set
		"servicePlace": servicePlace, //This value is set
		"serviceCountry": serviceCountry, //This value is set
		"deliveryPlace": deliveryPlace, //This value is set
		"deliveryCountry": deliveryCountry, //This value is set
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
		"billToShipIsChecked": billToShipIsChecked, //This value is set
		"shipToCustomerName": shipToCustomerName, //This value is set
		"shipToAddress": shipToAddress, //This value is set
		"shipToCity": shipToCity,  //This value is set
		"shipToPincode": shipToPincode, //This value is set
		"shipToState": shipToState, //This value is set
		"shipToStateCode": shipToStateCode, //This value is set
		"shipToStateCodeId": shipToStateCodeId, //This value is set
		"location": locationVal,
		"customerEmail": customerEmail,
		"ecommerce": ecommerce,
		"ecommerceGstin": ecommerceGstin,
		"reverseCharge": reverseCharge,
		"addChargesList": JSON.parse(JSON.stringify(acListArray)),
		"footerNote": footerNote,
		"documentType": documentType, //This value is set
		"isCustomerRegistered": isCustomerRegistered,
		"modeOfCreation": modeOfCreation,  //This value is set  
		"iterationNo": parseInt(iterationNo+1), //This value is set
		"lastRRType": "",
		"lastRR": "",
		"lastRRInvoiceNumber": "",
		"locationStoreId" : locationStoreId,
		"placeOfSupply" : placeOfSupply,
		"rrTypeForCreation" : rrType,    //This value is set
		"createDocType" : createDocType, //This value is set
		"invoiceNumber" : invNumber
	};
	 
	console.log(JSON.stringify(inputJson)); 
	return inputJson;  
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
	}, 3000);
	$('.loader').fadeOut("slow");
		
});

$(document).on("click", "#backToaddInvoiceDiv", function () {
	$('.loader').show();
	$("#partySecondPage").hide();
	$("#partyFirstPage").show();
	$('.loader').fadeOut("slow");
});

function formatDateInDDMMYYYY(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
	var actualDate = (date.getDate() < 10) ? ("0"+date.getDate()) : date.getDate();
	return (actualDate + "-"+month+"-"+date.getFullYear());	
}

function showHideInvoiceNumber(){
	var invoiceSequenceType = $("#invoiceSequenceType").val();
	var isFiledToGSTN = $("#invDataFiledToGstn").val();
	if(isFiledToGSTN == "true"){
		if($("#invoiceSequenceType").val() != 'Auto'){
			$("#invoiceNumberDiv").show();
			$("#invoiceNumber").val('');
		}
	}
}

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

