$(document).ready(function(){
	loadRevisionAndReturnsDetails();
	/*createDataTable('revisionAndReturnDataTable');*/
	 $('#revisionAndReturnDataTable').DataTable({
	     	bSort: false,
		        rowReorder: {
		              selector: 'td:nth-child(2)'
		        },
	         responsive: true
	 }); 
	$(".loader").fadeOut("slow");
	
});

function loadRevisionAndReturnsDetails(){
	 $.ajax({
			url : "getrevisionandreturndetaillist",
			method : "POST",
		//	contentType : "application/json",
			dataType : "json",
			async : false,
			headers: {_csrf_token : $("#_csrf_token").val()},
			beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },
			success:function(json,fTextStatus,fRequest){
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				 $.each(json,function(i,value){
					var formateedValueRR=formatTheValueRR(value[4]);
					var formatedValueDT=formatTheValueDT(value[3]);
			        console.log("My ID:"+value.originalInvoicePkId);
					 $('#revisionAndReturnDataTable tbody:last-child').append('<tr>'
							 +'<td><a href="#" onclick="javascript:viewRecordForRR('+value[6]+','+value[7]+','+value[8]+',\''+formatedValueDT+'\','+value[9]+');">'+value[0]+'</a></td>'
							 +'<td>'+formatDateInDDMMYYYYHHMM(value[1])+'</td>'
							 +'<td>'+value[2]+'</td>'
							 +'<td>'+formatedValueDT+'</td>'
							 +'<td>'+formateedValueRR+'</td>'
							 +'<td><a href="#" onclick="javascript:viewInvoiceHistoryForRR('+value[6]+','+value[7]+','+value[8]+',\''+formatedValueDT+'\','+value[9]+');">'+value[5]+'</a></td>'
							 
	                   +'</tr>'
		        	);
						 
				});
				 setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		});
}
	 
	 function formatTheValueRR(value){
		 if(value == 'salesReturn' ){
			 formateedValueRR='Sales Return';
		 }else if(value == 'discountChange'){
			 formateedValueRR='Discount Change';
		 }else if(value == 'salesPriceChange' ){
			 formateedValueRR='Sales Price Change';
		 }else if(value == 'rateChange'){
			 formateedValueRR='Rate Change';
		 }else if(value == 'quantityChange'){
			 formateedValueRR='Quality Change';
		 }else if(value == 'itemChange' ){
			 formateedValueRR='Item Change';
		 }else if(value == 'partyChange'){
			 formateedValueRR='Party Change';
		 }else if(value == 'multipleChanges'){
			 formateedValueRR='Multiple Changes';
		 }
		 return formateedValueRR;
	 }
	 
	 function formatTheValueDT(value){
         if(value == 'creditNote'){
        	 formatedValueDT='Credit Note';
		 }else if(value == 'debitNote'){
			 formatedValueDT='Debit Note';
		 }else if(value == 'revisedInvoice'){
			 formatedValueDT='Revised Invoice';
		 }else if(value == 'deleteInvoice'){
			 formatedValueDT='Delete Invoice';
		 }else if(value == 'newInvoice'){
			 formatedValueDT='New Invoice';
		 }
         return  formatedValueDT;
	 }
	 
	 function viewRecordForRR(idValue,iterationNo,documentPkId,formatedValueDT,cnDnIterationNo){
		
		 if(formatedValueDT == 'Debit Note' || formatedValueDT == 'Credit Note'){
			 iterationNo=cnDnIterationNo;
			 setValuesInHiddenFields(idValue,iterationNo,documentPkId);
			 getCnDnDetails(idValue,iterationNo,documentPkId);
		 }else{
			 if(formatedValueDT == 'Delete Invoice'){
				    iterationNo=iterationNo;
				    document.manageRR.action = "./getinvoicedetailhistoryforrr";
					document.manageRR.id.value = documentPkId;
					document.manageRR.iterationNo.value = iterationNo;
					document.manageRR.conditionValue.value ="RRInvoiceHistoryD";
					document.manageRR._csrf_token.value = $("#_csrf_token").val();
					document.manageRR.submit();
			 }else{
					 iterationNo=iterationNo+1;
					 document.manageRR.action = "./getinvoicedetailhistoryforrr";
					 document.manageRR.id.value = documentPkId;
					 document.manageRR.iterationNo.value = iterationNo;
					 document.manageRR.conditionValue.value ="RRInvoiceHistory";
					 document.manageRR._csrf_token.value = $("#_csrf_token").val();
					 document.manageRR.submit();
			   }
			 }
			
	     }
		
	 
	 function viewInvoiceHistoryForRR(idValue,iterationNo,documentPkId,formatedValueDT,cnDnIterationNo){
		     
			  document.manageRR.action = "./getinvoicedetailhistoryforrr";
			  document.manageRR.id.value = idValue;
			  document.manageRR.iterationNo.value = iterationNo;
			if(formatedValueDT =='Delete Invoice'){
			  document.manageRR.conditionValue.value ="RRInvoiceHistoryD";
			}else{
			  document.manageRR.conditionValue.value ="RRInvoiceHistory";
			}
			  document.manageRR._csrf_token.value = $("#_csrf_token").val();
			  document.manageRR.submit();
	     
	 }
	 
	 function setValuesInHiddenFields(idValue,iterationNo,documentPkId){
			$("#refInvoiceId").val(idValue);
			$("#refIterationNo").val(iterationNo);
			$("#refCnDnId").val(documentPkId);
		}
	 
	 function downloadRecordForCNDN(){
		 
		 var idvalue=$("#refInvoiceId").val();
		 var iterationNo=$("#refIterationNo").val();
		 var cId=$("#refCnDnId").val()
		 
			$('.loader').show();
			document.manageRR.action = "./downloadcndninvoices";
			document.manageRR.id.value = idvalue;
			document.manageRR.iterationNo.value =iterationNo;
			document.manageRR.cId.value = cId;
			document.manageRR._csrf_token.value = $("#_csrf_token").val();
			document.manageRR.submit();
			$('.loader').fadeOut("slow");
		}
	 
	 
	 function previewCNDNSendMail(){
			$("#secondDivId").hide();
			$("#backToPreview").hide();
			$("#breadcumheader2").hide();
		    $('.loader').show();
			var idValue = $("#refInvoiceId").val();
			var iterationNo =  $("#refIterationNo").val();
			var cnDnInvoiceId = $("#refCnDnId").val();
			
				 $.ajax({
						url : "sendCnDnMailToCustomerFromPreview",
						headers: {
							_csrf_token : $("#_csrf_token").val()
						},
						method : "post",
						data : {id : idValue, iterationNo : iterationNo, cId : cnDnInvoiceId},
						/*contentType : "application/json",*/
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
							
							if(json.status == 'SUCCESS'){	
						         $('.loader').fadeOut("slow"); 						
								bootbox.alert(json.response);
								$("#secondDivId").show();
								$("#backToPreview").show();
								$("#breadcumheader2").show();
							}else{					
						         $('.loader').fadeOut("slow"); 		
								bootbox.alert(json.response);
								$("#secondDivId").show();
								$("#backToPreview").show();
								$("#breadcumheader2").show();
							} 
							setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				         },
				         error:function(data,status,er) { 
				        	 getInternalServerErrorPage();   
				         }
				});	
			 $(".loader").fadeOut("slow");
		}
	 
	 function formatDateInDDMMYYYYHHMM(inputDate){
			var date = new Date(inputDate);  //or your date here
			var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
			var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
		    var am_pm = date.getHours() >= 12 ? "PM" : "AM"; 
			hours = hours < 10 ? "0" + hours : hours;
		    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
		    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
		    //time = hours + ":" + minutes + " " + am_pm;
		    time = '';
			return (date.getDate() + "/"+month+"/"+date.getFullYear()+"  "+time);	
		} 
	
	
