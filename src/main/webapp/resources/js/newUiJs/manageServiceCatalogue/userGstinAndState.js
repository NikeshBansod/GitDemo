/*var gstinJson='';

$(document).ready(function(){
	$('#showProductGrid').hide();
      var gstinJsonForUserData = fetchGstinDataForUserData();
    
});



function fetchGstinDataForUserData(){
      
      
         $.ajax({
                  url : "getgstinforloggedinuser",
                  method : "POST",
                  contentType : "application/json",
                  dataType : "json",
                  async : false,
                  headers: {
          			_csrf_token : $("#_csrf_token").val()
          	       },
                  success:function(json,fTextStatus,fRequest){
                        if (isValidSession(json) == 'No') {
                              window.location.href = getDefaultSessionExpirePage();
                              return;
                        }
                        
                        if(isValidToken(json) == 'No'){
                              window.location.href = getCsrfErrorPage();
                              return;
                        }
                        gstinJson=json.result;
                        $("#gstnStateId").empty();
                        $("#location").empty();
                        if(json.length == 1){
                              $("#gstnStateIdDiv").hide();
                              $.each(json,function(i,value) {
                                    $("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.state));
                              });
                              
                              //if location length is 1 hide location else show location dropdown
                              if(json[0].gstinLocationSet.length == 1){
                                    
                                    $.each(json[0].gstinLocationSet,function(i,value) {
                                       $("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
                                          $("#locationStore").val(value.gstinStore);
                                    });
                                    
                              }else{
                                    $("#locationDiv").show();
                                    $("#location").append('<option value="">Select</option>');
                                    $.each(json[0].gstinLocationSet,function(i,value) {
                                       $("#location").append($('<option>').text(value.gstinLocation).attr('value',value.id));
                                    });
                              }
                        }else{
                              $("#gstnStateIdDiv").show();
                              $("#gstnStateId").append('<option value="">Select</option>');
                              $.each(json.result,function(i,value) {
                                    $("#gstnStateId").append($('<option>').text(value.gstinNo+' [ '+value.stateInString +' ] ').attr('value',value.id));
                              });
                        }
                        setCsrfToken(fRequest.getResponseHeader('_csrf_token'));    
               },
               error: function (data,status,er) {
                   
                   getInternalServerErrorPage();   
               }
            });
}

$("#gstnStateId").on("change",function (){
	$('#calculateStock').prop('disabled', false);	
	
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#openingstockInventoryTab").DataTable().rows().remove();
	}
     
      var gstinSelectedId= $('select#gstnStateId option:selected').val();
      $("#location").empty();
      $("#location").append('<option value="">Select</option>');
      $.each(gstinJson,function(i,value) {
            if(value.id == gstinSelectedId ){
                  $.each(value.gstinLocationSet,function(i2,value2) {
                      $("#location").append($('<option>').text(value2.gstinLocation).attr('value',value2.id));
                  });               
                  
            }
      });
      
      
});
$("#location").on("change",function (){
	$('#calculateStock').prop('disabled', false);
	if($('#showProductGrid').is(':visible')){
		$('#showProductGrid').hide();
		$("#openingstockInventoryTab").DataTable().rows().remove();
	}
});
*/