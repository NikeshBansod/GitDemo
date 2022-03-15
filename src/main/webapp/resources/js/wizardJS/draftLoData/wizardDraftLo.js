

$(document).ready(function(){
	

	
	var fp = $("#financialPeriod").val();
	 var gstin = $("#gstinId").val();
	 var fpYear=$("#fpYear").val();
	 var month=$("#month").val();
	 getDataList(gstin,fp);
	
	function getDataList(gstin,fp){
		 /*var inputData={
				 
                 "gstin" : gstin,
                 "fp" : fp,
                 
   
          }*/
		 
		 
		 
	       $.ajax({
	              url : "wgetgstr1l0response",
	              method : "POST",
	            
	     		  data :{"gstin":gstin,"fp":fp},
	              //contentType : "application/json",
	              dataType : "json",
	              async : false,
	              headers: {
	  	            _csrf_token : $("#_csrf_token").val()
	  	            
	  	     },
	  	     success:function(json,fTextStatus,fRequest){
	  	    	 
	  	  /*  	console.log("json : "+json);*/
	  	    	
	  	    	
	  	    	if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	  	     
	  	   var b2btotaltaxamt = 0;
	  		var b2cltotaltaxamt = 0;
	  		var b2cstotaltaxamt = 0;
	  		 var b2bgstnSavedcnt=0;
		    	var  b2bgstnnotsavedcnt=0;
		    	var  b2berrorsavedgstn=0;
	  		
	  		var b2btotalinvoices = 0 ;
	  		var b2cltotalinvoices = 0 ;
	  		var b2cstotalinvoices = 0 ;
	  		var b2clgstnSavedcnt=0;
	  		var b2clgstnnotsavedcnt=0;
	  		var b2clerrorsavedgstn=0;
	  		
	  		var b2btotaltaxableamt = 0 ;
	  		var b2cltotaltaxableamt = 0 ;
	  		var b2cstotaltaxableamt = 0 ;
	  		
	  		
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
	  		
	  		var obj=JSON.parse(json);
	  		 shareInfoLen = obj.data.length;
	  		/*console.log("shareInfoLen:"+obj.data.length+obj);*/
	  		//iterate through the length and pick the desired object we want to print.the name of object
	  		for(i=0; i<shareInfoLen; i++){
	  		     var L0_mode=obj['data'][i].sec_nm;
	  		     if(L0_mode =='b2b'){
	  		    	 b2btotalinvoices = parseFloat(obj['data'][i].ttl_count);
	  		    	 b2btotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	  		    	 b2btotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	  		    	 b2bgstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	  		    	 b2bgstnnotsavedcnt=parseFloat(obj['data'][i].GstnFailed_cnt);
	  		    	 b2berrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
	  		     }else if(L0_mode =='b2cl'){
	  		        	b2cltotalinvoices = parseFloat(obj['data'][i].ttl_count);
	  		        	b2cltotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	  		        	b2cltotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	  		        	b2clgstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	  		        	b2clgstnnotsavedcnt=parseFloat(obj['data'][i].GstnFailed_cnt);
	  		        	b2clerrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
	  			 }else if(L0_mode =='b2cs'){
	  			    	b2cstotalinvoices = parseFloat(obj['data'][i].ttl_count);
	  			    	b2cstotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);   
	  			    	b2cstotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	  			    	
	  			 }
	  			else if(L0_mode =='cdnr'){
	  		    	cndntotalinvoices = parseFloat(obj['data'][i].ttl_count);
	  		    	cndntotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	  		    	cndntotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	  		    	cndngstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	  		    	cndngstnnotsavedcnt=parseFloat(obj['data'][i].GstnFailed_cnt);
	  		    	cndnerrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
	  			       
	  	     }else if(L0_mode =='cdnur'){
	  	    	    cndurtotalinvoices = parseFloat(obj['data'][i].ttl_count);
	  		    	cndurtotaltaxableamt = parseFloat(obj['data'][i].ttl_txval);
	  		    	cndurtotaltaxamt = parseFloat(obj['data'][i].ttl_igst) + parseFloat(obj['data'][i].ttl_cgst) + parseFloat(obj['data'][i].ttl_sgst) + parseFloat(obj['data'][i].ttl_cess);
	  		    	cndurgstnSavedcnt=parseFloat(obj['data'][i].GstnSaved_cnt);
	  		    	cndurgstnnotsavedcnt=parseFloat(obj['data'][i].GstnFailed_cnt);
	  		    	cndurerrorsavedgstn=parseFloat(obj['data'][i].GstnFailed_cnt);
	  	     }
	  		     
	  		     
	  		 } //for end 
	  		$("#l0draft").empty();
	  		
	  		$("#l0draft").append(
	  			     
	  				'<table class="table table-bordered m-table-first-col-200 ">'
				    +'<tr class="m-table-header-color">'
				    
						+'<th colspan="2" class="text-center text-gray border-bottom-none">'
						+'<span><a class="warning_icon"></a></span><span>Supplies to Registered Customers (B2B)</span>'
					    +'<span><a class="toogle_btn down_arrow"></a></span></th>'
					+'</tr>'
				    +'<tr >'
					    +'<th>Total Invoice:</th>'
					    +'<td>'+b2btotalinvoices+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<th>Taxable amount(₹):</th>'
					    +'<td>'+(b2btotaltaxableamt).toFixed(2)+'</td>'
				    +'</tr>'
				    +'<tr>'
					    +'<th>Tax Amount (₹)</th>'
					    +'<td>'+(b2btotaltaxamt).toFixed(2)+'</td>'
				    +'</tr>'
				    +'<tr>'
				    +'<th>Saved To Gstn </th>'
				    +'<td>'+ b2bgstnSavedcnt+'</td>'
			    +'</tr>'
			    +'<tr>'
			    +'<th>Not Saved To Gstn </th>'
			    +'<td>'+b2bgstnnotsavedcnt+'</td>'
		    +'</tr>'
		    
				 +'</table>'
	  				 
	  		      );
	  			 $("#l0draft").append(
	  					   
	  				
	  					'<table class="table table-bordered m-table-first-col-200 ">'
	 				    +'<tr class="m-table-header-color">'
	  						+'<th colspan="2" class="text-center text-gray border-bottom-none">'
	  						+'<span><a class="warning_icon"></a></span><span>Supplies to Unregistered-Large Customers (B2CL)</span>'
	  					    +'<span><a class="toogle_btn down_arrow"></a></span></th>'
	  					+'</tr>'
	  				    +'<tr >'
	  					    +'<th>Total Invoice:</th>'
	  					    +'<td>'+b2cltotalinvoices+'</td>'
	  				    +'</tr>'
	  				    +'<tr>'
	  					    +'<th>Taxable amount(₹):</th>'
	  					    +'<td>'+(b2cltotaltaxableamt).toFixed(2)+'</td>'
	  				    +'</tr>'
	  				    +'<tr>'
	  					    +'<th>Tax Amount (₹)</th>'
	  					    +'<td>'+(b2cltotaltaxamt).toFixed(2)+'</td>'
	  				    +'</tr>'
	  				  +'<tr>'
					    +'<th>Saved To Gstn </th>'
					    +'<td>'+ b2clgstnSavedcnt+'</td>'
				    +'</tr>'
				    +'<tr>'
				    +'<th>Not Saved To Gstn </th>'
				    +'<td>'+b2clgstnnotsavedcnt+'</td>'
			    +'</tr>'
	  				 +'</table>'
	  				 
	  		      );
	  			 $("#l0draft").append(
	  		    		   
	  				
	  					'<table class="table table-bordered m-table-first-col-200 ">'
	 				    +'<tr class="m-table-header-color">'
	  						+'<th colspan="2" class="text-center text-gray border-bottom-none">'
	  						+'<span><a class="warning_icon"></a></span><span>Supplies to Unregistered-Small Customers (B2CS)</span>'
	  					    +'<span><a class="toogle_btn down_arrow"></a></span></th>'
	  					+'</tr>'
	  				    +'<tr >'
	  					    +'<th>Total Invoice:</th>'
	  					    +'<td>'+b2cstotalinvoices+'</td>'
	  				    +'</tr>'
	  				    +'<tr>'
	  					    +'<th>Taxable amount(₹):</th>'
	  					    +'<td>'+(b2cstotaltaxableamt).toFixed(2)+'</td>'
	  				    +'</tr>'
	  				    +'<tr>'
	  					    +'<th>Tax Amount (₹)</th>'
	  					    +'<td>'+(b2cstotaltaxamt).toFixed(2)+'</td>'
	  				    +'</tr>'
	  				    
	  			    +'</table>'

	  			 );
	  			 
	  			 
	  			 
	  			 $("#l0draft").append(
	  		    		   
	 	  				
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
		  					    +'<td>'+(cndntotaltaxableamt).toFixed(2)+'</td>'
		  				    +'</tr>'
		  				    +'<tr>'
		  					    +'<th>Tax Amount (₹)</th>'
		  					    +'<td>'+(cndntotaltaxamt).toFixed(2)+'</td>'
		  				    +'</tr>'
		  				  +'<tr>'
						    +'<th>Saved To Gstn </th>'
						    +'<td>'+ cndngstnSavedcnt+'</td>'
					    +'</tr>'
					    +'<tr>'
					    +'<th>Not Saved To Gstn </th>'
					    +'<td>'+cndngstnnotsavedcnt+'</td>'
				    +'</tr>'
		  			    +'</table>'

		  			 );
	  			$("#l0draft").append(
	  		    		   
	 	  				
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
	  					    +'<td>'+(cndurtotaltaxableamt).toFixed(2)+'</td>'
	  				    +'</tr>'
	  				    +'<tr>'
	  					    +'<th>Tax Amount (₹)</th>'
	  					    +'<td>'+(cndurtotaltaxamt).toFixed(2)+'</td>'
	  				    +'</tr>'
	  				  +'<tr>'
					    +'<th>Saved To Gstn </th>'
					    +'<td>'+ cndurgstnSavedcnt+'</td>'
				    +'</tr>'
				    +'<tr>'
				    +'<th>Not Saved To Gstn </th>'
				    +'<td>'+cndurgstnnotsavedcnt+'</td>'
			    +'</tr>'
	  			    +'</table>'

	  			 );
	  			
	  			
	  		
	  		
	  		
	  		
	  		
	  		
	  	     } ,
	  	   error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
	  	     
	  	     
	  	     
	       });
		
	  	 
	  	 $("#proceed").click(function (){
	  		var fp = $("#financialPeriod").val();
			 var gstin = $("#gstinId").val();
			 var fpYear=$("#fpYear").val();
			 var month=$("#month").val();
	  		 verifySession(gstin,fp,fpYear,month)
	  		});
	  	 
	  	 
	  	function verifySession(gstin,fp,fpYear,month){
	  		
	  		
	  		$.ajax({
	  			
	  			url : "wgetgstr1otpresponse",
	  			method : "POST",
	  			
	  			
	  			 
	  			
	  			data :{"gstin":gstin, "fp":fp},
	  		    /*contentType : "application/json",*/
	  			dataType : "json",
	  			async : false,
	  			headers: {_csrf_token : $("#_csrf_token").val()},
	  			success:function(json,fTextStatus,fRequest){
	  				
	  				if (isValidSession(json) == 'No') {
	  					window.location.href = getDefaultSessionExpirePage();
	  					return;
	  				}

	  				if(isValidToken(json) == 'No'){
	  					window.location.href = getCsrfErrorPage();
	  					return;
	  				}
	  				
	  				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	  				
	  				
	  				console.log("json : "+json);
	  				 
						 /*bootbox.alert('You have set Auto Upload setting for this GSTIN. To still Upload the data Manually change your settings');*/
	  					console.log("ne");
	  					if(json=="firsttimeloginuser"|| json=="invalidsession"||json=="expired")
	  						{
	  					 bootbox.prompt({
	 			     	    title: "OTP has been sent on GSTN registered mobile number.",
	 			     	    inputType: 'text',
	 			     	     placeholder: "enter OTP here",
	 			     	    locale: 'custom',
	 			     	   
	 			     	   buttons: {
	 			     	        /*cancel: {
	 			     	        label: "Resend",
	 			     	     },*/
	 			     	     confirm: {
	 			     	        label: "Verify & Save",
	 			     	    }
	 			     	  },
	 			     	    callback: function (result) {
	 			     	    	
	 			     	    	
	 			     	      if(result != null){
	 			     	    	  
	 			     	    	  submitotp(fp,gstin,result,month,fpYear);
	 			     	    	 
	 			     	        }
	 	     	            }
	 	     	});
	  				
	  						}
	  					else if (json=="validsession")
	  					{
	  						result="";
	  						submitotp(fp,gstin,result,month,fpYear);
	  					}
	  					
	  					else
	  						{
	  						bootbox.alert(json)
	  						
	  						}
	  					 
					 
	  			},
	  			
	  			
	  	              error: function (data,status,er) {
	  	        	 
	  	        	 getInternalServerErrorPage();   
	  	        }
	  			
	  			
	  			
	  			
	  			
	  			
	  		});
	  		}
		
		function  submitotp(fp,gstin,result,fpYear,month){
			
			 
		       $.ajax({
		              url : "submitotpgstr1",
		              method : "POST",
		            
		     		  data :{"gstin":gstin,"fp":fp,"otp":result},
		              //contentType : "application/json",
		              dataType : "json",
		              async : false,
		              headers: {
		  	            _csrf_token : $("#_csrf_token").val()
		  	            
		  	     },
		  	     success:function(json,fTextStatus,fRequest){
		  	    	 
		  	  /*  	console.log("json : "+json);*/
		  	    	
		  	    	
		  	    	if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}

					if(isValidToken(json) == 'No'){
						window.location.href = getCsrfErrorPage();
						return;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					
					console.log("json : "+json);
					if (json=="1") 
						{
						
						bootbox.alert("Your data is getting Saved to GSTN for  "+month+" "+ fpYear+" - "+gstin+"" +
  								" You can view the saved data in Step 3- Submit data to GSTN");
						}
					else
						{
						bootbox.alert(json)
						
						}
					
		  	     }
		       }
		       );
		       }
		
		
	}
	
});

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
	

