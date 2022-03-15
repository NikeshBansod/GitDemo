 var L0_gstin ='';
 var L0_fp = '';
 var mode = '';
 var i='';
 var url = '';
 var getresponseforauthtokenindatabaseStatus='';
 

$(document).ready(function(){
	$('.loader').show();
	 L0_gstin = $("#L0_gstin").val();
	 L0_fp = $("#L0_fp").val();
	 GSTR_type=$("#GSTR_type").val();
	 mode = $("#payload_mode").val();
	
	 
	 if(L0_gstin != '' && L0_fp != ''){//SAME AS SUBMIT
		
			 url = "getgstr1responsefromGSTN";
			 var statusofaction= checkforsessionforsubmittoGSTN(L0_gstin,L0_fp,url);
			 
				if(statusofaction== "expired" ||statusofaction== "invalidsession" || statusofaction== "firsttimeloginuser"){
					$('.loader').fadeOut("slow");
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
					     	        confirm: {
					     	            label: "Verify & Save",
					     	        }
					     	   },
					     	    callback: function (result) {
					     	    	/*alert("my value is"+result);*/
					     	    	
					     	      if(result != "" && result != null){
					     	    	    url="getgstr1submittogstnl0response";
					     	    		/*responsesubmittogstn(L0_gstin,L0_fp,GSTR_type,result);*/
					     	    		loadL0DetailsDraft(L0_gstin,L0_fp,url,result);
					     	        }else{
					     	        	bootbox.alert("Please enter OTP.");
					     	        }
			     	            }
			     	});
				}
				else{
					 url="getgstr1submittogstnl0response";
					 result="";
	     	    		/*responsesubmittogstn(L0_gstin,L0_fp,GSTR_type,result);*/
	     	    	 loadL0DetailsDraft(L0_gstin,L0_fp,url,result);
				}
	       
	 }
	 /*$(".b2bdown").click(function (){
		    $(".b2bdata").toggle();
		   
		});
	 
	 $(".b2cldown").click(function (){
		    $(".b2cldata").toggle();
		   
		});*/
	 
	 var locale = {
			    OK: 'I Suppose',
			    CONFIRM: 'Verify',
			    CANCEL: 'Resend'
			};
	 
	 $("#fileToGSTN").click(function (){
		 $('.loader').show();
		 url = "getgstr1responsefromGSTN";
		 var statusofaction= checkforsessionforsubmittoGSTN(L0_gstin,L0_fp,url);
		 
		 if(statusofaction=="expired" || statusofaction=="invalidsession" ||statusofaction== "firsttimeloginuser"){
				$('.loader').fadeOut("slow");
			 
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
								     	    	
								     	    	
								     	      if(result != "" && result != null){
								     	    	 
								     	    	getresponseforauthtokenindatabaseStatus=getresponseforauthtokenindatabase(L0_gstin,L0_fp,GSTR_type,result);
								     	    	
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
												     	      if(result != "" && result != null){
												     	    	 responsefiletogstn(L0_gstin,L0_fp,GSTR_type,result);
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
			 var otpforresponsefiletogstnstatus= otpforresponsefiletogstn(L0_gstin,L0_fp);
			 if(otpforresponsefiletogstnstatus=="success"){
				 $('.loader').fadeOut("slow");
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
			     	    	
			     	    	
			     	      if(result != "" && result != null){
			     	    	 
			     	    	 responsefiletogstn(L0_gstin,L0_fp,GSTR_type,result);
			     	        }else{
			     	        	bootbox.alert("Please enter OTP.");
			     	        }
	     	            }
	     	     });
			 }else{
				 $('.loader').fadeOut("slow");
				 bootbox.alert("OTP sending fail.Please try again later.");
			 }
	    }
		
	});
	
});

function checkforsessionforsubmittoGSTN(L0_gstin,L0_fp,urlToCall){//SAME AS SUBMIT
	var statusofaction = "";
	$.ajax({		
		url : urlToCall,
		method : "POST",
		data :{"gstin":L0_gstin, "fp":L0_fp},
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
			
		},
        error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }		
	});
	
	return statusofaction;
	
}

function loadL0DetailsDraft(L0_gstin,L0_fp,urlToCall,otp){//SAME AS SUBMIT
	
	
    $.ajax({
		url : urlToCall,
		method : "POST",
		data :{"gstin":L0_gstin, "fp":L0_fp ,"otp":otp},
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
		
			if(json != 'Something went wrong in API response' && json !='Please select the preference Or Unauthorized User!' && json != 'OTP is incorrect.' && json != '' && json !='Please select the preference' && json !="Validation Failed : otp should be 6 digit integer."
				&& json !="Maximum session allowed for user with this GSP account exceeded." && json != "something went wrong in data"){
				
					displayDatainUI(json);
					setTimeout(function(){ 
						$('.loader').hide();
						$("#defaultShowDiv").show();
					}, 2000);
				
				
			}//end of json != null
			else{
				gobacktoasphomeforotperror(L0_gstin,L0_fp,GSTR_type,json);
			}
			
			 
		},
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	
	});
    
   
}


