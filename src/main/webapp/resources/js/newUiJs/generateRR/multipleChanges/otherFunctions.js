$(document).ready(function() {  
	$("#selectCountry").on("change", function() {
		 
	      if($(this).val()=="Other"){
	    	  $("#typeOfExport").show();
	    	  $("#shipTo-pincode-show-hide").hide();
	    	  $("#shipTo-city-show-hide").hide();
	    	  $("#selectState-show-hide").hide();
	    	 
	      }else{
	    	  $("#typeOfExport").hide();
	    	  $("#shipTo-pincode-show-hide").show();
	    	  $("#shipTo-city-show-hide").show();
	      }
	});
	
	/*$("#poDetails_poNo").on("change", function() {
		//$("#poDetails_poValidToDate").val($("select#poDetails_poNo option:selected").val());
		
		var poDetail = $("select#poDetails_poNo option:selected").val();
		var	values = poDetail.split('{--}');
		var one = values[0];
		var two = values[1];
		$("#poDetails_poValidToDate").val(one);
		$("#po_id").val(two);
	});*/
	
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
	
	$('#selectState').change(function(){
		var stateName = $("#selectState :selected").text();
		//alert("onchange deliveryPlace : stateName :"+stateName);
		var stateRespone = getStateCode(stateName); 
		var values = stateRespone.split('{--}');
		$("#shipTo_stateCode").val(values[0]);
		$("#shipTo_stateCodeId").val(values[1]);
		$("#posStateName").val(stateName);
		$("#posStateCode").val(values[0]);
	});
	
	$("#gstnStateId").on("change", function() {
		var gstnWithStateInString = $("#gstnStateId option:selected").text();
		var gstinNo = (gstnWithStateInString.split('[')[0]).trim();
		setResetSearchProductServiceInputFields('disabled');
		$("#locationStorePkId").val('');
		 $.ajax({
				url : "getGstinDetailsFromGstinNo",
				method : "POST",
				/*contentType : "application/json",*/
				dataType : "json",
				data : { gstinNo : gstinNo, _csrf_token : $("#_csrf_token").val() },
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
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					
					$.each(json.gstinLocationSet, function(i, value) {
						if(json.gstinLocationSet.length == 1){
							$("#locationDiv").hide();
							$("#location").empty();
							$.each(json.gstinLocationSet,function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
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
						}
					});
					
				},
				error: function (data,status,er) {
					 
					 getInternalServerErrorPage();   
				}
	        }); 
	});
	
});

function getPoDetails(id){
	//alert("PO : "+id);
	$.ajax({
		url : "getPoDetailsByPoCustomerName",
		method : "POST",
		/*contentType : "application/json",*/
		dataType : "json",
		data : { custId : id, _csrf_token : $("#_csrf_token").val()},
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
			
			$('#poDetails_poNo').empty();
			$('#poDetails_poNo').append('<option value="">Select</option>');
			$.each(json,function(i,value) {
				
				$('#poDetails_poNo').append($('<option>').text(value.poNo).attr('value',value.poValidToDateTemp+"{--}"+value.id));
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
    }); 
	
}

function getStateCode(stateName){
	var response = "";
	if(stateListXJson == ''){
		loadStateListJson();
	}
	
	$.each(stateListXJson,function(i,value) {
		if(value.stateName == stateName){
			response = value.stateCode+"{--}"+value.stateId+"{--}"+value.id;
		}
	}); 
	return response;
}

/*$(document).ready(function() {
});*/

function autoPopulateStateList(stateId){
	//alert("stateId : "+stateId);
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
			  $('#selectState').empty();
			  $("#pos").val("");
			  $.each(json, function(i, value) {
				   
				 if(stateId == value.id){
					  $('#selectState').append($('<option>').text(value.stateName).attr('value', value.stateId).attr('selected','selected')); 
					  $("#pos").val(value.stateName);
				 }else{
					 $('#selectState').append($('<option>').text(value.stateName).attr('value', value.stateId));
				 }
				 
				
					   
			   });
			  validateSelect("selectState","selectState-csv-id");  
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		  },
		  error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
		  }
	});
	
}

