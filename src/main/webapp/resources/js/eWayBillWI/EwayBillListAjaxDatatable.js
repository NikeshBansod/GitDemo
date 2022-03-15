$(document).ready(function() {
    $('#example').DataTable( {
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/ewaybill/getGeneratedEwayBillList",
            "type": "POST",
            "dataType" : "json",
    		"headers": "{client_id : clientId, secret_key : secretKey, app_code : appCode, ip_usr : ipUsr}",
    		"data" : "JSON.stringify(jsonData)",
        },
        "columns": [
            { "data": "Sr_No" },
            { "data": "EwayBillNo" },
            { "data": "Status" }
        ]
    } );
} );