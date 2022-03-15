jQuery(document).ready(function($){
     
    $('#poValidFromDateTemp').Zebra_DatePicker();
    $('#poValidToDateTemp').Zebra_DatePicker();
    
    var frmdate = $('#poValidFromDateTemp').val();
    
    $("#poValidFromDateTemp").Zebra_DatePicker({
 	   pair: $('#poValidToDateTemp'),
	   onSelect : function(){
		   validateDateField("poValidFromDateTemp","po-from-date")
	   }
 	 });
 	 $("#poValidToDateTemp").Zebra_DatePicker({
 		 
 		 direction: [frmdate,false],
		 onSelect : function(){
      		 validateDateField("poValidToDateTemp","po-to-date")
      	   }
 	 });
 	 
});

$(document).ready(function(){
	 selCustName =$("#custName").val();
	 loadCustomerList();
	
});


function loadCustomerList(){
$.ajax({
	  method:"GET",
	  url:"getCustomersList", 	
	  contentType:'application/json',
	  dataType: 'json',
	  success:function(json) {
		  $.each(json, function(i, value) {
			   
			 if(selCustName==value.id){
				  $('#poCustomerName').append($('<option>').text(value.custName).attr('value', value.id).attr('selected','selected')); 
			 }else{
				 $('#poCustomerName').append($('<option>').text(value.custName).attr('value', value.id));
			 }
				   
			});
	  }
});

}



$(document).ready(function(){
	 selPrdctName =$("#prdctName").val();
	 loadProductList();
	
});


function loadProductList(){
$.ajax({
	  url:"getProductsList", 	
	  type : "POST",
	  dataType: 'json',
	  success:function(json) {
		  $.each(json, function(i, value) {
			  selectType =$("#selctnType").val();
			  if(selectType == 'Product') {
				 if(selPrdctName==value.id){
				  $('#poAssocProductName').append($('<option>').text(value.name).attr('value', value.id).attr('selected','selected'));
			 }else{
				 $('#poAssocProductName').append($('<option>').text(value.name).attr('value', value.id));
			 }
			  } else {
				  $('#poAssocProductName').append($('<option>').text(value.name).attr('value', value.id));
			  }	   
			});
	  }
});

}

$(document).ready(function(){
	 selServiceName =$("#serviceName").val();
	 loadServiceList();
	 
	
});


function loadServiceList(){
$.ajax({
	  //method:"GET",
	  url:"getServicesList", 
	  type : "POST",
	  //contentType: 'application/json',
	 
	  dataType: 'json',
	  success:function(json) {
		  $.each(json, function(i, value) {
			  selectionType =$("#selctnType").val();
			  if(selectionType == 'Service') {
				 if(selServiceName==value.id){
				  $('#poAssocServiceName').append($('<option>').text(value.name).attr('value', value.id).attr('selected','selected'));
			 }else{
				 $('#poAssocServiceName').append($('<option>').text(value.name).attr('value', value.id));
			 }
			  } else {
				  $('#poAssocServiceName').append($('<option>').text(value.name).attr('value', value.id));
			  }
			});
	  }
});

}



$(document).ready(function() {
	selectnType =$("#selctnType").val();
	if(selectnType == 'Product'){
		$("#poAssocServiceName").val("");
		$("#poAssocServiceName").hide();
	} else {
		$("#poAssocProductName").val("");
		$("#poAssocProductName").hide();
	}
	
	    $('input[type=radio][name=selType]').change(function() {
	        if(this.value == 'Product') {
	        	$("#poAssocServiceName").val("");
	        	$("#poAssocServiceName").hide();
	        	$("#poAssocProductName").show();
	        	
	        }
	        else if(this.value == 'Service') {
	        	$("#poAssocProductName").val("");
	        	$("#poAssocProductName").hide();
	        	$("#poAssocServiceName").show();
	        	
	        }
	    });
	});

