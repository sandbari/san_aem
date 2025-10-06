$(document).ready(function () {
    $(document).on("click", "a[data-experience-popup]", function () {
        var expPath = $(this).attr("data-experience-popup");
        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            context: this,
            type: 'GET',
            url: expPath,
            dataType: "html",
            success: function(response) {
                $('body').addClass('active');
                var responseHTML = $.parseHTML(response);
                var containerHTML = $(responseHTML).find('.container').html();
                var wrapperExpDiv = $("<div>", {
                    "class": "globalExperiencePopUpWrapper"
                });
                
                var exp_popUp_container_div = $("<div>", {
                    "id": "vcm-exp-popup",
                    "class": "vcm-popup ",
                    "style": "display:block;"
                });
                
                exp_popUp_container_div.append("<div class='popup-header'><span class='vcm-popup-btn-close'></span></div>");
                exp_popUp_container_div.append(containerHTML);

                $(wrapperExpDiv).append(exp_popUp_container_div);   
                $(wrapperExpDiv).append("<div class='popup-mask' style='display: block;'></div>");
                $('body').append(wrapperExpDiv);
            }
        });
    });

    $(document).on("click", "#vcm-exp-popup .vcm-popup-btn-close", function (element) { 
        $('body').removeClass('active'); 
        $('.popup-mask').css("display", "none");
        $("#vcm-exp-popup").remove();
    });
    
});