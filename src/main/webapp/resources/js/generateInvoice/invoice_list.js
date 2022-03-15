 jQuery(document).ready(function($){
     
    $(".accordion_example2").smk_Accordion({
       closeAble: true, //boolean
    });
            
    $('#addCustomer').on('click', function(e) {
          $(".addCustomer").slideToggle();
            console.log('yes');
            e.preventDefault();
    });

});

$(document).ready(function () {
    $(".content").hide();
	$(".heading .cust-con").click(function () {
			$(this).parent().next(".content").slideToggle();
	});
});

function viewRecord(idValue){
	document.manageInvoice.action = "./getInvoiceDetails";
	document.manageInvoice.id.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();
}

$(document).ready(function () {
	$.ajax({
			url : "getInvoiceDetailList",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
		    type : "POST",
			dataType : "json",			
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
				setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
				var counter=1;
				 $.each(json,function(i,value){
					 $('#invoiceHistoryTab tbody:last-child').append(
							 '<tr>'
							// 	+'<td style="text-align: center;">'+counter+'</td>'
							 	+'<td style="text-align: left;"><a href="#"  onclick="javascript:viewRecord('+value[0]+');">'+value[1]+'</a></td>'
							 	+'<td style="text-align: left;">'+value[4]+'</td>'
							// 	+'<td style="text-align: center;">'+parseFloat(value[2])+'</td>'
							 	+'<td style="text-align: left;">'+value[5]+'</td>'
							// 	+'<td style="text-align: center;">'+formatDateInDDMMYYYYHHMM(value[3])+'</td>'
							 +'</tr>'
					 );
					 counter++;
				});
				 $('#invoiceHistoryTab').DataTable(
				    		{
				    			 "aaSorting": [],
				    			"bLengthChange": false,
				    			"pagingType": "simple",
				    			
				    				
				    		});
			},
			error: function (data,status,er) {				 
				getWizardInternalServerErrorPage();   
			}
	});
});


function formatDateInDDMMYYYYHHMM(inputDate){
	var date = new Date(inputDate);  //or your date here
	var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
	var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
    var am_pm = date.getHours() >= 12 ? "PM" : "AM"; 
	hours = hours < 10 ? "0" + hours : hours;
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    time = hours + ":" + minutes + " " + am_pm;
	return (date.getDate() + "/"+month+"/"+date.getFullYear()+"  "+time);	
}


/* 
$(document).ready(function () {
	$.ajax({
			url : "getInvoiceDetailList",
			headers: {
				_csrf_token : $("#_csrf_token").val()
		    },
			type : "POST",
			dataType : "json",
			async : false,
			success:function(json,textStatus,request){
				
				if (isValidSession(json) == 'No') {
					window.location.href = getDefaultSessionExpirePage();
					return;
				}

				if(isValidToken(json) == 'No'){
					window.location.href = getCsrfErrorPage();
					return;
				}
				
				 $.each(json,function(i,value){
					 $('.dnynamicProducts').append(
							'<div class="heading">'
							   +' <div class="cust-con">'
							   		+'<a href="#"  onclick="javascript:viewRecord('+value[0]+');">'
							       		+' <h1>'+value[1]+'</h1>'
							        +'</a>'
							    +'</div>'
						//	   +' <div class="cust-edit">'
						//	       +' <div class="cust-icon"><a href="edit-service.html"><i class="fa fa-pencil" aria-hidden="true"></i></a><a href="#"><i class="fa fa-trash-o" aria-hidden="true"></i></a></div>'
						//	   +' </div>'
							+'</div>'
						//	+'<div class="content">'
						//	    +'<div class="cust-con">'
						//	        +'<p><span>SAC Code:</span> 440237</p>'
						//	       +' <p><span>Service Name:</span> Technical Sound System</p>'
						//	       +' <p><span>Unit Measurement:</span> Per Hour</p>'
						//	        +'<p><span>Rate:</span> 5000.0</p>'
						//	   +' </div>'
						//	+'</div>'
					 );
				});
				 setCsrfToken(request.getResponseHeader('_csrf_token'));
			},
			error: function (data,status,er) {	 
				 getInternalServerErrorPage();   
			}
	}); 
});
 
 */