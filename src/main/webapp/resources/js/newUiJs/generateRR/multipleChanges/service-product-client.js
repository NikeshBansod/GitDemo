
/* Adding client side validation - Start */

	var contactNumRegex = /[0-9]{2}\d{8}/;
	var blankMsg="This field is required";
	var regMsg = "data should be in proper format";
	var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;/*/(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;*/ 
	var cessMsg = "Enter cess amount in proper format";
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/; 
	var gstinMsg = "GSTIN number should be in proper format";
	var gstinStateErrMsg = "Invalid State code for GSTIN entered";
	var quantityRegMsg = "Quantity should be more than 1 ";

$(document).ready(function(){ 
	$( "#service_add,#service_edit" ).on( "click", function(e) {
		var typeOfExport = $('select#exportType option:selected').val();
		var country = $('select#selectCountry option:selected').val();
		
		var errFlag0 = validateSelect("documentType","documentType-csv-id");
		var errFlag1 = validateSelect("service_name","service-name-csv-id");             
		var errFlag2 = validateSelect("calculation_on","calculation-on-csv-id");
		var errFlag3 = validateCalculationOn();
		var errFlag4 = false;
		var errFlag6 = false;
		var errFlag9 = false;
		var errFlag13 = false;
		var errFlag14 = false;
		var errFlag20 = false;
		var errFlag21 = false;
		var errFlag22 = false;
		var errFlag23 = false;
		var isCheckedShippingToBilling = $('#shipToBill').is(':checked');
		if(isCheckedShippingToBilling == true){
			   errFlag9 = false;
		}else{
			   errFlag13 = validateTextField("shipTo_customer_name","shipTo-customer-name-csv-id",blankMsg);
			   errFlag14 = validateTextField("shipTo_address","shipTo-customer-address-csv-id",blankMsg);
			   errFlag20 = validateTextField("shipTo_pincode","shipTo-customer-pincode-csv-id",blankMsg);
			   if(!errFlag20){
					   errFlag20 = validatePinCodeField("shipTo_pincode","shipTo-customer-pincode-csv-id",'Pincode should have 6 digits'); 
				  
			   }
			   errFlag21 = validateTextField("shipTo_city","shipTo-customer-city-csv-id",blankMsg);
			   if((country === '')){
				   errFlag9 = validateSelect("selectCountry","selectCountry-csv-id");
			   }
			  
	    }
		if((country === 'India')){
			errFlag4 = validateSelect("selectState","selectState-csv-id");
		}
		
		if((country === '') || (country === 'Other')){
			errFlag6 = validateSelect("exportType","exportType-csv-id");
		}
		
		var errFlag5 = validateCustomerField("customer_id","customer_name","customer-name-csv-id",blankMsg);
		
		var errFlag7 = false;
		var errFlag16 = false;
		var errFlag18 = false;
		var errFlag19 = false;
		
		var billMethod = $('select#calculation_on option:selected').val();
		/*if(billMethod == 'Amount'){*/
		if($('input[name=invoiceFor]').filter(':checked').val() == 'Product'){
			errFlag16 = validateQuantity("quantity", "quantity-csv-id",quantityRegMsg);
			if(errFlag16){
				$("#quantity").focus();
			}else{
				errFlag7 = validateRegexpressions("quantity", "quantity-csv-id", quantityRegMsg, currencyRegex);
				if(!errFlag7){
						if(parseInt($("#openingStockProduct").val()) < parseInt($("#quantity").val())){
							bootbox.alert("The entered quantity is greater than Current Stock Quantity");
						}
				}
			}
		}else{
			errFlag16 = validateQuantity("amountToShow", "amountToShow-csv-id",regMsg);
			if(errFlag16){
				$("#amountToShow").focus();
			}else{
				errFlag7 = validateRegexpressions("amountToShow", "amountToShow-csv-id", regMsg, currencyRegex);
			}
		}
			
			
		/*}*/
		if(billMethod == 'Lumpsum'){
			errFlag18 = validateQuantity("amountToShow", "amountToShow-csv-id",regMsg);
			if(errFlag18){
				$("#amountToShow").focus();
			}else{
				errFlag19 = validateRegexpressions("amountToShow", "amountToShow-csv-id", regMsg, currencyRegex);
			}
			
		}
		
		if(errFlag0){
			$("#documentType").focus();
		}
		
		if(errFlag1){
			if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
				$("#search-service-autocomplete").addClass("input-error");
			}else{
				$("#search-product-autocomplete").addClass("input-error");
			}
			$("#search-ser-prod-autocomplete-csv-id").show();
		}else{
			$("#search-service-autocomplete").addClass("input-correct").removeClass("input-error");
			$("#search-product-autocomplete").addClass("input-correct").removeClass("input-error");
			$("#search-ser-prod-autocomplete-csv-id").hide();
		}
		
		var errFlag10 = validateSelect("gstnStateId","gstnStateId-csv-id"); 
		if(errFlag10){
			$("#gstnStateId").focus();
		}
		
		var errFlag11 = false;
		if($("#cess").val().length > 0 ){
			errFlag11 = validateCess();
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
			if(!errFlag15){
				gstinStateValid = validGstinStateCode();
			}
			
		}
		if(errFlag15 || gstinStateValid){
			$("#ecommerceGstin").focus();
		}
		
		//check if discount amount > total amount - Start
		var errFlag17 = false;
		var discountAmt = $("#offerAmount").val();
		var actualAmt = $("#amountToShow").val();
		if(discountAmt.trim().length > 0){
			//alert("discountAmt : "+parseFloat(discountAmt)+",actualAmt :"+parseFloat(actualAmt));
			var discountType = $('input[name=itemDiscountType]').filter(':checked').val();
			if(discountType == 'Value'){
				if(parseFloat(discountAmt) >= parseFloat(actualAmt)){
					errFlag17 = validateForDiscountAmtGTTotalAmt();
				}
			}
			
			
		}
		
		//check if discount amount > total amount - End
		if($("#invDataFiledToGstn").val() == "true"){
			if($("#invoiceSequenceType").val() != 'Auto'){
				errFlag22 = validateTextField("invoiceNumber","invoiceNumber-csv-id",blankMsg);
				errFlag23 = checkIfInvoiceNumberAlreadyPresent($("#invoiceNumber").val());
				if(errFlag23){
					$("#invoiceNumber").addClass("input-error").removeClass("input-correct");
					$("#invoiceNumber-duplicate-csv-id").text("Invoice Number Already Present");
					$("#invoiceNumber-duplicate-csv-id").show();
				}
			}
		}
		
		if(errFlag22 || errFlag23){
			 $("#invoiceNumber").focus();
		}
		
		if ((errFlag0)|| (errFlag1) || (errFlag2) || (errFlag3) || (errFlag4) || (errFlag5) || (errFlag6) || (errFlag7) || (errFlag9) || (errFlag10) || (errFlag11) || (errFlag13) ||(errFlag14) ||(errFlag15) || (errFlag16) || (gstinStateValid) ||(errFlag17)|| (errFlag18) || (errFlag19) || (errFlag20) || (errFlag21) || (errFlag22) || (errFlag23)){
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

function validateSelect(id,spanid){
	if ($("#"+id).val() === "" || $("#"+id).val() === null){
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
	
	/*if (currencyRegex.test($("#quantity").val())!=true){
		 $("#quantity").addClass("input-error").removeClass("input-correct");
		 $("#quantity-csv-id").show();		 
	}
	
	if (currencyRegex.test($("#quantity").val())==true){
		 $("#quantity").addClass("input-correct").removeClass("input-error");
		 $("#quantity-csv-id").hide();		 
	}*/

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
		 if($('input[name=invoiceFor]').filter(':checked').val() == 'Product'){
			 response = callFieldCheck("quantity","quantity-csv-id");
			 clearInputErrorClass("amountToShow","amountToShow-csv-id"); 
		 }else{
			 response = callFieldCheck("amountToShow","amountToShow-csv-id");
			 clearInputErrorClass("amountToShow","amountToShow-csv-id"); 
		 }
		 
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

function validateCustomerContactNo(){
	errCustContactNo = validateTextField("customer_contactNo","contact-no-req",blankMsg);
	 if(!errCustContactNo){
		 errCustContactNo=validateRegexpressions("customer_contactNo","contact-no-req",regMsg,contactNumRegex);
	 }
	 return errCustContactNo;

}

$("#selectCountry").on("keyup click", function(){
	if ($("#selectCountry").val() === ""){
		$("#selectCountry").addClass("input-error").removeClass("input-correct");
		$("#selectCountry-csv-id").show();
	}
	if ($("#selectCountry").val() !== ""){
		$("#selectCountry").addClass("input-correct").removeClass("input-error");
		$("#selectCountry-csv-id").hide();
	}
});

$("#exportType").on("keyup click", function(){
	if ($("#exportType").val() === ""){
		$("#exportType").addClass("input-error").removeClass("input-correct");
		$("#exportType-csv-id").show();
	}
	if ($("#exportType").val() !== ""){
		$("#exportType").addClass("input-correct").removeClass("input-error");
		$("#exportType-csv-id").hide();
	}
});

function validateCess(){
	errsacServiceRate = validateTextField("cess","cess-csv-id",blankMsg);
	if(!errsacServiceRate){
		errsacServiceRate = validateRegexpressions("cess","cess-csv-id",cessMsg,currencyRegex);
	 }
	return errsacServiceRate;
}

$("#shipTo_customer_name").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
	if ($("#shipTo_customer_name").val().length > 1){
		 $("#shipTo_customer_name").addClass("input-correct").removeClass("input-error");
		 $("#shipTo-customer-name-csv-id").hide();
		 
	}
	if ($("#shipTo_customer_name").val().length < 1){
		 $("#shipTo_customer_name").addClass("input-error").removeClass("input-correct");
		 $("#shipTo-customer-name-csv-id").show();
	}	
});

$("#shipTo_address").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	//this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
	if ($("#shipTo_address").val().length > 1){
		 $("#shipTo_address").addClass("input-correct").removeClass("input-error");
		 $("#shipTo-customer-address-csv-id").hide();
		 
	}
	if ($("#shipTo_address").val().length < 1){
		 $("#shipTo_address").addClass("input-error").removeClass("input-correct");
		 $("#shipTo-customer-address-csv-id").show();
	}	
});


$("#selectState").on("keyup click", function(){
	if ($("#selectState").val() === ""){
		$("#selectState").addClass("input-error").removeClass("input-correct");
		$("#selectState-csv-id").show();
	}
	if ($("#selectState").val() !== ""){
		$("#selectState").addClass("input-correct").removeClass("input-error");
		$("#selectState-csv-id").hide();
	}

	
});

$("#shipTo_city").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	//this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
	if ($("#shipTo_city").val().length > 1){
		 $("#shipTo_city").addClass("input-correct").removeClass("input-error");
		 $("#shipTo-customer-city-csv-id").hide();
		 
	}
	if ($("#shipTo_city").val().length < 1){
		 $("#shipTo_city").addClass("input-error").removeClass("input-correct");
		 $("#shipTo-customer-city-csv-id").show();
	}	
});


