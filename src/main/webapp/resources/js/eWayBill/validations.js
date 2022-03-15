var blankMsg = "This field is required";

$(document).ready(function(){
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
		 
		 if(!errFlag4){
			 var modeOfTransport = $("#modeOfTransport option:selected").text();
			 
			 if(modeOfTransport == "Road"){
				 errFlag5 = validateTextField("vehicleNo","vehicleNo-csv-id",blankMsg);
				 //errFlag6 = validateTextField("transporterName","transporterName-csv-id",blankMsg);
				 //errFlag7 = validateTextField("docNo","docNo-csv-id",blankMsg);
				 //errFlag8 = validateTextField("transporterId","transporterId-csv-id",blankMsg);
			 }else if(modeOfTransport == "Rail" || modeOfTransport == "Air" || modeOfTransport == "Ship"){
				 errFlag7 = validateTextField("docNo","docNo-csv-id",blankMsg);
			 }
				 
			 
		 }
		 
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
			 
			 /*bootbox.confirm("Do you want to generate E-Way Bill ?", function(result){
				 if (result){ 
				   $('#loadingmessage').show();
				   $('#mainPg1').hide();
				   var inputData = generateInputJson();
				   
				   $.ajax({
						url : "addEwayBillPost",
						method : "post",
						data : JSON.stringify(inputData),
						contentType : "application/json",
						dataType : "json",
						async : true,
						success:function(json){
							if(json.status == 'success'){
								bootbox.alert("Successfully generated EWay Bill No - "+json.ewayBillNo, function() {
									$('#loadingmessage').hide(); // hide the loading message
									//location.reload();
									window.location.href = 'home#invoice';
									return;
								});
							}
							
							if(json.status == 'failure'){
								bootbox.alert(json.error_desc);
								$('#loadingmessage').hide(); // hide the loading message
								 $('#mainPg1').show();
							}
				         },
			            error:function(data,status,er) { 
			                    //alert("error: "+data+" status: "+status+" er:"+er);
			             }
					}); 
				 }//end if result  
			   });//end confirm 
*/		 }
		   
		
	});
	
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
	
	$("#kmsToBeTravelled").on("keyup click", function(){
		if ($("#kmsToBeTravelled").val().length > 0){
			 $("#kmsToBeTravelled").addClass("input-correct").removeClass("input-error");
			 $("#kmsToBeTravelled-csv-id").hide();		 
		}
		
		if (currencyRegex.test($("#kmsToBeTravelled").val())!=true){
			 $("#kmsToBeTravelled").addClass("input-error").removeClass("input-correct");
			 $("#kmsToBeTravelled-csv-id").show();		 
		}
		
		if (currencyRegex.test($("#kmsToBeTravelled").val())==true){
			 $("#kmsToBeTravelled").addClass("input-correct").removeClass("input-error");
			 $("#kmsToBeTravelled-csv-id").hide();		 
		}

		if (($("#kmsToBeTravelled").val().length < 1) || ($("#kmsToBeTravelled").val() <= 0)){
			 $("#kmsToBeTravelled").addClass("input-error").removeClass("input-correct");
			 $("#kmsToBeTravelled-csv-id").text('This field is required');	
			 $("#kmsToBeTravelled-csv-id").show();	
			 $("#kmsToBeTravelled").focus();
		}
	});
	
	$("#modeOfTransport").on("change", function(){
		if ($("#modeOfTransport").val() === ""){
			$("#modeOfTransport").addClass("input-error").removeClass("input-correct");
			$("#modeOfTransport-csv-id").show();
		}
		if ($("#modeOfTransport").val() !== ""){
			$("#modeOfTransport").addClass("input-correct").removeClass("input-error");
			$("#modeOfTransport-csv-id").hide();
		}
	});
	
	$("#vehicleNo").on("keyup input",function(e){
		/*if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9\s]*$/, '');*/
		if ($("#vehicleNo").val().length > 2){
			 $("#vehicleNo").addClass("input-correct").removeClass("input-error");
			 $("#vehicleNo-csv-id").hide();
			 
		}
		if ($("#vehicleNo").val().length < 2){
			 $("#vehicleNo").addClass("input-error").removeClass("input-correct");
			 $("#vehicleNo-csv-id").show();
		}	
	});
	
	/*$("#transporterName").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9\s]*$/, '');
		if ($("#transporterName").val().length > 2){
			 $("#transporterName").addClass("input-correct").removeClass("input-error");
			 $("#transporterName-csv-id").hide();
			 
		}
		if ($("#transporterName").val().length < 2){
			 $("#transporterName").addClass("input-error").removeClass("input-correct");
			 $("#transporterName-csv-id").show();
		}	
	});
	*/
	$("#docNo").on("keyup input",function(e){
		/*if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9\s]*$/, '');*/
		if ($("#docNo").val().length > 2){
			 $("#docNo").addClass("input-correct").removeClass("input-error");
			 $("#docNo-csv-id").hide();
			 
		}
		if ($("#docNo").val().length < 2){
			 $("#docNo").addClass("input-error").removeClass("input-correct");
			 $("#docNo-csv-id").show();
		}	
	});
	
