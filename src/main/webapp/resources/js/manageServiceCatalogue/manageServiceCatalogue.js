
jQuery(document).ready(function($){
            $(".accordion_example2").smk_Accordion({
                closeAble: true, //boolean
            });

            if($("#actionPerformed").val()){
          		 $('#toggle').hide();
          	}
            $('#addCustomer').on('click', function(e) {
            	$(".addCustomer").slideToggle();
            	 $('.serviceValuesTable').hide();
            	 $('#addCustomer').hide();
            	e.preventDefault();
            })
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
		if( (errsacdesc) || (errsacCode) || (errsacServiceName) || (errsacUOM) || (errsacServiceRate)|| (errServiceIGST) || (serviceExists) || (errOtherUOM)){
			 e.preventDefault();	
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

    $.ajax({
		url : "getServicesList",
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
					//alert(value.userName);
				 var dynamicUom='<p> Unit Of Measurement : '+value.unitOfMeasurement+'</p>';
				 if(value.unitOfMeasurement=="OTHERS"){dynamicUom='<p> Unit Of Measurement : '+value.otherUOM+'</p>';}
				 $('.serviceValues').append(
					/*'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.name+'</h1>'
						+'</div>'
						+'<div class="cust-edit">'
						  +'<div class="cust-icon">'
						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						  +'</div>'
						+'</div>'
					+'</div>'
		
					+'<div class="content">'
						+'<div class="cust-con">'
							+'<p> SAC Code : '+value.sacCode+'</p>'
							+'<p> SAC Description : '+value.sacDescription+'</p>'
							+dynamicUom
							+'<p> Rate : '+value.serviceRate+'</p>'
							+'<p> Rate of tax (%) : '+value.serviceIgst+'</p>'
						+'</div>'
					+'</div>'	*/ 
						 +'<tbody>'			
							+'<tr>'
								+'<td class="text-left"><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.name+'</a></td>' 
								+'<td class="text-right">'+value.serviceIgst+'</td>'
							+'</tr>'
					     +'</tbody>'
				 
				 );
			});
			 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
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
	document.manageService.action = "./editManageServiceCatalogue";
	document.manageService.id.value = idValue;
	document.manageService._csrf_token.value = $("#_csrf_token").val();
	document.manageService.submit();		
}
	    
function deleteRecord(lLocationId){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		 if (result){
	    document.manageService.action = "./deleteManageServiceCatalogue";
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
		type : "POST",
		dataType : "json",
		data:{_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			$("#serviceIgst").empty();
			if(editPage != 'true'){
			$("#serviceIgst").append('<option value="">Select Rate Of Tax</option>');
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
        	 
        	 getInternalServerErrorPage();   
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
			  type : "POST",
			  dataType: 'json',
			  data:{_csrf_token : $("#_csrf_token").val()},
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
				  $.each(json, function(i, value) {
					  
					if(selectType==value.quantityDescription){
						 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription).attr('selected','selected'));
					 }else{
						 $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription));
					 }
					    
					});
				 
			   },
			   error: function (data,status,er) {
					 
					 getInternalServerErrorPage();   
			   }
		 });
	 } else {
			
			$.ajax({
				url : "getUnitOfMeasurement",
				type : "POST",
				dataType : "json",
				data:{_csrf_token : $("#_csrf_token").val()},
				async : false,
				success:function(json,fTextStatus,fRequest){
					
					$("#unitOfMeasurement").empty();
					$("#unitOfMeasurement").append('<option value="">Select Unit Of Measurement</option>');
					
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}

					if(isValidToken(json) == 'No'){
						window.location.href = getCsrfErrorPage();
						return;
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					$.each(json,function(i,value) {
						$("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
					});
					
					
		         },
		         error: function (data,status,er) {
		        	 
		        	 getInternalServerErrorPage();   
		        }
			});
		}
	}//end of loadUnitOfMeasurement method 

$(document).ready(function(){
	loadUnitOfMeasurement();
	
});

$(document).ready(function() {
    $('#serviceValuesTab').DataTable(
              {
            	  "aaSorting": [],
                    "bLengthChange": false,
                    "pagingType": "simple",
              }
    
    );
});
