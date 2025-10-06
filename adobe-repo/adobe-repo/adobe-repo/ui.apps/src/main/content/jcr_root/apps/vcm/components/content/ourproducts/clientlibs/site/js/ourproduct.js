$(document).ready(function(){
  
  $(document).on("click", ".st-product-box", function(e) {
		var bx_val = $(this).attr("data-bx-value");
		$(".st-retirement-overlay").hide();
		$('.st-retirement-overlay[ data-bx-ol-value='+bx_val+'_overlay]').show();
    });
    $(".st-product-box").keypress(function(ke) {
        var ke = ke.which;
        if (13 == ke || 32 == ke) {
            $('.st-product-box').click();
            $('.st-overlay-close').focus();
        }
    });
   $(document).on("click", ".st-overlay-close", function(e) {
        $(".st-retirement-overlay").hide();
    });

    $(".st-overlay-close").on('keydown', function(key) {
        var key = key.which;
        if (13 == key || 32 == key) {
            $(".st-retirement-overlay").hide();
        }
    });

   $(".card-hdng-cnt-navarw").each(function(index,item) {
		var fundType = $(item).attr('data-fund-type');
        if(fundType.length){
            var redirectUrl = $(item).find('a').attr('href') + "?fundType=" + fundType;
            $(item).find('a').attr('href',redirectUrl);
        }
    });
    $('.card-hdng-cnt-navarw a.navg-right-arw-link').on('keydown', function(e){
        if( (e.which == 32) || (e.which == 13) ){
            var href = $(this).attr('href');
            window.location.href = location.origin+href;
             e.preventDefault();
        }
    });
});