/* Adding client side validation - End */

/* Preview Final Invoice Client JS - Start */

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

$("#documentType").on("keyup click", function(){
	if ($("#documentType").val() === ""){
		$("#documentType").addClass("input-error").removeClass("input-correct");
		$("#documentType-csv-id").show();
	}
	if ($("#documentType").val() !== ""){
		$("#documentType").addClass("input-correct").removeClass("input-error");
		$("#documentType-csv-id").hide();
	}
});

$('input[type=radio][name=itemDiscountType]').change(function() {
   $("#offerAmount").val("");
});

$("#descriptionTxt").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z0-9.-\s/_]*$/, '');
});


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

$("#customer_name").on("keyup click", function(e){
	var selectedDocType = $("#selectedDocType").val();
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z0-9-]*$/, ''); 

	if ($("#customer_name").val() === ""){
		$("#customer_name").addClass("input-error").removeClass("input-correct");
		if(selectedDocType == "rcInvoice"){
			$("#customer-name-rchg-csv-id").show();
		}else{
			$("#customer-name-csv-id").show();
		}
	}
	if ($("#customer_name").val() !== ""){
		$("#customer_name").addClass("input-correct").removeClass("input-error");
		if(selectedDocType == "rcInvoice"){
			$("#customer-name-rchg-csv-id").hide();
		}else{
			$("#customer-name-csv-id").hide();
		}
	}
});

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

