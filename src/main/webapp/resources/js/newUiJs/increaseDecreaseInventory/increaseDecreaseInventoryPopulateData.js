var increaseInventoryCheckboxTable; 
var gstinJson;
var inventoryType = $('#inventoryType').val();
var inventoryTypePage = $('#inventoryTypePage').val();
var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var blankMsg="This field is required";
var urlforAction = '';

$(document).ready(function () {	
	$("#search-product-autocomplete").attr("disabled", true);
	$("#quantity").attr("disabled", true);
	$("#stockValue").attr("disabled", true);
	
	if(inventoryType==="InventoryIncrease")
			{
				$("#decreaseInventoryDiv").fadeOut();
				
				
			
			}
		else if(inventoryType==="InventoryDecrease")
			{
				$("#increaseInventoryDiv").fadeOut();
			}
	increaseInventoryCheckboxTable = $('#increaseInventoryTab').DataTable({
		   columnDefs: [ {
		        orderable: false,
		        checkboxes: {
		            'selectRow': true
		         },
		        targets:   0
		    } ],
		    select: {
		        style:    'multi',
		        selector: 'td:first-child'
		    },
		    rowReorder: {
		        selector: 'td:nth-child(2)'
		    },
		    responsive: true
		   
		});
	
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#increaseInventoryTab").DataTable().rows().remove();
	}

	$('#showProductGrid').hide();
	if(inventoryType == 'InventoryIncrease' ){
		urlforAction='getinventoryreasonsforincrease';
	}else{
		urlforAction='getinventoryreasonsfordecrease';
	}
	loadGstinField();
	loadReasonField(urlforAction);

	$("#transactionDate").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
        onSelect: function(selected) {
        	$('#show').prop('disabled', false);
          $("#transactionDate").addClass("input-correct").removeClass("input-error");
          $("#transactionDate-csv-id").hide();
          $('#showProductGrid').hide();
  		$("#increaseInventoryTab").DataTable().rows().remove();
        }
    });
	
	$('.loader').fadeOut("slow");
	$('#show').prop('disabled', false);	
	$('#Save').prop('disabled', false);
});

$('#gstin').on("change", function() {
	$('#show').prop('disabled', false);	
	$('#reason').prop('selectedIndex','');
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#increaseInventoryTab").DataTable().rows().remove();
	}
	var gstinSelectedId = $('#gstin').val();	
	if(gstinSelectedId != ''){
		$('#gstin').addClass("input-correct").removeClass("input-error");
		$('#gstin-csv-id').hide();
	    //loadLocationField(gstinSelectedId);
		$("#location").empty();
		$("#location").append('<option value="">Select</option>');
		$.each(gstinJson,function(i,value) {
			if(value.id == gstinSelectedId ){
				$.each(value.gstinLocationSet,function(i2,value2) {
					/*$("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));*/
					$("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.uniqueSequence));
				});				
			}
		});			
	}else{
		$('#gstin-csv-id').show();
   	  	$('#gstin').addClass("input-error").removeClass("input-correct");
	}     
}); 

$('#location').on("change", function() {
	$('#show').prop('disabled', false);
	$('#reason').prop('selectedIndex','');
	$('#location').removeClass("input-correct");
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#increaseInventoryTab").DataTable().rows().remove();
	}
	if($('#location').val() != ''){
		$('#location').addClass("input-correct").removeClass("input-error");
		$('#location-csv-id').hide();
		$("#search-product-autocomplete").attr("disabled", false);
		$("#quantity").attr("disabled", false);
		$("#stockValue").attr("disabled", false);
		//$('#show-select-gstin-location-msg').hide();
		
		
		setResetSearchProductInputFields('');
		var gstnWithStateInString = $("#gstin option:selected").text();
		var gstnInString = (gstnWithStateInString.split('[')[0]).trim();
		setLocationId(gstnInString,$('select#gstin option:selected').val(),$('select#location option:selected').val(),$("#location option:selected").text());
	
	
	
	}else{
		$('#location').addClass("input-error").removeClass("input-correct");
		$('#location-csv-id').show();
	}	
});

$('#reason').on("change", function() {
	$('#reason').removeClass("input-correct");
	/*
	$('#show').prop('disabled', false);	
	 if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#increaseInventoryTab").DataTable().rows().remove();
	}*/
	if($('#reason').val() != ''){
		$('#reason').addClass("input-correct").removeClass("input-error");
		$('#reason-csv-id').hide();
	}else{
		$('#reason').addClass("input-error").removeClass("input-correct");
		$('#reason-csv-id').show();
	}	
});

