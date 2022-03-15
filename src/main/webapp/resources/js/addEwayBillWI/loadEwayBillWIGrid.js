var productRateJson='';

function getInputHSNDataJson(val){
	 var inputData = {
		"key" : val
     };
	 return inputData;
}

var EwayBill = (function(data){
	function clearTextField() {
		$(this).parent().parent().find('input').val('');
	}
	var c = 0;
	function addRowsContent(){
		var counter = $("#itemCounter").val();
		x = 'description_'+counter;
		$(".addrow").append('<div class="rows newrowgrid">'
		+'            <input type="hidden" class="form-control mb10 hsnId" id="hsnId_'+counter+'" name="hsnId_'+counter+'" value="">'
		+'            <div class="col-md-2">'
		+'                <label>Product by HSN/Description</label>'
		+'                <input type="text" class="form-control mb10 description " placeholder="Search" data-msg="This field is required." id="'+counter+'"  name="description_'+counter+'" >'
		+'            </div>'
		+'            <div class="col-md-2">'
		+'                <label>Product Name<span class="red-text">&bull;</span></label>'
		+'                <input type="text" class="form-control mb10 require addItemRequire productName" data-msg="This field is required." id="productName_'+counter+'" name="productName_'+counter+'" maxlength="100" >'
		+'            </div>'
		+'            <div class="col-md-1">'
		+'                <label>HSN <span class="red-text">&bull;</span></label>'
		+'                <input type="text" class="form-control mb10 require addItemRequire hsn" data-msg="" id="hsn_'+counter+'" name="hsn_'+counter+'" readonly="readonly">'
		+'            </div>'
		+'            <div class="col-md-1">'
		+'                <label>Quantity <span class="red-text">&bull;</span></label>'
		+'                <input type="text" class="form-control mb10 addItemRequire require quantity" data-msg="This field is required." id="quantity_'+counter+'" name="quantity_'+counter+'">'
		+'            </div>'
		+'            <div class="col-md-1">'
		+'                <label>Unit <span class="red-text">&bull;</span></label>'
		+'                <select class="form-control require addItemRequire unitOfMeasurement" id="unitOfMeasurement_'+counter+'" data-msg="This field is required." name="unitOfMeasurement_'+counter+'">'
		+'                </select><i class="clearfix"></i>'
		+'            </div>'
		+'			  <div class="col-md-2">'
		+'			  	<div class="tax_row">'	
		+'					<div class="col-xs-4">'
		+'						<label>Rate<span class="red-text">&bull;</span></label>'
		+'						<input type="text" class="form-control mb10 require addItemRequire rate"  data-msg="This field is required."  id="rate_'+counter+'" name="rate_'+counter+'" >'
		+'					</div>'
		+'					<div class="col-xs-8">'
		+'						<label>Taxable value (RS)<span class="red-text">&bull;</span></label>'
		+'						<input type="text" class="form-control mb10 taxableValue" data-msg="" id="taxableValue_'+counter+'" name="taxableValue_'+counter+'" readonly="readonly">'
		+'					</div>'
		+'				</div><i class="clearfix"></i>'
		+'			  </div>'
		+'            <div class="col-md-3">'
		/*+'          	<label>Tax Amount(CGST+SGST+IGST+Cess)<span class="red-text">&bull;</span> </label>'*/
		+'				<div class="tax_row">'
		+'					<div class="col-xs-6 col-sm-3">'
		+'						<label>CGST+ SGST Rate(%)<span class="green-txt fs24">•</span></label>'	
		+'						<select class="form-control mb10 cgstsgstrate " id="cgst_sgstrate_'+counter+'" name="cgst_sgstrate_'+counter+'"></select>'
		+'					</div>'
		+'					<div class="col-xs-6 col-sm-3">'
		+'						<label>IGST Rate(%)<span class="green-txt fs24">•</span></label>'	
		+'						<select class="form-control mb10 igstrate" id="igstrate_'+counter+'" name="igstrate_'+counter+'"></select>'
		+'					</div>'
		+'					<div class="col-xs-6 col-sm-3">'
		+'						<label>CESS Advol Rate(%)<span class="green-txt fs24">•</span></label>'	
		+'						<select class="form-control mb10 " id="cessadvolrate_'+counter+'" name="cessadvolrate_'+counter+'"></select>'
		+'					</div>'
		+'					<div class="col-xs-6 col-sm-3">'
		+'						<label>CESS non.Advol Rate(%)<span class="green-txt fs24">•</span></label>'	
		+'						<select class="form-control mb10 " id="cessnonadvolrate_'+counter+'" name="cessnonadvolrate_'+counter+'"></select>'
		+'					</div>'
		+'				</div>'
		+'            </div>'
		+'            <button class="removerow"><i class="fa fa-trash"></i></button>'
		+'            <i class="clearfix"></i>'
		+'</div>');
		loadUnitOfMeasurementEwayBillWI(counter);
		//loadProductRate(counter);

					$('#'+counter).autocomplete({
						source: function (request, response) {

					    	var clientId = $("#clientId").val(); 
					    	var secretKey = $("#secretKey").val(); 
					    	var appCode = $("#appCode").val();
							var ipUsr = $("#ipUsr").val();
					    	
					    	var inputData = {
					    			"key" : request.term
					    	     };
					    	 $.ajax({
					    		url : "ewaybill/getewbHSNCodeList",
					    		type : "POST",
					    		contentType : "application/json",
					    		dataType : "json",
					    		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
					    		data : JSON.stringify(inputData),
					    		async : false,
					    	    success: function(data) {
					    	    	if(data.status_cd == '0'){
					    	    		bootbox.alert("Invalid HSN. Please enter appropriate HSN");
					    	    	}else{
					    	    		response(data.map(function(value){
											x = '['+value.hsnCode +']'+ value.hsnDesc;
											y = value.hsnCode +'#'+ value.hsnDesc +'#'+ value.hsnId;
											return {
												label: x,
												value: y
											};
										}));
					    	    	}					    	    	
					             },
								 error: function (data,status,er) {
									 getInternalServerErrorPage();
								 }
					    	}); 
					    },
						minLength: 3,
					    select: function (event, ui) {
					    	var label = ui.item.label;
							var value = ui.item.value;
							
							var hsnCode = $.trim(value.split('#')[0]);
							var hsnDescription = $.trim(value.split('#')[1]);
							var hsnId = $.trim(value.split('#')[2]);
							ui.item.value = hsnDescription;
							
							$(this).parent().parent().find('.hsn').val(hsnCode);
							$(this).parent().parent().find('.hsnId').val(hsnId);
							$(this).val(hsnDescription);
							$(this).css({"border-color" : "#498648"});
							$(this).parent().parent().find('.hsn').css({"border-color" : "#498648"});
					    } 
					}); 
					$("#itemCounter").val(++counter);
	}
	function thisdate(d, m){
		date = d;
		var date = date.split('/');
		var mm = parseInt(date[1])-1;
		if(m == 'm'){
		var dd = parseInt(date[0])+1;
		}else{
		var dd = parseInt(date[0])-1;
		}
		var date1 = new Date(date[2], mm, dd);
		return date1;
	}
	function plusDate(){
		var date = $(this).parent().find('.transdocDate').val();
		var date1 = thisdate(date, 'm');
		$(this).parent().find('.transdocDate').datepicker('setDate', date1);
	}
	function minusDate(){
		var date = $(this).parent().find('.transdocDate').val();
		var date1 = thisdate(date, 'p');
		$(this).parent().find('.transdocDate').datepicker('setDate', date1);
	}
	
	
	function createBtn(text, type, c, fName){
		var submit = document.createElement("BUTTON");
		var t = document.createTextNode(text);
		submit.type = type;
		submit.appendChild(t);
		submit.classList.add("btn");
		submit.classList.add(c);
		submit.onclick = fName;
		document.getElementById("myBttn").appendChild(submit);
	}
	
	function hasEmpty(className) {
		var z = '';
		for (var i = 0; i < arguments.length; i++) {
			z += ' .' + arguments[i];
		}
		var has_empty = false;
		var is_empty = false;
		var fieldName ='';
		var iterate = true;
		$(z).each(function(){
			fieldName = $(this).context.id;
			if(fieldName == 'otherSubTypes' && $('select#subType option:selected').text() != "Others"){
				iterate = false;
			}else{
				iterate = true;
			}
			if(iterate){
				if (!$(this).val()){
					$(this).css({
						"border-color" : "#ff0000"
					});
					/*$(this).parent().find('p').remove();
					var msg = $(this).data('msg');
					if (typeof (msg) != 'undefined')
					{
						var DivClassName = $(this).parent().attr('class');
						if (DivClassName == 'input-group') {
							$(this).parent().parent().append('<p class="text-danger">' + msg + '</p>');
							} else {
							$(this).parent().append('<p class="text-danger">' + msg + '</p>');
						}
					}*/
					$(this).closest('.panel-collapse').parent().find('.panel-title').css({"background" : "#ff0000"});
					has_empty = true;
				} else{
					$(this).css({"border-color" : "#2f65b0"});
					$(this).closest('.panel-collapse').parent().find('.panel-title').css({"background" : "#144a94"});
					//$(this).parent().find('p').remove();
					has_empty = false;
				}
			}
			
			if(has_empty == true){
				is_empty = true;
			}
		});
		
		return is_empty;
	}
	var frmSubmit = (function(){
		
		var flagRequire = hasEmpty('require');
		var flagAddItemRequire = EwayBill.validation('addItemRequire');
		if(!flagRequire && !flagAddItemRequire){
			
			submitData();
		}else{
			$(".btn-info").prop("disabled",false);
			bootbox.alert("Please enter mandatory details.");
		}
	});
	var fromReset = (function(){
		$('.panel-title').css({"background": "#144a94"});
		$('input.form-control').css({"border-color": "#2f65b0"});
		// $('.form-control+p.text-danger').remove();
		document.getElementById("wayBill").reset();
	});
	return{
		clearText: function(){
			var clearFiled = $('.input-group-addon .fa-times-circle');
			for (var i = 0, j = clearFiled.length; i < j; i++) {
				clearFiled[i].onclick = clearTextField;
			}
		},
		popover:function(){
			$('[data-toggle="popover"]').popover();
			$(document).on('click', function (e) {
				$('[data-toggle="popover"],[data-original-title]').each(function () {
					if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
						(($(this).popover('hide').data('bs.popover')||{}).inState||{}).click = false  // fix
						// for
						// BS
						// 3.3.6
					}
				});
			});
		},
		createBtn:function(){
			createBtn('Submit', 'button', 'btn-info', frmSubmit);
			//createBtn('Exit', 'button', 'btn-danger', fromReset);
		},
		getDate:function(){
			var date = new Date();
			var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
			$('.transdocDate').datepicker({format: 'dd/mm/yyyy', autoclose:true,  todayHighlight: true, defaultViewDate: 'today'});
			$('.transdocDate').datepicker('setDate', today);
			var plusdate = $('.plusdate');
			var minusdate = $('.minusdate');
			for (var i = 0, j = plusdate.length; i < j; i++) {
				plusdate[i].onclick = plusDate;
			}
			for (var i = 0, j = minusdate.length; i < j; i++) {
				minusdate[i].onclick = minusDate;
			}
		},
		addRow:function(){
			addRowsContent();	
			$('.igstrate').prop("disabled", true);
			$('.cgstsgstrate').prop("disabled", true);
			$(".addrow").on('click', '.removerow', function(){
				$(this).parent().remove();
				var count = $("#itemCounter").val();
				var decount= --count;
				c = decount;
				$("#itemCounter").val(decount);
			});			
		},
		itemvalidation:function(){
			var flagForItemRequire = hasEmpty('forItemRequire');
			var flagAddItemRequire = hasEmpty('addItemRequire');
			if(!flagForItemRequire && !flagAddItemRequire){
				$('.addrow .removerow').remove();
				addRowsContent();
			}			
		},
		validation:function(r){
			return hasEmpty(r);			
		},
		calculate:function(){
			var flagForItemRequire = hasEmpty('forItemRequire');
			var flagAddItemRequire = hasEmpty('addItemRequire');
			if(!flagForItemRequire && !flagAddItemRequire){
				callCalculateAjax();
			}
		}
	}
});
 
