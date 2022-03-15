function sendMail(idValue){
	$.ajax({
			url : "sendMailToCustomerFromPreview",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
			method : "post",
			/*contentType : "application/json",*/
			dataType : "json",
			data : { "id" : idValue },
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
				//var json = $.parseJSON(jsonobj);
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				bootbox.alert(json);
			},
          error:function(data,status,er) { 
          	console.log("Error in sendMailToCustomerFromPreview");
          	getInternalServerErrorPage(); 
          }
	});
}

function createCNDN(idValue){
	document.manageInvoice.action = "./getInvoiceCNDNDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function createNewUiCNDN(idValue){
	document.manageInvoice.action = "./getInvCnDnDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}


function previewCNDN(idValue){
	document.manageInvoice.action = "./getCnDn";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function backToInvoicePage(idValue){
	document.manageInvoice.action = "./getInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	
}

function backToInvoiceHistoryPage(){
	document.manageInvoice.action = "./getInvoiceCNDNDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}


function shareShortURL(invId) {

	 $.ajax({
			url : "genTinyUrl",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
			data : {invId : invId},
			dataType : "json",
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
				var response = JSON.parse(json);
				
				if(response.shorturl){
					window.app.ShareInvoice(response.shorturl);
				}else{
					bootbox.alert("Something went wrong");
				}
				
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error:function(data,status,er) { 
	        	 getInternalServerErrorPage();    
	         }
	});
	
    
}  

function previewCNDNSendMail(){
	var idValue = $("#refInvoiceId").val();
	var iterationNo =  $("#refIterationNo").val();
	var cnDnInvoiceId = $("#refCnDnId").val();

	$('#loadingmessage').show();
	$("#secondDivId").hide();
	setTimeout(function(){
		 $.ajax({
				url : "sendCnDnMailToCustomerFromPreview",
				headers: {
					_csrf_token : $("#_csrf_token").val()
				},
				method : "post",
				data : {id : idValue, iterationNo : iterationNo, cId : cnDnInvoiceId},
				/*contentType : "application/json",*/
				dataType : "json",
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
					
					if(json.status == 'SUCCESS'){
						
						bootbox.alert(json.response);
						$('#loadingmessage').hide();
						$("#secondDivId").show();
					}else{
						
						bootbox.alert(json.response);
						$('#loadingmessage').hide();
						$("#secondDivId").show();
					} 
					
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		         },
		         error:function(data,status,er) { 
	            //alert("error: "+data+" status: "+status+" er:"+er);
		        	 getInternalServerErrorPage();
		         }
		});
		
	}, 5000);	
}

function getCnDnDetails(idValue,iterationNo,cnDnInvoiceId){
	$('#loadingmessage').show();
	
	$("#firstDivId").hide();
	$("#originalHeader").hide();
	setValuesInHiddenFields(idValue,iterationNo,cnDnInvoiceId);
	setTimeout(function(){
		var backendResponse = getCnDnDetailsInPreview(idValue,iterationNo,cnDnInvoiceId);
		showServiceListDetailsInPreviewInvoiceDiv(backendResponse);
		$("#secondDivId").show();
		$("#previewHeader").show();
		$('#loadingmessage').hide(); 
	}, 5000);
}

$(document).ready(function () {
	
	$("#backToPreview").click(function(e){
		$('#loadingmessage').show();
		
		$("#secondDivId").hide();
		$("#previewHeader").hide();
		setValuesInHiddenFields("","","");
		setTimeout(function(){
			$("#firstDivId").show();
			$("#originalHeader").show();
			$('#loadingmessage').hide(); 
		}, 2000);
	});
	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#options1Div').hide();
	      
		
	});
	
	$("#options1Div").click(function(e){
		$('#optionsMultiDiv').show();
		$('#options1Div').hide();
	      
		
	});
});

function setValuesInHiddenFields(idValue,iterationNo,cnDnInvoiceId){
	$("#refInvoiceId").val(idValue);
	$("#refIterationNo").val(iterationNo);
	$("#refCnDnId").val(cnDnInvoiceId);
}

