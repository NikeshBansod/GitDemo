    var rowNum = 0;
   
	function add_service_row(){ 
    	$(document).ready(function(){ 
    			rowNum++;
    			
    			//get variables 
    			var serviceNameSelectedTextValue = $("#service_name").find("option:selected").text();
    			var serviceNameVal = $('#service_name').val();
    			var uomValue = $('#uom').val();
    			var rateValue = $('#rate').val();
    			var quantityValue = $('#quantity').val();
    			
    			var calculation_on = $('select#calculation_on option:selected').val();
    			var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val();  
    			var previousAmount = 0;
    			var cessValue = $('#cess').val();
    			if(cessValue == ''){
    				cessValue = 0;
    			}
    			var offerAmountValue = $('#offerAmount').val();
    			if(offerAmountValue == ''){
    				offerAmountValue = 0;
    			}
    			var discountTypeInItem = $('input[name=itemDiscountType]').filter(':checked').val();
	
    			var billMethod = '';
    			var hsnSacCode = $('#hsnSacCode').val();
    			var typeOfExport = $('select#exportType option:selected').val();
    			var country = $('select#selectCountry option:selected').val();
    			if(country === 'India'){
    				typeOfExport = '';
    			}
    			
    			//check if calculation based on - Start 
    			var uomDisplay = 'none';
    			var quantityDisplay = 'block';
    			if(calculation_on =='Amount'){
    				previousAmount = quantityValue * rateValue;
    				billMethod = 'Quantity Based';
    				uomDisplay = 'block';
    				//quantityDisplay = 'block';
    			}else{
    				
    				rateValue = 0;
    				//quantityValue = 0;
    				previousAmount = $("#amountToShow").val();
    				billMethod = 'Lumpsum';
    			}
    			//check if calculation based on - End 
    			//check for diffPercent - Start
    			var diffPercent = 'N';
    			if(invoiceFor == 'Service'){
    				diffPercent = ($('#diffPercent').is(':checked'))? 'Y' : 'N';
    			}
    			
    			//check for diffPercent - End
            	var $toggle = $("#toggle");
            	var recordNo = rowNum;
            	$toggle.append('<div id="service_start_'+recordNo+'">'
    	        				+'<div class="heading">'
    				                +'<div class="cust-con">'
    				                    +'<h1 id="service_name_'+recordNo+'">'+serviceNameSelectedTextValue+'</h1>'
    				                +'</div>'
    				                +'<div class="cust-edit">'
    				                    +'<div class="cust-icon">'
    				                    	+'<a href="#callOnEditId" onclick="javascript:edit_service_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
    				                    	+'<a href="#" onclick="javascript:remove_service_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
    				                    +'</div>'
    				                +'</div>'
    				            +'</div>'
    				            +'<div class="content">'
    				                +'<div class="cust-con">'
    				                    +'<p style="display : '+uomDisplay+'" id="uom_'+recordNo+'" >Unit Of Measurement : '+rateValue+' Rs per ' +uomValue+' </p>'
    				                    +'<p style="display:none" id="rate_'+recordNo+'">Rate : '+rateValue+' </p>'
    				                    +'<p style="display : '+quantityDisplay+'" id="quantity_'+recordNo+'" >Quantity : '+quantityValue+'</p>'
    				                    +'<p id="amount_'+recordNo+'">Amount : '+previousAmount+'</p>'
    				                    +'<p id="cess_'+recordNo+'">Cess : '+cessValue+'</p>'
    				                    +'<p id="discountTypeInItem_'+recordNo+'">Discount In : '+discountTypeInItem+'</p>'
    				                    +'<p id="offerAmount_'+recordNo+'">Discount : '+offerAmountValue+'</p>'
    				                    
    				                +'</div>'
    				                +'<input type="hidden" id="service_name-'+recordNo+'" name="" value="'+serviceNameVal+'">'
    				                +'<input type="hidden" id="calculation_on-'+recordNo+'" name="" value="'+calculation_on+'">'
    				                +'<input type="hidden" id="uom-'+recordNo+'" name="" value="'+uomValue+'">'
    				                +'<input type="hidden" id="rate-'+recordNo+'" name="" value="'+rateValue+'">'
    				                +'<input type="hidden" id="quantity-'+recordNo+'" name="" value="'+quantityValue+'">'
    				                +'<input type="hidden" id="service_name_textToShow-'+recordNo+'" name="" value="'+serviceNameSelectedTextValue+'">'
    				                
    				                +'<input type="hidden" id="previousAmount-'+recordNo+'" name="" value="'+previousAmount+'">'
    				                
    				                +'<input type="hidden" id="billingFor-'+recordNo+'" name="" value="'+invoiceFor+'">'
    				                +'<input type="hidden" id="cess-'+recordNo+'" name="" value="'+cessValue+'">'
    				                +'<input type="hidden" id="discountTypeInItem-'+recordNo+'" name="" value="'+discountTypeInItem+'">'
    				                +'<input type="hidden" id="offerAmount-'+recordNo+'" name="" value="'+offerAmountValue+'">'
    				                +'<input type="hidden" id="hsnSacCode-'+recordNo+'" name="" value="'+hsnSacCode+'">'
    				                +'<input type="hidden" id="diffPercent-'+recordNo+'" name="" value="'+diffPercent+'">'
    				            +'</div>'
            				+'</div>');
            	
            
			openCloseAccordion(rowNum);
			resetFormValues();
        	calculateTaxForRemainingFields();
        	loadBillMethod();
     	});  
    	
    	
     }
	
	function openCloseAccordion(rowNum){
		var currId = "/"+rowNum;
		//alert("accordionsId ->"+accordionsId);
		if(accordionsId.includes(currId)){
			
		}else{
			$("#service_start_"+rowNum+" .content").hide();
			$("#service_start_"+rowNum+" .heading").click(function () {
				$(this).next(".content").slideToggle();
			});	
			accordionsId = accordionsId +","+currId;
		}
		
	}
    
    function resetFormValues(){
    	$('#search-service-autocomplete').val("");
    	$('#search-product-autocomplete').val("");
    	$('#service_name').val("");
    	
    	$('#uom').val("");
    	$('#uomtoShow').val("");
    	$('#rate').val("");
    	$('#ratetoShow').val("");
    	
    	//call the bill method default on change method
    	$("#calculation_on").change();
    	
    	//set quantity = 1 when bill method is lumpsum else set it blank - End
    	$('#amount').val("");
    	$('#amountToShow').val("");
    	
    	$('#cess').val("");
    	$('#offerAmount').val("");
    	$('#hsnSacCode').val("");
    	
    	
  	  	
  	    $("input[name='diffPercent']").prop('checked', false);
  	  	
  	  	//clear input errors
  	    clearInputErrorClass("amountToShow","amountToShow-csv-id");
  	    clearInputErrorClass("quantity","quantity-csv-id");
  	    clearInputErrorClass("service_name","service-name-csv-id");
    	
    }
    
    function edit_service_row(recordNo){ 
    	$("#service_add").hide();//Hide the add button
    	$("#service_edit").show();//display the edit button
    	if($('#billingFor-'+recordNo).val() == 'Service'){
    		$('#search-service-autocomplete').val($('#service_name_textToShow-'+recordNo).val());
    	}else{
    		$('#search-product-autocomplete').val($('#service_name_textToShow-'+recordNo).val());
    	}
    	
    	$('#service_name').val($('#service_name-'+recordNo).val());
    	
    	$('#uom').val($('#uom-'+recordNo).val());
    	$('#uomtoShow').val($('#uom-'+recordNo).val());
    	$('#rate').val($('#rate-'+recordNo).val());
    	$('#ratetoShow').val($('#rate-'+recordNo).val());
    	$('#quantity').val($('#quantity-'+recordNo).val());
    	$('#amount').val($('#previousAmount-'+recordNo).val());
    	$('#amountToShow').val($('#previousAmount-'+recordNo).val());
    	$('#cess').val($('#cess-'+recordNo).val());
    	$('#discountTypeInItem').val($('#discountTypeInItem-'+recordNo).val());
    	$('#offerAmount').val($('#offerAmount-'+recordNo).val());
    	$('#unqValId').val(recordNo);
    	if($('#diffPercent-'+recordNo).val() == 'Y'){
    		$("input[name='diffPercent']").prop('checked', true);
    	}
    	
    	
    	showHideDivBasedOnCalculationOn($('#calculation_on-'+recordNo).val());
    	openCloseAccordion(recordNo);
    }
    
    function update_service_row(){ 
    	
	    	var recordNo = $('#unqValId').val();
	    	
	    	//get variables 
			var serviceNameSelectedTextValue = $("#service_name").find("option:selected").text();
			var serviceNameVal = $('#service_name').val();
			var uomValue = $('#uom').val();
			var rateValue = $('#rate').val();
			var quantityValue = $('#quantity').val();
			
			var calculation_on = $('select#calculation_on option:selected').val();
			var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val();  
			var previousAmount = 0;
			var cessValue = $('#cess').val();
			if(cessValue == ''){
				cessValue = 0;
			}
			var offerAmountValue = $('#offerAmount').val();
			if(offerAmountValue == ''){
				offerAmountValue = 0;
			}
			var discountTypeInItem = $('input[name=itemDiscountType]').filter(':checked').val();
			
			var billMethod = '';
			var hsnSacCode = $('#hsnSacCode').val();
			var typeOfExport = $('select#exportType option:selected').val();
			var country = $('select#selectCountry option:selected').val();
			if(country === 'India'){
				typeOfExport = '';
			}
	    	
	    	//check if calculation based on - Start 
			var uomDisplay = 'none';
			var quantityDisplay = 'block';
			if(calculation_on =='Amount'){
				previousAmount = quantityValue * rateValue;
				billMethod = 'Quantity Based';
				uomDisplay = 'block';
				//quantityDisplay = 'block';
			}else{
				
				rateValue = 0;
				//quantityValue = 0;
				previousAmount = $("#amountToShow").val();
				billMethod = 'Lumpsum';
			}
			//check if calculation based on - End 
			//check for diffPercent - Start
			var diffPercent = 'N';
			if(invoiceFor == 'Service'){
				diffPercent = ($('#diffPercent').is(':checked'))? 'Y' : 'N';
			}
			
			//check for diffPercent - End
			
			
			
			//show in display
	    	$('#service_name_'+recordNo).text(serviceNameSelectedTextValue);
	    	
	    	$('#uom_'+recordNo).text("Unit Of Measurement : "+rateValue+' Rs per '+uomValue).css("display",uomDisplay);
	    	$('#rate_'+recordNo).text("Rate : "+rateValue).css("display","none");
	    	$('#quantity_'+recordNo).text("Quantity : "+quantityValue).css("display",quantityDisplay);
	    	$('#amount_'+recordNo).text("Amount : "+previousAmount);
	    	$('#cess_'+recordNo).text("Cess : "+cessValue);
	    	
	    	$('#discountTypeInItem_'+recordNo).text("Discount In : "+discountTypeInItem);
	    	$('#offerAmount_'+recordNo).text("Discount : "+offerAmountValue);
	  
	    	//set values in hidden fields
	    	$('#service_name-'+recordNo).val(serviceNameVal);
	    	$('#calculation_on-'+recordNo).val(calculation_on);
	    	$('#uom-'+recordNo).val(uomValue);
	    	$('#rate-'+recordNo).val(rateValue);
	    	$('#quantity-'+recordNo).val(quantityValue);
	    	$('#service_name_textToShow-'+recordNo).val(serviceNameSelectedTextValue);
	    
	    	$('#previousAmount-'+recordNo).val(previousAmount);
            
	    	$('#billingFor-'+recordNo).val(invoiceFor);
	    	$('#cess-'+recordNo).val(cessValue);
	    	$('#discountTypeInItem-'+recordNo).val(discountTypeInItem);
	    	$('#offerAmount-'+recordNo).val(offerAmountValue);
	    	$('#hsnSacCode-'+recordNo).val(hsnSacCode);
	    	$('#diffPercent-'+recordNo).val(diffPercent);
	    	
	    	$("#service_edit").hide();//Hide the edit button
	    	$("#service_add").show();//display the add  button  
	    	
	    	
	    openCloseAccordion(recordNo);
    	resetFormValues();//reset form values
    	calculateTaxForRemainingFields();
    	loadBillMethod();
    	
    }
    
    function remove_service_row(recordNo){
    	//alert("del"+recordNo);
    	   $('#service_start_'+recordNo).remove();
    	   calculateTaxForRemainingFields();
    	   
    	   accordionsId = accordionsId.replace(",/"+recordNo, "");
    }
    
    function calculateTaxAmount(gstnStateId,serviceId,deliveryStateId,rate,quantity,amount,calculation_on,invoiceFor,typeOfExport,cessValue,offerAmountValue){
    	var taxAmount = 0;
    	var goAhead = false;
    	if((typeOfExport === 'WITH_IGST') || (typeOfExport === 'WITH_BOND')){
    		deliveryStateId = 0;
    	}
    	//alert(gstnStateId+","+serviceId+","+deliveryStateId+","+rate+","+quantity+","+amount+","+offerId);
			var inputData = {
					"gstnStateId" : gstnStateId,
					"serviceId" : serviceId,
					"deliveryStateId" : deliveryStateId,
					"rate" : rate,
					"quantity" : quantity,
					"amount" : amount,
					"calculationBasedOn" : calculation_on,
					"billingFor" : invoiceFor,
					"typeOfExport" : typeOfExport,
					"cess" : cessValue,
					"offerAmount" : offerAmountValue
					
			};
			
			  $.ajax({
					url : "calculateTaxAmount",
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
						//alert( "taxAmount: "+json.taxAmount + ",sgst : "+json.sgstAmount+","+ ",cgst : "+json.cgstAmount+ ",igst : "+json.igstAmount+",amountAfterDiscount : "+json.amountAfterDiscount +",ugstAmount:"+ json.ugstAmount);
						if(json.isValid != 'Invalid Input'){
							taxAmount = parseFloat(json.sgstAmount).toFixed(2) +"{--}"+ parseFloat(json.cgstAmount).toFixed(2) +"{--}"+ parseFloat(json.igstAmount).toFixed(2) +"{--}"+ parseFloat(json.amountAfterDiscount).toFixed(2) +"{--}"+ parseFloat(json.taxAmount).toFixed(2) +"{--}"+ parseFloat(json.ugstAmount).toFixed(2)+"{--}"+ parseFloat(json.cess).toFixed(2)+"{--}"+ json.categoryType+"{--}"+ parseFloat(json.sgstPercentage).toFixed(2)+"{--}"+ parseFloat(json.ugstPercentage).toFixed(2)+"{--}"+ parseFloat(json.cgstPercentage).toFixed(2)+"{--}"+ parseFloat(json.igstPercentage).toFixed(2)+"{--}"+ parseFloat(json.offerAmount).toFixed(2)+"{--}"+json.hsnSacCode;
						}else{
							bootbox.alert("You tried to manipulate data", function() {
								window.location.href = getDefaultSessionExpirePage();
								return;
							});
						}
					},
		            error:function(data,status,er) { 
		                //alert("error: "+data+" status: "+status+" er:"+er);
		            	console.log("Error in calculateTaxAmount");
		            	getInternalServerErrorPage();   
		             }
				});
    	
		  return taxAmount;
		//call backend WS to calculate the tax amount - End
    }
    
    function calculateTaxForRemainingFields(){
    	
    	var subtotal = calculateTaxForSubTotal();
    	$("#amountAfterDiscountToShow").val(subtotal);
    	$("#amountAfterDiscount").val(subtotal);
    	$("#additionalAmountToShow").val(subtotal);
    	$("#additonalAmount").val(subtotal);
     	$("#discountAmountToShow").val(subtotal);
    	$("#discountAmount").val(subtotal);
    	
    	calculateTaxForIndividualComponents();
    	
    	var totalTax = calculateTaxForTotalTax();
    	$("#totalTaxToShow").val(totalTax);
    	$("#totalTax").val(totalTax);
    	
    	var invoiceValue = calculateTaxForInvoiceValue(subtotal,totalTax);
    	$("#invoiceValueToShow").val(invoiceValue);
    	$("#invoiceValue").val(invoiceValue);
    	
    }

    function calculateTaxForSubTotal(){
    	var subtotal = 0;
    	
    	var $toggle = $("#toggle");
    	var totalRecordNo = $toggle.children().length;
    	//alert("totalRecordNo : "+totalRecordNo);
    	var amount = 0;
    	
    	for (i = 0; i < totalRecordNo; i++) { 
    		var index2 = $toggle.children()[i].id.lastIndexOf("_");
    		var num2 = $toggle.children()[i].id.substring(index2);
    		//alert("$toggle.children()[i].id : "+$toggle.children()[i].id);
    		//alert("num2 : "+num2 + "index2 "+index2);
    		num2 = num2.replace("_","-");
    		var amount = $("#amount"+num2).val();
    		//alert("subtotal : "+subtotal+",amount : "+amount );
    		subtotal = parseFloat(subtotal) + parseFloat(amount);	
    	}
    	
    	//call method to show value in discountAmount field -Start 
    	
    	//check if discountType is Percentage/Value - Start 
    	var discountType = $('input[name=discountType]').filter(':checked').val();
    	var discountValue = $("#discountValue").val();
		if(!discountValue){
			discountValue = 0;
    		
    	}
    	if(discountType == 'Value'){
    		
    		subtotal = parseFloat(subtotal) - parseFloat(discountValue);
    		
    	}else{
    		
    		subtotal = parseFloat(subtotal) - parseFloat((parseFloat(subtotal) * parseFloat(discountValue)) / 100);
    			
    	}
    	
    	//check if additionalChargesType is Percentage/Value - End
    	
    	//call method to show value in discountAmount field -End 
    	
    	//check if additionalChargesType is Percentage/Value - Start 
    	var additionalChargesType = $('input[name=additionalChargesType]').filter(':checked').val();
    	var additionalChargesValue = $("#additionalChargesValue").val();
		if(!additionalChargesValue){
    		additionalChargesValue = 0;
    	}
    	if(additionalChargesType == 'Value'){
    		
    		subtotal = parseFloat(subtotal) + parseFloat(additionalChargesValue);
    	}else{
    		subtotal = parseFloat(subtotal) + parseFloat((subtotal * additionalChargesValue) / 100);
    		
    	}
    	
    	//check if additionalChargesType is Percentage/Value - End
    	return subtotal.toFixed(2);
    	
    }

    function calculateTaxForTotalTax(){
    	var subtotal = 0;
    	var totalTaxToShow = 0;
    	var $toggle = $("#toggle");
    	var totalRecordNo = $toggle.children().length;
    	var taxAmount = 0;
    	for (i = 0; i < totalRecordNo; i++) { 
    		var index2 = $toggle.children()[i].id.lastIndexOf("_");
    		var num2 = $toggle.children()[i].id.substring(index2);
    		num2 = num2.replace("_","-");
    		var taxAmount = $("#taxAmount"+num2).val();
    		subtotal = parseFloat(subtotal) + parseFloat(taxAmount);	
    	}
    	return subtotal.toFixed(2);
    }

    function calculateTaxForInvoiceValue(subtotal,totalTax){
    	var invoiceValue = 0;
    	invoiceValue = parseFloat(subtotal) + parseFloat(totalTax);
    	return invoiceValue.toFixed(2);
    	
    }  
    
    function showHideDivBasedOnCalculationOn(selVal){
    	  if(selVal=="Amount"){
	    	  $("#uom-show-hide").show();   
	    	 /* $("#rate-show-hide").show();*/
	    	  $("#quantity-show-hide").show();
	    	  $("#amount-show-hide").show();
	    	  $("#amountToShow").attr('disabled','disabled');
	    	 
	      }
	      
	      if(selVal=="Lumpsum"){
	    	  $("#uom-show-hide").hide();   
	    	 /* $("#rate-show-hide").hide();*/
	    	  $("#quantity-show-hide").show();
	    	  $("#amount-show-hide").show();
	    	  $("#amountToShow").removeAttr('disabled');
	      }
	      
	      if(selVal==""){
	    	  $("#uom-show-hide").hide();   
	    	  /*$("#rate-show-hide").hide();*/
	    	  $("#quantity-show-hide").hide();
	    	  $("#amount-show-hide").hide();
	    	  $("#amountToShow").removeAttr('disabled'); 
	      }
    }
    
    
    $(document).ready(function() { 
    	
    	$("#amountToShow").keyup(function() {
    	    var $this = $(this);
    	    $this.val($this.val().replace(/[^\d.]/g, ''));        
    	});

    	$("#discountValue,#additionalChargeAmountToShow").keyup(function() {
    	    var $this = $(this);
    	    $this.val($this.val().replace(/[^\d.]/g, ''));        
    	});
    	
    	$("#discountValue").blur(function() {
    		onblurOfDiscountType();     
    	});
    	
    	$("#discountValue").change(function(){
    		onblurOfDiscountType();
    	});
    	
    	$("#additionalChargesValue").keyup(function() {
    	    var $this = $(this);
    	    $this.val($this.val().replace(/[^\d.]/g, ''));        
    	});
    	
    	$("#additionalChargesValue").blur(function() {
    		onblurOfAdditionalChargesType();     
    	});
    	
    	$("#additionalChargesValue").change(function(){
    		onblurOfAdditionalChargesType();
    	});
    	
    	$('#invoice_date').datepicker({
    		showButtonPanel: true,
    		changeMonth: true, 
    		changeYear: true,
    		firstDay: 1,
    	    dateFormat: 'dd-mm-yy',
    	    numberOfMonths: 1,
    	    minDate:'01-07-2017', 
    	    maxDate: new Date,
    	    onSelect: function(dateAsString,evnt) {
    	    	
    	    	var newDateFormat = $.datepicker.formatDate( "yy-mm-dd", new Date( dateAsString.split('-')[2], dateAsString.split('-')[1] - 1, dateAsString.split('-')[0] ));
    	    	//checkDateForInvoice(newDateFormat);
    	    	
    	    }
    	}).datepicker("setDate", new Date());
    	

    });
 
    function checkDateForInvoice(value){     
	   var inputDate = value;//$('#invoice_date').val(); 
	   
	   $.ajax({
			url : "checkDateForInvoice",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			data : { inputDate : inputDate, _csrf_token : $("#_csrf_token").val()},
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
				
				if(json.allow === 'false'){
					
					bootbox.alert("The last invoice was created for date : "+json.lastInvoiceDate+". The new invoice can be created on or after "+json.lastInvoiceDate+".", function() {
						$("#invoice_date").val("");
						$("#invoice_date").focus();
					});
					
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				validateDateField("invoice_date","invoice-date-csv-id");
				
			},
           error:function(data,status,er) { 
                   //alert("error: "+data+" status: "+status+" er:"+er);
        	   getInternalServerErrorPage(); 
            }
		});
	}
    
    function onblurOfDiscountType(){
		var discountType = $('input[name=discountType]').filter(':checked').val(); 
		
		var discountValue = $("#discountValue").val();
		if(discountType == 'Percentage'){
		
			if(discountValue >= 100){
				bootbox.alert("Discount type is Percentage so Discount value cannot be greater than 100", function() {
					$("#discountValue").val("");
					$("#discountValue").focus();
				});
			}
			
		}
		calculateTaxForRemainingFields();
		
	}
    
    function onblurOfAdditionalChargesType(){
    	var additionalChargesType = $('input[name=additionalChargesType]').filter(':checked').val(); 
		
		var additionalChargesValue = $("#additionalChargesValue").val();
		if(additionalChargesType == 'Percentage'){
		
			if(additionalChargesValue > 100){
				bootbox.alert("Additional Charges Type is Percentage so Additional Charges value cannot be greater than 100", function() {
					$("#additionalChargesValue").val("");
					$("#additionalChargesValue").focus();
				});
			}
			
		}
		calculateTaxForRemainingFields();	
    }
    
    function cancel_service_row(){
    	resetFormValues();
    	calculateTaxForRemainingFields();
    }

