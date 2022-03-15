function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}


$( "#search-sac").autocomplete({
    source: function (request, response) {
        $.getJSON("./../getSacCodeList", {
            term: extractLast(request.term)
        },  function( data, status, xhr ){
        	response(data);
		//	setCsrfToken(xhr.getResponseHeader('_csrf_token'));
		});
    },
    select: function (event, ui) {
    	var label = ui.item.label;
        var value = ui.item.value;
        var sacCode = (value.split('] - ')[0]).replace("[","").trim();
        var sacDescription = (value.split('] - ')[1]).trim(); 
       
        $.ajax({
			url : "./../getIGSTValueBySacCode",
			method : "POST",
			/*contentType : "application/json",*/
			dataType : "json",
			data : { sacCode : sacCode, sacDescription : sacDescription, _csrf_token : $("#_csrf_token").val()},
			async : false,
			success : function(value,fTextStatus,fRequest) {
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));				
				 $('.currentServiceSAC').append(
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