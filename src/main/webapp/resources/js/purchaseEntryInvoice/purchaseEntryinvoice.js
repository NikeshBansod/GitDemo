var lastAndFinalInvoiceJson = '';
var accordionsId = '';
var addChgAccordionsId = '';

$(document).ready(function () {	
    var defaultInvoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
    callDefaultOnPageLoad(defaultInvoiceFor);
});

function split(val) {
    return val.split(/,\s*/);
}

function callDefaultOnPageLoad(defaultInvoiceFor){	
	if(defaultInvoiceFor == 'Service'){
		getServiceList();
		$("#callOnEditId").text("Add Services");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Service Name");
		$("#invoicePeriodDivShowHide").show();	
	}else{
		getProductList();
		$("#callOnEditId").html("");
		$("#callOnEditId").text("Add Goods");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Goods Name");
		$("#invoicePeriodDivShowHide").hide();
	}
}

$(function () {
	$("#reverseCharge").click(function () {
		 if ($(this).is(":checked")) {
			  $("#SupplierDoc").show();
		 }else{
			 $("#SupplierDoc").hide();
			 $("#supplierDocInvoiceNo").val('');
			 $("#supplierDocInvoiceDate").val('');
		 }		
	});
});

function getServiceList(){
	    $.ajax({
			url : "getServicesList",
			type : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			async : false,
			data : { _csrf_token : $("#_csrf_token").val()},
			success:function(json,textStatus,request){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				 $('#service_name').empty();
				 $('#service_name').append(
					'<option value="">Select Service</option>');
					$.each(json,function(i,value) {
						$('#service_name').append(
							$('<option>').text(value.name).attr('value',value.id)
						);
					});
					setCsrfToken(request.getResponseHeader('_csrf_token'));	
	         }
		});	
}

function getProductList(){
	    $.ajax({
			url : "getProductsList",
			method : "post",
			/*contentType : "application/json",*/
			dataType : "json",
			async : false,
			data : { _csrf_token : $("#_csrf_token").val()},
			success:function(json,textStatus,request){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				 $('#service_name').empty();
				 $('#service_name').append(
					'<option value="">Select Goods</option>');
					$.each(json,function(i,value) {
						$('#service_name').append(
							$('<option>').text(value.name).attr('value',value.id)
						);
					});
					setCsrfToken(request.getResponseHeader('_csrf_token'));	
	         }
		}); 
}

