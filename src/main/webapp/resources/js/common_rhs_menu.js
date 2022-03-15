$(function(){
	$('.main_btn a').on('click', function(){
		$('.main_btn').hide();
		var id = $(this).attr('data-id');
		$('.'+id).show();
	});
	$('.btn-back').on('click', function(){
		$('.doc_management').hide();
		$('.eway_management').hide();
		 $('.master_management').hide();
		$('.main_btn').show();
		 $('.inventory_management').hide();
		 $('.account_management').hide();
		
		
		
	});
	
	
	$('#document_management').on('click', function(){
		$('.btn-back').show();
	});
	
	$('#ewaybill_management').on('click', function(){
		$('.btn-back').show();
	});
	
	$('#masterdata_management').on('click', function(){
		$('.btn-back').show();
	});
	$('#account_management').on('click', function(){
		$('.btn-back').show();
	});
});
   
    

 $(document).ready(function () {
	 $(".box2").hide();
    	 $('.btn-back').hide();
    	 $('.doc_management').hide();
    	 $('.inventory_management').hide();
    	 $('.account_management').hide();
    	 $('.help_management').hide();
    	 $('.account_management').hide();
        if(window.location.href.indexOf("doc_management") > -1) 
        {            
			$('.main_btn').hide();
            $('.doc_management').show();
            $('.btn-back').show();
            $('.box2').show();
        }
        
        if(window.location.href.indexOf("eway_management") > -1) 
        {
        	$('.main_btn').hide();
            $('.eway_management').show();
            $('.btn-back').show();
            $('.box2').show();
        } 
        
        if(window.location.href.indexOf("master_management") > -1) 
        {
        	$('.main_btn').hide();
            $('.master_management').show();
            $('.btn-back').show();
            $('.box2').show();
        } 
        if(window.location.href.indexOf("inventory_management") > -1) 
        {
        	$('.main_btn').hide();
            $('.inventory_management').show();
            $('.btn-back').show();
            $('.box2').show();
        } 
        if(window.location.href.indexOf("account_management") > -1) 
        {
        	$('.main_btn').hide();
            $('.account_management').show();
            $('.btn-back').show();
            $('.box2').show();
        } 
        if(window.location.href.indexOf("help_management") > -1) 
        {
        	$('.main_btn').hide();
            $('.help_management').show();
            $('.btn-back').show();
            $('.box2').show();
        } 
     
    });

  /*   $('.dropdown-toggle').dropdown()*/