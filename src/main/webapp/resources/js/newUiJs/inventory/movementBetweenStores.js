var fromStoreCheckboxTable;
var gstinJson;
var blankMsg = "This field is required";
var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;

$(document).ready(function() {
	$("#search-product-autocomplete").attr("disabled", true);
	$("#quantity").attr("disabled", true);
	$("#stockValue").attr("disabled", true);
	$("#showToStoreProductGrid").hide();
	$("#show-select-gstin-location-msg").show();
	$("#Save").hide();
	$("#toStoreDiv").hide();
	$("#narrationDiv").hide();
	$("#fromstore").hide();
	$("#tostore").hide();
	$("#fromStorepanel").hide();
	$("#toStorepanel").hide();
	$('#showProductGrid').hide();
	
	/*initializeFromTable();
	intializeToStoreTable();*/
	
	/*$(document).on('click','.paginate_button', function() {
  	  var isRowSelected = false;
  	     toStoreProductDetailsTable.rows().every( function ( index, tableLoop, rowLoop ) {
			    	$("#toStoresproductdetailsId tbody #tr_id_"+index+" input:checkbox").attr('disabled',true).css('display','none');
			    });
  	}); */
	 
	

	$('.loader').fadeOut("slow");
	$('#show').prop('disabled', false);

	loadGstinField();
	
	$("#transactionDate").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
	    onSelect: function(selected) {
        $("#transactionDate").addClass("input-correct").removeClass("input-error");
         $("#transactionDate-csv-id").hide();
         $('#show').prop('disabled', false);
         
        /*if($('#fromStorepanel').is(':visible')){
      	  $('#fromStoreproductDetailsId').DataTable().row().remove();
        }*/
        
        if ($('#fromStorepanel').is(':visible')) {
			$("#fromStoreproductDetailsId tbody tr").remove();
			if ($.fn.DataTable.isDataTable( '#fromStoreproductDetailsId' ) ) {
				  $('#fromStoreproductDetailsId').DataTable().destroy();
			}
			$('#fromStorepanel').hide();
		}
        
	  	//$('#fromStorepanel').hide();
	  	$('#show').show();
	  	$('#showbuttondiv').show();
		$("#narrationDiv").hide();
		$("#Save").hide();
		$("#toStoreDiv").hide();
		$("#toStorepanel").hide();
      }
    });
	
	$("#fromStoreproductDetailsId").on("change",".qtyToBeMoved", function () {  	  
   	 	var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
        var id=parents.parent().prev('tr').attr('id');
		if(id == undefined){                       // for desktop view ,this condition works
			var $row = $(this).closest("tr");
			var id = $row.attr('id');
		}
     	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
     	var quantity = $(this).val();
     	 $(".qtyToBeMoved_"+rowNo).val(quantity);         
     }); 
	
	$("#fromStoreproductDetailsId").on("change",".stkValueToBeMoved", function () { 	  
   	 	var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
        var id=parents.parent().prev('tr').attr('id');
		if(id == undefined){                       // for desktop view ,this condition works
			var $row = $(this).closest("tr");
			var id = $row.attr('id');
		}
     	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
     	var stockquantity = $(this).val();
     	 $(".stkValueToBeMoved_"+rowNo).val(stockquantity);         
     }); 
	
	$(document).on('keyup',".qtyToBeMoved", function(){
		this.value = this.value.replace(/[^0-9-.]+/, '');
		if (currencyRegex.test($(this).val())!=true){
			$(this).addClass("input-error");
		}else{
			$(this).removeClass("input-error");
		}	
	}); 
	
	$(document).on('keyup',".stkValueToBeMoved", function(){
		this.value = this.value.replace(/[^0-9-.]+/, '');
		if (currencyRegex.test($(this).val())!=true){
			$(this).addClass("input-error");
		}else{
			$(this).removeClass("input-error");
		}	
	}); 
});


/*function intializeToStoreTable(){
	
	toStoreProductDetailsTable = $('#toStoresproductdetailsId').DataTable({
		columnDefs : [ {
			orderable : false,
			checkboxes : {
				'selectRow' : true
			},
			targets : 0
		} ],
		select : {
			style : 'multi',
			selector : 'td:first-child'
		},
		rowReorder : {
			selector : 'td:nth-child(2)'
		},
		responsive : true
	});
	$("#toStoresproductdetailsId thead tr #checkboxtd input:checkbox").hide().attr('disabled',true);
}*/

/*function initializeFromTable(){
	fromStoreCheckboxTable = $('#fromStoreproductDetailsId').DataTable({
		columnDefs : [ {
			orderable : false,
			checkboxes : {
				'selectRow' : true
			},
			targets : 0
		} ],
		select : {
			style : 'multi',
			selector : 'td:first-child'
		},
		rowReorder : {
			selector : 'td:nth-child(2)'
		},
		responsive : true
	});
}*/

