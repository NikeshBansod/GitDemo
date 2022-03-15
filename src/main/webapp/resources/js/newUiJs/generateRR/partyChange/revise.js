function showUserDetailsInInvoicePreview(responseJson){
	$("#userDetailsInInvoicePreview").html('');
	$("#userDetailsInInvoicePreview").append(
			'<strong>'+$("#firmName").val()+'</strong><br>'
			+responseJson.gstinDetails.gstinAddressMapping.address+" , "+responseJson.gstinDetails.gstinAddressMapping.city+" , "+responseJson.gstinDetails.gstinAddressMapping.state+"[ "+responseJson.gstinDetails.state+"  ] , "+responseJson.gstinDetails.gstinAddressMapping.pinCode+'<br>'
			+'<strong>GSTIN : </strong>'+responseJson.invoiceDetails.gstnStateIdInString +' <br>'
			+'<strong>PAN : </strong>'+$("#panNumber").val() +' <br>'
			+'<strong>Invoice No : </strong>'+responseJson.invoiceDetails.invoiceNumber +'<br/>'/*$("#invoiceNumber").val()*/
			+'<strong>Whether tax is payable on reverse charge : </strong>'+responseJson.invoiceDetails.reverseCharge+'</p>'	
			/*+'<strong>Original Tax Document Date : </strong><fmt:formatDate pattern = "dd-MM-yyyy" value = "${invoiceDetails.invoiceDate}" /><br>'*/
	);
}

function showBillToAndShipToInPreviewDiv(responseJson){
	var isChecked = responseJson.invoiceDetails.billToShipIsChecked; 
	var shipToCustomerName = '';
	var shipToAddress = '';
	var shipToCity = '';
	var shipToState = '';
	var shipToStateCode = '';
	var shipToStateCodeId = '';
	var placeOfSupply = '';
	 var custStateCodeId = '';
	  if(typeof responseJson.invoiceDetails.customerDetails.custGstinState !== "undefined"){
		  custStateCodeId = ' [ '+responseJson.invoiceDetails.customerDetails.custGstinState+" ]";
	  }
	  var placeOfSupply = '';
	  if(responseJson.invoiceDetails.billToShipIsChecked == 'Yes'){
		  placeOfSupply = responseJson.invoiceDetails.customerDetails.custState +" [ "+responseJson.customerStateCode +"]";
	  }
	  if(responseJson.invoiceDetails.billToShipIsChecked == 'No'){
		  placeOfSupply = responseJson.invoiceDetails.shipToState +" [ "+responseJson.invoiceDetails.shipToStateCodeId+"]";
	  }
	  
	if(isChecked == "Yes"){
		 shipToCustomerName = responseJson.invoiceDetails.customerDetails.custName;
		 shipToAddress = responseJson.invoiceDetails.customerDetails.custAddress1;
		 shipToCity = responseJson.invoiceDetails.customerDetails.custCity;
		 shipToState = responseJson.invoiceDetails.customerDetails.custState;
		 shipToStateCode = responseJson.customerStateCode;
		 shipToStateCodeId = custStateCodeId;
		 
	}else{
		 shipToCustomerName = responseJson.invoiceDetails.shipToCustomerName;
		 shipToAddress = responseJson.invoiceDetails.shipToAddress;
		 shipToCity = responseJson.invoiceDetails.shipToCity;
		 shipToState = responseJson.invoiceDetails.shipToState;
		 shipToStateCode = responseJson.invoiceDetails.shipToStateCode;
		 shipToStateCodeId = responseJson.invoiceDetails.shipToStateCodeId;
	}
	  
  $("#customerDetailsBillToDivInInvoicePreview").html("");
 
  $("#customerDetailsBillToDivInInvoicePreview").append(
		  '<strong>Name : </strong>'+responseJson.invoiceDetails.customerDetails.custName+'<br>'
		  +'<strong>Address : </strong>'+responseJson.invoiceDetails.customerDetails.custAddress1+'<br>'
		  +'<strong>City : </strong>'+responseJson.invoiceDetails.customerDetails.custCity+'<br>'
		  +'<strong>State : </strong>'+responseJson.invoiceDetails.customerDetails.custState+custStateCodeId+'<br>'
		  +'<strong>State Code : </strong>'+responseJson.customerStateCode+'<br>'
		  +'<strong>GSTIN/Unique Code : </strong>'+custStateCodeId+'<br>'
		  +'<strong>Place Of Supply : </strong>'+ placeOfSupply 
  );
  
  $("#customerDetailsShipToDivInInvoicePreview").html("");  
  if(responseJson.invoiceDetails.invoiceFor == 'Product'){
	  $("#customerDetailsShipToDivInInvoicePreview").append(
			  '<strong>Name : </strong>'+shipToCustomerName+'<br>'
			  +'<strong>Address : </strong>'+shipToAddress+'<br>'
			  +'<strong>City : </strong>'+shipToCity+'<br>'
			  +'<strong>State : </strong>'+shipToState+shipToStateCodeId+'<br>'
			  +'<strong>State Code : </strong>'+shipToStateCode+'<br>'
	  );
  }else{
	  $("#customerDetailsShipToDivInInvoicePreview").hide();
  }
	
}

