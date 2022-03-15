
$(document).ready(function(){
	   $("#submitId").click(function(e){
		   var accessAllowedOrNot = '';
		   var errorStatus = true;
		   var blankMsg="This field is required";
		   var errFlag0 = validateSelect("documentType","documentType-req");
		   var errFlag1 = validateSelect("gstnStateId","gstnStateId-req");  
		   var errFlag2 = validateDateField("invoice_date","invoice_date-req");
		   var errFlag3 = validateCustomerField("customer_id","customer_name","customer_name-req",blankMsg);
		   var errFlag4 = false;
		   var errFlag8 = false;
		   var errFlag9 = false;
		   var errFlag13 = false;
		   var errFlag14 = false;
		   var errFlag20 = false;
		   var errFlag21 = false;
		   var isCheckedShippingToBilling = $('#shipToBill').is(':checked'); 
		   var country = $('select#selectCountry option:selected').val();
		   if(isCheckedShippingToBilling == true){
			   errFlag9 = false;
		   }else{
			   errFlag13 = validateTextField("shipTo_customer_name","shipTo_customer_name-req",blankMsg);
			   errFlag14 = validateTextField("shipTo_address","shipTo_address-req",blankMsg);
			   errFlag20 = validateTextField("shipTo_pincode","empty-message-1",blankMsg);
			   errFlag21 = validateTextField("shipTo_city","shipTo_city-req",blankMsg);
			   if((country === '')){
				   errFlag9 = validateSelect("selectCountry","selectCountry-req");
				   $("#selectCountry").focus();
			   }
		   }
		  
		   if((country === 'India')){
				errFlag4 = validateSelect("selectState","selectState-req");
		   }
		   if((country === '') || (country === 'Other')){
				errFlag8 = validateSelect("exportType","exportType-csv-id");
		   }
		   var errFlag5 = checkForServiceLength();
		   var errFlag6 = validateSelect("calculation_on","calculation_on-req");
		   //var errFlag7 = validateCustomerContactNo();
		   
		   if(errFlag0){
				$("#documentType").focus();
		   }
		   
		   if(errFlag13){
				 $("#shipTo_customer_name").focus();
		   }
			
		   if(errFlag14){
				 $("#shipTo_address").focus();
		   }
		   
		   if(errFlag20){
				 $("#shipTo_pincode").focus();
			}
			
			if(errFlag21){
				 $("#shipTo_city").focus();
			}
		   
		   var errFlag15 = false;
		   var gstinStateValid = false;
		   var isCheckedEcommerce = $('#ecommerce').is(':checked');
		   if(isCheckedEcommerce == true){
				errFlag15 = validateEcommerceGstin();   
				gstinStateValid = validGstinStateCode();
		   }
		   if(errFlag15 || gstinStateValid){
				$("#ecommerceGstin").focus();
		   }
		   
		   if ((errFlag0) || (errFlag1) || (errFlag2) || (errFlag4) ||(errFlag5) || (errFlag6) || (errFlag3) || (errFlag8) || (errFlag9) || (errFlag13) ||(errFlag14) ||(errFlag15) || (gstinStateValid) || (errFlag20) || (errFlag21)){
				e.preventDefault();	 
		   }else{
			   errorStatus = false;
		   }
		   
		   
		   if(!errorStatus){
			   $('#loadingmessage').show();
			   $('.lk-toggle-nav').hide();
			   //RND - START
			   var generateInputJsonData = getInputFormDataJson();
			   //console.log(generateInputJsonData);
			   $.ajax({
					url : "calculateTaxOnInvoicePreview",
					method : "post",
					headers: {
						_csrf_token : $("#_csrf_token").val()
					},
					data : JSON.stringify(generateInputJsonData),
					contentType : "application/json",
					dataType : "json",
					async : false,
					success:function(json,fTextStatus,fRequest){
						if (isValidSession(json) == 'No') {
							window.location.href = getDefaultWizardSessionExpirePage();
							return;
						}

						if(isValidToken(json) == 'No'){
							window.location.href = getWizardCsrfErrorPage();
							return;
						}
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
						lastAndFinalInvoiceJson = json;
						if(json.renderData != 'accessDeny' ){
							accessAllowedOrNot = 'GRANT_ACCESS';
						}
			         },
		            error:function(data,status,er) { 

		            	getWizardInternalServerErrorPage();  
		            }
				}); 
			   //RND - END
			   
			   
			   
			   
			   if(accessAllowedOrNot == 'GRANT_ACCESS'){
				   $("#addInvoiceDiv").hide();
				   $("#generateInvoiceDefaultPageHeader").hide();
				   setTimeout(function(){ 
					   $("#previewInvoiceDiv").show(); 
					     
					   showCustomerDetailsInPreviewInvoiceDiv();
					   showServiceListDetailsInPreviewInvoiceDiv(lastAndFinalInvoiceJson);
					   var gstinNoWithState = $("#gstnStateId option:selected").text();
					   showReverseChargeValue();
					   showUserDetailsInPreviewInvoiceDiv(gstinNoWithState);
					   showCustomerPoNoInPreviewInvoiceDiv();
					   showPreviewPageHeader();
					   //showAmountInWords();
					   $("#customerEmailDiv").hide();
					   $('#loadingmessage').hide(); // hide the loading message
					   $('.lk-toggle-nav').show();
				   }, 4000);
			   }else{
				   $("#addInvoiceDiv").hide();
				   bootbox.alert("Data is been manipulated.", function() {
						setTimeout(function(){ 
							//location.reload();
							$('#loadingmessage').hide(); // hide the loading message
							$('.lk-toggle-nav').show();
							window.location.href = getWizardCustomLogoutPage();
							return;
						}, 1000);
				   });
			   }
			   
			  
		   }
	   });   
	   
	   $("#backToaddInvoiceDiv").click(function(){
		   $("#previewInvoiceDiv").hide();
		   $("#generateInvoicePageHeaderPreview").hide();
		   $('#loadingmessage').show();
		   $('.lk-toggle-nav').hide();
		   setTimeout(function(){
			   $("#addInvoiceDiv").show();
			   showDefaultPageHeader();
			   $('#loadingmessage').hide(); 
			   $('.lk-toggle-nav').show();
		   }, 3000);
	   }); 
	   
	   $("#finalSubmitId").click(function(){
			  var customerEmailAddress = $("#customer_custEmail").val(); 
			  var documentType = $("#documentType option:selected").text();
			  if(customerEmailAddress == ""){
				  openEmailAddressModelBox();
			  }else{
				  
				  bootbox.confirm({
					  message: "I hereby agree and declare that by clicking the 'Send "+documentType+"' button herein-below, a valid and binding document is generated by me and such clicking constitutes my signature thereto, as if the document is actually signed by me in writing.",
					    buttons: {
					        confirm: {
					            label: 'Send '+documentType,
					            className: 'btn-success'
					        },
					        cancel: {
					            label: 'Cancel',
					            className: 'btn-danger'
					        }
					    },
					    callback: function (result) {
						   if (result){ 
								  $("#previewInvoiceDiv").hide();
								  $("#customerEmailDiv").hide();
								  $("#generateInvoiceCustomerEmailPageHeader").hide();
								  $("#generateInvoicePageHeaderPreview").hide();
								  $('#loadingmessage').show();
								  $('.lk-toggle-nav').hide();
								  /*setTimeout(function(){*/
									  //disable the submit button 
									  $('.btn-success').prop("disabled", true);
									  
									   var inputData = getInputFormDataJson();
									   
								   	   console.log(inputData);
								       //alert("inputData : "+inputData);
									    $.ajax({
											url : "addGeneratedInvoice",
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
													window.location.href = getDefaultWizardSessionExpirePage();
													return;
												}

												if(isValidToken(json) == 'No'){
													window.location.href = getWizardCsrfErrorPage();
													return;
												}
												
												setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
												
												if(json.response == 'SUCCESS'){
													//resetCompleteFormValues();
													bootbox.alert(documentType+" generated Successfully with "+documentType+" No : "+json.InvoiceNumber, function() {
														/*setTimeout(function(){ */
															//location.reload();
															$('#loadingmessage').hide();
															$('.lk-toggle-nav').show();
															/*window.location.href = 'wHome#doc_management';
															return;*/
															$('<form>', {
															    "id": "manageInvoice",
															    "html": '<input type="hidden" name="id" value="'+json.InvoicePkId+'" /><input type="hidden" id="_csrf_token" name="_csrf_token" value="'+$("#_csrf_token").val()+'" />',
															    "action": "./getWizardInvoiceDetails",
															    "method":"post"
															}).appendTo(document.body).submit();
														/*}, 1000);*/
													});
												}
												
												if(json.response == 'accessDeny'){
													bootbox.alert("Data is been manipulated.", function() {
														/*setTimeout(function(){ */
															//location.reload();
															$('#loadingmessage').hide();
															$('.lk-toggle-nav').show();
															window.location.href = getWizardCustomLogoutPage();
															return;
														/*}, 1000);*/
													});
												}
												if(json.response == 'serverError'){
													bootbox.alert("Error occured while generating invoice.", function() {
														$('#loadingmessage').hide();
														$('.lk-toggle-nav').show();
														window.location.href = 'wHome#doc_management';
														return;
													});
												}
												
									         },
								            error:function(data,status,er) { 
								            	bootbox.alert("Error occured while generating invoice.", function() {		
								            		$('#loadingmessage').hide();
													$('.lk-toggle-nav').show();
													window.location.href = 'wHome#doc_management';
													return;
												});
								             }
										}); 
								    
								/*  }, 1000);*/
							    
						      }
					    } 
				}); //bootbox.confirm ends
		   }
		}); //#finalSubmitId.click ends
});



