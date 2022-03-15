var gstinId='';
var financialPeriod='';


$(document).ready(function(){
	$("#gstr-type").hide();
	$(".selectOptions").hide();
	$(".selectOptionsgstr2").hide();
	
	L0_gstin = $("#L0_gstin").val();
	 L0_fp = $("#L0_fp").val();
	 GSTR_type = $("#GSTR_type").val();
	 gobacktoasp = $("#gobacktoasp").val();
	 
	 $("#gstrfileoptions").click(function (e){
		 gstinId = $("#gstinId").val();
		 financialPeriod = $("#financialPeriod").val();
			
			var gstrtype=$("#gstrtype").val();
			
			var errGSTIN=false;
			var errFp = false;
			var errGSTRType = false;
			
			errGSTIN= validateGSTIN();
			
			errFp = validateFinPeriod();	
			
			errGSTRType=validateGSTRtype();
			
			
			if(errGSTIN || errFp ||errGSTRType){
				e.preventDefault();
			}
			if((errGSTIN)){
				 focusTextBox("gstinId");
			 } else if((errFp)){
				 focusTextBox("financialPeriod");
			 }else if((errGSTRType)){
				 focusTextBox("gstrtype");
			 } else{
				
					 var getGstinName = checkGstnId(gstinId);
				 
				 if(getGstinName == true ){
				 if(gstrtype=='GSTR1'||GSTR_type=='GSTR1'){
				 $(".selectOptions").show();
				 $(".selectOptionsgstr2").hide();
				 }
				 else if(gstrtype=='GSTR3B'){
					 $(".selectOptions").hide();
					 $(".selectOptionsgstr2").show();
				 }
				} else {
					bootbox.confirm({
							message :"<form name='saveGstinUId' id='saveGstinUId' method='post'>\
								<div class='card'>\
							    <div class='box-content'>\
							    <div class='form-group input-field'> \
							    <label class='label'>  \
							    <input type='text' id='gstnUserId' class='form-control' name='gstnUserId' />\
							    <div class='label-text label-text2'>GSTN Login ID :</div></label> \
							    <span class='text-danger cust-error' id='reg-gstin-id-req'>This field is required and should be in a proper format</span> \
								<span class='text-danger cust-error' id='reg-gstin-id-back-req'></span> \
							    </div> \
							    <div class='form-group input-field'> \
							    <label class='label'>  \
							    <input type='number' id='grossTurnover' class='form-control' name='grossTurnover' />\
							    <div class='label-text label-text2'>Gross Turnover :</div></label> \
							    <span class='text-danger cust-error' id='gross-turnover'>This field is required and should be numeric.</span> \
							    </div> \
							    <div class='form-group input-field'> \
							    <label class='label'>  \
							    <input type='number' id='currentTurnover' class='form-control' name='currentTurnover' />\
							    <div class='label-text label-text2'>Current Turnover :</div></label> \
							    <span class='text-danger cust-error' id='current-turnover'>This field is required and should be numeric.</span> \ </div> \ </div> \ </div> \
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
						 						
						 						if(gstrtype=='GSTR1'||GSTR_type=='GSTR1'){
						 							 $(".selectOptions").show();
						 							 $(".selectOptionsgstr2").hide();
						 							 }
						 							 else if(gstrtype=='GSTR3B'){
						 								 $(".selectOptions").hide();
						 								 $(".selectOptionsgstr2").show();
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
			/*$("#userLogin").attr("disabled", "disabled");
			$("#userLogin").text('Uploading');
			document.uploadAsp.action = "/uploadInvoicesManual";
			document.getElementById("uploadAsp").action = "./uploadInvoicesManual";
			$("#uploadAsp").submit();
			 */
			 }
			
		});

		loadFP(L0_fp);
		loadGstinListRoleWise(L0_gstin);
		loadGstrType(GSTR_type);
		
	 if(gobacktoasp == "gobacktoasp"){
		 $("#gstrfileoptions").click();
	 }
	
	 
	$("#userLogin").click(function (){
		$("#userLogin").attr("disabled", "disabled");
		$("#userLogin").text('Uploading');
		/*document.uploadAsp.action = "/uploadInvoicesManual";*/
		document.getElementById("uploadAsp").action = "./uploadInvoicesManual";
		$("#uploadAsp").submit();
	});
	
	$("#saveToGSTN").click(function (e){
		 gstinId = $("#gstinId").val();
		 financialPeriod = $("#financialPeriod").val();
		 var button="draft";
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
		 }
		 else{
			
			
			 var getuploadtojiogststatusvalue = getuploadtojiogststatus(gstinId,financialPeriod,button);
			 
			 if(getuploadtojiogststatusvalue == 'successfullyuploaded'){
				 $("#saveToGSTN").attr("disabled", "disabled");
				 $("#saveToGSTN").text('Getting Data');
				 document.getElementById("uploadAsp").action = "./getL0ResponsePage";
				 $("#uploadAsp").submit();
		    }
		 }	
	});
	
	$("#submitToGSTN").click(function (e){
		 gstinId = $("#gstinId").val();
		 financialPeriod = $("#financialPeriod").val();
		var errGSTIN=false;
		var errFp = false;
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
				 $("#submitToGSTN").attr("disabled", "disabled");
				 $("#submitToGSTN").text('Getting Data');
				 document.getElementById("uploadAsp").action = "./getSubmitToGSTNResponsePage";
				 $("#uploadAsp").submit();
		   }
		 }
	});
	
	$("#filegstr1").click(function (e){
		 gstinId = $("#gstinId").val();
		 financialPeriod = $("#financialPeriod").val();
		var errGSTIN=false;
		var errFp = false;
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
				 $("#filegstr1").attr("disabled", "disabled");
				 $("#filegstr1").text('Getting Data');
				 document.getElementById("uploadAsp").action = "./getfiletogstnresponsepage";
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
	
	function validateGSTRtype(){
		errGSTIN = validateSelectField("gstrtype","gstr-type");
		 return errGSTIN;

	} 

	$("#prepareGstr3B").click(function (e){
		var errGSTIN = validateGSTIN();	
		var errFp = validateFinPeriod();
		if(errGSTIN || errFp){
			e.preventDefault();
		}
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else if((errFp)){
			 focusTextBox("financialPeriod");
		 }
		 else{
			 $("#prepareGstr3B").attr("disabled", "disabled");
			 $("#prepareGstr3B").text('Please wait...');
			 document.getElementById("gstr3b").action = "preparegstr3b";
			 document.getElementById("gstr3b").gstinId.value = $("#gstinId").val();
			 document.getElementById("gstr3b").financialPeriod.value = $("#financialPeriod").val();
			 document.getElementById("gstr3b")._csrf_token.value = $("#_csrf_token").val();
			 $("#gstr3b").submit();
		 }			
	});

	$("#draftGstr3B").click(function (e){
		var errGSTIN = validateGSTIN();	
		var errFp = validateFinPeriod();
		if(errGSTIN || errFp){
			e.preventDefault();
		}
		if((errGSTIN)){
			 focusTextBox("gstinId");
		 } else if((errFp)){
			 focusTextBox("financialPeriod");
		 }
		 else{
			 $("#draftGstr3B").attr("disabled", "disabled");
			 $("#draftGstr3B").text('Please wait...');
			 document.getElementById("gstr3b").action = "draftgstr3bform";
			 document.getElementById("gstr3b").gstinId.value = $("#gstinId").val();
			 document.getElementById("gstr3b").financialPeriod.value = $("#financialPeriod").val();
			 document.getElementById("gstr3b")._csrf_token.value = $("#_csrf_token").val();
			 $("#gstr3b").submit();
		 }			
	});
  	
});

function loadGstinListRoleWise(selectedGstin){
	
    $.ajax({
		url : "getGSTINListAsPerRole",
		method : "get",
	//	contentType : "application/json",
		dataType : "json",
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
			$("#gstinId").append('<option value="">Select your GSTIN</option>');
			$.each(json,function(i,value) {
				if(selectedGstin == value.gstinNo){
                    $('#gstinId').append($('<option>').text(value.gstinNo).attr('value', value.gstinNo).attr('selected','selected')); 
            }else{

				
				$("#gstinId").append($('<option>').text(value.gstinNo).attr('value',value.gstinNo));
            }
			});
			
			
         }
	}); 
	
}

function getuploadtojiogststatus(gstinId,financialPeriod,button){
    var status='';
    var bootboxcount=0;
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
                       if(json.UploadToJiogst==0&& json.UploadToJiogstCndn==0)
                             {
                             status='successfullyuploaded';
                             }
                       else if(json.UploadToJiogstCndn==0)
                       {
                            
                            
                             bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices to JioGST before saving data to GSTN.');
                            
                       }
                       else
                       {
                    	   if(json.UploadToJiogst==0)
                    		   {
                    	   bootbox.alert('Please upload the remaining  '+ json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                    		   }
                    	   else
                    	   {
                    		   bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices  and' + json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                    	   }
                       
                       }
                      }
              
                      }
                
                
            //draft  
                
                
                 else if(button=="submit")
                 {
                       if ( json == null )
                                  {
                             bootbox.alert('No Invoices for this Financial Period');
                                  }
                       else{
                       if(json.UploadToJiogst==0 &&json.SaveToGstn==0 && json.UploadToJiogstCndn==0 && json.SaveToGstn==0)
                            {
                            status='successfullyuploaded';
                            }
                       
                       else if(json.UploadToJiogst!=0 &&json.SaveToGstn!=0 && json.UploadToJiogstCndn!=0 && json.SaveToGstn!=0)
                       {
                    	   bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices  and' + json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                       }
                      else
                      {
                    	  //checking upload for invoice/cndn
                    	  
                    	  if (json.UploadToJiogst!=0 || json.UploadToJiogstCndn !=0)
                    		  {
                    		  
                    		  if(json.UploadToJiogstCndn==0)
                    			  {
                    			  
                    			  bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices to JioGST before saving data to GSTN.');
                    			  bootboxcount++;
                    			  }
                    		  
                    		  else
                    		  {
                    		   if(json.UploadToJiogst==0)
                   		   {
                           	   bootbox.alert('Please upload the remaining  '+ json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                           	bootboxcount++;		 
                   		   }
                           	   else
                           	   {
                           		   bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices  and' + json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                           		bootboxcount++;
                           	   }
                    		  
                    		  }
                    		  
                    		  
                    		  }
                    	  
                    	//checking save for invoice/cndn
                    	  else
                     {
                    	  
                    	  if (json.SaveToGstn!=0 || json.SaveToGstnCndn!=0)
                    		  
                    	  	{
                    		  
                    		  if(json.SaveToGstn==0)
                    			  {
                    			  
                    			  bootbox.alert('Please save the remaining  '+json.SaveToGstn+' invoices to JioGST before saving data to GSTN.');
                    			  
                    			  }
                    		  
                    		  else
                    		  {
                    		   if(json.SaveToGstn==0)
                    		   	{
                           	   bootbox.alert('Please save the remaining  '+ json.SaveToGstnCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                           		   }
                           	   else
                           	   {
                           		   bootbox.alert('Please save the remaining  '+json.SaveToGstn+' invoices  and' + json.SaveToGstnCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                           	   }
                    		  
                    		  }
                    		  
                    		  
                    		  }
                    }
                      
                      }
                       
                       
                 }
                       
                       
                 }
                
                
                //file RnD
                
                
                 else if(button=="file")
                 {
                       if ( json == null )
                                  {
                             bootbox.alert('No Invoices for this Financial Period');
                                  }
                       else{
                    	   
                       if(json.UploadToJiogst==0 &&json.SubmitToGstn==0&&json.SaveToGstn==0&& json.UploadToJiogstCndn==0 &&json.SubmitToGstnCndn==0&&json.SaveToGstnCndn==0)
                            {
                            status='successfullyuploaded';
                            } 
                       
                       
                       else if(json.UploadToJiogst!=0 &&json.SaveToGstn!=0 &&json.SubmitToGstn!=0 && json.UploadToJiogstCndn!=0 && json.SaveToGstnCndn!=0 && json.SubmitToGstnCndn!=0)
                       {
                    	   bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices  and' + json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                       }
                       
                       else
                       {
                    	   
                    	//  //checking upload for invoice/cndn for filing button
                    	   if (json.UploadToJiogst!=0 || json.UploadToJiogstCndn !=0)
                 		  {
                 		  
                 		  if(json.UploadToJiogstCndn==0)
                 			  {
                 			  
                 			  bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices to JioGST before saving data to GSTN.');
                 			  bootboxcount++;
                 			  }
                 		  
                 		  else
                 		  {
                 		   if(json.UploadToJiogst==0)
                		   {
                        	   bootbox.alert('Please upload the remaining  '+ json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                        	bootboxcount++;		 
                		   }
                        	   else
                        	   {
                        		   bootbox.alert('Please upload the remaining  '+json.UploadToJiogst+' invoices  and' + json.UploadToJiogstCndn+' Credit/DebitNote to JioGST before saving data to GSTN.');
                        		bootboxcount++;
                        	   }
                 		  
                 		  }
                 		  
                 		  
                 		  }
             		  
             		
                       
                     //checking save for invoice/cndn for filing button 
                     	  else if (json.SaveToGstn!=0 || json.SaveToGstnCndn!=0)
                          {
                         		  
                         		  if(json.SaveToGstn==0)
                         			  {
                         			  
                         			  bootbox.alert('Please save the remaining  '+json.SaveToGstn+' invoices to Gstn before filing data to GSTN.');
                         			  
                         			  }
                         		  
                         		  else
                         		  {
                         		   if(json.SaveToGstn==0)
                         		   	{
                                	   bootbox.alert('Please save the remaining  '+ json.SaveToGstnCndn+' Credit/DebitNote to Gstn before filing data to GSTN.');
                                		   }
                                	   else
                                	   {
                                		   bootbox.alert('Please save the remaining  '+json.SaveToGstn+' invoices  and' + json.SaveToGstnCndn+' Credit/DebitNote to Gstn before filing data to GSTN.');
                                	   }
                         		  
                         		  }
                         		  
                         		  
                         		  
                         }
                     	 
                 
                       
                       //checking submit for invoice/cndn for filing button 

                     	 else if (json.SubmitToGstn!=0 || json.SubmitToGstnCndn!=0)
                    		  
                		  {
                		  
                     		if(json.SubmitToGstn==0)
               			  {
               			  
               			  bootbox.alert('Please submit the remaining  '+json.SubmitToGstn+' invoices to Gstn before filing the data to GSTN.');
               			  
               			  }
               		  
                     		 else
                    		  {
                    		   if(json.SubmitToGstn==0)
                    		   	{
                           	   bootbox.alert('Please submit the remaining  '+ json.SubmitToGstnCndn+' Credit/DebitNote to Gstn before filing data to GSTN.');
                           		   }
                           	   else
                           	   {
                           		   bootbox.alert('Please submit the remaining  '+json.SubmitToGstn+' invoices  and' + json.SubmitToGstnCndn+' Credit/DebitNote to Gstn before filing data to GSTN.');
                           	   }
                    		  
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

function getudrafttogstnstatus(gstinId,financialPeriod){
	var status='';
	$.ajax({
		url : "getuploadtojiogststatus",
		type : "POST",	
		dataType : "json",
		headers: {_csrf_token : $("#_csrf_token").val()},
		data : {gstin: gstinId , financialPeriod:financialPeriod},
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
			if($.isEmptyObject(json)){
				 bootbox.alert('Please upload the data to Jio GST.');
			 } else if(json.actionTaken == 'UploadToJioGST'){
				 bootbox.alert('Please Draft the data to GSTN.');
			 }else{
				 /*bootbox.alert('Data has been '+json.actionTaken+' on '+json.uploadDate+' for '+json.fpPeriod);*/
				 status='successfullyuploaded';
				 
				/* if(json.uploadType == 'Auto'){
					 bootbox.alert('You have set Auto Upload setting for this GSTIN. To still Upload the data Manually change your settings');
					 $("#userLogin").attr("disabled", "disabled");
				 } else {
					 $("#userLogin").removeAttr("disabled");
				 }*/
		}
			
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
		
    });
	return status;
	
}

$(document).ready(function(){
	
	$("#financialPeriod").change(function(){
		 var gstin = $("#gstinId").val();
		 var financialPeriod=$("#financialPeriod").val();
		if(gstin != null && gstin != ""){
			
			historyData(gstin,financialPeriod);
		}
	});		
	
	$("#gstinId").change(function(){
		 var financialPeriod = $("#financialPeriod").val();
		 var gstin = $("#gstinId").val();
		 $(".selectOptions").hide();
			if(financialPeriod != "" && financialPeriod != null){
				
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
		data : {gstin: gstin , financialPeriod:financialPeriod},
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
			if($.isEmptyObject(json)){
				 bootbox.alert('No data uploaded for selected GSTIN');
			 } else {
				 if(json.actionTaken == "UploadToJioGST"){
					 var type="uploaded to JioGST";
				 }else if(json.actionTaken=="SaveToGSTN"){
					 var type="saved to GSTN";
				 }else if(json.actionTaken=="SubmitToGSTN"){
					 var type="submited to GSTN";
				 }else if(json.actionTaken=="FileToGSTN"){
					 var type="submited to GSTN";
				 }
				 bootbox.alert('Data has been '+type+' on '+json.uploadDate);
				 if(json.uploadType == 'Auto'){
					 bootbox.alert('You have set Auto Upload setting for this GSTIN. To still Upload the data Manually change your settings');
					 $("#userLogin").attr("disabled", "disabled");
				 } else {
					 $("#userLogin").removeAttr("disabled");
				 }
		}
			
		},
		error: function (data,status,er) {
			 
			 getInternalServerErrorPage();   
		}
		
    });
}

function loadFP(selectedFP){
	
    $.ajax({
		url : "getFpMonthsArray",
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
			
			$("#financialPeriod").append('<option value="">Select </option>');
			$.each(json,function(key,value) {
				if(selectedFP == value){
                    $('#financialPeriod').append($('<option>').text(key).attr('value', value).attr('selected','selected')); 
            }else{

				$("#financialPeriod").append($('<option>').text(key).attr('value',value));
            }
			});
			
			
         },
         error: function (data,status,er) {
        	 
        	 getInternalServerErrorPage();   
        }

	}); 
	
}

function loadGstrType(selectedGSTRtype){
	
	$.ajax({	
		url : "getgstrtype",
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
				
				$("#gstrtype").append('<option value="">Select </option>');
				$.each(json,function(key,value) {
					if(selectedGSTRtype == value){
	                    $('#gstrtype').append($('<option>').text(key).attr('value', value).attr('selected','selected')); 
	            }else{
					$("#gstrtype").append($('<option>').text(key).attr('value',value));
	            }
				});
				
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }

		
	});
	
	
}

/*$('#uploadAsp').on('submit', function() {
	alert();
    return $('#testForm').jqxValidator('validate');
});*/