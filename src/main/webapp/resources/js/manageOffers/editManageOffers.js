
jQuery(document).ready(function($){
            $(".accordion_example2").smk_Accordion({
                closeAble: true, //boolean
            });

            if($("#actionPerformed").val()){
          		 $('#toggle').hide();
          	}
            $('#addCustomer').on('click', function(e) {
            	$(".addCustomer").slideToggle();
            	e.preventDefault();
            })
            
        	$('#offerDate').Zebra_DatePicker();
});
		


function val(){
	bootbox.alert($("#userName").val());
	return true;
}

function loadProductList(){
	$.ajax({
		  url:"getProductsList", 	
		  type : "POST",
		  dataType: 'json',
		  success:function(json) {
			  $.each(json, function(i, value) {
				  selectType =$("#selctnType").val();
				  if(selectType == 'Product') {
					 if(selPrdctName==value.id){
						  $('#selectOffer').append($('<option>').text(value.name).attr('value', value.id).attr('selected','selected'));
				 }else{
					 $('#selectOffer').append($('<option>').text(value.name).attr('value', value.id));
				 }
				  } else {
					  $('#selectOffer').append($('<option>').text(value.name).attr('value', value.id));
				  }	   
				});
		  }
	});

	}


function loadServiceList(){
$.ajax({
	url : "getServicesList",
	type : "POST",
	dataType : "json",
	async : false,
	  success:function(json) {
		  $.each(json, function(i, value) {
			  selectionType =$("#selctnType").val();
			  if(selectionType == 'Product') {
			 if(selServiceName==value.id){
				  $('#selectOffer').append($('<option>').text(value.name).attr('value', value.id).attr('selected','selected')); 
			 }else{
				 $('#selectOffer').append($('<option>').text(value.name).attr('value', value.id));
			 }
			  } else {
				  $('#selectOffer').append($('<option>').text(value.name).attr('value', value.id));
			  }
				   
			});
	  }
});

}



$(document).ready(function() {
	//$("#selectOffer").hide();
	 selectinType =$("#selctnType").val();
	 selServiceName =$("#offerId").val();
	  if(selectinType == 'Product') {
		  selPrdctName =$("#offerId").val();
		  loadProductList();
	  } else {
		  selServiceName =$("#offerId").val();
		  loadServiceList();
	  }
    $('input[type=radio][name=offerType]').change(function() {
        if (this.value == 'Product') {
        	$("#selectOffer").empty();
        	loadProductList();
        	$("#selectOffer").show();
        }
        else if (this.value == 'Service') {
        	$("#selectOffer").empty();
        	loadServiceList();
        	$("#selectOffer").show();
        }
    });
});

$(document).ready(function(){
    $.ajax({
		url : "getmanageOffers",
		type : "POST",
		dataType : "json",
		async : false,
		success:function(json){
			 $.each(json,function(i,value){
				 $("#toggle").append(
					'<div class="heading">'
						+'<div class="cust-con">'
							+'<h1>'+value.offerName+'</h1>'
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
							+'<p> Offer Type : '+value.offerType+'</p>'
							+'<p> Offer Type Id : '+value.offerTypeId+'</p>'
							+'<p> Discount In : '+value.discountIn+'</p>'
							+'<p> Discount Value : '+value.discountValue+'</p>'
							+'<p> Discount Valid Date : '+value.discountValidDateInString+'</p>'
						+'</div>'
					+'</div>'	 
				 
				 
				 
				 
				 );
			});
         }
	}); 

     
});


$(document).ready(function () {
	$(".content").hide();
	$(".heading").click(function () {
		$(this).next(".content").slideToggle(100);
	});
});

function editRecord(idValue){
	document.manageOffers.action = "./editManageOffers";
	document.manageOffers.id.value = idValue;
	document.manageOffers.submit();	
}

function deleteRecord(idValue){
	 var x = confirm("Are you sure you want to delete ?");
	 if (x){
		document.manageOffers.action = "./deleteManageOffers";
		document.manageOffers.id.value = idValue;
		
		document.manageOffers.submit();	
	 }
}
