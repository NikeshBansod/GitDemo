

$(document).ready(function(){
	

	inventoryHistoryDataTab = $('#inventoryHistoryDataTable').DataTable({
	        rowReorder: {
	            selector: 'td:nth-child(2)'
	        },
	        responsive: true
	    });

	
	loadInventoryHistoryDetails();
	
});

function loadInventoryHistoryDetails(){
	
	$.ajax({
		url : "getinventoryhistorydetails",
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
			$('.loader').fadeOut("slow");
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
			console.log(json);
			$('.loader').show();
			inventoryHistoryDataTab.clear().draw(); 
			counter = 0;
			
			if(json.access == 'ACCESS_DENIED'){
				bootbox.alert("Data is manipulated.", function() {
					window.location.href =  getCustomLogoutPage();
					return
				});
			}else if(json == '' || json == null){
				bootbox.alert("Please try again after some time.");
			}
			
			
			else if(json != null){
				if(json.error == true){
					bootbox.alert(json.errorcode+' '+json.message);
				}else{
					 var srno=counter+1;
				 	 $.each(json,function(i,value){
				 		if(value[0]=="InventoryIncrease"){	
				 		'<tr id="tr_id_'+counter+'">'
				 		inventoryHistoryDataTab.row.add($(
				 		    	
					 			'<tr id="tr_id_'+counter+'">'
							 	+'<td >'+srno+'</td>'
							 	+'<td>'+formatDateInDDMMYYYYHHMM(value[1])+'</td>'
							 	+'<td>'+value[4]+'</td>'
							 	+'<td>'+value[7]+'</td>'
							 	+'<td>'+value[0]+'</td>'
							 	/*+'<td>'+value[4]+'</td>'*/
							 	+'<td>'+value[13]+'</td>'
							 	+'<td>'+value[14]+'</td>'
							 	+'</tr>'					 	
					 	)).draw();
				 		   srno++;
				 	 }});
				 	$('.loader').fadeOut("slow");
					$('#histable').show();
				}
			}
			else{
				bootbox.alert('Service unavailable');
			}	
			
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}
	

function formatDateInDDMMYYYYHHMM(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
    var am_pm = date.getHours() >= 12 ? "PM" : "AM"; 
	hours = hours < 10 ? "0" + hours : hours;
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    time = hours + ":" + minutes + " " + am_pm;
	return (date.getDate() + "/"+month+"/"+date.getFullYear());	
	//+"  "+time
	} 
/*
$.each(json,function(i,value){
	var formateedValueRR=formatTheValueRR(value[4]);
	var formatedValueDT=formatTheValueDT(value[3]);
    console.log("My ID:"+value.originalInvoicePkId);
	 $('#revisionAndReturnDataTable tbody:last-child').append('<tr>'
			 +'<td><a href="#" onclick="javascript:viewRecordForRR('+value[6]+','+value[7]+','+value[8]+',\''+formatedValueDT+'\','+value[9]+');">'+value[0]+'</a></td>'
			 +'<td>'+formatDateInDDMMYYYYHHMM(value[1])+'</td>'
			 +'<td>'+value[2]+'</td>'
			 +'<td>'+formatedValueDT+'</td>'
			 +'<td>'+formateedValueRR+'</td>'
			 +'<td><a href="#" onclick="javascript:viewInvoiceHistoryForRR('+value[6]+','+value[7]+','+value[8]+',\''+formatedValueDT+'\','+value[9]+');">'+value[5]+'</a></td>'
			 
       +'</tr>'
	);
		 
});
 	*/