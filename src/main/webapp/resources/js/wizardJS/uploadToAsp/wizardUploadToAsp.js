$(document).ready(function(){
	
	
	var gstrtypedata= $("#gstrtype").val();
	var gstinId=$("#gstinId").val();
	var financialPeriod=$("#financialPeriod").val();
	var goback = $("#goback").val();
		
    if(goback == "goback"){
           //alert("onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);
    	var backgstrtypedata=$("#return_gstrtype").val();
    	var backgstinId=$("#return_gstinId").val();
    	var backfinancialperiod=$("#return_financialperiod").val();  
    	loadFP(backfinancialperiod);
    	loadGstinListRoleWise(backgstinId);
    	loadGSTR_TYPE(backgstrtypedata);
    	if(backgstrtypedata=="GSTR-1")
    		{
    			
    		$("#gstr1").show();
    		/*$("#proceed").hide();*/
    		}
    		else
    		{
    			$("#gstr1").hide();
    			$("#gstr3").show();
    			
    		}
    }
    else{
    
    loadFP(financialPeriod);
	loadGstinListRoleWise(gstinId);
	loadGSTR_TYPE(gstrtypedata);
    }	
		
	
    $('#gstrtype').on('change', function() {
    	  var value = $(this).val();
    	  
    	 /* $("#proceed").click();*/
    	});
	
	$("#proceed").click(function (e){
		
		var gstrtype=false;
		var errGSTIN=false;
		var errFp = false;
		var gstinId=$("#gstinId").val();
		var gstrtypedata= $("#gstrtype").val();
		 
		
	    errGSTIN= validateGSTIN();
		
		errFp = validateFinPeriod();
		errGSTNTYPE= validateGSTINTYPE();
		if(errGSTIN || errFp || errGSTNTYPE){
			e.preventDefault();
		
		
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } 
		else if((errFp)){
			 focusTextBox("financialPeriod");}
		
		 else ((errGSTNTYPE))
			 {
				 focusTextBox("gstrtype"); 
				 
			 }
		 }
		 
		else{
			var getGstinName = checkGstnId(gstinId);
			 
			 if(getGstinName == true ){
		
		if(gstrtypedata=="GSTR-1"){
			/*$("#proceed").hide();*/
			$("#gstr3").hide();
		$("#gstr1").show();
		}
		else if (gstrtypedata="GSTR-3")
		{
			$("#gstr1").hide();
			$("#gstr3").show();
			
		}}
		else{
			bootbox.confirm({
				message :"<form name='saveGstinUId' id='saveGstinUId' method='post'>\
					<div class='account-det'>\
				    <div class='det-row'>\
				    <div class='det-row-col-full '> \
					<span class='text-danger cust-error' id='reg-gstin-id-req'>GSTN Login ID :</span> \
				    <label class='label'>  \
				    <input type='text' id='gstnUserId' class='form-control' name='gstnUserId' />\
				    <div class='label-text color: black;'>GSTN Login ID :</div></label> \
					<span class='text-danger cust-error' id='reg-gstin-id-back-req'></span> \
				    </div> \
				    <div class='det-row-col-full '> \
				    <span class='text-danger cust-error' id='gross-turnover'>Gross Turnover :</span> \
				    <label class='label'>  \
				    <input type='number' id='grossTurnover' class='form-control' name='grossTurnover' />\
				    <div class='label-text '>Gross Turnover :</div></label> \
				    </div> \
				    <div class='det-row-col-full '> \
					<span class='text-danger cust-error' id='current-turnover'>Current Turnover :</span> \
				    <label class='label'>  \
				    <input type='number' id='currentTurnover' class='form-control' name='currentTurnover' />\
				    <div class='label-text '>Current Turnover :</div></label> \
				    </div> \ </div> \ </div> \
					<input type='hidden' name='gstinId' value=''> \
				    </form>", 
				    buttons: {
				        confirm: {
				            label: 'Save'
				        },
				        cancel: {
				            label: 'Cancel'
				        }
				    },
				    callback :function(result, e) {
			        if(result){
			        	var blankMsg="This field is required";
			        	var regMsg = "data should be in proper format";
			        	var currencyRegex =/(?=.)^\$?([0-9]{1,15})?(\.[0-9]{1,2})?$/;
			        	
			        	var gstnUserId = $("#gstnUserId").val();
			        	var grossTurnover = $("#grossTurnover").val();
			        	var currentTurnover = $("#currentTurnover").val();	
			        							        	
			        	var errFlag1 = validateGSTNUserId();
			    		var errFlag2 = validategrossTurnover();
			    		var errFlag3 = validateCurrentTurnover();
			    		
			    		if((errFlag1) || (errFlag2) || (errFlag3)){
			    			e.preventDefault();	
			    		}
			    		
			    		if(errFlag1){
			   			 focusTextBox("gstnUserId");
				   		 } else if(errFlag2){
				   			 focusTextBox("grossTurnover");
				   		 } else if(errFlag3){
				   			 focusTextBox("currentTurnover");
				   		 }
			    		
			    		function validateGSTNUserId(){
			    			errFlag1 = validateTextField("gstnUserId","reg-gstin-id-req",blankMsg);
			    			 
			    			 return errFlag1;
			    		}
			    	 	
			    	 	function validategrossTurnover(){
			    			errFlag2 = validateTextField("grossTurnover","gross-turnover",blankMsg);
			    			 if(!errFlag2){
			    				 errFlag2=validateRegexpressions("grossTurnover","gross-turnover",regMsg,currencyRegex);
			    			 }
			    			 return errFlag2;
			    		}
			    	 	
			    	 	function validateCurrentTurnover(){
			    			errFlag3 = validateTextField("currentTurnover","current-turnover",blankMsg);
			    			 if(!errFlag3){
			    				 errFlag3=validateRegexpressions("currentTurnover","current-turnover",regMsg,currencyRegex);
			    			 }
			    			 return errFlag3;
			    		}
			    	 	
			    	 	 $.ajax({
			 				url : "saveGstinUserId",
			 				type : "POST",
			 				dataType : "json",
			 				data : {gstinId : gstinId, gstnUserId : gstnUserId, grossTurnover : grossTurnover, currentTurnover : currentTurnover },
			 				headers: {_csrf_token : $("#_csrf_token").val()},
			 				async : false,
			 				success : function(jsonVal,fTextStatus,fRequest) {
			 					if (isValidSession(jsonVal) == 'No') {
			 						window.location.href = getDefaultSessionExpirePage();
			 						return;
			 					}
			 					if(isValidToken(jsonVal) == 'No'){
			 						window.location.href = getCsrfErrorPage();
			 						return;
			 					}
			 					
			 					if(jsonVal == "SUCCESS"){
			 						
			 						if(gstrtypedata=="GSTR-1"){
			 							$("#gstr3").hide();
			 							$("#gstr1").show();
			 							}
			 							else 
			 							{
			 								$("#gstr1").hide();
			 								$("#gstr3").show();
			 							}
			 					}
			 				}
			 				
			    	 	 });
			    	 	
			     //   	document.saveGstinUId.action = "./saveGstinUserId";
			     //   	document.saveGstinUId.gstinId.value = gstinId;
			     //   	document.saveGstinUId.submit();
			        } 
			    		},
			    closeButton : false
	});
			
		
		}
		}
		 
		
		
		
		
	});
	
	$("#userLogin").click(function (e){
		
		var errGSTIN=false;
		var errFp = false;
		
		errGSTIN= validateGSTIN();
		
		errFp = validateFinPeriod();
		
		if(errGSTIN || errFp){
			e.preventDefault();
		}
		
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else if((errFp)){
			 focusTextBox("financialPeriod");
		 } else{
		
		
		$("#userLogin").attr("disabled", "disabled");
		$("#userLogin").text('Uploading');
		 document.getElementById("uploadAsp").action = "./wUploadInvoicesManual";
		$("#uploadAsp").submit();
	}
	});
	
	$("#L0").click(function (e){
		var errGSTIN=false;
		var errFp = false;
		var gstinId=$("#gstinId").val();
		var financialPeriod=$("#financialPeriod").val();
		var button="draft";
		
		errGSTIN= validateGSTIN();
		
		errFp = validateFinPeriod();
		if(errGSTIN || errFp){
			e.preventDefault();
		}
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else if((errFp)){
			 focusTextBox("financialPeriod");
		 }
		 else{
var getuploadtojiogststatusvalue = getuploadtojiogststatus(gstinId,financialPeriod,button);
			 
			 if(getuploadtojiogststatusvalue == 'successfullyuploaded'){
			 $("#L0").attr("disabled", "disabled");
				$("#L0").text('Getting Data');
				/*$("#proceed").hide();*/
			 document.getElementById("uploadAsp").action = "./wgetL0ResponsePage";
			 $("#uploadAsp").submit();
		 }
		 }
			
	});
	
	$("#submit").click(function (e){
		var errGSTIN=false;
		var errFp = false;
		var gstinId=$("#gstinId").val();
		var financialPeriod=$("#financialPeriod").val();
		var button="submit";
		errGSTIN= validateGSTIN();
		
		errFp = validateFinPeriod();
		if(errGSTIN || errFp){
			e.preventDefault();
		}
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else if((errFp)){
			 focusTextBox("financialPeriod");
		 }
		 else{
var getuploadtojiogststatusvalue = getuploadtojiogststatus(gstinId,financialPeriod,button);
			 
			 if(getuploadtojiogststatusvalue == 'successfullyuploaded'){
			 $("#submit").attr("disabled", "disabled");
				$("#submit").text('Getting Data');
				/*$("#proceed").hide();*/
			 document.getElementById("uploadAsp").action = "./wgetsubmitpage";
			 $("#uploadAsp").submit();
			 }
		 }
			
	});

	$("#File").click(function (e){
		var errGSTIN=false;
		var errFp = false;
		var gstinId=$("#gstinId").val();
		var financialPeriod=$("#financialPeriod").val();
		var button="file";
		errGSTIN= validateGSTIN();
		
		errFp = validateFinPeriod();
		if(errGSTIN || errFp){
			e.preventDefault();
		}
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else if((errFp)){
			 focusTextBox("financialPeriod");
		 }
		 else{
var getuploadtojiogststatusvalue = getuploadtojiogststatus(gstinId,financialPeriod,button);
			 
			 if(getuploadtojiogststatusvalue == 'successfullyuploaded'){
			 $("#File").attr("disabled", "disabled");
				$("#File").text('Getting Data');
				/*$("#proceed").hide();*/
			 document.getElementById("uploadAsp").action = "./wgetfilepage";
			 $("#uploadAsp").submit();
		 }
		 }
	});

	
	function validateGSTIN(){
		errGSTIN = validateSelectField("gstinId","selectUser-req");
		 return errGSTIN;

	} 
	
	function validateFinPeriod(){
		errGSTIN = validateSelectField("financialPeriod","master-desc");
		 return errGSTIN;

	} 
	function validateGSTINTYPE(){
		errGSTINTYPE = validateSelectField("gstrtype","gsttype");
		 return errGSTINTYPE;

	} 
		  	
});



