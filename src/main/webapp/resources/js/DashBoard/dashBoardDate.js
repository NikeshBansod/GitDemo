var jsonDashBoardResponse = '';

$(document).ready(function (){
       
       $("#table").click(function (){
              $("#dashboardreport").show();
              $("#piechart").hide();
              $("#columnchart").hide();
              if(checkForJsonLength() > 0){
                     showChartsDynamic("","defaultTable","");
              }
       });
       
        $("#chart").click(function () {
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
              /* $("#dashboardreport").html('');*/
              $("#piechart").hide();
              if(checkForJsonLength() > 0){
                     showChartsDynamic("","","Barchart");
              }
              
       });
       
       $("#radioButtons").hide();
       
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
                  $("#chart").click();
              }
       });


});

function getDataList(startmonth, years){
       var inputData={
                     "startdate" : startmonth,
                     "years" : years,
                     "eWaybillCount" : 0,
                     "cndnCount" : 0,
                     "invoiceCount" : 0,
                     "invoiceEWaybillCount":0,
                     "onlyMonth":"",
                     "onlyYear":"",
       
              }
       
       $.ajax({
              url : "getDashboardDataAjax",
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

function showAllRecords(startdate,enddate,getInvType,onlyMonth,onlyYear){
       
       document.invoiceCount.action = "./showAllRecordsList";
       document.invoiceCount.startdate.value = startdate;
       document.invoiceCount.enddate.value = enddate;
       document.invoiceCount.getInvType.value = getInvType;
       document.invoiceCount.onlyMonth.value = onlyMonth;
       document.invoiceCount.onlyYear.value = onlyYear;
       document.invoiceCount._csrf_token.value = $("#_csrf_token").val();
       document.invoiceCount.submit();  
}

function loadMonthList(selectedMonth){
    $.ajax({
       url : "getMonthList",
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
       url : "getYearList",
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
       if(json.invoiceEWaybillCount == 0 &&  json.eWaybillCount==0 && json.cndnCount==0 && json.invoiceCount==0){
         bootbox.alert("All Records are empty");
         $("#piechart").empty();
         $("#columnchart").empty();
         $("#dashboardreport").empty();
         count = 0;
    }
       return count;
}

function showChartsDynamic(pie,table,column){
       
       var options;
     var chart;
     var json = jsonDashBoardResponse;
   
       $("#radioButtons").show();
                     //start for pie chart and column chart
             google.charts.load('current', {'packages':['corechart']});
             google.charts.setOnLoadCallback(drawChart);

             function drawChart() {

               var data = google.visualization.arrayToDataTable([
                 ["", "", { role: 'style' },{ role: 'annotation' }],
                 ["Ewaybill Through Invoice", json.invoiceEWaybillCount,"#3366CC",json.invoiceEWaybillCount],
                 ["Generic Eway Bill",json.eWaybillCount,"#DC3912",json.eWaybillCount],
                 ["Document", json.invoiceCount,"#FF9900",json.invoiceCount],
                 ["CNDN",json.cndnCount,"#109618",json.cndnCount],
                
               
               ]);
              if(pie == 'Piechart'){
                 options = {
                             pieSliceText: 'none',
                             sliceVisibilityThreshold:0,
                             chartArea:{left:10,right:10,top:20,width:"100%",height:"100%"},
                             pieStartAngle: 40,
                                is3D: true,
                               legend: {
                                   position: 'labeled',
                                   labeledValueText: 'value',
                                   textStyle: {
                                             color: 'blue', 
                                              fontSize: 11.5,
                                              },
                                      },
                                      tooltip: {
                                          text: 'value',
                                      },
                  };
                  chart = new google.visualization.PieChart(document.getElementById('piechart'));
                  chart.draw(data, options); 
                  $(window).resize(function() {
                            drawChart();
                  });
              }
              if(column == 'Barchart') {
                
                 options = {
                             bar: {groupWidth: "60%"},
                             legend: {position: 'none'},
                                chartArea:{left:40,right:40,top:20,bottom:40,width:"100%",height:"100%"},
               };
                  chart = new google.visualization.ColumnChart(document.getElementById('columnchart'));
                  chart.draw(data, options);
                  $(window).resize(function() {
                            drawChart();
                        });
               }
              if(table == 'defaultTable') {
                  $("#dashboardreport").empty();
                           $("#dashboardreport").append(
                        		         '<thead>'
                                         +'<tr>'
                                         +'<th>Report Type</th>'
                                         +'<th> Total Count</th>'
                                         +'</tr>'
                                         +'</thead>' 
                                         + '<tr>'
                                         +'<td>Eway Bill through Invoice</td>'
                                         +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'invoiceewaybill\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.invoiceEWaybillCount+'</a>'
                                         +'</tr>'
                                         + '<tr>'
                                         +'<td>Generic Eway Bill</td>'
                                         +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'ewaybill\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.eWaybillCount+'</a>'
                                         +'</tr>'
                                         +'<tr>'
                                         +'<td> CNDN </td>'
                                         +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'cndn\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.cndnCount+'</a></td>'
                                         +'</tr>'
                                         +'<tr>'
                                         +'<td> Documents </td>'
                                         +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'invoice\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.invoiceCount+'</a></td>'
                                         +'</tr>'
       
                           );
              }
              
               
             }
              
              //} //end for pie chart and column chart
}
