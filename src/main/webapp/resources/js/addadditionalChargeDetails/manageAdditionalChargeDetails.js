
jQuery(document).ready(function($){
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
    //as per business they need to hide the list of customer on click of edit
	if($("#actionPerformed").val()){
		 $('#toggle').hide();
	}
    $('#addCustomer').on('click', function(e) {
    	$(".addCustomer").slideToggle();
    	 $('.additionalChargesValuesTable').hide();
    	 $('#addCustomer').hide();
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
	 
			    
    $.ajax({
		url : "getAddChargesDetailsList",
		type : "post",
		/*contentType : "application/json",*/
		dataType : "json",
		data : { _csrf_token : $("#_csrf_token").val()},
		async : false,
		beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success:function(json,textStatus,request){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			 $.each(json,function(i,value){
				
				 $('.additionalChargesValues').append(
						 +'<tbody>'			
							+'<tr>'
								+'<td class="text-left"><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.chargeName+'</a></td>' 
								+'<td class="text-right">'+value.chargeValue+'<center></td>'
								+'</tr>'
					+'</tbody>'
				 
				 
				 );
			});
				setCsrfToken(request.getResponseHeader('_csrf_token'));	
         },
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
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
	document.editAdditionalChargeDetails.action = "./editAdditionalChargeDetails";
	document.editAdditionalChargeDetails.id.value = idValue;
	document.editAdditionalChargeDetails._csrf_token.value = $("#_csrf_token").val();
	document.editAdditionalChargeDetails.submit();	
}


function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
			document.editAdditionalChargeDetails.action = "./deleteAddChargeDetails";
			document.editAdditionalChargeDetails.id.value = idValue;
			document.editAdditionalChargeDetails._csrf_token.value = $("#_csrf_token").val();
			document.editAdditionalChargeDetails.submit();	
		 }
	});
	 
}
$(document).ready(function() {
    $('#additionalChargesValuesTab').DataTable(
              {
            	  "aaSorting": [],
                    "bLengthChange": false,
                    "pagingType": "simple",
                    
                    
              }
    
    );
} );

