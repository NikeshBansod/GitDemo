$(document).on('keyup',".revisedDC", function(e){
	 var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
});

$(document).on('change','#openingstockInventoryTab tbody td .revisedDC',function(){

    var row = $(this).closest('tr');
    var isRowSelected = false;
    var $checkbox = row.find('td:first-child input[type="checkbox"]')
    if($checkbox.is(':checked')){
       isRowSelected = true;
    }
    var currentDiscount = parseFloat(row.find('.revisedDC').val());
    var actualDiscountType = row.find('.actualDiscountType').val();
    if(actualDiscountType == 'Percentage' && isRowSelected){
    	if(currentDiscount > 100){
    		bootbox.alert("Revised Discount cannot be greater than 100%");
    		row.find('.revisedDC').val('');
    	}
    }
});

function callDiscountChange(){
	var counter = 1;
	
	var discountChangeListArray = generateDiscountChangeArray();
	$.each(invoiceJson.serviceList,function(i,value){
		$.each(discountChangeListArray, function(k, v) {
			if(k == value.id){
				var postDiscount = 'INR';
				var discountToShow = '';
				if(value.discountTypeInItem == 'Percentage'){
					postDiscount = '%';
					//discountToShow = ((parseFloat(value.offerAmount) * parseFloat(100)) / parseFloat(value.previousAmount) );
					discountToShow = v;
				}else{
					//discountToShow = value.offerAmount;
					discountToShow = v;
				}
				openingstockCheckboxTable.row.add($(
		 			'<tr id="tr_id_'+counter+'">'
			 			+'<td><input type="hidden" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'"></td>'
			 			+'<td>'+counter+'<input type="hidden" id="actualDC_'+counter+'" value="'+discountToShow+'"></td>'
					 	+'<td style="width:20px;">'+value.serviceIdInString+'<input type="hidden" class="actualDiscountType" id="actualDiscountType_'+counter+'" value="'+value.discountTypeInItem+'"></td>'
					 	+'<td>'+discountToShow+"("+postDiscount+')</td>'
					 	+'<td><input type="text" class="revisedDC revisedDC_'+counter+'" id="revisedDC_'+counter+'" disabled></td>'
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
			}
			
		});
	});
	
}


function checkValidationsForDiscountChange(){
	var isError = false;
	var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	if(rows_selected.length == 0){
		bootbox.alert('Please select atleast 1 product');
		isError = true;
	}else{
		var table_data = openingstockCheckboxTable.rows().data();
		table_data.each(function(value,index){
			if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
				var currentDiscount = parseFloat($("#revisedDC_"+(index+1)).val());
				var oldDiscount = parseFloat($("#actualDC_"+(index+1)).val());
				var currentDiscountType = $("#actualDiscountType_"+(index+1)).val();
				var currentDiscountInValue = 0;
				var currentTaxableValue = parseFloat($("#common_previousAmount_"+(index+1)).val());//parseFloat($("#common_taxableValue_"+(index+1)).val());
				if( typeof currentDiscount == 'number' && currentDiscount != null  && !isNaN(currentDiscount) && typeof oldDiscount == 'number' && oldDiscount != null && !isNaN(oldDiscount)){
					if(currentDiscount == oldDiscount){
						bootbox.alert('Revised Discount cannot be same as Original Discount');
						isError = true;
					}
					if(currentDiscountType == 'Percentage'){
						if(currentDiscount > 100){
							bootbox.alert("Revised Discount cannot be greater than or equal to 100%");
							isError = true;
						}
					}
					if(currentDiscountType == 'Percentage'){
						 currentDiscountInValue = ((parseFloat(currentDiscount) * parseFloat(currentTaxableValue))/(parseFloat(100)));
							
					}else{
						currentDiscountInValue = currentDiscount;	
					}
					
					if(parseFloat(currentDiscountInValue) > parseFloat(currentTaxableValue)){
						bootbox.alert("Discount Value cannot be greater than "+currentTaxableValue);
						isError = true;
					}
				}else{
					bootbox.alert('Please enter correct Revised Discount');
					isError = true;
				}
			}
		});
		
	
	}
	return isError;
}

function logicFilingDiscountChange(beforeAfter){
	var totalOldDiscount = 0;
	var totalCurrentDiscount = 0;
	var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	var totalTaxableValue = 0;
	var totalSelectedDiscountValue = 0;
	table_data.each(function(value,index){
		totalTaxableValue = parseFloat(totalTaxableValue) + parseFloat($("#common_taxableValue_"+(index+1)).val());
		if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
			var currentDiscount = parseFloat($("#revisedDC_"+(index+1)).val());
			var oldDiscount = parseFloat($("#actualDC_"+(index+1)).val());
			var currentDiscountType = $("#actualDiscountType_"+(index+1)).val();
			var selectedDiscountValue = parseFloat($("#revisedDC_"+(index+1)).val());
			
			totalOldDiscount = parseFloat(totalOldDiscount) + parseFloat(oldDiscount);
			totalCurrentDiscount = parseFloat(totalCurrentDiscount) + parseFloat(currentDiscount);
			
			if(currentDiscountType == "Percentage"){
				selectedDiscountValue = (((parseFloat(currentDiscount) * parseFloat($("#common_taxableValue_"+(index+1)).val())))/(parseFloat(100))); 
			}
			totalSelectedDiscountValue = parseFloat(totalSelectedDiscountValue) + parseFloat(selectedDiscountValue);
			
		}
	});
	
	if(beforeAfter == "before"){
		if(totalTaxableValue == totalSelectedDiscountValue){
			//create cn
			showHideRadioButtons("show","hide","hide","hide","hide","creditNote","show");
		}else if(totalOldDiscount < totalCurrentDiscount){
			//create CN or Revised Inv
			showHideRadioButtons("show","hide","show","hide","hide","creditNote","show");
		}else if(totalOldDiscount > totalCurrentDiscount){
			//create DN or Revised Inv
			showHideRadioButtons("hide","show","show","hide","hide","debitNote","show");
		}
	}else{
		if(totalTaxableValue == totalSelectedDiscountValue){
			//create cn
			showHideRadioButtons("show","hide","hide","hide","hide","creditNote","show");
		}else if(totalOldDiscount < totalCurrentDiscount){
			//create CN or New Inv
			showHideRadioButtons("show","hide","hide","hide","show","creditNote","show");
		}else if(totalOldDiscount > totalCurrentDiscount){
			//create DN or New Inv
			showHideRadioButtons("hide","show","hide","hide","show","debitNote","show");
		}
	}
	
}

