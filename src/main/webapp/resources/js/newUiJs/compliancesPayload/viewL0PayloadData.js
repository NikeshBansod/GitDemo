 var L0_gstin ='';
 var L0_fp = '';
 var mode = '';
 var i='';
 var url = '';



$(document).ready(function(){
	$('.loader').show();
	 L0_gstin = $("#L0_gstin").val();
	 L0_fp = $("#L0_fp").val();
	 GSTR_type=$("#GSTR_type").val();
	 mode = $("#payload_mode").val();
	
	 
	 if(L0_gstin != '' && L0_fp != ''){
		 console.log("L0_gstin" +L0_gstin);
		 
			 url = "getgstr1l0response";
			
			 loadL0DetailsDraft(L0_gstin,L0_fp,url);
			 
		
	       
	 }
	 $(".b2bdown").click(function (){
		    $(".b2bdata").toggle();
		   
		});
	 $(".cndndown").click(function (){
		    $(".cndndata").toggle();
		   
		});
	 $(".cndurdown").click(function (){
		    $(".cndurdata").toggle();
		   
		});
	 
	 $(".b2cldown").click(function (){
		    $(".b2cldata").toggle();
		   
		});
	 
	 var locale = {
			    OK: 'I Suppose',
			    CONFIRM: 'Verify',
			    CANCEL: 'Resend'
			};
	 
	 $("#saveToGSTN").click(function (){	
		 $('.loader').show();
	   var otpresponse=getgstr1otpresponse();
		 
		
		
		if(otpresponse == "firsttimeloginuser" ||otpresponse=="invalidsession"||otpresponse== "expired" ){
			$('.loader').fadeOut("slow"); 
			 bootbox.addLocale('custom', locale);
			 bootbox.prompt({
			     	    title: "OTP has been sent on GSTN registered mobile number.",
			     	    inputType: 'text',
			     	     placeholder: "enter OTP here",
			     	    locale: 'custom',
			     	   
			     	   buttons: {
			     	        cancel: {
			     	        label: "Cancel",
			     	     },
			     	     confirm: {
			     	        label: "Verify & Save",
			     	    }
			     	  },
			     	    callback: function (result) {
			     	    	/*alert("my value is"+result);*/
			     	    	
			     	      if(result != "" && result != null){
			     	    	 $('.loader').show();
			     	    	           var statuscode= responsesavetogstn(L0_gstin,L0_fp,GSTR_type,result);
			     	    	
			     	    		/* var statuscode=responsesubmittogstn(L0_gstin,L0_fp,GSTR_type,result);*/
			     	        }
			     	      else if(result == ""){
			     	    	 $('.loader').fadeOut("slow"); 
			     	    	  bootbox.alert("Please enter OTP");
			     	      }
	     	            }
	     	});
		}
		else{
		   
		       redirectToUploadAspPage(L0_gstin,L0_fp,GSTR_type);
		    
		      /*redirectToUploadAspPageafterSubmit(L0_gstin,L0_fp,GSTR_type);*/
		    
		  }
	
		});
	 
	
});

function checkforsessionforsubmittoGSTN(L0_gstin,L0_fp,urlToCall){
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

function loadL0DetailsDraft(L0_gstin,L0_fp,urlToCall,otp){
	
	
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
		
			if(json != ''){
				
					displayDatainUI(json);
					setTimeout(function(){ 
						$('.loader').hide();
						$("#defaultShowDiv").show();
					}, 2000);
				
				
			}//end of json != null
			else{
				bootbox.alert("Something went wrong in API response");
				$('.loader').hide();
			}
			
			 
		},
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	
	});
    
   
}


function getgstr1otpresponse(){
	var statusofaction = "";
	$.ajax({		
		url : "getgstr1otpresponse",
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
			bootbox.alert("json:"+json+"\tstatusofaction:"+statusofaction);
		},
        error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }		
	});
	
	return statusofaction;
}



