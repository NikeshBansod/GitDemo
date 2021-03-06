var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var creditNoteMsg = 'Credit Note Value cannot be more than Invoice Value';
var blankMsg="This field is required";
  
$(document).ready(function () {
	var invoiceId = $("#refInvId").val();
	var defaultCnDnType = $('input[name=cnDnType]').filter(':checked').val(); 
	loadCnDnReasonList();
	fetchInvoiceDetails(invoiceId,defaultCnDnType);
	$('.loader').fadeOut("slow");
	
	$(".service_name_class").click(function () {
		
		var zid = $(this).attr("id");
		var index = zid.substring(zid.lastIndexOf("-")+1);
		//alert(zid + ","+index);
		 if ($(this).is(":checked")) {
			 enableDisableCnDnServiceFields(index,false);
		 }else{
			 enableDisableCnDnServiceFields(index,true);
		 }
		
	});
	
	$("#previewSubmit,#finalSubmitId").click(function(e){
		var idClicked = $(this).attr('id');
		
		var backendResponse = '';
		var errFlag1 = false;
		var errFlag2 = false;
		var errFlag3 = false;
		var errFlag4 = false;
		var cnDnType = $('input[name=cnDnType]').filter(':checked').val();
		
		//1)check if atleast one item is selected
		 var isAtLeastOneServiceSelected = checkIfServiceIsSelected();
		
		 if(!isAtLeastOneServiceSelected){
			 bootbox.alert("Please select atleast one service/product in order to proceed to Preview Invoice");
			 errFlag1 = true;
		 }
		 
		//2)check if cndn value of one service is less than the invoice value - total of all cndn's - check at client side.
		 if(isAtLeastOneServiceSelected){
			 errFlag2 = checkForTotalCnDnValueAtClientSide();
		 }
		 
		 if(cnDnType === 'creditNote'){
			
			//3)check if cndn value of one service is less than the invoice value - total of all cndn's - check at server side.
			 if(!errFlag2){
				 errFlag3 = checkForTotalCnDnValueAtServerSide(); 
			 }
		 }
		
		 
		//4) Validate Select a reason field
		errFlag4 = validateSelect("selectReason","selectReason-csv-id"); 
		
		 
		if((errFlag1) || (errFlag2) || (errFlag3) || (errFlag4)){
			e.preventDefault();	 
		}else{
			
			$('.loader').show();
			if(idClicked == 'previewSubmit'){
				$("#addCndnHeader").css("display","none"); 
				$("#addCnDnDiv").hide();
				var inputData = getInputFormCnDnDataJson();
				backendResponse = calculateTaxForCnDn(inputData);
				 setTimeout(function(){
						//alert(backendResponse.invoiceDetails.invoiceFor);
						//console.log(backendResponse);
					 
						showServiceListDetailsInPreviewInvoiceDiv(backendResponse);
						
						 $("#previewCnDnInvoiceDiv").show(); 
						 dynamicChangesInPreviewLabels();
						
						 
						 $("#previewHeader").show();
						 //$("#previewHeaderLabel").html("");
						 if(cnDnType === 'creditNote'){
							 //$("#previewHeaderLabel").html("Preview Credit Note");
						 }else{
							 //$("#previewHeaderLabel").html("Preview Debit Note");
						 }
				    
					 $('.loader').hide(); // hide the loading message
				 }, 1000);
			}
			
			if(idClicked == 'finalSubmitId'){
				$("#previewHeader").css("display","none");
				$("#previewCnDnInvoiceDiv").hide();
				var inputData = getInputFormCnDnDataJson();
				
				setTimeout(function(){
					submitFormAjax(inputData,cnDnType);
					$('.loader').hide(); // hide the loading message
				}, 1000);
			}	
			
		}
		
		if(errFlag4){
			$("#selectReason").focus();
		}
	
		
	});
	
	$(".quantity,.taxableValue,.taxRate,.valueAfterTax,.cess").keyup(function() {
	    var $this = $(this);
	    $this.val($this.val().replace(/[^\d.]/g, ''));        
	});
	
	$('.taxRate,.taxableValue,.cess').change(function(){
		var zid = $(this).attr('id');
		var index = zid.substring(zid.lastIndexOf("-")+1);
		   
	    var taxableValue = parseFloat($("#taxableValuePost-"+index).val()).toFixed(2);
	    var taxRate = parseFloat($("#taxRatePost-"+index).val()).toFixed(2);
	    var diffPercent = $("#diffPercent-"+index).val();
	    var cess = $("#cessPost-"+index).val();
	    if(!cess){
	    	cess = 0;
	    }else{
	    	cess = parseFloat(cess).toFixed(2);
	    }
	    var valueAfterTax = 0;
	   
	    if(jQuery.type(taxableValue) === "undefined" || taxableValue =='' || taxableValue =='NaN'){
		   //bootbox.alert("Input field Taxable Value is empty");
		   //$("#taxableValuePost-"+index).focus();
			validateTextField("taxableValuePost-"+index,"taxableValueSpan-"+index,blankMsg);
		 }else if(jQuery.type(taxRate) === "undefined" || taxRate ==''){
			validateTextField("taxRatePost-"+index,"taxRateSpan-"+index,blankMsg);
		 }else{
			 if(diffPercent == 'Y'){
				 valueAfterTax = (parseFloat(taxableValue) + (parseFloat(((taxableValue * taxRate) / 100) * 0.65) + (parseFloat(cess)))).toFixed(2);
			 }else{
				 valueAfterTax = (parseFloat(taxableValue) + (parseFloat((taxableValue * taxRate) / 100) + (parseFloat(cess)))).toFixed(2); 
			 }
			
			//alert("quantity : "+quantity+",taxRate : "+taxRate+",taxableValue : "+taxableValue+",valueAfterTax : "+valueAfterTax);
			$("#taxableValuePost-"+index).val(taxableValue);
			$("#valueAfterTaxPost-"+index).val(valueAfterTax);
			$("#valueAfterTaxShowPost-"+index).val(valueAfterTax);
		 }
			     
			   
		
	     
	});
	
   $('.quantity,.taxRate,.taxableValue').on("keyup change click", function(){
		var zid = $(this).attr('id');
		var index = zid.substring(zid.lastIndexOf("-")+1);
		//alert(zid);
		
		if(($(this).val() > 0) && ($(this).val().length > 0)){
			if(zid.startsWith('quantity')){
				$("#quantitySpan-"+index).css("display","none");
				validateTextField("quantityPost-"+index,"quantitySpan-"+i,blankMsg);
			}
			
			if(zid.startsWith('taxRate')){
				$("#taxRateSpan-"+index).css("display","none");
				validateTextField("taxRatePost-"+index,"taxRateSpan-"+i,blankMsg);
				
			}
			
			if(zid.startsWith('taxableValue')){
				$("#taxableValueSpan-"+index).css("display","none");
				validateTextField("taxableValuePost-"+index,"taxableValueSpan-"+i,blankMsg);
			}
			
		}
		
		
   });
   
   $('.cess').on("keyup change click", function(){
	   var zid = $(this).attr('id');
	   var index = zid.substring(zid.lastIndexOf("-")+1);
	   if($(this).val() >= 0){
		   if(zid.startsWith('cess')){
				$("#cessSpan-"+index).css("display","none");
				validateTextField("cessPost-"+index,"cessSpan-"+i,blankMsg);
			}
	   }
   });
	
	
	$("#selectReason").on("keyup click change", function(){
		if ($("#selectReason").val() === ""){
			$("#selectReason").addClass("input-error").removeClass("input-correct");
			$("#selectReason-csv-id").show();
		}
		if ($("#selectReason").val() !== ""){
			$("#selectReason").addClass("input-correct").removeClass("input-error");
			$("#selectReason-csv-id").hide();
		}
	});
	
	$("#backToAddCnDnDivDiv").click(function(e){
		$('.loader').show();
		$("#previewCnDnInvoiceDiv").hide(); 
		
		setTimeout(function(){
			
			$("#addCnDnDiv").show();
			$("#addCndnHeader").show(); 
			$("#previewHeader").css("display","none");
			$('.loader').hide(); // hide the loading message
		}, 2000);
		
	});
	
   $('input[type=radio][name=cnDnType]').change(function() {
	   $('.cndnTypeText').html('');
	   $("#previewBreadcrum").html("");
	   if (this.value == 'creditNote') {
		  cndnTypeText = "CN Details";
		  $('.cndnTypeText').html("Credit <br/> Note <br/> Details");
		  $("#previewSubmit").text("Preview Credit Note");
		  $("#previewBreadcrum").text("Preview Credit Note");
	   } else if (this.value == 'debitNote') {
		  cndnTypeText = "DN Details";
		   $('.cndnTypeText').html("Debit <br/> Note <br/> Details");
		  $("#previewSubmit").text("Preview Debit Note");
		  $("#previewBreadcrum").text("Preview Debit Note");
	   }
	   
	   var $dnynamicGoodServices = $("#dnynamicGoodServices");
	   var totalRecordNo = $dnynamicGoodServices.children().length;
	   for(i = 1; i <= totalRecordNo; i++){
			//$("#cndnDetailsText"+i).text(cndnTypeText);
			if(this.value == 'creditNote'){
				$("#cndnValuePreviouslyAddedDisplayId-"+i).css("display","block");
				$("#dnValuePreviouslyAddedDisplayId-"+i).css("display","none");
			}else{
				$("#cndnValuePreviouslyAddedDisplayId-"+i).css("display","none");
				$("#dnValuePreviouslyAddedDisplayId-"+i).css("display","block");
				$("#errorId-"+i).css("display","none");
				$("#taxableValuePost-" + i).addClass("").removeClass("input-error");
			}
			
	   }
    });
	
});

