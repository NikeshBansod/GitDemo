$(document).ready(function() {
	createDataTable('invoiceHistoryTabCNDN');	
	$('.loader').fadeOut("slow"); 
} );

//for CNDN invoice preview show
function getCnDnDetails(idValue,iterationNo,cId,cndnNumber){
    $('.loader').show();
       document.cndn.action = "./getCNDNPreviewDetails";
       document.cndn.id.value = idValue;
       document.cndn.iterationNo.value = iterationNo;
       document.cndn.cndnNumber.value = cndnNumber;
       document.cndn.cId.value = cId;
       document.cndn._csrf_token.value = $("#_csrf_token").val();
       document.cndn.submit();
       $('.loader').fadeOut("slow"); 
}
//from CNDN invoice preview show to cndn list
$("#goBackToCNDN").click(function(){       
	goBackFrompreview($("#dash_startdate").val(),$("#dash_enddate").val(),$("#dash_conditionValue").val(),$("#onlyMonth").val(),$("#onlyYear").val());   
});

//from CNDN preview page to CNDN list in dashboard
function goBackFrompreview(startdate,enddate,getInvType,onlyMonth,onlyYear){
       /*alert("error: "+startdate+" status: "+enddate+" type: "+getInvType+" onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);*/
       document.redirectToBackFromCNDN.action ="./showAllRecordsList";
       document.redirectToBackFromCNDN.startdate.value = startdate;
       document.redirectToBackFromCNDN.enddate.value = enddate;
       document.redirectToBackFromCNDN.getInvType.value = getInvType;
       document.redirectToBackFromCNDN.onlyMonth.value = onlyMonth;
       document.redirectToBackFromCNDN.onlyYear.value = onlyYear;
       document.redirectToBackFromCNDN._csrf_token.value = $("#_csrf_token").val();
       document.redirectToBackFromCNDN.submit();     
}

$("#goBackToDashboard").click(function(){
       goBackFromCNDNToDashboard($("#onlyMonth").val(),$("#onlyYear").val());  
});

function goBackFromCNDNToDashboard(onlyMonth,onlyYear){       
       document.gotoDashboard.action = "./gotoDashboard";                  
       document.gotoDashboard.onlyMonth.value = onlyMonth;
       document.gotoDashboard.onlyYear.value = onlyYear;
       document.gotoDashboard._csrf_token.value = $("#_csrf_token").val();
       document.gotoDashboard.submit();
}

