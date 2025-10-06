(function($) {
   "use strict";
$(document).ready(function(){     
     $('#searchTerm').keyup(function(){
    	  let $form = $(this).closest('form');
    	  let $suggestionLimit=$form.data("suggestionsLimit");
    	  let $rootPagePath=$form.data("suggestionsRootPath");
          let $searchUrl = $form.data('quick-suggestions');
    	  let $searchTerm = $('#searchTerm').val();
    	  let $list = $form.find('.quick-lists');
    	 $('.search-list').empty();
         $.ajax({
             contentType: "application/x-www-form-urlencoded; charset=UTF-8",
             context: this,
 			type: 'GET', 
             url: $searchUrl,
             data: { searchtext: $searchTerm,limit: $suggestionLimit, rootPagePath: $rootPagePath},
             dataType: "json",
             success: function (response)
             {
            	 $list.find('dd').remove();
            	 if(response != null 
            			 && typeof response != "undefined"
            			 && response.suggestion != null 
            			 && typeof response.suggestion != "undefined"
            			 && response.suggestion.length > 0){
            		 	response.suggestion.forEach(function(item){
	             			$list.find('.quick-suggestions').append($('<dd>').text(item));
	             			showHide($list, response.suggestion.length > 0);
	             		});
            	 }
            	 else{
            		 showHide($list, response.suggestion.length > 0);
            	 }
                 
             },
             error: function (jqXhr, textStatus, errorMessage)
     		{
             	var errorHtm="Oops!! Something went wrong. Please try again later.";
             }

     	});
     });
     $('form').on('click', '.quick-lists .quick-suggestions dd', function() {
         var $form = $(this).parents('form'),
             $searchField = $form.find('input[name=q]'),
             autoComplete = $(this).text();
         	 $searchField.val(autoComplete);
         	$(this).parents('.quick-lists').hide();
         	$('.search-list').empty();
 	});
    function showHide($el, flag) {
         if (flag) {
             $el.show();
         } else {
             $el.hide();
         }
     }
     
	});
})(jQuery);