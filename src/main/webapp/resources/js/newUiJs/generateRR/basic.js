var openingstockCheckboxTable = '';
var invoiceJson = '';
var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var taxRateJson = '';
var cndnInputJson = '';
var cndnBackendResponse = '';

$(document).ready(function () {
	initializeDatatable();
	fetchInvoiceJson($("#unqIncDes").val());
	setBreadCrumHeaders();
	loadRRList($("#backRRType").val());
	getProductTaxRate();
	$('.loader').fadeOut("slow");
	
	$("#rrType").on("change", function() {
		$("#rrType-csv-id").hide();
		$("#showProductGrid").show();
		$("#tableField1").html('');
		$("#tableField2").html('');
		$("#tableField3").html('');
		
		if($(this).val()=="salesReturn"){
			$("#tableField1").text("Goods");
			$("#tableField2").text("Original Quantity");
			$("#tableField3").text("Returned Quantity");
			showDataInTable("salesReturn");
			
		}else if($(this).val()=="discountChange"){
			$("#tableField1").text("Goods");
			$("#tableField2").text("Original Discount");
			$("#tableField3").text("Revised Discount");
			showDataInTable("discountChange");
			
		}else if($(this).val()=="salesPriceChange"){
			$("#tableField1").text("Goods");
			$("#tableField2").text("Net Selling Price");
			$("#tableField3").text("Net Revised Selling Price");
			showDataInTable("salesPriceChange");
			
		}else if($(this).val()=="rateChange"){
			$("#tableField1").text("Goods");
			$("#tableField2").text("Original Rate");
			$("#tableField3").text("Revised Rate");
			showDataInTable("rateChange");
			
		}else if($(this).val()=="quantityChange"){
			$("#tableField1").text("Goods");
			$("#tableField2").text("Original Quantity");
			$("#tableField3").text("Revised Quantity");
			showDataInTable("quantityChange");
			
		}else if($(this).val()=="itemChange"){
			getRevAndRetByItemChange();
		}else if($(this).val()=="partyChange"){
			getRevAndRetByPartyChnage();
		}else if($(this).val()=="multipleChanges"){
			getRevAndRetByMultipleChanges();
		}else{
			$("#rrType-csv-id").show();
			$("#showProductGrid").hide();
		}
    });
	
	$('#Save').on('click', function(e){
		$('.loader').show();
		$('#Save').prop('disabled', true);
		var errFlagRRType = validateSelect("rrType","rrType-csv-id");
		var alertFlag = false;
		if($('select#rrType option:selected').val() == 'salesReturn'){
			alertFlag = checkValidationsForSalesReturn();
		}else if($('select#rrType option:selected').val() == 'discountChange'){
			alertFlag = checkValidationsForDiscountChange();
		}else if($('select#rrType option:selected').val() == 'salesPriceChange'){
			alertFlag = checkValidationsForSalesPriceChange();
		}else if($('select#rrType option:selected').val() == 'rateChange'){
			alertFlag = checkValidationsForRateChange();
		}else if($('select#rrType option:selected').val() == 'quantityChange'){
			alertFlag = checkValidationsForQuantityChange();
		}else if($('select#rrType option:selected').val() == 'itemChange'){
			
		}else if($('select#rrType option:selected').val() == 'partyChange'){
			
		}else if($('select#rrType option:selected').val() == 'multipleChanges'){
			
		}
		
		if(errFlagRRType || alertFlag ){		//|| errFlagNarration
			e.preventDefault();	
			$('#Save').prop('disabled', false);
			$('.loader').fadeOut("slow");
		}else{
			//alert("SUCCESS");
			callPostEditInvoice();
			$('#Save').prop('disabled', false);//change this to true later
			$('.loader').fadeOut("slow");
		 }
		
	});
	
});

$(document).on("click", "#secondPageCancel", function () {
	$("#firstPage").show();
	$("#secondPage").hide();
	$('.loader').fadeOut("slow");
	
});


$(document).on("click", "#backToFirstPage,#backToaddInvoiceDiv", function () {
	$('.loader').show();
	$("#previewCnDnInvoiceDiv").hide();
	$("#previewInvoiceDiv").hide();
	$("#firstPage").show();
	$('.loader').fadeOut("slow");
});


