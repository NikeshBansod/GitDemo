$(document).ready(function (){
       
       var onlyMonth = $("#onlyMonth").val();
       var onlyYear = $("#onlyYear").val();   
       var traverseFrom = $("#traverseFrom").val();
       if(traverseFrom == "gotoWizardDashboard"){
              //alert("onlyMonth: "+onlyMonth+" onlyYear: "+onlyYear);
          var onlyMonthforBack = $("#onlyMonthforback").val();
           var onlyYearforBack = $("#onlyYearforback").val();   
              getDataList(onlyMonthforBack, onlyYearforBack);
              loadMonthList(onlyMonthforBack);
              loadYearList(onlyYearforBack);

       }
       else{
       loadMonthList(onlyMonth);
       loadYearList(onlyYear);
       }
       
});
$("#calculate").click(function(){
       var startmonth =$("#startmonth").val();
       if(startmonth == null)
       {
       bootbox.alert("Please select the month");
       }
       
       else
              {
    var startmonth =$("#startmonth").val();
       var years =$("#years").val();
       getDataList(startmonth, years);
              }
       

       
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
              url : "getWizardDashboardDataAjax",
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
          if(json.invoiceEWaybillCount == 0 &&  json.eWaybillCount==0 && json.cndnCount==0 && json.invoiceCount==0)     {
              bootbox.alert("All Records are empty");
              $("#dashboardreport").empty();
              $("#chartdiv").empty();
          }
          
          else{
          
         
          
                     /*$.each(json,function(i,value){*/
                     $("#dashboardreport").empty();
                     
                           $("#dashboardreport").append(
                                         '<tr>'
                                         +'<th>Report Type</th>'
                                         +'<th> Total Count</th>'
                                         +'</tr>'
                                         + '<tr>'
                                         +'<tr>'
                        +'<td>E-way Bill through Invoice</td>'
                        +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'invoiceewaybill\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.invoiceEWaybillCount+'</a>'
                        +'</tr>'
                        
                        + '<tr>'
                        +'<td>Generic E-way Bill</td>'
                        +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'ewaybill\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.eWaybillCount+'</a>'
                        
                        +'</tr>'
                        +'<tr>'
                        +'<td> CNDN </td>'
                        +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'cndn\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.cndnCount+'</a></td>'
                        
                        +'</tr>'

                        +'<tr>'
                        +'<td> Invoice </td>'
                        +'<td><a href="#" onclick="javascript:showAllRecords(\''+json.startdate+'\',\''+json.enddate+'\',\'invoice\',\''+json.onlyMonth+'\',\''+json.onlyYear+'\');">'+json.invoiceCount+'</a></td>'
                        +'</tr>'
                                  
                                  
       
                           );
                           $("#chartdiv").empty();
                           google.charts.load("current", {packages:["corechart"]});
                           google.charts.setOnLoadCallback(drawChart);
                           function drawChart() {
                             var data = google.visualization.arrayToDataTable([
                               ['Language', 'Speakers (in millions)'],
                               ['E-Way Bill Through Invoice',  json.invoiceEWaybillCount],
                               ['Generic E-Way Bill ',  json.eWaybillCount],
                               ['Credit/Debit Note', json.cndnCount],
                               ['Invoice', json.invoiceCount]
                             ]);

                           var options = {
                            is3D: true,
                             pieSliceText: 'none',
                             tooltip: {
                                 text: 'value'
                             },
                             
                             legend : {
                                       
                                               position: 'labeled',
                                          labeledValueText: 'value',
                                          textStyle: {
                                                    color: 'blue', 
                                                     fontSize: 11.5,
                                                     
                                                    
                                                     },
                                             }, 
                                     
                           };
                            
                           
                             var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
                             chart.draw(data, options, { backgroundColor: { fill: "blue" } });
                           }
                           $(window).resize(function(){
                              drawChart();
                              drawChart();
                            });
                           
              
          }                                  
                           
              },
              error: function (data,status,er) {
             
             getInternalServerErrorPage();   
       }
       });

}



       function showAllRecords(startdate,enddate,getInvType,onlyMonth,onlyYear){
       
       document.invoiceCount.action = "./showAllWizardRecordsList";
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
              url : "getWizardMonthList",
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
           url : "getWizardYearList",
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



function previewWizardCNDN(idValue){
       document.manageInvoice.action = "./getWizardDashboardCnDn";
       document.manageInvoice.id.value = idValue;
       document.manageInvoice._csrf_token.value = $("#_csrf_token").val();
       document.manageInvoice.submit();
}


function viewEwaybillRecord(idValue,ewayBillNo){
       document.invoice.action = "./";
       document.invoice.ewayBillNo.value = ewayBillNo;
       document.invoice.id.value = idValue;
    document.invoice.submit();
}


/*function goBackFromRespectivePage(startdate,enddate,getInvType){
       //alert("error: "+startdate+" status: "+enddate+" type: "+getInvType);
       document.redirectToBack.action = "./showAllRecordsList";
       document.redirectToBack.startdate.value = startdate;
       document.redirectToBack.enddate.value = enddate;
       document.redirectToBack.getInvType.value = getInvType;
       document.redirectToBack.submit();       
}*/
//for CNDN invoice PDF show
/*function getCnDnDetails(idValue,iterationNo,cId,cndnNumber){
       alert("error: "+idValue+" status: "+iterationNo+" type: "+cndnNumber);
       document.cndn.action = "./getCNDNPreviewDetails";
       document.cndn.id.value = idValue;
       document.cndn.iterationNo.value = iterationNo;
       document.cndn.cndnNumber.value = cndnNumber;
       document.cndn.cId.value = cId;
       document.cndn.submit();
}*/
//from CNDN preview page to CNDN list in dashboard
function goBackFrompreview(startdate,enddate,getInvType){
       //alert("error: "+startdate+" status: "+enddate+" type: "+getInvType);
       document.redirectToBackFromCNDN.action ="./showAllRecordsList";
       document.redirectToBackFromCNDN.startdate.value = startdate;
       document.redirectToBackFromCNDN.enddate.value = enddate;
       document.redirectToBackFromCNDN.getInvType.value = getInvType;
       document.redirectToBackFromCNDN.submit();     
}

function getPreviewOfEwayBillDetails(invoiceId,ewaybillNo){
       
       document.cndn.action = "./getEwayPreviewDetails";
       document.cndn.id.value = invoiceId;
       document.cndn.ewayBillNo.value = ewaybillNo;
       
       document.cndn.submit();
}

