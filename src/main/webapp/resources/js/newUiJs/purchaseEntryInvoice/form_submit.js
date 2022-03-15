
$(document).ready(function(){
	   $("#submitId").click(function(e){
		    var accessAllowedOrNot = '';
		    var errorStatus = true;
		    var blankMsg="This field is required";
		    var cmpMsg = "The State of Supplier should be same as GSTIN of the Supplier";
		    
		    var errFlag1 = false;
			var errFlag2 = false; 
			var errFlagGSTINStateId = false;
			var errFlagLocation = false;
			var errFlagGSTINSupplier = false;
			var gstinStateValid = false;
			var errFlagSupplierName = false;
			var errFlagSupplierPincode = false;
			var errFlagSupplierState = false;
			var errFlagPurchaseDate = false;
			var errFlagInvoiceNo = false;
			var errFlagReverceCharge = false;		
			var errFlagSupplierDocInvoiceNo = false;
			var errFlagSupplierDocDate = false;
			var errFlagBillingAddress = false;		
			var errFlagShipAddress = false;		
			var errFlagInvoicePeriod = false;
			var errFlagInvoicePeriodFrom = false;
			var errFlagInvoicePeriodTo = false;
			var errFlagPlaceOfSupply = false;
			var errFlagSupplierGstinANDSupplierState = false;
			
			if($("#invoicePeriodFromDateInString").val().length > 0){
				if($("#invoicePeriodToDateInString").val().length > 0){
			      	  $("#invoicePeriodToDateInString").addClass("input-correct").removeClass("input-error");
			    }else{
			    	errFlagInvoicePeriodTo = true;
			    	$("#invoicePeriodToDateInString").addClass("input-error").removeClass("input-correct");
			    }
				$("#invoicePeriodFromDateInString").addClass("input-correct").removeClass("input-error");
	        }	        
			
			if($("#invoicePeriodToDateInString").val().length > 0){
				if($("#invoicePeriodFromDateInString").val().length > 0){
			      	  $("#invoicePeriodFromDateInString").addClass("input-correct").removeClass("input-error");
			    }else{
			    	errFlagInvoicePeriodFrom = true;
			    	$("#invoicePeriodFromDateInString").addClass("input-error").removeClass("input-correct");
			    }
	      	  	$("#invoicePeriodToDateInString").addClass("input-correct").removeClass("input-error");
	        }
	       
			if(errFlagInvoicePeriodFrom || errFlagInvoicePeriodTo){
				errFlagInvoicePeriod = true;
			}
				
			errFlagGSTINStateId = validateSelect("gstnStateId","gstnStateId-csv-id"); 
			errFlagLocation = validateSelect("location","gstnStateId-csv-id"); 
			
			if ($("#Registered").prop("checked")) {
				errFlagGSTINSupplier = validateSupplierGstin();
				if(!errFlagGSTINSupplier){
					gstinStateValid = validGstinStateCode();
				}
				
				errFlagSupplierGstinANDSupplierState = validateGstinAndStAddrState();
			}

			errFlagSupplierName = validateTextField("supplier_name","shipTo-customer-name-csv-id",blankMsg);
			
			errFlagSupplierPincode = validateTextField("supplier_pincode","shipTo-customer-pincode-csv-id",blankMsg);
			  /*if(!errFlagSupplierPincode){
				  errFlagSupplierPincode = validatePinCodeField("supplier_pincode","shipTo-customer-pincode-csv-id",'Pincode should have 6 digits'); 
			  } */
			
			errFlagSupplierState = validateTextField("supplier_state","shipTo-customer-address-csv-id",blankMsg);
		
			//errFlagPurchaseDate = validateDateField("purchase_date","purchase_date-id");
			errFlagInvoiceNo = validateTextField("invoice_number","invoice_number-csv-id",blankMsg);
			
			errFlagPlaceOfSupply = validateSelect("placeOfSupply","placeOfSupply-csv-id"); 
			
			var isCheckedReverseCharge = $('#reverseCharge').is(':checked');
			if(isCheckedReverseCharge == true){
				errFlagSupplierDocInvoiceNo = validateTextField("supplierDocInvoiceNo","supplierDocInvoiceNo-csv-id",blankMsg);
				errFlagSupplierDocDate = validateDateField("supplierDocInvoiceDate","supplierDocInvoiceDate-id");
				
				if(errFlagSupplierDocInvoiceNo || errFlagSupplierDocDate){
					errFlagReverceCharge = true;					
				}
			}
			
			errFlagBillingAddress = validateTextField("billing_address","billing_address-csv-id",blankMsg);
					
			var isCheckedShippingToBilling = $('#shipToBill').is(':checked');
			if(isCheckedShippingToBilling == true){	
				errFlagShipAddress = validateTextField("shipping_address","shipping_address-csv-id",blankMsg);	
				if ($("#shipping_address").val().length > 1){
					if($("#billing_address").val() === $("#shipping_address").val()){
						$("#billing_address,#shipping_address").addClass("input-correct").removeClass("input-error");
					}else{
						var billingAddr = $("#billing_address").val();
			            $("#shipping_address").val(billingAddr);
					}
				}else{
					$("#shipping_address").addClass("input-error").removeClass("input-correct");
				}		   	  			    		  
		  }
			
		    errFlag1 = checkForServiceLength();
		    errFlag2 = validateSelect("calculation_on","calculation-on-csv-id");		   
		   
		    if(errFlagGSTINStateId){
				$("#gstnStateId").focus();
			}

		    if(errFlagLocation){
				$("#location").focus();
			}
			
			if(errFlagGSTINSupplier || gstinStateValid){
				$("#supplier_gstin").focus();
			}
			
			if(errFlagSupplierName){
				 $("#supplier_name").focus();
			}

			if(errFlagSupplierPincode || errFlagSupplierGstinANDSupplierState){
				 $("#supplier_pincode").focus();
			}		   
			
			if(errFlagPurchaseDate){
				$("#purchase_date").focus();
			}
			
			if(errFlagInvoiceNo){
				$("#invoice_number").focus();
			}

			if(errFlagPlaceOfSupply){
				 $("#placeOfSupply").focus();
			}
			
			if(errFlagBillingAddress){
				 $("#billing_address").focus();
			}

			if(errFlagShipAddress){
				 $("#shipping_address").focus();
			}

			if(errFlagInvoicePeriod){
				if(errFlagInvoicePeriodFrom ){
					$("#invoicePeriodFromDateInString").focus();
				}
				if(errFlagInvoicePeriodTo){
					$("#invoicePeriodToDateInString").focus();
				}
			}
				
		   if((errFlag1) || (errFlag2) || (errFlagGSTINStateId) || (errFlagLocation) || (errFlagGSTINSupplier) || (gstinStateValid) || (errFlagSupplierName) || (errFlagReverceCharge) ||
			  (errFlagSupplierPincode) || (errFlagSupplierState) || (errFlagPurchaseDate) || (errFlagInvoiceNo) || (errFlagSupplierDocInvoiceNo) || (errFlagSupplierDocDate) ||
			  (errFlagBillingAddress) || (errFlagShipAddress) || (errFlagInvoicePeriod) || (errFlagPlaceOfSupply) || (errFlagSupplierGstinANDSupplierState)){
			   
				e.preventDefault();	 
		   }else{
			   errorStatus = false;
		   }
		   		   
		   if(!errorStatus){
			   $('.loader').show();
			   //RND - START
			   var generateInputJsonData = getInputFormDataJson();
			   $.ajax({
					url : "calculateTaxForPurchaseEntryPreview",
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
						if(json.renderData != 'accessDeny' ){							
								   $("#addInvoiceDiv").slideToggle();
								   $("#generateInvoiceDefaultPageHeader").hide();
								   setTimeout(function(){ 
									   $("#previewInvoiceDiv").slideToggle(); 	 
									   showSupplierDetailsPreviewDiv();											//1st div Supplier details
									   showPurchaserDetailsInPreviewDiv()										//2ns div Purchaser details
									   showServiceListDetailsInPreviewInvoiceDiv(lastAndFinalInvoiceJson);		//3rd div Service/Product details
									   showCustomerPoNoInPreviewInvoiceDiv();									//4th div Pruchase order detail
									   showPreviewPageHeader();													//Show/hide header div
									   
									   //showAmountInWords();
									   //$("#customerEmailDiv").hide();
									   $('.loader').fadeOut("slow");
								   }, 2000);
						}else{ 
			            	$("#addInvoiceDiv").slideToggle();
			            	$('.loader').fadeOut("slow");
							   bootbox.alert("Data is been manipulated.", function() {
									setTimeout(function(){ 
										window.location.href = getCustomLogoutPage();
										return;
									}, 1000);
							   });
			             }
			         },
		            error:function(data,status,er) {
		            	getInternalServerErrorPage();  
		            }
				}); 
			   //RND - END	
		   }
	   });   
	   
	   $("#backToaddInvoiceDiv").click(function(){
		   $("#previewInvoiceDiv").slideToggle();
		   $("#generateInvoicePreviewPageHeader").hide();
		   $('.loader').show();
		   setTimeout(function(){
			   $("#addInvoiceDiv").slideToggle();
			   showDefaultPageHeader();
			   $('.loader').fadeOut("slow");
		   }, 500);
	   }); 
	   
	   $("#finalSubmitId").click(function(){
			 // var customerEmailAddress = $("#customer_custEmail").val(); 
			 // if(customerEmailAddress == ""){
			//	  openEmailAddressModelBox();
			 // }else{
				  
				  bootbox.confirm({
					  message: "Do you want to Save?",
					    buttons: {
					        confirm: {
					            label: 'Save',
					            className: 'btn-success'
					        },
					        cancel: {
					            label: 'Cancel',
					            className: 'btn-danger'
					        }
					    },
					    callback: function (result) {
						   if (result){ 
								  $("#previewInvoiceDiv").slideToggle();
								 // $("#customerEmailDiv").hide();
								  $("#generateInvoicePreviewPageHeader").hide();
								  $('.loader').show();
								  setTimeout(function(){ 
									  //disable the submit button 
									  $('#finalSubmitId').prop("disabled", true);
									  
									   var inputData = getInputFormDataJson();
									   
									    $.ajax({
											url : "addGeneratedPurchaseEntry",
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
												
												if(json.response == 'SUCCESS'){															
													$('.loader').fadeOut("slow");											
													bootbox.alert("Purchase Entry generated Successfully with Document Id : "+json.InvoiceNumber, function() {	
														window.location.href = 'home#doc_management';//getHomePage();
														return;														
													});
												}
												
												if(json.response == 'accessDeny'){
													$('.loader').fadeOut("slow");
													bootbox.alert("Data is been manipulated.", function() {
														window.location.href = getCustomLogoutPage();
														return;
													});
												}
												if(json.response == 'serverError'){
													$('.loader').fadeOut("slow");
													bootbox.alert("Error occured while generating invoice.", function() {
														window.location.href = 'home#doc_management';
														return;
													});
												}
									         },
								            error:function(data,status,er) { 
								            	$('.loader').fadeOut("slow");
								            	bootbox.alert("Error occured while generating invoice.", function() {	
													window.location.href = 'home#doc_management';
													return;
								            	});
								            }
										}); 						    
								  }, 1000);					    
						      }
					    } 
				}); //bootbox.confirm ends
		   //}
		}); //#finalSubmitId.click ends   
});

