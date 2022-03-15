var globalTotalTaxableAmt = 0;
var globalCgstAmt = 0;
var globalSgstAmt = 0;
var globalIgstAmt = 0;
var globalCessAdvolAmt = 0;
var globalCessNonAdvolAmt = 0;
var globalOtherAmt = 0;
var globalTotalInvAmt = 0;

$(document).ready(function(){
	$("#otherSubType").hide();
	loadTransactionType();
	loadSubTransactionType();
//	loadDocumentType();
	defaultloadTransaportationDetails();
	 $('.loader').fadeOut("slow");
	
	$("#transctionType").on("change", function() {
		$('.loader').show();
		$('input[name=buyerSellerType]').attr('disabled', false);
		var optionValue = $('select#transctionType option:selected').val();
		if(optionValue=="O"){
			changeLabelName("buySelTypeText","Buyer Type");
		}else if(optionValue=="I"){
			changeLabelName("buySelTypeText","Seller Type");
		}else{
			changeLabelName("buySelTypeText","Buyer/Seller Type");
		}
		
		$('input[name=buyerSellerType]').prop("checked", false);
		$("#billFromStateDiv").show();
		$("#billToStateDiv").show();
		loadSubTransactionType();
		resetBillFromFields();
		resetBillToFields();
		loadBillToOrFrom($("#transctionType").val());
		resetItemDetails();
		$('.igstrate').prop("disabled", true);
		$('.cgstsgstrate').prop("disabled", true);
		$('.loader').fadeOut("slow");
	});
	
	$("#subType").change(function(){
		$('.loader').show();
		$('input[name=buyerSellerType]').attr('disabled', false);
		var transTypeOptionValue = $('select#transctionType option:selected').val();
		var optionValue = $('select#subType option:selected').val();  ;
		if($('select#subType option:selected').text() == "Others" || optionValue == 8){
			$("#otherSubType").show();
		}else{
			$("#otherSubType").hide();
		}
		$('input[name=buyerSellerType]').prop("checked", false);
		$("#billFromStateDiv").show();
		$("#billToStateDiv").show();
		if(transTypeOptionValue=="O"){
			if($('select#subType option:selected').text() == "Export" || optionValue == 3){
				$("#buyerSellerType-req").hide();
				$("#billToGstin-back-req").hide();
				$("#billToStateDiv").show();
				resetBillToFields();
				$('input[name=buyerSellerType]').prop("checked", false);
				$("input[name=buyerSellerType]").attr('disabled', true);
				$("#billToGstin").val('URP').prop("disabled", true);
				$("#billToName").prop("readonly", false);
				$("#billToState").prop("disabled", false);
				$("#shipToPincode").prop("disabled", true);
				loadURPState("billToState");
			}else{
				resetBillToFields();
				$("#billToState-req").hide();
				$('input[name=buyerSellerType]').prop("checked", false);
				$("input[name=buyerSellerType]").attr('disabled', false);
			} 
		}else if(transTypeOptionValue=="I"){
			if($('select#subType option:selected').text() == "Import" || optionValue == 2){
				$("#buyerSellerType-req").hide();
				$("#billFromGstin-back-req").hide();
				$("#billFromStateDiv").show();
				resetBillFromFields();
				$('input[name=buyerSellerType]').prop("checked", false);
				$("input[name=buyerSellerType]").attr('disabled', true);
				$("#billFromGstin").val('URP').prop("disabled", true);
				$("#billFromName").prop("readonly", false);
				$("#billFromState").prop("disabled", false);
				$("#dispatchFromPincode").prop("disabled", true);
				loadURPState("billFromState");
			}else{
				resetBillFromFields();
				$('input[name=buyerSellerType]').prop("checked", false);
				$("input[name=buyerSellerType]").attr('disabled', false);
			}
		}else{
			$('input[name=buyerSellerType]').prop("checked", false);
			$("input[name=buyerSellerType]").attr('disabled', false);
			resetBillFromFields();
			resetBillToFields();
		}
		loadDocumentType();
		resetItemDetails();
		$('.igstrate').prop("disabled", true);
		$('.cgstsgstrate').prop("disabled", true);
		$('.loader').fadeOut("slow");
	}); 
	
	$("#documentType").on("change", function() {
		$('.loader').show();
		$('input[name=buyerSellerType]').attr('disabled', false);
		$('input[name=buyerSellerType]').prop("checked", false);
		var optionValue = $('select#transctionType option:selected').val();
		loadBillToOrFromByDocType($("#transctionType").val(),$('select#subType option:selected').text(),$("#documentType").val());
		$('.loader').fadeOut("slow");
	});
	
	
	$('input[name=buyerSellerType]').click(function() {
		$("#buyerSellerType-req").hide();
		var transcationtype = $("#transctionType").val();
		var subTypeOptionValue = $("#subType").val();
		var subType = $('select#subType option:selected').text();
		var buyerSellerTypeVal = $('input[name=buyerSellerType]').filter(':checked').val();
		if(transcationtype != '' && transcationtype == 'O' && subTypeOptionValue != '' && subType != "Export"){
			if(buyerSellerTypeVal == 'UR'){
				$("#billToGstin-back-req").hide();
				$("#billToStateDiv").hide();
				resetBillToFields();
				$("#billToGstin").val('URP').prop("disabled", true);
				$("#billToName").prop("readonly", false);
				$("#billToState").prop("disabled", true);
			}else{
				$("#billToStateDiv").show();
				resetBillToFields();			 			 
			}
		}else if(transcationtype != '' && transcationtype == 'I' && subTypeOptionValue != '' && subType != "Import"){
			if(buyerSellerTypeVal == 'UR'){
				$("#billFromGstin-back-req").hide();
				$("#billFromState-back-req").hide();
				$("#billFromStateDiv").hide();
				resetBillFromFields();
				$("#billFromGstin").val('URP').prop("disabled", true);
				$("#billFromName").prop("readonly", false);
				$("#billFromState").prop("disabled", true);
			}else{
				$("#billFromStateDiv").show();
				resetBillFromFields();			 			 
			}
		}else{
			$('input[name=buyerSellerType]').prop("checked", false);
			$('input[name=buyerSellerType]').attr('disabled', false);
			if(transcationtype == ''){
				$("#transctionType").css({"border-color" : "#ff0000"});
				$("#transctionType").focus();
			}	
			if(subTypeOptionValue == ''){
				$("#subType").css({"border-color" : "#ff0000"});
				$("#subType").focus();
			}	
		}	
		resetItemDetails();
		$('.igstrate').prop("disabled", true);
		$('.cgstsgstrate').prop("disabled", true);
	});
	
	$("#billFromState").change(function(){
		var optionVal = $('select#billFromState option:selected').val();		
		if($('select#billFromState option:selected').text == 'Select' || optionVal == ''){
			$("#billFromState").css({"border-color" : "#ff0000"});
			$("#billFromState-req").text('This field is required.');
			$("#billFromState-req").show();
			resetExportReflection();
		}else{
			$("#billFromState").css({"border-color" : "#498648"});
			$("#billFromState-req").hide();
			$("#billFromStateId").val(optionVal);
			$("#billFromStateValue").val($("#billFromState option:selected").text());
			$("#dispatchFromPincode").val('999999').css({"border-color" : "#498648"});
			$("#dispatchFromState").val($("#billFromState option:selected").text());
			$("#dispatchFromStateId").val($("#billFromStateId").val());
			$("#dispatchFromStateValue").val($("#billFromStateValue").val());
		}
	});
	
	$("#billToState").change(function(){
		var optionValue = $('select#billToState option:selected').val();		
		if($('select#billToState option:selected').text() == "Select" || optionValue == ''){
			$("#billToState").css({"border-color" : "#ff0000"});
			$("#billToState-req").text('This field is required.');
			$("#billToState-req").show();
			resetExportReflection();
		}else{
			$("#billToState").css({"border-color" : "#498648"});
			$("#billToState-req").hide();
			$("#billToStateId").val(optionValue);
			$("#billToStateValue").val($("#billToState option:selected").text());
			$("#shipToPincode").val('999999').css({"border-color" : "#498648"});
			$("#shipToState").val($("#billToState option:selected").text());
			$("#shipToStateId").val($("#billToStateId").val());
			$("#shipToStateValue").val($("#billToStateValue").val());
		}		
	}); 
	
}); 

