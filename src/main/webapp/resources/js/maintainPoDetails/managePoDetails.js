jQuery(document).ready(function($){
    $(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
    if($("#actionPerformed").val()){
  		 $('#toggle').hide();
  	}

    $('#addCustomer').on('click', function(e) {
    	$(".addCustomer").slideToggle();
    	 $('#addCustomer').hide();
    	 $('#toggle').hide();
    	e.preventDefault();
    })
    
     	 
});
		

function val(){
	alert($("#userName").val());
	return true;
}



$(document).ready(function(){
	
	var poNoExists = false;
	var blankMsg="This field is required";
	var length = 2, pinCodeLength = 6;
	var lengthMsg = "Minimum length should be ";
	var regMsg = "data should be in proper format";
	var DateMsg = "From date can not be greater than To date";

	
	$("#poSubmitBtn").click(function(e){
		var errFlag = validatePoNo();
		var errFlag1 = validatefromDate();
		var errFlag2 =validateToDate();
		var errFlag3 = validateCustomername();
		var errFlag4 =validateselectionType();
		var errFlag5 = validateselectionId();
		var errFlag6 = comparingDateField();  
		 
		 if((errFlag) || (errFlag1) || (errFlag2) || (errFlag3) || (errFlag4) || (errFlag5) || (errFlag6) ||(poNoExists)){
			 e.preventDefault();
		 }
		 
		 if((errFlag3)){
			 focusTextBox("poCustomerName");
		 } else if((errFlag)){
			 focusTextBox("poNo");
		 } else if((errFlag1) ){
			 focusTextBox("poValidFromDateTemp");
		 } else if((errFlag2)){
			 focusTextBox("poValidToDateTemp");
		 } 
		 
	});

	function validatePoNo(){
		errFlag = validateTextField("poNo","po-no",blankMsg);
		if(!errFlag){
			errFlag=validateFieldLength("poNo","po-no",lengthMsg,length);
		 }
		 return errFlag;
		 
	}

	function validatefromDate(){
		errFlag1 = validateDateField("poValidFromDateTemp","po-from-date",blankMsg);
		 return errFlag1;
		
	}

	function validateToDate(){
		errFlag2 = validateDateField("poValidToDateTemp","po-to-date",blankMsg);
		 return errFlag2;
		
	}
	
	function comparingDateField(){
		errFlag2 = compareDateField("poValidFromDateTemp","poValidToDateTemp","po-from-date", DateMsg);
		 return errFlag2;
		
	}

	function validateCustomername(){
		errFlag3 = validateSelectField("poCustomerName","po-cust-name");
		 return errFlag3;
	}


	function validateselectionType(){
		errFlag4 = validateRadio("Product", "Service", "prod-serv-req");
		 return errFlag4;
	}

	function validateselectionId(){
		errFlag5 = false;
		if($("#Product").is(":checked")){
			 errFlag5=	validateSelectField("poAssocProductName","po-product");
			 $("#po-service").hide();
		}
		else if($("#Service").is(":checked")){
			 errFlag5=	validateSelectField("poAssocServiceName","po-service");
			 $("#po-product").hide();
		}
		return errFlag5;
	}



	$("#poNo").on("keyup input",function(){
		//this.value = removeWhiteSpace(this.value);
		this.value = this.value.replace(/[^[a-zA-Z0-9-]*$/, '');
		 if ($("#poNo").val().length >= 1){
			 $("#poNo").addClass("input-correct").removeClass("input-error");
			 $("#po-no").hide();		 
		 }
		 	 
		 if ($("#poNo").val().length < 1 ){
			 $("#poNo").addClass("input-error").removeClass("input-correct");
			 $("#po-no").text('This field is required');	
			 $("#po-no").show();	
			 //$("#poNo").focus();
		 }
		 
	});


	$("#poCustomerName").change(function(){
		validateSelectField("poCustomerName","po-cust-name");
	});

	$("#poAssocProductName").change(function(){
		validateSelectField("poAssocProductName","po-product");
	});

	$("#poAssocServiceName").change(function(){
		validateSelectField("poAssocServiceName","po-service");
	});
	
	
    $.ajax({
		url : "getPODetailsList",
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			 $.each(json,function(i,value){
				 $('.dnynamicPODetails').append(
					'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.poNo+'</h1>'
						+'</div>'
						+'<div class="cust-edit">'
						  +'<div class="cust-icon">'
						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						  +'</div>'
						+'</div>'
					+'</div>'
		
					+'<div class="content">'
						+'<div class="cust-con">'
						+'<p> PO/WO Number : '+value.poNo+'</p>'
						+'<p> PO/WO Valid From Date : '+value.poValidFromDate+'</p>'
						+'<p> PO/WO Valid To Date : '+value.poValidToDate+'</p>'
						+'<p> Customer Name : '+value.poCustomerName.custName+'</p>'
						+'</div>'
					+'</div>'	 
				 
				 
				 );
			});
         }
	}); 

     
});




function editRecord(idValue){
	document.poDetail.action = "./editPODetails";
	document.poDetail.id.value = idValue;
	document.poDetail.submit();		
}
	    
function deleteRecord(lLocationId){
	 var x = confirm("Are you sure you want to delete ?");
	 if (x){
	    document.poDetail.action = "./deletePoDetails";
		document.poDetail.id.value = lLocationId;
		document.poDetail.submit();    
	}
	 			
}


$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});