$(document).on('keyup','.description', function(){
	var flag = EwayBill.validation('forItemRequire');
	if(flag){
		$(".description").val('');
		bootbox.alert("Please enter mandatory fields."); 
	}
});

function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

var cache = {},
lastXhr;

var EwayBill = new EwayBill();
EwayBill.popover();
EwayBill.clearText();
EwayBill.getDate();
EwayBill.addRow();
EwayBill.createBtn();

	
function loadUnitOfMeasurementEwayBillWI(counter){
	var clientId = $("#clientId").val();
	var secretKey = $("#secretKey").val();
	var appCode = $("#appCode").val();
	var ipUsr = $("#ipUsr").val();
	$.ajax({
		url : "ewaybill/getewbunitOfMeasurementmaster",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
		async : false,
		success:function(json){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			$('#unitOfMeasurement_'+counter).empty();
			$('#unitOfMeasurement_'+counter).append('<option value="">Select</option>');
			$.each(json,function(i,value) {
				$('#unitOfMeasurement_'+counter).append($('<option>').text(value.quantityDes).attr('value',value.quantityCode));
			});
		},
		error: function (data,status,er) {
			getInternalServerErrorPage();
		}
	});
}	

function loadProductRate(counter, igstDisabled, cgstDisabled){
	var inputRequest = getInputTransactionOrDocumentType("ewayBillRate");
	if(productRateJson == '' || productRateJson == undefined || productRateJson == null){
		productRateJson = callAjaxProductRate(inputRequest,"cgst_sgstrate_","igstrate_","cessadvolrate_","cessnonadvolrate_",counter); 
	}
	appendProductRate(productRateJson,"cgst_sgstrate_","igstrate_","cessadvolrate_","cessnonadvolrate_",counter,igstDisabled,cgstDisabled);
}
function appendProductRate(productRateJson,cgstSgstFieldId,igstFieldId,cessAdvolFieldId,cessNonAdvolFieldId,counter,igstDisabled,cgstDisabled){	
	$("#"+cgstSgstFieldId+""+counter).empty();
	if(cgstDisabled){
		$("#"+cgstSgstFieldId+""+counter).append('<option value="0.0">Select</option>');
	} else{
		$("#"+cgstSgstFieldId+""+counter).append('<option value="">Select</option>');
	}
	
	$("#"+igstFieldId+""+counter).empty();
	if(igstDisabled){
		$("#"+igstFieldId+""+counter).append('<option value="0.0">Select</option>');
	} else{
		$("#"+igstFieldId+""+counter).append('<option value="">Select</option>');
	}
//	$("#"+igstFieldId+""+counter).append('<option value="">Select</option>');
	$("#"+cessAdvolFieldId+""+counter).empty();
	$("#"+cessAdvolFieldId+""+counter).append('<option value="0">Select</option>');
	$("#"+cessNonAdvolFieldId+""+counter).empty();
	$("#"+cessNonAdvolFieldId+""+counter).append('<option value="0">Select</option>');		
	
	$.each(productRateJson.cgst_sgst_rate,function(i,value) {
		$("#"+cgstSgstFieldId+""+counter).append($('<option>').text(value.value).attr('value',value.code));
	});
	$.each(productRateJson.igst_rate,function(i,value) {
		$("#"+igstFieldId+""+counter).append($('<option>').text(value.value).attr('value',value.code));
	});
	$.each(productRateJson.cess_advol_rate,function(i,value) {
		$("#"+cessAdvolFieldId+""+counter).append($('<option>').text(value.value).attr('value',value.code));
	});
	$.each(productRateJson.cess_nonadvol_rate,function(i,value) {
		$("#"+cessNonAdvolFieldId+""+counter).append($('<option>').text(value.value).attr('value',value.code));
	});
}
function callAjaxProductRate(inputRequest,cgstSgstFieldId,igstFieldId,cessAdvolFieldId,cessNonAdvolFieldId,counter){	
	var response='';
	var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var appCode = $("#appCode").val(); 
	var ipUsr = $("#ipUsr").val();
	$.ajax({
		url : "ewaybill/getewbwimaster",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
		data : JSON.stringify(inputRequest),
		async : false,
		success:function(json){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			if(json.status == 'failure'){
	 
			}else{	
				response =  json;
				appendProductRate(json,cgstSgstFieldId,igstFieldId,cessAdvolFieldId,cessNonAdvolFieldId,counter, true, true);
			}			
         },
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
	}); 
	return response;
}
