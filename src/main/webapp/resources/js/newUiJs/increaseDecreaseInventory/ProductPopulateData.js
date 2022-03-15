var productTaxRateJson = '';
var productUnitOfMeasurementJson = '';
var blankMsg="This field is required";
var openingStockRowNum = 0;
var dynamicallyAddedProductJson = '';
var regMsg = "data should be in proper format";
var dny_prod_length = 2;
var dny_prod_lengthMsg = "Minimum length should be ";
var dny_prod_regMsg = "Goods Rate should be in proper format";
var dny_prod_igstMsg = "Rate of tax (%) should be in proper format";
var dny_prod_currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var dny_prod_percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var dny_prod_productIgstMsg='Rate of Tax for service should me less than 100';
var rowNum = 0;
var accordionsId="";
var userProductList = '';
var $toggle = $("#toggle");
var editRecordFlag = false;

$(document).ready(function(){
	
	$("#finalSave").hide();
	
	$("#quantity").keyup(function() {
			var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
			this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');      
	 });
	 
	 $("#quantity").on("keyup click", function(){
			if ($("#quantity").val().length > 0){
				 $("#quantity").addClass("input-correct").removeClass("input-error");
				 $("#current-quantity-csv-id").hide();		 
			}

			if (($("#quantity").val().length < 1) || ($("#quantity").val() <= 0)){
				 $("#quantity").addClass("input-error").removeClass("input-correct");
				 $("#current-quantity-csv-id").text('This field is required.');	
				 $("#current-quantity-csv-id").show();	
				 $("#quantity").focus();
			}
		});
	

	 $("#stockValue").keyup(function() {
			var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
			this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');      
	 });
	 
	 $("#stockValue").on("keyup click", function(){
			if ($("#stockValue").val().length > 0){
				 $("#stockValue").addClass("input-correct").removeClass("input-error");
				 $("#stockValue-csv-id").hide();		 
			}

			if (($("#stockValue").val().length < 1) || ($("#stockValue").val() <= 0)){
				 $("#stockValue").addClass("input-error").removeClass("input-correct");
				 $("#stockValue-csv-id").text('This field is required.');	
				 $("#stockValue-csv-id").show();	
				 $("#stockValue").focus();
			}
		});

});	 

/*Adding product	 */

$("#service_add,#service_edit").on( "click", function(e) {
	
	$('#service_add').prop('disabled', false); 
	$('#service_edit').prop('disabled', false);
	var productNameFlag = validateSelectField("search-product-autocomplete","search-ser-prod-autocomplete-csv-id");
	var quantityFlag = validateTextField("quantity","current-quantity-csv-id",blankMsg);
	var stockValueFlag = validateTextField("stockValue","stockValue-csv-id",blankMsg);
	var uomFlag = validateTextField("uomtoShow","unitOfMeasurement-csv-id",blankMsg);
	
	var gstinErrFlag = validateSelectField("gstin","gstin-csv-id");
	var locationErrFlag = validateSelectField("location","location-csv-id");
	var reasonErrFlag = validateSelectField("reason","reason-csv-id");
	var errFlagTransactionDate = validateTextField("transactionDate","transactionDate-csv-id",blankMsg);
	
	
	if (productNameFlag || quantityFlag  || stockValueFlag || gstinErrFlag || locationErrFlag || reasonErrFlag || errFlagTransactionDate||uomFlag){ 
		e.preventDefault();
		
		$('#service_add').prop('disabled', false);
		$('#service_edit').prop('disabled', false);
		
	}
	else{
		
		if(this.id == 'service_add'){
			add_service_row();
		}
		
		if(this.id == 'service_edit'){
			update_service_row();
		}
	}
	
});

function split2(val) {
    return val.split(/,\s*/);
}

function extractLastRW(term) {
    return split2(term).pop();
}

function cancel_service_row(){
	
	resetFormValues();
	
}

/*loading product name after entering 3 latter */

