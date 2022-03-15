jQuery(document).ready(function($){
	$('.loader').show();
	$(".user-drop").hide(); 
	getNotificationList();
	$("#okButton").click(function () {
		$("#notificationDiv").hide();
		$("#homeDiv").show();
	});
	
	$("#closeButton").click(function () {
		$("#notificationDiv").hide();
		$("#homeDiv").show();
	});
	
	$("#callNotification").click(function () {
		$("#notificationDiv").show();
		$("#homeDiv").hide();
	});
	$(".loader").fadeOut("slow");
            
});

function getNotificationList(){
	$.ajax({
		url : "getNotificationList",
	    type : "POST",
	    dataType : "json",
	    async : false,
	    beforeSend: function(){
	         $('.loader').show();
	     },
	     complete: function(){
	         $('.loader').hide();
	     },
	    success:function(json){
	    	$.each(json,function(i,value){
	    		
	    		$('#custNotificatn').append(
	    			 '<div class="notifiCard card">'
	                    +'<a href="#!">'
	                        //+'<i class="fa fa-angle-right"></i>'
	                        +'<p>'+value.notification+'</p>'
	                        //+'<span class="timeStamp">'+value.updatedOn+'</span>'
	                    +'</a>'
	                +'</div>'
	    		);
	    	});
	    }
	}); 
}