function dynamicChangesInPreviewLabels(){
	var cnDnType = $('input[name=cnDnType]').filter(':checked').val();
	$('h3[id*=place1]').text('');
	$('li[id*=place2]').text('');
	$('li[id*=place3]').text('');
	$('li[id*=place4]').text('');
	$('li[id*=place5]').text('');
	if(cnDnType === 'creditNote'){
		$('h3[id*=place1]').text('CREDIT NOTE');
		$('li[id*=place2]').text('Total Credit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Credit Note Value (A+B)');
		$('li[id*=place4]').text('Total Credit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Credit Note value Rs.(in words): ');
		$("#previewSubmit").text("Preview Credit Note");
		$("#finalSubmitId").text("Send Credit Note");
	}else{
		$('h3[id*=place1]').text('DEBIT NOTE');
		$('li[id*=place2]').text('Total Debit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Debit Note Value (A+B)');
		$('li[id*=place4]').text('Total Debit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Debit Note value Rs.(in words): ');
		$("#previewSubmit").text("Preview Debit Note");
		$("#finalSubmitId").text("Send Debit Note");
	}
}

function fetchInvoiceDetails(invoiceId,defaultCnDnType){
	  $("#dnynamicGoodServices").html("");
	  var recordNo = 0;
	  var valueAfterTax = 0;
	  var taxRate = 0; 
	  var quantity = 0;
	  var taxableValue = 0;
	  var cndnValuePreviouslyAdded = 0;
	  var cndnDetailsText = '';
	  if(defaultCnDnType == 'creditNote'){
		  cndnDetailsText = "Credit Note Details";
		  $("#previewSubmit").text("Preview Credit Note");
	  }else{
		  cndnDetailsText = "Debit Note Details";
		  $("#previewSubmit").text("Preview Debit Note");
	  }
	  
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
				
				var cndnArray = {};
				var dnArray = {};
				$.each(json.cnDnList,function(i,value) {
					if(value.cnDnType == 'creditNote'){
						 if(cndnArray[value.serviceId]){
							 cndnArray[value.serviceId] = parseFloat(cndnArray[value.serviceId]) + parseFloat(value.valueAfterTax);
						 }else{
							 cndnArray[value.serviceId] = parseFloat(value.valueAfterTax);
						 }
					}else{
						if(dnArray[value.serviceId]){
							 dnArray[value.serviceId] = parseFloat(dnArray[value.serviceId]) + parseFloat(value.valueAfterTax);
						 }else{
							 dnArray[value.serviceId] = parseFloat(value.valueAfterTax);
						 }
					}
					
				});
				
				$.each(json.serviceList,function(i,value) {
					recordNo++;
					cndnValuePreviouslyAdded = 0;
					dnValuePreviouslyAdded = 0;
					quantity = value.quantity;
					taxableValue = (value.amount).toFixed(2);
					taxRate = (value.sgstPercentage + value.ugstPercentage + value.cgstPercentage + value.igstPercentage);
					cess = (value.cess).toFixed(2);
					var containsDiffPerctage = '';
					if(value.diffPercent == 'Y'){
						valueAfterTax = (parseFloat(taxableValue) + parseFloat(((taxableValue * taxRate) / 100) * 0.65)+parseFloat(cess)).toFixed(2);
						containsDiffPerctage = '<span style="color:red">*</span>';
					}else{
						valueAfterTax = (parseFloat(taxableValue) + parseFloat((taxableValue * taxRate) / 100)+parseFloat(cess)).toFixed(2);
					}
					
					
					
					$.each(cndnArray, function(k, v) {
						if(k == value.id){
								cndnValuePreviouslyAdded = v.toFixed(2);
						}
						
					});
					
					$.each(dnArray, function(k, v) {
						if(k == value.id){
								dnValuePreviouslyAdded = v.toFixed(2);
						}
						
					});
					
					$("#dnynamicGoodServices").append(
						'<div id="service_start_'+recordNo+'">'	
						   +'<div class="form-group checkbox-inline" style="margin-top:10px">'
	                            +'<span></span>'
	                            +'<div class="checkbox checkbox-success">'
	                                +'<input type="checkbox" class="service_name_class" id="service_name-'+recordNo+'">'
	                                
	                                +'<label for="service_name-'+recordNo+'">'+value.serviceIdInString +containsDiffPerctage+'</label>'
	                                
	                            +'</div>'
	                      +'</div>'
	                   	  +'<div class="table-responsive">'
								+'<table class="table table-bordered cndnTable">'
									+'<thead>'
                            			+'<tr style="color:white;background-color:#364EB5">'
                            				+'<th>Original Document fields</th>'
                            				+'<th>Original Document details</th>'
                            				+'<th class="cndnTypeText" id="cndnDetailsText'+recordNo+'">'+cndnDetailsText+'</th>'
                            			+'</tr>'
                            		+'</thead>'
									+'<tbody>'
                            			+'<tr>'
	                            			+'<td>Quantity</td>'
	                            			+'<td>'+quantity+'</td>'
	                            			+'<td>'
	                            				/*+'<input class="quantity width-75" disabled ="disabled" id="quantityPost-'+recordNo+'" type="text">'
	                            				+'<span id="quantitySpan-'+recordNo+'" style="display:none">Quantity is mandatory</span>'*/
	                            				+'<div class="input-field" style="" id="" >'
		                            				+'<label class="label">'
		                            					+'<input type="text" disabled ="disabled" id="quantityPost-'+recordNo+'" maxlength="15" required class="form-control quantity">'
		                            				+'</label>'
		                            				+'<span class="text-danger cust-error" style="display:none" id="quantitySpan-'+recordNo+'">This field is required.</span>'
		                            			+'</div>'
	                            			+'</td>'
                            			+'</tr>'
                            			
                            			+'<tr>'
	                            			+'<td>Taxable <br/>value</td>'
	                            			+'<td>'+taxableValue+'</td>'
	                            			+'<td>'
	                            				/*+'<input class="taxableValue width-75" disabled ="disabled" id="taxableValuePost-'+recordNo+'" type="text">'
	                            				+'<span id="taxableValueSpan-'+recordNo+'" style="display:none">Taxable Value is mandatory</span>'*/
		                            			+'<div class="input-field" style="" id="" >'
		                            				+'<label class="label">'
		                            					+'<input type="text" disabled ="disabled" id="taxableValuePost-'+recordNo+'" maxlength="15" required class="form-control taxableValue">'
		                            				+'</label>'
		                            				+'<span class="text-danger cust-error" style="display:none" id="taxableValueSpan-'+recordNo+'">This field is required.</span>'
		                            			+'</div>'
	                            			+'</td>'
                            			+'</tr>'
                            			
                            			+'<tr>'
	                            			+'<td>Tax rate<br/> (%)</td>'
	                            			+'<td>'+taxRate+'</td>'
	                            			+'<td>'
	                            				/*+'<input class="taxRate width-75" disabled ="disabled" value="'+taxRate+'" id="taxRatePost-'+recordNo+'" type="text">'
	                            				+'<span id="taxRateSpan-'+recordNo+'" style="display:none">Tax Rate is mandatory</span>'*/
		                            			+'<div class="input-field" style="" id="" >'
		                            				+'<label class="label">'
		                            					/*+'<input type="text" disabled ="disabled" value="'+taxRate+'" id="taxRatePost-'+recordNo+'" maxlength="15" required class="form-control taxRate">'*/
		                            					+'<select style="height:40px !important " disabled ="disabled" id="taxRatePost-'+recordNo+'" required class="form-control taxRate">'
		                            						
		                            					+'</select>'
		                            				+'</label>'
		                            				+'<span class="text-danger cust-error" style="display:none" id="taxRateSpan-'+recordNo+'">This field is required.</span>'
		                            			+'</div>'
	                            			+'</td>'
                            			+'</tr>'
                            			
                            			+'<tr>'
	                            			+'<td>Cess</td>'
	                            			+'<td>'+cess+'</td>'
	                            			+'<td>'
	                            				/*+'<input class="taxRate width-75" disabled ="disabled" value="'+taxRate+'" id="taxRatePost-'+recordNo+'" type="text">'
	                            				+'<span id="taxRateSpan-'+recordNo+'" style="display:none">Tax Rate is mandatory</span>'*/
		                            			+'<div class="input-field" style="" id="" >'
		                            				+'<label class="label">'
		                            					/*+'<input type="text" disabled ="disabled" value="'+taxRate+'" id="taxRatePost-'+recordNo+'" maxlength="15" required class="form-control taxRate">'*/
		                            				
		                            					+'<input type="text" disabled ="disabled" id="cessPost-'+recordNo+'" maxlength="15" required class="form-control cess">'
		                            				+'</label>'
		                            				/*+'<span class="text-danger cust-error" style="display:none" id="cessSpan-'+recordNo+'">This field is required.</span>'*/
		                            			+'</div>'
	                            			+'</td>'
	                        			+'</tr>'
                        			
                            			+'<tr>'
	                            			+'<td>Value <br/>after tax</td>'
	                            			+'<td>'+valueAfterTax+'</td>'
	                            			+'<td>'
	                            				/*+'<input class="valueAfterTax width-75" disabled ="disabled" id="valueAfterTaxShowPost-'+recordNo+'" type="text">'*/
	                            				+'<input class="valueAfterTax" id="valueAfterTaxPost-'+recordNo+'" type="hidden">'
	                            				+'<div class="input-field" style="" id="" >'
		                            				+'<label class="label">'
		                            					+'<input type="text" disabled ="disabled" id="valueAfterTaxShowPost-'+recordNo+'" maxlength="15" required class="form-control valueAfterTax">'
		                            				+'</label>'
		                            			+'</div>'
	                            			+'</td>'
                            			+'</tr>'	                            			
                            		+'</tbody>'

                            	+'</table>'
                        	+'</div>'
                        	
                        	+'<div class="table-responsive">'
                        		+'<table class="table table-bordered cndnSummary">'
                        			+'<tbody>'
                        				+'<tr id="cndnValuePreviouslyAddedDisplayId-'+recordNo+'">'
                            				+'<td style="color:white;background-color:#364EB5">CN value previously created</td>'
                            				+'<td>'+cndnValuePreviouslyAdded+'</td>'
                        				+'</tr>'
                        				+'<tr id="dnValuePreviouslyAddedDisplayId-'+recordNo+'" style="display:none">'
                        					+'<td style="color:white;background-color:#364EB5">DN value previously created</td>'
                        					+'<td>'+dnValuePreviouslyAdded+'</td>'
                        				+'</tr>'
                        			+'</tbody>'
                        		+'</table>'
                        	+'</div>'
                        	+'<input type="hidden" id="cndnValuePreviouslyAddedId-'+recordNo+'" value="'+cndnValuePreviouslyAdded+'">'
                        	+'<input type="hidden" id="quantityId-'+recordNo+'" value="'+quantity+'">'
                        	+'<input type="hidden" id="taxableValueId-'+recordNo+'" value="'+taxableValue+'">'
                        	+'<input type="hidden" id="taxRateId-'+recordNo+'" value="'+taxRate+'">'
                        	+'<input type="hidden" id="valueAfterTaxId-'+recordNo+'" value="'+valueAfterTax+'">'
                        	+'<input type="hidden" id="serviceId-'+recordNo+'" value="'+value.id+'">'
                        	+'<input type="hidden" id="calculationBasedOn-'+recordNo+'" value="'+value.calculationBasedOn+'">'
                        	+'<input type="hidden" id="rate-'+recordNo+'" value="'+value.rate+'">'
                        	+'<input type="hidden" id="cess-'+recordNo+'" value="'+cess+'">'
                        	+'<input type="hidden" id="diffPercent-'+recordNo+'" value="'+value.diffPercent+'">'
                        	+'<div id="errorId-'+recordNo+'" style="display:none;color:red;">Error! &#39;Value after Tax&#39; in Credit Note details should be less than or equal to (Value after Tax in Original Document details) minus (Credit notes value previously created)</div>'
                        	+'<hr>'
                        +'</div>'
					);
					
					if(value.billingFor == 'Product'){
						setProductTaxRate(recordNo,taxRate);
					}else{
						setServiceTaxRate(recordNo,taxRate);
					}
					
				});
	        },
	        error:function(data,status,er) { 
                  //alert("error: "+data+" status: "+status+" er:"+er);
	        	
	        	//callCustomErrorPage(data.status,data.responseText,er,"getInvoiceDetailsForCnDn");
	        	alert("1");
	        	getInternalServerErrorPage();  
            }
		});
}


