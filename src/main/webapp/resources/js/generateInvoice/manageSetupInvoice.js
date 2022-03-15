$(document).ready(function(){
	
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var regMsg = "data should be in proper format";
	var blankMsg="This field is required";
	
	$("#invoiceUpdateBtn").click(function(e){
		errFlag = validateCustomerEmail();
		
		if(errFlag){
			e.preventDefault();
		}
		
		if((errFlag) ){
			 focusTextBox("custEmail");
		 } 
		 
		 });
	
	
	function validateCustomerEmail(){
		errFlag = validateTextField("custEmail","cust-email-format",blankMsg);
		 if(!errFlag){
			 errFlag=validateRegexpressions("custEmail","cust-email-format",regMsg,emailRegex);
		 }
		 return errFlag;

	}

		$("#custEmail").on("keyup input", function(){
			if(emailRegex.test($("#custEmail").val()) === true){
				$("#cust-email-format").hide();
				$("#custEmail").addClass("input-correct").removeClass("input-error");
				return true;
				}
			if(!emailRegex.test($("#custEmail").val())){
			$("#cust-email-format").show();
			$("#custEmail").addClass("input-error").removeClass("input-correct");
			$("#custEmail").focus();
			return true;
		}
		if(emailRegex.test($("#custEmail").val())){
			$("#cust-email-format").show();
			$("#custEmail").addClass("input-correct").removeClass("input-error");
			$("#custEmail").focus();
			return true;
		}
		
		});
	
});