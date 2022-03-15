  
var lastAndFinalInvoiceJson = '';
var accordionsId = '';
var addChgAccordionsId = '';
var dynamicServiceAccordionsId = '';
var dynamicProductAccordionsId = '';
var userServiceList = '';
var userProductList = '';
var oneGstinOneLocation = false;
var oneGstinMultipleLocation = false;
var multipleGstins = false;
var userGstinsJson = '';
var productAdvolCessRateJson = '';
var productNonAdvolCessRateJson = '';
var serviceAdvolCessRateJson = '';
var serviceNonAdvolCessRateJson = '';
var dnyGstinOpeningstockProductTable='';
var dnyGstinOpeningStockServiceTable='';
var gstinsInventoryJson = '';
var stateListXJson = '';
var userAdditionalChargeJson = '';

$(document).ready(function () {
	$('input[name="invoiceFor"][value="' + $("#old_invoiceFor").val() + '"]').prop('checked', true);
    var defaultInvoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
    //alert('Document Ready'+defaultInvoiceFor);
    var defaultDocumentType = $("#selectedDocType").val();
    var invoiceSequenceType = $("#invoiceSequenceType").val();
    callDefaultOnPageLoad(defaultInvoiceFor, defaultDocumentType,invoiceSequenceType);
    loadStateListJson();
	//loadStateList();
	loadCountry();
	loadGenericUserGstin();
	loadGstinsInventory();
	
	$("#location").on("change", function() {
		var response = validateSelect("location","location-csv-id");
		if(response){
			setResetSearchProductServiceInputFields('disabled');
		}else{
			setResetSearchProductServiceInputFields('');
			var gstnWithStateInString = $("#gstnStateId option:selected").text();
			var gstnInString = (gstnWithStateInString.split('[')[0]).trim();
			setLocationStorePkId(gstnInString,$('select#gstnStateId option:selected').val(),$('select#location option:selected').val(),$("#location option:selected").text());
		}
	});
	
	$("#service_name").on("change", function() {
		 
		 var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
		// alert("serviceName onchange :"+$(this).val() + " -->"+invoiceFor);
	     var serviceId = $(this).val();
	     var quantity = $("#quantity").val();
	     var billMethod = $('select#calculation_on option:selected').val();
	     
	     if(serviceId){
		 	if(invoiceFor == 'Service'){
		 		
		 		 $.ajax({
						url : "getManageServiceById",
						method : "POST",
						/*contentType : "application/json",*/
						dataType : "json",
						data : { serviceId : serviceId, _csrf_token : $("#_csrf_token").val() },
						async : false,
						success : function(json,fTextStatus,fRequest) {
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultSessionExpirePage();
								return;
							}
							
							if(isValidToken(json) == 'No'){
								window.location.href = getCsrfErrorPage();
								return;
							}
							
							 $("#uom").val(json.unitOfMeasurement);
							 $("#uomtoShow").val(json.serviceRate +" Rs per "+json.unitOfMeasurement);
							 
							 $("#rate").val(json.serviceRate);
							 $("#hsnSacCode").val(json.sacCode);
							/* $("#ratetoShow").val(json.serviceRate);*/
							 if(quantity){
								 if(billMethod == 'Amount'){
								     var amount = quantity * json.serviceRate;
								     $("#amount").val(amount);
								     $("#amountToShow").val(amount);
								}
							 }
						  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	 
						},
						error: function (data,status,er) {
							 
							 getInternalServerErrorPage();   
						}
			        }); 
				//getOffers(serviceId,"Service");
			}else{
				
				 $.ajax({
						url : "getProductById",
						method : "POST",
						/*contentType : "application/json",*/
						data : { productId : serviceId , _csrf_token : $("#_csrf_token").val()},
						dataType : "json",
						async : false,
						success : function(json,fTextStatus,fRequest) {
							
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultSessionExpirePage();
								return;
							}
							
							if(isValidToken(json) == 'No'){
								window.location.href = getCsrfErrorPage();
								return;
							}
							
							 $("#uom").val(json.unitOfMeasurement);
							 $("#uomtoShow").val(json.productRate+" Rs per "+json.unitOfMeasurement);
							 
							 $("#rate").val(json.productRate);
							 $("#hsnSacCode").val(json.hsnCode);
							 /*$("#ratetoShow").val(json.productRate);*/
							 
							 if(quantity){
								 if(billMethod == 'Amount'){
								     var amount = quantity * json.productRate;
								     $("#amount").val(amount);
								     $("#amountToShow").val(amount);
								}
							 }
							 
							 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
						},
						error: function (data,status,er) {
							 
							 getInternalServerErrorPage();   
						}
			        }); 
				//getOffers(serviceId,"Product");
			} 
	      
	     }  
	      
	      
		 
	  });
	
	$("#quantity").keyup(function() {
		var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
		this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');        
	});
	
	$("#rate").keyup(function() {
		var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
		this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
		if($("#quantity").val()){
			 var quantity = $("#quantity").val();
		     var rate = $("#rate").val();
		     var amount = parseFloat(quantity * rate).toFixed(2);
		     $("#amount").val(amount);
		     $("#amountToShow").val(amount);
		}        
	});
	
	$("#cessNonAdvolId").keyup(function() { 
	    var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
		this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
	});
	
	$("#amountToShow").on("keyup",function(){
		var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
		this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1'); 
		$("#amount").val(this.value);
	});
	
	
	$('#quantity').change(function(){
		var billMethod = $('select#calculation_on option:selected').val();
		if(billMethod == 'Amount'){
		     var quantity = $("#quantity").val();
		     var rate = $("#rate").val();
		     var amount = parseFloat(quantity * rate).toFixed(2);
		     $("#amount").val(amount);
		     $("#amountToShow").val(amount);
		}
	     
	});
	
	 $('input[type=radio][name=invoiceFor]').change(function() {
		 resetFormValues();
        if (this.value == 'Service') {
            //getServiceList();
            $("#callOnEditId").text("Add Services");
            $("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
            $("#service_name_label").text("Search By Service Name");
            $("#search-service-autocomplete").show();
            $("#search-product-autocomplete").hide();
            $("#invoicePeriodDivShowHide").hide();
            $("#ecommerce-label").text("Services are sold through E-commerce operator");
            $("#diffPercentageDiv").show();
            $("#amountToShow").removeAttr('disabled');
            $("#rate").attr('disabled','disabled');
            $("#rate-show-hide").hide();
            $("#quantity-show-hide").hide();
            $("#unitOfMeasurement-show-hide").hide();
            
            if($('#shipToBill').is(':checked') == true){
            	$("#selectState-show-hide").show();
                $("select[id^=selectState]").removeAttr("disabled");
                $("#pos-show-hide").hide();
            }else{
            	$("#selectState-show-hide").hide();
                $("select[id^=selectState]").attr("disabled",true);
                $("#pos-show-hide").show();
            }
        }
        else if (this.value == 'Product') {
            //getProductList();
            
            $("#callOnEditId").text("Add Goods");
            $("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
            $("#service_name_label").text("Search By Goods Name");
            $("#search-service-autocomplete").hide();
            $("#search-product-autocomplete").show();
            $("#invoicePeriodDivShowHide").hide();
            $("#ecommerce-label").text("Goods are sold through E-commerce operator");
            $("#diffPercentageDiv").hide();
            $("#amountToShow").attr('disabled','disabled');
            $("#rate").removeAttr('disabled');
            $("#rate-show-hide").show();
            $("#quantity-show-hide").show();
            $("#unitOfMeasurement-show-hide").show();
            $("#selectState-show-hide").hide();
            $("select[id^=selectState]").attr("disabled",true);
            $("#pos-show-hide").show();
        }
    });
	
	   $("#invoicePeriodFromDateInString").datepicker({
		    uiLibrary: 'bootstrap',
	    	changeMonth: true, 
			changeYear: true,
	    	dateFormat: 'dd-mm-yy',
		    numberOfMonths: 1,
		    minDate:'01-07-2017', 
		    maxDate: new Date,
	        onSelect: function(selected) {
	          $("#invoicePeriodToDateInString").datepicker("option","minDate", selected);
	          $("#invoicePeriodFromDateInString").addClass("input-correct").removeClass("input-error");
	          if($("#invoicePeriodToDateInString").val().length > 0){
	        	  $("#invoicePeriodToDateInString").addClass("input-correct").removeClass("input-error");
	        	  $("invoicePeriodToDateInString-req").hide();	
	          }
	          else{
	        	  $("#invoicePeriodToDateInString").addClass("input-error").removeClass("input-correct");
	        	  $("#invoicePeriodFromDateInString-req").show();
	          }
	        }
	    });//.datepicker("setDate", new Date());

	    $("#invoicePeriodToDateInString").datepicker({
	    	uiLibrary: 'bootstrap',
	    	changeMonth: true, 
			changeYear: true,
	    	dateFormat: 'dd-mm-yy',
		    numberOfMonths: 1,
		    minDate:'01-07-2017', 
		    maxDate: new Date,
	        onSelect: function(selected) {
	           $("#invoicePeriodFromDateInString").datepicker("option","maxDate", selected);
	           $("#invoicePeriodToDateInString").addClass("input-correct").removeClass("input-error");
	           if($("#invoicePeriodFromDateInString").val().length <= 0){
	         	  $("#invoicePeriodFromDateInString").addClass("input-error").removeClass("input-correct");
	        	  $("invoicePeriodFromDateInString-req").show();	
	           }
	           else{
	         	  $("#invoicePeriodFromDateInString").addClass("input-correct").removeClass("input-error");
	        	  $("#invoicePeriodToDateInString-req").hide();
	           }
	        }
	    });//.datepicker("setDate", new Date()); 
	    
	    getAdditionalChargesDetailsList();
	    
	    $("#additionalChargeName").on("change", function() {
	    	var additionalChargeValue = $('select#additionalChargeName option:selected').val();
	    	var additionalChargeAmountToShow = 0;
	    	var additionalChargeAmount = 0;
	    	var	values = '';
	    	if(additionalChargeValue != ''){
	    		values = additionalChargeValue.split('{--}');
	    		additionalChargeAmountToShow = values[2];
	    		$("#additionalChargeAmountToShow").val(additionalChargeAmountToShow);
	    		$("#additionalChargeAmount").val(additionalChargeAmount);
	    	}
	    });
	    
	    
	    $("#documentType").on("change", function() {
	    	var documentType = $("#documentType option:selected").text();
	    	if($("#documentType").val() == ''){
	    		documentType = 'Document';
	    	}
	    	$("#submitId").text("Preview Final "+documentType);
	    	$("#finalSubmitId").text("Send "+documentType);
	    });
	    
	    $('input[type=radio][name=cess-applicable]').change(function() {
			 if(this.value == 'No'){
				 $("#cess-applicable-show-hide").hide();
			 }else{
				 $("#cess-applicable-show-hide").show();
			 }
		 });
	
	    $('.loader').fadeOut("slow");
	
});

