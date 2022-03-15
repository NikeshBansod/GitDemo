var addChgRowNum = 0;
var dynamicallyAddedAdditionalChargeJson = '';
   
function add_chg_row(){ 
    $(document).ready(function(){ 
    	addChgRowNum++;
    	var additionalChargeValue = $('select#additionalChargeName option:selected').val();
    	var additionalChargeAmount = $('#additionalChargeAmountToShow').val();
    	var additionalChargeName = '';
    	var additionalChargeId = 0;
    	if(additionalChargeAmount && (additionalChargeAmount > 0)){
    		var	values = additionalChargeValue.split('{--}');
    		additionalChargeId = values[0]; 
    		additionalChargeName = values[1];
    		additionalChargeAmountFixed = values[2];
    		additionalChargeAmount = parseFloat(additionalChargeAmount).toFixed(2);
    		$("#additionalChargeAmount").val(additionalChargeAmount);
    	    //dynamic generate accordion - Start
    		var $addChgToggle = $("#add_chg_toggle");
        	var recordNo = addChgRowNum;
        	$addChgToggle.append('<div class="cust-content" id="add_chg_start_'+recordNo+'">'
				        			+'<div class="heading">'
						                +'<div class="cust-con">'
						                    +'<h1 id="additionalChargeName_'+recordNo+'">'+additionalChargeName+'</h1>'
						                +'</div>'
						                +'<div class="cust-edit">'
						                    +'<div class="cust-icon">'
						                    	+'<a href="#callOnAddChgEditId" onclick="javascript:edit_add_chg_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						                    	+'<a href="#" onclick="javascript:remove_add_chg_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						                    +'</div>'
						                +'</div>'
						            +'</div>'
						            +'<div class="content">'
						                +'<div class="cust-con" id="addChgAmtForEdit_'+recordNo+'">'
						                    +'<p id="additionalChargeAmount_'+recordNo+'" >Amount : '+additionalChargeAmount+' </p>'
						                    
						                +'</div>'	
						            +'</div>'
						            +'<input type="hidden" id="additionalChargeId-'+recordNo+'" name="" value="'+additionalChargeId+'">'
						            +'<input type="hidden" id="additionalChargeName-'+recordNo+'" name="" value="'+additionalChargeName+'">'
						            +'<input type="hidden" id="additionalChargeAmount-'+recordNo+'" name="" value="'+additionalChargeAmount+'">'
						            +'<input type="hidden" id="additionalChargeAmountFixed-'+recordNo+'" name="" value="'+additionalChargeAmountFixed+'">'						            
       
        	);
        	
    	    //dynamic generate accordion - End
        	openCloseAddChargesAccordion(addChgRowNum);
        	resetAdditionalChargesValues();
    	}
    	
    	
    	
    });	

}

function openCloseAddChargesAccordion(rowNum){
	var currId = "/"+rowNum;
	
	if(addChgAccordionsId.includes(currId)){
		//donot add in accordion
	}else{
		$("#add_chg_start_"+rowNum+" .content").hide();
		$("#add_chg_start_"+rowNum+" .heading").click(function () {
			$(this).next(".content").slideToggle();
		});
		addChgAccordionsId = addChgAccordionsId +","+currId;
	}
	
}

function resetAdditionalChargesValues(){
	$('#additionalChargeName').val("");
	$('#additionalChargeAmountToShow').val("");
	$('#search-additional-charge-autocomplete').val("");
}

function edit_add_chg_row(recordNo){
	
	$("#add_chg_add").hide();//Hide the add button
	$("#add_chg_edit").show();//display the edit button
	
	$('#search-additional-charge-autocomplete').val($('#additionalChargeName-'+recordNo).val());
	$('#additionalChargeName').val($('#additionalChargeId-'+recordNo).val()+"{--}"+$('#additionalChargeName-'+recordNo).val()+"{--}"+parseFloat($('#additionalChargeAmountFixed-'+recordNo).val()));
	$('#additionalChargeAmountToShow').val($('#additionalChargeAmount-'+recordNo).val());
	$('#additionalChargeAmount').val($('#additionalChargeAmount-'+recordNo).val());
	$('#unqAddChgValId').val(recordNo);
	
	openCloseAddChargesAccordion(recordNo);
}

function update_add_chg_row(){
	var recordNo = $('#unqAddChgValId').val();
	var additionalChargeValue = $('select#additionalChargeName option:selected').val();
	var additionalChargeAmount = 0;
	var additionalChargeName = '';
	var additionalChargeId = 0;
	var	values = additionalChargeValue.split('{--}');
		additionalChargeId = values[0]; 
		additionalChargeName = values[1];
		//additionalChargeAmount = values[2];
		additionalChargeAmount = $('#additionalChargeAmountToShow').val();
		
	//show in display
	$('#additionalChargeName_'+recordNo).text(additionalChargeName);
	$('#additionalChargeAmount_'+recordNo).text(additionalChargeAmount);
	
	
	//set values in hidden fields
	$('#additionalChargeId-'+recordNo).val(additionalChargeId);
	$('#additionalChargeName-'+recordNo).val(additionalChargeName);
	$('#additionalChargeAmount-'+recordNo).val(additionalChargeAmount);
	
	$('#addChgAmtForEdit_'+recordNo).html("");
	$('#addChgAmtForEdit_'+recordNo).append(
			'<p id="additionalChargeAmount_'+recordNo+'" >Amount : '+additionalChargeAmount+' </p>'
	);

	
	$("#add_chg_edit").hide();//Hide the edit button
	$("#add_chg_add").show();//display the add  button  
	
	
	openCloseAddChargesAccordion(recordNo);
	resetAdditionalChargesValues();//reset form values
}

