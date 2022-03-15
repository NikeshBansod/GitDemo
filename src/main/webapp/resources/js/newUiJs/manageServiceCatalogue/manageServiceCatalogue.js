var StoresGstinRowNum = 0;
var accordionsId = '';
var userGstinJson = '';
var advolCessRateJson = '';
var nonAdvolCessRateJson = '';
var dnyGstinOpeningStockServiceTable='';
var gstinsInventoryJson = '';
var blankMsg = "This field is required";
var length = 2;
var lengthMsg = "Minimum length should be ";
var serviceExists = false;
var contactNumRegex = /[2-9]{2}\d{8}/;
var regMsg = "Service Rate should be in proper format";
var igstMsg = "Rate of tax (%) should be in proper format";
// var currencyRegex =/(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\.[0-9]{1,4})?$/;
var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var percentRegex = /(^99([.]0{1,2})?)$|(^\d{1,2}([.]\d{1,2})?)$/;
var serviceIgstMsg = 'Rate of Tax for service should me less than 100';


jQuery(document).ready(function($) {
	loadServiceRateOfTax();
	loadServiceList();
	$('#showProductGrid').hide();
	loadGstinDataForUserData();
	loadGstinsInventory();
	
	dnyGstinOpeningStockServiceTable = $('#dny-service-gstin-openingstock').DataTable({
		 rowReorder: {
		        selector: 'td:nth-child(2)'
		 },
		 responsive: true,
		 searching: false,
		 /*bPaginate: false,*/
		 bLengthChange: false,
	});
	setDataInOpeningStockService();
	
	/*loadAdvolCessRate();
	loadNonAdvolCessRate();*/
	$('.loader').fadeOut("slow");

	$("#addServiceDetails").hide();
	$('#addheader').hide();

	$('#addServiceButton').on('click', function(e) {
		$('.loader').show();
		$('#addServiceDetails').slideToggle();
		$('#listheader').hide();
		$('#listTable').hide();
		$('#addServiceButton').hide();
		$('#addServicediv').hide();
		$('#addheader').show();
		$('.loader').fadeOut("slow");
		e.preventDefault();
	})
	$('#gobacktolisttable').on('click', function(e) {
		$('.loader').show();
		$('#listheader').show();
		$('#listTable').show();
		$('#addServiceButton').show();
		$('#addheader').hide();
		$('#addServiceDetails').hide();
		$('#addServicediv').show();
		$('.loader').fadeOut("slow");
	});
});