$("#search-product-autocomplete").autocomplete({
    source: function (request, response) {
        $.getJSON("getInventoryProductNameList", {
            term: extractLastRW(request.term),
            gNo : $("#gstin").val(),
            location :  $('#locationId').val(),
            documentType : $("#documentType").val()
        }, function( data, status, xhr ){
        	response(data);
			setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		});
        
        if (isValidSession(response) == 'No') {
			window.location.href = getDefaultSessionExpirePage();
			return;
		}
        
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
    	var zipToShow = $("#search-product-autocomplete").val();
    	//bootbox.alert"Blank response : zipToShow :"+zipToShow);
    	
	        if (ui.content.length === 0) {
	        	
	        		 $("#search-product-autocomplete").addClass("input-error").removeClass("input-correct");
	        		 //$("#dny-empty-message").text("No results found for selected pin code : "+zipToShow);
	        		 $("#dny-product-no-records-found").show();
	                 $("#search-product-autocomplete").val("");
	        } else {
	            $("#dny-product-no-records-found").hide();
	        }
    	
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        
        dynamicallyAddedProductJson = fetchDynamicallyAddedProduct($("#locationId").val(),value.trim());
		
		if(dynamicallyAddedProductJson){
			changeServiceNameAsPerAutoComplete(dynamicallyAddedProductJson);
			$("#search-product-autocomplete").addClass("input-correct").removeClass("input-error");
    		$("#dny-product-no-records-found").hide();
    		$("#search-ser-prod-autocomplete-csv-id").hide();
		}
         return false;
    }
});

function changeServiceNameAsPerAutoComplete(selectedJson){
	
	setValueInSelectServiceName(selectedJson);
    setValueInAddServiceProductFormFields(selectedJson);
}

function setValueInAddServiceProductFormFields(json){
	
		$("#search-product-autocomplete").val(json.name);
		$("#uom").val(json.unitOfMeasurement);
		$("#oldQuantity").val(json.currentStock);
		$("#oldStockValue").val(json.currentStockValue);
		
		// $("#uomtoShow").val(json.productRate+" Rs per "+json.unitOfMeasurement);
		$("#uomtoShow").val(json.unitOfMeasurement);
		 $('#curr_stockValueLabel').html("");
		 $('#current_quantity').text("Current Qty : "+json.currentStock);
		 $('#curr_stockValueLabel').text("Current Stock Value : "+json.currentStockValue);
		
		 
		 if ($("#uomtoShow").val().length > 0){
			 $("#uomtoShow").addClass("input-correct").removeClass("input-error");
			 $("#unitOfMeasurement-csv-id").hide();		 
		}
		 
		
	
}

//fuction for featching data from database(like Current Qty,Current Stock Value,Unit Of Measurement etc )
function fetchDynamicallyAddedProduct(storeId,productName){
	var resp = '';
	  $.ajax({
          url:"getProductByProductNameAndStoreId",     
          type : "POST",
          data : {"storeId": storeId , "productName":productName,_csrf_token : $("#_csrf_token").val()},
          dataType: 'json',
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
                setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                dynamicallyAddedProductJson = json;
                resp = json;
                
                
          },
          error: function (data,status,er) {
                    
                     getInternalServerErrorPage();   
          }
	  });
   return resp;
}

