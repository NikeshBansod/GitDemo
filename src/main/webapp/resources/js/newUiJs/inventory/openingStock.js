var MyUniqueID;
var gstinJson='';
var openingstockCheckboxTable;
var counter=0;

var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;

var selectedGstin = '';
var selectedLocation = '';

$(document).ready(function(){
	selectedGstin = $('#selectedGstin').val();
	selectedLocation = $('#selectedLocation').val();
	$('#showProductGrid').hide();
      var gstinJsonForUserData = fetchGstinDataForUserData();
      
      initializeDatatable();
      $('.loader').fadeOut("slow");

      $("#openingstockInventoryTab").on("change",".editQty", function () {
    	  $(this).val(roundToTwoDecimal(this.value));
    	if(!isNaN($(this).val())){
    		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
             var id=parents.parent().prev('tr').attr('id');
        	  if(id == undefined){                       // for desktop view ,this condition works
        		  var $row = $(this).closest("tr");
                  var id = $row.attr('id');
        	  }
        	  
              	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
              	//var quantity = $('#quantity_'+rowNo).val();
              	var quantity = $(this).val();
              	 $(".quantity_"+rowNo).val(quantity);
              	//parents.find('#purchaseRate_'+rowNo).css('background', 'red');
              	var purchaseRate = $('#purchaseRate_'+rowNo).val();
              	
              	 var amount = quantity * purchaseRate;
              	 $(".stockvalue_"+rowNo).val(amount.toFixed(2));
      	}else{
      		$(this).val('').focus();
      		
      	}
    	
      });
      
      $("#openingstockInventoryTab").on("change",".editStockValue", function () {
    	  $(this).val(roundToTwoDecimal(this.value));
      	if(!isNaN($(this).val())){
      		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
               var id=parents.parent().prev('tr').attr('id');
          	  if(id == undefined){                       // for desktop view ,this condition works
          		  var $row = $(this).closest("tr");
                    var id = $row.attr('id');
          	  }
          	  
                	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
                	//var quantity = $('#quantity_'+rowNo).val();
                	var quantityValue = $(this).val();
                	 $(".stockvalue_"+rowNo).val(quantityValue);
        	}else{
        		$(this).val('').focus();
        		
        	}
    
      });
      
      
      $('#openingstockInventoryTab .child input').on('change', function(){
          var parents = $(this).parents( "td.child");
          console.log(parents.parent().prev('tr').attr('id'));
});
      
      $(document).on('click','.paginate_button', function() {
    	  var isRowSelected = false;
			openingstockCheckboxTable.rows().every( function ( index, tableLoop, rowLoop ) {
				var rowX = openingstockCheckboxTable.row(index).node();
			    var row = $(rowX);
			   
			    var inventorystatus = row.find('.inventoryStatus_'+index).val(); 
			    if(inventorystatus == "Y"){
			    	$("#openingstockInventoryTab tbody .tr_id_"+index+" input:checkbox").attr('disabled',true).css('display','none');
			    }
			    
			    });
    	});

});

