var invoiceJson = '';
var rowNum = 0;
var addChgRowNum = 0;

$(document).ready(function () {
	fetchInvoiceJson($("#unqIncDes").val());
	setBreadCrumHeaders();
});

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
					$("#selectedDocType").val(json.documentType);
					$("#invDataFiledToGstn").val(json.fileToGstn);
					$("#iterationNo").val(json.iterationNo);
					$("#invNo").val(json.invoiceNumber);
					$("#old_invoiceFor").val(json.invoiceFor);
					$("#old_gstnStateId").val(json.gstnStateId);
					$("#old_gstnStateIdInString").val(json.gstnStateIdInString);
					$("#old_location").val(json.location);
					$("#old_locationStoreId").val(json.locationStoreId);
					$("#old_invoiceDate").val(json.invoiceDate);
					
				}
				$('.loader').fadeOut("slow");
			},
			error: function (data,status,er) {
		        getInternalServerErrorPage();   
		    }
			
		 });
	}	
}

function setBreadCrumHeaders(){
	var documentType = $("#selectedDocType").val();
	var invoiceNumber = $("#invNo").val();
	$("#generateInvoiceDefaultPageHeader").html("");
	var breadCrumHeader = '';
	
	if(documentType == 'invoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Edit Invoice</strong>';
	}else if(documentType == 'billOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Edit Bill Of Supply</strong>';
	}else if(documentType == 'rcInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>Reverse Charge Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Edit Reverse Charge Invoice</strong>';
	}else if(documentType == 'eComInvoice'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Invoice History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Edit E-Commerce Invoice</strong>';
	}else if(documentType == 'eComBillOfSupply'){
		breadCrumHeader = '<a href="javascript:void(0);" onclick="gotoBackHistoryListPage()"><strong>E-Commerce Bill Of Supply History</strong></a> &raquo; <a href="javascript:void(0);" onclick="gotoActualInvoicePage()"><strong>'+invoiceNumber+'</strong></a> &raquo; <strong id="">Edit E-Commerce Bill Of Supply</strong>';
	}
	
	
	$("#generateInvoiceDefaultPageHeader").append(
			breadCrumHeader
	);
}

function gotoBackHistoryListPage(){
	var documentType = $("#selectedDocType").val();
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