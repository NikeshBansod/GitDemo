var openingStockRowNum = 0;
var accordionsId = '';
var advolCessRateJson = '';
var nonAdvolCessRateJson = '';
var userGstinJson = '';

var productExists = false;
var blankMsg="This field is required";
var length = 2;
var lengthMsg = "Minimum length should be ";
//var currencyRegex = /(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;
var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var regMsg = "Goods Rate should be in proper format";
var igstMsg = "Rate of tax (%) should be in proper format";
var percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var productIgstMsg='Rate of Tax for service should me less than 100';
var dnyGstinOpeningstockProductTable='';
var gstinsInventoryJson = '';
var editPage = $("#editPage").val();
var productForEditJson='';
jQuery(document).ready(function($){
	
	dnyGstinOpeningstockProductTable = $('#dny-gstin-openingstock-product').DataTable({
		 rowReorder: {
		        selector: 'td:nth-child(2)'
		 },
		 responsive: true,
		 searching: false,
		 /*bPaginate: false,*/
		 bLengthChange: false,
	});
	
	loadGstinsInventory();
	setDataInOpeningStockProduct();
	if(editPage == 'true'){
	
		/*var productName=${productObj.name}*/
		var productName=$("#name").val();
		var prod=$("#prod").val();
		setDataInOpeningStockProductForEditPage(productName,prod);
	}
	 $('.loader').fadeOut("slow"); 
	 $("#addProductDetails").hide();
     $('#addheader').hide();
     
     $('#addProductButton').on('click', function(e) {
         $('.loader').show();
     	 $('#addProductDetails').slideToggle();
    	 $('#listheader').hide();
     	 $('#listTable').hide();
     	 $('#addProductButton').hide();
     	 $('#addProductdiv').hide();
     	 $('#addheader').show();
     	 $('.loader').fadeOut("slow"); 
     	 e.preventDefault();
     });
     
     $('#gobacktolisttable').on('click', function(e){
         $('.loader').show();
    	 $('#listheader').show();
     	 $('#listTable').slideToggle();
 	   	 $('#addProductButton').show();
 	   	 $('#addheader').hide();
 	     $('#addProductDetails').hide();
 	     $('.loader').fadeOut("slow"); 
     }); 
});

