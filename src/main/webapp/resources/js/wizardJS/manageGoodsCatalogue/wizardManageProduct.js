
jQuery(document).ready(function($){
    $('#listheader2').hide();
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
    if($("#actionPerformed").val()){
  		 $('#toggle').hide();
  	}

    $("#addProductDetails").hide();
    $('#addProduct').on('click', function(e) {
    	$("#addProductDetails").slideToggle();
    	$('#addProduct').hide();
    	$('.container').hide();
   	 	$('#listheader1').hide();
   	 	$('#listheader2').show();
    	e.preventDefault();
    }); 
});


$(document).ready(function(){
	
	var productExists = false;
	var blankMsg="This field is required";
	var length = 2;
	var lengthMsg = "Minimum length should be ";
//	var currencyRegex = /(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;
	var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
	var regMsg = "Goods Rate should be in proper format";
	var igstMsg = "Rate of tax (%) should be in proper format";
	var percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
	var productIgstMsg='Rate of Tax for service should me less than 100'
	
	$("#prodSubmitBtn").on('click', function(e){
		
		var errHsndesc = hsnDesc();
		var errHsnCode = hsnCode();
		var errhsnProductName = hsnProductName();
		var errhsnUOM = hsnUOM();
		var errhsnProductRate = hsnProductRate();
		var errhsnProductIgst = hsnProductIgst();
		
		 if($("#unitOfMeasurement").val()=="OTHERS"){
			 var errOtherUOM=validateOtherUOM();
			 $("#tempUom").val($("#unitOfMeasurement").val()+'-'+'('+$("#otherUOM").val()+')');
		 }
		 
		
		if((errHsndesc) || (errHsnCode) || (errhsnProductName) ||  (errhsnUOM) || (errhsnProductRate) || (errhsnProductIgst) || (productExists) || (errOtherUOM)){
			 e.preventDefault();	
		}else{
			document.getElementById("prdForm").submit();
		}
		
		 if((errHsndesc) || (errHsnCode)){
			 focusTextBox("search-hsn");
		 } else if((errhsnProductName) ){
			 focusTextBox("name");
		 } else if((errhsnUOM)){
			 focusTextBox("unitOfMeasurement");
		 } else if((errhsnProductRate)){
			 focusTextBox("productRate");
		 } else if((hsnProductIgst)){
			 focusTextBox("productIgst");
		 }else if((errOtherUOM)){
			 focusTextBox("otherUOM");
		 }

	});


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

	function hsnProductRate(){		
		errhsnProductRate = validateTextField("productRate","prod-rate",blankMsg);
		if(!errhsnProductRate){
			errhsnProductRate=validateRegexpressions("productRate","prod-rate",regMsg,currencyRegex);
		 }
		return errhsnProductRate;
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
/*
    $("#unitOfMeasurement").on("keyup input",function(){
    	 this.value = this.value.replace(/[^[a-zA-Z]*$/, '');
    	 if ($("#unitOfMeasurement").val() !== ""){
    		 $("#unitOfMeasurement").addClass("input-correct").removeClass("input-error");
    		 $("#prod-uom").hide();		 
    	 }
    	 if ($("#unitOfMeasurement").val() === ""){
    		 $("#unitOfMeasurement").addClass("input-error").removeClass("input-correct");
    		 $("#prod-uom").show();		 
    	 } 
    });*/
	
	$("#unitOfMeasurement").change(function(){
		 if ($("#unitOfMeasurement").val() === ""){
			 $("#unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#ser-uom").show();			 
		 }
		 if ($("#unitOfMeasurement").val() !== ""){
			 $("#unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#ser-uom").hide();			 
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
		 } 
	});

    $("#productRate").on("keyup input",function(){
    	
    	this.value = this.value.replace(/[^[0-9.]*$/, '');
    	
    	if(currencyRegex.test($("#productRate").val()) === true){
			$("#prod-rate").hide();
			$("#productRate").addClass("input-correct").removeClass("input-error");	
		}    	
		if(currencyRegex.test($("#productRate").val()) !== true){
			$("#prod-rate").text(regMsg);
			$("#prod-rate").show();
			$("#productRate").addClass("input-error").removeClass("input-correct");	
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
    	    	

    document.addEventListener('invalid', (function(){
        return function(e){
            //prevent the browser from showing default error bubble/ hint
            e.preventDefault();
            // optionally fire off some custom validation handler
            // myvalidationfunction();
        };
    })(), true);
    $("#divOtherUnitOfMeasurement").hide();
    $("#unitOfMeasurement").change(function(){
		otherUnitOfMeasurement();
		
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


$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});


function editRecord(idValue){
	document.manageProduct.action = "./wizardEditProduct";
	document.manageProduct.id.value = idValue;
	document.manageProduct._csrf_token.value = $("#_csrf_token").val();
	document.manageProduct.submit();
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
	 if (result){
		document.manageProduct.action = "./WizardDeleteProduct";
		document.manageProduct.id.value = idValue;
		document.manageProduct._csrf_token.value = $("#_csrf_token").val();
		document.manageProduct.submit();	
	 }
	});
}


$(document).ready(function(){
	var editPage =$("#editPage").val();
	var selRateOfTax =$("#rateOfTax").val();
	var selectType =$("#unitOfMeasurementHidden").val();
	if(selectType=="OTHERS"){
		$("#divOtherUnitOfMeasurement").show();
	}
	
    $.ajax({
		url : "getUnitOfMeasurement",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#unitOfMeasurement").empty();
			if(editPage != 'true'){
				$("#unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
			}
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			$.each(json,function(i,value) {
				if(editPage == 'true'){
					if(selectType==value.quantityDescription){
						$("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription).attr('selected','selected')); 
					 }else{
						 $("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
					 }
				} else {
					$("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
				}
			});
         },
         error: function (data,status,er) {        	 
        	 getWizardInternalServerErrorPage();   
        }
	}); 

    $.ajax({
		url : "getProductRateOfTaxDetails",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#productIgst").empty();
			if(editPage != 'true'){
				$("#productIgst").append('<option value="">Select Rate Of Tax</option>');
			}
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
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
         },
         error: function (data,status,er) {        	 
        	 getWizardInternalServerErrorPage();   
        }
	});     
});





function loadUnitOfMeasurement(){
	var selectType =$("#unitOfMeasurementHidden").val();
	if(selectType=="OTHERS"){
		$("#divOtherUnitOfMeasurement").show();
	}
	$.ajax({
		  url:"getUnitOfMeasurement", 
		  headers: {
				_csrf_token : $("#_csrf_token").val()
			},
		  type : "POST",
		  dataType: 'json',
		  success:function(json,fTextStatus,fRequest) {
			  if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			  }

			  if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			  }
			  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				
			  $.each(json, function(i, value) {
				  
				 if(selectType==value.quantityDescription){
					 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription).attr('selected','selected'));
				 }else{
					 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription));
				 }				    
			  });
		  },
		  error: function (data,status,er) {				 
				 getWizardInternalServerErrorPage();   
		  }
	});
}
/*
$(document).ready(function(){
	loadUnitOfMeasurement();
});
*/