function getInputTransactionOrDocumentType(val){
	 var inputData = {
				"masterType" : val,
				"masterSubType" : null, 
				"masterCode" : "",
   				"isActive" : ""
     };
	 return inputData;
}

function getInputDocumentType(val1, val2){
	 var inputData = {
				"masterType" : val1,
				"masterSubType" : val2, 
				"masterCode" : "",
  				"isActive" : ""
    };
	 return inputData;
}

function loadTransactionType(){
	var generateInputJsonData = getInputTransactionOrDocumentType("Transaction");
	callAjaxSubTransOrDocumentType(generateInputJsonData,"transctionType"); 
}

function loadSubTransactionType(){
	var transactionType = $('select#transctionType option:selected').text();	
	var generateInputJsonData = getInputTransactionOrDocumentType(transactionType);
	callAjaxSubTransOrDocumentType(generateInputJsonData,"subType");
}

function loadDocumentType(){
	var generateInputJsonData = getInputDocumentType($('select#subType option:selected').text(), $('select#transctionType option:selected').text());
	callAjaxSubTransOrDocumentType(generateInputJsonData,"documentType"); 
}

function loadURPState(fieldId){
	var URPJsonData = getInputTransactionOrDocumentType("URP");
	callAjaxSubTransOrDocumentType(URPJsonData,fieldId); 
}

function resetSelectField(fieldId){
	$("#" + fieldId).empty();
	$("#" + fieldId).append('<option value="">Select</option>');
}

function callAjaxSubTransOrDocumentType(generateInputJsonData,fieldId){
	var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var appCode = $("#appCode").val(); 
	var ipUsr = $("#ipUsr").val();
	$.ajax({
		url : "ewaybill/getewbwimaster",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
		data : JSON.stringify(generateInputJsonData),
		async : false,
		success:function(json){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			resetSelectField(fieldId);
			if(json.status == 'failure'){
	
			}else{				
				$.each(json,function(i,value) {
					$("#" + fieldId).append($('<option>').text(value.value).attr('value',value.code));
				});
			}			
         },
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
	}); 
}

function getState(stateName,id,optionId,optionValue){
    var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var appCode = $("#appCode").val();
	var ipUsr = $("#ipUsr").val();
	$.ajax({
		url : "ewaybill/getewbstatecodemaster" ,
		type : "post",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
		async : false,
		success : function(json) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			$.each(json,function(i,value) {
				if(stateName == value.stateName){
					$("#" + id).append($('<option>').text(value.stateName).attr('value',value.stateId).attr('selected','selected'));
					$("#" + optionId).val(value.stateId);
					$("#" + optionValue).val(value.stateName);
				}else{
					$("#" + id).append($('<option>').text(value.stateName).attr('value',value.stateId));
				}					
			});
		},
        error: function (data,status,er) {        	 
       	 getInternalServerErrorPage();   
       }
	});
}