function callDefaultOnPageLoad(defaultInvoiceFor, defaultDocumentType,invoiceSequenceType){
	
	if($("#invDataFiledToGstn").val() == "true"){
		if(invoiceSequenceType != 'Auto'){
			$("#invoiceNumberDiv").show();
			$("#invoiceNumber").val('');
		}
	}
	
	//make Bill method = Quantity - Start
		$("#calculation_on option[value='Amount']").attr('selected', 'selected'); 
		$("#uom-show-hide").show();   
   	    //$("#quantity-show-hide").show();
   	    $("#amount-show-hide").show();
   	    
	//make Bill method = Quantity - End
   	    
   	//check for gstin number and then check for location - Start
   	    var responseFetched = fetchGstins();
   	    
   //check for gstin number and then check for location - End
   	    
   //set customer data - Start
   	 $("#customer_name").val("["+invoiceJson.customerDetails.contactNo+"] - "+invoiceJson.customerDetails.custName);
   	 $("#customer_contactNo").val(invoiceJson.customerDetails.contactNo);
   	 $("#customer_id").val(invoiceJson.customerDetails.id);
   	 $("#customer_custAddress1").val(invoiceJson.customerDetails.custAddress1);
   	 $("#customer_custCity").val(invoiceJson.customerDetails.custCity);
   	 $("#customer_custState").val(invoiceJson.customerDetails.custState);
   	 var stateRespone = getStateCode(invoiceJson.customerDetails.custState);
	 var values = stateRespone.split('{--}');
	 var stateCode = values[0];
	 var stateCodeId = values[1];
	 var stateId = values[2];
	 $("#customer_custStateCode").val(stateCode);
	 $("#customer_custStateCodeId").val(stateCodeId);
	 $("#customer_custStateId").val(stateId);
   	 $("#customer_custType").val(invoiceJson.customerDetails.custType);
   	 $("#customer_custGstId").val(invoiceJson.customerDetails.custGstId);
   	 $("#customer_custEmail").val(invoiceJson.customerDetails.custEmail);
   	 $("#posStateName").val(invoiceJson.customerDetails.custState);
	 $("#posStateCode").val(stateCode);
   	 autoPopulateStateList(stateId);
   //set customer data - End
   	
   //set shipping address same as billing address - Start
   	var billToShipIsChecked = invoiceJson.billToShipIsChecked;
   	if(billToShipIsChecked == "No"){
   		$("#shipTo_customer_name").val(invoiceJson.shipToCustomerName);
   		$("#shipTo_address").val(invoiceJson.shipToAddress);
   		$("#shipTo_pincode").val(invoiceJson.shipToPincode);
   		$("#shipTo_city").val(invoiceJson.shipToCity);
		var stateRespone = getStateCode(invoiceJson.shipToState);
		var values = stateRespone.split('{--}');
		var stateCode = values[0];
		var stateCodeId = values[1];
		var stateId = values[2];
   		$("#shipTo_stateCode").val(invoiceJson.stateCode);
   		$("#shipTo_stateCodeId").val(invoiceJson.stateCodeId);
   		
   		$('#shipToBill').prop('checked', false);
	   	$("#selectCountryShowHide").css("display","none");
	    $("#shipTo-pincode-show-hide").show();
	 	$("#shipTo-city-show-hide").show();
		autoPopulateStateList(stateId);
   	}
   	
  //set shipping address same as billing address - End	
   
   if(defaultInvoiceFor == 'Service'){
		//getServiceList();
		$("#callOnEditId").text("Add Services");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Search By Service Name");
		$("#search-service-autocomplete").show();
        $("#search-product-autocomplete").hide();
		$("#invoicePeriodDivShowHide").hide();
		$("#ecommerce-label").text("Services are sold through E-commerce operator");
		$("#diffPercentageDiv").show();
		$("#amountToShow").removeAttr('disabled');
		$("#rate").attr('disabled','disabled');
		$("#rate-show-hide").hide();
        $("#quantity-show-hide").hide();
        $("#unitOfMeasurement-show-hide").hide();
        if($('#shipToBill').is(':checked') == true){
        	$("#selectState-show-hide").show();
            $("select[id^=selectState]").removeAttr("disabled");
            $("#pos-show-hide").hide();
        }else{
        	$("#selectState-show-hide").hide();
            $("select[id^=selectState]").attr("disabled",true);
            $("#pos-show-hide").show();
        }
		
	}else{
		//getProductList();
		$("#callOnEditId").html("");
		$("#callOnEditId").text("Add Goods");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Search By Goods Name");
		$("#search-service-autocomplete").hide();
        $("#search-product-autocomplete").show();
		$("#invoicePeriodDivShowHide").hide();
		$("#ecommerce-label").text("Goods are sold through E-commerce operator");
		$("#diffPercentageDiv").hide();
		$("#amountToShow").attr('disabled','disabled');
		$("#rate").removeAttr('disabled');
		$("#rate-show-hide").show();
        $("#quantity-show-hide").show();
        $("#unitOfMeasurement-show-hide").show();
        $("#selectState-show-hide").hide();
        $("select[id^=selectState]").attr("disabled",true);
        $("#pos-show-hide").show();
        
	}
   
	//make document type selected - Start
	$("#documentType option:selected").removeAttr("selected");
	if(defaultDocumentType == 'invoice'){
		$("#documentType option[value='invoice']").attr('selected', 'selected'); 
		$("#documentType-div").hide();
		$('input[name=reverseCharge]').attr('checked', false);
		$('input[name=ecommerce]').attr('checked', false);
		$("#ecommerceGstinDiv").hide();
		
		
		
	}else if(defaultDocumentType == 'billOfSupply'){
		$("#documentType option[value='billOfSupply']").attr('selected', 'selected'); 
		$("#documentType-div").hide();
		$('input[name=reverseCharge]').attr('checked', false);
		$('input[name=ecommerce]').attr('checked', false);
		$("#ecommerceGstinDiv").hide();
		
		
		
	}else if(defaultDocumentType == 'rcInvoice'){
		$("#documentType option[value='rcInvoice']").attr('selected', 'selected'); 
		$("#documentType-div").hide();
		$('input[name=reverseCharge]').attr('checked', true);
		$('input[name=ecommerce]').attr('checked', false);
		$("#ecommerceGstinDiv").hide();
		
		
		
	}else if(defaultDocumentType == 'eComInvoice'){
		$("#documentType option[value='eComInvoice']").attr('selected', 'selected'); 
		$("#documentType-div").hide();
		$('input[name=reverseCharge]').attr('checked', false);
		$('input[name=ecommerce]').attr('checked', true);
		$("#ecommerceGstinDiv").show();
		
		
		
	}else if(defaultDocumentType == 'eComBillOfSupply'){
		$("#documentType option[value='eComBillOfSupply']").attr('selected', 'selected');
		$("#documentType-div").hide();
		$('input[name=reverseCharge]').attr('checked', false);
		$('input[name=ecommerce]').attr('checked', true);
		$("#ecommerceGstinDiv").show();
		
		
	}
	//make document type selected - End
	
	//set old items in dynmic div - Start
	
	var $toggle = $("#toggle");
	
	$.each(invoiceJson.serviceList,function(i,value) {
		rowNum++;
		var recordNo = rowNum;
		//get variables - Start
		var serviceNameSelectedTextValue = value.serviceIdInString;
		var serviceNameVal = value.serviceId;
		var uomValue = value.unitOfMeasurement;
		var rateValue = value.rate;
		var quantityValue = value.quantity;
		var calculation_on = value.calculationBasedOn;
		var invoiceFor = value.billingFor;  
		var previousAmount = 0;
		var cessValue = value.cess;
		var cessAdvolValue = value.advolCess;
		var cessNonAdvolValue = value.nonAdvolCess;
		var offerAmountValue = 0;
		var discountTypeInItem = value.discountTypeInItem;
		if(value.discountTypeInItem == "Percentage"){
			offerAmountValue = ((parseFloat(value.offerAmount).toFixed(2) * parseFloat(100))/(parseFloat(value.previousAmount).toFixed(2)));
		}else{
			offerAmountValue = value.offerAmount;
		}
		

		var billMethod = '';
		var hsnSacCode = value.hsnSacCode;
		var typeOfExport = '';
		var country = 'India';
		//check if calculation based on - Start 
		var uomDisplay = 'none';
		var quantityDisplay = 'block';
		if(calculation_on =='Amount'){
			billMethod = 'Quantity Based';
			uomDisplay = 'block';
			if(invoiceFor == 'Service'){
				quantityValue = 1;
				rateValue = value.previousAmount;
				previousAmount = value.previousAmount;
				uomDisplay = 'none';
				quantityDisplay = 'none';
			}else{
				previousAmount = quantityValue * rateValue;
				uomDisplay = 'block';
			}
		}else{
			rateValue = 0;
			//quantityValue = 0;
			previousAmount = value.amount;
			billMethod = 'Lumpsum';
		}
		//check if calculation based on - End 
		//check for diffPercent - Start
		var diffPercent = 'N';
		if(invoiceFor == 'Service'){
			diffPercent = (value.diffPercent == "Y")? 'Y' : 'N';
		}
		//check for diffPercent - End
		
		//check for description - Start
		var isDescriptionChecked = value.isDescriptionChecked;
		var descriptionCheck = "No";
		var descriptionTxt = '';
		var descriptionTxtDisplay = 'none';
		if(isDescriptionChecked == 'Yes'){
			descriptionTxt = value.description;
			descriptionTxtDisplay = 'block';
			descriptionCheck = "Yes";
		}
		//check for description - End
		
		
		//get variables - End
		
		$toggle.append('<div class="cust-content" id="service_start_'+recordNo+'">'
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
                    /*+'<p id="cess_'+recordNo+'">Cess : '+cessValue+'</p>'*/
                    +'<p id="cess_advol_rate_'+recordNo+'">Cess Advol Rate : '+cessAdvolValue+'%</p>'
                    +'<p id="cess_non_advol_rate_'+recordNo+'">Cess Non Advol. Amt : '+cessNonAdvolValue+'</p>'
                    +'<p id="discountTypeInItem_'+recordNo+'">Discount In : '+discountTypeInItem+'</p>'
                    +'<p id="offerAmount_'+recordNo+'">Discount : '+offerAmountValue+'</p>'
                    +'<p style="display : '+descriptionTxtDisplay+'" id="description_'+recordNo+'">Description : '+descriptionTxt+'</p>'
                    
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
                +'<input type="hidden" id="cess_advol_rate-'+recordNo+'" name="" value="'+cessAdvolValue+'">'
                +'<input type="hidden" id="cess_non_advol_rate-'+recordNo+'" name="" value="'+cessNonAdvolValue+'">'
                +'<input type="hidden" id="discountTypeInItem-'+recordNo+'" name="" value="'+discountTypeInItem+'">'
                +'<input type="hidden" id="offerAmount-'+recordNo+'" name="" value="'+offerAmountValue+'">'
                +'<input type="hidden" id="hsnSacCode-'+recordNo+'" name="" value="'+hsnSacCode+'">'
                +'<input type="hidden" id="diffPercent-'+recordNo+'" name="" value="'+diffPercent+'">'
                +'<input type="hidden" id="description_checked-'+recordNo+'" name="" value="'+descriptionCheck+'">'
                +'<input type="hidden" id="description-'+recordNo+'" name="" value="'+descriptionTxt+'">'
            +'</div>'
		+'</div>');
		openCloseAccordion(recordNo);
	});
	//set old items in dynmic div - End
	
	//set old additional charges item - Start
	var $addChgToggle = $("#add_chg_toggle");
	
	$.each(invoiceJson.addChargesList,function(i,value) {
		addChgRowNum++;
		var recordNo = addChgRowNum;
		var additionalChargeId = value.additionalChargeId;
		var additionalChargeName = value.additionalChargeName;
		var additionalChargeAmount = value.additionalChargeAmount;
		var additionalChargeAmountFixed = value.additionalChargeAmount;
		$addChgToggle.append('<div class="cust-content" id="add_chg_start_'+recordNo+'">'
    			+'<div class="heading">'
	                +'<div class="cust-con">'
	                    +'<h1 id="additionalChargeName_'+recordNo+'">'+additionalChargeName+'</h1>'
	                +'</div>'
	                +'<div class="cust-edit">'
	                    +'<div class="cust-icon">'
	                    	+'<a href="#callOnAddChgEditId" onclick="javascript:edit_add_chg_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
	                    	+'<a href="#" onclick="javascript:remove_add_chg_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
	                    +'</div>'
	                +'</div>'
	            +'</div>'
	            +'<div class="content">'
	                +'<div class="cust-con" id="addChgAmtForEdit_'+recordNo+'">'
	                    +'<p id="additionalChargeAmount_'+recordNo+'" >Amount : '+additionalChargeAmount+' </p>'
	                    
	                +'</div>'	
	            +'</div>'
	            +'<input type="hidden" id="additionalChargeId-'+recordNo+'" name="" value="'+additionalChargeId+'">'
	            +'<input type="hidden" id="additionalChargeName-'+recordNo+'" name="" value="'+additionalChargeName+'">'
	            +'<input type="hidden" id="additionalChargeAmount-'+recordNo+'" name="" value="'+additionalChargeAmount+'">'
	            +'<input type="hidden" id="additionalChargeAmountFixed-'+recordNo+'" name="" value="'+additionalChargeAmountFixed+'">'						            

		);
		openCloseAddChargesAccordion(addChgRowNum);
		
	});
	
	//set old additional charges item - End
	
	//set Button labels - start
	var btnName = "New";
	var showDocumentType = $("#documentType option:selected").text()
	if($("#invDataFiledToGstn").val() == "false"){
		btnName = "Revised";
	}
	$("#submitId").text("Preview "+btnName+" "+showDocumentType);
	$("#finalSubmitId").text("Send "+btnName+" "+showDocumentType);
	//end
	
}

