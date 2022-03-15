 
var gstinJson;
var goodsDetailJson;
var fDate ;
var tDate ;
var storeId ;
var productId ;
var gstin ;
var currentOpeningStock;

$(document).ready(function () {	
	 fDate = $('#fDate').val();
	 tDate = $('#tDate').val();
	 storeId = $('#storeId').val();
	 productId = $('#productId').val();
	 gstin = $('#gstin').val();
	 currentOpeningStock = $('#currentOpeningStock').val();
	loadDetailedReport(storeId,productId,fDate,tDate,currentOpeningStock);

});

function loadDetailedReport(storeId,productId,fDate,tDate,currentOpeningStock){
	$.ajax({
		url : "getstockdetailedreport",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
		dataType : "json",	
		data : {storeId : storeId, productId : productId, fDate : fDate, tDate : tDate, currentOpeningStock : currentOpeningStock},
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
				if(json.error == true){
					bootbox.alert(json.message,function(){
						goBackToSummaryReportPage(gstin,storeId,fDate,tDate);
					
					});
				}else{
					var counter=1;
					if(json.result != null && json.result.length > 0){
						
						
				 		$.each(json.result,function(i,value){
				 			if(i == 0){
				 				$("#productName").val(value.productName);
								var balanceStock = checkNConvertToDecimal(0);
						 		var increaseQty = '-';
						 		var decreaseQty = '-';
						 		var InventoryIncrease;
						 		var InventoryDecrease;
						 		var transaction_Date=value.transactionDate;
						 		
						 		if(typeof transaction_Date === "undefined"){
						 			
						 			transaction_Date='';
						 		}
						 		else{
						 			 
						 			transaction_Date = '-';
						 		}
						 		
						 		
						 			balanceStock = checkNConvertToDecimal(currentOpeningStock);
						 		
						 			
						 		$("#stockStatusDetailedTab  tbody").empty();
						 		$("#stockStatusDetailedTab  tbody:last-child").append(
							 		'<tr>'
									 	+'<td style="width:20px;">'+counter+'</td>'
									 	+'<td>'+transaction_Date+'</td>'
									 	+'<td>'+'-'+'</td>'
									 	+'<td>Opening</td>'
									 	+'<td>'+increaseQty+'</td>'
									 	+'<td>'+decreaseQty+'</td>'
									 	+'<td>'+balanceStock+'</td>'
									 +'</tr>'
							 	);
				 			}
				 		});	
				 		var balanceStockfinal = 0;
				 		var balanceStockcheck = 0;
				 		if(json.result.length >=1){
				 			
						 	$.each(json.result,function(i2,value2){
						 		
						 		if(value2.inventoryUpdate=="Y"){
						 		//var balanceStock = checkNConvertToDecimal(value2.openingStock);
						 			var balanceStock = checkNConvertToDecimal(currentOpeningStock);
						 		if(i2==0){
						 			 balanceStockcheck = balanceStock;	
						 		}
						 		var increaseQty = '-';
						 		var decreaseQty = '-';
						 		var InventoryIncrease;
						 		var InventoryDecrease;
						 		var transaction_Date=value2.transactionDate;
						 		var balanceStockfinal;
						 		var qtyinc='-';
						 		var qtydec='-';
						 		var qtybal=0;
						 		
						 		if(value2.updateType == 'CREDIT'){
						 			qtyinc = checkNConvertToDecimal(value2.quantity);
						 			
							 		if(qtyinc != '-' || !isNaN(qtyinc)){
							 			//balanceStockfinal = (balanceStockcheck*100 + qtyinc*100) / 100 ;
							 			balanceStockfinal = parseFloat(balanceStockcheck) + parseFloat(qtyinc);
							 		}
						 		}else if(value2.updateType == 'DEBIT'){
						 			if(value2.quantity.search("-") === 0){
						 				qtydec = checkNConvertToDecimal(value2.quantity.slice(1));
						 			}else if(value2.quantity.search("-") === -1){
						 				qtydec = checkNConvertToDecimal(value2.quantity);
						 			}
						 			if(qtydec != '-' || !isNaN(qtydec)){
						 				//balanceStockfinal = (balanceStockcheck*100 - qtydec*100) / 100 ;
						 				balanceStockfinal = parseFloat(balanceStockcheck) - parseFloat(qtydec);
						 			}
						 		}
						 		
						 		if(value2.action === 'InventoryIncrease'){
						 			value2.action = 'Other Incr.';
						 		}
						 		else if(value2.action === 'InventoryDecrease'){
						 			value2.action = 'Other Decr.';
						 		}
						 		else if(value2.action === 'Movement Between Stores'){
						 			value2.action = 'Store Move';
						 		}
						 		else if(value2.action === 'CN-CREDIT'){
						 			value2.action = 'CN';
						 		}
						 		else if(value2.action === 'CN-DEBIT'){
						 			value2.action = 'CN';
						 		}
						 		else if(value2.action === 'DN-CREDIT'){
						 			value2.action = 'DN';
						 		}
						 		else if(value2.action === 'DN-DEBIT'){
						 			value2.action = 'DN';
						 		}
						 		else if(value2.action === 'INVOICE-DEBIT'){
						 			value2.action = 'Sales';
						 		}else if(value2.action === 'PURCHASE-ENTRY-CREDIT'){
						 			value2.action = 'Purchase';
						 		}
						 		
						 			$("#stockStatusDetailedTab  tbody:last-child").append(
									 		'<tr>'
											 	+'<td style="width:20px;">'+(counter+1)+'</td>'
											 	+'<td>'+formatDateInDDMMYYYYHHMM(value2.transactionDate)+'</td>'
											 	+'<td>'+value2.documentNo+'</td>'
											 	+'<td>'+value2.action+'</td>'
											 	+'<td>'+qtyinc+'</td>'
											 	+'<td>'+qtydec+'</td>'
											 	+'<td>'+checkNConvertToDecimal(balanceStockfinal)+'</td>'
											 +'</tr>'
									 	);
						 		
						 		counter++;
						 		balanceStockcheck=balanceStockfinal;
						 	
						 	}
						 	});
						 	
				 		}
							createDataTable('stockStatusDetailedTab');	
							$('#stockStatusDetailedGrid').show();
				 					 		
					}else{
						if(json.error == true){
							
							bootbox.alert(json.message)
							
						}
					}
				}
			}
			$('#show').prop('disabled', false);
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
	return (date.getDate() + "/"+month+"/"+date.getFullYear());	//+"  "+time
	} 


$("#goBackToSummaryReport").click(function(){
	goBackToSummaryReportPage($("#gstin").val(),$("#storeId").val(),$("#fDate").val(),$("#tDate").val());
});

function goBackToSummaryReportPage(gstin,location,fromDate,toDate){
      $('.loader').show();
      document.redirectBackToSummaryReport.action = "./backtostocksummaryreport";
      document.redirectBackToSummaryReport.gstin.value = gstin;
  	  document.redirectBackToSummaryReport.location.value =location;
  	  document.redirectBackToSummaryReport.fromDate.value =fromDate;
  	  document.redirectBackToSummaryReport.toDate.value =toDate;
      document.redirectBackToSummaryReport._csrf_token.value = $("#_csrf_token").val();
      document.redirectBackToSummaryReport.submit(); 
      $('.loader').fadeOut("slow");
}

