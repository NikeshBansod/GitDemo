var ewaybillNo=null;
var ewayBillid=null;
var ewaybillStatus=null;
var docDate = null;
var gstin = "";
var blankMsg="This field is required";
var regMsg = "data should be in proper format";
var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;

var clientId = $("#clientId").val(); 
var secretKey = $("#secretKey").val(); 
var userId = $("#userId").val();
var appCode = $("#appCode").val();
var ipUsr =  $("#ipUsr").val();

jQuery(document).ready(function($){
	
	 
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
    //as per business they need to hide the list of customer on click of edit
	if($("#actionPerformed").val()){
		 $('.ewaybillTable').hide();
	}
	

	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#optionsDiv').hide();
	      
		
	});
	
	$("#backToGenericEwayPreview").click(function(){
		$('.ewaybillDetailPage').show();
		$('#customerEmailDiv').hide();
	      
		
	});
    
});

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
		setTimeout(function(){
			$("#firstDivId").show();
			$("#originalHeader").show();
			$('#loadingmessage').hide(); 
		}, 2000);
	});
	
	
	$("#sendMailId").click(function(e){
		$(".ewaybillDetailPage").hide();
		showCustomerEmailPageHeader();
		$("#customerEmailDiv").show();
		
	});
	
	
	function showCustomerEmailPageHeader(){
		$("#previewHeader").css("display","none");
		$("#originalHeader").css("display","none");
		$("#customerEmailPageHeader").css("display","block");
	}
	
		$("#custEmailSave").on("click", function(e){
			var errFlag14 = validateCustomerEMailId();
			if(errFlag14){
				e.preventDefault();	 
			}else{
				var custEmail = $("#cust_email_addr").val();
				$("#customer_custEmail").val(custEmail);
				sendEwayBillPdf(custEmail);
			}
				
	});
	
		
		function validateCustomerEMailId(){
			var errFlag14 = validateTextField("cust_email_addr","cust-email-format",blankMsg);
			 if(!errFlag14){
				 errFlag14=validateRegexpressions("cust_email_addr","cust-email-format", regMsg, emailRegex);
			 }
			 return errFlag14;
		}
	
	
	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#optionsDiv').hide();
		
	});
	
	
	 var jsonData = {"userId" : userId};
	 var status = "Active";
	 var cancelSpan = "";
	 var cancelClass = "";
	 var color = "";
	 
	 $('.ewaybillDetailPage').hide();
	$.ajax({
		
		url : "ewaybill/getGeneratedEwayBillList",
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
		data : JSON.stringify(jsonData),
	//	data : JSON.stringify(generateInputJsonData),
		async : false,
		success : function(jsonVal) {
			if(jsonVal.status == "failure"){
				if(jsonVal.error_desc == "no record found"){
					$('.ewaybillValues').append(
							'<div class="text-center text-danger"> No E-Way Bill is created against this Organization </div>'
									);
				}
			}else if(jsonVal.length == 0){
				$('.ewaybillValues').append(
					'<div class="text-center text-danger"> No E-Way Bill is created against this Organization </div>'
				  );
			}else {
				 $.each(jsonVal,function(i,value){
					 if(value.ewaybillStatus == "GENEWAYBILL" ){
						
						// status =  "Active";
						 cancelClass = "";
						 cancelSpan = "";
						 status = '<td class="text-right"><a href="#" onclick="javascript:getPreviewOfEwayBillDetails('+value.ewaybillNo+','+userId+');">'+value.ewaybillNo+'</a></td>';
					 } else{
					//	 status = "Inactive";
						 cancelClass = "fa fa-file-text-o";
						 cancelSpan = '<span class="glyphicon glyphicon-remove-circle" style="padding-top: 8px;"></span> '
							 status =  '<td class="text-right"><a href="#" style="color: red" onclick="javascript:getPreviewOfEwayBillDetails('+value.ewaybillNo+','+userId+');">'+value.ewaybillNo+'</a></td>';
					 }
					 $('.ewaybillValues').append(
								/*'<div class="heading">'
									+'<div class="cust-con ">'
										+'<h1>'+value.ewaybillNo+'</h1>'
									+'</div>'
									+'<div class="cust-edit">'
									  +'<div class="cust-icon">'
										+ cancelSpan
										+'<a href="#" onclick="javascript:getPreviewOfEwayBillDetails('+value.ewaybillNo+','+userId+');"><i class="fa fa-file-text-o" aria-hidden="true"></i></a>'
									  +'</div>'
									+'</div>'
								+'</div>'
								+'<div class="content">'
									+'<div class="cust-con">'
									+'<p> E-way Bill Date : '+value.ewaybill_date+'</p>'
									+'<p> Valid upto : '+value.ewaybill_valid_upto+'</p>'
									+'<p> Status : '+status+'</p>'
									+'</div>'
								+'</div>'*/		
							 
							 +'<tbody>'			
								+'<tr>'
								+'<td class="text-left">'+value.toTraderName+'</td>'
								+ status 
								+'</tr>'
						+'</tbody>'
							 );	
						});
			}
		},
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
    });
	
});


