$(document).ready(function() {
let showingAllText = $('#showing').text();
if($('.faqdetails').length > 0) {

                 var accordian, segregation, seeall, element;
    
                 var jsonText = JSON.parse($('.json-data').text());
    
                 //$.getJSON('js/FaqDetailsComponent.json', function(data) {

                loadFAQView();

                    $(".loadMore").each(function(e) {
                        $(this).parents('.acc-segregation').find('.closeall').hide();
                        $(this).parents('.acc-segregation').find('.card').hide();
                        $(this).parents('.acc-segregation').find('.card').slice(0, 4).show();
                    });
                
                    $(".loadMore").on("click", function(e) {
                        $(this).parents('.acc-segregation').find('.closeall').show();
                        $(this).parents('.acc-segregation').find('.card:hidden').slideDown();
                        $(this).parents('.acc-segregation').find('.seeall').hide();
                    });



                    $(document).on('click', 'a.groupNameCheck', function(e) {
                    	let bckshow = $('#showallfaq #bcktoshow');
                    	if(bckshow.length <= 0){
                    		$('<p><a role="menuitem" class="groupNameCheck" href="javascript:void(0);" id="bcktoshow">Show all FAQ</a></p>').insertBefore('#showallfaq p:first-child');
                    	}
                    });


                    $(document).on('click', 'a.groupNameCheck', function(e) {
                        var tmpName = ($(this).attr('id'));
                        $('a.groupNameCheck').parent().show();
                        $(this).parent().hide();
                        var chu = $(this).text();
                        $('#showing').html(chu);
                        $('div.acc-segregation').hide();
                        $('#bcktoshow').parent().show();
                        $('#showallfaq').removeClass('show');
                        $('.card-header.acc-title').attr('aria-expanded','false');
                                    $('.card-header.acc-title').addClass('collapsed');

                        $('div.acc-segregation[data-group=' + tmpName + ']').show().attr("style", "margin-top: 25px !important;margin-bottom: 20px");
                      //  $(".blueHdng-greenBorder").attr("style", "margin-left: 8px !important");

                        ($('div.acc-segregation[data-group=' + tmpName + '] .blueHdng-greenBorder')).remove();
                        //$('.card-link .blueHdng-greenBorder').replaceWith(head);

                    });


                    $(document).on('click', '#bcktoshow', function(e) {
                               e.preventDefault();
                                var tmpName = ($(this).attr('id'));
                                        $('a.groupNameCheck').parent().show();
                                        $(this).parent().hide();
                                        $('#showing').html(showingAllText);
                                        $('div.acc-segregation').hide();

                                        $('#showallfaq').removeClass('show');
                                        $('.card-header.acc-title').attr('aria-expanded','false');
                                                    $('.card-header.acc-title').addClass('collapsed');
                           // location.reload();
                               $('.accordionContainer').empty();
                               loadFAQView();
                      });
                    // To ensure only 4 categories are loaded on page load event.
                    $('.acc-segregation').hide().slice(0, 4).show();

                    if ($('.faqdetails').length) {
                        $('.faqdetails').parents('.content-wrapper').addClass('faq-wrapper');
                    }

                   $('.faqdetails .card-header').on('keydown', function(e) {
                        var key = e.which;
                        if (key == 13 || key == 32) // the enter key code
                        {

							$('#showallfaq').toggleClass('show');
                            $('#showallfaq').find('p').removeClass('visFaq');
                             $('#showallfaq').find('p').addClass('visFaq');
                            $(this).toggleClass("collapsed");
                            if ($(this).hasClass('collapsed')) {
                                $(this).attr("aria-expanded", "false");
                            } else {
                                $(this).attr("aria-expanded", "true");
                                $(this).next().children().find('.groupNameCheck').first().focus();

                                faqTraverse(event);
                                return false;
                            }
                        }

                            if (key == 38) {
                                $(this).addClass("collapsed").attr("aria-expanded", "false").next().removeClass("show");
                            }
                            if (key == 40) {
                                $(this).removeClass("collapsed").attr("aria-expanded", "true").next().addClass("show");
                                $(this).next().children().find('.groupNameCheck').first().focus();
                            }

                    });



                    var ariaSetSize = $('#showallfaq .groupNameCheck').length;

                    $('#showallfaq .groupNameCheck').each(function(el, list) {
                        var $thisList = $(list);
                        var $ariaPosInset = el + 1;
                        $thisList.attr({
                            'tabindex': '-1',
                            'aria-setsize': ariaSetSize,
                            'aria-posinset': $ariaPosInset
                        });
                    });

                    var ariaSetPos = function() {
                        $('a.groupNameCheck').removeAttr('aria-posinset');
                        var visibleLi = $('#showallfaq p').filter(function() {
                            return $(this).css('display') !== 'none';
                        });
                        $(visibleLi).each(function(ele, vList) {
                            var $visibleList = $(vList);
                            var $visiblePosInset = ele + 1;
                            $visibleList.children('a').attr({
                                'tabindex': '-1',
                                'aria-setsize': ariaSetSize,
                                'aria-posinset': $visiblePosInset
                            });
                        });
                        $(this).removeAttr('aria-posinset aria-setsize');
                    }
                    $(document).on('click', 'a.groupNameCheck', ariaSetPos);
                    $(document).on('keypress', 'a.groupNameCheck', function(e) {
                        if (event.which == 13) ariaSetPos();
                    });

    }

 function loadFAQView(){

	    $('#showallfaq .card-body').empty();
		jsonText.forEach((faq, i) => {
			var listElements = "<p><a href='javascript:void(0);' role='menuitem' id='refId" + i + "' class='groupNameCheck'>" + faq.groupName + "</a></p>";
			$(listElements).appendTo('#showallfaq .card-body');
			segregation = "<div class='acc-segregation' data-group='refId" + i + "'><h3 class='blueHdng-greenBorder'>" + faq.groupName + "</h3><div></div>";
			$(segregation).appendTo('.accordionContainer');
			faqList = faq.faqList;
			faqList.forEach((ques, index) => {
				if (index == 0) {
					accordian = "<div class='card first-card'><div id='faq" + i + index + "' class='card-header collapsed' data-toggle='collapse' role='button' tabindex='0' href='#collapse" + i + index + "' aria-expanded='false' aria-controls='collapse" + i + index + "'><div class='card-link'>" + ques.question + "</div><div class='accordionIconMain accordionPlusIcon'></div></div><div id='collapse" + i + index + "' class='collapse' aria-labelledby='faq" + i + index + "' data-parent='#accordion' ><div class='card-body'>" + ques.answer + "</div></div></div></div>"
				} else {
					accordian = "<div class='card'><div id='faq" + i + index + "' class='card-header collapsed' data-toggle='collapse' role='menu' tabindex='0' href='#collapse" + i + index + "' aria-expanded='false' aria-controls='collapse" + i + index + "'><div class='card-link'>" + ques.question + "</div><div class='accordionIconMain accordionPlusIcon'></div></div><div id='collapse" + i + index + "' class='collapse' data-parent='#accordion' aria-labelledby='faq" + i + index + "'><div class='card-body'>" + ques.answer + "</div></div></div></div>"
				}
				//accordian = "<div class='card'><div class='card-header collapsed' data-toggle='collapse' href='#collapse" + i + index + "' aria-expanded='false'><div class='card-link'>" + ques.question + "</div><div class='accordionIconMain accordionPlusIcon'></div></div><div id='collapse" + i + index + "' class='collapse' data-parent='#accordion' ><div class='card-body'>" + ques.answer + "</div></div></div></div>"
				element = $($('.acc-segregation')[i]);
				$(accordian).appendTo(element);
			})
			if ($(window).width() < 768) {
				if (faqList.length > 4) {
					seeall = '<p class="seeall"><a href="#" class="loadMore">See all</a></p>';
					$(seeall).appendTo(element);

					// closeall = '<p class="closeall"><a href="#" class="loadLess">Close all</a></p>';
					// $(closeall).appendTo(element);

				}
			}

		});
 }



 function faqTraverse(e){
     $('.visFaq .groupNameCheck').on('keydown', function(e) {
      //var key = evt.which;
        firstFaq = $('#showallfaq p:visible').first();
        lastFaq = $('#showallfaq p:visible').last();

        if (e.which == 40) {
            if($(this).parent('p:visible').text() == lastFaq.text()){
               firstFaq.find('a').focus();
			}
             if($(this).parent('p').next('p').css('display')=='none'){
                 $(this).parent('p').next().next().find('a.groupNameCheck').focus();
             }
            else{
                $(this).parent().next().find('a.groupNameCheck').focus();
            }
            e.preventDefault();
        }

        if (e.which == 38) {
			if($(this).parent('p:visible').text() == firstFaq.text()){
               lastFaq.find('a').focus();
			}
             if($(this).parent('p').prev('p').css('display')=='none'){
                 $(this).parent('p').prev('p').prev('p').find('a.groupNameCheck').focus();

             }
            else{
                $(this).parent('p').prev('p').find('a.groupNameCheck').focus();

            }
		e.preventDefault();
        }
        if (e.which == 27) {
            $('.faqdetails .card-header:first').focus();
            $('.faqdetails .card-header').addClass("collapsed").attr("aria-expanded", "false").next().removeClass("show");
        }

	});

 }

});
