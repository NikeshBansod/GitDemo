 
jQuery(document).ready(function($){
    $("#addServiceDetails").hide();
    $('#listheader2').hide();

    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });

    if($("#actionPerformed").val()){
  		 $('#toggle').hide();
  	}
    
    $('#addService').on('click', function(e) {
    	$("#addServiceDetails").slideToggle();
    	$('#addService').hide();
     	$('.container').hide();
    	$('#listheader1').hide();
    	$('#listheader2').show();
    	e.preventDefault();
    });
   
});


$(document).ready(function(){
	var blankMsg="This field is required";
	var length = 2;
	var lengthMsg = "Minimum length should be ";
	var serviceExists = false;
	var contactNumRegex = /[2-9]{2}\d{8}/;
	var regMsg = "Service Rate should be in proper format";
	var igstMsg = "Rate of tax (%) should be in proper format";
	//var currencyRegex = /(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;
	var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
	var percentRegex=/(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
	var serviceIgstMsg='Rate of Tax for service should me less than 100'
	
	$("#srvSubmitBtn").click(function(e){
		 var errsacdesc = sacDesc();
		 var errsacCode = sacCode();
		 var errsacServiceName = sacServiceName(); 
		 var errsacUOM = sacUOM();
		 var errsacServiceRate = sacServiceRate();
		 var errServiceIGST =sacServiceIgst();
		 if($("#unitOfMeasurement").val()=="OTHERS"){
		 var errOtherUOM=validateOtherUOM();
		 $("#tempUom").val($("#unitOfMeasurement").val()+'-'+'('+$("#otherUOM").val()+')');
		 }
		if((errsacdesc) || (errsacCode) || (errsacServiceName) || (errsacUOM) || (errsacServiceRate)|| (errServiceIGST) || (serviceExists) || (errOtherUOM)){
			 e.preventDefault();	
		}else{
			document.getElementById("servForm").submit();
		}
		
		if((errsacdesc) || (errsacCode) ){
			 focusTextBox("search-sac");
		 } else if((errsacServiceName) ){
			 focusTextBox("name");
		 } else if((errsacUOM) ){
			 focusTextBox("unitOfMeasurement");
		 } else if((errsacServiceRate)){
			 focusTextBox("serviceRate");
		 } else if((errServiceIGST)){
			 focusTextBox("serviceIgst");
		 }else if((errOtherUOM)){
			 focusTextBox("otherUOM");
		 }
		
	});

	/*function searchSac(){
		errsearchSac = validateTextField("search-sac","ser-uom",blankMsg);
		 return errsearchSac;
	}*/

	function sacDesc(){
		
		errsacdesc = validateTextField("sacDescription","ser-sac-desc",blankMsg);
		 if(!(errsacdesc)){
			 errsacdesc=validateFieldLength("sacDescription","ser-sac-desc",lengthMsg,length);
		 }
	 
		 return errsacdesc;
	}

	function sacCode(){
		errsacCode = validateTextField("sacCode","ser-sac-code",blankMsg);
		 if(!(errsacCode)){
			 errsacCode = validateFieldLength("sacCode","ser-sac-code",lengthMsg,length);
		 }
			
		 return errsacCode;
	}

	function sacServiceName(){
		errsacServiceName = validateTextField("name","ser-name",blankMsg);
		 if(!(errsacServiceName)){
			 errsacServiceName = validateFieldLength("name","ser-name",lengthMsg,length);
		 }
		
		 return errsacServiceName;
	}

	function sacUOM(){
		errsacUOM = validateTextField("unitOfMeasurement","ser-uom",blankMsg);
		/* if(!(errsacUOM)){
			 errsacUOM = validateFieldLength("unitOfMeasurement","ser-uom",lengthMsg,length);
		 }*/

		 return errsacUOM;
	}

	function sacServiceRate(){
		
		errsacServiceRate = validateTextField("serviceRate","ser-rate",blankMsg);
		if(!errsacServiceRate){
			errsacServiceRate=validateRegexpressions("serviceRate","ser-rate",regMsg,currencyRegex);
		 }
		return errsacServiceRate;
	}
	
function sacServiceIgst(){
		
	errServiceIGST = validateTextField("serviceIgst","service-igst",blankMsg);
		if(!errServiceIGST){
			errServiceIGST=validateRegexpressions("serviceIgst","service-igst",serviceIgstMsg,percentRegex);
		 }
		return errServiceIGST;
	}

	function validateOtherUOM(){
		errOtherUOM = validateTextField("otherUOM","otherOrgType-req",blankMsg);
		 if(!errOtherUOM){
			 errOtherUOM=validateFieldLength("otherUOM","otherOrgType-req",lengthMsg,1);
		 }
		 
		 return errOtherUOM;
	}
	
	
	 $("#search-sac").on("input keyup click", function(e){
		 if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
		if ( $("#sacDescription").val() === "" || $("#sacCode").val() === ""){
			$("#reg-gstin-req, #ser-sac-desc, #ser-sac-code").show();
			$("#search-sac").addClass("input-error").removeClass("input-correct");
		}
		
	});

	$("#name").on("keyup input",function(){
		//this.value = removeWhiteSpace(this.value);
		this.value = this.value.replace(/[\\[]*$/, '');
	//	this.value = this.value.replace(/[^[a-zA-Z-, ]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		 if ($("#name").val() === ""){
			 $("#name").addClass("input-error").removeClass("input-correct");
			 $("#ser-name").show();
			 
		 }
		 if ($("#name").val() !== ""){
			 $("#name").addClass("input-correct").removeClass("input-error");
			 $("#ser-name").hide();
			 
		 } 
	});

	$("#unitOfMeasurement").on("keyup click",function(){
		 if ($("#unitOfMeasurement").val() === ""){
			 $("#unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			 $("#ser-uom").show();
			 
		 }
		 if ($("#unitOfMeasurement").val() !== ""){
			 $("#unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			 $("#ser-uom").hide();
			 
		 } 
	});
	
	
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
	
	$("#serviceRate").on("keyup input",function(){
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(currencyRegex.test($("#serviceRate").val()) === true){
			$("#ser-rate").hide();
			$("#serviceRate").addClass("input-correct").removeClass("input-error");	
		}
		if(currencyRegex.test($("#serviceRate").val()) !== true){
			$("#ser-rate").text(regMsg);
			$("#ser-rate").show();
			$("#serviceRate").addClass("input-error").removeClass("input-correct");	
		}
	});
	
	$("#serviceIgst").change(function(){
	//	this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(percentRegex.test($("#serviceIgst").val()) === true){
			$("#service-igst").hide();
			$("#serviceIgst").addClass("input-correct").removeClass("input-error");	
		}
		
		if(percentRegex.test($("#serviceIgst").val()) !== true){
			$("#service-igst").text(igstMsg);
			$("#service-igst").show();
			$("#serviceIgst").addClass("input-error").removeClass("input-correct");	
		}
	});

  //Removed the HTML5 required message box
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
	document.manageService.action = "./wizardEditManageServiceCatalogue";
	document.manageService.id.value = idValue;
	document.manageService._csrf_token.value = $("#_csrf_token").val();
	document.manageService.submit();		
}
	    
function deleteRecord(lLocationId){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		 if (result){
	    document.manageService.action = "./wizardDeleteManageServiceCatalogue";
		document.manageService.id.value = lLocationId;
		document.manageService._csrf_token.value = $("#_csrf_token").val();
		document.manageService.submit();    
		 }
	});		
}


$(document).ready(function(){
	var editPage =$("#editPage").val();
	var selRateOfTax =$("#serviceRateOfTax").val();
	    
    $.ajax({
		url : "getServiceRateOfTaxDetails",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#serviceIgst").empty();
			if(editPage != 'true'){
				$("#serviceIgst").append('<option value="">Select Rate Of Tax</option>');
			}
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$.each(json,function(i,value) {
				if(editPage == 'true'){
					if(selRateOfTax==value.taxRate){
						$("#serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate).attr('selected','selected')); 
					 }else{
						 $("#serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
					 }
				} else {
					$("#serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
				}
				
			});
		   setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
         },
         error: function (data,status,er) {
        	 
        	 getWizardInternalServerErrorPage();   
        }
	});


});

function loadUnitOfMeasurement(){
	var editPage =$("#editPage").val();
	if(editPage == 'true'){
	
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
			  async : false,
			  success:function(json,fTextStatus,fRequest) {
				  if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				  }
	
				  if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				  }
					
				  $.each(json, function(i, value) {
					  
					if(selectType==value.quantityDescription){
						 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription).attr('selected','selected'));
					 }else{
						 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription));
					 }
					    
					});
				  setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			  	},
			  	error: function (data,status,er) {
			  		 
			  		 getWizardInternalServerErrorPage();   
			  	}
			});
		} else {
			
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
					$("#unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultWizardSessionExpirePage();
						return;
					}

					if(isValidToken(json) == 'No'){
						window.location.href = getWizardCsrfErrorPage();
						return;
					}
					$.each(json,function(i,value) {
						$("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
					});
					
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		         },
		         error: function (data,status,er) {
		        	 
		        	 getWizardInternalServerErrorPage();   
		        }
			});
		}
	}

$(document).ready(function(){
	loadUnitOfMeasurement();
	
});
