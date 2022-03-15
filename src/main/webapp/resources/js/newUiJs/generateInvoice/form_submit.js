$(document).ready(function(){
	   $("#submitId").click(function(e){
		   var accessAllowedOrNot = '';
		   var errorStatus = true;
		   var blankMsg="This field is required";
		   var errFlag0 = validateSelect("documentType","documentType-csv-id");
		   var errFlag1 = validateSelect("gstnStateId","gstnStateId-csv-id");  
		   var errFlag2 = validateDateField("invoice_date","invoice-date-csv-id");
		   var errFlag3 = validateCustomerField("customer_id","customer_name","customer-name-csv-id",blankMsg);
		   var errFlag4 = false;
		   var errFlag8 = false;
		   var errFlag9 = false;
		   var errFlag13 = false;
		   var errFlag14 = false;
		   var errFlag20 = false;
		   var errFlag21 = false;
		   var errFlag22 = false;
		   var errFlag23 = false;
		   var isCheckedShippingToBilling = $('#shipToBill').is(':checked'); 
		   var country = $('select#selectCountry option:selected').val();
		   if(isCheckedShippingToBilling == true){
			   errFlag9 = false;
		   }else{
			   errFlag13 = validateTextField("shipTo_customer_name","shipTo-customer-name-csv-id",blankMsg);
			   errFlag14 = validateTextField("shipTo_address","shipTo-customer-address-csv-id",blankMsg);
			   errFlag20 = validateTextField("shipTo_pincode","shipTo-customer-pincode-csv-id",blankMsg);
			   errFlag21 = validateTextField("shipTo_city","shipTo-customer-city-csv-id",blankMsg);
			   if((country === '')){
				   errFlag9 = validateSelect("selectCountry","selectCountry-csv-id");
				   $("#selectCountry").focus();
			   }
		   }
		  
		   if((country === 'India')){
				errFlag4 = validateSelect("selectState","selectState-csv-id");
		   }
		   if((country === '') || (country === 'Other')){
				errFlag8 = validateSelect("exportType","exportType-csv-id");
		   }
		   
		   var errFlag6 = validateSelect("calculation_on","calculation-on-csv-id");
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
		   
		   var errFlag5 = true;
		   if($("#invoiceSequenceType").val() != 'Auto'){
				errFlag22 = validateTextField("invoiceNumber","invoiceNumber-csv-id",blankMsg);
				errFlag23 = checkIfInvoiceNumberAlreadyPresent($("#invoiceNumber").val());
				if(errFlag23){
					$("#invoiceNumber").addClass("input-error").removeClass("input-correct");
					$("#invoiceNumber-duplicate-csv-id").text("Invoice Number Already Present");
					$("#invoiceNumber-duplicate-csv-id").show();
				}
			}
			
			if(errFlag22 || errFlag23){
				 $("#invoiceNumber").focus();
			}
			
		   if ((errFlag1) || (errFlag2) || (errFlag4) || (errFlag6) || (errFlag3) || (errFlag8) || (errFlag9) || (errFlag13) ||(errFlag14) ||(errFlag15) || (gstinStateValid) || (errFlag20) || (errFlag21) || (errFlag22) || (errFlag23)){
				e.preventDefault();	
				bootbox.alert("Please fill in mandatory fields");
				
		   }else{
			   errFlag5 = checkForServiceLength();
		   }
		   
		   //alert("errFlag1"+errFlag1+",errFlag2"+errFlag2+",errFlag4"+errFlag4+",errFlag5"+errFlag5+",errFlag6"+errFlag6+",errFlag7"+errFlag7+",errFlag8"+errFlag8);
		   if ((errFlag1) || (errFlag2) || (errFlag4) ||(errFlag5) || (errFlag6) || (errFlag3) || (errFlag8) || (errFlag9) || (errFlag13) ||(errFlag14) ||(errFlag15) || (gstinStateValid) || (errFlag20) || (errFlag21)){
				e.preventDefault();	 
		   }else{
			   errorStatus = false;
		   }
		   
		   
		   if(!errorStatus){
			   $('.loader').show();
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
							window.location.href = getDefaultSessionExpirePage();
							return;
						}
						
						if(isValidToken(json) == 'No'){
							window.location.href = getCsrfErrorPage();
							return;
						}
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
						lastAndFinalInvoiceJson = json;
						if((json.renderData != 'accessDeny') && (json.renderData != 'TOO_LONG_INVOICE_VALUE')){
							accessAllowedOrNot = 'GRANT_ACCESS';
						}else{
							accessAllowedOrNot = json.renderData;
						}
			         },
			         error: function (data,status,er) {
			        	 
			        	 getInternalServerErrorPage();   
			        }
				}); 
			   //RND - END
			   
			   
			   
			   
			   if(accessAllowedOrNot == 'GRANT_ACCESS'){
				   $("#addInvoiceDiv").hide();
				   setTimeout(function(){ 
					   $("#previewInvoiceDiv").show(); 
					   
					   showBillDetailsInPreviewDiv(); //show Billing Details
					   showBillToAndShipToInPreviewDiv(); //show Bill To and Ship To
					   showServiceListDetailsInPreviewInvoiceDiv(lastAndFinalInvoiceJson); 
					   showCustomerPoNoInPreviewInvoiceDiv();
					   showPreviewPageHeader();
					   
					   
					   $("#customerEmailDiv").hide();
					   $('.loader').hide(); // hide the loading message
				   }, 3000);
			   }else if(accessAllowedOrNot == 'TOO_LONG_INVOICE_VALUE'){
				   $('.loader').hide();
				   bootbox.alert("Invoice value greater than 15 digits", function() {
					   setTimeout(function(){
						   $("#addInvoiceDiv").show();
						   showDefaultPageHeader();
						    
					   }, 3000);
				   });
				   
			   }else{
				   $("#addInvoiceDiv").hide();
				   $('.loader').hide();
				   bootbox.alert("Data is been manipulated.", function() {
						setTimeout(function(){ 
							//location.reload();
							 // hide the loading message
							window.location.href = getCustomLogoutPage();
							return;
						}, 1000);
				   });
			   }
			   
			  
		   }
	   });   
	   
	   $("#backToaddInvoiceDiv").click(function(){
		   $("#previewInvoiceDiv").hide();
		   $('.loader').show();
		   setTimeout(function(){
			   $("#addInvoiceDiv").show();
			   showDefaultPageHeader();
			   $('.loader').hide(); 
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
					            className: 'btn-success inv-btn'
					        },
					        cancel: {
					            label: 'Cancel',
					            className: 'btn-danger inv-btn'
					        }
					    },
					    callback: function (result) {
						   if (result){ 
								  $("#previewInvoiceDiv").hide();
								  $("#customerEmailDiv").hide();
								  $('.loader').show();
								  
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
											async : true,
											success:function(json,fTextStatus,fRequest){
												//alert(json);
												if (isValidSession(json) == 'No') {
													window.location.href = getDefaultSessionExpirePage();
													return;
												}
												
												if(isValidToken(json) == 'No'){
													window.location.href = getCsrfErrorPage();
													return;
													//alert("Invalid Token");
												}
												
												setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
												$('.loader').hide();
												
												if(json.response == 'SUCCESS'){
													
													bootbox.alert(documentType+" generated Successfully with "+documentType+" No : "+json.InvoiceNumber, function() {
														
															//$('.loader').hide();
															//window.location.href = 'home#invoice';//getHomePage();
															//return;
															$('<form>', {
															    "id": "manageInvoice",
															    "html": '<input type="hidden" name="id" value="'+json.InvoicePkId+'" /><input type="hidden" id="_csrf_token" name="_csrf_token" value="'+$("#_csrf_token").val()+'" />',
															    "action": "./getInvoiceDetails",
															    "method":"post"
															}).appendTo(document.body).submit();
															
													});
												}
												
												if(json.response == 'accessDeny'){
													bootbox.alert("Data is been manipulated.", function() {
														
															//$('.loader').hide();
															window.location.href = getCustomLogoutPage();
															return;
													});
												}
												
												
												if(json.response == 'serverError'){
													bootbox.alert("Error occured while generating invoice.", function() {
															//$('.loader').hide();
															window.location.href = 'home#invoice';
															return;
													});
												}
									         },
								            error:function(data,status,er) { 
								            	bootbox.alert("Error occured while generating invoice.", function() {
			
														//$('.loader').hide();
														window.location.href = 'home#invoice';
														return;
												});
								             }
										}); 
								    
							    
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
		 shipToAddress = $("#customer_custAddress1").val();
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
	  $("#customerDetailsInPreview").append(
			  	'<div class="col-sm-12 text-center">'
          			+'<h3>'+documentType+'</h3>'
			  		+'<hr>'
			  	+'</div>'
				+'<div class="col-sm-6">'
					+'<div class="invoiceInfo invoiceFirst">'
						+'<h4>Bill To</h4>'
						+'<p><span>Name:</span>'+$("#customer_name").val().split('] - ')[1].trim()+'</p>'
						+'<p><span>Address:</span>'+$("#customer_custAddress1").val()+'</p>'
						+'<p><span>City:</span>'+$("#customer_custCity").val()+'</p>'
						+'<p><span>State:</span>'+$("#customer_custState").val()+' [ '+$("#customer_custStateCodeId").val()+' ]'+'</p>'
						+'<p><span>State Code:</span>'+$("#customer_custStateCode").val()+'</p>'
						+'<p><span>GSTIN/Unique Code:</span>'+$("#customer_custGstId").val()+'</p>'
					+'</div>'
				+'</div>');
	  if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
		  $("#customerDetailsInPreview").append('<div class="col-sm-6">'
					+'<div class="invoiceInfo">'
					+'</div>'
				+'</div>'
		  );	
	  }else{
		  $("#customerDetailsInPreview").append('<div class="col-sm-6">'
					+'<div class="invoiceInfo">'
						+'<h4>Ship To</h4>'
						+'<p><span>Name:</span>'+shipToCustomerName+'</p>'
						+'<p><span>Address:</span>'+shipToAddress+'</p>'
						+'<p><span>City:</span>'+shipToCity+'</p>'
						+'<p><span>State:</span>'+shipToState+' [ '+shipToStateCodeId+' ]'+'</p>'
						+'<p><span>State Code:</span>'+shipToStateCode+'</p>'
						
					+'</div>'
				+'</div>'
		  );
	  }
	  
	  $("#placeOfSupplyPreview").html("");
	  $("#placeOfSupplyPreview").append(
			  '<div class="col-sm-12">'
				+'<div class="invoiceInfo invoiceFirst">Place Of Supply : '
					+ placeOfSupply
					
				+'</div>'
			+'</div>'
			  
	  );
	 
}
	