$(".narration").on("keyup input",function(e){
	if(e.keyCode == 32){
		   this.value = removeWhiteSpace(this.value);
	}
	this.value = this.value.replace(/[\\[]*$/, '');
	this.value = this.value.replace(/[^[a-zA-Z0-9.-\s/_]*$/, '');
	
	 if ($(".narration").val() !== ""){
		 $(".narration").addClass("input-correct").removeClass("input-error");
		 		 
	 }
	 if ($(".narration").val() === ""){
		 $(".narration").addClass("input-error").removeClass("input-correct");
		 		 
	 } 
}); 


$('#show').on('click',function(e){
	$('#show').prop('disabled', true);
	var gstinErrFlag = validateSelectField("gstin","gstin-csv-id");
	var locationErrFlag = validateSelectField("location","location-csv-id");
	var reasonErrFlag = validateSelectField("reason","reason-csv-id");
	var errFlagTransactionDate = validateTextField("transactionDate","transactionDate-csv-id",blankMsg);
	
	if(gstinErrFlag || locationErrFlag || reasonErrFlag || errFlagTransactionDate){
		e.preventDefault();
		$('#show').prop('disabled', false);
	}
	else{
		var locationId = $('#location').val();
		var transactionDate = $('#transactionDate').val();
		var gstinNo=$('#gstin').val();
		loadInventoryProduct(gstinNo,locationId,transactionDate);
	}	
});


$(document).on("blur", '.editStockValue', function(e){
	if(e.keyCode == 32){
	   this.value = removeWhiteSpace(this.value);
	}
	$(this).val(roundToTwoDecimal(this.value));
	
	if(currencyRegex.test($(this).val()) === true){
		if($("p").is(":visible")){
			$('#'+this.parentNode.lastElementChild.id).hide();
		}
		$(this).removeClass("input-error");		
		var currentStockValue = $(this).val();
		var oldStockValue = this.nextElementSibling.value;
		
		var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
        var id=parents.parent().prev('tr').attr('id');
		   	  if(id == undefined){                       // for desktop view ,this condition works
		   		  var $row = $(this).closest("tr");
		             var id = $row.attr('id');
		   	  }
         	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
         	 $(".stockvalue_"+rowNo).val(currentStockValue);
		
		if(currentStockValue === undefined || currentStockValue === null || currentStockValue === ''){
			$(this).addClass("input-error").focus();
		}else{
			$(this).removeClass("input-error").addClass("input-correct");
			var thisValue = checkNConvertToDecimal($(this).val());			
			$(this).val(thisValue);
		}
		
		if(inventoryType === 'InventoryDecrease' && parseFloat(currentStockValue) > parseFloat(oldStockValue)){
			$(this).addClass("input-error").removeClass("input-correct");
			bootbox.alert('Your stock value will become negative for selected product');
		}else{
			$(this).removeClass("input-error").addClass("input-correct");
		}
	}else{
		$(this).removeClass("input-correct").addClass("input-error").focus();
		if(isNaN($(this).val())){
			$(this).val('');
		}
	}
});

$(document).on("blur", '.editQty', function(e){
	if(e.keyCode == 32){
	   this.value = removeWhiteSpace(this.value);
	}
	$(this).val(roundToTwoDecimal(this.value));
	
	if(currencyRegex.test($(this).val())==true && $(this).val().length > 0){
		$(this).removeClass("input-error");		
		var currentQuantity = $(this).val();
		var oldQty = this.nextElementSibling.value;
		var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
        var id=parents.parent().prev('tr').attr('id');
		   	  if(id == undefined){                       // for desktop view ,this condition works
		   		  var $row = $(this).closest("tr");
		             var id = $row.attr('id');
		   	  }
         	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
         	 $(".quantity_"+rowNo).val(currentQuantity);
         	 
		if(inventoryType === 'InventoryIncrease' && (currentQuantity === undefined || currentQuantity === null || currentQuantity == 0 || currentQuantity == '')){
			$(this).addClass("input-error").removeClass("input-correct");
		}else if(inventoryType === 'InventoryDecrease' ){
			if(currentQuantity === undefined || currentQuantity === null || currentQuantity == 0 || currentQuantity == ''){
				$(this).addClass("input-error").removeClass("input-correct");
			}else if(parseInt(currentQuantity) > parseInt(oldQty)) {
				$(this).addClass("input-error").removeClass("input-correct");
				bootbox.alert('Your quantity will become negative for selected product');
			}else if(parseInt(currentQuantity) <= parseInt(oldQty)){
				$(this).removeClass("input-error").addClass("input-correct");
			}
		}else{
			$(this).removeClass("input-error").addClass("input-correct");
			var quantityEntered = checkNConvertToDecimal($(this).val());
			if(isNaN(quantityEntered)){
				$(this).val('');
			}else{
				$(this).val(quantityEntered);				
			}
		}
	}else{
		$(this).addClass("input-error");
		if(isNaN($(this).val())){
			$(this).val('');
		}
	}
});

//to field enable/disable
$('#increaseInventoryTab tbody').on('click', 'input[type="checkbox"]', function(e){
	if(this.checked){
		 this.parentNode.parentNode.children[4].firstElementChild.disabled=false;
		 this.parentNode.parentNode.children[7].firstElementChild.disabled=false;	
		$(this).parent().parent().next('tr.child').find('input').removeAttr('disabled');	
	}else{
		 this.parentNode.parentNode.children[4].firstElementChild.value='';
		 this.parentNode.parentNode.children[7].firstElementChild.value='';
		 this.parentNode.parentNode.children[4].firstElementChild.disabled=true;
		 this.parentNode.parentNode.children[7].firstElementChild.disabled=true;
		 $(this).parent().parent().next('tr.child').find('input').val('');
		$(this).parent().parent().next('tr.child').find('input').attr('disabled', 'true');
	}
});
/*
$('#checkboxtd').on('click', 'input[type="checkbox"]', function(e){	
	var rowCount = $('#increaseInventoryTab >tbody >tr').length;
	for(i=0;i<rowCount;i++){
		if(this.checked){
		$('#quantity_'+i).prop('disabled',false);
		$('#stockvalue_'+i).prop('disabled',false);
		}else{
			$('#quantity_'+i).prop('disabled',true);
			$('#stockvalue_'+i).prop('disabled',true);
		}
	}
	if($(this).is(":checked")){
		$('tr.child input').removeAttr('disabled');
	}else{
		$('tr.child input').attr('disabled', 'true');
	}
});
*/

$(document).on('keyup',".editQty", function(){
	this.value = this.value.replace(/[^0-9.]+/, '');
	if (currencyRegex.test($(this).val()) !== true){
		$(this).addClass("input-error");
	}else{
		$(this).removeClass("input-error");
	}	
});

$(document).on('keyup',".editStockValue", function(){
	this.value = this.value.replace(/[^[0-9.]*$/, '');
	if (currencyRegex.test($(this).val()) !== true){
		$(this).addClass("input-error");
	}else{
		$(this).removeClass("input-error");
	}	
});

function loadGstinField(){
	$.ajax({
		url : "getgstinforloggedinuser",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",			
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
			if(json != null){
				if(json.error == false){
					gstinJson = json.result;
					
					
					if(gstinJson == undefined){
						bootbox.alert(json.errorcode+' '+json.message);
					}else{
					$("#gstin").empty();						
					if(json.result.length == 1){
						$.each(json.result,function(i,value) {
							$("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
						});
						
						if(json.result[0].gstinLocationSet.length == 1){
							$.each(json.result[0].gstinLocationSet,function(i,value) {
								/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
								$("#locationStore").val(value.gstinStore);*/
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
								$("#locationStore").val(value.gstinStore);
								$("#locationId").val(value.id);
							
							});
							setResetSearchProductInputFields('');
						}else{
							$("#location").append('<option value="">Select</option>');
							$.each(json.result[0].gstinLocationSet,function(i,value) {
								/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));*/
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
							});
							setResetSearchProductInputFields('disabled');
						}
					}else{
						$("#gstin").append('<option value="">Select</option>');
						$.each(json.result,function(i,value) {
							$("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
						});
						setResetSearchProductInputFields('disabled');
					}
					}
				}else{
					bootbox.alert(json.errorcode+' '+json.message);
				}
			}			
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}

function loadLocationField(gstinId){
	$.ajax({
		url : "getlocationbygstinforloggedinuser",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
		data : {gstinId : gstinId},		
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
			
			if(json != null && json.length>0){
				$.each(json.gstinLocationSet, function(i, value) {
					if(json.gstinLocationSet.length == 1){
						$("#location").empty();
						$.each(json.gstinLocationSet,function(i,value) {
							/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));*/
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
						});
					}else{
						$("#location").empty();
						$("#location").append('<option value="">Select</option>');
						$.each(json.gstinLocationSet,function(i,value) {
							/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));*/
							$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
						});
					}
				});		
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});
}

function loadInventoryProduct(gstinNo,storeId,tDate){
	$.ajax({
		url : "getgoodsbystoreidandcurrentdate",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
		data :  {gstinNo:gstinNo , storeId : storeId, tDate : tDate},
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
			
			if(json.access == 'ACCESS_DENIED'){
				bootbox.alert("Data is manipulated.", function() {
					window.location.href =  getCustomLogoutPage();
					return
				});
			}else if(json == '' || json == null){
				bootbox.alert("Please try after some time");
				$('#show').prop('disabled', false);
			}else if(json != null){
				if(json.error == true){
					$('#show').prop('disabled', false);
					bootbox.alert(json.errorcode+' '+json.message);
				}else if(json.status == 'accessDeny'){
					bootbox.alert("Data is been manipulated.", function() {
						window.location.href = getCustomLogoutPage();
				   });
				}else{
					var counter=0; var srno=counter+1;
				 	$.each(json.result,function(i,value){
				 		var uom;
				 		if(value.uom === 'OTHERS'){
				 			uom = value.otherUOM;
				 		}else{
				 			uom = value.uom;
				 		}
					 	increaseInventoryCheckboxTable.row.add($(
					 			'<tr id="tr_id_'+counter+'">'
							 	+'<td><input type="hidden" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'"></td>'
							 	+'<td style="width:20px;"><input class="editCheckBox" type="hidden" id="product_id_'+counter+'" name="product_id_'+counter+'" value="'+value.prodId+'">'+(counter+1)+'</td>'
							 	+'<td><input type="hidden" id="product_name_'+counter+'" name="product_name_'+counter+'" value="'+value.productName+'">'+value.productName+'</td>'
							 	+'<td>'+checkNConvertToDecimal(value.currentStock)+'</td>'
								+'<td><input class="editQty quantity_'+counter+'" type="text" id="quantity_'+counter+'" name="quantity_'+counter+'" value="" disabled>'
									+'<input class="oldQty" type="hidden" id="oldquantity_'+counter+'" name="oldquantity_'+counter+'" value="'+checkNConvertToDecimal(value.currentStock)+'"></td>'
							 	+'<td><input type="hidden" id="unitOfMeasurement_'+counter+'" name="unitOfMeasurement_'+counter+'" value="'+value.uom+'">'
							 		+'<input type="hidden" id="otherUOM_'+counter+'" name="otherUOM_'+counter+'" value="'+value.uom+'">'+uom+'</td>'
							 	+'<td>'+checkNConvertToDecimal(value.stockValue)+'</td>'							 		
							 	+'<td><input class="editStockValue stockvalue_'+counter+'" type="text" id="stockvalue_'+counter+'" name="stockvalue_'+counter+'" value=""disabled>'
							 		+'<input class="oldStockValue" type="hidden" id="oldstockvalue_'+counter+'" name="oldstockvalue_'+counter+'" value="'+checkNConvertToDecimal(value.stockValue)+'"></td>'
							 	+'</tr>'					 	
					 	)).draw();
						counter++;
					});
					$('#showProductGrid').show();
				}
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}


$('#Save').on('click', function(e){
	$('.loader').show();
/*	$('#Save').prop('disabled', true);*/
	var gstin = $('#gstin').val();
	var location = $('#location').val();
	var reason = $('#reason').val();
	var narration = $('#narration').val();
	var transactionDate = $('#transactionDate').val();
	
	var alertFlag = false;	
	var alertFlagQty = false;	
	var alertFlagQtyNull = false;	
	var alertFlagValue = false;	
	var alertFlagValueNull = false;
	
	var errFlagGstin = validateSelect("gstin","gstin-csv-id");
	var errFlagLocation = validateSelect("location","location-csv-id");
	var errFlagReason = validateSelect("reason","reason-csv-id");
	var errFlagTransactionDate = validateTextField("transactionDate","transactionDate-csv-id",blankMsg);
	
	if(errFlagGstin || errFlagLocation || errFlagReason || errFlagTransactionDate){
		e.preventDefault();	
		$('#Save').prop('disabled', false);
		$('.loader').fadeOut("slow");
	}else{
		var rows_selected = increaseInventoryCheckboxTable.column(0).checkboxes.selected();
		if(rows_selected.length == 0){
			$('#Save').prop('disabled', false);
			$('.loader').fadeOut("slow");
			bootbox.alert('Please select atleast 1 product');
		}else{
			var productObject;
			var productRowArray = new Array();
			var alertText = '';
			var table_data = increaseInventoryCheckboxTable.rows().data();
			/*table_data.each(function(value,index){	
				if(rows_selected.indexOf(increaseInventoryCheckboxTable.row(index).data()[0]) != -1){*/
			var isRowSelected = false;
			increaseInventoryCheckboxTable.rows().every(function ( index, tableLoop, rowLoop ) {
				var rowX = increaseInventoryCheckboxTable.row(index).node();
			    var row = $(rowX);
			    isRowSelected = false;
			    var $checkbox = row.find('td:first-child input[type="checkbox"]')
			    if($checkbox.is(':checked')){
			       isRowSelected = true;
			    } 
			    // Get the current quantity
			    if(isRowSelected){
					var newQty = checkNConvertToDecimal(row.find("#quantity_"+index).val());
					var oldQty = checkNConvertToDecimal(row.find("#oldquantity_"+index).val());
					var modifiedQty = '';

					var newStockValue = row.find("#stockvalue_"+index).val();
					var oldStockValue = row.find("#oldstockvalue_"+index).val();
					var modifiedStockValue = '';
					
					if(newQty != '' && newQty != 0.00 && newQty != null && !isNaN(newQty) && (oldQty != '' || oldQty == 0) && oldQty != null && !isNaN(oldQty)){
						/*if(newQty != oldQty){*/
							if(inventoryType === 'InventoryIncrease'){
								/*if(newQty>oldQty){
									modifiedQty = newQty - oldQty;
								}else{
									$('#quantity_'+index).addClass("input-error").removeClass("input-correct");
									alertFlag = true;
									alertFlagQty = true;
									return false;
								}*/
								modifiedQty = (newQty* 100 + oldQty* 100) / 100 ;
							}else if(inventoryType === 'InventoryDecrease'){							
								/*if(newQty<oldQty || newQty>oldQty){
									modifiedQty = oldQty - newQty;	
								}else{
									$('#quantity_'+index).addClass("input-error").removeClass("input-correct");
									alertFlag = true;
									alertFlagQty = true;
									return false;
								}*/	
								modifiedQty = (oldQty* 100 - newQty* 100) / 100 ;
							}								
						/*}else{
							$('#quantity_'+index).addClass("input-error").removeClass("input-correct");
							alertFlag = true;
							alertFlagQty = true;
							return false;
						}*/		
					}else{
						$('#quantity_'+index).addClass("input-error").removeClass("input-correct");
						alertFlag = true;
						alertFlagQtyNull = true;
						return false;						
					}
					
					if(newStockValue != '' && newStockValue != null  && !isNaN(newStockValue) && oldStockValue != '' && oldStockValue != null){
						//if(parseFloat(newStockValue) != parseFloat(oldStockValue)){
							if(inventoryType === 'InventoryIncrease')
								modifiedStockValue = (parseFloat(newStockValue)* 100 + parseFloat(oldStockValue) * 100) / 100;
							else
								modifiedStockValue = (parseFloat(oldStockValue)* 100 - parseFloat(newStockValue) * 100) / 100;
							/*}else{
							$('#stockvalue_'+index).addClass("input-error").removeClass("input-correct");
							alertFlag = true;
							alertFlagValue = true;
							return false;
						}*/
					}else{
						$('#stockvalue_'+index).addClass("input-error").removeClass("input-correct");
						alertFlag = true;
						alertFlagValueNull = true;
						return false;						
					}
					
					if(!alertFlagQty && !alertFlagQtyNull && !alertFlagValue && !alertFlagValueNull){	
						productObject = new Object();
						productObject.id = row.find("#product_id_"+index).val();
						productObject.name = row.find("#product_name_"+index).val();
						productObject.unitOfMeasurement = row.find("#unitOfMeasurement_"+index).val();
						productObject.otherUOM = row.find("#otherUOM_"+index).val();
						productObject.currentStock = checkNConvertToDecimal(modifiedQty);
						productObject.currentStockValue = parseFloat(modifiedStockValue).toFixed(2);
						productObject.storeId = $("#location").val();
						productObject.modifiedQty = newQty;
						productObject.modifiedStockValue = parseFloat(newStockValue).toFixed(2);
						productObject.transactionDate = transactionDate;
						productRowArray.push(productObject);
					}			
				}		
			});	
			
			if(!alertFlag){
				var jsonInputData = {
						"inventoryType" : inventoryType,
						"productList" : JSON.parse(JSON.stringify(productRowArray)),
						"narration" : narration,
						"reason" : reason
				}
				sendDataServer(jsonInputData);	
			}else{
				e.preventDefault();	
				$('.loader').fadeOut("slow");
				$('#Save').prop('disabled', false);
				var userAlertText = '';
				
				var flagQty = false;
				var flagValue = false;
				var flagQtyNull = false;
				var flagValueNull = false;
				
				if(alertFlagQty){
					if(!flagQty){
						userAlertText = 'Please '+inventoryTypePage.toLowerCase()+' the quantity for selected product.<br>';
						flagQty = true;
					}
				}
				
				if(alertFlagValue){
					if(!flagValue){
						userAlertText += 'Please '+inventoryTypePage.toLowerCase()+' the stock value for selected product.<br>';
						flagQty = true;
					}
				}
			
				if(alertFlagQtyNull){
					if(!flagQtyNull){
						userAlertText += 'Please enter appropriate quantity for selected product.<br>';
						flagQty = true;
					}
				}
				
				if(alertFlagValueNull){
					if(!flagValueNull){
						userAlertText += 'Please enter appropriate stock value for selected product.<br>';
						flagQty = true;
					}
				}	
				
				if(userAlertText != ''){
					bootbox.alert(userAlertText);
				}
			}			
		}
	}	
});

function validateSelect(id,spanid){
	if ($("#"+id).val() === ""){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+spanid).show();
		$("#"+id).focus();
		return true;
	}else{
		$("#"+id).addClass("input-correct").removeClass("input-error");
		$("#"+spanid).hide();
		return false;
	}
}

function sendDataServer(jsonInputData){
	$.ajax({
		url : "saveinventorydetailsfromInventorty",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
	    contentType : "application/json",
		data : JSON.stringify(jsonInputData),		
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
			if(json != null){
				$('#Save').prop('disabled', false);
				$('.loader').fadeOut("slow");
				if(json.error == true) {					
					bootbox.alert(json.errorcode+' '+json.message);
				}else{
					if(json.status == 'accessDeny'){
						bootbox.alert("Data is been manipulated.", function() {
							window.location.href = getCustomLogoutPage();
					   });
					}else{
						bootbox.alert(json.message, function() {
							if(inventoryType === 'InventoryIncrease')
								window.location.href = "./increaseinventory";
							else
								window.location.href = "./decreaseinventory";
							return;
						});
					}
				}				
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}

function loadReasonField(urlforAction){
	$.ajax({
		url :urlforAction,
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "GET",
		dataType : "json",	
	    contentType : "application/json",	
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').fadeOut("slow");
		},
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
			if(json != null){				
				$("#reason").empty();
				$("#reason").append('<option value="">Select</option>');
				$.each(json,function(i,value) {
					$("#reason").append($('<option>').text(value).attr('value',i));
				});
			}else{
				console.log("Reason field json is null");
				bootbox.alert("Something went wrong");
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});				
}


function setLocationId(gstnNumber,gstnState,gstinLocationSetUniqueSequence,gstinLocationSetgstinLocation){
	if(gstinJson != ''){
		$.each(gstinJson,function(i,value){
			if(value.gstinNo == gstnNumber){
				if(value.gstinLocationSet.length == 1){
	  				$.each(value.gstinLocationSet,function(i,value2) {
	  					$("#locationId").val(value2.id);
	  				});
	  			}
	  			else{   
					 $.each(value.gstinLocationSet,function(i,value3){
						 if(value3.uniqueSequence == gstinLocationSetUniqueSequence && value3.gstinLocation == gstinLocationSetgstinLocation){
							 $("#locationId").val(value3.id); 
						 }
					});
	  			}
				
			} 
		});
	}
}

function setResetSearchProductInputFields(disable){
	if(disable == 'disabled'){
		$("#search-product-autocomplete").attr('disabled', 'disabled');
		$("#show-select-gstin-location-msg").show();
	}else{
		$("#search-product-autocomplete").removeAttr("disabled");
		$("#show-select-gstin-location-msg").hide();
	}
}