/*$(document).ready(function(){
	$("#customer_contactNo").blur(function(){
	    var custContact = $("#customer_contactNo").val();
	    if(custContact.length == 10){
	    var data = { "custContact" : custContact }; 	
	    
		   $.ajax({
				url : "checkIfCustomerContactNoExists",
				type: 'POST',
				dataType : "json",
				data : data,
				success : function(json) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}
					if(json.isAvailable == 'Yes'){
						 $("#customer_contactNo").addClass("input-correct").removeClass("input-error");
						 //$("#contact-no-req").text('Customer Contact  - '+custContact+' already Exists for your Organization');
						 $("#contact-no-req").hide();
						 custContactExists = true;
						 //autopopulate customer
						 $("#customer_name").val(json.custName);
						 $("#customer_place").val(json.custAddress1+","+json.custAddress2+","+json.custCity);//mandatory
						 $("#customer_country").val(json.custState);//mandatory
						 $("#customer_id").val(json.id);//mandatory
						 $("#customer_custAddress1").val(json.custAddress1);//optional
						 $("#customer_custAddress2").val(json.custAddress2);//optional
						 $("#customer_custCity").val(json.custCity);//optional
						 $("#customer_custState").val(json.custState);//optional
						 $("#customer_custType").val(json.custType);
						 var stateRespone = getStateCode(json.custState);
						 var values = stateRespone.split('{--}');
						 var stateCode = values[0];
						 var stateCodeId = values[1];
						 var stateId = values[2];
						 $("#customer_custStateCode").val(stateCode);
						 $("#customer_custStateCodeId").val(stateCodeId);
						 $("#customer_custStateId").val(stateId);
						 getPoDetails(json.id);
						 autoPopulateStateList(stateId);
					}else{
						 $("#customer_contactNo").addClass("input-error").removeClass("input-correct");
						 $("#contact-no-req").show();
						 custContactExists = false;
						 //give alert to customer to redirect to add customer page
						 
						 if (confirm("No Customer is present with contact number : "+custContact+". Click OK to add New Customer Or Click cancel to enter other contact number.")) {
							 window.location = 'customerDetails';
						 } else {
							 $("#customer_contactNo").val("");
							 //clear fields - start
							 $("#customer_name").val("");
							 $("#customer_place").val("");
							 $("#customer_country").val("");
							 $("#customer_id").val("");
							 $("#customer_custAddress1").val("");
							 $("#customer_custAddress2").val("");
							 $("#customer_custCity").val("");
							 $("#customer_custState").val("");
							 $("#customer_custType").val("");
							 $("#customer_custStateCode").val("");
							 $("#customer_custStateCodeId").val("");
							 $('#poDetails_poNo').empty();//clear po field
							 $('#selectState').empty();//clear State Of Delivery
							 loadStateList();
							 //end
							 $("#customer_contactNo").focus();
						 }
					}
				}
		    });
	    }
	});
});*/


$("#customer_name").autocomplete({
    source: function (request, response) {
        $.getJSON("getCustomerDetailsForAutoCompleteList", {
            term: extractLast(request.term),
            documentType : $("#selectedDocType").val()
        }, function( data, status, xhr ) {
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
			response(data);
        });
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}

        
    },
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#customer_name").val();
    	//alert("Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	if(zipToShow.length >= 3){
	        		 $("#customer_name").addClass("input-error").removeClass("input-correct");
	        		 /*$("#empty-message").text("No results found for selected value : "+zipToShow);*/
	        		 $("#empty-message").show();
	                 $("#customer_name").val("");
	                 loadStateList();
	        	}
	        	
	        } else {
	            $("#empty-message").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var contactNo = (value.split('] - ')[0]).replace("[","").trim();
        var custName = (value.split('] - ')[1]).trim(); 
        $.ajax({
			url : "getCustomerDetailByCustNameAndContactNo",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
			data : { custName : custName, contactNo : contactNo },
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
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				 $("#customer_contactNo").val(contactNo);
				 $("#customer_place").val(json.custAddress1+","+json.custCity);//mandatory
				 $("#customer_country").val(json.custState);//mandatory
				 $("#customer_id").val(json.id);//mandatory
				 $("#customer_custAddress1").val(json.custAddress1);//optional
				 //$("#customer_custAddress2").val(json.custAddress2);//optional
				 $("#customer_custCity").val(json.custCity);//optional
				 $("#customer_custState").val(json.custState);//optional
				 $("#customer_custType").val(json.custType);
				 var stateRespone = getStateCode(json.custState);
				 var values = stateRespone.split('{--}');
				 var stateCode = values[0];
				 var stateCodeId = values[1];
				 var stateId = values[2];
				 $("#customer_custStateCode").val(stateCode);
				 $("#customer_custStateCodeId").val(stateCodeId);
				 $("#customer_custStateId").val(stateId);
				 $("#customer_custGstId").val(json.custGstId);
				 $("#customer_custEmail").val(json.custEmail);
				 $("#posStateName").val(json.custState);
				 $("#posStateCode").val(stateCode);
				 //getPoDetails(json.id);
				 autoPopulateStateList(stateId);
				 //
			}
        }); 
    }
});

/*$(document).ready(function() {

});*/

