var jsonDashBoardResponse = '';

$(document).ready(function (){
	 
       $("#table").click(function (){
              $("#dashboardreport").show();
              $("#piechart").hide();
              $("#columnchart").hide();
              $("#radioButtons").hide();
              if(checkForJsonLength() > 0){
                     showChartsDynamic("","defaultTable","");
              }
       });
       
 /*       $("#chart").click(function () {
              $("#piechart").show();
              $("#dashboardreport").hide();
              $("#columnchart").hide();
              if(checkForJsonLength() > 0){
                     showChartsDynamic("Piechart","","");
              }
       });
       
        $("#column").click(function () {
              $("#columnchart").show();
              $("#dashboardreport").hide();
               $("#dashboardreport").html('');
              $("#piechart").hide();
              if(checkForJsonLength() > 0){
                     showChartsDynamic("","","Barchart");
              }
              
       });*/
       
      
       
    var onlyMonth = $("#onlyMonth").val();
       var onlyYear = $("#onlyYear").val();   
       var traverseFrom = $("#traverseFrom").val();
       if(traverseFrom == "gotoDashboard"){
              //alert("onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);
              getDataList(onlyMonth, onlyYear);
              $("#table").click();
              $("#radioButtons").show();

       }
       loadMonthList(onlyMonth);
       loadYearList(onlyYear);

       $("#calculate").click(function(){
              var startmonth =$("#startmonth").val();
              var years =$("#years").val();
              if(startmonth == null){
                     bootbox.alert("Please select the month");
              }else{
                  getDataList(startmonth, years);
                  $("#table").click();
              }
       });


});

function getDataList(startmonth, years){
       var inputData={
    		   "startdate" : startmonth,
               "years" : years,
               "desktopinvoiceCount":0,
               "invoiceCount" : 0,
               "mobileinvoiceCount":0,
               "onlyMonth":"",
               "onlyYear":"",
               "GenericEwaybillCount" : 0,
               "mobilegenricEWaybillCount" : 0,
               "desktopgenricEWaybillCount" : 0,
               "wizardapplicationgenricEWaybillCount" : 0,
               "InvoiceEwaybillCount":0,
               "mobileinvoiceEWaybillCount":0,
               "desktopinvoiceEWaybillCount":0,
       
              }
       
       $.ajax({
              url : "igetDashboardDataAjax",
              method : "post",
              headers: {
                     _csrf_token : $("#_csrf_token").val()
              },
              data : JSON.stringify(inputData),
              contentType : "application/json",
              dataType : "json",
              async : false,
              success:function(json,fTextStatus,fRequest){
                     
                     if (isValidSession(json) == 'No') {
                           window.location.href = getDefaultSessionExpirePage();
                           return;
                     }
                     
                     if(isValidToken(json) == 'No'){
                           window.location.href = getCsrfErrorPage();
                           return;
                     }
                     setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                           
                     jsonDashBoardResponse = json;
                     
                           
              },
        error: function (data,status,er) {
              
                     getInternalServerErrorPage();   
        }
       });
       
}

/*function showAllRecords(startdate,enddate,getInvType,onlyMonth,onlyYear){
       
       document.invoiceCount.action = "./showAllRecordsList";
       document.invoiceCount.startdate.value = startdate;
       document.invoiceCount.enddate.value = enddate;
       document.invoiceCount.getInvType.value = getInvType;
       document.invoiceCount.onlyMonth.value = onlyMonth;
       document.invoiceCount.onlyYear.value = onlyYear;
       document.invoiceCount._csrf_token.value = $("#_csrf_token").val();
       document.invoiceCount.submit();  
}*/

function loadMonthList(selectedMonth){
    $.ajax({
       url : "igetMonthList",
       method : "GET",
              dataType : "json",
              headers: {
                     _csrf_token : $("#_csrf_token").val()
              },
              async : false,
              success:function(json,fTextStatus,fRequest){
                     if (isValidSession(json) == 'No') {
                           window.location.href = getDefaultSessionExpirePage();
                           return;
                     }
                     
                     if(isValidToken(json) == 'No'){
                           window.location.href = getCsrfErrorPage();
                           return;
                     }
                     setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                     
                     $("#startmonth").empty();
                     
                     $("#startmonth").append('<option value="00" disabled selected>Select Month</option>');
                     $.each(json,function(i,value) {
                           if(selectedMonth == value.monthVal){
                                    $('#startmonth').append($('<option>').text(value.monthDesc).attr('value', value.monthVal).attr('selected','selected')); 
                            }else{
                                  $("#startmonth").append($('<option>').text(value.monthDesc).attr('value',value.monthVal));
                           }
                     });
                     
         },
         error: function (data,status,er) {
              
              getInternalServerErrorPage();   
         }
       }); 
}

