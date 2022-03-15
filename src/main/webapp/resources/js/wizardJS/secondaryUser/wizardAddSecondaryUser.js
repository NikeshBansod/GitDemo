$(document).ready(function(){
		
		$("#userId").blur(function(){
		    var userId = $("#userId").val();
		    if(userId != ''){
		    $.ajax({
		    	url : "checkIfUserNameExists?userName="+userId,
				//method : "GET",
				//contentType : "application/json",
				type : "POST",
				dataType : "json",
				data : { _csrf_token : $("#_csrf_token").val()},
				async : false,
				success : function(json,textStatus,request) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultWizardSessionExpirePage();
						return;
					}
					
					if(isValidToken(json) == 'No'){
						window.location.href = getWizardCsrfErrorPage();
						return;
					}
					if(json == true){
						 $("#userId").addClass("input-error").removeClass("input-correct");
						 $("#userId-back-req").text('User ID - '+userId+' already exists. Try some other User Id.');
						 $("#userId-back-req").show();
						 userexist = true;
						// $("#userId").val("");
						// $("#userId").focus();
					}else{
						
						 userexist = false;
					}	
					setCsrfToken(request.getResponseHeader('_csrf_token'));	
				},
				error: function (data,status,er) {
					 
					getWizardInternalServerErrorPage();   
				}
	        });
		    }
		});
		
		
		$("#contactNo").blur(function(){
		    var mobileNo = $("#contactNo").val();
		    if(mobileNo != ''){
		    $.ajax({
				url : "checkIfUserMobileNoExists",
				//method : "GET",
				//contentType : "application/json",
				type : "POST",
				dataType : "json",
				data : {mobileNo:mobileNo, _csrf_token : $("#_csrf_token").val()},
				async : false,
				success : function(json,textStatus,request) {
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultWizardSessionExpirePage();
						return;
					}
					
					if(isValidToken(json) == 'No'){
						window.location.href = getWizardCsrfErrorPage();
						return;
					}
					if(json == true){
						 $("#contactNo").addClass("input-error").removeClass("input-correct");
						 $("#contactNo-exist").text('Mobile No - '+mobileNo+' already exists. Try some other Mobile No.');
						 $("#contactNo-exist").show();
						 userMobileNoExist = true;
						// $("#userId").val("");
						// $("#userId").focus();
					}else{
						 $("#contactNo").addClass("input-correct").removeClass("input-error");
						 $("#contactNo-exist").hide();
						 userMobileNoExist = false;
					}	
					setCsrfToken(request.getResponseHeader('_csrf_token'));	
				},
				error: function (data,status,er) {
					 
					getWizardInternalServerErrorPage();   
				}
	        });
		    }
		   
		}); 
		
		var pwdRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/ ;
		$("#password").blur(function(){
		    var password = $("#password").val();
		    if(pwdRegex.test(password) == false){
				$("#password-format").show();			
				$("#password").addClass("input-error").removeClass("input-correct");	
			} 
		    
		    if(pwdRegex.test(password)){
				$("#password-format").hide();
				$("#password").addClass("input-correct").removeClass("input-error");
			}
		});
		
	});