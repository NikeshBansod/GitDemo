
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
    	 $('.customerValuesTable').hide();
    	 $('#addCustomer').hide();
    	e.preventDefault();
    })
    
});


function val(){
	bootbox.alert($("#userName").val());
	return true;
}


$(document).ready(function () {
	/*Reqd to check GSTIN validation on change of Cust type everytime before submitting*/
	var errFlag7=false;
	var reqAddr = false;
	var blankMsg="This field is required";
	
	if($("#Individual").is(":checked")){
		$("#divAddr").removeClass("mandatory");
		$("#custGstId").val('');
		$("#custGstinState").val('');
		$("#divGstinNo").hide();
		$("#divGstinState").hide();
		reqAddr = true;	
		
    } else {
    	$("#divGstinNo").show();
    	$("#divGstinState").show();
    	$("#divAddr").addClass("mandatory");  // mandatory
    	reqAddr = false;
    	errFlag7 = validateGstinAndStAddrState();
    }
	
	
	function validateGstinAndStAddrState(){
		errFlag7 = validateTextField("custGstId","custGstId-req",blankMsg);
		
		 return errFlag7;
	}
	
});

$(document).ready(function(){
	var reqAddr = false;
	var errFlag7 = false;
	$("#custCountry").val("India");
	var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$/;
//	var GstinNumRegex = /^[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$/;
	var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	var contactNumRegex = /[0-9]{2}\d{8}/;
	var blankMsg="This field is required";
	var alphnumericRegx =  /^[a-zA-Z0-9.-]+$/;
	var pinSelMsg = "Please select pinCode from populated list";
	var length = 2, pinCodeLength = 6;
	var lengthMsg = "Minimum length should be ";
	var regMsg = "data should be in proper format";
	var cmpMsg = "The State in Address should be same as GSTIN State";
	var gstinStateErrMsg = "Invalid State code for GSTIN entered";
	var custContactExists = false; 
	var gstinStateValid = false;
	
	$("#custSubmitBtn").click(function(e){
		
		/*Reqd to check GSTIN validation on change of Cust type everytime before submitting*/
		if($("#Individual").is(":checked")){
			$("#divAddr").removeClass("mandatory");
			$("#custGstId").val('');
		//	$("#custName").val('');
			$("#divGstinNo").hide();
			$("#divGstinState").hide();
			errFlag7=false;
			reqAddr = true;	
	    } else {
	    	$("#divGstinNo").show();
	    	$("#divGstinState").show();
	    	$("#divAddr").addClass("mandatory");  // mandatory
	    	reqAddr = false;
	    	errFlag7 = validateGstinAndStAddrState();
	    }

		var errCustAddr1 = false;
		var errCustGstin = false;
		var errCustName = validateCustomerName();
		var errFlag = validateCustomerEmail();
		var errCustContactNo = validateCustomerContactNo();
		
		if(reqAddr != true){
			
		errCustGstin= validateCustGstId();
		errCustAddr1 = validateAddress1();	
		gstinStateValid = validGstinStateCode();
		
		}
		var errCustPIN = validateCustPin();
		var errCustcity = validateCustCity();
		var errCustState = validateCustState();
		
		
	 	if((errCustName)  || (errFlag) || (errCustContactNo) ||(errCustGstin) ||(errCustAddr1) || (errCustPIN) || (errCustcity) || (errCustState) ||(custContactExists) 
	 		|| (errFlag7) || (gstinStateValid)){
	 		e.preventDefault();
		}	
	 	
	 	if((errCustName)){
			 focusTextBox("custName");
		 } else if((errFlag) ){
			 focusTextBox("custEmail");
		 } else if((errCustContactNo)){
			 focusTextBox("contactNo");
		 } else if((errCustGstin)){
			 focusTextBox("custGstId");
		 }else if((errCustAddr1)){
			 focusTextBox("custAddress1");
		 } else if((errCustPIN)){
			 focusTextBox("pinCode");
		 }else if((errCustcity)){				//if pincode is not selected from autopopulate
			 focusTextBox("pinCode");
		 }else if((errCustState)){ 				//if pincode is not selected from autopopulate
			 focusTextBox("pinCode");
		 }
	 	
	});

	function validGstinStateCode(){
		gstinStateValid=validateGstinStateCode("custGstId","custGstId-req",gstinStateErrMsg);
		 
		 return gstinStateValid;
 	}
	
	function validateCustomerName(){		
		errCustName = validateTextField("custName","cust-name-req",blankMsg);
		 if(!errCustName){
			 errCustName=validateFieldLength("custName","cust-name-req",lengthMsg,length);
		 }
		 return errCustName;
		
	}
			
	function validateCustomerContactNo(){
		/*errCustContactNo = validateTextField("contactNo","contact-no-req",blankMsg);
		 if(!errCustContactNo){*/
			 errCustContactNo=validateRegexpressions("contactNo","contact-no-req",regMsg,contactNumRegex);
		// }
		 return errCustContactNo;

	}

	function validateCustomerEmail(){
		
			 errFlag=validateRegexpressions("custEmail","cust-email-format",regMsg,emailRegex);
			 return errFlag;

	}

	function validateCustomerSelect(){		
		
			 errCustType=validateTextField("custType","cust-sel-req",blankMsg);
		 return errCustType;
	}
		
	function validateCustPin(){
		
		errCustPIN = validateTextField("pinCode","cust-zip-req",blankMsg);
		 if(!errCustPIN){
			 errCustPIN=validateFieldLength("pinCode","cust-zip-req",lengthMsg,pinCodeLength);
		 }
		 return errCustPIN;
		
	}

	function validateCustCity(){		
		errCustcity = validateTextField("custCity","cust-zip-req",pinSelMsg);
		 
		 return errCustcity;
		
	}
	
	function validateCustState(){		
		errCustState = validateTextField("custState","cust-zip-req",pinSelMsg);
		 
		 return errCustState;
		
	}
	function validateAddress1(){
		
		errCustAddr1 = validateTextField("custAddress1","address1-req",blankMsg);
		if(!errCustAddr1){
			errCustAddr1=validateFieldLength("custAddress1","address1-req",lengthMsg,length);
		 }
		 return errCustAddr1;
	 }
	 

	function validateCustGstId(){
		errCustGstin = validateTextField("custGstId","custGstId-req",blankMsg);
		if(!errCustGstin){
			errCustGstin=validateRegexpressions("custGstId","custGstId-req",regMsg,GstinNumRegex);
		}
		 return errCustGstin;
		}

	function validateGstinAndStAddrState(){
		errFlag7 = validateTextField("custGstId","custGstId-req",blankMsg);
		if(!errFlag7){
		 errFlag7=compareTextFields("custState","custGstinState","custState-err",cmpMsg);
		}
		 
		 return errFlag7;
	}
	
	function validateGSTINWithRegex(gstin){
		if(GstinNumRegex.test(gstin) === true){				
			$("#custGstId").addClass("input-correct").removeClass("input-error");
			 $("#custGstId-req").hide();
			 $("#custGstId-req").hide();
			 return true;
		}
		if(GstinNumRegex.test(gstin) !== true){
			$("#custGstId").addClass("input-error").removeClass("input-correct");
			 $("#custGstId-req").show();
			 return false;
		}
		
	}
	
	$("#custName").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		if ($("#custName").val().length > 1){
			 $("#custName").addClass("input-correct").removeClass("input-error");
			 $("#cust-name-req").hide();
			 
		 }
		if ($("#custName").val().length < 1){
			 $("#custName").addClass("input-error").removeClass("input-correct");
			 $("#cust-name-req").show();
			 }
		
		
	});

	$("#custName").on("keydown input",function(){
		
		if ($("#custName").val().length == '' ){
			 $("#custName").addClass("input-error").removeClass("input-correct");
			 $("#cust-name-req").show();
			 }
		
	});
		
	    $('input[type=radio][name=custType]').change(function() {
	        if (this.value == 'Individual') {
	        	/*custName = $("#custName").val().replace(/[^[a-zA-Z\s]*$/, '');
	        	$("#custName").val(custName);*/
	        	$("#custGstId").val('');
	        	$("#custGstinState").val('');
	        	$("#custAddress1").val('');
	    		$("#custName").val('');
	        	$("#divGstinNo").hide();
	        	$("#divGstinState").hide();
	        	$("#custAddress1").addClass("").removeClass("input-error");
	        	$("#divAddr").removeClass("mandatory");  // mandatory
				$("#address1-req").hide();
	        	reqAddr = true;
	        } else {
	        	$("#divGstinNo").show();
	        	$("#divGstinState").show();
	        	$("#divAddr").addClass("mandatory").removeClass("input-error");
	        	$("#divGstinNo").addClass("mandatory");  // mandatory
	        	reqAddr = false;
	        }
	    });
	
		
	    $("#custEmail").on("keyup input", function(e){
	    	if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			if((this.value!='') && emailRegex.test($("#custEmail").val()) === true){
				$("#cust-email-format").hide();
				$("#custEmail").addClass("input-correct").removeClass("input-error");
				}
			
			if((this.value!='') && !emailRegex.test($("#custEmail").val())){
			$("#cust-email-format").show();
			$("#custEmail").addClass("input-error").removeClass("input-correct");
			$("#custEmail").focus();
			}
			
			if((this.value!='') && emailRegex.test($("#custEmail").val())){
			$("#cust-email-format").hide();
			$("#custEmail").addClass("input-correct").removeClass("input-error");
			$("#custEmail").focus();
			}
			
			if($("#custEmail").val() == ''){
			$("#cust-email-format").hide();
			$("#custEmail").removeClass("input-error");
		}
		
		});

		$("#contactNo").on("keyup input", function(){
			this.value = this.value.replace(/^0/, '');
			var contactNumRegex = /[0-9]{2}\d{8}/;
			if(contactNumRegex.test($("#contactNo").val()) !== true){
				
				this.value = this.value.replace(/[^0-9]+/, '');
			}
			if(contactNumRegex.test($("#contactNo").val()) === true){
				$("#contact-no-req").hide();
				$("#contactNo").addClass("input-correct").removeClass("input-error");	
			}
			if(contactNumRegex.test($("#contactNo").val()) !== true){
				$("#contact-no-req").show();
				$("#contactNo").addClass("input-error").removeClass("input-correct");	
			}
		});
		
		$("#pinCode").on("keyup input", function(e){
			if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			var pincodeRegex = /[0-9]{6}/;
			if(pincodeRegex.test($("#pinCode").val()) !== true){
				
				this.value = this.value.replace(/[^0-9]+/, '');
			}
			if(pincodeRegex.test($("#pinCode").val()) === true){
				$("#cust-zip-req").hide();
				$("#pinCode").addClass("input-correct").removeClass("input-error");	
			}
			if(pincodeRegex.test($("#pinCode").val()) !== true){
				$("#cust-zip-req").hide();
				$("#pinCode").addClass("input-error").removeClass("input-correct");	
			}
		});
		
		 $("#custAddress1").on("keyup input",function(e){
			 if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }
			 
			 this.value = this.value.replace(/[\\[]*$/, '');
			// this.value = this.value.replace(/[^[a-zA-Z0-9-\s,]*$/, '');
			 this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
			 if(reqAddr != true){
				 $("#custAddress1").addClass("");
				 $("#address1-req").hide();
			 }
			 if ($("#custAddress1").val().length > 1 && reqAddr != true){
				 $("#custAddress1").addClass("input-correct").removeClass("input-error");
				 $("#address1-req").hide();
				 
			 }
			 if ($("#custAddress1").val().length < 1 && reqAddr != true){
				 $("#custAddress1").addClass("input-error").removeClass("input-correct");
				 $("#address1-req").show();
				 
			 }
		 });
		
				$("#custGstId").on("keyup input", function(e){
					if(e.keyCode == 32){
						   this.value = removeWhiteSpace(this.value);
					   }
					this.value = this.value.toUpperCase();
					this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
					 this.value = this.value.replace(/^00/, '');
					if((this.value!='') && GstinNumRegex.test($("#custGstId").val()) === true){				
						$("#custGstId-req").hide(); 
						$("#custGstId").addClass("input-correct").removeClass("input-error");
						 
					}
					if((this.value!='') && GstinNumRegex.test($("#custGstId").val()) !== true){
						$("#custGstId-req").show(); 
						$("#custGstId").addClass("input-error").removeClass("input-correct");
						 	
					}
					if($("#custGstId").val() == ""){				
						$("#custGstId-req").hide(); 
						$("#custGstId").addClass("input-correct").removeClass("input-error");
						 
					}
				});
				

				$("#custCountry").on("keyup input",function(e){
					if(e.keyCode == 32){
						   this.value = removeWhiteSpace(this.value);
					   }
				});
				
	
    $.ajax({
		url : "getCustomerDetailsList",
		type : "post",
	//	contentType : "application/json",
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		async : false,
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
				 
				
				 
				/* var dynamicGstin="";*/
				/* if(value.custType=="Individual"){
					 dynamicCustType = 'Not Registered Under GST'+""+'</p>';
					 dynamicGstin='';
				 }else if(value.custType=="Organization"){
					 dynamicCustType = '<p> Customer Type : Registered Under GST'+""+'</p>';
					 dynamicGstin='<p> GSTIN : '+value.custGstId+'</p>';
				 }*/
				 var dynamicCustType="";
				 if(value.custGstId==""){
					 dynamicCustType = 'Not Registered';
					 
				 }else{
					 dynamicCustType = value.custGstId;
					 
				 }
				 
				 
				 $('.customerValues').append(
					/*'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.custName+'</h1>'
						+'</div>'
						+'<div class="cust-edit">'
						  +'<div class="cust-icon">'
						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						  +'</div>'
						+'</div>'
					+'</div>'
		
					+'<div class="content">'
						+'<div class="cust-con">'
							+'<p> Customer Name : '+value.custName+'</p>'
							+dynamicCustType
							+'<p> Email ID : '+value.custEmail+'</p>'
							+'<p> Mobile Number : '+value.contactNo+'</p>'
							+dynamicGstin
							+'<p> State : '+value.custState+'</p>'
							+'<p> Country : '+value.custCountry+'</p>'
						+'</div>'
					+'</div>'*/	 
						
						+'<tbody>'			
								+'<tr>'
									+'<td class="text-left"><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.custName+'</a></td>' 
									+'<td class="text-left">'+dynamicCustType+'</td>'									
								+'</tr>'
						+'</tbody>'
				 );
			});
			 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	 
         },
         	error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	}); 

    
 	/*$("#custGstId").blur(function(){
	    var gstin = $("#custGstId").val();
	    if(gstin != ''){
	    if(validateGSTINWithRegex(gstin)){
	    $.ajax({
			url : "isGstinRegisteredWithOrg",
			type : "POST",
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
			data : {gstin : gstin},
			async : false,
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
					 $("#custGstId").addClass("input-error").removeClass("input-correct");
					 $("#custGstId-req").text('GSTIN  - '+gstin+' is Registered with your orgnization.');
					 $("#custGstId-req").show();
					 gstinRegistered = true;
				}else{
					 $("#custGstId").addClass("input-correct").removeClass("input-error");
					 $("#custGstId-req").hide();
					 $("#custGstId-req").hide();
					 gstinRegistered = false;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
        });
	    if(gstinRegistered!=true){
	    	getStateByGstinNumber(gstin);
	    }
	    
	}
	    }
	    
	});*/
    
    $("#custGstId").blur(function(){
        var gstin = $("#custGstId").val();
        if(gstin != ''){
    // function checkIfGstinIsRegistered(gstin){
    		if(validateGSTINWithRegex(gstin)){
    	    $.ajax({
    			url : "checkIfGstinIsRegistered",
    			type : "POST",
    			dataType : "json",
    			data : {gstin : gstin},
    			headers: {_csrf_token : $("#_csrf_token").val()},
    			async : false,
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
    	    
    	    if(gstinRegistered!=true){
    	    	getStateByGstinNumber(gstin);
    	    
    	    }	    
        }
    		
//    	}
        }
    	});
     
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
	document.editCustomerDetails.action = "./editCustomerDetails";
	document.editCustomerDetails.id.value = idValue;
	document.editCustomerDetails._csrf_token.value = $("#_csrf_token").val();
	document.editCustomerDetails.submit();	
}


function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
			document.editCustomerDetails.action = "./deleteCustomerDetails";
			document.editCustomerDetails.id.value = idValue;
			document.editCustomerDetails._csrf_token.value = $("#_csrf_token").val();
			document.editCustomerDetails.submit();	
		 }
	});
	 
}

	
	function getStateByGstinNumber(gstinNumber){
		$("#custGstinState").empty();
					$.ajax({
						url : "getStateByGstinNumber" ,
						type : "post",
						headers: {_csrf_token : $("#_csrf_token").val()},
						data : {"id":gstinNumber},
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
								$("#custGstinState").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId))
								getDataByGstinNumber(gstinNumber);  
							}
							
						},
						
						error: function(xhr, textStatus, errorThrown){
						bootbox.alert('request failed');
					      
					    }
					});

		}

	
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
				$("#custGstinState").val("");
			}else{ 								
				var address = "";
				var stcd = response.gstin.substring(0,2);
				var state = response.pradr.addr.stcd ;
				if(response.pradr.addr.bno != ""){
					address = response.pradr.addr.bno + ", ";
				}if(response.pradr.addr.bnm != ""){ 
					address = address + response.pradr.addr.bnm + ", ";
				}
				address = address + response.pradr.addr.st + " "+ response.pradr.addr.loc; 
				$("#custAddress1").val(address);
				$("#custName").val(response.lgnm);
			//	$("#custGstinState").append($('<option>').text(state).attr('value',stcd));
			
			}
			
		},
		error: function (data,status,er) { 							 
			 getInternalServerErrorPage();   
		}
	});
	}
		

function getPANFromGSTIN(){
	var gstin = $("#custGstId").val();
	var panNo=gstin.substring(2,12);
	return panNo;
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
$(document).ready(function() {
    $('#customerValuesTab').DataTable(
              {
            	  	"aaSorting": [],
                    "bLengthChange": false,
                    "pagingType": "simple",
                    
              }
             
    );
} );