$('#fromgstin').on("change",function() {
	$("#fromlocationId").val('');
			var gstinSelectedId = $('#fromgstin').val();
			if (gstinSelectedId != '') {
				$('#fromgstin').addClass("input-correct").removeClass("input-error");
				$('#fromgstin-csv-id').hide();
				$("#fromlocation").empty();
				$("#fromlocation").append('<option value="">Select</option>');
				$.each(gstinJson, function(i, value) {
					if (value.id == gstinSelectedId) {
						$.each(value.gstinLocationSet, function(i2, value2) {
							$("#fromlocation").append($('<option>').text(value2.gstinLocation).attr('value', value2.uniqueSequence));
							//$("#fromlocationId").val(value2.id);
						});
					}
				});
			} else {
				$('#fromgstin-csv-id').show();
				$('#fromgstin').addClass("input-error").removeClass("input-correct");
			}
			
			$("#togstin").val($('select#fromgstin option:selected').val());
		});

$('#fromlocation').on("change",function() {
			var currentgstin = $('select#fromgstin option:selected').val();
			var currentlocation = $('select#fromlocation option:selected').val();
			changeToLocation(currentgstin, currentlocation);
			changeFromLocation(currentgstin, currentlocation);
			if ($('#fromlocation').val() != '') {
				$('#fromlocation').addClass("input-correct").removeClass("input-error");
				$('#location-csv-id').hide();
				$("#search-product-autocomplete").attr("disabled", false);
				$("#quantity").attr("disabled", false);
				$("#stockValue").attr("disabled", false);
				setResetSearchProductInputFields('');
				
			} else {
				$('#fromlocation').addClass("input-error").removeClass("input-correct");
				$('#location-csv-id').show();
			}
		});

$('#transactionDate').on("change",function() {
	$('#show').prop('disabled', false);
	$('#showbuttondiv').show();
	$("#narrationDiv").hide();
	$("#Save").hide();
	$("#toStoreDiv").hide();
	$("#toStorepanel").hide();
	$("#showToStoreProductGrid").hide();
	
	$("#toStoresproductdetailsId tbody tr").remove();
	if ($.fn.DataTable.isDataTable( '#toStoresproductdetailsId' ) ) {
		  $('#toStoresproductdetailsId').DataTable().destroy();
	}
	
	if ($('#fromStorepanel').is(':visible')) {
		$("#fromStoreproductDetailsId tbody tr").remove();
		if ($.fn.DataTable.isDataTable( '#fromStoreproductDetailsId' ) ) {
			  $('#fromStoreproductDetailsId').DataTable().destroy();
		}
		$('#fromStorepanel').hide();
	}
	if ($('#transactionDate').val() != '') {
		$('#transactionDate').addClass("input-correct").removeClass("input-error");
		$('#transactionDate-csv-id').hide();
		$('#showbuttondiv').show();
	} else {
		$('#transactionDate').addClass("input-error").removeClass("input-correct");
		$('#show').prop('disabled', false);
		$('#transactionDate-csv-id').show();
	}
});

$('#tolocation').on("change", function() {
	$("#Save").show();
	$('#show').hide();
	$('#narrationDiv').show();
	$('#show').prop('disabled', false);
	
	if ($('#toStorepanel').is(':visible')) {
		$("#toStoresproductdetailsId tbody tr").remove();
		if ($.fn.DataTable.isDataTable( '#toStoresproductdetailsId' ) ) {
			  $('#toStoresproductdetailsId').DataTable().destroy();
		}
		$('#toStorepanel').hide();
	}
	if ($('#tolocation').val() != '') {
		$('#tolocation').addClass("input-correct").removeClass("input-error");
		$('#tolocation-csv-id').hide();
	} else {
		$('#tolocation').addClass("input-error").removeClass("input-correct");
		$('#tolocation-csv-id').show();
	}
});



$('#show').on('click',function(e) {
			$("#fromstore").show();
			$("#tostore").hide();
			$("#toStoreDiv").show();
			$("#togstin").val($('#fromgstin').val());
			$("#narrationDiv").show();
			$("#toStorepanel").hide();
			$('#savebuttondiv').show();
			$('#Save').show();
			$("#showbuttondiv").hide();
			$('#show').prop('disabled', true);
			
			if ($('#fromStorepanel').is(':visible')) {
				$("#fromStoreproductDetailsId tbody tr").remove();
				if ($.fn.DataTable.isDataTable( '#fromStoreproductDetailsId' ) ) {
					  $('#fromStoreproductDetailsId').DataTable().destroy();
				}
				$('#fromStorepanel').hide();
			}
			
			var gstinErrFlag = validateSelectField("fromgstin","fromgstin-csv-id");
			var locationErrFlag = validateSelectField("fromlocation","fromlocation-csv-id");
			var errFlagTransactionDate = validateTextField("transactionDate","transactionDate-csv-id",blankMsg);
			if (gstinErrFlag || locationErrFlag || errFlagTransactionDate) {
				e.preventDefault();
				$('#showbuttondiv').show();
				$("#narrationDiv").hide();
				$("#toStoreDiv").hide();
				$('#savebuttondiv').hide();
			} else {
				var storeId = $('#fromlocation').val();
				var toDate = $('#transactionDate').val();
				var gstinNo=$('#fromgstin').val();
				//loadInventoryProduct(storeId)
				loadInventoryProduct(gstinNo,storeId,toDate);
				$("#show").hide();
			}
		});