function enableDisableCnDnServiceFields(index,enableDisable){
	$("#quantityPost-"+index).prop("disabled", enableDisable);
	$("#taxableValuePost-"+index).prop("disabled", enableDisable);
	$("#taxRatePost-"+index).prop("disabled", enableDisable);
	$("#cessPost-"+index).prop("disabled", enableDisable);
	
	/*$("#valueAfterTaxPost-"+index).prop("disabled", enableDisable);*/
	
	if(enableDisable){
		$("#quantityPost-"+index).val("");
		$("#taxableValuePost-"+index).val("");
		/*$("#taxRatePost-"+index).val("");*/
		$("#cessPost-"+index).val("");
		$("#valueAfterTaxPost-"+index).val("");
		$("#valueAfterTaxShowPost-"+index).val("");
		
		removeErrorMsgFromInputFields(index);
		
	}
	
	if(!enableDisable){
		var $dnynamicGoodServices = $("#dnynamicGoodServices");
		var totalRecordNo = $dnynamicGoodServices.children().length;
		for(i = 1; i <= totalRecordNo; i++){
			if(index == i){
				$("#taxRatePost-"+index).val($("#taxRateId-"+index).val());
			}
			
		}
	}
}

function checkIfServiceIsSelected(){
	var isSelected = false;
	
	var $dnynamicGoodServices = $("#dnynamicGoodServices");
	var totalRecordNo = $dnynamicGoodServices.children().length;
	for(i = 1; i <= totalRecordNo; i++){
		if($("#service_name-"+i).is(':checked')){
			isSelected = true;
			break;
		}
		
	}
	
	return isSelected;
}

