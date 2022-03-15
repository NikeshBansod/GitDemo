var blankMsg = "This field is required";
var clientId = $("#clientId").val(); 
var secretKey = $("#secretKey").val(); 
var userId = $("#userId").val();
var appCode = $("#appCode").val();
var ipUsr = $("#ipUsr").val();
var ewaybillno = $("#uiEWayBillNo").val();

$(document).ready(function(){
    $('.loader').fadeOut("slow");
	 
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
		
	$("#cancelId").click(function(e){
		 $('.loader').show();		   
		 var errorStatus = true; 
		 var errFlag1 = validateTextField("nicUserId","nicUserId-csv-id",blankMsg);
		 var errFlag2 = validateTextField("nicPwd","nicPwd-csv-id",blankMsg);
		 var errFlag3 = validateTextField("remarks","remarksId",blankMsg);
		 
		 if ((errFlag1) || (errFlag2) || (errFlag3)){
		        $('.loader').fadeOut("slow");
				e.preventDefault();	 
		 }else{
			   errorStatus = false;
		 }
		 
		 if(!errorStatus){
			 $('#mainPg1').hide();
			 var nicUserId = $("#nicUserId").val();
			 var nicPwd = $("#nicPwd").val();
			 var gstn = $("#uiGstnNo").val();
			 var remarks = $("#remarks").val();			 
			 
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
					        $('.loader').fadeOut("slow");
							bootbox.alert("E-Way Bill Cancelled successfully" , function() {
								window.location.href = "./getGenericEWayBills";							
								return;
							});						
						}					
						if(json.status == 'failure'){
					        $('.loader').fadeOut("slow");
					        $("#mainPg1").show();
							//"Cancellation of E-way bill failed"
							bootbox.alert(json.error_desc);						
						}					
			         },
			         error: function (data,status,er) {
					     $('.loader').fadeOut("slow");
			        	 getInternalServerErrorPage(); 						
			        }
				});	
		        $('.loader').fadeOut("slow");
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


function gobacktoEwayBillDetailedPage(){
    $('.loader').show(); 
	document.previewInvoice.action = "./getpreviewgenericewaybill";
	document.previewInvoice.id.value = ewaybillno;
	document.previewInvoice.userId.value = userId;
	document.previewInvoice._csrf_token.value = $("#_csrf_token").val();
	document.previewInvoice.submit();
    $('.loader').fadeOut("slow");
}

function redirectToEWayBillDetailsPage(nicUserId,nicPwd,gstin){
    $('.loader').show(); 
	document.manageInvoice.action = "./getEwayBillPage";
	document.manageInvoice.nicUserId.value = nicUserId;
	document.manageInvoice.nicPwd.value = nicPwd;
	document.manageInvoice.gstin.value = gstin;
	document.manageInvoice.submit();
    $('.loader').fadeOut("slow");
}	

