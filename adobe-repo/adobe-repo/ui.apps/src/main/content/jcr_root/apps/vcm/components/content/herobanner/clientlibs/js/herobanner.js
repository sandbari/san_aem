$( document ).ready(function() {
    
    /* BreadCumb should be displayed right below the banner befor any text */
        
        var breadCumbFound = $(document).find('.breadcrumb');
            if(breadCumbFound.length > 0) {
                var freeTextEle = $(document).find('.freeTextMobile');
                $('.breadcrumb').append(freeTextEle);
    }
    $(".loginBox input").on('input',function(e){
       var usrName =  $(username).val();
       var pwd =  $(password).val();
       if(usrName != "" && pwd != ""){
          $('#sbmitBtn').addClass("activeBtn");
       }
       else {
            $('#sbmitBtn').removeClass("activeBtn");
       }
    });

 
    if($('.focusarea').length >0 ){
        $('.focusarea').each(function () {
            var cssValue = $(this).css("background-image");
            var imgName = cssValue.replace(/(?:^url\(["']?|["']?\)$)/g, "");
            var finalname = imgName.substring(imgName.lastIndexOf("/")+1,imgName.length);
            if(finalname == "white-pattern.png"){
                $(this).css('border-bottom','1px solid #d2d2d2')
            }
        });
    }
    
    var learnMoreFilterFound = $(document).find('.learnmorefilter .filter-wrapper');
    if(learnMoreFilterFound.length > 0) {
        var freeTextMobileElement = $(document).find('.freeTextMobile');
        $('.learnmorefilter .filter-wrapper').append(freeTextMobileElement);
    }
    
    if($('.franchise-banner').length> 0){
        $('.columncontrol').first().find('.col-lg-4').append('<div id="right-franchise"></div>');
        $('.genericContainer').appendTo('#right-franchise');
        if(($('#right-franchise').height())==0){
            $('#right-franchise').parents('.columncontrol').find('div.col-sm-12.col-md-6.col-lg-8').removeClass('col-md-6 col-lg-8');
            $('#right-franchise').parent('.col-lg-4').hide();
        }
    }
    
    
});