var gstin ='';
 var fp = '';
 var mode = '';
 var i='';
 var url = '';

$(document).ready(function(){
	
	 fp = $("#fp").val();
	  gstin = $("#gstin").val();
	  mode = '';
	  i='';
	  
	 GSTR_type=$("#GSTR_type").val();
	 mode = $("#payload_mode").val();
	 
	 if(gstin != '' && fp != ''){
			
		 url = "wgetgstr1responsefromGSTN";
		 var statusofaction= checkforsessionforsubmittoGSTN(gstin,fp,url);
		 
			if(statusofaction== "expired"||statusofaction== "invalidsession"||statusofaction== "firsttimeloginuser"){
				var locale = {
					    OK: 'I Suppose',
					    CONFIRM: 'Verify',
					    CANCEL: 'Resend'
					};
				 bootbox.addLocale('custom', locale);
				 bootbox.prompt({
				     	    title: "OTP has been sent on GSTN registered mobile number.",
				     	    inputType: 'text',
				     	     placeholder: "enter OTP here",
				     	    locale: 'custom',
				     	   
				     	   buttons: {
				     	       /* cancel: {
				     	        label: "Resend",
				     	     },*/
				     	     confirm: {
				     	        label: "Verify & Save",
				     	    }
				     	  },
				     	    callback: function (result) {
				     	    	/*alert("my value is"+result);*/
				     	    	
				     	      if(result != null){
				     	    	 url="wgetgstr1submittogstnl0response";
				     	    		/*responsesubmittogstn(L0_gstin,L0_fp,GSTR_type,result);*/
				     	    		loadL0DetailsDraft(gstin,fp,url,result);
				     	        }
		     	            }
		     	});
			}
			else{
				 url="wgetgstr1submittogstnl0response";
				 var result="";
     	    		/*responsesubmittogstn(L0_gstin,L0_fp,GSTR_type,result);*/
     	    	 loadL0DetailsDraft(gstin,fp,url,result);
			}
       
 }
	 
	 
	 $("#saveToGSTN").click(function (){
			
		 var otpresponse= getgstr1responsefromGSTN();
	 
	
	if(otpresponse == "firsttimeloginuser" ||otpresponse=="invalidsession"||otpresponse== "expired" ){
		var locale = {
			    OK: 'I Suppose',
			    CONFIRM: 'Verify',
			    CANCEL: 'Resend'
			};
		 bootbox.addLocale('custom', locale);
		 bootbox.prompt({
		     	    title: "OTP has been sent on GSTN registered mobile number.",
		     	    inputType: 'text',
		     	     placeholder: "enter OTP here",
		     	    locale: 'custom',
		     	   
		     	   buttons: {
		     	       /* cancel: {
		     	        label: "Resend",
		     	     },*/
		     	     confirm: {
		     	        label: "Verify & Save",
		     	    }
		     	  },
		     	    callback: function (result) {
		     	    	/*alert("my value is"+result);*/
		     	    	
		     	      if(result != null){
		     	    	 
		     	    		 var response=responsesubmittogstn(gstin,fp,GSTR_type,result);
		     	    		console.log(response);
		     	  	     
		     	    	    bootbox.alert(response);
		     	    	  
		     	    	 
		     	        }
     	            }
     	});
	}
	else{
	     var response= redirectToUploadAspPageafterSubmit(gstin,fp,GSTR_type);
	     console.log(response);
	     
		bootbox.alert(response);
		
	    
	  }
	});
	 
	 
	 //for filing to gstn FileToGSTN
	 var locale = {
			    OK: 'I Suppose',
			    CONFIRM: 'Verify',
			    CANCEL: 'Resend'
			};
	
$("#FileToGSTN").click(function (){
		 
		 url = "getgstr1responsefromGSTN";
		 var statusofaction= checkforsessionforsubmittoGSTN(gstin,fp,url);
		 
		 if(statusofaction=="expired" || statusofaction=="invalidsession" ||statusofaction== "firsttimeloginuser")
			 {
								 bootbox.addLocale('custom', locale);
								 bootbox.prompt({
								     	    title: "Your session is expired.OTP has been sent on GSTN registered mobile number.",
								     	    inputType: 'text',
								     	     placeholder: "enter OTP here",
								     	    locale: 'custom',
								     	   
								     	   buttons: {
								     	       
								     	     confirm: {
								     	        label: "Verify & Save",
								     	    }
								     	  },
								     	    callback: function (result) {
								     	    	
								     	    	
								     	      if(result != ""){
								     	    	 
								     	    	getresponseforauthtokenindatabaseStatus=getresponseforauthtokenindatabase(gstin,fp,GSTR_type,result);
								     	    	
								     	    	 if(getresponseforauthtokenindatabaseStatus=='success'){
													 bootbox.prompt({
												     	     title: "Enter OTP for file the GSTR1 .OTP has been sent on GSTN registered mobile number.",
												     	     inputType: 'text',
												     	     placeholder: "enter OTP here",
												     	     locale: 'custom',
												     	   
												     	    buttons: {
												     	       confirm: {
												     	          label: "Verify & Save",
												     	        }
												     	     },
												     	    callback: function (result) {
												     	      if(result != ""){
												     	    	 responsefiletogstn(gstin,fp,GSTR_type,result);
												     	        }else{
												     	        	bootbox.alert("Please enter OTP.");
												     	        }
										  	                }
										  	          });
												    }else{
												    	bootbox.alert(getresponseforauthtokenindatabaseStatus);
												    }
								     	    	 
								     	        }else{
								     	        	bootbox.alert("Please enter OTP.");
								     	        }
						     	            }
						     	});
			 
					
		      }else{
			 var otpforresponsefiletogstnstatus= otpforresponsefiletogstn();
			 if(otpforresponsefiletogstnstatus=="success"){
			     bootbox.addLocale('custom', locale);
			     bootbox.prompt({
			     	     title: "Enter OTP for file the GSTR1 .OTP has been sent on GSTN registered mobile number.",
			     	     inputType: 'text',
			     	     placeholder: "enter OTP here",
			     	     locale: 'custom',
			     	   
			     	    buttons: {
			     	     confirm: {
			     	        label: "Verify & Save",
			     	    }
			     	  },
			     	    callback: function (result) {
			     	    	
			     	    	
			     	      if(result != ""){
			     	    	 
			     	    	 responsefiletogstn(gstin,fp,GSTR_type,result);
			     	        }else{
			     	        	bootbox.alert("Please enter OTP.");
			     	        }
	     	            }
	     	     });
			 }else{
				 bootbox.alert("OTP sending fail.Please try again later.");
			 }
	    }
	});
	 
	 
});
	 
