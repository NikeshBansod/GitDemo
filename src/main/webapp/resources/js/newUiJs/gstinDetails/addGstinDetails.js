 var gstin = $("#reg-gstin").val();
$(document).ready(function(){
	$("#removeButtonEdit").show();
	$("#addGstnDetails").hide();
    $('#addheader').hide();	
            $('#addGstnButton').on('click', function(e) {
            	 $('.loader').show();
            	$('#addGstnDetails').slideToggle();
           	 	$('#listheader').hide();
            	 $('#listTable').hide();
            	 $('#addGstnButton').hide();
            	 $('#addheader').show();
            	 $(".loader").fadeOut("slow");
            	e.preventDefault();
            })
            $('#gobacktolisttable').on('click', function(e){
             $('.loader').show();
          	 $('#listheader').show();
           	 $('#listTable').slideToggle();
       	   	 $('#addGstnButton').show();
       	   	 $('#addheader').hide();
       	     $('#addGstnDetails').hide();
     	     $(".loader").fadeOut("slow");
           });
            
            $(document).on('keyup', '.gstinLocationRequired', function(e){
            	if(e.keyCode == 32){
 				   this.value = removeWhiteSpace(this.value);
 			   }
			   this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
			   if ($(this).val() !== ""){
   	    		 $(this).addClass("input-correct").removeClass("input-error");
   	    		 $(this).parent().find('.gstinLocationError').hide();
   	    	   }
   	    	   if ($(this).val() === ""){
   	    		 /*$(this).css({"border-color" : "#ff0000"});*/
				 $(this).addClass("input-error").removeClass("input-correct");
	   	    	 $(this).parent().find('.gstinLocationError').show();
   	    	   } 
            })
	
	
	$("#removeButton").hide();
            if ($('.gstinLocation ').length >= 1) {
				$(".loader").fadeOut("slow");
				$("#removeButton").show();
	        }
            else if ($('.gstinLocation ').length < 1){
            	$("#removeButton").hide();
            	bootbox.alert("Maximum 1 Locations has to be added");
            }
            
	   $("#addButton").click(function () {
		   $("#removeButtonEdit").show();
		   $('.loader').show();
		   var counterButton = $("#counterButtonValue").val();
			if(counterButton < 6){
				
				var currentCount = counterButton;
				currentCount++;
				$("#dynamicLocCount").val(currentCount);	
			}
			
			if($("#adddynamicloc").is(':hidden')){
				$("#adddynamicloc").show();
			}
			if($("#removeButton").is(':hidden')){
				$("#removeButton").show();
				$("#removeButtonEdit").show();
			}
			if ($('.gstinLocation ').length >= 6) {
				$(".loader").fadeOut("slow");
				$("#addButton").hide();
	            bootbox.alert("Maximum 6 Locations Can be added");
	            return false;
	        }
			
	       var outerDivCount = $('.gstinLocation').length;
	       var newIndex=outerDivCount;
	       
	       var dynamicLocDivContent =  '<div class="col-md-4 gstinLocation" id="dynamiclocDiv'+newIndex+'">'
			                           +'<label for="label">Store/Location/Channel/Department: '+(newIndex+1)+'<span style="font-weight: bold;color: #ff0000;"> *</span></label>'
			                           +'<input name="gstinLocationSet['+newIndex+'].gstinLocation" type="text" id="gstinLocation_'+newIndex+'"  class="gstinLocationRequired"/>'
          	                           +'<span class="text-danger cust-error gstinLocationError" id="gstinLocation-empty_'+newIndex+'"></span>'
			                           +' </div>';
	       
	       var dynamicStoreDivContent = '<div hidden="true" class="col-md-4 gstinStore mandatory" id="dynamicStoreDiv'+newIndex+'">'
                                         +'<label for="label">Store: '+(newIndex+1)+'<span style="font-weight: bold;color: #ff0000;"> *</span></label>'
                                         +'<input name="gstinLocationSet['+newIndex+'].gstinStore" type="text" id="gstinStore_'+newIndex+'" />'
                                         +'<span class="text-danger cust-error" id="gstinStore-empty_'+newIndex+'"></span>'
                                         +'</div>';
	       
	       
	       $("#adddynamicloc").append(dynamicLocDivContent);	       
	       counterButton++;   
	       $("#counterButtonValue").val(counterButton);	
	       if(counterButton == 6){
				var endDynamicRow = '</div>'	
				$(endDynamicRow).insertAfter(dynamicLocDivContent);
		    }	
	       $(".loader").fadeOut("slow");
	    });
	   
	    
	   $("#removeButton").click(function () {
	    	 $('.loader').show();
	    	if (($('.gstinLocation ').length-1) == 1) {
	    		$("#removeButton").hide();
	    		$("#addButton").show();
	    		$("#adddynamicloc").hide();
	        }
	    	
	    	var removeLocDivId = $('.gstinLocation:last').attr('id');	    	
	    	$("#"+removeLocDivId).remove();
	    	
	    	if($("#counterButtonValue").val()<=6){
	    		$("#addButton").show();
	    	}
	
	    	var currentCount = $("#dynamicLocCount").val();
			currentCount--;
			$("#dynamicLocCount").val(currentCount);	
			
			var buttoncounter = $("#counterButtonValue").val();
			buttoncounter--;
			 $("#counterButtonValue").val(buttoncounter);
	    	
			 $(".loader").fadeOut("slow");
	    });
	   
	   $("#removeButtonEdit").click(function () {
		   var outerDivCount = $('.gstinLocation').length;
	    	 $('.loader').show();
	    	if (($('.gstinLocation ').length-1) == 1) {
	    		$("#removeButtonEdit").hide();
	    		$("#addButton").show();
	    		$("#adddynamicloc").hide();
	        }
	    	if(outerDivCount==1){
	    		$(".loader").fadeOut("slow");
	    		$("#removeButtonEdit").hide();
	    		$("#addButton").show();
	    	    bootbox.alert("Maximum 1 Locations is required");
	    	    return false;
	    	   
	    	}
	    	
	    	var removeLocDivId = $('.gstinLocation:last').attr('id');	    	
	    	$("#"+removeLocDivId).remove();
	    	
	    	if($("#counterButtonValue").val()<=6){
	    		$("#addButton").show();
	    	}
	
	    	var currentCount = $("#dynamicLocCount").val();
			currentCount--;
			$("#dynamicLocCount").val(currentCount);	
			
			var buttoncounter = $("#counterButtonValue").val();
			buttoncounter--;
			 $("#counterButtonValue").val(buttoncounter);
	    	
			 $(".loader").fadeOut("slow");
	    });
	
	
	
	var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/; 
	var blankMsg="This field is required";
	var lengthMsg = "Minimum length should be ";
	var length = 2;
	var regMsg = "data should be in proper format";
	var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
	var cmpMsg = "Pincode does not belong to the mentioned GSTIN's state";
//	var cmpMsg = "The State in Address should be same as GSTIN State";
	var gstinStateErrMsg = "Invalid State code for GSTIN entered";
	var gstinRegistered = false;
	var gstinValidWithRegisteredPan =false;
	var gstinLocation = false;
	var gstinStore = false;
	var gstinStateValid = false;

	
		$("#submitGstin").click(function(e){
	    $('.loader').show();
		var errFlag1 = regGstin();
		var errFlag2 = validateState();
		var errFlag3 = validateUser();
		var errFlag5 = validateAddress();
		var errFlag6  = validatePinCode(); 
		//gstinValidWithRegisteredPan=checkGSTINPANValidation(gstinValidWithRegisteredPan);
		validateGstinState();
		var errFlag7 = validateGstinAndStAddrState();
		gstinLocation = validateGstinLocation();
	    //gstinStore = validateStoreLocation();
		//gstinStateValid = validGstinStateCode();
		var errFlag8 = validateGSTNUserId();
		var errFlag9 = validategrossTurnover();
		var errFlag10 = validateCurrentTurnover();
		//var errFlag11 = validateAddressField();
		
		
		 if ((errFlag1) || (errFlag2) || (errFlag3) || (errFlag5) || (errFlag6) || (errFlag7) || (errFlag8)  || (errFlag9) || (errFlag10) || (gstinLocation)){
			 $(".loader").fadeOut("slow");
			 e.preventDefault();	 
		 } 
		 
		 if((errFlag1)){
			 focusTextBox("reg-gstin");
		 } else if((errFlag2) ){
			 focusTextBox("reg-gstin-state");
		 } else if((errFlag3)){
			 focusTextBox("gstinUname");
		 } else if((errFlag5)){
			 focusTextBox("address1");
		 } else if((errFlag6)){				
			 focusTextBox("pinCode");
		 } else if(errFlag7){
			 focusTextBox("pinCode");
		 } else if(errFlag8){
			 focusTextBox("gstnUserId");
		 } else if(errFlag9){
			 focusTextBox("grossTurnover");
		 } else if(errFlag10){
			 focusTextBox("currentTurnover");
		 }
		 
		 }); 

	
	 	
	 	function validategrossTurnover(){
			errFlag9 = validateTextField("grossTurnover","gross-turnover",blankMsg);
			 if(!errFlag9){
				 errFlag9=validateRegexpressions("grossTurnover","gross-turnover",regMsg,currencyRegex);
			 }
			 return errFlag9;
		}
	 	
	 	function validateCurrentTurnover(){
			errFlag10 = validateTextField("currentTurnover","current-turnover",blankMsg);
			 if(!errFlag10){
				 errFlag10=validateRegexpressions("currentTurnover","current-turnover",regMsg,currencyRegex);
			 }
			 return errFlag10;
		}
		
		
	
	
	function regGstin(){
  		errFlag1 = validateTextField("reg-gstin","reg-gstin-req",blankMsg);
		 if(!errFlag1){
			 errFlag1=validateRegexpressions("reg-gstin","reg-gstin-req",regMsg,GstinNumRegex);
		 }
		 return errFlag1;		  
} 
	
	function validateState(){
		var errFlag2 = false;
		errFlag2 = validateSelectField("reg-gstin-state","reg-gstin-state-reg");
	return errFlag2;	 
} 
	
	function validateUser(){	 		
		errFlag3 = validateTextField("gstinUname","reg-gstin-user-req",blankMsg);
	 if(!errFlag3){
		 errFlag3=validateFieldLength("gstinUname","reg-gstin-user-req",lengthMsg,length);
	 }
	 return errFlag3;
	} 
	
	function validateAddress(){			
		 errFlag5=validateFieldLength("address1","address1-req",lengthMsg,2);			 
		 return errFlag5;
}
	/*function validateAddressField(){			
		 errFlag11=validateTextField("address1","address1-req",blankMsg);			 
		 return errFlag11;
}*/
	
	function validatePinCode(){
	errFlag6 = validateTextField("pinCode","zip-req",blankMsg);
	 if(!errFlag6){
		 errFlag6=validateFieldLength("pinCode","zip-req",lengthMsg,6);
	 }
	 return errFlag6;
}
	
	function validateGstinAndStAddrState(){
	 errFlag7=compareTextFields("state","reg-gstin-state","empty-message",cmpMsg);			 
	 return errFlag7;
	}
	
	function validateGSTNUserId(){
		errFlag8 = validateTextField("gstnUserId","reg-gstin-id-req",blankMsg);
		 
		 return errFlag8;
	}
	
	function validGstinStateCode(){
		gstinStateValid=validateGstinStateCodeSpecific("reg-gstin","reg-gstin-back-req",gstinStateErrMsg);			 
	 return gstinStateValid;
	}
	 	
		function validateGstinStateCodeSpecific(id, spanid, msg) {
			var result=false;
			var stateCode=$("#" + id).val().substring(0, 2);
			var errorMsg = $("#" + spanid).text();
			if (stateCode ==0 || stateCode >37) {
				$("#" + id).addClass("input-error").removeClass("input-correct");
				if($('#reg-gstin-back-req').is(':visible')){
					
					$("#" + spanid).text(errorMsg+"   "+msg);
					$("#" + spanid).show();
				}else{
					$("#" + spanid).text(msg);
					$("#" + spanid).show();
				}					
				gstinStateValid=true;
			} else {
				if($('#reg-gstin-back-req').is(':hidden') || errorMsg === ""){
					$("#" + id).addClass("input-correct").removeClass("input-error");
					$("#" + spanid).hide();
				}				
				gstinStateValid=false;
			}
			return result;
		}
	 	
	 	
		function validateGstinLocation(){
	 		 $("#gstinLocation-empty").hide();
	 			
	 		gstinLocation=false;
	 		if($("#gstinLocation").val() === ''){ 				
				gstinLocation=true;
	 			$("#gstinLocation-empty").text("Location/Store cannot be Blank");
	 			$("#gstinLocation").addClass("input-error").removeClass("input-correct");
				$("#gstinLocation-empty").show();
				
	 		}
	 		//var locCount = $("#dynamicLocCount").val();
	 		var outerDivCount = $('.gstinLocation').length;
	 		for(var i=1; i<=outerDivCount;i++){ 				
				if($("#gstinLocation_"+i).val() === ''){
					gstinLocation=true;	 			
	 				$("#gstinLocation-empty_"+i).text("Location/Store cannot be Blank");
	 				$("#gstinLocation_"+i).addClass("input-error").removeClass("input-correct");
	 				$("#gstinLocation-empty_"+i).show();
	 				
	 			}
				else{
					$("#gstinLocation-empty_"+i).hide();
					$("#gstinLocation_"+i).addClass("input-correct").removeClass("input-error");
				}
				
	 		}
	 		return gstinLocation;	 		
	 	}
	 	
	 	function validateStoreLocation(){
	 		 $("#gstinStore-empty").hide();
	 		$( "input[name^='gstinStore']" ).each(function(index, obj){
	 			  
	 			if(obj.value==''){
	 				
	 				gstinStore=true;
	 				 $("#gstinStore-empty").text("Location/Store cannot be Blank");
	 				 $("#gstinStore-empty").show();
	 				
	 			}else{
	 				gstinStore=false;
	 				 $("#gstinStore-empty").hide();
	 			}
			}); 
	 		
	 		return gstinStore;
	 		
	 	}
	 	
	 	$.each($('[id^=gstinLocation]'), function (i, item) {
            $(document).on('keyup', '[id*="gstinLocation"]',function () {
            	this.value = this.value.replace(/[^[a-zA-Z0-9-, ]*$/, '');
            });

       	});
	 	
	 	$("#gstinLocation").on("keyup input", function(){
			this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
	  		 if ($("#gstinLocation").val() === ""){
		 			$("#gstinLocation-empty").text("Location/Store cannot be Blank");
					$("#gstinLocation-empty").show();
					$("#gstinLocation").addClass("input-error").removeClass("input-correct");
	  		 }
	  		if($("#gstinLocation").val() !== ""){
	  			$("#gstinLocation-empty").text("Location/Store cannot be Blank");
	  			$("#gstinLocation").addClass("input-correct").removeClass("input-error");
				$("#gstinLocation-empty").hide();
				
	  		 }
	  		
	  	});
	 	
	 	
	 	 $("#gstnUserId").on("keyup input",function(){
		    	if($("#gstnUserId").val() === ""){
					$("#reg-gstin-id-req").show();
					$("#gstnUserId").addClass("input-error").removeClass("input-correct");	
				}
				if($("#gstnUserId").val() !== ""){
					$("#reg-gstin-id-req").hide();
					$("#gstnUserId").addClass("input-correct").removeClass("input-error");	
				}
				    	
		    });
	 	
	    $("#grossTurnover").on("keyup input",function(){
	    	this.value = this.value.replace(/[^[0-9.]*$/, '');
	    	if(currencyRegex.test($("#grossTurnover").val()) === true){
				$("#gross-turnover").hide();
				$("#grossTurnover").addClass("input-correct").removeClass("input-error");	
			}
	    	
			if(currencyRegex.test($("#grossTurnover").val()) !== true){
				$("#gross-turnover").text(regMsg);
				$("#gross-turnover").show();
				$("#grossTurnover").addClass("input-error").removeClass("input-correct");	
			}
			if($("#grossTurnover").val() === ""){
				$("#gross-turnover").show();
				$("#grossTurnover").addClass("input-error").removeClass("input-correct");	
			}
			if($("#grossTurnover").val() !== ""){
				$("#gross-turnover").hide();
				$("#grossTurnover").addClass("input-correct").removeClass("input-error");	
			}
			    	
	    });
	 	
	    $("#currentTurnover").on("keyup input",function(){
	    	
	    	this.value = this.value.replace(/[^[0-9.]*$/, '');
	    	
	    	if(currencyRegex.test($("#currentTurnover").val()) === true){
				$("#current-turnover").hide();
				$("#currentTurnover").addClass("input-correct").removeClass("input-error");	
			}
	    	
			if(currencyRegex.test($("#currentTurnover").val()) !== true){
				//$("#current-turnover").text(regMsg);
				$("#current-turnover").show();
				$("#currentTurnover").addClass("input-error").removeClass("input-correct");	
			}
			
			/*if($("#current-turnover").val() === ""){
				$("#current-turnover").show();
				$("#currentTurnover").addClass("input-error").removeClass("input-correct");	
			}*/
			if($("#current-turnover").val() !== ""){
				$("#current-turnover").hide();
				$("#currentTurnover").addClass("input-correct").removeClass("input-error");	
			}
			    	
	    });

	
		
		
	  	$("#reg-gstin").on("keyup input",function(e){
	  		if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			 this.value = this.value.toUpperCase();
			 this.value = this.value.replace(/^00/, '');
			 
		if(GstinNumRegex.test(this.value) === true){				
			 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
			 $("#reg-gstin-req").hide();
			 
		}
		if(GstinNumRegex.test(this.value) !== true){
			 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
			 $("#reg-gstin-req").text('This field is required and should be in a proper format');
			 $("#reg-gstin-req").show();
			 $("#reg-gstin").focus();
		}
		/*if ($("#reg-gstin").val() !== ""){
			$("#reg-gstin").addClass("input-correct").removeClass("input-error");
			$("#reg-gstin-req").hide();
		}*/
		if ($("#reg-gstin").val() === ""){
			$("#reg-gstin").addClass("input-error").removeClass("input-correct");
			$("#reg-gstin-req").show();
		}
		
		}); 
		
		  	$("#gstinUname").on("keyup input", function(e){
		  		if(e.keyCode == 32){
					   this.value = removeWhiteSpace(this.value);
				   }
		  		this.value = this.value.replace(/[^[a-zA-Z-, ]*$/, '');
		  		 if ($("#gstinUname").val() === ""){
		  			 $("#gstinUname").addClass("input-error").removeClass("input-correct");
		  			 $("#reg-gstin-user-req").show();
		  			 
		  		 }
		  		 if ($("#gstinUname").val() !== ""){
		  			 $("#gstinUname").addClass("input-correct").removeClass("input-error");
		  			 $("#reg-gstin-user-req").hide();
		  			 
		  		 }
		  	});

		  	
		  	$("#reg-gstin").on("keyup input",function(e){
		  		if(e.keyCode == 32){
					   this.value = removeWhiteSpace(this.value);
				   }
				 this.value = this.value.toUpperCase();
				 this.value = this.value.replace(/^00/, '');
				 
			if(GstinNumRegex.test(this.value) === true){				
				 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
				 $("#reg-gstin-req").hide();
			}
			if(GstinNumRegex.test(this.value) !== true){
				 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
				 $("#reg-gstin-req").text('This field is required and should be in a proper format');
				 $("#reg-gstin-req").show();
				 $("#reg-gstin").focus();
			}
			}); 
	 
	 
		  	$("#reg-gstin-state").change(function(){
			if ($("#reg-gstin-state").val() !== ""){
				$("#reg-gstin-state").addClass("input-correct").removeClass("input-error");
				$("#reg-gstin-state-reg").hide();
			}
			if ($("#reg-gstin-state").val() === ""){
				$("#reg-gstin-state").addClass("input-error").removeClass("input-correct");
				$("#reg-gstin-state-reg").show();
			}
			});
		  	
		  	$("#address1").on("keyup input",function(){
				 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
				if ($("#address1").val() !== ""){
					 $("#address1").addClass("input-correct").removeClass("input-error");
					 $("#address1-req").hide();		 
				 }
				 
			});
	 
		  	function validateGSTINWithRegex(gstin){
				if(GstinNumRegex.test(gstin) === true){				
					 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
					 $("#reg-gstin-req").hide();
					 $("#reg-gstin-back-req").hide();
					 return true;
				}
				if(GstinNumRegex.test(gstin) !== true){
					 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
					 $("#reg-gstin-req").show();
				//	 $("#reg-gstin").focus();
					 $("#reg-gstin-back-req").hide();
					 return false;
				}
				
			}

		  	 function compareTextFields(id1, id2, spanid, msg){
		  		var result=false;
		  		if ($("#"+id1).val() === $.trim($("#"+id2).text())){
		  			 $("#"+spanid).hide();
		  			 result= false;
		  		 } else { 
		  			 $("#"+spanid).text(msg);
		  			 $("#"+spanid).show();
		  			 result= true;
		  		 }
		  		 return result;
		  	}
		  	 
		  	 
			 $("#pinCode").on("keyup input", function(){
					var contactNumRegex = /[0-9]{2}\d{8}/;
					if(contactNumRegex.test(this.value) !== true){
						this.value = this.value.replace(/[^0-9]+/, '');
						 $("#pinCode").addClass("input-error").removeClass("input-correct");
						 $("#zip-req").show();
						 $("#pinCode").focus();
					}
					if ($("#pinCode").val().length === 6){
						 $("#pinCode").addClass("input-correct").removeClass("input-error");
						 $("#zip-req").hide();
						 
					}
				}); 
			 
			 getGstinDetails();
    
 	$("#reg-gstin").blur(function(){
	    var gstin = $("#reg-gstin").val();
	    if(gstin != ''){
	    	checkIfGstinIsRegistered(gstin);
	    }	    
	});
 	
 	function checkIfGstinIsRegistered(gstin){
		if(validateGSTINWithRegex(gstin)){
	    $.ajax({
			url : "checkIfGstinIsRegistered",
			type : "POST",
			//method : "GET",
			//contentType : "application/json",
			dataType : "json",
			data : {gstin : gstin},
			headers: {_csrf_token : $("#_csrf_token").val()},
			async : false,
			beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },

			success : function(jsonVal,fTextStatus,fRequest) {
				if (isValidSession(jsonVal) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				if(isValidToken(jsonVal) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				if(jsonVal == true){
					 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
					 $("#reg-gstin-back-req").text('GSTIN  - '+gstin+' already Registered. Try some other GSTIN.');
					 $("#reg-gstin-back-req").show();
					 gstinRegistered = true;
				}else{
					 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
					 $("#reg-gstin-req").hide();
					 $("#reg-gstin-back-req").hide();
					 gstinRegistered = false;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {						 
				 getInternalServerErrorPage();   
			}
			
        });
	    var isValidPan = checkGSTINPANValidation(gstinValidWithRegisteredPan);
	    if(isValidPan!= true && gstinRegistered!=true){
	    	getDataByGstinNumber(gstin);    	
	    }	    
    }
	}
 	
 	function getDataByGstinNumber(gstin){
 		$("#reg-gstin-state").empty();
	$.ajax({
		url : "getDataByGstinNumber" ,
		type : "post",
		data : {"gstinNo":gstin},
		headers: {_csrf_token : $("#_csrf_token").val()},
		dataType : "json",
		async : false,
		beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success : function(response,fTextStatus,fRequest) {
			if (isValidSession(response) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(response) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			if(response.err_cd!=null){
				$("#state").val("");
				$("#gstinUname").val("");
				bootbox.alert("Invalid GSTIN,Please enter valid GSTIN");
				
			}else{ 								
				var address = "";
				getStateByGstinNumber(gstin);
				if(response.pradr != "" && response.pradr != undefined){
					if(response.pradr.addr != "" && response.pradr.addr != undefined){
						
						if(response.pradr.addr.bno != "" && response.pradr.addr.bno != undefined){
							address = response.pradr.addr.bno + ", ";
						}
						if(response.pradr.addr.bnm != "" && response.pradr.addr.bnm != undefined){ 
							address = address + response.pradr.addr.bnm + ", ";
						}
					}						
					address = address + response.pradr.addr.st + " "+ response.pradr.addr.loc; 
				}
				
				/*
				if(response.pradr.addr.bno != ""){
					address = response.pradr.addr.bno + ", ";
				}if(response.pradr.addr.bnm != ""){ 
					address = address + response.pradr.addr.bnm + ", ";
				}
				address = address + response.pradr.addr.st + " "+ response.pradr.addr.loc; */
				$("#gstinUname").val(response.tradeNam);
				$("#gstinUname").addClass("input-correct").removeClass("input-error");
				$("#reg-gstin-user-req").hide();
				$("#address1").val(address);
				//$("#address1").addClass("input-correct").removeClass("input-error");
				//$("#address1-req").hide();
				/*$("#pinCode").val(response.pradr.addr.pncd);
				$("#city").val(response.pradr.addr.lgnm);
				$("#state").val(response.pradr.addr.lgnm);*/
				//getStateByGstinNumber(gstinNumber); 								
			}
		},
		error: function (data,status,er) { 							 
			 getInternalServerErrorPage();   
		}
	});
	}
 	
 	function getStateByGstinNumber(gstin){
		$("#reg-gstin-state").empty();
	$.ajax({
		url : "getStateByGstinNumber" ,
		type : "post",
		data : {"id":gstin},
		headers: {_csrf_token : $("#_csrf_token").val()},
		dataType : "json",
		async : false,
		beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success : function(response,fTextStatus,fRequest) {
			if (isValidSession(response) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(response) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if(response==null){
					$("#state").val("");
			}else{
				$("#reg-gstin-state").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId)); 	
				$("#reg-gstin-state").addClass("input-correct").removeClass("input-error");
				$("#reg-gstin-state-reg").hide();
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) { 							 
			 getInternalServerErrorPage();   
		}
	});
	}
 	
 	$(".loader").fadeOut("slow");	
});


function getPANFromGSTIN(){
	var gstin = $("#reg-gstin").val();
	var panNo=gstin.substring(2,12);
	return panNo;
}

function validateGstinState(){
	if ($("#reg-gstin-state").val() != null){
		$("#reg-gstin-state").addClass("input-correct").removeClass("input-error");
		$("#reg-gstin-state-reg").hide();
	}
	if ($("#reg-gstin-state").val() == null){
		$("#reg-gstin-state").addClass("input-error").removeClass("input-correct");
		$("#reg-gstin-state-reg").show();
	}
}

function getGstinDetails(){
$.ajax({
	url : "getGstinDetailsAndCity",
	method : "POST",
//	contentType : "application/json",
	dataType : "json",
	async : false,
	headers: {_csrf_token : $("#_csrf_token").val()},
	beforeSend: function(){
        $('.loader').show();
    },
    complete: function(){
        $('.loader').hide();
    },

	success:function(json,fTextStatus,fRequest){
		if (isValidSession(json) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}

		if(isValidToken(json) == 'No'){
			window.location.href = getCsrfErrorPage();
			return;
		}
		
		
		 $.each(json,function(i,value){
				
				 $('#gstinValuesTab tbody:last-child')
					.append('<tr>'
							//+'<td>'+(i+1)+'</td>'
							+'<td><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.gstinNo+'</a></td>'
			        		+'<td>'+value.nickName+'</td>'
			        		+'<td>'+value.stateInString+'</td>'
			        		+'<td>'+value.gstinAddressMapping.city+'</td>'
			        		//+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-eye" aria-hidden="true"></i></a></td>'
			        		//+'<td><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>' 
			        		//+'<td><a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'
			        		+'</tr>'
			        	);
			});
		 createDataTable('gstinValuesTab');
		
		 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
     },
     error: function (data,status,er) {
    	 
    	 getInternalServerErrorPage();   
    }
});
}


function checkGSTINPANValidation(gstinValidWithRegisteredPan){
	var panNo = getPANFromGSTIN();
	var gstin = $("#reg-gstin").val();
	$.ajax({
		url : "checkIfGstinIsValidWithRegisteredPAN",
		type : "POST",
		dataType : "json",
		async : false,
		data : {panNo : panNo},
		headers: {_csrf_token : $("#_csrf_token").val()},
		success : function(jsonVal,fTextStatus,fRequest) {
			if (isValidSession(jsonVal) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(jsonVal) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if(jsonVal == true){
				$("#reg-gstin").addClass("input-correct").removeClass("input-error");
				 $("#reg-gstin-req").hide();
				 $("#reg-gstin-back-req").hide();
				 gstinValidWithRegisteredPan = false;
			}else{
				 $("#reg-gstin-back-req").text('GSTIN - '+gstin+' is Invalid for Registered PAN');
				 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
				 $("#reg-gstin-back-req").show();
				 $("#reg-gstin-state").empty();
				 gstinValidWithRegisteredPan = true;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) {			 
			 getInternalServerErrorPage();   
		}		
    });
	return gstinValidWithRegisteredPan;
}

/*$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});*/

function editRecord(idValue){
	document.manageGstinDetail.action = "./editGstinDetails";
	$("#removeButton").show();
	document.manageGstinDetail.id.value = idValue;
	document.manageGstinDetail._csrf_token.value = $("#_csrf_token").val();
	document.manageGstinDetail.submit();	
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
		document.manageGstinDetail.action = "./deleteGstinDetails";
		document.manageGstinDetail.id.value = idValue;
		document.manageGstinDetail._csrf_token.value = $("#_csrf_token").val();
		document.manageGstinDetail.submit();	
		 }
	});
}
