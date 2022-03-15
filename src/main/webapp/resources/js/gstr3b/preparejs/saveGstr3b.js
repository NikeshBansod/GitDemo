$(document).ready(function(){	
	$("#savePrepareGstr3b").click(function (e){
		 $("#savePrepareGstr3b").prop("disabled", true);
		 var flag = validateFieldValues();		 	 
		 if(!flag){
			 saveDataToJioGst(); 
		 }else{
			 e.preventDefault();
			 $("#savePrepareGstr3b").prop("disabled", false);
		 }		 
	});
});

function findNullValue(fieldId,counter){
	var flag = false;
	var fieldValue = $("#"+fieldId+""+counter).val();
	if(fieldValue == '' || fieldValue == undefined || fieldValue == null ){
		flag = true;
	}
	return flag;
}

function findHasValue(fieldId,counter){
	var nullFlag = findNullValue(fieldId,counter);	
	if(!nullFlag){
		var hasValueFlag = false;
		var value = $("#"+fieldId+""+counter).val();
		if(value != "0" && value != "0.0" && value != "0.00"){
			hasValueFlag = true;
		}
	}else{
		return nullFlag;
	}
	return hasValueFlag;
}

function checkTableValue(tableId,posFieldId,totAmtFieldId,iamtFieldId,currentCount,purpose){
	var flag = false;	
	var flagArray = new Array();
	
	$("#"+tableId+" tbody").each(function() {
		var totalTemp = 0;
		var iamtTemp = 0;
		var posTemp = '';
		var tbodyfirstelement;
		var tbodyId = this.id;
		if(tbodyId == 0)
			tbodyfirstelement = this.childNodes[1].value;
		else
			tbodyfirstelement = this.childNodes[0].value;
		
		if(tbodyfirstelement != undefined){
			if(tbodyfirstelement == '0' && currentCount == '0' && purpose == 'validate' && !findHasValue(totAmtFieldId,tbodyfirstelement) && !findHasValue(iamtFieldId,tbodyfirstelement)){
				if($("#"+posFieldId+""+tbodyfirstelement).val() == '' ){
					$("#"+posFieldId+""+tbodyfirstelement).css({"border-color" : "#2f65b0"});
				} 
			}else if(tbodyfirstelement == '0' && currentCount == '0' && purpose == 'validate' && (findHasValue(totAmtFieldId,tbodyfirstelement) || findHasValue(iamtFieldId,tbodyfirstelement))){
				if($("#"+posFieldId+""+tbodyfirstelement).val() == '' ){
					$("#"+posFieldId+""+tbodyfirstelement).focus();
					flagArray.push("posFlag_"+tbodyfirstelement);
					$("#"+posFieldId+""+tbodyfirstelement).css({"border-color" : "#ff0000"});
				} 
			}else{
				if($("#"+posFieldId+""+tbodyfirstelement).val() != ''){
					$("#"+posFieldId+""+tbodyfirstelement).css({"border-color" : "#2f65b0"});
				}else{
					$("#"+posFieldId+""+tbodyfirstelement).focus();
					flagArray.push("posFlag_"+tbodyfirstelement);
					$("#"+posFieldId+""+tbodyfirstelement).css({"border-color" : "#ff0000"});
				}
			}
			if(!findNullValue(totAmtFieldId,tbodyfirstelement)){
				$("#"+totAmtFieldId+""+tbodyfirstelement).css({"border-color" : "#2f65b0"});
			}else{
				flagArray.push("totFlag_"+tbodyfirstelement);
				$("#"+totAmtFieldId+""+tbodyfirstelement).css({"border-color" : "#ff0000"});
			}
			if(!findNullValue(iamtFieldId,tbodyfirstelement)){
				$("#"+iamtFieldId+""+tbodyfirstelement).css({"border-color" : "#2f65b0"});
			}else{
				flagArray.push("iamtFlag_"+tbodyfirstelement);
				$("#"+iamtFieldId+""+tbodyfirstelement).css({"border-color" : "#ff0000"});
			}			
		}	
     });
	
	 if(flagArray.length>0){
	 	flag = true;
	 }
	 
	return flag;
}

