$(document).ready(function () {
        $('.equal-height-row .col-lg-4').each(function(){
            if($.trim($(this).html()).length == 0){
                $(this).css("border", 0)
            }
        });
    });