function checkForTotalCnDnValueAtClientSide(){
	var isError = false;
	var finalInvoiceValue = parseFloat($("#fInvValz").val());
	var totalPreviousCnDnValue = 0;
	var cnDnType = $('input[name=cnDnType]').filter(':checked').val();
	var $dnynamicGoodServices = $("#dnynamicGoodServices");
	var totalRecordNo = $dnynamicGoodServices.children().length;
	for(i = 1; i <= totalRecordNo; i++){
		var cndnValuePreviouslyAdded = 0;
		var quantityId = 0;
		var taxableValueId = 0;
		var taxRateId = 0;
		var valueAfterTaxId = 0;
		var valueAfterTaxPost = 0;
		var cndnValuePreviouslyAddedId = 0;
		totalPreviousCnDnValue = (parseFloat(totalPreviousCnDnValue) + parseFloat($("#cndnValuePreviouslyAddedId-"+i).val())).toFixed(2);
		
		if($("#service_name-"+i).is(':checked')){
			quantityId = $("#quantityPost-"+i).val();
			taxableValueId = $("#taxableValuePost-"+i).val();
			taxRateId = $("#taxRatePost-"+i).val();
			valueAfterTaxId = $("#valueAfterTaxPost-"+i).val();
			valueAfterTaxPrev = $("#valueAfterTaxId-"+i).val();
			cndnValuePreviouslyAddedId = $("#cndnValuePreviouslyAddedId-"+i).val();
			totalPreviousCnDnValue = (parseFloat(totalPreviousCnDnValue) + parseFloat(valueAfterTaxId)).toFixed(2);
			
			//1.check if quantity field is empty
			var errorFlag1 = false;
			if(jQuery.type(quantityId) === "undefined" || quantityId ==''){
				errorFlag1 = true;
				//$("#quantitySpan-"+i).css("display","block");
				validateTextField("quantityPost-"+i,"quantitySpan-"+i,blankMsg);
			}
			
			//2.check if taxable value is empty
			var errorFlag2 = false;
			if(jQuery.type(taxableValueId) === "undefined" || taxableValueId == ''){
				errorFlag2 = true;
				//$("#taxableValueSpan-"+i).css("display","block");
				validateTextField("taxableValuePost-"+i,"taxableValueSpan-"+i,blankMsg);
			}
			
			//3.check if taxRate is empty
			var errorFlag3 = false;
			if( jQuery.type(taxRateId) === "undefined" || taxRateId == ''){
				errorFlag3 = true;
				//$("#taxRateSpan-"+i).css("display","block");
				validateTextField("taxRatePost-"+i,"taxRateSpan-"+i,blankMsg);
			}
			
			//4.check if valueAfterTax is empty
			var errorFlag4 = false;
			if( jQuery.type(valueAfterTaxId) === "undefined" || valueAfterTaxId == ''){
				errorFlag4 = true;
			}
			
			//5. check if cndn value is not greater than invoice value
			var errorFlag5 = false;
			if(cnDnType === 'creditNote'){
				if((parseFloat(cndnValuePreviouslyAddedId)+(parseFloat(valueAfterTaxId))) > (parseFloat(valueAfterTaxPrev))){
					errorFlag5 = true;
					$("#errorId-"+i).css("display", "block");
					$("#taxableValuePost-" + i).addClass("input-error").removeClass("input-correct");
					$("#cessPost-" + i).addClass("input-error").removeClass("input-correct");
				}else{
					$("#errorId-"+i).css("display", "none");
				}
			}
			
			
			if(errorFlag1){
				
				$("#quantityPost-"+i).focus();
				isError = true;
			}else if (errorFlag2){
				
				$("#taxableValuePost-"+i).focus();
				isError = true;
			}else if (errorFlag3){
				
				$("#taxRatePost-"+i).focus();
				isError = true;
			}else if(errorFlag4 || errorFlag5){
				$("#valueAfterTaxPost-"+i).focus();
				isError = true;
			}
			
			
		}
		
	}
	
	if(cnDnType === 'creditNote'){
		if(totalPreviousCnDnValue > finalInvoiceValue){
			bootbox.alert("Total of all CN document value exceeds the original document value. Hence cannot be created.");
			isError = true;
		}
	}
	
	
	return isError;
}

