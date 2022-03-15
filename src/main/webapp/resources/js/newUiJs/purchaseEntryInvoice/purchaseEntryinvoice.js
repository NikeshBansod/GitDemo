
var lastAndFinalInvoiceJson = '';
var accordionsId = '';
var addChgAccordionsId = '';
var userProductList = '';
var userGstinsJson = '';
var productAdvolCessRateJson = '';
var productNonAdvolCessRateJson= '';

var gstinsInventoryJson = '';

$(document).ready(function (e) {	
	$('#previewInvoiceDiv').hide();
    var defaultInvoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
    callDefaultOnPageLoad(defaultInvoiceFor);
    loadGstinPerRole();
    loadAdditionalChargeList();
    //loadStateList();
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
    
    $(".loader").fadeOut("slow");
});

$("#reverseCharge").click(function () {
	 if ($(this).is(":checked")) {
		  $("#SupplierDoc").show();
	 }else{
		 $("#SupplierDoc").hide();
		 $("#supplierDocInvoiceNo").val('');
		 $("#supplierDocInvoiceDate").val('');
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

$('input[type=radio][name=custType]').change(function() {
    if (this.value == 'Registered') {           
        $("#consignee").show();
        $('#astrickGstin').show();
        if($("#supplier_gstin").val().length > 14){				
        	$("#supplier_gstin").addClass("input-correct").removeClass("input-error");		 
 		}else{
 			$("#supplier_gstin").addClass("input-error").removeClass("input-correct");		 
 		}            
    }
    else if (this.value == 'UnRegistered') {          
        $("#consignee").hide();
        $('#astrickGstin').hide();
        $("#supplier_gstin").val('').removeClass("input-correct").removeClass("input-error");
    }
});

$("#shipToBill").click(function() {
	if ($(this).is(":checked")) {
		var billingAddr = $("#billing_address").val();
		
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
	        	$("#shipTo_pincode").val('');
	        	$("#shipTo_city").val('');
	        	$("#shipTo_customer_name").val('');
	        	$("#shipTo_address").val('');
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

$("#service_name").on("change", function() {    	
	 var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
     var serviceId = $(this).val();
     var quantity = $("#quantity").val();
     var billMethod = $('select#calculation_on option:selected').val();
     
     if(serviceId){
	 	if(invoiceFor == 'Service'){
			 loadServiceById(serviceId,quantity,billMethod);
		}else{				
			 loadProductById(serviceId,quantity,billMethod);
		} 	      
     }  
  });

$("#quantity").keyup(function() {
    var $this = $(this);
    $this.val($this.val().replace(/[^\d.]/g, ''));        
});	

$("#rate").keyup(function() {
    var $this = $(this);
    $this.val($this.val().replace(/[^\d.]/g, ''));        
});

$("#amountToShow").on("keyup",function(){
	var $this = $(this);
    $this.val($this.val().replace(/[^\d.]/g, ''));  
	$("#amount").val($this.val());
});

$('#quantity').change(function(){
	$("#calculation_on option[value='Amount']").attr('selected', 'selected'); 
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
    	  $("#quantity").val(1);
    	  $("#quantity-show-hide").show();
    	  $("#amount-show-hide").show();
    	  $("#amountToShow").removeAttr('disabled');
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


$('#invoicePeriodFromDateInString').datepicker({
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
}).datepicker("setDate", new Date());

$('#invoicePeriodToDateInString').datepicker({
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
}).datepicker("setDate", new Date());     

 $('#purchase_date').datepicker({
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

function loadGstinPerRole(){
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
						$("#locationStorePkId").val(value.id);
					});	
					setResetSearchProductServiceInputFields('');
				}else{
					$("#locationDiv").show();
					$("#location").append('<option value="">Select</option>');
					$.each(json[0].gstinLocationSet,function(i,value) {
						$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
					});
					setResetSearchProductServiceInputFields('disabled');
				}
			}else{
				$("#gstnStateIdDiv").show();
				$("#gstnStateId").append('<option value="">Select</option>');
				$.each(json,function(i,value) {
					$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
				});
				setResetSearchProductServiceInputFields('disabled');
			}
			getGstinDetailsFromGstinNo();
         }
	});   
}
function loadAdditionalChargeList(){
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
			$("#additionalChargeName").append('<option value="">Select</option>');
			$.each(json,function(i,value) {					
				$("#additionalChargeName").append($('<option>').text(value.chargeName).attr('value',value.id+"{--}"+value.chargeName+"{--}"+value.chargeValue));
			});
			setCsrfToken(request.getResponseHeader('_csrf_token'));	
         }
	}); 
}