function loadStateListJson(){
	$.ajax({
    	url : "getStatesList",
    	method : "POST",
		dataType : "json",
		data : { _csrf_token : $("#_csrf_token").val()},
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
			stateListXJson= json;
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
         }
	}); 
}

function loadStateList(){
	if(stateListXJson == ''){
		loadStateListJson();
	}
	
	$("#selectState").empty();
	$("#pos").val("");
	$("#selectState").append('<option value="">Select</option>');
	$.each(stateListXJson,function(i,value) {
		$("#selectState").append($('<option>').text(value.stateName).attr('value',value.id));
	});
}

function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

function checkIfGstinAndLocationIsSelected(){
	var isSelected = false;
	
	//fetch gstin number like 27XXXXX1234A1ZQ[Maharashtra]
	 var gstnWithStateInString = $("#gstnStateId option:selected").text();
	 var gstnStateIdInString = (gstnWithStateInString.split('[')[0]).trim();
	 var gstnStateId = $('select#gstnStateId option:selected').val();
	 if(gstnStateIdInString == undefined || gstnStateIdInString == 'Select' || gstnStateId == ''){ 
		   alert("Please Select GSTIN");
	 }else{
		 
		//fetch location
		 var locationVal = $('select#location option:selected').val();
		 if(locationVal == undefined || locationVal == '' ){ 
			   alert("please select location");
		 }else{
			 isSelected = true;
		 }
		 
	 }
	return isSelected;
}