$(document).ready(function(){
    $.ajax({
		url : "getGSTINListAsPerRole",
		method : "get",
		contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json,textStatus,request){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			setCsrfToken(request.getResponseHeader('_csrf_token'));
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
					$("#location").append('<option value="">Select your Location</option>');
					$.each(json[0].gstinLocationSet,function(i,value) {
						$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
					});
				}
			}else{
				$("#gstnStateIdDiv").show();
				$("#gstnStateId").append('<option value="">Select your GSTIN</option>');
				$.each(json,function(i,value) {
					$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
				});
			}
			getGstinDetailsFromGstinNo();
         }
	});   

	$.ajax({
		url : "getAddChargesDetailsList",
		type : "post",
		/*contentType : "application/json",*/
		dataType : "json",
		async : false,
		data : { _csrf_token : $("#_csrf_token").val()},
		success:function(json,textStatus,request){
			if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
			}
			$("#additionalChargeName").empty();				
			$("#additionalChargeName").append('<option value="">Select Additional Charges</option>');
			$.each(json,function(i,value) {					
				$("#additionalChargeName").append($('<option>').text(value.chargeName).attr('value',value.id+"{--}"+value.chargeName+"{--}"+value.chargeValue));
			});
			setCsrfToken(request.getResponseHeader('_csrf_token'));	
         }
	}); 

    $.ajax({
    	url : "getStatesList",
    	method : "POST",
		dataType : "json",
		async : false,
		data : { _csrf_token : $("#_csrf_token").val()},
		success:function(json,textStatus,request){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			$("#placeOfSupply").empty();
			$("#placeOfSupply").append('<option value="">Select Place Of Supply</option>');
			$.each(json,function(i,value) {					
				$("#placeOfSupply").append($('<option>').text(value.stateName).attr('value',value.id));
			});
			setCsrfToken(request.getResponseHeader('_csrf_token'));	
         }
	}); 

    
    $('input[type=radio][name=invoiceFor]').change(function() {
        if (this.value == 'Service') {
            getServiceList();
            $("#callOnEditId").text("Add Services");
            $("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
            $("#service_name_label").text("Service Name");
            $("#invoicePeriodDivShowHide").show();
        }
        else if (this.value == 'Product') {
            getProductList();
            
            $("#callOnEditId").text("Add Goods");
            $("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
            $("#service_name_label").text("Goods Name");
            $("#invoicePeriodDivShowHide").hide();
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
          }
          else{
        	  $("#invoicePeriodToDateInString").addClass("input-error").removeClass("input-correct");
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
          }
          else{
        	  $("#invoicePeriodFromDateInString").addClass("input-correct").removeClass("input-error");
          }
        }
    });//.datepicker("setDate", new Date());     
    
    $("#purchase_date").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
        onSelect: function(selected) {
        	$("#purchase_date").addClass("input-correct").removeClass("input-error");
        }
    }).datepicker("setDate", new Date());
    
    $("#supplierDocInvoiceDate").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
        onSelect: function(selected) {
        	$("#supplierDocInvoiceDate").addClass("input-correct").removeClass("input-error");
        }
    });
    
    $('input[type=radio][name=custType]').change(function() {
        if (this.value == 'Registered') {           
            $("#consignee").show();
            if($("#supplier_gstin").val().length > 14){				
            	$("#supplier_gstin").addClass("input-correct").removeClass("input-error");		 
	 		}else{
	 			$("#supplier_gstin").addClass("input-error").removeClass("input-correct");		 
	 		}            
        }
        else if (this.value == 'UnRegistered') {          
            $("#consignee").hide();
            $("#supplier_gstin").val('').removeClass("input-correct").removeClass("input-error");
        }
    });
    
    $("#shipToBill").click(function() {
    	if ($(this).is(":checked")) {
    		var billingAddr = $("#billing_address").val();
    		
    		if ($("#billing_address").val().length > 1){
    			$("#shipping_address").prop("disabled", true);
    			$("#shipping_address").val(billingAddr);
    			$("#billing_address,#shipping_address").addClass("input-correct").removeClass("input-error");
    		}else{
    			$("#billing_address").addClass("input-error").removeClass("input-correct");
    		}
		 }else{
			 $("#shipping_address").val('');
 			 $("#shipping_address").prop("disabled", false);
			 $("#shipping_address").removeClass("input-correct");
			 $("#shipping_address").removeClass("input-error");
		 }        
    });
    
    $("#service_name").on("change", function() {		 
		 var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
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
						async : true,
						success : function(json,textStatus,request) {
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultSessionExpirePage();
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
								setCsrfToken(request.getResponseHeader('_csrf_token'));	
						}
			        }); 
				//getOffers(serviceId,"Service");
			}else{				
				 $.ajax({
						url : "getProductById",
						method : "POST",
						/*contentType : "application/json",*/
						data : { productId : serviceId, _csrf_token : $("#_csrf_token").val()  },
						dataType : "json",
						async : true,
						success : function(json,textStatus,request) {							
							if (isValidSession(json) == 'No') {
								window.location.href = getDefaultSessionExpirePage();
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
								setCsrfToken(request.getResponseHeader('_csrf_token'));	
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
	
	$('#quantity').change(function(){
		var billMethod = $('select#calculation_on option:selected').val();
		if(billMethod == 'Amount'){
		     var quantity = $("#quantity").val();
		     var rate = $("#rate").val();
		     var amount = quantity * rate;
		     $("#amount").val(amount);
		     $("#amountToShow").val(amount);
		}	     
	})
	
	$("#calculation_on").on("change", function() {
		$("#quantity").val("");
	      if($(this).val()=="Amount"){
	    	  $("#uom-show-hide").show();   
	    	 /* $("#rate-show-hide").show();*/
	    	  $("#quantity-show-hide").show();
	    	  $("#amount-show-hide").show();
	    	  $("#amountToShow").attr('disabled','disabled');    	 
	      }
	      
	      if($(this).val()=="Lumpsum"){
	    	  $("#uom-show-hide").hide();   
	    	 /* $("#rate-show-hide").hide();*/
	    	  $("#quantity-show-hide").show();
	    	  $("#amount-show-hide").show();
	    	  $("#amountToShow").removeAttr('disabled');
	    	  $("#quantity").val(1);
	      }
	      
	      if($(this).val()==""){
	    	  $("#uom-show-hide").hide();   
	    	  /*$("#rate-show-hide").hide();*/
	    	  $("#quantity-show-hide").hide();
	    	  $("#amount-show-hide").hide();
	    	  $("#amountToShow").removeAttr('disabled'); 
	      }
	});	
	
	$("#placeOfSupply").on("change", function(){
		var posId = $('select#placeOfSupply option:selected').val();
		if(posId == ""){
			$("#placeOfSupply_Name").val('');
			$("#placeOfSupply_Code").val('');
			$("#placeOfSupply_Id").val('');	
		}
		$.ajax({
			url : "getStatesList",
			method : "POST",
			dataType : "json",
			data : { _csrf_token : $("#_csrf_token").val()},
			async : false,
			  success:function(json,textStatus,request) {
				  if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
				  }
				  $.each(json, function(i, value) {				   
					  if(posId == value.id){
						  $("#placeOfSupply_Name").val(value.stateName);
						  $("#placeOfSupply_Code").val(value.stateCode);
						  $("#placeOfSupply_Id").val(value.stateId);				  
					  }
				  });
					setCsrfToken(request.getResponseHeader('_csrf_token'));
			  }
		});	 
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
    	else if(additionalChargeValue === ""){
    		$("#additionalChargeAmountToShow").val('');
    	}
    });	

	$("#gstnStateId").on("change", function() {
		getGstinDetailsFromGstinNo(); 
	});
	
});

function getGstinDetailsFromGstinNo(){
	var gstnWithStateInString = $("#gstnStateId option:selected").text();
	var gstinNo = (gstnWithStateInString.split('[')[0]).trim();
	
	if(gstnWithStateInString != 'Select your GSTIN' || $("#gstnStateId").val() != ""){
		$.ajax({
			url : "getGstinDetailsFromGstinNo",
			method : "POST",
			dataType : "json",
			data : { gstinNo : gstinNo, _csrf_token : $("#_csrf_token").val() },
			async : false,
			success : function(json,textStatus,request) {					
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}					
				$.each(json.gstinLocationSet, function(i, value) {
					if(json.gstinLocationSet.length == 1){
						$("#locationDiv").hide();
						$("#location").empty();
						$.each(json.gstinLocationSet,function(i,value) {
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
						});
					}else{
						$("#locationDiv").show();
						$("#location").empty();
						$("#location").append('<option value="">Select your Location/Store</option>');
						$.each(json.gstinLocationSet,function(i,value) {
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
						});
					}
				});
				
				$("#purchaser_org_address1").val(json.gstinAddressMapping.address); 
				$("#purchaser_org_city").val(json.gstinAddressMapping.city);
				$("#purchaser_org_pinCode").val(json.gstinAddressMapping.pinCode);
				$("#purchaser_org_stateCode").val(json.state);
				$("#purchaser_org_state").val(json.gstinAddressMapping.state);
				setCsrfToken(request.getResponseHeader('_csrf_token'));						
			}
	    }); 
	}else{
		$("#location").empty();
	}    
}
