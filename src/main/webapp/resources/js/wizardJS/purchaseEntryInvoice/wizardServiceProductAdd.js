/* Adding client side validation - Start */

	var contactNumRegex = /[0-9]{2}\d{8}/;
	var blankMsg="This field is required";
	var regMsg = "Data should be in proper format";
	var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;/*/(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;*/ 
	var cessMsg = "Enter cess amount in proper format";
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//	var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
	var gstinMsg = "GSTIN number should be in proper format";
	var gstinStateErrMsg = "Invalid State code for GSTIN entered";
	var quantityRegMsg = "Quantity should be more than 1 ";
	var cmpMsg = "The State of Supplier should be same as GSTIN of the Supplier";

$(document).ready(function(){ 
	$( "#service_add,#service_edit" ).on( "click", function(e) {
		           
		var errFlag2 = validateSelect("calculation_on","calculation-on-csv-id");
		var errFlag3 = validateCalculationOn();
		//var errFlag14 = false;
		var errFlag17 = false;		
		var errFlagGSTINStateId = false;
		var errFlagLocation = false;
		//var errFlagGSTINSupplierId = false;
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
		var errFlagSelectQuantity = false;
		var errFlagQuantity = false;
		var errFlagSelectLumpsum = false;
		var errFlagLumpsum = false;		
		var errFlagServiceName = false;  
		var errFlagCess = false;
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
		  if(!errFlagSupplierPincode){
			  errFlagSupplierPincode = validatePinCodeField("supplier_pincode","shipTo-customer-pincode-csv-id",'Pincode should have 6 digits'); 
		  } 
		
		errFlagSupplierState = validateTextField("supplier_state","shipTo-customer-address-csv-id",blankMsg);
		
		errFlagPurchaseDate = validateDateField("purchase_date","purchase_date-id");
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
		
		errFlagShipAddress = validateTextField("shipping_address","shipping_address-csv-id",blankMsg);
		errFlagServiceName = validateSelect("service_name","service-name-csv-id");
		
		var billMethod = $('select#calculation_on option:selected').val();
		errFlagSelectQuantity = validateQuantity("quantity", "quantity-csv-id",quantityRegMsg);
		if(errFlagSelectQuantity){
			$("#quantity").focus();
		}else{
			errFlagQuantity = validateRegexpressions("quantity", "quantity-csv-id", quantityRegMsg, currencyRegex);
		}
		
		if(billMethod == 'Lumpsum'){
			errFlagSelectLumpsum = validateQuantity("amountToShow", "amountToShow-csv-id",regMsg);
			if(errFlagSelectLumpsum){
				$("#amountToShow").focus();
			}else{
				errFlagLumpsum = validateRegexpressions("amountToShow", "amountToShow-csv-id", regMsg, currencyRegex);
			}			
		}
		
		
		if(errFlagGSTINStateId){
			$("#gstnStateId").focus();
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

		if(errFlagPlaceOfSupply){
			 $("#placeOfSupply").focus();
		}
		
		if($("#cess").val().length > 0 ){
			errFlagCess = validateCess();
		}
		
		if(errFlagInvoicePeriod){
			if(errFlagInvoicePeriodFrom ){
				$("#invoicePeriodFromDateInString").focus();
			}
			if(errFlagInvoicePeriodTo){
				$("#invoicePeriodToDateInString").focus();
			}
		}
				
		//check if discount amount > total amount - Start
		var discountAmt = $("#offerAmount").val();
		var actualAmt = $("#amountToShow").val();
		if(discountAmt.trim().length > 0){
			if(parseFloat(discountAmt) >= parseFloat(actualAmt)){
				errFlag17 = validateForDiscountAmtGTTotalAmt();
			}			
		}		
		//check if discount amount > total amount - End
		
		
		if ( (errFlag2) || (errFlag3) || (errFlag17) || (errFlagGSTINStateId) || (errFlagLocation) || (errFlagGSTINSupplier) || (gstinStateValid) || (errFlagSupplierName) ||
			 (errFlagSupplierPincode) || (errFlagSupplierState) || (errFlagPurchaseDate) || (errFlagInvoiceNo) || (errFlagBillingAddress) || (errFlagReverceCharge) ||
			 (errFlagShipAddress) || (errFlagSelectQuantity) || (errFlagQuantity) || (errFlagSelectLumpsum) || (errFlagLumpsum) || 
			 (errFlagServiceName) || (errFlagCess) || (errFlagInvoicePeriod) || (errFlagPlaceOfSupply) || errFlagSupplierGstinANDSupplierState){
			 e.preventDefault();	 
		}else{
			if(this.id == 'service_add'){
				add_service_row();
			}
			
			if(this.id == 'service_edit'){
				update_service_row();
			}			
		}		
	});	
});


$("#supplier_gstin").blur(function(){
	if($("#supplier_gstin").val().length >= 15)
	{
		var supplierGstinText = $("#supplier_gstin").val();
		var supplierGstinNo = supplierGstinText.substring(0,2);
		$("#supplierGstnNumber").val(parseInt(supplierGstinNo));
	}
});


 function compareTextFields(id1, id2, spanid, msg){
	var result=false;
	if ($("#"+id1).val() === $("#"+id2).val()){
		$("#"+spanid).hide();
		result= false;
	 } else { 
		$("#"+spanid).text(msg);
		$("#"+spanid).show();
		result= true;
	 } 
	 return result;
}
 function validateGstinAndStAddrState(){		
		errFlagSupplierGstinANDSupplierState = compareTextFields("supplier_stateCodeId","supplierGstnNumber","supplierState-req",cmpMsg);
		return errFlagSupplierGstinANDSupplierState;
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

function validateCalCulationOnSelect(){
	 if($("#calculation_on").val()=="Amount"){
		if ($("#quantity").val().length > 1){
			 $("#quantity").addClass("input-correct").removeClass("input-error");
			 $("#quantity-csv-id").hide();		 
		}
	
		if ($("#quantity").val().length < 1 ){
			 $("#quantity").addClass("input-error").removeClass("input-correct");
			 $("#quantity-csv-id").text('This field is required');	
			 $("#quantity-csv-id").show();	
			 $("#quantity").focus();
		}
	 }
}

function validateCess(){
	errsacServiceRate = validateTextField("cess","cess-csv-id",blankMsg);
	if(!errsacServiceRate){
		errsacServiceRate = validateRegexpressions("cess","cess-csv-id",cessMsg,currencyRegex);
	 }
	return errsacServiceRate;
}

function callFieldCheck(id,spanid){
	var response = true;
	if ($("#"+id).val().length > 0){
		 $("#"+id).addClass("input-correct").removeClass("input-error");
		 $("#"+spanid).hide();	
		 response =  false;
	}

	if ($("#"+id).val().length < 1 ){
		 $("#"+id).addClass("input-error").removeClass("input-correct");
		 $("#"+spanid).text('This field is required');	
		 $("#"+spanid).show();	
		 $("#"+id).focus();
		 response =  true;
	}
	return response;
}

function validateCalculationOn(){
	var response = true;
	var calculationOn = $("#calculation_on").val();
	 if( calculationOn == "Amount"){
		 response = callFieldCheck("quantity","quantity-csv-id");
		 clearInputErrorClass("amountToShow","amountToShow-csv-id");
	 }
	 if( calculationOn == "Lumpsum"){
		 response = callFieldCheck("amountToShow","amountToShow-csv-id");
		 clearInputErrorClass("quantity","quantity-csv-id");
	 } 
	 return response;
}

function clearInputErrorClass(id,spanid){
	$("#"+id).removeClass("input-error").addClass("");
	$("#"+spanid).css("display","none");
	
}

function validateCustomerContactNo(){
	errCustContactNo = validateTextField("customer_contactNo","contact-no-req",blankMsg);
	 if(!errCustContactNo){
		 errCustContactNo=validateRegexpressions("customer_contactNo","contact-no-req",regMsg,contactNumRegex);
	 }
	 return errCustContactNo;
}

function validateSupplierGstin(){
	var	err  = validateTextField("supplier_gstin","ecommerce-gstin-csv-id",blankMsg);
	if(!err){
		err = validateRegexpressions("supplier_gstin","ecommerce-gstin-csv-id",gstinMsg,GstinNumRegex);
	}
	return err;
}

function validGstinStateCode(){
	var	error = validateGstinStateCode("supplier_gstin","ecommerce-gstin-csv-id",gstinStateErrMsg);	 
	 return error;
}

function validateForDiscountAmtGTTotalAmt(){
	$("#offerAmount-csv-id").text("Discount cannot be more than or equal to Amount");
	$("#offerAmount-csv-id").show();
	$("#offerAmount").addClass("input-error").removeClass("input-correct");
	
	return true;
}

function validatePinCodeField(id, spanid, msg) {
	var result=false;
	if (($("#" + id).val().length > 6) || ($("#" + id).val().length < 6)) {
		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg);
		$("#" + spanid).show();
		result=true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
		result=false;
	}
	return result;
}

$("#invoicePeriodFromDateInString").on("keyup click", function(){
	if($("#invoicePeriodFromDateInString").val().length <= 0){
		if($("#invoicePeriodToDateInString").val().length <= 0){
		  	  $("#invoicePeriodToDateInString").removeClass("input-correct");
		  	  $("#invoicePeriodToDateInString").removeClass("input-error");
		}else{
			$("#invoicePeriodToDateInString").addClass("input-error").removeClass("input-correct");
		}
		$("#invoicePeriodFromDateInString").removeClass("input-correct");
		$("#invoicePeriodFromDateInString").removeClass("input-error");
	}
});
$("#invoicePeriodToDateInString").on("keyup click", function(){
	if($("#invoicePeriodToDateInString").val().length <= 0){
		if($("#invoicePeriodFromDateInString").val().length <= 0){
		  	  $("#invoicePeriodFromDateInString").removeClass("input-correct");
		  	  $("#invoicePeriodFromDateInString").removeClass("input-error");
		}else{
			$("#invoicePeriodFromDateInString").addClass("input-error").removeClass("input-correct");
		}
		$("#invoicePeriodToDateInString").removeClass("input-correct");
		$("#invoicePeriodToDateInString").removeClass("input-error");
	}
});

$("#gstnStateId").on("keyup click", function(){
	if ($("#gstnStateId").val() === ""){
		$("#gstnStateId").addClass("input-error").removeClass("input-correct");
		$("#gstnStateId-csv-id").show();
	}
	if ($("#gstnStateId").val() !== ""){
		$("#gstnStateId").addClass("input-correct").removeClass("input-error");
		$("#gstnStateId-csv-id").hide();
	}
});

$("#placeOfSupply").on("keyup click", function(){
	if ($("#placeOfSupply").val() === ""){
		$("#placeOfSupply").addClass("input-error").removeClass("input-correct");
		$("#placeOfSupply-csv-id").show();
	}
	if ($("#placeOfSupply").val() !== ""){
		$("#placeOfSupply").addClass("input-correct").removeClass("input-error");
		$("#placeOfSupply-csv-id").hide();
	}
});

$("#location").on("keyup click", function(){
	if ($("#location").val() === ""){
		$("#location").addClass("input-error").removeClass("input-correct");
	}
	if ($("#location").val() !== ""){
		$("#location").addClass("input-correct").removeClass("input-error");
	}
});

$(document).ready(function() {
	$("#supplier_gstin").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		this.value = this.value.toUpperCase();
		this.value = this.value.replace(/^00/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
		
		 var abcd = $('input[name=custType]').val();
		 
		 if ($("#Registered").prop("checked")) {
			 if((this.value!='') && GstinNumRegex.test($("#supplier_gstin").val()) === true){	
		 			$("#supplier_gstin").addClass("input-correct").removeClass("input-error");			 
		 		}
		 		if((this.value!='') && GstinNumRegex.test($("#supplier_gstin").val()) !== true){
		 			$("#supplier_gstin").addClass("input-error").removeClass("input-correct");			 	
		 		}
		 		if($("#supplier_gstin").val() == ""){				
		 			$("#supplier_gstin").addClass("input-error").removeClass("input-correct");			 
		 		}
		 }else{
			 $("#supplier_gstin").removeClass("input-correct");
	    	 $("#supplier_gstin").removeClass("input-error");
		 }		
	});
});