function getServiceList(){
	var defaultDocumentType = $("#selectedDocType").val();
	var urlToFetchService = '';
	if(defaultDocumentType == 'invoice' || defaultDocumentType == 'rcInvoice' || defaultDocumentType == 'eComInvoice'){ 
		urlToFetchService = 'invoiceServiceList';
	}else if(defaultDocumentType == 'billOfSupply' || defaultDocumentType == 'eComBillOfSupply'){
		urlToFetchService = 'billOfSupplyServiceList';
	}
	
	 if(checkIfGstinAndLocationIsSelected()){
		 $(document).ready(function(){
			    $.ajax({
					url : urlToFetchService,/*"getServicesList",*/
					type : "POST",
					/*contentType : "application/json",*/
					dataType : "json",
					data : { _csrf_token : $("#_csrf_token").val(), gNo : $("#gstnStateId option:selected").text().split('[')[0].trim(), location : $('select#location option:selected').val()},
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
						
						/* $('#service_name').empty();
						 $('#service_name').append(
							'<option value="">Select Service</option>');
							$.each(json,function(i,value) {
								$('#service_name').append(
									$('<option>').text(value.name).attr('value',value.id)
								);
							});*/
						userServiceList = json;	
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			         },
			         error: function (data,status,er) {
			        	 getInternalServerErrorPage();   
			         }
				}); 
			
			     
			}); 
	 }
	

}