function remove_add_chg_row(recordNo){
	//alert("del"+recordNo);
	   $('#add_chg_start_'+recordNo).remove();
}

$("#search-additional-charge-autocomplete").autocomplete({
    source: function (request, response) {
        $.post("getAdditionalChargesList", {
            term: extractLastRW(request.term)
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
    	var zipToShow = $("#search-additional-charge-autocomplete").val();
	        if (ui.content.length === 0) {
        		 $("#search-additional-charge-autocomplete").addClass("input-error").removeClass("input-correct");
        		 $("#dny-additional-charge-no-records-found").show();
                 $("#search-additional-charge-autocomplete").val("");
                 $("#additionalChargeAmountToShow").val('');
     			 $("#additionalChargeAmount").val('');
	        } else {
	            $("#dny-additional-charge-no-records-found").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var chargeName = ui.item.value;
        
        dynamicallyAddedAdditionalChargeJson = fetchDynamicallyAddedAdditionalCharge(chargeName.trim());
		
		if(dynamicallyAddedAdditionalChargeJson){
			changeAdditionalChargeNameAsPerAutoComplete(dynamicallyAddedAdditionalChargeJson);
			$("#search-additional-charge-autocomplete").addClass("input-correct").removeClass("input-error");
    		$("#dny-additional-charge-no-records-found").hide();
    		$("#search-additional-charge-autocomplete-csv-id").hide();
		}
       
      
         return false;
    }
});

function fetchDynamicallyAddedAdditionalCharge(chargeName){
	var resp = '';
	  $.ajax({
          url:"getAdditionalChargeByChargeName",     
          type : "POST",
          headers: {_csrf_token : $("#_csrf_token").val()},
          data : {"chargeName":chargeName},
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

function changeAdditionalChargeNameAsPerAutoComplete(additionalChargeJson){
	$('#additionalChargeName').empty();
	$('#additionalChargeName').append('<option value="">Select Additional Charge</option>');	
	
	if(userAdditionalChargeJson == ''){
		userAdditionalChargeJson = getAdditionalChargesDetailsList();
	}
	$.each(userAdditionalChargeJson,function(i,value) {
		if(additionalChargeJson.id == value.id){
			$('#additionalChargeName').append($('<option>').text(value.chargeName).attr('value',value.id+"{--}"+value.chargeName+"{--}"+value.chargeValue).attr('selected','selected'));
			$("#additionalChargeAmountToShow").val(value.chargeValue);
			$("#additionalChargeAmount").val(value.chargeValue);
			$("#search-additional-charge-autocomplete").val(value.chargeName);
		}else{
			 $('#additionalChargeName').append($('<option>').text(value.chargeName).attr('value',value.id));
		}
		
	});
	
	
}

function clearAdditionalChargesFormFields(){
	$("#dny-additional-charge-chargeNameValue").val('');
	$("#dny-additional-charge-chargeNameValue").addClass("input-correct").removeClass("input-error");
	$("#dny-additional-charge-chargeValuedetail").val('');
	$("#dny-additional-charge-chargeValuedetail").addClass("input-correct").removeClass("input-error");
	dynamicallyAddedAdditionalChargeJson = '';
}



$(document).ready(function(){
	$("#dnyAddNewAdditionalCharge").click(function(e){
		
		showCustomerAddPageHeader();
		$("#addInvoiceDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		$("#dnyCustomerDiv").hide();
		$("#dnyServiceProductDiv").hide();
		$("#search-additional-charge-autocomplete").addClass("input-correct").removeClass("input-error");
		$("#dny-additional-charge-no-records-found").hide();
		
		$('#dnyAdditionalChargeDiv').scrollTop($(this).position().top);
		$("#dnyAdditionalChargeDiv").show();
		
	});
	
	$("#dnyAdditionalChargeCancelBtn,#dny-additional-charge-cancel-link").click(function(e){
		clearAdditionalChargesFormFields();
		$("#dnyCustomerDiv").hide();
		$("#previewInvoiceDiv").hide(); 
		$("#customerEmailDiv").hide();
		$("#dnyServiceProductDiv").hide();
		$("#dnyAdditionalChargeDiv").hide();
		$("#addInvoiceDiv").show();
		$('html, body').animate({
		      scrollTop: $("#callOnEditAdditionalChargeId").offset().top
		}, 1000);
		showDefaultPageHeader();
		//$("#generateInvoiceProductDetailsPageHeader").hide();
	});
	
	$("#dny-additional-charge-chargeNameValue").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		
		if ($("#dny-additional-charge-chargeNameValue").val().length > 1){
			 $("#dny-additional-charge-chargeNameValue").addClass("input-correct").removeClass("input-error");
			 $("#dny-additional-charge-charge-name-req").hide();			 
		 }		
		if ($("#dny-additional-charge-chargeNameValue").val().length < 1){
			 $("#dny-additional-charge-chargeNameValue").addClass("input-error").removeClass("input-correct");
			 $("#dny-additional-charge-charge-name-req").show();
		}
	});
		
	$("#dny-additional-charge-chargeValuedetail").on("keyup input",function(){ 	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	if(currencyRegex.test($("#dny-additional-charge-chargeValuedetail").val()) === true){
			$("#dny-additional-charge-charge-value-req").hide();
			$("#dny-additional-charge-chargeValuedetail").addClass("input-correct").removeClass("input-error");	
		}    	
		if(currencyRegex.test($("#dny-additional-charge-chargeValuedetail").val()) !== true){
			$("#dny-additional-charge-charge-value-req").text(regMsg);
			$("#dny-additional-charge-charge-value-req").show();
			$("#dny-additional-charge-chargeValuedetail").addClass("input-error").removeClass("input-correct");	
		}			
    });
	
	$("#dnyAdditionalChargeSubmitBtn").click(function(e){
		var errorStatus = true;
		var errChargeName = validateTextField("dny-additional-charge-chargeNameValue","dny-additional-charge-charge-name-req",blankMsg);
		var errChargeValue = validateTextField("dny-additional-charge-chargeValuedetail","dny-additional-charge-charge-value-req",blankMsg);
	   

		if((errChargeName) || (errChargeValue)){
			e.preventDefault();	
		}else{
			errorStatus = false;
			callPostAddAdditionalCharge();
			e.preventDefault(); 
		}
		
		if((errChargeName)){
			focusTextBox("dny-additional-charge-chargeNameValue");
		} else if((errChargeValue) ){
			focusTextBox("dny-additional-charge-chargeValuedetail");
		} 
	});
	
	$( "#add_chg_cancel" ).on( "click", function(e) {
		$("#search-additional-charge-autocomplete").val('');
		$("#additionalChargeAmountToShow").val('');
		$("#additionalChargeAmount").val('');
		$("#add_chg_add").show();//Hide the add button
		$("#add_chg_edit").hide();//display the edit button
	});

});

function generateAdditionalChargeInputJson(){
	var chargeName = $("#dny-additional-charge-chargeNameValue").val();
	var chargeValue= parseFloat($("#dny-additional-charge-chargeValuedetail").val()).toFixed(2);
	
	var inputData = {
		"chargeName": chargeName,
		"chargeValue": chargeValue
	};
	
	console.log("inputData : "+JSON.stringify(inputData));
	return inputData;
}

function callPostAddAdditionalCharge(){
	$("#dnyAdditionalChargeDiv").hide();
	$('.loader').show();
	var inputData = generateAdditionalChargeInputJson();
	$.ajax({
		url : "saveAdditionalChargeAjax",
		method : "post",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
		data : JSON.stringify(inputData),
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
			
			if(json.status == 'SUCCESS'){	
				 //set service data - start 
				dynamicallyAddedAdditionalChargeJson = fetchDynamicallyAddedAdditionalCharge($("#dny-additional-charge-chargeNameValue").val());
				userAdditionalChargeJson = getAdditionalChargesDetailsList();
				 
				 if(dynamicallyAddedAdditionalChargeJson){
					 changeAdditionalChargeNameAsPerAutoComplete(dynamicallyAddedAdditionalChargeJson);
					$("#search-additional-charge-autocomplete").addClass("input-correct").removeClass("input-error");
	        		$("#dny-additional-charge-no-records-found").hide();
				 }
				 
				 //set service data - end 
				 $('.loader').hide();
				bootbox.alert(json.message, function() {
					clearAdditionalChargesDymanicForm();
					$("#dnyCustomerDiv").hide();
					$("#previewInvoiceDiv").hide(); 
					$("#customerEmailDiv").hide();
					$("#dnyAdditionalChargeDiv").hide();
					$("#addInvoiceDiv").show();
					$('html, body').animate({
					      scrollTop: $("#callOnEditAdditionalChargeId").offset().top
					},0);
					showDefaultPageHeader();
				});
			}
			
			if(json.status == 'FAILURE'){
				$('.loader').hide();
				bootbox.alert(json.message, function() {
					$("#dnyAdditionalChargeDiv").show();
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
				bootbox.alert("Error occured while adding additional charges.", function() {
				    $("#dnyAdditionalChargeDiv").show();
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

function clearAdditionalChargesDymanicForm(){
	$("#dny-additional-charge-chargeNameValue").val('');
	$("#dny-additional-charge-chargeValuedetail").val('');
}
