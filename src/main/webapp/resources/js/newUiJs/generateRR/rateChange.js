function callRateChange(){
	var counter = 1;
	$.each(invoiceJson.serviceList,function(i,value){
		var invoiceTaxRate = 0;
		if(value.categoryType == 'CATEGORY_WITH_IGST'){
			invoiceTaxRate = value.igstPercentage;
		}else if(value.categoryType == 'CATEGORY_WITH_SGST_CSGT' || value.categoryType == 'CATEGORY_WITH_UGST_CGST'){
			invoiceTaxRate = (value.sgstPercentage + value.ugstPercentage + value.cgstPercentage);
		}
		openingstockCheckboxTable.row.add($(
 			'<tr id="tr_id_'+counter+'">'
	 			+'<td><input type="hidden" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'"></td>'
	 			+'<td>'+counter+'<input type="hidden" class="actualRC" id="actualRC_'+counter+'" value="'+invoiceTaxRate+'"></td>'
			 	+'<td style="width:20px;">'+value.serviceIdInString+'</td>'
			 	+'<td>'+invoiceTaxRate+'%</td>'
			 	+'<td><select class="revisedRC revisedRC_'+counter+'" id="revisedRC_'+counter+'" style="width:70%" disabled></select></td>'
			 	+'<td>'
		 			+'<input type="hidden" id="common_service_id_'+counter+'" value="'+value.id+'">'
			 		+'<input type="hidden" id="common_quantity_'+counter+'" value="'+value.quantity+'">'
			 		+'<input type="hidden" id="common_rate_'+counter+'" value="'+value.rate+'">'
			 		+'<input type="hidden" id="common_previousAmount_'+counter+'" value="'+value.previousAmount+'">'
			 		+'<input type="hidden" id="common_offerAmount_'+counter+'" value="'+value.offerAmount+'">'
			 		+'<input type="hidden" id="common_taxableValue_'+counter+'" value="'+value.amount+'">'
			 		+'<input type="hidden" id="common_cess_'+counter+'" value="'+value.cess+'">'
			 		+'<input type="hidden" id="common_diffPercent_'+counter+'" value="'+value.diffPercent+'">'
			 		+'<input type="hidden" id="common_regime_'+counter+'" value="postGst">'
			 		+'<input type="hidden" id="common_sgstPercentage_'+counter+'" value="'+value.sgstPercentage+'">'
			 		+'<input type="hidden" id="common_ugstPercentage_'+counter+'" value="'+value.ugstPercentage+'">'
			 		+'<input type="hidden" id="common_cgstPercentage_'+counter+'" value="'+value.cgstPercentage+'">'
			 		+'<input type="hidden" id="common_igstPercentage_'+counter+'" value="'+value.igstPercentage+'">'
			 		+'<input type="hidden" id="common_billingFor_'+counter+'" value="'+value.billingFor+'">'
			 		+'<input type="hidden" id="common_item_service_id_'+counter+'" value="'+value.serviceId+'">'
			 		+'<input type="hidden" id="common_discountTypeInItem_'+counter+'" value="'+value.discountTypeInItem+'">'
		 			
			 	+'</td>'
		 	+'</tr>'					 	
	 	)).draw();
		setTaxRate("revisedRC_"+counter);
		counter++;
	});
}

function checkValidationsForRateChange(){
	var isError = false;
	var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	if(rows_selected.length == 0){
		bootbox.alert('Please select atleast 1 product');
		isError = true;
	}else{
		var documentType = $("#invDocType").val();
		var table_data = openingstockCheckboxTable.rows().data();
		table_data.each(function(value,index){
			if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
				var currentRateChange = $('#revisedRC_'+(index+1)).val();
				var oldRateChange = $("#actualRC_"+(index+1)).val();
				
				if( currentRateChange == '' || currentRateChange == null){
					bootbox.alert('Please select correct Revised Rate Change');
					isError = true;
				}else if(documentType == 'invoice' && currentRateChange == 0){
					bootbox.alert('Revised Rate Change cannot be 0');
					isError = true;
				}else if(parseFloat(currentRateChange) == parseFloat(oldRateChange)){
					bootbox.alert('Revised Rate Change cannot be same as Original Rate Change');
					isError = true;
				}
			}
		});
	
	}
	return isError;
}

function setTaxRate(fieldId){
	if(taxRateJson == ''){
		getProductTaxRate();
	}else{
		$('#'+fieldId).empty();
		$('#'+fieldId).append('<option value="">Select</option>');
		 $.each(taxRateJson, function(i, value) {
			$('#'+fieldId).append($('<option>').text(value.taxRate).attr('value', value.taxRate));    
		 });
	}
	
}

function logicFilingRateChange(beforeAfter){
	
	if(beforeAfter == "before"){
		//create Revised Invoice
		showHideRadioButtons("hide","hide","show","hide","hide","revisedInvoice","hide");
	}else{
		//create New Invoice
		showHideRadioButtons("hide","hide","hide","hide","show","newInvoice","hide");
	}
	
}

function revisedRateChangeJson(){
   var jsonObject;
   var serviceListArray = new Array();
   
    var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	table_data.each(function(value,index){
	
		 var currentRateChange = parseFloat($('#actualRC_'+(index+1)).val());
		 jsonObject = new Object();
		 var isNew = "N";
	     jsonObject.serviceId = parseInt($("#common_item_service_id_"+(index+1)).val());
	     jsonObject.billingFor = $("#common_billingFor_"+(index+1)).val();
	     jsonObject.id = $("#common_service_id_"+(index+1)).val();
		if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
			currentRateChange = parseFloat($("#revisedRC_"+(index+1)).val());
		    isNew = "Y";
		}
		jsonObject.isNew = isNew;
		jsonObject.rate = currentRateChange;
		serviceListArray.push(jsonObject);
	});
		return serviceListArray;
}

$("#openingstockInventoryTab").on("change",".revisedRC", function () {
	$(this).val(roundToTwoDecimal(this.value));
	if(!isNaN($(this).val())){
		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
         var id=parents.parent().prev('tr').attr('id');
    	  if(id == undefined){                       // for desktop view ,this condition works
    		  var $row = $(this).closest("tr");
              var id = $row.attr('id');
    	  }
    	  
          	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
          	var inputValue = $(this).val();
          	 $(".revisedRC_"+rowNo).val(inputValue);
  	}else{
  		$(this).val('').focus();
  		
  	}
});