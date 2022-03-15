var gstinUserExist = false;

$(document).ready(function(){
var blankMsg="This field is required";
var selectMsg ="Select atleast one GSTIN";

$("#submitGstinMapping").click(function(e){
	 
	 var errFlag1 = validateGSTIN();
	 var errFlag2 = validateGSTINUser();
	 var errFlag3 = validateMultipleGSTINUser();
	 gstinUserExist = validateDuplicateUser();
	 if ((errFlag1) || (errFlag2) || (errFlag3) || (gstinUserExist)){
		 e.preventDefault();	 
	 } 
	 	
});

function validateGSTIN(){
	errFlag1 = validateSelectField("selectUser","selectUser-req");
	 return errFlag1;

} 

function validateGSTINUser(){
	errFlag2 = validateSelectField("gstinAddressMapping","selectGSTINLocation-req");
	 return errFlag2;

} 

function validateMultipleGSTINUser(){
	errFlag3 = multiSelectValidate("selectGSTINDetail","selectGSTINDetail-req");
	 return errFlag3;
	
}

function validateDuplicateUser(){
	  var referenceUserId = $("#selectUser").val();
	  if(referenceUserId != ''){
	  var refUserName = $('#selectUser option:selected').text();
	  var data={"referenceUserId":referenceUserId}; 	
	   $.ajax({
			url : "checkIfGSTINUserExists",
			method: 'POST',
			dataType : "json",
			data : data,
			async : false,
			success : function(jsonVal) {
				if(jsonVal == true){
					 $("#selectUser").addClass("input-error").removeClass("input-correct");
					 $("#selectUser-req").text('User Name :'+refUserName+' already Exists. Select other user');
					 $("#selectUser-req").show();
					 gstinUserExist = true;
				}else{
					 $("#selectUser").addClass("input-correct").removeClass("input-error");
					 $("#selectUser-req").hide();
					 gstinUserExist = false;
				}
				
			}
	    }); 
	  }
	   return gstinUserExist;
}

});
 
