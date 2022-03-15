
$(document).ready(function () {
	$('#divTwo').hide();
	loadPurchaseEntryTable();
	$('.loader').fadeOut("slow");	
	$('#divTwo').show();
});
 
function formatDateInDDMMYYYYHHMM(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
    var am_pm = date.getHours() >= 12 ? "PM" : "AM"; 
	hours = hours < 10 ? "0" + hours : hours;
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    //time = hours + ":" + minutes + " " + am_pm;
    time = '';
	return (date.getDate() + "/"+month+"/"+date.getFullYear()+"  "+time);
	
} 

function loadPurchaseEntryTable(){
	$.ajax({
		url : "getPurchaseEntriesDetailList",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",			
		async : false,
		beforeSend: function(){
			$('.loader').show();
		},
		complete: function(){
			$('.loader').hide();
		},		
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
			var counter=1;
			 $.each(json,function(i,value){
				 $('#purchaseHistoryTab tbody:last-child').append(
						 '<tr>'
						 	/*+'<td>'+counter+'</td>'*/
						 	+'<td><a href="#"  onclick="javascript:viewRecord('+value[0]+');">'+value[5]+'</a></td>'
							+'<td>'+formatDateInDDMMYYYYHHMM(value[6])+'</td>'
						 	+'<td>'+value[2]+'</td>'
						 	+'<td>'+parseFloat(value[3])+'</td>'
						 
						 +'</tr>'
				 );
				 counter++;
			});
		},
		error: function (data,status,er) {				 
			 getInternalServerErrorPage();   
		}
	});

	createDataTable('purchaseHistoryTab');
}

function viewRecord(idValue){
	document.managePurchaseEntries.action = "./getPurchaseEntryDetails"; 
	document.managePurchaseEntries.id.value = idValue;
	document.managePurchaseEntries._csrf_token.value = $("#_csrf_token").val();
	document.managePurchaseEntries.submit();
}