$(document).on("click", "#secondPageSave", function () {
	//alert("Oops ! Functionality is under development. Please wait for a while!!!!");
	var createDocType = $('input[name=createDocType]').filter(':checked').val();//creditNote debitNote revisedInvoice deleteInvoice newInvoice
	var errFlag1 = false;
	var errFlag2 = false;
	var errFlag3 = false;
	if(createDocType == 'creditNote' || createDocType == 'debitNote'){
		var toExecute = true;
		var inputData = getInputFormCnDnDataJson();
		if(createDocType == 'creditNote'){
			$('.loader').show();
			
			errFlag1 = checkForTotalCnDnValueAtServerSide(inputData); 
			$('.loader').fadeOut("slow");
		}
		
		if($("#invoiceSequenceType").val() != 'Auto'){
			errFlag2 = validateTextField("invoiceNumber","invoiceNumber-csv-id","This field is required");
			errFlag3 = checkIfInvoiceNumberAlreadyPresent($("#invoiceNumber").val());
			if(errFlag3){
				$("#invoiceNumber").addClass("input-error").removeClass("input-correct");
				$("#invoiceNumber-duplicate-csv-id").text("Invoice Number Already Present");
				$("#invoiceNumber-duplicate-csv-id").show();
			}
			
			if(errFlag2 || errFlag3){
				toExecute = false;
			}
		}
		
		if(!errFlag1 && toExecute){
			$('.loader').show();
			cndnBackendResponse = calculateTaxForCnDn(inputData);
			$("#secondPage").hide();
			$("#previewCnDnInvoiceDiv").show();
			if(createDocType == 'creditNote'){
				$("#previewBreadcrum").text("Preview Credit Note");
				$("#finalSubmitCnDnId").text("Send Credit Note");
			}else{
				$("#previewBreadcrum").text("Preview Debit Note");
				$("#finalSubmitCnDnId").text("Send Debit Note");
			}
			showUserDetailsInPreview(cndnBackendResponse);
			showCustomerDetailsBillToDiv(cndnBackendResponse);
			showServiceListDetailsInCnDnDiv(cndnBackendResponse);
			$('.loader').fadeOut("slow");
		}
		
	}else if(createDocType == 'revisedInvoice' || createDocType == 'newInvoice'){
		var toExecute = true;
		if(createDocType == 'newInvoice'){
			if($("#invoiceSequenceType").val() != 'Auto'){
				errFlag2 = validateTextField("invoiceNumber","invoiceNumber-csv-id","This field is required");
				errFlag3 = checkIfInvoiceNumberAlreadyPresent($("#invoiceNumber").val());
				if(errFlag3){
					$("#invoiceNumber").addClass("input-error").removeClass("input-correct");
					$("#invoiceNumber-duplicate-csv-id").text("Invoice Number Already Present");
					$("#invoiceNumber-duplicate-csv-id").show();
				}
				
				if(errFlag2 || errFlag3){
					toExecute = false;
				}
			}
		}
		if(toExecute){
			$('.loader').show();
			$("#secondPage").hide();
			$("#previewInvoiceDiv").show();
			if(createDocType == 'revisedInvoice'){
				$("#generateInvoicePageHeaderPreview").text("Preview Revised Note");
				$("#finalRevisedSubmitId").text("Create Revised Note");
			}else{
				$("#generateInvoicePageHeaderPreview").text("Preview New Invoice");
				$("#finalRevisedSubmitId").text("Create New Invoice");
			}
			invoiceInputJson = generateInvoiceInputJson();
			responseJson = callEditInvoicePost(invoiceInputJson);
			showUserDetailsInInvoicePreview(responseJson);
			showBillToAndShipToInPreviewDiv(responseJson);
			showServiceListDetailsInPreviewInvoiceDiv(responseJson);
			$('.loader').fadeOut("slow");
		}
			
	}else if(createDocType == 'deleteInvoice'){
		var inputData = callDeleteInvoiceJson();
		callDeleteInvoiceFromRR(inputData);
	}
	
	//var inputJson = generateInputJson();
	//callEditInvoicePost(inputJson);
	
});