/*function loadStateList(){
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
			$("#placeOfSupply").append('<option value="">Select</option>');
			$.each(json,function(i,value) {					
				$("#placeOfSupply").append($('<option>').text(value.stateName).attr('value',value.id));
			});
			setCsrfToken(request.getResponseHeader('_csrf_token'));	
         }
	}); 
 }*/

function split(val) {
    return val.split(/,\s*/);
}

function callDefaultOnPageLoad(defaultInvoiceFor){	
	if(defaultInvoiceFor == 'Service'){
		//getServiceList();
		$("#callOnEditId").text("Add Services");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Service Name");
		$("#invoicePeriodDivShowHide").show();	
	}else{
		//getProductList();
		$("#callOnEditId").html("");
		$("#callOnEditId").text("Add Goods");
		$("#callOnEditId").append('<div class="acc_icon_expand"></div>'); 
		$("#service_name_label").text("Goods Name");
		$("#invoicePeriodDivShowHide").hide();
		
   	    $("#amountToShow").attr('disabled','disabled'); 
   	    $("#rate").removeAttr('disabled');
		$("#rate-show-hide").show();
        $("#quantity-show-hide").show();
        $("#unitOfMeasurement-show-hide").show();
    	$("#uom-show-hide").show();   
   	    $("#amount-show-hide").show();
	}
}

function getServiceList(){
    $.ajax({
		url : "getServicesList",
		type : "POST",
		/*contentType : "application/json",*/
		dataType : "json",
		async : false,
		data : { _csrf_token : $("#_csrf_token").val()},
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
		success:function(json,textStatus,request){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
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
			setCsrfToken(request.getResponseHeader('_csrf_token'));	
         }
	});	
}

function getProductList(){
   
  
    var defaultDocumentType = $("#selectedDocType").val();
	var urlToFetchProduct = '';
	if(defaultDocumentType == 'invoice' || defaultDocumentType == 'rcInvoice' || defaultDocumentType == 'eComInvoice'){ 
		urlToFetchProduct = 'invoiceProductList';
	}else if(defaultDocumentType == 'billOfSupply' || defaultDocumentType == 'eComBillOfSupply'){
		urlToFetchProduct = 'billOfSupplyProductList';
	}else if(defaultDocumentType == 'purchaseEntryInvoiceAndBillOfSupply'){
		urlToFetchProduct = 'purchaseEntryInvoiceAndBillOfSupplyProductList';
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
						
						userProductList = json;	
						setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			         },
			         error: function (data,status,er) {
			        	 
			        	 getInternalServerErrorPage();   
			        }
				});    
			}); 
	 }
}

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
			beforeSend: function(){
				$('.loader').show();
			},
			complete: function(){
				$('.loader').fadeOut("slow");
			},
			success : function(json,textStatus,request) {					
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}	
				setCsrfToken(request.getResponseHeader('_csrf_token'));			
				
				if(json != null && json.gstinLocationSet != null && json.gstinLocationSet != '' && json.gstinLocationSet != undefined){
					$.each(json.gstinLocationSet, function(i, value) {
						if(json.gstinLocationSet.length == 1){
							$("#locationDiv").hide();
							$("#location").empty();
							$.each(json.gstinLocationSet,function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
								$("#locationStore").val(value.gstinStore);
								$("#locationStorePkId").val(value.id);
							});
							setResetSearchProductServiceInputFields('');
						}else{
							$("#locationDiv").show();
							$("#location").empty();
							$("#location").append('<option value="">Select</option>');
							$.each(json.gstinLocationSet,function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence));
							});
							setResetSearchProductServiceInputFields('disabled');
						}
					});
					$("#purchaser_org_address1").val(json.gstinAddressMapping.address); 
					$("#purchaser_org_city").val(json.gstinAddressMapping.city);
					$("#purchaser_org_pinCode").val(json.gstinAddressMapping.pinCode);
					$("#purchaser_org_stateCode").val(json.state);
					$("#purchaser_org_state").val(json.gstinAddressMapping.state);	
				}		
			}
	    }); 
	}else{
		$("#location").empty();
	}    
}