$(document).ready(function() {
	$("#srvSubmitBtn").click(function(e) {
		$('.loader').show();
		var errsacdesc = sacDesc();
		var errsacCode = sacCode();
		var errsacServiceName = sacServiceName();
		var errsacUOM = false; /* sacUOM(); */
		var errsacServiceRate = sacServiceRate();
		var errServiceIGST = sacServiceIgst();
		serviceExists = checkIfServiceAlreadyExists($("#name").val());
		if ($("#unitOfMeasurement").val() == "OTHERS") {
			var errOtherUOM = validateOtherUOM();
			$("#tempUom").val($("#unitOfMeasurement").val()+ '-'+ '('+ $("#otherUOM").val()+ ')');
		}
		if ((errsacdesc) || (errsacCode) || (errsacServiceName) || (errsacUOM) || (errsacServiceRate) || (errServiceIGST) || (serviceExists) || (errOtherUOM)) {
			$('.loader').fadeOut("slow");
			e.preventDefault();
		} else {
			//var totalStoresRecordNo = $("#toggleStoresGstin").children().length;
			var editPage = $("#editPage").val();
			if (editPage != 'true') {
				/*if (totalStoresRecordNo == 0) {
					bootbox.alert("Please add atleast one STORE");
					e.preventDefault();
				} else {*/
					errorStatus = false;
					callServiceFormSubmit();
					e.preventDefault();
				/*}*/
			}
		}

		if ((errsacdesc) || (errsacCode)) {
			focusTextBox("search-sac");
		} else if ((errsacServiceName)) {
			focusTextBox("name");
		} else if ((errsacUOM)) {
			focusTextBox("unitOfMeasurement");
		} else if ((errsacServiceRate)) {
			focusTextBox("serviceRate");
		} else if ((errServiceIGST)) {
			focusTextBox("serviceIgst");
		} else if ((errOtherUOM)) {
			focusTextBox("otherUOM");
		}
			
		

		$('.loader').fadeOut("slow");
	});



	$("#search-sac").on("input keyup click",function(e) {
		if (e.keyCode == 32) {
			this.value = removeWhiteSpace(this.value);
		}
		if ($("#sacDescription").val() === "" || $("#sacCode").val() === "") {
			$("#reg-gstin-req, #ser-sac-desc, #ser-sac-code").show();
			$("#search-sac").addClass("input-error").removeClass("input-correct");
		}
	
	});
	
	$("#name").on("keyup input",function() {
		// this.value = removeWhiteSpace(this.value);
		this.value = this.value.replace(/[\\[]*$/, '');
		// this.value = this.value.replace(/[^[a-zA-Z-,]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9.-\s]*$/, '');
		if ($("#name").val() === "") {
			$("#name").addClass("input-error").removeClass("input-correct");
			$("#ser-name").show();
		}
		if ($("#name").val() !== "") {
			$("#name").addClass("input-correct").removeClass("input-error");
			$("#ser-name").hide();
		}
	});
	
	$("#unitOfMeasurement").on("keyup click",function() {
		if ($("#unitOfMeasurement").val() === "") {
			$("#unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			$("#ser-uom").show();
		}
		if ($("#unitOfMeasurement").val() !== "") {
			$("#unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			$("#ser-uom").hide();
		}
	});
	
	$("#unitOfMeasurement").change(function() {
		if ($("#unitOfMeasurement").val() === "") {
			$("#unitOfMeasurement").addClass("input-error").removeClass("input-correct");
			$("#ser-uom").show();
		}
		if ($("#unitOfMeasurement").val() !== "") {
			$("#unitOfMeasurement").addClass("input-correct").removeClass("input-error");
			$("#ser-uom").hide();
		}
	});
	
	$("#otherUOM").on("keyup input",function() {
		if ($("#otherUOM").val() === "") {
			$("#otherUOM").addClass("input-error").removeClass("input-correct");
			$("#otherOrgType-req").show();
		}
		if ($("#otherUOM").val() !== "") {
			$("#otherUOM").addClass("input-correct").removeClass("input-error");
			$("#otherOrgType-req").hide();
		}
	});
	
	$("#serviceRate").on("keyup input",function() {
		this.value = this.value.replace(/[^[0-9.]*$/, '');
	
		if (currencyRegex.test($("#serviceRate").val()) === true) {
			$("#ser-rate").hide();
			$("#serviceRate").addClass("input-correct").removeClass("input-error");
		}
		if (currencyRegex.test($("#serviceRate").val()) !== true) {
			$("#ser-rate").text(regMsg);
			$("#ser-rate").show();
			$("#serviceRate").addClass("input-error").removeClass("input-correct");
		}
	});
	
	$("#serviceIgst").change(function() {
		// this.value = this.value.replace(/[^[0-9.]*$/, '');
	
		if (percentRegex.test($("#serviceIgst").val()) === true) {
			$("#service-igst").hide();
			$("#serviceIgst").addClass("input-correct").removeClass("input-error");
		}
	
		if (percentRegex.test($("#serviceIgst").val()) !== true) {
			$("#service-igst").text(igstMsg);
			$("#service-igst").show();
			$("#serviceIgst").addClass("input-error").removeClass("input-correct");
		}
	});
	
	$("#serviceCancelBtn").on("click", function(e) {
		resetStoresGstinFormValues();
	});
	
	$("#serviceSaveBtn").on("click",function(e) {
		var isValidationDone = false;
		var serviceGstin = validateSelectField("gstnStateId","gstnStateId-csv-id");
		var serviceLocation = validateSelectField("location", "location-csv-id");
		if ((serviceGstin) || (serviceLocation)) {
			e.preventDefault();
		} else {
			var serviceGstinText = $("#gstnStateId").find("option:selected").text();
			var serviceLocationText = $("#location").find("option:selected").text();
			if (checkIfGstinExistsForLocation()) {
				e.preventDefault();
				resetStoresGstinFormValues();
				bootbox.alert("Service already Added for GSTIN-"+ serviceGstinText+ " and Store-"+ serviceLocationText+ ". ");
				e.preventDefault();
			} else {
				constructDynamicDivForStoresGstin();
				e.preventDefault();
			}
		}
	});
	
	$("#gstnStateId").on("change",function() {
		$('#calculateStock').prop('disabled',false);
	
		if ($('#showProductGrid').is(':visible')) {
			$('#showProductGrid').hide();
			$("#openingstockInventoryTab").DataTable().rows().remove();
		}
	
		var gstinSelectedId = $('select#gstnStateId option:selected').val();
		$("#location").empty();
		$("#location").append('<option value="">Select</option>');
		$.each(gstinJson,function(i, value) {
			if (value.id == gstinSelectedId) {
				$.each(value.gstinLocationSet,function(i2,value2) {
					$("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
				});
	
			}
		});
	
	});
	
	$("#location").on("change",function() {
		$('#calculateStock').prop('disabled', false);
		if ($('#showProductGrid').is(':visible')) {
			$('#showProductGrid').hide();
			$("#openingstockInventoryTab").DataTable().rows().remove();
		}
	});
	
	// Removed the HTML5 required message box
	document.addEventListener('invalid', (function() {
		return function(e) {
			// prevent the browser from showing default error bubble/ hint
			$('.loader').fadeOut("slow");
			e.preventDefault();
			// optionally fire off some custom validation handler myvalidationfunction();
		};
	})(), true);
	
	$("#divOtherUnitOfMeasurement").hide();
	$("#unitOfMeasurement").change(function() {
		otherUnitOfMeasurement();
	
	});
	$('.loader').fadeOut("slow");
});


function sacDesc() {
	errsacdesc = validateTextField("sacDescription","ser-sac-desc", blankMsg);
	if (!(errsacdesc)) {
		errsacdesc = validateFieldLength("sacDescription","ser-sac-desc", lengthMsg, length);
	}
	return errsacdesc;
}

function sacCode() {
	errsacCode = validateTextField("sacCode","ser-sac-code", blankMsg);
	if (!(errsacCode)) {
		errsacCode = validateFieldLength("sacCode","ser-sac-code", lengthMsg, length);
	}
	return errsacCode;
}

function sacServiceName() {
	errsacServiceName = validateTextField("name","ser-name", blankMsg);
	if (!(errsacServiceName)) {
		errsacServiceName = validateFieldLength("name","ser-name", lengthMsg, length);
	}
	return errsacServiceName;
}

function sacUOM() {
	errsacUOM = validateTextField("unitOfMeasurement","ser-uom", blankMsg);
	/*if(!(errsacUOM)){ errsacUOM = validateFieldLength("unitOfMeasurement","ser-uom",lengthMsg,length); }*/
	return errsacUOM;
}

function sacServiceRate() {
	errsacServiceRate = validateTextField("serviceRate","ser-rate", blankMsg);
	if (!errsacServiceRate) {
		errsacServiceRate = validateRegexpressions("serviceRate", "ser-rate", regMsg,currencyRegex);
	}
	return errsacServiceRate;
}

function sacServiceIgst() {
	errServiceIGST = validateTextField("serviceIgst","service-igst", blankMsg);
	if (!errServiceIGST) {
		errServiceIGST = validateRegexpressions("serviceIgst", "service-igst",serviceIgstMsg, percentRegex);
	}
	return errServiceIGST;
}

function validateOtherUOM() {
	errOtherUOM = validateTextField("otherUOM","otherOrgType-req", blankMsg);
	if (!errOtherUOM) {
		errOtherUOM = validateFieldLength("otherUOM","otherOrgType-req", lengthMsg, 1);
	}
	return errOtherUOM;
}

function loadServiceList() {
	$.ajax({
		url : "getServicesListJson",
		type : "POST",
		dataType : "json",
		data : {
			_csrf_token : $("#_csrf_token").val()
		},
		async : false,
		success : function(json, fTextStatus, fRequest) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if (isValidToken(json) == 'No') {
				window.location.href = getCsrfErrorPage();
				return;
			}

			$.each(json,function(i, value) {
				// alert(value.userName);
				var dynamicUom = '<p> Unit Of Measurement : '+ value.unitOfMeasurement+ '</p>';
				if (value.unitOfMeasurement == "OTHERS") {
					dynamicUom = '<p> Unit Of Measurement : '+ value.otherUOM + '</p>';
				}
				$('#ServiceValuesTab tbody:last-child').append(
					'<tr>'
						+ '<td><a href="#" onclick="javascript:editRecord('+ value.id+ ');">'+ value.name+ '</a></td>'
						+ '<td >'+ value.serviceRate+ '</td>'
						+ '<td >'+ value.sacCode+ '</td>'
						+ '<td >'+ value.serviceIgst+ '</td>'
						/*+ '<td ><a href="#" onclick="javascript:editRecord('+ value.id+ ');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>'
						+ '<td><a href="#" onclick="javascript:deleteRecord('+ value.id+ ');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'
					*/+ '</tr>'
				);
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error : function(data, status, er) {
			getInternalServerErrorPage();
		}
	});
	//createDataTable('ServiceValuesTab');
	
	 $('#ServiceValuesTab').DataTable({
     	bSort: false,
	        rowReorder: {
	              selector: 'td:nth-child(2)'
	        },
         responsive: true
 });
}

function otherUnitOfMeasurement() {
	if ($("#unitOfMeasurement").val() === "OTHERS") {
		$("#divOtherUnitOfMeasurement").show();
	} else {
		$("#divOtherUnitOfMeasurement").hide();
		$("#otherUOM").val("");
	}
}

function editRecord(idValue) {
	$('.loader').show();
	document.manageService.action = "./editManageServiceCatalogue";
	document.manageService.id.value = idValue;
	document.manageService._csrf_token.value = $("#_csrf_token").val();
	document.manageService.submit();
	$('.loader').fadeOut("slow");
}

function deleteRecord(lLocationId) {
	bootbox.confirm("Are you sure you want to delete ?", function(result) {
		if (result) {
			$('.loader').show();
			document.manageService.action = "./deleteManageServiceCatalogue";
			document.manageService.id.value = lLocationId;
			document.manageService._csrf_token.value = $("#_csrf_token").val();
			document.manageService.submit();
			$('.loader').fadeOut("slow");
		}
	});
}

function loadServiceRateOfTax() {
	var editPage = $("#editPage").val();
	var selRateOfTax = $("#serviceRateOfTax").val();

	$.ajax({
		url : "getServiceRateOfTaxDetails",
		type : "POST",
		dataType : "json",
		data : {
			_csrf_token : $("#_csrf_token").val()
		},
		async : false,
		success : function(json, fTextStatus, fRequest) {
			$("#serviceIgst").empty();
			if (editPage != 'true') {
				$("#serviceIgst").append('<option value="">Select</option>');
			}
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if (isValidToken(json) == 'No') {
				window.location.href = getCsrfErrorPage();
				return;
			}

			$.each(json, function(i, value) {
				if (editPage == 'true') {
					if (selRateOfTax == value.taxRate) {
						$("#serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate).attr('selected','selected'));
					} else {
						$("#serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
					}
				} else {
					$("#serviceIgst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
				}
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error : function(data, status, er) {
			getInternalServerErrorPage();
		}
	});
}
/*
 * function loadUnitOfMeasurement(){ var editPage =$("#editPage").val();
 * if(editPage == 'true'){ var selectType =$("#unitOfMeasurementHidden").val();
 * if(selectType=="OTHERS"){ $("#divOtherUnitOfMeasurement").show(); } $.ajax({
 * url:"getUnitOfMeasurement", type : "POST", dataType: 'json',
 * data:{_csrf_token : $("#_csrf_token").val()}, async : false,
 * success:function(json,fTextStatus,fRequest) {
 * 
 * if (isValidSession(json) == 'No') { window.location.href =
 * getDefaultSessionExpirePage(); return; }
 * 
 * if(isValidToken(json) == 'No'){ window.location.href = getCsrfErrorPage();
 * return; } setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
 * $.each(json, function(i, value) {
 * 
 * if(selectType==value.quantityDescription){ $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription).attr('selected','selected'));
 * }else{ $('#unitOfMeasurement').append($('<option>').text(value.quantityDescription).attr('value', value.quantityDescription)); }
 * 
 * }); }, error: function (data,status,er) {
 * 
 * getInternalServerErrorPage(); } }); } else {
 * 
 * $.ajax({ url : "getUnitOfMeasurement", type : "POST", dataType : "json",
 * data:{_csrf_token : $("#_csrf_token").val()}, async : false,
 * success:function(json,fTextStatus,fRequest){
 * 
 * $("#unitOfMeasurement").empty(); $("#unitOfMeasurement").append('<option
 * value="">Select</option>');
 * 
 * if (isValidSession(json) == 'No') { window.location.href =
 * getDefaultSessionExpirePage(); return; }
 * 
 * if(isValidToken(json) == 'No'){ window.location.href = getCsrfErrorPage();
 * return; } setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
 * $.each(json,function(i,value) { $("#unitOfMeasurement").append($('<option>').text(value.quantityDescription).attr('value',value.quantityDescription));
 * });
 *  }, error: function (data,status,er) {
 * 
 * getInternalServerErrorPage(); } }); } }
 */// end of loadUnitOfMeasurement method

function loadGstinDataForUserData() {
	$.ajax({
		url : "getgstinforloggedinuser",
		method : "POST",
		contentType : "application/json",
		dataType : "json",
		async : false,
		headers : {
			_csrf_token : $("#_csrf_token").val()
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
			if (json != null) {
				if (json.error == false) {
					gstinJson = json.result;
					userGstinJson = json.result;
					$("#gstnStateId").empty();
					$("#location").empty();
					if (json.result.length == 1) {

						$("#gstnStateIdDiv").hide();
						$.each(json.result, function(i, value) {
							$("#gstnStateId").append($('<option>').text(value.gstinNo + ' [ '+ value.stateInString+ ' ] ').attr('value',value.state));
						});

						// if location length is 1 hide location else show
						// location dropdown
						if (json.result[0].gstinLocationSet.length == 1) {
							$.each(json.result[0].gstinLocationSet, function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value', value.id));
								$("#locationStore").val(value.gstinStore);
							});

						} else {
							$("#locationDiv").show();
							$("#location").append('<option value="">Select</option>');
							$.each(json.result[0].gstinLocationSet, function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value', value.id));
							});
						}

					} else {
						$("#gstnStateIdDiv").show();
						$("#gstnStateId").append('<option value="">Select</option>');
						$.each(json.result, function(i, value) {
							$("#gstnStateId").append($('<option>').text(value.gstinNo + ' [ '+ value.stateInString+ ' ] ').attr('value',value.id));
						});
					}
				} else {
					bootbox.alert(json.errorcode + ' ' + json.message);
				}
			}
		},
		error : function(data, status, er) {

			getInternalServerErrorPage();
		}
	});
}

function resetStoresGstinFormValues() {
	loadGstinDataForUserData();
	$("#edtUniq").val("");
	$("#serviceSaveBtn").show();
}

function constructDynamicDivForStoresGstin() {
	StoresGstinRowNum++;
	var serviceGstinText = $("#gstnStateId").find("option:selected").text();
	var serviceGstinValue = $('select#gstnStateId option:selected').val();
	var serviceLocationText = $("#location").find("option:selected").text();
	var serviceLocationValue = $('select#location option:selected').val();
	// append dynamic div
	var $toggle = $("#toggleStoresGstin");
	var recordNo = StoresGstinRowNum;
	$toggle.append('<div class="cust-content" id="service__start_' + recordNo
			+ '">' + '<div class="heading">' + '<div class="cust-con">'
			+ '<h1 id="service_location_' + recordNo + '">'
			+ serviceLocationText + '</h1>' + '</div>'
			+ '<div class="cust-edit">' + '<div class="cust-icon">'
			+ '<a href="#" onclick="javascript:remove_service_row(' + recordNo
			+ ');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
			+ '</div>' + '</div>' + '</div>' + '<div class="content">'
			+ '<div class="cust-con">' + '<p id="service_gstin_' + recordNo
			+ '" >GSTIN : ' + serviceGstinText + ' </p>' + '</div>'
			+ '<input type="hidden" id="service_gstin-' + recordNo
			+ '" name="" value="' + serviceGstinText + '">'
			+ '<input type="hidden" id="service_gstin_val-' + recordNo
			+ '" name="" value="' + serviceGstinValue + '">'
			+ '<input type="hidden" id="service_location-' + recordNo
			+ '" name="" value="' + serviceLocationText + '">'
			+ '<input type="hidden" id="service_location_val-' + recordNo
			+ '" name="" value="' + serviceLocationValue + '">'

			+ '</div>' + '</div>');
	openCloseAccordion(recordNo);
	resetStoresGstinFormValues();

}

function openCloseAccordion(rowNum) {
	var currId = "/" + rowNum;
	// alert("accordionsId ->"+accordionsId);
	if (accordionsId.includes(currId)) {

	} else {
		$("#service__start_" + rowNum + " .content").hide();
		$("#service__start_" + rowNum + " .heading").click(function() {
			$(this).next(".content").slideToggle();
		});
		accordionsId = accordionsId + "," + currId;
	}

}

function updateExistingRecord() {

	var recordNo = $("#edtUniq").val();
	var serviceGstinText = $("#gstnStateId").find("option:selected").text();
	var serviceGstinValue = $('select#gstnStateId option:selected').val();
	var serviceLocationText = $("#location").find("option:selected").text();
	var serviceLocationValue = $('select#location option:selected').val();

	$("#service_gstin_" + recordNo).html('');
	$("#service_location_" + recordNo).html('');

	$("#service_location_" + recordNo).text(serviceLocationText);
	$("#service_gstin_" + recordNo).text("GSTIN : " + serviceGstinText);

	$("#service_gstin-" + recordNo).val(serviceGstinText);
	$("#service_gstin_val-" + recordNo).val(serviceGstinValue);
	$("#service_location-" + recordNo).val(serviceLocationText);
	$("#service_location_val-" + recordNo).val(serviceLocationValue);
}

function remove_service_row(recordNo) {
	bootbox.confirm("Are you sure you want to remove ?", function(result) {
		if (result) {
			$('#service__start_' + recordNo).remove();
			accordionsId = accordionsId.replace(",/" + recordNo, "");
		}
	});

}

function checkIfGstinExistsForLocation() {
	var stockExists = false;

	var serviceGstinValue = $('select#gstnStateId option:selected').val();
	var serviceLocationValue = $('select#location option:selected').val();

	// get add charges from list in javascript - Start
	var $addChgToggle = $("#toggleStoresGstin");
	var totalACRecordNo = $addChgToggle.children().length;
	/*
	 * var jsonObjectAC; var acListArray = new Array();
	 */
	if (totalACRecordNo == 0) {
		stockExists = false;
	} else {
		for (i = 0; i < totalACRecordNo; i++) {
			// Start
			var index2 = $addChgToggle.children()[i].id.lastIndexOf("_");
			var num2 = $addChgToggle.children()[i].id.substring(index2);
			num2 = num2.replace("_", "-");
			if (($("#service_gstin_val" + num2).val() == serviceGstinValue) && ($("#service_location_val" + num2).val() == serviceLocationValue)) {
				stockExists = true;
				break;
			}
			// End
		}

	}
	// get add charges from list in javascript - End
	return stockExists;
}

function edit_stores_gstin_row(recordNo) {
	$("#serviceSaveBtn").hide();
	var serviceGstin = $("#service_gstin_val-" + recordNo).val();
	var serviceLocation = $("#service_location_val-" + recordNo).val();

	changeValueInGstinAndStoreDropdown(serviceGstin, serviceLocation);
	$("#edtUniq").val(recordNo);
}

function changeValueInGstinAndStoreDropdown(serviceGstin, serviceLocation) {
	if (userGstinJson == '') {
		loadGstinDataForUserData();
	}
	$("#gstnStateId").empty();
	$("#location").empty();
	$.each(userGstinJson, function(i, value) {
		if (serviceGstin == value.id) {
			$("#gstnStateId").append($('<option>').text(value.gstinNo + ' [ ' + value.stateInString+ ' ] ').attr('value', value.id).attr('selected', 'selected'));
			$("#location").append('<option value="">Select</option>');
			$.each(value.gstinLocationSet, function(i2, value2) {
				if (value2.id == serviceLocation) {
					$("#location").append(
							$('<option>').text(value2.gstinLocation).attr('value', value2.id).attr('selected','selected'));
				} else {
					$("#location").append($('<option>').text(value2.gstinLocation).attr('value', value2.id));
				}

			});

		} else {
			$("#gstnStateId").append($('<option>').text(value.gstinNo + ' [ ' + value.stateInString+ ' ] ').attr('value', value.id));
		}
	});

}

function callServiceFormSubmit() {

	var serviceJsonData = constructService();

	$.ajax({
		url : "serviceSaveAjax",
		method : "post",
		headers : {
			_csrf_token : $("#_csrf_token").val()
		},
		data : JSON.stringify(serviceJsonData),
		contentType : "application/json",
		dataType : "json",
		async : false,
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
			if (json.status == 'SUCCESS') {
				bootbox.alert({
					message : json.message,
					callback : function() {
						console.log('This was logged in the callback!');
						window.location.href = 'manageServiceCatalogue';
						return;
					}
				})
			} else {
				bootbox.alert(json.message);
			}

		},
		error : function(data, status, er) {

			getInternalServerErrorPage();
		}
	});
}

function constructService() {
	var sacDescription = $("#sacDescription").val();
	var sacCode = $("#sacCode").val();
	var name = $("#name").val();
	var serviceIgst = $('select#serviceIgst option:selected').val();
	var serviceRate = $("#serviceRate").val();
	var unitOfMeasurement = "NA";
	var otherUOM = $("#otherUOM").val();
	var sacCodePkId = $("#sacCodePkId").val();
	var advolCess = 0;
	var nonAdvolCess = 0;

	// get add charges from list in javascript - Start
	var jsonObjectAC;
	var acListArray = new Array();
	dnyGstinOpeningStockServiceTable.rows().every( function ( index, tableLoop, rowLoop ) {
		var rowX = dnyGstinOpeningStockServiceTable.row(index).node();
	    var row = $(rowX);
	    jsonObjectAC = new Object();
	    jsonObjectAC.gstnId = $("#dny-service-opening_stock_gstin_val_"+index).val();
		jsonObjectAC.storeId = $("#dny-service-opening_stock_location_val_"+index).val();
		acListArray.push(jsonObjectAC);
   });

	// get add charges from list in javascript - End

	var inputData = {
		"name" : name,
		"sacCode" : sacCode,
		"sacDescription" : sacDescription,
		"unitOfMeasurement" : unitOfMeasurement,
		"otherUOM" : otherUOM,
		"serviceRate" : serviceRate,
		"serviceIgst" : serviceIgst,
		"sacCodePkId" : sacCodePkId,
		"advolCess" : advolCess,
		"nonAdvolCess" : nonAdvolCess,
		"storesBean" : JSON.parse(JSON.stringify(acListArray))
	};

	console.log("Final JSON : " + JSON.stringify(inputData));
	return inputData;
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

function setDataInOpeningStockService(){
	if(gstinsInventoryJson == ''){
		loadGstinsInventory();
	}
	dnyGstinOpeningStockServiceTable.clear().draw();
	counter = 0;
	$.each(gstinsInventoryJson,function(i,value){
		var gstinNumber = value.gstinNo;
		var gstinId = value.id;
		$.each(value.gstinLocationSet,function(i,value2){	
			
			dnyGstinOpeningStockServiceTable.row.add($(
		 			'<tr id="'+counter+'" >'
		 				+'<td>'+(counter+1)+'<input type="hidden" class="xCount_'+counter+'" value="'+counter+'"></td>'
		 				+'<td>'+gstinNumber+'<input type="hidden" id="dny-service-opening_stock_gstin_val_'+counter+'" value="'+gstinId+'"></td>'
		 				+'<td>'+value2.gstinLocation+'<input type="hidden" id="dny-service-opening_stock_location_val_'+counter+'" value="'+value2.id+'"></td>'
		 			+'</tr>'					 	
			)).draw();
			counter++;
	      });
	   
    });
}
