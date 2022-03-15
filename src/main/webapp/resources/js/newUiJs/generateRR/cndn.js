function checkForTotalCnDnValueAtServerSide(inputData){
	var isError = false;
	isError = verifyCnDnValueAtServerSide(inputData);
	return isError;
}

function getInputFormCnDnDataJson(){
	var invoiceId = $("#unqIncDes").val();
	var cnDnType = $('input[name=createDocType]').filter(':checked').val();
	var rrType = $('select#rrType option:selected').val();
	var iterationNo = $("#iterationNo").val();
	var invoiceDate = $("#invoice_date").val();
	var serviceListArray = new Array();
	if( rrType == "salesReturn"){
		serviceListArray = cnDnSalesReturnJson();
	}else if(rrType == "discountChange"){
		serviceListArray = cnDnDiscountChangeJson();
	}else if(rrType == "salesPriceChange"){
		serviceListArray = cnDnSalesPriceChangeJson();
	}else if(rrType == "quantityChange"){
		serviceListArray = cnDnSalesQuantityChangeJson();
	}
	var invNumber = '';
	if($("#invoiceNumber").val()){
		  invNumber = $("#invoiceNumber").val();
	}

	 var inputData = {
			 	"id" : invoiceId,
			 	"cnDnList" : JSON.parse(JSON.stringify(serviceListArray)),
			 	"cnDnType" : cnDnType,
			 	"createDocType" : rrType,
			 	"iterationNo" : iterationNo,
			 	"footerNote" : "",
			 	"invoiceDateInString": invoiceDate,
			 	"invoiceNumber" : invNumber
        };
	
	return inputData;
}

function verifyCnDnValueAtServerSide(inputData){
	var isError = false;
	 $.ajax({
			url : "verifyInvoiceCnDn",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
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
				
				if(json.response == 'SUCCESS'){
					if(json.status == 'FAILURE'){
						isError = true;
						bootbox.alert(json.message);
					}else{
						//console.log("Server side status true");
					}
				}
	         },
	         error:function(data,status,er) { 
	        	 bootbox.alert("Some error occured");
	        	 getInternalServerErrorPage();
	        	 
	         }
		});
	 return isError;
}

function calculateTaxForCnDn(inputData){
	var backendResponse = '';
	 $.ajax({
			url : "calculateTaxOnCnDnInvoicePreview",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
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
				
				//console.log(json);
				if(json.renderData === 'accessDeny'){
					bootbox.alert("Data is manipulated.", function() {
						setTimeout(function(){
							window.location.href = callInvoiceHistoryPage();
						}, 1000);
					});
				}else{
					cndnBackendResponse = json;
					backendResponse = json;
				}
	         },
	         error:function(data,status,er) { 
	 
	        	 bootbox.alert("Some error occured");
	        	 getInternalServerErrorPage(); 
	         }
		});
	 return backendResponse;
}

function showUserDetailsInPreview(cndnBackendResponse){
	$("#userDetailsInPreview").html('');
	var firmName = $("#firmName").val();
	$("#userDetailsInPreview").append(
			'<strong>'+firmName+'</strong><br>'
			+cndnBackendResponse.gstinDetails.gstinAddressMapping.address+" , "+cndnBackendResponse.gstinDetails.gstinAddressMapping.city+" , "+cndnBackendResponse.gstinDetails.gstinAddressMapping.state+"[ "+cndnBackendResponse.gstinDetails.state+"  ] , "+cndnBackendResponse.gstinDetails.gstinAddressMapping.pinCode+'<br>'
			+'<strong>GSTIN : </strong>'+cndnBackendResponse.invoiceDetails.gstnStateIdInString +' <br>'
			+'<strong>Original Tax Document No : </strong>'+cndnBackendResponse.invoiceDetails.invoiceNumber +'<br/>'
			/*+'<strong>Original Tax Document Date : </strong><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>'*/
	);
}

function showCustomerDetailsBillToDiv(cndnBackendResponse){
	$("#customerDetailsBillToDiv").html('');
	var custGstinState = '';
	if(cndnBackendResponse.invoiceDetails.customerDetails.custGstinState){
		custGstinState = cndnBackendResponse.invoiceDetails.customerDetails.custGstinState;
	}
	$("#customerDetailsBillToDiv").append(
			'<strong>Name : </strong>'+cndnBackendResponse.invoiceDetails.customerDetails.custName +'<br/>'
			+'<strong>Address : </strong>'+cndnBackendResponse.invoiceDetails.customerDetails.custAddress1 +'<br/>'
			+'<strong>City : </strong>'+cndnBackendResponse.invoiceDetails.customerDetails.custCity +'<br/>'
			+'<strong>State : </strong>'+cndnBackendResponse.invoiceDetails.customerDetails.custState +"[ "+custGstinState+" ]"
			+'<br/>'
			+'<strong>State Code : </strong>'+cndnBackendResponse.customerStateCode +'<br/>'
			+'<strong>GSTIN/Unique Code : </strong>'+cndnBackendResponse.invoiceDetails.customerDetails.custGstId +'<br/>'
	);
}

