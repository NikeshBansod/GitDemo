$(document).ready(function(){
    $('.loader').fadeOut("slow"); 
       var selOrgType = $("#orgTypeHidden").val(); 
       var selState = $("#stateHidden").val(); 
       var orgTypeDisplay=$("#orgType option:selected").text(); 
       loadOrgTypeListList(selOrgType,orgTypeDisplay);
       //loadStateByPincode();
       if($("#reg-gstin").val()!=""){
              //getStateByGstinNumber($("#reg-gstin").val());
       }
       manageOrgType();    
});

function manageOrgType(){
       if ($("#orgtype option:selected").text() === "Others"){
              $("#divOtherOrgType").show();
       }else{
              $("#divOtherOrgType").hide();
              $("#otherOrgType").val("");
       }
       
}

function loadOrgTypeListList(selOrgType,orgTypeDisplay){
       $.ajax({
                method:"GET",
                url:"getOrganizationTypeList",        
              //  contentType:'application/json',
                async : false,
                dataType: 'json',
                success:function(json,fTextStatus,fRequest) {
                           $("#orgType").append('<option value="">Select Firm Type</option>');
                       $.each(json, function(i, value) {
                           
                            if(selOrgType==value.id){
                                    $('#orgType').append($('<option>').text(value.orgType).attr('value', value.id).attr('selected','selected')); 
                                    if(selOrgType=="15"){
                                           $("#divOtherOrgType").show();
                                         }else{
                                           $("#divOtherOrgType").hide();
                                         }
                           }else{
                                  $('#orgType').append($('<option>').text(value.orgType).attr('value', value.id));
                           //     $("#divOtherOrgType").hide();
                           }
                                    
                           });
                       
                       setCsrfToken(fRequest.getResponseHeader('_csrf_token')); 
                }
       });
       
}



/*
function getStateByGstinNumber(gstinNumber){
       $("#reg-gstin-state").empty();
                           $.ajax({
                                  url : "getStateByGstinNumber" ,
                                  type : "post",
                                  data : {"id":gstinNumber},
                                  dataType : "json",
                                  async : false,
                                  success : function(response) {
                                         if(response==null){
                                                       $("#state").val("");
                                         }else{
                                                $("#reg-gstin-state").append($('<option>').text(response[0].stateName).attr('value',response[0].stateId))
                                                
                                         }
                                         
                                         
                                  },
                           error: function(xhr, textStatus, errorThrown){
                                  bootbox.alert('request failed');
                                 
                               }
                           });


       }
*/

function is_int(value) {
       if ((parseFloat(value) == parseInt(value)) && !isNaN(value)) {
              return true;
       } else {
              return false;
       }
}



function getDeviceOs(){
       var deviceOS;
       var client = new ClientJS(); 
       // Create A New Client Object 
       var browser = client.getBrowser();
       var isMobileAndroid = client.isMobileAndroid();
       //alert("browser :"+browser);
       //alert("isMobileAndroid :"+isMobileAndroid);
       var isMobileWindows = client.isMobileWindows();
       //alert("isMobileWindows :"+isMobileWindows);
       var isMobileIOS = client.isMobileIOS();
       //alert("isMobileIOS :"+isMobileIOS);
       var isIpad = client.isIpad();
       var isIphone = client.isIphone();
       
       if(isMobileAndroid){
              deviceOS="Android"
       }
       else if(isMobileWindows){
              deviceOS="Windows"
       }
       else if(isMobileIOS){
              deviceType="IOS"
       }
       else if(isIpad){
              deviceOS="IPAD"
       }
       else if(isIphone){
              deviceOS="isIphone"
       }
       return deviceOS;
}

function getClientDeviceType(){
       var client = new ClientJS(); 
       var deviceType = client.getDeviceType(); 
       if(deviceType==null){
              deviceType="Desktop";
       }
       return deviceType;
}