function getInputBillFormOrToData(gstin){
	 var inputData = {
				"gstin" : gstin
     };
	 return inputData;
}

function loadBillToOrFrom(val){
	 var gstin = $("#gstin").val();
	if(val=='O'){
		 $("#billFromGstin").prop("disabled", true);
		 $("#billToGstin").prop("disabled", false);
		 $("#billFromGstin").val(gstin);
		 $("#billFromName").val("");
		 BillFromDataLoad(gstin);
		 resetBillToFields();	
	}else if(val=='I'){
		 $("#billToGstin").prop("disabled", true);
		 $("#billFromGstin").prop("disabled", false);
		 $("#billToGstin").val(gstin);
		 $("#billToName").val("");
		 BillToDataLoad(gstin);
		 resetBillFromFields();		 
	}else if(val==''){
		$("#subType").css({"border-color" : "#2f65b0"});
		$("#otherSubType").hide();
		$("#otherSubTypes").val("");		
		resetBillFromFields();		
		resetBillToFields();	
	}
}

function loadBillToOrFromByDocType(trnType, subType, docType){
	 var gstin = $("#gstin").val();
	if(trnType=='O'){
		if(subType == 'Recipient Not Known' && docType == 'CHL'){
			BillFromDataOnDocTypeSel(gstin);
		} else if(subType == 'Recipient Not Known' && docType == 'OTH'){
			BillFromDataOnDocTypeSel(gstin);
		} else if(subType == 'For Own Use' && docType == 'CHL'){
			BillFromDataOnDocTypeSel(gstin);
		} else if(subType == 'Exhibition or Fairs' && docType == 'CHL'){
			BillFromDataOnDocTypeSel(gstin);
		} else if(subType == 'Line Sales' && docType == 'CHL'){
			BillFromDataOnDocTypeSel(gstin);
		}  
	}else if(trnType=='I'){
		if(subType == 'Exhibition or Fairs' && docType == 'CHL'){
			 BillToDataOnDocTypeSel(gstin);
		} else if(subType == 'For Own Use' && docType == 'CHL'){
			 BillToDataOnDocTypeSel(gstin);
		} else if(subType == 'SKD/CKD' && docType == 'BOE'){
			BillToDataOnSubTypeSel(gstin);
		} 
		
	}
}

function BillFromDataOnDocTypeSel(gstin){
	$("#billToGstin").prop("disabled", true);
	$("#billToGstin").val(gstin);
	BillToDataLoad(gstin);
	$(".buyerSellerType").val('R');
	$('input[name=buyerSellerType]').attr('disabled', true);
	
}

function BillToDataOnDocTypeSel(gstin){
	$("#billFromGstin").prop("disabled", true);
	 $("#billFromGstin").val(gstin);
	 BillFromDataLoad(gstin);
	 $(".buyerSellerType").val('R');
	 $('input[name=buyerSellerType]').attr('disabled', true);
}

function  BillToDataOnSubTypeSel(gstin){
	$('input[name=buyerSellerType]').attr('disabled', true);
	$("#billFromGstin-back-req").hide();
	$("#billFromState-back-req").hide();
	$("#billFromStateDiv").hide();
	resetBillFromFields();
	$("#billFromGstin").val('URP').prop("disabled", true);
	$("#billFromName").prop("readonly", false);
	$("#billFromState").prop("disabled", true);
}

function BillFromDataLoad(gstin){	   
	var generateInputJsonData = getInputBillFormOrToData(gstin);
    var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var appCode = $("#appCode").val();
	var ipUsr = $("#ipUsr").val();
    if(gstin != '' && validateGSTINWithRegex(gstin,"billFromGstin","billFromGstin-back-req")){
		    $.ajax({
				url : "ewaybill/getCustomerGstinDetails",
				type : "POST",
				contentType : "application/json",
				dataType : "json",
				headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
				data : JSON.stringify(generateInputJsonData),
				async : false,
				success : function(jsonVal) {
					if (isValidSession(jsonVal) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}

					if(jsonVal.status == "failure"){
						 $("#billFromGstin").css({"border-color" : "#ff0000"});
						 $("#billFromGstin-back-req").text(jsonVal.error_desc);
						 $("#billFromGstin-back-req").show();
					}else{
						 $("#billFromGstin").css({"border-color" : "#498648"});
						 $("#billFromGstin-back-req").hide();
						 $("#billFromName").val(jsonVal.trader_name);
						 $("#dispatchFromAddress").val(jsonVal.trader_address.bnm+"  "+jsonVal.trader_address.st+"  "+jsonVal.trader_address.dst);
						// if(jsonVal.trader_address.dst === ""){
							 $("#dispatchFromPlace").val(jsonVal.trader_address.loc);
						/* }else{
							 $("#dispatchFromPlace").val(jsonVal.trader_address.dst);
						 }*/
						 $("#dispatchFromPincode").val(jsonVal.trader_address.pncd);
						 $("#dispatchFromState").val(jsonVal.trader_address.stcd);
						 getState(jsonVal.trader_address.stcd,"billFromState","billFromStateId","billFromStateValue");								
						 getState(jsonVal.trader_address.stcd,"dispatchFromState","dispatchFromStateId","dispatchFromStateValue");
					}
				},
		         error: function (data,status,er) {        	 
		        	 getInternalServerErrorPage();   
		        }
		    });	    
    }else{
		$("#billFromGstin").focus();
    	$("#billFromGstin").css({"border-color" : "#ff0000"});
    	$("#billFromGstin-back-req").text('This field is required and should be in a proper format.');
		$("#billFromGstin-back-req").show();
    }
}
 