function validateFieldValues(){
	var commonFlag = false;
	var unregFlag = false;
	var compFlag = false;
	var uinFlag = false;
	
	for(var aa=0;aa<insterStatetableIdArray.length;aa++){
		if(insterStatetableIdArray[aa] == '3_2_unregisteredTable'){
			unregFlag = checkTableValue(insterStatetableIdArray[aa],"interstate_unreg_pos_","interstate_unreg_totamt_","interstate_unreg_iamt_",$("#counter3_2_unreg").val(),"validate");			
		}else if(insterStatetableIdArray[aa] == '3_2_compositionTable'){
			compFlag = checkTableValue(insterStatetableIdArray[aa],"interstate_compositn_pos_","interstate_compositn_totamt_","interstate_compositn_iamt_",$("#counter3_2_compositn").val(),"validate");			
		}else if(insterStatetableIdArray[aa] == '3_2_uinTable'){
			uinFlag = checkTableValue(insterStatetableIdArray[aa],"interstate_uin_pos_","interstate_uin_totamt_","interstate_uin_iamt_",$("#counter3_2_uin").val(),"validate");
		}	
	}
	
	if(unregFlag || compFlag || uinFlag){
		commonFlag = true;
	}
	return commonFlag;
}

function generateJson(){
	var gstin = $("#gstinId").val();
	var financialPeriod = $("#financialPeriod").val();

//3.1 netSupplies	
	var supDetailsListArray = new Array();
	//(a) Outward Taxable Supplies (Other than Zero Rated, Nil Rated, and Exempted)
	var outwardNonZeroSupdetailsObject = new Object();
	outwardNonZeroSupdetailsObject.action = "R";
	outwardNonZeroSupdetailsObject.sup_ty = "osup_det";
	outwardNonZeroSupdetailsObject.txval = parseFloat($("#outward_nonzero_totamt").val());
	outwardNonZeroSupdetailsObject.iamt = parseFloat($("#outward_nonzero_iamt").val());
	outwardNonZeroSupdetailsObject.camt = parseFloat($("#outward_nonzero_camt").val());
	outwardNonZeroSupdetailsObject.samt = parseFloat($("#outward_nonzero_samt").val());
	outwardNonZeroSupdetailsObject.csamt = parseFloat($("#outward_nonzero_csamt").val());
	supDetailsListArray.push(outwardNonZeroSupdetailsObject);
	//(b) Outward Taxable Supplies (Zero Rated)
	var outwardZeroSupdetailsObject = new Object();
	outwardZeroSupdetailsObject.action = "R";
	outwardZeroSupdetailsObject.sup_ty = "osup_zero";
	outwardZeroSupdetailsObject.txval = parseFloat($("#outward_zero_totamt").val());
	outwardZeroSupdetailsObject.iamt = parseFloat($("#outward_zero_iamt").val());
	outwardZeroSupdetailsObject.csamt = parseFloat($("#outward_zero_csamt").val());
	supDetailsListArray.push(outwardZeroSupdetailsObject);
	//(c) Other Outward Supplies (Nil Rated, Exempted)
	var otherOutwardSupdetailsObject = new Object();
	otherOutwardSupdetailsObject.action = "R";
	otherOutwardSupdetailsObject.sup_ty = "osup_nil_exmp";
	otherOutwardSupdetailsObject.txval = parseFloat($("#otheroutward_totamt").val());
	supDetailsListArray.push(otherOutwardSupdetailsObject);
	//(d) Inward Supplies (Liable to Reverse Charge)
	var inwardSupdetailsObject = new Object();
	inwardSupdetailsObject.action = "R";
	inwardSupdetailsObject.sup_ty = "isup_rev";
	inwardSupdetailsObject.txval = parseFloat($("#inward_totamt").val());
	inwardSupdetailsObject.iamt = parseFloat($("#inward_iamt").val());
	inwardSupdetailsObject.camt = parseFloat($("#inward_camt").val());
	inwardSupdetailsObject.samt = parseFloat($("#inward_samt").val());
	inwardSupdetailsObject.csamt = parseFloat($("#inward_csamt").val());	
	supDetailsListArray.push(inwardSupdetailsObject);
	//(e) Non-GST Outward Supplies
	var nonGstOutwardSupdetailsObject = new Object();
	nonGstOutwardSupdetailsObject.action = "R";
	nonGstOutwardSupdetailsObject.sup_ty = "osup_nongst";
	nonGstOutwardSupdetailsObject.txval = parseFloat($("#nongstoutward_totamt").val());
	supDetailsListArray.push(nonGstOutwardSupdetailsObject);
	
//4 Eligible Itc
	var  eligibleItcListArray = new Array();
	
	//A Itc Available
	//(1) import of Goods
	var importGoodItcAvailableObject = new Object();
	importGoodItcAvailableObject.action = "R";
	importGoodItcAvailableObject.itc_ty = "itc_avl";
	importGoodItcAvailableObject.ty = "IMPG";
	importGoodItcAvailableObject.iamt = parseFloat($("#itcavailable_goods_iamt").val());
	importGoodItcAvailableObject.csamt = parseFloat($("#itcavailable_goods_csamt").val());	
	eligibleItcListArray.push(importGoodItcAvailableObject);
	//(2) import of Service
	var importServiceItcAvailableObject = new Object();
	importServiceItcAvailableObject.action = "R";
	importServiceItcAvailableObject.itc_ty = "itc_avl";
	importServiceItcAvailableObject.ty = "IMPS";
	importServiceItcAvailableObject.iamt = parseFloat($("#itcavailable_services_iamt").val());
	importServiceItcAvailableObject.csamt = parseFloat($("#itcavailable_services_csamt").val());	
	eligibleItcListArray.push(importServiceItcAvailableObject);
	//(3) Inward Supplies liable to Reverse Charge (other than 1 & 2 above)
	var inwardToReverseItcAvailableObject = new Object();
	inwardToReverseItcAvailableObject.action = "R";
	inwardToReverseItcAvailableObject.itc_ty = "itc_avl";
	inwardToReverseItcAvailableObject.ty = "ISRC";
	inwardToReverseItcAvailableObject.iamt = parseFloat($("#itcavailable_inwardreverse_iamt").val());
	inwardToReverseItcAvailableObject.camt = parseFloat($("#itcavailable_inwardreverse_camt").val());
	inwardToReverseItcAvailableObject.samt = parseFloat($("#itcavailable_inwardreverse_samt").val());
	inwardToReverseItcAvailableObject.csamt = parseFloat($("#itcavailable_inwardreverse_csamt").val());	
	eligibleItcListArray.push(inwardToReverseItcAvailableObject);
	//(4) Inward Supplies from ISD	
	var inwardSupISDItcAvailableObject = new Object();
	inwardSupISDItcAvailableObject.action = "R";
	inwardSupISDItcAvailableObject.itc_ty = "itc_avl";
	inwardSupISDItcAvailableObject.ty = "ISD";
	inwardSupISDItcAvailableObject.iamt = parseFloat($("#itcavailable_inwardisd_iamt").val());
	inwardSupISDItcAvailableObject.camt = parseFloat($("#itcavailable_inwardisd_camt").val());
	inwardSupISDItcAvailableObject.samt = parseFloat($("#itcavailable_inwardisd_samt").val());
	inwardSupISDItcAvailableObject.csamt = parseFloat($("#itcavailable_inwardisd_csamt").val());	
	eligibleItcListArray.push(inwardSupISDItcAvailableObject);
	//(5) All other ITC
	var allOtherItcItcAvailableObject = new Object();
	allOtherItcItcAvailableObject.action = "R";
	allOtherItcItcAvailableObject.itc_ty = "itc_avl";
	allOtherItcItcAvailableObject.ty = "OTH";
	allOtherItcItcAvailableObject.iamt = parseFloat($("#itcavailable_allotheritc_iamt").val());
	allOtherItcItcAvailableObject.camt = parseFloat($("#itcavailable_allotheritc_camt").val());
	allOtherItcItcAvailableObject.samt = parseFloat($("#itcavailable_allotheritc_samt").val());
	allOtherItcItcAvailableObject.csamt = parseFloat($("#itcavailable_allotheritc_csamt").val());	
	eligibleItcListArray.push(allOtherItcItcAvailableObject);
	
	//B Itc Reserved
	//(1) As Per Rule 42 & 43 of CGST Rules	
	var ruleItcReservedObject = new Object();
	ruleItcReservedObject.action = "R";
	ruleItcReservedObject.itc_ty = "itc_rev";
	ruleItcReservedObject.ty = "RUL";
	ruleItcReservedObject.iamt = parseFloat($("#itcreversed_4243cgstrules_iamt").val());
	ruleItcReservedObject.camt = parseFloat($("#itcreversed_4243cgstrules_camt").val());
	ruleItcReservedObject.samt = parseFloat($("#itcreversed_4243cgstrules_samt").val());
	ruleItcReservedObject.csamt = parseFloat($("#itcreversed_4243cgstrules_csamt").val());	
	eligibleItcListArray.push(ruleItcReservedObject);
	//(2) Others	
	var otherItcReservedObject = new Object();
	otherItcReservedObject.action = "R";
	otherItcReservedObject.itc_ty = "itc_rev";
	otherItcReservedObject.ty = "OTH";
	otherItcReservedObject.iamt = parseFloat($("#itcreversed_others_iamt").val());
	otherItcReservedObject.camt = parseFloat($("#itcreversed_others_camt").val());
	otherItcReservedObject.samt = parseFloat($("#itcreversed_others_samt").val());
	otherItcReservedObject.csamt = parseFloat($("#itcreversed_others_csamt").val());	
	eligibleItcListArray.push(otherItcReservedObject);
	
	//C Net ITC Available (A) - (B)
	var netItcAvailableObject = new Object();
	netItcAvailableObject.action = "R";
	netItcAvailableObject.itc_ty = "itc_net";
	netItcAvailableObject.iamt = parseFloat($("#itc_net_iamt").text());
	netItcAvailableObject.camt = parseFloat($("#itc_net_camt").text());
	netItcAvailableObject.samt = parseFloat($("#itc_net_samt").text());
	netItcAvailableObject.csamt = parseFloat($("#itc_net_csamt").text());	
	eligibleItcListArray.push(netItcAvailableObject);
	
	//D Ineligible ITC
	//(1) As Per Section 17(5)
	var sectionIneligibleItcObject = new Object();
	sectionIneligibleItcObject.action = "R";
	sectionIneligibleItcObject.itc_ty = "itc_inelg";
	sectionIneligibleItcObject.ty = "RUL";
	sectionIneligibleItcObject.iamt = parseFloat($("#itcineligible_section17_iamt").val());
	sectionIneligibleItcObject.camt = parseFloat($("#itcineligible_section17_camt").val());
	sectionIneligibleItcObject.samt = parseFloat($("#itcineligible_section17_samt").val());
	sectionIneligibleItcObject.csamt = parseFloat($("#itcineligible_section17_csamt").val());	
	eligibleItcListArray.push(sectionIneligibleItcObject);
	//(2) Others
	var otherIneligibleItcObject = new Object();
	otherIneligibleItcObject.action = "R";
	otherIneligibleItcObject.itc_ty = "itc_inelg";
	otherIneligibleItcObject.ty = "OTH";
	otherIneligibleItcObject.iamt = parseFloat($("#itcineligible_others_iamt").val());
	otherIneligibleItcObject.camt = parseFloat($("#itcineligible_others_camt").val());
	otherIneligibleItcObject.samt = parseFloat($("#itcineligible_others_samt").val());
	otherIneligibleItcObject.csamt = parseFloat($("#itcineligible_others_csamt").val());	
	eligibleItcListArray.push(otherIneligibleItcObject);	

//5 Values of exempt, nil & nongst inward supplies
	var  inwardSuppliesGstNonGstListArray = new Array();
	//From a Supplier under Composition Scheme, Exempt and Nil Rated Supply
	var inwardSuppliesGstObject = new Object();
	inwardSuppliesGstObject.action = "R";
	inwardSuppliesGstObject.ty = "GST";
	inwardSuppliesGstObject.inter = parseFloat($("#inwardsup_exemptnil_inter").val());
	inwardSuppliesGstObject.intra = parseFloat($("#inwardsup_exemptnil_intra").val());
	inwardSuppliesGstNonGstListArray.push(inwardSuppliesGstObject);
	//Non-GST Supply
	var inwardSuppliesNonGstObject = new Object();
	inwardSuppliesNonGstObject.action = "R";
	inwardSuppliesNonGstObject.ty = "NONGST";
	inwardSuppliesNonGstObject.inter = parseFloat($("#inwardsup_nongst_inter").val());
	inwardSuppliesNonGstObject.intra = parseFloat($("#inwardsup_nongst_intra").val());
	inwardSuppliesGstNonGstListArray.push(inwardSuppliesNonGstObject);
	
//6.1 Interest Payable	
	var interestPayableObject = new Object();
	interestPayableObject.action = "R";
	interestPayableObject.iamt = parseFloat($("#interest_iamt").val());
	interestPayableObject.camt = parseFloat($("#interest_camt").val());
	interestPayableObject.samt = parseFloat($("#interest_samt").val());
	interestPayableObject.csamt = parseFloat($("#interest_csamt").val());	

//3.2 Inter state
	var interStateSuppliesListArray = new Array();
	var temppTotal = 0;
	var temppIamt = 0;
	//Supplies made to Unregistered Persons
	var unregInsterStateSupObject;
	$("#3_2_unregisteredTable tbody").each(function() {
		var tbodyfirstelement;
		var tbodyId = this.id;
		if(tbodyId == 0)
			tbodyfirstelement = this.childNodes[1].value;
		else
			tbodyfirstelement = this.childNodes[0].value;
		if(tbodyfirstelement != undefined){
			if($("#interstate_unreg_pos_"+tbodyfirstelement).val() != '' && !findNullValue("interstate_unreg_totamt_",tbodyfirstelement) && !findNullValue("interstate_unreg_iamt_",tbodyfirstelement)){
				unregInsterStateSupObject = new Object();
				unregInsterStateSupObject.action = "R";
				unregInsterStateSupObject.sup_ty = "unreg_details";
				unregInsterStateSupObject.pos = appendZeroToNumber($("#interstate_unreg_pos_"+tbodyfirstelement).val());
				unregInsterStateSupObject.txval = parseFloat($("#interstate_unreg_totamt_"+tbodyfirstelement).val());	
				unregInsterStateSupObject.iamt = parseFloat($("#interstate_unreg_iamt_"+tbodyfirstelement).val());
				interStateSuppliesListArray.push(unregInsterStateSupObject);
			}			
		}else{
			console.log("Error in creating Json for data of unreg table");
			bootbox.alert("Something went wrong", function() {
				$("#savePrepareGstr3b").prop("disabled", false);
		    	$(".loader").hide();
				return;
			});
		}
     });	
	
	//Supplies made to Composition Taxable Persons
	var compositionInsterStateSupObject;
	$("#3_2_compositionTable tbody").each(function() {
		var tbodyfirstelement;
		var tbodyId = this.id;
		if(tbodyId == 0)
			tbodyfirstelement = this.childNodes[1].value;
		else
			tbodyfirstelement = this.childNodes[0].value;
		if(tbodyfirstelement != undefined){
			if($("#interstate_compositn_pos_"+tbodyfirstelement).val() != '' && !findNullValue("interstate_compositn_totamt_",tbodyfirstelement) && !findNullValue("interstate_compositn_iamt_",tbodyfirstelement)){
				compositionInsterStateSupObject = new Object();
				compositionInsterStateSupObject.action = "R";
				compositionInsterStateSupObject.sup_ty = "comp_details";
				compositionInsterStateSupObject.pos = appendZeroToNumber($("#interstate_compositn_pos_"+tbodyfirstelement).val()); 
				compositionInsterStateSupObject.txval = parseFloat($("#interstate_compositn_totamt_"+tbodyfirstelement).val());	
				compositionInsterStateSupObject.iamt = parseFloat($("#interstate_compositn_iamt_"+tbodyfirstelement).val());
				interStateSuppliesListArray.push(compositionInsterStateSupObject);
			}
		}else{
			console.log("Error in creating Json for data of composition table");
			bootbox.alert("Something went wrong", function() {
				$("#savePrepareGstr3b").prop("disabled", false);
		    	$(".loader").hide();
				return;
			});
		}
     });	
	
	//Supplies made to UIN holders
	var uinInsterStateSupObject;
	$("#3_2_uinTable tbody").each(function() {
		var tbodyfirstelement;
		var tbodyId = this.id;
		if(tbodyId == 0)
			tbodyfirstelement = this.childNodes[1].value;
		else
			tbodyfirstelement = this.childNodes[0].value;
		if(tbodyfirstelement != undefined){
			if($("#interstate_uin_pos_"+tbodyfirstelement).val() != "" && !findNullValue("interstate_uin_totamt_",tbodyfirstelement) && !findNullValue("interstate_uin_iamt_",tbodyfirstelement)){
				uinInsterStateSupObject = new Object();
				uinInsterStateSupObject.action = "R";
				uinInsterStateSupObject.sup_ty = "uin_details";
				uinInsterStateSupObject.pos = appendZeroToNumber($("#interstate_uin_pos_"+tbodyfirstelement).val());	
				uinInsterStateSupObject.txval = parseFloat($("#interstate_uin_totamt_"+tbodyfirstelement).val());	
				uinInsterStateSupObject.iamt = parseFloat($("#interstate_uin_iamt_"+tbodyfirstelement).val());
				interStateSuppliesListArray.push(uinInsterStateSupObject);
			}
		}else{
			console.log("Error in creating Json for data of uin table");
			bootbox.alert("Something went wrong", function() {
				$("#savePrepareGstr3b").prop("disabled", false);
		    	$(".loader").hide();
				return;
			});
		}
     });		
	
	var jsonData = {
			"gstin": gstin,
			"ret_period": financialPeriod,
			"sup_details" : supDetailsListArray,
			"inter_sup" : interStateSuppliesListArray,
			"itc_elg" : eligibleItcListArray,
			"inward_sup" : inwardSuppliesGstNonGstListArray,
			"intr_details" : interestPayableObject			
	}
	
	return jsonData;
}

