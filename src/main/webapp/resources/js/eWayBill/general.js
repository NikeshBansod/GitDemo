//var blankMsg = "This field is required";
var currencyRegex = /(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;

$(document).ready(function(){
	
	
	defaultPageLoad();
	
	$("#openCloseDiv").hide();
	
	$('#doc_date').datepicker({
		showButtonPanel: true,
		changeMonth: true, 
		changeYear: true,
		firstDay: 1,
	    dateFormat: 'dd-mm-yy',
	    numberOfMonths: 1,
	    minDate:'01-07-2017', 
	    maxDate: new Date,
	    onSelect: function(dateAsString,evnt) {
	    	
	    	var newDateFormat = $.datepicker.formatDate( "yy-mm-dd", new Date( dateAsString.split('-')[2], dateAsString.split('-')[1] - 1, dateAsString.split('-')[0] ));
	    	//checkDateForInvoice(newDateFormat);
	    	
	    }
	}).datepicker("setDate", new Date());
	
	$("#modeOfTransport").on("change", function() {
		resetModeOfTransportData();
		defaultPageLoad();
	});
	
	$("#kmsToBeTravelled").keyup(function() {
	    var $this = $(this);
	    $this.val($this.val().replace(/[^\d.]/g, ''));        
	});
	
});

function defaultPageLoad(){
	var modeOfTransport = $("#modeOfTransport option:selected").text();
	//alert("modeOfTransport : "+modeOfTransport);
	if(modeOfTransport == "Road"){
		$("#vehicleTypeDiv").show();
		$("#vehicleNoDiv").show();
		$("#transporterNameDiv").show();
		$("#docNoDiv").show();
		$("#docDateDiv").show();
		$("#transporterIdDiv").show();
		
		$("#vehicleNoLabel").text("Vehicle No");
		$("#transporterNameLabel").text("Transporter Name");
		$("#docNoLabel").text("Transporter Doc No");
		$("#docDateLabel").text("Document Date");
		$("#transporterIdLabel").text("Transporter Id");
		
		$("#docNoDiv").removeClass("mandatory");	 
	}else if(modeOfTransport == "Rail"){
		
		$("#vehicleTypeDiv").hide();
		$("#vehicleNoDiv").hide();
		$("#transporterNameDiv").hide();
		$("#docNoDiv").show();
		$("#docDateDiv").show();
		$("#transporterIdDiv").hide();
		
		$("#vehicleNoLabel").text("");
		$("#transporterNameLabel").text("");
		$("#docNoLabel").text("RR No");
		$("#docDateLabel").text("Document Date");
		$("#transporterIdLabel").text("");
		
		$("#docNoDiv").addClass("mandatory");
		
	}else if(modeOfTransport == "Air"){
		
		$("#vehicleTypeDiv").hide();
		$("#vehicleNoDiv").hide();
		$("#transporterNameDiv").hide();
		$("#docNoDiv").show();
		$("#docDateDiv").show();
		$("#transporterIdDiv").hide();
		
		$("#vehicleNoLabel").text("");
		$("#transporterNameLabel").text("");
		$("#docNoLabel").text("Airway Bill No");
		$("#docDateLabel").text("Document Date");
		$("#transporterIdLabel").text("");
		
		$("#docNoDiv").addClass("mandatory");
		
	}else if(modeOfTransport == "Ship"){
		
		$("#vehicleTypeDiv").hide();
		$("#vehicleNoDiv").hide();
		$("#transporterNameDiv").hide();
		$("#docNoDiv").show();
		$("#docDateDiv").show();
		$("#transporterIdDiv").hide();
		
		$("#vehicleNoLabel").text("");
		$("#transporterNameLabel").text("");
		$("#docNoLabel").text("Bill Of Lading No");
		$("#docDateLabel").text("Document Date");
		$("#transporterIdLabel").text("");
		
		$("#docNoDiv").addClass("mandatory");
	}else{
		
		$("#vehicleTypeDiv").hide();
		$("#vehicleNoDiv").hide();
		$("#transporterNameDiv").hide();
		$("#docNoDiv").hide();
		$("#docDateDiv").hide();
		$("#transporterIdDiv").hide();
		
		$("#vehicleNoLabel").text("Vehicle No");
		$("#transporterNameLabel").text("Transporter Name");
		$("#docNoLabel").text("Transporter Doc No");
		$("#docDateLabel").text("Document Date");
		$("#transporterIdLabel").text("Transporter Id");
		
		$("#docNoDiv").removeClass("mandatory");
	}
}

function generateInputJson1(){
	var kmsToBeTravelled = $("#kmsToBeTravelled").val();
	var modeOfTransport = $('select#modeOfTransport option:selected').val();
	var vehicleNo = $("#vehicleNo").val();
	var transporterName = $("#transporterName").val();
	var docNo = $("#docNo").val();
	var docDateInString = $("#doc_date").val();
	var transporterId = $("#transporterId").val();
	var uiInvoice = $("#uiInvoice").val();
	var nicUserId = $("#nicUserId").val();
	var nicPwd = $("#nicPwd").val();
	var isCheckNicUserId = $('#isCheckNicUserId').is(':checked')? 'Yes': 'No'; 
	var vehicleType = $('input[name=vehicleType]').filter(':checked').val();
	
	   var inputData = {
				"kmsToBeTravelled": parseFloat(kmsToBeTravelled),
				"modeOfTransport": parseFloat(modeOfTransport),
				"vehicleNo": vehicleNo,
				"transporterName": transporterName,
				"docNo": docNo,
				"docDateInString": docDateInString,
				"transporterId": transporterId,
				"invoiceId" : parseFloat(uiInvoice),
				"nic_id" : nicUserId,
				"nicPwd" : nicPwd,
				"isCheckNicUserId" : isCheckNicUserId,
				"vehicleType" : vehicleType
			
			};
	   
	   return inputData;
	   console.log(inputData);
}

function resetModeOfTransportData(){
	$("#vehicleNo").val("");
	$("#transporterName").val("");
	$("#docNo").val("");
	$("#doc_date").datepicker("setDate", new Date());
	$("#transporterId").val("");
	
	$("#docNo").removeClass("input-error");
	$("#docNo-csv-id").hide();
	
	
}


