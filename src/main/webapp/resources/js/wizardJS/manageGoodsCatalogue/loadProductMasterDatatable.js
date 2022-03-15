
$(document).ready(function(){
	$.ajax({
    		url : "getProductsList",
    		headers: {_csrf_token : $("#_csrf_token").val()},
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
    			
    			 $.each(json,function(i,value){
    				/* var dynamicUom='<p> Unit Of Measurement : '+value.unitOfMeasurement+'</p>';
    				 if(value.unitOfMeasurement=="OTHERS"){dynamicUom='<p> Unit Of Measurement : '+value.otherUOM+'</p>';}
	    			 $('#toggle').append(
	    				'<div class="cust-content">'
							 +'<div class="heading">'
	    						+'<div class="cust-con">'+value.name+'</div>'
	    						+'<div class="cust-edit">'
	    						  +'<div class="cust-icon">'
	    						  	+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
	    						  	+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
	    						  +'</div>'	//eo div cust-icon
								+'</div>'	//eo div cust-edit
							+'</div>'	//eo div heading
	    		
	    					+'<div class="content">'
	    						+'<div class="cust-con">'
	    							+'<p> HSN Code : '+value.hsnCode+'</p>'
	    							+'<p> HSN Description : '+value.hsnDescription+'</p>'
	    							+dynamicUom
	    							+'<p> Rate : '+value.productRate+'</p>'
	    							+'<p> Rate of tax (%) : '+value.productIgst+'</p>'
	    						+'</div>'	//eo div cust-icon
								+'<i class="clearfix"></i>'
							+'</div>'	 //eo div content
							+'<i class="clearfix"></i>'
						+'</div>'	//eo div cust-content	
	    			);*/
    				 $('#productTab tbody:last-child')
 					.append('<tr>'
 							+'<td align="center">'+(i+1)+'</td>'
 			        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.name+'</a></td>' 
 			        		//+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-eye" aria-hidden="true"></i></a></td>'
 			        		+'<td align="center">'+value.hsnCode+'</td>'
 			        		+'<td align="center">'+value.productRate+'</td>'
 			        		+'<td align="center">'+value.productIgst+'</td>'
 			        		+'<td align="center"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>' 
 			        		+'<td align="center"><a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'
 			        		+'</tr>'
 			        	);
    			});
    			$("#productTab").DataTable();  
             },
             error: function (data,status,er) {            	 
            	 getWizardInternalServerErrorPage();   
            }
    	});  
});    