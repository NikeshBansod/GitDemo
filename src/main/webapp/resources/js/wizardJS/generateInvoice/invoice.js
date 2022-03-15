
var lastAndFinalInvoiceJson = '';
var accordionsId = '';
var addChgAccordionsId = '';
var userServiceList = '';
var userProductList = '';

$(document).ready(function () {
	
    var defaultInvoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
    //alert('Document Ready'+defaultInvoiceFor); 
    
    callDefaultOnPageLoad(defaultInvoiceFor);
    
    loadStateList();
	loadCountry();
	
	
	if($('.accordion_example2').length > 0){
	    $(".accordion_example2").smk_Accordion({
	        closeAble: true, //boolean
	    });
	}
	
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
						headers: {_csrf_token : $("#_csrf_token").val()},
						method : "POST",
						/*contentType : "application/json",*/
						dataType : "json",
						data : { serviceId : serviceId },
						async : false,
						success : function(json,fTextStatus,fRequest) {
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultWizardSessionExpirePage();
								return;
							}
							
							if(isValidToken(json) == 'No'){
								window.location.href = getWizardCsrfErrorPage();
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
							 getWizardInternalServerErrorPage();   
						}
			        }); 
				//getOffers(serviceId,"Service");
			}else{
				
				 $.ajax({
						url : "getProductById",
						headers: {
							_csrf_token : $("#_csrf_token").val()
						},
						method : "POST",
						/*contentType : "application/json",*/
						data : { productId : serviceId },
						dataType : "json",
						async : false,
						success : function(json,fTextStatus,fRequest) {							
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultWizardSessionExpirePage();
								return;
							}
							
							if(isValidToken(json) == 'No'){
								window.location.href = getWizardCsrfErrorPage();
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
							 getWizardInternalServerErrorPage();   
						}
			        }); 
				//getOffers(serviceId,"Product");
			} 	      
	     }  	       
	  });
	
	$("#quantity").keyup(function() {
	    var $this = $(this);
	    $this.val($this.val().replace(/[^\d.]/g, ''));        
	});
	
	
	$('#quantity').keyup(function(){
		var billMethod = $('select#calculation_on option:selected').val();
		if(billMethod == 'Amount'){
		     var quantity = $("#quantity").val();
		     var rate = $("#rate").val();
		     var amount = quantity * rate;
		     $("#amount").val(amount);
		     $("#amountToShow").val(amount);
		}
	     
	}); 
	
    $('input[type=radio][name=invoiceFor]').change(function() {
        if (this.value == 'Service') {
            getServiceList();
            $("#callOnEditId").text("Add Services");
            $("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
            $("#service_name_label").text("Search By Service Name");
            $("#search-service-autocomplete").show();
            $("#search-product-autocomplete").hide();
            $("#invoicePeriodDivShowHide").show();
            $("#ecommerce-label").text("Services are sold through E-commerce operator");
            $("#diffPercentageDiv").show();
        }
        else if (this.value == 'Product') {
            getProductList();
            
            $("#callOnEditId").text("Add Goods");
            $("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
            $("#service_name_label").text("Search By Goods Name");
            $("#search-service-autocomplete").hide();
            $("#search-product-autocomplete").show();
            $("#invoicePeriodDivShowHide").hide();
            $("#ecommerce-label").text("Goods are sold through E-commerce operator");
            $("#diffPercentageDiv").hide();
        }
     });
    
    $("#invoicePeriodFromDateInString").datepicker({
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
	
    $.ajax({
		url : "getGSTINListAsPerRole",
		method : "get",
		contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$("#gstnStateId").empty();
			if(json.length == 1){
				$("#gstnStateIdDiv").hide();
				$.each(json,function(i,value) {
					$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
				});
				
				//if location length is 1 hide location else show location dropdown
				if(json[0].gstinLocationSet.length == 1){
					$("#locationDiv").hide();
					$.each(json[0].gstinLocationSet,function(i,value) {
						$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
						$("#locationStore").val(value.gstinStore);
					});
					
				}else{
					$("#locationDiv").show();
					$("#location").append('<option value="">Select</option>');
					$.each(json[0].gstinLocationSet,function(i,value) {
						$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
					});
				}
			}else{
				$("#gstnStateIdDiv").show();
				$("#gstnStateId").append('<option value="">Select</option>');
				$.each(json,function(i,value) {
					$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
				});
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
         },
         error: function (data,status,er) {        	 
        	 getWizardInternalServerErrorPage();   
         }
	});
    
    $.ajax({
		url : "getAddChargesDetailsList",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "post",
		contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$("#additionalChargeName").empty();
				
			$("#additionalChargeName").append('<option value="">Select</option>');
			$.each(json,function(i,value) {
					
				$("#additionalChargeName").append($('<option>').text(value.chargeName).attr('value',value.id+"{--}"+value.chargeName+"{--}"+value.chargeValue));
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {        	 
        	 getWizardInternalServerErrorPage();   
        }
	}); 
    
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
    
    
	
});

function callDefaultOnPageLoad(defaultInvoiceFor){
	
	if(defaultInvoiceFor == 'Service'){
		getServiceList();
		$("#callOnEditId").text("Add Services");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Search By Service Name");
		$("#search-service-autocomplete").show();
        $("#search-product-autocomplete").hide();
		$("#invoicePeriodDivShowHide").show();
		$("#ecommerce-label").text("Services are sold through E-commerce operator");
		$("#diffPercentageDiv").show();
		
	}else{
		getProductList();
		$("#callOnEditId").html("");
		$("#callOnEditId").text("Add Goods");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Search By Goods Name");
		$("#search-service-autocomplete").hide();
        $("#search-product-autocomplete").show();
		$("#invoicePeriodDivShowHide").hide();
		$("#ecommerce-label").text("Goods are sold through E-commerce operator");
		$("#diffPercentageDiv").hide();
		
	}
}

function loadStateList(){
    $.ajax({
    	url : "getStatesList",
    	headers: {
    		_csrf_token : $("#_csrf_token").val()
    	},
    	method : "POST",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$("#selectState").empty();
			$("#pos").val("");
			$("#selectState").append('<option value="">Select</option>');
			$.each(json,function(i,value) {
				
				$("#selectState").append($('<option>').text(value.stateName).attr('value',value.id));
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getWizardInternalServerErrorPage();   
        }
	}); 
}

function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}



function getServiceList(){
	$(document).ready(function(){
	    $.ajax({
			url : "getServicesList",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			type : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
				 $('#service_name').empty();
				 $('#service_name').append(
					'<option value="">Select</option>');
					$.each(json,function(i,value) {
						$('#service_name').append(
							$('<option>').text(value.name).attr('value',value.id)
						);
					});
					userServiceList = json;	
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {	        	 
	        	 getWizardInternalServerErrorPage();   
	        }
		}); 	     
	}); 

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
	$(document).ready(function(){
	    $.ajax({
			url : "getProductsList",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
			/*contentType : "application/json",*/
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}
				
				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				
				 $('#service_name').empty();
				 $('#service_name').append(
					'<option value="">Select</option>');
					$.each(json,function(i,value) {
						$('#service_name').append(
							$('<option>').text(value.name).attr('value',value.id)
						);
					});
					userProductList = json;
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));			
	         },
	         error: function (data,status,er) {	        	 
	        	 getWizardInternalServerErrorPage();   
	        }
		}); 	     
	}); 
}

