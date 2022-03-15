//3.1 outward & inward to reverse charge
var netSuppliesArray = ["3_1_totaltaxablevalue", "3_1_itaxamt", "3_1_ctaxamt", "3_1_staxamt", "3_1_csamt"];
		var totamtNetSuppArray = ["outward_nonzero_totamt", "outward_zero_totamt", "otheroutward_totamt", "inward_totamt", "nongstoutward_totamt"];
		var iamtNetSuppArray = ["outward_nonzero_iamt", "outward_zero_iamt", "inward_iamt"];
		var camtNetSuppArray = ["outward_nonzero_camt", "inward_camt"];
		var samtNetSuppArray = ["outward_nonzero_samt", "inward_samt"];
		var csamtNetSuppArray = ["outward_nonzero_csamt", "outward_zero_csamt", "inward_csamt"];
		
//4 Eligilbe Itc
//4.a Itc Available		
var itcAvailableArray = ["itc_available_iamt", "itc_available_camt", "itc_available_samt", "itc_available_csamt"];	
		var iamtItcAvailableArray = ["itcavailable_goods_iamt", "itcavailable_services_iamt", "itcavailable_inwardreverse_iamt","itcavailable_inwardisd_iamt","itcavailable_allotheritc_iamt"];
		var camtItcAvailableArray = ["itcavailable_inwardreverse_camt", "itcavailable_inwardisd_camt","itcavailable_allotheritc_camt"];
		var samtItcAvailableArray = ["itcavailable_inwardreverse_samt", "itcavailable_inwardisd_samt", "itcavailable_allotheritc_samt"];
		var csamtItcAvailableArray = ["itcavailable_goods_csamt", "itcavailable_services_csamt", "itcavailable_inwardreverse_csamt","itcavailable_inwardisd_csamt","itcavailable_allotheritc_csamt"];

//4.a Itc Reserved		
var itcReservedArray = ["itc_reserved_iamt", "itc_reserved_camt", "itc_reserved_samt", "itc_reserved_csamt"];
		var iamtItcReservedArray = ["itcreversed_4243cgstrules_iamt", "itcreversed_others_iamt"];
		var camtItcReservedArray = ["itcreversed_4243cgstrules_camt", "itcreversed_others_camt"];
		var samtItcReservedArray = ["itcreversed_4243cgstrules_samt", "itcreversed_others_samt"];
		var csamtItcReservedArray = ["itcreversed_4243cgstrules_csamt", "itcreversed_others_csamt"];

//4.a Itc Ineligible
var itcIneligibleArray = ["itcineligible_itc_iamt", "itcineligible_itc_camt", "itcineligible_itc_samt", "itcineligible_itc_csamt"];
		var iamtItcEligibleArray = ["itcineligible_section17_iamt", "itcineligible_others_iamt"];
		var camtItcEligibleArray = ["itcineligible_section17_camt", "itcineligible_others_camt"];
		var samtItcEligibleArray = ["itcineligible_section17_samt", "itcineligible_others_samt"];
		var csamtItcEligibleArray = ["itcineligible_section17_csamt", "itcineligible_others_csamt"];

//3.2 Inter state
var insterStatetableIdArray = ["3_2_unregisteredTable","3_2_compositionTable","3_2_uinTable"];
var interStatePosArray = ["interstate_unreg_pos_","interstate_compositn_pos_","interstate_uin_pos_"];
var interStateUnregArray = ["3_2_unreg_totaltaxablevalue","3_2_unreg_itaxamt"];
var interStateCompositnArray = ["3_2_compositn_totaltaxablevalue","3_2_compositn_itaxamt"];
var interStateUinArray = ["3_2_uin_totaltaxablevalue","3_2_uin_itaxamt"];

function convertStringToFloatWithZeroDecimal(value, digitAfterDecimalPoint, defaultValue) 
{
	/*if(value != null && value != undefined && value != '') {
		value = parseFloat(value);
		if(!isNaN(value))
			return value.toFixed(digitAfterDecimalPoint);
	}
	return defaultValue;*/	
	value = parseFloat(value);
	if(!isNaN(value))
		return value.toFixed(digitAfterDecimalPoint);
	else
		return defaultValue.toFixed(digitAfterDecimalPoint);
}