$("#supplier_name").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
	if ($("#supplier_name").val().length > 1){
		 $("#supplier_name").addClass("input-correct").removeClass("input-error");
		 $("#shipTo-customer-name-csv-id").hide();		 
	}
	if ($("#supplier_name").val().length < 1){
		 $("#supplier_name").addClass("input-error").removeClass("input-correct");
		 $("#shipTo-customer-name-csv-id").show();
	}	
});	

$("#billing_address").on("keyup input",function(){
	this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
	if ($("#billing_address").val().length > 1){
		 $("#billing_address").addClass("input-correct").removeClass("input-error");
	}else{
		 $("#shipping_address").val('');
		$("#billing_address").addClass("input-error").removeClass("input-correct");
	}
	
	var isshipToBill = $('#shipToBill').is(':checked');
	if(isshipToBill){
		if ($("#shipping_address").val().length > 1){
			if($("#billing_address").val() === $("#shipping_address").val()){
				$("#billing_address,#shipping_address").addClass("input-correct").removeClass("input-error");
			}else{
				//$("#billing_address,#shipping_address").addClass("input-error").removeClass("input-correct");
				var billingAddr = $("#billing_address").val();
	            $("#shipping_address").val(billingAddr);
			}
		}else{
			$("#shipping_address").addClass("input-error").removeClass("input-correct");
		}	
	}else{
		$("#shipping_address").removeClass("input-correct");
		$("#shipping_address").removeClass("input-error");
	}	
});