function validateSelect(id,spanid){
	if ($("#"+id).val() === ""){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+spanid).show();
		$("#"+id).focus();
		return true;
	}else{
		$("#"+id).addClass("input-correct").removeClass("input-error");
		$("#"+spanid).hide();
		return false;
	}

}

function getInputFormCnDnDataJson(){
	
	var invoiceId = $("#refInvId").val();
	var cnDnType = $('input[name=cnDnType]:checked').val();
	
	//get services from list in javascript - Start 
	   var $dnynamicGoodServices = $("#dnynamicGoodServices");
	   var totalRecordNo = $dnynamicGoodServices.children().length;
	   var jsonObject;
	   var serviceListArray = new Array();
	   for (i = 1; i <= totalRecordNo; i++) { 
		   if($("#service_name-"+i).is(':checked')){
			 //Start
			   var cess = $("#cessPost-"+i).val();
			   if(!cess){
				   cess = 0;
			   }
			   jsonObject = new Object();
			   jsonObject.serviceId = $("#serviceId-"+i).val();
			   jsonObject.quantity = $("#quantityPost-"+i).val();
			   jsonObject.taxableValue = $("#taxableValuePost-"+i).val();
			   jsonObject.cess = cess;
			   jsonObject.rate = $("#taxRatePost-"+i).val();
			   jsonObject.valueAfterTax = $("#valueAfterTaxPost-"+i).val();
			   jsonObject.reason = $('select#selectReason option:selected').val();
			   jsonObject.cnDnType = $('input[name=cnDnType]:checked').val();
	//		   jsonObject.regime = $('input[name=regime]:checked').val();
			   jsonObject.regime = $("#regime").val();
			   jsonObject.diffPercent = $("#diffPercent-"+i).val();
			   serviceListArray.push(jsonObject);
			 //End
		   }	   
		}
	  	
	   //get services from list in javascript - End
	   
	   //get footer note 
	   var footerNote = $("#footerNote").val();
	
	
	 var inputData = {
			 	"id" : invoiceId,
			 	"cnDnList" : JSON.parse(JSON.stringify(serviceListArray)),
			 	"cnDnType" : cnDnType,
			 	"footerNote" : footerNote
        };
	//console.log(inputData);
	
	return inputData;
}

function submitFormAjax(inputData,cnDnType){
	var cnDnTypeText = '';
	 $.ajax({
			url : "addInvoiceCnDn",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
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
				
				if(json.response == 'SUCCESS'){
					if(cnDnType === 'creditNote'){
						cnDnTypeText = "Credit Note ";
					}else{
						cnDnTypeText = "Debit Note ";
					}
					bootbox.alert(cnDnTypeText+" "+json.InvoiceNumber+" generated successfully.", function() {
						setTimeout(function(){ 
							backToInvoicePage($("#refInvId").val());
							//window.location.href = getHomePage();
							//return;
						}, 1000);
					});
				}
				
				if(json.response == 'accessDeny'){
					bootbox.alert("Data is been manipulated.", function() {
							setTimeout(function(){ 
								window.location.href = getCustomLogoutPage();
								return;
							}, 1000);
					});
				}
				if(json.response == 'FAILURE'){
					bootbox.alert("Failed to generate"+cnDnTypeText+".", function() {
							setTimeout(function(){ 
								window.location.href = getHomePage();
								return;
							}, 1000);
					});
				}
	         },
        error:function(data,status,er) { 
                //alert("error: "+data+" status: "+status+" er:"+er);
        	//callCustomErrorPage(data.status,data.responseText,er,"addInvoiceCnDn");
        	alert("2");
        	 getInternalServerErrorPage();   
         }
	});
}

