var blankMsg="This field is required";
var length = 2;
var lengthMsg = "Minimum length should be ";


$(document).ready(function(){
	
	$(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
	
	$('#addCustomer').on('click', function(e) {
		$('#search-sac').val("");
		$('.currentServiceSAC').html("");
		
      	$(".addCustomer").slideToggle();
      	e.preventDefault();
    });
	
	 $.ajax({
	 		url : "getiCompleteSACCodeList",
	 		type : "GET",
	 		dataType : "json",
	 		async : false,
	 		success:function(json,fTextStatus,fRequest){
	 			 $.each(json,function(i,value){
	 				 $('.dnynamicSACCode').append(
	 					'<div class="heading">'
	 						+'<div class="cust-con">'
	 							+'<h1>'+value.sacCode+'</h1>'
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
	 							+'<p> SAC Code : '+value.sacCode+'</p>'
	 							+'<p> SAC Description : '+value.sacDescription+'</p>'
	 							+'<p> SGST/UGST  : '+value.sgstOrUgst+'%</p>'
	 							+'<p> CGST  : '+value.cgst+'%</p>'
	 							+'<p> IGST  : '+value.igst+'%</p>'
	 							
	 						+'</div>'
	 					+'</div>'	 
	 				 
	 				 
	 				 );
	 			});
	 			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	          }
	 		
	 	});
	 
	 $(".content").hide();
		$(".heading .cust-con").click(function () {
			$(this).parent().next(".content").slideToggle();
	 });
		
		
	 $.ajax({
			url : "getiServiceRateOfTaxDetails",
			type : "POST",
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

				$("#igst").empty();
				$("#igst").append($('<option>').text("Select Rate of tax ").attr('value',""));
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}
				
				$.each(json,function(i,value) {
					$("#igst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
				});
				
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	         },
	         error: function (data,status,er) {
	        	 
	        	 getInternalServerErrorPage();   
	        }
		});
	
	
});

function editRecord(idValue){
	document.manageSacCode.action = "./editSACDetails";
	document.manageSacCode.id.value = idValue;
	document.manageSacCode._csrf_token.value = $("#_csrf_token").val();
	document.manageSacCode.submit();
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
	 if (result){
		document.manageSacCode.action = "./deleteSACDetails";
		document.manageSacCode.id.value = idValue;
		document.manageSacCode._csrf_token.value = $("#_csrf_token").val();
		document.manageSacCode.submit();	
	 }
	});
}