function responsesavetogstn(L0_gstin,L0_fp,GSTR_type,otp)
{
	$('.loader').show();
	 document.goBackToAspafterSuccessfulSaveToGSTN.action ="./responsesavetogstn";
     document.goBackToAspafterSuccessfulSaveToGSTN.L0_gstin.value = L0_gstin;
     document.goBackToAspafterSuccessfulSaveToGSTN.L0_fp.value = L0_fp;
     document.goBackToAspafterSuccessfulSaveToGSTN.GSTR_type.value = GSTR_type;
     document.goBackToAspafterSuccessfulSaveToGSTN.otp.value = otp;
     document.goBackToAspafterSuccessfulSaveToGSTN._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAspafterSuccessfulSaveToGSTN.submit();     
}

function redirectToUploadAspPage(L0_gstin,L0_fp,GSTR_type)
{
	 document.goBackToAsp.action ="./responsesavetogstn";
     document.goBackToAsp.L0_gstin.value = L0_gstin;
     document.goBackToAsp.L0_fp.value = L0_fp;
     document.goBackToAsp.GSTR_type.value = GSTR_type;
     document.goBackToAsp._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAsp.submit();     
	}

/*function responsesubmittogstn(L0_gstin,L0_fp,GSTR_type,otp)
{
	 document.goBackToAspafterSuccessfulSaveToGSTN.action ="./responsesubmittogstn";
     document.goBackToAspafterSuccessfulSaveToGSTN.L0_gstin.value = L0_gstin;
     document.goBackToAspafterSuccessfulSaveToGSTN.L0_fp.value = L0_fp;
     document.goBackToAspafterSuccessfulSaveToGSTN.GSTR_type.value = GSTR_type;
     document.goBackToAspafterSuccessfulSaveToGSTN.otp.value = otp;
     document.goBackToAspafterSuccessfulSaveToGSTN._csrf_token.value = $("#_csrf_token").val();
     document.goBackToAspafterSuccessfulSaveToGSTN.submit();     
	}

function redirectToUploadAspPageafterSubmit(L0_gstin,L0_fp,GSTR_type)
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
	
	var b2bgstnSavedcnt = 0;
	var b2bgstnnotsavedcnt = 0;
	var b2berrorsavedgstn = 0;
	
	var cndngstnSavedcnt = 0;
	var cndngstnnotsavedcnt = 0;
	var cndnerrorsavedgstn = 0;
	
	var cndurgstnSavedcnt = 0;
	var cndurgstnnotsavedcnt = 0;
	var cndurerrorsavedgstn = 0;
	
	var b2clgstnSavedcnt = 0;
	var b2clgstnnotsavedcnt = 0;
	var b2clerrorsavedgstn = 0;
	
	var obj=JSON.parse(json);
	var shareInfoLen = obj.data.length;
	console.log("shareInfoLen:"+obj.data.length);
	
	/*$.each(obj,function(i,value){*/
	
for(i=0; i<shareInfoLen; i++){
     var L0_mode=obj['data'][i].sec_nm;
      if(L0_mode =='b2b'){
	    	 b2btotalinvoices = parseFloat(obj['data'][i].ttl_count);
	    	 b2btotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	    	 b2btotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	    	 b2bgstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	    	 b2bgstnnotsavedcnt=parseFloat(obj['data'][i].New_cnt);
	    	 b2berrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
    	 
    	 
     }else if(L0_mode =='b2cl'){
        	b2cltotalinvoices = parseFloat(obj['data'][i].ttl_count);
        	b2cltotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
        	b2cltotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
        	b2clgstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
        	b2clgstnnotsavedcnt=parseFloat(obj['data'][i].New_cnt);
        	b2clerrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
        	
        	
        	
	 }else if(L0_mode =='b2cs'){
	    	b2cstotalinvoices = parseFloat(obj['data'][i].ttl_count);
	    	b2cstotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);   
	    	b2cstotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
		       
     }else if(L0_mode =='cdnr'){
	    	cndntotalinvoices = parseFloat(obj['data'][i].ttl_count);
	    	cndntotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	    	cndntotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	    	cndngstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	    	cndngstnnotsavedcnt=parseFloat(obj['data'][i].New_cnt);
	    	cndnerrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
		       
     }else if(L0_mode =='cdnur'){
    	    cndurtotalinvoices = parseFloat(obj['data'][i].ttl_count);
	    	cndurtotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	    	cndurtotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	    	cndurgstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	    	cndurgstnnotsavedcnt=parseFloat(obj['data'][i].New_cnt);
	    	cndurerrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
     }
 }//end of for loop