$(document).ready(function(){

	$("#prodSubmitBtn").click(function(e){
		var errorStatus = true;
		var errHsndesc = hsnDesc();
		var errHsnCode = hsnCode();
		var errhsnProductName = hsnProductName();
		var errhsnProductIgst = hsnProductIgst();
		
		var errhsnProductRate = hsnProductRate();
		var errhsnUOM = hsnUOM();
		var errOtherUOM = false;
		if($("#unitOfMeasurement").val()=="OTHERS"){
			    errOtherUOM=validateOtherUOM();
			    $("#tempUom").val($("#unitOfMeasurement").val()+'-'+'('+$("#otherUOM").val()+')');
	    }
		
		var errhsnProductBuyingRate = hsnProductBuyingRate();
		var errpurchaseUOM = false;//purchaseUOM();
		var errotherPurchaseUOM = false;
	    /*if($("#purchaseUOM").val()=="OTHERS"){
		    errotherPurchaseUOM=validateOtherPurchaseUOM();
		    $("#tempUom").val($("#purchaseUOM").val()+'-'+'('+$("#purchaseOtherUOM").val()+')');
	    }*/
		 
		if((errHsndesc) || (errHsnCode) || (errhsnProductName) ||  (errhsnUOM) || (errpurchaseUOM)||(errpurchaseUOM)||(errhsnProductBuyingRate)||(errhsnProductRate) || (errhsnProductIgst) || (productExists) || (errOtherUOM)||(errotherPurchaseUOM)){
			 e.preventDefault();	
		}else{
			//submit form as no errors occured
			var totalOpeningStockRecordNo = $("#toggleOpeningStock").children().length;
			var editPage = $("#editPage").val();
			if(editPage != 'true'){
			  /*if(totalOpeningStockRecordNo == 0){
				 bootbox.alert("Please add atleast one record in Opening Stock");
				 e.preventDefault();
			  }else{*/
				 errorStatus = false;
				 callProductFormSubmit();
				 e.preventDefault();
			//}	
			}
			
		}
		 if((errHsndesc) || (errHsnCode)){
			 focusTextBox("search-hsn");
		 } else if((errhsnProductName) ){
			 focusTextBox("name");
		 } else if((errhsnUOM)){
			 focusTextBox("unitOfMeasurement");
		 } else if((errhsnProductRate)){
			 focusTextBox("#productSellingRate");
		 } else if((hsnProductIgst)){
			 focusTextBox("productIgst");
		 }else if((errOtherUOM)){
			 focusTextBox("otherUOM");
		 }else if((errhsnProductBuyingRate)){
			 focusTextBox("#productBuyingRate");
		 }else if((errpurchaseUOM)){
			 focusTextBox("purchaseUOM");
		 }else if((errotherPurchaseUOM)){
			 focusTextBox("purchaseOtherUOM");
		 }
		 
	});
	
	$("#openingStockCancelBtn").on( "click", function(e){
		resetOpeningStockFormValues();
	});
	
	$("#openingStockSaveBtn,#openingStockEditBtn").on( "click", function(e){
		var isValidationDone = false;
		var openingStockGstin = validateSelectField("gstin","opening-stock-gstin");
		var openingStockLocation = validateSelectField("locationStore","opening-stock-location");
		var openingStockQuantity ='';				 /*validateTextField("open_stock_qty","opening-stock-quantity","Please enter quantity");*/
		var openingStockValue=''; 						/*validateTextField("open_stock_stock_value","opening-stock-stock-value","Enter Stock Value");*/
		if((openingStockGstin) || (openingStockLocation)){
			e.preventDefault();
		}else{
			//alert("Validation complete");
			var openingStockGstinText = $("#gstin").find("option:selected").text();
			var openingStockLocationText = $("#locationStore").find("option:selected").text();
			if(checkIfStockExistsForGstinAndLocation()){
				if(this.id == 'openingStockSaveBtn'){
					e.preventDefault();
					bootbox.confirm("Opening Stock already Added for GSTIN-"+openingStockGstinText+" and Store-"+openingStockLocationText+". Do you want to change it ?", function(result){
						 if (result){
							//override the values in opening stock	
							 overrideExsitingStockvalue();
							 resetOpeningStockFormValues();
							 e.preventDefault();
						 }else{
							 //donot override and discard
							 resetOpeningStockFormValues();
							 e.preventDefault();
						 }
						
					});
				}else{ 
					//bootbox.alert("WARNING !!! You cannot change Opening Stock for GSTIN-"+openingStockGstinText+" and Store-"+openingStockLocationText +" as record already exists.");
					updateExistingRecord();
					resetOpeningStockFormValues();
					e.preventDefault();
				}
				
				
			}else{
				if(this.id == 'openingStockSaveBtn'){
					constructDynamicDivForOpeningStock();
				}else{
					updateExistingRecord();
					resetOpeningStockFormValues();
				}
				
				e.preventDefault();
			}
			
		}
		
	});
	
	
	
	$("#open_stock_qty").on("keyup input",function(){
		var purchasePrice = 0;
		$("#open_stock_stock_value").val(0);
    	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#open_stock_qty").val()) === true){
			$("#opening-stock-quantity").hide();
			$("#open_stock_qty").addClass("input-correct").removeClass("input-error");	
			if($("#productBuyingRate").val().length != 0) {
				/*if(!checkForZeroQuantity("open_stock_qty","opening-stock-quantity","Quantity should be greater than 0")){*/
					$("#open_stock_stock_value").val(($("#productBuyingRate").val() * $("#open_stock_qty").val()).toFixed(2));
					$("#open_stock_stock_value").removeClass("input-error");
					$("#opening-stock-stock-value").hide();
				
			}
		}
    	
		if(currencyRegex.test($("#open_stock_qty").val()) !== true){
			$("#opening-stock-quantity").text("Quantity should be numeric");
			$("#opening-stock-quantity").show();
			$("#open_stock_qty").addClass("input-error").removeClass("input-correct");	
		}    	
    });
	
	$("#open_stock_stock_value").on("keyup input",function(){
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#open_stock_stock_value").val()) === true){
			$("#opening-stock-stock-value").hide();
			$("#open_stock_stock_value").addClass("input-correct").removeClass("input-error");	
		}
    	
		if(currencyRegex.test($("#open_stock_stock_value").val()) !== true){
			$("#opening-stock-stock-value").text("Quantity should be numeric");
			$("#opening-stock-stock-value").show();
			$("#open_stock_stock_value").addClass("input-error").removeClass("input-correct");	
		}    	
    });
	

	$("#name").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		
		this.value = this.value.replace(/[\\[]*$/, '');
	//	this.value = this.value.replace(/[^[a-zA-Z-, ]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		
    	 if ($("#name").val() !== ""){
    		 $("#name").addClass("input-correct").removeClass("input-error");
    		 $("#prod-name").hide();		 
    	 }
    	 if ($("#name").val() === ""){
    		 $("#name").addClass("input-error").removeClass("input-correct");
    		 $("#prod-name").show();		 
    	 } 
    });
	

	$("#unitOfMeasurement").change(function(){
		 if ($("#unitOfMeasurement").val() === ""){
			 $("#unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#prod-uom").show();
			 
		 }
		 if ($("#unitOfMeasurement").val() !== ""){
			 $("#unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#prod-uom").hide();
			 
		 } 
		 if($("#unitOfMeasurement").val()=="OTHERS"){
			 $("#purchaseUOM").val($('#otherUOM').val());
		 }
		 
		 else{
		 $("#purchaseUOM").val($('select#unitOfMeasurement option:selected').val());
		 }
	});
	
	$("#purchaseUOM").change(function(){
		 if ($("#purchaseUOM").val() === ""){
			 $("#purchaseUOM").addClass("input-error").removeClass("input-correct");
			 $("#purchase-uom").show();
			 
		 }
		 if ($("#purchaseUOM").val() !== ""){
			 $("#purchaseUOM").addClass("input-correct").removeClass("input-error");
			 $("#purchase-uom").hide();
			 
		 } 
	});
	
	$("#otherUOM").on("keyup input",function(){
		 if ($("#otherUOM").val() === ""){
			 $("#otherUOM").addClass("input-error").removeClass("input-correct");
			 $("#otherOrgType-req").show();
			 
		 }
		 if ($("#otherUOM").val() !== ""){
			 $("#otherUOM").addClass("input-correct").removeClass("input-error");
			 $("#otherOrgType-req").hide();
			 $("#purchaseUOM").val($('#otherUOM').val());
			 $("#purchaseOtherUOM").val($('#otherUOM').val());
			 
		 } 
	});

	$("#purchaseOtherUOM").on("keyup input",function(){
		 if ($("#purchaseOtherUOM").val() === ""){
			 $("#purchaseOtherUOM").addClass("input-error").removeClass("input-correct");
			 $("#otherPurchaseOrgType-req").show();
			 
		 }
		 if ($("#purchaseOtherUOM").val() !== ""){
			 $("#purchaseOtherUOM").addClass("input-correct").removeClass("input-error");
			 $("#otherPurchaseOrgType-req").hide();
			 
		 } 
	});
	
    $("#productSellingRate").on("keyup input",function(){
    	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#productSellingRate").val()) === true){
			$("#prod-rate").hide();
			$("#productSellingRate").addClass("input-correct").removeClass("input-error");	
		}
    	
		if(currencyRegex.test($("#productSellingRate").val()) !== true){
			$("#prod-rate").text(regMsg);
			$("#prod-rate").show();
			$("#productSellingRate").addClass("input-error").removeClass("input-correct");	
		}
		    	
    });

   $("#productBuyingRate").on("keyup input",function(){
    	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#productBuyingRate").val()) === true){
			$("#prod-buy-rate").hide();
			$("#productBuyingRate").addClass("input-correct").removeClass("input-error");	
		}
    	
		if(currencyRegex.test($("#productBuyingRate").val()) !== true){
			$("#prod-buy-rate").text(regMsg);
			$("#prod-buy-rate").show();
			$("#productBuyingRate").addClass("input-error").removeClass("input-correct");	
		}
		    	
    });
    
    $("#productIgst").change(function(){
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(percentRegex.test($("#productIgst").val()) === true){
    		$("#prod-igst").hide();
    		$("#productIgst").addClass("input-correct").removeClass("input-error");	
    	}
    	if(percentRegex.test($("#productIgst").val()) !== true){
    		$("#prod-igst").text(igstMsg);
    		$("#prod-igst").show();
    		$("#productIgst").addClass("input-error").removeClass("input-correct");	
    	}
    });


    $("#search-hsn").on("input keyup click", function(e){
    	if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
    	if ( $("#hsnDescription").val() === "" || $("#hsnCode").val() ==="" ){
    		$("#prod-hsn-desc, #prod-hsn-code, #reg-gstin-req").show();
    		$("#search-hsn").addClass("input-error").removeClass("input-correct");
    	}
    	
    	if ($("#hsnDescription").val() !== "" && $("#hsnCode").val() !== ""){
    		$("#prod-hsn-desc, #prod-hsn-code, #reg-gstin-req").hide();
    		//$("#search-hsn").val("");
    		$("#search-hsn").removeClass("input-error");
    	}
    });
    	
    $('#gstin').on("change", function() {
    	$('#show').prop('disabled', false);
    	var gstinSelectedId = $('#gstin').val();	
    	if(gstinSelectedId != ''){
    		$('#gstin').addClass("input-correct").removeClass("input-error");
    		$('#opening-stock-gstin').hide();
    		$("#locationStore").empty();
    		$("#locationStore").append('<option value="">Select</option>');
    		$.each(gstinJson,function(i,value) {
    			if(value.id == gstinSelectedId ){
    				$.each(value.gstinLocationSet,function(i2,value2) {
    					$("#locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
    				});				
    			}
    		});
    		
    		
    	}else{
    		$('#opening-stock-gstin').show();
       	  	$('#gstin').addClass("input-error").removeClass("input-correct");
    	}     
    });
    
        $.ajax({
    		url : "getProductsListjson",
    		type : "POST",
    		dataType : "json",
    		data : {_csrf_token : $("#_csrf_token").val()},
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
    			
    			 $.each(json,function(i,value){
    				 var dynamicUom='<p> Unit Of Measurement : '+value.unitOfMeasurement+'</p>';
    				 if(value.unitOfMeasurement=="OTHERS"){dynamicUom='<p> Unit Of Measurement : '+value.otherUOM+'</p>';}
    				 $('#ProductValuesTab tbody:last-child').append(
    						 '<tr>'
  			        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+')">'+value.name+'</a></td>' 
  			        		+'<td align="center">'+value.productRate+'</td>'
  			        		+'<td align="center">'+value.unitOfMeasurement+'</td>'
  			        		+'<td align="center">'+value.hsnCode+'</td>'
  			        		+'<td align="center">'+value.productIgst+'</td>'
  			        		+'<td align="center">'+value.purchaseRate+'</td>'
  			        		/*+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>' 
  			        		+'<td align="center"><a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'*/
  			        		+'</tr>'
    				 );
    			});
    			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	 
             },
             error: function (data,status,er) {
            	 
            	 getInternalServerErrorPage();   
            }
    	}); 
       // createDataTable('ProductValuesTab');

        $('#ProductValuesTab').DataTable({
	        	bSort: false,
		        rowReorder: {
		              selector: 'td:nth-child(2)'
		        },
	            responsive: true
        });
        
        document.addEventListener('invalid', (function(){
            return function(e){
                //prevent the browser from showing default error bubble/ hint
                e.preventDefault();
                // optionally fire off some custom validation handler
                // myvalidationfunction();
            };
        })(), true);
        $("#divOtherUnitOfMeasurement").hide();
        $("#divPurchaseOtherUOM").hide();
        
        $("#unitOfMeasurement").change(function(){
    		otherUnitOfMeasurement();
    		
    	});
        
        $("#purchaseUOM").change(function(){
    		otherPurchaseUOM();
    		
    	});
        
        
        $(".dny-product-opn-stock-qty,.dny-product-opn-stock-value").on("keyup input",function(){
        	//this.value = this.value.replace(/[^[0-9.]*$/, '');
        	 var regExp = new RegExp('(\\.[\\d]{2}).', 'g');    
        	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(regExp, '$1');
        });
        
        
        $('.loader').fadeOut("slow"); 
        
        
        $("#prodDeleteBtn").click(function(e){
            $('.loader').fadeOut("slow"); 
            var idValue= $("#prod").val();
            deleteRecord(idValue);
        });
});