function showServiceListDetailsInCnDnDiv(json){
	var cnDnType = ($('input[name=createDocType]').filter(':checked').val() === 'creditNote') ? ('Credit Note') : ('Debit Note');
	
	$("#mytable2Header").html("");
	$("#mytable2Header").text(cnDnType.toUpperCase());
	
	var isDiffPercentPresent = 0;
	$("#mytable2").html("");
    $("#mytable2").append(
		'<thead>'
		   +'<tr>'
			   +'<th>Description</th>'
	           +'<th>SAC/HSN</th>'
	           +'<th>Quantity</th>'
	           +'<th>UOM</th>'
	           +'<th class="text-right">Price/UOM(Rs.)</th>'
	           +'<th class="text-right">Discount(Rs.)</th>'
	           +'<th class="text-right">Total (Rs.)</th>'
	       +' </tr>'
	    +'</thead>'	 
	 );

   var amountSubtotal = 0;
   var cessTotalTax = 0;
   var containsAdditionalCharges = '';
   var containsDiffPercentage = '';
   
   $("#mytable2").append('<tbody>');
   $.each(json.invoiceDetails.cnDnList,function(i,value) {
	   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
	   var showDiscountedRate = 0;
	   if(value.reason == 'discountChange'){
		   showDiscountedRate = parseFloat(value.offerAmount/value.quantity).toFixed(2);
	   }else{
		   showDiscountedRate = value.offerAmount;
	   }
	   $("#mytable2 tbody:last-child").append(
		  '<tr>'
           +'<td>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></td>'
           +'<td>'+value.hsnSacCode+'</td>'
           +'<td>'+value.quantity+'</td>'
           +'<td>'+value.unitOfMeasurement+'</td>'
           +'<td class="text-right">'+showDiscountedRate+'</td>'
           +'<td class="text-right">'+value.offerAmount+'</td>'
           +'<td class="text-right">'+(value.previousAmount - value.offerAmount).toFixed(2)+'</li>'
           +'</tr>'		 
		 );
		 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
		 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
	});
   
   $("#mytable2 tbody:last-child").append(
       '<tr>'                 
      	    +'<td>&nbsp;</td>'    	
        	+'<td class="hlmobil"><b>Taxable Value</b></td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td>&nbsp;</td>'
        	+'<td class="text-right">'+amountSubtotal.toFixed(2)+'</td>'
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
	                   	+'<td class="hlmobil"><b>Add : Additional Charges</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
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
		                   	+'<td>&nbsp;</td>'
		                   	+'<td>'+(value.additionalChargeAmount).toFixed(2)+'</td>'
	                    +'</tr>'
			  ); 
			  addChargeAmount = parseFloat(addChargeAmount) + parseFloat(value.additionalChargeAmount);
		  });
		  
		  $("#mytable2 tbody:last-child").append(
	               '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td class="hlmobil"><b>Total Additional Charges (B)</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>'+addChargeAmount.toFixed(2)+'</td>'
	               +'</tr>'
		  );
		  
		  $("#mytable2 tbody:last-child").append(
	               '<tr>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td class="hlmobil"><b>Total Value (A+B)</b></td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
	                   	+'<td>&nbsp;</td>'
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
                    	+'<td class="hlmobil"><b>Central Tax</b></td>'
                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
   					    +'<td></td>'
                    	+'<td>'+tax+'</td>'
   					    +'<td></td>'
   					    +'<td>&nbsp;</td>'
                     +'</tr>'
			  );
			  zCentral++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                          +'<td></td>'
                          +'<td class="hidemob"></td>'
	                      +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				  +'<td></td>'
	                      +'<td>'+tax+'</td>'
	       				  +'<td></td>'
	       				  +'<td>&nbsp;</td>'
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
                        	+'<td class="hlmobil"><b>Central Tax</b><span style="color:red">*</span></td>'
                        	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
                        	+'<td>'+tax+'</td>'
	        				+'<td></td>'
	        				+'<td>&nbsp;</td>'
                         +'</tr>'
				  );
				  zCentral++;
			  }else{
				 
				  $("#mytable2 tbody:last-child").append(
						  '<tr>'
						  	  +'<td></td>'
	                          +'<td class="hidemob"></td>'
	                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				  +'<td></td>'
	                          +'<td>'+tax+'</td>'
	        				  +'<td></td>'
	        				  +'<td>&nbsp;</td>'
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
                    	+'<td class="hlmobil"><b>State Tax</b></td>'
                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
       				    +'<td>&nbsp;</td>'
                     +'</tr>'  
			  );
			  zState++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                            +'<td></td>'
                            +'<td class="hidemob"></td>'
                            +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        				+'<td></td>'
                            +'<td>'+tax+'</td>'
	        				+'<td></td>'
	        				+'<td>&nbsp;</td>'
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
                    	+'<td class="hlmobil"><b>State Tax</b><span style="color:red">*</span></td>'
                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
       				    +'<td>&nbsp;</td>'
                     +'</tr>'
			  );
			  zState++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
	        			 +'<td>&nbsp;</td>'
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
	                    +'<td class="hlmobil"><b>Integrated Tax</b></td>'
	                    +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	                    +'<td>'+tax+'</td>'
	       				+'<td></td>'
	       				+'<td>&nbsp;</td>'
                     +'</tr>'
			  );
			  zIntegrated++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
	        			 +'<td>&nbsp;</td>'
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
                    	+'<td class="hlmobil"><b>Integrated Tax</b><span style="color:red">*</span></td>'
                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
       				    +'<td>&nbsp;</td>'
                     +'</tr>'
			  );
			  zIntegrated++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
	        			 +'<td>&nbsp;</td>'
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
                    	+'<td class="hlmobil"><b>Union Territory Tax</b></td>'
                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
       				    +'<td></td>'
       				    +'<td>&nbsp;</td>'
                     +'</tr>'
			  );
			  zUnionT++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
					  	 +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
	        			 +'<td></td>'
	        			 +'<td>&nbsp;</td>'
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
                    	+'<td class="hlmobil"><b>Union Territory Tax</b><span style="color:red">*</span></td>'
                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
       				    +'<td></td>'
                    	+'<td>'+tax+'</td>'
                    	+'<td></td>'
                    	+'<td>&nbsp;</td>'
                     +'</tr>'
			  );
			  zUnionT++;
		  }else{
			 
			  $("#mytable2 tbody:last-child").append(
					  '<tr>'
                         +'<td></td>'
                         +'<td class="hidemob"></td>'
                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	        			 +'<td></td>'
                         +'<td>'+tax+'</td>'
                         +'<td></td>'
                         +'<td>&nbsp;</td>'
                     +'</tr>'  
			  
			  );
		  }
		 
		});
	  //Display UGST - End	  
	  
	  //Types of taxes - End
	  $("#mytable2 tbody:last-child").append(
			  '<tr>'
                +'<td></td>'
                +'<td class="hlmobil"><b>Cess</b></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td>&nbsp;</td>'
                +'<td class="text-right">'+cessTotalTax.toFixed(2)+'</td>'
             +'</tr>'
	  );
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
				  	+'<td></td>'
 					+'<td class="hlmobil"><b>Total Taxes</b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td>&nbsp;</td>'
        			+'<td class="text-right">'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
        		  +'</tr>'
		  );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
                    +'<td></td>'
 					+'<td class="hlmobil"><b>Total Taxes</b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td>&nbsp;</td>'
        			+'<td class="text-right">'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
        		  +'</tr>'
		  );
	  }
	  
	  if(containsAdditionalCharges == 'YES'){
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  			+'<td><b>Total '+cnDnType+' Value </b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td>&nbsp;</td>'
                    +'<td class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
                   +'</tr>'
         );
	  }else{
		  $("#mytable2 tbody:last-child").append(
				  '<tr>'
		  			+'<td><b>Total '+cnDnType+' Value </b></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td></td>'
       				+'<td>&nbsp;</td>'
                    +'<td class="text-right">'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
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
   				+'<td>&nbsp;</td>'
                +'<td class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
               +'</tr>'
               +'<tr>'
                +'<td><b>Total '+cnDnType+' Value (After Round off)</b></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td>&nbsp;</td>'
                +'<td class="text-right">'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
               +'</tr>'
               +'<tr>'
                +'<td><b>Total '+cnDnType+' value Rs.(in words): </b></td>'
                +'<td><strong>'+ json.amtInWords+'</strong></td>'  
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>'
   				+'<td></td>' 
   				+'<td>&nbsp;</td>'
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
	  if($("#footerNote").val()){
			$("#footerNoteDiv").append(
					'<hr>'+$("#footerNote").val()+'<hr>'
			
			);
	  }
}

function createCNDNAjax(inputData,cnDnType){
	var cnDnTypeText = '';
	 $.ajax({
			url : "addInvoiceCnDn",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
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
				
				if(json.response == 'SUCCESS'){
					if(cnDnType === 'creditNote'){
						cnDnTypeText = "Credit Note ";
					}else{
						cnDnTypeText = "Debit Note ";
					}
					bootbox.alert(cnDnTypeText+" "+json.InvoiceNumber+" generated successfully.", function() {
						setTimeout(function(){ 
							backToInvoicePage($("#unqIncDes").val());
							//window.location.href = getHomePage();
							//return;
						}, 1000);
					});
				}
				
				if(json.response == 'accessDeny'){
					bootbox.alert("Data is been manipulated.", function() {
							setTimeout(function(){ 
								window.location.href = getCustomLogoutPage();
								return;
							}, 1000);
					});
				}
				if(json.response == 'FAILURE'){
					bootbox.alert("Failed to generate"+cnDnTypeText+".", function() {
							setTimeout(function(){ 
								window.location.href = getHomePage();
								return;
							}, 1000);
					});
				}
	         },
        error:function(data,status,er) { 
        	 getInternalServerErrorPage();   
         }
	});
}

function backToInvoicePage(idValue) {
	document.previewInvoice.action = "./getCnDn";	//getInvoiceCNDNDetails
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
}