$('#fromStoreproductDetailsId tbody').on('click', 'input[type="checkbox"]', function(e){
	if(this.checked){
		 this.parentNode.parentNode.children[4].firstElementChild.disabled=false;
		 this.parentNode.parentNode.children[7].firstElementChild.disabled=false;
		 $(this).parent().parent().next('tr.child').find('.qtyToBeMoved').removeAttr('disabled');
		 $(this).parent().parent().next('tr.child').find('.stkValueToBeMoved').removeAttr('disabled');
		 $(this).parent().parent().next('tr.child').find('input').removeAttr('disabled');
		 
	}else{
		 this.parentNode.parentNode.children[4].firstElementChild.value='';
         this.parentNode.parentNode.children[7].firstElementChild.value='';
		 this.parentNode.parentNode.children[4].firstElementChild.disabled=true;
		 this.parentNode.parentNode.children[7].firstElementChild.disabled=true;
		 $(this).parent().parent().next('tr.child').find('.qtyToBeMoved').attr('disabled', 'true');
		 $(this).parent().parent().next('tr.child').find('.stkValueToBeMoved').removeAttr('disabled');
		 $(this).parent().parent().next('tr.child').find('input').val('');
         $(this).parent().parent().next('tr.child').find('input').attr('disabled', 'true');

	}
}); 



function loadGstinField() {
	$.ajax({
		url : "getgstinforloggedinuser",
		headers : {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		async : false,
		beforeSend : function() {
			$('.loader').show();
		},
		complete : function() {
			$('.loader').fadeOut("slow");
		},
		success : function(json, fTextStatus, fRequest) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if (isValidToken(json) == 'No') {
				window.location.href = getCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

			if (json.error == false) {
				gstinJson = json.result;
				$("#fromgstin").empty();
				if (json.result.length == 1) {
					$.each(json.result, function(i, value) {
						$("#fromgstin").append(
						$('<option>').text(value.gstinNo + ' [ '+ value.stateInString + ' ] ').attr('value', value.id));
					});

					if (json.result[0].gstinLocationSet.length == 1) {
						$.each(json.result[0].gstinLocationSet, function(i,value) {
							/*$("#fromlocation").append($('<option>').text(value.gstinLocation).attr('value', value.uniqueSequence));
							$("#fromlocationStore").val(value.gstinStore);
							$("#fromlocationId").val(value.id);
							$("#tolocation").append($('<option>').text(value.gstinLocation).attr('value', value.id));
							$("#tolocationStore").val(value.gstinStore);
							$('#show').show();*/
							$("#fromlocation").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
							$("#fromlocationStore").val(value.gstinStore);
							$("#fromlocationId").val(value.id);
						});

						setResetSearchProductInputFields('');
					} else {
						/*$("#fromlocation").append(
								'<option value="">Select</option>');
						$("#tolocation").append(
								'<option value="">Select</option>');
						$.each(json.result[0].gstinLocationSet, function(i,
								value) {
							$("#fromlocation").append(
									$('<option>').text(value.gstinLocation)
											.attr('value', value.uniqueSequence));
							$("#fromlocationId").val(value.id);
							$("#tolocation").append(
									$('<option>').text(value.gstinLocation)
											.attr('value', value.id));
						});*/
						$("#fromlocation").append('<option value="">Select</option>');
						$.each(json.result[0].gstinLocationSet,function(i,value) {
							/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));*/
							$("#fromlocation").append($('<option>').text(value.gstinLocation).attr('value',value.uniqueSequence).attr('selected','selected'));
							//$("#fromlocationId").val(value.id);
						});
						setResetSearchProductInputFields('disabled');
					}
				} else {
					/*$("#fromgstin").append('<option value="">Select</option>');
					$.each(json.result, function(i, value) {
						$("#fromgstin").append(
								$('<option>').text(
										value.gstinNo + ' [ '
												+ value.stateInString + ' ] ')
										.attr('value', value.id));
						$("#togstin").append(
								$('<option>').text(
										value.gstinNo + ' [ '
												+ value.stateInString + ' ] ')
										.attr('value', value.id));
					});*/
					$("#fromgstin").append('<option value="">Select</option>');
					$.each(json.result,function(i,value) {
						$("#fromgstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
					});
					setResetSearchProductInputFields('disabled');
				}
			} else {
				bootbox.alert(json.status);
			}

		},
		error : function(data, status, er) {
			getInternalServerErrorPage();
		}
	});
}

