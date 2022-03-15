//3.1 outward & inward to reverse charge
		var totamtNetSuppArray = ["outward_nonzero_totamt", "outward_zero_totamt", "otheroutward_totamt", "inward_totamt", "nongstoutward_totamt"];
		var iamtNetSuppArray = ["outward_nonzero_iamt", "outward_zero_iamt", "inward_iamt"];
		var camtNetSuppArray = ["outward_nonzero_camt", "inward_camt"];
		var samtNetSuppArray = ["outward_nonzero_samt", "inward_samt"];
		var csamtNetSuppArray = ["outward_nonzero_csamt", "outward_zero_csamt", "inward_csamt"];
//4 Eligilbe Itc
//4.a Itc Available		
		var iamtItcAvailableArray = ["itcavailable_goods_iamt", "itcavailable_services_iamt", "itcavailable_inwardreverse_iamt","itcavailable_inwardisd_iamt","itcavailable_allotheritc_iamt"];
		var camtItcAvailableArray = ["itcavailable_inwardreverse_camt", "itcavailable_inwardisd_camt","itcavailable_allotheritc_camt"];
		var samtItcAvailableArray = ["itcavailable_inwardreverse_samt", "itcavailable_inwardisd_samt", "itcavailable_allotheritc_samt"];
		var csamtItcAvailableArray = ["itcavailable_goods_csamt", "itcavailable_services_csamt", "itcavailable_inwardreverse_csamt","itcavailable_inwardisd_csamt","itcavailable_allotheritc_csamt"];
//4.a Itc Reserved	
		var iamtItcReservedArray = ["itcreversed_4243cgstrules_iamt", "itcreversed_others_iamt"];
		var camtItcReservedArray = ["itcreversed_4243cgstrules_camt", "itcreversed_others_camt"];
		var samtItcReservedArray = ["itcreversed_4243cgstrules_samt", "itcreversed_others_samt"];
		var csamtItcReservedArray = ["itcreversed_4243cgstrules_csamt", "itcreversed_others_csamt"];
//4.a Itc Ineligible
		var iamtItcEligibleArray = ["itcineligible_section17_iamt", "itcineligible_others_iamt"];
		var camtItcEligibleArray = ["itcineligible_section17_camt", "itcineligible_others_camt"];
		var samtItcEligibleArray = ["itcineligible_section17_samt", "itcineligible_others_samt"];
		var csamtItcEligibleArray = ["itcineligible_section17_csamt", "itcineligible_others_csamt"];
		
//3.2 Inter state
var interStatePosArray = ["interstate_unreg_pos_","interstate_compositn_pos_","interstate_uin_pos_"];
var interStateUnregArray = ["3_2_unreg_totaltaxablevalue","3_2_unreg_itaxamt"];
var interStateCompositnArray = ["3_2_compositn_totaltaxablevalue","3_2_compositn_itaxamt"];
var interStateUinArray = ["3_2_uin_totaltaxablevalue","3_2_uin_itaxamt"];

var stateListJson = '';
	