function BillToDataLoad(gstin){ 
	var generateInputJsonData = getInputBillFormOrToData(gstin);
    var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var appCode = $("#appCode").val();	
	var ipUsr = $("#ipUsr").val();	
    if(gstin != '' && validateGSTINWithRegex(gstin,"billToGstin","billToGstin-back-req")){
		    $.ajax({
				url : "ewaybill/getCustomerGstinDetails",
				type : "POST",
				contentType : "application/json",
				dataType : "json",
				headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
				data : JSON.stringify(generateInputJsonData),
				async : false,
				success : function(jsonVal) {
					if (isValidSession(jsonVal) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}

					if(jsonVal.status == "failure"){
						 $("#billToGstin").css({"border-color" : "#ff0000"});
						 $("#billToGstin-back-req").text(jsonVal.error_desc);
						 $("#billToGstin-back-req").show();
					}else{
						 $("#billToGstin").css({"border-color" : "#498648"});
						 $("#billToGstin-back-req").hide();
						 $("#billToGstin-back-req").hide();
						 $("#billToName").val(jsonVal.trader_name);
						 $("#shipToAddress").val(jsonVal.trader_address.bnm+"  "+jsonVal.trader_address.st+"  "+jsonVal.trader_address.dst);
						 //if(jsonVal.trader_address.dst === ""){
							 $("#shipToPlace").val(jsonVal.trader_address.loc);
						 /*}else{
							 $("#shipToPlace").val(jsonVal.trader_address.dst);
						 }*/						 
						 $("#shipToPincode").val(jsonVal.trader_address.pncd);
						 $("#shipToPincode").css({"border-color" : "#498648"});
						 $("#shipToState").val(jsonVal.trader_address.stcd);
						 getState(jsonVal.trader_address.stcd,"billToState","billToStateId","billToStateValue");
						 getState(jsonVal.trader_address.stcd,"shipToState","shipToStateId","shipToStateValue");
					}
				},
		         error: function (data,status,er) {        	 
		        	 getInternalServerErrorPage();   
		        }
		    });	   
    }else{
		$("#billToGstin").focus();
    	$("#billToGstin").css({"border-color" : "#ff0000"});
    	$("#billToGstin-back-req").text('This field is required and should be in a proper format.');
		$("#billToGstin-back-req").show();
    }
}

$('input[name=mode]').click(function(){
	$('.loader').show();
	loadTransaportationDetails();	
	$('.loader').fadeOut("slow");
});

function defaultloadTransaportationDetails(){
	$("#vechicleNo").prop("disabled", false);
	$("input[name=vechicle_type]").attr('disabled', false);
	$("#transporterId").val("");
	$("#vechicleNo").val("");
	$("#vechicleNo").css({"border-color" : "#ff0000"});
	$("#transporterDocumentNo").val("");
	changeFieldValue("transporterName","");	
	$("#transporterDocNo").show();
	$("#rrNo").hide();
	$("#airwayNo").hide();
	$("#ladingNo").hide();
	changeLabelName("transporterDate","Transporter Doc Date");
}

function changeLabelName(id,val){
	$("#"+id).text(val);
}

function changeFieldValue(id,val){
	$("#"+id).val(val);
}

function loadTransaportationDetails(){
	var mode = $('input[name=mode]').filter(':checked').val();
	if(mode==1){
		defaultloadTransaportationDetails();
	}else {
		$("#transporterId").val("");
		$("#vechicleNo").prop("disabled", true);
		$("#vechicleNo").css({"border-color" : "#2f65b0"});
		$("#transporterDocumentNo").val("");
		$("input[name=vechicle_type]").attr('disabled', true);
		if(mode==2){
			$("#vechicleNo").val("");
			$("#transporterDocumentNo").val("");
			changeFieldValue("transporterName","Rail");		
			$("#rrNo").show();
			$("#transporterDocNo").hide();
			$("#airwayNo").hide();
			$("#ladingNo").hide();
			changeLabelName("transporterDate","RR Date");		
		}else if(mode==3){
			$("#vechicleNo").val("");
			$("#transporterDocumentNo").val("");
			changeFieldValue("transporterName","Air");	
			$("#airwayNo").show();
			$("#transporterDocNo").hide();
			$("#rrNo").hide();
			$("#ladingNo").hide();
			changeLabelName("transporterDate","Airway Bill Date");
		}else{
			$("#vechicleNo").val("");
			$("#transporterDocumentNo").val("");
			changeFieldValue("transporterName","Ship");	
			$("#ladingNo").show();
			$("#transporterDocNo").hide();
			$("#rrNo").hide();
			$("#airwayNo").hide();
			changeLabelName("transporterDate","Bill of lading Date");
		}
	}	
}

function backToValidatePage(){
    $('.loader').show(); 
	window.location.href = "./loginGenerateEwayBill";
    $('.loader').fadeOut("slow");
}

function resetExportReflection(){
	$("#billToStateId").val('');
	$("#billToStateValue").val('');
	$("#shipToPincode").val('').css({"border-color" : "#2f65b0"});
	$("#shipToState").val('');
	$("#shipToStateId").val('');
	$("#shipToStateValue").val('');
}

