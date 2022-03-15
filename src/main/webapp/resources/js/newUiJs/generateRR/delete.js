 function callDeleteInvoiceJson(){
	var invoiceId = $("#unqIncDes").val();
	var cnDnType = $('input[name=createDocType]').filter(':checked').val();
	var rrType = $('select#rrType option:selected').val();
	var iterationNo = $("#iterationNo").val();
	var serviceListArray = new Array();
	var jsonObject;
	var fetchField = '';
	if(rrType == 'salesReturn'){
		fetchField = '#actualSR_';
	}else if(rrType == 'quantityChange'){
		fetchField = '#actualQC_';
	}
	   
    var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	var rows_selected_count = 0;
	table_data.each(function(value,index){
		var oldQuantity = parseFloat($(fetchField+(index+1)).val());
	
		var rate = parseFloat($("#common_rate_"+(index+1)).val());
		var taxableValue = parseFloat(rate * oldQuantity);
		var taxRate = parseFloat($("#common_sgstPercentage_"+(index+1)).val()) + parseFloat($("#common_ugstPercentage_"+(index+1)).val()) + parseFloat($("#common_cgstPercentage_"+(index+1)).val()) + parseFloat($("#common_igstPercentage_"+(index+1)).val());
		var valueAfterTax = parseFloat(taxableValue) + parseFloat((taxableValue * taxRate) / (100));
		
		jsonObject = new Object();
	    jsonObject.serviceId = parseInt($("#common_service_id_"+(index+1)).val());
	    jsonObject.quantity = parseFloat($(fetchField+(index+1)).val());
	    jsonObject.taxableValue = taxableValue;
	    jsonObject.cess = 0;
	    jsonObject.rate = taxRate;
	    jsonObject.valueAfterTax = valueAfterTax;
	    jsonObject.reason = $('select#rrType option:selected').val();
	    jsonObject.cnDnType = $('input[name=createDocType]').filter(':checked').val();
	    jsonObject.diffPercent = $("#common_diffPercent_"+(index+1)).val();
	    jsonObject.regime = $("#common_regime_"+(index+1)).val();
	   serviceListArray.push(jsonObject);
	});

	 var inputData = {
			 	"id" : invoiceId,
			 	"cnDnList" : JSON.parse(JSON.stringify(serviceListArray)),
			 	"cnDnType" : cnDnType,
			 	"createDocType" : rrType,
			 	"rrTypeForCreation" : rrType,
			 	"createDocType" : cnDnType,
			 	"iterationNo" : iterationNo,
			 	"footerNote" : ""
        };
	
	return inputData;
}




function callDeleteInvoiceFromRR(inputData){
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
						url : "deleteInvoicefromRR",
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
										if(json.status === 'SUCCESS'){
											gotoBackHistoryListPage();
								
										}
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

function gotoDeleteRRHistoryListPage(){  
    window.location.href = './showrevisedandreturndetails';
}