function cnDnDiscountChangeJson(){
	 var jsonObject;
	   var serviceListArray = new Array();
	   
	    var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
		var table_data = openingstockCheckboxTable.rows().data();
		var rows_selected_count = 0;
		table_data.each(function(value,index){
			if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
				
				var currentDiscount = parseFloat($("#revisedDC_"+(index+1)).val());
				var oldDiscount = parseFloat($("#actualDC_"+(index+1)).val());
				
				
				var rate = parseFloat($("#common_rate_"+(index+1)).val());
				var taxableValue = currentDiscount;
				var taxRate = parseFloat($("#common_sgstPercentage_"+(index+1)).val()) + parseFloat($("#common_ugstPercentage_"+(index+1)).val()) + parseFloat($("#common_cgstPercentage_"+(index+1)).val()) + parseFloat($("#common_igstPercentage_"+(index+1)).val());
			    var valueAfterTax = parseFloat(taxableValue) + parseFloat((taxableValue * taxRate) / (100))
				 jsonObject = new Object();
			     jsonObject.serviceId = parseInt($("#common_service_id_"+(index+1)).val());
			     jsonObject.quantity = $("#common_quantity_"+(index+1)).val();
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

function revisedDiscountChangeJson(){
   var jsonObject;
   var serviceListArray = new Array();
   
    var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
	var table_data = openingstockCheckboxTable.rows().data();
	table_data.each(function(value,index){
		 var revisedDiscount = parseFloat($("#actualDC_"+(index+1)).val());
		 jsonObject = new Object();
		 var isNew = "N";
	     jsonObject.serviceId = parseInt($("#common_item_service_id_"+(index+1)).val());
	     jsonObject.billingFor = $("#common_billingFor_"+(index+1)).val();
	     jsonObject.id = $("#common_service_id_"+(index+1)).val();
	     jsonObject.discountTypeInItem = $("#common_discountTypeInItem_"+(index+1)).val();
		if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){
		    
		    revisedDiscount = parseFloat($("#revisedDC_"+(index+1)).val());
			//var oldDiscount = parseFloat($("#actualDC_"+(index+1)).val());
		    isNew = "Y";
		}
		jsonObject.isNew = isNew;
		jsonObject.offerAmount = revisedDiscount;
		serviceListArray.push(jsonObject);
	});
	return serviceListArray;
}

$("#openingstockInventoryTab").on("change",".revisedDC", function () {
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
          	 $(".revisedDC_"+rowNo).val(inputValue);
  	}else{
  		$(this).val('').focus();
  		
  	}
});

function generateDiscountChangeArray(){
	var discountChangeListArray = {};
	
	$.each(invoiceJson.serviceList,function(i1,value1){
		var discount = 0;
		if(value1.discountTypeInItem == 'Percentage'){
			discount = ((parseFloat(value1.offerAmount) * parseFloat(100)) / parseFloat(value1.previousAmount) );
		}else{
			discount = value1.offerAmount;
		}
		discountChangeListArray[value1.id] = parseFloat(discount);	
	});

	$.each(invoiceJson.serviceList,function(i1,value1){
		if(invoiceJson.cnDnList.length > 0){
			$.each(invoiceJson.cnDnList,function(i2,value) {
				if(value1.id == value.serviceId){
					var discountT = 0;
					if(value1.discountTypeInItem == 'Percentage'){
						discountT = ((parseFloat(value.offerAmount) * parseFloat(100)) / parseFloat(value1.previousAmount) );
					}else{
						discountT = value.offerAmount;
					} 
			 	 
					if(value.cnDnType == "creditNote"){
						discountChangeListArray[value.serviceId] = parseFloat(discountChangeListArray[value.serviceId]) + parseFloat(discountT);
					}else{
						discountChangeListArray[value.serviceId] = parseFloat(discountChangeListArray[value.serviceId]) - parseFloat(discountT);
					}
					 
				}
				  
			});
		}
		
	});
	
	return discountChangeListArray;
}
