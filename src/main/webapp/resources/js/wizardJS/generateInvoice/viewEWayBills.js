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
				 
				 getWizardInternalServerErrorPage();   
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
				 
				 getWizardInternalServerErrorPage();   
			}
		});
}

function backToInvoicePage(idValue){
	document.manageInvoice.action = "./getWizardInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice.submit();	
}


function getEWayBillDetails(idValue){
	$('#loadingmessage').show();
	$('.lk-toggle-nav').hide();
	//$('#card').hide();
	$("#firstDivId").hide();
	$("#originalHeader").hide();
	setValuesInHiddenFields(idValue);
	setTimeout(function(){
		getEwayBillDetailsInPreview(idValue);
		$("#secondDivId").show();
		$("#previewHeader").show();
	//	$('#card').show();
		$('#loadingmessage').hide(); 
		$('.lk-toggle-nav').show();
	}, 2000);
}


$(document).ready(function () {
	$("#backToPreview").click(function(e){
		$('#loadingmessage').show();
		$('.lk-toggle-nav').hide();
		$("#secondDivId").hide();
		$("#previewHeader").hide();
		setValuesInHiddenFields("");
		setTimeout(function(){
			$("#firstDivId").show();
			$("#originalHeader").show();
			$('#loadingmessage').hide(); 
			$('.lk-toggle-nav').show();
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
							window.location.href =  'wHome#doc_management';
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
									    +'<b>eWay Bill No: </b>'+data.ewaybillNo +'<br>'
										+'<b>Generated By: </b>'+data.gstin+'<br>'
										+'<b>Mode: </b>'+data.modeOfTransportDesc+'<br>'
										+'<b>Type: </b>'+data.supplyType+'-'+data.subSupplyType+'<br>'
									+'</div>'
								+'</div>'
								+'<div class="col-sm-6">'
									+'<div class="invoiceInfo ">'
										+'<b>Generated Date: </b>'+data.ewaybill_date+'<br>'
										+'<b>Valid Upto: </b>'+data.ewaybill_valid_upto+'<br>'
										+'<b>Approx Distance: </b>'+data.kmsToBeTravelled+'<br>'
										+'<b>Document Details: </b>'+data.docType+' - '+data.docNo +' - '+documentDate+'<br>'
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
			                        '<b>'+label1+'</b>'+data.docNo+' &nbsp;&nbsp;&nbsp;&nbsp; <b>'+label2+'</b> '+documentDate
			                        +'&nbsp;&nbsp;&nbsp;&nbsp; <b> Transporter ID: </b>'+data.transporterId+'&nbsp;&nbsp;&nbsp;&nbsp;<b> Transporter Name: </b>'+data.transporterName+'<br>'
			                       /* +'</div> </div> </div>'*/
							 );
							
									/* if(data.modeOfTransportDesc == 'Road'){
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
									 }	*/
							
							 if(data.modeOfTransportDesc == 'Road'){
							 	$("#vehicleDetTable tbody:last-child").append(
							 					 '<tr>'	
							 					 +'<td>'+data.modeOfTransportDesc+'</td>'
							                     +'<td>'+data.vehicleNo +'</td>'
							                     +'<td>'+location+'</td>'
							                     +'<td>'+data.ewaybill_date +'</td>'
							                     +'<td>'+data.gstin+'</td>'
							                     +'<td>-</td>'
							 					 +'</tr>'	
							 	);
							 }else{
							 	$("#vehicleDetTable tbody:last-child").append(
							 					 '<tr>'	
							 					 +'<td>'+data.modeOfTransportDesc+'</td>'
							                     +'<td>-</td>'
							                     +'<td>'+location+'</td>'
							                     +'<td>'+data.ewaybill_date +'</td>'
							                     +'<td>'+data.gstin+'</td>'
							                     +'<td>-</td>'
							 					 +'</tr>'	
							 	);
							 }
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {
	        	 
	        	 getWizardInternalServerErrorPage();   
	        }

		});
	 return backendResponse;
}


function redirectToInvoiceHistoryPage(){
	return 'getWizardMyInvoices';
}

function generateEWayBill(idValue){
	document.manageInvoice.action = "./wgenerateEWayBill";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function getEWayBills(idValue){
	document.manageInvoice.action = "./wgetEWayBills";
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

function canceleWayBillPage(invoiceId){
	if(ewaybillStatus == 'GENEWAYBILL'){
	document.cancelEwayBill.action = "./wcancelEWayBillPage";
	document.cancelEwayBill.id.value = invoiceId;
	document.cancelEwayBill.ewayBillId.value = ewayBillid;
	document.cancelEwayBill._csrf_token.value = $("#_csrf_token").val();
	document.cancelEwayBill.submit();
	} else {
		bootbox.alert("This E-Way Bill is cancelled already");
	} 
}


