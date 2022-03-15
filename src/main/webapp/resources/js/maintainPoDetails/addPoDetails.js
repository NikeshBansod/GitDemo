jQuery(document).ready(function($){
       
   $('#poValidFromDateTemp').Zebra_DatePicker();
   $('#poValidToDateTemp').Zebra_DatePicker();
   
   $("#poValidFromDateTemp").Zebra_DatePicker({
	   pair: $('#poValidToDateTemp'),
	   onSelect : function(){
		   validateDateField("poValidFromDateTemp","po-from-date")
	   }
	 });

	 $("#poValidToDateTemp").Zebra_DatePicker({
		 
		 direction: true,
		 onSelect : function(){
  		 validateDateField("poValidToDateTemp","po-to-date")
  	   }
	 });
});


$(document).ready(function(){
    $.ajax({
		url : "getProductsList",
		method : "post",
		contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json){
			 
			 $('#poAssocProductName').append(
				'<option value="">Select Goods</option>');
				$.each(json,function(i,value) {
					$('#poAssocProductName').append(
						$('<option>').text(value.name)
							.attr('value',value.id));
				});
         }
	}); 


    $.ajax({
		url : "getCustomerDetailsList",
		method : "post",
		contentType : "application/json",
		dataType : "json",
//		async : false,
		success:function(json){
			 
			 $('#poCustomerName').append(
				'<option value="">Select Customer</option>');
				$.each(json,function(i,value) {
					$('#poCustomerName').append(
						$('<option>').text(value.custName)
							.attr('value',value.id));
				});
         }
	}); 
    
    
    $.ajax({
		url : "getServicesList",
		method : "post",
		contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json){
			 
			 $('#poAssocServiceName').append(
				'<option value="">Select Service</option>');
				$.each(json,function(i,value) {
					$('#poAssocServiceName').append(
						$('<option>').text(value.name)
							.attr('value',value.id));
				});
         }
	});
    
    

     
});

$("#poNo").blur(function(){
	
    var poNo = $("#poNo").val();
    if(poNo != ''){
    var data={"poNo":poNo}; 	
    
   $.ajax({
		url : "checkIfPoNoExists",
		type: 'POST',
		dataType : "json",
		data : data,
		success : function(jsonVal) {
			if(jsonVal == true){
				 $("#poNo").addClass("input-error").removeClass("input-correct");
				 $("#po-no").text('Po No - '+poNo+' already Exists for your Organization');
				 $("#po-no").show();
				 poNoExists = true;
			}else{
				 $("#poNo").addClass("input-correct").removeClass("input-error");
				 $("#po-no").hide();
				 poNoExists = false;
				
			}
			
		}
    }); 
    }
   
});

$(document).ready(function() {
	$("#poAssocServiceName").hide();
	
    $('input[type=radio][name=selType]').change(function() {
        if (this.value == 'Product') {
        	$("#poAssocServiceName").hide();
        	$("#poAssocProductName").show();
        	$("#poAssocServiceName").val("");
        }
        else if (this.value == 'Service') {
        	$("#poAssocProductName").hide();
        	$("#poAssocServiceName").show();
        	$("#poAssocProductName").val("");
        }
    });
});
	



