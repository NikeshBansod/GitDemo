
$(document).ready(function(){
	   $("#directId").click(function(){
		 bootbox.confirm("Do you want to send this invoice to customer ?", function(result){
		 if (result){ 
		   var inputData = {
					"invoiceFor": "Product",
					"customerDetails": {
						"id": "2086",
						"custName": "[7417417410] - Nikesh bhjb  v",
						"custAddress1": "addr1",
						"custAddress2": "",
						"custCity": "THANE",
						"custState": "Maharashtra"
					},
					"servicePlace": "addr1,,THANE",
					"serviceCountry": "Maharashtra",
					"deliveryPlace": "27",
					"deliveryCountry": "India",
					"poDetails": "ADVBCGY jbbs",
					"typeOfExport": "",
					"serviceList": [{
						"serviceId": "922",
						"unitOfMeasurement": "DOZEN",
						"rate": "100",
						"quantity": "21",
						"amount": "1850.00",
						"calculationBasedOn": "Amount",
						"taxAmount": "433.00",
						"serviceIdInString": "Shoe Box",
						"sgstAmount": "166.50",
						"cgstAmount": "166.50",
						"igstAmount": "0.00",
						"ugstAmount": "0.00",
						"previousAmount": "2100",
						"sgstPercentage": "9.00",
						"cgstPercentage": "9.00",
						"igstPercentage": "0.00",
						"ugstPercentage": "0.00",
						"billingFor": "Product",
						"gstnStateId": "27",
						"deliveryStateId": "27",
						"categoryType": "CATEGORY_WITH_SGST_CSGT",
						"cess": "100.00",
						"offerAmount": "250.00"
					}, {
						"serviceId": "490",
						"unitOfMeasurement": "GREAT GROSS",
						"rate": "1234",
						"quantity": "2",
						"amount": "2368.00",
						"calculationBasedOn": "Amount",
						"taxAmount": "675.04",
						"serviceIdInString": "Nikesh Hair Oil",
						"sgstAmount": "331.52",
						"cgstAmount": "331.52",
						"igstAmount": "0.00",
						"ugstAmount": "0.00",
						"previousAmount": "2468",
						"sgstPercentage": "14.00",
						"cgstPercentage": "14.00",
						"igstPercentage": "0.00",
						"ugstPercentage": "0.00",
						"billingFor": "Product",
						"gstnStateId": "27",
						"deliveryStateId": "27",
						"categoryType": "CATEGORY_WITH_SGST_CSGT",
						"cess": "12.00",
						"offerAmount": "100.00"
					}],
					"discountType": "Percentage",
					"discountValue": 0,
					"discountAmount": "4218.00",
					"discountRemarks": "",
					"additionalChargesType": "Percentage",
					"additionalChargesValue": 0,
					"additonalAmount": "4218.00",
					"additionalChargesRemark": "",
					"amountAfterDiscount": "4218.00",
					"totalTax": "1108.04",
					"invoiceValue": "5326.04",
					"gstnStateId": "27",
					"invoiceDateInString": "07-08-2017",
					"gstnStateIdInString": "27QWERT1234HXZX",
					"invoicePeriodFromDateInString": "",
					"invoicePeriodToDateInString": "",
					"billToShipIsChecked": "Yes",
					"shipToCustomerName": "",
					"shipToAddress": "",
					"shipToCity": "",
					"shipToState": "",
					"shipToStateCode": "",
					"shipToStateCodeId": "",
					"location": "A2",
					"customerEmail": "nikesh.bansod@ril.com",
					"ecommerce": "No",
					"ecommerceGstin": "",
					"reverseCharge": "No"
				};
		   
		   $.ajax({
				url : "addGeneratedInvoice",
				method : "post",
				data : JSON.stringify(inputData),
				contentType : "application/json",
				dataType : "json",
				async : false,
				success:function(json){
					if(json.response == 'SUCCESS'){
						bootbox.alert("Invoice Added Successfully with Invoice Id : "+json.InvoiceNumber, function() {
							setTimeout(function(){ location.reload(); }, 1000);
						});
					}
		         },
	            error:function(data,status,er) { 
	                    //alert("error: "+data+" status: "+status+" er:"+er);
	             }
			}); 
		 }//end if result  
	   });//end confirm
	});
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
	  $("#customer_custAddress2").val("");//Customer
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
		   var errFlag5 = checkForServiceLength();
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
		   
		   //alert("errFlag1"+errFlag1+",errFlag2"+errFlag2+",errFlag4"+errFlag4+",errFlag5"+errFlag5+",errFlag6"+errFlag6+",errFlag7"+errFlag7+",errFlag8"+errFlag8);
		   if ((errFlag1) || (errFlag2) || (errFlag4) ||(errFlag5) || (errFlag6) || (errFlag3) || (errFlag8) || (errFlag9) || (errFlag13) ||(errFlag14) ||(errFlag15) || (gstinStateValid) || (errFlag20) || (errFlag21)){
				e.preventDefault();	 
		   }else{
			   errorStatus = false;
		   }
		   
		   
		   if(!errorStatus){
			   $('#loadingmessage').show();
			   //RND - START
			   var generateInputJsonData = getInputFormDataJson();
			   //console.log(generateInputJsonData);
			   $.ajax({
					url : "calculateTaxOnInvoicePreview",
					method : "post",
					data : JSON.stringify(generateInputJsonData),
					contentType : "application/json",
					dataType : "json",
					async : false,
					success:function(json){
						lastAndFinalInvoiceJson = json;
						if(json.renderData != 'accessDeny' ){
							accessAllowedOrNot = 'GRANT_ACCESS';
						}
			         },
		            error:function(data,status,er) { 
		                    //alert("error: "+data+" status: "+status+" er:"+er);
		             }
				}); 
			   //RND - END
			   
			   
			   
			   
			   if(accessAllowedOrNot == 'GRANT_ACCESS'){
				   $("#addInvoiceDiv").hide();
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
				   }, 4000);
			   }else{
				   $("#addInvoiceDiv").hide();
				   bootbox.alert("Data is been manipulated.", function() {
						setTimeout(function(){ 
							//location.reload();
							$('#loadingmessage').hide(); // hide the loading message
							window.location.href = getCustomLogoutPage();
							return;
						}, 1000);
				   });
			   }
			   
			  
		   }
	   });   
	   
	   $("#backToaddInvoiceDiv").click(function(){
		   $("#previewInvoiceDiv").hide();
		   $('#loadingmessage').show();
		   setTimeout(function(){
			   $("#addInvoiceDiv").show();
			   showDefaultPageHeader();
			   $('#loadingmessage').hide(); 
		   }, 3000);
	   }); 
});

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
		 shipToAddress = $("#customer_custAddress1").val()+" , "+$("#customer_custAddress2").val();
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
						+'<p><span>Address:</span>'+$("#customer_custAddress1").val()+' , '+$("#customer_custAddress2").val()+'</p>'
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


	$(document).ready(function(){
	   $("#finalSubmitId").click(function(){
		  var customerEmailAddress = $("#customer_custEmail").val(); 
		  if(customerEmailAddress == ""){
			  openEmailAddressModelBox();
		  }else{
			  
			  bootbox.confirm({
				  message: "I hereby agree and declare that by clicking the 'Send Document' button herein-below, a valid and binding document is generated by me and such clicking constitutes my signature thereto, as if the document is actually signed by me in writing.",
				    buttons: {
				        confirm: {
				            label: 'Send Document',
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
							  $('#loadingmessage').show();
							  setTimeout(function(){
								  //disable the submit button 
								  $('.btn-success').prop("disabled", true);
								  
								   var inputData = getInputFormDataJson();
								   
							   	   console.log(inputData);
							       //alert("inputData : "+inputData);
								    $.ajax({
										url : "addGeneratedInvoice",
										method : "post",
										data : JSON.stringify(inputData),
										contentType : "application/json",
										dataType : "json",
										async : false,
										success:function(json){
											//alert(json);
											if(json.response == 'SUCCESS'){
												//resetCompleteFormValues();
												bootbox.alert("Document generated Successfully with Document Id : "+json.InvoiceNumber, function() {
													/*setTimeout(function(){ */
														//location.reload();
														$('#loadingmessage').hide();
														window.location.href = 'home#invoice';//getHomePage();
														return;
													/*}, 1000);*/
												});
											}
											
											if(json.response == 'accessDeny'){
												bootbox.alert("Data is been manipulated.", function() {
													/*setTimeout(function(){ */
														//location.reload();
														$('#loadingmessage').hide();
														window.location.href = getCustomLogoutPage();
														return;
													/*}, 1000);*/
												});
											}
								         },
							            error:function(data,status,er) { 
							                //alert("error: "+data+" status: "+status+" er:"+er);
							            	
							             }
									}); 
							    
							  }, 1000);
						    
					      }
				    } 
			}); //bootbox.confirm ends
	   }
	}); //#finalSubmitId.click ends    
});
	
	
	function showServiceListDetailsInPreviewInvoiceDiv(json){
		  var isDiffPercentPresent = 0;
		  //RND - Start
		  
		  $("#stable").html("");
		  $("#stable1").html("");
		  $("#stable2").html("");
		  $("#stable3").html("");
		  
		  $("#stable").append(
			      '<ul class="productdet headers">'
	                 +'<li>Description</li>'
	                 +'<li>HSN/SAC Code</li>'
	                 +'<li>Quantity</li>'
	                 +'<li>UOM</li>'
	                 +'<li class="text-right">Rate (Rs.)/UOM</li>'
	                 +'<li class="text-right">Discount (Rs.)</li>'
	                 +'<li class="text-right">Total (Rs.) after discount</li>'
	              +'</ul>'	 
		   );
		  var amountSubtotal = 0;
		  var cessTotalTax = 0;
		  var containsAdditionalCharges = '';
		  var containsDiffPercentage = '';
		   $.each(json.invoiceDetails.serviceList,function(i,value) {
			   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
			  
				 $("#stable").append(
				     '<ul class="productdet">'
                     +'<li>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></li>'
                     +'<li>'+value.hsnSacCode+'</li>'
                     +'<li>'+value.quantity+'</li>'
                     +'<li>'+value.unitOfMeasurement+'</li>'
                     +'<li class="text-right">'+value.rate+'</li>'
                     +'<li class="text-right">'+value.offerAmount+'</li>'
                     +'<li class="text-right">'+(value.previousAmount - value.offerAmount).toFixed(2)+'</li>'
                  +'</ul>'	 
				 );
				 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
				 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
			});
		   
		   
			  $("#stable1").append(
	                    '<ul class="taxtable">'
                     	+'<li>&nbsp;</li>'
                     	+'<li class="hlmobil">Total Value (A)</li>'
                     	+'<li>&nbsp;</li>'
                     	+'<li>'+amountSubtotal.toFixed(2)+'</li>'
                      +'</ul>'
			  );
			  
		  var addChgLength = json.invoiceDetails.addChargesList.length;	 
		  var addChargeAmount = 0;
		  if(addChgLength > 0){
			  containsAdditionalCharges = 'YES';
			  $("#stable1").append(
	                    '<ul class="taxtable">'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li class="hlmobil">Add : Additional Charges</li>'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li>&nbsp;</li>'
	                    +'</ul>'
			  );
			  $.each(json.invoiceDetails.addChargesList,function(i,value) {
				  $("#stable1").append(
		                    '<ul class="taxtable">'
			                   	+'<li>&nbsp;</li>'
			                   	+'<li class="hlmobil">'+value.additionalChargeName+'</li>'
			                   	+'<li>&nbsp;</li>'
			                   	+'<li>'+(value.additionalChargeAmount).toFixed(2)+'</li>'
		                    +'</ul>'
				  ); 
				  addChargeAmount = parseFloat(addChargeAmount) + parseFloat(value.additionalChargeAmount);
			  });
			  $("#stable1").append(
	                    '<ul class="taxtable">'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li class="hlmobil">Total Additional Charges (B)</li>'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li>'+addChargeAmount.toFixed(2)+'</li>'
	                    +'</ul>'
			  );
			  $("#stable1").append(
	                    '<ul class="taxtable">'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li class="hlmobil">Total Value (A+B)</li>'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li>'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</li>'
	                    +'</ul>'
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
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">Central Tax</li>'
                         	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zCentral++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
			  }
			 
			});
		  	var zCentral = 0;
			  $.each(cgstDiffPercentArray, function(k, v) {
				  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
				  if(zCentral == 0){
					  $("#stable2").append(
							  '<ul class="taxtable">'
	                         	+'<li>&nbsp;</li>'
	                         	+'<li class="hlmobil">Central Tax<span style="color:red">*</span></li>'
	                         	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
	                         	+'<li>'+tax+'</li>'
	                          +'</ul>'
					  );
					  zCentral++;
				  }else{
					 
					  $("#stable2").append(
							  '<ul class="taxtable">'
		                          +'<li>&nbsp;</li>'
		                          +'<li class="hidemob">&nbsp;</li>'
		                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
		                          +'<li>'+tax+'</li>'
	                          +'</ul>'  
					  
					  );
				  }
				 
				});
		  //Display CGST - End
		  
		  //Display SGST - Start
		  var zState = 0;
		  $.each(sgstArray, function(k, v) {
			  var tax = ((v*parseFloat(k))/100).toFixed(2);
			  if(zState == 0){
				
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">State Tax</li>'
                         	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zState++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
				  
			  }
			 
			});
		  
		  var zState = 0;
		  $.each(sgstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zState == 0){
				
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">State Tax<span style="color:red">*</span></li>'
                         	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zState++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
				  
			  }
			 
			});
		  //Display SGST - End
		  
		  //Display IGST - Start
		  var zIntegrated = 0;
		  $.each(igstArray, function(k, v) {
			  var tax = ((v*parseFloat(k))/100).toFixed(2);
			  if(zIntegrated == 0){
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">Integrated Tax</li>'
                         	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zIntegrated++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
			  }
			 
			});
		  
		  var zIntegrated = 0;
		  $.each(igstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zIntegrated == 0){
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">Integrated Tax<span style="color:red">*</span></li>'
                         	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zIntegrated++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
			  }
			 
			});
		  //Display IGST - End
		  
		//Display UGST - Start
		  var zUnionT = 0;
		  $.each(ugstArray, function(k, v) {
			  var tax = ((v*parseFloat(k))/100).toFixed(2);
			  if(zUnionT == 0){
				 
				  
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">Union Territory Tax</li>'
                         	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zUnionT++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
			  }
			 
			});
		  
		  var zUnionT = 0;
		  $.each(ugstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zUnionT == 0){
				 
				  
				  $("#stable2").append(
						  '<ul class="taxtable">'
                         	+'<li>&nbsp;</li>'
                         	+'<li class="hlmobil">Union Territory Tax<span style="color:red">*</span></li>'
                         	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                         	+'<li>'+tax+'</li>'
                          +'</ul>'
				  );
				  zUnionT++;
			  }else{
				 
				  $("#stable2").append(
						  '<ul class="taxtable">'
	                          +'<li>&nbsp;</li>'
	                          +'<li class="hidemob">&nbsp;</li>'
	                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
	                          +'<li>'+tax+'</li>'
                          +'</ul>'  
				  
				  );
			  }
			 
			});
		  //Display UGST - End		  
		  
		  //Types of taxes - End
		  
		  $("#stable2").append(
				  
				    '<ul class="taxtable">'
                     	+'<li>&nbsp;</li>'
                     	+'<li class="hlmobil">Cess</li>'
                     	+'<li>&nbsp;</li>'
                     	+'<li>'+cessTotalTax.toFixed(2)+'</li>'
                  +'</ul>'
		  );
		  
		 
		  
		  if(containsAdditionalCharges == 'YES'){
			  $("#stable2").append(
			    		'<ul class="taxtable">'
             			  +'<li>&nbsp;</li>'
	  					  +'<li class="hlmobil">Total Taxes (C)</li>'
	  					  +'<li>&nbsp;</li>'
             			  +'<li>'+(json.invoiceDetails.totalTax).toFixed(2)+'</li>'
             		  +'</ul>'
			  );
		  }else{
			  $("#stable2").append(
			    		'<ul class="taxtable">'
             			  +'<li>&nbsp;</li>'
	  					  +'<li class="hlmobil">Total Taxes (B)</li>'
	  					  +'<li>&nbsp;</li>'
             			  +'<li>'+(json.invoiceDetails.totalTax).toFixed(2)+'</li>'
             		  +'</ul>'
			  );
		  }
		  
		  
		  if(containsAdditionalCharges == 'YES'){
			  $("#stable3").append(
	                    '<ul class="producttotal">'
			  				+'<li>Total Document Value (A+B+C)</li>'
	                        +'<li class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</li>'                                  
	                    +'</ul>'
	          );
		  }else{
			  $("#stable3").append(
	                    '<ul class="producttotal">'
			  				+'<li>Total Document Value (A+B)</li>'
	                        +'<li class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</li>'                                  
	                    +'</ul>'
	          ); 
		  }
		  $("#stable3").append(
				     '<ul class="producttotal">'
	                    +'<li>Round off</li>'
	                    +'<li class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</li>'                                  
	                +'</ul>'
	                +'<ul class="producttotal">'
	                    +'<li>Total Document Value (After Round off)</li>'
	                    +'<li class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</li>'                                  
	                +'</ul>'
                    +'<ul class="producttotal">'
                        +'<li>Total Document value Rs.(in words): </li>'
                        +'<li><strong>'+ json.amtInWords+'</strong></li>'                                   
                    +'</ul>'
		  );
		  
		  
		  
		  
		  //RND - End
		  
		  if(isDiffPercentPresent > 0){
			 $("#diffPercentShowHide").show(); 
		  }else{
			  $("#diffPercentShowHide").hide();  
		  }
		  
		
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
		method : "POST",
		dataType : "json",
		data : { gstinNo : gstinNo},
		async : false,
		  success:function(json) {
			  if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
			  }
			  //alert(json.gstinAddressMapping.address + ", "+json.gstinAddressMapping.pinCode+ ", "+json.gstinAddressMapping.city+ ", "+json.gstinAddressMapping.state);
			  address = json.gstinAddressMapping.address; 
			  city = json.gstinAddressMapping.city;
			  pincode = json.gstinAddressMapping.pinCode;
			  stateCodeId = json.state;
			  stateToShow = json.gstinAddressMapping.state;
		  }
	});
	
	$("#userDetailsInPreview").html("");
	$('#userDetailsInPreview').append(
			 '<h4>'+$("#user_org_name").val()+'</h4>'
			  +'<p><span>'+address+' , '+city+' , '+stateToShow+' [ '+stateCodeId+' ]'+' , '+pincode+'</span></p>'
			  +'<p><span>GSTIN:</span>'+$("#gstnStateId option:selected").text()+'</p>'
			  +'<p><span>PAN:</span>'+$("#user_org_panNumber").val()+'</p>'
			 /* +'<p><span>Invoice No:</span></p>'*/
			  +'<p><span>Document Date:</span>'+$("#invoice_date").val()+'</p>'
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
	$("#preview_customer_purchase_order").append(
			'<div class="col-sm-6">'
				+'<div class="invoiceInfo invoiceFirst">'
				+'<p><span>Customer Purchase Order : </span>'+$("#poDetails_poNo").val()+'</p>'
					       
				+'</div>'
			+'</div>'
			+'<div class="col-sm-6">'
				+'<div class="invoiceInfo">'
					    
				+'</div>'
			+'</div>');
	
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

/*function showAmountInWords(){
	var amtInWord = '';
	$("#amtInWords").html("");
	var totalInvoiceValue = $("#invoiceValue").val();
	if(totalInvoiceValue != ''){
		$.ajax({
			url : "convertAmountInWords",
			method : "POST",
			dataType : "json",
			data : { amtInNumber : totalInvoiceValue},
			async : false,
			  success:function(json) {
				  if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
				  }
				  $("#amtInWords").append(json);
				  amtInWord = json;
			  }
		});
	}
return amtInWord;
	
}*/

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
			   					"custAddress2" : $("#customer_custAddress2").val(),
			   					"custCity" : $("#customer_custCity").val(),
			   					"custState": $("#customer_custState").val()
			   				 };
	   var customerEmail = $("#customer_custEmail").val();
	   var servicePlace = $("#customer_place").val();
	   var serviceCountry = $("#customer_country").val();
	   var deliveryPlace = $('select#selectState option:selected').val();
	   var deliveryCountry = $('select#selectCountry option:selected').val();
	   if(deliveryCountry == '' || deliveryCountry == 'Other'){
		   deliveryPlace = 0;
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
	   if(locationVal == undefined){
		   locationVal = "00";
	   }
	   //get Shipping address - Start 
	   
	 //check if billing address is same as shipping address - start
		var isChecked = $('#shipToBill').is(':checked'); 
		var billToShipIsChecked = 'No';
		var shipToCustomerName = '';
		var shipToAddress = '';
		var shipToCity = '';
		var shipToState = '';
		var shipToStateCode = '';
		var shipToStateCodeId = '';
		if(isChecked == true){
			 billToShipIsChecked = 'Yes';
		}else{
			 shipToCustomerName = $("#shipTo_customer_name").val();
			 shipToAddress = $("#shipTo_address").val();
			 shipToCity = $("#shipTo_city").val();
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
			   				"documentType" : documentType
	                   };
	//console.log(inputData);
	
	return inputData;
}