function calculateTaxForIndividualComponents(){
	var sgstSubtotal = 0;
	var cgstSubtotal = 0;
	var ugstSubtotal = 0;
	var igstSubtotal = 0;
	var cessSubtotal = 0;
	var amountSubtotal = 0;
	var $toggle = $("#toggle");
	var totalRecordNo = $toggle.children().length;

	for (i = 0; i < totalRecordNo; i++) { 
		var index2 = $toggle.children()[i].id.lastIndexOf("_");
		var num2 = $toggle.children()[i].id.substring(index2);
		num2 = num2.replace("_","-");
		var sgstAmount = $("#sgst"+num2).val();
		var cgstAmount = $("#cgst"+num2).val();
		var ugstAmount = $("#ugst"+num2).val();
		var igstAmount = $("#igst"+num2).val();
		var cessAmount = $("#cess"+num2).val();
		var amount = $("#amount"+num2).val();
		sgstSubtotal = parseFloat(sgstSubtotal) + parseFloat(sgstAmount);
		cgstSubtotal = parseFloat(cgstSubtotal) + parseFloat(cgstAmount);
		ugstSubtotal = parseFloat(ugstSubtotal) + parseFloat(ugstAmount);
		igstSubtotal = parseFloat(igstSubtotal) + parseFloat(igstAmount);
		cessSubtotal = parseFloat(cessSubtotal) + parseFloat(cessAmount);
		amountSubtotal = parseFloat(amountSubtotal) + parseFloat(amount);
	}
	
	$("#sgstTotalTax").val(sgstSubtotal.toFixed(2));
	$("#cgstTotalTax").val(cgstSubtotal.toFixed(2));
	$("#ugstTotalTax").val(ugstSubtotal.toFixed(2));
	$("#igstTotalTax").val(igstSubtotal.toFixed(2));
	$("#cessTotalTax").val(cessSubtotal.toFixed(2));
	$("#totalAmount").val(amountSubtotal.toFixed(2));
	
}

function loadBillMethod(){
	  var selVal = $('select#calculation_on option:selected').val();
	  showHideDivBasedOnCalculationOn(selVal);
}

function onblurOfDiscountTypeInAdddingItem(){
	var discountType = $('input[name=itemDiscountType]').filter(':checked').val(); 
	
	var discountValue = $("#offerAmount").val();
	if(discountType == 'Percentage'){
	
		if(discountValue >= 100){
			bootbox.alert("Discount percentage cannot be greater than or equal to 100", function() {
				$("#offerAmount").val("");
				$("#offerAmount").focus();
			});
		}
		
	}
	
}


   