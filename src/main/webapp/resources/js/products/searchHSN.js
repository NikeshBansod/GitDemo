$(document).ready(function(){
	
	$(".accordion_example2").smk_Accordion({
        closeAble: true, //boolean
    });
});


function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

$( "#search-hsn").autocomplete({
    source: function (request, response) {
        $.getJSON("./../getHSNCodeList", {
            term: extractLast(request.term)
        	}, function( data, status, xhr ) {
			response(data);
			//setCsrfToken(xhr.getResponseHeader('_csrf_token'));
        	}
        );
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var hsnCode = (value.split('] - ')[0]).replace("[","").trim();
        var hsnDescription = (value.split('] - ')[1]).trim(); 
        
    	/*if ($("#hsnDescription").val() !== "" && $("#hsnCode").val() !== ""){
    		$("#prod-hsn-desc, #prod-hsn-code, #reg-gstin-req").hide();
    		$("#search-hsn").removeClass("input-error");
    	}*/
        
        $.ajax({
			url : "./../getIGSTValueByHsnCode",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
		    headers : {_csrf_token : $("#_csrf_token").val()},
			data : { hsnCode : hsnCode, hsnDescription : hsnDescription},
			async : false,
			success : function(value,fTextStatus,fRequest) {
				if (isValidSession(value) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(value) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				 $('.currentProductHsnCode').html("");
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
					
				 $('.currentProductHsnCode').append(
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
				 dynLoadRecord();
			}
        }); 
    }
});

function dynLoadRecord(){
	$(document).ready(function(){
		
		$(".content").hide();
		$(".heading .cust-con").click(function () {
			$(this).parent().next(".content").slideToggle();
		});
	});
	
}

	