function resetBillFromFields(){
	$("#billFromGstin").val("").prop("disabled", false).css({"border-color" : "#2f65b0"});
	$("#billFromName").val("").prop("readonly", true).css({"border-color" : "#2f65b0"});
	resetSelectField("billFromState");
	$("#billFromState").prop("disabled", true).css({"border-color" : "#2f65b0"});
	$("#billFromStateId").val("");
	$("#billFromStateValue").val("");
	$("#billFromState-req").hide();
	$("#dispatchFromAddress").val("").css({"border-color" : "#2f65b0"});
	$("#dispatchFromPlace").val("").css({"border-color" : "#2f65b0"});
	$("#dispatchFromPincode").val("").prop("disabled", false).css({"border-color" : "#2f65b0"});
	$("#dispatchFromState").val("").css({"border-color" : "#2f65b0"});
	$("#dispatchFromStateId").val("");
	$("#dispatchFromStateValue").val("");
}

function resetBillToFields(){
	$("#billToGstin").val("").prop("disabled", false).css({"border-color" : "#2f65b0"});
	$("#billToName").val("").prop("readonly", true).css({"border-color" : "#2f65b0"});
	resetSelectField("billToState");
	$("#billToState").prop("disabled", true).css({"border-color" : "#2f65b0"});
	$("#billToStateId").val("");
	$("#billToStateValue").val("");	
	$("#billToState-req").hide();
	$("#shipToAddress").val("").css({"border-color" : "#2f65b0"});
	$("#shipToPlace").val("").css({"border-color" : "#2f65b0"});
	$("#shipToPincode").val("").prop("disabled", false).css({"border-color" : "#2f65b0"});
	$("#shipToState").val("").css({"border-color" : "#2f65b0"});
	$("#shipToStateId").val("");
	$("#shipToStateValue").val("");
}

function resetItemDetails(){	
	var itemCounter = $("#itemCounter").val();
	$(".description").val('').css({"border-color" : "#2f65b0"});
	for(i=0;i<itemCounter;i++){
		$(i).val("").css({"border-color" : "#2f65b0"});
		$("#productName_"+i).val("").css({"border-color" : "#2f65b0"});
		$("#hsn_"+i).val("").css({"border-color" : "#2f65b0"});
		$("#quantity_"+i).val("").css({"border-color" : "#2f65b0"});
		loadUnitOfMeasurementEwayBillWI(i);
		$('#unitOfMeasurement_'+i).css({"border-color" : "#2f65b0"});
		$("#rate_"+i).val("").css({"border-color" : "#2f65b0"});
		$("#taxableValue_"+i).val("").css({"border-color" : "#2f65b0"});
		/*$("#cgst_"+i).val("").css({"border-color" : "#2f65b0"});
		$("#sgst_"+i).val("").css({"border-color" : "#2f65b0"});
		$("#igst_"+i).val("").css({"border-color" : "#2f65b0"});
		$("#cess_"+i).val("").css({"border-color" : "#2f65b0"});*/
		$("#cgst_sgstrate_"+i).empty().append('<option value="">Select</option>').css({"border-color" : "#2f65b0"});
		$("#igstrate_"+i).empty().append('<option value="">Select</option>').css({"border-color" : "#2f65b0"});
		$("#cessadvolrate_"+i).empty().append('<option value="0">Select</option>').css({"border-color" : "#2f65b0"});
		$("#cessnonadvolrate_"+i).empty().append('<option value="0">Select</option>').css({"border-color" : "#2f65b0"});
		//loadProductRate(i);
	}	
	$("#totaltaxableamt").val(0);
	$("#cgstamt").val(0);
	$("#sgstamt").val(0);
	$("#igstamt").val(0);
	$("#cessadvolamt").val(0);
	$("#cessnonadvolamt").val(0);
	$("#otheramt").val(0);
	$("#totalinvamt").val(0);
}

