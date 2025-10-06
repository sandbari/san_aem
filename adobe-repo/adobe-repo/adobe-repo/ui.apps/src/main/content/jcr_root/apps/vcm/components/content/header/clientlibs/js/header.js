$(document).ready(function() {
    $('.searchIcon').click(function() {
        sharedJS.openSearchNav();
        $('.investment-popup').attr('aria-expanded', 'false');
        $('.investment-popup').hide();

    });

    let hostAuthUrl = $('#logoLinkUrl').val();
    if(hostAuthUrl == '/content/vcm/us/en.html'){
        $('.home-logo').attr("src", $('#insLogoPath').val());
    }

    let hostNameUrl = window.location.hostname;
    if(hostNameUrl == 'stage.mysecure.vcm.com'
           || hostNameUrl == 'qa.mysecure.vcm.com'
           || hostNameUrl == 'dev.mysecure.vcm.com'
           || hostNameUrl == 'mysecure.vcm.com'){
        $('.home-logo').attr("src", $('#insLogoPath').val());
    }


    //code for hamburger menu
    setCookiePage(COOKIEKEYCONFIG.USER_ENTITY_TYPE, sharedJS.getCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE));

    function setCookiePage(cname, cvalue, exdays = 90) {
        var d = new Date();
        var baseDomain = '.vcm.com';
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";domain="+baseDomain+";"+expires+";path=/;secure;httpOnly=true";
    }

    $('.navbar-toggler').click(function(){
        setTimeout(function(){
			if($('.collapsed').length==0){
			$('body').addClass('no-scroll');
        }
        else  if($('.collapsed').length>0){
			$('body').removeClass('no-scroll');
        }
        },0);
    });
    $('.drop-icon').attr('aria-expanded', 'false');
	//code for dropdown arrow
    $('.drop-icon').click(function() {
       $('.investment-popup').toggle();
       $(this).closest('li').find('img').toggleClass('active');
        if($('.drop-icon').hasClass('active')){
            $('.drop-icon').attr('aria-expanded', 'true');
        }
        else{
            $('.drop-icon').attr('aria-expanded', 'false');
        }
       //$('.drop-icon').addClass('active');

       $(".mask").hide();
       $('body').removeClass('active');
       $("li .overlay").hide();
       $(".nav-link").removeClass('active');
       /*if($('.drop-icon').hasClass('active')){
          $('.investment-popup').attr('aria-expanded', 'true')
       } else {
          $('.investment-popup').attr('aria-expanded', 'false');
       }*/

       $('.investment-popup ul li:visible').addClass('activeInvestor');
       var visibleInv = $('.investment-popup ul li.activeInvestor a');
       var tot_visibleInv= $('.investment-popup ul li.activeInvestor').length;
       $('.investment-popup ul li.activeInvestor a').attr('aria-setsize', tot_visibleInv);
       $(visibleInv).each(function(el, list) {
          var $thisSearchlList = $(list);
          var $posSearchlinset = el+1;
          $thisSearchlList.attr('aria-posinset', $posSearchlinset);
       });
       $('.investment-popup').removeAttr('aria-expanded');
       $('.investment-popup a').on('keydown', function(e){

          if (e.which == 40) {
             $(this).parent().next().find('a').focus();
             e.preventDefault();
             return;
          }
          if (e.which == 38) {
             e.preventDefault();
             $(this).parent().prev().find('a').focus();
             return;
          }
          if (e.which == 9) {
               $('.investment-popup').hide();
               $(this).parents('.showinDesktop ').find('img.drop-icon').removeClass('active');
               $('.drop-icon').attr('aria-expanded', 'false');
          }
          if (e.which == 27) {
               $('.drop-icon').attr('aria-expanded', 'false');
               $('.investment-popup').hide();
               $(this).parents('.showinDesktop ').find('img.drop-icon').removeClass('active');
               $(this).parents('.showinDesktop ').find('img.drop-icon').siblings('a').focus();
          }

       });
        $('div.investment-popup ul li a').attr('tabindex', '-1');
        $('.drop-icon').on('keydown', function(e){
            if (e.which == 40) {
                e.preventDefault();
                $('div.investment-popup ul li a').attr('tabindex', '-1');
                var firstlink = $(this).siblings('div.investment-popup').find('ul li:first-child');
                if(firstlink.css('display')=='none'){
                   $(this).siblings('div.investment-popup').find('ul li:nth-child(2) a').attr('tabindex', '0').focus();
                }
                else{
                   $(this).siblings('div.investment-popup').find('ul li:first-child a').attr('tabindex', '0').focus();
                }
                var lastvisible = $('div.investment-popup ul li:visible').last().find('a');
                $(lastvisible).on('keydown', function (e) {
                   if (e.which == 40) {
                      $('div.investment-popup ul li a').attr('tabindex', '-1');
                      if($('div.investment-popup ul li:first-child').css('display') == 'none'){
                         $('div.investment-popup ul li:first-child').next('li').find('a').attr('tabindex', '0').focus();
                      }
                      else{
                         $('div.investment-popup ul li:first-child a').attr('tabindex', '0').focus();
                      }
                   }
                });
                var firstvisible = $('div.investment-popup ul li:visible').first().find('a');
                $(firstvisible).on('keydown', function (e) {
                   if (e.which == 38) {
                         $('div.investment-popup ul li:last-child a').focus();
                   }
                });
                return;
            }
            if (e.which == 27) {
                e.preventDefault();
                $('.drop-icon').attr('aria-expanded', 'false');
                $('.investment-popup').hide();
                $('div.investment-popup ul li a').attr('tabindex', '-1');
                $(this).parents('.showinDesktop ').find('img').removeClass('active');
            }
            if (e.which == 9) {
                e.preventDefault();
                $('.investment-popup').hide();
                $('div.investment-popup ul li a').attr('tabindex', '-1');
                $('.showinDesktop ').find('img.drop-icon').removeClass('active');
                $(this).parent('li').next('li').find('a').focus();
                $('.drop-icon').attr('aria-expanded', 'false');
                return;
            }
        });
     });
        $('div.investment-popup ul li a').on('keydown', function(e){
            if (e.which == 27) {
                $(this).parents('li.linksDropdown').find('a.drop-icon').focus();
                $('div.investment-popup ul li a').attr('tabindex', '-1');
            }
            if (e.which == 40) {
                e.preventDefault();
                $('div.investment-popup ul li a').attr('tabindex', '-1');
                if($(this).parent('li').next('li').css('display') == 'none'){
                    $(this).parent('li').next('li').next('li').find('a').attr('tabindex', '0').focus();
                }   else
                     $(this).parent('li').next('li').find('a').attr('tabindex', '0').focus();
            }
            if (e.which == 38) {
                e.preventDefault();
                $('div.investment-popup ul li a').attr('tabindex', '-1');
                if($(this).parent('li').prev('li').css('display') == 'none'){
                    $(this).parent('li').prev('li').prev('li').find('a').attr('tabindex', '0').focus();
                } else
                    $(this).parent('li').prev('li').find('a').attr('tabindex', '0').focus();
            }
        });
     $('div.investment-popup ul li:last-child a').on('keydown', function(e){
       if (e.which == 40) {
            $('div.investment-popup ul li:first-child a').focus();
            e.preventDefault();
            return false;
       }
     });
     $('.nav-item').each(function(i){
       var currLi = $(this).find('.overlay-content .overly-row li');
       var totItem = currLi.length;
       $(currLi).attr('aria-setsize', totItem);
       $(currLi).each(function(el, list) {
          var $thisItem = $(list);
          var $posMenulinset = el+1;
          $thisItem.attr('aria-posinset', $posMenulinset);
       });
    });
    $('.popup-close').click(function() {
        $('.search-pop').hide();
    });
    $('.mask').click(function() {
        $(this).hide();
        $('.listCont .overlay').hide();
        $('.listCont .nav-link').removeClass('active');
        $('body').removeClass('active');
    });


    $(".header .nav-link").click(function() {
        let size = $(window).width();
        $('.investment-popup').hide();
        $('.drop-icon').removeClass('active');
        if (size > 992) {

            if ($(this).closest("li").find(".overlay").is(':visible')) {
                $(this).closest(".listCont").find("li .overlay").hide();
                $(".mask").hide();
                $("body").removeClass('active');
                $(this).removeClass('active');
                $(this).attr('aria-expanded', 'false');
                return true;
            } else {
                $(this).closest(".listCont").find("li .overlay").hide();
                $(this).closest('li').find(".overlay").show();
                $(".mask").show();
                $("body").addClass('active');
                $(this).closest(".listCont").find(".nav-link").removeClass('active');
                $(this).addClass('active');
                $(this).attr('aria-expanded', 'true');
                return false;
            }
        }
    });
    //} else {
    $(".nav-link").click(function() {
        let size = $(window).width();
        $('.investment-popup').attr('aria-expanded', 'false');
        if (size < 992) {
			$(this).closest(".nav-item").find(".myNav").css('width', '100%');
        }
    });
    $(".lft-arrow").click(function() {
        let size = $(window).width();
        if (size < 992) {
			$(this).closest(".myNav").css('width', '0%');
        }
    });
	$(".navbar-toggler").click(function () {
        let size = $(window).width();
        if (size < 992) {
			$(".navbar-nav.listCont").find("li .overlay").css('width', '0%');
        }
    });
        $('.nav-link').on('keydown', function(e){
          if( e.which == 40 ){
             e.preventDefault();
             $(this).parent('li').find('div.overlay .overlay-content .overly-row:first-child li:first-child a').focus();
          }
       })
       $('.overlay-content .overly-row li a').on('keydown', function(e){
          if( e.which == 40 ){
             e.preventDefault();
             $(this).parent('li').find('div.overlay .overlay-content .overly-row:first-child li:first-child a').focus();
          }
       })

       $('.overlay-content .overly-row:last-child ul li:last-child a, .overlay-content .col-sm-3:last-child a, .overlay-content .splitul li:last-child a').on('keydown', function(e){
          if( e.which == 9 ){
             $(".mask, .overlay ").hide();
             $("body").removeClass('active');
             $('.nav-link').attr('aria-expanded', 'false');
          }
       });
       $('.overly-row a, .splitul a, .investment  a, .investment .image-wrapper a').on('keydown', function(e){
          if( e.which == 27 ){
             $(".mask, .overlay ").hide();
             $("body").removeClass('active');
             $('.nav-link').attr('aria-expanded', 'false');
          }
       })

});

//code for resize

var width = $(window).width();
$(window).resize(function() {
        $('.investment-popup').attr('aria-expanded', 'false');
    	$('.investment-popup').hide();
        var windowsize = $(window).width()
        //if(windowsize !== width){
        if (windowsize < 992 && windowsize !== width) {
        //$("#navbarToggleExternalContent").removeClass('show')
        //$(".navbar-toggler").addClass('collapsed')
        $('.nav-link').removeClass('active');
        //$(".navbar-toggler").attr('aria-expanded', false)
        $('.nav-link').closest(".nav-item").find(".overlay").css('width', '0%');
        $('.nav-link').closest(".listCont").find("li .overlay").show();
        $('body').removeClass('active');
        } else if( windowsize >= 992){
        $('.nav-link').closest(".nav-item").find(".overlay").css('width', '100%');
        $('.nav-link').closest(".listCont").find("li .overlay").hide();
        $(".mask").hide();
        $('.listCont .nav-link').removeClass('active');
        $('body').removeClass('no-scroll');
    }
});