function getCnDnDetailsInPreview(idValue,iterationNo,cnDnInvoiceId){
	var backendResponse = '';
	 $.ajax({
			url : "getCndnDetailsInPreview",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
			data : {id : idValue, iterationNo : iterationNo, cId : cnDnInvoiceId},
			/*contentType : "application/json",*/
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				console.log(json);
				if (isValidSession(json) == 'No') 
				{
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				if(json.renderData === 'accessDeny'){
					bootbox.alert("Data is manipulated.", function() {
						setTimeout(function(){
							window.location.href = callInvoiceHistoryPage();
						}, 1000);
					});
				}else{
					backendResponse = json;
				}
	         },
	         error:function(data,status,er) { 
	                //alert("error: "+data+" status: "+status+" er:"+er);
	        	 getInternalServerErrorPage();   
	         }
		});
	 return backendResponse;
}


function showServiceListDetailsInPreviewInvoiceDiv(json){
	var cnDnType = json.invoiceDetails.cnDnType;
	var isDiffPercentPresent = 0;
	  //RND - Start
	  
	  $("#stable").html("");
	  $("#stable1").html("");
	  $("#stable2").html("");
	  $("#stable3").html("");
	  
	  $("#stable").append(
		      '<ul class="cndndet headers">'
               +'<li>Description</li>'
               +'<li>HSN/SAC Code</li>'
               +'<li>Quantity</li>'
               +'<li>UOM</li>'
               +'<li class="text-right">Rate (Rs.)/UOM</li>'
               /*+'<li class="text-right">Discount (Rs.)</li>'*/
               +'<li class="text-right">Total (Rs.)</li>'/* after discount*/
            +'</ul>'	 
	   );
	  var amountSubtotal = 0;
	  var cessTotalTax = 0;
	  var containsAdditionalCharges = '';
	  var containsDiffPercentage = '';
	   $.each(json.invoiceDetails.cnDnList,function(i,value) {
		   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
			 $("#stable").append(
			     '<ul class="cndndet">'
               +'<li>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></li>'
               +'<li>'+value.hsnSacCode+'</li>'
               +'<li>'+value.quantity+'</li>'
               +'<li>'+value.unitOfMeasurement+'</li>'
               +'<li class="text-right">'+value.rate+'</li>'
               /*+'<li class="text-right">'+value.offerAmount+'</li>'*/
               +'<li class="text-right">'+(value.previousAmount - value.offerAmount).toFixed(2)+'</li>'
            +'</ul>'	 
			 );
			 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
			 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
		});
	   
	   
		  $("#stable1").append(
                  '<ul class="taxtable">'
               	+'<li>&nbsp;</li>'
               	+'<li class="hlmobil">Taxable Value </li>'
               	+'<li>&nbsp;</li>'
               	+'<li>'+amountSubtotal.toFixed(2)+'</li>'
                +'</ul>'
		  );
		  
	if (json.invoiceDetails.addChargesList && json.invoiceDetails.addChargesList.length) {
			  
		  
	  var addChgLength = json.invoiceDetails.addChargesList.length;	 
	  var addChargeAmount = 0;
	  if(addChgLength > 0){
		  containsAdditionalCharges = 'YES';
		  $("#stable1").append(
                  '<ul class="taxtable">'
	                   	+'<li>&nbsp;</li>'
	                   	+'<li class="hlmobil">Add : Additional Charges</li>'
	                   	+'<li>&nbsp;</li>'
	                   	+'<li>&nbsp;</li>'
                  +'</ul>'
		  );
		  $.each(json.invoiceDetails.addChargesList,function(i,value) {
			  $("#stable1").append(
	                    '<ul class="taxtable">'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li class="hlmobil">'+value.additionalChargeName+'</li>'
		                   	+'<li>&nbsp;</li>'
		                   	+'<li>'+(value.additionalChargeAmount).toFixed(2)+'</li>'
	                    +'</ul>'
			  ); 
			  addChargeAmount = parseFloat(addChargeAmount) + parseFloat(value.additionalChargeAmount);
		  });
		  $("#stable1").append(
                  '<ul class="taxtable">'
	                   	+'<li>&nbsp;</li>'
	                   	+'<li class="hlmobil">Total Additional Charges (B)</li>'
	                   	+'<li>&nbsp;</li>'
	                   	+'<li>'+addChargeAmount.toFixed(2)+'</li>'
                  +'</ul>'
		  );
		  $("#stable1").append(
                  '<ul class="taxtable">'
	                   	+'<li>&nbsp;</li>'
	                   	+'<li class="hlmobil">Total Value (A+B)</li>'
	                   	+'<li>&nbsp;</li>'
	                   	+'<li>'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</li>'
                  +'</ul>'
		  );
	  }
    }
	  
	  //Types of taxes - Start
	  
	  var cgstArray = {};
	  var sgstArray = {};
	  var ugstArray = {};
	  var igstArray = {};
	  var cgstDiffPercentArray = {};
	  var sgstDiffPercentArray = {};
	  var ugstDiffPercentArray = {};
	  var igstDiffPercentArray = {};
	  $.each(json.invoiceDetails.cnDnList,function(i,value) {
			 if((value.categoryType == 'CATEGORY_WITH_SGST_CSGT')|| (value.categoryType == 'CATEGORY_WITH_UGST_CGST')){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(cgstDiffPercentArray[value.cgstPercentage]){
						 cgstDiffPercentArray[value.cgstPercentage] = parseFloat(cgstArray[value.cgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 cgstDiffPercentArray[value.cgstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }else{
					 if(cgstArray[value.cgstPercentage]){
						 cgstArray[value.cgstPercentage] = parseFloat(cgstArray[value.cgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 cgstArray[value.cgstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }
			 }
			 
			 if(value.categoryType == 'CATEGORY_WITH_SGST_CSGT'){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(sgstDiffPercentArray[value.sgstPercentage]){
						 sgstDiffPercentArray[value.sgstPercentage] = parseFloat(sgstArray[value.sgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 sgstDiffPercentArray[value.sgstPercentage] = parseFloat(value.amountAfterDiscount);
					 } 
				 }else{
					 if(sgstArray[value.sgstPercentage]){
						 sgstArray[value.sgstPercentage] = parseFloat(sgstArray[value.sgstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 sgstArray[value.sgstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }
			 }
			 
			 if((value.categoryType == 'CATEGORY_WITH_IGST') || (value.categoryType == 'CATEGORY_EXPORT_WITH_IGST')){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(igstDiffPercentArray[value.igstPercentage]){
						 igstDiffPercentArray[value.igstPercentage] = parseFloat(igstArray[value.igstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 igstDiffPercentArray[value.igstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }else{
					 if(igstArray[value.igstPercentage]){
						 igstArray[value.igstPercentage] = parseFloat(igstArray[value.igstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 igstArray[value.igstPercentage] = parseFloat(value.amountAfterDiscount);
					 } 
				 }
			 }
			 
			 if(value.categoryType == 'CATEGORY_WITH_UGST_CGST'){
				 if(value.diffPercent == 'Y'){
					 isDiffPercentPresent = isDiffPercentPresent + 1;
					 if(ugstDiffPercentArray[value.ugstPercentage]){
						 ugstDiffPercentArray[value.ugstPercentage] = parseFloat(ugstArray[value.ugstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 ugstDiffPercentArray[value.ugstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }else{
					 if(ugstArray[value.ugstPercentage]){
						 ugstArray[value.ugstPercentage] = parseFloat(ugstArray[value.ugstPercentage]) + parseFloat(value.amountAfterDiscount);
					 }else{
						 ugstArray[value.ugstPercentage] = parseFloat(value.amountAfterDiscount);
					 }
				 }
			 }
			
		});
	 
	 
	  //Display CGST - Start
	  var zCentral = 0;
	 
	  $.each(cgstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zCentral == 0){
			  $("#stable2").append(
					  '<ul class="taxtable">'
                   	+'<li>&nbsp;</li>'
                   	+'<li class="hlmobil">Central Tax</li>'
                   	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                   	+'<li>'+tax+'</li>'
                    +'</ul>'
			  );
			  zCentral++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                        +'<li>&nbsp;</li>'
                        +'<li class="hidemob">&nbsp;</li>'
                        +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                        +'<li>'+tax+'</li>'
                    +'</ul>'  
			  
			  );
		  }
		 
		});
	  
	  var zCentral = 0;
	  $.each(cgstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zCentral == 0){
			  $("#stable2").append(
					  '<ul class="taxtable">'
                     	+'<li>&nbsp;</li>'
                     	+'<li class="hlmobil">Central Tax<span style="color:red">*</span></li>'
                     	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                     	+'<li>'+tax+'</li>'
                      +'</ul>'
			  );
			  zCentral++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                          +'<li>&nbsp;</li>'
                          +'<li class="hidemob">&nbsp;</li>'
                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                          +'<li>'+tax+'</li>'
                      +'</ul>'  
			  
			  );
		  }
		 
		});
	  //Display CGST - End
	  
	  //Display SGST - Start
	  var zState = 0;
	  $.each(sgstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zState == 0){
			
			  $("#stable2").append(
					  '<ul class="taxtable">'
                   	+'<li>&nbsp;</li>'
                   	+'<li class="hlmobil">State Tax</li>'
                   	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                   	+'<li>'+tax+'</li>'
                    +'</ul>'
			  );
			  zState++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                        +'<li>&nbsp;</li>'
                        +'<li class="hidemob">&nbsp;</li>'
                        +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                        +'<li>'+tax+'</li>'
                    +'</ul>'  
			  
			  );
			  
		  }
		 
		});
	  
	  var zState = 0;
	  $.each(sgstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zState == 0){
			
			  $("#stable2").append(
					  '<ul class="taxtable">'
                     	+'<li>&nbsp;</li>'
                     	+'<li class="hlmobil">State Tax<span style="color:red">*</span></li>'
                     	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                     	+'<li>'+tax+'</li>'
                      +'</ul>'
			  );
			  zState++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                          +'<li>&nbsp;</li>'
                          +'<li class="hidemob">&nbsp;</li>'
                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                          +'<li>'+tax+'</li>'
                      +'</ul>'  
			  
			  );
			  
		  }
		 
		});
	  //Display SGST - End
	  
	  //Display IGST - Start
	  var zIntegrated = 0;
	  $.each(igstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zIntegrated == 0){
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                   	+'<li>&nbsp;</li>'
                   	+'<li class="hlmobil">Integrated Tax</li>'
                   	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                   	+'<li>'+tax+'</li>'
                    +'</ul>'
			  );
			  zIntegrated++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                        +'<li>&nbsp;</li>'
                        +'<li class="hidemob">&nbsp;</li>'
                        +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                        +'<li>'+tax+'</li>'
                    +'</ul>'  
			  
			  );
		  }
		 
		});
	  
	  var zIntegrated = 0;
	  $.each(igstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zIntegrated == 0){
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                     	+'<li>&nbsp;</li>'
                     	+'<li class="hlmobil">Integrated Tax<span style="color:red">*</span></li>'
                     	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                     	+'<li>'+tax+'</li>'
                      +'</ul>'
			  );
			  zIntegrated++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                          +'<li>&nbsp;</li>'
                          +'<li class="hidemob">&nbsp;</li>'
                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                          +'<li>'+tax+'</li>'
                      +'</ul>'  
			  
			  );
		  }
		 
		});
	  //Display IGST - End
	  
	//Display UGST - Start
	  var zUnionT = 0;
	  $.each(ugstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zUnionT == 0){
			 
			  
			  $("#stable2").append(
					  '<ul class="taxtable">'
                   	+'<li>&nbsp;</li>'
                   	+'<li class="hlmobil">Union Territory Tax</li>'
                   	+'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                   	+'<li>'+tax+'</li>'
                    +'</ul>'
			  );
			  zUnionT++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                        +'<li>&nbsp;</li>'
                        +'<li class="hidemob">&nbsp;</li>'
                        +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                        +'<li>'+tax+'</li>'
                    +'</ul>'  
			  
			  );
		  }
		 
		});
	  
	  var zUnionT = 0;
	  $.each(ugstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zUnionT == 0){
			 
			  
			  $("#stable2").append(
					  '<ul class="taxtable">'
                     	+'<li>&nbsp;</li>'
                     	+'<li class="hlmobil">Union Territory Tax<span style="color:red">*</span></li>'
                     	+'<li>'+k+'% of 65% on '+v.toFixed(2)+'</li>'
                     	+'<li>'+tax+'</li>'
                      +'</ul>'
			  );
			  zUnionT++;
		  }else{
			 
			  $("#stable2").append(
					  '<ul class="taxtable">'
                          +'<li>&nbsp;</li>'
                          +'<li class="hidemob">&nbsp;</li>'
                          +'<li>'+k+'% on '+v.toFixed(2)+'</li>'
                          +'<li>'+tax+'</li>'
                      +'</ul>'  
			  
			  );
		  }
		 
		});
	  //Display UGST - End		  
	  
	  //Types of taxes - End
	  
	  $("#stable2").append(
			  
			    '<ul class="taxtable">'
               	+'<li>&nbsp;</li>'
               	+'<li class="hlmobil">Cess</li>'
               	+'<li>&nbsp;</li>'
               	+'<li>'+cessTotalTax.toFixed(2)+'</li>'
            +'</ul>'
	  );
	  
	 
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#stable2").append(
		    		'<ul class="taxtable">'
       			  +'<li>&nbsp;</li>'
					  +'<li class="hlmobil">Total Taxes</li>'
					  +'<li>&nbsp;</li>'
       			  +'<li>'+(json.invoiceDetails.totalTax).toFixed(2)+'</li>'
       		  +'</ul>'
		  );
	  }else{
		  $("#stable2").append(
		    		'<ul class="taxtable">'
       			  +'<li>&nbsp;</li>'
					  +'<li class="hlmobil">Total Taxes</li>'
					  +'<li>&nbsp;</li>'
       			  +'<li>'+(json.invoiceDetails.totalTax).toFixed(2)+'</li>'
       		  +'</ul>'
		  );
	  }
	 
	  
	  var displayTextBasedOnType = '';
	  if(cnDnType === 'creditNote'){
		  displayTextBasedOnType = 'Credit Note'; 
	  }else{
		  displayTextBasedOnType = 'Debit Note';
	  }
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#stable3").append(
                  '<ul class="producttotal">'
		  				+'<li>Total '+displayTextBasedOnType+' Value </li>'
                      +'<li class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</li>'                                  
                  +'</ul>'
        );
	  }else{
		  $("#stable3").append(
                  '<ul class="producttotal">'
		  				+'<li>Total '+displayTextBasedOnType+' Value </li>'
                      +'<li class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</li>'                                  
                  +'</ul>'
        ); 
	  }
	  $("#stable3").append(
			     '<ul class="producttotal">'
                  +'<li>Round off</li>'
                  +'<li class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</li>'                                  
              +'</ul>'
              +'<ul class="producttotal">'
                  +'<li>Total '+displayTextBasedOnType+' Value (After Round off)</li>'
                  +'<li class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</li>'                                  
              +'</ul>'
              +'<ul class="producttotal">'
                  +'<li>Total '+displayTextBasedOnType+' value Rs.(in words): </li>'
                  +'<li><strong>'+ json.amtInWords+'</strong></li>'                                   
              +'</ul>'
	  ); 
	  //RND - End
	  
	  if(isDiffPercentPresent > 0){
		 $("#diffPercentShowHide").show(); 
	  }else{
		  $("#diffPercentShowHide").hide();  
	  }
	  
	  $("#cndnFooterNoteDiv").html("");
	  if(json.invoiceDetails.footerNote != '' && json.invoiceDetails.footerNote != undefined){
		  
		  $("#cndnFooterNoteDiv").append(
				  '<hr>'
					+'<div style="text-align:center">'
                      +'<p><span id="footerNoteDiv">'+json.invoiceDetails.footerNote+'</span></p>'
                  +'</div>'
				  +'<hr>'
		  
		  );
	  }
	  
	  //call this function in order to have responsive table
	  //$('.resTable').riltable();
	  
	  dynamicChangesInPreviewLabels(cnDnType);
	  
	  dynamicChangesInInvoiceNoDisplay(json);
	  
	
}