function otherUnitOfMeasurement(){
	if ($("#unitOfMeasurement").val() === "OTHERS"){
		$("#divOtherUnitOfMeasurement").show();
	}else{
		$("#divOtherUnitOfMeasurement").hide();
		$("#otherUOM").val("");
	}
	
}

function otherPurchaseUOM(){
	if ($("#purchaseUOM").val() === "OTHERS"){
		$("#divPurchaseOtherUOM").show();
	}else{
		$("#divPurchaseOtherUOM").hide();
		$("#purchaseOtherUOM").val("");
	}
	
}

$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});


function editRecord(idValue){
    $('.loader').show();
	document.manageProduct.action = "./editProduct";
	document.manageProduct.id.value = idValue;
	document.manageProduct._csrf_token.value = $("#_csrf_token").val();
	document.manageProduct.submit();
	$('.loader').fadeOut("slow");
}

function deleteRecord(idValue){
    $('.loader').show();
	bootbox.confirm("Are you sure you want to delete ?", function(result){
	 if (result){
		document.manageProduct.action = "./deleteProduct";
		document.manageProduct.id.value = idValue;
		document.manageProduct._csrf_token.value = $("#_csrf_token").val();
		document.manageProduct.submit();	
	 }
	});
	$('.loader').fadeOut("slow"); 
}