function createCNDN(idValue){
	document.manageInvoice.action = "./getInvoiceCNDNDetails";
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

/*function previewCNDNSendMail(){
	var idValue = $("#refInvoiceId").val();
	var iterationNo =  $("#refIterationNo").val();
	var cnDnInvoiceId = $("#refCnDnId").val();
	$('.loader').show();
	$("#secondDivId").hide();
	$("#backToPreview").hide();
	$("#breadcumheader2").hide();	
	setTimeout(function(){
		 $.ajax({
				url : "sendCnDnMailToCustomerFromPreview",
				headers: {
					_csrf_token : $("#_csrf_token").val()
				},
				method : "post",
				data : {id : idValue, iterationNo : iterationNo, cId : cnDnInvoiceId},
				contentType : "application/json",
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
						$('.loader').fadeOut("slow");						
						bootbox.alert(json.response);
						$("#secondDivId").show();
						$("#heading").hide();
						$("#backToPreview").show();
						$("#breadcumheader2").show();
					}else{		
						$('.loader').fadeOut("slow");					
						bootbox.alert(json.response);
						$("#secondDivId").show();
						$("#heading").hide();
						$("#backToPreview").show();
						$("#breadcumheader2").show();
					} 
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		         },
		         error:function(data,status,er) { 
		        	 getInternalServerErrorPage();   
		         }
		});		
	}, 1000);	
}*/

function getCnDnDetails(idValue,iterationNo,cnDnInvoiceId){
	$('.loader').show();
	$("#firstDivId").hide();
	$("#breadcumheader").hide();
	$("#originalHeader").hide();
	/*previewCNDNDetails(idValue);*/
	setValuesInHiddenFields(idValue,iterationNo,cnDnInvoiceId);
	setTimeout(function(){
		var backendResponse = getCnDnDetailsInPreview(idValue,iterationNo,cnDnInvoiceId);
		showBillDetailsInPreviewDiv(backendResponse);
		showBillToAndShipToInPreviewDiv(backendResponse);
		showServiceListDetailsInPreviewInvoiceDiv(backendResponse);
		$("#secondDivId").show();
		$("#heading").hide();
		$("#backToPreview").show();
		$("#breadcumheader2").show();
		$('.loader').fadeOut("slow"); 
	}, 500);
}

$(document).ready(function () {
	createDataTable('CndnTab');
	$("#backToPreview").hide();
	$("#breadcumheader2").hide();
	$("#backToPreview").click(function(e){
		$('.loader').show();
		$("#secondDivId").hide();
		$("#backToPreview").hide();
		$("#breadcumheader2").hide();
		
		setValuesInHiddenFields("","","");
		setTimeout(function(){
			$("#firstDivId").show();
			$("#breadcumheader").show();
			$('.loader').fadeOut("slow"); 
		}, 500);
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
			success:function(json,fTextStatus,fRequest){
				//console.log(json);
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
						}, 500);
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
                +'  </tr>'	 
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
	  
	  //call this function in order to have responsive table
	  //$('.resTable').riltable();
	  
	  dynamicChangesInPreviewLabels(cnDnType);
	  
	  dynamicChangesInInvoiceNoDisplay(json);
	  
	
}

function dynamicChangesInInvoiceNoDisplay(json){
	  
	 
	  
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
			    $('.loader').show();
				$("#divTwo").hide();
				$("#header").hide();
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
									setTimeout(function(){
										window.location.href = getDefaultSessionExpirePage();
									}, 500);
								});
							}else{
								$('.loader').fadeOut("slow");
								if(json.status === 'FAILURE'){
									$("#divTwo").show();
									bootbox.alert(json.response);
								}else{
									bootbox.alert(json.response, function() {
										setTimeout(function(){
											/*bootbox.alert(json.response);*/
											window.location.href = redirectToInvoiceHistoryPage();
										}, 500);
									});
								}								
							}							
						},
			        error:function(data,status,er) { 
			        	getInternalServerErrorPage();   
			        }
				});
			 }, 1000);
		   }
		}//end of callback
		
	});	
}

function redirectToWizardInvoiceHistoryPage(){
	return 'getWizardMyInvoices';
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

function downloadRecord(idValue){
	document.manageInvoice.action = "./downloadInvoices";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}


function previewCNDNDetails(idValue){
   
       $.ajax({
              url : "getcndndetails",
              method : "post",
              headers: {
                     _csrf_token : $("#_csrf_token").val()
              },
              data : JSON.stringify(inputData),
              contentType : "application/json",
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
                     setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                           
                     jsonDashBoardResponse = json; 
              },
              error: function (data,status,er) {              
                     getInternalServerErrorPage();   
              }
       });
       
}

/*function previewCNDNDetails(idValue){
	document.manageInvoice.action = "./getcndndetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}*/