$("#shipping_address").on("keyup input",function(){
	this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
	
	var isshipToBill = $('#shipToBill').is(':checked');
	if(!isshipToBill){
		if ($("#shipping_address").val().length > 1){
			$("#shipping_address").addClass("input-correct").removeClass("input-error");
		}else{
			$("#shipping_address").addClass("input-error").removeClass("input-correct");
		}	
	}
});

$("#supplier_pincode").on("keyup input",function(){
	this.value = this.value.replace(/[^0-9]+/, '');
	if ($("#supplier_pincode").val().length < 1){
		 $("#supplier_pincode").addClass("input-error").removeClass("input-correct");
		 $("#supplier_state").val('');
		 $("#supplierState-req").hide();
	}
});

$("#invoice_number").on("keyup input",function(){
	this.value = this.value.replace(/[^a-zA-Z0-9-/s]*$/, '');
	if ($("#invoice_number").val().length < 1){
		 $("#invoice_number").addClass("input-error").removeClass("input-correct");
	}else{
		$("#invoice_number").addClass("input-correct").removeClass("input-error");
	}
});

$("#supplierDocInvoiceNo").on("keyup input",function(){
	this.value = this.value.replace(/[^a-zA-Z0-9-/s]*$/, '');
	if ($("#supplierDocInvoiceNo").val().length < 1){
		 $("#supplierDocInvoiceNo").addClass("input-error").removeClass("input-correct");
	}else{
		$("#supplierDocInvoiceNo").addClass("input-correct").removeClass("input-error");
	}
});

