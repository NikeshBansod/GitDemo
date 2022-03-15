var stateListJson = '';

$(document).ready(function()
{		
	$("#loadingmessage").show();
	var counter3_2_unreg=0;			
	var counter3_2_compositn=0;	
	var counter3_2_uin=0;	
	var gstinId = $("#gstinId").val(); 
	var financialPeriod = $("#financialPeriod").val(); 
	var appCode = $("#appCode").val(); 
	var loggedInFrom = $("#loggedInFrom").val();
	var username = $("#username").val();

	callAjaxForDraftForm(gstinId, financialPeriod, appCode, loggedInFrom);		
	$("#removelastRowUnregTab, #removelastRowCompTab, #removelastRowUinTab").hide();
	$('.dropdown-menu li a').on('click', function(){
		var text = $(this).text();
		var id= $(this).data(id);
		$(this).parents('.btn-group').find('button').html(text+ '<span class="caret"></span>');
		$('.rowData').hide()
		$('#'+id.id).show();
	});
	
	$("#saveDraftGstr3bToGstn").click(function (e){
		saveDataToGSTIN(gstinId, financialPeriod, appCode, loggedInFrom, username);
	});
});

function callAjaxForDraftForm(gstinId, financialPeriod, appCode, loggedInFrom)
{	
	var inputData = {
		"gstin" : gstinId,
		"fp" : financialPeriod, 
		"serviceType" : "",
		"service" : ""
	};
	
	$.ajax({
		url : "getGstr3bJiogstL2",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {app_code : appCode},
		data : JSON.stringify(inputData),
		async : false,
		beforeSend: function(){
	    	$("#loadingmessage").show();
	    },
	    complete: function(){
	    	$("#loadingmessage").hide();
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
		    	populateDataFromServerForDraft(json);
			}			
         },
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
	}); 
}


