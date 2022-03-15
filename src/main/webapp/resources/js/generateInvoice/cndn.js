var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
var creditNoteMsg = 'Credit Note Value cannot be more than Invoice Value';

$(document).ready(function () {
	
	$("#cnDnValue").keyup(function() {
	    var $this = $(this);
	    $this.val($this.val().replace(/[^\d.]/g, ''));   
	    vCnDnValue();
	});
	
	$("#previewSubmit").click(function(e){
		
		var invoiceValue = $("#invoice_value").val(); 
		var cnDnType = $('input[name=cnDnType]').filter(':checked').val();
		var cnDnValue = $("#cnDnValue").val(); 
		var errFlag1 = vCnDnValue();
		var showPreview = false;
		if(!errFlag1){
			if(cnDnType === 'creditNote'){
				var invoiceValueFl = parseFloat(invoiceValue).toFixed(2);
				var cnDnValuePrevious = getCnDnValuePreviouslyAdded();
				var cnDnValueCurrent = parseFloat(cnDnValue).toFixed(2);
				var cnDnValueFl = parseFloat(cnDnValuePrevious) + parseFloat(cnDnValueCurrent);
				if(parseFloat(invoiceValueFl) < parseFloat(cnDnValueFl)){
					$("#cnDnValue").val("");
					$("#cnDnValue").addClass("input-error").removeClass("input-correct");
					 $("#cnDnValue-csv-id").text(creditNoteMsg);
					 $("#cnDnValue-csv-id").show();	
					//bootbox.alert(creditNoteMsg);
				}else{
					showPreview = true;
				}
			}else{
				showPreview = true;
			}
		}
		
		if(errFlag1){
			e.preventDefault();	 
		}else if(showPreview){
			//var invoiceId = $("#refInvId").val();
			//var inputJson = generateAddCNDNInputJson(cnDnType,parseFloat(cnDnValue).toFixed(2),invoiceId);
			//submitFormAjax(inputJson);
			 $("#previewCnDnInvoiceDiv").show(); 
			 dynamicChangesInPreviewLabels();
			 $("#addCnDnDiv").hide();
			 $("#addCndnHeader").css("display","none"); 
			 $("#previewHeader").show();
		}
		
		if(errFlag1){
			 $("#cnDnValue").focus();
		}
		
		
	});
	
	
	$("#finalSubmitId").click(function(e){
		var invoiceId = $("#refInvId").val();
		var cnDnType = $('input[name=cnDnType]').filter(':checked').val();
		var cnDnValue = $("#cnDnValue").val(); 
		var inputJson = generateAddCNDNInputJson(cnDnType,parseFloat(cnDnValue).toFixed(2),invoiceId);
		submitFormAjax(inputJson);
		
	});
	
	$("#backToAddCnDnDivDiv").click(function(e){
		$("#previewCnDnInvoiceDiv").hide(); 
		$("#addCnDnDiv").show();
		$("#addCndnHeader").show(); 
		$("#previewHeader").css("display","none");
		
	});
	
});

function vCnDnValue(){
	var isError = false;
	if ($("#cnDnValue").val().length > 0){
		 $("#cnDnValue").addClass("input-correct").removeClass("input-error");
		 $("#cnDnValue-csv-id").hide();		 
	}
	
	if (currencyRegex.test($("#cnDnValue").val())!=true){
		 $("#cnDnValue").addClass("input-error").removeClass("input-correct");
		 $("#cnDnValue-csv-id").show();	
		 isError = true;
	}
	
	if (currencyRegex.test($("#cnDnValue").val()) == true){
		 $("#cnDnValue").addClass("input-correct").removeClass("input-error");
		 $("#cnDnValue-csv-id").hide();		 
	}

	if (($("#cnDnValue").val().length < 1) || ($("#cnDnValue").val() <= 0)){
		 $("#cnDnValue").addClass("input-error").removeClass("input-correct");
		 $("#cnDnValue-csv-id").text('This field is required');	
		 $("#cnDnValue-csv-id").show();	
		 isError = true;
	}
	
	return isError;
}

function generateAddCNDNInputJson(cnDnType,cnDnValue,invoiceId){
	var inputJson = {
					"cnDnType" : cnDnType,
					"cnDnValue" : cnDnValue,
					"id" : invoiceId
				};
	
	return inputJson;
}

function submitFormAjax(inputData){
	 $.ajax({
			url : "addCnDn",
			method : "post",
			data : JSON.stringify(inputData),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success:function(json){
				if(json.response == 'SUCCESS'){
					bootbox.alert(json.message, function() {
						setTimeout(function(){
							window.location.href = callInvoiceHistoryPage();
						}, 1000);
					});
				}
	         },
         error:function(data,status,er) { 
                 //alert("error: "+data+" status: "+status+" er:"+er);
          }
		});
}

function getCnDnValuePreviouslyAdded(){
	var totalCnDnAmount = $("#totalCnDnAmount").val();
	//alert("totalCnDnAmount : "+$("#totalCnDnAmount").val());
	return totalCnDnAmount;
}

function dynamicChangesInPreviewLabels(){
	var cnDnType = $('input[name=cnDnType]').filter(':checked').val();
	$('h3[id*=place1]').text('');
	$('li[id*=place2]').text('');
	$('li[id*=place3]').text('');
	$('li[id*=place4]').text('');
	$('li[id*=place5]').text('');
	if(cnDnType === 'creditNote'){
		$('h3[id*=place1]').text('CREDIT NOTE');
		$('li[id*=place2]').text('Total Credit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Credit Note Value (A+B)');
		$('li[id*=place4]').text('Total Credit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Credit Note value Rs.(in words): ');
	}else{
		$('h3[id*=place1]').text('DEBIT NOTE');
		$('li[id*=place2]').text('Total Debit Note Value (A+B+C)');
		$('li[id*=place3]').text('Total Debit Note Value (A+B)');
		$('li[id*=place4]').text('Total Debit Note Value (After Round off)');
		$('li[id*=place5]').text('Total Debit Note value Rs.(in words): ');
	}
}