$("#service_name").on("change", function(){
	if ($("#service_name").val() === ""){
		$("#service_name").addClass("input-error").removeClass("input-correct");
		$("#service-name-csv-id").show();
	}
	if ($("#service_name").val() !== ""){
		$("#service_name").addClass("input-correct").removeClass("input-error");
		$("#service-name-csv-id").hide();
	}
});

$("#calculation_on").on("change", function(){
	if ($("#calculation_on").val() === ""){
		$("#calculation_on").addClass("input-error").removeClass("input-correct");
		$("#calculation-on-csv-id").show();
	}
	if ($("#calculation_on").val() !== ""){
		$("#calculation_on").addClass("input-correct").removeClass("input-error");
		$("#calculation-on-csv-id").hide();
	}
	
	if ($("#calculation_on").val() === "Amount"){
		clearInputErrorClass("amountToShow","amountToShow-csv-id");
	}
	
	if ($("#calculation_on").val() === "Lumpsum"){
		clearInputErrorClass("quantity","quantity-csv-id");
	}	
});

$("#quantity").on("keyup click", function(){
	if ($("#quantity").val().length > 0){
		 $("#quantity").addClass("input-correct").removeClass("input-error");
		 $("#quantity-csv-id").hide();		 
	}
	
	if (currencyRegex.test($("#quantity").val())!=true){
		 $("#quantity").addClass("input-error").removeClass("input-correct");
		 $("#quantity-csv-id").show();		 
	}
	
	if (currencyRegex.test($("#quantity").val())==true){
		 $("#quantity").addClass("input-correct").removeClass("input-error");
		 $("#quantity-csv-id").hide();		 
	}

	if (($("#quantity").val().length < 1) || ($("#quantity").val() <= 0)){
		 $("#quantity").addClass("input-error").removeClass("input-correct");
		 $("#quantity-csv-id").text('This field is required');	
		 $("#quantity-csv-id").show();	
		 $("#quantity").focus();
	}
});