$(document).ready(function(){
	   $("#dummySubmId").click(function(){
		 var accessAllowedOrNot = '';  
		 bootbox.confirm("Do you want to send this invoice to customer ?", function(result){
		 if (result){ 
		   var inputData = {
					"invoiceFor": "Product",
					"customerDetails": {
						"id": "1476",
						"custName": "[7979797979] - Nikesh Bansod",
						"custAddress1": "Kalyan",
						"custAddress2": "kalyan",
						"custCity": "THANE",
						"custState": "Maharashtra"
					},
					"servicePlace": "Kalyan,kalyan,THANE",
					"serviceCountry": "Maharashtra",
					"deliveryPlace": "27",
					"deliveryCountry": "India",
					"poDetails": "",
					"typeOfExport": "",
					"serviceList": [{
						"serviceId": "486",
						"calculationBasedOn": "Amount",
						"unitOfMeasurement": "BOX",
						"rate": "1200",
						"quantity": "1",
						"serviceIdInString": "organic color",
						"previousAmount": "1200",
						"billingFor": "Product",
						"cess": "14",
						"offerAmount": "100",
						"hsnSacCode": "32041511"
					}, {
						"serviceId": "487",
						"calculationBasedOn": "Amount",
						"unitOfMeasurement": "LITERS",
						"rate": "98",
						"quantity": "25",
						"serviceIdInString": "Oil",
						"previousAmount": "2450",
						"billingFor": "Product",
						"cess": "100",
						"offerAmount": "250",
						"hsnSacCode": "84329090"
					}],
					"discountType": "Percentage",
					"discountValue": 0,
					"discountAmount": 0,
					"discountRemarks": "",
					"additionalChargesType": "Percentage",
					"additionalChargesValue": 0,
					"additonalAmount": 0,
					"additionalChargesRemark": "",
					"amountAfterDiscount": 0,
					"totalTax": 0,
					"invoiceValue": 0,
					"gstnStateId": "27",
					"invoiceDateInString": "28-08-2017",
					"gstnStateIdInString": "27ASDCL1456LAZS",
					"invoicePeriodFromDateInString": "",
					"invoicePeriodToDateInString": "",
					"billToShipIsChecked": "Yes",
					"shipToCustomerName": "",
					"shipToAddress": "",
					"shipToCity": "",
					"shipToState": "",
					"shipToStateCode": "",
					"shipToStateCodeId": "",
					"location": "A2",
					"customerEmail": "nikesh.bansod@ril.com",
					"ecommerce": "No",
					"ecommerceGstin": "",
					"reverseCharge": "Yes",
					"addChargesList": [{
						"additionalChargeId": "21",
						"additionalChargeName": "Parking Charges",
						"additionalChargeAmount": "300"
					}]
				};
		   
		   $.ajax({
				url : "calculateTaxOnInvoicePreview",
				method : "post",
				data : JSON.stringify(inputData),
				contentType : "application/json",
				dataType : "json",
				async : false,
				success:function(json){
					console.log(json);
					if(json.renderData != 'accessDeny' ){
						accessAllowedOrNot = 'GRANT_ACCESS';
					}
		         },
	            error:function(data,status,er) { 
	                    alert("error: "+data+" status: "+status+" er:"+er);
	             }
			}); 
		   
		   if(accessAllowedOrNot == 'GRANT_ACCESS'){
			  alert("VVV");  
		   }else{
			   bootbox.alert("Data is been manipulated.", function() {
					setTimeout(function(){ 
						//location.reload();
						window.location.href = getCustomLogoutPage();
						return;
					}, 1000);
			   });
		   }
		   
		   
		 }//end if result  
	   });//end confirm
	});
});