function showServiceListDetailsInPreviewInvoiceDiv(json){
  
   var documentType = $("#documentType option:selected").text();	
   $("#mytable2Header").html("");
   $("#mytable2Header").text(documentType.toUpperCase());
   
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
	           +'<th class="text-right">Disc(Rs.)</th>'
	           +'<th class="text-right">Total (Rs.) After Disc</th>'
	       +' </tr>'
	    +'</thead>'	 
	);
   
   var amountSubtotal = 0;
   var cessTotalTax = 0;
   var cessAdvolAmt = 0;
   var cessNonAdvolAmt = 0;
   var containsAdditionalCharges = '';
   var containsDiffPercentage = '';
   
   $("#mytable2").append('<tbody>');
   $.each(json.invoiceDetails.serviceList,function(i,value) {
	   var description = (value.isDescriptionChecked == 'Yes') ? (" - "+value.description) : '' ;
	   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
		 $("#mytable2 tbody:last-child").append(
		     '<tr>'
            +'<td>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span>'+description+'</td>'
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
		 cessAdvolAmt = parseFloat(cessAdvolAmt) + parseFloat(value.advolCessAmount);
		 cessNonAdvolAmt = parseFloat(cessNonAdvolAmt) + parseFloat(value.nonAdvolCess);
	});
	   
	   
    $("#mytable2 tbody:last-child").append(
       '<tr>'                 
      	    +'<td>&nbsp;</td>'    	
        	+'<td class="hlmobil"><b>Total Value (A)</b></td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td class="text-right">'+amountSubtotal.toFixed(2)+'</td>'
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
                   	+'<td class="text-right">'+addChargeAmount.toFixed(2)+'</td>'
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
                   	+'<td class="text-right">'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</td>'
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
	  if(parseFloat(cessTotalTax) > 0){
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
	  }
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
				  	  +'<td></td>'
 					  +'<td class="hlmobil"><b>Total Taxes (C)</b></td>'
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
 					+'<td class="hlmobil"><b>Total Taxes (B)</b></td>'
       				+'<td></td>'
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
		  			+'<td><b>Total '+documentType+' Value (A+B+C)</b></td>'
       				+'<td></td>'
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
		  			+'<td><b>Total '+documentType+' Value (A+B)</b></td>'
       				+'<td></td>'
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
	   				+'<td></td>'
	                +'<td class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
	           +'</tr>'
               +'<tr>'
                    +'<td><b>Total '+documentType+' Value (After Round off)</b></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	                +'<td class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
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
			  address = json.gstinAddressMapping.address; 
			  city = json.gstinAddressMapping.city;
			  pincode = json.gstinAddressMapping.pinCode;
			  stateCodeId = json.state;
			  stateToShow = json.gstinAddressMapping.state;
			  
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		  },
		  error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
		  }
	});

	var documentType = $("#documentType option:selected").text();	
	
	$("#userDetailsInPreview").html("");
	$('#userDetailsInPreview').append(
			 '<h4>'+$("#user_org_name").val()+'</h4>'
			  +'<p><span>'+address+' , '+city+' , '+stateToShow+' [ '+stateCodeId+' ]'+' , '+pincode+'</span></p>'
			  +'<p><span>GSTIN:</span>'+$("#gstnStateId option:selected").text()+'</p>'
			  +'<p><span>PAN:</span>'+$("#user_org_panNumber").val()+'</p>'
			 /* +'<p><span>Invoice No:</span></p>'*/
			  +'<p><span>'+documentType+' Date:</span>'+$("#invoice_date").val()+'</p>'
	);
	
	
	if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
		if($("#invoicePeriodFromDateInString").val() == '' || $("#invoicePeriodToDateInString").val() == ''){
			$('#userDetailsInPreview').append( '<p><span>Service Period :</span> N/A  TO  N/A </p>');
		}else{
			$('#userDetailsInPreview').append( '<p><span>Service Period :</span>'+$("#invoicePeriodFromDateInString").val()+' TO '+$("#invoicePeriodToDateInString").val()+'</p>');
		}
		
	}
	
	$('#userDetailsInPreview').append(
			'<p><span>Whether tax is payable on reverse charge : </span>'+$("#reverseChargeYesNo").val()+'</p>'	
	);
	
	$("#userAddressWithDetails").html("");
	/*$("#userAddressWithDetails").append(
			'<span>'+address+' , '+city+' - '+pincode+'</span>'
	);*/
}

