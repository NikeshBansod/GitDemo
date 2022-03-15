var gstinUserExists = false;

jQuery(document).ready(function($){
	$("#addUserGstnMappingDetails").hide();
    $('#addheader').hide();	
	
	
    $('#addUserGstinButton').on('click', function(e) {
    	$('.loader').show();
    	$('#addUserGstnMappingDetails').slideToggle();
   	 	$('#listheader').hide();
    	 $('#listTable').hide();
    	 $('#addUserGstinButton').hide();
    	 $('#addheader').show();
    	 $(".loader").fadeOut("slow");
    	e.preventDefault();
    })
    $('#gobacktolisttable').on('click', function(e){
    	$('.loader').show();
   	 	 $('#listheader').show();
    	 $('#listTable').slideToggle();
	   	 $('#addUserGstinButton').show();
	   	 $('#addheader').hide();
	     $('#addUserGstnMappingDetails').hide();
 	     $(".loader").fadeOut("slow");
    }); 
    
    
});

var blankMsg="This field is required";
$(document).ready(function(){
      
	var blankMsg="This field is required";
	var selectMsg ="Select atleast one GSTIN";

	$("#submitGstinMapping").click(function(e){
		$('.loader').show();
		 var errFlag1 = validateGSTIN();
		 var errFlag2 = validateGSTINLocation();
		 var errFlag3 = validateGSTINUser();
		 var errFlag4 = validateMultipleGSTINUser();
		 
		
		 if ((errFlag1) || (errFlag2) || (errFlag3) || (errFlag4)){
			 $(".loader").fadeOut("slow");
			 e.preventDefault();	 
		 } 
		 	
	});

	function validateGSTIN(){
		errFlag1 = validateSelectField("gstinId","selectUser-req");
		 return errFlag1;

	} 

	function validateGSTINLocation(){
		errFlag2 = validateSelectField("gstinAddressMapping","selectGSTINLocation-req");
		 return errFlag2;

	} 
	
	
	function validateGSTINUser(){
		errFlag3 = validateSelectField("gstinUserSet","selectGSTINDetail-req");
		 return errFlag3;

	} 

	function validateMultipleGSTINUser(){
		errFlag4 = multiSelectValidate("gstinUserSet","selectGSTINDetail-req");
		 return errFlag4;
		
	}
	
	$("#gstinId").change(function(){
		 if ($("#gstinId").val() === ""){
			 $("#gstinId").addClass("input-error").removeClass("input-correct");
			 $("#selectUser-req").show();
			 
		 }
		 if ($("#gstinId").val() !== ""){
			 $("#gstinId").addClass("input-correct").removeClass("input-error");
			 $("#selectUser-req").hide();
			 
		 } 
	});
	
	
	$("#gstinAddressMapping").change(function(){
		 if ($("#gstinAddressMapping").val() === ""){
			 $("#gstinAddressMapping").addClass("input-error").removeClass("input-correct");
			 $("#selectGSTINLocation-req").show();
			 
		 }
		 if ($("#gstinAddressMapping").val() !== ""){
			 $("#gstinAddressMapping").addClass("input-correct").removeClass("input-error");
			 $("#selectGSTINLocation-req").hide();
			 
		 } 
	});
	$(".loader").fadeOut("slow");
});

$(document).ready(function(){    
    $.ajax({
		url : "getUserGSTINMappingList",
		method : "POST",
		headers: {_csrf_token : $("#_csrf_token").val()},
		/*contentType : "application/json",*/
		dataType : "json",
		async : false,
		 beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
			 $.each(json,function(i,value){
				 $('#userGstinValuesTab tbody:last-child')
					.append('<tr>'
							+'<td>'+(i+1)+'</td>'
			        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.gstinNo+'</a></td>' 
			        		//+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-eye" aria-hidden="true"></i></a></td>'
			        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>' 
			        		+'<td><a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'
			        		+'</tr>'
			        	);
			});	
			$("#userGstinMapTab").DataTable();  
         },
		 error: function (data,status,er) {
			 getInternalServerErrorPage();    
		 }
	}); 
    createDataTable('userGstinValuesTab');
    $(".loader").fadeOut("slow");
});





$(document).ready(function(){
	
	 var editPage =$("#editPage").val();
	 var selGstinNo =$("#gstinId").val();
	 
    $.ajax({
		url : "getGstinDetails",
		type : "POST",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
		dataType : "json",
		async : false,
		 beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },

		success:function(json,fTextStatus,fRequest){
			$("#gstinId").empty();
			if(editPage != 'true'){
			$("#gstinId").append('<option value="">Select</option>');
			}
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			if(isValidToken(json) == 'No'){
				window.location.href = getCsrfErrorPage();
				return;
			}			
			$.each(json,function(i,value) {
				if(editPage == 'true'){
					if(selGstinNo==value.gstinNo){
						$("#gstinId").append($('<option>').text(value.gstinNo).attr('value',value.id).attr('selected','selected')); 
					 }else{
						 $("#gstinId").append($('<option>').text(value.gstinNo).attr('value',value.id));
					 }
				} else {
					$("#gstinId").append($('<option>').text(value.gstinNo).attr('value',value.id));
				}
				
			});	
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
         },
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
	});
    $(".loader").fadeOut("slow");
});


$(document).ready(function(){
	
	$("#gstinId").change(function(){
		var gstinId = $("#gstinId").val();
		var gstin = null;
		gstinId.id = gstin; 
		$.ajax({
			url : "getGstinLocationDetails",
			type : "POST",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
			data : {gstinId:gstinId},
			dataType : "json",
			async : false,
			 beforeSend: function(){
		         $('.loader').show();
		     },
		     complete: function(){
		         $('.loader').hide();
		     },

			success:function(json,fTextStatus,fRequest){
				$("#gstinAddressMapping").empty();
				if(editPage != 'true'){
				$("#gstinAddressMapping").append('<option value="">Select</option>');
				}
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}	
				
					$.each(json,function(i,value) {
						if(editPage == 'true'){
							if(selGstinNo==value.gstinNo){
								$("#gstinAddressMapping").append($('<option>').text(value.gstinLocation).attr('value',value.id).attr('selected','selected')); 
							 }else{
								 $("#gstinAddressMapping").append($('<option>').text(value.gstinLocation).attr('value',value.id));
							 }
						} else {
							$("#gstinAddressMapping").append($('<option>').text(value.gstinLocation).attr('value',value.id));
						}
						
					});
					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
				error: function (data,status,er) {
					 
					 getInternalServerErrorPage();   
				}
		});
	});
	$(".loader").fadeOut("slow");
});



function editRecord(idValue){
	document.userGstinMapping.action = "./editUserGSTINMapping";
	document.userGstinMapping.id.value = idValue;
	document.userGstinMapping._csrf_token.value = $("#_csrf_token").val();
	document.userGstinMapping.submit();	
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
		 
		document.userGstinMapping.action = "./deleteUserGSTINMapping";
		document.userGstinMapping.id.value = idValue;
		document.userGstinMapping._csrf_token.value = $("#_csrf_token").val();
		document.userGstinMapping.submit();	
		 }
	});
}


