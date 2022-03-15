$(document).ready(function(){
	
	$("#addBtn").click(function(e){
		 alert(CKeditorAPI.GetInstance('notifyBody') );
	    /*if ($("#notifyBody iframe").contents().find("body").text() == "") {
	       alert("Please enter some text");
	    } else {
	    	$('form[name=custForm]').attr('action','iSubmitnotifications');
			$('form[name=custForm]').submit();
	    }*/
		

	});
	
	
	$("#cancelBtn").click(function(e){
		$("#notifyBody").val("");
		
	});
});