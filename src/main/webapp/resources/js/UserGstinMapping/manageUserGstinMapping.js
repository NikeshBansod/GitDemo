var gstinUserExists = false;

jQuery(document).ready(function($){
            $(".accordion_example2").smk_Accordion({
                closeAble: true, //boolean
            });

       	 if($("#actionPerformed").val()){
       		 $('#toggle').hide();
       	}
            $('#addCustomer').on('click', function(e) {
            	$(".addCustomer").slideToggle();
            	$('.userGstinValuesTable').hide();
            	$('#addCustomer').hide();
           	 	$('#toggle').hide();
            	e.preventDefault();
            })
});


$(document).ready(function(){
      
	var blankMsg="This field is required";
	var selectMsg ="Select atleast one GSTIN";

	$("#submitGstinMapping").click(function(e){
		 
		 var errFlag1 = validateGSTIN();
		 var errFlag2 = validateGSTINLocation();
		 var errFlag3 = validateGSTINUser();
		 var errFlag4 = validateMultipleGSTINUser();
		
		 if ((errFlag1) || (errFlag2) || (errFlag3) || (errFlag4)){
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
	
});


$(document).ready(function(){
    
    $.ajax({
		url : "getUserGSTINMappingList",
		method : "POST",
		headers: {
			_csrf_token : $("#_csrf_token").val()
	    },
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
			 $.each(json,function(i,value){
				 $(".userGstinValues").append(
					/*'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.gstinNo+'</h1>'
						+'</div>'
						+'<div class="cust-edit">'
						  +'<div class="cust-icon">'
						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						  +'</div>'
						+'</div>'
					+'</div>'
		
					+'<div class="content">'
						+'<div class="cust-con">'
							+'<p> GSTIN : '+value.gstinNo+'</p>'
							+'<p> Employee : '+value.userName+'</p>'
							
						+'</div>'
					+'</div>'	 */
						 +'<tbody>'			
							+'<tr>'
								+'<td class="text-left"><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.gstinNo+'</a></td>' 
								+'<td class="text-left">'+value.userName+'</td>'
						 +'</tr>'
					+'</tbody>'
				 
				 );
			});	
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));	
         },
			error: function (data,status,er) {
				 
				 getInternalServerErrorPage();   
			}
	}); 

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
		success:function(json,fTextStatus,fRequest){
			$("#gstinId").empty();
			if(editPage != 'true'){
			$("#gstinId").append('<option value="">Select GSTIN</option>');
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
			success:function(json,fTextStatus,fRequest){
				$("#gstinAddressMapping").empty();
				if(editPage != 'true'){
				$("#gstinAddressMapping").append('<option value="">Select GSTIN Location</option>');
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
	     
});



$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
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
$(document).ready(function() {
    $('#userGstinValuesTab').DataTable(
              {
            	  "aaSorting": [],
                    "bLengthChange": false,
                    "pagingType": "simple",
                    
                    
              }
    
    );
} );