function hsnDesc(){
	
	errHsndesc = validateTextField("hsnDescription","prod-hsn-desc",blankMsg);
	 if(!errHsndesc){
		 errHsndesc=validateFieldLength("hsnDescription","prod-hsn-desc",lengthMsg,length);
	 }
 
	 return errHsndesc;
}

function hsnCode(){
	 errHsnCode = validateTextField("hsnCode","prod-hsn-code",blankMsg);
	 if(!(errHsnCode)){
		 errHsnCode = validateFieldLength("hsnCode","prod-hsn-code",lengthMsg,length);
	 }
		
	 return errHsnCode;
}

function hsnProductName(){
	 errhsnProductName = validateTextField("name","prod-name",blankMsg);
	 if(!(errhsnProductName)){
		 errhsnProductName = validateFieldLength("name","prod-name",lengthMsg,length);
	 }
	
	 return errhsnProductName;
}

function hsnUOM(){
	 errhsnUOM = validateTextField("unitOfMeasurement","prod-uom",blankMsg);
	/* if(!errhsnUOM){
		 errhsnUOM = validateFieldLength("unitOfMeasurement","prod-uom",lengthMsg,length);
	 }*/

	 return errhsnUOM;
}

function purchaseUOM(){
	 errpurchaseUOM = validateTextField("purchaseUOM","purchase-uom",blankMsg);
	/* if(!errhsnUOM){
		 errhsnUOM = validateFieldLength("unitOfMeasurement","prod-uom",lengthMsg,length);
	 }*/

	 return errpurchaseUOM;
}

function hsnProductRate(){
	
	errhsnProductRate = validateTextField("productSellingRate","prod-rate",blankMsg);
	if(!errhsnProductRate){
		errhsnProductRate=validateRegexpressions("productSellingRate","prod-rate",regMsg,currencyRegex);
	 }
	return errhsnProductRate;
}

function hsnProductBuyingRate(){
	
	errhsnProductBuyingRate = validateTextField("productBuyingRate","prod-buy-rate",blankMsg);
	if(!errhsnProductBuyingRate){
		errhsnProductBuyingRate=validateRegexpressions("productBuyingRate","prod-buy-rate",regMsg,currencyRegex);
	 }
	return errhsnProductBuyingRate;
}

function hsnProductIgst(){
	
	errhsnProductIgst = validateTextField("productIgst","prod-igst",blankMsg);
	if(!errhsnProductIgst){
		errhsnProductIgst=validateRegexpressions("productIgst","prod-igst",productIgstMsg,percentRegex);
	 }
	return errhsnProductIgst;
}

function validateOtherUOM(){
	errOtherUOM = validateTextField("otherUOM","otherOrgType-req",blankMsg);
	 if(!errOtherUOM){
		 errOtherUOM=validateFieldLength("otherUOM","otherOrgType-req",lengthMsg,1);
	 }
	 
	 return errOtherUOM;
}

function validateOtherPurchaseUOM(){
	errotherPurchaseUOM = validateTextField("purchaseOtherUOM","otherPurchaseOrgType-req",blankMsg);
	 if(!errotherPurchaseUOM){
		 errotherPurchaseUOM=validateFieldLength("purchaseOtherUOM","otherPurchaseOrgType-req",lengthMsg,1);
	 }
	 
	 return errotherPurchaseUOM;
}



$(document).ready(function(){
	var editPage =$("#editPage").val();
	var selRateOfTax =$("#rateOfTax").val();
	
    $.ajax({
		url : "getUnitOfMeasurement",
		type : "POST",
		dataType : "json",
		data:{_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#unitOfMeasurement").empty();
			$("#purchaseUOM").empty();
			$("#unitOfMeasurement").append('<option value="">Select</option>');
			$("#purchaseUOM").append('<option value="">Select</option>');
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			$.each(json,function(i,value) {
				$("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
				$("#purchaseUOM").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
    
	}); 

    $.ajax({
		url : "getProductRateOfTaxDetails",
		type : "POST",
		dataType : "json",
		data:{_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#productIgst").empty();
			if(editPage != 'true'){
				$("#productIgst").append('<option value="">Select</option>');
			}
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			$.each(json,function(i,value) {
				if(editPage == 'true'){
					if(selRateOfTax==value.taxRate){
						$("#productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate).attr('selected','selected')); 
					 }else{
						 $("#productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
					 }
				} else {
					$("#productIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
				}
				
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }
	}); 

    
});





function loadUnitOfMeasurement(){
	var selectType =$("#unitOfMeasurementHidden").val();
	
	if(selectType=="OTHERS"){
		$("#divOtherUnitOfMeasurement").show();
		$("#divPurchaseOtherUOM").show();
	}
	$.ajax({
		  url:"getUnitOfMeasurement", 	
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
				
			  $.each(json, function(i, value) {
				  
				if(selectType==value.quantityDescription){
					 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription).attr('selected','selected'));
					 $('#purchaseUOM').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription).attr('selected','selected'));
					 
				 }else{
					 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription));
					 $('#purchaseUOM').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription));
				 }
				    
			  });
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		  },
		  error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
		  }
	});

}