$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});


function getPreviewOfEwayBillDetails(ewaybillno, userId){

	$('.ewaybillDetailPage').show();
	 var jsonData = {"userId" : userId, "ewaybillno" : ewaybillno};
	 
	 $.ajax({
		 	url : "ewaybill/getEwayBillByNumber",
		 	type : "POST",
		 	contentType : "application/json",
			dataType : "json",
			headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
			data : JSON.stringify(jsonData),
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				console.log(json);
				if(json.status === 'failure'){
					bootbox.alert(error_desc, function() {
						setTimeout(function(){
							window.location.href =  "./getGenericEWayBills";
						}, 1000);
					});
				}else{
					$("#firstDivId").hide();
					$("#originalHeader").hide();
					$(".ewaybillTable").hide();
					$("#secondDivId").show();
					$("#previewHeader").show();
					$('#card').show();
					
					 var totalAmount = 0;
					 var cessTotalTax = 0;
					 var sgstAmount = 0;
					 var cgstAmount = 0;
					 var igstAmount = 0;
					 var otherValue = 0;
					 var cessadvolAmount = 0;
					 var cessnonadvolAmount = 0;
					 
					 var othSubType = "";
					 if(json.subSupplyDesc == 'Others'){
						 othSubType = "-("+json.othersSubType+")";
					 }
					 if(json.supplyType == 'O'){
						 gstin = json.fromGstin;
					 } else {
						 gstin = json.toGstin;
					 }
					ewaybillNo=json.ewayBillNo;
					ewaybillStatus=json.ewaybillStatus;
				//	gstin = json.fromGstin;
							 $("#etable").append(
								 '<div class="col-sm-6">'
									+'<div class="invoiceInfo ">'
									    +'<b>E-Way Bill No: </b>'+json.ewayBillNo +'<br>'
										+'<b>Generated By: </b>'+gstin+'<br>'
										+'<b>Mode: </b>'+json.transModeDesc+'<br>'
										+'<b>Type: </b>'+json.supplyTypeDesc+'-'+json.subSupplyDesc+othSubType+'<br>'
									+'</div>'
								+'</div>'
								+'<div class="col-sm-6">'
									+'<div class="invoiceInfo ">'
										+'<b>Generated Date: </b>'+json.ewayBillDate+'<br>'
										+'<b>Valid Upto: </b>'+json.ewaybill_valid_upto+'<br>'
										+'<b>Approx Distance: </b>'+json.transDistance+'<br>'
										+'<b>Document Details: </b>'+json.docTypeDesc+' - '+json.docNo +' - '+json.docDate+'<br>'
									+'</div>'
								+'</div>'
							 );							 
							 		
							 $("#fromGstin").append(
								'<h6>From</h6>'	
								+ '<p><span>GSTIN:</span>'+json.fromGstin+'</p>'
								+ '<p>'+json.fromTrdName+'<br>'
								+ '<p><span>Address:</span>'+json.fromAddr1+'</p>'
								+ '<p>'+json.fromPlace+'</p>'
								+ '<p><span>Pincode : </span>'+json.fromPincode+'</p>'
									 
							 );
							 
							 $("#toGstin").append(
								 ' <h6>To</h6>'
								+'<p><span>GSTIN:</span>'+json.toGstin+'</p>'
								+'<p>'+json.toTrdName+'<br>'
								+'<p><span>Address:</span>'+json.toAddr1+'</p>'
								+'<p>'+json.toPlace+'</p>'
								+'<p><span>Pincode : </span>'+json.toPincode+'</p>'
									 
							 );							 
							 //start
							 var dynamicDocNo='';
							 if(json.transModeDesc == 'Road'){
								 dynamicDocNo='Transporter Doc';
							 }else if(json.transModeDesc == 'Rail'){
								 dynamicDocNo='RR';
							 }else if(json.transModeDesc == 'Air'){
								 dynamicDocNo='Airway Bill';
							 }else{
								 dynamicDocNo='Bill of lading';
							 }
							 //end
							 
							 $("#transportTable").append(
			                        '<b>'+dynamicDocNo+' No: </b>'+json.docNo+' <br> <b>'+dynamicDocNo+' Date: </b> '+json.transDocDate
			                        +'<br> <b> Transporter ID: </b>'+json.transporterId+'<br> <b> Transporter Name: </b>'+json.transporterName+'<br>'
							 );
							
													
							 if(json.transModeDesc == 'Road'){
								 $("#vehicleDet").append(
							 					 '<li>'+json.transModeDesc+'</li>'
							                     +'<li>'+json.vehicleNo +'</li>'
							                     +'<li>'+json.fromPlace+'</li>'
							                     +'<li>'+json.ewayBillDate +'</li>'
							                     +'<li>'+gstin+'</li>'
							                     +'<li>-</td>'
							 	);
							 }else{
								 $("#vehicleDet").append(
							 					 '<li>'+json.transModeDesc+'</li>'
							                     +'<li>-</td>'
							                     +'<li>'+json.fromPlace+'</li>'
							                     +'<li>'+json.ewayBillDate +'</li>'
							                     +'<li>'+gstin+'</li>'
							                     +'<li>-</li>'
							 	);
							 }
							
							 cessadvolAmount = cessadvolAmount + json.cessadvolAmount;
							 cessnonadvolAmount = cessnonadvolAmount + json.cessnonadvolAmount;
							 cessTotalTax = cessTotalTax + json.cessValue;
							 sgstAmount = sgstAmount + json.sgstValue;
							 cgstAmount = cgstAmount + json.cgstValue;
							 igstAmount = igstAmount + json.igstValue;
							 otherValue = otherValue + json.otherValue;
							 
							 $.each(json.itemList,function(i,value){
								 var taxAmt = (value.cgstRate+value.sgstRate+value.igstRate+value.cessadvolAmount+ value.cessnonadvolAmount);
								 $("#itemDet").append(
								'<ul class="e-productdet" >'		                        
								+ '<li>'+value.hsnCode+'</li>'
								+ '<li>'+value.productName+'</li>'
								+ '<li>'+value.quantity+'</li>'
								+ '<li>'+value.taxableAmount+'</li>'
								+ '<li>'+taxAmt+'</li>'
						    	+ '</ul>'
							 	);
								 totalAmount = totalAmount + value.taxableAmount;								 
							 });
							
							 $("#itemTaxDet").append(
									      '<p><span>Total Taxable Amt : </span>'+json.totalValue
									 +' <br/> <span> CGST Amt : </span>'+cgstAmount
									 +' <br/> <span> SGST Amt : </span>'+sgstAmount
									 + '<br/> <span> IGST Amt : </span>'+igstAmount
									 +' <br/> <span> CESS Advol Amt : </span>'+cessadvolAmount
									 +' <br/> <span> CESS Non. Advol Amt : </span>'+cessnonadvolAmount
									 +' <br/> <span> Other Amt : </span>'+otherValue
									 +' <br/> <span> Total Inv. Amt : </span>'+json.totInvValue+'</p>'
							);
				}
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }

		});
	
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