function add_service_row(){ 
	$(document).ready(function(){ 
			rowNum++;
			
			
			//get variables 
			var serviceNameSelectedTextValue = $("#service_name").find("option:selected").text();
			var serviceNameVal = $('#service_name').val();
			var uomValue = $('#uomtoShow').val();
			var stockValue = $('#stockValue').val();
			var quantityValue = $('#quantity').val();
			var Old_QuantityValue = $('#oldQuantity').val();
			var Old_stockValue = $('#oldStockValue').val();
			var narration  = $('#narration').val();
			if(rowNum>0){
				
				$("#finalSave").show();
			}
			
	//check if calculation based on - Start 
			var uomDisplay = 'none';
			var quantityDisplay = 'block';
        	var $toggle = $("#toggle");
        	var recordNo = rowNum;
        	
        	$toggle.append('<div class="cust-content" style="width:auto;margin: 2em 0 0 0;" id="service_start_'+recordNo+'">'
	        				+'<div class="heading">'
				                +'<div class="cust-con">'
				                    +'<h1 id="service_name_'+recordNo+'">'+serviceNameSelectedTextValue+'</h1>'
				                +'</div>'
				                +'<div class="cust-edit">'
				                    +'<div class="cust-icon">'
				                    	+'<a href="#callOnEditId" onclick="javascript:edit_service_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
				                    	+'<a href="#" onclick="javascript:remove_service_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
				                    +'</div>'
				                +'</div>'
				            +'</div>'
				            +'<div class="content">'
				                +'<div class="cust-con">'
				                    +'<p id="uom_'+recordNo+'" >Unit Of Measurement : '+uomValue+' </p>'
				                    +'<p id="quantity_'+recordNo+'" >Quantity : '+quantityValue+'</p>'
				                    +'<p id="stockValue_'+recordNo+'">Stock Value : '+stockValue+'</p>'
				                
				                    
				                +'</div>'
				                +'<input type="hidden" id="service_name-'+recordNo+'" name="" value="'+serviceNameVal+'">'
				                +'<input type="hidden" id="stockValue-'+recordNo+'" name="" value="'+stockValue+'">'
				                +'<input type="hidden" id="uom-'+recordNo+'" name="" value="'+uomValue+'">'
				                +'<input type="hidden" id="quantity-'+recordNo+'" name="" value="'+quantityValue+'">'
				                +'<input type="hidden" id="service_name_textToShow-'+recordNo+'" name="" value="'+serviceNameSelectedTextValue+'">'
				                +'<input type="hidden" id="old_quantity-'+recordNo+'" name="" value="'+Old_QuantityValue+'">'
				                +'<input type="hidden" id="old_stockValue-'+recordNo+'" name="" value="'+Old_stockValue+'">'
				                
				               
				            +'</div>'
        				+'</div>');
        	
        
		openCloseAccordion(rowNum);
		resetFormValues();
    	//calculateTaxForRemainingFields();
    	//loadBillMethod();
 	});  
}
	
function openCloseAccordion(rowNum){
	var currId = "/"+rowNum;
	//alert("accordionsId ->"+accordionsId);
	if(accordionsId.includes(currId)){
		
	}else{
		$("#service_start_"+rowNum+" .content").hide();
		$("#service_start_"+rowNum+" .heading").click(function () {
			$(this).next(".content").slideToggle();
		});	
		accordionsId = accordionsId +","+currId;
	}
	
}

function clearInputErrorClass(id,spanid){
	$("#"+id).removeClass("input-error").addClass("");
	$("#"+spanid).css("display","none");
	
}