function resetCompleteFormValues(){
	  $("#addInvoiceDiv").show(); 
	  $("#previewInvoiceDiv").hide();  
	  $("#customerDetailsInPreview").html("");//div within preview
	  $("#serviceListPreviewtable").html("");//div within preview
	  
	  //clear fields in invoice form
	  $("#gstnStateId").val("");
	  $("#invoice_date").val("");
	  
	  $("#customer_name").val("");//Customer
	  $("#customer_id").val("");//Customer
	  $("#customer_custAddress1").val("");//Customer
	  //$("#customer_custAddress2").val("");//Customer
	  $("#customer_custCity").val("");//Customer
	  $("#customer_custState").val("");//Customer
	  $("#customer_place").val("");//Customer
	  $("#customer_country").val("");//Customer
	  
	  $("#selectState").val("");
	  $("#selectCountry").val("");
	  
	  $("#po_id").val("");//PODETAILS
	  $("#poDetails_poNo").val("");//PODETAILS
	  $("#poDetails_poValidToDate").val("");//PODETAILS
	  $("#exportType").val("");
	  
	  resetFormValues();
	  
	  
	  $("#toggle").html("");
	  $("#discountValue").val("");
	  $("#discountAmountToShow").val("");
	  $("#discountAmount").val("");
	  $("#discountRemarks").val("");
	  $("#additionalChargesValue").val("");
	  $("#additionalAmountToShow").val("");
	  $("#additonalAmount").val("");
	  $("#additionalChargesRemark").val("");
	  $("#amountAfterDiscountToShow").val("");
	  $("#amountAfterDiscount").val("");
	  $("#totalTaxToShow").val("");
	  $("#totalTax").val("");
	  $("#invoiceValueToShow").val("");
	  $("#invoiceValue").val("");

}