$("#amountToShow").on("keyup click", function(){
	if ($("#amountToShow").val().length > 0){
		 $("#amountToShow").addClass("input-correct").removeClass("input-error");
		 $("#amountToShow-csv-id").hide();		 
	}
	
	if (currencyRegex.test($("#amountToShow").val())!=true){
		 $("#amountToShow").addClass("input-error").removeClass("input-correct");
		 $("#amountToShow-csv-id").show();		 
	}
	
	if (currencyRegex.test($("#amountToShow").val())==true){
		 $("#amountToShow").addClass("input-correct").removeClass("input-error");
		 $("#amountToShow-csv-id").hide();		 
	}

	if (($("#amountToShow").val().length < 1) || ($("#amountToShow").val() <= 0)){
		 $("#amountToShow").addClass("input-error").removeClass("input-correct");
		 $("#amountToShow-csv-id").text('This field is required');	
		 $("#amountToShow-csv-id").show();	
		 $("#amountToShow").focus();
	}	
});

$("#customer_contactNo").on("keyup input", function(){
	var contactNumRegex = /[0-9]{2}\d{8}/;
	if(contactNumRegex.test($("#customer_contactNo").val()) !== true){		
		this.value = this.value.replace(/[^0-9]+/, '');
	}
	if(contactNumRegex.test($("#customer_contactNo").val()) === true){
		$("#contact-no-req").hide();
		$("#customer_contactNo").addClass("input-correct").removeClass("input-error");	
	}
	if(contactNumRegex.test($("#customer_contactNo").val()) !== true){
		$("#contact-no-req").show();
		$("#customer_contactNo").addClass("input-error").removeClass("input-correct");	
	}
});

$(document).ready(function() { 
	var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;/*/(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;*/
	var msg = "Enter cess amount in proper format";
	$("#cess").on("keyup input",function(){
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(currencyRegex.test($("#cess").val()) === true){
			$("#cess-csv-id").hide();
			$("#cess").addClass("input-correct").removeClass("input-error");	
		}
		if(currencyRegex.test($("#cess").val()) !== true){
			$("#cess-csv-id").text(msg);
			$("#cess-csv-id").show();
			$("#cess").addClass("input-error").removeClass("input-correct");	
		}
		if($("#cess").val().length == 0 ){
			$("#cess-csv-id").hide();
			$("#cess").addClass("").removeClass("input-error");	
		}
	});
	
	$("#offerAmount").on("keyup input",function(){
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(currencyRegex.test($("#offerAmount").val()) === true){
			$("#offerAmount-csv-id").hide();
			$("#offerAmount").addClass("input-correct").removeClass("input-error");	
		}
		if(currencyRegex.test($("#offerAmount").val()) !== true){
			$("#offerAmount-csv-id").text(msg);
			$("#offerAmount-csv-id").show();
			$("#offerAmount").addClass("input-error").removeClass("input-correct");	
		}
		if($("#offerAmount").val().length == 0 ){
			$("#offerAmount-csv-id").hide();
			$("#offerAmount").addClass("").removeClass("input-error");	
		}
	});
		
	$("#footerNote").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');		
	});
});