/*function getOffers(serviceId,serviceType){
	$(document).ready(function() {
		$.ajax({
			url : "getManageOfferById",
			method : "POST",
			contentType : "application/json",
			dataType : "json",
			data : { serviceId : serviceId, serviceType : serviceType},
			async : true,
			success : function(json) {
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				//var json = $.parseJSON(jsonobj);
				
				 $('#offers').empty();
				 $('#offers').append(
					'<option value="">Select Offers</option>');
					$.each(json,function(i,value) {
						$('#offers').append(
							$('<option>').text(value.offerName).attr('value',value.id)
						);
					});
			}
        }); 
	});
	
}*/

function getProductList(){
	/*if ($("#location").val() === "" || $("#location").val() === null || $("#location").val() == undefined){
		//alert("Please select GSTIN and Location");
	}else{*/
		var defaultDocumentType = $("#selectedDocType").val();
		var urlToFetchProduct = '';
		if(defaultDocumentType == 'invoice' || defaultDocumentType == 'rcInvoice' || defaultDocumentType == 'eComInvoice'){ 
			urlToFetchProduct = 'invoiceProductList';
		}else if(defaultDocumentType == 'billOfSupply' || defaultDocumentType == 'eComBillOfSupply'){
			urlToFetchProduct = 'billOfSupplyProductList';
		}
		 
		 if(checkIfGstinAndLocationIsSelected()){
			 $(document).ready(function(){
				    $.ajax({
						url : urlToFetchProduct,/*"getProductsList",*/
						method : "post",
						/*contentType : "application/json",*/
						dataType : "json",
						data : { _csrf_token : $("#_csrf_token").val(), gNo : $("#gstnStateId option:selected").text().split('[')[0].trim(), location : $('select#location option:selected').val()},
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
							
							/* $('#service_name').empty();
							 $('#service_name').append(
								'<option value="">Select Goods</option>');
								$.each(json,function(i,value) {
									$('#service_name').append(
										$('<option>').text(value.name).attr('value',value.id)
									);
								});*/
							userProductList = json;	
							setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				         },
				         error: function (data,status,er) {
				        	 
				        	 getInternalServerErrorPage();   
				        }
					});    
				}); 
		 }
	/*}*/
	
}