$(document).on("click", "#finalSubmitCnDnId", function () {
	var createDocType = $('input[name=createDocType]').filter(':checked').val();//creditNote debitNote revisedInvoice deleteInvoice newInvoice
	var errFlag1 = false;
	if(createDocType == 'creditNote' || createDocType == 'debitNote'){
		$('.loader').show();
		
		var inputData = getInputFormCnDnDataJson();
		if(createDocType == 'creditNote' ){
			var errFlag1 = false;
			errFlag1 = checkForTotalCnDnValueAtServerSide(inputData); 
			$('.loader').fadeOut("slow");
		}
		if(!errFlag1){
			$('.loader').show();
			
			createCNDNAjax(inputData,createDocType);
			$('.loader').fadeOut("slow");
		}
		
	}
	
});

$(document).on("click", "#finalRevisedSubmitId", function () {
	var createDocType = $('input[name=createDocType]').filter(':checked').val();//creditNote debitNote revisedInvoice deleteInvoice newInvoice
	var rrType = $('select#rrType option:selected').val();
	var errFlag1 = false;
	$('.loader').show();
	setTimeout(function(){

		var inputData = generateInvoiceInputJson();
		if(createDocType == 'revisedInvoice'){
			createRevisedInvoiceAjax(inputData);
		}
		if(createDocType == 'newInvoice'){
			createNewInvoiceAjax(inputData);
		}
		$('.loader').fadeOut("slow");
	}, 3000);
	
	
	
});

$('#openingstockInventoryTab tbody').on('click', 'input[type="checkbox"]', function(e){
	if(this.checked){
		 this.parentNode.parentNode.children[4].firstElementChild.disabled=false;	
		$(this).parent().parent().next('tr.child').find('input').removeAttr('disabled');	
	}else{
		 this.parentNode.parentNode.children[4].firstElementChild.value='';
		 this.parentNode.parentNode.children[4].firstElementChild.disabled=true;
		 $(this).parent().parent().next('tr.child').find('input').val('');
		 $(this).parent().parent().next('tr.child').find('input').attr('disabled', 'true');
	}
});

$(document).on("keyup", "#invoiceNumber", function (e) {
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

$(document).on('change', 'input[name=createDocType]', function() {
	$("#invoiceNumberDiv").hide();
	var createDocType = $('input[name=createDocType]').filter(':checked').val();
	if(createDocType == 'newInvoice' || createDocType == 'creditNote' || createDocType == 'debitNote'){
		var invoiceSequenceType = $("#invoiceSequenceType").val();
		if(invoiceSequenceType != 'Auto'){
			$("#invoiceNumberDiv").show();
		}
		
	}
});

function initializeDatatable(){
    openingstockCheckboxTable = $('#openingstockInventoryTab').DataTable({
	   columnDefs: [ {
	        orderable: false,
	        checkboxes: {
	            'selectRow': true
	         },
	        targets:   0
	    } ],
	    select: {
	        style:    'multi',
	        selector: 'td:first-child'
	    },
	    rowReorder: {
	        selector: 'td:nth-child(2)'
	    },
	    responsive: true
	});
    $("#openingstockInventoryTab thead tr #checkboxtd input:checkbox").hide().attr('disabled',true);
}


function loadRRList(backRRtype){
    $.ajax({
    	url : "rrList",
    	method : "POST",
		dataType : "json",
		data : { _csrf_token : $("#_csrf_token").val()},
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
			
			$("#rrType").empty();
			$("#rrType").append('<option value="">Select</option>');
			$.each(json,function(i,value) {
				if($("#invDocType").val() == "billOfSupply" || $("#invDocType").val() == "eComBillOfSupply"){
					if((value.rrTypeName == 'salesReturn' || value.rrTypeName == 'quantityChange') && $("#invFor").val() == 'Service'){
						
					}else{
						if(value.rrTypeName != 'rateChange'){
							if(backRRtype == value.rrType){
								$("#rrType").append($('<option>').text(value.rrType).attr('value',value.rrTypeName).attr('selected','selected'));
							}else{
								$("#rrType").append($('<option>').text(value.rrType).attr('value',value.rrTypeName));
							}
							
						}
					}
					
				}else{
					if((value.rrTypeName == 'salesReturn' || value.rrTypeName == 'quantityChange') && $("#invFor").val() == 'Service'){
						
					}else{
						if(backRRtype == value.rrType){
							$("#rrType").append($('<option>').text(value.rrType).attr('value',value.rrTypeName).attr('selected','selected'));
						}else{
							$("#rrType").append($('<option>').text(value.rrType).attr('value',value.rrTypeName));
						}
					}
				}
				
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			$('.loader').fadeOut("slow");
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
         }
	}); 
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
					$("#invDataFiledToGstn").val(json.fileToGstn);
					$("#iterationNo").val(json.iterationNo);
					$("#invNo").val(json.invoiceNumber);
					$("#invFor").val(json.invoiceFor);
					$("#invDate").val(formatDateInDDMMYYYY(json.invoiceDate));
				}
			},
			error: function (data,status,er) {
		        getInternalServerErrorPage();   
		    }
			
		 });
	}
}