function checkForTotalCnDnValueAtServerSide(){
	var isError = false;
	var inputData = getInputFormCnDnDataJson();
	isError = verifyCnDnValueAtServerSide(inputData);
	return isError;
}

function verifyCnDnValueAtServerSide(inputData){
	var isError = false;
	 $.ajax({
			url : "verifyInvoiceCnDn",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
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
				
				if(json.response == 'SUCCESS'){
					if(json.status == 'FAILURE'){
						isError = true;
						bootbox.alert(json.message);
						//console.log("Server side status false");
					}else{
						//console.log("Server side status true");
					}
				}
	         },
	         error:function(data,status,er) { 
              // alert("error1: "+data.status+data.responseText+" status: "+status+" er:"+er);
	        	 console.log("Came Here 2");
	        	 //callCustomErrorPage(data.status,data.responseText,er,"verifyInvoiceCnDn");
	        	 alert("3");
	        	 getInternalServerErrorPage();
	        	 
	         }
		});
	 return isError;
}

function calculateTaxForCnDn(inputData){
	var backendResponse = '';
	 $.ajax({
			url : "calculateTaxOnCnDnInvoicePreview",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
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
				
				//console.log(json);
				if(json.renderData === 'accessDeny'){
					bootbox.alert("Data is manipulated.", function() {
						setTimeout(function(){
							window.location.href = callInvoiceHistoryPage();
						}, 1000);
					});
				}else{
					backendResponse = json;
				}
	         },
	         error:function(data,status,er) { 
	             //alert("error2: "+data.status+data.responseText+" status: "+status+" er:"+er);
	        	
	        	 //callCustomErrorPage(data.status,data.responseText,er,"calculateTaxOnCnDnInvoicePreview");
	        	 alert("4");
	        	 getInternalServerErrorPage(); 
	         }
		});
	 return backendResponse;
}