function showBillDetailsInPreviewDiv(backendResponse){
	var invoiceGstin = '';
	if(backendResponse != ''){
		invoiceGstin = backendResponse.invoiceDetails.gstnStateIdInString;
		var gstinJsonForUserData = fetchGstinDataForUserData(invoiceGstin);
		var address = '';
	    var city = '';
	    var pincode = '';
	    var stateCodeId = '';
	    var stateToShow = '';
	    var gstin=backendResponse.invoiceDetails.gstnStateIdInString;
	    var billingDetailsAddress='';
		if(gstinJsonForUserData != '' && gstinJsonForUserData != null && gstinJsonForUserData != undefined){
			if(gstinJsonForUserData.gstinAddressMapping != '' && gstinJsonForUserData.gstinAddressMapping != null && gstinJsonForUserData.gstinAddressMapping != undefined){	
				$("#userDetailsInPreview").html('');			
	            address = (gstinJsonForUserData.gstinAddressMapping.address) ? gstinJsonForUserData.gstinAddressMapping.address : ''; 
	            city = (gstinJsonForUserData.gstinAddressMapping.city) ? gstinJsonForUserData.gstinAddressMapping.city : '';
	            pincode = (gstinJsonForUserData.gstinAddressMapping.pinCode) ? gstinJsonForUserData.gstinAddressMapping.pinCode : '';
	            stateCodeId = (gstinJsonForUserData.state) ? gstinJsonForUserData.state : '';
	            stateToShow = (gstinJsonForUserData.gstinAddressMapping.state) ? gstinJsonForUserData.gstinAddressMapping.state : '';
	            if(address != ''){
	            	billingDetailsAddress = address;
	            }if(city != ''){
	            	billingDetailsAddress += ', '+city;
	            }if(stateToShow != ''){
	            	billingDetailsAddress += ', '+stateToShow;
	            }if(stateCodeId != ''){
	            	billingDetailsAddress += ', ['+stateCodeId+']';
	            }if(pincode != ''){
	            	billingDetailsAddress += ', '+pincode;
	            }
			}	            
		}
        $("#userDetailsInPreview").append(
                    '<strong> '+$("#user_org_name").val()+'</strong><br>'
                      +billingDetailsAddress+'<br>'
                      +'<strong>GSTIN : </strong> '+gstin+'<br>'
                    /*  +'<strong>PAN : </strong>'+$("#user_org_panNumber").val()+'<br>'*/ 	 	
                      +'<strong>Original Tax Invoice No : </strong> '+backendResponse.invoiceNoForDashboard+'<br>'
					  +'<strong>Original Tax Invoice Date : </strong> '+modifydate(backendResponse.invoiceDetails.invoiceDate)+'<br>'	
					  +'<strong id="place100"></strong><br>'
					  
        );	
       
        $('strong[id*=place100]').text('');
  	  	if(backendResponse.invoiceDetails.cnDnType === 'creditNote'){
  		  $('strong[id*=place100]').append(
  			'<strong>Credit Note No : </strong>'+backendResponse.invoiceDetails.invoiceNumber
  		  );
  	  	}else{
  		  $('strong[id*=place100]').append(
  			'<strong>Debit Note No : </strong>'+backendResponse.invoiceDetails.invoiceNumber
  		  );
  	  	}
	}
}

function fetchGstinDataForUserData(gstinNo){
	 var gstinJsonForUserData = '';
     $.ajax({
           url : "getGstinDetailsFromGstinNo",
           method : "POST",
           dataType : "json",
           data : { gstinNo : gstinNo,_csrf_token : $("#_csrf_token").val()},
           async : false,
             success:function(json,fTextStatus,fRequest) {
                   if (isValidSession(json) == 'No') {
                             window.location.href = getDefaultSessionExpirePage();
                             return;
                   }
                   if(isValidToken(json) == 'No'){
                             window.location.href = getCsrfErrorPage();
                             return;
                   }
                   //alert(json.gstinAddressMapping.address + ", "+json.gstinAddressMapping.pinCode+ ", "+json.gstinAddressMapping.city+ ", "+json.gstinAddressMapping.state);
                   
                   setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                   gstinJsonForUserData = json;
             },
             error: function (data,status,er) {
                       
                        getInternalServerErrorPage();   
             }
     });
     
     return gstinJsonForUserData;
}


function modifydate(inputDate){
	var date = new Date(inputDate);  //or your date here
	//console.log((date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear());
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	return (date.getDate() + "-"+month+"-"+date.getFullYear());
} 

function showBillToAndShipToInPreviewDiv(backendResponse){
	if(backendResponse != ''){
		$("#customerDetailsBillToDiv").html("");
		$("#customerDetailsBillToDiv").append(
            '<strong>Name : </strong>'+backendResponse.invoiceDetails.customerDetails.custName+'<br>'
            +'<strong>Address : </strong>'+backendResponse.invoiceDetails.customerDetails.custAddress1+'<br>'
            +'<strong>City : </strong>'+backendResponse.invoiceDetails.customerDetails.custCity+'<br>'
            +'<strong>State : </strong>'+backendResponse.invoiceDetails.customerDetails.custState+'<br>'
            +'<strong>State Code : </strong>'+autoPopulateStateList(backendResponse.invoiceDetails.customerDetails.custState)+'<br>'
		);
	}
    
}

function autoPopulateStateList(custState){
	var stateCode = '';
	$.ajax({
		url : "getStatesList",
		method : "POST",
		dataType : "json",
		data : { _csrf_token : $("#_csrf_token").val()},
		async : false,
		  success:function(json,fTextStatus,fRequest) {
			  if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
			  }
			  if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
			  }
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			  $.each(json, function(i, value) {
				   
				 if(custState == value.stateName){
					 stateCode = value.stateCode;
				 }   
			   }); 
		  },
		  error: function (data,status,er) { 
				 getInternalServerErrorPage();   
		  }
	});
	return stateCode;
}

