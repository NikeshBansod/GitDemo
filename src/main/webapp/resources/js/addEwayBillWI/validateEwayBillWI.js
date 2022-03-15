var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
var blankMsg="This field is required";	
var regMsg = "data should be in proper format";
var cmpMsg = "Pincode does not belong to the mentioned GSTIN's state";
var gstinStateErrMsg = "Invalid State code for GSTIN entered";
var gstinRegistered = false;
var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;


	$("#transctionType").change(function(){
		 if ($("#transctionType").val() === ""){
			 $("#transctionType").css({"border-color" : "#ff0000"});
		 }
		 if ($("#transctionType").val() !== ""){
			 $("#transctionType").css({"border-color" : "#498648"});
		 } 
	});

	$("#subType").change(function(){
		 if ($("#subType").val() === ""){
			 $("#subType").css({"border-color" : "#ff0000"});
		 }
		 if ($("#subType").val() !== ""){
			 $("#subType").css({"border-color" : "#498648"});
		 } 		 
	});

	$("#otherSubTypes").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		 
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		if($("#otherSubTypes").val().length >= 1){
			 $("#otherSubTypes").css({"border-color" : "#498648"});			 
		 }
		if($("#otherSubTypes").val().length < 1){
			 $("#otherSubTypes").css({"border-color" : "#ff0000"});
		}		
	});

	$("#documentType").change(function(){
		 if ($("#documentType").val() === ""){
			 $("#documentType").css({"border-color" : "#ff0000"});
		 }
		 if ($("#documentType").val() !== ""){
			 $("#documentType").css({"border-color" : "#498648"});
		 } 
	});
	
	$("#documentNo").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		if($("#documentNo").val().length >= 1){
			 $("#documentNo").css({"border-color" : "#498648"});
			 $("#documentNo-req").hide();			 
		 }
		if($("#documentNo").val().length < 1){
			 $("#documentNo").css({"border-color" : "#ff0000"});
			 $("#documentNo-req").text('This field is required.');			 
			 $("#documentNo-req").show();
		}		
	});

	$("#billFromGstin").on("keyup input",function(e){
		if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.toUpperCase();
		this.value = this.value.replace(/^00/, '');
		
		var transTypeOptionValue = $('select#transctionType option:selected').val();
		var subTypeOptionValue = $('select#subType option:selected').val();
		var radioValue = $("input[name='buyerSellerType']:checked").val();
		if(radioValue == undefined && transTypeOptionValue == "I"){
			$("#billFromGstin").val('');
			$("#buyerSellerType-req").text('This field is required');
			$("#buyerSellerType-req").show();
			if(subTypeOptionValue == ''){
				focusTextBox("subType");
				$("#subType").css({"border-color" : "#ff0000"});
			}
		}
		
		if ($("#billFromGstin").val() === ""){
			$("#billFromGstin").css({"border-color" : "#ff0000"});
	    	$("#billFromGstin-back-req").text('This field is required and should be in a proper format.');
			$("#billFromGstin-back-req").show();
		} 
		if(GstinNumRegex.test(this.value) === true){				
			 $("#billFromGstin").css({"border-color" : "#498648"});
			 $("#billFromGstin-back-req").hide();
			 BillFromDataLoad(this.value);
		}
		if(GstinNumRegex.test(this.value) !== true){
			 $("#billFromGstin").css({"border-color" : "#ff0000"});
		     $("#billFromGstin-back-req").text('This field is required and should be in a proper format.');
			 $("#billFromGstin-back-req").show();
			 $("#billFromGstin").focus();
		}
	}); 
  
	$("#billFromName").on("keyup input",function(e){
		if(e.keyCode == 32){
			his.value = removeWhiteSpace(this.value);
		}		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		if ($("#billFromName").val().length >= 1){
			 $("#billFromName").css({"border-color" : "#498648"});
			 $("#billFromName-req").hide();			 
		}
		if ($("#billFromName").val().length < 1){
			 $("#billFromName").css({"border-color" : "#ff0000"});
			 $("#billFromName-req").show();
		}
	});

	 $("#dispatchFromAddress").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		 
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		 if ($("#dispatchFromAddress").val().length >= 1 ){
			 $("#dispatchFromAddress").css({"border-color" : "#498648"});
			 $("#dispatchFromAddress-req").hide();			 
		 }
		 if ($("#dispatchFromAddress").val().length < 1 ){
			 $("#dispatchFromAddress").css({"border-color" : "#ff0000"});
		     $("#dispatchFromAddress-req").text('This field is required.');
			 $("#dispatchFromAddress-req").show();			 
		 }
	 });

	$("#dispatchFromPincode").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var pincodeRegex = /[0-9]{6}/;
		if(pincodeRegex.test($("#dispatchFromPincode").val()) !== true){			
			this.value = this.value.replace(/[^0-9]+/, '');
		}
		if(pincodeRegex.test($("#dispatchFromPincode").val()) === true){
			$("#dispatchFromPincode").css({"border-color" : "#498648"});
			if($("#dispatchFromPincode").val().length==6){
				loadPincodeNState("dispatchFromPincode",$("#dispatchFromPincode").val(),"dispatchFromState","dispatchFromStateId","dispatchFromStateValue","dispatchFromPincode-back-req");

				if($("#billFromGstin").val() == 'URP'){
					$("#billFromStateId").val($("#dispatchFromStateId").val());
					$("#billFromStateValue").val($("#dispatchFromStateValue").val());
					resetItemDetails();
				}
			}
		}
		if(pincodeRegex.test($("#dispatchFromPincode").val()) !== true){
			$("#dispatchFromPincode").css({"border-color" : "#ff0000"});
			$("#dispatchFromState").val('');
			if($("#billFromGstin").val() == 'URP'){
				$("#billFromStateId").val('');
				$("#billFromStateValue").val('');
			}
		}
	});

	$("#dispatchFromPlace").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		 
		 this.value = this.value.replace(/[\\[]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		 if ($("#dispatchFromPlace").val().length >= 1 ){
			 $("#dispatchFromPlace").css({"border-color" : "#498648"});
			 $("#dispatchFromPlace-req").hide();			 
		 }
		 if ($("#dispatchFromPlace").val().length < 1 ){
			 $("#dispatchFromPlace").css({"border-color" : "#ff0000"});
		     $("#dispatchFromPlace-req").text('This field is required.');
			 $("#dispatchFromPlace-req").show();			 
		 }
	});

	$("#billToGstin").on("keyup input",function(e){
		if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.toUpperCase();
		this.value = this.value.replace(/^00/, '');
		
		var transTypeOptionValue = $('select#transctionType option:selected').val();
		var subTypeOptionValue = $('select#subType option:selected').val();
		var radioValue = $("input[name='buyerSellerType']:checked").val();
		if(radioValue == undefined && transTypeOptionValue == "O"){
			$("#billToGstin").val('');
			$("#buyerSellerType-req").text('This field is required');
			$("#buyerSellerType-req").show();
			if(subTypeOptionValue == ''){
				focusTextBox("subType");
				$("#subType").css({"border-color" : "#ff0000"});
			}
		}
		
		if ($("#billToGstin").val() === ""){
			$("#billToGstin").css({"border-color" : "#ff0000"});
	    	$("#billToGstin-back-req").text('This field is required and should be in a proper format.');
			$("#billToGstin-back-req").show();
		} 
		if(GstinNumRegex.test(this.value) === true){				
			 $("#billToGstin").css({"border-color" : "#498648"});
			 $("#billToGstin-back-req").hide();
			 BillToDataLoad(this.value);
		}
		if(GstinNumRegex.test(this.value) !== true){
			 $("#billToGstin").css({"border-color" : "#ff0000"});
		     $("#billToGstin-back-req").text('This field is required and should be in a proper format.');
			 $("#billToGstin-back-req").show();
		}
	}); 

	$("#billToName").on("keyup input",function(e){
		if(e.keyCode == 32){
			his.value = removeWhiteSpace(this.value);
		}		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		if ($("#billToName").val().length > 1){
			 $("#billToName").css({"border-color" : "#498648"});
			 $("#billToName-req").hide();			 
		}
		if ($("#billToName").val().length < 1){
			 $("#billToName").css({"border-color" : "#ff0000"});
			 $("#billToName-req").show();
		}
	});

	 $("#shipToAddress").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		 
		 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		 if ($("#shipToAddress").val().length > 1 ){
			 $("#shipToAddress").css({"border-color" : "#498648"});
			 $("#shipToAddress-req").hide();			 
		 }
		 if ($("#shipToAddress").val().length < 1 ){
			 $("#shipToAddress").css({"border-color" : "#ff0000"});
		     $("#shipToAddress-req").text('This field is required.');
			 $("#shipToAddress-req").show();			 
		 }
	 });
		
		$("#shipToPincode").on("keyup input", function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			var pincodeRegex = /[0-9]{6}/;
			if(pincodeRegex.test($("#shipToPincode").val()) !== true){				
				this.value = this.value.replace(/[^0-9]+/, '');
			}
			if(pincodeRegex.test($("#shipToPincode").val()) === true){
				$("#shipToPincode").css({"border-color" : "#498648"});
				if($("#shipToPincode").val().length==6){
					loadPincodeNState("shipToPincode",$("#shipToPincode").val(),"shipToState","shipToStateId","shipToStateValue","shipToPincode-req");
					if($("#billToGstin").val() == 'URP'){
						$("#billToStateId").val($("#shipToStateId").val());
						$("#billToStateValue").val($("#shipToStateValue").val());
						resetItemDetails();
					}
				}	
			}
			if(pincodeRegex.test($("#shipToPincode").val()) !== true){
				$("#shipToPincode").css({"border-color" : "#ff0000"});
				$("#shipToState").val('');
				if($("#billToGstin").val() == 'URP'){
					$("#billToStateId").val('');
					$("#billToStateValue").val('');
				}
			}
		});
				
		$("#shipToPlace").on("keyup input",function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		 
		 this.value = this.value.replace(/[\\[]*$/, '');
		 this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		 if ($("#shipToPlace").val().length > 1 ){
			 $("#shipToPlace").css({"border-color" : "#498648"});
			 $("#shipToPlace-req").hide();			 
		 }
		 if ($("#shipToPlace").val().length < 1 ){
			 $("#shipToPlace").css({"border-color" : "#ff0000"});
		     $("#shipToPlace-req").text('This field is required.');
			 $("#shipToPlace-req").show();			 
		 }
	 });

		$("#transporterName").on("keyup input",function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }		
			this.value = this.value.replace(/[\\[]*$/, '');
			this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
			if($("#transporterName").val().length > 1){
				 $("#transporterName").css({"border-color" : "#498648"});				 
			 }
			if($("#transporterName").val().length < 1){
				 $("#transporterName").css({"border-color" : "#2f65b0"});
			}		
		});

		$("#transporterId").on("keyup input",function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }		
			this.value = this.value.replace(/[\\[]*$/, '');
			this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
			if($("#transporterId").val().length > 1){
				 $("#transporterId").css({"border-color" : "#498648"});				 
			 }
			if($("#transporterId").val().length < 1){
				 $("#transporterId").css({"border-color" : "#2f65b0"});
			}		
		});

		$("#approxDistance").on("keyup input", function(){
			this.value = this.value.replace(/[^[0-9.]*$/, '');
			if(currencyRegex.test($(this).val()) === true){
				$("#approxDistance").css({"border-color" : "#498648"});	
			}    	
			if(currencyRegex.test($(this).val()) !== true){
				$("#approxDistance").css({"border-color" : "#ff0000"});		
			}
		});
		
		$("#transporterDocNo").on("keyup input",function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }		
			this.value = this.value.replace(/[\\[]*$/, '');
			this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
			if($("#transporterDocNo").val().length > 1){
				 $("#transporterDocNo").css({"border-color" : "#498648"});
				 $("#transporterDocNo-req").hide();				 
			 }
			if($("#transporterDocNo").val().length < 1){
				 $("#transporterDocNo").css({"border-color" : "#ff0000"});
				 $("#transporterDocNo-req").show();
			}		
		});

		$("#vechicleNo").on("keyup", function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			}
			this.value = this.value.replace(/[\\[]*$/, '');
			this.value = this.value.replace(/[^[a-zA-Z0-9-]*$/, ''); 
			if ($("#vechicleNo").val() === ""){
				$("#vechicleNo").css({"border-color" : "#2f65b0"});
			}
			if ($("#vechicleNo").val() !== ""){
				$("#vechicleNo").css({"border-color" : "#498648"});
			}
		});
		
	$(function(){
		$(document).on('keyup', '.productName', function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }		
			this.value = this.value.replace(/[\\[]*$/, '');
			this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
			if ($(this).val() !== ""){
	    		 $(this).css({"border-color" : "#498648"});
	    	 }
	    	 if ($(this).val() === ""){
	    		 $(this).css({"border-color" : "#ff0000"});
	    	 } 
		}); 

		$(document).on("keyup ", '.quantity', function(){
			this.value = this.value.replace(/[^0-9]+/, '');
			if (currencyRegex.test($(this).val())!=true){
				$(this).css({"border-color" : "#ff0000"});	 
			}
			
			if (currencyRegex.test($(this).val())==true && $(this).val().length > 0){
				$(this).css({"border-color" : "#498648"});
				if($(this).parent().parent().find('.rate').val() != "" && $(this).parent().parent().find('.rate').val().length > 0){
					var quantity = $(this).val();
					var rate = $(this).parent().parent().find('.rate').val();
					$(this).parent().parent().find('.taxableValue').val(quantity*rate);	
					/*$(this).parent().parent().find('.cgst').val(0);	
					$(this).parent().parent().find('.sgst').val(0);	
					$(this).parent().parent().find('.igst').val(0);		
					$(this).parent().parent().find('.cess').val('');
					$(this).parent().parent().find('.cess').css({"border-color" : "#ff0000"});*/
				}else{	
					$(this).parent().parent().find('.rate').css({"border-color" : "#ff0000"});
				}
			}

			if (($(this).val().length < 1) || ($(this).val() <= 0)){
				$(this).css({"border-color" : "#ff0000"});	
				$(this).focus();
				$(this).parent().parent().find('.taxableValue').val('');
			}
		});
		
		$(document).on('change', '.unitOfMeasurement', function(){
			if ($(this).val() === ""){
				 $(this).css({"border-color" : "#ff0000"});
			 }
			 if ($(this).val() !== ""){
				 $(this).css({"border-color" : "#498648"});
			 } 
		}); 

		$(document).on('keyup', '.rate', function(e){
			this.value = this.value.replace(/[^[0-9.]*$/, '');
			if(currencyRegex.test($(this).val()) === true){
				$(this).css({"border-color" : "#498648"});
				if($(this).parent().parent().parent().parent().find('.quantity').val() != "" && $(this).parent().parent().parent().parent().find('.quantity').val().length > 0){
					var quantity = $(this).parent().parent().parent().parent().find('.quantity').val();
					var rate = $(this).val();
					$(this).parent().parent().find('.taxableValue').val(quantity*rate);	
					/*$(this).parent().parent().parent().parent().find('.cgst').val(0);	
					$(this).parent().parent().parent().parent().find('.sgst').val(0);	
					$(this).parent().parent().parent().parent().find('.igst').val(0);		
					$(this).parent().parent().parent().parent().find('.cess').val('');
					$(this).parent().parent().parent().parent().find('.cess').css({"border-color" : "#ff0000"});*/
				}else{						
					$(this).parent().parent().parent().parent().find('.quantity').css({"border-color" : "#ff0000"});
				}
			}    	
			if(currencyRegex.test($(this).val()) !== true){
				$(this).css({"border-color" : "#ff0000"});	
				$(this).parent().parent().find('.taxableValue').val('');
			}
		}); 
		
		/*$(document).on('keyup', '.cess', function(){
			this.value = this.value.replace(/[^[0-9.]*$/, '');
			if ($(this).val() === ""){
				 $(this).css({"border-color" : "#ff0000"});
			 }
			 if ($(this).val() !== ""){
				 $(this).css({"border-color" : "#498648"});
			 } 
		})		*/
	});
	

	function validateGSTINWithRegex(gstin,id,spanid){
		if(GstinNumRegex.test(gstin) === true){				
			$("#" + id).css({"border-color" : "#498648"});
			$("#" + spanid).hide();
			return true;
		}
		if(GstinNumRegex.test(gstin) !== true){
			$("#" + id).css({"border-color" : "#ff0000"});
			$("#" + spanid).show();
			return false;
		}		
	}	

	
	function generateFinalDataJson(){		
		var transctionType = $("#transctionType").val();
		var subType = $("#subType").val();
		var othersSubType=null;
		if(subType==8){
			othersSubType = $("#otherSubTypes").val();
		}
		var documentType = $("#documentType").val();		
		var documentNo = $("#documentNo").val();
	    var documentDate = $("#documentDate").val();	    
	    var billFromGstin = $("#billFromGstin").val();
	    var billToGstin = $("#billToGstin").val();
		var transporterName = $("#transporterName").val();
	    var transporterId = $("#transporterId").val();
	    var approxDistance = parseFloat($("#approxDistance").val()).toFixed(2);
		var mode = $('input[name=mode]').filter(':checked').val();
		var vechicle_type = $('input[name=vechicle_type]').filter(':checked').val();
		var vechicleNo = $("#vechicleNo").val();
	    var transporterDocNo = $("#transporterDocumentNo").val();	    
	    var transporterDocDate = $("#transporterDocDate").val();	    
	    var nicUserId = $("#nicId").val();
	    var nicPwd = $("#nicPassword").val();	
	    var userId = $("#userId").val();
	     
	    var billFromName = $("#billFromName").val();
	    var billFromStateId = $("#billFromStateId").val();
	    var billFromStateValue = $("#billFromStateValue").val();	   
	    var dispatchFromAddress = $("#dispatchFromAddress").val();
	    var dispatchFromPlace = $("#dispatchFromPlace").val();
	    var dispatchFromPincode = $("#dispatchFromPincode").val();
	    var dispatchFromStateId = $("#dispatchFromStateId").val();
	    var dispatchFromStateValue = $("#dispatchFromStateValue").val();
	    var billToName = $("#billToName").val();
	    var billToStateId = $("#billToStateId").val();
	    var billToStateValue = $("#billToStateValue").val();	    
	    var shipToAddress = $("#shipToAddress").val();
	    var shipToPlace = $("#shipToPlace").val();
	    var shipToPincode = $("#shipToPincode").val();
	    var shipToStateId = $("#shipToStateId").val();
	    var shipToStateValue = $("#shipToStateValue").val();	    
	    var sgstTotalAmount = $("#sgstTotalAmount").val();
	    var cgstTotalAmount = $("#cgstTotalAmount").val();
	    var igstTotalAmount = $("#igstTotalAmount").val();
	    var totalAmount = $("#totalAmount").val();
	    var totalcessAmount = $("#totalcessAmount").val();
	   
	    var totalCessAdvolAmt = $('#cessadvolamt').val();
		var totalCessNonAdvolAmt = $('#cessnonadvolamt').val();
		var otherAmt = $('#otheramt').val();
		
	    var totalNoOfRecords = $('.addrow .newrowgrid').length;
	    var jsonObject;
		var productListArray = new Array();
		var jsonObject;
		for (i = 0; i < totalNoOfRecords; i++) { 
			 jsonObject = new Object();
		     jsonObject.hsnId = $("#hsnId_"+i).val();
			 jsonObject.productDesc = $("#"+i).val();
			 jsonObject.productName = $("#productName_"+i).val();
			 jsonObject.quantity = $("#quantity_"+i).val();
			 jsonObject.quantityUnit = $("#unitOfMeasurement_"+i).val();
			 jsonObject.taxableAmount = parseFloat($("#taxableValue_"+i).val()).toFixed(1);
			 jsonObject.cessValue = 0.00;
			 jsonObject.cgstsgstRate = $("#cgst_sgstrate_"+i).val();
			 jsonObject.igstRate = $("#igstrate_"+i).val();
			 jsonObject.cessadvolrate = parseFloat($("#cessadvolrate_"+i).val()).toFixed(1);
			 jsonObject.cessnonadvolrate = parseFloat($("#cessnonadvolrate_"+i).val()).toFixed(1);
		     productListArray.push(jsonObject);
		}
		
		var inputData = {
   				"supplyType" : transctionType,
   				"subSupplyType" : subType, 
   				"othersSubType" : othersSubType,
   				"documentType" : documentType,
   				"documentNo" : documentNo,
   				"documentDateInString" : documentDate,  
   				
	   			"fromGstin" : billFromGstin,	   			
	   			"fromTraderName": billFromName,
	   			"fromAddress1": dispatchFromAddress,
	   			"fromPlace": dispatchFromPlace,
	   			"fromPincode": dispatchFromPincode,
	   			"fromStateCode": billFromStateId,
	   			"actFromStateCode": dispatchFromStateId,
	   			
	   			"toGstin" : billToGstin,
	   			"toTraderName": billToName,
	   			"toAddress1": shipToAddress,	   			
	   			"toAddress2": "",                   
	   			"toPlace": shipToPlace,
	   			"toPincode": shipToPincode,
	   			"toStateCode": billToStateId,
	   			"actToStateCode": shipToStateId,
	   		  
				"transId" : transporterId,
   				"transName" : transporterName,
   				"transDocNo" : transporterDocNo,   				
   				"transMode" : mode,
   				"transDocDateInString" : transporterDocDate,
   				"vehicleNo" : vechicleNo,
   				"vehicleType" : vechicle_type,
   				"transDistance" : approxDistance,
				"nicId": nicUserId,
				"nicPassword": nicPwd,
				"userId":userId,
				"totalCessadvolValue": totalCessAdvolAmt,
				"totalCessnonadvolValue": totalCessNonAdvolAmt,
				"otherValue": otherAmt,
				"ewayBillWIItem" : productListArray,
				};
		 return inputData;
	}
	
	function validateproductCalculation(){
		var flag= false;
		var totalNoOfRecords = $('.addrow .newrowgrid').length;
		var calculateFlagForNoOfProducts = $('#calculateFlagForNoOfProducts').val();
		if(totalNoOfRecords == calculateFlagForNoOfProducts){
			flag = true;
		}
		return flag;
	}
	
	function submitData(){
	    var generateFinalJsonData = generateFinalDataJson();
	    var flag = validateproductCalculation();
	    if(flag){
	    	var clientId = $("#clientId").val(); 
	    	var secretKey = $("#secretKey").val(); 
	    	var appCode = $("#appCode").val();
			var ipUsr = $("#ipUsr").val();

			//url : "ewaybill/wizardGenerateEwayBill",
			//console.log("submit data :" +JSON.stringify(generateFinalJsonData));
			  $.ajax({
					url : "ewaybill/wizardGenerateEwayBillV3",
					type : "POST",
					contentType : "application/json",
					dataType : "json",
					headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
					data : JSON.stringify(generateFinalJsonData),
					async : false,
					beforeSend: function(){
				    	$("#loadingmessage").show();
						$("#DivWIEwayBill").hide();
				    },
				    complete: function(){
						$("#DivWIEwayBill").show();
				    	$("#loadingmessage").hide();
				    },
					success : function(jsonVal) {
						if (isValidSession(jsonVal) == 'No') {
							window.location.href = getDefaultSessionExpirePage();
							return;
						}

						if(jsonVal.status == 'success'){
							$(".btn-info").prop("disabled",false);
							bootbox.alert("Eway Bill generated Successfully No : "+jsonVal.ewayBillNo+" valid upto :"+jsonVal.validUpto, function() {
											setTimeout(function(){ 
												if($("#loggedInFrom").val()=='MOBILE'){
													window.location.href = "./getGenericEWayBills";
												}else{
													window.location.href = "./wizardGetGenericEWayBills";
												}											
												return;
											}, 500)
							$(".btn-info").prop("disabled",true);
							});
						}
						if(jsonVal.status == 'failure'){
							$(".btn-info").prop("disabled",false);
							bootbox.alert(jsonVal.error_desc);
						}
					},
			         error: function (data,status,er) {  
			 			$(".btn-info").prop("disabled",false);      	 
			        	 getInternalServerErrorPage();   
			        }
			    });
			  
	    }else{
	    	bootbox.alert("Please calculate Tax for newly added products");
	    }
		
	}
			
	function verifyTextField(id, spanid, msg) {
		var result=false;
		if (($("#" + id).val() === "")) {
			$("#" + id).css({"border-color" : "#ff0000"});
			//$("#" + spanid).text(msg);
			//$("#" + spanid).show();
			result=true;
		} else {
			$("#" + id).css({"border-color" : "#498648"});
			//$("#" + spanid).hide();
			result=false;
		}
		return result;
	}
	
	function backToLoginValidate(){	
		window.location.href = "./wizardLoginGenerateEwayBill";
	}

	/*
	 function generateFinalDataJson(){		
		var transctionType = $("#transctionType").val();
		var subType = $("#subType").val();
		var othersSubType=null;
		if(subType==8){
			othersSubType = $("#otherSubTypes").val();
		}
		var documentType = $("#documentType").val();		
		var documentNo = $("#documentNo").val();
	    var documentDate = $("#documentDate").val();	    
	    var billFromGstin = $("#billFromGstin").val();
	    var billToGstin = $("#billToGstin").val();
		var transporterName = $("#transporterName").val();
	    var transporterId = $("#transporterId").val();
	    var approxDistance = $("#approxDistance").val();		
		var mode = $('input[name=mode]').filter(':checked').val();
		var vechicle_type = $('input[name=vechicle_type]').filter(':checked').val();
		var vechicleNo = $("#vechicleNo").val();
	    var transporterDocNo = $("#transporterDocumentNo").val();	    
	    var transporterDocDate = $("#transporterDocDate").val();	    
	    var nicUserId = $("#nicId").val();
	    var nicPwd = $("#nicPassword").val();	
	    var userId = $("#userId").val();
	    
	    var billFromName = $("#billFromName").val();
	    var billFromStateId = $("#billFromStateId").val();
	    var billFromStateValue = $("#billFromStateValue").val();	   
	    var dispatchFromAddress = $("#dispatchFromAddress").val();
	    var dispatchFromPlace = $("#dispatchFromPlace").val();
	    var dispatchFromPincode = $("#dispatchFromPincode").val();
	    var dispatchFromStateId = $("#dispatchFromStateId").val();
	    var dispatchFromStateValue = $("#dispatchFromStateValue").val();
	    var billToName = $("#billToName").val();
	    var billToStateId = $("#billToStateId").val();
	    var billToStateValue = $("#billToStateValue").val();	    
	    var shipToAddress = $("#shipToAddress").val();
	    var shipToPlace = $("#shipToPlace").val();
	    var shipToPincode = $("#shipToPincode").val();
	    var shipToStateId = $("#shipToStateId").val();
	    var shipToStateValue = $("#shipToStateValue").val();	    
	    var sgstTotalAmount = $("#sgstTotalAmount").val();
	    var cgstTotalAmount = $("#cgstTotalAmount").val();
	    var igstTotalAmount = $("#igstTotalAmount").val();
	    var totalAmount = $("#totalAmount").val();
	    var totalcessAmount = $("#totalcessAmount").val();
	   
	    var totalNoOfRecords = $('.addrow .rows').length;
	    var jsonObject;
		var productListArray = new Array();
		var jsonObject;
		for (i = 0; i < totalNoOfRecords; i++) { 
			 jsonObject = new Object();
		     jsonObject.hsnId = $("#hsnId_"+i).val();
			 jsonObject.productDesc = $("#"+i).val();
			 jsonObject.productName = $("#productName_"+i).val();
			 jsonObject.quantity = $("#quantity_"+i).val();
			 jsonObject.quantityUnit = $("#unitOfMeasurement_"+i).val();
			 jsonObject.taxableAmount = $("#taxableValue_"+i).val();
			 jsonObject.cessValue = $("#cess_"+i).val();
		     productListArray.push(jsonObject);
		}
		
		var inputData = {
   				"supplyType" : transctionType,
   				"subSupplyType" : subType, 
   				"othersSubType" : othersSubType,
   				"documentType" : documentType,
   				"documentNo" : documentNo,
   				"documentDateInString" : documentDate,   				
	   			"fromGstin" : billFromGstin,
	   			
	   			"fromTraderName": billFromName,
	   			"fromAddress1": dispatchFromAddress,
	   			"fromPlace": dispatchFromPlace,
	   			"fromPincode": dispatchFromPincode,
	   			"fromStateCode": billFromStateId,
	   			"actFromStateCode": dispatchFromStateId,
	   			
	   			"toGstin" : billToGstin,

	   			"toTraderName": billToName,
	   			"toAddress1": shipToAddress,	   			
	   			"toAddress2": "",                   
	   			"toPlace": shipToPlace,
	   			"toPincode": shipToPincode,
	   			"toStateCode": billToStateId,
	   			"actToStateCode": shipToStateId,
	   		  
				"transId" : transporterId,
   				"transName" : transporterName,
   				"transDocNo" : transporterDocNo,   				
   				"transMode" : mode,
   				"transDocDateInString" : transporterDocDate,
   				"vehicleNo" : vechicleNo,
   				"vehicleType" : vechicle_type,
   				"transDistance" : approxDistance,
				"ewayBillWIItem" : productListArray,
				"nicId": nicUserId,
				"nicPassword": nicPwd,
				"userId":userId
				};
		 return inputData;
	}  
	 */