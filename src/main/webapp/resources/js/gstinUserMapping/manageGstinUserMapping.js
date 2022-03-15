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
            	$('#addCustomer').hide();
           	 	$('#toggle').hide();
            	e.preventDefault();
            })
});


$(document).ready(function(){
    $.ajax({
		url : "getSecondaryUserList",
		method : "POST",
		dataType : "json",
		async : true,
		success:function(json){
			$("#selectUser").empty();
			$("#selectUser").append(
			'<option value="">Select Employee</option>');
			$.each(json,function(i,value) {
				$("#selectUser").append(
					$('<option>').text(value.userName)
						.attr('value',value.id));
			});
         }
	}); 

     
});


$(document).ready(function(){
    $.ajax({
		url : "getGstinDetails",
		method : "POST",
		//contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json){
			/*$("#selectGSTINDetail").append(
			'<option value="">GSTIN Number</option>');*/
			$("#selectGSTINDetail").empty();
			$.each(json,function(i,value) {
				$("#selectGSTINDetail").append(
					$('<option>').text(value.gstinNo)
						.attr('value',value.id));
			});
         }
	}); 

     
});


$(document).ready(function(){
    $.ajax({
		url : "getGSTINUserMappingList",
		method : "POST",
		contentType : "application/json",
		dataType : "json",
		async : false,
		success:function(json){
			 $.each(json,function(i,value){
				 $(".dnynamicGstinUserMap").append(
					'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.userName+'</h1>'
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
							
						+'</div>'
					+'</div>'	 
				 
				 );
			});
         }
	}); 

     
});



$(document).ready(function () {
	$(".content").hide();
	$(".heading .cust-con").click(function () {
		$(this).parent().next(".content").slideToggle();
	});
});

function editRecord(idValue){
	document.gstinUserMapping.action = "./editGSTINUserMapping";
	document.gstinUserMapping.id.value = idValue;
	document.gstinUserMapping.submit();	
}

function deleteRecord(idValue){
	bootbox.confirm("Are you sure you want to delete ?", function(result){
		if (result){
		 
		document.gstinUserMapping.action = "./deleteGSTINUserMapping";
		document.gstinUserMapping.id.value = idValue;
		
		document.gstinUserMapping.submit();	
		 }
	});
}


$(document).ready(function() {
	


$("#selectUser").on("keyup input",function(){
   
	isUserAlreadyMapped();
	
	});
});

function isUserAlreadyMapped(){
	
	 var referenceUserId = $("#selectUser").val();
	    if(referenceUserId != ''){
	    var data={"referenceUserId":referenceUserId}; 	
	    var refUserName = $('#selectUser option:selected').text();
		   $.ajax({
				url : "checkIfGSTINUserExists",
				type: 'POST',
				dataType : "json",
				data : data,
				success : function(jsonVal) {
					if(jsonVal == true){
						 $("#selectUser").addClass("input-error").removeClass("input-correct");
						 $("#selectUser-req").text('User Name : '+refUserName+' is already Mapped. Select other user');
						 $("#selectUser-req").show();
						 gstinUserExists = true;
					}else{
						 $("#selectUser").addClass("input-correct").removeClass("input-error");
						 $("#selectUser-req").hide();
						 gstinUserExists = false;
					}
					
				}
		    }); 
	    }
	    return gstinUserExists;
}

$(document).ready(function(){
	var referenceUserId = $("#referenceUserId").val();
    $.ajax({
		url : "getGstinNameList",
		method : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			
			$.each(json,function(i,value) {
				if(referenceUserId == value.id){
					  $('#selectGstinUser').val(value.userName); 
				 }
			});
         }
	}); 

     
});


$(document).ready(function(){
	var selectedGSTIN = $("#selectedGSTIN").val();
	
    $.ajax({
		url : "getGstinNoList",
		method : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			
			$.each(json,function(i,value) {
				var count = 0;
				if(selectedGSTIN.indexOf(',') == -1){
					
					if(selectedGSTIN == value.id){
						  $('#selectGstinNoDemo').append($('<option>').text(value.gstinNo).attr('value', value.id).attr('selected','selected'));
					}else{
						 $('#selectGstinNoDemo').append($('<option>').text(value.gstinNo).attr('value', value.id));
					}
				}else{
					 var str_array = selectedGSTIN.split(',');
					 for(var i = 0; i < str_array.length; i++) {
						   if(str_array[i] == value.id){
								  $('#selectGstinNoDemo').append($('<option>').text(value.gstinNo).attr('value', value.id).attr('selected','selected'));
								  count = 1;
							 }
					 }
					 if(count == 0){
						 $('#selectGstinNoDemo').append($('<option>').text(value.gstinNo).attr('value', value.id)); 
					 }
				}
				
			});
         }
	}); 

     
});