$(function () {
	
	var isChecked = $('#shipToBill').is(':checked'); 

	if(isChecked == true){
		$("#consignee").hide();
		$("#selectState-show-hide").hide();
		$("#pos-show-hide").show();
	}else{
		$("#consignee").show();
		loadStateList();
		$("#selectState-show-hide").show();
		$("select[id^=selectState]").attr("disabled", true);
		$("#pos-show-hide").hide();
	}
	
	$("#shipToBill").click(function () {
        if ($(this).is(":checked")) {
        	$("#consignee").hide();
        	autoPopulateStateList($("#customer_custStateId").val());
        	$("#shipTo-pincode-show-hide").hide();
 	    	$("#shipTo-city-show-hide").hide();
 	    	
 	    	$("#selectState-show-hide").hide();
 			$("#pos-show-hide").show();
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
	
	$("#reverseCharge").click(function () {
		 if ($(this).is(":checked")) {
			 //$("#ecommerce").prop("disabled", true);
		 }else{
			 //$("#ecommerce").prop("disabled", false);
			 
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
		$('#service_name').append('<option value="">Select Service</option>');	 
	}else{
		$('#service_name').append('<option value="">Select Goods</option>');	
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
	
	if(invoiceFor == 'Service'){
		 $("#search-service-autocomplete").val(json.name);
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
	}else{
		$("#search-product-autocomplete").val(json.name);
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
	}
}
 