function sendEwayBillPdf(emailId) {
	$('#loadingmessage').show();
	$('#mainPg1').hide();
	$("#customerEmailDiv").hide();
	
	var inputData = {
		"userId" : userId,
		"ewaybillno" : ewaybillNo,
		"email_id" : emailId,
	};

	var data = JSON.stringify(inputData);
	$.ajax({
		url : "ewaybill/sendEwayBillPdfToMail",
		type : "POST",
		contentType : "application/json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
		data : data,		
		dataType : "json",		
		async : false,
		success : function(json,fTextStatus,fRequest) {
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if (json.status == 'success') {
				bootbox.alert("E-way Bill sent successfully", function() {
						$('#loadingmessage').hide();
						window.location.href ="./getGenericEWayBills";
						return;
				});
			}

			if (json.status == 'failure') {
				bootbox.alert("Error occurred while sending E-way Bill", function() {
				$('#loadingmessage').hide();
				window.location.href ="./getGenericEWayBills";
				return;
				});

			}
		},
		error : function(data, status, er) {
			// alert("error: "+data+" status: "+status+" er:"+er);
			$('#loadingmessage').hide();
			 window.location.href ="./getGenericEWayBills";
		}
	});
}

function canceleWayBillPage(){
	if(ewaybillStatus == 'GENEWAYBILL'){
	document.cancelEwayBill.action = "./cancelGenEWayBillPage";
	document.cancelEwayBill.ewayBillNo.value = ewaybillNo;
	document.cancelEwayBill.gstin.value = gstin;
	document.cancelEwayBill.submit();
	} else {
		bootbox.alert("This E-way Bill is cancelled already");
	} 
}
$(document).ready(function() {
    $('#ewaybillTab').DataTable(
              {
            	  "aaSorting": [],
                    "bLengthChange": false,
                    "pagingType": "simple",
                   
                    
              }
    
    );
} );

scrollingElement = (document.scrollingElement || document.body)
function scrollSmoothToBottom () {
	   $(scrollingElement).animate({
	      scrollTop: document.body.scrollHeight
	   }, 200);
	}

	//Require jQuery
	function scrollSmoothToTop () {
	   $(scrollingElement).animate({
	      scrollTop: 0
	   }, 200);
	}


