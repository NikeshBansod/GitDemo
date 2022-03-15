
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
            
            $('#offerDate').Zebra_DatePicker();
});


function loadServicesList(){
    $.ajax({
		url : "getServicesList",
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			 
			 $('#selectOffer').append(
				'<option value="">Select Service</option>');
				$.each(json,function(i,value) {
					$('#selectOffer').append(
						$('<option>').text(value.name)
							.attr('value',value.id));
				});
         }
	}); 

     
}


function loadProductsList(){
    $.ajax({
		url : "getProductsList",
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			 
			 $('#selectOffer').append(
				'<option value="">Select Goods</option>');
				$.each(json,function(i,value) {
					$('#selectOffer').append(
						$('<option>').text(value.name)
							.attr('value',value.id));
				});
         }
	}); 

     
}



$(document).ready(function() {
	var offerExists = false;
	var blankMsg="This field is required";
	var discountRegex = /\d+(\.\d{1,2})?/;
	var regMsg = "Discount value should be in proper format";
//	var currencyRegex = /(?=.)^\$?(([1-9][0-9]{0,2}(,[0-9]{13})*)|[0-9]+)?(\.[0-9]{1,2})?$/;
	var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
	loadProductsList();

	$("#offerSaveBtn").click(function(e){
		
		var errofferDiscount = validateDiscountValue();
		
		if((offerExists) || (errofferDiscount)){
			 e.preventDefault();	
		}
	});

	function validateDiscountValue(){
		errofferDiscount = validateTextField("discountValue","contact-no-req",blankMsg);
		 if(!errofferDiscount){
			 errofferDiscount=validateRegexpressions("discountValue","contact-no-req",regMsg,currencyRegex);
		 }
		 return errofferDiscount;

	}
	
	$("#discountValue").on("keyup input", function(){
		
		this.value = this.value.replace(/[^[0-9.]*$/, '');
		
		if(currencyRegex.test($("#discountValue").val()) === true){
			$("#contact-no-req").hide();
			$("#discountValue").addClass("input-correct").removeClass("input-error");	
		}
		if(currencyRegex.test($("#discountValue").val()) !== true){
			$("#contact-no-req").text(regMsg);
			$("#contact-no-req").show();
			$("#discountValue").addClass("input-error").removeClass("input-correct");	
		}
	});
	
	
	$("#offerNameId").blur(function(){
		
	    var offer = $("#offerNameId").val();
	    if(offer != ''){
	    var data={"offer":offer}; 	
	    
	   $.ajax({
			url : "checkIfOfferExists",
			type: 'POST',
			dataType : "json",
			data : data,
			success : function(jsonVal) {
				if(jsonVal == true){
					 $("#offerNameId").addClass("input-error").removeClass("input-correct");
					 $("#ser-name").text('Offer Name  - '+offer+' already Exists for your Organization');
					 $("#ser-name").show();
					 offerExists = true;
				}else{
					 $("#offerNameId").addClass("input-correct").removeClass("input-error");
					 $("#ser-name").hide();
					 offerExists = false;
				}
				
			}
	    }); 
	   
	    }
	   
	});

	
	
	
    $('input[type=radio][name=offerType]').change(function() {
        if (this.value == 'Product') {
        	$("#selectOffer").empty();
        	loadProductsList();
        	$("#selectOffer").show();
        }
        else if (this.value == 'Service') {
        	$("#selectOffer").empty();
        	loadServicesList();
        	$("#selectOffer").show();
        }
    });


    
    $.ajax({
		url : "getmanageOffers",
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			 $.each(json,function(i,value){
				 $("#toggle").append(
					'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.offerName+'</h1>'
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
							+'<p> Offer Type : '+value.offerType+'</p>'
							+'<p> Offer Type Id : '+value.offerTypeId+'</p>'
							+'<p> Discount In : '+value.discountIn+'</p>'
							+'<p> Discount Value : '+value.discountValue+'</p>'
							+'<p> Discount Valid Date : '+value.discountValidDate+'</p>'
						+'</div>'
					+'</div>'	 
				 
				 );
			});
         }
	}); 

    
    $("#discountValue").on("keyup input",function(e){
    	if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
    	
    });
     
    $("#offerNameId").on("keyup input",function(e){
    	if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }
    	
    });
    
});


$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});

function editRecord(idValue){
	document.editManageOffers.action = "./editManageOffers";
	document.editManageOffers.id.value = idValue;
	document.editManageOffers.submit();	
}

function deleteRecord(idValue){
	 var x = confirm("Are you sure you want to delete ?");
	 if (x){
		document.editManageOffers.action = "./deleteManageOffers";
		document.editManageOffers.id.value = idValue;
		
		document.editManageOffers.submit();	
	 }
}
