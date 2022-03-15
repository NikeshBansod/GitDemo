$(document).on('keyup',".returnedSR", function(e){
	 var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
});

function callSalesReturn(){
	var counter = 1;
	$.each(invoiceJson.serviceList,function(i,value){
		openingstockCheckboxTable.row.add($(
 			'<tr id="tr_id_'+counter+'">'
	 			+'<td><input type="hidden" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'"></td>'
	 			+'<td>'+counter+'<input type="hidden" id="actualSR_'+counter+'" value="'+value.quantity+'"></td>'
			 	+'<td style="width:20px;">'+value.serviceIdInString+'</td>'
			 	+'<td>'+value.quantity+"("+value.unitOfMeasurement+')</td>'
			 	+'<td><input type="text" class="returnedSR returnedSR_'+counter+'" id="returnedSR_'+counter+'" maxlength="8" disabled></td>'
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
		counter++;
	});
}

function checkValidationsForSalesReturn(){
	var isError = false;
	var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	if(rows_selected.length == 0){
		bootbox.alert('Please select atleast 1 product');
		isError = true;
	}else{

		var table_data = openingstockCheckboxTable.rows().data();
		table_data.each(function(value,index){
			if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
				var currentQty = parseFloat($("#returnedSR_"+(index+1)).val());
				var oldQty = parseFloat($("#actualSR_"+(index+1)).val());
				
				if( typeof currentQty == 'number' && currentQty != null  && !isNaN(currentQty) && typeof oldQty == 'number' && oldQty != null && !isNaN(oldQty)){
					if(currentQty == 0){
						bootbox.alert('Returned Quantity cannot be zero');
						isError = true;
					}else if(currentQty > oldQty){
						bootbox.alert('Returned Quantity cannot be greater than Original Quantity');
						isError = true;
					}
				}else{
					bootbox.alert('Please enter correct Returned Quantity');
					isError = true;
				}
			}
		});
		
	
	}
	return isError;
}

function logicFilingSalesReturn(beforeAfter){
	
	var totalOldQty = 0;
	var totalCurrentQty = 0;
	var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	var table_row_count=0;
	var rows_selected_count = 0;
	table_data.each(function(value,index){
		table_row_count++;
		if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
			rows_selected_count++;
			var currentQty = parseFloat($("#returnedSR_"+(index+1)).val());
			var oldQty = parseFloat($("#actualSR_"+(index+1)).val());
			
			totalOldQty = parseFloat(totalOldQty) + parseFloat(oldQty);
			totalCurrentQty = parseFloat(totalCurrentQty) + parseFloat(currentQty);
			
		}
	});
	
	if(beforeAfter == "before"){
	    if(totalOldQty > totalCurrentQty){
		//create CN or Revised Invoice
			showHideRadioButtons("show","hide","show","hide","hide","creditNote","show");
		}
		
		if(totalOldQty == totalCurrentQty){
		//create CN or Delete invoice
			if(table_row_count == rows_selected_count){
				showHideRadioButtons("show","hide","hide","show","hide","creditNote","show");
			}else{
				showHideRadioButtons("show","hide","hide","hide","hide","creditNote","show");
			}
			
		}
	}else{
		if(totalOldQty > totalCurrentQty){
			//create CN or New Invoice
				showHideRadioButtons("show","hide","hide","hide","show","creditNote","show");
		}
			
		if(totalOldQty == totalCurrentQty){
			//create CN
			if(table_row_count == rows_selected){
				showHideRadioButtons("show","hide","hide","show","hide","creditNote","show");
			}else{
				showHideRadioButtons("show","hide","hide","hide","hide","creditNote","show");
			}
		}
	}
	
}

function cnDnSalesReturnJson(){
   var jsonObject;
   var serviceListArray = new Array();
   
    var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	var rows_selected_count = 0;
	table_data.each(function(value,index){
		if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
			rows_selected_count++;
			var currentQty = parseFloat($("#returnedSR_"+(index+1)).val());
			var oldQty = parseFloat($("#actualSR_"+(index+1)).val());
			var rate = parseFloat($("#common_rate_"+(index+1)).val());
			var taxableValue = parseFloat(rate * currentQty);
			var taxRate = parseFloat($("#common_sgstPercentage_"+(index+1)).val()) + parseFloat($("#common_ugstPercentage_"+(index+1)).val()) + parseFloat($("#common_cgstPercentage_"+(index+1)).val()) + parseFloat($("#common_igstPercentage_"+(index+1)).val());
		    var valueAfterTax = parseFloat(taxableValue) + parseFloat((taxableValue * taxRate) / (100));
			 jsonObject = new Object();
		     jsonObject.serviceId = parseInt($("#common_service_id_"+(index+1)).val());
		     jsonObject.quantity = currentQty;
		     jsonObject.taxableValue = taxableValue;
		     jsonObject.cess = 0;
		     jsonObject.rate = taxRate;
		     jsonObject.valueAfterTax = valueAfterTax;
		     jsonObject.reason = $('select#rrType option:selected').val();
		     jsonObject.cnDnType = $('input[name=createDocType]').filter(':checked').val();
		     jsonObject.diffPercent = $("#common_diffPercent_"+(index+1)).val();
		     jsonObject.regime = $("#common_regime_"+(index+1)).val();
		   serviceListArray.push(jsonObject);
		}
	});
	return serviceListArray;
}

function revisedSalesReturnJson(){
	   var jsonObject;
	   var serviceListArray = new Array();
	   
	    var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
		var table_data = openingstockCheckboxTable.rows().data();
		table_data.each(function(value,index){
			 var revisedQty = parseFloat($("#actualSR_"+(index+1)).val()); 
			 jsonObject = new Object();
		     jsonObject.serviceId = parseInt($("#common_item_service_id_"+(index+1)).val());
		     jsonObject.billingFor = $("#common_billingFor_"+(index+1)).val();
		     jsonObject.id = $("#common_service_id_"+(index+1)).val();
		     
			
			if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
				var currentQty = parseFloat($("#returnedSR_"+(index+1)).val());
				var oldQty = parseFloat($("#actualSR_"+(index+1)).val());
			    revisedQty = parseFloat(oldQty - currentQty);   
			}
			jsonObject.quantity = revisedQty;
			serviceListArray.push(jsonObject);
		});
		return serviceListArray;
}

$("#openingstockInventoryTab").on("change",".returnedSR", function () {
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
          	 $(".returnedSR_"+rowNo).val(inputValue);
  	}else{
  		$(this).val('').focus();
  		
  	}
});
