var blankMsg = "This field is required";

var clientId = $("#clientId").val(); 
var secretKey = $("#secretKey").val(); 
var userId = $("#userId").val();
var appCode = $("#appCode").val();
var ipUsr = $("#ipUsr").val();


$(document).ready(function(){		
	$("#nicUserId").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9]*$/, '');
		if ($("#nicUserId").val().length > 2){
			 $("#nicUserId").addClass("input-correct").removeClass("input-error");
			 $("#nicUserId-csv-id").hide();			 
		}
		if ($("#nicUserId").val().length < 2){
			 $("#nicUserId").addClass("input-error").removeClass("input-correct");
			 $("#nicUserId-csv-id").show();
		}	
	});
	
	$("#nicPwd").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		}
		if ($("#nicPwd").val().length > 2){
			 $("#nicPwd").addClass("input-correct").removeClass("input-error");
			 $("#nicPwd-csv-id").hide();			 
		}
		if ($("#nicPwd").val().length < 2){
			 $("#nicPwd").addClass("input-error").removeClass("input-correct");
			 $("#nicPwd-csv-id").show();
		}	
	});
	
	
	 $.ajax({
			url : "getGSTINListAsPerRole",
			method : "get",
			contentType : "application/json",
			dataType : "json",
			async : false,
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}				
				/*if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}*/				
				$("#gstnStateId").empty();
				if(json.length == 1){
					$("#gstnStateIdDiv").show();
					$.each(json,function(i,value) {
						$("#gstnStateId").append($('<option>').text(value.gstinNo).attr('value',value.gstinNo).attr('selected','selected'));
						setDefaultNICId($("#gstnStateId").val(), userId);	
					});
				}else{
					$("#gstnStateIdDiv").show();
					$("#gstnStateId").append('<option value="">Select</option>');
					$.each(json,function(i,value) {
						$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.gstinNo));
					});
				}
				//setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
	         },
	         error: function (data,status,er) {	        	 
	        	 getWizardInternalServerErrorPage();     
	         }
		}); 
	 

	 $("#gstnStateId").blur(function(){
		 var gstnStateId = $("#gstnStateId").val();
		 setDefaultNICId(gstnStateId,userId);		 
	 });
	 
	 
 function setDefaultNICId(gstin,userId){
		 
		 var inputData = {
					"gstin" : gstin,
					"userId" : userId
			};
		 
		 $.ajax({
				url : "ewaybill/getEwayBillCustomerOnboardedList",
				type : "POST",
				data : JSON.stringify(inputData),
				contentType : "application/json",
				dataType : "json",
				headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
				async : false,
				success:function(json,fTextStatus,fRequest){
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultWizardSessionExpirePage();
						return;
					}
					
					if(json.status == 'success'){
						$('#nicUserId').val(json.nicId);
						$('#nicUserId').prop('disabled', true);
						
					}
					
					if(json.status == 'failure'){
						bootbox.alert(json.error_desc);
						
					}
		         },
		         error: function (data,status,er) {
		        	
		        	 getWizardInternalServerErrorPage();   
		        }
			});	
		 
	 }
 
	 
	$("#validateId").click(function(e){		   
		 var errorStatus = true; 
		 var errFlag1 = validateTextField("nicUserId","nicUserId-csv-id",blankMsg);
		 var errFlag2 = validateTextField("nicPwd","nicPwd-csv-id",blankMsg);		 
		 if ((errFlag1) || (errFlag2)){
				e.preventDefault();	 
		 }else{
			   errorStatus = false;
		 }
		 
		 if(!errorStatus){
			 $('#loadingmessage').show();
			 $('#mainPg1').hide();
			 $('#originalHeader').hide();
			 var gstin = $("#gstnStateId").val();
			 var nicUserId = $("#nicUserId").val();
			 var nicPwd = $("#nicPwd").val();
			 var mobile_number = $("#mobile_number").val();
			 var email_id = $("#email_id").val();
			 var mobile_number = $("#mobile_number").val();
             var email_id = $("#email_id").val();
             var isCheckNicUserId = $('#isCheckNicUserId').is(':checked')? 'Yes': 'No'; 
			 
			
			 var inputData = {
						"gstin" : gstin,
						 "nic_id" : nicUserId,
						 "mobile_number" : mobile_number,
						 "email_id" : email_id,
						 "password" : nicPwd,
						 "userId" : userId
				};
			 
		 $.ajax({
				url : "ewaybill/getewbcustomerbonboarding",
				type : "POST",
				data : JSON.stringify(inputData),
				contentType : "application/json",
				dataType : "json",
				headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
				async : false,
				success:function(json,fTextStatus,fRequest){
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultWizardSessionExpirePage();
						return;
					}
					if(json.status == 'success'){
						$('#loadingmessage').hide();
						redirectToEWayBillDetailsPage(nicUserId,nicPwd,gstin);
					}
					if(json.status == 'failure'){
						bootbox.alert(json.error_desc);
						$('#loadingmessage').hide(); // hide the loading message
						$('#mainPg1').show();
					}					
		         },
		         error: function (data,status,er) {		        	 
		        	getWizardInternalServerErrorPage();   
		        	$('#loadingmessage').hide(); // hide the loading message
					$('#mainPg1').show();
					$('#originalHeader').show();
		        }
			});
			 
		 }
		 
	});
	
	function redirectToEWayBillDetailsPage(nicUserId,nicPwd,gstin){
		document.manageInvoice.action = "./getWIEwayBillPage";
		document.manageInvoice.nicUserId.value = nicUserId;
		document.manageInvoice.nicPwd.value = nicPwd;
		document.manageInvoice.gstin.value = gstin;
		document.manageInvoice.submit();
	}		
});

function validateSelect(id,spanid){
	if ($("#"+id).val() === ""){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+spanid).show();
		$("#"+id).focus();
		return true;
	}else{
		$("#"+id).addClass("input-correct").removeClass("input-error");
		$("#"+spanid).hide();
		return false;
	}

}


function getWizardEWayBills(idValue){
	document.manageInvoice.action = "./wgetEWayBills";
	document.manageInvoice.id.value = idValue;
	//document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

function backToEWayBillDocs(){	
	window.location.href = "./wizardGetGenericEWayBills";
}