function dynamicChangesInInvoiceNoDisplay(json){
	  
	  $('p[id*=place100]').text('');
	  if(json.invoiceDetails.cnDnType === 'creditNote'){
		  $('p[id*=place100]').append(
			'<span>Credit Note No. :</span>'+json.invoiceDetails.invoiceNumber+'</div>'	  
		  );
	  }else{
		  $('p[id*=place100]').append(
					'<span>Debit Note No. :</span>'+json.invoiceDetails.invoiceNumber+'</div>'	  
				  );
	  }
	  
} 

function dynamicChangesInPreviewLabels(cnDnType){
	//var cnDnType = json.invoiceDetails.cnDnType;
	$('h3[id*=place1]').text('');
	$('li[id*=place2]').text('');
	$('li[id*=place3]').text('');
	$('li[id*=place4]').text('');
	$('li[id*=place5]').text('');
	if(cnDnType === 'creditNote'){
		$('h3[id*=place1]').text('CREDIT NOTE');
		$('li[id*=place2]').text('Total Credit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Credit Note Value (A+B)');
		$('li[id*=place4]').text('Total Credit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Credit Note value Rs.(in words): ');
	}else{
		$('h3[id*=place1]').text('DEBIT NOTE');
		$('li[id*=place2]').text('Total Debit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Debit Note Value (A+B)');
		$('li[id*=place4]').text('Total Debit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Debit Note value Rs.(in words): ');
	}
}


