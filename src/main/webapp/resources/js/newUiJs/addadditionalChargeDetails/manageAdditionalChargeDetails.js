var blankMsg="This field is required";
var length = 2, pinCodeLength = 6;
var lengthMsg = "Minimum length should be ";
var igstMsg = "Rate of tax (%) should be in proper format";
var regMsg = "This field is required and should be in a proper format";

//	var currencyRegex = /(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;
var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var custContactExists = false; 
	
jQuery(document).ready(function($){
	$("#addAdditionalChargeDetails").hide();
    $('#addheader').hide();	
	
    $('#addAdditionalCharge').on('click', function(e) {
    	$('.loader').show();
    	$('#addAdditionalChargeDetails').slideToggle();
   	 	$('#listheader').hide();
    	 $('#listTable').hide();
    	 $('#addAdditionalChargeButton').hide();
    	 $('#addheader').show();
    	 
    	 $(".loader").fadeOut("slow");
    	e.preventDefault();
    })
    $('#gobacktolisttable').on('click', function(e){
    	$('.loader').show();
   	 	 $('#listheader').show();
    	 $('#listTable').slideToggle();
	   	 $('#addAdditionalChargeButton').show();
	   	 $('#addheader').hide();
	     $('#addAdditionalChargeDetails').hide();
	     $('#addService').show();
    	 $(".loader").fadeOut("slow");
	     
    });    
    
    
    var editPage = $("#editPage").val();
	if(editPage == 'false'){
		$("#chargeValue").val('0');
	} 	
	loadAddChargeTable();
	//createDataTable('additionalChargesValuesTab');	

	$("#chargesSubmitBtn").click(function(e){
		$('.loader').show();
		 var errChargeNameValueFlag = validateAddChargeName();
		 var errChargeValueFlag = validateChargeValue();	
	 	if((errChargeNameValueFlag) || (errChargeValueFlag)){
	 		$(".loader").fadeOut("slow");
	 		e.preventDefault();
		}		 	
	 	if((errChargeNameValueFlag)){
			 focusTextBox("chargeNameValue");
	 	} else if(errChargeValueFlag){
	 		 focusTextBox("chargeValuedetail");
	 	} 	 	
	});

	$("#chargeNameValue").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }		
		this.value = this.value.replace(/[\\[]*$/, '');
	//	this.value = this.value.replace(/[^[a-zA-Z\s]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		
		if ($("#chargeNameValue").val().length > 1){
			 $("#chargeNameValue").addClass("input-correct").removeClass("input-error");
			 $("#charge-name-req").hide();			 
		 }		
		if ($("#chargeNameValue").val().length < 1){
			 $("#chargeNameValue").addClass("input-error").removeClass("input-correct");
			 $("#charge-name-req").show();
		}
	});
		
	 $("#chargeValuedetail").on("keyup input",function(){ 	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	if(currencyRegex.test($("#chargeValuedetail").val()) === true){
			$("#charge-value-req").hide();
			$("#chargeValuedetail").addClass("input-correct").removeClass("input-error");	
		}    	
		if(currencyRegex.test($("#chargeValuedetail").val()) !== true){
			$("#charge-value-req").text(regMsg);
			$("#charge-value-req").show();
			$("#chargeValuedetail").addClass("input-error").removeClass("input-correct");	
		}			
    });
	 
	 $(".loader").fadeOut("slow");
});

function val(){
	bootbox.alert($("#userName").val());
	return true;
}

function loadAddChargeTable(){
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

			setCsrfToken(request.getResponseHeader('_csrf_token'));	
			
			$.each(json,function(i,value){
				$('#additionalChargesValuesTab tbody:last-child')
					.append('<tr>'
						
		        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.chargeName+'</a></td>' 
		        		+'<td>'+value.chargeValue+'</a></td>'
		        		//+'<td style="text-align: center;"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-eye" aria-hidden="true"></i></a></td>'
		        		
	        		+'</tr>'
	        	);		 
			});
			 $('#additionalChargesValuesTab').DataTable({
		     	bSort: false,
			        rowReorder: {
			              selector: 'td:nth-child(2)'
			        },
		         responsive: true
		 });
         },
       

		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});  	
}

function validateAddChargeName(){
	  var errChargeNameValue = validateTextField("chargeNameValue","charge-name-req",blankMsg);
	 if(!errChargeNameValue){
		 errChargeNameValue=validateFieldLength("chargeNameValue","charge-name-req",lengthMsg,length);
	 }
	 return errChargeNameValue;
}

function validateChargeValue(){
	var errChargeValue = validateTextField("chargeValuedetail","charge-value-req",blankMsg);
	if(!errChargeValue){
		errChargeValue=validateRegexpressions("chargeValuedetail","charge-value-req",regMsg,currencyRegex);
	 }
	return errChargeValue;
}


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