function loadGstn(){
	
	$.ajax({
		  url:"getgstinforloggedinuser", 	
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
			  
			  if(json.error == false){
				  userGstinJson = json.result;
				  gstinJson = json.result;
				  $("#gstin").empty();
				  $("#locationStore").empty();
				  if(json.result.length == 1){
					  $.each(json.result,function(i,value){
						  $("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
						  
					  });
					  
			  			if(json.result[0].gstinLocationSet.length == 1){
			  				$.each(json.result[0].gstinLocationSet,function(i,value) {
		  					  $("#locationStore").append($('<option>').text(value.gstinLocation).attr('value',value.id));
							  $("#StoreId").val(value.gstinStore);
			  				});
			  			}
			  			else{
						     $("#locationStore").append('<option value="">Select</option>');
							 $.each(json.result[0].gstinLocationSet,function(i,value){
								$("#locationStore").append($('<option>').text(value.gstinLocation).attr('value',value.id));
							});
			  			}
		  				
		  			}else{
						$("#gstin").append('<option value="">Select</option>');
						$.each(json.result,function(i,value) {
							$("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
						});
						
					}
					 				
			  }else{
					bootbox.alert(json.status);
				}
			 
		  },
		  error: function (data,status,er) {				 
				getInternalServerErrorPage();   
			}
					  
	});	
} 
/*
function loadAdvolCessRate(){
    var editPage = $("#editPage").val();
    if(advolCessRateJson == ''){
          $.ajax({
                  url:"getAdvolCessRate",     
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
                        advolCessRateJson = json;
                        
                  },
                  error: function (data,status,er) {
                            
                             getInternalServerErrorPage();   
                  }
          });
    }
    
    if(advolCessRateJson != ''){
          if(editPage == 'true'){
                var productCessAdvol = $("#cessAdvolTaxRateId").val();
                $.each(advolCessRateJson, function(i, value) {
                        
                      if(productCessAdvol==value.cessRate){
                            $('#advolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate).attr('selected','selected'));
                      }else{
                            $('#advolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));
                      }
                          
                });
                
          }else{
                $.each(advolCessRateJson, function(i, value) {
                      $('#advolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));    
              });
          }
    }
    
}

function loadNonAdvolCessRate(){
    var editPage = $("#editPage").val();
    if(nonAdvolCessRateJson == ''){
          $.ajax({
                  url:"getNonAdvolCessRate", 
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
                        nonAdvolCessRateJson = json;
                        
                  },
                  error: function (data,status,er) {
                            
                             getInternalServerErrorPage();   
                  }
          });
    }
    
    if(nonAdvolCessRateJson != ''){
          
          if(editPage == 'true'){
                var productCessNonAdvol = $("#cessNonAdvolTaxRateId").val();
                $.each(nonAdvolCessRateJson, function(i, value) {
                        
                      if(productCessNonAdvol==value.cessRate){
                            $('#nonAdvolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate).attr('selected','selected'));
                      }else{
                            $('#nonAdvolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));
                      }
                          
                });
          }else{
                $.each(nonAdvolCessRateJson, function(i, value) {
                      $('#nonAdvolCess').append($('<option>').text(value.cessRate).attr('value', value.cessRate));    
              });
          }
          
          
    }
    
}*/

$(document).ready(function(){
	loadUnitOfMeasurement();
	loadGstn();
	/*loadAdvolCessRate();
	loadNonAdvolCessRate();*/
	
	
});



function callProductFormSubmit(){
	
	var generateInputJsonData = constructProduct();
	
	
	 $.ajax({
			url : "saveProductWithOpeningStockAjax",
			method : "post",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
			data : JSON.stringify(generateInputJsonData),
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
				if(json.status == 'SUCCESS'){
					bootbox.alert({
					    message: json.message,
					    callback: function () {
					        console.log('This was logged in the callback!');
					        window.location.href = 'getProducts';
							return;
					    }
					})
				}else{
					bootbox.alert(json.message);
				}
				
				/*lastAndFinalInvoiceJson = json;
				if((json.renderData != 'accessDeny') && (json.renderData != 'TOO_LONG_INVOICE_VALUE')){
					accessAllowedOrNot = 'GRANT_ACCESS';
				}else{
					accessAllowedOrNot = json.renderData;
				}*/
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		});
}

function constructProduct(){
	var hsnDescription = $("#hsnDescription").val();
	var hsnCode = $("#hsnCode").val();
	var name = $("#name").val();
	var productIgst = $('select#productIgst option:selected').val();
	var productRate = $("#productSellingRate").val();
	var unitOfMeasurement = $('select#unitOfMeasurement option:selected').val();
	var otherUOM = $("#otherUOM").val();
	var purchaseRate = $("#productBuyingRate").val();
	var purchaseUOM = $('select#unitOfMeasurement option:selected').val();
	var purchaseOtherUOM = $("#otherUOM").val();
	var hsnCodePkId = $("#hsnCodePkId").val();
	var advolCess = 0;
	var nonAdvolCess = 0;
	
	 //get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningStock");
	   var totalACRecordNo = $addChgToggle.children().length;
	   var jsonObjectAC;
	   var acListArray = new Array();
	  /* for (i = 0; i < totalACRecordNo; i++) { 
			 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
			 var num2 = $addChgToggle.children()[i].id.substring(index2);
			 //alert("num2 : "+num2 + "index2 "+index2);
			 num2 = num2.replace("_","-");
			 jsonObjectAC = new Object();
			 jsonObjectAC.gstnId = $("#opening_stock_gstin_val"+num2).val();
			 jsonObjectAC.storeId = $("#opening_stock_location_val"+num2).val();
			 jsonObjectAC.openingStock = $("#opening_stock_quantity"+num2).val();
			 jsonObjectAC.openingStockValue = $("#opening_stock_value"+num2).val();
			 jsonObjectAC.currentStock = $("#opening_stock_quantity"+num2).val();
			 jsonObjectAC.currentStockValue = $("#opening_stock_value"+num2).val();
			 jsonObjectAC.inventoryUpdate = "N";
			 acListArray.push(jsonObjectAC);
	   }*/
	   
	   //fetch from datatable - start
	   dnyGstinOpeningstockProductTable.rows().every( function ( index, tableLoop, rowLoop ) {
			var rowX = dnyGstinOpeningstockProductTable.row(index).node();
		    var row = $(rowX);
		    var currentQty = row.find(".quantity_"+index).val();
		    var currentStockValue = row.find(".stockvalue_"+index).val();
		    if(currentQty=='')
		    	{
		    	currentQty=0;
		    	}
		    if(currentStockValue=='')
		    	{
		    	currentStockValue=0;
		    	}
		    jsonObjectAC = new Object();
		    jsonObjectAC.gstnId = $("#opening_stock_gstin_val_"+index).val();
			jsonObjectAC.storeId = $("#opening_stock_location_val_"+index).val();
			jsonObjectAC.openingStock = parseFloat(currentQty).toFixed(2);
			jsonObjectAC.openingStockValue = parseFloat(currentStockValue).toFixed(2);
			jsonObjectAC.currentStock = parseFloat(currentQty).toFixed(2);
			jsonObjectAC.currentStockValue = parseFloat(currentStockValue).toFixed(2);
			jsonObjectAC.inventoryUpdate = "N";
			acListArray.push(jsonObjectAC);
	   });
	   //fetch from datatable - end
	   
	  //get add charges from list in javascript - End 
	
	var inputData = {
		"name" : name,
		"hsnCode" : hsnCode,
		"hsnDescription" : hsnDescription,
		"unitOfMeasurement" : unitOfMeasurement,
		"otherUOM" : otherUOM,
		"productRate" : productRate,
		"productIgst" : productIgst,
		"hsnCodePkId" : hsnCodePkId,
		"advolCess" : advolCess,
		"nonAdvolCess" : nonAdvolCess,
		"purchaseRate" : purchaseRate,
		"purchaseUOM" : purchaseUOM,
		"purchaseOtherUOM" : purchaseOtherUOM,
		"openingStockBean" :  JSON.parse(JSON.stringify(acListArray))
	};
	
	console.log("Final JSON : "+JSON.stringify(inputData));
	return inputData;
}

function checkForZeroQuantity(id,span,msg){
	var resp = false;
	if($("#"+id).val() == 0 ){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+span).text(msg);
		$("#"+span).show();
		resp = true;
	}	
	return resp;
}

