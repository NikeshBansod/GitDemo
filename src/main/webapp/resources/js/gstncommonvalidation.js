/*
history.pushState({ page: 1 }, "Title 1", "#no-back");
window.onhashchange = function (event) {
  window.location.hash = "no-back";
};  
*/



function isValidSession(sessionVal){
	
	var result='Yes';
	
	if(sessionVal && sessionVal.isSessionValid){
		
		if(sessionVal.isSessionValid=='No'){
			result='No';
		}
	}
	return result;
}

function getDefaultSessionExpirePage(){
	return 'login';
}

function getInternalServerErrorPage(){
	bootbox.alert("SOMETHING WENT WRONG.");
	//return 'home';
}

function getCsrfErrorPage(){
	return 'tError';
}

function isValidToken(sessionVal){
	
	var result='Yes';
	if(sessionVal && sessionVal.isTokenValid){
		
		if(sessionVal.isTokenValid=='Invalid Request'){
			result='No';
		}
	}
	return result;
}


function validateTextField(id, spanid, msg) {
	var result=false;
	if (($("#" + id).val() === "")) {
		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg);
		$("#" + spanid).show();
		result=true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
		result=false;
	}

	return result;
}

function focusTextBox(id){
	$("#" + id).focus();
}


function removeWhiteSpace(id) {
	return id.replace(/^\s*/, '');
} 

function validateFieldLength(id, spanid, msg, length) {
	var result=false;
	if($("#" + id).val() !== ''){
	if ($("#" + id).val().length < length) {
		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg+length+" characters");
		$("#" + spanid).show();
		result=true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
		result=false;
	}
	}
	return result;
}



function validateRegexpressions(id, spanid, msg, regEx) {
	var result=false;
	if($("#" + id).val() !== ''){
	if(!regEx.test(($("#" + id).val()))){
		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg);
		$("#" + spanid).show();
		result=true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
		result=false;
	}
	}
	return result;
}

function validateSelectField(id,spanid){
	var result=false;
	 if(($("#"+id).val() === "")  || $("#"+id).val() == null){
			$("#"+id).addClass("input-error").removeClass("input-correct");
			$("#"+spanid).show();
			result=true;
		}else {
			$("#"+id).addClass("input-correct").removeClass("input-error");
			$("#"+spanid).hide();
			result=false;
		}
	 $("#"+id).click(function(){
		 if ($("#"+id).val() !== ""){
			 $("#"+id).addClass("input-correct").removeClass("input-error");
			 $("#"+spanid).hide();
			 result=false;
		 } 
	 });		
	 return result;
}

function multiSelectValidate(id, spanid){
	var result=false;
	var options = $("#"+id+' > option:selected');
	
    if(options.length == 0){
    	$("#"+id).addClass("input-error").removeClass("input-correct");
    	$("#"+spanid).text("Select atleast one value");
    	$("#"+spanid).show();
    	result = true;
    } else {
    	$("#"+id).addClass("input-correct").removeClass("input-error");
    	$("#"+spanid).hide();
    	result = false;
    }
    
    return result;
}

function validateDateField(id,spanid){
	var result=false;
	if(($("#"+id).val() === "") || (!$("#"+id).val())){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+spanid).show();
		result=true;
	}else{
		 $("#"+id).addClass("input-correct").removeClass("input-error");
		 $("#"+spanid).hide();
		 result = false;
		
	}
	return result;
}


function compareDateField(id1, id2, spanid, msg){
	var result=false;
	if(($("#"+id1).val() === "") || (!$("#"+id1).val()) && ($("#"+id2).val() === "") || (!$("#"+id2).val())){
		$("#"+id1).addClass("input-error").removeClass("input-correct");
		$("#"+spanid).show();
		result=true;
	}else{
		if($("#"+id1).val() > $("#"+id2).val()){
			$("#"+id1).addClass("input-error").removeClass("input-correct");
			$("#"+spanid).text(msg);
			$("#"+spanid).show();
			result=true;
		} else {
		 $("#"+id1).addClass("input-correct").removeClass("input-error");
		 $("#"+spanid).hide();
		 result = false;
		}
		
	}
	return result;
}