$(document).ready(function()
{		
	$(".loader").show();
	var counter3_2_unreg=0;			
	var counter3_2_compositn=0;	
	var counter3_2_uin=0;			
	var gstinId = $("#gstinId").val(); 
	var financialPeriod = $("#financialPeriod").val(); 
	var appCode = $("#appCode").val(); 
	var loggedInFrom = $("#loggedInFrom").val();
	$('.rowData').hide();

	$("#removelastRowUnregTab, #removelastRowCompTab, #removelastRowUinTab").hide();
	callAjaxForPrepareForm(gstinId, financialPeriod, appCode, loggedInFrom);		

	if(typeof interStatePosArray != undefined && interStatePosArray != null && interStatePosArray.length > 0){
		for(var x=0;x<interStatePosArray.length;x++){
			stateListJson = autoPopulateStateList(interStatePosArray[x],'0','',stateListJson); //,"0.00","0.00"
		}
	}else{
		bootbox.alert("Something went wrong", function() {
	    	$(".loader").hide();
			redirectToPreviousPage();
			return;
		});
	}	
		
	$('.dropdown-menu li a').on('click', function(){
		var text = $(this).text();
		var id= $(this).data(id);
		$(this).parents('.btn-group').find('button').html(text+ '<span class="caret"></span>');
		$('.rowData').hide()
		$('#'+id.id).show();
	});

	$("#outward_nonzero_totamt, #outward_nonzero_iamt, #outward_nonzero_camt, #outward_nonzero_samt, #outward_nonzero_csamt, #outward_zero_totamt, #outward_zero_iamt, #outward_zero_csamt, #otheroutward_totamt, #inward_totamt, #inward_iamt, #inward_camt, #inward_samt, #inward_csamt, #nongstoutward_totamt, #itcavailable_goods_iamt, #itcavailable_goods_csamt, #itcavailable_services_iamt, #itcavailable_services_csamt, #itcavailable_inwardreverse_iamt, #itcavailable_inwardreverse_camt, #itcavailable_inwardreverse_samt, #itcavailable_inwardreverse_csamt, #itcavailable_inwardisd_iamt, #itcavailable_inwardisd_camt, #itcavailable_inwardisd_samt, #itcavailable_inwardisd_csamt, #itcavailable_allotheritc_iamt, #itcavailable_allotheritc_camt, #itcavailable_allotheritc_samt, #itcavailable_allotheritc_csamt, #itcreversed_4243cgstrules_iamt, #itcreversed_4243cgstrules_camt, #itcreversed_4243cgstrules_samt, #itcreversed_4243cgstrules_csamt, #itcreversed_others_iamt, #itcreversed_others_camt, #itcreversed_others_samt, #itcreversed_others_csamt, #itcineligible_section17_iamt, #itcineligible_section17_camt, #itcineligible_section17_samt, #itcineligible_section17_csamt, #itcineligible_others_iamt, #itcineligible_others_camt, #itcineligible_others_samt, #itcineligible_others_csamt, #inwardsup_exemptnil_inter, #inwardsup_exemptnil_intra, #inwardsup_nongst_inter, #inwardsup_nongst_intra, #interest_iamt, #interest_camt, #interest_samt, #interest_csamt")
	  .click(function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			}					
			$(this).val('');
	});

	$("#outward_nonzero_totamt, #outward_nonzero_iamt, #outward_nonzero_camt, #outward_nonzero_samt, #outward_nonzero_csamt, #outward_zero_totamt, #outward_zero_iamt, #outward_zero_csamt, #otheroutward_totamt, #inward_totamt, #inward_iamt, #inward_camt, #inward_samt, #inward_csamt, #nongstoutward_totamt, #itcavailable_goods_iamt, #itcavailable_goods_csamt, #itcavailable_services_iamt, #itcavailable_services_csamt, #itcavailable_inwardreverse_iamt, #itcavailable_inwardreverse_camt, #itcavailable_inwardreverse_samt, #itcavailable_inwardreverse_csamt, #itcavailable_inwardisd_iamt, #itcavailable_inwardisd_camt, #itcavailable_inwardisd_samt, #itcavailable_inwardisd_csamt, #itcavailable_allotheritc_iamt, #itcavailable_allotheritc_camt, #itcavailable_allotheritc_samt, #itcavailable_allotheritc_csamt, #itcreversed_4243cgstrules_iamt, #itcreversed_4243cgstrules_camt, #itcreversed_4243cgstrules_samt, #itcreversed_4243cgstrules_csamt, #itcreversed_others_iamt, #itcreversed_others_camt, #itcreversed_others_samt, #itcreversed_others_csamt, #itcineligible_section17_iamt, #itcineligible_section17_camt, #itcineligible_section17_samt, #itcineligible_section17_csamt, #itcineligible_others_iamt, #itcineligible_others_camt, #itcineligible_others_samt, #itcineligible_others_csamt, #inwardsup_exemptnil_inter, #inwardsup_exemptnil_intra, #inwardsup_nongst_inter, #inwardsup_nongst_intra, #interest_iamt, #interest_camt, #interest_samt, #interest_csamt")
	  .on("keyup input",function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			}					
			this.value = this.value.replace(/[^[0-9.]*$/, '');
			var patt = new RegExp(/[0-9]*[.]{1}[0-9]{2}/i);
		    var matchedString = $(this).val().match(patt);
		    if (matchedString) {
		        $(this).val(matchedString);
		    }
	});

	$("#outward_nonzero_totamt, #outward_nonzero_iamt, #outward_nonzero_camt, #outward_nonzero_samt, #outward_nonzero_csamt, #outward_zero_totamt, #outward_zero_iamt, #outward_zero_csamt, #otheroutward_totamt, #inward_totamt, #inward_iamt, #inward_camt, #inward_samt, #inward_csamt, #nongstoutward_totamt, #itcavailable_goods_iamt, #itcavailable_goods_csamt, #itcavailable_services_iamt, #itcavailable_services_csamt, #itcavailable_inwardreverse_iamt, #itcavailable_inwardreverse_camt, #itcavailable_inwardreverse_samt, #itcavailable_inwardreverse_csamt, #itcavailable_inwardisd_iamt, #itcavailable_inwardisd_camt, #itcavailable_inwardisd_samt, #itcavailable_inwardisd_csamt, #itcavailable_allotheritc_iamt, #itcavailable_allotheritc_camt, #itcavailable_allotheritc_samt, #itcavailable_allotheritc_csamt, #itcreversed_4243cgstrules_iamt, #itcreversed_4243cgstrules_camt, #itcreversed_4243cgstrules_samt, #itcreversed_4243cgstrules_csamt, #itcreversed_others_iamt, #itcreversed_others_camt, #itcreversed_others_samt, #itcreversed_others_csamt, #itcineligible_section17_iamt, #itcineligible_section17_camt, #itcineligible_section17_samt, #itcineligible_section17_csamt, #itcineligible_others_iamt, #itcineligible_others_camt, #itcineligible_others_samt, #itcineligible_others_csamt, #inwardsup_exemptnil_inter, #inwardsup_exemptnil_intra, #inwardsup_nongst_inter, #inwardsup_nongst_intra, #interest_iamt, #interest_camt, #interest_samt, #interest_csamt")
	  .on("blur",function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			}					
			if (event.which != 46 || $(this).val().indexOf('.') != -1){
				$(this).val(convertStringToFloatWithZeroDecimal(this.value, 2, 0));
		    }
			if(this.value == '' || this.value == null || this.value == undefined){
		    	$(this).val(convertStringToFloatWithZeroDecimal(this.value, 2, 0));
		    }
	});	
	$(document).on('change','.interstateUnregPos, .interstateCompositnPos, .interstateUinPos', function(e){
		if(this.value == ''){
			$(this).css({"border-color" : "#ff0000"});
		}else{
			$(this).css({"border-color" : "#2f65b0"});
		}
	});
	/*------------calculate values on user input--------------*/
	//netSupplies
	$(".netSupTotAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(totamtNetSuppArray,'prepare');
		$("#3_1_totaltaxablevalue").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".netSupIAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(iamtNetSuppArray,'prepare');
		$("#3_1_itaxamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".netSupCAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(camtNetSuppArray,'prepare');
		$("#3_1_ctaxamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".netSupSAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(samtNetSuppArray,'prepare');
		$("#3_1_staxamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".netSupCsAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(csamtNetSuppArray,'prepare');
		$("#3_1_csamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});
	
	//itcAvailable
	$(".itcAvailableIAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(iamtItcAvailableArray,'prepare');
		$("#itc_available_iamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_iamt","itc_reserved_iamt","itc_net_iamt");
	});

	$(".itcAvailableCAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(camtItcAvailableArray,'prepare');
		$("#itc_available_camt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_camt","itc_reserved_camt","itc_net_camt");
	});

	$(".itcAvailableSAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(samtItcAvailableArray,'prepare');
		$("#itc_available_samt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_samt","itc_reserved_samt","itc_net_samt");
	});

	$(".itcAvailableCsAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(csamtItcAvailableArray,'prepare');
		$("#itc_available_csamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_csamt","itc_reserved_csamt","itc_net_csamt");
	});

	//itcReserved
	$(".itcReservedIAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(iamtItcReservedArray,'prepare');
		$("#itc_reserved_iamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_iamt","itc_reserved_iamt","itc_net_iamt");
	});

	$(".itcReservedCAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(camtItcReservedArray,'prepare');
		$("#itc_reserved_camt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_camt","itc_reserved_camt","itc_net_camt");
	});

	$(".itcReservedSAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(samtItcReservedArray,'prepare');
		$("#itc_reserved_samt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_samt","itc_reserved_samt","itc_net_samt");
	});

	$(".itcReservedCsAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(csamtItcReservedArray,'prepare');
		$("#itc_reserved_csamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
		evaluateNetITCAvailable("itc_available_csamt","itc_reserved_csamt","itc_net_csamt");
	});
	
	//itcIneligible
	$(".itcIneligibleIAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(iamtItcEligibleArray,'prepare');
		$("#itcineligible_itc_iamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".itcIneligibleCAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(camtItcEligibleArray,'prepare');
		$("#itcineligible_itc_camt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".itcIneligibleSAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(samtItcEligibleArray,'prepare');
		$("#itcineligible_itc_samt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});

	$(".itcIneligibleCsAmt").on('keyup',function(e){
		var totalAmt = iterateNSum(csamtItcEligibleArray,'prepare');
		$("#itcineligible_itc_csamt").text(convertStringToFloatWithZeroDecimal(totalAmt, 2, 0));
	});	    
	
	//Inter state
	$(document).on('keyup','.interstateUnregTotAmt, .interstateUnregIAmt', function(e){
		tableFieldId("3_2_unregisteredTable","interstate_unreg_totamt_","interstate_unreg_iamt_",'prepare');
	});	  
	
	$(document).on('keyup','.interstateCompositnTotAmt, .interstateCompositnIAmt',function(e){
		tableFieldId("3_2_compositionTable","interstate_compositn_totamt_","interstate_compositn_iamt_",'prepare');
	});	

	$(document).on('keyup','.interstateUinTotAmt, .interstateUinIAmt',function(e){
		tableFieldId("3_2_uinTable","interstate_uin_totamt_","interstate_uin_iamt_",'prepare');
	});		
	/*--------------------------------------------------------*/	

	$(document).on('click','.removeTbody',function() {
		var id = $(this).data('tid');
		var removeCount = 0;	
		if(id == '3_2_unregisteredTable'){
			removeCount = $("#counter3_2_unreg").val();
			removeCount--;
			$("#counter3_2_unreg").val(removeCount);
			if(removeCount == 0)
				$("#removelastRowUnregTab").hide();
		}else if(id == '3_2_compositionTable'){
			removeCount = $("#counter3_2_compositn").val();
			removeCount--;
			$("#counter3_2_compositn").val(removeCount);
			if(removeCount == 0)
				$("#removelastRowCompTab").hide();
		}else if(id == '3_2_uinTable'){
			removeCount = $("#counter3_2_uin").val();
			removeCount--;
			$("#counter3_2_uin").val(removeCount);
			if(removeCount == 0)
				$("#removelastRowUinTab").hide();
		}
		$(this).closest('tbody').remove();

		if(id == '3_2_unregisteredTable')
			tableFieldId("3_2_unregisteredTable","interstate_unreg_totamt_","interstate_unreg_iamt_",'prepare');			
		else if(id == '3_2_compositionTable')
			tableFieldId("3_2_compositionTable","interstate_compositn_totamt_","interstate_compositn_iamt_",'prepare');			
		else if(id == '3_2_uinTable')
			tableFieldId("3_2_uinTable","interstate_uin_totamt_","interstate_uin_iamt_",'prepare');			
	});                                          

	$('.addTbodyRow').on('click', function(){
		var id = $(this).data('tid');
		var posId = "";
		var iamtId = "";
		var totamtId = "";
		var addCount = 0;		
		var fieldtTottaxvalClass = "";
		var fieldtIAmtClass = "";
		var fieldPosClass = "";
		var errorflag = false;
		if(id == '3_2_unregisteredTable'){
			addCount = $("#counter3_2_unreg").val();
			errorflag = checkTableValue("3_2_unregisteredTable","interstate_unreg_pos_","interstate_unreg_totamt_","interstate_unreg_iamt_",addCount,"addGrid")
			if(!errorflag){
				posId = $(this).data('unregposid');
				iamtId = $(this).data('unregiamtid');
				totamtId = $(this).data('unregtotamtid');				
				addCount++;
				$("#counter3_2_unreg").val(addCount);
				fieldPosClass = "interstateUnregPos";
				fieldtTottaxvalClass = "interstateUnregTotAmt";
				fieldtIAmtClass = "interstateUnregIAmt";
				if(addCount>0)
					$("#removelastRowUnregTab").show();
			}			
		}else if(id == '3_2_compositionTable'){
			addCount = $("#counter3_2_compositn").val();
			errorflag = checkTableValue("3_2_compositionTable","interstate_compositn_pos_","interstate_compositn_totamt_","interstate_compositn_iamt_",addCount,"addGrid")
			if(!errorflag){
				posId = $(this).data('compositnposid');
				iamtId = $(this).data('compositniamtid');
				totamtId = $(this).data('compositntotamtid');				
				addCount++;
				$("#counter3_2_compositn").val(addCount);
				fieldPosClass = "interstateCompositnPos";
				fieldtTottaxvalClass = "interstateCompositnTotAmt";
				fieldtIAmtClass = "interstateCompositnIAmt";
				if(addCount>0)
					$("#removelastRowCompTab").show();
			}			
		}else if(id == '3_2_uinTable'){
			addCount = $("#counter3_2_uin").val();
			errorflag = checkTableValue("3_2_uinTable","interstate_uin_pos_","interstate_uin_totamt_","interstate_uin_iamt_",addCount,"addGrid")
			if(!errorflag){
				posId = $(this).data('uinposid');
				iamtId = $(this).data('uiniamtid');
				totamtId = $(this).data('uintotamtid');				
				addCount++;
				$("#counter3_2_uin").val(addCount);
				fieldPosClass = "interstateUinPos";
				fieldtTottaxvalClass = "interstateUinTotAmt";
				fieldtIAmtClass = "interstateUinIAmt";
				if(addCount>0)
					$("#removelastRowUinTab").show();
			}
		}	
		if(!errorflag){
			var tbody = appendTbody(posId,totamtId,iamtId,addCount,id,'0.00','0.00',fieldtTottaxvalClass,fieldtIAmtClass,fieldPosClass);
			$("#"+id+" table").append(tbody);
			stateListJson = autoPopulateStateList(posId,addCount,'',stateListJson);		
		}
	});

	$('.removelastRow').on('click', function(){
		var id = $(this).data('tid');
		var flag = false;
		var removeCount = 0;
		if(id == '3_2_unregisteredTable'){
			removeCount = $("#counter3_2_unreg").val();	
			if(removeCount>0){
				removeCount--;
				$("#counter3_2_unreg").val(removeCount);
				flag = true;
			}
			if(removeCount == 0)
				$("#removelastRowUnregTab").hide();
		}else if(id == '3_2_compositionTable'){			
			removeCount = $("#counter3_2_compositn").val();
			if(removeCount>0){
				removeCount--;
				$("#counter3_2_compositn").val(removeCount);
				flag = true;
			}
			if(removeCount == 0)
				$("#removelastRowCompTab").hide();
		}else if(id == '3_2_uinTable'){			
			removeCount = $("#counter3_2_uin").val();
			if(removeCount>0){
				removeCount--;
				$("#counter3_2_uin").val(removeCount);
				flag = true;
			}
			if(removeCount == 0)
				$("#removelastRowUinTab").hide();
		}
		
		if(flag)
			$('#'+id+' table tbody:last-child').remove();
		
		if(id == '3_2_unregisteredTable')
			tableFieldId("3_2_unregisteredTable","interstate_unreg_totamt_","interstate_unreg_iamt_",'prepare');			
		else if(id == '3_2_compositionTable')
			tableFieldId("3_2_compositionTable","interstate_compositn_totamt_","interstate_compositn_iamt_",'prepare');			
		else if(id == '3_2_uinTable')
			tableFieldId("3_2_uinTable","interstate_uin_totamt_","interstate_uin_iamt_",'prepare');			
	});	
});

function evaluateNetITCAvailable(itcAvailA,itcReservedB,netAvailC){
	console.log("itcAvailA : "+$("#"+itcAvailA).val()+" itcReservedB : "+$("#"+itcReservedB).val());
	var totalValue = parseFloat($("#"+itcAvailA).text())-parseFloat($("#"+itcReservedB).text());
    $("#"+netAvailC).text(convertStringToFloatWithZeroDecimal(totalValue, 2, 0));
}

function callAjaxForPrepareForm(gstinId, financialPeriod, appCode, loggedInFrom)
{	
	var inputData = {
		"gstin" : gstinId,
		"fp" : financialPeriod, 
		"serviceType" : "",
		"service" : ""
	};
	
	$.ajax({
		url : "getGstr3bJiogstData",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {app_code : appCode},
		data : JSON.stringify(inputData),
		async : false,
		beforeSend: function(){
	    	$(".loader").show();
	    },
	    complete: function(){
	    	$(".loader").hide();
	    	$('#wholePrepareform').show();
	    },
		success:function(json,fTextStatus,fRequest){
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
			if(json.status_cd == '1'){
				bootbox.alert(json.error_desc, function() {
					redirectToPreviousPage();
					return;
				});
			}else{
		    	populateDataFromServer(json);
			}			
         },
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
	}); 
}

function populateDataFromServer(json)
{
	$.each(json.data.record,function(i,recordArray) {
		$.each(recordArray,function(i2,detailsArray) {
			if(i2 == 'sup_details'){
				$.each(detailsArray,function(i3,value) {
					if(value.sup_ty == 'osup_det'){
						$("#outward_nonzero_totamt").val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
						$("#outward_nonzero_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#outward_nonzero_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
						$("#outward_nonzero_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
						$("#outward_nonzero_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.sup_ty == 'osup_zero'){
						$("#outward_zero_totamt").val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
						$("#outward_zero_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#outward_zero_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.sup_ty == 'osup_nil_exmp'){
						$("#otheroutward_totamt").val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
					}else if(value.sup_ty == 'isup_rev'){
						$("#inward_totamt").val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
						$("#inward_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#inward_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
						$("#inward_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
						$("#inward_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.sup_ty == 'osup_nongst'){
						$("#nongstoutward_totamt").val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
					}
				});
			}else if(i2 == 'inter_sup'){
				var gridCounterUnreg = 0;
				var gridCounterCompositn = 0;
				var gridCounterUin = 0;
				$.each(detailsArray,function(i3,value) {
					if(value.sup_ty == 'unreg_details'){
						if(gridCounterUnreg == 0 ){
							$("#interstate_unreg_totamt_"+gridCounterUnreg).val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#interstate_unreg_iamt_"+gridCounterUnreg).val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						}else{
							var tbody = appendTbody("interstate_unreg_pos_","interstate_unreg_totamt_","interstate_unreg_iamt_",gridCounterUnreg,"3_2_unregisteredTable",convertStringToFloatWithZeroDecimal(value.txval, 2, 0),convertStringToFloatWithZeroDecimal(value.iamt, 2, 0),"interstateUnregTotAmt","interstateUnregIAmt","interstateUnregPos");
							$("#3_2_unregisteredTable table").append(tbody);
							$("#removelastRowUnregTab").show();
						}						
						stateListJson = autoPopulateStateList("interstate_unreg_pos_",gridCounterUnreg,value.pos,stateListJson);
						counter3_2_unreg = gridCounterUnreg;
						$("#counter3_2_unreg").val(counter3_2_unreg);
						gridCounterUnreg++;
					}else if(value.sup_ty == 'comp_details'){
						if(gridCounterCompositn == 0 ){
							$("#interstate_compositn_totamt_"+gridCounterCompositn).val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#interstate_compositn_iamt_"+gridCounterCompositn).val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						}else{
							var tbody = appendTbody("interstate_compositn_pos_","interstate_compositn_totamt_","interstate_compositn_iamt_",gridCounterCompositn,"3_2_compositionTable",convertStringToFloatWithZeroDecimal(value.txval, 2, 0),convertStringToFloatWithZeroDecimal(value.iamt, 2, 0),"interstateCompositnTotAmt","interstateCompositnIAmt","interstateCompositnPos");
							$("#3_2_compositionTable table").append(tbody);
							$("#removelastRowCompTab").show();
						}						
						stateListJson = autoPopulateStateList("interstate_compositn_pos_",gridCounterCompositn,value.pos,stateListJson);
						counter3_2_compositn = gridCounterCompositn;
						$("#counter3_2_compositn").val(counter3_2_compositn);
						gridCounterCompositn++;
					}else if(value.sup_ty == 'uin_details'){
						if(gridCounterUin == 0 ){
							$("#interstate_uin_totamt_"+gridCounterUin).val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#interstate_uin_iamt_"+gridCounterUin).val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						}else{
							var tbody = appendTbody("interstate_uin_pos_","interstate_uin_totamt_","interstate_uin_iamt_",gridCounterUin,"3_2_uinTable",convertStringToFloatWithZeroDecimal(value.txval, 2, 0),convertStringToFloatWithZeroDecimal(value.iamt, 2, 0),"interstateUinTotAmt","interstateUinIAmt","interstateUinPos");
							$("#3_2_uinTable table").append(tbody);
							$("#removelastRowUinTab").show();
						}						
						stateListJson = autoPopulateStateList("interstate_uin_pos_",gridCounterUin,value.pos,stateListJson);
						
						counter3_2_uin = gridCounterUin;
						$("#counter3_2_uin").val(counter3_2_uin);
						gridCounterUin++;
					}					
				});				
			}else if(i2 == 'inward_sup'){
				$.each(detailsArray,function(i3,value) {
					if(value.sup_ty == 'isup_details'){ 
						if(value.ty == 'gst'){
							$("#inwardsup_exemptnil_inter").val(convertStringToFloatWithZeroDecimal(value.inter, 2, 0));
							$("#inwardsup_exemptnil_intra").val(convertStringToFloatWithZeroDecimal(value.intra, 2, 0));
						}
						else if(value.ty == 'nongst'){
							$("#inwardsup_nongst_inter").val(convertStringToFloatWithZeroDecimal(value.inter, 2, 0));
							$("#inwardsup_nongst_intra").val(convertStringToFloatWithZeroDecimal(value.intra, 2, 0));
						}
					}
				});
			}else if(i2 == 'itc_elg'){
				$.each(detailsArray,function(i3,value) {
					if(value.itc_ty == 'itc_avl'){
						if(value.ty == 'IMPG'){
							$("#itcavailable_goods_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_goods_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_goods_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_goods_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'IMPS'){
							$("#itcavailable_services_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_services_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_services_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_services_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'ISRC'){
							$("#itcavailable_inwardreverse_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_inwardreverse_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_inwardreverse_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_inwardreverse_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'ISD'){
							$("#itcavailable_inwardisd_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_inwardisd_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_inwardisd_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_inwardisd_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'OTH'){
							$("#itcavailable_allotheritc_totamt").val(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#itcavailable_allotheritc_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_allotheritc_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_allotheritc_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_allotheritc_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}
					}else if(value.itc_ty == 'itc_rev'){
						if(value.ty == 'RUL'){
							$("#itcreversed_4243cgstrules_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcreversed_4243cgstrules_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcreversed_4243cgstrules_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcreversed_4243cgstrules_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'OTH'){
							$("#itcreversed_others_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcreversed_others_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcreversed_others_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcreversed_others_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}
					}else if(value.itc_ty == 'itc_net'){
						$("#itc_net_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#itc_net_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
						$("#itc_net_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
						$("#itc_net_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.itc_ty == 'itc_inelg'){
						if(value.ty == 'RUL'){
							$("#itcineligible_section17_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcineligible_section17_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcineligible_section17_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcineligible_section17_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'OTH'){
							$("#itcineligible_others_iamt").val(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcineligible_others_camt").val(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcineligible_others_samt").val(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcineligible_others_csamt").val(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}
					}
				});
			}else if(i2 == 'intr_ltfee'){
				if(detailsArray.sup_ty == 'intr_details'){
					$("#interest_iamt").val(convertStringToFloatWithZeroDecimal(detailsArray.iamt, 2, 0));
					$("#interest_camt").val(convertStringToFloatWithZeroDecimal(detailsArray.camt, 2, 0));
					$("#interest_samt").val(convertStringToFloatWithZeroDecimal(detailsArray.samt, 2, 0));
					$("#interest_csamt").val(convertStringToFloatWithZeroDecimal(detailsArray.csamt, 2, 0));
				}
			}
		});
	});	
	calculateNetValues('prepare');
}

function appendTbody(posId,totamtId,iamtId,count,tid,totamtValue,iamtValue,fieldtTottaxvalClass,fieldtIAmtClass,fieldPosClass){
	return '<tbody id="'+count+'">'+
				'<input type="hidden" id="index_'+tid+'_'+count+'" value="'+count+'">'+
				'<tr>'+
					'<td class="w55 ">Place of Supply (State/ UT)</td>'+
					'<td class="text-right">'+
						'<select class="form-control '+fieldPosClass+'" id="'+posId+''+count+'" ></select>'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td class="w55 active">Total Taxable Value</td>'+
					'<td class="text-right"><input type="text" id="'+totamtId+''+count+'" value="'+totamtValue+'" class="form-control text-right '+fieldtTottaxvalClass+'"/></td>'+
					
				'</tr>'+
				'<tr>'+
					'<td class="w55 active">Total Integrated Tax Amount</td>'+
					'<td class="text-right"><input type="text" id="'+iamtId+''+count+'" value="'+iamtValue+'" class="form-control text-right '+fieldtIAmtClass+'"/></td>'+
				'</tr>'+
				'<tr>'+
					'<td class="text-right" colspan="2">'+
						'<button class="removeTbody btn btn-danger" type="button" data-tid="'+tid+'">  <i class="fa fa-trash-o"></i> Remove </button>'+
					'</td>'+
				'</tr>'+
			'</tbody>';
}

