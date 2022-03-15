var invoiceJson = '';
var itemChangeDataTable = '';
var invoiceItemId = [];
var invoiceItemCount = 0;

//invoice
var accordionsId = '';
var addChgAccordionsId = '';
var dynamicServiceAccordionsId = '';
var dynamicProductAccordionsId = '';
var userServiceList = '';
var userProductList = '';
var userGstinsJson = '';
var productAdvolCessRateJson = '';
var productNonAdvolCessRateJson = '';
var serviceAdvolCessRateJson = '';
var serviceNonAdvolCessRateJson = '';
var dnyGstinOpeningstockProductTable='';
var dnyGstinOpeningStockServiceTable='';
var gstinsInventoryJson = '';
//invoice

$(document).ready(function () {
	fetchInvoiceJson($("#unqIncDes").val());
	setBreadCrumHeaders();
	itemChangeDataTable = $('#itemChangeTable').DataTable();
	setInvoiceItemsInTable();
	callDefaultOnPageLoad($("#invoiceFor").val());
	loadGenericUserGstin();
	loadGstinsInventory();
	
});

function setBreadCrumHeaders(){
	var documentType = $("#invDocType").val();
	var invoiceNumber = $("#invNo").val();
	$("#generateInvoiceDefaultPageHeader").html("");
	var breadCrumHeader = '';
	
	if(documentType == 'invoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Item Change</strong>';
	}else if(documentType == 'billOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Item Change </strong>';
	}else if(documentType == 'rcInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Reverse Charge Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Item Change</strong>';
	}else if(documentType == 'eComInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Item Change</strong>';
	}else if(documentType == 'eComBillOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Item Change</strong>';
	}
	
	
	$("#generateInvoiceDefaultPageHeader").append(
			breadCrumHeader
	);
}

function backToRRPage(){
	var idValue = $("#unqIncDes").val();
	$('.loader').show();
	document.previewInvoice.action = "./getRevAndRet";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
	$('.loader').fadeOut("slow");
}

function fetchInvoiceJson(invoiceId){
	if(invoiceJson == ''){
		 $.ajax({
			url : "getInvoiceDetailsForCnDn",
			headers: {
				_csrf_token : $("#_csrf_token").val()
			},
			method : "post",
			data : { "invIdt" : invoiceId },
			/*contentType : "application/json",*/
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
				invoiceJson = json;
				if(invoiceJson){
					$("#invDocType").val(json.documentType);
					$("#invDataFiledToGstn").val(json.fileToGstn);
					$("#iterationNo").val(json.iterationNo);
					$("#invNo").val(json.invoiceNumber);
					$("#invoiceFor").val(json.invoiceFor);
					$("#invDate").val(formatDateInDDMMYYYY(json.invoiceDate));
					$("#gstnStateId").val(json.gstnStateId);
					$("#gstnStateIdInString").val(json.gstnStateIdInString);
					$("#location").val(json.location);
					$("#documentType").val(json.documentType);
					$("#locationStorePkId").val(json.locationStoreId);
				}
				$('.loader').fadeOut("slow");
			},
			error: function (data,status,er) {
		        getInternalServerErrorPage();   
		    }
			
		 });
	}
}

function setInvoiceItemsInTable(){
	if(invoiceJson == ''){
		fetchInvoiceJson($("#unqIncDes").val());
	}
	var counter = 1;
	$.each(invoiceJson.serviceList,function(i,value){
		invoiceItemCount++;
		invoiceItemId.push(parseInt(value.id));
		itemChangeDataTable.row.add($(
 			'<tr id="tr_id_'+counter+'">'
	 			+'<td>'+counter
	 				+'<input type="hidden" class="xCounter" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'">'
	 				+'<input type="hidden" class="xId" id="common_service_id_'+counter+'" value="'+value.id+'">'
			 		+'<input type="hidden" class="xServiceId" id="common_item_service_id_'+counter+'" value="'+value.serviceId+'">'
			 		+'<input type="hidden" id="common_item_service_name_'+counter+'" value="'+value.serviceIdInString+'">'
			 		+'<input type="hidden" class="xBillingFor" id="common_billingFor_'+counter+'" value="'+value.billingFor+'">'
	 			+'</td>'
			 	+'<td style="width:20px;">'+value.serviceIdInString+'</td>'
			 	
			 	+'<td><a href="javascript:void(0);" onclick="remove('+counter+')"><span class="glyphicon glyphicon-remove-circle"></span> Remove </a></td>'
		 	+'</tr>'					 	
	 	)).draw();
		counter++;
	});
}