function checkforsessionforsubmittoGSTN(gstin,fp,urlToCall){
	var statusofaction = "";
	$.ajax({		
		url : urlToCall,
		method : "POST",
		data :{"gstin":gstin, "fp":fp},
	    /*contentType : "application/json",*/
		
		async : false,
		headers: {_csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			console.log("json : "+json);
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			statusofaction = json;		
			
		},
        error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }		
	});
	
	return statusofaction;
	
}	

function loadL0DetailsDraft(gstin,fp,urlToCall,otp){
	
	
    $.ajax({
		url : urlToCall,
		method : "POST",
		data :{"gstin":gstin, "fp":fp ,"otp":otp},
	    /*contentType : "application/json",*/
		dataType : "json",
		async : false,
		
		headers: {_csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			/*console.log("json : "+json);*/
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		   
			if(json == 'Something went wrong. Try again after some time'){
				
					
					bootbox.alert(json)
					console.log("final json"+json)
					/*setTimeout(function(){ 
						$('#loadingmessage').hide();
						$("#defaultShowDiv").show();
					}, 2000);*/
			}
			else{
		   displayDatainUI(json);
			$("#defaultShowDiv").show();
			}
			//end of json != null
			
				/*gobacktoasphomeforotperror(gstin,fp,GSTR_type,json);*/
				
			
						/*bootbox.alert(json)*/
						
					
			
			
			 
		},
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	
	});
    
    
   
}
	 

