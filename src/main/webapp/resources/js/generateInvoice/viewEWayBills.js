var ewaybillNo=null;
var ewayBillid=null;
var ewaybillStatus=null;
var docDate = null;
function sendMail(idValue){
	
	  $.ajax({
			url : "sendMailToCustomerFromPreview",
			method : "post",
			contentType : "application/json",
			dataType : "json",
			data : { "id" : idValue },
			headers: {_csrf_token : $("#_csrf_token").val()},
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
				//var json = $.parseJSON(jsonobj);
				bootbox.alert(json);
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
		});
}


function download(idValue){
	
	  $.ajax({
			url : "sendMailToCustomerFromPreview",
			method : "post",
			contentType : "application/json",
			dataType : "json",
			data : { "id" : idValue },
			headers: {_csrf_token : $("#_csrf_token").val()},
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
				//var json = $.parseJSON(jsonobj);
				bootbox.alert(json);
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
		});
}



function backToInvoicePage(idValue){
	document.manageInvoice.action = "./getInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
	
}


function getEWayBillDetails(idValue){
	$('#loadingmessage').show();
	$('#card').hide();
	$("#firstDivId").hide();
	$("#originalHeader").hide();
	setValuesInHiddenFields(idValue);
	setTimeout(function(){
		getEwayBillDetailsInPreview(idValue);
		$("#secondDivId").show();
		$("#previewHeader").show();
		$('#card').show();
		$('#loadingmessage').hide(); 
	}, 2000);
}


$(document).ready(function () {
	$("#backToPreview").click(function(e){
		$('#loadingmessage').show();
		
		$("#secondDivId").hide();
		$("#previewHeader").hide();
		setValuesInHiddenFields("");
		setTimeout(function(){
			$("#firstDivId").show();
			$("#originalHeader").show();
			$('#loadingmessage').hide(); 
		}, 2000);
	});
	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#optionsDiv').hide();
		
	});
});

function setValuesInHiddenFields(idValue){
	$("#refInvoiceId").val(idValue);
}