function resetDatatable(){
	$('#openingstockInventoryTab').DataTable().rows().remove();
	$('#openingstockInventoryTab').DataTable().destroy();
	initializeDatatable();	
}

function showDataInTable(rrType){
	$('.loader').show();
	resetDatatable();
	
	if(invoiceJson == ''){
		fetchInvoiceJson($("#unqIncDes").val());
	}
	if( rrType == "salesReturn"){
		callSalesReturn();
	}else if(rrType == "discountChange"){
		callDiscountChange();
	}else if(rrType == "salesPriceChange"){
		callSalesPriceChange();
	}else if(rrType == "rateChange"){
		callRateChange();
	}else if(rrType == "quantityChange"){
		callQuantityChange();
	}else if(rrType == "itemChange"){
		callItemChange();
	}else if(rrType == "partyChange"){
		callPartyChange();
	}else if(rrType == "multipleChanges"){
		callMultipleChanges();
	}
	
	$('.loader').fadeOut("slow");
}


function callItemChange(){
	
}

function callPartyChange(){
	
}

function callMultipleChanges(){
	
}

function getProductTaxRate(){
	if(taxRateJson == ''){
		 $.ajax({
				url : "getProductRateOfTaxDetails",
				headers: {
					_csrf_token : $("#_csrf_token").val()
				},
				type : "POST",
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
					taxRateJson = json; 
		         },
		         error: function (data,status,er) {
		        	 getInternalServerErrorPage();   
		        }
			});
	}   
}

function callPostEditInvoice(){
	$("#firstPage").hide();
	$("#secondPage").show();
	createSecondPageHtml();
	$('.loader').fadeOut("slow");
	var rrType = $('select#rrType option:selected').val();
	var isFiledToGSTN = $("#invDataFiledToGstn").val();
	if(isFiledToGSTN == "false"){
		//alert("Not filed to gstn");
		if( rrType == "salesReturn"){
			logicFilingSalesReturn("before");
		}else if(rrType == "discountChange"){
			logicFilingDiscountChange("before");
		}else if(rrType == "salesPriceChange"){
			logicFilingSalesPriceChange("before");
		}else if(rrType == "rateChange"){
			logicFilingRateChange("before");
		}else if(rrType == "quantityChange"){
			logicFilingQuantityChange("before");
		}else if(rrType == "itemChange"){
		}else if(rrType == "partyChange"){
		}else if(rrType == "multipleChanges"){
		}
	}else{
		//alert(" filed to gstn");
		if( rrType == "salesReturn"){
			logicFilingSalesReturn("after");
		}else if(rrType == "discountChange"){
			logicFilingDiscountChange("after");
		}else if(rrType == "salesPriceChange"){
			logicFilingSalesPriceChange("after");
		}else if(rrType == "rateChange"){
			logicFilingRateChange("after");
		}else if(rrType == "quantityChange"){
			logicFilingQuantityChange("after");
		}else if(rrType == "itemChange"){
		}else if(rrType == "partyChange"){
		}else if(rrType == "multipleChanges"){
		}
	}
}