function redirectToPreviousPage()
{
	 document.goBackToAspOnErrorFromGstr3b.action ="./returntopreviousmenuasp";
     document.goBackToAspOnErrorFromGstr3b.gstinId.value = $("#gstinId").val();
     document.goBackToAspOnErrorFromGstr3b.financialPeriod.value = $("#financialPeriod").val();
     document.goBackToAspOnErrorFromGstr3b.gstrType.value = "GSTR3B";
     document.goBackToAspOnErrorFromGstr3b._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAspOnErrorFromGstr3b.submit();     
} 

function autoPopulateStateList(posId,count,optionValue,stateListJson){
	if(stateListJson == '' || stateListJson == undefined || stateListJson == null){
		stateListJson = fetchStateJson();
	}
	setStateValues(posId,count,optionValue,stateListJson);
	return stateListJson;
}

function setStateValues(posId,count,optionValue,stateListJson){
	$("#" + posId+''+count).append($('<option>').text('Select').attr('value',''));		
	$.each(stateListJson, function(i, value) {
		if(optionValue != ""){
			if(optionValue == value.stateId){
				$("#" + posId+''+count).append($('<option>').text(value.stateId+'-'+value.stateName).attr('value',value.stateId).attr('selected','selected'));
			}else{
				$("#" + posId+''+count).append($('<option>').text(value.stateId+'-'+value.stateName).attr('value',value.stateId));
			}
		}else{
			$("#" + posId+''+count).append($('<option>').text(value.stateId+'-'+value.stateName).attr('value',value.stateId));
		}			  
	});		
}

