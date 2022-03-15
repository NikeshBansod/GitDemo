$(document).ready(function () {
	$.ajax({
			url : "getInvoiceDetailList",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
		    type : "POST",
			dataType : "json",			
			async : false,
			success:function(json,fTextStatus,fRequest){				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultWizardSessionExpirePage();
					return;
				}
				if(isValidToken(json) == 'No'){
					window.location.href = getWizardCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				var counter=1;
				 $.each(json,function(i,value){
					 $('#invoiceHistoryTab tbody:last-child').append(
							 '<tr>'
							 	+'<td style="text-align: center;">'+counter+'</td>'
							 	+'<td style="text-align: center;"><a href="#"  onclick="javascript:viewRecord('+value[0]+');">'+value[1]+'</a></td>'
							 	+'<td>'+value[4]+'</td>'
							 	+'<td style="text-align: center;">'+parseFloat(value[2])+'</td>'
							 	+'<td style="text-align: center;">'+value[5]+'</td>'
							 	+'<td style="text-align: center;">'+formatDateInDDMMYYYYHHMM(value[3])+'</td>'
							 +'</tr>'
					 );
					 counter++;
				});
				 $('#invoiceHistoryTab').DataTable();
			},
			error: function (data,status,er) {				 
				getWizardInternalServerErrorPage();   
			}
	});
});


function formatDateInDDMMYYYYHHMM(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
    var am_pm = date.getHours() >= 12 ? "PM" : "AM"; 
	hours = hours < 10 ? "0" + hours : hours;
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    time = hours + ":" + minutes + " " + am_pm;
	return (date.getDate() + "/"+month+"/"+date.getFullYear()+"  "+time);	
} 

function viewRecord(idValue){
	document.manageInvoice.action = "./getWizardInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}