function constructDynamicDivForOpeningStock(){
	openingStockRowNum++;
	var openingStockGstinText = $("#gstin").find("option:selected").text();
	var openingStockGstinValue = $('select#gstin option:selected').val();
	var openingStockLocationText = $("#locationStore").find("option:selected").text();
	var openingStockLocationValue = $('select#locationStore option:selected').val();
	var openingStockQuantity = '';
	var openingStockValue ='';
	if (($('#open_stock_qty').val() === "")) {
		openingStockQuantity=0;
	}
	else{
		openingStockQuantity= $('#open_stock_qty').val();
	}
	
	if (($('#open_stock_stock_value').val() === "")) {
		openingStockValue=0;
	}
	else{
		openingStockValue=$('#open_stock_stock_value').val();
	}
	//append dynamic div
	var $toggle = $("#toggleOpeningStock");
	var recordNo = openingStockRowNum;
	$toggle.append('<div class="cust-content" id="opening_stock__start_'+recordNo+'">'
			+'<div class="heading">'
                +'<div class="cust-con">'
                    +'<h1 id="opening_stock_location_'+recordNo+'">'+openingStockLocationText+'</h1>'
                +'</div>'
                +'<div class="cust-edit">'
                    +'<div class="cust-icon">'
                    	+'<a href="#callOnEditId" onclick="javascript:edit_opening_stock_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
                    	+'<a href="#" onclick="javascript:remove_opening_stock_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
                    +'</div>'
                +'</div>'
            +'</div>'
            +'<div class="content">'
                +'<div class="cust-con">'
                    +'<p id="opening_stock_gstin_'+recordNo+'" >GSTIN : '+openingStockGstinText+' </p>'
                    +'<p id="opening_stock_quantity_'+recordNo+'" >Quantity : '+openingStockQuantity+' </p>'
                    +'<p id="opening_stock_value_'+recordNo+'" >Stock Value : '+openingStockValue+' </p>'
                +'</div>'
                +'<input type="hidden" id="opening_stock_gstin-'+recordNo+'" name="" value="'+openingStockGstinText+'">'
                +'<input type="hidden" id="opening_stock_gstin_val-'+recordNo+'" name="" value="'+openingStockGstinValue+'">'
                +'<input type="hidden" id="opening_stock_quantity-'+recordNo+'" name="" value="'+openingStockQuantity+'">'
                +'<input type="hidden" id="opening_stock_value-'+recordNo+'" name="" value="'+openingStockValue+'">'
                +'<input type="hidden" id="opening_stock_location-'+recordNo+'" name="" value="'+openingStockLocationText+'">'
                +'<input type="hidden" id="opening_stock_location_val-'+recordNo+'" name="" value="'+openingStockLocationValue+'">'
                
                +'</div>'
		+'</div>');
	openCloseAccordion(recordNo);
	resetOpeningStockFormValues();
	
}

function openCloseAccordion(rowNum){
	var currId = "/"+rowNum;
	//alert("accordionsId ->"+accordionsId);
	if(accordionsId.includes(currId)){
		
	}else{
		$("#opening_stock__start_"+rowNum+" .content").hide();
		$("#opening_stock__start_"+rowNum+" .heading").click(function () {
			$(this).next(".content").slideToggle();
		});	
		accordionsId = accordionsId +","+currId;
	}
	
}

