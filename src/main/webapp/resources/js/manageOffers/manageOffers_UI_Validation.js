"use strict;"

function runValidation(){
	bootbox.alert("1");
	if($("#offerNameId").val() === ""){
		bootbox.alert("2", function() {
			$("#offerNameId").css("border","2px solid red");
		});
	}
}

$("#discountValue").blur(function() {
	onblurOfDiscountType();     
}); 

function onblurOfDiscountType(){
	var discountType = $('input[name=discountIn]').filter(':checked').val(); 
	
	var discountValue = $("#discountValue").val();
	if(discountType == 'Percentage'){
	
		if(discountValue >= 100){
			bootbox.alert("Discount type is Percentage so Discount value cannot be greater than 100", function() {
				$("#discountValue").val("");
				$("#discountValue").focus();
			});
		}
	} 
	
}