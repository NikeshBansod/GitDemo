var blankMsg="This field is required";
var length = 2;
var lengthMsg = "Minimum length should be ";

$(document).ready(function(){
	
	$(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
	
	$('#addCustomer').on('click', function(e) {
		$('#search-hsn').val("");
		$('.currentProductHsnCode').html("");
      	$(".addCustomer").slideToggle();
      	e.preventDefault();
    });
	
	 $.ajax({
 		url : "./../getCompleteHSNCodeList",
 		type : "GET",
 		dataType : "json",
 		async : false,
 		success:function(json,fTextStatus,fRequest){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultSessionExpirePage();
				return;
			}
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
 			 $.each(json,function(i,value){
 				 $('.dnynamicHsnCode').append(
 					'<div class="heading">'
 						+'<div class="cust-con">'
 							+'<h1>'+value.hsnCode+'</h1>'
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
 							+'<p> HSN Code : '+value.hsnCode+'</p>'
 							+'<p> HSN Description : '+value.hsnDesc+'</p>'
 							+'<p> SGST/UGST  : '+value.sgstUgst+'%</p>'
 							+'<p> CGST  : '+value.cgst+'%</p>'
 							+'<p> IGST  : '+value.igst+'%</p>' 							
 						+'</div>'
 					+'</div>'	 				 
 				 );
 			});
          },
          error: function (data,status,er) {       	 
            	 getInternalServerErrorPage();   
            }
 	});
	 
	 
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
	
	 $.ajax({
			url : "./../getProductRateOfTaxDetails",
			type : "POST",
		    headers : {_csrf_token : $("#_csrf_token").val()},
			dataType : "json",
			async:false,
			success:function(json,fTextStatus,fRequest){
				$("#igst").empty();
				$("#igst").append($('<option>').text("Select Rate of tax ").attr('value',""));
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				$.each(json,function(i,value) {
					$("#igst").append($('<option>').text(value.taxRate).attr('value',value.taxRate));
				});
	         },
	         error: function (data,status,er) {       	 
	           	 getInternalServerErrorPage();   
	         }
		});
	 
	 $("#hsnSubmitBtn").click(function(e){
		 var errHsndesc = hsnDesc();
		 var errHsnCode = hsnCode();
		 var errHsnIgst = hsnIgst();
		 if((errHsndesc) || (errHsnCode) || (errHsnIgst)){
			 e.preventDefault();	
		 }
		 
		 if(errHsnCode){
			 focusTextBox("hsnCode");
		 } else if(errHsndesc){
			 focusTextBox("hsnDesc");
		 } else if(errHsndesc){
			 focusTextBox("igst");
		 }		 
	 }); 
});

function hsnDesc(){	
	errHsndesc = validateTextField("hsnDesc","prod-hsn-desc",blankMsg);
	 if(!errHsndesc){
		 errHsndesc=validateFieldLength("hsnDesc","prod-hsn-desc",lengthMsg,length);
	 } 
	 return errHsndesc;
}

function hsnCode(){
	 errHsnCode = validateTextField("hsnCode","prod-hsn-code",blankMsg);
	 if(!(errHsnCode)){
		 errHsnCode = validateFieldLength("hsnCode","prod-hsn-code",lengthMsg,length);
	 }		
	 return errHsnCode;
}

function hsnIgst(){	
	errhsnProductIgst = validateTextField("igst","prod-igst",blankMsg);
	if(!errhsnProductIgst){
		errhsnProductIgst=validateRegexpressions("productIgst","prod-igst",productIgstMsg,percentRegex);
	 }
	return errhsnProductIgst;
}

$(document).ready(function(){
	$("#hsnCode").on("input keyup", function(e){
	/*	if(e.keyCode == 32){
			   this.value = removeWhiteSpace(this.value);
		   }*/
		if ($("#hsnCode").val() === ""){
			$("#prod-hsn-code").show();
			$("#hsnCode").addClass("input-error").removeClass("input-correct");
		}
		
		if ($("#hsnCode").val() !== ""){
			$("#prod-hsn-code").hide();
			$("#hsnCode").removeClass("input-error");
		}
	});
	
	$("#hsnDesc").on("input keyup", function(e){
		/*	if(e.keyCode == 32){
				   this.value = removeWhiteSpace(this.value);
			   }*/
			if ($("#hsnDesc").val() === ""){
				$("#prod-hsn-code").show();
				$("#hsnDesc").addClass("input-error").removeClass("input-correct");
			}
			
			if ($("#hsnDesc").val() !== ""){
				$("#prod-hsn-code").hide();
				$("#hsnDesc").removeClass("input-error");
			}
		});
});

function editRecord(idValue){
	document.manageHsnCode.action = "./editHSNDetails";
	document.manageHsnCode.id.value = idValue;
	document.manageHsnCode._csrf_token.value = $("#_csrf_token").val();
	document.manageHsnCode.submit();
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
	 if (result){
		document.manageHsnCode.action = "./deleteHSNDetails";
		document.manageHsnCode.id.value = idValue;
		document.manageHsnCode._csrf_token.value = $("#_csrf_token").val();
		document.manageHsnCode.submit();	
	 }
	});
}