function initializeDatatable(){
    openingstockCheckboxTable = $('#openingstockInventoryTab').DataTable({
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
    $("#openingstockInventoryTab thead tr #checkboxtd input:checkbox").hide().attr('disabled',true);
   
}



function fetchGstinDataForUserData(){
         $.ajax({
                  url : "getgstinforloggedinuser",
                  method : "POST",
                  contentType : "application/json",
                  dataType : "json",
                  async : false,
                  headers: {
          			_csrf_token : $("#_csrf_token").val()
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
                        gstinJson=json.result;
                        $("#gstnStateId").empty();
                        if(json.error == true){
                        	bootbox.alert(json.message);
                        }else if(gstinJson.length == 1){
                              $("#gstnStateIdDiv").hide();
                              $.each(gstinJson,function(i,value) {
                                    $("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
                              });
                              
                              //if location length is 1 hide location else show location dropdown
                              if(gstinJson[0].gstinLocationSet.length == 1){
                                    
                                    $.each(gstinJson[0].gstinLocationSet,function(i,value) {
                                       $("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
                                          $("#locationStore").val(value.gstinStore);
                                    });
                              }else{
                                    $("#locationDiv").show();
                                    $("#location").append('<option value="">Select</option>');
                                    $.each(gstinJson[0].gstinLocationSet,function(i,value) {
                                       $("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
                                    });
                              }
                        }else{
                              $("#gstnStateIdDiv").show();
                              $("#gstnStateId").append('<option value="">Select</option>');
                              $.each(json.result,function(i,value) {
                                    $("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
                              });
                        }
                        setCsrfToken(fRequest.getResponseHeader('_csrf_token'));    
               },
               error: function (data,status,er) {
                   
                   getInternalServerErrorPage();   
               }
            });
}

$("#calculateStock").click(function(e){
	$('.loader').show();
	selectedGstin = $('#gstnStateId').val();
	selectedLocation = $('#location').val();
	/*$("#openingstockInventoryTab thead tr #checkboxtd input:checkbox").hide().attr('disabled',true);*/
	/*$('#checkboxtd').hide();*/
	$('#calculateStock').prop('disabled', true);
	
      errGSTIN= validateGSTIN();
      errLOCATION= validateLOCATION();
      
      function validateGSTIN(){
            errGSTIN = validateSelectField("gstnStateId","gstnStateId-csv-id");
            return errGSTIN;
      } 
      
      function validateLOCATION(){
            errLOCATION = validateSelectField("location","location-csv-id");
            return errLOCATION;
      } 
      
      if((errGSTIN)){
            focusTextBox("gstnStateId");
      }else if((errLOCATION)){
            focusTextBox("location");
      }else{
    	  getOpeningStockWithStoreId($("#location").val());
      }
      $('.loader').fadeOut("slow");
});

$("#gstnStateId").on("change",function (){
	$('#calculateStock').prop('disabled', false);	
	
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#openingstockInventoryTab").DataTable().rows().remove();
	}
      var gstinSelectedId= $('select#gstnStateId option:selected').val();
      $("#location").empty();
      $("#location").append('<option value="">Select</option>');
      $.each(gstinJson,function(i,value) {
            if(value.id == gstinSelectedId ){
                  $.each(value.gstinLocationSet,function(i2,value2) {
                      $("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
                  });               
            }
      });
});
$("#location").on("change",function (){
	$('#calculateStock').prop('disabled', false);
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#openingstockInventoryTab").DataTable().rows().remove();
	}
});

$('#checkboxtd').on('click', 'input[type="checkbox"]', function(e){
	
	var rowCount = $('#openingstockInventoryTab >tbody >tr').length;
	for(i=0;i<rowCount;i++){
		if(this.checked){
		$('#quantity_'+i).prop('disabled',false);
		$('#stockvalue_'+i).prop('disabled',false);
		}else{
			$('#quantity_'+i).prop('disabled',true);
			$('#stockvalue_'+i).prop('disabled',true);
		}
	}
});

function getOpeningStockWithStoreId(locationId){
	$.ajax({
		url : "fetchopeningstockwrtstoreid",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
		data : {locationId : locationId},
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
			console.log(json);
			$('.loader').show();
			$('#openingstockInventoryTab').DataTable().rows().remove();
			$('#openingstockInventoryTab').DataTable().destroy();
			initializeDatatable();	
			
			counter = 0;
			if(json.access == 'ACCESS_DENIED'){
				bootbox.alert("Data is manipulated.", function() {
					window.location.href =  getCustomLogoutPage();
					return
				});
			}else if(json == ''  || json == null){
				bootbox.alert("Please try after some time");
			}else if(json != null){
				if(json.error == true){
					bootbox.alert(json.message);
				} else if(json.status == 'accessDeny'){
					bootbox.alert("Data is been manipulated.", function() {
						window.location.href = getCustomLogoutPage();
				   });
					}
				else{
					 var srno=0;
				 	$.each(json.result,function(i,value){
				 		
				 		var uom;
				 		var srno=counter+1;
				 		if(value.unitOfMeasurement === 'OTHERS'){
				 			uom = value.otherUOM;
				 		}else{
				 			uom = value.unitOfMeasurement;
				 		}
				 		if(value.inventoryUpdateFlag == 'N'){
				 		openingstockCheckboxTable.row.add($(
					 			'<tr id="tr_id_'+counter+'" class="tr_id_'+counter+'">'
					 			+'<td><input type="hidden" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'"></td>'
							 	+'<td><input class="editCheckBox" type="hidden" id="product_id_'+counter+'" name="product_id_'+counter+'" value="'+value.id+'">'+srno+'</td>'
							 	+'<td><input type="hidden" id="product_name_'+counter+'" name="product_name_'+counter+'" value="'+value.name+'">'+value.name+'</td>'
							 	
							 	+'<td><input class="editQty quantity_'+counter+'" type="text" id="quantity_'+counter+'" name="quantity_'+counter+'" value="'+value.openingStock+'" disabled>'
							 		+'<input class="oldQty" type="hidden" id="oldquantity_'+counter+'" name="oldquantity_'+counter+'" value="'+value.openingStock+'"></td>'
							 		+'<input class="purchaseRate" type="hidden" id="purchaseRate_'+counter+'" name="purchaseRate_'+counter+'" value="'+value.purchaseRate+'"></td>'
							 	+'<td><input type="hidden" id="unitOfMeasurement_'+counter+'" name="unitOfMeasurement_'+counter+'" value="'+value.unitOfMeasurement+'">'
							 	+'<input type="hidden" id="otherUOM_'+counter+'" name="otherUOM_'+counter+'" value="'+value.otherUOM+'">'+uom+'</td>'
							 	+'<td><input class="editStockValue stockvalue_'+counter+'" type="text" id="stockvalue_'+counter+'" name="stockvalue_'+counter+'" value="'+value.openingStockValue.toFixed(2)+'"disabled>'
							 		+'<input class="oldStockValue" type="hidden" id="oldstockvalue_'+counter+'" name="oldstockvalue_'+counter+'" value="'+value.openingStockValue+'">'
							 		+'<input class="inventoryStatus inventoryStatus_'+counter+'" type="hidden" id="inventoryStatus'+counter+'" name="inventoryStatus'+counter+'" value="'+value.inventoryUpdateFlag+'">'
							 		+'<span class="text-danger cust-error" id="stockvalue-error_'+counter+'" style="display:none;"> Stock Value cannot be empty</span></td>'
							 	+'</tr>'					 	
					 	)).draw();
				 		}else{
				 			MyUniqueID=counter;
				 		    openingstockCheckboxTable.row.add($(
					 			'<tr id="tr_id_'+counter+'" class="tr_id_'+counter+'">'
							 	+'<td><input type="hidden" id="counter_'+counter+'" name="counter_'+counter+'" value="'+(counter+1)+'"></td>'
							 	+'<input class="inventoryStatus inventoryStatus_'+counter+'" type="hidden" id="inventoryStatus'+counter+'" name="inventoryStatus'+counter+'" value="'+value.inventoryUpdateFlag+'">'
							 	+'<td>'+srno+'</td>'
							 	+'<td >'+value.name+'</td>'
							 	+'<td>'+value.openingStock+'</td>'
							 	+'<td>'+value.unitOfMeasurement+'</td>'
							 	+'<td>'+value.openingStockValue.toFixed(2)+'</td>'
							 	+'</tr>'					 	
					 	)).draw();
				 		 $("#openingstockInventoryTab tbody .tr_id_"+counter+" input:checkbox").attr('disabled',true).css('display','none');
				 		}
						counter++;
						
					});
					$('#showProductGrid').show();
				 	$('.loader').fadeOut("slow");
				}
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}


//for field enable/disable
$('#openingstockInventoryTab tbody').on('click', 'input[type="checkbox"]', function(e){
	if(this.checked){
		 this.parentNode.parentNode.children[3].firstElementChild.disabled=false;
		 this.parentNode.parentNode.children[6].firstElementChild.disabled=false;
		 $(this).parent().parent().next('tr.child').find('.editQty').removeAttr('disabled');
		 $(this).parent().parent().next('tr.child').find('.editStockValue').removeAttr('disabled');
	}else{
		 this.parentNode.parentNode.children[3].firstElementChild.disabled=true;
		 this.parentNode.parentNode.children[6].firstElementChild.disabled=true;
		 $(this).parent().parent().next('tr.child').find('.editQty').attr('disabled', 'true');
		 $(this).parent().parent().next('tr.child').find('.editStockValue').attr('disabled', 'true');
	}
});

$('#checkboxtd').on('click', 'input[type="checkbox"]', function(e){
	
	var rowCount = $('#openingstockInventoryTab >tbody >tr').length;
	for(i=0;i<rowCount;i++){
		if(this.checked){
		$('#quantity_'+i).prop('disabled',false);
		$('#stockvalue_'+i).prop('disabled',false);
		}else{
			$('#quantity_'+i).prop('disabled',true);
			$('#stockvalue_'+i).prop('disabled',true);
		}
	}
});


$('#Save').on('click', function(e){
	$('.loader').show();
	var blankMsg="This field is required";
	var gstin = $('#gstnStateId').val();
	var location = $('#location').val();
	
	var alertFlag = false;
	
	var alertFlagQty = false;
	var alertTextQty = '';
	
	var alertFlagQtyNull = false;
	var alertTextQtyNull = '';
	
	var alertFlagValue = false;
	var alertTextValue = '';
	
	var alertFlagValueNull = false;
	var alertTextValueNull = '';
	
	var arrayFlag = [];
	
	var errFlagGstin = validateSelect("gstnStateId","gstnStateId-csv-id");
	var errFlagLocation = validateSelect("location","location-csv-id");
	
	if(errFlagGstin || errFlagLocation ){
		e.preventDefault();	
		$('#Save').prop('disabled', false);
		$('.loader').fadeOut("slow");
	}else{
		var rows_selected = openingstockCheckboxTable.column(0).checkboxes.selected();
		if(rows_selected.length == 0){
			$('#Save').prop('disabled', false);
			$('.loader').fadeOut("slow");
			bootbox.alert('Please select atleast 1 Product.');
		}else{
			var productObject;
			var productRowArray = new Array();
			var alertText = '';
			//rnd -start
			var isRowSelected = false;
			openingstockCheckboxTable.rows().every( function ( index, tableLoop, rowLoop ) {
				var rowX = openingstockCheckboxTable.row(index).node();
			    var row = $(rowX);
			    isRowSelected = false;
			    var $checkbox = row.find('td:first-child input[type="checkbox"]')
			    if($checkbox.is(':checked')){
			       isRowSelected = true;
			    } 
			    // Get the current quantity
			    if(isRowSelected){
			    var quantity = row.find('.quantity_'+index).val(); 
				console.log('.quantity_'+index +" : "+quantity);
				var currentQty = row.find(".quantity_"+index).val();
				
				var oldQty = row.find("#oldquantity_"+index).val()
				var modifiedQty = '';

				var currentStockValue = row.find(".stockvalue_"+index).val();
				var oldStockValue = row.find("#oldstockvalue_"+index).val()
				var modifiedStockValue = '';
				
				if(currentQty != '' && currentQty != null && oldQty != '' && oldQty != null){
					if(currentQty != oldQty){
						if(currentQty > oldQty)
							modifiedQty = currentQty - oldQty;
						else 
							modifiedQty = oldQty - currentQty;			
					}else{
						$('#quantity_'+index).addClass("input-error").removeClass("input-correct");
						arrayFlag.push('qty_'+index);
						alertFlag = true;
						alertFlagQty = true;
						return false;
					}		
				}else{
					$('#quantity_'+index).addClass("input-error").removeClass("input-correct");
					arrayFlag.push('qtyNul_'+index);
					alertFlag = true;
					alertFlagQtyNull = true;
					return false;						
				}
				
				if(!alertFlagQty && !alertFlagQtyNull /*&& !alertFlagValue && !alertFlagValueNull*/){	
					productObject = new Object();
					productObject.id = row.find("#product_id_"+index).val();
					productObject.name = row.find("#product_name_"+index).val();
					productObject.unitOfMeasurement = row.find("#unitOfMeasurement_"+index).val();
					productObject.otherUOM = row.find("#otherUOM_"+index).val();
					productObject.openingStock = row.find("#quantity_"+index).val();
					productObject.currentStock = row.find("#quantity_"+index).val();
					productObject.currentStockValue = parseFloat(row.find("#stockvalue_"+index).val()).toFixed(2);
					productObject.openingStockValue = parseFloat(row.find("#stockvalue_"+index).val()).toFixed(2);
					productObject.storeId = $("#location").val();
					
					productRowArray.push(productObject);
				}			
			    }
			}); 
				
			//rnd -end
			
			/*var table_data = openingstockCheckboxTable.rows().data();
			table_data.each(function(value,index){	*/
				/*if(rows_selected.indexOf(openingstockCheckboxTable.row(index).data()[0]) != -1){*/
					
				/*}*/		
			/*});*/	
			
			if(!alertFlag){
				var jsonInputData = {
						"productList" : JSON.parse(JSON.stringify(productRowArray)),
				}
				console.log(jsonInputData);
				sendDataServer(jsonInputData);	
			}else{
				e.preventDefault();	
				$('.loader').fadeOut("slow");
				$('#Save').prop('disabled', false);
				
				if(alertFlag)
					bootbox.alert('Please change quantity for selected product');
			}			
		}
	}	
	  $('.loader').fadeOut("slow");
});

$(document).on('keyup',".editQty", function(){
	this.value = this.value.replace(/[^0-9.]+/, '');
	if (currencyRegex.test($(this).val())!=true){
		$(this).addClass("input-error");
	}else{
		$(this).removeClass("input-error");
	}	
});

$(document).on('keyup',".editStockValue", function(){
	this.value = this.value.replace(/[^0-9.]+/, '');
	if (currencyRegex.test($(this).val())!=true){
		$(this).addClass("input-error");
	}else{
		$(this).removeClass("input-error");
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
		url : "saveopeningstockproductlist",
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
				$('.loader').fadeOut("slow");
				bootbox.alert(json.message, function() {
					 if(json.error == true){
							$('#Save').prop('disabled', false);
						}else if(json.status == 'accessDeny'){
							bootbox.alert("Data is been manipulated.", function() {
								window.location.href = getCustomLogoutPage();
						   });}else{
								/*window.location.href = "./getopeningstock";*/
							/*backToLoadedOpeningStockPage(selectedGstin,selectedLocation);*/
							getOpeningStockWithStoreId(selectedLocation);					
						}
						return;
				   });
			}
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}




           
 

