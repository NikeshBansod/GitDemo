 
jQuery(document).ready(function($){  
	
	var blankMsg="This field is required";
	var length = 2;
	var lengthMsg = "Minimum length should be ";
	var i=0;
	
	$("#feedbackSubmit").click(function(e){
		var blankMsg="This field is required";
		 var errFlag1 = validateMaster();
		 var errFlag2 = validateFeedbackQuery();
		
		 if ((errFlag1) || (errFlag2)){
			 e.preventDefault();	 
		 } 
		 	
	});

	function validateMaster(){
		errFlag1 = validateSelectField("masterDesc","master-desc");
		 return errFlag1;

	}
	
	function validateFeedbackQuery(){
		
		errFlag2 = validateTextField("feedbackDesc","feedback-query-req",blankMsg);
		if(!errFlag2){
			errFlag2=validateFieldLength("feedbackDesc","feedback-query-req",lengthMsg,length);
		 }
		 return errFlag2;
	 }
	
	
$.ajax({
		url : "getMasterDescList",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
		type : "POST",
		dataType : "json",
		success:function(json,fTextStatus,fRequest){
			$("#masterDesc").empty();
			
			$("#masterDesc").append('<option value="">Select Area of feedback</option>');
			
			if (isValidSession(json) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(json) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
			
			$.each(json,function(i,value) {
				$("#masterDesc").append($('<option>').text(value.masterDesc).attr('value',value.id));
			});
			
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
			
         },
         error: function (data,status,er) {
        	 
        	 getWizardInternalServerErrorPage();   
        }
	}); 

/*$(function()
		$("#files-input-upload").click(function(){
document.getElementById('files-input-upload').addEventListener('change', function() {
	document.getElementById('fake-file-input-name').value = this.value;
	
	document.getElementById('fake-file-input-name').removeAttribute('disabled');
});*/



/*$(function()
{
       var i=1;
       $(document).on('click', '#file1', function(e){       
              document.getElementById('file1').addEventListener('change', function() {
                     if ($(this).val()) {                   
                           $('.btn-add').attr('disabled', false);
                     }
              });
       });
       
       $(document).on('click', '.btn-add', function(e){       
           var childCount = $(".controls").children().length;
           if(childCount<3){
                  i++;
                   e.preventDefault();
                   var controlForm = $('.controls:first'),
                   currentEntry = $(this).parents('.entry:first'),
                   newEntry = $(currentEntry.clone()).appendTo(controlForm);
           
                   newEntry.find('input').val('').attr("id","file"+i);
                   newEntry.find('button').attr("id","button"+i);
                   $('#file'+i).attr('disabled', false);
                   
                   controlForm.find('.entry:not(:last) .btn-add')
                       .removeClass('btn-add').addClass('btn-remove')
                       .removeClass('btn-success').addClass('btn-danger')
                       .html('<span class="glyphicon glyphicon-minus"></span>');
                        
                 $('.btn-remove').attr('disabled',false);
                 $('.btn-add').attr('disabled',true);
                  }else{
                  bootbox.alert("You can upload Maximum 3 Images.")
                  }
        }).on('click', '.btn-remove', function(e){
           $(this).parents('.entry:first').remove();
         
           if ($(this).val()){              
                          $('.btn-add ').attr('disabled', false);
                  }
                  e.preventDefault();
                  return false;
           });
       
        
        $(document).on('click', '.controls .entry .input-group input', function(e){  
           var zi=$(this).attr("id").substring(4);
           //console.log("zi"+zi);
           //console.log("xyz"+$(this).attr("id"));
           
                  document.getElementById('file'+zi).addEventListener('change', function() {
                         if($(this).val() == ''){   
                             $('#button'+zi+'.btn-add ').attr('disabled', true);
                         }else{
                               $('#button'+zi+'.btn-add ').attr('disabled', false);
                         }
                  });
        }); });

       */

$(function(){ 
    
    var i=1;
 $(document).on('click', '.btn-add', function(e){       
    var childCount = $(".controls").children().length;
    if(childCount<3){
           i++;
            e.preventDefault();
            var controlForm = $('.controls:first'),
            currentEntry = $(this).parents('.entry:first'),
            newEntry = $(currentEntry.clone()).appendTo(controlForm);
    
            newEntry.find('input').val('').attr("id","file"+i);
            newEntry.find('button').attr("id","button"+i);
            $('#file'+i).attr('disabled', false);
            
            controlForm.find('.entry:not(:last) .btn-add')
                .removeClass('btn-add').addClass('btn-remove')
                .removeClass('btn-success').addClass('btn-danger')
                .html('<span class="glyphicon glyphicon-minus"></span>');
            
           
                 
          $('.btn-remove').attr('disabled',false);
          $('.btn-add').attr('disabled',true);
          
           }else{
           bootbox.alert("You can upload Maximum 3 Images.")
           }
 }).on('click', '.btn-remove', function(e){
    var childCount = $(".controls").children().length;
    var  maxIdValue = 0;
    var maxIdInputTextValue = '';
    $(this).parents('.entry:first').remove();
  
    if ($(this).val()){              
                   $('.btn-add ').attr('disabled', false);
           }
    
    /*$(".controls .entry").each(function() {
           maxIdValue = $(".entry .input-group input").attr("id").substring(4);
           console.log("NNNNN : "+maxIdValue);
    });*/
    
    $('.controls .entry .input-group').children('input').each(function () {
           //alert($(this).attr("id").substring(4));
           maxIdInputTextValue = this.value;
           maxIdValue=$(this).attr("id").substring(4); // "this" is the current element in the loop
    });
    
    enableDisableAddButtonOnLastElement(maxIdInputTextValue,maxIdValue);
    
    /* if(maxIdInputTextValue ==''){
            $('#button'+maxIdValue).removeClass('btn-remove').addClass('btn-add')
          .removeClass('btn-danger').addClass('btn-success')
          .html('<span class="glyphicon glyphicon-plus"></span>');
            $('.btn-add ').attr('disabled', true);
     }else{
            $('#button'+maxIdValue).removeClass('btn-remove').addClass('btn-add')
          .removeClass('btn-danger').addClass('btn-success')
          .html('<span class="glyphicon glyphicon-plus"></span>');
            $('.btn-add ').attr('disabled', false);
     }*/
    
    
           e.preventDefault();
           return false;
    });

 
 $(document).on('click', '.controls .entry .input-group input', function(e){  
    var zi=$(this).attr("id").substring(4);
    var childCount1 = $(".controls").children().length;
    
    //console.log("zi"+zi);
    //console.log("xyz"+$(this).attr("id"));
    
           document.getElementById('file'+zi).addEventListener('change', function() {
                  if($(this).val() == ''){   
                      $('#button'+zi+'.btn-add ').attr('disabled', true);
                  }else{
                        if(childCount1 == 3){
                               $('#button'+zi+'.btn-add ') .removeClass('btn-add').addClass('btn-remove')
                               .removeClass('btn-success').addClass('btn-danger')
                               .html('<span class="glyphicon glyphicon-minus"></span>');
                               $('#button'+zi+'.btn-remove ').attr('disabled', false);
                        }else{
                         $('#button'+zi+'.btn-add ').attr('disabled', false);
                        }
                        
                  }
           });
 }); 
});

function enableDisableAddButtonOnLastElement(maxIdInputTextValue,maxIdValue){
    if(maxIdInputTextValue ==''){
           $('#button'+maxIdValue).removeClass('btn-remove').addClass('btn-add')
      .removeClass('btn-danger').addClass('btn-success')
      .html('<span class="glyphicon glyphicon-plus"></span>');
           $('.btn-add ').attr('disabled', true);
    }else{
           $('#button'+maxIdValue).removeClass('btn-remove').addClass('btn-add')
      .removeClass('btn-danger').addClass('btn-success')
      .html('<span class="glyphicon glyphicon-plus"></span>');
           $('.btn-add ').attr('disabled', false);
    }
}



/*function viewRecord(idValue,conditionValue){
	document.manageInvoice.action = "./getFeedbackDescription";
	document.manageInvoice.value = idValue;
	document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
	document.manageInvoice.submit();*/










});