function gotoBackHistoryListPage(){
	var documentType = $("#invDocType").val();
	var redirectTo = '';
    if(documentType == 'invoice'){
    	redirectTo = "./getInvoices";
	}else if(documentType == 'billOfSupply'){
		redirectTo = "./getBOS";
	}else if(documentType == 'rcInvoice'){
		redirectTo = "./getRCInvoice";
	}else if(documentType == 'eComInvoice'){
		redirectTo = "./getEComInvoice";
	}else if(documentType == 'eComBillOfSupply'){
		redirectTo = "./getEComBOS";
	}  
    window.location.href = redirectTo;
}

function gotoActualInvoicePage(){
	var documentType = $("#invDocType").val();
	var idValue = $("#unqIncDes").val();
	document.previewInvoice.action = "./getInvoiceDetails";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
}

function remove(counter){
	var serviceName = $("#common_item_service_name_"+counter).val();
	bootbox.confirm({
	    title: "Remove "+serviceName+"?",
	    message: "Do you want to remove item "+serviceName+" from invoice? This cannot be undone.",
	    buttons: {
	        cancel: {
	            label: '<i class="fa fa-times"></i> Cancel'
	        },
	        confirm: {
	            label: '<i class="fa fa-check"></i> Confirm'
	        }
	    },
	    callback: function (result) {
	        console.log('This was logged in the callback: ' + result);
	        if(result){
	        	reConstructDatatable(counter);
	        }
	    }
	});
}

function reConstructDatatable(counter){
	var $toggle = $("#toggle");
	var newAddedProductCount = $toggle.children().length;
	var totalCount = 0;
	
	totalCount = parseInt(invoiceItemId.length) + parseInt(newAddedProductCount);
	if(totalCount <= 1){
		bootbox.alert("Invoice should contain atleast one line item.");
	}else{
		//remove row 
		itemChangeDataTable.rows().every( function ( index, tableLoop, rowLoop ) {
			 var rowX = itemChangeDataTable.row(index).node();
			 var row = $(rowX);
			 var xCounter = parseInt(row.find('.xCounter').val());
			 console.log("xCounter : "+xCounter);
			 if(parseInt(xCounter) == parseInt(counter)){
				 arrayRemove(invoiceItemId,parseInt(row.find('.xId').val()));
			 }
		});
		
		constructNewTable();
	}

}

function constructNewTable(){
	if(invoiceJson == ''){
		fetchInvoiceJson($("#unqIncDes").val());
	}
	var counter = 1;
	itemChangeDataTable.clear().draw();
	$.each(invoiceJson.serviceList,function(i,value){
	if(contains(invoiceItemId,parseInt(value.id))){
		itemChangeDataTable.row.add($(
	 			'<tr id="tr_id_'+counter+'">'
		 			+'<td>'+counter
		 				+'<input type="hidden" class="xCounter" id="counter_'+counter+'" name="counter_'+counter+'" value="'+counter+'">'
		 				+'<input type="hidden" class="xId" id="common_service_id_'+counter+'" value="'+value.id+'">'
				 		+'<input type="hidden" class="xServiceId" id="common_item_service_id_'+counter+'" value="'+value.serviceId+'">'
				 		+'<input type="hidden" id="common_item_service_name_'+counter+'" value="'+value.serviceIdInString+'">'
				 		+'<input type="hidden" class="xBillingFor" id="common_billingFor_'+counter+'" value="'+value.billingFor+'">'
		 			+'</td>'
				 	+'<td style="width:20px;">'+value.serviceIdInString+'</td>'
				 	
				 	+'<td><a href="javascript:void(0);" onclick="remove('+counter+')"><span class="glyphicon glyphicon-remove-circle"></span> Remove </a></td>'
			 	+'</tr>'					 	
		 )).draw();
		counter++;
	}
		
	});
}
function arrayRemove(array,number){
	for(var i = array.length - 1; i >= 0; i--) {
	    if(array[i] === number) {
	       array.splice(i, 1);
	    }
	}
	
}


function contains(arr, element) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === element) {
            return true;
        }
    }
    return false;
}

function split2(val) {
    return val.split(/,\s*/);
}

function extractLastRW(term) {
    return split2(term).pop();
}

$(document).on("click", "#backToFirstPage", function () {
	$('.loader').show();
	$("#itemSecondPage").hide();
	$("#itemFirstPage").show();
	$('.loader').fadeOut("slow");
});

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