function getEwayBillDetailsInPreview(idValue){
	var location = $("#location").val();
	var backendResponse = '';
	 $.ajax({
			url : "getEwayBillDetailsInPreview",
			method : "post",
			data : {id : idValue},
		//	contentType : "application/json",
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
			async : false,
			success:function(data,fTextStatus,fRequest){
				if (isValidSession(data) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(data) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				console.log(data);
				if(data.renderData === 'accessDeny'){
					bootbox.alert("Data is manipulated.", function() {
						setTimeout(function(){
							window.location.href =  'home#invoice';
						}, 1000);
					});
				}else{
					ewaybillNo=data.ewaybillNo;
					ewayBillid=data.id;
					ewaybillStatus=data.ewaybillStatus;
					docDate = new Date(data.docDate);
					var day = docDate.getDate();
					var month = docDate.getMonth()+1;
					var year = docDate.getFullYear();
					var documentDate = day+"/"+month+"/"+year;
				//	 $("#etable").html("");
					
							 $("#etable").append(
								 '<div class="col-sm-6">'
								+'<div class="invoiceInfo ">'
							    +'<p> <span>eWay Bill No: </span>'+data.ewaybillNo +'</p>'
								+'<p> <span>Generated By: </span>'+data.gstin+'</p>'
								+'<p> <span>Mode: </span>'+data.modeOfTransportDesc+'</p>'
								+'<p> <span>Type: </span>'+data.supplyType+'-'+data.subSupplyType+'</p>'
								+'</div>'
								+'</div>'
								+'<div class="col-sm-6">'
								+'<div class="invoiceInfo ">'
								+'<p> <span>Generated Date: </span>'+data.ewaybill_date+'</p>'
								+'<p> <span>Valid Upto: </span>'+data.ewaybill_valid_upto+'</p>'
								+'<p> <span>Approx Distance: </span>'+data.kmsToBeTravelled+'</p>'
								+'<p> <span>Document Details: </span>'+data.docType+' - '+data.docNo +' - '+documentDate+'</p>'
								+'</div>'
								+'</div>'
							 );
							 
							 var label1 = "Transporter Doc. No: ";
							 var label2 = "Transporter Doc Date: ";	
							 
							 if(data.modeOfTransportDesc == 'Road'){
								 label1 = "Transporter Doc. No: ";
								 label2 = "Transporter Doc Date: ";
							 } else if(data.modeOfTransportDesc == 'Rail'){
								 label1 = "RR No: ";
								 label2 = "RR Date: ";
							 }else if(data.modeOfTransportDesc == 'Air'){
								 label1 = "Airway Bill No: ";
								 label2 = "Airway Bill Date: ";
							 }else if(data.modeOfTransportDesc == 'Ship'){
								 label1 = "Bill of lading No: ";
								 label2 = "Bill of lading Date: ";
							 } 
							 
							 $("#transportTable").append(
									
									 
									/*'<div class="invoiceDetail">'
									+'<div class="col-sm-6">'
									+'<div class="invoiceInfo ">'*/
			                        '<p><span>'+label1+'</span>'+data.docNo+' <br/> <span>'+label2+'</span> '+documentDate
			                        +'<br/> <span> Transporter ID: </span>'+data.transporterId+'<br/> <span> Transporter Name: </span>'+data.transporterName+'</p>'
			                       /* +'</div> </div> </div>'*/
							 );
							
									 if(data.modeOfTransportDesc == 'Road'){
										 $("#vehicleDet").append(
									'<li>'+data.modeOfTransportDesc+'</li>'   
									+'<li>'+data.vehicleNo +'</li>'
									+'<li>'+location+'</li>'
									+'<li>'+data.ewaybill_date+'</li>'
									+'<li>'+data.gstin+'</li>'
									+'<li> - </li>'
										 );
									 } else {
										 $("#vehicleDet").append(
													'<li>'+data.modeOfTransportDesc+'</li>'   
													+'<li> - </li>'
													+'<li>'+location+'</li>'
													+'<li>'+data.ewaybill_date+'</li>'
													+'<li>'+data.gstin+'</li>'
													+'<li> - </li>'
														 );
									 }
							 
									 
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		});
	 return backendResponse;
}


function redirectToInvoiceHistoryPage(){
	return 'getMyInvoices';
}

function generateEWayBill(idValue){
	document.manageInvoice.action = "./generateEWayBill";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}


function getEWayBills(idValue){
	document.manageInvoice.action = "./getEWayBills";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}



function downloadEWayBills(gstin, invoiceId ){
	document.downloadEWayBill.action = "./downloadEWayBill";
	document.downloadEWayBill.invoiceId.value = invoiceId;
	document.downloadEWayBill.gstin.value = gstin;
	document.downloadEWayBill.ewaybillNo.value = ewaybillNo;
	document.downloadEWayBill._csrf_token.value = $("#_csrf_token").val();
	document.downloadEWayBill.submit();
}


function sendEwayBillPdf(gstin, invoiceId) {
	$('#loadingmessage').show();
	$('#mainPg1').hide();
	var inputData = {
		"invoiceId" : invoiceId,
		"gstin" : gstin,
		"ewaybillNo" : ewaybillNo,
	};

	var data = JSON.stringify(inputData);
	$.ajax({
		url : "sendEwayBillPdf",
		method : "post",
		data : data,
		contentType : "application/json",
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		async : false,
		success : function(json,fTextStatus,fRequest) {
			$('#loadingmessage').hide();
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}
			
			if (json.status == 'success') {
				bootbox.alert("E-way Bill PDF sent successfully", function() {
					setTimeout(function(){
					getEWayBills(invoiceId);
					}, 500);
				});
			}

			if (json.status == 'failure') {
				bootbox.alert("Error while sending E-way Bill Pdf ");

			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
		},
		error : function(data, status, er) {
			// alert("error: "+data+" status: "+status+" er:"+er);
			 getInternalServerErrorPage();   
		}
	});
}
function canceleWayBillPage(invoiceId){
	if(ewaybillStatus == 'GENEWAYBILL'){
	document.cancelEwayBill.action = "./cancelEWayBillPage";
	document.cancelEwayBill.id.value = invoiceId;
	document.cancelEwayBill.ewayBillId.value = ewayBillid;
	document.cancelEwayBill._csrf_token.value = $("#_csrf_token").val();
	document.cancelEwayBill.submit();
	} else {
		bootbox.alert("This E-Way Bill is cancelled already");
	} 
}