function validateGstinAndStAddrState(){		
	errFlagSupplierGstinANDSupplierState = compareTextFields("supplier_stateCodeId","supplierGstnNumber","supplierState-req",cmpMsg);
	return errFlagSupplierGstinANDSupplierState;
}

function getInputFormDataJson(){	
	// get the form values
	   var gstnStateId = $('select#gstnStateId option:selected').val();
	   var gstnWithStateInString = $("#gstnStateId option:selected").text();
	   var gstnStateIdInString = (gstnWithStateInString.split('[')[0]).trim();
	   var locationVal = $('select#location option:selected').val();
	   if(locationVal == undefined){
		   locationVal = "00";
	   }
	   var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val();
	   var checkdealerType =  $("#supplier_dealertye").val();
	   
	   if(checkdealerType == 'Individual' ){
		   var dealerType ='UnRegistered';
	   }else{
		   var dealerType ='Registered';
	   }
	   var supplierGstin = $("#supplier_gstin").val();
	   var supplierName = $("#supplier_name").val();
	   var supplierPincode = $("#supplier_pincode").val();
	   var supplierState = $("#supplier_state").val();
	   var supplierStateCode = $("#supplier_stateCode").val();
	   var supplierStateCodeId = $("#supplier_stateCodeId").val();   
	   var supplierAddress = $("#supplier_address").val();
	   
	   var purchaseDateInString = $("#invoice_date").val();
	   var invoiceNumber = $("#invoice_number").val();
	   var invoicePeriodFromDateInString = $("#invoicePeriodFromDateInString").val();
	   var invoicePeriodToDateInString = $("#invoicePeriodToDateInString").val();

	    var placeOfSupplyName = $("#placeOfSupply_Name").val();
		var placeOfSupplyId = $("#placeOfSupply_Id").val();
		var placeOfSupplyCode = $("#placeOfSupply_Code").val();
	   
	   var isReverseChargeChecked = $("#reverseCharge").is(':checked');
	   var reverseChargeApplicable = 'No';
	   var supplierDocInvoiceNo = null;
	   var supplierDocInvoiceDateInString = null;
	   if(isReverseChargeChecked == true){
		   reverseChargeApplicable = 'Yes';
		   supplierDocInvoiceNo = $("#supplierDocInvoiceNo").val();
		   supplierDocInvoiceDateInString = $("#supplierDocInvoiceDate").val();
	   }
		
	   //get Shipping address - Start && check if billing address is same as shipping address - start
	   var billingAddress = $("#billing_address").val();
	   var isChecked = $('#shipToBill').is(':checked'); 
	   var billToShipIsChecked = 'No';
	   
	   if(isChecked == true){
		  billToShipIsChecked = 'Yes';
		  var purchaserShippingAddress = $("#purchaser_org_address1").val();
	   }else{
		   var purchaserShippingAddress = $("#shipTo_address").val()+" - "+$("#shipTo_pincode").val()+","+$("#shipTo_city").val()
	   }
	   //get Shipping address - End 
	   
	   var poDetails = $("#poDetails_poNo").val();
	   var amountAfterDiscount = 0;
	   var totalTax = 0;
	   var invoiceValue = 0;
	   
	   //get services from list in javascript - Start 
	   var $toggle = $("#toggle");
	   var totalRecordNo = $toggle.children().length;
	   var jsonObject;
	   var serviceListArray = new Array();
	   for (i = 0; i < totalRecordNo; i++) { 		  
			 //Start
			 var index2 = $toggle.children()[i].id.lastIndexOf("_");
			 var num2 = $toggle.children()[i].id.substring(index2);
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
			 num2 = num2.replace("_","-");
			 jsonObjectAC = new Object();
			 jsonObjectAC.additionalChargeId = $("#additionalChargeId"+num2).val();
			 jsonObjectAC.additionalChargeName = $("#additionalChargeName"+num2).val();
			 jsonObjectAC.additionalChargeAmount = $("#additionalChargeAmount"+num2).val();
			 acListArray.push(jsonObjectAC);
			//End
	   }	   
	  //get add charges from list in javascript - End 
	   
	   var footerNote = $("#footerNote").val();
	   
	   var inputData = {
 				"gstnStateId" : gstnStateId,
  				"gstnStateIdInString" : gstnStateIdInString,
  				"location" : locationVal,
  				"invoiceFor" : invoiceFor,
  				"dealerType" : dealerType,
  				"supplierGstin" : supplierGstin,
  				"supplierName" : supplierName,
  				"supplierPincode" : supplierPincode,
  				"supplierState" : supplierState,
  				"supplierStateCode" : supplierStateCode,
  				"supplierStateCodeId" : supplierStateCodeId,
  				"supplierAddress" : supplierAddress,
  				"purchaseDateInString" : purchaseDateInString,
  				"invoiceNumber" : invoiceNumber,
  				"invoicePeriodFromDateInString" : invoicePeriodFromDateInString,
  				"invoicePeriodToDateInString" : invoicePeriodToDateInString,
  				"reverseChargeApplicable" : reverseChargeApplicable,
  				"supplierDocInvoiceNo" : supplierDocInvoiceNo,
  				"supplierDocInvoiceDateInString" : supplierDocInvoiceDateInString,
  				"billingAddress" : billingAddress,
  				"billToShipIsChecked" : billToShipIsChecked,
  				"purchaserShippingAddress" : purchaserShippingAddress,  				
  				"poDetails" : poDetails,
  				"amountAfterDiscount" : amountAfterDiscount,
  				"totalTax" : totalTax,
  				"invoiceValue" : invoiceValue,
  				"serviceList" : JSON.parse(JSON.stringify(serviceListArray)),  				
  				"addChargesList" : JSON.parse(JSON.stringify(acListArray)),
  				"footerNote" : footerNote,
  				"placeOfSupplyId" : placeOfSupplyId,
  				"placeOfSupplyName" : placeOfSupplyName,
  				"placeOfSupplyCode" : placeOfSupplyCode
          };
	
	   /* var discountType = $('input[name=discountType]').filter(':checked').val();
	   var discountValue = 0;
	   var discountAmount = 0;
	   var discountRemarks = $("#discountRemarks").val();
	   var additionalChargesType = $('input[name=additionalChargesType]').filter(':checked').val();
	   var additionalChargesValue = 0;
	   var additonalAmount = 0;
	   var additionalChargesRemark = $("#additionalChargesRemark").val();*/
	return inputData;
}


