
jQuery(document).ready(function($){

	$("#addAdditionalChargeDetails").hide();
    $('#listheader2').hide();
	
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
    //as per business they need to hide the list of customer on click of edit
	if($("#actionPerformed").val()){
		 $('#toggle').hide();
	}
    $('#addAdditionalCharge').on('click', function(e) {
    	$("#addAdditionalChargeDetails").slideToggle();
    	 $('#addAdditionalCharge').hide();
    	 $('.container').hide();
     	 $('#listheader1').hide();
     	 $('#listheader2').show();
    	e.preventDefault();
    })
});


function val(){
	bootbox.alert($("#userName").val());
	return true;
}


$(document).ready(function(){	
	var blankMsg="This field is required";
	var length = 2, pinCodeLength = 6;
	var lengthMsg = "Minimum length should be ";
	var igstMsg = "Rate of tax (%) should be in proper format";
	var regMsg = "This field is required and should be in a proper format";
	
//	var currencyRegex = /(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;
	var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
	var percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
	var custContactExists = false; 
		
	$("#chargesSubmitBtn").click(function(e){		
		var errChargeName = validateAddChargeName();
		var errChargeValue = validateChargeValue();
	//	var errChargeRate = validateChargeRate();		
	 	if((errChargeName) || (errChargeValue)){
	 		e.preventDefault();
		}	
	 	
	 	if((errChargeName)){
			 focusTextBox("chargeName");
	 	} else if(errChargeValue){
	 		 focusTextBox("chargeValue");
	 	} 	 	
	});

	function validateAddChargeName(){		
		errChargeName = validateTextField("chargeName","cust-name-req",blankMsg);
		 if(!errChargeName){
			 errChargeName=validateFieldLength("chargeName","cust-name-req",lengthMsg,length);
		 }
		 return errChargeName;
	}

	function validateChargeValue(){		
		errChargeValue = validateTextField("chargeValue","contact-no-req",blankMsg);
		if(!errChargeValue){
			errChargeValue=validateRegexpressions("chargeValue","contact-no-req",regMsg,currencyRegex);
		 }
		return errChargeValue;
	}
		
	$("#chargeName").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		
		this.value = this.value.replace(/[\\[]*$/, '');
	//	this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');		
		if ($("#chargeName").val().length > 1){
			 $("#chargeName").addClass("input-correct").removeClass("input-error");
			 $("#cust-name-req").hide();			 
		 }
		
		if ($("#chargeName").val().length < 1){
			 $("#chargeName").addClass("input-error").removeClass("input-correct");
			 $("#cust-name-req").show();
		}		
	});
		
	 $("#chargeValue").on("keyup input",function(){	    	
	    	this.value = this.value.replace(/[^[0-9.]*$/, '');	    	
	    	if(currencyRegex.test($("#chargeValue").val()) === true){
				$("#contact-no-req").hide();
				$("#chargeValue").addClass("input-correct").removeClass("input-error");	
			}	    	
			if(currencyRegex.test($("#chargeValue").val()) !== true){
				$("#contact-no-req").text(regMsg);
				$("#contact-no-req").show();
				$("#chargeValue").addClass("input-error").removeClass("input-correct");	
			}			
	  });			
});


$(document).ready(function(){
		var editPage = $("#editPage").val();
	if(editPage == 'false'){
		$("#chargeValue").val('0');
	} 	
});

$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});

function is_int(value) {
    if ((parseFloat(value) == parseInt(value)) && !isNaN(value)) {
      return true;
    } else {
      return false;
    }
  }


function editRecord(idValue){
	document.editAdditionalChargeDetails.action = "./wizaedEditAdditionalChargeDetails";
	document.editAdditionalChargeDetails.id.value = idValue;
	document.editAdditionalChargeDetails.submit();	
}


function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
			document.editAdditionalChargeDetails.action = "./wizardDeleteAddChargeDetails";
			document.editAdditionalChargeDetails.id.value = idValue;
			document.editAdditionalChargeDetails._csrf_token.value = $("#_csrf_token").val();
			document.editAdditionalChargeDetails.submit();	
		 }
	});
	 
}