function getgstr1responsefromGSTN(){
	var statusofaction = "";
	$.ajax({		
		url : "wgetgstr1responsefromGSTN",
		method : "POST",
		data :{"gstin":gstin, "fp":fp},
	    /*contentType : "application/json",*/
		dataType : "json",
		async : false,
		headers: {_csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			console.log("json : "+json);
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			statusofaction = json;		
			/*bootbox.alert("json:"+json+"\tstatusofaction:"+statusofaction);*/
		},
        error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }		
	});
	
	return statusofaction;
}





/*function gobacktoasphomeforotperror(gstin,fp,GSTR_type,message)
{
	 document.gobacktoasphomeforotperror.action ="./wgobacktoasphomeforotperror";
     document.gobacktoasphomeforotperror.gstin.value = gstin;
     document.gobacktoasphomeforotperror.fp.value = fp;
     document.gobacktoasphomeforotperror.GSTR_type.value = GSTR_type;
     document.gobacktoasphomeforotperror.message.value = message;
     document.gobacktoasphomeforotperror._csrf_token.value = $("#_csrf_token").val();
     document.gobacktoasphomeforotperror.goback.value = goback;
     document.gobacktoasphomeforotperror.submit();     
	}*/


function responsesubmittogstn(gstin,fp,GSTR_type,otp){
var response = "";
$.ajax({		
	url : "./wresponsesubmittogstn",
	method : "POST",
	data :{"gstin":gstin, "fp":fp,"GSTR_type":GSTR_type,"otp":otp},
    /*contentType : "application/json",*/
	dataType : "json",
	async : false,
	headers: {_csrf_token : $("#_csrf_token").val()},
	success:function(json,fTextStatus,fRequest){
		/*alert("json : "+json);*/
		if (isValidSession(json) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}

		if(isValidToken(json) == 'No'){
			window.location.href = getCsrfErrorPage();
			return;
		}			
		setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		response = json;		
		
	},
    error: function (data,status,er) {        	 
    	 getInternalServerErrorPage();   
    }		
});

return response;
}


/*function filetogstn(gstin,fp,GSTR_type,otp){
	var response = "";
	$.ajax({		
		url : "./wfiletogstn",
		method : "POST",
		data :{"gstin":gstin, "fp":fp,"GSTR_type":GSTR_type,"otp":otp},
	    contentType : "application/json",
		dataType : "json",
		async : false,
		headers: {_csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			alert("json : "+json);
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			response = json;		
			
		},
	    error: function (data,status,er) {        	 
	    	 getInternalServerErrorPage();   
	    }		
	});

	return response;
	}*/
/*{
	 document.goBackToAspafterSuccessfulSaveToGSTN.action ="./wresponsesubmittogstn";
     document.goBackToAspafterSuccessfulSaveToGSTN.gstin.value = gstin;
     document.goBackToAspafterSuccessfulSaveToGSTN.fp.value = fp;
     document.goBackToAspafterSuccessfulSaveToGSTN.GSTR_type.value = GSTR_type;
     document.goBackToAspafterSuccessfulSaveToGSTN.otp.value = otp;
     document.goBackToAspafterSuccessfulSaveToGSTN.goback.value = goback;
     document.goBackToAspafterSuccessfulSaveToGSTN._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAspafterSuccessfulSaveToGSTN.submit();     
 
	}*/

/*function redirectToUploadAspPageafterSubmit(gstin,fp,GSTR_type)
{
	 document.submitToGstn.action ="./wresponsesubmittogstn";
     document.submitToGstn.gstin.value = gstin;
     document.submitToGstn.fp.value = fp;
     document.submitToGstn.GSTR_type.value = GSTR_type;
     document.submitToGstn._csrf_token.value = $("#_csrf_token").val();
     
     document.submitToGstn.submit();     
     
	}*/

//////////////
function  redirectToUploadAspPageafterSubmit(gstin,fp,GSTR_type){
	var response = "";
	$.ajax({		
		url : "./wresponsesubmittogstn",
		method : "POST",
		data :{"gstin":gstin, "fp":fp,"GSTR_type":GSTR_type},
	    /*contentType : "application/json",*/
		dataType : "json",
		async : false,
		headers: {_csrf_token : $("#_csrf_token").val()},
		success:function(json,fTextStatus,fRequest){
			/*alert("json : "+json);*/
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			response = json;		
			
		},
        error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }		
	});
	
	return response;
	
}