function showCustomerDetailsInPreviewInvoiceDiv(){
	
	//check if billing address is same as shipping address - start
	var isChecked = $('#shipToBill').is(':checked'); 
	var shipToCustomerName = '';
	var shipToAddress = '';
	var shipToCity = '';
	var shipToState = '';
	var shipToStateCode = '';
	var shipToStateCodeId = '';
	var placeOfSupply = '';
	if(isChecked == true){
		 shipToCustomerName = ($("#customer_name").val().split('] - ')[1]).trim();
		 shipToAddress = $("#customer_custAddress1").val();	//+" , "+$("#customer_custAddress2").val()
		 shipToCity = $("#customer_custCity").val();
		 shipToState = $("#customer_custState").val();
		 shipToStateCode = $("#customer_custStateCode").val();
		 shipToStateCodeId = $("#customer_custStateCodeId").val();
		 placeOfSupply = shipToState + "[ "+shipToStateCode+" ]";
		 
	}else{
		 shipToCustomerName = $("#shipTo_customer_name").val();
		 shipToAddress = $("#shipTo_address").val();
		 shipToCity = $("#shipTo_city").val();
		 shipToState = $("#selectState option:selected").text();
		 shipToStateCode = $("#shipTo_stateCode").val();
		 shipToStateCodeId = $("#shipTo_stateCodeId").val();
		 placeOfSupply = shipToState +"[ "+shipToStateCode+" ]";
	}
	
	//End
	
	var documentType = $("#documentType option:selected").text().toUpperCase();	
	
	  $("#customerDetailsInPreview").html("");
	  $("#customerDetailsInPreview").append('<h1>'+documentType+'</h1>');	  
	  
	  if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
		  $("#customerDetailsInPreview").append(
				  '<div class="invoice-txt">'
					+'<strong><u>Bill To</u></strong><br>'
					+'<b>Name : </b>'+$("#customer_name").val().split('] - ')[1].trim()+'<br>'
					+'<b>Address : </b>'+$("#customer_custAddress1").val()+'<br>'			//+' , '+$("#customer_custAddress2").val()
					+'<b>City : </b>'+$("#customer_custCity").val()+'<br>'
					+'<b>State : </b>'+$("#customer_custState").val()+' [ '+$("#customer_custStateCodeId").val()+' ]'+'<br>'
					+'<b>State Code : </b>'+$("#customer_custStateCode").val()+'<br>'
					+'<b>GSTIN/Unique Code : </b>'+$("#customer_custGstId").val()+'<br>'
					+'<b>Place Of Supply : </b>'+ placeOfSupply+'<br>'
					+'</div>');
		  
		 
	  }else{
		  $("#customerDetailsInPreview").append(
				  '<div class="col-sm-6">'
					+'<div class="invoice-txt">'
					+'<strong><u>Bill To</u></strong><br>'
					+'<b>Name : </b>'+$("#customer_name").val().split('] - ')[1].trim()+'<br>'
					+'<b>Address : </b>'+$("#customer_custAddress1").val()+'<br>'			//+' , '+$("#customer_custAddress2").val()
					+'<b>City : </b>'+$("#customer_custCity").val()+'<br>'
					+'<b>State : </b>'+$("#customer_custState").val()+' [ '+$("#customer_custStateCodeId").val()+' ]'+'<br>'
					+'<b>State Code : </b>'+$("#customer_custStateCode").val()+'<br>'
					+'<b>GSTIN/Unique Code : </b>'+$("#customer_custGstId").val()+'<br>'
					+'<b>Place Of Supply : </b>'+ placeOfSupply+'<br>'
					+'</div>'
				+'</div>'
		  
				+'<div class="col-sm-6">'
					+'<div class="invoice-txt">'
						+'<strong><u>Ship To</u></strong><br>'
						+'<b>Name : </b>'+shipToCustomerName+'<br>'
						+'<b>Address : </b>'+shipToAddress+'<br>'
						+'<b>City : </b>'+shipToCity+'<br>'
						+'<b>State : </b>'+shipToState+' [ '+shipToStateCodeId+' ]'+'<br>'
						+'<b>State Code : </b>'+shipToStateCode+'<br>'	
					+'</div>'
				+'</div>');
		  
	  }   
}
	
	
	function showServiceListDetailsInPreviewInvoiceDiv(json){
		var documentType = $("#documentType option:selected").text();	
		  //RND - Start
		 var isDiffPercentPresent = 0;
		  $("#mytable2").html("");
		  
		  $("#mytable2").append(
				  '<thead><tr>'
					  +'<th>Description</th>'
	                  +'<th>SAC/HSN</th>'
	                  +'<th>Quantity</th>'
	                  +'<th>UOM</th>'
	                  +'<th>Price/UOM(Rs.)</th>'
	                  +'<th>Disc(Rs.)</th>'
	                  +'<th>Total (Rs.) After Disc</th>'
	              +' </tr></thead>'	 
				 );
		  
		  
		  var amountSubtotal = 0;
		  var cessTotalTax = 0;
		  var containsAdditionalCharges = '';
		  var containsDiffPercentage = '';
		  $("#mytable2").append('<tbody>');
		   $.each(json.invoiceDetails.serviceList,function(i,value) {
			   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
				 $("#mytable2 tbody:last-child").append(
				     '<tr>'
                     +'<td>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></td>'
                     +'<td>'+value.hsnSacCode+'</td>'
                     +'<td>'+value.quantity+'</td>'
                     +'<td>'+value.unitOfMeasurement+'</td>'
                     +'<td class="text-right">'+value.rate+'</td>'
                     +'<td class="text-right">'+value.offerAmount+'</td>'
                     +'<td class="text-right">'+(value.previousAmount - value.offerAmount).toFixed(2)+'</td>'
                  +'  </tr>'	 
				 );
				 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
				 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
			});
		   
		   
			  $("#mytable2 tbody:last-child").append(
	                    '<tr>'                 
                   	+'<td>&nbsp;</td>'    	
                     	+'<td class="hlmobil"><b>Total Value (A)</b></td>'
                     	+'<td>&nbsp;</td>'
                     	+'<td>&nbsp;</td>'
                     	+'<td>&nbsp;</td>'
                     	+'<td>&nbsp;</td>'
                     	+'<td>'+amountSubtotal.toFixed(2)+'</td>'
                      +'</tr>'
			  );
			  
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
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</td>'
	                    +'</tr>'
			  );
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
		  $.each(json.invoiceDetails.serviceList,function(i,value) {
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
        					+'<td></td>'
                         	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        					+'<td></td>'
        					+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'
				  );
				  zCentral++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
	                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
	        				  +'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				  +'<td></td>'
	        				  +'<td></td>'
	                          +'<td>'+tax+'</td>'
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
		        				+'<td></td>'
	                         	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                         	+'<td>'+tax+'</td>'
	                          +'</tr>'
					  );
					  zCentral++;
				  }else{
					 
					  $("#mytable2 tbody:last-child").append(
							  '<tr>'
							  	  +'<td></td>'
		                          +'<td class="hidemob"></td>'
		        				  +'<td></td>'
		                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				  +'<td></td>'
		        				  +'<td></td>'
		                          +'<td>'+tax+'</td>'
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
	        				+'<td></td>'
                         	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'  
				  );
				  zState++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
	                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                          +'<td>'+tax+'</td>'
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
	        				+'<td></td>'
                         	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'
				  );
				  zState++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                          +'<td>'+tax+'</td>'
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
	        				+'<td></td>'
                         	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'
				  );
				  zIntegrated++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                          +'<td>'+tax+'</td>'
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
	        				+'<td></td>'
                         	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'
				  );
				  zIntegrated++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                          +'<td>'+tax+'</td>'
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
	        				+'<td></td>'
                         	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'
				  );
				  zUnionT++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                          +'<td>'+tax+'</td>'
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
	        				+'<td></td>'
                         	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				+'<td></td>'
                         	+'<td>'+tax+'</td>'
                          +'</tr>'
				  );
				  zUnionT++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
                          +'<td></td>'
	                          +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                          +'<td>'+tax+'</td>'
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
        				+'<td></td>'
                     	+'<td>'+cessTotalTax.toFixed(2)+'</td>'
                  +'</tr>'
		  );
		  
		 
		  
		  if(containsAdditionalCharges == 'YES'){
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                      +'<td></td>'
	  					  +'<td class="hlmobil"><b>Total Taxes (C)</b></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
             			  +'<td>'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
             		  +'</tr>'
			  );
		  }else{
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                      +'<td></td>'
	  					  +'<td class="hlmobil"><b>Total Taxes (B)</b></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
             			  +'<td>'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
             		  +'</tr>'
			  );
		  }
		  
		  
		  if(containsAdditionalCharges == 'YES'){
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
			  				+'<td><b>Total '+documentType+' Value (A+B+C)</b></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	                        +'<td>'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
	                    +'</tr>'
	          );
		  }else{
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
			  				+'<td><b>Total '+documentType+' Value (A+B)</b></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	        				+'<td></td>'
	                        +'<td>'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
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
        				+'<td></td>'
	                    +'<td>'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
	                +'</tr>'
	                +'<tr>'
	                    +'<td><b>Total '+documentType+' Value (After Round off)</b></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
	                    +'<td>'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
	                +'</tr>'
                    +'<tr>'
                        +'<td><b>Total '+documentType+' value Rs.(in words): </b></td>'
                        +'<td><strong>'+ json.amtInWords+'</strong></td>'  
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'                                 
                    +'</tr>'
		  );
		  
		  $("#mytable2").append('</tbody>');
		  
		  if(isDiffPercentPresent > 0){
			 $("#diffPercentShowHide").show(); 
		  }else{
			  $("#diffPercentShowHide").hide();  
		  }
		  //RND - End
		  
		
		  //call this function in order to have responsive table
		  //$('.resTable').riltable();
	}
	