function loadYearList(selectedMonth){
    $.ajax({
       url : "igetYearList",
       method : "GET",
              dataType : "json",
              headers: {
                     _csrf_token : $("#_csrf_token").val()
              },
              async : false,
              success:function(json,fTextStatus,fRequest){
                     if (isValidSession(json) == 'No') {
                           window.location.href = getDefaultSessionExpirePage();
                           return;
                     }
                     
                     if(isValidToken(json) == 'No'){
                           window.location.href = getCsrfErrorPage();
                           return;
                     }
                     setCsrfToken(fRequest.getResponseHeader('_csrf_token'));
                     
                     $("#years").empty();
                     
                     $.each(json,function(i,value) {
                           if(selectedMonth == value){
                                  $('#years').append($('<option>').text(value).attr('value', value).attr('selected','selected')); 
                            }else{
                                  $("#years").append($('<option>').text(value).attr('value',value));
                           }
                     });
         },
         error: function (data,status,er) {
              
              getInternalServerErrorPage();   
         }
       }); 
}

function checkForJsonLength(){
       var count = 1;
       var json = jsonDashBoardResponse;
       if(json.totalinvoiceCount == 0 &&  json.desktopinvoiceCount==0 && json.mobileinvoiceCount==0 ){
         bootbox.alert("All Records are empty");
         /*$("#piechart").empty();
         $("#columnchart").empty();*/
         $("#dashboardreport").empty();
         count = 0;
    }
       return count;
}

function showChartsDynamic(table){
       
       var options;
     var chart;
     var json = jsonDashBoardResponse;
   
       $("#radioButtons").hide();
                     //start for pie chart and column chart
            /* google.charts.load('current', {'packages':['corechart']});
             google.charts.setOnLoadCallback(drawChart);*/
       $("#dashboardreport").empty();
       $("#dashboardreport").append(
    		         '<thead>'
                     +'<tr>'
                     +'<th><center>Invoice<center></th>'
                     +'<th> <center>Total Count<center></th>'
                     +'</tr>'
                     +'</thead>' 
                     + '<tr>'
                     +'<td>Total Generated Invoice</td>'
                     +'<td><a >'+json.totalinvoiceCount+'</a>'
                     +'</tr>'
                     + '<tr>'
                     +'<td>Invoice generated Through Mobile</td>'
                     +'<td><a>'+json.mobileinvoiceCount+'</a>'
                     +'</tr>'
                     +'<tr>'
                     +'<td> Invoice generated Through Desktop </td>'
                     +'<td><a>'+json.desktopinvoiceCount+'</a></td>'
                     +'</tr>'
                     +'<tr>'
                     +'<br>'
                     +'<br>'
                     +'<th><center>Ewaybill<center></th>'
                     +'<th> <center>Total Count<center></th>'
                     +'</tr>'
                     +'</thead>' 
                     + '<tr>'
                     +'<td>Total Generic Ewaybill</td>'
                     +'<td><a>'+json.GenericEwaybillCount+'</a>'
                     +'</tr>'
                     +'<tr>'
                     +'<td> Generic Ewaybill Through Mobile </td>'
                     +'<td><a >'+json.MobileGenericEwaybillCount+'</a></td>'
                     +'</tr>'
                     +'<tr>'
                     +'<tr>'
                     +'<td> Generic Ewaybill Through Desktop </td>'
                     +'<td><a>'+json.DesktopGenericEwaybillCount+'</a></td>'
                     +'</tr>'
                     +'<tr>'
                     +'<tr>'
                     +'<td> Generic Ewaybill Through Wizard Application </td>'
                     +'<td><a>'+json.WizardGenericEwaybillCount+'</a></td>'
                     +'</tr>'
                     +'<tr>'
                     + '<tr>'
                     +'<td>Total Ewaybill Through Invoice</td>'
                     +'<td><a href="#">'+json.InvoiceEwaybillCount+'</a>'
                     +'</tr>' 
                     +'<td>  Ewaybill Through Invoice using Mobile</td>'
                     +'<td><a>'+json.MobileInvoiceEwaybillCount+'</a>'
                     +'</tr>'
                     +'<td>Ewaybill Through Invoice using Desktop</td>'
                     +'<td><a >'+json.DesktopInvoiceEwaybillCount+'</a>'
                     +'</tr>'
                   
                     

       );
     /*  $("#dashboardreport").append(
		         '<thead>'
               +'<tr>'
               
               

 );*/

             
              
              //} //end for pie chart and column chart
}
