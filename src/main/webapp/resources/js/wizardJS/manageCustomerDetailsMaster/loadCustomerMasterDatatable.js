$(document).ready(function(){

	/*
	var tab =  $('#custTab').DataTable( {
	        "processing": true,
	        "ajax": {
	            "url": "./getCustomerDetailsList",
	            "type": "POST",
	            "datatype": "json",
	            "dataSrc": function ( json ) {
		            for ( var i=0, ien=json.length ; i<ien ; i++ ) {
		                console.log(json[i][0]);
		              }
		              return json;
		            },
	         },
	        "columns": [ 
	           //      {
	           //       	"targets":0,
	           //        	"sortable": false,
			   //         	"searchable":false,
			   //         	"sWidth": "4%"
	           //       },
			   //       {
			   //        	"targets":1,
			   //           "className":      'details-control',
			   //           "orderable":      false,
			   //           "data":           null,
			   //           "defaultContent": ''
			   //       },
			            { 
			            	"targets":0,
			            	"name": "custName",
			            	"data": "custName",
			                "sortable": true,
			             	"searchable":true
			                //,"render": function(data, type, full) {
			                //	 return '<span data-toggle="tooltip" title="' + data + '">' + data + '</span>';
			                //}
			            },
			            { 
			            	"targets":1,
			            	"data": null,
			                "className": "center",
			             	"sortable": false,
			             	"searchable":false,
			             	"render": function(data, type, full) {
				                   return '<a href="#" onclick="javascript:editRecord(' + data.id + ')">View</a> / <a href="#" onclick="javascript:editRecord(' + data.id + ')">Edit</a> / <a href="#" onclick="javascript:deleteRecord(' + data.id + ')">Delete</a>';
				             }
			             }
			        ]	
	    } );
	//show/hide extra details row  
	function format ( d ) {
	    // `d` is the original data object for the row
	    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
	        '<tr>'+
	            '<td>Full name:</td>'+
	            //'<td>'+d.name+'</td>'+
	        '</tr>'+
	        '<tr>'+
	            '<td>Extension number:</td>'+
	           // '<td>'+d.extn+'</td>'+
	        '</tr>'+
	        '<tr>'+
	            '<td>Extra info:</td>'+
	            '<td>And any further details here (images etc)...</td>'+
	        '</tr>'+
	    '</table>';
	}
	 $('#custTab tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = table.row(tr);
	 
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            row.child.hide();
	            tr.removeClass('shown');
	        }
	        else {
	            // Open this row
	            row.child( format(row.data()) ).show();
	            tr.addClass('shown');
	        }
	    } );
	    
	    //tooltip
	    $('#custTab').on('draw.dt', function () {
            $('[data-toggle="tooltip"]').tooltip({
            	 "delay": 0,
                 "track": true,
                 "fade": 250
            });
        });
	   */
	   
	   
	   
	    
	 $.ajax({
			url : "getCustomerDetailsList",
			type : "post",
		//	contentType : "application/json",
			dataType : "json",
			headers: {_csrf_token : $("#_csrf_token").val()},
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
				/* $.each(json,function(i,value){
					 var dynamicCustType="";
					 var dynamicGstin="";
					 if(value.custType=="Individual"){
						 dynamicCustType = '<p> Customer Type : Not Registered Under GST'+""+'</p>';
						 dynamicGstin='';
					 }else if(value.custType=="Organization"){
						 dynamicCustType = '<p> Customer Type : Registered Under GST'+""+'</p>';
						 dynamicGstin='<p> GSTIN : '+value.custGstId+'</p>';
					 }
					 $('#toggle').append(
						'<div class="cust-content">'
							 +'<div class="heading">'
							 	+'<div class="cust-con">'+value.custName+'</div>'
							 	+'<div class="cust-edit">'
							 		+'<div class="cust-icon">'
							 			+'<a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
							 			+'<a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
							 		+'</div>'	//eo div cust-icon
							 	+'</div><i class="clearfix"></i>'	//eo div cust-edit
							 +'</div>'	//eo div heading
			
							 +'<div class="content">'
							 	+'<div class="cust-con">'
							 		+'<p> Customer Name : '+value.custName+'</p>'
							 		+dynamicCustType
							 		+'<p> Email ID : '+value.custEmail+'</p>'
							 		+'<p> Mobile Number : '+value.contactNo+'</p>'
							 		+dynamicGstin
							 		+'<p> State : '+value.custState+'</p>'
							 		+'<p> Country : '+value.custCountry+'</p>'
							 	+'</div>'	//eo div cust-icon
							 	+'<i class="clearfix"></i>'
							 +'</div>'	 //eo div content
							 +'<i class="clearfix"></i>'
						+'</div>'	//eo div cust-content						 
					 );
				});	*/	
				 $.each(json,function(i,value){	
					 $('#custTab tbody:last-child')
						.append('<tr>'
								+'<td style="text-align: center;">'+(i+1)+'</td>'
				        		+'<td><a href="#" onclick="javascript:editRecord('+value.id+');">'+value.custName+'</a></td>' 
				        		+'<td style="text-align: center;">'+value.custGstId+'</td>' 
				        		+'<td style="text-align: center;"><a href="#" onclick="javascript:editRecord('+value.id+');"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>' 
				        		+'<td style="text-align: center;"><a href="#" onclick="javascript:deleteRecord('+value.id+');"><i class="fa fa-trash-o" aria-hidden="true"></i></td>'
				        		+'</tr>'
				        	);
				});	
			    $("#custTab").DataTable();  
	         },
	         	error: function (data,status,er) {	        	 
	        	 getWizardInternalServerErrorPage();   
	        }
		});
	   

	
});    