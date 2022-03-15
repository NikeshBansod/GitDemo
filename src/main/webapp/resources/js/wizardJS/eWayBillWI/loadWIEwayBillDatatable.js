function generateEWayBill(){	
	window.location.href = "./wizardLoginGenerateEwayBill";
}

function viewWIEwayBillDetailedPage(idValue,userId){
	document.previewInvoice.action = "./getPreviewWIEwayBill";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice.userId.value = userId;
	document.previewInvoice.submit();
}


$(document).ready(function() {
	
	var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var userId = $("#userId").val();
	var appCode = $("#appCode").val();
	var ipUsr = $("#ipUsr").val();

	var jsonData = {"userId" : userId};
	
	var table = $("#invoiceHistoryTab tbody");
    $.ajax({
        url: 'ewaybill/getGeneratedEwayBillList',
        type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
		data : JSON.stringify(jsonData),
		async : false,
        success : function(response) {
        	if (isValidSession(response) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
        	if(response.status == 'failure'){
				//bootbox.alert(response.error_desc);
			}else{
				$.each(response, function (a, b) {
					var stat;
					if(b.ewaybillStatus=='GENEWAYBILL'){
						stat = '<span class="textColourGreen">Valid</span>';
					}else if(b.ewaybillStatus=='CANEWB'){
						stat = '<span class="textColourRed">Cancelled</span>';
					}else{
						stat = '<span class="textColourRed">Invalid</span>';
					}
	                table.append('<tr><td>'+(a+1)+'</td>'
	                		+'<td style="text-align: center;"><a href="#" onclick="javascript:viewWIEwayBillDetailedPage('+b.ewaybillNo+','+userId+');">'+b.ewaybillNo+'</a></td>'
	                		+'<td>'+b.toTraderName+'</td>' 
	                		+'<td style="text-align: center;">'+b.ewaybill_date+'</td>'
	                		+'<td style="text-align: center;">'+b.ewaybill_valid_upto+'</td>' 
	                		+'<td style="text-align: center;">'+stat+'</td></tr>');
	            });
			}  
			$("#invoiceHistoryTab").DataTable();          
        },
        error: function (data,status,er) {        	 
        	getWizardInternalServerErrorPage();   
       }
    });
    
});