function deleteInvoice(idValue){
	
	bootbox.confirm({
		message: "Do you really want to delete the invoice ? ",
	    buttons: {
	        confirm: {
	            label: 'Delete this invoice',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: 'Cancel',
	            className: 'btn-danger'
	        }
	    },
	    callback: function (result) {
		   if (result){ 
			   $('#loadingmessage').show();
				$("#divTwo").hide();
				setTimeout(function(){
				  $.ajax({
						url : "delInv",
						headers: {
							_csrf_token : $("#_csrf_token").val()
						},
						method : "post",
						data : { "id" : idValue },
					//	contentType : "application/json",
						dataType : "json",
						async : false,
						success:function(json,fTextStatus,fRequest){
							$('#loadingmessage').hide();
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultSessionExpirePage();
								return;
							}
							if(isValidToken(json) == 'No'){
								window.location.href = getCsrfErrorPage();
								return;
							}
							
							setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
							
							//var json = $.parseJSON(jsonobj);
							if(json.status === 'ACCESS_DENIED'){
								bootbox.alert(json.response, function() {
									setTimeout(function(){
										window.location.href = getDefaultSessionExpirePage();
									}, 1000);
								});
							}else{
								
								if(json.status === 'FAILURE'){
									$("#divTwo").show();
									bootbox.alert(json.response);
								}else{
									bootbox.alert(json.response, function() {
										setTimeout(function(){
											bootbox.alert(json.response);
											window.location.href = redirectToInvoiceHistoryPage();
										}, 1000);
									});
								}
								
							}
							
						},
			        error:function(data,status,er) { 
			            //alert("error: "+data+" status: "+status+" er:"+er);
			        	getInternalServerErrorPage();   
			         }
					});
				}, 3000);
		   }
		}
		
	});
	
	
	
	
	
}

function redirectToInvoiceHistoryPage(){
	return 'getMyInvoices';
}


function getEWayBills(idValue){
	document.manageInvoice.action = "./getEWayBills";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function generateEWayBill(idValue){
	document.manageInvoice.action = "./generateEWayBill";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function xmlInvoice(idValue){
	document.manageInvoice.action = "./xmlInvDetail";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	
}

scrollingElement = (document.scrollingElement || document.body)
function scrollSmoothToBottom () {
	   $(scrollingElement).animate({
	      scrollTop: document.body.scrollHeight
	   }, 200);
	}

	//Require jQuery
	function scrollSmoothToTop () {
	   $(scrollingElement).animate({
	      scrollTop: 0
	   }, 200);
	}
