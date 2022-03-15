var ewaybillNo=null;
var ewayBillid=null;
var ewaybillStatus=null;
var docDate = null;
var gstin = "";
var clientId = $("#clientId").val(); 
var secretKey = $("#secretKey").val(); 
var userId = $("#userId").val();
var appCode = $("#appCode").val();
var ipUsr = $("#ipUsr").val();
var ewaybillNo = $("#ewaybillNo").val();
var blankMsg="This field is required";	
var regMsg = "data should be in proper format";
var emailRegex = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;

$(document).ready(function(){
	$('#divTwo').hide();
	getGenericEwayByNO();
	$('.loader').fadeOut("slow");
	
	$("#sendMailId").click(function(e){
		$("#divTwo").hide();
		$("#customerEmailDiv").show();		
	});
		
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

	$("#backToGenericEwayPreview").click(function(){
		$('#divTwo').show();
		$('#customerEmailDiv').hide();
	});
	
	$("#optionsDiv").click(function(e){
		$('#optionsMultiDiv').show();
		$('#optionsDiv').hide();		
	});
	
});
	
function validateCustomerEMailId(){
	var errFlag14 = validateTextField("cust_email_addr","cust-email-format",blankMsg);
	 if(!errFlag14){
		 errFlag14=validateRegexpressions("cust_email_addr","cust-email-format", regMsg, emailRegex);
	 }
	 return errFlag14;
}

function backToDocumets(){	
	window.location.href = "./getGenericEWayBills";
}

function downloadWIEWayBills(userId, ewaybillno, client_id, secret_key, app_code, ip_usr){
	$('.loader').show();
	document.downloadWIEWayBill.action = "./ewaybill/generatedEWayBillDownload";
	document.downloadWIEWayBill.userId.value = userId;
	document.downloadWIEWayBill.ewaybillno.value = ewaybillno;
	document.downloadWIEWayBill.client_id.value = client_id;
	document.downloadWIEWayBill.secret_key.value = secret_key;
	document.downloadWIEWayBill.app_code.value = app_code;
	document.downloadWIEWayBill.ip_usr.value = ip_usr;
	document.downloadWIEWayBill.submit();
	$('.loader').fadeOut("slow");
}

function canceleWayBillPage(){
	$('.loader').show();
	if(ewaybillStatus == 'GENEWAYBILL'){
	document.cancelEwayBill.action = "./cancelGenEWayBillPage";
	document.cancelEwayBill.ewayBillNo.value = ewaybillNo;
	document.cancelEwayBill.gstin.value = gstin;
	document.cancelEwayBill.submit();
	$('.loader').fadeOut("slow");
	} else {
		$('.loader').fadeOut("slow");
		bootbox.alert("This E-way Bill is cancelled already");
	} 
}

function sendEwayBillPdf(emailId) {
	$("#customerEmailDiv").hide();
	$('.loader').show();
	
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
		beforeSend: function(){
	    	$('.loader').show();
	    },
	    complete: function(){
	    	$('.loader').hide();
	    },
		success : function(json,fTextStatus,fRequest) {			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}

			if (json.status == 'success') {
				$('.loader').fadeOut("slow");
				bootbox.alert("E-way Bill sent successfully", function() {
					backToDocumets();
				});
			}

			if (json.status == 'failure') {
				$('.loader').fadeOut("slow");
				bootbox.alert("Error occurred while sending E-way Bill", function() {
					backToDocumets();
				});
			}
		},
		error : function(data, status, er) {
			backToDocumets();
		}
	});
}