$("#customer_place,#shipTo_address").on("keyup input",function(){
	 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
});

$("#customer_country,#shipTo_city").on("keyup input",function(){
	 this.value = this.value.replace(/[^[a-zA-Z-\s,]*$/, '');
});

$("#shipTo_pincode").on("keyup input",function(){
	this.value = this.value.replace(/[^0-9]+/, '');
});


function validateCustomerEMailId(){
	var errFlag14 = validateTextField("cust_email_addr","cust-email-format",blankMsg);
	 if(!errFlag14){
		 errFlag14=validateRegexpressions("cust_email_addr","cust-email-format", regMsg, emailRegex);
	 }
	 return errFlag14;
}

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
/* Customer Email validations - End */

$(document).ready(function() {
	$("#ecommerceGstin").on("keyup input", function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		this.value = this.value.toUpperCase();
		this.value = this.value.replace(/^00/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
		if((this.value!='') && GstinNumRegex.test($("#ecommerceGstin").val()) === true){				
			$("#ecommerce-gstin-csv-id").hide(); 
			$("#ecommerceGstin").addClass("input-correct").removeClass("input-error");
			 
		}
		if((this.value!='') && GstinNumRegex.test($("#ecommerceGstin").val()) !== true){
			$("#ecommerce-gstin-csv-id").show(); 
			$("#ecommerceGstin").addClass("input-error").removeClass("input-correct");
			 	
		}
		if($("#ecommerceGstin").val() == ""){				
			$("#ecommerce-gstin-csv-id").hide(); 
			$("#ecommerceGstin").addClass("input-correct").removeClass("input-error");
			 
		}
	});
});

function validateEcommerceGstin(){
	var	errEcommerceGstin  = validateTextField("ecommerceGstin","ecommerce-gstin-csv-id",blankMsg);
	if(!errEcommerceGstin){
		errEcommerceGstin = validateRegexpressions("ecommerceGstin","ecommerce-gstin-csv-id",gstinMsg,GstinNumRegex);
	}
	var selectedUserGstnZZ = $("#gstnStateId option:selected").text().split('[')[0].trim();
	if(!errEcommerceGstin){
		if(selectedUserGstnZZ == $("#ecommerceGstin").val().trim()){
			$("#ecommerceGstin").addClass("input-error").removeClass("input-correct");
			$("#ecommerce-gstin-csv-id").text("Selected GSTIN and Ecommerce GSTIN cannot be same");
			$("#ecommerce-gstin-csv-id").show();
			errEcommerceGstin = true;
		}
	}
	
	return errEcommerceGstin;
}

$(document).ready(function() { 
	var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;/*/(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;*/
	var msg = "Enter cess amount in proper format";
	var discountErrmsg = "Enter Discount % or Value in proper format";
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
			$("#offerAmount-csv-id").text(discountErrmsg);
			$("#offerAmount-csv-id").show();
			$("#offerAmount").addClass("input-error").removeClass("input-correct");	
		}
		if($("#offerAmount").val().length == 0 ){
			$("#offerAmount-csv-id").hide();
			$("#offerAmount").addClass("").removeClass("input-error");	
		}
	});
	
	$("#offerAmount").on("blur",function(){
		
		onblurOfDiscountTypeInAdddingItem();
	});
	
	
	$("#footerNote").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		
	});
	
	
});

