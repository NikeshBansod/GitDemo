$(document).ready(function() {	
	
	var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var userId = $("#userId").val();
	var appCode = $("#appCode").val();
	var invoiceId = $("#invoiceId").val();

    $.ajax({
        url: 'ewaybill/getEWayBills',
        type : "POST",
		contentType : "application/json",
		dataType : "json",
		headers: {invoiceId : invoiceId},
		data : JSON.stringify(jsonData),
		async : false,
		beforeSend: function(){
			$('.loader').show();
	    },
	    complete: function(){
	    	$('.loader').hide();
	    },
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
					 $('#genericEwayBillTab tbody:last-child').append(
						'<tr>'
							 +'<td>'+(a+1)+'</td>'
	                		 +'<td><a href="#" onclick="javascript:viewWIEwayBillDetailedPage('+b.ewaybillNo+','+userId+');">'+b.ewaybillNo+'</a></td>'
	                		 +'<td>'+b.toTraderName+'</td>' 
	                		 +'<td>'+b.ewaybill_date+'</td>'
	                		 +'<td>'+b.ewaybill_valid_upto+'</td>' 
	                		 +'<td>'+stat+'</td>'
	                    +'</tr>');
	            });
			}        
        },
        error: function (data,status,er) {        	 
        	getWizardInternalServerErrorPage();   
       }
    }); 

	createDataTable('genericEwayBillTab');
	$(".loader").fadeOut("slow");	
});


function generateEWayBill(){	
	window.location.href = "./loginGenerateEwayBill";
}

function viewWIEwayBillDetailedPage(idValue,userId){
	document.previewInvoice.action = "./getpreviewgenericewaybill";
	document.previewInvoice.id.value = idValue;
	document.previewInvoice.userId.value = userId;
	document.previewInvoice.submit();
}