function loadLocationField(gstinId) {
	$.ajax({
		url : "getlocationbygstinforloggedinuser",
		headers : {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		data : {
			gstinId : gstinId
		},
		async : false,
		beforeSend : function() {
			$('.loader').show();
		},
		complete : function() {
			$('.loader').fadeOut("slow");
		},
		success : function(json, fTextStatus, fRequest) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if (isValidToken(json) == 'No') {
				window.location.href = getCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			console.log(json);
			if (json != null && json.length > 0) {
				$.each(json.gstinLocationSet, function(i, value) {
					if (json.gstinLocationSet.length == 1) {
						$("#fromlocation").empty();
						$.each(json.gstinLocationSet, function(i, value) {
							$("#fromlocation").append(
									$('<option>').text(value.gstinLocation)
											.attr('value', value.uniqueSequence));
							$("#fromlocationId").val(value.id);
						});
					} else {
						$("#fromlocation").empty();
						$("#fromlocation").append(
								'<option value="">Select</option>');
						$.each(json.gstinLocationSet, function(i, value) {
							$("#fromlocation").append(
									$('<option>').text(value.gstinLocation)
											.attr('value', value.uniqueSequence));
							$("#fromlocationId").val(value.id);

						});
					}
				});
			}
		},
		error : function(data, status, er) {
			getInternalServerErrorPage();
		}
	});
}

/*function loadInventoryProduct(locationId) {
	$.ajax({
				url : "showinventoryproductbygstinnlocationid",
				headers : {
					_csrf_token : $("#_csrf_token").val()
				},
				type : "POST",
				dataType : "json",
				data : {
					locationId : locationId
				},
				async : false,
				beforeSend : function() {
					$('.loader').show();
				},
				complete : function() {
					$('.loader').fadeOut("slow");
				},
				success : function(json, fTextStatus, fRequest) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}
					if (isValidToken(json) == 'No') {
						window.location.href = getCsrfErrorPage();
						return;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					console.log(json);
					if (json != null) {
						if (json.error == true) {
							bootbox.alert(json.message);
							$("#toStoreDiv").hide();
							$('#showbuttondiv').show();
							$('#narrationDiv').hide();
							$('#Save').hide();
						} else {
							$("#fromStoreproductDetailsId tbody tr").remove();
							if ($.fn.DataTable.isDataTable( '#fromStoreproductDetailsId' ) ) {
								  $('#fromStoreproductDetailsId').DataTable().destroy();
							}
							initializeFromTable();
							
							var counter = 0;
							var srno = counter + 1;
							$
									.each(
											json.result,
											function(i, value) {
												var uom;
												if (value.unitOfMeasurement === 'OTHERS') {
													uom = value.otherUOM;
												} else {
													uom = value.unitOfMeasurement;
												}
												fromStoreCheckboxTable.row
														.add(
																$('<tr id="tr_id_'+counter+'">'
																		+ '<td><input type="hidden" id="counter_'
																		+ counter
																		+ '" name="counter_'
																		+ counter
																		+ '" value="'
																		+ counter
																		+ '"></td>'
																		+ '<td style="width:20px;"><input class="editCheckBox" type="hidden" id="product_id_'
																		+ counter
																		+ '" name="product_id_'
																		+ counter
																		+ '" value="'
																		+ value.id
																		+ '">'
																		+ (counter + 1)
																		+ '</td>'
																		+ '<td><input type="hidden" id="product_name_'
																		+ counter
																		+ '" name="product_name_'
																		+ counter
																		+ '" value="'
																		+ value.name
																		+ '">'
																		+ value.name
																		+ '</td>'
																		+ '<td><input type="hidden" id="quantity_'
																		+ counter
																		+ '" name="quantity_'
																		+ counter
																		+ '" value="'
																		+ value.currentStock
																		+ '">'
																		+ checkNConvertToDecimal(value.currentStock)
																		+ '</td>'
																		+ '<td><input class="qtyToBeMoved qtyToBeMoved_'+counter+'" type="text"  id="qtyToBeMoved_'
																		+ counter
																		+ '" name="qtyToBeMoved_'
																		+ counter
																		+ '" value="" disabled></td>'
																		+ '<span class="text-danger cust-error qtyToBeMovedError" id="qtyToBeMoved-empty_'
																		+ counter
																		+ '"></span>'
																		+ '<td><input type="hidden" id="unitOfMeasurement_'
																		+ counter
																		+ '" name="unitOfMeasurement_'
																		+ counter
																		+ '" value="'
																		+ value.unitOfMeasurement
																		+ '">'
																		+ '<input type="hidden" id="otherUOM_'
																		+ counter
																		+ '" name="otherUOM_'
																		+ counter
																		+ '" value="'
																		+ value.otherUOM
																		+ '">'
																		+ uom
																		+ '</td>'
																		+ '<td><input type="hidden" id="stockvalue_'
																		+ counter
																		+ '" name="stockvalue_'
																		+ counter
																		+ '" value="'
																		+ value.currentStockValue
																		+ '">'
																		+ checkNConvertToDecimal(value.currentStockValue)
																		+ '</td>'
																		+ '<td><input class="stkValueToBeMoved stkValueToBeMoved_'+counter+'" type="text" id="stkValueToBeMoved_'
																		+ counter
																		+ '" name="stkValueToBeMoved_'
																		+ counter
																		+ '" value="" disabled></td>'
																		+ '<span class="text-danger cust-error stkValueToBeMovedError" id="stkValueToBeMoved-empty_'
																		+ counter
																		+ '"></span>'
																		+ '</tr>'))
														.draw();
												counter++;
											});

							$("#fromStorepanel").show();
							$('#showProductGrid').show();
						}
					}
				},
				error : function(data, status, er) {
					getInternalServerErrorPage();
				}
			});
}*/

/*function loadToStoreProduct(locationId) {
	$.ajax({
				url : "showinventoryproductbygstinnlocationid",
				headers : {
					_csrf_token : $("#_csrf_token").val()
				},
				type : "POST",
				dataType : "json",
				data : {
					locationId : locationId
				},
				async : false,
				beforeSend : function() {
					$('.loader').show();
				},
				complete : function() {
					$('.loader').fadeOut("slow");
				},
				success : function(json, fTextStatus, fRequest) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}
					if (isValidToken(json) == 'No') {
						window.location.href = getCsrfErrorPage();
						return;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					console.log(json);
					if (json != null) {
						if (json.error == true) {
							bootbox.alert(json.message);
						} else {
							
							$("#toStoresproductdetailsId tbody tr").remove();
							if ($.fn.DataTable.isDataTable( '#toStoresproductdetailsId' ) ) {
								  $('#toStoresproductdetailsId').DataTable().destroy();
							}
							intializeToStoreTable();
							
							var counter = 0;
							var srno = counter + 1;
							$
									.each(
											json.result,
											function(i, value) {
												var uom;
												if (value.unitOfMeasurement === 'OTHERS') {
													uom = value.otherUOM;
												} else {
													uom = value.unitOfMeasurement;
												}
												toStoreProductDetailsTable.row
														.add(
																$('<tr id="tr_id_'+counter+'">'
																		+ '<td><input type="hidden" id="counter_'
																		+ counter
																		+ '" name="counter_'
																		+ counter
																		+ '" value="'
																		+ counter
																		+ '"></td>'
																		+ '<td style="width:20px;"><input class="editCheckBox" type="hidden" id="product_id_'
																		+ counter
																		+ '" name="product_id_'
																		+ counter
																		+ '" value="'
																		+ value.id
																		+ '">'
																		+ (counter + 1)
																		+ '</td>'
																		+ '<td><input type="hidden" id="product_name_'
																		+ counter
																		+ '" name="product_name_'
																		+ counter
																		+ '" value="'
																		+ value.name
																		+ '">'
																		+ value.name
																		+ '</td>'
																		+ '<td><input type="hidden" id="quantity_'
																		+ counter
																		+ '" name="quantity_'
																		+ counter
																		+ '" value="'
																		+ value.currentStock
																		+ '">'
																		+ checkNConvertToDecimal(value.currentStock)
																		+ '</td>'
																		+ '<td><input type="hidden" id="unitOfMeasurement_'
																		+ counter
																		+ '" name="unitOfMeasurement_'
																		+ counter
																		+ '" value="'
																		+ value.unitOfMeasurement
																		+ '">'
																		+ '<input type="hidden" id="otherUOM_'
																		+ counter
																		+ '" name="otherUOM_'
																		+ counter
																		+ '" value="'
																		+ value.otherUOM
																		+ '">'
																		+ uom
																		+ '</td>'
																		+ '<td><input type="hidden" id="tostockvalue_'
																		+ counter
																		+ '" name="tostockvalue_'
																		+ counter
																		+ '" value="'
																		+ value.currentStockValue
																		+ '">'
																		+ checkNConvertToDecimal(value.currentStockValue)
																		+ '</td>'
																		+ '</tr>'))
														.draw();
												$("#toStoresproductdetailsId tbody #tr_id_"+counter+" input:checkbox").hide().attr('disabled',true); 
												counter++;
											});
							$('#showToStoreProductGrid').show();
						}
					}
				},
				error : function(data, status, er) {
					getInternalServerErrorPage();
				}
			});

}*/

/*$('#Save').on('click',function(e) {
					$("#fromstore").hide();
					$("#tostore").show();
					$("#fromStorepanel").hide();
					$("#toStorepanel").show();
					$('.loader').show();
					$('#Save').prop('disabled', true);
					var fromgstin = $('#fromgstin').val();
					var fromlocation = $('#fromlocation').val();
					var togstin = $('#togstin').val();
					var tolocation = $('#tolocation').val();
					var narration = $('#narration').val();
					var transactionDate = $('#transactionDate').val();

					var alertFlag = false;
					var alertFlagQty = false;
					var alertFlagQtyNull = false;
					var alertFlagValue = false;
					var alertFlagValueNull = false;

					var errFlagfromGstin = validateSelect("fromgstin","fromgstin-csv-id");
					var errFlagfromLocation = validateSelect("fromlocation","fromlocation-csv-id");
					var errFlagToGstin = validateSelect("togstin","togstin-csv-id");
					var errFlagToLocation = validateSelect("tolocation","tolocation-csv-id");
					var errFlagTransactionDate = validateTextField("transactionDate","transactionDate-csv-id",blankMsg);

					if (errFlagfromGstin || errFlagfromLocation || errFlagToGstin || errFlagToLocation || errFlagTransactionDate) {
						e.preventDefault();
						$('#Save').prop('disabled', false);
						 $("#fromStorepanel").show();
						 $("#fromstore").show();
						 $('#showProductGrid').show();
						 $("#toStorepanel").hide();
						  $("#show").hide(); 
						  $("#showbuttondiv").hide();
						  $("#savebuttondiv").show();
						  $("#narrationDiv").show();
						$('.loader').fadeOut("slow");
					} 
					else {
						var rows_selected = fromStoreCheckboxTable.column(0).checkboxes.selected();
						if (rows_selected.length == 0) {
							$('#Save').prop('disabled', false);
							$('.loader').fadeOut("slow");
							bootbox.alert('Please select atleast 1 row.');
						} else {
							var productObject;
							var productRowArray = new Array();
							var alertText = '';
							//var table_data = fromStoreCheckboxTable.rows().data();
							//table_data.each(function(value, index) {
											//if (rows_selected.indexOf(fromStoreCheckboxTable.row(index).data()[0]) != -1) {
							var isRowSelected = false;
							fromStoreCheckboxTable.rows().every( function ( index, tableLoop, rowLoop ) {
								var rowX = fromStoreCheckboxTable.row(index).node();
							    var row = $(rowX);
							    isRowSelected = false;
							    var $checkbox = row.find('td:first-child input[type="checkbox"]')
							    if($checkbox.is(':checked')){
							       isRowSelected = true;
							    } 
							    // Get the current quantity
							    if(isRowSelected){ 
												$('#narrationDiv').show();
												var currentQty =  parseFloat(row.find("#quantity_"+ index).val());
												var modifiedQty =   parseFloat(row.find("#qtyToBeMoved_"+ index).val());
												var currentStockValue =  parseFloat(row.find("#stockvalue_"+ index).val());
												var fromproductName = row.find("#product_name_"+ index).val();
												var modifiedStockValue = parseFloat(row.find("#stkValueToBeMoved_"+ index).val());
												var transactionDate = $('#transactionDate').val();
												var userAlertText = '';

												if (currentQty != '' && !isNaN(currentQty) && currentQty != null &&  modifiedQty != null && modifiedQty != '' && !isNaN(modifiedQty)) {
													  if(modifiedQty > currentQty){
														$('#qtyToBeMoved_'+ index).addClass("input-error").removeClass("input-correct");
														alertFlag = true;
														alertFlagQty = true;
														return false;
														
													 }
												}
												else {
													$('#qtyToBeMoved_' + index).addClass("input-error").removeClass("input-correct");
													alertFlag = true;
													alertFlagQtyNull = true;
													return false;
													
												}
												
										if(currentStockValue != ''  && currentStockValue != null && !isNaN(currentStockValue) &&  modifiedStockValue != null && modifiedStockValue != '' && !isNaN(modifiedStockValue)){
													if(modifiedStockValue > currentStockValue){
														$('#stkValueToBeMoved_'+ index).addClass("input-error").removeClass("input-correct");
														alertFlag = true;
														alertFlagValue = true;
														return false;
													 }
												}
												else{
													$('#stkValueToBeMoved_' + index).addClass("input-error").removeClass("input-correct");
													alertFlag = true;
													alertFlagValueNull = true;
													return false;
												}
										
									if(!alertFlagQty && !alertFlagQtyNull && !alertFlagValue && !alertFlagValueNull){
														    productObject = new Object();
															productObject.name = row.find("#product_name_"+ index).val();
															productObject.currentStock = row.find("#quantity_"+ index).val();
															productObject.currentStockValue = parseFloat(row.find("#stockvalue_"+ index).val()).toFixed(1);
															productObject.fromStoreId = $("#fromlocation").val();
															productObject.toStoreId = $("#tolocation").val();
															productObject.fromProductName = fromproductName;
															productObject.modifiedQty = modifiedQty;
															productObject.modifiedStockValue = modifiedStockValue;
															productObject.transactionDate = transactionDate;
															productRowArray.push(productObject);
													 }
							    }
							              });
											//}
									//});

							if (!alertFlag) {
								var jsonInputData = {
									"productList" : productRowArray,
									"narration" : narration,
								}
								  sendDataServer(jsonInputData);
								  locationId=$("#tolocation").val();
								  var today = new Date();
								  //tilldtae=$("#transactionDate").val();
								  loadToStoreProduct(locationId);
								  var date = new Date();
								  var day = date.getDate();
								  var month = date.getMonth() + 1;
								  var year = date.getFullYear();
								  if (month < 10) month = "0" + month;
								  if (day < 10) day = "0" + day;
								  //var today = year + "-" + month + "-" + day;  
								  var today = day + "-" + month + "-" + year;
								  document.getElementById("transactionDate").value = today;							  
								  //loadToStoreProduct(locationId,tilldtae);
								  
								  $("#toStorepanel").show();
								  $("#fromStoreproductDetailsId tbody tr").remove();
								  if ($.fn.DataTable.isDataTable( '#fromStoreproductDetailsId' ) ) {
								  	  $('#fromStoreproductDetailsId').DataTable().destroy();
							      }
								  $("#fromStorepanel").hide();
								  $("#show").hide(); $("#Save").hide();
								  $("#showbuttondiv").hide();
								  $("#savebuttondiv").hide();
								  $("#narrationDiv").hide();
							} 
							else {
								e.preventDefault();
								$('.loader').fadeOut("slow");
								$('#Save').prop('disabled', false);
								var userAlertText = '';

								var flagQty = false;
								var flagQtyNull = false;
								var flagValue = false;
								var flagValueNull = false;
								
							if(alertFlagQty){
									if (!flagQty) {
										userAlertText = 'Please enter quantity to be moved less then the current quantity of the product.<br>';
										$("#toStoresproductdetailsId").hide();
										$("#narrationDiv").show();
										$("#toStorepanel").hide();
										$("#fromStorepanel").show();
										flagQty = true;
									}
							}
							if(alertFlagQtyNull){
									if (!flagQtyNull) {
										userAlertText += 'Please enter appropriate quantity to be moved for selected product.<br>';
										$("#toStoresproductdetailsId").hide();
										$("#narrationDiv").show();
										$("#toStorepanel").hide();
										$("#fromStorepanel").show();
										flagQty = true;
									}
								}
									
									if(alertFlagValue){
										if (!flagValue) {
											userAlertText = 'Please enter stock value to be moved less then the current stock value of the product.<br>';
											$("#toStoresproductdetailsId").hide();
											$("#narrationDiv").show();
											$("#toStorepanel").hide();
											$("#fromStorepanel").show();
											flagValue = true;
										}
								}
									
									if(alertFlagValueNull){
										if (!flagValueNull) {
											userAlertText = 'Please enter appropriate stock value to be moved for selected product.<br>';
											$("#toStoresproductdetailsId").hide();
											$("#narrationDiv").show();
											$("#toStorepanel").hide();
											$("#fromStorepanel").show();
											flagValueNull = true;
										}
								}
								}
								if (userAlertText != '') {
									bootbox.alert(userAlertText);
								}
							}
					}
				});*/

function sendDataServer(jsonInputData) {
	$('#Save').prop('disabled', true);
	$
			.ajax({
				url : "saveinventorydetailsbetweenstores",
				headers : {
					_csrf_token : $("#_csrf_token").val()
				},
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(jsonInputData),
				async : false,
				beforeSend : function() {
					$('.loader').show();
				},
				complete : function() {
					$('.loader').fadeOut("slow");
				},
				success : function(json, fTextStatus, fRequest) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}
					if (isValidToken(json) == 'No') {
						window.location.href = getCsrfErrorPage();
						return;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					
					if(json.access == 'ACCESS_DENIED'){
						bootbox.alert("Data is manipulated.", function() {
							window.location.href =  getCustomLogoutPage();
							return
						});
					}else if(json == null) {					
						bootbox.alert(json.errorcode+' '+json.message);
					}else{
						bootbox.alert(json.message, function() {
							
							return;
						});
					}
				},
				error : function(data, status, er) {
					getInternalServerErrorPage();
				}
			});
	$('#Save').prop('disabled', false);
}

