
$("#name").blur(function(){
	
	var product = $("#name").val();
	if(product == ''){
    var data={"product":product,_csrf_token : $("#_csrf_token").val()};
   $.ajax({
		url : "checkIfProductExists",
		type: 'POST',
		dataType : "json",
		data : data,
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
				 $("#name").addClass("input-error").removeClass("input-correct");
				 $("#prod-name").text('Goods Name  - '+product+' already Exists for your Organization');
				 $("#prod-name").show();
				 productExists = true;
			}else{
				if(product.length > 0){
				 $("#prod-name").addClass("input-correct").removeClass("input-error");
				 $("#prod-name").hide();
				 productExists = false;
				}
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
    }); 
	}
   
});