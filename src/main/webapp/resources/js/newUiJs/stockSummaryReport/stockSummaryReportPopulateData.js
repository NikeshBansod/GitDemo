
var gstinJson;
var traversFrom;
var expanded = false;
var multiLocation;
var storeId;

// function for select all option 
$(document).on('click', '#selectall', function(){
	
        if(this.checked){
            $('.checkboxall').each(function(){
                this.checked = true;
              
            })
        }else{
            $('.checkboxall').each(function(){
                this.checked = false;
            })
        }
   
});


//function for Multiple Checkbox Select/Deselect w.r.t select all option

$(document).on('click', '.checkboxall', function(){

	if($(".checkboxall").length == $(".checkboxall:checked").length) {
		
		$("#selectall").prop('checked',true);
	
	} else {
		
		$("#selectall").prop('checked',false);
		
	
	}

});



$(function() {
    $(".multiselectId").multiselect();
});

$(document).ready(function () {	
	
	var fDate = $("#backfDate").val();
	var tDate = $("#backtDate").val();
	 multiLocation = $("#backstoreId").val();
	var gstin = $("#backgstin").val();
	 traversFrom = $("#traversFrom").val();
	if(multiLocation==""){
		multiLocation=0
	}
	$('#generateReportButton').prop('disabled', false);	

	$('#generateReportGrid').hide();
	
	//createDataTable(stockSummaryReportTab);
	$('.loader').fadeOut("slow");
	
	summaryreportTable = $('#stockSummaryReportTab').DataTable({
        rowReorder: {
            selector: 'td:nth-child(2)'
        },
        responsive: true
    });
	
	if(traversFrom =="backData")
	{
	
		loadReport(gstin,multiLocation,fDate,tDate);
	
	}
     loadGstinField(gstin,storeId);
	
	$("#fromDate").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
        onSelect: function(selected) {
          $("#toDate").datepicker("option","minDate", selected);
          $("#fromDate").addClass("input-correct").removeClass("input-error");          
	  	  if($("#toDate").val().length > 0){
	  		  $("#toDate").addClass("input-correct").removeClass("input-error");
          }
          else{
        	  $("#toDate").addClass("input-error").removeClass("input-correct");
          }
	  	  
          $('#generateReportButton').prop('disabled', false);
          if($('#generateReportGrid').is(':visible')){
        	  $('#stockSummaryReportTab').DataTable().row().remove();
          }
	  	  $('#generateReportGrid').hide(); 
	  	  $('#fromDate-csv-id').hide()
        }
    }).datepicker("setDate", fDate);
	
	
	$("#toDate").datepicker({
    	changeMonth: true, 
		changeYear: true,
    	dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
        onSelect: function(selected) {
           $("#fromDate").datepicker("option","maxDate", selected);
           $("#toDate").addClass("input-correct").removeClass("input-error");
          if($("#fromDate").val().length <= 0){
        	  $("#fromDate").addClass("input-error").removeClass("input-correct");
          }
          else{
        	  $("#toDate").addClass("input-correct").removeClass("input-error");
          }
          
          $('#generateReportButton').prop('disabled', false);
          if($('#generateReportGrid').is(':visible')){
        	  $('#stockSummaryReportTab').DataTable().row().remove();
          }
	  	  $('#generateReportGrid').hide(); 
	  	$('#toDate-csv-id').hide();
        }
    }).datepicker("setDate", tDate);;   



});


$('#gstin').on("change", function() {
	 $('#generateReportButton').prop('disabled', false);
	 if($('#generateReportGrid').is(':visible')){
   	  $('#stockSummaryReportTab').DataTable().row().remove();
     }
 	  $('#generateReportGrid').hide();     
	var gstinSelectedId = $('#gstin').val();	
	if(gstinSelectedId != ''){
		$('#gstin').addClass("input-correct").removeClass("input-error");
		$('#gstin-csv-id').hide();
		$("#multiselectId").empty();

		/*$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" id="selectall" name="selectedAllLocation" value="'+0+'"/> Select all </label>'));*/
		
		$.each(gstinJson,function(i,value) {
			if(value.id == gstinSelectedId ){
				
				if(value.gstinLocationSet.length>1){
					$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" id="selectall" name="selectedAllLocation" value="'+0+'"/> Select all </label>'));
					$.each(value.gstinLocationSet,function(i2,value2) {
					$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option" value="'+value2.id+'"/>   '+value2.gstinLocation+'</label>'));
				});	

				}
				else{
				$.each(value.gstinLocationSet,function(i2,value2) {
					$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option" value="'+value2.id+'"/>   '+value2.gstinLocation+'</label>'));
					
				
				
				});	
			}		
						
			}
		});			
	}else{
		$('#gstin-csv-id').show();
   	  	$('#gstin').addClass("input-error").removeClass("input-correct");
	}     
}); 

