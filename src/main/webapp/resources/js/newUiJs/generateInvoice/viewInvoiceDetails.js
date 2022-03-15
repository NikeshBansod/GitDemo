$(document).ready(function () {
	$('.loader').fadeOut("slow");
});
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
			beforeSend: function(){
				$('.loader').show();
			},
			complete: function(){
				$('.loader').fadeOut("slow");
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
				//var json = $.parseJSON(jsonobj);
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				bootbox.alert(json);				
			},
          error:function(data,status,er) { 
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
	$('.loader').show();
	document.manageInvoice.action = "./getCnDn";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$('.loader').fadeOut("slow");
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
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
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
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			var response = JSON.parse(json);				
			if(response.shorturl){
				window.app.ShareInvoice(response.shorturl);
			}else{
				bootbox.alert("Something went wrong");
			}
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

	$("#secondDivId").hide();
	$("#previewHeader").hide();
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
			beforeSend: function(){
				$('.loader').show();
			},
			complete: function(){
				$('.loader').fadeOut("slow");
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
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				
				if(json.status == 'SUCCESS'){							
					bootbox.alert(json.response);
					$('.loader').fadeOut("slow");
					$("#secondDivId").show();
				}else{							
					bootbox.alert(json.response);
					$('.loader').fadeOut("slow");
					$("#secondDivId").show();
				} 
	         },
	         error:function(data,status,er) {
	        	 getInternalServerErrorPage();   
	         }
		});		
	}, 5000);	
}

function getCnDnDetails(idValue,iterationNo,cnDnInvoiceId){
	$('.loader').show();
	$("#firstDivId").hide();
	$("#originalHeader").hide();
	setValuesInHiddenFields(idValue,iterationNo,cnDnInvoiceId);
	setTimeout(function(){
		var backendResponse = getCnDnDetailsInPreview(idValue,iterationNo,cnDnInvoiceId);
		showServiceListDetailsInPreviewInvoiceDiv(backendResponse);
		$("#secondDivId").show();
		$("#previewHeader").show();
		$('.loader').fadeOut("slow"); 
	}, 5000);
}

$(document).ready(function () {
	$("#backToPreview").click(function(e){
		$('.loader').show();
		$("#secondDivId").hide();
		$("#previewHeader").hide();
		setValuesInHiddenFields("","","");
		setTimeout(function(){
			$("#firstDivId").show();
			$("#originalHeader").show();
			$('.loader').fadeOut("slow");
		}, 2000);
	});
	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#optionsDiv').hide();    
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
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
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
        	 getInternalServerErrorPage();   
         }
	});
	return backendResponse;
}

