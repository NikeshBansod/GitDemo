

$(document).ready(function(){ 
	$( "#submitPartyChgBtn" ).on( "click", function(e) {
		
		var errFlag1 = false;	
		var errFlag2 = false;
		var errFlag3 = false;
		var errFlag4 = false;
		var errFlag5 = false;
		var errFlag6 = false;
		var errFlag7 = false;
		var errFlag8 = false;
		var previousCustomerId = $("#old_customer_id").val();	
		var customerId = $("#customer_id").val();	
		
		var isCheckedShippingToBilling = $('#shipToBill').is(':checked');
		if(isCheckedShippingToBilling == false){
		
			   errFlag1 = validateTextField("shipTo_customer_name","shipTo-customer-name-csv-id",blankMsg);
			   errFlag2 = validateTextField("shipTo_address","shipTo-customer-address-csv-id",blankMsg);
			   errFlag3 = validateTextField("shipTo_pincode","shipTo-customer-pincode-csv-id",blankMsg);
			   if(!errFlag3){
					   errFlag3 = validatePinCodeField("shipTo_pincode","shipTo-customer-pincode-csv-id",'Pincode should have 6 digits'); 
				  
			   }
			   errFlag4 = validateTextField("shipTo_city","shipTo-customer-city-csv-id",blankMsg);	  
	    }
		
		var errFlag6 = validateCustomerField("customer_id","customer_name","customer-name-csv-id",blankMsg);
		
		if(!errFlag6 && previousCustomerId == customerId){
			if(isCheckedShippingToBilling){
				errFlag5 = true;
				bootbox.alert("Previous and New Customer is same");
			}
		}
		
		if($("#invDataFiledToGstn").val() == "true"){
			if($("#invoiceSequenceType").val() != 'Auto'){
				errFlag7 = validateTextField("invoiceNumber","invoiceNumber-csv-id","This field is required");
				errFlag8 = checkIfInvoiceNumberAlreadyPresent($("#invoiceNumber").val());
				if(errFlag8){
					$("#invoiceNumber").addClass("input-error").removeClass("input-correct");
					$("#invoiceNumber-duplicate-csv-id").text("Invoice Number Already Present");
					$("#invoiceNumber-duplicate-csv-id").show();
				}
			}
		}
		
		if(errFlag7 || errFlag8){
			 $("#invoiceNumber").focus();
		}
		
		
		if ((errFlag1) || (errFlag2) || (errFlag3) || (errFlag4) || (errFlag5) || (errFlag6) || (errFlag7) || (errFlag8)){
			 e.preventDefault();	 
		}else{
			//alert("SUCCESS");
			callPostPartyChange();
		}
		
	});
});

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

$("#shipTo_pincode").on("keyup input",function(){
	this.value = this.value.replace(/[^0-9]+/, '');
});

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