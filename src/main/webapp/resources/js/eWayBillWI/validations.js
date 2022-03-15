var blankMsg = "This field is required";

$(document).ready(function(){
	
	var clientId = $("#clientId").val(); 
	var secretKey = $("#secretKey").val(); 
	var userId = $("#userId").val();
	var appCode = $("#appCode").val();
	var ipUsr = $("#ipUsr").val();
	var ewaybillId = $("#uiEWayBillId").val();
	
	$("#submitId").click(function(e){
		   
		 var errorStatus = true; 
		 var errFlag1 = validateTextField("nicUserId","nicUserId-csv-id",blankMsg);
		 var errFlag2 = validateTextField("nicPwd","nicPwd-csv-id",blankMsg);
		 var errFlag3 = validateKmsToBeTravelled("kmsToBeTravelled","kmsToBeTravelled-csv-id",blankMsg);//validateTextField("kmsToBeTravelled","kmsToBeTravelled-csv-id",blankMsg);
		 var errFlag4 = validateSelect("modeOfTransport","modeOfTransport-csv-id");
		 var errFlag5 = false;
		 var errFlag6 = false;
		 var errFlag7 = false;
		 var errFlag8 = false;
		 		 		 
		 if ((errFlag1) || (errFlag2) || (errFlag3) || (errFlag4) || (errFlag5) || (errFlag6) || (errFlag7) || (errFlag8)){
				e.preventDefault();	 
		 }else{
			   errorStatus = false;
		 }
		 
		 if(!errorStatus){
			 
			 bootbox.confirm({
				    message: "Do you want to generate E-Way Bill ?",
				    buttons: {
				        confirm: {
				            label: 'Yes',
				            className: 'btn-success'
				        },
				        cancel: {
				            label: 'No',
				            className: 'btn-danger'
				        }
				    },
				    callback: function (result) {
				    	 if (result){ 
				    		 $('.btn-success').prop("disabled", true);
							   $('#loadingmessage').show();
							   $('#mainPg1').hide();
							   var inputData = generateInputJson();
							   
							   $.ajax({
									url : "addEwayBillPost",
									method : "post",
									data : JSON.stringify(inputData),
									contentType : "application/json",
									dataType : "json",
									headers: {_csrf_token : $("#_csrf_token").val()},
									async : false,
									success:function(json,fTextStatus,fRequest){
										if (isValidSession(json) == 'No') {
											window.location.href = getDefaultSessionExpirePage();
											return;
										}

										if(isValidToken(json) == 'No'){
											window.location.href = getCsrfErrorPage();
											return;
										}
										
										if(json.status == 'success'){
											bootbox.alert("Successfully generated EWay Bill No - "+json.ewayBillNo, function() {
												$('#loadingmessage').hide(); // hide the loading message
												//location.reload();
												//window.location.href = 'home#invoice';
												getEWayBills(inputData.invoiceId);
												return;
											});
										}
										
										if(json.status == 'failure'){
											bootbox.alert(json.error_desc);
											$('#loadingmessage').hide(); // hide the loading message
											 $('#mainPg1').show();
										}
										
										setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
							         },
							         error: function (data,status,er) {
							        	 
							        	 getInternalServerErrorPage();   
							        }
								}); 
							 }
				    }
				});
			 
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
				
				$("#gstnStateId").empty();
				if(json.length == 1){
					$("#gstnStateIdDiv").show();
					$.each(json,function(i,value) {
						$("#gstnStateId").append($('<option>').text(value.gstinNo).attr('value',value.gstinNo).attr('selected','selected'));
						setDefaultNICId($("#gstnStateId").val(), userId);	
					});

				}else{
					$("#gstnStateIdDiv").show();
					$("#gstnStateId").append('<option value="">Select your GSTIN</option>');
					$.each(json,function(i,value) {
						$("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.gstinNo));
					});
				}
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
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
						window.location.href = getDefaultSessionExpirePage();
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
		        	
		         getInternalServerErrorPage();   
		        }
			});	
		 
	 }	
	 
	 
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
						window.location.href = getDefaultSessionExpirePage();
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
		        	 $('#loadingmessage').hide();
		        	 $('#mainPg1').show();
		        //	 getInternalServerErrorPage();   
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

	
	$("#cancelId").click(function(e){
		   
		 var errorStatus = true; 
		 var errFlag1 = validateTextField("nicUserId","nicUserId-csv-id",blankMsg);
		 var errFlag2 = validateTextField("nicPwd","nicPwd-csv-id",blankMsg);
		 var errFlag3 = validateTextField("remarks","remarksId",blankMsg);
		 
		 if ((errFlag1) || (errFlag2) || (errFlag3)){
				e.preventDefault();	 
		 }else{
			   errorStatus = false;
		 }
		 
		 if(!errorStatus){
			 $('#loadingmessage').show();
			 $('#mainPg1').hide();
			 var nicUserId = $("#nicUserId").val();
			 var nicPwd = $("#nicPwd").val();
			 var gstn = $("#uiGstnNo").val();
			 var remarks = $("#remarks").val();
			 var ewaybillno = $("#uiEWayBillNo").val();
			 
			 
			 var inputData = {
					 "userId" : userId,
					 "ewaybillno" : ewaybillno,
					 "gstin" : gstn,
					 "nic_id" : nicUserId,
					 "password" : nicPwd,
					 "cancelRmrk" : remarks
					 
			};
			 
			 var data = JSON.stringify(inputData);
			 
		 $.ajax({
				url : "ewaybill/cancelGeneratedEwayBill",
				type : "POST",
				contentType : "application/json",
				headers: {client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr},
				data : data,
				dataType : "json",
				async : false,
				success:function(json,fTextStatus,fRequest){
					if (isValidSession(json) == 'No') {
						window.location.href = getDefaultSessionExpirePage();
						return;
					}				
					
					if(json.status == 'success'){
						bootbox.alert("E-Way Bill Cancelled successfully" , function() {
							$('#loadingmessage').hide();
							$('#mainPg1').hide();
							window.location.href = "./getGenericEWayBills";
							return;
						});
						
					}
					
					if(json.status == 'failure'){
						
						bootbox.alert("Cancellation of E-way bill failed" , function() {
							$('#loadingmessage').hide();
							$('#mainPg1').show();
							window.location.href = "./loginGenerateEwayBill";
							return;
						});
						
					}
					
		         },
		         error: function (data,status,er) {
		        	 $('#loadingmessage').hide();
						$('#mainPg1').show();
						window.location.href = "./loginGenerateEwayBill";
						return;
		        }
			});
			 
		 }
		 
	});
	
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

