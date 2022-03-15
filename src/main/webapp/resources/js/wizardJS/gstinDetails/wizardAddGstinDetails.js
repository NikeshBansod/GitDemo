
/*jQuery(document).ready(function($){
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
    if($("#actionPerformed").val()){
  		 $('#toggle').hide();
  	}

});*/

$(document).ready(function(){		   
	   
	   var buttonC = 1;
	   $("#counterButtonValue").val(buttonC); 	   
	   
		$("#addButton").click(function () {	
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
			}
		  
		   
		   if ($('.gstinLocation ').length >= 6) {
			   $("#addButton").hide();	
			   bootbox.alert("Maximum 6 Locations can be added");
	            return false;
	        }		
			
	       var outerDivCount = $('.gstinLocation').length;
	       var newIndex=outerDivCount;
	       
	       if(counterButton == 4)
			{
				var dynamicLocDivContent = '<br>'
											+ '<div class="det-row-col astrick gstinLocation" id="dynamiclocDiv'+newIndex+'">'
												+'<div class="label-text">Store/Location/Channel/Department: '+(newIndex+1)+'</div>'
												+'<input  name="gstinLocationSet['+newIndex+'].gstinLocation" type="text" id="gstinLocation_'+newIndex+'" class="form-control" />'
												+'<span class="text-danger cust-error" id="gstinLocation-empty_'+newIndex+'"></span>'
											+'</div>';
		   }
	       else{
	    	   var dynamicLocDivContent =  '<div class="det-row-col astrick gstinLocation" id="dynamiclocDiv'+newIndex+'">'
												+'<div class="label-text">Store/Location/Channel/Department: '+(newIndex+1)+'</div>'
												+'<input  name="gstinLocationSet['+newIndex+'].gstinLocation" type="text" id="gstinLocation_'+newIndex+'" class="form-control" />'
												+'<span class="text-danger cust-error" id="gstinLocation-empty_'+newIndex+'"></span>'
											+'</div>';
	       }
	       
	       var dynamicStoreDivContent = '<div class="det-row-col astrick gstinStore" id="dynamicStoreDiv'+newIndex+'">'
	       									+'<div class="label-text">Store: '+(newIndex+1)+'</div>'
	       									+'<input name="gstinLocationSet['+newIndex+'].gstinStore" type="text" id="gstinStore_'+newIndex+'" class="form-control" />'
	       									+'<span class="text-danger cust-error" id="gstinStore-empty_'+newIndex+'"></span>'
	       								+'</div>';
	       	       
	      
	       $("#adddynamicloc").append(dynamicLocDivContent);	       
	       counterButton++;   
	       $("#counterButtonValue").val(counterButton);	
	       if(counterButton == 6){
				var endDynamicRow = '</div>'	
				$(endDynamicRow).insertAfter(dynamicLocDivContent);
		    }	    
		});


	$("#removeButton").hide();	
	 $("#removeButton").click(function () {
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
	    });
	
	var GstinNumRegex =  /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/; 
//	var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
	var blankMsg="This field is required";
	var lengthMsg = "Minimum length should be ";
	var length = 2;
	var regMsg = "data should be in proper format";
	var cmpMsg = "Pincode does not belong to the mentioned GSTIN's state";
//	var cmpMsg = "The State in Address should be same as GSTIN State";
	var gstinStateErrMsg = "Invalid State code for GSTIN entered";
	var gstinRegistered = false;
	var gstinValidWithRegisteredPan =false;
	var gstinLocation = false;
	var gstinStore = false;
	var gstinStateValid = false;
	
	$("#submitGstin").click(function(e){
		var errFlag1 = regGstin();
		var errFlag2 = validateState();
		var errFlag3 = validateUser();
		var errFlag5 = validateAddress();
		var errFlag6  = validatePinCode(); 
		gstinValidWithRegisteredPan=checkGSTINPANValidation(gstinValidWithRegisteredPan);
		validateGstinState();
		var errFlag7 = validateGstinAndStAddrState();
		gstinLocation = validateGstinLocation();
	//	gstinStore = validateStoreLocation();
		gstinStateValid = validGstinStateCode();
			
		 if ((errFlag1) || (errFlag2) || (errFlag3) || (errFlag5) || (errFlag6) || (errFlag7) || (gstinValidWithRegisteredPan) || (gstinLocation) ){
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
		 }
		 
		 }); 

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
	 		 /*$("#gstinLocation-empty").hide();*/
	 		/*$( "input[name^='gstinLocation']" ).each(function(index, obj){	 			  
	 			if(obj.id != 'gstinStore'){
	 			if(obj.value ==''){	 				
	 				gstinLocation=true;
	 				 $("#gstinLocation-empty").text("Location/Store Cannot be Blank");
	 				 $("#gstinLocation-empty").show();	 				
	 			}else{
	 				gstinLocation=false;
	 				 $("#gstinLocation-empty").hide();
	 			}
	 		}
 			});*/ 	
	 		gstinLocation=false;
	 		if($("#gstinLocation").val() == ''){ 				
 				gstinLocation=true;
	 			$("#gstinLocation-empty").text("Location/Store Cannot be Blank");
 				$("#gstinLocation-empty").show();
	 		}
	 		var locCount = $("#dynamicLocCount").val();
	 		for(var i=1; i<=locCount;i++){ 				
 				if($("#gstinLocation_"+i).val() == ''){
 					gstinLocation=true;	 			
	 				$("#gstinLocation-empty_"+i).text("Location/Store Cannot be Blank");
	 				$("#gstinLocation-empty_"+i).show();
	 			}
 				else{
 					$("#gstinLocation-empty_"+i).hide();
 				}
	 		}
	 		
	 		return gstinLocation;	 		
	 	}
	 	
	 	function validateStoreLocation(){
	 		 $("#gstinStore-empty").hide();
	 		$( "input[name^='gstinStore']" ).each(function(index, obj){	 			  
	 			if(obj.value==''){	 				
	 				gstinStore=true;
	 				 $("#gstinStore-empty").text("Location/Store Cannot be Blank");
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
	 	
	/* 	$.each($('[id^=gstinStore]'), function (i, item) {
            $(document).on('keyup', '[id*="gstinStore"]',function () {
            	this.value = this.value.replace(/[\\[]*$/, '');
           	 this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
            });

       	});*/
	 	
		$("#reg-gstin").on("keyup input", function(){
			this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
	  		 if ($("#reg-gstin").val() === ""){
	  			 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
	  			 $("#reg-gstin-req").show();	  			 
	  		 }
	  		 if ($("#reg-gstin").val() !== ""){
	  			 $("#reg-gstin").addClass("input-correct").removeClass("input-error");
	  			 $("#reg-gstin-req").hide();	  			 
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
		  	
    
    
 	$("#reg-gstin").blur(function(){
	    var gstin = $("#reg-gstin").val();
	    if(gstin != ''){
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
					success : function(jsonVal,fTextStatus,fRequest) {
						if (isValidSession(jsonVal) == 'No') {
							window.location.href = getDefaultWizardSessionExpirePage();
							return;
						}
						if(isValidToken(jsonVal) == 'No'){
							window.location.href = getWizardCsrfErrorPage();
							return;
						}
						
						if(jsonVal == true){
							 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
							 $("#reg-gstin-back-req").text('GSTIN  - '+gstin+' already Registered. Try some other GSTIN.\n');
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
						 getWizardInternalServerErrorPage();   
					}
		        });
			    var isValidPan =  checkGSTINPANValidation(gstinValidWithRegisteredPan);
			    if(isValidPan!= true && gstinRegistered!=true){
			    	//getStateByGstinNumber(gstin);
			    	getDataByGstinNumber(gstin);
			    }	    
			}
	    }	    
	});
 	
 	function getDataByGstinNumber(gstinNumber){
 		$("#reg-gstin-state").empty();
		$.ajax({
			url : "getDataByGstinNumber" ,
			type : "post",
			data : {"gstinNo":gstinNumber},
			headers: {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
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
				
				if(response==null){
					$("#state").val("");
				}else{ 								
					var address = "";
					if(response.pradr.addr.bno != ""){
						address = response.pradr.addr.bno + ", ";
					}if(response.pradr.addr.bnm != ""){ 
						address = address + response.pradr.addr.bnm + ", ";
					}
					address = address + response.pradr.addr.st + " "+ response.pradr.addr.loc; 
					$("#gstinUname").val(response.tradeNam);
					$("#address1").val(address);
					/*$("#pinCode").val(response.pradr.addr.pncd);
					$("#city").val(response.pradr.addr.lgnm);
					$("#state").val(response.pradr.addr.lgnm);*/
					getStateByGstinNumber(gstinNumber); 								
				}
			},
			error: function (data,status,er) { 							 
				 getInternalServerErrorPage();   
			}
		});
 	}
 	
     
 	function getStateByGstinNumber(gstinNumber){
 		$("#reg-gstin-state").empty();
		$.ajax({
			url : "getStateByGstinNumber" ,
			type : "post",
			data : {"id":gstinNumber},
			headers: {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async : false,
			success : function(response,fTextStatus,fRequest) {
				if (isValidSession(response) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}
				if(isValidToken(response) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
				if(response==null){
					$("#state").val("");
				}else{
					$("#reg-gstin-state").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId))					
				} 		
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {				 
				 getWizardInternalServerErrorPage();   
			}
		});
 	} 	
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
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			if(isValidToken(jsonVal) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			if(jsonVal == true){
				$("#reg-gstin").addClass("input-correct").removeClass("input-error");
				 $("#reg-gstin-req").hide();
				 $("#reg-gstin-back-req").hide();
				 gstinValidWithRegisteredPan = false;
			}else{
				 $("#reg-gstin").addClass("input-error").removeClass("input-correct");
				 $("#reg-gstin-back-req").text('GSTIN - '+gstin+' is Invalid for Registered PAN.\n');
				 $("#reg-gstin-back-req").show();
				 gstinValidWithRegisteredPan = true;
			}	
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) {			 
			 getWizardInternalServerErrorPage();   
		}
    });
	return gstinValidWithRegisteredPan;
}


$(document).ready(function(){
	$("#address1").on("keyup input",function(){
		this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
		if ($("#address1").val() !== ""){
			 $("#address1").addClass("input-correct").removeClass("input-error");
			 $("#address1-req").hide();		 
		 }		 
	});
});
