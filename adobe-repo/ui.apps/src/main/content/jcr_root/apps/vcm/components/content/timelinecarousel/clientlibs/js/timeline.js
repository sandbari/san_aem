(function($) {
  $.fn.swipeDetector = function(options) {
    // States: 0 - no swipe, 1 - swipe started, 2 - swipe released
    var swipeState = 0;
    // Coordinates when swipe started
    var startX = 0;
    var startY = 0;
    // Distance of swipe
    var pixelOffsetX = 0;
    var pixelOffsetY = 0;
    // Target element which should detect swipes.
    var swipeTarget = this;
    var defaultSettings = {
      // Amount of pixels, when swipe don't count.
      swipeThreshold: 70,
      // Flag that indicates that plugin should react only on touch events.
      // Not on mouse events too.
      useOnlyTouch: false
    };

    // Initializer
    (function init() {
      options = $.extend(defaultSettings, options);
      // Support touch and mouse as well.
      swipeTarget.on("mousedown touchstart", swipeStart);
      $("html").on("mouseup touchend", swipeEnd);
      $("html").on("mousemove touchmove", swiping);
    })();

    function swipeStart(event) {
      if (options.useOnlyTouch && !event.originalEvent.touches) return;

      if (event.originalEvent.touches) event = event.originalEvent.touches[0];

      if (swipeState === 0) {
        swipeState = 1;
        startX = event.clientX;
        startY = event.clientY;
      }
    }

    function swipeEnd(event) {
      if (swipeState === 2) {
        swipeState = 0;

        if (
          Math.abs(pixelOffsetX) > Math.abs(pixelOffsetY) &&
          Math.abs(pixelOffsetX) > options.swipeThreshold
        ) {
          // Horizontal Swipe
          if (pixelOffsetX < 0) {
            swipeTarget.trigger($.Event("swipeLeft.sd"));
          } else {
            swipeTarget.trigger($.Event("swipeRight.sd"));
          }
        } else if (Math.abs(pixelOffsetY) > options.swipeThreshold) {
          // Vertical swipe
          if (pixelOffsetY < 0) {
            swipeTarget.trigger($.Event("swipeUp.sd"));
          } else {
            swipeTarget.trigger($.Event("swipeDown.sd"));
          }
        }
      }
    }

    function swiping(event) {
      // If swipe don't occuring, do nothing.
      if (swipeState !== 1) return;

      if (event.originalEvent.touches) {
        event = event.originalEvent.touches[0];
      }

      var swipeOffsetX = event.clientX - startX;
      var swipeOffsetY = event.clientY - startY;

      if (
        Math.abs(swipeOffsetX) > options.swipeThreshold ||
        Math.abs(swipeOffsetY) > options.swipeThreshold
      ) {
        swipeState = 2;
        pixelOffsetX = swipeOffsetX;
        pixelOffsetY = swipeOffsetY;
      }
    }

    return swipeTarget; // Return element available for chaining.
  };
})(jQuery);
$(function(){
    function reLoad(){
        if ($(window).width() < 600) {
          var winWidth = $(window).width();
          var liWidth=winWidth-40;
            $('#timeline').css('width', winWidth);
            $('#issues li').css('width', liWidth);
            if(typeof $().timelinr != "undefined"){
	            	$().timelinr({
	                    arrowKeys: 'true',
	                    issuesTransparency: 0.2,
	                    startAt: 3,
	                    issuesSpeed: 'normal'
	                })
	                swipeTimeliner();
             }
            }
            else{
                    $('#issues li, #timeline').removeAttr("style"); 
                    if( typeof $().timelinr != "undefined"){
                    	$().timelinr({
			                    arrowKeys: 'true',
			                    issuesTransparency: 0.0,
			                    startAt: 3,
			                    issuesSpeed: 'normal'
			                }) 
                    }
        }
        $("#issues li").each(function(index, el) {
            $(this).attr("data-yrNumber", index+1);
        });
    }
    if ( $( "#timeline" ).length ) {
        reLoad(); 
    }
    $(window).resize(function() { 
        if ( $( "#timeline" ).length ) {
            reLoad();
        }
    });
    // for swipe left / right in mobile starts
         function swipeTimeliner(){
            $("#issues li").swipeDetector()
                .on("swipeLeft.sd swipeRight.sd", function(event) {
                  if (event.type === "swipeLeft") {
                    event.preventDefault();
                    $("#next").click();
                  } else if (event.type === "swipeRight") {
                     event.preventDefault();
                     $("#prev").click();
                 }
            });
         }
    // for swipe left / right in mobile ends

      $('#timeline li a').on('click', function(){
        $('#timeline li').removeAttr("aria-current", "true");
        $(this).parent('li').attr("aria-current", "true");
        setTimeout(function(){
            var totSlide = $('#issues li').length;
            var selYr = $('#issues li.selected').attr('data-yrNumber');
            $('.item-show').empty().append("item " + selYr + " out of " + totSlide);
           }, 500);
        let targetYear = $(this).attr("href");
        $("ul#issues li").attr("aria-hidden","true");
        //$("ul#issues li").attr("aria-atomic","true");
        if(sharedJS.nonNullCheck(targetYear)){
        	$(targetYear).attr("aria-hidden","false");
        	//$(targetYear).attr("aria-atomic","true");
        }
      })
    });
    window.addEventListener('load', function(){
      // Everything has loaded!
      $('#timeline li a.selected ').parents('li').attr("aria-current", "true");
      var totSlide = $('#issues li').length;
      var selYr = $('#issues li.selected').attr('data-yrNumber');
      $('.item-show').empty().append("item " + selYr + " out of " + totSlide);
    });
