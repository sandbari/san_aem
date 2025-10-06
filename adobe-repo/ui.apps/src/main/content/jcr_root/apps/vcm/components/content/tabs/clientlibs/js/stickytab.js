$(document).ready(function () {
if($('.sticky-tab-container').length > 0) {
    $('#collapse-Performance .container').children('div').attr('tabindex', '-1');
    // on window resize
    //$(window).on('resize',function(){location.reload();});
    // Desktop: Initial Tab content will be aligned as rightside but not for Mobile View
    function tabletResize() {
    if ($(window).width() > 1023) { // Desktop
        //$('.tab-pane:first').clone().appendTo('.tab-pane').eq(1).show();
        var firstTabCol4 = document.createElement("div");
        var stickyRightRail = $( ".sticky-tab-container" ).data( "stickyright");
        firstTabCol4.id = "tabrightcontent";
        //firstTabCol4.className = "col-lg-4";
        if (stickyRightRail == "sticky-right") {
        firstTabCol4.className += " tabright";
        }
        document.body.appendChild(firstTabCol4);
        var firstTabCol8 = $('.tab-pane').eq(1).attr('id');
        $('.tab-pane:first .collapse .card-body').clone().appendTo('#tabrightcontent').show();
        $('#content').append($('#tabrightcontent'));
       // $('#tabrightcontent').insertAfter(firstTabCol8);
       // $(this).find('.sticky-tab-container .nav-item:first').remove();
        //$(this).find('.sticky-tab-container .tab-pane:first').remove();
        // Make first tab active
        $(".sticky-tab-container").find('.nav-item:first').hide();
        $(".sticky-tab-container").find('.tab-pane.card:first').hide();
        if (!$('.sticky-tab-container .nav-item:first').is(':visible')) {
            $('.sticky-tab-container .nav-item:eq(1)').addClass('active').children('a').addClass('active');
        }
   } else {
        $(".sticky-tab-container").find('.tab-pane.card:first').show();
        if ($(".sticky-tab-container .collapse").hasClass("show")) {
            $(".sticky-tab-container .collapse").addClass('hide');
            $(".sticky-tab-container .collapse").removeClass('show');
        }
    }
    }
    tabletResize();
    $(window).on('resize', function() {
        tabletResize();
    });
    $(".sticky-tabs .nav-link").click(function (e) {
        e.preventDefault();
        $('.nav-item').removeClass('active');
        $(this).parent().addClass("active");
        var target = $(this.hash);
        //console.log(target);
        var topValue = target.offset().top - 115;
        if (!($(".fix-container").hasClass("fixed-tabs"))) {
            topValue = topValue - 99;
        }
        //To Reduce space for initial tab
        if ( $('.sticky-tab-container .nav-item').eq(0).hasClass('active') ){
           // topValue = topValue - 40;
        }
        $('html, body').animate({
            scrollTop: topValue
        }, 1000);
    });
    $("ul#funddetail-tabs-container li a").click(function(){
        var idVariable = $(this).attr("href");
        idVariable = idVariable.substring(1, idVariable.length);
        $('#' + idVariable).attr("tabindex", "-1").focus();
    });
    $(window).on("scroll", function () {
        if ($(window).width() > 1023) {
            var NavTop = $(".sticky-tab-container").offset().top;
            var stickyMenu = $(".sticky-tabs .nav"),
                stickyMenuHeight = stickyMenu.outerHeight() + 50,
                scrollDistance = $(window).scrollTop() + stickyMenuHeight;
            var totalTab = $('.tab-pane').length;
            // End of Tab scroll remove position
            var TabContent   = document.querySelector('.sticky-tab-container');
            var TabContentSection   = TabContent.getBoundingClientRect();
            $('.tab-pane').each(function (i) {
                if ($(this).position().top <= scrollDistance) {
                    $('.sticky-tabs .nav-item a').removeClass('active');
                    $('.sticky-tabs .nav-item').removeClass('active');
                    $('.sticky-tabs .nav-item').eq(i).addClass('active');
                    $('.sticky-tabs .nav-item a').eq(i).addClass('active');
                    if (($('.sticky-tab-container .nav-item:first').hasClass('active')) && ($(!$('.sticky-tab-container .nav-item:first').is(':visible')))) {
                        $('.nav-item a').removeClass('active');
                        $('.nav-item').removeClass('active');
                        $('.sticky-tab-container .nav-item:eq(1)').addClass('active').children('a').addClass('active');
                    }
                }
            });
            if (window.scrollY >= NavTop || window.pageYOffset >= NavTop) {
                $(".fix-container").addClass("fixed-tabs");
                if($("#tabrightcontent").hasClass("tabright") ==true)
                    $(".tabright").addClass("fixed-tabright");
                $(".purchase-fund-btn").addClass("fixed-button");
            } else {
                $(".fix-container").removeClass("fixed-tabs");
                 if($("#tabrightcontent").hasClass("tabright") ==true)
                    $(".tabright").removeClass("fixed-tabright");
                $(".purchase-fund-btn").removeClass("fixed-button");
            }
            // Remove active class for tabs when scroll in fund details section
            if(  (TabContentSection.bottom  <= 120)) {
                $(".fix-container, .purchase-fund-btn").fadeOut(500);
            }   else {
                $(".fix-container, .purchase-fund-btn").fadeIn(500);
            }
            // Remove sticky for Right tab content when scroll in end of tab section
            if(  (TabContentSection.bottom  <= $(".tabright").outerHeight())) {
                if($("#tabrightcontent").hasClass("tabright") ==true)
                    $(".tabright").fadeOut(500);
            }   else {
                if($("#tabrightcontent").hasClass("tabright") ==true)
                    $(".tabright").fadeIn(500);
            }
        }
    });
    // accordion open close icon toggle
    $(".sticky-tab-container .card-header a").click(function () {
        $('.accordion-icon').removeClass('accordion-close');
        $('.card-header').removeClass('active');
        if ($(this).hasClass('collapsed')) {
            $(this).find('.accordion-icon').addClass('accordion-close');
            $(this).parent('.card-header').addClass('active');
        } else {
            $(this).find('.accordion-icon').removeClass('accordion-close');
            $(this).parent('.card-header').removeClass('active');
        }
    });
    }
});