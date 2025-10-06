(function (document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function (e) {
        // if there is already an inital value make sure the according target element becomes visible
        showHideHandler($(".cq-dialog-dropdown-showhide", e.target));
    });

    function showHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-select")) {
                // handle Coral3 base drop-down
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                    component.on("change", function () {
                        showHide(component, element);
                    });
                });
            } else {
                // handle Coral2 based drop-down
                var component = $(element).data("select");
                if (component) {
                    showHide(component, element);
                }
            }
        })
    }

    function showHide(component, element) {
        var elementName = $(element).get(0).name;
        var selectedValue = $(component).get(0).value;
        var target = $("[data-cqdialogdropdownshowhidetarget]");
		var targetValue;
        var targetData=[];
        target.each(function (i, element) {
			targetValue = $(element).get(0).dataset.cqdialogdropdownshowhidetarget;
			targetData = targetValue.split("-");
            if(check(targetData, selectedValue)){
                if($(element).get(0).className.indexOf("cq-RichText") !== -1){
                    $(element).parent().parent().removeClass("hide");
                }
                else{
					$(element).parent().removeClass("hide");
                }

            }
			 else {
                 if($(element).get(0).className.indexOf("cq-RichText") !== -1){
                    $(element).parent().parent().addClass("hide");
                }
                else{
					$(element).parent().addClass("hide");
                }

            }

        });

    }

     function check(targetData, selectedValue) {
         return jQuery.inArray(selectedValue, targetData) !== -1;;
     }




})(document, Granite.$);