function addTotal(subtotal,totalTax){
	var finalSubTotal = 0;
	finalSubTotal = parseFloat(subtotal) + parseFloat(totalTax);
	return finalSubTotal.toFixed(2);	
}

function showUserDetailsInPreviewInvoiceDiv(gstinNoWithState){
	var address = '';
	var city = '';
	var pincode = '';
	var stateCodeId = '';
	var stateToShow = '';
	var gstinNo = gstinNoWithState.split(' [')[0];
	$.ajax({
		url : "getGstinDetailsFromGstinNo",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		method : "POST",
		dataType : "json",
		data : { gstinNo : gstinNo},
		async : false,
		  success:function(json,fTextStatus,fRequest) {
			  if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			  }

			  if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			  }
			  //alert(json.gstinAddressMapping.address + ", "+json.gstinAddressMapping.pinCode+ ", "+json.gstinAddressMapping.city+ ", "+json.gstinAddressMapping.state);
			  address = json.gstinAddressMapping.address; 
			  city = json.gstinAddressMapping.city;
			  pincode = json.gstinAddressMapping.pinCode;
			  stateCodeId = json.state;
			  stateToShow = json.gstinAddressMapping.state;
			  
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		  },
		  error: function (data,status,er) {
				 
				 getWizardInternalServerErrorPage();   
		  }
	});
	
	var documentType = $("#documentType option:selected").text();	
	
	$("#userDetailsInPreview").html("");
	$('#userDetailsInPreview').append(
			 '<b>'+$("#user_org_name").val()+'</b> <br>'
			  +address+' , '+city+' , '+stateToShow+' [ '+stateCodeId+' ]'+' , '+pincode+'<br>'
			  +'<b>GSTIN  : </b>'+$("#gstnStateId option:selected").text()+'<br>'
			  +'<b>PAN : </b>'+$("#user_org_panNumber").val()+'<br>'
			 /* +'<p><span>Invoice No:</span></p>'*/
			  +'<b>'+documentType+' Date : </b>'+$("#invoice_date").val()+'<br>'
	);
	
	
	if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
		if($("#invoicePeriodFromDateInString").val() == '' || $("#invoicePeriodToDateInString").val() == ''){
			$('#userDetailsInPreview').append( '<b>Service Period :</b> N/A  TO  N/A </br>');
		}else{
			$('#userDetailsInPreview').append( '<b>Service Period : </b>'+$("#invoicePeriodFromDateInString").val()+' TO '+$("#invoicePeriodToDateInString").val()+'</br>');
		}
		
	}
	
	$('#userDetailsInPreview').append(
			'<b>Whether tax is payable on reverse charge : </b>'+$("#reverseChargeYesNo").val()+'</br>'	
	);
	
	$("#userAddressWithDetails").html("");
	/*$("#userAddressWithDetails").append(
			'<span>'+address+' , '+city+' - '+pincode+'</span>'
	);*/
}