function resetCompleteFormValues(){
	  $("#addInvoiceDiv").show(); 
	  $("#previewInvoiceDiv").slideToggle();  
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


function showSupplierDetailsPreviewDiv(){//userdetails block
	//var dealerType = $('input[name=custType]').filter(':checked').val();	
	var supplierGstin = $("#supplier_gstin").val();
	 var checkdealerType =  $("#supplier_dealertye").val();
	   
	   if(checkdealerType == 'Individual' ){
		   var dealerType ='UnRegistered';
		   supplierGstin = 'NA';
	   }else{
		   var dealerType ='Registered';
	   }
	
	var supplierName = $("#supplier_name").val();
	var supplierPincode = $("#supplier_pincode").val();
	var supplierState = $("#supplier_state").val();
	var supplierStateCode = $("#supplier_stateCode").val();
	var supplierStateCodeId = $("#supplier_stateCodeId").val();   
	var supplierAddress = $("#supplier_address").val();
	
	var completeAddress = null;
	if(supplierAddress === "" ){
		completeAddress = supplierState+'[ '+supplierStateCodeId+' ]'+', '+supplierPincode;
	}else{
		completeAddress = supplierAddress+' , '+supplierState+' [ '+supplierStateCodeId+' ]'+' , '+supplierPincode;
	}
	
	var purchaseDateInString = $("#invoice_date").val();
	var invoiceNumber = $("#invoice_number").val();
	if(invoiceNumber === "" ){
		invoiceNumber = 'NA';
	}
	var invoicePeriodFromDateInString = $("#invoicePeriodFromDateInString").val();
	var invoicePeriodToDateInString = $("#invoicePeriodToDateInString").val();
	var isReverseChargeChecked = $("#reverseCharge").is(':checked');
	var reverseChargeApplicable = 'No';
	var supplierDocInvoiceNo = null;
	var supplierDocInvoiceDateInString = null;
	if(isReverseChargeChecked == true){
		reverseChargeApplicable = 'Yes';
		supplierDocInvoiceNo = $("#supplierDocInvoiceNo").val();
		supplierDocInvoiceDateInString = $("#supplierDocInvoiceDate").val();
	}
	$("#reverseChargeYesNo").val(reverseChargeApplicable);
		 
	$("#userDetailsInPreview").html("");
	$('#userDetailsInPreview').append(
			 '<strong>'+supplierName+'</strong><br>'
			  +completeAddress+'<br>'
			  +'<strong>GSTIN :</strong>'+supplierGstin+'<br>'
			  +'<strong>Purchase Date : </strong>'+purchaseDateInString+'<br>'
			  +'<strong>Invoice Number : </strong>'+invoiceNumber+'<br>'
	);			
	if(invoicePeriodFromDateInString === ""  && invoicePeriodToDateInString === "" ){
		$('#userDetailsInPreview').append(
				'<strong>Invoice Period : </strong>NA<br>'
		);
	}else{
		$('#userDetailsInPreview').append(
				'<strong>Invoice Period : </strong>'+invoicePeriodFromDateInString+' TO '+invoicePeriodToDateInString+'<br>'
		);
	}
	
	$('#userDetailsInPreview').append(
			'<strong>Reverse Charge Applicable : </strong>'+reverseChargeApplicable+'<br>'	
	);
	
	if(isReverseChargeChecked == true){
		$('#userDetailsInPreview').append(
				'<strong>Supplier Document/Invoice No : </strong>'+supplierDocInvoiceNo+'<br>'	
				+'<strong>Supplier Document/Invoice Date : </strong>'+supplierDocInvoiceDateInString	
		);
	}
}


function showPurchaserDetailsInPreviewDiv(){
	var firmName = $("#purchaser_org_name").val();
	var address = $("#purchaser_org_address1").val();
	var city = $("#purchaser_org_city").val();
	var pincode = $("#purchaser_org_pinCode").val();
	var stateCodeId = $("#purchaser_org_stateCode").val();
	var stateToShow = $("#purchaser_org_state").val();
	var gstnWithStateInString = $("#gstnStateId option:selected").text();
	var gstnNo = gstnWithStateInString.split(' [')[0];
	
	var placeOfSupply = '';
	var placeOfSupplyName = $("#placeOfSupply_Name").val();
	var placeOfSupplyId = $("#placeOfSupply_Id").val();
	var placeOfSupplyCode = $("#placeOfSupply_Code").val();
	
	var billingAddress = $("#billing_address").val();
	var isChecked = $('#shipToBill').is(':checked'); 
	var billToShipIsChecked = 'No';
	
	if(isChecked == true){
	  billToShipIsChecked = 'Yes';
	  var purchaserShippingAddress = $("#purchaser_org_address1").val();
    }else{
		 shipToCustomerName = $("#shipTo_customer_name").val();
		 shipToAddress = $("#shipTo_address").val();
		 shipToCity = $("#shipTo_city").val();
		 shipToPincode = $("#shipTo_pincode").val();
		 shipToState = $("#selectState option:selected").text();
		 shipToStateCode = $("#shipTo_stateCode").val();
		 shipToStateCodeId = $("#shipTo_stateCodeId").val();
		 var purchaserShippingAddress = $("#shipTo_address").val()+" - "+$("#shipTo_pincode").val()+","+$("#shipTo_city").val();
	}
	
	 //placeOfSupply = stateToShow +'[ '+stateCodeId+' ]';
	placeOfSupply = placeOfSupplyName+'[ '+placeOfSupplyCode+' ]';

	  $("#customerDetailsBillToDiv").html("");
	  $("#customerDetailsBillToDiv").append(
			  '<strong>Name : </strong>'+firmName+'<br>'
			  +'<strong>Address : </strong>'+address+'<br>'
			  +'<strong>City : </strong>'+city+'<br>'
			  +'<strong>State : </strong>'+stateToShow+'<br>'
			  +'<strong>State Code : </strong>'+stateCodeId+'<br>'
			  +'<strong>GSTIN/Unique Code : </strong>'+gstnNo+'<br>'
			  +'<strong>Place Of Supply : </strong>'+ placeOfSupply
	  );
	  	  
	  //if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
	  $("#customerDetailsShipToDiv").html("");
	  if(billToShipIsChecked == 'Yes'){
		  $("#customerDetailsShipToDiv").append(
			  '<strong>Name : </strong>'+firmName+'<br>'
              +'<strong>Address : </strong>'+purchaserShippingAddress+'<br>'
		  );	
	  }else{
		  $("#customerDetailsShipToDiv").append(
			  '<strong>Name : </strong>'+firmName+'<br>'
              +'<strong>Address : </strong>'+purchaserShippingAddress+'<br>'
				/*+'<strong>City:</strong>'+city+'<br>'
				+'<strong>State:</strong>'+stateToShow+'<br>'
				+'<strong>State Code:</strong>'+stateCodeId+'<br>'*/
		  );
	  }	 
}	
	

function showServiceListDetailsInPreviewInvoiceDiv(json){	  
	
	  var amountSubtotal = 0;
	  var cessTotalTax = 0;
	  var containsAdditionalCharges = '';
	  $("#mytable2 tbody:last-child").empty();
	   $.each(json.purchaseEntryDetails.serviceList,function(i,value) {
			 $("#mytable2 tbody:last-child").append(
				'<tr>'
	                 +'<td>'+value.serviceIdInString+'</td>'
	                 +'<td>'+value.hsnSacCode+'</td>'
	                 +'<td>'+value.quantity+'</td>'
	                 +'<td>'+value.unitOfMeasurement+'</td>'
	                 +'<td>'+value.rate+'</td>'
                     +'<td>'+value.offerAmount+'</td>'
                     +'<td>'+(value.previousAmount - value.offerAmount).toFixed(2)+'</td>'
               +'</tr>'	 
			 );
			 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
			 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
		});		   
	   
		  $("#mytable2 tbody:last-child").append(
               '<tr>'                 
               		+'<td>&nbsp; &nbsp;</td>'    	
                 	+'<td><strong>Total Value (A)</strong></td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>'+amountSubtotal.toFixed(2)+'</td>'
               +'</tr>'
		  );
		  
	  var addChgLength = json.purchaseEntryDetails.addChargesList.length;	 
	  var addChargeAmount = 0;
	  if(addChgLength > 0){
		  containsAdditionalCharges = 'YES';
		  $("#mytable2 tbody:last-child").append(
              '<tr>'
                 	+'<td>&nbsp;</td>'
                 	+'<td><strong>Add : Additional Charges</strong></td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
                 	+'<td>&nbsp; &nbsp;</td>'
              +'</tr>'
		  );
		  $.each(json.purchaseEntryDetails.addChargesList,function(i,value) {
			  $("#mytable2 tbody:last-child").append(
	                    '<tr>'
					  		+'<td>&nbsp; &nbsp;</td>'
					  		+'<td>'+value.additionalChargeName+'</td>'
		                   	+'<td>&nbsp; &nbsp;</td>'
		                   	+'<td>&nbsp; &nbsp;</td>'
		                   	+'<td>&nbsp; &nbsp;</td>'
		                   	+'<td>&nbsp; &nbsp;</td>'
		                   	+'<td>'+(value.additionalChargeAmount).toFixed(2)+'</td>'
	                    +'</tr>'
			  ); 
			  addChargeAmount = parseFloat(addChargeAmount) + parseFloat(value.additionalChargeAmount);
		  });
		  
		  $("#mytable2 tbody:last-child").append(
                '<tr>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td><strong>Total Additional Charges (B)</strong></td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>'+addChargeAmount.toFixed(2)+'</td>'
                +'</tr>'
		  );
		  $("#mytable2 tbody:last-child").append(
                '<tr>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td><strong>Total Value (A+B)</strong></td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>&nbsp; &nbsp;</td>'
	                   	+'<td>'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</td>'
                +'</tr>'
		  );
	  }		  
	  
	  //Types of taxes - Start
	  
	  var cgstArray = {};
	  var sgstArray = {};
	  var ugstArray = {};
	  var igstArray = {};
	  $.each(json.purchaseEntryDetails.serviceList,function(i,value) {
			 if((value.categoryType == 'CATEGORY_WITH_SGST_CSGT')|| (value.categoryType == 'CATEGORY_WITH_UGST_CGST')){
				 if(cgstArray[value.cgstPercentage]){
					 cgstArray[value.cgstPercentage] = parseFloat(cgstArray[value.cgstPercentage]) + parseFloat(value.amountAfterDiscount);
				 }else{
					 cgstArray[value.cgstPercentage] = parseFloat(value.amountAfterDiscount);
				 }
			 }
			 
			 if(value.categoryType == 'CATEGORY_WITH_SGST_CSGT'){
				 if(sgstArray[value.sgstPercentage]){
					 sgstArray[value.sgstPercentage] = parseFloat(sgstArray[value.sgstPercentage]) + parseFloat(value.amountAfterDiscount);
				 }else{
					 sgstArray[value.sgstPercentage] = parseFloat(value.amountAfterDiscount);
				 }
			 }
			 
			 if((value.categoryType == 'CATEGORY_WITH_IGST') || (value.categoryType == 'CATEGORY_EXPORT_WITH_IGST')){
				 if(igstArray[value.igstPercentage]){
					 igstArray[value.igstPercentage] = parseFloat(igstArray[value.igstPercentage]) + parseFloat(value.amountAfterDiscount);
				 }else{
					 igstArray[value.igstPercentage] = parseFloat(value.amountAfterDiscount);
				 }
			 }
			 
			 if(value.categoryType == 'CATEGORY_WITH_UGST_CGST'){
				 if(ugstArray[value.ugstPercentage]){
					 ugstArray[value.ugstPercentage] = parseFloat(ugstArray[value.ugstPercentage]) + parseFloat(value.amountAfterDiscount);
				 }else{
					 ugstArray[value.ugstPercentage] = parseFloat(value.amountAfterDiscount);
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
	                   	+'<td><strong>Central Tax</strong></td>'
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
	                      +'<td></td>'
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
                   	+'<td><strong>State Tax</strong></td>'
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
                        +'<td></td>'
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
                   	+'<td><strong>Integrated Tax</strong></td>'
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
                    	+'<td></td>'
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
                   	+'<td><strong>Union Territory Tax</strong></td>'
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
                    	+'<td></td>'
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
               	+'<td><strong>Cess</strong></td>'
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
            		+'<td><strong>Total Taxes (C)</strong></td>'
            		+'<td></td>'
            		+'<td></td>'
            		+'<td></td>'
            		+'<td></td>'
            		+'<td>'+(json.purchaseEntryDetails.totalTax).toFixed(2)+'</td>'
       		  +'</tr>'
		  );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
				  	+'<td></td>'
				  	+'<td><strong>Total Taxes (B)</strong></td>'
				  	+'<td></td>'
				  	+'<td></td>'
				  	+'<td></td>'
				  	+'<td></td>'
				  	+'<td>'+(json.purchaseEntryDetails.totalTax).toFixed(2)+'</td>'
       		  +'</tr>'
		  );
	  }		  
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  			+'<td><strong>Total Document Value (A+B+C)</strong></td>'
      				+'<td></td>'
      				+'<td></td>'
      				+'<td></td>'
      				+'<td></td>'
      				+'<td></td>'
                    +'<td>'+(json.purchaseEntryDetails.invoiceValue).toFixed(2)+'</td>'                                  
                  +'</tr>'
        );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				 '<tr>'
		  			+'<td><strong>Total Document Value (A+B)</strong></td>'
      				+'<td></td>'
      				+'<td></td>'
      				+'<td></td>'
      				+'<td></td>'
      				+'<td></td>'
                    +'<td>'+(json.purchaseEntryDetails.invoiceValue).toFixed(2)+'</td>'                                  
                 +'</tr>'
        ); 
	  }
	  
	  $("#mytable2 tbody:last-child").append(
			  '<tr>'
                +'<td><strong>Round off</strong></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
                +'<td>'+(json.purchaseEntryDetails.invoiceValueAfterRoundOff - json.purchaseEntryDetails.invoiceValue).toFixed(2)+'</td>'                                  
              +'</tr>'
              +'<tr>'
                +'<td><strong>Total Document Value (After Round off)</strong></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
                +'<td>'+(json.purchaseEntryDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
              +'</tr>'
              +'<tr>'
                +'<td><strong>Total Document value Rs.(in words): </strong></td>'
                +'<td><strong>'+ json.amtInWords+'</strong></td>'  
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'
  				+'<td></td>'                                 
              +'</tr>'
	  );
}

	
function addTotal(subtotal,totalTax){
	var finalSubTotal = 0;
	finalSubTotal = parseFloat(subtotal) + parseFloat(totalTax);
	return finalSubTotal.toFixed(2);	
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
	//$("#generateInvoicePreviewPageHeader").show();
	//$("#generateInvoiceCustomerEmailPageHeader").css("display","none");
}

function showDefaultPageHeader(){
	//$("#generateInvoicePreviewPageHeader").css("display","none");
	$("#generateInvoiceDefaultPageHeader").show();
	//$("#generateInvoiceCustomerEmailPageHeader").css("display","none");
}

function callInvoicePageOnBackButton(){
	 $("#previewInvoiceDiv").slideToggle();
	 $("#addInvoiceDiv").show();
	 showDefaultPageHeader();
}

/*function openEmailAddressModelBox(){
	$("#previewInvoiceDiv").hide();
	$("#addInvoiceDiv").hide();
	showCustomerEmailPageHeader();
	$("#customerEmailDiv").show();
	
}

function showCustomerEmailPageHeader(){
	$("#generateInvoicePreviewPageHeader").css("display","none");
	$("#generateInvoiceDefaultPageHeader").css("display","none");
	$("#generateInvoiceCustomerEmailPageHeader").css("display","block");
}*/

