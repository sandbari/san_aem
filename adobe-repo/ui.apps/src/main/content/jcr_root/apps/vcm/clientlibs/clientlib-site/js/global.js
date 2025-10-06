$(document).ready(function() { 
	$('.root.responsivegrid').find('.responsivegrid.aem-GridColumn.aem-GridColumn--default--12:first').attr("role", "main");
    $('.footer.aem-GridColumn.aem-GridColumn--default--12').parents('.experiencefragment.aem-GridColumn.aem-GridColumn--default--12').attr("role", "contentinfo");

    $('.navigationLinksButton').hover(
        function() {
            $(this).addClass("hoverIn");
        },
        function() {
            $(this).removeClass("hoverIn");
        }
    );

    $(".cardLink").hover(function(){
        $(this).addClass('linkCardActive');
    }, function() {
        $(this).removeClass('linkCardActive');
    });


    $(".cardLink, .card-hdng-cnt-navarw, .learningresource .advice-tool .card-image-hdng-small").click(function(){
        var href = $(this).find('a').attr('href');
        window.location.href = location.origin+href;
    });

     $(".learnmorefilter .row div[class*='col-lg-']").filter(function(){
        var text = $.trim($(this).find('.textimageCTA').text());
        return text == '' || text == '-';
     }).remove();

    //filter title accordion
    $(".bloglisting .filter-wrapper .accordion-filter .heading-filter").keypress(function(key) {
        var key = key.which;
        if (13 == key || 32 == key) return $(this).toggleClass("collapsed").parent().toggleClass("active").next().toggleClass("show"), $(this).hasClass("collapsed") ? $(this).attr("aria-expanded", "false") : $(this).attr("aria-expanded", "true"), !1
    });

    //filtercheckbox
    $(".bloglisting .container-checkbox :checkbox.check_box").focusin(function(){$(this).parent().children(".checkmark").css("border","2px solid #000")}),$(".container-checkbox :checkbox.check_box").focusout(function(){$(this).parent().children(".checkmark").css("border","1px solid #004A98")}),
    $(".bloglisting .container-checkbox :checkbox.check_box").focusin(function(){$(this).parent().children("body.using-mouse .checkmark").css("border","1px solid #004A98")}),$(".container-checkbox :checkbox.check_box").focusout(function(){$(this).parent().children("body.using-mouse .checkmark").css("border","1px solid #004A98")});

   if ($('.shape-triangle-mid-right-btm').length) {
        $('.backgroundcontainer').addClass("bottomSpacing");
    }

     $('.tt-input').on('keydown', function(e){
        if( (e.which == 27)){
        $(this).val('');
        }
    });

     $(".productAccordion .card-header,.faqlist .card-header,.tabs .card-header,.video-block .card-header").keypress(function(s) {
        var a=s.which;
        if(13==a||32==a)return $(this).hasClass("collapsed")?($(this).attr("aria-expanded","true").next().addClass("show"),$(this).removeClass("collapsed")):($(this).addClass("collapsed"),$(this).attr("aria-expanded","false").next().removeClass("show")),!1
     });
    //Adding rel attribute for the links opening in new tab
    $('a[target="_blank"]').attr('rel', 'noopener noreferrer');

   
    $('.register').click(function(e) {
        if (window.innerWidth < 1280 && $('.homepage-banner .loginBox').length) {
            event.preventDefault();
            $(".popup-mask").css("display", "block");
            $('.homepage-banner .loginBox').css("display", "block");
            $('.homepage-banner .loginBox').addClass("activeLogin");
            var loginBox = $(document).find('.loginBox');
            $(".loginPopup").append(loginBox).show();
            //$('.homepage-banner .content-wrapper-position').css("transform", "none");
        }
        //   $('.homepage-banner .loginBox').toggle();
    });

    function loginBoxMove(){
		if (window.innerWidth >= 1280 && $('.homepage-banner .loginBox').length) {
				var loginBox = $(document).find('.loginBox');
				$('.loginBox').parents('.banner-container').find('.content-wrapper').prepend(loginBox);
				$('.homepage-banner .loginBox').removeClass("activeLogin");
				$(".popup-mask").css("display", "none");
				$('.loginBox.desktopShow').show();

        }
		else if (window.innerWidth < 1280 && $('.homepage-banner .loginBox').length){
			var loginBox = $(document).find('.loginBox');
			$(".loginPopup").append(loginBox).hide();
			$(".popup-mask").css("display", "none");
		}

	}

	 $(window).resize(function() { 
        if ( $( ".loginBox" ).length ) {
            loginBoxMove();

        }
    });
	 
    $(".homepage-banner .signinBox .close").click(function() {
        $(".popup-mask").css("display", "none");
        $('.homepage-banner .loginBox').css("display", "none");
        $('.homepage-banner .loginBox').removeClass("activeLogin");
        $('.homepage-banner .content-wrapper-position').css("transform", "translateY(-50%)");
    })



    	
	if($('.faqlist').length && $('.phonedetails').length) {
		$('.content-wrapper').addClass('contactus-wrapper');
		$('.contactus-wrapper').addClass('mxw-1100');
    }

    // Code to add f=green border for multiline heading
   //greenborderSetFunction();
});


/*function greenborderSetFunction() {
    var parentEl = $("body .content-wrapper").find('.blueHdng-multiLine-greenBorder');
    for (var i = 0; i < parentEl.length; i++) {
        var currentEl = parentEl[i];
        var button = currentEl;
        var words = button.innerText.split(/[\s]+/); // An array of allthe words split by spaces, since that's where text breaks by default. var
        var lastLine = []; // Putall words that don't change the height here.
        var currentHeight = currentEl.clientHeight; // The starting height.
        while (1) {
            lastLine.push(words.pop());
            button.innerText = words.join(' ');
            if (currentEl.clientHeight < currentHeight) {
                var span = document.createElement('span');
                span.className += "underline";
                //span.classList=['underline'];
                span.innerText = ' ' + lastLine.reverse().join(' ');
                button.appendChild(span);
                break;
            }
            currentHeight = parentEl[i].clientHeight;
            if (!words.length) {
                break;
            }
        }
    }
}*/

$(window).resize(function() {
    //greenborderSetFunction();
})
$(document).ready(function() {
    $("br, hr").attr("aria-hidden", "true");
    $('body').on('mousedown', function() {
        $('body').addClass('using-mouse');
    });
    // Re-enable focus styling when Tab is pressed
    $('body').on('keydown', function(event) {
        if (event.keyCode === 9) {
            $('body').removeClass('using-mouse');
        }
    });
});