$(function () {
	
	var isChecked = $('#shipToBill').is(':checked'); 

	if(isChecked == true){
		$("#consignee").hide();
		$("#selectState-show-hide").hide();
		$("#pos-show-hide").show();
		if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
			$("#selectState-show-hide").show();
            $("select[id^=selectState]").removeAttr("disabled");
            $("#pos-show-hide").hide();
		}else{
			$("#selectState-show-hide").hide();
	        $("select[id^=selectState]").attr("disabled",true);
	        $("#pos-show-hide").show();
		}
	}else{
		$("#consignee").show();
		//loadStateList();
		$("#selectState-show-hide").show();
		$("select[id^=selectState]").attr("disabled", true);
		$("#pos-show-hide").hide();
		if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
			$("#selectState-show-hide").show();
            $("select[id^=selectState]").removeAttr("disabled");
            $("#pos-show-hide").hide();
		}else{
			$("#selectState-show-hide").hide();
	        $("select[id^=selectState]").attr("disabled",true);
	        $("#pos-show-hide").show();
		}
	}
	
	$("#shipToBill").click(function () {
        if ($(this).is(":checked")) {
        	$("#consignee").hide();
        	autoPopulateStateList($("#customer_custStateId").val());
        	$("#shipTo-pincode-show-hide").hide();
 	    	$("#shipTo-city-show-hide").hide();
 	    	
 	    	$("#selectState-show-hide").hide();
 			$("#pos-show-hide").show();
 			if($('input[name=invoiceFor]').filter(':checked').val() == 'Service'){
 				$("#selectState-show-hide").show();
 	            $("select[id^=selectState]").removeAttr("disabled");
 	            $("#pos-show-hide").hide();
 			}else{
 				$("#selectState-show-hide").hide();
 		        $("select[id^=selectState]").attr("disabled",true);
 		        $("#pos-show-hide").show();
 			}
        } else {
            $("#consignee").show();
            loadStateList();
            //$("#selectCountry").val("");
            loadCountry();
            $("#selectCountryShowHide").css("display","none");
            $("#shipTo-pincode-show-hide").show();
	    	$("#shipTo-city-show-hide").show();
	    	
	    	$("#selectState-show-hide").show();
	    	$("select[id^=selectState]").attr("disabled", true);
			$("#pos-show-hide").hide();
        }
	});
	
	
});

function loadCountry(){
	$('#selectCountry option[value=India]').attr('selected','selected');
}