function resetOpeningStockFormValues(){
	loadGstn();
	$('#open_stock_qty').val("");
	$('#open_stock_stock_value').val("");
	$("#edtUniq").val("");
	$("#openingStockSaveBtn").show();
	$("#openingStockEditBtn").hide();
	
	$("#gstin").show();
	$("#gstin-hidden").val('');
	$("#gstin-hidden").hide();
	$("#locationStore").show();
	$("#locationStore-hidden").val('');
	$("#locationStore-hidden").hide();
}

function checkIfStockExistsForGstinAndLocation(){
	var stockExists = false;
	
	var openingStockGstinValue = $('select#gstin option:selected').val();
	var openingStockLocationValue = $('select#locationStore option:selected').val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningStock");
	   var totalACRecordNo = $addChgToggle.children().length;
	  /* var jsonObjectAC;
	   var acListArray = new Array();*/
	   if(totalACRecordNo == 0){
		   stockExists = false;
	   }else{
		   for (i = 0; i < totalACRecordNo; i++) { 
			   //Start
				 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
				 var num2 = $addChgToggle.children()[i].id.substring(index2);
				 num2 = num2.replace("_","-");
				 if(($("#opening_stock_gstin_val"+num2).val() == openingStockGstinValue) && ($("#opening_stock_location_val"+num2).val() == openingStockLocationValue)){
					 stockExists = true;
					 break;
				 }
				//End
		   }
		   
	   }
	  //get add charges from list in javascript - End 
	   return stockExists;
}

function overrideExsitingStockvalue(){
	var openingStockGstinValue = $('select#gstin option:selected').val();
	var openingStockLocationValue = $('select#locationStore option:selected').val();
	
	//get add charges from list in javascript - Start 
	   var $addChgToggle = $("#toggleOpeningStock");
	   var totalACRecordNo = $addChgToggle.children().length;
	   for (i = 0; i < totalACRecordNo; i++) { 
		   //Start
			 var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
			 var num2 = $addChgToggle.children()[i].id.substring(index2);
			 num2 = num2.replace("_","-");
			 var recordNo = num2.replace("-","");
			 if(($("#opening_stock_gstin_val"+num2).val() == openingStockGstinValue) && ($("#opening_stock_location_val"+num2).val() == openingStockLocationValue)){
				 $('#opening_stock_quantity_'+recordNo).text("Quantity : "+$('#open_stock_qty').val());
				 $('#opening_stock_quantity-'+recordNo).val($('#open_stock_qty').val());
				 $('#opening_stock_value_'+recordNo).text("Stock Value : " +$('#open_stock_stock_value').val());
				 $('#opening_stock_value-'+recordNo).text( $('#open_stock_stock_value').val());
				break; 
			 }
			//End
	   }   
	  //get add charges from list in javascript - End
}

function edit_opening_stock_row(recordNo){
	$("#openingStockSaveBtn").hide();
	$("#openingStockEditBtn").show();
	var openingStockGstin = $("#opening_stock_gstin_val-"+recordNo).val();
	var openingStockQuantity = $("#opening_stock_quantity-"+recordNo).val();
	var openingStockValue = $("#opening_stock_value-"+recordNo).val();
	var openingStockLocation = $("#opening_stock_location_val-"+recordNo).val();
	
	changeValueInGstinAndStoreDropdown(openingStockGstin,openingStockLocation);
	$("#edtUniq").val(recordNo);
	$("#open_stock_qty").val(openingStockQuantity);
	$("#open_stock_stock_value").val(openingStockValue);
	
	//hide the gstin dropdown and show input field - Start
	$("#gstin-hidden").val($("#opening_stock_gstin-"+recordNo).val());
	$("#gstin-hidden").show();
	$("#gstin").hide();
	
	$("#locationStore-hidden").val($("#opening_stock_location-"+recordNo).val());
	$("#locationStore-hidden").show();
	$("#locationStore").hide();
	//hide the gstin dropdown and show input field - End
	
	
}

