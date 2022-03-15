$(document).ready(function() {           
       var clientId = $("#clientId").val(); 
       var secretKey = $("#secretKey").val(); 
       var userId = $("#userId").val();
       var appCode = $("#appCode").val();
       var ipUsr = $("#ipUsr").val();
       var jsonData = {"userId" : userId}; 
    $.ajax({
        url: 'ewaybill/getGeneratedEwayBillList',
        type : "POST",
              contentType : "application/json",
              dataType : "json",
              headers: {client_id : clientId, secret_key : secretKey, app_code : appCode,  ip_usr : ipUsr},
              data : JSON.stringify(jsonData),
              async : false,
        success : function(response) {
              if (isValidSession(response) == 'No') {
                           window.location.href = getDefaultSessionExpirePage();
                           return;
                     }
              
                     if(isValidToken(response) == 'No'){
                           window.location.href = getCsrfErrorPage();
                           return;
                     }
                     
              if(response.status == 'failure'){
                           //bootbox.alert(response.error_desc);
                     }else{
                           $.each(response, function (a, b) {
                                  var stat;
                                  if(b.ewaybillStatus=='GENEWAYBILL'){
                                         stat = '<span class="textColourAmber">Valid</span>';
                                  }else if(b.ewaybillStatus=='CANEWB'){
                                         stat = '<span class="textColourRed">Cancelled</span>';
                                  }else{
                                         stat = '<span class="textColourRed">Expired</span>';
                                  }
                                  $('#genericEwayBillTab tbody:last-child').append(
                                         '<tr>'
                                                /*+'<td>'+(a+1)+'</td>'*/
                                   +'<td><a href="#" onclick="javascript:viewWIEwayBillDetailedPage('+b.ewaybillNo+','+userId+');">'+b.ewaybillNo+'</a></td>'
                                   +'<td>'+b.ewaybill_date+'</td>' 
                                   +'<td>'+b.toTraderName+'</td>'
                                   +'<td>'+b.transModeDesc+'</td>'
                                   +'<td>'+stat+'</td>' 
                                   +'<td>'+b.ewaybill_valid_upto+'</td>'
                           +'</tr>');
                   });
                     }        
        },
        error: function (data,status,er) {              
              getInternalServerErrorPage();   
       }
    }); 

       createDataTable1('genericEwayBillTab');
       $(".loader").fadeOut("slow");
});
$(document).ready(function() {    
       


jQuery.extend( jQuery.fn.dataTableExt.oSort, {
   "date-uk-pre": function ( a ) {
       return parseDates(a);
   },

   "date-uk-asc": function ( a, b ) {
       a = parseDates(a);
       b = parseDates(b);
       return ((a < b) ? -1 : ((a > b) ? 1 : 0));
   },

   "date-uk-desc": function ( a, b ) {
       a = parseDates(a);
       b = parseDates(b);
       return ((a < b) ? 1 : ((a > b) ? -1 : 0));
   }
});
}); 

function createDataTable1(tableId){
    var table = $('#'+tableId+'').DataTable({
        colReorder: true,
              order: [],
              aoColumns: [
                             null,
                             { "sType": "date-uk" },
                             null,
                             null,
                             null,
                             { "sType": "date-uk" }
                             
                         ],
             rowReorder: {
                    selector: 'td:nth-child(0)',
                    
              },
              responsive: true
    });
}
function generateEWayBill(){
       $('.loader').show(); 
       window.location.href = "./loginGenerateEwayBill";
       $('.loader').fadeOut("slow");
}

function viewWIEwayBillDetailedPage(idValue,userId){
       $('.loader').show();
       document.previewInvoice.action = "./getpreviewgenericewaybill";
       document.previewInvoice.id.value = idValue;
       document.previewInvoice.userId.value = userId;
       document.previewInvoice.submit();
       $('.loader').fadeOut("slow");
}

function parseDates(a) {
       var frDatea = $.trim(a).split(' ');
    var frTimea = (undefined != frDatea[1]) ? frDatea[1].split(':') : [00,00,00];
    var frDatea2 = frDatea[0].split('/');
    var fhours='';
    if(frDatea[2]="PM" && frTimea[0]!="12"){
       fhours = parseInt(frTimea[0])+12;
    }
    return (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[1] + fhours + frTimea[2]) * 1;

}