function fetchStateJson(){	
	var response='';
	$.ajax({
		url : "getStatesList",
		method : "POST",
		dataType : "json",
		data : {_csrf_token : $("#_csrf_token").val() },
		async : false,
		success:function(json,fTextStatus,fRequest) {
			if (isValidSession(json) == 'No') {
				if(loggedInFrom == 'MOBILE')
					window.location.href = getDefaultSessionExpirePage();
				else
					window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				if(loggedInFrom == 'MOBILE')
					window.location.href = getCsrfErrorPage();
				else
					window.location.href = getWizardCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			response =  json;
		}
	});
	return response;
}

function calculateNetValues(fromPage)
{
	//netSuppliesArray	
	if(typeof netSuppliesArray !== undefined && netSuppliesArray != null && netSuppliesArray.length > 0){
		for(var i=0;i<netSuppliesArray.length;i++){
			var sumOfValues = 0;
			if(netSuppliesArray[i] == '3_1_totaltaxablevalue')
				sumOfValues = evaluateTotalAmount("netSupplies",totamtNetSuppArray,fromPage);
			else if(netSuppliesArray[i] == '3_1_itaxamt')
				sumOfValues = evaluateTotalAmount("netSupplies",iamtNetSuppArray,fromPage);
			else if(netSuppliesArray[i] == '3_1_ctaxamt')
				sumOfValues = evaluateTotalAmount("netSupplies",camtNetSuppArray,fromPage);
			else if(netSuppliesArray[i] == '3_1_staxamt')
				sumOfValues = evaluateTotalAmount("netSupplies",samtNetSuppArray,fromPage);
			else if(netSuppliesArray[i] == '3_1_csamt')
				sumOfValues = evaluateTotalAmount("netSupplies",csamtNetSuppArray,fromPage);
			
			$("#"+netSuppliesArray[i]).empty();
			$("#"+netSuppliesArray[i]).text(convertStringToFloatWithZeroDecimal(sumOfValues, 2, 0));
		}
	}else{
		console.log("Error in calculateNetValues for netSuppliesArray");
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}
	
	//InterState
	for(var aa=0;aa<insterStatetableIdArray.length;aa++){
		if(insterStatetableIdArray[aa] == '3_2_unregisteredTable'){
			tableFieldId(insterStatetableIdArray[aa],"interstate_unreg_totamt_","interstate_unreg_iamt_",fromPage);
		}else if(insterStatetableIdArray[aa] == '3_2_compositionTable'){
			tableFieldId(insterStatetableIdArray[aa],"interstate_compositn_totamt_","interstate_compositn_iamt_",fromPage);		
		}else if(insterStatetableIdArray[aa] == '3_2_uinTable'){
			tableFieldId(insterStatetableIdArray[aa],"interstate_uin_totamt_","interstate_uin_iamt_",fromPage);		
		}		
	}
		
	//itcAvailableArray
	if(typeof itcAvailableArray !== undefined && itcAvailableArray != null && itcAvailableArray.length > 0){
		for(var i=0;i<itcAvailableArray.length;i++){
			var sumOfValues = parseFloat(0);
			if(itcAvailableArray[i] == 'itc_available_iamt')
				sumOfValues = evaluateTotalAmount("itcAvailable",iamtItcAvailableArray,fromPage);
			else if(itcAvailableArray[i] == 'itc_available_camt')
				sumOfValues = evaluateTotalAmount("itcAvailable",camtItcAvailableArray,fromPage);
			else if(itcAvailableArray[i] == 'itc_available_samt')
				sumOfValues = evaluateTotalAmount("itcAvailable",samtItcAvailableArray,fromPage);
			else if(itcAvailableArray[i] == 'itc_available_csamt')
				sumOfValues = evaluateTotalAmount("itcAvailable",csamtItcAvailableArray,fromPage);
			
			$("#"+itcAvailableArray[i]).empty();
			$("#"+itcAvailableArray[i]).text(convertStringToFloatWithZeroDecimal(sumOfValues, 2, 0));
		}
	}else{
		console.log("Error in calculateNetValues for itcAvailableArray");
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}
		
	//itcReservedArray
	if(typeof itcReservedArray !== undefined && itcReservedArray != null && itcReservedArray.length > 0){
		for(var i=0;i<itcReservedArray.length;i++){
			var sumOfValues = parseFloat(0);
			if(itcReservedArray[i] == 'itc_reserved_iamt')
				sumOfValues = evaluateTotalAmount("itcReserved",iamtItcReservedArray,fromPage);
			else if(itcReservedArray[i] == 'itc_reserved_camt')
				sumOfValues = evaluateTotalAmount("itcReserved",camtItcReservedArray,fromPage);
			else if(itcReservedArray[i] == 'itc_reserved_samt')
				sumOfValues = evaluateTotalAmount("itcReserved",samtItcReservedArray,fromPage);
			else if(itcReservedArray[i] == 'itc_reserved_csamt')
				sumOfValues = evaluateTotalAmount("itcReserved",csamtItcReservedArray,fromPage);
			
			$("#"+itcReservedArray[i]).empty();
			$("#"+itcReservedArray[i]).text(convertStringToFloatWithZeroDecimal(sumOfValues, 2, 0));
		}
	}else{
		console.log("Error in calculateNetValues for itcReservedArray");
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}
	
	//itcIneligibleArray	
	if(typeof itcIneligibleArray !== undefined && itcIneligibleArray != null && itcIneligibleArray.length > 0){
		for(var i=0;i<itcIneligibleArray.length;i++){
			var sumOfValues = parseFloat(0);
			if(itcIneligibleArray[i] == 'itcineligible_itc_iamt')
				sumOfValues = evaluateTotalAmount("itcinEligible",iamtItcEligibleArray,fromPage);
			else if(itcIneligibleArray[i] == 'itcineligible_itc_camt')
				sumOfValues = evaluateTotalAmount("itcinEligible",camtItcEligibleArray,fromPage);
			else if(itcIneligibleArray[i] == 'itcineligible_itc_samt')
				sumOfValues = evaluateTotalAmount("itcinEligible",samtItcEligibleArray,fromPage);
			else if(itcIneligibleArray[i] == 'itcineligible_itc_csamt')
				sumOfValues = evaluateTotalAmount("itcinEligible",csamtItcEligibleArray,fromPage);
			
			$("#"+itcIneligibleArray[i]).empty();
			$("#"+itcIneligibleArray[i]).text(convertStringToFloatWithZeroDecimal(sumOfValues, 2, 0));
		}
	}else{
		console.log("Error in calculateNetValues for itcIneligibleArray");
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}	
}
	
function evaluateTotalAmount(type,arrayList,fromPage)
{
	var sumAmt=parseFloat(0);
	if(type == 'netSupplies'){		
		sumAmt = iterateNSum(arrayList,fromPage);
	}else if(type == 'itcAvailable'){
		sumAmt = iterateNSum(arrayList,fromPage);
	}else if(type == 'itcReserved'){
		sumAmt = iterateNSum(arrayList,fromPage);
	}else if(type == 'itcinEligible'){
		sumAmt = iterateNSum(arrayList,fromPage);
	}
	return sumAmt;
}

function iterateNSum(arrayList,fromPage)
{
	var sumAmt=parseFloat(0);
	if(typeof arrayList !== undefined && arrayList != null && arrayList.length > 0){
		for(var j=0;j<arrayList.length;j++){
			if(fromPage == 'prepare'){
				if($("#"+arrayList[j]).val() != null && $("#"+arrayList[j]).val() != undefined && $("#"+arrayList[j]).val() != ''){
					sumAmt += parseFloat($("#"+arrayList[j]).val()); 
				}else{
					sumAmt += parseFloat(0); 
					$("#"+arrayList[j]).val(0.00)
				}
			}else if(fromPage == 'draft'){
				if($("#"+arrayList[j]).text() != null && $("#"+arrayList[j]).text() != undefined && $("#"+arrayList[j]).text() != ''){
					sumAmt += parseFloat($("#"+arrayList[j]).text()); 
				}else{
					sumAmt += parseFloat(0); 
					$("#"+arrayList[j]).text(0.00)
				}
			}					
		}
	}else{
		console.log("Error in iterateNSum ");
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}	
	return sumAmt;
}

function tableFieldId(tableId,fieldId1,fieldId2,fromPage){
	var tempTotal = 0;
	var tempIamt = 0;
	var xx = 0;
	
	$("#"+tableId+" tbody").each(function() {
		var tbodyfirstelement;
		var tbodyId = this.id;
		if(tbodyId == 0)
			tbodyfirstelement = this.childNodes[1].value;
		else
			tbodyfirstelement = this.childNodes[0].value;
		if(tbodyfirstelement != undefined){
			if(fromPage == 'prepare'){
				tempTotal += parseFloat($("#"+fieldId1+""+tbodyfirstelement).val());
				tempIamt += parseFloat($("#"+fieldId2+""+tbodyfirstelement).val());
			}else if(fromPage == 'draft'){
				tempTotal += parseFloat($("#"+fieldId1+""+tbodyfirstelement).text());
				tempIamt += parseFloat($("#"+fieldId2+""+tbodyfirstelement).text());
			}
			
		}else{
			console.log("Error in tableFieldId for data of unreg table of InterState");
			bootbox.alert("Something went wrong", function() {
		    	$(".loader").hide();
				redirectToPreviousPage();
				return;
			});
		}
		xx++;
     });	

	if(tableId == '3_2_unregisteredTable'){
		for(var i=0;i<interStateUnregArray.length;i++){
			if(interStateUnregArray[i] == '3_2_unreg_totaltaxablevalue'){
				$("#"+interStateUnregArray[i]).empty();
				$("#"+interStateUnregArray[i]).text(convertStringToFloatWithZeroDecimal(tempTotal, 2, 0));			
			}else if(interStateUnregArray[i] == '3_2_unreg_itaxamt'){
				$("#"+interStateUnregArray[i]).empty();
				$("#"+interStateUnregArray[i]).text(convertStringToFloatWithZeroDecimal(tempIamt, 2, 0));			
			}
		}	
	}else if(tableId == '3_2_compositionTable'){
		for(var i=0;i<interStateCompositnArray.length;i++){
			if(interStateCompositnArray[i] == '3_2_compositn_totaltaxablevalue'){
				$("#"+interStateCompositnArray[i]).empty();
				$("#"+interStateCompositnArray[i]).text(convertStringToFloatWithZeroDecimal(tempTotal, 2, 0));			
			}else if(interStateCompositnArray[i] == '3_2_compositn_itaxamt'){
				$("#"+interStateCompositnArray[i]).empty();
				$("#"+interStateCompositnArray[i]).text(convertStringToFloatWithZeroDecimal(tempIamt, 2, 0));			
			}
		}	
	}else if(tableId == '3_2_uinTable'){
		for(var i=0;i<interStateUinArray.length;i++){
			if(interStateUinArray[i] == '3_2_uin_totaltaxablevalue'){
				$("#"+interStateUinArray[i]).empty();
				$("#"+interStateUinArray[i]).text(convertStringToFloatWithZeroDecimal(tempTotal, 2, 0));			
			}else if(interStateUinArray[i] == '3_2_uin_itaxamt'){
				$("#"+interStateUinArray[i]).empty();
				$("#"+interStateUinArray[i]).text(convertStringToFloatWithZeroDecimal(tempIamt, 2, 0));			
			}
		}	
	}else{
		console.log("Error in tableFieldId");
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}	
}
