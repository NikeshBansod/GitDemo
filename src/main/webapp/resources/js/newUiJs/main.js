//********************
//Load Function
//********************
/*commented on 23/10/2018
$(document).ready(function () {
    $('#datepicker').datepicker({
      uiLibrary: 'bootstrap'
    });
});*/
//data table
/*$(document).ready(function() {
    var table = $('#example').DataTable( {
        scrollY:        "590px",
        scrollX:        true,
        scrollCollapse: true,
        paging:         true,
        fixedColumns:   {
            leftColumns: 3
        }
    } );
} );*/


function createDataTable(tableId){
    var table = $('#'+tableId+'').DataTable({
        rowReorder: {
            selector: 'td:nth-child(2)'
        },
        responsive: true
    });
}
//same height
$(function(){
    function equalHeight(){

        var heightArray = $(".app-box").map( function(){
            return  $(this).height();
        }).get();

        var maxHeight = Math.max.apply( Math, heightArray);
            $(".app-box").height(maxHeight);
        }

    equalHeight();  
});
$(function(){
    function equalHeight(){
        var heightArray = $(".height").map( function(){
            return  $(this).height();
        }).get();

        var maxHeight = Math.max.apply( Math, heightArray);
            $(".height").height(maxHeight);
        }

    equalHeight();
}); 

$(document).ready(function(){
    // hide #back-top first
    $("#back-top").hide();
    
    // fade in #back-top
    $(function () {
        $(window).scroll(function () {
            if ($(this).scrollTop() > 100) {
                $('#back-top').fadeIn();
            } else {
                $('#back-top').fadeOut();
            }
        });

        // scroll body to 0px on click
        $('#back-top a').click(function () {
            $('body,html').animate({
                scrollTop: 0
            }, 800);
            return false;
        });
    });
    
});