function showCustomerPoNoInPreviewInvoiceDiv(){
	$("#preview_customer_purchase_order").html("");
	$("#preview_customer_purchase_order").append(
			/*'<div class="col-sm-6">'*/
				'<div class="tax-invoice">'
				+'<b>Customer Purchase Order : </b>'+$("#poDetails_poNo").val()+'<br>'					       
				/*+'</div>'
			+'</div>'
			+'<div class="col-sm-6">'
				+'<div class="invoiceInfo">'
					    
				+'</div>'*/
			+'</div>');
	
	$("#footerNoteDiv").html("");
	$("#footerNoteDiv").append(
			'<p><span>'+$("#footerNote").val()+'</span></p>'
	);
}


function showPreviewPageHeader(){
	$("#generateInvoiceDefaultPageHeader").css("display","none");
	showPreviewPageHeaderBasedOnDocumentType();
	$("#generateInvoicePreviewPageHeader").show();
	$("#generateInvoiceCustomerEmailPageHeader").css("display","none");
}

function showPreviewPageHeaderBasedOnDocumentType(){
	var documentType = $("#documentType option:selected").text();
	$("#generateInvoicePageHeaderPreview").show();
	$("#generateInvoicePageHeaderPreview").html("");
	$("#generateInvoicePageHeaderPreview").text(documentType+" Preview");
}