function populateDataFromServerForDraft(json)
{
	$.each(json.data.record,function(i,recordArray) {
		$.each(recordArray,function(i2,detailsArray) {
			if(i2 == 'sup_details'){
				$.each(detailsArray,function(i3,value) {
					if(value.sup_ty == 'osup_det'){
						$("#outward_nonzero_totamt").text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
						$("#outward_nonzero_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#outward_nonzero_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
						$("#outward_nonzero_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
						$("#outward_nonzero_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.sup_ty == 'osup_zero'){
						$("#outward_zero_totamt").text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
						$("#outward_zero_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#outward_zero_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.sup_ty == 'osup_nil_exmp'){
						$("#otheroutward_totamt").text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
					}else if(value.sup_ty == 'isup_rev'){
						$("#inward_totamt").text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
						$("#inward_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#inward_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
						$("#inward_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
						$("#inward_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.sup_ty == 'osup_nongst'){
						$("#nongstoutward_totamt").text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
					}
				});
			}else if(i2 == 'inter_sup'){
				var gridCounterUnreg = 0;
				var gridCounterCompositn = 0;
				var gridCounterUin = 0;
				$.each(detailsArray,function(i3,value) {
					if(value.sup_ty == 'unreg_details'){
						if(gridCounterUnreg == 0 ){
							$("#interstate_unreg_totamt_"+gridCounterUnreg).text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#interstate_unreg_iamt_"+gridCounterUnreg).text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
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
							$("#interstate_compositn_totamt_"+gridCounterCompositn).text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#interstate_compositn_iamt_"+gridCounterCompositn).text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
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
							$("#interstate_uin_totamt_"+gridCounterUin).text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#interstate_uin_iamt_"+gridCounterUin).text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
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
						if(value.ty == 'GST'){
							$("#inwardsup_exemptnil_inter").text('').text(convertStringToFloatWithZeroDecimal(value.inter, 2, 0));
							$("#inwardsup_exemptnil_intra").text('').text(convertStringToFloatWithZeroDecimal(value.intra, 2, 0));
						}
						else if(value.ty == 'NONGST'){
							$("#inwardsup_nongst_inter").text('').text(convertStringToFloatWithZeroDecimal(value.inter, 2, 0));
							$("#inwardsup_nongst_intra").text('').text(convertStringToFloatWithZeroDecimal(value.intra, 2, 0));
						}
					}
				});
			}else if(i2 == 'itc_elg'){
				$.each(detailsArray,function(i3,value) {
					if(value.itc_ty == 'itc_avl'){
						if(value.ty == 'IMPG'){
							$("#itcavailable_goods_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_goods_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_goods_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_goods_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'IMPS'){
							$("#itcavailable_services_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_services_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_services_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_services_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'ISRC'){
							$("#itcavailable_inwardreverse_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_inwardreverse_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_inwardreverse_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_inwardreverse_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'ISD'){
							$("#itcavailable_inwardisd_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_inwardisd_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_inwardisd_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_inwardisd_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'OTH'){
							$("#itcavailable_allotheritc_totamt").text('').text(convertStringToFloatWithZeroDecimal(value.txval, 2, 0));
							$("#itcavailable_allotheritc_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcavailable_allotheritc_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcavailable_allotheritc_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcavailable_allotheritc_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}
					}else if(value.itc_ty == 'itc_rev'){
						if(value.ty == 'RUL'){
							$("#itcreversed_4243cgstrules_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcreversed_4243cgstrules_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcreversed_4243cgstrules_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcreversed_4243cgstrules_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'OTH'){
							$("#itcreversed_others_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcreversed_others_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcreversed_others_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcreversed_others_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}
					}else if(value.itc_ty == 'itc_net'){
						$("#itc_net_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
						$("#itc_net_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
						$("#itc_net_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
						$("#itc_net_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
					}else if(value.itc_ty == 'itc_inelg'){
						if(value.ty == 'RUL'){
							$("#itcineligible_section17_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcineligible_section17_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcineligible_section17_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcineligible_section17_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}else if(value.ty == 'OTH'){
							$("#itcineligible_others_iamt").text('').text(convertStringToFloatWithZeroDecimal(value.iamt, 2, 0));
							$("#itcineligible_others_camt").text('').text(convertStringToFloatWithZeroDecimal(value.camt, 2, 0));
							$("#itcineligible_others_samt").text('').text(convertStringToFloatWithZeroDecimal(value.samt, 2, 0));
							$("#itcineligible_others_csamt").text('').text(convertStringToFloatWithZeroDecimal(value.csamt, 2, 0));
						}
					}
				});
			}else if(i2 == 'intr_ltfee'){
				if(detailsArray.sup_ty == 'intr_details'){
					$("#interest_iamt").text('').text(convertStringToFloatWithZeroDecimal(detailsArray.iamt, 2, 0));
					$("#interest_camt").text('').text(convertStringToFloatWithZeroDecimal(detailsArray.camt, 2, 0));
					$("#interest_samt").text('').text(convertStringToFloatWithZeroDecimal(detailsArray.samt, 2, 0));
					$("#interest_csamt").text('').text(convertStringToFloatWithZeroDecimal(detailsArray.csamt, 2, 0));
				}
			}
		});
	});	
	calculateNetValues('draft');
}

function appendTbody(posId,totamtId,iamtId,count,tid,totamtValue,iamtValue,fieldtTottaxvalClass,fieldtIAmtClass,fieldPosClass){
	return '<tbody id="'+count+'">'+
				'<input type="hidden" id="index_'+tid+'_'+count+'" value="'+count+'">'+
				'<tr>'+
					'<td class="w55 ">Place of Supply (State/ UT)</td>'+
					'<td class="text-right">'+
						'<select class="form-control '+fieldPosClass+'" id="'+posId+''+count+'" disabled></select>'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td class="w55 active">Total Taxable Value</td>'+
					'<td class="text-right"><label id="'+totamtId+''+count+'" class="'+fieldtTottaxvalClass+'">'+totamtValue+'</td>'+
					
				'</tr>'+
				'<tr>'+
					'<td class="w55 active">Total Integrated Tax Amount</td>'+
					'<td class="text-right"><label id="'+iamtId+''+count+'" class="'+fieldtIAmtClass+'">'+iamtValue+'</label></td>'+
				'</tr>'+
				/*'<tr>'+
					'<td class="text-right" colspan="2">'+
						'<button class="removeTbody btn btn-danger" type="button" data-tid="'+tid+'">  <i class="fa fa-trash-o"></i> Remove </button>'+
					'</td>'+
				'</tr>'+*/
			'</tbody>';
}

function saveDataToGSTIN(gstinId, financialPeriod, appCode, loggedInFrom, username){
  var jsonData = {
		  "sname":"ABC"
  };
  $.ajax({
		url : "getGstr3bGstnSave",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {app_code : appCode, fp : financialPeriod, gstin : gstinId, username : username},
		data : JSON.stringify(jsonData),
		async : false,
		beforeSend: function(){
	    	$("#loadingmessage").show();
	    	$('#wholePrepareform').hide();
	    },
	    complete: function(){
	    	$("#loadingmessage").hide();
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
			
			if(jsonVal.status_cd == '0' || jsonVal.status_cd == '1' || jsonVal.status_cd == '500'){
				bootbox.alert(jsonVal.error_desc);
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
