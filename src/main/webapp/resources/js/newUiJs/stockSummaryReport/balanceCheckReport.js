
var gstinJson;
var balancereportTable;

$(document).ready(function () {	
	$('#generateReportButton').prop('disabled', false);	

	$('#generateReportGrid').hide();
	loadGstinField();
	//createDataTable(balanceStocksTab);
	$('.loader').fadeOut("slow");
	
 balancereportTable = $('#balanceStocksTab').DataTable({
        rowReorder: {
            selector: 'td:nth-child(2)'
        },
        responsive: true
    });
	
	
	
	
	$("#tillDate").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
        onSelect: function(selected) {
          $("#tillDate").addClass("input-correct").removeClass("input-error");
         $("#fromDate-csv-id").hide();
          $('#generateReportButton').prop('disabled', false);
          if($('#generateReportGrid').is(':visible')){
        	  $('#balanceStocksTab').DataTable().row().remove();
          }
	  	  $('#generateReportGrid').hide();    
        
        }
    });
	   
});


$('#gstin').on("change", function() {
	$('#generateReportButton').prop('disabled', false);	
	if($('#generateReportGrid').is(':visible')){
		
		$('#generateReportGrid').hide();
		$("#balanceStocksTab").DataTable().rows().remove();
	}
	var gstinSelectedId = $('#gstin').val();	
	if(gstinSelectedId != ''){
		var length;
		$('#gstin').addClass("input-correct").removeClass("input-error");
		$('#gstin-csv-id').hide();
		$("#location").empty();
		$("#location").append('<option value="">Select</option>');
		//$("#location").append('<option value="0">All</option>');
		$.each(gstinJson,function(i,value) {
			if(value.id == gstinSelectedId ){
				$.each(value.gstinLocationSet,function(i2,value2) {
					
					$("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
				});				
			}
		
		});			
	
			/*length = $('#location').children('option').length;
			if(length==3){
				$("#location option[value='0']").remove();
			}
			else{
				$("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
			}*/
		
	
	}else{
		$('#gstin-csv-id').show();
   	  	$('#gstin').addClass("input-error").removeClass("input-correct");
	}     
}); 

$('#location').on("change", function() {
	$('#generateReportButton').prop('disabled', false);
	$('#location').removeClass("input-correct");
	if($('#generateReportGrid').is(':visible')){
		$('#generateReportGrid').hide();
		$("#balanceStocksTab").DataTable().rows().remove();
	}
	if($('#location').val() != ''){
		$('#location').addClass("input-correct").removeClass("input-error");
		$('#location-csv-id').hide();
	}else{
		$('#location').addClass("input-error").removeClass("input-correct");
		$('#location-csv-id').show();
	}	
});

$('#generateReportButton').on('click',function(e){
	$('#generateReportButton').prop('disabled', true);
	var gstinErrFlag = validateSelectField("gstin","gstin-csv-id");
	var locationErrFlag = validateSelectField("location","location-csv-id");
	var tillDate = validateSelectField("tillDate","fromDate-csv-id");
	
	if(gstinErrFlag || locationErrFlag ||tillDate ){
		e.preventDefault();
		$('#generateReportButton').prop('disabled', false);
	}
	else{
		var storeId = $('#location').val();
		var tDate = $('#tillDate').val();
		var gstinNo=$('#gstin').val();
		loadReport(gstinNo,storeId,tDate);
		
		
	}	
});



function loadGstinField(){
	$.ajax({
		url : "getgstinforloggedinuser",
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
			if(json != null){
				if(json.error == false){
					gstinJson = json.result;
					$("#gstin").empty();						
					if(json.result.length == 1){
						$.each(json.result,function(i,value) {
							$("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
						});
						
						if(json.result[0].gstinLocationSet.length == 1){
							$.each(json.result[0].gstinLocationSet,function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
								$("#locationStore").val(value.gstinStore);
							});
							
						}else{
							$("#location").append('<option value="">Select</option>');
							/*$("#location").append('<option value="0">All</option>');*/
							$.each(json.result[0].gstinLocationSet,function(i,value) {
								$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
							});
						}
					}else{
						$("#gstin").append('<option value="">Select</option>');
						$.each(json.result,function(i,value) {
							$("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
						});
					}		
				}else{
					bootbox.alert(json.errorcode+' '+json.message);
				}
			}			
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}




function loadReport(gstinNo,storeId,tDate){
	$.ajax({
		url : "getgoodsbystoreidandtodate",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
		data : {gstinNo:gstinNo,storeId : storeId, tDate : tDate},
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
			var showTableFlag = false;
			balancereportTable.clear().draw(); 
			counter = 0;
			
			if(json == ''){
				bootbox.alert("Please try after some time");
			}else if(json != null){
				if(json.error == true){
					bootbox.alert(json.message);
				}else{
					 var srno=counter+1;
				 	$.each(json.result,function(i,value){
				 		if( value.currentStock <= 0){
				 			showTableFlag = true;
				 			
				 		    balancereportTable.row.add($(
				 				
					 			'<tr id="tr_id_'+counter+'">'
							 	+'<td >'+srno+'</td>'
							 	+'<td>'+value.productName+'</td>'
							 	+'<td>'+checkNConvertToDecimal(value.currentStock)+'</td>'
							 	+'<td>'+checkNConvertToDecimal(value.stockValue)+'</td>'
							 	+'<td>'+value.uom+'</td>'
							 	+'</tr>'					 	
					 	)).draw();
				 		  
				 		   srno++;
				 		}  
				 		
					});
				 	/*balancereportTable.destroy();
				 	datatble('balanceStocksTab');*/
				 	if(showTableFlag === true){
						$('#generateReportGrid').show();
				 	}else{
				 		bootbox.alert('No record found');
				 	}
				 	$('.loader').fadeOut("slow");
				 	
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