$(document).ready(function(){ 
	$( "#add_chg_add" ).on( "click", function(e) {
		var additionalChargeValue = $('select#additionalChargeName option:selected').val();
		if(additionalChargeValue != ''){
			add_chg_row();
		}	
	});
	
	$( "#add_chg_edit" ).on( "click", function(e) {
		var additionalChargeValue = $('select#additionalChargeName option:selected').val();
		if(additionalChargeValue != ''){
			update_add_chg_row();
		}	
	});
});

/* Adding client side validation - End */

/* Preview Final Invoice Client JS - Start */

function validateCustomerField(id,displayId,spanid,msg){
	var response = false;
	 if(($("#"+id).val() === "") || ($("#"+displayId).val() === "")){
			$("#"+displayId).addClass("input-error").removeClass("input-correct");
			$("#"+spanid).text(msg);
			$("#"+spanid).show();
			$("#"+displayId).focus();
			response = true;
	 }	
	 return response;
}

function validateDateField(id,spanid){
	if(($("#"+id).val() === "") || (!$("#"+id).val())){
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

function validateQuantity(id, spanid, msg) {
	var result = false;
	if($("#" + id).val() <= 0){

		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg);
		$("#" + spanid).show();
		result = true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
	}
	
	return result;
}

function checkForServiceLength(){
	var response = true;
	var $toggle = $("#toggle");
	var totalRecordNo = $toggle.children().length;
	if(totalRecordNo > 0){
		response = false;
	}else{
		bootbox.alert("Please add atleast one Service/Product");
	}
	return response;
}

/* Preview Final Invoice Client JS - End */

/* Customer Email validations - Start */
$(document).ready(function() {
	$("#custEmailSave").on("click", function(e){
	var errFlag14 = validateCustomerEMailId();
	if(errFlag14){
		e.preventDefault();	 
	}else{
		var custEmail = $("#cust_email_addr").val();
		$("#customer_custEmail").val(custEmail);
		
		$('#finalSubmitId').click();
		
	}	
		
	});
	
	$("#custEmailCancel").on("click", function(e){
		$("#cust_email_addr").val("");
		$('#submitId').click();
		$("#generateInvoiceCustomerEmailPageHeader").hide();
		 $("#customerEmailDiv").hide();
		e.preventDefault();
	});
	
	$("#cust_email_addr").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		if ($("#cust_email_addr").val() == ""){
			 $("#cust-email-format").text('This field is required');
			 $("#cust-email-format").show();
				$("#cust_email_addr").addClass("input-error").removeClass("input-correct");
		 } else {
				if(!emailRegex.test($("#cust_email_addr").val())){
					$("#cust-email-format").show();
					$("#cust-email-format").text('This field should be in a correct format');
					$("#cust_email_addr").addClass("input-error").removeClass("input-correct");
					$("#cust_email_addr").focus();
				}
				else{
					$("#cust-email-format").hide();
					$("#cust_email_addr").addClass("input-correct").removeClass("input-error");
				}
		 }
		 
	});
	
});

function validateCustomerEMailId(){
	var errFlag14 = validateTextField("cust_email_addr","cust-email-format",blankMsg);
	 if(!errFlag14){
		 errFlag14=validateRegexpressions("cust_email_addr","cust-email-format", regMsg, emailRegex);
	 }
	 return errFlag14;
}

/* Customer Email validations - End */