/*	$("#transporterId").on("keyup input",function(e){
		if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		}
		this.value = this.value.replace(/[\\[]*$/, '');
		this.value = this.value.replace(/[^[a-zA-Z0-9\s]*$/, '');
		if ($("#transporterId").val().length > 2){
			 $("#transporterId").addClass("input-correct").removeClass("input-error");
			 $("#transporterId-csv-id").hide();
			 
		}
		if ($("#transporterId").val().length < 2){
			 $("#transporterId").addClass("input-error").removeClass("input-correct");
			 $("#transporterId-csv-id").show();
		}	
	});
	*/
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
			 var nicUserId = $("#nicUserId").val();
			 var nicPwd = $("#nicPwd").val();
			 //var gstn = $("#uiGstnX").val();
			// var invoiceId = $("#uiInvoiceId").val();
			 var uiInvoice = $("#uiInvoice").val();
			// alert(parseFloat(uiInvoice));
			 var isCheckNicUserId = $('#isCheckNicUserId').is(':checked')? 'Yes': 'No'; 
			 var inputData = {
					 "nic_id" : nicUserId,
					 "nicPwd" : nicPwd,
					 "invoiceId" : parseFloat(uiInvoice),
					 "isCheckNicUserId" : isCheckNicUserId
			};
			 
		 $.ajax({
				url : "eWayBillOnBoarding",
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
						$("#openCloseDiv").show();
						$("#validateId").hide();
						$('#mainPg1').show();
						$('#loadingmessage').hide();
						
						//set nic userid and password disabled
						$("#nicUserIdDiv").css('display','none');
						$("#nicPwdDiv").css('display','none');
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
		 
	});
	
	
	$("#cancelId").click(function(e){
		   
		 var errorStatus = true; 
		 var errFlag1 = validateTextField("nicUserId","nicUserId-csv-id",blankMsg);
		 var errFlag2 = validateTextField("nicPwd","nicPwd-csv-id",blankMsg);
		 var errFlag3 = validateTextField("remarks","remarks",blankMsg);
		 
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
			 
			 var invoiceId = $("#uiInvoiceId").val();
			 
			 var inputData = {
					 "ewaybillNo" : ewaybillno,
					 "gstin" : gstn,
					 "nic_id" : nicUserId,
					 "nicPwd" : nicPwd,
					 "cancelRmrk" : remarks
					 
			};
			 
			 var data = JSON.stringify(inputData);
			 
		 $.ajax({
				url : "cancelEWayBill",
				method : "post",
				data : data,
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
						bootbox.alert("E-Way Bill Cancelled successfully" , function() {
							$('#loadingmessage').hide();
							$('#mainPg1').hide();
							getEWayBills(invoiceId);
							return;
						});
						
					}
					
					if(json.status == 'failure'){
						
						bootbox.alert("Cancellation of E-way bill failed" , function() {
							$('#loadingmessage').hide();
							$('#mainPg1').show();
							getEWayBills(invoiceId);
							return;
						});
						
					}
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					
		         },
		         error: function (data,status,er) {
		        	 setCsrfToken(er.getResponseHeader('_csrf_token'));
		        	 getInternalServerErrorPage();   
		        	 $('#loadingmessage').hide();
						$('#mainPg1').show();
						getEWayBills(invoiceId);
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

function validateKmsToBeTravelled(id, spanid, msg) {
	var result = false;
	if((currencyRegex.test($("#" + id).val())!=true) || ($("#kmsToBeTravelled").val().length < 1) || ($("#kmsToBeTravelled").val() <= 0)){

		$("#" + id).addClass("input-error").removeClass("input-correct");
		$("#" + spanid).text(msg);
		$("#" + spanid).show();
		result = true;
	} else {
		$("#" + id).addClass("input-correct").removeClass("input-error");
		$("#" + spanid).hide();
	}
	
	return result;
}


function getEWayBills(idValue){
	document.manageInvoice.action = "./getEWayBills";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}