function createSecondPageHtml(){
	
	$("#secondPage").html('');
	$("#secondPage").append(
			'<div class="form-wrap">'
		  		+'<div class="row">'
		  			+'<div class="col-md-4 diff">'
		                 +'<label class="form-section-title-pad15">&nbsp;</label>'
		                 +'<div class="radio radio-success radio-inline" id="creditNoteRadio">'
		                     +'<input type="radio" class="styled" name="createDocType" value="creditNote" id="radio1" checked="checked"/>'
		                     +'<label for="radio1">Create a Credit Note</label>'
		                 +'</div>'
		                 +'<div class="radio radio-success radio-inline" id="debitNoteRadio">'
					         +'<input type="radio" class="styled" name="createDocType" value="debitNote" id="radio2"/>'
		                     +'<label for="radio2">Create a Debit Note</label>'
		                 +'</div>'
		                 +'<div class="radio radio-success radio-inline" id="revisedInvoiceRadio">'
					         +'<input type="radio" class="styled" name="createDocType" value="revisedInvoice" id="radio3"/>'
		                     +'<label for="radio3">Edit Original Invoice</label>'
		                 +'</div>'
		                 +'<div class="radio radio-success radio-inline" id="deleteInvoiceRadio">'
					         +'<input type="radio" class="styled" name="createDocType" value="deleteInvoice" id="radio4"/>'
		                     +'<label for="radio4">Delete Invoice</label>'
		                 +'</div>'
		                 +'<div class="radio radio-success radio-inline" id="newInvoiceRadio">'
					         +'<input type="radio" class="styled" name="createDocType" value="newInvoice" id="radio5"/>'
		                     +'<label for="radio5">Create Amendment Invoice</label>'
		                 +'</div>'                               
		             +'</div>'
		             
		             +'<div class="col-md-4" id="invoice_date_datePicker">'
		                   +'<label for="">Document Date <span>*</span></label>'
		                   +'<input id="invoice_date" readonly="readonly" required>'               
		             +'</div>'
		             
		             +'<div class="col-md-4" style="display:none" id="invoiceNumberDiv" >'
		             	+'<label for="invoiceNumber">Invoice Number <span>*</span></label>'
		             	+'<input type="text" name="invoiceNumber" id="invoiceNumber" maxlength="20" required>'
		             	+'<span class="text-danger cust-error" id="invoiceNumber-csv-id"></span>'
		             	+'<span class="text-danger cust-error" id="invoiceNumber-duplicate-csv-id"></span>'
		             +'</div>'
		  		
		  		+'</div>'
		  		
		  		+'<div class="row">'
					+'<div class="col-md-12 button-wrap">'
						+'<button type="button" id="secondPageSave" style="width:auto;" class="btn btn-success blue-but">OK</button>'
						+'<button type="button" id="secondPageCancel" style="width:auto;" class="btn btn-success blue-but">Cancel</button>'
					+'</div>'
				+'</div>'
		  	 
	   		+'</div>'
	   		);
	
	setDatePickerForDate();
}

function showHideRadioButtons(creditNoteRadio,debitNoteRadio,revisedInvoiceRadio,deleteInvoiceRadio,newInvoiceRadio,selectedId,invoiceNumberDiv){
	
	if(creditNoteRadio == 'show'){
		$("#creditNoteRadio").show();
	}else{
		$("#creditNoteRadio").hide();
	}
	
	if(debitNoteRadio == 'show'){
		$("#debitNoteRadio").show();
	}else{
		$("#debitNoteRadio").hide();
	}
	
	if(revisedInvoiceRadio == 'show'){
		$("#revisedInvoiceRadio").show();
	}else{
		$("#revisedInvoiceRadio").hide();
	}
	
	if(deleteInvoiceRadio == 'show'){
		$("#deleteInvoiceRadio").show();
	}else{
		$("#deleteInvoiceRadio").hide();
	}
	
	if(newInvoiceRadio == 'show'){
		$("#newInvoiceRadio").show();
	}else{
		$("#newInvoiceRadio").hide();
	}
	
	if($("#invoiceSequenceType").val() != 'Auto'){
		if(invoiceNumberDiv == 'show'){
			$("#invoiceNumberDiv").show();
		}else{
			$("#invoiceNumberDiv").hide();
		}
	}
	
	
	$('input:radio[name="createDocType"]').filter('[value="'+selectedId+'"]').attr('checked', true);
	
}