function validateRadio(id1, id2, spanid){
	var result=false;
	if(!($("#"+id1).is(":checked")) && !($("#"+id2).is(":checked"))){
		$("#"+spanid).show();
		result = true;
	} else {
		$("#"+spanid).hide();
		result = false;
	}
	$("#"+id1).click(function() {
		$("#"+spanid).hide();
		result = false;
	});
	$("#"+id2).click(function() {
		$("#"+spanid).hide();
		result = false;
	});
	
	return result;
}


function validatePasswordWithConfPwd(id1, id2, spanid, msg){
	var result=false;
	if ($("#"+id1).val() === $("#"+id2).val()){
		 $("#"+spanid).hide();
		 result= false;
	 }
	 if ($("#"+id1).val()!== $("#"+id2).val()){
		 $("#"+spanid).text(msg);
		 $("#"+spanid).show();
		 result= true;
	 }
	 return result;
}

function validateOldAndNewpassword(id1, id2, spanid, msg){
	 if ($("#"+id1).val() != $("#"+id2).val()){
		 $("#"+spanid).hide();
		 return false;
	 }
	 if ($("#"+id1).val() === $("#"+id2).val()){
		 $("#"+spanid).text(msg);
		 $("#"+spanid).show();
		 return true;
	 }
}



function ClearCaptchaText(){
	document.getElementById('captcha_id').src = 'captcha.jpg?' + Math.random();  
	$("#captchaImgText").val("");
	return false;
}

function restrictNumberTyping(obj){
	obj.value = obj.value.replace(/[^[a-zA-Z\s]*$/, '');
}

$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	
	$("#cancel").click(function(){
		var form = $(this).parents('form:first');
		var id = form[0].id;
		$("#"+id)[0].reset();
		$('input').removeClass('input-error');
		$('input').removeClass('input-correct');
		$('.cust-error').text('');
		
	});
	
	$('#editCancel').click(function(e){
    	$("#commonEditAccordionId").click();
    	$("#addCustomer").click();
    	 $('#addCustomer').show();
    	 $('#toggle').show();
    	e.preventDefault();
    });
	
});

function getHomePage(){
	return 'home';
}

function validateGstinStateCode(id, spanid, msg) {
	var result=false;
	var stateCode=$("#" + id).val().substring(0, 2);
	
	if (stateCode ==0 || stateCode > 37) {
		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg);
		$("#" + spanid).show();
		result=true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
		result=false;
	}

	return result;
}

function callCustomErrorPage(status,responseText,error,methodName){

	 $.ajax({
			url : "logCustomError",
			method : "post",
			data : { status : status , responseText : responseText , error : error, methodName : methodName },
			/*contentType : "application/json",*/
			dataType : "json",
			async : false,
			success:function(json){
				 window.location = "customErrorPage.jsp";
	        },
	        error:function(data,status,er) { 
	        }
	});
}


function roundToTwoDecimal(num) {    
    return +(Math.round(num + "e+2")  + "e-2");
}

function checkNConvertToDecimal(value){
	var newValue = 0;
	if(value === '' || value === undefined || value === null)
		newValue = parseFloat(newValue).toFixed(2);
	else		
		newValue = parseFloat(roundToTwoDecimal(value)).toFixed(2);
	
	return newValue;
}

function checkNConvertToInteger(val){
	var newIntValue = 0;
	if(val === '' || val === undefined || val === null)
		newIntValue = parseInt(newIntValue);
	else
		newIntValue = parseInt(val);
	
	return newIntValue;
}


function multiSelectCheckBox(id,type, spanid){
	var result=false;
	var options = $('input[type='+type+' ]:checked');
	
    if(options.length == 0){
    	$("#"+id).addClass("input-error").removeClass("input-correct");
    	/*$("#"+spanid).text("Select atleast one value");*/
    	$("#"+spanid).show();
    	result = true;
    } else {
    	$("#"+id).addClass("input-correct").removeClass("input-error");
    	$("#"+spanid).hide();
    	result = false;
    }
    
    return result;
}