function displayDatainUI(json){
	
	var cndntotaltaxamt = 0;
	var cndurtotaltaxamt = 0;
		
		var cndntotalinvoices = 0;
		var cndurtotalinvoices = 0;
		
		var cndntotaltaxableamt = 0;
		var cndurtotaltaxableamt = 0;
		
		var cndngstnSavedcnt = 0;
		var cndngstnnotsavedcnt = 0;
		var cndnerrorsavedgstn = 0;
		
		var cndurgstnSavedcnt = 0;
		var cndurgstnnotsavedcnt = 0;
		var cndurerrorsavedgstn = 0;

	var b2btotaltaxamt = 0;
	var b2cltotaltaxamt = 0;
	var b2cstotaltaxamt = 0;
	
	var b2btotalinvoices = 0 ;
	var b2cltotalinvoices = 0 ;
	var b2cstotalinvoices = 0 ;
	
	var b2btotaltaxableamt = 0 ;
	var b2cltotaltaxableamt = 0 ;
	var b2cstotaltaxableamt = 0 ;
	
	var b2bgstnSavedcnt=0;
	var b2bgstnnotsavedcnt=0;
	var b2berrorsavedgstn=0;
	
	var b2clgstnSavedcnt=0;
	var b2clgstnnotsavedcnt=0;
	var b2clerrorsavedgstn=0;
	
	
	//json = "{\"l0ResponseMap\":{\"data\":[{\"gstin\":\"27GSPMH0782G1ZJ\",\"ret_period\":\"072017\",\"chksum\":\"6306ae529d7ca033d87b33ae7751f4bae46e1f54bdcbd5fba3c94462b84028fe\",\"sec_sum\":[{\"sec_nm\":\"CDNUR\",\"chksum\":\"74313561d1897af3dc03f4fae174960d28968f92b49230523faca462b848db60\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"EXPA\",\"chksum\":\"3b7546ed79e3e5a7907381b093c5a182cbf364c5dd0443dfa956c8cca271cc33\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"DOC_ISSUE\",\"chksum\":\"\",\"ttl_rec\":0,\"ttl_doc_issued\":0,\"ttl_doc_cancelled\":0,\"net_doc_issued\":0},{\"sec_nm\":\"TXPDA\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"HSN\",\"chksum\":\"\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"EXP\",\"chksum\":\"3b7546ed79e3e5a7907381b093c5a182cbf364c5dd0443dfa956c8cca271cc33\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"CDNURA\",\"chksum\":\"74313561d1897af3dc03f4fae174960d28968f92b49230523faca462b848db60\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"NIL\",\"chksum\":\"\",\"ttl_rec\":0,\"ttl_expt_amt\":0.0,\"ttl_ngsup_amt\":0.0,\"ttl_nilsup_amt\":0.0},{\"sec_nm\":\"CDNRA\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"CDNR\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"B2CL\",\"chksum\":\"38e13ad4a95e4f3a4bdd262693aa241f06f3766c515fb59efabde48a239f0071\",\"ttl_rec\":1,\"ttl_tax\":15000.23,\"ttl_igst\":1000.2,\"ttl_val\":310000.0,\"ttl_cess\":100.0,\"cpty_sum\":[{\"state_cd\":\"21\",\"chksum\":\"fd349bc896a0cc85ddd904684bd70fd143cee3e9fb81fb3efd86a82ddd0a5efb\",\"ttl_rec\":1,\"ttl_tax\":15000.23,\"ttl_igst\":1000.2,\"ttl_val\":310000.0,\"ttl_cess\":100.0}]},{\"sec_nm\":\"B2CSA\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"B2CS\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"AT\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"ATA\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"TXPD\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"B2BA\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"B2CLA\",\"chksum\":\"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855\",\"ttl_rec\":0,\"ttl_tax\":0.0,\"ttl_igst\":0.0,\"ttl_val\":0.0,\"ttl_cess\":0.0},{\"sec_nm\":\"B2B\",\"chksum\":\"636fe4c2b4a9960f70aeef449260b4a17ddc0378ab1c8adfa448d16249fdab00\",\"ttl_rec\":19,\"ttl_tax\":324960.23,\"ttl_igst\":56423.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":787000.02,\"ttl_cess\":3672.5,\"cpty_sum\":[{\"ctin\":\"33GSPTN0782G1Z6\",\"chksum\":\"158b2b64c4f03bfe1c57d5fe949cf18ad7e274ef074dfd235d915b165516f243\",\"ttl_rec\":5,\"ttl_tax\":316000.23,\"ttl_igst\":55317.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":375000.01,\"ttl_cess\":3272.5},{\"ctin\":\"33GSPTN0781G1Z7\",\"chksum\":\"acec4579393970cde6b50cc7a826eb31abaaf183a47ced9727ccbf60d39cc759\",\"ttl_rec\":11,\"ttl_tax\":5960.0,\"ttl_igst\":956.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":397000.01,\"ttl_cess\":100.0},{\"ctin\":\"04AAACR5055K1ZF\",\"chksum\":\"7b73aa426dcf4599f576bcda969c91939da7ce3f6af26ed9a0d967fad2cd9948\",\"ttl_rec\":3,\"ttl_tax\":3000.0,\"ttl_igst\":150.0,\"ttl_sgst\":0.0,\"ttl_cgst\":0.0,\"ttl_val\":15000.0,\"ttl_cess\":300.0}]}]}],\"http_status_cd\":\"200\",\"meta\":{\"offset\":\"0\",\"level\":\"L0\",\"limit\":\"100\",\"fp\":\"072017\",\"section\":\"all\",\"gstin\":\"27GSPMH0782G1ZJ\"}}}";
	var obj=JSON.parse(json);
	var shareInfoLen = obj.l0ResponseMap.data[0].sec_sum.length;
	
	
	/*$.each(obj,function(i,value){*/
	
for(i=0; i<shareInfoLen; i++){
	 var xcv = obj.l0ResponseMap.data[0].sec_sum;
     var L0_mode=xcv[i].sec_nm;
     if(L0_mode =='B2B'){
    	 b2btotalinvoices = parseFloat(xcv[i].ttl_rec);
    	 b2btotaltaxableamt = parseFloat(xcv[i].ttl_tax);
    	 var igst = xcv[i].ttl_igst ? parseFloat(xcv[i].ttl_igst) : 0;
     	 var cgst = xcv[i].ttl_cgst ? parseFloat(xcv[i].ttl_cgst) : 0;
     	 var sgst = xcv[i].ttl_sgst ? parseFloat(xcv[i].ttl_sgst) : 0;
     	 var cess = xcv[i].ttl_cess ? parseFloat(xcv[i].ttl_cess) : 0;
    	 b2btotaltaxamt =  parseFloat(igst) + parseFloat(cgst) + parseFloat(sgst) + parseFloat(cess);
    	/* b2bgstnSavedcnt=parseFloat(xcv[i].GstnSaved_cnt);
    	 b2bgstnnotsavedcnt=parseFloat(xcv[i].GstnFailed_cnt);
    	 b2berrorsavedgstn=parseFloat(xcv[i].GstnFailed_cnt);*/
    	 
    	 
     }else if(L0_mode =='B2CL'){
        	b2cltotalinvoices = parseFloat(xcv[i].ttl_rec);
        	b2cltotaltaxableamt = parseFloat(xcv[i].ttl_tax);
        	var igst = xcv[i].ttl_igst ? parseFloat(xcv[i].ttl_igst) : 0;
        	var cgst = xcv[i].ttl_cgst ? parseFloat(xcv[i].ttl_cgst) : 0;
        	var sgst = xcv[i].ttl_sgst ? parseFloat(xcv[i].ttl_sgst) : 0;
        	var cess = xcv[i].ttl_cess ? parseFloat(xcv[i].ttl_cess) : 0;
        	b2cltotaltaxamt = parseFloat(igst) + parseFloat(cgst) + parseFloat(sgst) + parseFloat(cess);
        	/*b2clgstnSavedcnt=parseFloat(xcv[i].GstnSaved_cnt);
        	b2clgstnnotsavedcnt=parseFloat(xcv[i].GstnFailed_cnt);
        	b2clerrorsavedgstn=parseFloat(xcv[i].GstnFailed_cnt);*/
        	
        	
        	
	 }else if(L0_mode =='B2CS'){
	    	b2cstotalinvoices = parseFloat(xcv[i].ttl_rec);
	    	b2cstotaltaxableamt = parseFloat(xcv[i].ttl_tax); 
	    	var igst = xcv[i].ttl_igst ? parseFloat(xcv[i].ttl_igst) : 0;
	     	var cgst = xcv[i].ttl_cgst ? parseFloat(xcv[i].ttl_cgst) : 0;
	     	var sgst = xcv[i].ttl_sgst ? parseFloat(xcv[i].ttl_sgst) : 0;
	     	var cess = xcv[i].ttl_cess ? parseFloat(xcv[i].ttl_cess) : 0;
	    	b2cstotaltaxamt = parseFloat(igst) + parseFloat(cgst) + parseFloat(sgst) + parseFloat(cess);
		       
     }
	 else if(L0_mode =='CDNR'){
	    	cndntotalinvoices = parseFloat(xcv[i].ttl_rec);
	    	cndntotaltaxableamt = parseFloat(xcv[i].ttl_tax); 
	    	var igst = xcv[i].ttl_igst ? parseFloat(xcv[i].ttl_igst) : 0;
	     	var cgst = xcv[i].ttl_cgst ? parseFloat(xcv[i].ttl_cgst) : 0;
	     	var sgst = xcv[i].ttl_sgst ? parseFloat(xcv[i].ttl_sgst) : 0;
	     	var cess = xcv[i].ttl_cess ? parseFloat(xcv[i].ttl_cess) : 0;
	    	cndntotaltaxamt = parseFloat(igst) + parseFloat(cgst) + parseFloat(sgst) + parseFloat(cess);
		       
  }else if(L0_mode =='CDNUR'){
	    	cndurtotalinvoices = parseFloat(xcv[i].ttl_rec);
	    	cndurtotaltaxableamt = parseFloat(xcv[i].ttl_tax); 
	    	var igst = xcv[i].ttl_igst ? parseFloat(xcv[i].ttl_igst) : 0;
	     	var cgst = xcv[i].ttl_cgst ? parseFloat(xcv[i].ttl_cgst) : 0;
	     	var sgst = xcv[i].ttl_sgst ? parseFloat(xcv[i].ttl_sgst) : 0;
	     	var cess = xcv[i].ttl_cess ? parseFloat(xcv[i].ttl_cess) : 0;
	    	cndurtotaltaxamt = parseFloat(igst) + parseFloat(cgst) + parseFloat(sgst) + parseFloat(cess);
		       
  }
 }//end of for loop

displayDataInHtml(b2btotalinvoices,b2btotaltaxableamt,b2btotaltaxamt,b2cltotalinvoices,b2cltotaltaxableamt,b2cltotaltaxamt,b2cstotalinvoices,
		b2cstotaltaxableamt,b2cstotaltaxamt,b2bgstnSavedcnt,b2bgstnnotsavedcnt,b2berrorsavedgstn,b2clgstnSavedcnt,b2clgstnnotsavedcnt,b2clerrorsavedgstn,cndntotalinvoices,cndntotaltaxableamt,cndntotaltaxamt,
        cndurtotalinvoices,cndurtotaltaxableamt,cndurtotaltaxamt);
	
	 
}