function resetFormValues(){
	$('#search-product-autocomplete').val("");
	$('#service_name').val("");
	
	$('#uom').val("");
	$('#uomtoShow').val("");
	$('#stockValue').val("");
	$('#quantity').val("");
	$('#current_quantity').text("Current Qty ");
	$('#curr_stockValueLabel').text("Current Stock Value ");
	
	 
	  	
	  	//clear input errors
	    clearInputErrorClass("search-product-autocomplete","search-ser-prod-autocomplete-csv-id");
	    clearInputErrorClass("quantity","current-quantity-csv-id");
	    clearInputErrorClass("uomtoShow","unitOfMeasurement-csv-id");
	    clearInputErrorClass("stockValue","stockValue-csv-id");
}

	  function remove_service_row(recordNo){
	    	
	    	   $('#service_start_'+recordNo).remove();
	    	   resetFormValues();
	    	   accordionsId = accordionsId.replace(",/"+recordNo, "");
	    	   var totalChildRecordNo = $toggle.children().length;
	    	 
	    	   if(totalChildRecordNo == 0)
	    		   {
	    		   		$("#finalSave").hide();//Hide the submit button
	    		   		$("#service_add").show();//Hide the add button
	    		    	$("#service_edit").hide();//display the edit button
	    		   }
	    	   else{
	    		    
	    		    $("#service_add").show();//Hide the add button
   		    		$("#service_edit").hide();//display the edit button 
	    	   }
	  
	  }
	    
	    function edit_service_row(recordNo){ 
	    	
	    	editRecordFlag=true;
	    	$("#service_add").hide();//Hide the add button
	    	$("#service_edit").show();//display the edit button
	    	$('#search-product-autocomplete').val($('#service_name_textToShow-'+recordNo).val());
	    	    	
	    	$('#service_name').val($('#service_name-'+recordNo).val());
	    	$('#stockValue').val($('#stockValue-'+recordNo).val());
	    	$('#uom').val($('#uom-'+recordNo).val());
	    	$('#uomtoShow').val($('#uom-'+recordNo).val());
	    	$('#quantity').val($('#quantity-'+recordNo).val());
	    	$('#unqValId').val(recordNo);
	   	 	$('#current_quantity').text("Current Qty : "+$('#old_quantity-'+recordNo).val());
	   	 	$('#curr_stockValueLabel').text("Current Stock Value : "+$('#old_stockValue-'+recordNo).val());
	    }
	    
	    function update_service_row(){ 
	    	
		    	var recordNo = $('#unqValId').val();
		    	var uomDisplay = 'none';
				var quantityDisplay = 'block';
		    	//get variables 
				var serviceNameSelectedTextValue = $("#service_name").find("option:selected").text();
				var serviceNameVal = $('#service_name').val();
				var uomValue = $('#uom').val();
				var stockValue = $('#stockValue').val();
				var quantityValue = $('#quantity').val();
				
		
		
				
				
				//show in display
		    	$('#service_name_'+recordNo).text(serviceNameSelectedTextValue);
		    	$('#uom_'+recordNo).text("Unit Of Measurement : "+uomValue);
		    	$('#stockValue_'+recordNo).text("Stock Value : "+stockValue);
		    	$('#quantity_'+recordNo).text("Quantity : "+quantityValue);
		    	


		    	//set values in hidden fields
		    	$('#service_name-'+recordNo).val(serviceNameVal);
		    	$('#uom-'+recordNo).val(uomValue);
		    	$('#stockValue-'+recordNo).val(stockValue);
		    	$('#quantity-'+recordNo).val(quantityValue);
		    	$('#service_name_textToShow-'+recordNo).val(serviceNameSelectedTextValue);
		    
		
		    	$("#service_edit").hide();//Hide the edit button
		    	$("#service_add").show();//display the add  button  
		    	
		    	
		    openCloseAccordion(recordNo);
	    	resetFormValues();//reset form values
	    	//calculateTaxForRemainingFields();
	    	//loadBillMethod();
	    	
	    }
	    
	    function validateQuantity(id, spanid, msg) {
	    	var result = false;
	    	if($("#" + id).val() <= 0){

	    		$("#" + id).addClass("input-error").removeClass("input-correct");
	    		$("#" + spanid).text(msg);
	    		$("#" + spanid).show();
	    		result = true;
	    	} else {
	    		$("#" + id).addClass("input-correct").removeClass("input-error");
	    		$("#" + spanid).hide();
	    	}
	    	
	    	return result;
	    }
	    
	   
 function setValueInSelectServiceName(selectedJson){
			var json = '';
			getProductList();
			json = userProductList;

		
		$('#service_name').empty();
		$('#service_name').append('<option value="">Select</option>');	
		$.each(json,function(i,value) {
			if(selectedJson.id == value.id){
				$('#service_name').append($('<option>').text(value.name).attr('value',value.id).attr('selected','selected')); 
			 }else{
				 $('#service_name').append($('<option>').text(value.name).attr('value',value.id));
			 }
			
		});
	}
 
	    
	    
	    function getProductList(){
	    
	    		var defaultDocumentType = $("#selectedDocType").val();
	    		var urlToFetchProduct = 'inventoryAddProductList';
	    		var gNo = $("#gstin option:selected").text().split('[')[0].trim();
	           var location = $('select#location option:selected').val();
	    		//var location='3';
	    			 $(document).ready(function(){
	    				    $.ajax({
	    						url : urlToFetchProduct,/*"getProductsList",*/
	    						method : "post",
	    						dataType : "json",
	    						data : { _csrf_token : $("#_csrf_token").val(), gNo : gNo , location : location },
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
	    function checkIfGstinAndLocationIsSelected(){
	    	var isSelected = false;
	    	
	    	//fetch gstin number like 27XXXXX1234A1ZQ[Maharashtra]
	    	 var gstnWithStateInString = $("#gstin option:selected").text();
	    	 var gstnStateIdInString = (gstnWithStateInString.split('[')[0]).trim();
	    	 var gstnStateId = $('select#gstin option:selected').val();
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
	 
	$('#finalSave').on('click', function(e){
	 $('.loader').show();
	    	
	    	  var $toggle = $("#toggle");
	    	  var totalRecordNo = $toggle.children().length;
	   	   	  var productJsonObject;
	   	   	  var gstin = $('#gstin').val();
	   	   	  var locationUniqueSequence = $('#location').val();
	   	   	  var location = $('#locationId').val();
	   	   	  var reason = $('#reason').val();
	   	   	 // var  narration="";
	   	   	  var transactionDate = $('#transactionDate').val();
	   	   	  var narration =$('#narration').val();
	   	   	  
			  var alertFlag = false;	
			  var alertFlagQty = false;	
			  var alertFlagQtyNull = false;	
			  var alertFlagValue = false;	
			  var alertFlagValueNull = false;
	   	   	  
	    	
	   	   var productListArray = new Array();
		   for (i = 0; i < totalRecordNo; i++) { 
			  
			   
				 //Start
				 var index2 = $toggle.children()[i].id.lastIndexOf("_");
				 var num2 = $toggle.children()[i].id.substring(index2);
				 	 num2 = num2.replace("_","-");
					var newQty = $("#quantity"+num2).val();
					var oldQty = $("#old_quantity"+num2).val();
					var modifiedQty = '';

					var newStockValue = $("#stockValue"+num2).val();
					var oldStockValue = $("#old_stockValue"+num2).val();
					var modifiedStockValue = '';
				 
					if(newQty != '' && newQty != 0.00 && newQty != null && !isNaN(newQty) && (oldQty != '' || oldQty == 0) && oldQty != null && !isNaN(oldQty)){
					
							if(inventoryType === 'InventoryIncrease'){
								
								modifiedQty = (newQty* 100 + oldQty* 100) / 100 ;
							}else if(inventoryType === 'InventoryDecrease'){							
								
								modifiedQty = (oldQty* 100 - newQty* 100) / 100 ;
							}								
							
					}else{
						$('#quantity'+index).addClass("input-error").removeClass("input-correct");
						alertFlag = true;
						alertFlagQtyNull = true;
						return false;						
					}
					
					if(newStockValue != '' && newStockValue != null  && !isNaN(newStockValue) && oldStockValue != '' && oldStockValue != null){
						
							if(inventoryType === 'InventoryIncrease')
								modifiedStockValue = (parseFloat(newStockValue)* 100 + parseFloat(oldStockValue) * 100) / 100;
							else
								modifiedStockValue = (parseFloat(oldStockValue)* 100 - parseFloat(newStockValue) * 100) / 100;
							
					}else{
						$('#stockValue'+index).addClass("input-error").removeClass("input-correct");
						alertFlag = true;
						alertFlagValueNull = true;
						return false;						
					}
				
					
					
				 productJsonObject = new Object();
				 productJsonObject.id = $("#service_name"+num2).val();
				 productJsonObject.name = $("#service_name_textToShow"+num2).val();
				 productJsonObject.unitOfMeasurement = $("#uom"+num2).val();
				 productJsonObject.otherUOM = $("#uom"+num2).val();
				 productJsonObject.currentStock = checkNConvertToDecimal(modifiedQty);
				 productJsonObject.currentStockValue = parseFloat(modifiedStockValue).toFixed(2);
				 productJsonObject.storeId = location;
				 productJsonObject.modifiedQty = newQty;
				 productJsonObject.modifiedStockValue = parseFloat(newStockValue).toFixed(2);
				 productJsonObject.transactionDate = transactionDate;
				
				 productListArray.push(productJsonObject);
				 //End
		   
		   }
	    	
		   
		   if(!alertFlag){
				var jsonInputData = {
						"inventoryType" : inventoryType,
						"productList" : JSON.parse(JSON.stringify(productListArray)),
						"narration" : narration,
						"reason" : reason
				}
				sendDataServer(jsonInputData);	
	
		   }
	
	
	
	});