function showServiceListDetailsInPreviewInvoiceDiv(json){
	var cnDnType = ($('input[name=cnDnType]').filter(':checked').val() === 'creditNote') ? ('Credit Note') : ('Debit Note');
	
	$("#mytable2Header").html("");
	$("#mytable2Header").text(cnDnType.toUpperCase());
	
	var isDiffPercentPresent = 0;
	$("#mytable2").html("");
    $("#mytable2").append(
		'<thead>'
		   +'<tr>'
			   +'<th>Description</th>'
	           +'<th>SAC/HSN</th>'
	           +'<th>Quantity</th>'
	           +'<th>UOM</th>'
	           +'<th class="text-right">Price/UOM(Rs.)</th>'
	           +'<th class="text-right">Total (Rs.)</th>'
	       +' </tr>'
	    +'</thead>'	 
	 );

   var amountSubtotal = 0;
   var cessTotalTax = 0;
   var containsAdditionalCharges = '';
   var containsDiffPercentage = '';
   
   $("#mytable2").append('<tbody>');
   $.each(json.invoiceDetails.cnDnList,function(i,value) {
	   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
	   $("#mytable2 tbody:last-child").append(
		  '<tr>'
           +'<td>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></td>'
           +'<td>'+value.hsnSacCode+'</td>'
           +'<td>'+value.quantity+'</td>'
           +'<td>'+value.unitOfMeasurement+'</td>'
           +'<td class="text-right">'+value.rate+'</td>'
           /*+'<td class="text-right">'+value.offerAmount+'</td>'*/
           +'<td class="text-right">'+(value.previousAmount - value.offerAmount).toFixed(2)+'</li>'
           +'</tr>'		 
		 );
		 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
		 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
	});
   
   $("#mytable2 tbody:last-child").append(
       '<tr>'                 
      	    +'<td>&nbsp;</td>'    	
        	+'<td class="hlmobil"><b>Taxable Value</b></td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td class="text-right">'+amountSubtotal.toFixed(2)+'</td>'
       +'</tr>'
    );
		  
	if (json.invoiceDetails.addChargesList && json.invoiceDetails.addChargesList.length) {
			
	  var addChgLength = json.invoiceDetails.addChargesList.length;	 
	  var addChargeAmount = 0;
	  if(addChgLength > 0){
		  containsAdditionalCharges = 'YES';
		  $("#mytable2 tbody:last-child").append(
	               '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td class="hlmobil"><b>Add : Additional Charges</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	               +'</tr>'
		  );
		  $.each(json.invoiceDetails.addChargesList,function(i,value) {
			  $("#mytable2 tbody:last-child").append(
	                    '<tr>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td class="hlmobil">'+value.additionalChargeName+'</td>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>'+(value.additionalChargeAmount).toFixed(2)+'</td>'
	                    +'</tr>'
			  ); 
			  addChargeAmount = parseFloat(addChargeAmount) + parseFloat(value.additionalChargeAmount);
		  });
		  
		  $("#mytable2 tbody:last-child").append(
	               '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td class="hlmobil"><b>Total Additional Charges (B)</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>'+addChargeAmount.toFixed(2)+'</td>'
	               +'</tr>'
		  );
		  
		  $("#mytable2 tbody:last-child").append(
	               '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td class="hlmobil"><b>Total Value (A+B)</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</td>'
	               +'</tr>'
		  );
	  }
    }
	  
	  //Types of taxes - Start
	  
	  var cgstArray = {};
	  var sgstArray = {};
	  var ugstArray = {};
	  var igstArray = {};
	  var cgstDiffPercentArray = {};
	  var sgstDiffPercentArray = {};
	  var ugstDiffPercentArray = {};
	  var igstDiffPercentArray = {};
	  $.each(json.invoiceDetails.cnDnList,function(i,value) {
			 if((value.categoryType == 'CATEGORY_WITH_SGST_CSGT')|| (value.categoryType == 'CATEGORY_WITH_UGST_CGST')){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(cgstDiffPercentArray[value.cgstPercentage]){
						 cgstDiffPercentArray[value.cgstPercentage] = parseFloat(cgstArray[value.cgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 cgstDiffPercentArray[value.cgstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }else{
					 if(cgstArray[value.cgstPercentage]){
						 cgstArray[value.cgstPercentage] = parseFloat(cgstArray[value.cgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 cgstArray[value.cgstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }
			 }
			 
			 if(value.categoryType == 'CATEGORY_WITH_SGST_CSGT'){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(sgstDiffPercentArray[value.sgstPercentage]){
						 sgstDiffPercentArray[value.sgstPercentage] = parseFloat(sgstArray[value.sgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 sgstDiffPercentArray[value.sgstPercentage] = parseFloat(value.amountAfterDiscount);
					 } 
				 }else{
					 if(sgstArray[value.sgstPercentage]){
						 sgstArray[value.sgstPercentage] = parseFloat(sgstArray[value.sgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 sgstArray[value.sgstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }
			 }
			 
			 if((value.categoryType == 'CATEGORY_WITH_IGST') || (value.categoryType == 'CATEGORY_EXPORT_WITH_IGST')){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(igstDiffPercentArray[value.igstPercentage]){
						 igstDiffPercentArray[value.igstPercentage] = parseFloat(igstArray[value.igstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 igstDiffPercentArray[value.igstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }else{
					 if(igstArray[value.igstPercentage]){
						 igstArray[value.igstPercentage] = parseFloat(igstArray[value.igstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 igstArray[value.igstPercentage] = parseFloat(value.amountAfterDiscount);
					 } 
				 }
			 }
			 
			 if(value.categoryType == 'CATEGORY_WITH_UGST_CGST'){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(ugstDiffPercentArray[value.ugstPercentage]){
						 ugstDiffPercentArray[value.ugstPercentage] = parseFloat(ugstArray[value.ugstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 ugstDiffPercentArray[value.ugstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }else{
					 if(ugstArray[value.ugstPercentage]){
						 ugstArray[value.ugstPercentage] = parseFloat(ugstArray[value.ugstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 ugstArray[value.ugstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }
			 }
			
		});
	 
	 
	  //Display CGST - Start
	  var zCentral = 0;
	 
	  $.each(cgstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zCentral == 0){
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                    	+'<td></td>'
                    	+'<td class="hlmobil"><b>Central Tax</b></td>'
                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
   					    +'<td></td>'
                    	+'<td>'+tax+'</td>'
   					    +'<td></td>'
                     +'</tr>'
			  );
			  zCentral++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                          +'<td></td>'
                          +'<td class="hidemob"></td>'
	                      +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				  +'<td></td>'
	                      +'<td>'+tax+'</td>'
	       				  +'<td></td>'
                     +'</tr>'  
			  
			  );
		  }
		 
		});
	  	var zCentral = 0;
		  $.each(cgstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zCentral == 0){
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
                        	+'<td></td>'
                        	+'<td class="hlmobil"><b>Central Tax</b><span style="color:red">*</span></td>'
                        	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
                        	+'<td>'+tax+'</td>'
	        				+'<td></td>'
                         +'</tr>'
				  );
				  zCentral++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
						  	  +'<td></td>'
	                          +'<td class="hidemob"></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				  +'<td></td>'
	                          +'<td>'+tax+'</td>'
	        				  +'<td></td>'
                         +'</tr>'  
				  
				  );
			  }
			 
			});
	  //Display CGST - End
	  
	  //Display SGST - Start
	  var zState = 0;
	  $.each(sgstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zState == 0){
			
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
					  	+'<td></td>'
                    	+'<td class="hlmobil"><b>State Tax</b></td>'
                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
                     +'</tr>'  
			  );
			  zState++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                            +'<td></td>'
                            +'<td class="hidemob"></td>'
                            +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
                            +'<td>'+tax+'</td>'
	        				+'<td></td>'
                     +'</tr>'  
			  
			  );
			  
		  }
		 
		});
	  
	  var zState = 0;
	  $.each(sgstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zState == 0){
			
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                        +'<td></td>'
                    	+'<td class="hlmobil"><b>State Tax</b><span style="color:red">*</span></td>'
                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
                     +'</tr>'
			  );
			  zState++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
                     +'</tr>'  
			  
			  );
			  
		  }
		 
		});
	  //Display SGST - End
	  
	  //Display IGST - Start
	  var zIntegrated = 0;
	  $.each(igstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zIntegrated == 0){
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
	                    +'<td></td>'
	                    +'<td class="hlmobil"><b>Integrated Tax</b></td>'
	                    +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	                    +'<td>'+tax+'</td>'
	       				+'<td></td>'
                     +'</tr>'
			  );
			  zIntegrated++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
                     +'</tr>'  
			  
			  );
		  }
		 
		});
	  
	  var zIntegrated = 0;
	  $.each(igstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zIntegrated == 0){
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                        +'<td></td>'
                    	+'<td class="hlmobil"><b>Integrated Tax</b><span style="color:red">*</span></td>'
                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
                     +'</tr>'
			  );
			  zIntegrated++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
                     +'</tr>'  
			  
			  );
		  }
		 
		});
	  //Display IGST - End
	  
	//Display UGST - Start
	  var zUnionT = 0;
	  $.each(ugstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zUnionT == 0){
			 
			  
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                        +'<td></td>'
                    	+'<td class="hlmobil"><b>Union Territory Tax</b></td>'
                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
                     +'</tr>'
			  );
			  zUnionT++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
					  	 +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
                     +'</tr>'  
			  
			  );
		  }
		 
		});
	  
	  var zUnionT = 0;
	  $.each(ugstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zUnionT == 0){
			 
			  
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                        +'<td></td>'
                    	+'<td class="hlmobil"><b>Union Territory Tax</b><span style="color:red">*</span></td>'
                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
                    	+'<td></td>'
                     +'</tr>'
			  );
			  zUnionT++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
                         +'<td></td>'
                     +'</tr>'  
			  
			  );
		  }
		 
		});
	  //Display UGST - End	  
	  
	  //Types of taxes - End
	  $("#mytable2 tbody:last-child").append(
			  '<tr>'
                +'<td></td>'
                +'<td class="hlmobil"><b>Cess</b></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
                +'<td class="text-right">'+cessTotalTax.toFixed(2)+'</td>'
             +'</tr>'
	  );
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
                 +'<td></td>'
 					+'<td class="hlmobil"><b>Total Taxes</b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
        			+'<td class="text-right">'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
        		  +'</tr>'
		  );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
                    +'<td></td>'
 					+'<td class="hlmobil"><b>Total Taxes</b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
        			+'<td class="text-right">'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
        		  +'</tr>'
		  );
	  }
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  			+'<td><b>Total '+cnDnType+' Value </b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
                    +'<td class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
                   +'</tr>'
         );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  			+'<td><b>Total '+cnDnType+' Value </b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
                    +'<td class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
                  +'</tr>'
         ); 
	  }
	  
	  $("#mytable2 tbody:last-child").append(
			  '<tr>'
                +'<td><b>Round off</b></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
                +'<td class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
               +'</tr>'
               +'<tr>'
                +'<td><b>Total '+cnDnType+' Value (After Round off)</b></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
                +'<td class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
               +'</tr>'
               +'<tr>'
                +'<td><b>Total '+cnDnType+' value Rs.(in words): </b></td>'
                +'<td><strong>'+ json.amtInWords+'</strong></td>'  
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'                                
               +'</tr>'
	  );
	  
	  $("#mytable2").append('</tbody>');
	  
	  //RND - End
	  
	  if(isDiffPercentPresent > 0){
		 $("#diffPercentShowHide").show(); 
	  }else{
		  $("#diffPercentShowHide").hide();  
	  }
	  
	  $("#footerNoteDiv").html("");
	  if($("#footerNote").val()){
			$("#footerNoteDiv").append(
					'<hr>'+$("#footerNote").val()+'<hr>'
			
			);
	  }
}

