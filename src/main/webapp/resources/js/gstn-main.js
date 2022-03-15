function isValidSession(sessionVal){
	
	var result='Yes';
	if(sessionVal && sessionVal.isSessionValid){
		
		if(sessionVal.isSessionValid=='No'){
			result='No';
		}
	}
	return result;
}

function getDefaultSessionExpirePage(){
	return 'login';
} 

function getCustomLogoutPage(){
	return 'logout';
} 

function callInvoiceHistoryPage(){
	return 'getMyInvoices';
}

function getInternalServerErrorPage(){
	bootbox.alert("SOMETHING WENT WRONG.");
	//return 'home';
}

function getCsrfErrorPage(){
	return 'tError';
}

function isValidToken(sessionVal){
	
	var result='Yes';
	if(sessionVal && sessionVal.isTokenValid){
		
		if(sessionVal.isTokenValid=='Invalid Request'){
			result='No';
		}
	}
	return result;
}