function getGenericEwayByNO(){
	var jsonData = {"userId" : userId, "ewaybillno" : ewaybillNo};
	$.ajax({
	 	url : "ewaybill/getEwayBillByNumber",
	 	type : "POST",
	 	contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
		data : JSON.stringify(jsonData),
		async : false,
		beforeSend: function(){
			$('.loader').show();
	    },
	    complete: function(){
	    	$('.loader').hide();
	    },
		success:function(data,fTextStatus,fRequest){
			if (isValidSession(data) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			if(data.status === 'failure'){
				bootbox.alert(data.error_desc, function() {
					setTimeout(function(){
						backToDocumets();
					}, 500);
				});
			}else{
			//	ewaybillNo=data.ewaybillNo;
				ewayBillid=data.id;
				ewaybillStatus=data.ewaybillStatus;
				var totalAmount = 0;
				var cessTotalTax = 0;
				var sgstAmount = 0;
				var cgstAmount = 0;
				var igstAmount = 0;
				var otherValue = 0;
				var cessadvolAmount = 0;
				var cessnonadvolAmount = 0;
				var othSubType = "";
				if(data.subSupplyDesc == 'Others'){
					othSubType = "-("+data.othersSubType+")";
				}
				 
				if(data.supplyType == 'O'){
					 gstin = data.fromGstin;
				 } else {
					 gstin = data.toGstin;
				 }
				
				 $("#etable").append(
					 '<div class="col-md-6">'
						    +'<strong>eWay Bill No : </strong>'+data.ewayBillNo +'<br>'
							+'<strong>Generated By : </strong>'+gstin+'<br>'
							+'<strong>Mode : </strong>'+data.transModeDesc+'<br>'
							+'<strong>Type : </strong>'+data.supplyTypeDesc+'-'+data.subSupplyDesc+othSubType+''
					+'</div>'
					+'<div class="col-sm-6">'
							+'<strong>Generated Date : </strong>'+data.ewayBillDate+'<br>'
							+'<strong>Valid Upto : </strong>'+data.ewaybill_valid_upto+'<br>'
							+'<strong>Approx Distance : </strong>'+data.transDistance+'<br>'
							+'<strong>Document Details : </strong>'+data.docTypeDesc+' - '+data.docNo +' - '+data.docDate+''
					+'</div>'
				 );		
				 
				 $("#fromGstin").append(
					'<strong>From</strong><br>'	
					+ '<strong>GSTIN :</strong>'+data.fromGstin+'<br>'
					+  data.fromTrdName+'<br>'
					+ '<strong>Address :</strong>'+data.fromAddr1+'<br>'
					+ data.fromPlace+'<br>'
					+ data.fromPincode+'<br>'					 
				 );
				 
				 $("#toGstin").append(
					 '<strong>To</strong><br>'
					+'<strong>GSTIN :</strong>'+data.toGstin+'<br>'
					+  data.toTrdName+'<br>'
					+'<strong>Address :</strong>'+data.toAddr1+'<br>'
					+ data.toPlace+'<br>'
					+ data.toPincode+'<br>'						 
				 );				 

				 $.each(data.itemList,function(i,value){
					var taxAmt = (value.cgstRate+value.sgstRate+value.igstRate+value.cessadvolAmount+value.cessnonadvolAmount);
					$("#mytable2 tbody:last-child").append(
						'<tr>'		                        
						+ '<td>'+value.hsnCode+'</td>'
						+ '<td>'+value.productName+'</td>'
						+ '<td>'+value.quantity+'</td>'
						+ '<td>'+value.taxableAmount+'</td>'
						+ '<td>'+taxAmt+'</td>'
				    	+ '</tr>'
				 	);
					 totalAmount = totalAmount + value.taxableAmount;								 
				 });

				 cessadvolAmount = cessadvolAmount + data.cessadvolAmount;
				 cessnonadvolAmount= cessnonadvolAmount + data.cessnonadvolAmount;
				 sgstAmount = sgstAmount + data.sgstValue;
				 cgstAmount = cgstAmount + data.cgstValue;
				 igstAmount = igstAmount + data.igstValue;
				 otherValue = otherValue + data.otherValue;
				 
				 
				 $("#itemTaxDet").append('<div class="col-md-3"><strong>Total Taxable Amt : </strong>'+data.totalValue+'</div>'
					 +'<div class="col-md-3"><strong> CGST Amt : </strong>'+cgstAmount+'</div>'
					 +'<div class="col-md-3"><strong> SGST Amt : </strong>'+sgstAmount+'</div>'
	                 +'<div class="col-md-3"><strong> IGST Amt : </strong>'+igstAmount+'</div>'
	                 +'<div class="col-md-3"><strong> CESS Advol Amt : </strong>'+cessadvolAmount+'</div>'
	                 +'<div class="col-md-3"><strong>CESS Non. Advol Amt : </strong>'+cessnonadvolAmount+'</div>'
	                 +'<div class="col-md-3"><strong>Other Amt : </strong>'+otherValue+'</div>'
	                 +'<div class="col-md-3"><strong>Total Inv. Amt : </strong>'+data.totInvValue+'</div>'
				 );
					
				 var label1 = "Transporter Doc. No: ";
				 var label2 = "Transporter Doc Date: ";	
				 
				 if(data.transModeDesc == 'Road'){
					 label1 = "Transporter Doc. No: ";
					 label2 = "Transporter Doc Date: ";
				 } else if(data.transModeDesc == 'Rail'){
					 label1 = "RR No: ";
					 label2 = "RR Date: ";
				 }else if(data.transModeDesc == 'Air'){
					 label1 = "Airway Bill No: ";
					 label2 = "Airway Bill Date: ";
				 }else if(data.transModeDesc == 'Ship'){
					 label1 = "Bill of lading No: ";
					 label2 = "Bill of lading Date: ";
				 } 
				 
				 $("#transportTable").append('<div class="col-md-3"><strong>'+label1+'</strong>'+data.transDocNo+'</div>'
                        + '<div class="col-md-3"><strong>'+label2+'</strong> '+data.transDocDate+'</div>'
                        + '<div class="col-md-3"><strong> Transporter ID : </strong>'+data.transporterId+'</div>'
                        + '<div class="col-md-3"><strong> Transporter Name : </strong>'+data.transporterName+'</div>'
				 );						
						
				 if(data.transModeDesc == 'Road'){
				 	$("#vehicleDetTable tbody:last-child").append(
		 				'<tr>'	
		 					 +'<td>'+data.transModeDesc+'</td>'
		                     +'<td>'+data.vehicleNo +'</td>'
		                     +'<td>'+data.fromPlace+'</td>'
		                     +'<td>'+data.ewayBillDate +'</td>'
		                     +'<td>'+gstin+'</td>'
		                     +'<td>-</td>'
		 				+'</tr>'	
				 	);
				 }else{
				 	$("#vehicleDetTable tbody:last-child").append(
		 				'<tr>'	
		 					 +'<td>'+data.transModeDesc+'</td>'
		                     +'<td>-</td>'
		                     +'<td>'+data.fromPlace+'</td>'
		                     +'<td>'+data.ewayBillDate +'</td>'
		                     +'<td>'+gstin+'</td>'
		                     +'<td>-</td>'
		 				+'</tr>'	
				 	);
				 }
				 $('#divTwo').show();
			}
         },
         error: function (data,status,er) {        	 
        	 getInternalServerErrorPage();   
        }
	});
}