function backToInvoicePage(idValue) {
	document.previewInvoice.action = "./getCnDn";	//getInvoiceCNDNDetails
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
}

function callDefaultOnPageLoad(defaultCnDnType){
	if(defaultCnDnType == 'creditNote'){
		$("#previewSubmit").text("Preview Credit Note");
	}else{
		$("#previewSubmit").text("Preview Debit Note");
	}
}

function removeErrorMsgFromInputFields(index){
	$("#quantityPost-" + index).addClass("").removeClass("input-error");
	$("#quantitySpan-" + index).css("display","none");
	
	$("#taxableValuePost-" + index).addClass("").removeClass("input-error");
	$("#taxableValueSpan-" + index).css("display","none");
	
	$("#taxRatePost-" + index).addClass("").removeClass("input-error");
	$("#taxRateSpan-" + index).css("display","none");
	
	$("#errorId-"+index).css("display","none");
}

function loadCnDnReasonList(){
  $.ajax({
		url : "getCNDNReasonList",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#selectReason").empty();
			$("#selectReason").append('<option value="">Select</option>');
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			$.each(json,function(i,value) {
				$("#selectReason").append($('<option>').text(value.reason).attr('value',value.reason));
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         }
	});
}

function setProductTaxRate(recordNo,taxRate){
	
	$(document).ready(function(){
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
				
				 $.each(json,??function(i,??value)??{
					  
					if(taxRate == value.taxRate){
						 $('#taxRatePost-'+recordNo).append($('<option>').text(value.taxRate).attr('value',??value.taxRate).attr('selected','selected'));
					 }else{
						 $('#taxRatePost-'+recordNo).append($('<option>').text(value.taxRate).attr('value',??value.taxRate));
					 }
						    
				});
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {
	        	 alert("5");
	        	 getInternalServerErrorPage();   
	        }
		}); 
	});
}

function setServiceTaxRate(recordNo,taxRate){
	
	$(document).ready(function(){
	    $.ajax({
			url : "getServiceRateOfTaxDetails",
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

			
				 $.each(json,??function(i,??value)??{
					  
						if(taxRate == value.taxRate){
							 $('#taxRatePost-'+recordNo).append($('<option>').text(value.taxRate).attr('value',??value.taxRate).attr('selected','selected'));
						 }else{
							 $('#taxRatePost-'+recordNo).append($('<option>').text(value.taxRate).attr('value',??value.taxRate));
						 }
							    
					});
			
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
	         },
	         error: function (data,status,er) {
	        	 alert("6");
	        	 getInternalServerErrorPage();   
	        }
		});
	});
    
}

function showBillDetailsInPreviewDiv(){
	var gstinJsonForUserData = fetchGstinDataForUserData($("#gstnStateId").val());
	$("#userDetailsInPreview").html('');
	var address = '';
	var city = '';
	var pincode = '';
	var stateCodeId = '';
	var stateToShow = '';
	var documentType = $("#documentType option:selected").text();
	if(gstinJsonForUserData != ''){
		address = (gstinJsonForUserData.gstinAddressMapping.address) ? gstinJsonForUserData.gstinAddressMapping.address : ''; 
		city = (gstinJsonForUserData.gstinAddressMapping.city) ? gstinJsonForUserData.gstinAddressMapping.city : '';
		pincode = (gstinJsonForUserData.gstinAddressMapping.pinCode) ? gstinJsonForUserData.gstinAddressMapping.pinCode : '';
		stateCodeId = (gstinJsonForUserData.state) ? gstinJsonForUserData.state : '';
		stateToShow = (gstinJsonForUserData.gstinAddressMapping.state) ? gstinJsonForUserData.gstinAddressMapping.state : '';
		
		$("#userDetailsInPreview").append(
				'<strong>'+$("#user_org_name").val()+'</strong><br>'
				  +address+' , '+city+' , '+stateToShow+' [ '+stateCodeId+' ]'+' , '+pincode+'<br>'
				  +'<strong>GSTIN : </strong>'+$("#gstnStateId").val()+'<br>'
				  +'<strong>Original Tax Document No : </strong>'+$("#invoiceNumber").val()+'<br/>'
				  +'<strong>Original Tax Document Date : </strong>'+formatDateInJS($("#invoiceDate").val())+'<br>'
		);
		
	}
	
}

function fetchGstinDataForUserData(gstinNo){
	var gstinJsonForUserData = '';
	$.ajax({
		url : "getGstinDetailsFromGstinNo",
		method : "POST",
		dataType : "json",
		data : { gstinNo : gstinNo,_csrf_token : $("#_csrf_token").val()},
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
			  //alert(json.gstinAddressMapping.address + ", "+json.gstinAddressMapping.pinCode+ ", "+json.gstinAddressMapping.city+ ", "+json.gstinAddressMapping.state);
			  
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			  gstinJsonForUserData = json;
		  },
		  error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
		  }
	});
	
	return gstinJsonForUserData;
}

function formatDateInJS( inputDate){
	var formattedDate = inputDate.split("-");
	console.log(inputDate);
	//var jsDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2].substr(0, 2), dateParts[2].substr(3, 2), dateParts[2].substr(6, 2), dateParts[2].substr(9, 2));
	return formattedDate[2].substr(0, 2)+"-"+formattedDate[1]+"-"+formattedDate[0];
}