$(function () {
	
	$("#ecommerce").click(function () {
		 if ($(this).is(":checked")) {
			 //$("#reverseCharge").prop("disabled", true);
			 $("#ecommerceGstinDiv").show();
		 }else{
			 //$("#reverseCharge").prop("disabled", false);
			 $("#ecommerceGstinDiv").hide();
		 }
		
	});
	
	$("#description").click(function () {
		 if ($(this).is(":checked")) {
			 $("#descriptionDiv").show();
		 }else{
			 $("#descriptionDiv").hide();	 
		 }
		
	});
	
});

function changeServiceNameAsPerAutoComplete(selectedJson){
	var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
    //var serviceId = $(this).val();
    var quantity = $("#quantity").val();
    var billMethod = $('select#calculation_on option:selected').val();
    setValueInSelectServiceName(selectedJson,invoiceFor);
    setValueInAddServiceProductFormFields(selectedJson,invoiceFor,quantity,billMethod);
    setValueInSelectCessNonAdvol(selectedJson.advolCess,selectedJson.nonAdvolCess,invoiceFor);
}
function changeServiceNameDropDownAsPerSelectedOne(invoiceFor , serviceName){
	
	var dynamicallyFetchJson = '';
	if(invoiceFor == 'Service'){
		dynamicallyFetchJson = fetchDynamicallyAddedService($("#locationStorePkId").val(),serviceName);
	}else{
		dynamicallyFetchJson = fetchDynamicallyAddedProduct($("#locationStorePkId").val(),serviceName);
	}
	setValueInSelectServiceName(dynamicallyFetchJson,invoiceFor);
	
}
function setValueInSelectServiceName(selectedJson,invoiceFor){
	var json = '';
	
	if(invoiceFor == 'Service'){
		getServiceList();
		json = userServiceList;
	}else{
		getProductList();
		json = userProductList;
	}
	
	$('#service_name').empty();
	if(invoiceFor == 'Service'){
		$('#service_name').append('<option value="">Select</option>');	 
	}else{
		$('#service_name').append('<option value="">Select</option>');	
	}
	
	$.each(json,function(i,value) {
		if(selectedJson.id == value.id){
			$('#service_name').append($('<option>').text(value.name).attr('value',value.id).attr('selected','selected')); 
		 }else{
			 $('#service_name').append($('<option>').text(value.name).attr('value',value.id));
		 }
		
	});
}

function setValueInAddServiceProductFormFields(json,invoiceFor,quantity,billMethod){
	
	$("#uom").val('');
	$("#uomtoShow").val('');
	$("#rate").val('');
	$("#hsnSacCode").val('');
	$("#amount").val('');
	$("#amountToShow").val('');
	$("#openingStockProduct").val('');
	
	if(invoiceFor == 'Service'){
		 $("#search-service-autocomplete").val(json.name);
		 $("#uom").val(json.unitOfMeasurement);
		 $("#uomtoShow").val(json.serviceRate +" Rs per "+json.unitOfMeasurement);
		 
		 $("#rate").val(json.serviceRate);
		 $("#hsnSacCode").val(json.sacCode);
		/* $("#ratetoShow").val(json.serviceRate);*/
		/* if(quantity){
			 if(billMethod == 'Amount'){
			     var amount = quantity * json.serviceRate;
			     $("#amount").val(amount);
			     $("#amountToShow").val(amount);
			}
		 }*/
		 $("#amount").val(json.serviceRate);
	     $("#amountToShow").val(json.serviceRate);
		 
	}else{
		$("#search-product-autocomplete").val(json.name);
		$("#uom").val(json.unitOfMeasurement);
		 $("#uomtoShow").val(json.productRate+" Rs per "+json.unitOfMeasurement);
		 
		 $("#rate").val(json.productRate);
		 $("#hsnSacCode").val(json.hsnCode);
		 /*$("#ratetoShow").val(json.productRate);*/
		 $("#openingStockProduct").val(json.currentStock);
		 
		 if(quantity){
			 if(billMethod == 'Amount'){
			     var amount = quantity * json.productRate;
			     $("#amount").val(amount);
			     $("#amountToShow").val(amount);
			}
		 }
		 $("#qtyLabel").text("  [ Current - "+json.currentStock+" ]");
	}
}

function fetchGstins(){
	var resultFetched = false;
	var oldGstnStateId = $("#old_gstnStateId").val();
	var oldGstnStateIdInString = $("#old_gstnStateIdInString").val();
	var oldLocation = $("#old_location").val();
	var oldLocationStorePkId = $("#old_locationStoreId").val();

	$.ajax({
		url : "getGSTINListAsPerRole",
		method : "get",
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
			
			$("#gstnStateId").empty();
			if(json.length == 1){
				$("#gstnStateIdDiv").hide();
				$.each(json,function(i,value) {
					if(value.gstinNo == oldGstnStateIdInString){
						$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state).attr('selected','selected'));
					}
				});
				
				//if location length is 1 hide location else show location dropdown
				if(json[0].gstinLocationSet.length == 1){
					$("#locationDiv").hide();
					$.each(json[0].gstinLocationSet,function(i,value) {
						if(value.uniqueSequence == oldLocation){
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
							$("#locationStore").val(value.gstinStore);
							$("#locationStorePkId").val(value.id);
						}
						
					});
					setResetSearchProductServiceInputFields('');
					
				}else{
					$("#locationDiv").show();
					$("#location").append('<option value="">Select</option>');
					$.each(json[0].gstinLocationSet,function(i,value) {
						if(value.uniqueSequence == oldLocation){
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
							$("#locationStore").val(value.gstinStore);
							$("#locationStorePkId").val(value.id);
						}else{
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
						}
						
					});
					setResetSearchProductServiceInputFields('');
				}
			}else{
				$("#gstnStateIdDiv").show();
				$("#gstnStateId").append('<option value="">Select</option>');
				$.each(json,function(i,value) {
					if(value.gstinNo == oldGstnStateIdInString){
						$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state).attr('selected','selected'));
						
						//if location length is 1 hide location else show location dropdown
						if(value.gstinLocationSet.length == 1){
							$("#locationDiv").hide();
							$.each(json[0].gstinLocationSet,function(i,value) {
								if(value.uniqueSequence == oldLocation){
									$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
									$("#locationStore").val(value.gstinStore);
									$("#locationStorePkId").val(value.id);
								}
								
							});
							setResetSearchProductServiceInputFields('');
							
						}else{
							$("#locationDiv").show();
							$("#location").append('<option value="">Select</option>');
							$.each(value.gstinLocationSet,function(i,value) {
								if(value.uniqueSequence == oldLocation){
									$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
									$("#locationStore").val(value.gstinStore);
									$("#locationStorePkId").val(value.id);
								}else{
									$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
								}
								
							});
							setResetSearchProductServiceInputFields('');
						}
						
						
						
					}else{
						$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
					}
					
				});

				
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			userGstinsJson = json;
			resultFetched = true;
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
         }
	}); 
	return resultFetched;
}