function showCustomerPoNoInPreviewInvoiceDiv(){
	$("#preview_customer_purchase_order").html("");
	$("#preview_customer_purchase_order").append('<h1 class="black-bold">Customer Purchase Order : </h1>'+$("#poDetails_poNo").val());
	
	$("#footerNoteDiv").html("");
	$("#footerNoteDiv").append(
		$("#footerNote").val()	
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
	$("#generateInvoicePageHeaderPreview").html("");
	$("#generateInvoicePageHeaderPreview").text("Preview "+documentType);
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
	   if(invoiceFor == "Service"){
		   deliveryPlace = $('select#selectState option:selected').val(); 
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
		     jsonObject.advolCess = $("#cess_advol_rate"+num2).val();
		     jsonObject.nonAdvolCess = $("#cess_non_advol_rate"+num2).val();
		     jsonObject.discountTypeInItem = $("#discountTypeInItem"+num2).val();
		     jsonObject.offerAmount = $("#offerAmount"+num2).val();
		     jsonObject.hsnSacCode = $("#hsnSacCode"+num2).val();
		     jsonObject.diffPercent = $("#diffPercent"+num2).val();
		     jsonObject.isDescriptionChecked = $("#description_checked"+num2).val();
		     jsonObject.description = $("#description"+num2).val();
		     
		     jsonObject.iterationNo = parseInt(1);
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
	   
	   var modeOfCreation = $("#modeOfCreation").val();
	   var locationStoreId = parseInt($("#locationStorePkId").val());
	   var placeOfSupply = $("#posStateName").val()+" [ "+$("#posStateCode").val()+" ]";
	   
	   //Invoice Number
	   var invoiceSequenceType = $("#invoiceSequenceType").val();
	   var invoiceNumber = '';
	   if(invoiceSequenceType != 'Auto'){
		   invoiceNumber = $("#invoiceNumber").val();
	   }
	   
	   
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
			   				"isCustomerRegistered" : isCustomerRegistered,
			   				"modeOfCreation" : modeOfCreation,
			   				"iterationNo" : parseInt(1),
			   				"lastRRType" : "",
			   				"lastRR" : "",
			   				"lastRRInvoiceNumber" : "",
			   				"locationStoreId" : locationStoreId,
			   				"placeOfSupply" : placeOfSupply,
			   				"invoiceNumber" : invoiceNumber
	                   };
	//console.log(inputData);
	
	return inputData;
}

function showBillDetailsInPreviewDiv(){
	var gstinJsonForUserData = fetchGstinDataForUserData($("#gstnStateId option:selected").text());
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
				  +'<strong>GSTIN : </strong>'+$("#gstnStateId option:selected").text()+'<br>'
				  +'<strong>PAN : </strong>'+$("#user_org_panNumber").val()+'<br>'
				  +'<strong>'+documentType+' Date : </strong>'+$("#invoice_date").val()+'<br>'
		);
		
		if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
			if($("#invoicePeriodFromDateInString").val() == '' || $("#invoicePeriodToDateInString").val() == ''){
				$('#userDetailsInPreview').append( '<strong>Invoice Period : </strong>NA<br>');
			}else{
				$('#userDetailsInPreview').append( '<strong>Service Period : </strong>'+$("#invoicePeriodFromDateInString").val()+' TO '+$("#invoicePeriodToDateInString").val()+'<br/>');
			}
			
		}
		
		showReverseChargeValue();
		$('#userDetailsInPreview').append(
				'<strong>Whether tax is payable on reverse charge : </strong>'+$("#reverseChargeYesNo").val()+'</br>'	
		);
		
	}
	
}