function displayDataInHtml(b2btotalinvoices,b2btotaltaxableamt,b2btotaltaxamt,
		                   b2cltotalinvoices,b2cltotaltaxableamt,b2cltotaltaxamt,
		                   b2cstotalinvoices,b2cstotaltaxableamt,b2cstotaltaxamt,
		                   b2bgstnSavedcnt,b2bgstnnotsavedcnt,b2berrorsavedgstn,
		                   b2clgstnSavedcnt,b2clgstnnotsavedcnt,b2clerrorsavedgstn,cndntotalinvoices,cndntotaltaxableamt,cndntotaltaxamt,
		                   cndurtotalinvoices,cndurtotaltaxableamt,cndurtotaltaxamt){
	$("#L0Table").append(
		     '<table class="table table-bordered m-table-first-col-200 " >'
			    +'<tr class="m-table-header-color">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none">'
					+'<span>Supplies to Registered Customers (B2B)</span>'
				    +'<span><i class="fa fa-angle-down b2bdown"></i></span></th>'
				+'</tr>'
			    +'<tr >'
				    +'<td width="70%" ">Total Invoice:</td>'
				    +'<td width="30%"class="text-right">'+b2btotalinvoices+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td width="70%" ">Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2btotaltaxableamt+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" ">Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2btotaltaxamt+'</td>'
			    +'</tr>'
			   /* +'<tr class="b2bdata" >'
			    +'<td width="70%" bgcolor="#E9E9ED">Not Saved to GSTN:</td>'
			    +'<td width="30%"class="text-right">'+b2bgstnnotsavedcnt+'</td>'
		    +'</tr>'
		    +'<tr class="b2bdata">'
			    +'<td width="70%" bgcolor="#E9E9ED">Saved to GSTN:</td>'
			    +'<td width="30%" class="text-right">'+b2bgstnSavedcnt+'</td>'
		    +'</tr>'*/
		    /*+'<tr class="b2bdata">'
			    +'<td  width="70%" bgcolor="#E9E9ED">Error while saved to GSTN</td>'
			    +'<td width="30%" class="text-right">'+b2berrorsavedgstn+'</td>'
		    +'</tr>'*/
			 +'</table>'
	      );
		 $("#L0Table").append(
				   
			'<table class="table table-bordered m-table-first-col-200 ">'
			    +'<tr class="m-table-header-color">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none">'
					+'<span>Supplies to Unregistered-Large Customers (B2CL)</span>'
				    +'<span><i class="fa fa-angle-down b2cldown"></i></span></th>'
				+'</tr>'
			    +'<tr >'
				    +'<td width="70%" >Total Invoice:</td>'
				    +'<td width="30%" class="text-right">'+b2cltotalinvoices+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" >Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2cltotaltaxableamt+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" >Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2cltotaltaxamt+'</td>'
			    +'</tr>'
			    /*+'<tr class="b2cldata">'
			    +'<td width="70%" bgcolor="#E9E9ED">Not Saved to GSTN:</td>'
			    +'<td width="30%"class="text-right">'+b2clgstnnotsavedcnt+'</td>'
		    +'</tr>'
		    +'<tr class="b2cldata">'
			    +'<td width="70%" bgcolor="#E9E9ED">Saved to GSTN:</td>'
			    +'<td width="30%" class="text-right">'+b2clgstnSavedcnt+'</td>'
		    +'</tr>'*/
		   /* +'<tr class="b2cldata">'
			    +'<td  width="70%" bgcolor="#E9E9ED">Error while saved to GSTN</td>'
			    +'<td width="30%" class="text-right">'+b2clerrorsavedgstn+'</td>'
		    +'</tr>'*/
			 +'</table>'
	      );
		 $("#L0Table").append(
	    		   
			'<table class="table table-bordered m-table-first-col-200 ">'
			    +'<tr class="m-table-header-color">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none">'
					+'<span>Supplies to Unregistered-Small Customers (B2CS)</span>'
				    +'<span><i class="toogle_btn down_arrow"></i></span></th>'
				+'</tr>'
			    /*+'<tr >'
				    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
				    +'<td width="30%" class="text-right">'+b2cstotalinvoices+'</td>'
			    +'</tr>'*/
			    +'<tr>'
				    +'<td width="70%" >Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2cstotaltaxableamt+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td width="70%" >Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2cstotaltaxamt+'</td>'
			    +'</tr>'
		    +'</table>'

		 );
		 $("#L0Table").append(
	    		   
	  				
					'<table class="table table-bordered m-table-first-col-200 ">'
				    +'<tr class="m-table-header-color">'
						+'<th colspan="2" class="text-center text-gray border-bottom-none">'
						+'<span><a class="warning_icon"></a></span><span>Credit/Debit note issued to Registered Customers</span>'
					    +'<span><a class="toogle_btn down_arrow"></a></span></th>'
					+'</tr>'
				    +'<tr >'
					    +'<th>Total Invoice:</th>'
					    +'<td>'+cndntotalinvoices+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<th>Taxable amount(₹):</th>'
					    +'<td>'+cndntotaltaxableamt+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<th>Tax Amount (₹)</th>'
					    +'<td>'+cndntotaltaxamt+'</td>'
				    +'</tr>'
				 
			    +'</table>'

			 );
		$("#L0Table").append(
	    		   
				
				'<table class="table table-bordered m-table-first-col-200 ">'
			    +'<tr class="m-table-header-color">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none">'
					+'<span><a class="warning_icon"></a></span><span>Credit/Debit note issued to unregistered Customers</span>'
				    +'<span><a class="toogle_btn down_arrow"></a></span></th>'
				+'</tr>'
			    +'<tr >'
				    +'<th>Total Invoice:</th>'
				    +'<td>'+cndurtotalinvoices+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<th>Taxable amount(₹):</th>'
				    +'<td>'+cndurtotaltaxableamt+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<th>Tax Amount (₹)</th>'
				    +'<td>'+cndurtotaltaxamt+'</td>'
			    +'</tr>'
			  
		    +'</table>'

		 );
	
}
	  	
	  	$("#gobacktoasp").click(function(){
	  		
	  		gobacktoasp($("#return_gstinId").val(),$("#return_financialperiod").val(),$("#return_gstrtype").val(),$("#goback").val());
	  		
	  	});

	  	function gobacktoasp(return_gstinId,return_financialperiod,return_gstrtype,goback){
	  		
	  		document.gobacktoasp.action = "./gotoCompliance";                  
	  		document.gobacktoasp.return_gstinId.value = return_gstinId;
	  		document.gobacktoasp.return_financialperiod.value = return_financialperiod;
	  		document.gobacktoasp.return_gstrtype.value= return_gstrtype;
	  		document.gobacktoasp.goback.value = goback;
	  		document.gobacktoasp._csrf_token.value = $("#_csrf_token").val();
	  		document.gobacktoasp.submit();
	  	}
	//filing methods which are seperate 
	  	function otpforresponsefiletogstn(){
	  		var statusofaction = "";
	  		$.ajax({		
	  			url : "otpforresponsefiletogstn",
	  			method : "POST",
	  			data :{"gstin":gstin, "fp":fp},
	  		    contentType : "application/json",
	  			dataType : "json",
	  			async : false,
	  			headers: {_csrf_token : $("#_csrf_token").val()},
	  			success:function(json,fTextStatus,fRequest){
	  				console.log("json : "+json);
	  				if (isValidSession(json) == 'No') {
	  					window.location.href = getDefaultSessionExpirePage();
	  					return;
	  				}

	  				if(isValidToken(json) == 'No'){
	  					window.location.href = getCsrfErrorPage();
	  					return;
	  				}			
	  				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	  				statusofaction = json;		
	  				
	  			},
	  	        error: function (data,status,er) {        	 
	  	        	 getInternalServerErrorPage();   
	  	        }		
	  		});
	  		
	  		return statusofaction;
	  	}

	  	function getresponseforauthtokenindatabase(gstin,fp,GSTR_type,otp){
	  		var statusofaction = "";
	  		$.ajax({		
	  			url : "getresponseforauthtokenindatabase",
	  			method : "POST",
	  			data :{"gstin":gstin,"fp":fp,"gstrType":GSTR_type,"otp":otp},
	  		    /*contentType : "application/json",*/
	  			dataType : "json",
	  			async : false,
	  			headers: {_csrf_token : $("#_csrf_token").val()},
	  			success:function(json,fTextStatus,fRequest){
	  				console.log("json : "+json);
	  				if (isValidSession(json) == 'No') {
	  					window.location.href = getDefaultSessionExpirePage();
	  					return;
	  				}

	  				if(isValidToken(json) == 'No'){
	  					window.location.href = getCsrfErrorPage();
	  					return;
	  				}			
	  				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	  				statusofaction = json;		
	  				/*bootbox.alert("json:"+json+"\tstatusofaction:"+statusofaction);*/
	  			},
	  	        error: function (data,status,er) {        	 
	  	        	 getInternalServerErrorPage();   
	  	        }		
	  		});
	  		
	  		return statusofaction;
	  	}