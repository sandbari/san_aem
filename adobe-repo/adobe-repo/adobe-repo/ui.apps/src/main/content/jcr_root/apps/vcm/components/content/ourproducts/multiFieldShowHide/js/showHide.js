
(function(document, $) {
    "use strict";
	    // when dialog gets injected
	    $(document).on("foundation-contentloaded", function(e) {
	        // if there is already an inital value make sure the 
	  //according target element becomes visible
	        showHideHandler($(".cq-dialog-dropdown-showhide1", e.target));
	    });

    $(document).on("change", ".cq-dialog-dropdown-showhide1", function(e) {
        showHideHandler($(this));
    });

    function showHideHandler(el) {
        el.each(function(i, element) {
                // handle Coral3 base drop-down
                Coral.commons.ready(element, function(component) {
                    showHideCustom(component, element);
                    component.on("change", function(e) {
                        showHideCustom(component, element);
                    });
                });
        })
    }

    function showHideCustom(component, element) {
         // get the selector to find the target elements. 
         //its stored as data-.. attribute
       var target = $(element).data("cq-dialog-dropdown-showhide-target1");
       var elementIndex = $(element).closest('coral-multifield-item').index();
       if (target) {
         $(element).closest("coral-multifield-item").find(target)
         .each(function(index,item) {
            var tarIndex = $(this).closest('coral-multifield-item').index();
            if (elementIndex == tarIndex) {
            	//$(this).not(".hide").parent().addClass("hide");
            	if($(element).prop('checked')){
            		
            		$(this).filter("[data-showhidetargetvalue='" + false + "']")
            		.removeClass('show').addClass('hide');
            		$(this).filter("[data-showhidetargetvalue='" + false + "']")
            		.parent().removeClass('show').addClass('hide');
            		
            		$(this).filter("[data-showhidetargetvalue='" + true + "']")
            		.removeClass('hide').addClass('show');
            		$(this).filter("[data-showhidetargetvalue='" + true + "']")
            		.parent().removeClass('hide').addClass('show');
            	}
            	else
            	{
            		$(this).filter("[data-showhidetargetvalue='" + true + "']")
            		.removeClass('show').addClass('hide');
            		$(this).filter("[data-showhidetargetvalue='" + true + "']")
            		.parent().removeClass('show').addClass('hide');
            		
            		$(this).filter("[data-showhidetargetvalue='" + false + "']")
            		.removeClass('hide').addClass('show');
            		$(this).filter("[data-showhidetargetvalue='" + false + "']")
            		.parent().removeClass('hide').addClass('show');
            	}
            }
         });
        }
    }

})(document, Granite.$);