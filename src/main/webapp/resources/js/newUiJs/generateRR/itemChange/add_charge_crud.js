var addChgRowNum = 0;
   
function add_chg_row(){ 
    $(document).ready(function(){ 
    	addChgRowNum++;
    	var additionalChargeValue = $('select#additionalChargeName option:selected').val();
    	var additionalChargeAmount = $('#additionalChargeAmountToShow').val();
    	var additionalChargeName = '';
    	var additionalChargeId = 0;
    	if(additionalChargeAmount && (additionalChargeAmount > 0)){
    		var	values = additionalChargeValue.split('{--}');
    		additionalChargeId = values[0]; 
    		additionalChargeName = values[1];
    		additionalChargeAmountFixed = values[2];
    		additionalChargeAmount = parseFloat(additionalChargeAmount).toFixed(2);
    		$("#additionalChargeAmount").val(additionalChargeAmount);
    	    //dynamic generate accordion - Start
    		var $addChgToggle = $("#add_chg_toggle");
        	var recordNo = addChgRowNum;
        	$addChgToggle.append('<div class="cust-content" id="add_chg_start_'+recordNo+'">'
				        			+'<div class="heading">'
						                +'<div class="cust-con">'
						                    +'<h1 id="additionalChargeName_'+recordNo+'">'+additionalChargeName+'</h1>'
						                +'</div>'
						                +'<div class="cust-edit">'
						                    +'<div class="cust-icon">'
						                    	+'<a href="#callOnAddChgEditId" onclick="javascript:edit_add_chg_row('+recordNo+');"><i class="fa fa-pencil" aria-hidden="true"></i></a>'
						                    	+'<a href="#" onclick="javascript:remove_add_chg_row('+recordNo+');"><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
						                    +'</div>'
						                +'</div>'
						            +'</div>'
						            +'<div class="content">'
						                +'<div class="cust-con" id="addChgAmtForEdit_'+recordNo+'">'
						                    +'<p id="additionalChargeAmount_'+recordNo+'" >Amount : '+additionalChargeAmount+' </p>'
						                    
						                +'</div>'	
						            +'</div>'
						            +'<input type="hidden" id="additionalChargeId-'+recordNo+'" name="" value="'+additionalChargeId+'">'
						            +'<input type="hidden" id="additionalChargeName-'+recordNo+'" name="" value="'+additionalChargeName+'">'
						            +'<input type="hidden" id="additionalChargeAmount-'+recordNo+'" name="" value="'+additionalChargeAmount+'">'
						            +'<input type="hidden" id="additionalChargeAmountFixed-'+recordNo+'" name="" value="'+additionalChargeAmountFixed+'">'						            
       
        	);
        	
    	    //dynamic generate accordion - End
        	openCloseAddChargesAccordion(addChgRowNum);
        	resetAdditionalChargesValues();
    	}
    	
    	
    	
    });	

}

function openCloseAddChargesAccordion(rowNum){
	var currId = "/"+rowNum;
	
	if(addChgAccordionsId.includes(currId)){
		//donot add in accordion
	}else{
		$("#add_chg_start_"+rowNum+" .content").hide();
		$("#add_chg_start_"+rowNum+" .heading").click(function () {
			$(this).next(".content").slideToggle();
		});
		addChgAccordionsId = addChgAccordionsId +","+currId;
	}
	
}

function resetAdditionalChargesValues(){
	$('#additionalChargeName').val("");
	$('#additionalChargeAmountToShow').val("");
	
}

function edit_add_chg_row(recordNo){
	
	$("#add_chg_add").hide();//Hide the add button
	$("#add_chg_edit").show();//display the edit button
	
	$('#additionalChargeName').val($('#additionalChargeId-'+recordNo).val()+"{--}"+$('#additionalChargeName-'+recordNo).val()+"{--}"+parseFloat($('#additionalChargeAmountFixed-'+recordNo).val()));
	$('#additionalChargeAmountToShow').val($('#additionalChargeAmount-'+recordNo).val());
	$('#additionalChargeAmount').val($('#additionalChargeAmount-'+recordNo).val());
	$('#unqAddChgValId').val(recordNo);
	
	openCloseAddChargesAccordion(recordNo);
}

function update_add_chg_row(){
	var recordNo = $('#unqAddChgValId').val();
	var additionalChargeValue = $('select#additionalChargeName option:selected').val();
	var additionalChargeAmount = 0;
	var additionalChargeName = '';
	var additionalChargeId = 0;
	var	values = additionalChargeValue.split('{--}');
		additionalChargeId = values[0]; 
		additionalChargeName = values[1];
		//additionalChargeAmount = values[2];
		additionalChargeAmount = $('#additionalChargeAmountToShow').val();
		
	//show in display
	$('#additionalChargeName_'+recordNo).text(additionalChargeName);
	$('#additionalChargeAmount_'+recordNo).text(additionalChargeAmount);
	
	
	//set values in hidden fields
	$('#additionalChargeId-'+recordNo).val(additionalChargeId);
	$('#additionalChargeName-'+recordNo).val(additionalChargeName);
	$('#additionalChargeAmount-'+recordNo).val(additionalChargeAmount);
	
	$('#addChgAmtForEdit_'+recordNo).html("");
	$('#addChgAmtForEdit_'+recordNo).append(
			'<p id="additionalChargeAmount_'+recordNo+'" >Amount : '+additionalChargeAmount+' </p>'
	);

	
	$("#add_chg_edit").hide();//Hide the edit button
	$("#add_chg_add").show();//display the add  button  
	
	
	openCloseAddChargesAccordion(recordNo);
	resetAdditionalChargesValues();//reset form values
}

function remove_add_chg_row(recordNo){
	//alert("del"+recordNo);
	   $('#add_chg_start_'+recordNo).remove();
}