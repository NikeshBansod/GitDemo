$(document).ready(function(){
	showNote();
	
	$("#masterType").on("change", function() {
		showNote();
	});
	// Fake file upload
	document.getElementById('fake-file-button-browse').addEventListener('click', function() {
		document.getElementById('files-input-upload').click();
	});

	document.getElementById('files-input-upload').addEventListener('change', function() {
		document.getElementById('fake-file-input-name').value = this.value;
		
		document.getElementById('fake-file-button-upload').removeAttribute('disabled');
	});
	
	
	
	
});

function showNote(){
	var masterType=$('#masterType').val();
	if(masterType=='customermastertemplate'){
		$("#noteMsg").text('Note: GSTIN should be entered manually for registered customer.');
		$("#noteMsg").show();
	}else{
		$("#noteMsg").text('Note: For Unit of Measurement please refer sheet 2.');
		$("#noteMsg").show();
	}
}


function downloadExcelTemplate(){
	var masterType=$('#masterType').val();
	var action='downloadmastertemplate';
	var csrf=$("#_csrf_token").val();	
	var form='<form action="'+action+'" method="get">'+'<input type="hidden" name="templatename" value="'+masterType+'">'+'<input type="hidden" id="_csrf_token" name="_csrf_token" value="'+csrf+'" />'+'</form>';
	$(form).appendTo('body').submit().remove();
}

function uploadMasterExcel(){
	if($("#files-input-upload").val()==''){
		bootbox.alert("Select Input Excel File.");
		return false;
	}
	$('#hiddenMasterType').val($('#masterType').val());
	 
	var formData = new FormData($('#uploadMaster')[0]);	
	$("#fake-file-button-upload").prop('disabled', true);
	$.ajax({
	    url: "uploadmasterexceldata",
		headers: {
			_csrf_token : $("#_csrf_token").val()
		},
	    type: "POST",
	    dataType: 'text',
	    contentType: false,
	    processData: false,
	    cache: false,
	    data: formData,
	    beforeSend: function(){
	    	$("#loadingmessage").show();
	    	$('.inside-cont').hide();
	    	$('.lk-toggle-nav').hide();
	    },
	    complete: function(){
	    	$("#loadingmessage").hide();
	    	$('.lk-toggle-nav').show();
	    	$('.inside-cont').show();
	    },
	    success: function(response,fTextStatus,fRequest) {
	    	if (isValidSession(response) == 'No') {
				window.location.href = getDefaultWizardSessionExpirePage();
				return;
			}
			
			if(isValidToken(response) == 'No'){
				window.location.href = getWizardCsrfErrorPage();
				return;
			}
	    	bootbox.alert(response);
	    	$('#fake-file-input-name').prop('readonly',false);
	    	$("#fake-file-input-name").val('');
	    	$('#fake-file-input-name').prop('readonly',true);
	    	$("#files-input-upload").val('');	        
	        $("#fake-file-button-upload").prop('disabled', false);
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	    },
	    error: function(response,fTextStatus,fRequest) {
	    	bootbox.alert('ERROR: unable to upload files');
	    	$('#fake-file-input-name').prop('readonly',false);
	    	$("#fake-file-input-name").val('');
	    	$('#fake-file-input-name').prop('readonly',true);
	    	$("#files-input-upload").val('');
	        $("#fake-file-button-upload").prop('disabled', false);
			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
	    }
	});
}