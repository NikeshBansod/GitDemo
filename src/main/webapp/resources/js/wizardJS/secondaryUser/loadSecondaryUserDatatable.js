$(document).ready(function () {
	$.ajax({
		url : "getSecondaryUserList",
		method : "post",
		/*contentType : "application/json",*/
		dataType : "json",
		data : { _csrf_token : $("#_csrf_token").val()},
		async : false,
		success:function(json,textStatus,request){
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}	
			setCsrfToken(request.getResponseHeader('_csrf_token'));
			$.each(json,function(i,value){
			  /*$('#toggle').append(
				'<div class="cust-content">'
					 +'<div class="heading">'
						+'<div class="cust-con">'+value.userName+'</div>'
						+'<div class="cust-edit">'
						  +'<div class="cust-icon">'
						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
  						  +'</div>'	//eo div cust-icon
  						+'</div>'	//eo div cust-edit
  					+'</div>'	//eo div heading
		
					+'<div class="content">'
						+'<div class="cust-con">'
							+'<p> Email ID : '+value.emailId+'</p>'
							+'<p> Mobile Number : '+value.contactNo+'</p>'
							+'<p> Aadhar No : '+value.secUserAadhaarNo+'</p>'
						+'</div>'	//eo div cust-icon
						+'<i class="clearfix"></i>'
					+'</div>'	 //eo div content
					+'<i class="clearfix"></i>'
				+'</div>'	//eo div cust-content	
				 );*/
				
				 $('#secondaryUserTab tbody:last-child')
					.append('<tr>'
							+'<td align="center">'+(i+1)+'</td>'
			        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.userName+'</a></td>' 
			        		//+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-eye" aria-hidden="true"></i></a></td>'
			        		+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>' 
			        		+'<td align="center"><a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'
			        		+'</tr>'
			        	);
			});	
			$("#secondaryUserTab").DataTable();  
         },
		 error: function (data,status,er) {
			 getWizardInternalServerErrorPage();   
		 }
	}); 
     
});