function callCalculateAjax(){
	$('.loader').show();
	var fromGstinFlag = verifyTextField("billFromGstin","billFromGstin-back-req",blankMsg);
	var toGstinFlag = verifyTextField("billToGstin","billToGstin-back-req",blankMsg);
	var totalNoOfRecords = $('.addrow .rows').length;
	var fromGstin = $("#billFromGstin").val();
	var toGstin = $("#billToGstin").val();
	var billToStateId = $("#billToStateId").val();
    var billFromStateId = $("#billFromStateId").val();
	var arrayItemList = new Array();

	var i=0;
	$('.newrowgrid').each(function(){
		var strs = this.children[0].id;
		var divCounter = strs.slice((strs.indexOf("_"))+1);
		if(divCounter != undefined){
			var itemList = new Object();
			itemList.taxableAmount= parseFloat($("#taxableValue_"+divCounter).val()).toFixed(1);
			itemList.cgstsgstRate = $("#cgst_sgstrate_"+divCounter).val();
			itemList.igstRate = $("#igstrate_"+divCounter).val();
			itemList.cessadvolrate = parseFloat($("#cessadvolrate_"+divCounter).val()).toFixed(1);
			itemList.cessnonadvolrate = parseFloat($("#cessnonadvolrate_"+divCounter).val()).toFixed(1);
					
			itemList.hsnId = $("#hsnId_"+divCounter).val();
			itemList.quantity = parseFloat($("#quantity_"+divCounter).val()).toFixed(1);
			itemList.cessAmount = 0;
			itemList.cgstAmount = 0;
			itemList.sgstAmount = 0;
			itemList.igstAmount = 0
			itemList.cgstRate = 0;
			itemList.sgstRate = 0;
			itemList.cessadvolAmount = 0;
			itemList.cessnonadvolAmount = 0;
			itemList.itemNo = divCounter;			
	   		arrayItemList.push(itemList);
	   		i++;
		}
	});
	$('#calculateFlagForNoOfProducts').val(i);
		
	if(arrayItemList != undefined || arrayItemList != ''){
		var ewayBillCalculateTax = {
				"fromGstin" : fromGstin,
				"fromStateCode": billFromStateId,
				"toGstin" : toGstin,
	   			"toStateCode": billToStateId,
				"totalCessadvolAmount" : 0,
				"totalCessnonadvolAmount" : 0,
				"otherAmount" : 0,
				"sgstTotalAmount" : 0,
				"cgstTotalAmount" : 0,
				"igstTotalAmount" : 0,
				"totaltaxAmount" : 0,
				"totalAmount" : 0,
				"totalcessAmount" : 0,
				"itemList" : JSON.parse(JSON.stringify(arrayItemList))
		};
		
		var clientId = $("#clientId").val(); 
    	var secretKey = $("#secretKey").val(); 
    	var appCode = $("#appCode").val();
		var ipUsr = $("#ipUsr").val();
		//url : "ewaybill/ewayBillcalculateTaxAmount",    	
	    $.ajax({
	    	url : "ewaybill/ewayBillcalculateTaxAmountV3",
			type : "POST",
			contentType : "application/json",
			dataType : "json",
			headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
			data : JSON.stringify(ewayBillCalculateTax),
			async : false,
			success : function(jsonVal) {
				if (isValidSession(jsonVal) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(jsonVal.status == "failure"){
					bootbox.alert(jsonVal.error_desc);
				}else{	
					/*$("#sgstTotalAmount").val(jsonVal.sgstTotalAmount);
					$("#cgstTotalAmount").val(jsonVal.cgstTotalAmount);
					$("#igstTotalAmount").val(jsonVal.igstTotalAmount);
					$("#totalAmount").val(jsonVal.totalAmount);
					$("#totalcessAmount").val(jsonVal.totalcessAmount);
					$.each(jsonVal.itemList,function(i,value) {
						$(self).parent().parent().find('.cgst').val(value.cgstAmount);
    	    			$(self).parent().parent().find('.sgst').val(value.sgstAmount);
    	    			$(self).parent().parent().find('.igst').val(value.igstAmount);	
					});*/
					$("#totaltaxableamt").val(parseFloat(jsonVal.totalAmount).toFixed(2));
					$("#cgstamt").val(parseFloat(jsonVal.cgstTotalAmount).toFixed(2));
					$("#sgstamt").val(parseFloat(jsonVal.sgstTotalAmount).toFixed(2));
					$("#igstamt").val(parseFloat(jsonVal.igstTotalAmount).toFixed(2));
					$("#cessadvolamt").val(parseFloat(jsonVal.totalCessadvolAmount).toFixed(2));					
					$("#cessnonadvolamt").val(parseFloat(jsonVal.totalCessnonadvolAmount).toFixed(2));
					$("#otheramt").val(parseFloat(jsonVal.otherAmount).toFixed(2));
					$("#totalinvamt").val(parseFloat(jsonVal.totaltaxAmount).toFixed(2));
					
					globalTotalTaxableAmt = jsonVal.totalAmount;
					globalCgstAmt = jsonVal.cgstTotalAmount;
					globalSgstAmt = jsonVal.sgstTotalAmount;
					globalIgstAmt = jsonVal.igstTotalAmount;
					globalCessAdvolAmt = jsonVal.totalCessadvolAmount;
					globalCessNonAdvolAmt = jsonVal.totalCessnonadvolAmount;
					globalOtherAmt = jsonVal.otherAmount;
					globalTotalInvAmt = jsonVal.totaltaxAmount;
				}
			},
	         error: function (data,status,er) {        	 
	        	 getInternalServerErrorPage();   
	        }
	    });			
	} 
	$('.loader').fadeOut("slow");
}

$(function(){
	$(document).on('change', '.unitOfMeasurement', function(){
		$('.loader').show();
		var buyerSellerTypeVal = $('input[name=buyerSellerType]').filter(':checked').val();
		var billfromStateId = $('#billFromStateId').val();
		var billToStateId = $('#billToStateId').val();
		var itemCounterVal = $('#itemCounter').val();
		if(billfromStateId == billToStateId){				
			$('.igstrate').prop("disabled", true).removeClass("addItemRequire");
			$('.cgstsgstrate').prop("disabled", false).addClass("addItemRequire");
			loadProductRate(itemCounterVal-1,true, false);
		}else{					
			$('.igstrate').prop("disabled", false).addClass("addItemRequire");
			$('.cgstsgstrate').prop("disabled", true).removeClass("addItemRequire");				
			loadProductRate(itemCounterVal-1, false, true);
		}
		$('.loader').fadeOut("slow");
	}); 
})

$(document).on('blur','#cessadvolamt, #cessnonadvolamt, #otheramt',function(e){
	var tot_tax_amt = $("#totaltaxableamt").val();
	var cgst_amt = $("#cgstamt").val();
	var sgst_amt = $("#sgstamt").val();
	var igst_amt = $("#igstamt").val();
	var cess_advol_amt = $('#cessadvolamt').val();
	var cess_nonadvol_amt = $('#cessnonadvolamt').val();
	var other_amt = $('#otheramt').val();
	var cessadvolflag = false;
	var cessnonadvolflag = false;
	var otheramtflag = false;

	if(tot_tax_amt == null || tot_tax_amt == undefined || tot_tax_amt == '') {
		if(isNaN(parseFloat(tot_tax_amt))){
			$('#cessadvolamt').val(0);
			tot_tax_amt = 0;
			cessadvolflag = true;
		}			
	}

	if(cgst_amt == null || cgst_amt == undefined || cgst_amt == '') {
		if(isNaN(parseFloat(cgst_amt))){
			$('#cessadvolamt').val(0);
			cgst_amt = 0;
			cessadvolflag = true;
		}			
	}

	if(sgst_amt == null || sgst_amt == undefined || sgst_amt == '') {
		if(isNaN(parseFloat(sgst_amt))){
			$('#cessadvolamt').val(0);
			sgst_amt = 0;
			cessadvolflag = true;
		}			
	}

	if(igst_amt == null || igst_amt == undefined || igst_amt == '') {
		if(isNaN(parseFloat(cessadvolamt))){
			$('#cessadvolamt').val(0);
			igst_amt = 0;
			cessadvolflag = true;
		}			
	}
	
	if(cess_advol_amt == null || cess_advol_amt == undefined || cess_advol_amt == '') {
		if(isNaN(parseFloat(cess_advol_amt))){
			$('#cessadvolamt').val(0);
			cess_advol_amt = 0;
			cessadvolflag = true;
		}			
	}
		
	if(cess_nonadvol_amt == null || cess_nonadvol_amt == undefined || cess_nonadvol_amt == '') {
		if(isNaN(parseFloat(cess_nonadvol_amt))){
			$('#cessnonadvolamt').val(0);
			cess_nonadvol_amt = 0;
			cessnonadvolflag = true;
		}			
	}
	
	if(other_amt == null || other_amt == undefined || other_amt == '') {
		if(isNaN(parseFloat(other_amt))){
			$('#otheramt').val(0);
			other_amt = 0;
			otheramtflag = true;
		}			
	} 
	    
	cess_advol_amt = checkIfValuesAreDifferent(parseFloat(globalCessAdvolAmt).toFixed(2), parseFloat(cess_advol_amt).toFixed(2));
	cess_nonadvol_amt = checkIfValuesAreDifferent(parseFloat(globalCessNonAdvolAmt).toFixed(2), parseFloat(cess_nonadvol_amt).toFixed(2));
	other_amt = checkIfValuesAreDifferent(parseFloat(globalOtherAmt).toFixed(2), parseFloat(other_amt).toFixed(2));
	
	var sum = parseFloat(tot_tax_amt)+parseFloat(cgst_amt)+parseFloat(sgst_amt)+parseFloat(igst_amt)+parseFloat(cess_advol_amt)+parseFloat(cess_nonadvol_amt)+parseFloat(other_amt);
	$('#totalinvamt').val(parseFloat(sum).toFixed(2));
});	

function checkIfValuesAreDifferent(globalAmt, inputAmt){
	if(globalAmt != inputAmt)
		return inputAmt
	else
		return globalAmt
}


/*
$(function(){	
	$(document).on('keyup', '.cess', function(){
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		if ($(this).val() === ""){
			 $(this).css({"border-color" : "#ff0000"});
		 }
		 if ($(this).val() !== ""){
			 $(this).css({"border-color" : "#498648"});
		 } 
		 
		var self = this;
		var blankMsg="This field is required";
		var fromGstinFlag = verifyTextField("billFromGstin","billFromGstin-back-req",blankMsg);
		var toGstinFlag = verifyTextField("billToGstin","billToGstin-back-req",blankMsg);	

		var billToStateId = $("#billToStateId").val();
	    var billFromStateId = $("#billFromStateId").val();
		var taxableValueFlag;
		var rateFlag;
		var quantityFlag;
		var hsnIdFlag;
		var ProductnameFlag;
		
		if($(self).parent().parent().parent().parent().find('.hsnId').val() !=="" || $(self).parent().parent().parent().parent().find('.hsnId').val() != 0)
			hsnIdFlag = false;	
		else
			hsnIdFlag = true;

		if($(self).parent().parent().parent().parent().find('.productName').val() !=="" || $(self).parent().parent().parent().parent().find('.productName').val() != 0)
			ProductnameFlag = false;	
		else
			ProductnameFlag = true;

		if($(self).parent().parent().parent().parent().find('.quantity').val() !=="" || $(self).parent().parent().parent().parent().find('.quantity').val() != 0)
			quantityFlag = false;	
		else
			quantityFlag = true;

		if($(self).parent().parent().parent().parent().find('.rate').val() !=="" || $(self).parent().parent().parent().parent().find('.rate').val() != 0)
			rateFlag = false;	
		else
			rateFlag = true;
		
		if($(self).parent().parent().parent().parent().find('.taxableValue').val() !=="" || $(self).parent().parent().parent().parent().find('.taxableValue').val() != 0)
			taxableValueFlag = false;	
		else
			taxableValueFlag = true;
		
		if($(this).val() !=="" && !fromGstinFlag && !toGstinFlag && !taxableValueFlag && !rateFlag && !quantityFlag && !hsnIdFlag && !ProductnameFlag 
				&& billFromStateId != '' && billToStateId != ''){
			var totalNoOfRecords = $('.addrow .rows').length;
			var fromGstin = $("#billFromGstin").val();
			var toGstin = $("#billToGstin").val();

			var itemList = new Object();			
			itemList.hsnId = $(this).parent().parent().parent().parent().find('.hsnId').val();
			itemList.taxableAmount= parseFloat($(this).parent().parent().parent().parent().find('.taxableValue').val()).toFixed(1);
			itemList.quantity = parseFloat($(this).parent().parent().parent().parent().find('.quantity').val()).toFixed(1);
			itemList.cessAmount = parseFloat($(this).parent().parent().parent().parent().find('.cess').val()).toFixed(1);
			itemList.cgstAmount = 0;
			itemList.sgstAmount = 0;
			itemList.igstAmount = 0;
			itemList.itemNo = totalNoOfRecords;
	   		var arrayItemList = new Array();
	   		arrayItemList.push(itemList);
			
			var ewayBillCalculateTax = {
					"fromGstin" : fromGstin,
					"fromStateCode": billFromStateId,
					"toGstin" : toGstin,
		   			"toStateCode": billToStateId,
					"sgstTotalAmount" :0,
					"cgstTotalAmount" :0,
					"igstTotalAmount" :0,
					"totaltaxAmount" :0,
					"totalAmount" :0,
					"totalcessAmount" :0,
					"itemList" : JSON.parse(JSON.stringify(arrayItemList))
			};
			
			var clientId = $("#clientId").val(); 
	    	var secretKey = $("#secretKey").val(); 
	    	var appCode = $("#appCode").val();
			var ipUsr = $("#ipUsr").val();
	    	
		    $.ajax({
				url : "ewaybill/ewayBillcalculateTaxAmount",
				type : "POST",
				contentType : "application/json",
				dataType : "json",
				headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
				data : JSON.stringify(ewayBillCalculateTax),
				async : false,
				success : function(jsonVal) {
					if (isValidSession(jsonVal) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}

					if(jsonVal.status == "failure"){
						bootbox.alert(jsonVal.error_desc);
					}else{	
						$("#sgstTotalAmount").val(jsonVal.sgstTotalAmount);
						$("#cgstTotalAmount").val(jsonVal.cgstTotalAmount);
						$("#igstTotalAmount").val(jsonVal.igstTotalAmount);
						$("#totalAmount").val(jsonVal.totalAmount);
						$("#totalcessAmount").val(jsonVal.totalcessAmount);
						$.each(jsonVal.itemList,function(i,value) {
							$(self).parent().parent().find('.cgst').val(value.cgstAmount);
	    	    			$(self).parent().parent().find('.sgst').val(value.sgstAmount);
	    	    			$(self).parent().parent().find('.igst').val(value.igstAmount);	
						});
					}
				},
		         error: function (data,status,er) {        	 
		        	 getInternalServerErrorPage();   
		        }
		    });
		}else{ 
			$(this).val('');
			$(this).focus();
			$(this).addClass("input-errorEWay").removeClass("input-correctEWay");
			
			if(fromGstinFlag){
				focusTextBox("billFromGstin");
			}
			
			if(toGstinFlag){
				focusTextBox("billToGstin");
			}
			
			if(billFromStateId == ''){
				var transctionTypeValue = $('select#transctionType option:selected').val();
				var billFromGstin = $("#billFromGstin").text();
				if(transctionTypeValue=="I"){
					if(billFromGstin != '' && billFromGstin == 'URP'){
						$("#dispatchFromPincode").css({"border-color" : "#ff0000"});
						$("#dispatchFromPincode-back-req").text('Invalid Pincode');
						$("#dispatchFromPincode-back-req").show();
						focusTextBox("dispatchFromPincode");
					}else{
						$("#dispatchFromPincode-back-req").hide();
						//focusTextBox("billFromGstin");
					}
				}
			}
			if(billToStateId == ''){
				var transctionTypeValue = $('select#transctionType option:selected').val();
				var billToGstin = $("#billToGstin").val();
				if(transctionTypeValue=="O"){
					if(billToGstin != '' && billToGstin == 'URP'){
						$("#shipToPincode").css({"border-color" : "#ff0000"});
						$("#shipToPincode-req").text('Invalid Pincode');
						$("#shipToPincode-req").show();
						focusTextBox("shipToPincode");
					}else{
						$("#shipToPincode-req").hide();
						//focusTextBox("billToGstin");
					}
				}
			}
			
			if(ProductnameFlag){
				$(self).parent().parent().parent().parent().find('.productName').css({"border-color" : "#ff0000"});
				$(self).parent().parent().parent().parent().find('.productName').focus();
			}else{
				$(self).parent().parent().parent().parent().find('.productName').css({"border-color" : "#498648"});
			}

			if(hsnIdFlag){
				if($(self).parent().parent().parent().parent().find('.hsn').val() === ""){
					$(self).parent().parent().parent().parent().find('.hsn').css({"border-color" : "#ff0000"});
					$(self).parent().parent().parent().parent().find('.hsn').focus();
				}				
			}else{
				$(self).parent().parent().parent().parent().find('.hsn').css({"border-color" : "#498648"});
			}

			if(quantityFlag){
				$(self).parent().parent().parent().parent().find('.quantity').css({"border-color" : "#ff0000"});
				$(self).parent().parent().parent().parent().find('.quantity').focus();
			}else{
				$(self).parent().parent().parent().parent().find('.quantity').css({"border-color" : "#498648"});
			}
			
			if(rateFlag){
				$(self).parent().parent().parent().parent().find('.rate').css({"border-color" : "#ff0000"});
				$(self).parent().parent().parent().parent().find('.rate').focus();
			}else{
				$(self).parent().parent().parent().parent().find('.rate').css({"border-color" : "#498648"});
			}
			
			if(taxableValueFlag){
				$(self).parent().parent().parent().parent().find('.taxableValue').css({"border-color" : "#ff0000"});
				$(self).parent().parent().parent().parent().find('.taxableValue').focus();
			}else{
				$(self).parent().parent().parent().parent().find('.taxableValue').css({"border-color" : "#498648"});
			}				
			
			$(self).parent().parent().find('.cgst').val(0);
			$(self).parent().parent().find('.sgst').val(0);
			$(self).parent().parent().find('.igst').val(0);
		}				
	}); 
});
*/