function changeToLocation(currentgstin, currentlocation) {
	$('#show').show();
	$("#tolocation").empty();
	$("#tolocation").append('<option value="">Select</option>');
	$.each(gstinJson, function(i, value) {
		if (value.id == currentgstin) {
			$.each(value.gstinLocationSet, function(i2, value2) {
				if (value2.uniqueSequence != currentlocation) {
					$("#tolocation").append(
							$('<option>').text(value2.gstinLocation).attr(
									'value', value2.id));
				}

			});
		}
	});
}

function changeFromLocation(currentgstin,currentlocation) {
	$.each(gstinJson, function(i, value) {
		if (value.id == currentgstin) {
			$.each(value.gstinLocationSet, function(i2, value2) {
				if(value2.uniqueSequence==currentlocation){
					$("#fromlocationId").val(value2.id);
				}
			});
		}
	});
}

function changeToLocationMultipleGstn(fromgstin, togstin) {
	var fromgstin = $('select#fromgstin option:selected').val();
	var togstin = $('select#togstin option:selected').val();
	var currentlocation = $('select#fromlocation option:selected').val();
	if (fromgstin == togstin) {
		$("#tolocation").empty();
		$("#tolocation").append('<option value="">Select</option>');
		$.each(gstinJson, function(i, value) {
			if (value.id == fromgstin) {
				$.each(value.gstinLocationSet, function(i2, value2) {
					if (value2.uniqueSequence != currentlocation) {
						$("#tolocation").append(
								$('<option>').text(value2.gstinLocation).attr(
										'value', value2.id));
					}

				});
			}

		});
	} else {
		$("#tolocation").empty();
		$("#tolocation").append('<option value="">Select</option>');
		$.each(gstinJson, function(i, value) {
			if (value.id == togstin) {
				$.each(value.gstinLocationSet, function(i2, value2) {
					$("#tolocation").append(
							$('<option>').text(value2.gstinLocation).attr(
									'value', value2.id));
				});
			}
		});
	}
}