function generateInvoiceInputJson(){
	 var createDocType = $('input[name=createDocType]').filter(':checked').val();//creditNote debitNote revisedInvoice deleteInvoice newInvoice
	 var invoiceNo = $("#unqIncDes").val();
	 var documentType = $("#invDocType").val();
	 var modeOfCreation = $("#modeOfCreation").val();//EDIT_INVOICE
	 var rrType = $('select#rrType option:selected').val(); //salesReturn discountChange salesPriceChange rateChange quantityChange itemChange partyChange multipleChanges
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
	  serviceListArray = new Array();
	  acListArray = new Array();  
	  footerNote = invoiceJson.footerNote;
	  locationStoreId = 0;
	  if($("#invoiceNumber").val()){
		  invNumber = $("#invoiceNumber").val();
	  }
	   
	 //RND - End
	  
	 //Generate respective json as per rrType - Start
	  if(rrType == 'salesReturn'){
			idValName = 'returnedSR_';
			serviceListArray  = revisedSalesReturnJson();
		}else if(rrType == 'discountChange'){
			idValName = 'revisedDC_';
			serviceListArray  = revisedDiscountChangeJson();
		}else if(rrType == 'salesPriceChange'){
			idValName = 'revisedSPC_';
			serviceListArray  = revisedSalesPriceChangeJson();
		}else if(rrType == 'rateChange'){
			idValName = 'revisedRC_';
			serviceListArray  = revisedRateChangeJson();
			
		}else if(rrType == 'quantityChange'){
			idValName = 'revisedQC_';
			serviceListArray  = revisedQuantityChangeJson();
		
		}else if(createDocType == 'itemChange'){
			idValName = '';
			
		}else if(createDocType == 'partyChange'){
			idValName = '';
			
		}else if(createDocType == 'multipleChanges'){
			idValName = '';
			
		} 
	  
	  //Generate respective json as per rrType - End
	
	 
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
		"invoiceNumber" : invNumber
	};
	 
	console.log(JSON.stringify(inputJson)); 
	return inputJson;  
}

function getLineItemsFromTable(rrType,idValName){
	var serviceListArray = new Array();

	var jsonObject;
	var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	table_data.each(function(value,index){
		if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
			var currentVal = parseFloat($("#returnedSR_"+(index+1)).val());
			var servicePkId = $("#common_service_id_"+(index+1)).val();
			
			jsonObject = new Object();
			jsonObject.serviceId = servicePkId;
			if(rrType == 'salesReturn' || rrType == 'quantityChange'){
				jsonObject.quantity = currentVal;
			}
			if(rrType == 'discountChange'){
				jsonObject.offerAmount = currentVal;
			}
			if(rrType == 'salesPriceChange'){
				jsonObject.previousAmount = currentVal;
			}
			if(rrType == 'rateChange'){
				jsonObject.igstPercentage = currentVal;
			}
			serviceListArray.push(jsonObject);
		}
	});
	return serviceListArray;
}