function loadProductById(serviceId,quantity,billMethod){
	$.ajax({
		url : "getProductById",
		method : "POST",
		/*contentType : "application/json",*/
		data : { productId : serviceId, _csrf_token : $("#_csrf_token").val()  },
		dataType : "json",
		async : true,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
}

function loadServiceById(serviceId,quantity,billMethod){
 	$.ajax({
		url : "getManageServiceById",
		method : "POST",
		/*contentType : "application/json",*/
		dataType : "json",
		data : { serviceId : serviceId, _csrf_token : $("#_csrf_token").val() },
		async : true,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
 }

function split2(val) {
    return val.split(/,\s*/);
}

function extractLastRW(term) {
    return split2(term).pop();
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

function setResetSearchProductServiceInputFields(disable){
	if(disable == 'disabled'){
		//$("#search-service-autocomplete").attr('disabled', 'disabled');
		$("#search-product-autocomplete").attr('disabled', 'disabled');
		$("#show-select-gstin-location-msg").show();
	}else{
		//$("#search-service-autocomplete").removeAttr("disabled");
		$("#search-product-autocomplete").removeAttr("disabled");
		$("#show-select-gstin-location-msg").hide();
	}
}

function setLocationStorePkId(gstnNumber,gstnState,gstinLocationSetUniqueSequence,gstinLocationSetgstinLocation){
	if(userGstinsJson != ''){
		$.each(userGstinsJson,function(i,value){
			if(value.gstinNo == gstnNumber){
				if(value.gstinLocationSet.length == 1){
	  				$.each(value.gstinLocationSet,function(i,value2) {
	  					$("#locationStore").val(value2.gstinStore);
	  					$("#locationStorePkId").val(value2.id);
	  				});
	  			}
	  			else{   
					 $.each(value.gstinLocationSet,function(i,value3){
						 if(value3.uniqueSequence == gstinLocationSetUniqueSequence && value3.gstinLocation == gstinLocationSetgstinLocation){
								$("#locationStore").val(value3.gstinStore);
							 $("#locationStorePkId").val(value3.id); 
						 }
					});
	  			}
				
			} 
		});
	}
}

function changeServiceNameAsPerAutoComplete(selectedJson){
	var invoiceFor = $('input[name=invoiceFor]').filter(':checked').val(); 
    //var serviceId = $(this).val();
    var quantity = $("#quantity").val();
    var billMethod = $('select#calculation_on option:selected').val();
    setValueInSelectServiceName(selectedJson,invoiceFor);
    setValueInAddServiceProductFormFields(selectedJson,invoiceFor,quantity,billMethod);
    setValueInSelectCessNonAdvol(selectedJson.advolCess,selectedJson.nonAdvolCess,invoiceFor);
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
		 $("#uomtoShow").val(json.purchaseRate+" Rs per "+json.unitOfMeasurement);
		 
		 $("#rate").val(json.purchaseRate);
		 $("#hsnSacCode").val(json.hsnCode);
		 /*$("#ratetoShow").val(json.productRate);*/
		 $("#openingStockProduct").val(json.currentStock);
		 
		 if(quantity){
			 if(billMethod == 'Amount'){
			     var amount = quantity * json.purchaseRate;
			     $("#amount").val(amount);
			     $("#amountToShow").val(amount);
			}
		 }
		 $("#qtyLabel").text("  [ Current - "+json.currentStock+" ]");
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
		if(selectedJsonAdvolCess == value.cessRate){
			$('#cessAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate).attr('selected','selected')); 
		 }else{
			 $('#cessAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate));
		 }
		
	});
	
	$('#cessNonAdvolId').empty();
	$.each(cessNonAdvolJson,function(i,value) {
		if(selectedJsonNonAdvolCess == value.cessRate){
			/*$('#cessNonAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate).attr('selected','selected')); */
			$('#cessNonAdvolId').val(selectedJsonNonAdvolCess);
		 }else{
			 $('#cessNonAdvolId').append($('<option>').text(value.cessRate).attr('value',value.cessRate));
		 }
		
	});
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