function otpforresponsefiletogstn(L0_gstin,L0_fp){
	var statusofaction = "";
	$.ajax({		
		url : "otpforresponsefiletogstn",
		method : "POST",
		data :{"gstin":L0_gstin, "fp":L0_fp},
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
			
		},
        error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }		
	});
	
	return statusofaction;
}

function getgstr1responsefromGSTN(){
	var statusofaction = "";
	$.ajax({		
		url : "getgstr1responsefromGSTN",
		method : "POST",
		data :{"gstin":L0_gstin, "fp":L0_fp},
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

function getresponseforauthtokenindatabase(L0_gstin,L0_fp,GSTR_type,otp){
	var statusofaction = "";
	$.ajax({		
		url : "getresponseforauthtokenindatabase",
		method : "POST",
		data :{"gstin":L0_gstin,"fp":L0_fp,"gstrType":GSTR_type,"otp":otp},
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

function gobacktoasphomeforotperror(L0_gstin,L0_fp,GSTR_type,message)
{
	 document.gobacktoasphomeforotperror.action ="./gobacktoasphomeforotperrorfromfile";
     document.gobacktoasphomeforotperror.L0_gstin.value = L0_gstin;
     document.gobacktoasphomeforotperror.L0_fp.value = L0_fp;
     document.gobacktoasphomeforotperror.GSTR_type.value = GSTR_type;
     document.gobacktoasphomeforotperror.message.value = message;
     document.gobacktoasphomeforotperror._csrf_token.value = $("#_csrf_token").val();
     document.gobacktoasphomeforotperror.submit();     
	}


function responsefiletogstn(L0_gstin,L0_fp,GSTR_type,otp)
{    
	$('.loader').show();
	 document.goBackToAspafterSuccessfulSaveToGSTN.action ="./responsefiletogstn";
     document.goBackToAspafterSuccessfulSaveToGSTN.L0_gstin.value = L0_gstin;
     document.goBackToAspafterSuccessfulSaveToGSTN.L0_fp.value = L0_fp;
     document.goBackToAspafterSuccessfulSaveToGSTN.GSTR_type.value = GSTR_type;
     document.goBackToAspafterSuccessfulSaveToGSTN.otp.value = otp;
     document.goBackToAspafterSuccessfulSaveToGSTN._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAspafterSuccessfulSaveToGSTN.submit();     
	}

/*function redirectToUploadAspPageafterSubmit(L0_gstin,L0_fp,GSTR_type)
{
	 document.goBackToAsp.action ="./responsesubmittogstn";
     document.goBackToAsp.L0_gstin.value = L0_gstin;
     document.goBackToAsp.L0_fp.value = L0_fp;
     document.goBackToAsp.GSTR_type.value = GSTR_type;
     document.goBackToAsp._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAsp.submit();     
	}*/


function displayDatainUI(json){
	var b2btotaltaxamt = 0;
	var b2cltotaltaxamt = 0;
	var b2cstotaltaxamt = 0;
	var cndntotaltaxamt = 0;
	var cndurtotaltaxamt = 0;
	
	var b2btotalinvoices = 0 ;
	var b2cltotalinvoices = 0 ;
	var b2cstotalinvoices = 0 ;
	var cndntotalinvoices = 0;
	var cndurtotalinvoices = 0;
	
	var b2btotaltaxableamt = 0 ;
	var b2cltotaltaxableamt = 0 ;
	var b2cstotaltaxableamt = 0 ;
	var cndntotaltaxableamt = 0;
	var cndurtotaltaxableamt = 0;
	
	var b2bgstnSavedcnt=0;
	var b2bgstnnotsavedcnt=0;
	var b2berrorsavedgstn=0;
	
	var b2clgstnSavedcnt=0;
	var b2clgstnnotsavedcnt=0;
	var b2clerrorsavedgstn=0;
	
	var cndngstnSavedcnt = 0;
	var cndngstnnotsavedcnt = 0;
	var cndnerrorsavedgstn = 0;
	
	var cndurgstnSavedcnt = 0;
	var cndurgstnnotsavedcnt = 0;
	var cndurerrorsavedgstn = 0;
	
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
		       
     }else if(L0_mode =='CDNR'){
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
		b2cstotaltaxableamt,b2cstotaltaxamt,b2bgstnSavedcnt,b2bgstnnotsavedcnt,b2berrorsavedgstn,b2clgstnSavedcnt,b2clgstnnotsavedcnt,b2clerrorsavedgstn,
		cndntotalinvoices,cndntotaltaxableamt,cndntotaltaxamt,cndurtotalinvoices,cndurtotaltaxableamt,cndurtotaltaxamt);
	
	 
}

function displayDataInHtml(b2btotalinvoices,b2btotaltaxableamt,b2btotaltaxamt,
        b2cltotalinvoices,b2cltotaltaxableamt,b2cltotaltaxamt,
        b2cstotalinvoices,b2cstotaltaxableamt,b2cstotaltaxamt,
        b2bgstnSavedcnt,b2bgstnnotsavedcnt,b2berrorsavedgstn,
        b2clgstnSavedcnt,b2clgstnnotsavedcnt,b2clerrorsavedgstn,
        cndntotalinvoices,cndntotaltaxableamt,cndntotaltaxamt,
        cndurtotalinvoices,cndurtotaltaxableamt,cndurtotaltaxamt){
	$("#L0Table").append(
		     '<table class="table table-bordered m-table-first-col-200 hide_row" >'
			    +'<tr class="m-table-header-color" bgcolor="#666">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
					+'<span>Supplies to Registered Customers (B2B)</span>'
				    /*+'<span><i class="fa fa-angle-down b2bdown"></i></span></th>'*/
				+'</tr>'
			    +'<tr >'
				    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
				    +'<td width="30%"class="text-right">'+b2btotalinvoices+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2btotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2btotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
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
			    +'<tr class="m-table-header-color" bgcolor="#666">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
					+'<span>Supplies to Unregistered-Large Customers (B2CL)</span>'
				   /* +'<span><i class="fa fa-angle-down b2cldown"></i></span></th>'*/
				+'</tr>'
			    +'<tr >'
				    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
				    +'<td width="30%" class="text-right">'+b2cltotalinvoices+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2cltotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2cltotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
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
			    +'<tr class="m-table-header-color" bgcolor="#666" >'
					+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
					+'<span>Supplies to Unregistered-Small Customers (B2CS)</span>'
				   /* +'<span><i class="toogle_btn down_arrow"></i></span></th>'*/
				+'</tr>'
			    /*+'<tr >'
				    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
				    +'<td width="30%" class="text-right">'+b2cstotalinvoices+'</td>'
			    +'</tr>'*/
			    +'<tr>'
				    +'<td width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2cstotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2cstotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
			    +'</tr>'
		    +'</table>'

		 ); $("#L0Table").append(
				   
					'<table class="table table-bordered m-table-first-col-200 ">'
					    +'<tr class="m-table-header-color" bgcolor="#666">'
							+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
							+'<span>Credit/Debit note issued to Registered Customers</span>'
						   /* +'<span><i class="fa fa-angle-down b2cldown"></i></span></th>'*/
						+'</tr>'
					    +'<tr >'
						    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
						    +'<td width="30%" class="text-right">'+cndntotalinvoices+'</td>'
					    +'</tr>'
					    +'<tr>'
						    +'<td  width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
						    +'<td width="30%" class="text-right">'+cndntotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
					    +'</tr>'
					    +'<tr>'
						    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
						    +'<td width="30%" class="text-right">'+cndntotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
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
					    +'<tr class="m-table-header-color" bgcolor="#666">'
							+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
							+'<span>Credit/Debit note issued to unregistered Customers</span>'
						   /* +'<span><i class="fa fa-angle-down b2cldown"></i></span></th>'*/
						+'</tr>'
					    +'<tr >'
						    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
						    +'<td width="30%" class="text-right">'+cndurtotalinvoices+'</td>'
					    +'</tr>'
					    +'<tr>'
						    +'<td  width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
						    +'<td width="30%" class="text-right">'+cndurtotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
					    +'</tr>'
					    +'<tr>'
						    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
						    +'<td width="30%" class="text-right">'+cndurtotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
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
	
	
}

$("#goBackToAspHomepage").click(function(){
	
	goBackFromL0ResponsePage($("#L0_gstin").val(),$("#L0_fp").val(),$("#GSTR_type").val());
	
});

function goBackFromL0ResponsePage(L0_gstin,L0_fp,GSTR_type){
	
	document.goBackToAsp.action = "./gobacktoasphome";                  
	document.goBackToAsp.L0_gstin.value = L0_gstin;
	document.goBackToAsp.L0_fp.value = L0_fp;
	document.goBackToAsp.GSTR_type.value = GSTR_type;
	document.goBackToAsp._csrf_token.value = $("#_csrf_token").val();
	document.goBackToAsp.submit();
}