function validateSelect(id, spanid) {
	if ($("#" + id).val() === "") {
		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).show();
		$("#" + id).focus();
		return true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
		return false;
	}
}



function loadInventoryProduct(gstinNo,storeId,tDate) {
	$.ajax({
				url : "getgoodsbystoreidandcurrentdate",
				headers : {
					_csrf_token : $("#_csrf_token").val()
				},
				type : "POST",
				dataType : "json",
				data : {gstinNo:gstinNo, storeId : storeId, tDate : tDate},
				async : false,
				beforeSend : function() {
					$('.loader').show();
				},
				complete : function() {
					$('.loader').fadeOut("slow");
				},
				success : function(json, fTextStatus, fRequest) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}
					if (isValidToken(json) == 'No') {
						window.location.href = getCsrfErrorPage();
						return;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					console.log(json);
					if(json.access == 'ACCESS_DENIED'){
						bootbox.alert("Data is manipulated.", function() {
							window.location.href =  getCustomLogoutPage();
							return
						});
					}else if(json == '' || json == null){
						bootbox.alert("Please try after some time");
						$('#calculateStock').prop('disabled', false);
					}else if (json != null) {
						if (json.error == true) {
							bootbox.alert(json.message);
							$("#toStoreDiv").hide();
							$('#showbuttondiv').show();
							$('#narrationDiv').hide();
							$('#Save').hide();
						} else {
							$("#fromStoreproductDetailsId tbody tr").remove();
							if ($.fn.DataTable.isDataTable( '#fromStoreproductDetailsId' ) ) {
								  $('#fromStoreproductDetailsId').DataTable().destroy();
							}
							initializeFromTable();
							
							var counter = 0;
							var srno = counter + 1;
							$
									.each(
											json.result,
											function(i, value) {
												var uom;
												if (value.unitOfMeasurement === 'OTHERS') {
													uom = value.otherUOM;
												} else {
													uom = value.unitOfMeasurement;
												}
												fromStoreCheckboxTable.row
														.add(
																$('<tr id="tr_id_'+counter+'">'
																		+ '<td><input type="hidden" id="counter_'
																		+ counter
																		+ '" name="counter_'
																		+ counter
																		+ '" value="'
																		+ counter
																		+ '"></td>'
																		+ '<td style="width:20px;"><input class="editCheckBox" type="hidden" id="product_id_'
																		+ counter
																		+ '" name="product_id_'
																		+ counter
																		+ '" value="'
																		+ value.id
																		+ '">'
																		+ (counter + 1)
																		+ '</td>'
																		+ '<td><input type="hidden" id="product_name_'
																		+ counter
																		+ '" name="product_name_'
																		+ counter
																		+ '" value="'
																		+ value.productName
																		+ '">'
																		+ value.productName
																		+ '</td>'
																		+ '<td><input type="hidden" id="quantity_'
																		+ counter
																		+ '" name="quantity_'
																		+ counter
																		+ '" value="'
																		+ value.currentStock
																		+ '">'
																		+ checkNConvertToDecimal(value.currentStock)
																		+ '</td>'
																		+ '<td><input class="qtyToBeMoved qtyToBeMoved_'+counter+'" type="text"  id="qtyToBeMoved_'
																		+ counter
																		+ '" name="qtyToBeMoved_'
																		+ counter
																		+ '" value="" disabled></td>'
																		+ '<span class="text-danger cust-error qtyToBeMovedError" id="qtyToBeMoved-empty_'
																		+ counter
																		+ '"></span>'
																		+ '<td><input type="hidden" id="unitOfMeasurement_'
																		+ counter
																		+ '" name="unitOfMeasurement_'
																		+ counter
																		+ '" value="'
																		+ value.uom
																		+ '">'
																		+ '<input type="hidden" id="otherUOM_'
																		+ counter
																		+ '" name="otherUOM_'
																		+ counter
																		+ '" value="'
																		+ value.otherUOM
																		+ '">'
																		+ value.uom
																		+ '</td>'
																		+ '<td><input type="hidden" id="stockvalue_'
																		+ counter
																		+ '" name="stockvalue_'
																		+ counter
																		+ '" value="'
																		+ value.stockValue
																		+ '">'
																		+ checkNConvertToDecimal(value.stockValue)
																		+ '</td>'
																		+ '<td><input class="stkValueToBeMoved stkValueToBeMoved_'+counter+'" type="text" id="stkValueToBeMoved_'
																		+ counter
																		+ '" name="stkValueToBeMoved_'
																		+ counter
																		+ '" value="" disabled></td>'
																		+ '<span class="text-danger cust-error stkValueToBeMovedError" id="stkValueToBeMoved-empty_'
																		+ counter
																		+ '"></span>'
																		+ '</tr>'))
														.draw();
												counter++;
											});

							$("#fromStorepanel").show();
							$('#showProductGrid').show();
						}
					}
				},
				error : function(data, status, er) {
					getInternalServerErrorPage();
				}
			});
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