function showServiceListDetailsInPreviewInvoiceDiv(json){
	var cnDnType = json.invoiceDetails.cnDnType;
	var isDiffPercentPresent = 0;
	  //RND - Start
	  
	  $("#mytable2").html("");
	  
	  $("#mytable2").append(
		  '<thead><tr>'
			  +'<th>Description</th>'
	          +'<th>HSN/SAC Code</th>'
	          +'<th>Quantity</th>'
	          +'<th>Unit Of Measurement</th>'
	          +'<th>Price (Rs.)/UOM</th>'
	         /* +'<th>Disc(Rs)</th>'*/
	          +'<th>Total(Rs.)</th>'
	      +' </tr></thead>'	 
	 );   
	  
	  var amountSubtotal = 0;
	  var cessTotalTax = 0;
	  var containsAdditionalCharges = '';
	  var containsDiffPercentage = '';
	  $("#mytable2").append('<tbody>');
	   $.each(json.invoiceDetails.cnDnList,function(i,value) {
		   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
		   $("#mytable2 tbody:last-child").append(
				'<tr>'
	                +'<td>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></td>'
	                +'<td>'+value.hsnSacCode+'</td>'
	                +'<td>'+value.quantity+'</td>'
	                +'<td>'+value.unitOfMeasurement+'</td>'
	                +'<td>'+value.rate+'</td>'
	               /* +'<td>'+value.offerAmount+'</td>'*/
	                +'<td>'+(value.previousAmount - value.offerAmount).toFixed(2)+'</td>'
                +'</tr>'	 
			);
			 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
			 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
		});
	   
	   
	   $("#mytable2 tbody:last-child").append(
             '<tr>'                 
			    +'<td>&nbsp;</td>'    	
            	+'<td class="hlmobil"><b>Taxable Value </b></td>'
            	+'<td>&nbsp;</td>'
            	+'<td>&nbsp;</td>'
            	+'<td>&nbsp;</td>'
            	/*+'<td>&nbsp;</td>'*/
            	+'<td>'+amountSubtotal.toFixed(2)+'</td>'
             +'</tr>'
	  );
		  
	if (json.invoiceDetails.addChargesList && json.invoiceDetails.addChargesList.length) {
	  var addChgLength = json.invoiceDetails.addChargesList.length;	 
	  var addChargeAmount = 0;
	  if(addChgLength > 0){
		  containsAdditionalCharges = 'YES';
		  
		  $("#mytable2 tbody:last-child").append(
                  '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td><b>Add : Additional Charges</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   /*	+'<td>&nbsp;</td>'*/
	                   	+'<td>&nbsp;</td>'
                  +'</tr>'
		  );
		  
		  $.each(json.invoiceDetails.addChargesList,function(i,value) {
			  $("#mytable2 tbody:last-child").append(
	                    '<tr>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td class="hlmobil">'+value.additionalChargeName+'</td>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>&nbsp;</td>'
		                   	/*+'<td>&nbsp;</td>'*/
		                   	+'<td>'+(value.additionalChargeAmount).toFixed(2)+'</td>'
	                    +'</tr>'
			  ); 
			  addChargeAmount = parseFloat(addChargeAmount) + parseFloat(value.additionalChargeAmount);
		  });
		  
		  $("#mytable2 tbody:last-child").append(
                  '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td><b>Total Additional Charges (B)</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   /*	+'<td>&nbsp;</td>'*/
	                   	+'<td>'+addChargeAmount.toFixed(2)+'</td>'
                  +'</tr>'
		  );
		  
		  $("#mytable2 tbody:last-child").append(
                  '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td><b>Total Value (A+B)</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   /*	+'<td>&nbsp;</td>'*/
	                   	+'<td>'+(parseFloat(amountSubtotal) + parseFloat(addChargeAmount)).toFixed(2)+'</td>'
                  +'</tr>'
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
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                     	+'<td></td>'
                     	+'<td><b>Central Tax</b></td>'
    					+'<td></td>'
                     	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
    					+'<td></td>'
    					/*+'<td></td>'*/
                     	+'<td>'+tax+'</td>'
                      +'</tr>'
			  );
			  zCentral++;
		  }else{			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                          +'<td></td>'
                          +'<td></td>'
        				  +'<td></td>'
                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				  +'<td></td>'
        				  /*+'<td></td>'*/
                          +'<td>'+tax+'</td>'
                      +'</tr>'  			  
			  );
		  }		 
		});
	  
	  var zCentral = 0;
	  $.each(cgstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zCentral == 0){
			  $("#mytable2 tbody:last-child").append(
					 '<tr>'
					  	+'<td></td>'
                   		+'<td><b>Central Tax</b><span style="color:red">*</span></td>'
                   		+'<td></td>'
                     	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
                   		+'<td></td>'
                     	+'<td>'+tax+'</td>'
                     +'</tr>'
			  );
			  zCentral++;
		  }else{			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                          +'<td></td>'
                          +'<td></td>'
        				  +'<td></td>'
                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				  +'<td></td>'
                          +'<td>'+tax+'</td>'
                      +'</tr>'  			  
			  );
		  }		 
		});
	  //Display CGST - End
	  
	  //Display SGST - Start
	  var zState = 0;
	  $.each(sgstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zState == 0){	
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
					  	+'<td></td>'
                     	+'<td><b>State Tax</b></td>'
        				+'<td></td>'
                     	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
                     	+'<td>'+tax+'</td>'
                      +'</tr>'  
			  );
			  zState++;
		  }else{			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                        +'<td></td>'
                        +'<td></td>'
        			    +'<td></td>'
                        +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
                        +'<td>'+tax+'</td>'
                      +'</tr>'  		  
			  );			  
		  }		 
		});
	  
	  var zState = 0;
	  $.each(sgstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zState == 0){			
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
					  	+'<td></td>'
                     	+'<td><b>State Tax</b><span style="color:red">*</span></td>'
                   		+'<td></td>'
                     	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
                   		+'<td></td>'
                     	+'<td>'+tax+'</td>'
                     +'</tr>'
			  );
			  zState++;
		  }else{
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                          +'<td></td>'
                          +'<td></td>'
        				  +'<td></td>'
                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				  +'<td></td>'
                          +'<td>'+tax+'</td>'
                      +'</tr>'  			  
			  );		  
		  	}		 
		});
	  //Display SGST - End
	  
	  //Display IGST - Start
	  var zIntegrated = 0;	 
	  $.each(igstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zIntegrated == 0){			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                      +'<td></td>'
                     	+'<td><b>Integrated Tax</b></td>'
        				+'<td></td>'
                     	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
                     	+'<td>'+tax+'</td>'
                      +'</tr>'
			  );
			  zIntegrated++;
		  }else{			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                      	+'<td></td>'
                        +'<td></td>'
	        			+'<td></td>'
                        +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			+'<td></td>'
	        			/*+'<td></td>'*/
                        +'<td>'+tax+'</td>'
                      +'</tr>' 
			  );
		  }		 
		});
		  var zIntegrated = 0;
		  $.each(igstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zIntegrated == 0){	
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
						  	+'<td></td>'
	                     	+'<td><b>Integrated Tax</b><span style="color:red">*</span></td>'
	                   		+'<td></td>'
	                     	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	                   		+'<td></td>'
	                     	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zIntegrated++;
			  }else{	
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
	                          +'<td></td>'
	                          +'<td></td>'
	        				  +'<td></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				  +'<td></td>'
	                          +'<td>'+tax+'</td>'
	                      +'</tr>'  			  
				  );
			  }			 
			});
	  //Display IGST - End
	  
	//Display UGST - Start
	  var zUnionT = 0;	  
	  $.each(ugstArray, function(k, v) {
		  var tax = ((v*parseFloat(k))/100).toFixed(2);
		  if(zUnionT == 0){
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                        +'<td></td>'
                     	+'<td><b>Union Territory Tax</b></td>'
        				+'<td></td>'
                     	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
                     	+'<td>'+tax+'</td>'
                      +'</tr>'
			  );
			  zUnionT++;
		  }else{			 
			  $("#mytable2 tbody:last-child").append(
					    '<tr>'
					  		+'<td></td>'
                            +'<td></td>'
	        				+'<td></td>'
                            +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
	        				/*+'<td></td>'*/
                            +'<td>'+tax+'</td>'
                      +'</tr>' 
			  );
		  }
		});
	  var zUnionT = 0;
	  $.each(ugstDiffPercentArray, function(k, v) {
		  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
		  if(zUnionT == 0){ 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
					  	+'<td></td>'
                     	+'<td><b>Union Territory Tax</b><span style="color:red">*</span></td>'
                   		+'<td></td>'
                     	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
                   		+'<td></td>'
                     	+'<td>'+tax+'</td>'
                     +'</tr>'
			  );
			  zUnionT++;
		  }else{
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                          +'<td></td>'
                          +'<td></td>'
        				  +'<td></td>'
                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
        				  +'<td></td>'
                          +'<td>'+tax+'</td>'
                      +'</tr>'  			  
			  );
		  }		 
		});
	  //Display UGST - End		  
	  
	  //Types of taxes - End
	  
	  $("#mytable2 tbody:last-child").append(
			  '<tr>'
              		+'<td></td>'
                 	+'<td><b>Cess</b></td>'
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				/*+'<td></td>'*/
                 	+'<td>'+cessTotalTax.toFixed(2)+'</td>'
              +'</tr>'
	  );	 
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				 '<tr>'
                    +'<td></td>'
				    +'<td><b>Total Taxes</b></td>'
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				/*+'<td></td>'*/
    				+'<td>'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
         		+'</tr>'
		  );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
                  		+'<td></td>'
                  		+'<td><b>Total Taxes</b></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
        				+'<td>'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
         		  +'</tr>'
		  );
	  }
	 
	  
	  var displayTextBasedOnType = '';
	  if(cnDnType === 'creditNote'){
		  displayTextBasedOnType = 'Credit Note'; 
	  }else{
		  displayTextBasedOnType = 'Debit Note';
	  }
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  				+'<td><b>Total '+displayTextBasedOnType+' Value</b></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
                        +'<td>'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
                    +'</tr>'
          );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  				+'<td><b>Total '+displayTextBasedOnType+' Value</b></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				+'<td></td>'
        				/*+'<td></td>'*/
                        +'<td>'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
                    +'</tr>'
          );
	  }
	  
	  $("#mytable2 tbody:last-child").append(
			  '<tr>'
                    +'<td><b>Round off</b></td>'
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				/*+'<td></td>'*/
                    +'<td>'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
                +'</tr>'
                +'<tr>'
                    +'<td><b>Total '+displayTextBasedOnType+' Value (After Round off)</b></td>'
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				/*+'<td></td>'*/
                    +'<td>'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
                +'</tr>'
                +'<tr>'
                    +'<td><b>Total '+displayTextBasedOnType+' value Rs.(in words): </b></td>'
                    +'<td><strong>'+ json.amtInWords+'</strong></td>'  
    				+'<td></td>'
    				+'<td></td>'
    				+'<td></td>'
    				/*+'<td></td>'*/
    				+'<td></td>'                                 
                +'</tr>'
	  );
	  $("#mytable2").append('</tbody>');
	  //RND - End

	  if(isDiffPercentPresent > 0){
		 $("#diffPercentShowHide").show(); 
	  }else{
		  $("#diffPercentShowHide").hide();  
	  }
	  	  
	  $("#footerNoteDiv").html("");
	  if(json.invoiceDetails.footerNote != '' && json.invoiceDetails.footerNote != undefined){
		  $("#footerNoteDiv").show(); 
		  $("#footerNoteDiv").append(
			 '<div class="add-charges"> '
				 + json.invoiceDetails.footerNote
             +'</div>'	  
		  );
	  }else{
		  $("#footerNoteDiv").hide(); 
	  }
	  
	  dynamicChangesInPreviewLabels(cnDnType);
	  dynamicChangesInInvoiceNoDisplay(json);	
}

