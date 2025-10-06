$(document).ready(function() {
    checkWidth();
    checkMediaWidth();

});

function checkWidth() {

    $(".image-wrapper").on('click keyup', function(e){
        if(e.keyCode==13 || e.keyCode==32 || e.type === 'click' ){

            if ($(this).next().find('.inner-block').is(":visible")) {
                $(this).removeClass('activebtn');
                $(this).next().find('.inner-block').hide();
                $(this).next().css('height', 0);
                $(this).attr('aria-expanded', 'false');
                return;
            } else {
                $('.inner-block').hide();
                $(".image-wrapper").removeClass('activebtn');
                $(".heightcontainer").css('height', 0);
                var h = 0;
                $(".image-wrapper").attr('aria-expanded', 'false');
                $(this).attr('aria-expanded', 'true');
                // $(this).next().find('.inner-block').height()
                if ($('.desktopFranchise').length) {
                    h = $(this).next().find('.inner-block').height() + 22;
                } else {
                    h = $(this).next().find('.inner-block').height();
                }
                $(this).next().css('height', h);
                $(this).next().find('.inner-block').show();
                $(this).addClass('activebtn');
            }
        }
         if (e.which == 39) {
            $(this).parent().next().find('div.image-wrapper').focus();
            return;
         }
         if (e.which == 37) {
            $(this).parent().prev().find('div.image-wrapper').focus();
            return;
         }
    })
    // $(".image-wrapper").click(function() {
       
    // });
}

function checkMediaWidth() {
    if (matchMedia) {
        const mq = window.matchMedia("(min-width: 1025px)");
        mq.addListener(WidthChange);
        WidthChange(mq);
    }

    // media query change
    function WidthChange(mq) {
        if ($('.franchiseBlock').length) {
            if (mq.matches) {
                $('.franchiseBlock').addClass('desktopFranchise');
            } else
                $('.franchiseBlock').removeClass('desktopFranchise');
        }
    }
}
$(window).resize(function() {

    $(".heightcontainer").css('height', 0);
    $(".inner-block").hide();
    $('.image-wrapper').removeClass('activebtn');
    checkMediaWidth();

});