function appendZeroToNumber(num) {
    return ( num.toString().length < 2 ? "0"+num : num ).toString();
}

function saveDataToJioGst(){
  var jsonData = generateJson();
  console.log("generateJson :"+JSON.stringify(jsonData));
  $.ajax({
		url : "gstr3bSaveToJiogst",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {app_code : $("#appCode").val()},
		data : JSON.stringify(jsonData),
		async : false,
		beforeSend: function(){
	    	$(".loader").show();
	    	$('#wholePrepareform').hide();
	    },
	    complete: function(){
	    	$(".loader").hide();
	    	$('#wholePrepareform').show();
			$("#savePrepareGstr3b").prop("disabled", false);
	    },
		success : function(jsonVal,fTextStatus,fRequest) {
			if (isValidSession(jsonVal) == 'No') {
				if(loggedInFrom == 'MOBILE')
					window.location.href = getDefaultSessionExpirePage();
				else
					window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			if(isValidToken(jsonVal) == 'No'){
				if(loggedInFrom == 'MOBILE')
					window.location.href = getCsrfErrorPage();
				else
					window.location.href = getWizardCsrfErrorPage();
				return;
			}			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			if(jsonVal.status_cd == '0' || jsonVal.status_cd == '1' ){
		    	$(".loader").hide();
		    	$('#wholePrepareform').show();
				$("#savePrepareGstr3b").prop("disabled", false);
				bootbox.alert(jsonVal.error_desc);
			}else if(jsonVal.status_cd == '400'){
				
				var msg ='';
				$.each(jsonVal.details.err,function(i,value) {
					$.each(value,function(j,value) {
						var type = '';
						if(value.sup_ty == 'osup_det')
							type = '(a) Outward Taxable Supplies (Other than Zero Rated, Nil Rated, and Exempted)';
						else if(value.sup_ty == 'osup_zero')
							type = '(b) Outward Taxable Supplies (Zero Rated)';
						else if(value.sup_ty == 'osup_nil_exmp')
							type = '(c) Other Outward Supplies (Nil Rated, Exempted)';
						else if(value.sup_ty == 'osup_nongst')
							type = '(d) Inward Supplies (Liable to Reverse Charge)';
						else if(value.sup_ty == 'osup_nongst')
							type = '(e) Non-GST Outward Supplies';
						else if(value.sup_ty == 'unreg_details')
							type = 'Supplies made to Unregistered Persons';
						else if(value.sup_ty == 'comp_details')
							type = 'Supplies made to Composition Taxable Persons';
						else if(value.sup_ty == 'uin_details')
							type = 'Supplies made to UIN holders';
						else if(value.sup_ty == 'itc_avl')
							type = '(A) ITC Available (whether in full or part)';
						else if(value.sup_ty == 'itc_rev')
							type = '(B) ITC Reversed';
						else if(value.sup_ty == 'itc_net')
							type = '(C) Net ITC Available (A) - (B)';
						else if(value.sup_ty == 'itc_inelg')
							type = '(D) Ineligible ITC';
						else if(value.sup_ty == 'isup_details')
							type = 'From a Supplier under Composition Scheme, Exempt and Nil Rated Supply';
						else if(value.sup_ty == 'nongst')
							type = 'Non-GST Supply';
						else if(value.sup_ty == 'intr_details')
							type = 'Interest';
						
						msg += value.error_msg+' for '+type+' '; 
					});
				});
				bootbox.alert(jsonVal.usr_msg+' '+jsonVal.usr_act+' '+msg);
			}else if(jsonVal.data.status == 'Success'){
				bootbox.alert("Saved Successfully", function() {
					redirectToPreviousPage();
					return;
				});
			}
		},
         error: function (data,status,er) {  
        	 getInternalServerErrorPage();   
        }
    });
}