$("#invoiceNumber").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z0-9\s/]*$/, '');
	
	 if ($("#invoiceNumber").val() !== ""){
		 $("#invoiceNumber").addClass("input-correct").removeClass("input-error");
		 $("#invoiceNumber-csv-id").hide();		 
	 }
	 if ($("#invoiceNumber").val() === ""){
		 $("#invoiceNumber").addClass("input-error").removeClass("input-correct");
		 $("#invoiceNumber-csv-id").show();		 
	 } 
	 
	 $("#invoiceNumber-duplicate-csv-id").hide();	
}); 

function validGstinStateCode(){
	var	gstinStateValid = validateGstinStateCode("ecommerceGstin","ecommerce-gstin-csv-id",gstinStateErrMsg);
	 
	 return gstinStateValid;
}

$(document).ready(function(){ 
	$( "#add_chg_add" ).on( "click", function(e) {
		var errFlag0 = validateSelect("additionalChargeName","search-additional-charge-autocomplete-csv-id");
		if(!errFlag0){
			add_chg_row();
		}
	});
	
	$( "#add_chg_edit" ).on( "click", function(e) {
		var errFlag0 = validateSelect("additionalChargeName","search-additional-charge-autocomplete-csv-id");
		if(!errFlag0){
			update_add_chg_row();
		}
	
	});
});

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
	