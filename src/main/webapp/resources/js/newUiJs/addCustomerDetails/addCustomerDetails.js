
$(document).ready(function(){
var custContactExists = false; 
			 $("#contactNo").blur(function(){
				 var custName = $("#custName").val();
				 var custContact = $("#contactNo").val();
			    if(custContact != '' && custName != ''){
			    checkIfCustomerExists(custContact, custName);
			 }
			});
			 
			 $("#custName").blur(function(){
				 var custName = $("#custName").val();
				 var custContact = $("#contactNo").val();
				    if(custContact != '' && custName != ''){
				//    var data={"custContact":custContact, "custName":custName }; 	
				    checkIfCustomerExists(custContact, custName);
				 }
				});
			 //checkIfCustomerExists(custContact, custName);
			 $(".loader").fadeOut("slow");
			
});

function checkIfCustomerExists(custContact, custName){
	 var data={"custContact":custContact, "custName":custName};
	 $.ajax({
			url : "checkIfCustomerExists",
			method : 'POST',
			dataType : "json",
			async : false,
			data : data,
			headers: {_csrf_token : $("#_csrf_token").val()},
			beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },

			success : function(jsonVal,fTextStatus,fRequest) {
				
				if (isValidSession(jsonVal) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(jsonVal) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				if(jsonVal == true){
					 $("#custName").addClass("input-error").removeClass("input-correct");
					 $("#contact-no-req").text('This customer name is already present for given mobile number-('+custContact+')');
					 $("#contact-no-req").show();
					 custContactExists = true;
				}else{
					 $("#custName").addClass("input-correct").removeClass("input-error");
					 $("#contact-no-req").hide();
					 custContactExists = false;
				}
				
				 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
	    }); 
	 
}