function dynamicChangesInInvoiceNoDisplay(json){	  
	$('b[id*=place100]').text('');
	if(json.invoiceDetails.cnDnType === 'creditNote'){
		 $('b[id*=place100]').append(
				 '<b>Credit Note No. :</b>'+json.invoiceDetails.invoiceNumber
		 );
	}else{
		$('b[id*=place100]').append(
				'<b>Debit Note No. :</b>'+json.invoiceDetails.invoiceNumber
		);
	}	  
} 

function dynamicChangesInPreviewLabels(cnDnType){
	//var cnDnType = json.invoiceDetails.cnDnType;
	$('h1[id*=place1]').text('');
	$('li[id*=place2]').text('');
	$('li[id*=place3]').text('');
	$('li[id*=place4]').text('');
	$('li[id*=place5]').text('');
	if(cnDnType === 'creditNote'){
		$('h1[id*=place1]').text('CREDIT NOTE');
		$('li[id*=place2]').text('Total Credit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Credit Note Value (A+B)');
		$('li[id*=place4]').text('Total Credit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Credit Note value Rs.(in words): ');
	}else{
		$('h1[id*=place1]').text('DEBIT NOTE');
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
				  $('.loader').show();
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
						beforeSend: function(){
							$("#divTwo").hide();
					    	$('#breadcumheader').hide();
						},
						complete: function(){
							$("#divTwo").slideToggle();
					    	$('#breadcumheader').show();
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
							setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
							
							//var json = $.parseJSON(jsonobj);
							if(json.status === 'ACCESS_DENIED'){
						    	$('.loader').fadeOut("slow");
								bootbox.alert(json.response, function() {
									window.location.href = getDefaultSessionExpirePage();
								});
							}else{								
								if(json.status === 'FAILURE'){
							    	$('.loader').fadeOut("slow");
									bootbox.alert(json.response);
								}else{
							    	$('.loader').fadeOut("slow");
									bootbox.alert(json.response, function() {
										bootbox.alert(json.response);
										window.location.href = redirectToInvoiceHistoryPage();
									});
								}								
							}							
						},
			        error:function(data,status,er) { 	
			        	getInternalServerErrorPage();   
			        }
				});
		   }
		}//end of callback		
	});	
}

function redirectToInvoiceHistoryPage(){
	return 'getMyInvoices';
}

function getEWayBills(idValue){
	$('.loader').show();
	document.manageInvoice.action = "./getEWayBills";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$('.loader').fadeOut("slow");
}

function generateEWayBill(idValue){
	$('.loader').show();
	document.manageInvoice.action = "./generateEWayBill";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$('.loader').fadeOut("slow");
}

function xmlInvoice(idValue){
	document.manageInvoice.action = "./xmlInvDetail";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice.submit();
}

function downloadRecord(idValue){
	$('.loader').show();
	document.manageInvoice.action = "./downloadInvoices";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$('.loader').fadeOut("slow");
}

function getRevAndRet(idValue){
	$('.loader').show();
	document.manageInvoice.action = "./getRevAndRet";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	$('.loader').fadeOut("slow");
}