displayDataInHtml(b2btotalinvoices,b2btotaltaxableamt,b2btotaltaxamt,b2cltotalinvoices,b2cltotaltaxableamt,b2cltotaltaxamt,b2cstotalinvoices,
		b2cstotaltaxableamt,b2cstotaltaxamt,b2bgstnSavedcnt,b2bgstnnotsavedcnt,b2berrorsavedgstn,b2clgstnSavedcnt,b2clgstnnotsavedcnt,b2clerrorsavedgstn,
		cndntotalinvoices,cndntotaltaxableamt,cndntotaltaxamt,cndngstnSavedcnt,cndngstnnotsavedcnt,cndnerrorsavedgstn,
		cndurtotalinvoices,cndurtotaltaxableamt,cndurtotaltaxamt,cndurgstnSavedcnt,cndurgstnnotsavedcnt,cndurerrorsavedgstn);
	
	 
}

function displayDataInHtml(b2btotalinvoices,b2btotaltaxableamt,b2btotaltaxamt,
		                   b2cltotalinvoices,b2cltotaltaxableamt,b2cltotaltaxamt,
		                   b2cstotalinvoices,b2cstotaltaxableamt,b2cstotaltaxamt,
		                   b2bgstnSavedcnt,b2bgstnnotsavedcnt,b2berrorsavedgstn,
		                   b2clgstnSavedcnt,b2clgstnnotsavedcnt,b2clerrorsavedgstn,
		                   cndntotalinvoices,cndntotaltaxableamt,cndntotaltaxamt,
		                   cndngstnSavedcnt,cndngstnnotsavedcnt,cndnerrorsavedgstn,
		           		   cndurtotalinvoices,cndurtotaltaxableamt,cndurtotaltaxamt,
		           		   cndurgstnSavedcnt,cndurgstnnotsavedcnt,cndurerrorsavedgstn){
	$("#L0Table").append(
		     '<table class="table table-bordered m-table-first-col-200 hide_row" >'
			    +'<tr class="m-table-header-color" bgcolor="#666">'
					+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
					+'<span>Supplies to Registered Customers (B2B)</span>'
				    +'<span><i class="fa fa-angle-down b2bdown"></i></span></th>'
				+'</tr>'
			    +'<tr >'
				    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
				    +'<td width="30%"class="text-right">'+b2btotalinvoices+'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
				    +'<td width="30%" class="text-right">'+b2btotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 }) +'</td>'
			    +'</tr>'
			    +'<tr>'
				    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
				    +'<td width="30%" class="text-right">'+b2btotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
			    +'</tr>'
			    +'<tr class="b2bdata" >'
			    +'<td width="70%" bgcolor="#E9E9ED">Not Saved to GSTN:</td>'
			    +'<td width="30%"class="text-right">'+b2bgstnnotsavedcnt+'</td>'
		    +'</tr>'
		    +'<tr class="b2bdata">'
			    +'<td width="70%" bgcolor="#E9E9ED">Saved to GSTN:</td>'
			    +'<td width="30%" class="text-right">'+b2bgstnSavedcnt+'</td>'
		    +'</tr>'
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
				    +'<span><i class="fa fa-angle-down b2cldown"></i></span></th>'
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
			    +'<tr class="b2cldata">'
			    +'<td width="70%" bgcolor="#E9E9ED">Not Saved to GSTN:</td>'
			    +'<td width="30%"class="text-right">'+b2clgstnnotsavedcnt+'</td>'
		    +'</tr>'
		    +'<tr class="b2cldata">'
			    +'<td width="70%" bgcolor="#E9E9ED">Saved to GSTN:</td>'
			    +'<td width="30%" class="text-right">'+b2clgstnSavedcnt+'</td>'
		    +'</tr>'
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
				    +'<span><i class="toogle_btn down_arrow"></i></span></th>'
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

		 );
		 $("#L0Table").append(
	    		   
				 '<table class="table table-bordered m-table-first-col-200 hide_row" >'
				    +'<tr class="m-table-header-color" bgcolor="#666">'
						+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
						+'<span>Credit/Debit note issued to Registered Customers</span>'
					    +'<span><i class="fa fa-angle-down cndndown"></i></span></th>'
					+'</tr>'
				    +'<tr >'
					    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
					    +'<td width="30%"class="text-right">'+cndntotalinvoices+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<td width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
					    +'<td width="30%" class="text-right">'+cndntotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
					    +'<td width="30%" class="text-right">'+cndntotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
				    +'</tr>'
				    +'<tr class="cndndata" >'
				    +'<td width="70%" bgcolor="#E9E9ED">Not Saved to GSTN:</td>'
				    +'<td width="30%"class="text-right">'+cndngstnnotsavedcnt+'</td>'
			    +'</tr>'
			    +'<tr class="cndndata">'
				    +'<td width="70%" bgcolor="#E9E9ED">Saved to GSTN:</td>'
				    +'<td width="30%" class="text-right">'+cndngstnSavedcnt+'</td>'
			    +'</tr>'
			    /*+'<tr class="b2bdata">'
				    +'<td  width="70%" bgcolor="#E9E9ED">Error while saved to GSTN</td>'
				    +'<td width="30%" class="text-right">'+b2berrorsavedgstn+'</td>'
			    +'</tr>'*/
				 +'</table>'

				 );
		 $("#L0Table").append(
	    		   
				 '<table class="table table-bordered m-table-first-col-200 hide_row" >'
				    +'<tr class="m-table-header-color" bgcolor="#666">'
						+'<th colspan="2" class="text-center text-gray border-bottom-none white">'
						+'<span>Credit/Debit note issued to unregistered Customers</span>'
					    +'<span><i class="fa fa-angle-down cndurdown"></i></span></th>'
					+'</tr>'
				    +'<tr >'
					    +'<td width="70%" bgcolor="#E9E9ED">Total Invoice:</td>'
					    +'<td width="30%"class="text-right">'+cndurtotalinvoices+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<td width="70%" bgcolor="#E9E9ED">Taxable amount(₹):</td>'
					    +'<td width="30%" class="text-right">'+cndurtotaltaxableamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<td  width="70%" bgcolor="#E9E9ED">Tax Amount (₹)</td>'
					    +'<td width="30%" class="text-right">'+cndurtotaltaxamt.toLocaleString('en-IN',{ minimumFractionDigits: 2 })+'</td>'
				    +'</tr>'
				    +'<tr class="cndurdata" >'
				    +'<td width="70%" bgcolor="#E9E9ED">Not Saved to GSTN:</td>'
				    +'<td width="30%"class="text-right">'+cndurgstnnotsavedcnt+'</td>'
			    +'</tr>'
			    +'<tr class="cndurdata">'
				    +'<td width="70%" bgcolor="#E9E9ED">Saved to GSTN:</td>'
				    +'<td width="30%" class="text-right">'+cndurgstnSavedcnt+'</td>'
			    +'</tr>'
			    /*+'<tr class="b2bdata">'
				    +'<td  width="70%" bgcolor="#E9E9ED">Error while saved to GSTN</td>'
				    +'<td width="30%" class="text-right">'+b2berrorsavedgstn+'</td>'
			    +'</tr>'*/
				 +'</table>'

				 );
	
}

$("#goBackToAspHomepage").click(function(){
    $('.loader').show();
	goBackFromL0ResponsePage($("#L0_gstin").val(),$("#L0_fp").val(),$("#GSTR_type").val());
	$('.loader').fadeOut("slow"); 
});

function goBackFromL0ResponsePage(L0_gstin,L0_fp,GSTR_type){
	
	document.goBackToAsp.action = "./gobacktoasphome";                  
	document.goBackToAsp.L0_gstin.value = L0_gstin;
	document.goBackToAsp.L0_fp.value = L0_fp;
	document.goBackToAsp.GSTR_type.value = GSTR_type;
	document.goBackToAsp._csrf_token.value = $("#_csrf_token").val();
	document.goBackToAsp.submit();
}


