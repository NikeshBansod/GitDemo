$("#pincode").autocomplete({
    source: function (request, response) {
        $.getJSON("getPinCodeList", {
            term: extractLast(request.term)
        }, function( data, status, xhr ){
             response(data);
                     setCsrfToken(xhr.getResponseHeader('_csrf_token'));
              });
        
        if (isValidSession(response) == 'No') {
                     window.location.href = getDefaultWizardSessionExpirePage();
                     return;
              }    
    },
    minLength: 3,
    response: function(event, ui) {
        // ui.content is the array that's about to be sent to the response callback.
       var zipToShow = $("#pincode").val();
       //bootbox.alert"Blank response : zipToShow :"+zipToShow);
       
               if (ui.content.length === 0) {
                    if(zipToShow.length === 6){
                            $("#empty-message").text("No results found for selected pin code : "+zipToShow);
                            $("#empty-message").show();
                        $("#pincode").val("");
                        $("#city").val("");
                    }
               } else {
                   $("#empty-message").hide();
               }
       
    },
    select: function (event, ui) {
       var label = ui.item.label;
        var value = ui.item.value;
        var pincode = (value.split('[')[0]).trim();
        var district = value.substring(value.indexOf("[ ") + 1, value.indexOf(" ]")).trim();
     
       
         $.ajax({
             url : "getPincodeByIdAndDistrict" ,
                     type : "post",
                     data : {"id" : pincode , "district" : district, _csrf_token : $("#_csrf_token").val()},
                     dataType : "json",
                     success : function(json,fTextStatus,fRequest) {
                           setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                           if(json==null){
                                  $("#city").val("");
                                  $("#stateName").val("");
                                  $("#state").val("");
                           }else{
                                  $("#city").val(json.district);
                                  $("#stateName").val(json.stateInString);
                                  $("#state").val(json.stateId);
                                  $("#pincode").val(pincode);
                     //            $("#zip").val(pincode+"-"+json.district+"-"+json.stateInString);
                                  $("#zipCode,#pinNo").val(pincode);
                                  //$("#pincode").addClass("input-correct").removeClass("input-error");
                                  $("#zip-req").hide();
                                  
                           }      
                     },
                     error: function noData() {
                           bootbox.alert("No data found");
                     }
        }); 
         return false;
    }
});

function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}

function loadStateByPincode(){

       var pincode = $("#pincode").val();
       var district = $("#city").val();
       
         $.ajax({
      url : "getPincodeByIdAndDistrict" ,
                     type : "post",
                     data : {"id" : pincode , "district" : district, "_csrf_token" : $("#_csrf_token").val()},
                     dataType : "json",
                     success : function(json,fTextStatus,fRequest) {

                           if(json==null){
                                  $("#city").val("");
                                  $("#stateName").val("");
                                  $("#state").val("");
                           }else{
                                  $("#city").val(json.district);
                                  $("#cityToShow").val(json.district);
                                  $("#stateName").val(json.stateInString);
                                  $("#stateToShow").val(json.stateInString);
                                  $("#state").val(json.stateId);
                                  
                                  $("#zip").val(pincode);
                           
                           }
                           
                           setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                     }
      }); 

}
