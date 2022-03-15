function isValidSession(sessionVal){
	
	var result='Yes';
	if(sessionVal && sessionVal.isSessionValid){
		
		if(sessionVal.isSessionValid=='No'){
			result='No';
		}
	}
	return result;
}

function getWizardCustomLogoutPage(){
	return 'logout';
} 

function callWizardInvoiceHistoryPage(){
	return 'getMyInvoices';
}

function getDefaultWizardSessionExpirePage(){
	return 'login';
}

function getDefaultSessionExpirePage(){
	return 'login';
}

function getWizardCsrfErrorPage(){
	return 'tError';
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

function getWizardInternalServerErrorPage(){
	bootbox.alert("SOMETHING WENT WRONG.");
	//return 'home';
}