function loadGstinListRoleWise(selectedgstin){
	
    $.ajax({
		url : "getGSTINListAsPerRole",
		method : "get",
	//	contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			$("#gstinId").append('<option value="">Select your GSTIN</option>');
			$.each(json,function(i,value) 
					{
				if(selectedgstin == value.gstinNo){
                    $('#gstinId').append($('<option>').text(value.gstinNo).attr('value', value.gstinNo).attr('selected','selected')); 
            }
				
			else{$("#gstinId").append($('<option>').text(value.gstinNo).attr('value',value.gstinNo));}
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         }
	}); 
	
}

$(document).ready(function(){
	
	$("#financialPeriod").change(function(){
		 var gstin = $("#gstinId").val();
		 var financialPeriod = $("#financialPeriod").val();
		if(gstin != null && gstin != ""){
			
			historyData(gstin,financialPeriod);
		}
	});		
	
	$("#gstinId").change(function(){
		 var financialPeriod = $("#financialPeriod");
		 var gstin = $("#gstinId").val();
			 
			if(financialPeriod.val() != "" && financialPeriod.val() != null){
				
				historyData(gstin,financialPeriod);
			}
	});
	
	
});

function historyData(gstin,financialPeriod){
	
	$.ajax({
		url : "lastUploadedGstinData",
		type : "POST",
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		data : {gstin : gstin,financialPeriod:financialPeriod},
		async : false,
		success : function(json,fTextStatus,fRequest) {
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			 //if(json.length == 0){
			if($.isEmptyObject(json,fTextStatus,fRequest)){
				 bootbox.alert('No data uploaded for selected GSTIN');
			 } else {
				 bootbox.alert('Data last uploaded to JioGST on '+json.uploadDate);
				 if(json.uploadType == 'Auto'){
					 bootbox.alert('You have set Auto Upload setting for this GSTIN. To still Upload the data Manually change your settings');
					 $("#userLogin").attr("disabled", "disabled");
				 } else {
					 $("#userLogin").removeAttr("disabled");
				 }
		}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
		},
		error: function (data,status,er) {
			 
			 getWizardInternalServerErrorPage();   
		}
    });
}

function loadFP(selectedfp){
	
    $.ajax({
		url : "getFpMonthsArray",
		method : "POST",
	//	contentType : "application/json",
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}

			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}

			$("#financialPeriod").append('<option value="">Select </option>');
			$.each(json,function(key,value) {
				if(selectedfp == value){
                    $('#financialPeriod').append($('<option>').text(key).attr('value', value).attr('selected','selected')); 
            }
				else{$("#financialPeriod").append($('<option>').text(key).attr('value',value));}
			});
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
         error: function (data,status,er) {
        	 
        	 getWizardInternalServerErrorPage();   
        }
	}); 
	
}
function loadGSTR_TYPE(selectedgstrtype){
	
    $.ajax({
		url : "getGstrtype",
		method : "POST",
	//	contentType : "application/json",
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
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
			 $("#gstrtype").empty();
			$("#gstrtype").append('<option value="">Select </option>');
			$.each(json,function(i,value) {
				if(selectedgstrtype == value){
                    $('#gstrtype').append($('<option>').text(value).attr('value', value).attr('selected','selected')); 
            }
				else{$("#gstrtype").append($('<option>').text(value).attr('value',value));}
			});
			
		
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }

	}); 
    
}
    function getuploadtojiogststatus(gstinId,financialPeriod,button){
    	var status='';
    	$.ajax({
    		url : "wgetuploadtojiogststatus",
    		type : "POST",	
    		dataType : "json",
    		headers: {_csrf_token : $("#_csrf_token").val()},
    		data : {gstin: gstinId , financialPeriod:financialPeriod,button:button},
    		async : false,
    		success : function(json,fTextStatus,fRequest) {
    			if (isValidSession(json) == 'No') {
    				window.location.href = getDefaultSessionExpirePage();
    				return;
    			}

    			if(isValidToken(json) == 'No'){
    				window.location.href = getCsrfErrorPage();
    				return;
    			}
    			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
    			 //if(json.length == 0){
    			/*if($.isEmptyObject(json)){
    				 bootbox.alert('Please upload the data to Jio GST.');
    			 }*/
    			console.log(json)
    			
    			/*if(json.UploadToJiogst==0&&json.SaveToGstn==0&&json.SubmitToGstn==0)
    				{
    				status='successfullyuploaded';
    				
    				}*/
    			 if(button=="draft")
    				{
    				 if ( json == null )
						{
						bootbox.alert('No Invoices for this Financial Period');
						}
    				 else{
    				 if(json.UploadToJiogst==0)
    					 {
    					 status='successfullyuploaded';
    					 }
    				 else
    				 {
    					
    					
    					 bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices to JioGST before saving data to GSTN.');
    					 

    				 }
    				}
    				}
    			
    			
    			 else if(button=="submit")
    			 {
    				 if ( json == null )
						{
    					 bootbox.alert('No Invoices for this Financial Period');
						}
    				 else{
    				 if(json.UploadToJiogst==0 &&json.SaveToGstn==0)
					 {
					 status='successfullyuploaded';
					 }
				 else
				 {
					 if(json.UploadToJiogst==0)
						 {
						 bootbox.alert('Please save the remaining  '+json.SaveToGstn+' invoices to GSTN. ');
						 }
					 else{
						 if ( json == null )
 						{
							 bootbox.alert('No Invoices for this Financial Period');
 						}
						 else{
					 bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices to JioGST before saving data to GSTN  ');
						 }
					 }
				 } 
    			 } 
    			 }
    			
    			
    			 else if(button=="file")
    			 {
    				 if ( json == null )
						{
    					 bootbox.alert('No Invoices for this Financial Period');
						}
    				 else{
    				 if(json.UploadToJiogst==0 &&json.SubmitToGstn==0&&json.SaveToGstn==0)
					 {
					 status='successfullyuploaded';
					 } 
    				 else
    				 {
    					 if(json.UploadToJiogst==0)
						 {
    						 if(json.SaveToGstn==0)
    							 {
						 bootbox.alert('Please submit the remaining  ' +json.SubmitToGstn+' invoices to GSTN.');
    							 }
    						 else
    						 {
    							 bootbox.alert('Please save the remaining  '+json.SaveToGstn+' invoices to GSTN. '); 
    						 }
						 } 
    					 else
    					 {
    						 if ( json == null )
     						{
    							 bootbox.alert('No Invoices for this Financial Period');
     						}
    						 else{
    						 bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices to JioGST before saving data to GSTN.  '); 
    						 }
    					 }
    				 }
    				 
    			 }
    			 }
    			
    			/*if(json[0]=="0")
    				{
    				
    				status='successfullyuploaded';
    				}
    			else {
    				 bootbox.alert('Data has been '+json.actionTaken+' on '+json.uploadDate+' for '+json.fpPeriod);
    				if(json[1]=="UploadToJiogst")
    					{
    				bootbox.alert('Please upload '+json[0]+' invoices to Jio GST.');
    					}
    				else if(json[1]=="SaveToGstn")
    					{
    					bootbox.alert('Please Click on Draft and save '+json[0]+' invoices to Gstn.');
    					
    					}
    				else if(json[1]=="SubmitToGstn")
					{
					bootbox.alert('Please Click on Submit and submit remaining '+json[0]+' invoices to Gstn.');
					
					}
    				
    				
    				 
    				 if(json.uploadType == 'Auto'){
    					 bootbox.alert('You have set Auto Upload setting for this GSTIN. To still Upload the data Manually change your settings');
    					 $("#userLogin").attr("disabled", "disabled");
    				 } else {
    					 $("#userLogin").removeAttr("disabled");
    				 }
    		}*/
    			
    		},
    		error: function (data,status,er) {
    			 
    			 getInternalServerErrorPage();   
    		}
    		
        });
    	return status;
    	
    }
	
    function checkGstnId(gstinId){
    	var status='';
    	$.ajax({
    		url : "getGstinDetailsFromGstinNo",
    		type : "POST",	
    		dataType : "json",
    		headers: {_csrf_token : $("#_csrf_token").val()},
    		data : {gstinNo: gstinId},
    		async : false,
    		success : function(json,fTextStatus,fRequest) {
    			if (isValidSession(json) == 'No') {
    				window.location.href = getDefaultSessionExpirePage();
    				return;
    			}

    			if(isValidToken(json) == 'No'){
    				window.location.href = getCsrfErrorPage();
    				return;
    			}
    			
    			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

    			status = json.isGstnUId;
    			
    		},
    		error: function (data,status,er) {
    			 
    			 getInternalServerErrorPage();   
    		}
    		
        });
    	return status;
    	
    }