function fetchGstinDataForUserData(gstinNoWithState){
	var gstinJsonForUserData = '';
	var gstinNo = gstinNoWithState.split(' [')[0];
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

function showBillToAndShipToInPreviewDiv(){
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
		 shipToAddress = $("#customer_custAddress1").val();
		 shipToCity = $("#customer_custCity").val();
		 shipToState = $("#customer_custState").val();
		 shipToStateCode = $("#customer_custStateCode").val();
		 shipToStateCodeId = $("#customer_custStateCodeId").val();
		 //placeOfSupply = shipToState + "[ "+shipToStateCode+" ]";
		 
	}else{
		 shipToCustomerName = $("#shipTo_customer_name").val();
		 shipToAddress = $("#shipTo_address").val();
		 shipToCity = $("#shipTo_city").val();
		 shipToState = $("#selectState option:selected").text();
		 shipToStateCode = $("#shipTo_stateCode").val();
		 shipToStateCodeId = $("#shipTo_stateCodeId").val();
		 //placeOfSupply = shipToState +"[ "+shipToStateCode+" ]";
	}
	placeOfSupply = $("#posStateName").val()+" [ "+$("#posStateCode").val()+" ]";
	  
  $("#customerDetailsBillToDiv").html("");
  $("#customerDetailsBillToDiv").append(
		  '<strong>Name : </strong>'+$("#customer_name").val().split('] - ')[1].trim()+'<br>'
		  +'<strong>Address : </strong>'+$("#customer_custAddress1").val()+'<br>'
		  +'<strong>City : </strong>'+$("#customer_custCity").val()+'<br>'
		  +'<strong>State : </strong>'+$("#customer_custState").val()+' [ '+$("#customer_custStateCodeId").val()+' ]'+'<br>'
		  +'<strong>State Code : </strong>'+$("#customer_custStateCode").val()+'<br>'
		  +'<strong>GSTIN/Unique Code : </strong>'+$("#customer_custGstId").val()+'<br>'
		  +'<strong>Place Of Supply : </strong>'+ placeOfSupply 
  );
  
  $("#customerDetailsShipToDiv").html("");  
  if($('input[name=invoiceFor]').filter(':checked').val() == 'Product'){
	  $("#customerDetailsShipToDiv").append(
			  '<strong>Name : </strong>'+shipToCustomerName+'<br>'
			  +'<strong>Address : </strong>'+shipToAddress+'<br>'
			  +'<strong>City : </strong>'+shipToCity+'<br>'
			  +'<strong>State : </strong>'+shipToState+' [ '+shipToStateCodeId+' ]'+'<br>'
			  +'<strong>State Code : </strong>'+shipToStateCode+'<br>'
	  );
  }else{
	  $("#customerDetailsShipToDiv").hide();
	  $("#customerDetailsShipToUpperDiv").hide();
  }
	
}