function changeValueInGstinAndStoreDropdown(openingStockGstin,openingStockLocation){
	if(userGstinJson == ''){
		loadGstn();
	}
	 $("#gstin").empty();
	 $("#locationStore").empty();
	 $.each(userGstinJson, function(i, value) {
         if(openingStockGstin==value.id){
        	 $("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id).attr('selected','selected'));
        	 $("#locationStore").append('<option value="">Select</option>');
			 $.each(value.gstinLocationSet,function(i2,value2) {
				 if(value2.id == openingStockLocation){
					 $("#locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id).attr('selected','selected'));
				 }else{
					 $("#locationStore").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
				 }
				
			 });				
     			
     		 
         }else{
        	 $("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
         }       
   });
	
}

function updateExistingRecord(){
	
	var recordNo = $("#edtUniq").val();
	var openingStockGstinText = $("#gstin").find("option:selected").text();
	var openingStockGstinValue = $('select#gstin option:selected').val();
	var openingStockLocationText = $("#locationStore").find("option:selected").text();
	var openingStockLocationValue = $('select#locationStore option:selected').val();
	var openingStockQuantity = $('#open_stock_qty').val();
	var openingStockValue =  $('#open_stock_stock_value').val();
	
	$("#opening_stock_gstin_"+recordNo).html('');
	$("#opening_stock_quantity_"+recordNo).html('');
	$("#opening_stock_value_"+recordNo).html('');
	$("#opening_stock_location_"+recordNo).html('');
	
	$("#opening_stock_location_"+recordNo).text(openingStockLocationText);
	$("#opening_stock_gstin_"+recordNo).text("GSTIN : "+openingStockGstinText);
	$("#opening_stock_quantity_"+recordNo).text("Quantity : "+openingStockQuantity);
	$("#opening_stock_value_"+recordNo).text("Stock Value : "+openingStockValue);
    
    $("#opening_stock_gstin-"+recordNo).val(openingStockGstinText);
	$("#opening_stock_gstin_val-"+recordNo).val(openingStockGstinValue);
	$("#opening_stock_quantity-"+recordNo).val(openingStockQuantity);
	$("#opening_stock_value-"+recordNo).val(openingStockValue);
	$("#opening_stock_location-"+recordNo).val(openingStockLocationText);
	$("#opening_stock_location_val-"+recordNo).val(openingStockLocationValue); 
}

function remove_opening_stock_row(recordNo){
	bootbox.confirm("Are you sure you want to remove ?", function(result){
		 if (result){
			 $('#opening_stock__start_'+recordNo).remove();
			accordionsId = accordionsId.replace(",/"+recordNo, "");
		 }
	});	 
	
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

function setDataInOpeningStockProduct(){
	if(gstinsInventoryJson == ''){
		loadGstinsInventory();
	}
	dnyGstinOpeningstockProductTable.clear().draw();
	counter = 0;
	$.each(gstinsInventoryJson,function(i,value){
		var gstinNumber = value.gstinNo;
		var gstinId = value.id;
		$.each(value.gstinLocationSet,function(i,value2){	
			
			dnyGstinOpeningstockProductTable.row.add($(
		 			'<tr id="'+counter+'" >'
		 				+'<td>'+(counter+1)+'<input type="hidden" class="xCount_'+counter+'" value="'+counter+'"></td>'
		 				+'<td>'+gstinNumber+'</td>'
		 				+'<td>'+value2.gstinLocation+'</td>'
		 				+'<td><input type="hidden" id="opening_stock_gstin_val_'+counter+'" value="'+gstinId+'"><input type="hidden" id="opening_stock_location_val_'+counter+'" value="'+value2.id+'"><input type="text" value="0" class="dny-product-opn-stock-qty editQty quantity_'+counter+'" maxlength="8"></td>'
		 				+'<td><input type="text" value="0" class="dny-product-opn-stock-value editStkVal stockvalue_'+counter+'" maxlength="8"></td>'
		 			+'</tr>'					 	
			)).draw();
			counter++;
	      });
	   
    });
}
function setDataInOpeningStockProductForEditPage(productName,prod){
	
	if(gstinsInventoryJson == ''){
		loadGstinsInventory();
	}
	loadProductForEdit(productName,prod);
	dnyGstinOpeningstockProductTable.clear().draw();
	counter = 0;
	$.each(productForEditJson,function(i,value){
		/*var gstinNumber = value.gstinNo;
		var gstinId = value.id;*/
		/*$.each(value.gstinLocationSet,function(i,value2){*/	
			
			dnyGstinOpeningstockProductTable.row.add($(
		 			'<tr id="'+counter+'" >'
		 				+'<td>'+(counter+1)+'<input type="hidden" class="xCount_'+counter+'" value="'+counter+'"></td>'
		 				+'<td>'+value.gstinNumber+'</td>'
		 				+'<td>'+value.storeName+'</td>'
		 				+'<td>'+value.openingStock+'</td>'
		 				+'<td>'+value.openingStockValue+'</td>'
		 				/*+'<td>'+value2.gstinLocation+'</td>'
		 				+'<td><input type="hidden" id="opening_stock_gstin_val_'+counter+'" value="'+gstinId+'"><input type="hidden" id="opening_stock_location_val_'+counter+'" value="'+value2.id+'"><input type="text" value="0" class="dny-product-opn-stock-qty editQty quantity_'+counter+'" maxlength="8"></td>'
		 				+'<td><input type="text" value="0" class="dny-product-opn-stock-value editStkVal stockvalue_'+counter+'" maxlength="8"></td>'*/
		 			+'</tr>'					 	
			)).draw();
			counter++;
	     /* });*/
	   
    });
}
$("#dny-gstin-openingstock-product").on("change",".editQty", function () {
	  $(this).val(roundToTwoDecimal(this.value));
	if(!isNaN($(this).val())){
		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
       var id=parents.parent().prev('tr').attr('id');
  	  if(id == undefined){                       // for desktop view ,this condition works
  		  var $row = $(this).closest("tr");
            var id = $row.attr('id');
  	  }
  	  
        	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
        	var quantity = $(this).val();
        	 $(".quantity_"+rowNo).val(quantity);
        	var purchaseRate = $('#productBuyingRate').val();
        	
        	 var amount = quantity * purchaseRate;
        	 $(".stockvalue_"+rowNo).val(amount.toFixed(2));
	}else{
		$(this).val('').focus();
		
	}
	
});

$("#dny-gstin-openingstock-product").on("change",".editStkVal", function () {
	  $(this).val(roundToTwoDecimal(this.value));
	if(!isNaN($(this).val())){
		 var parents = $(this).parents( "td.child"); // for mobile view,find tr id from child tr
     var id=parents.parent().prev('tr').attr('id');
	  if(id == undefined){                       // for desktop view ,this condition works
		  var $row = $(this).closest("tr");
          var id = $row.attr('id');
	  }
	  
      	var rowNo = id.substring(id.lastIndexOf("_") + 1, id.length);
      	var stkVal = $(this).val();
      	 $(".stockvalue_"+rowNo).val(stkVal);
	}else{
		$(this).val('').focus();
		
	}
	
});

function loadProductForEdit(productName,prod){
	
	$.ajax({
		  url:"getProductForEditPage", 	
		  type : "POST",
		  dataType: 'json',
		  async : false,
		  data:{_csrf_token : $("#_csrf_token").val(),"name":productName,"prod":prod},
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
			  productForEditJson = json;
			 
		  }
	});
	
}