function dummyJson(){
	var inputJson ={"id":"4643","invoiceFor":"Product","customerDetails":{"id":2594,"custName":"Ashish","custType":"Individual","custEmail":"ashish.k@ril.com","contactNo":"9898989898","custAddress1":"Ghansoli","pinCode":400007,"custCity":"MUMBAI","custState":"Maharashtra","createdOn":"Feb 22, 2019 3:52:56 PM","createdBy":"4508","refId":4508,"custGstId":"","custCountry":"India","status":"1","refOrgId":450},"servicePlace":"Ghansoli,MUMBAI","serviceCountry":"Maharashtra","deliveryPlace":27,"deliveryCountry":"India","poDetails":"","typeOfExport":"","serviceList":[{"serviceId":4882,"quantity":30},{"serviceId":4883,"quantity":5}],"discountType":"Percentage","discountValue":0,"discountAmount":0,"discountRemarks":"","additionalChargesType":"Percentage","additionalChargesValue":0,"additonalAmount":0,"additionalChargesRemark":"","amountAfterDiscount":5685,"totalTax":2196.25,"invoiceValue":7881.25,"gstnStateId":35,"invoiceDateInString":"","gstnStateIdInString":"35ASDCL1455L1ZS","invoicePeriodFromDateInString":"","invoicePeriodToDateInString":"","billToShipIsChecked":"Yes","shipToCustomerName":"","shipToAddress":"","shipToCity":"","shipToPincode":"","shipToState":"","shipToStateCode":"","shipToStateCodeId":"","location":"1","customerEmail":"ashish.k@ril.com","ecommerce":"No","ecommerceGstin":"","reverseCharge":"No","addChargesList":[],"footerNote":"","documentType":"invoice","isCustomerRegistered":"No","modeOfCreation":"EDIT_INVOICE","iterationNo":0,"lastRRType":"","lastRR":"","lastRRInvoiceNumber":"","locationStoreId":0,"rrTypeForCreation":"salesReturn","createDocType":"revisedInvoice"};//{"id":0,"invoiceFor":"Product","customerDetails":{"id":2594,"custName":"[9898989898] - Ashish","custAddress1":"Ghansoli","custCity":"MUMBAI","custState":"Maharashtra","status":"1"},"servicePlace":"Ghansoli,MUMBAI","serviceCountry":"Maharashtra","deliveryPlace":27,"deliveryCountry":"India","poDetails":"","typeOfExport":"","serviceList":[{"serviceId":2007,"unitOfMeasurement":"GROSS","rate":450.0,"quantity":50.0,"amount":22100.0,"calculationBasedOn":"Amount","taxAmount":7693.0,"sgstAmount":0.0,"ugstAmount":0.0,"cgstAmount":0.0,"igstAmount":6188.0,"previousAmount":22500.0,"serviceIdInString":"Non Zero Table","gstnStateId":35,"deliveryStateId":27,"billingFor":"Product","amountAfterDiscount":22100.0,"amountAfterCalculation":0.0,"offerAmount":400.0,"typeOfExport":"","cess":1505.0,"categoryType":"CATEGORY_WITH_IGST","sgstPercentage":0.0,"ugstPercentage":0.0,"cgstPercentage":0.0,"igstPercentage":28.0,"hsnSacCode":"95044000","additionalAmount":0.0,"hsnSacDescription":"ARTICLES FOR FUNFAIR, TABLE OR PARLOUR GAMES, INCLUDING PINTABLES, BILLIARDS, SPECIAL TABLES FOR CASINO GAMES AND AUTOMATIC BOWLING ALLEY EQUIPMENT - PLAYING CARDS","cndnValuePreviouslyAdded":0.0,"valueAfterTax":0.0,"cnDnAppliedRate":0.0,"taxableValue":0.0,"diffPercent":"N","discountTypeInItem":"Value","advolCess":5.0,"advolCessAmount":1105.0,"nonAdvolCess":400.0,"iterationNo":1},{"serviceId":2009,"unitOfMeasurement":"GRAMMES","rate":55.0,"quantity":10.0,"amount":530.0,"calculationBasedOn":"Amount","taxAmount":442.4,"sgstAmount":0.0,"ugstAmount":0.0,"cgstAmount":0.0,"igstAmount":26.5,"previousAmount":550.0,"serviceIdInString":"Non Zero Sugar","gstnStateId":35,"deliveryStateId":27,"billingFor":"Product","amountAfterDiscount":530.0,"amountAfterCalculation":0.0,"offerAmount":20.0,"typeOfExport":"","cess":415.9,"categoryType":"CATEGORY_WITH_IGST","sgstPercentage":0.0,"ugstPercentage":0.0,"cgstPercentage":0.0,"igstPercentage":5.0,"hsnSacCode":"04081100","additionalAmount":0.0,"hsnSacDescription":"BIRDS EGGS, NOT IN SHELL, AND EGG YOLKS, FRESH, DRIED, COOKED BY STEAMING OR BY BOILING IN WATER, MOULDED, FROZEN OR OTHERWISE PRESERVED, WHETHER OR NOT CONTAINING ADDED SUGAR OR OTHER SWEETENING MATTER EGG YOLKS : DRIED","cndnValuePreviouslyAdded":0.0,"valueAfterTax":0.0,"cnDnAppliedRate":0.0,"taxableValue":0.0,"diffPercent":"N","discountTypeInItem":"Value","advolCess":3.0,"advolCessAmount":15.9,"nonAdvolCess":400.0,"iterationNo":1}],"discountType":"Percentage","discountValue":0.0,"discountAmount":0.0,"discountRemarks":"","additionalChargesType":"Percentage","additionalChargesValue":0.0,"additonalAmount":0.0,"additionalChargesRemark":"","amountAfterDiscount":22630.0,"totalTax":8135.4,"invoiceValue":30765.4,"invoiceDateInString":"22-02-2019","gstnStateId":35,"gstnStateIdInString":"35ASDCL1455L1ZS","invoicePeriodFromDateInString":"","invoicePeriodToDateInString":"","billToShipIsChecked":"Yes","shipToCustomerName":"","shipToAddress":"","shipToCity":"","shipToPincode":"","shipToState":"","shipToStateCode":"","shipToStateCodeId":"","location":"1","customerEmail":"ashish.k@ril.com","ecommerce":"No","ecommerceGstin":"","reverseCharge":"No","invoiceValueAfterRoundOff":30765.0,"deleteYn":"N","uploadToJiogst":"false","saveToGstn":"false","submitToGstn":"false","fileToGstn":"false","addChargesList":[],"cnDnList":[],"cnDnAdditionalList":[],"footerNote":"","documentType":"invoice","isCustomerRegistered":"No","otherCharges":0.0,"modeOfCreation":"CREATE_INVOICE","iterationNo":1,"lastRRType":"","lastRR":"","lastRRInvoiceNumber":""}; 
	//{"id":"4631","invoiceFor":"","customerDetails":{},"servicePlace":"","serviceCountry":"","deliveryPlace":"","deliveryCountry":"","poDetails":"","typeOfExport":"","serviceList":[{"serviceId":"2007","quantity":2},{"serviceId":"2009","quantity":11},{"serviceId":"2011","quantity":32}],"discountType":"","discountValue":0,"discountAmount":0,"discountRemarks":"","additionalChargesType":"","additionalChargesValue":0,"additonalAmount":0,"additionalChargesRemark":"","amountAfterDiscount":0,"totalTax":0,"invoiceValue":0,"gstnStateId":"","invoiceDateInString":"","gstnStateIdInString":"","invoicePeriodFromDateInString":"","invoicePeriodToDateInString":"","billToShipIsChecked":"No","shipToCustomerName":"","shipToAddress":"","shipToCity":"","shipToPincode":"","shipToState":"","shipToStateCode":"","shipToStateCodeId":"","location":"0","customerEmail":"","ecommerce":"No","ecommerceGstin":"","reverseCharge":"No","addChargesList":[],"footerNote":"","documentType":"invoice","isCustomerRegistered":"","modeOfCreation":"EDIT_INVOICE","iterationNo":0,"lastRRType":"","lastRR":"","lastRRInvoiceNumber":"","locationStoreId":0,"rrTypeForCreation":"salesReturn"};
	callEditInvoicePost(inputJson);
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

function setBreadCrumHeaders(){
	var documentType = $("#invDocType").val();
	var invoiceNumber = $("#invNo").val();
	$("#generateInvoiceDefaultPageHeader").html("");
	var breadCrumHeader = '';
	
	if(documentType == 'invoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Revise & Returns</strong>';
	}else if(documentType == 'billOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Revise & Returns </strong>';
	}else if(documentType == 'rcInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Reverse Charge Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Revise & Returns</strong>';
	}else if(documentType == 'eComInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Revise & Returns</strong>';
	}else if(documentType == 'eComBillOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Revise & Returns</strong>';
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
	document.previewInvoice.conditionValue.value =documentType;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
}

function getRevAndRetByPartyChnage(){
	var idValue = $("#unqIncDes").val();
	$('.loader').show();
	document.previewInvoice.action = "./getRRByPartyChange";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
	$('.loader').fadeOut("slow");
}

function getRevAndRetByItemChange(){
	var idValue = $("#unqIncDes").val();
	$('.loader').show();
	document.previewInvoice.action = "./getRRByItemChange";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
	$('.loader').fadeOut("slow");
}

function getRevAndRetByMultipleChanges(){
	var idValue = $("#unqIncDes").val();
	$('.loader').show();
	document.previewInvoice.action = "./getRRByMultipleChanges";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
	$('.loader').fadeOut("slow");
}

function formatDateInDDMMYYYY(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
	var actualDate = (date.getDate() < 10) ? ("0"+date.getDate()) : date.getDate();
	return (actualDate + "-"+month+"-"+date.getFullYear());	
}

function setDatePickerForDate(){
	var invoiceDate = $("#invDate").val();
	
	$('#invoice_date').datepicker({
		/*uiLibrary: 'bootstrap',*/
		showButtonPanel: true,
		changeMonth: true, 
		changeYear: true,
		firstDay: 1,
	    dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate: invoiceDate, 
	    maxDate: new Date,
	    onSelect: function(dateAsString,evnt) {
	    	
	    	var newDateFormat = $.datepicker.formatDate( "yy-mm-dd", new Date( dateAsString.split('-')[2], dateAsString.split('-')[1] - 1, dateAsString.split('-')[0] ));
	    	//checkDateForInvoice(newDateFormat);
	    	
	    }
	}).datepicker("setDate", new Date());
	
}

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