function showServiceListDetailsInPreviewInvoiceDiv(json){
	  
	   var documentType = json.invoiceDetails.documentType;	
	   $("#mytable2HeaderInInvoicePreview").html("");
	   $("#mytable2HeaderInInvoicePreview").text(documentType.toUpperCase());
	   
	   var isDiffPercentPresent = 0;
	   
	   $("#mytable2InInvoicePreview").html("");
	   $("#mytable2InInvoicePreview").append(
			'<thead>'
			   +'<tr>'
				   +'<th>Description</th>'
		           +'<th>SAC/HSN</th>'
		           +'<th>Quantity</th>'
		           +'<th>UOM</th>'
		           +'<th>Price/UOM(Rs.)</th>'
		           +'<th>Disc(Rs.)</th>'
		           +'<th>Total (Rs.) After Disc</th>'
		       +' </tr>'
		    +'</thead>'	 
		);
	   
	   var amountSubtotal = 0;
	   var cessTotalTax = 0;
	   var cessAdvolAmt = 0;
	   var cessNonAdvolAmt = 0;
	   var containsAdditionalCharges = '';
	   var containsDiffPercentage = '';
	   
	   $("#mytable2InInvoicePreview").append('<tbody>');
	   $.each(json.invoiceDetails.serviceList,function(i,value) {
		   containsDiffPercentage = ((value.diffPercent == 'Y')?'show':'none');
			 $("#mytable2InInvoicePreview tbody:last-child").append(
			     '<tr>'
	            +'<td>'+value.serviceIdInString+'&nbsp;<span style="display:'+containsDiffPercentage+';color:red">*</span></td>'
	            +'<td>'+value.hsnSacCode+'</td>'
	            +'<td>'+value.quantity+'</td>'
	            +'<td>'+value.unitOfMeasurement+'</td>'
	            +'<td class="text-right">'+value.rate+'</td>'
	            +'<td class="text-right">'+value.offerAmount+'</td>'
	            +'<td class="text-right">'+(value.previousAmount - value.offerAmount).toFixed(2)+'</td>'
	         +'  </tr>'	 
			 );
			 amountSubtotal = parseFloat(amountSubtotal) + parseFloat(value.previousAmount - value.offerAmount);
			 cessTotalTax = parseFloat(cessTotalTax) + parseFloat(value.cess);
			 cessAdvolAmt = parseFloat(cessAdvolAmt) + parseFloat(value.advolCessAmount);
			 cessNonAdvolAmt = parseFloat(cessNonAdvolAmt) + parseFloat(value.nonAdvolCess);
		});
		   
		   
	    $("#mytable2InInvoicePreview tbody:last-child").append(
	       '<tr>'                 
	      	    +'<td>&nbsp;</td>'    	
	        	+'<td class="hlmobil"><b>Total Value (A)</b></td>'
	        	+'<td>&nbsp;</td>'
	        	+'<td>&nbsp;</td>'
	        	+'<td>&nbsp;</td>'
	        	+'<td>&nbsp;</td>'
	        	+'<td>'+amountSubtotal.toFixed(2)+'</td>'
	       +'</tr>'
	    );
			  
	   var addChgLength = json.invoiceDetails.addChargesList.length;	 
	   var addChargeAmount = 0;
	   if(addChgLength > 0){
		  containsAdditionalCharges = 'YES';
		  $("#mytable2InInvoicePreview tbody:last-child").append(
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
			  $("#mytable2InInvoicePreview tbody:last-child").append(
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
		  $("#mytable2InInvoicePreview tbody:last-child").append(
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
		  $("#mytable2InInvoicePreview tbody:last-child").append(
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
		  
		 
		  //Types of taxes - Start
		  
		  var cgstArray = {};
		  var sgstArray = {};
		  var ugstArray = {};
		  var igstArray = {};
		  var cgstDiffPercentArray = {};
		  var sgstDiffPercentArray = {};
		  var ugstDiffPercentArray = {};
		  var igstDiffPercentArray = {};
		  $.each(json.invoiceDetails.serviceList,function(i,value) {
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
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                    	+'<td></td>'
	                    	+'<td class="hlmobil"><b>Central Tax</b></td>'
	   					+'<td></td>'
	                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	   					+'<td></td>'
	   					+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zCentral++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                         +'<td></td>'
	                         +'<td class="hidemob"></td>'
	       				  +'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				  +'<td></td>'
	       				  +'<td></td>'
	                         +'<td>'+tax+'</td>'
	                     +'</tr>'  
				  
				  );
			  }
			 
			});
		  	var zCentral = 0;
			  $.each(cgstDiffPercentArray, function(k, v) {
				  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
				  if(zCentral == 0){
					  $("#mytable2InInvoicePreview tbody:last-child").append(
							  '<tr>'
	                        	+'<td></td>'
	                        	+'<td class="hlmobil"><b>Central Tax</b><span style="color:red">*</span></td>'
		        				+'<td></td>'
	                        	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                        	+'<td>'+tax+'</td>'
	                         +'</tr>'
					  );
					  zCentral++;
				  }else{
					 
					  $("#mytable2InInvoicePreview tbody:last-child").append(
							  '<tr>'
							  	  +'<td></td>'
		                          +'<td class="hidemob"></td>'
		        				  +'<td></td>'
		                          +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				  +'<td></td>'
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
				
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
						  	+'<td></td>'
	                    	+'<td class="hlmobil"><b>State Tax</b></td>'
	       				+'<td></td>'
	                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'  
				  );
				  zState++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                         +'<td></td>'
	                         +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                         +'<td>'+tax+'</td>'
	                     +'</tr>'  
				  
				  );
				  
			  }
			 
			});
		  
		  var zState = 0;
		  $.each(sgstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zState == 0){
				
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                    	+'<td class="hlmobil"><b>State Tax</b><span style="color:red">*</span></td>'
	       				+'<td></td>'
	                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zState++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                         +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
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
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                    	+'<td class="hlmobil"><b>Integrated Tax</b></td>'
	       				+'<td></td>'
	                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zIntegrated++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                         +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                         +'<td>'+tax+'</td>'
	                     +'</tr>'  
				  
				  );
			  }
			 
			});
		  
		  var zIntegrated = 0;
		  $.each(igstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zIntegrated == 0){
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                    	+'<td class="hlmobil"><b>Integrated Tax</b><span style="color:red">*</span></td>'
	       				+'<td></td>'
	                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zIntegrated++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                         +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
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
				 
				  
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                    	+'<td class="hlmobil"><b>Union Territory Tax</b></td>'
	       				+'<td></td>'
	                    	+'<td>'+k+'% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zUnionT++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                         +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                         +'<td>'+tax+'</td>'
	                     +'</tr>'  
				  
				  );
			  }
			 
			});
		  
		  var zUnionT = 0;
		  $.each(ugstDiffPercentArray, function(k, v) {
			  var tax = (((0.65)*(v*parseFloat(k))/100)).toFixed(2);
			  if(zUnionT == 0){
				 
				  
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                    	+'<td class="hlmobil"><b>Union Territory Tax</b><span style="color:red">*</span></td>'
	       				+'<td></td>'
	                    	+'<td>'+k+'% of 65% on '+v.toFixed(2)+'</td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                    	+'<td>'+tax+'</td>'
	                     +'</tr>'
				  );
				  zUnionT++;
			  }else{
				 
				  $("#mytable2InInvoicePreview tbody:last-child").append(
						  '<tr>'
	                     +'<td></td>'
	                         +'<td class="hidemob"></td>'
		        				+'<td></td>'
	                         +'<td>'+k+'% on '+v.toFixed(2)+'</td>'
		        				+'<td></td>'
		        				+'<td></td>'
	                         +'<td>'+tax+'</td>'
	                     +'</tr>'  
				  
				  );
			  }
			 
			});
		  //Display UGST - End		  
		  
		  //Types of taxes - End
		  if(parseFloat(cessTotalTax) > 0){
			  $("#mytable2InInvoicePreview tbody:last-child").append(
					  '<tr>'
		             +'<td></td>'
		                	+'<td class="hlmobil"><b>Cess</b></td>'
		   				+'<td></td>'
		   				+'<td></td>'
		   				+'<td></td>'
		   				+'<td></td>'
		                	+'<td>'+cessTotalTax.toFixed(2)+'</td>'
		             +'</tr>'
			  );
		  }
		  
		  if(containsAdditionalCharges == 'YES'){
			  $("#mytable2InInvoicePreview tbody:last-child").append(
					  '<tr>'
	                 +'<td></td>'
	 					  +'<td class="hlmobil"><b>Total Taxes (C)</b></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	        			  +'<td>'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
	        		  +'</tr>'
			  );
		  }else{
			  $("#mytable2InInvoicePreview tbody:last-child").append(
					  '<tr>'
	                 +'<td></td>'
	 					  +'<td class="hlmobil"><b>Total Taxes (B)</b></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	        			  +'<td>'+(json.invoiceDetails.totalTax).toFixed(2)+'</td>'
	        		  +'</tr>'
			  );
		  }
		  
		  
		  if(containsAdditionalCharges == 'YES'){
			  $("#mytable2InInvoicePreview tbody:last-child").append(
					  '<tr>'
			  				+'<td><b>Total '+documentType+' Value (A+B+C)</b></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                       +'<td>'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
	                   +'</tr>'
	         );
		  }else{
			  $("#mytable2InInvoicePreview tbody:last-child").append(
					  '<tr>'
			  				+'<td><b>Total '+documentType+' Value (A+B)</b></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	       				+'<td></td>'
	                       +'<td>'+(json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
	                  +'</tr>'
	         ); 
		  }
		  $("#mytable2InInvoicePreview tbody:last-child").append(
				  '<tr>'
	                   +'<td><b>Round off</b></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	                   +'<td>'+(json.invoiceDetails.invoiceValueAfterRoundOff - json.invoiceDetails.invoiceValue).toFixed(2)+'</td>'                                  
	               +'</tr>'
	               +'<tr>'
	                   +'<td><b>Total '+documentType+' Value (After Round off)</b></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	                   +'<td>'+(json.invoiceDetails.invoiceValueAfterRoundOff).toFixed(2)+'</td>'                                  
	               +'</tr>'
	               +'<tr>'
	                   +'<td><b>Total '+documentType+' value Rs.(in words): </b></td>'
	                   +'<td><strong>'+ json.amtInWords+'</strong></td>'  
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'
	   				+'<td></td>'                                 
	               +'</tr>'
		  );
		  
		  $("#mytable2InInvoicePreview").append('</tbody>');
		  
		  if(isDiffPercentPresent > 0){
			 $("#diffPercentShowHide").show(); 
		  }else{
			  $("#diffPercentShowHide").hide();  
		  }   		
}

function createRevisedInvoiceAjax(inputData){
	 $.ajax({
			url : "createRevisedInvoice",
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
					
					bootbox.alert("Revised invoice generated successfully.", function() {
						setTimeout(function(){ 
							backToGeneratedInvoicePage($("#unqIncDes").val());
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
					bootbox.alert("Failed to generate revised invoice.", function() {
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

function backToGeneratedInvoicePage(idValue) {
	document.previewInvoice.action = "./getInvoiceDetails";	//getInvoiceCNDNDetails
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
}

function createNewInvoiceAjax(inputData){
	 $.ajax({
			url : "createNewInvoice",
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
					
					bootbox.alert("New invoice generated successfully.", function() {
						setTimeout(function(){ 
							backToGeneratedInvoicePage($("#unqIncDes").val());
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
					bootbox.alert("Failed to generate revised invoice.", function() {
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