function setResetSearchProductServiceInputFields(disable){
	if(disable == 'disabled'){
		$("#search-service-autocomplete").attr('disabled', 'disabled');
		$("#search-product-autocomplete").attr('disabled', 'disabled');
		$("#show-select-gstin-location-msg").show();
	}else{
		$("#search-service-autocomplete").removeAttr("disabled");
		$("#search-product-autocomplete").removeAttr("disabled");
		$("#show-select-gstin-location-msg").hide();
	}
}

function loadGenericUserGstin(){
	if(userGstinsJson == ''){
		$.ajax({
			url : "getGSTINListAsPerRole",
			method : "get",
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
				userGstinsJson = json;
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	         }
		});
	}
	
}

function setLocationStorePkId(gstnNumber,gstnState,gstinLocationSetUniqueSequence,gstinLocationSetgstinLocation){
	if(userGstinsJson != ''){
		$.each(userGstinsJson,function(i,value){
			if(value.gstinNo == gstnNumber){
				if(value.gstinLocationSet.length == 1){
	  				$.each(value.gstinLocationSet,function(i,value2) {
	  					$("#locationStorePkId").val(value2.id);
	  				});
	  			}
	  			else{   
					 $.each(value.gstinLocationSet,function(i,value3){
						 if(value3.uniqueSequence == gstinLocationSetUniqueSequence && value3.gstinLocation == gstinLocationSetgstinLocation){
							 $("#locationStorePkId").val(value3.id); 
						 }
					});
	  			}
				
			} 
		});
	}
}

function setValueInSelectCessNonAdvol(selectedJsonAdvolCess,selectedJsonNonAdvolCess,invoiceFor){
	var cessNonAdvolJson = '';
	var cessAdvolJson = '';
	
	if(invoiceFor == 'Service'){
		cessNonAdvolJson = serviceNonAdvolCessRateJson;
		cessAdvolJson = serviceAdvolCessRateJson;
	}else{
		cessNonAdvolJson = productNonAdvolCessRateJson;
		cessAdvolJson = productAdvolCessRateJson;
	}
	
	$('#cessAdvolId').empty();
	$.each(cessAdvolJson,function(i,value) {
		/*if(selectedJsonAdvolCess == value.cessRate){
			$('#cessAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate).attr('selected','selected')); 
		 }else{*/
			 $('#cessAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate));
		 /*}*/
		
	});
	
	//$('#cessNonAdvolId').val(selectedJsonNonAdvolCess);
	$('#cessNonAdvolId').val(0);
}

function loadGstinsInventory(){
	$.ajax({
		  url:"getGSTINForProductServices", 	
		  type : "POST",
		  dataType: 'json',
          async : false,
		  data:{_csrf_token : $("#_csrf_token").val()},
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
			  //console.log(json);
			  gstinsInventoryJson = json;
			 
		  }
	});
	
}

function checkIfInvoiceNumberAlreadyPresent(invoiceNumber){
	var isPresent = false;
	$.ajax({
		  url:"checkIfInvNumAlreadyPresent", 	
		  type : "POST",
		  dataType: 'json',
		  headers: {_csrf_token : $("#_csrf_token").val()},
          async : false,
		  data:{"invoiceNumber" : invoiceNumber},
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
			  if(json.allowed_access == "TRUE"){
				  isPresent = false;
			  }else if(json.allowed_access == "FALSE"){
				  isPresent = true;
			  }
			 
		  }
	});
	
	return isPresent;
}

function getAdditionalChargesDetailsList(){
	 $.ajax({
		url : "getAddChargesDetailsList",
		type : "post",
		/*contentType : "application/json",*/
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
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
			
			$("#additionalChargeName").empty();
				
			$("#additionalChargeName").append('<option value="">Select</option>');
			$.each(json,function(i,value) {
					
				$("#additionalChargeName").append($('<option>').text(value.chargeName).attr('value',value.id+"{--}"+value.chargeName+"{--}"+value.chargeValue));
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			userAdditionalChargeJson = json;
        },
        error: function (data,status,er) {
       	 
       	 getInternalServerErrorPage();   
        }
	});
	 return userAdditionalChargeJson;
}

