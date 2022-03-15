  
$(document).ready(function () {	
	$('#divTab').hide();
	changeBreadCrum($("#documentType").val());
	loadInvoiceTable($("#documentType").val());
	$('#divTab').show();
	$('.loader').fadeOut("slow");
	//createDataTable('invoiceHistoryTab');
	 $('#invoiceHistoryTab').DataTable( {

		 colReorder: true,
		 /*initComplete: function() {
		    resetColReorderMD()
		 },*/
		 order: [],
		 aoColumns: [
		               null,
		               { "sType": "date-uk" },
		               null,
		               null,
		               null
		               
		           ],
	 	rowReorder: {
	 		selector: 'td:nth-child(0)',
	 		
	 	},
		 responsive: true
     });
});


$(document).ready(function() {
	
	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	    "date-uk-pre": function ( a ) {
	        return parseDates(a);
	    },

	    "date-uk-asc": function ( a, b ) {
	        a = parseDates(a);
	        b = parseDates(b);
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },

	    "date-uk-desc": function ( a, b ) {
	        a = parseDates(a);
	        b = parseDates(b);
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	});
}); 





function resetColReorderMD() {
  $('#invoiceHistoryTab thead th').each(function() {
    var md = $._data($(this)[0]).events.mousedown;
    for (var i=0, l=md.length; i<l; i++) {
      if (md[i].namespace == 'ColReorder') {
        md[i].handler = function() {}
      }
    }  
  });
}

function loadInvoiceTable(documentType){
	var urlToCall = "getInvoiceDetailList";
	if(documentType == 'invoice'){
		urlToCall = "getOnlyInvoicesList";
	}else if(documentType == 'billOfSupply'){
		urlToCall = "getOnlyBillOfSupplyList";
	}else if(documentType == 'rcInvoice'){
		urlToCall = "getRCInvoicesList";
	}else if(documentType == 'eComInvoice'){
		urlToCall = "getEComInvoicesList";
	}else if(documentType == 'eComBillOfSupply'){
		urlToCall = "getEComBillOfSupplyList";
	}
	
	$.ajax({
		url : urlToCall,
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
	    type : "POST",
	    data : { "documentType" : documentType },
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
			var counter=1;
			 $.each(json,function(i,value){
				 $('#invoiceHistoryTab tbody:last-child').append(
						 '<tr>'
						 /*	+'<td>'+counter+'</td>'*/
						 	+'<td><a href="#"  onclick="javascript:viewRecord('+value[0]+');">'+value[1]+'</a></td>'
						 	+'<td>'+formatDateInDDMMYYYYHHMM(value[6])+'</td>'
						 	+'<td>'+value[4]+'</td>'
						 	+'<td>'+value[5]+'</td>'
							+'<td>'+parseFloat(value[2])+'</td>'
						 	
						 +'</tr>');
				 counter++;
			});
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
	var actualdate = (date.getDate()) < 10 ? "0" + (date.getDate()) : (date.getDate());
    var am_pm = date.getHours() >= 12 ? "PM" : "AM"; 
	hours = hours < 10 ? "0" + hours : hours;
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    //time = hours + ":" + minutes + " " + am_pm;
    time = '';
	return (actualdate +"/"+month +"/"+date.getFullYear());	
} 

function viewRecord(idValue,conditionValue){
	document.manageInvoice.action = "./getInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice.conditionValue.value =$("#documentType").val();
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function changeBreadCrum(documentType){
	$("#internalBreadCrum").html("");
	if(documentType == 'invoice'){
		internalBreadCrumText = ' Invoice History';
	}else if(documentType == 'billOfSupply'){
		internalBreadCrumText = ' Bill Of Supply History';
	}else if(documentType == 'rcInvoice'){
		internalBreadCrumText = ' Reverse Charge History';
	}else if(documentType == 'eComInvoice'){
		internalBreadCrumText = ' E-Commerce Invoice History';
	}else if(documentType == 'eComBillOfSupply'){
		internalBreadCrumText = ' E-Commerce Bill Of Supply History';
	}
	$("#internalBreadCrum").text(internalBreadCrumText);
}

function parseDates(a) {
    var ukDatea = a.split('/');
    return (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
}