$('#location').on("change", function() {
	$('#generateReportButton').prop('disabled', false);
	if($('#generateReportGrid').is(':visible')){
  	  $('#stockSummaryReportTab').DataTable().row().remove();
    }
	  $('#generateReportGrid').hide();    
	if($('#location').val() != ''){
		$('#location').addClass("input-correct").removeClass("input-error");
		$('#location-csv-id').hide();
	}else{
		$('#location').addClass("input-error").removeClass("input-correct");
		$('#location-csv-id').show();
	}	
});

$('#generateReportButton').on('click',function(e){

	if($('input:checkbox[name=selectedAllLocation]').is(':checked'))
		{
			multiLocation = $('input[id=selectall]:checked').map(function(_, el) {
	        return $('#selectall').val();
	    }).get().join();
			//alert(multiLocation);
		}
	else{
			multiLocation = $('input[type=checkbox]:checked').map(function(_, el) {
	        return $(el).val();
	    }).get().join();
	    
			//alert(multiLocation);
		}
	
	$('#generateReportButton').prop('disabled', true);
	if($('#generateReportGrid').is(':visible')){
  	  $('#stockSummaryReportTab').DataTable().row().remove();
    }
	var gstinErrFlag = validateSelectField("gstin","gstin-csv-id");
	//var locationErrFlag = validateSelectField("location","location-csv-id");
	var locationErrFlag = multiSelectCheckBox("multiselectId","checkbox","location-csv-id");
	var fromDateFlag = validateSelectField("fromDate","fromDate-csv-id");
	var toDateFlag = validateSelectField("toDate","toDate-csv-id");
	
	
	if(gstinErrFlag ||fromDateFlag || toDateFlag || locationErrFlag ){
		e.preventDefault();
		$('#generateReportButton').prop('disabled', false);
	}
	else{
		var storeId = multiLocation;
		var fDate = $('#fromDate').val();
		var tDate = $('#toDate').val();
		var gstinNo = $('#gstin').val();
		loadReport(gstinNo,storeId,fDate,tDate);
	}	
});



function loadGstinField(gstin,storeId){
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
							if(gstin == value.id){
								$('#gstin').append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value', value.id).attr('selected', 'selected'));

							}else{
							    $("#gstin").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
							}
						});
						
						if(json.result[0].gstinLocationSet.length == 1){
							$.each(json.result[0].gstinLocationSet,function(i,value) {
								if(multiLocation == value.id){
									/*$('#location').append($('<option>').text(value.gstinLocation).attr('value', value.id).attr('selected', 'selected'));
*/
									$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'" checked="checked"/>   '+value.gstinLocation+'</label>'));
								}else{
									/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
									$("#locationStore").val(value.gstinStore);*/
									$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'"/>   '+value.gstinLocation+'</label>'));
								}
							});
							
						}else{
							/*$("#location").append('<option value="">Select</option>');*/
							//$("#location").append('<option value="0">All</option>');
							$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" id="selectall" name="option[]" value="'+0+'"/> Select all </label>'));
							$.each(json.result[0].gstinLocationSet,function(i,value) {
								if(multiLocation == value.id){
									/*$('#location').append($('<option>').text(value.gstinLocation).attr('value', value.id).attr('selected', 'selected'));*/
									$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'" checked="checked"/>   '+value.gstinLocation+'</label>'));
								}
								
								else{
								/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));*/
									$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'"/>   '+value.gstinLocation+'</label>'));	
								}
							});
							
							/*if(storeId=="0" && traversFrom == "backData"){
								$("#location option[value='0']").remove();
								$("#location").append($('<option>').text("All").attr('value', "0").attr('selected', 'selected'));
							}*/
							
						}
					}else{
						$("#gstin").append('<option value="">Select</option>');
						$.each(json.result,function(i,value2) {
							if(gstin == value2.id){
								$('#gstin').append($('<option>').text(value2.gstinNo+' [ '+value2.stateInString +' ] ').attr('value', value2.id).attr('selected', 'selected'));
								
								//Rnd -Start
								if(value2.gstinLocationSet.length == 1){
									$.each(json.result[0].gstinLocationSet,function(i,value) {
										if(multiLocation == value.id){
											/*$('#location').append($('<option>').text(value.gstinLocation).attr('value', value.id).attr('selected', 'selected'));*/
											$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'" checked="checked"/>   '+value.gstinLocation+'</label>'));
										}else{
											/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
											  $("#locationStore").val(value.gstinStore);*/
											$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'"/>   '+value.gstinLocation+'</label>'));
										
										}
									});
									
								}else{
									
								if(multiLocation=="0" && traversFrom == "backData"){
									$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" id="selectall" name="option[]" value="0" checked="checked"/>Select all   </label>'));
								}
									
									$.each(value2.gstinLocationSet,function(i,value) {
										if(multiLocation=="0" && traversFrom == "backData"){
											
											$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'" checked="checked"/>   '+value.gstinLocation+'</label>'));
										}
										
										
										
										else if(multiLocation == value.id){
											
											/*$('#location').append($('<option>').text(value.gstinLocation).attr('value', value.id).attr('selected', 'selected'));*/
											$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'" checked="checked"/>   '+value.gstinLocation+'</label>'));
										}
										
										else{
										/*$("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));*/
											$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" id="selectall" name="option[]" value="0" />Select all</label>'));
											$("#multiselectId").append($('<label>&nbsp;&nbsp;<input type="checkbox" class="checkboxall" name="option[]" value="'+value.id+'"/>   '+value.gstinLocation+'</label>'));
										
										}
									});
								
									
								}
								//Rnd _ End
								
								
								
								
							}else{
								$("#gstin").append($('<option>').text(value2.gstinNo+' [ '+value2.stateInString +' ] ').attr('value',value2.id));
							}
						});
					}		
				}else{
					bootbox.alert(json.message);
				}
			}			
		},
		error: function (data,status,er) {				 
			getInternalServerErrorPage();   
		}
	});	
}

