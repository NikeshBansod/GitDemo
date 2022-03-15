$(document).ready(function(){

	
	
	///////////////////////////////
	// Menu
	///////////////////////////////
	$('.button-collapse').sideNav({
        menuWidth: 300, // Default is 300
        edge: 'left', // Choose the horizontal origin
        closeOnClick: false, // Closes side-nav on <a> clicks, useful for Angular/Meteor
        draggable: true // Choose whether you can drag to open on touch screens
    });
    
    $('.navToggle').on('click', function(){
        $(this).toggleClass('is-active');
    });
    
    $('.drag-target').on('click', function(){
        $('.navToggle').removeClass('is-active');
    });

	$('.btn-back').on('click', function(){
		window.history.back();
    });


    ///////////////////////////////
	// Accordion
	///////////////////////////////
	if ($('.accordion').length > 0) { 
	    // it exists 
	    $(".accordion").smk_Accordion({
	        closeAble: true, //boolean
	    });
	}


	///////////////////////////////
	// Datepicker
	///////////////////////////////
	$('.datepicker').pickadate({
		selectMonths: true, // Creates a dropdown to control month
		selectYears: 15, // Creates a dropdown of 15 years to control year
		format: 'dd-mm-yyyy',
        formatSubmit: 'dd-mm-yyyy',
        onSet: function(context) {
            if ('select' in context){ //prevent closing on selecting month/year
	            //alert();
	            this.close();
	        };
            console.log(this.get());
        }
	});


	///////////////////////////////
	// Content Hide
	///////////////////////////////
	$(".content").hide();
	$(".heading").click(function () {
    	$(this).next(".content").slideToggle(100);
    });
	
	
  	///////////////////////////////
	// Responsive Table
	///////////////////////////////
	/*$('.resTable').riltable();*/
	
	$("#information").click();

})