function showDefaultPageHeader(){
	$("#generateInvoicePreviewPageHeader").css("display","none");
	$("#generateInvoiceDefaultPageHeader").show();
	$("#generateInvoiceCustomerEmailPageHeader").css("display","none");
}

function callInvoicePageOnBackButton(){
	 $("#previewInvoiceDiv").hide();
	 $("#addInvoiceDiv").show();
	 showDefaultPageHeader();
}

function openEmailAddressModelBox(){
	$("#previewInvoiceDiv").hide();
	$("#addInvoiceDiv").hide();
	showCustomerEmailPageHeader();
	$("#customerEmailDiv").show();
	
}

function showCustomerEmailPageHeader(){
	$("#generateInvoicePreviewPageHeader").css("display","none");
	$("#generateInvoiceDefaultPageHeader").css("display","none");
	$("#generateInvoiceCustomerEmailPageHeader").css("display","block");
}

function showReverseChargeValue(){
	var isReverseChargeChecked = $("#reverseCharge").is(':checked');
	var reverseCharge = 'No';
	if(isReverseChargeChecked == true){
		reverseCharge = 'Yes';
	}
	$("#reverseChargeYesNo").val(reverseCharge);
}

function getInputFormDataJson(){
	
	// get the form values
	   var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val();
	   var customerDetails = { 
			   					"id" : $("#customer_id").val() ,
			   					"custName": $("#customer_name").val(),
			   					"custAddress1" : $("#customer_custAddress1").val(),
			   					"custAddress2" : '',
			   					"custCity" : $("#customer_custCity").val(),
			   					"custState": $("#customer_custState").val()
			   				 };
	   var customerEmail = $("#customer_custEmail").val();
	   var isCustomerRegistered = "No";
	   if(typeof $("#customer_custGstId").val() != 'undefined' && $("#customer_custGstId").val() != ''){
			isCustomerRegistered = "Yes";
	   }
	   var servicePlace = $("#customer_place").val();
	   var serviceCountry = $("#customer_country").val();
	   var deliveryPlace = $('select#selectState option:selected').val();
	   var deliveryCountry = $('select#selectCountry option:selected').val();
	   if(deliveryCountry == '' || deliveryCountry == 'Other'){
		   deliveryPlace = 0;
	   }
	   if(isCustomerRegistered == "Yes"){
		   deliveryPlace = $("#customer_custStateCodeId").val();    //deliveryPlace = $("#customer_custStateId").val(); 
	   }
	  
	   var poDetails = $("#poDetails_poNo").val();
	   var typeOfExport = $('select#exportType option:selected').val();
	   
	   var discountType = $('input[name=discountType]').filter(':checked').val();
	   var discountValue = 0;
	  /* if(($("#discountValue").val()) && ($("#discountValue").val() != '')){
		   discountValue = $("#discountValue").val()
	   }*/
	   var discountAmount = 0;
	 /*  if(($("#discountAmount").val()) && ($("#discountAmount").val() != '')){
		   discountAmount = $("#discountAmount").val();
	   }*/
	   var discountRemarks = $("#discountRemarks").val();
	   var additionalChargesType = $('input[name=additionalChargesType]').filter(':checked').val();
	   var additionalChargesValue = 0;
	/*   if(($("#additionalChargesValue").val()) && ($("#additionalChargesValue").val() != '')){
		   additionalChargesValue = $("#additionalChargesValue").val()
	   }*/
	   var additonalAmount = 0;
	  /* if(($("#additonalAmount").val()) && ($("#additonalAmount").val() != '')){
		   additonalAmount = $("#additonalAmount").val();
	   }*/
	   var additionalChargesRemark = $("#additionalChargesRemark").val();
	   var amountAfterDiscount = 0;
	   var totalTax = 0;
	   var invoiceValue = 0;
	   var gstnStateId = $('select#gstnStateId option:selected').val();
	   var invoiceDate = $("#invoice_date").val();
	   var gstnWithStateInString = $("#gstnStateId option:selected").text();
	   var gstnStateIdInString = (gstnWithStateInString.split('[')[0]).trim();
	   
	   var invoicePeriodFromDate = $("#invoicePeriodFromDateInString").val();
	   var invoicePeriodToDate = $("#invoicePeriodToDateInString").val();
	   
	   var locationVal = $('select#location option:selected').val();
	   if(locationVal == undefined || locationVal == '' || (locationVal.length > 1)){
		   locationVal = "0";
	   }
	   //get Shipping address - Start 
	   
	 //check if billing address is same as shipping address - start
		var isChecked = $('#shipToBill').is(':checked'); 
		var billToShipIsChecked = 'No';
		var shipToCustomerName = '';
		var shipToAddress = '';
		var shipToCity = '';
		var shipToPincode = '';
		var shipToState = '';
		var shipToStateCode = '';
		var shipToStateCodeId = '';
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
	   
	   //get Shipping address - End 
	
	//check if ecommerce is checked or not - Start
		var isEcommerceChecked = $('#ecommerce').is(':checked');
		var ecommerceGstin = '';
		var ecommerce = 'No';
		if(isEcommerceChecked == true){
			ecommerceGstin = $("#ecommerceGstin").val();
			ecommerce = 'Yes';
		}
		var isReverseChargeChecked = $("#reverseCharge").is(':checked');
		var reverseCharge = 'No';
		if(isReverseChargeChecked == true){
			reverseCharge = 'Yes';
		}
	//check if ecommerce is checked or not - End
	   
	   //get services from list in javascript - Start 
	   var $toggle = $("#toggle");
	   var totalRecordNo = $toggle.children().length;
	   var jsonObject;
	   var serviceListArray = new Array();
	   for (i = 0; i < totalRecordNo; i++) { 
		  
			 //Start
			 var index2 = $toggle.children()[i].id.lastIndexOf("_");
			 var num2 = $toggle.children()[i].id.substring(index2);
			 //alert("num2 : "+num2 + "index2 "+index2);
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
		     jsonObject.discountTypeInItem = $("#discountTypeInItem"+num2).val();
		     jsonObject.offerAmount = $("#offerAmount"+num2).val();
		     jsonObject.hsnSacCode = $("#hsnSacCode"+num2).val();
		     jsonObject.diffPercent = $("#diffPercent"+num2).val();
			 serviceListArray.push(jsonObject);
			 //End
		}
	  	
	   //get services from list in javascript - End 
	   
	  //get add charges from list in javascript - Start 
	   var $addChgToggle = $("#add_chg_toggle");
	   var totalACRecordNo = $addChgToggle.children().length;
	   var jsonObjectAC;
	   var acListArray = new Array();
	   for (i = 0; i < totalACRecordNo; i++) { 
		   //Start
			 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
			 var num2 = $addChgToggle.children()[i].id.substring(index2);
			 //alert("num2 : "+num2 + "index2 "+index2);
			 num2 = num2.replace("_","-");
			 jsonObjectAC = new Object();
			 jsonObjectAC.additionalChargeId = $("#additionalChargeId"+num2).val();
			 jsonObjectAC.additionalChargeName = $("#additionalChargeName"+num2).val();
			 jsonObjectAC.additionalChargeAmount = $("#additionalChargeAmount"+num2).val();
			 acListArray.push(jsonObjectAC);
			//End
	   }
	   
	  //get add charges from list in javascript - End 
	   
	  //get Footer Note - Start
	   var footerNote = $("#footerNote").val();
	  //get Footer Note - End
	   
	   var documentType = $('select#documentType option:selected').val();
	
	   
	   var inputData = {
			   				"invoiceFor" : invoiceFor,
			   				"customerDetails" : customerDetails, 
			   				"servicePlace" : servicePlace,
			   				"serviceCountry" : serviceCountry,
			   				"deliveryPlace" : deliveryPlace,
			   				"deliveryCountry" : deliveryCountry,
			   				"poDetails" : poDetails,
			   				"typeOfExport" : typeOfExport,
			   				"serviceList" : JSON.parse(JSON.stringify(serviceListArray)),/* serviceJsonData, */
			   				"discountType" : discountType,
			   				"discountValue" : discountValue,
			   				"discountAmount" : discountAmount,
			   				"discountRemarks" : discountRemarks,
			   				"additionalChargesType" : additionalChargesType,
			   				"additionalChargesValue" : additionalChargesValue,
			   				"additonalAmount" : additonalAmount,
			   				"additionalChargesRemark" : additionalChargesRemark,
			   				"amountAfterDiscount" : amountAfterDiscount,
			   				"totalTax" : totalTax,
			   				"invoiceValue" : invoiceValue,
			   				"gstnStateId" : gstnStateId,
			   				"invoiceDateInString" : invoiceDate,
			   				"gstnStateIdInString" : gstnStateIdInString,
			   				"invoicePeriodFromDateInString" : invoicePeriodFromDate,
			   				"invoicePeriodToDateInString" : invoicePeriodToDate,
			   				"billToShipIsChecked" : billToShipIsChecked,
			   				"shipToCustomerName" : shipToCustomerName,
			   				"shipToAddress" : shipToAddress,
			   				"shipToCity" : shipToCity,
			   				"shipToPincode" : shipToPincode,
			   				"shipToState" : shipToState,
			   				"shipToStateCode" : shipToStateCode,
			   				"shipToStateCodeId" : shipToStateCodeId,
			   				"location" : locationVal,
			   				"customerEmail" : customerEmail,
			   				"ecommerce" : ecommerce,
			   				"ecommerceGstin" : ecommerceGstin,
			   				"reverseCharge" : reverseCharge,
			   				"addChargesList" : JSON.parse(JSON.stringify(acListArray)),
			   				"footerNote" : footerNote,
			   				"documentType" : documentType,
			   				"isCustomerRegistered" : isCustomerRegistered
	                   };
	//console.log(inputData);
	
	return inputData;
}