function loadReport(gstinNo,storeId,fDate,tDate){
	$.ajax({
		url : "getgoodsbystoreidanddate",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
		data : {gstinNo:gstinNo, storeId : storeId, fDate : fDate, tDate : tDate},
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
			summaryreportTable.clear().draw(); 
			counter = 0;
			
			if(json != null){
				if(json.error == true){
					bootbox.alert(json.errorcode+' '+json.message);
				}else{
					 var srno=counter+1;
				 	 $.each(json.result,function(i,value){
				 		
				 		var uom;
				 		if(value.unitOfMeasurement === 'OTHERS'){
				 			uom = value.otherUOM;
				 		}else{
				 			uom = value.unitOfMeasurement;
				 		}

				 		var incrementQty;
				 		if(value.afterincreaseValue === 'null'){
				 			incrementQty = '-';
				 		}else{
				 			incrementQty = checkNConvertToDecimal(value.afterincreaseValue);
				 		}

				 		var decrementQty;
				 		if(value.afterdecreaseValue === 'null'){
				 			decrementQty = '-';
				 		}else{
				 			decrementQty = checkNConvertToDecimal(value.afterdecreaseValue);
				 		}
				 		
				 			MyUniqueID=counter;
				 		    summaryreportTable.row.add($(
				 		    		
					 			'<tr id="tr_id_'+counter+'">'
							 	+'<td >'+srno+'</td>'
							 	//+'<td>'+value.productName+'</td>'
							 	+'<td><a href="#"  onclick="javascript:viewStockDetailedReportRecord('+value.prodId+','+checkNConvertToDecimal(value.currentOpeningStock)+');">'+value.productName+'</a></td>'
							 	+'<td>'+checkNConvertToDecimal(value.currentOpeningStock)+'</td>'
							 	+'<td>'+incrementQty+'</td>'
							 	+'<td>'+decrementQty+'</td>'
							 	+'<td>'+checkNConvertToDecimal(value.closingStock)+'</td>'
							 	+'<td>'+value.uom+'</td>'
							 	+'</tr>'					 	
					 	)).draw();
				 		  
				 		   srno++;
					});
				 	$('.loader').fadeOut("slow");
					$('#generateReportGrid').show();
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


function viewStockDetailedReportRecord(product_id,currentOpeningStock){
	
	document.manageStockDetailedReport.action = "./getstockdetailedreportpage";
	document.manageStockDetailedReport.product_id.value =product_id;
	document.manageStockDetailedReport.currentOpeningStock.value =currentOpeningStock;
	document.manageStockDetailedReport.gstin.value =$("#gstin").val();
	/*document.manageStockDetailedReport.location.value =$("#location").val();*/
	document.manageStockDetailedReport.location.value = multiLocation;
	document.manageStockDetailedReport.fromDate.value =$("#fromDate").val();
	document.manageStockDetailedReport.toDate.value =$("#toDate").val();
	document.manageStockDetailedReport._csrf_token.value = $("#_csrf_token").val();
	document.manageStockDetailedReport.submit();
}

