var categoryList;
var sectionList;
var detailHTML;
var defaultServiceURL;
var detailServiceURL;
var selectedItems="selectedItems";
$(document).ready(function () {
	if ($('.default-block').length) {
		
		/* Setting up the base service URL for detail HTML fetch*/
		 
		
		let pathname=$("#currentPagePath").val();
		
		if(pathname.includes(".html")){
			pathname = pathname.split(".html")[0];
		}
		if(pathname.includes(".headless")){
			pathname = pathname.split(".headless")[0];
		}	
		var urlBase = pathname + "." + "learnmore"

		/* Expand/collapse filter accordion */
		$('.panel-collapse').on('show.bs.collapse', function () {
			$(this).siblings('.panel-heading').addClass('active');
		});
		$('.panel-collapse').on('hide.bs.collapse', function () {
			$(this).siblings('.panel-heading').removeClass('active');
		});

		loadCheckboxes();
		applyScrollOnDefault();
		getLastCheckedFilter();
		applyCheckboxChange();
		seeAllClick();
	}

	/* See all link click */
	function seeAllClick() {
		$(document).on('click', '.see-all-link', function (event) {
			event.preventDefault();
			var $this = $(this);
			var section = $this.parents('.vcm-bg-wrapper').find('.lob-name')[0].innerHTML.trim();
			$('input[type="checkbox"]').each(function () {
				if ($(this).val() == section) {
					$(this).prop("checked", true).trigger("change");
				}
			});
		});
	}

	/* Dynamic loading of filter checkboxes */
	function loadCheckboxes() {
		sectionList = $('.default-block .vcm-bg-wrapper').find('.lob-title');
		var name, isAdded;
		categoryList = [];
		$.each(sectionList, function (item, obj) {
			isAdded = false;
			if (categoryList.length) {
				$.each(categoryList, function (i, ob) {
					if (ob.title.indexOf(obj.innerHTML.trim()) > -1) {
						isAdded = true;
					}
				});
			}
			if (!isAdded) {
				name = $(obj).closest('.vcm-bg-wrapper').find('.lob-name')[0].innerHTML.trim();
				categoryList.push({ "title": obj.innerHTML.trim(), "name": name });
			}
		});
		$.each(categoryList, function (index, obj) {
            let id = "filterId" + index
            $('.filter-checkbox').append(
                '<div class="container-checkbox" role="listitem">' +
                '<label for='+id+'><input id='+id+' type="checkbox" value="' + obj.name + '" class="check_box" tabindex="0"><span class="checkmark" aria-hidden="true"></span><span class="ax_inlinblock">'+obj.title+'</span></label>' +
                '</div>');
        });
	}

	/* Getting the previousely checked filter values from local storage */
	function getLastCheckedFilter() {
		let check_box_state={}
		$('input[type="checkbox"]').each(function () {
			var checkedVal = $(this).val();
			
			var cboxChecked = false;
			if (window.location.href.indexOf('?') > 0 && sharedJS.nonNullCheck(getParameterByName('selected'))) {
				let selectedItems = getParameterByName('selected');
				let selectedItemsArray = selectedItems.split(",");
				if ($.inArray(checkedVal, selectedItemsArray) >= 0) {
					cboxChecked = true;
				}
				else{
					cboxChecked = false;
				}
				
			}
			else {
				  let localstoreItems=localStorage.getItem(selectedItems);
				  if(sharedJS.nonNullCheck(localstoreItems)){
					  let localstoreItemsArr=localstoreItems.split(",");
					  if ($.inArray(checkedVal, localstoreItemsArr) >= 0) {
							cboxChecked = true;
							
					   }
					  else{
						  cboxChecked = false;
					  }
				  }
				  else{
					  cboxChecked = false;
				  }
			}
			// On page load check if any of the checkboxes has previously been selected and mark it as "checked"
			if (cboxChecked) {
				$(this).prop('checked', true);
			}
			check_box_state[$(this).val()]=$(this).is(':checked');
		});
		updateURL(check_box_state);
		processCheckboxSelection();
	}

	/* On checkbox change/click */
	function applyCheckboxChange() {
		$(document).on('change','.check_box', function () {
			processCheckboxSelection();
		});
	}

	//calling custom scroll onload for all sections
	function applyScrollOnDefault() {
		$.each(sectionList, function (item, obj) {
			var $this = $(obj).closest('.vcm-bg-wrapper');
			$this.find('.columncontrol').wrap('<div class="scrollable-content"></div>');

			$this.find('.col-lg-9').addClass('box-size-75').find('.col-12').removeClass("col-12");
			$this.find('.col-lg-6').addClass('box-size-50').find('.col-12').removeClass("col-12");
			$this.find('.col-lg-3').addClass('box-size-25').find('.col-12').removeClass("col-12");
            $this.find('.col-lg-4').addClass('box-size-33').find('.col-12').removeClass("col-12");

			addCustomScroll();
			$(".mCustomScrollBox").removeAttr("tabindex");
		});
	}

	function getParameterByName(name, url) {
		if (!url) {
			url = window.location.href;
		}
		name = name.replace(/[\[\]]/g, '\\$&');
		var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
			results = regex.exec(url);
		if (!results) return null;
		if (!results[2]) return '';
		return decodeURIComponent(results[2].replace(/\+/g, ' '));
	}

	function updateQueryStringParameter(check_box_state, uri) {
		let selectedItemsKey = "selected";
		if (!uri) {
			uri = window.location.href;
		}
		var re = new RegExp("([?&])" + selectedItemsKey + "=.*?(&|$)", "i");
		var separator = uri.indexOf('?') !== -1 ? "&" : "?";
		var selectedItems = getParameterByName(selectedItemsKey, uri);

		if (sharedJS.nonNullCheck(selectedItems) && uri.match(re)) {
			let selectedItemsArray = selectedItems.split(',');
			$.each(check_box_state , function(key, value) { 
				if (value) {
					if ($.inArray(key, selectedItemsArray) < 0) {
						selectedItemsArray.push(key);
					}
				}
				else {
					if ($.inArray(key, selectedItemsArray) >= 0) {
						selectedItemsArray = jQuery.grep(selectedItemsArray, function(value) {
							return value != key;
						});
					}
				}
			});
			if (selectedItemsArray.length > 0) {
				uri = uri.replace(re, '$1' + selectedItemsKey + "=" + selectedItemsArray.toString() + '$2');
			}
			else{
				uri=uri.split("?")[0];
			}
		}
		else {
			let selectedItemsArray = [];
			$.each(check_box_state , function(key, value) { 
				if (value) {
					selectedItemsArray.push(key);
				}
			});
			if(selectedItemsArray.length > 0 && uri.match(re)){
				uri = uri.replace(re, '$1' + selectedItemsKey + "=" + selectedItemsArray.toString() + '$2');
			}
			else if (selectedItemsArray.length > 0 && !uri.match(re)) {
				uri = uri + separator + selectedItemsKey + "=" + selectedItemsArray.toString();
			}
			else{
				uri=uri.split("?")[0];
			}
		}
		return uri;
	}

	function updateURL(check_box_state) {
		if (history.pushState) {
			var newurl = updateQueryStringParameter(check_box_state);
			window.history.pushState({ path: newurl }, '', newurl);
		}
	}
	/* Function to show and hide category divs based on the checkbox selection */
	function processCheckboxSelection() {
		let selectedCheckBox=[];
		$('input[type="checkbox"]').each(function () {
			if($(this).is(':checked')){
				selectedCheckBox.push($(this).val());
			}
		});
		localStorage.setItem(selectedItems, selectedCheckBox); //setting the value in local storage
		if ($('.check_box:checked').length === 0) { //if none selected
			$.each(sectionList, function (item, obj) {
				var $this = $(obj).closest('.vcm-bg-wrapper');
				$this.removeClass("d-none").removeClass('d-block');
				if ($this.next().hasClass('detailed-blocks')) {
					$this.next().addClass('d-none').removeClass('d-block');
				}
				$this.find('.see-all-row').addClass('d-block').removeClass('d-none');
				addCustomScroll();
				$(".mCustomScrollBox").removeAttr("tabindex");
			});
		} else {//other than none selected
			// hiding all default and detailed section
			$.each(sectionList, function (item, obj) {
				var $this = $(obj).closest('.vcm-bg-wrapper');
				$this.find('.see-all-row').addClass('d-block').removeClass('d-none');
				$this.addClass("d-none").removeClass('d-block');
				if ($this.next().hasClass('detailed-blocks')) {
					$this.next().addClass('d-none').removeClass('d-block');
				}
			});
			$('input[type="checkbox"]').each(function () {
				var cboxValue = $(this).val();
				if ($(this).is(':checked')) { //if checked
					var hasCheckedSection = false;
					$.each(sectionList, function (item, obj) {
						var $this = $(obj).closest('.vcm-bg-wrapper');
						if ($this.find('.lob-name')[0].innerHTML.trim() == cboxValue) { //if matching section exists
							$this.removeClass("d-none").addClass('d-block');//showing default section
							$this.find('.see-all-row').addClass('d-none').removeClass('d-block');
							$last = $this;
							hasCheckedSection = true;
						}
					});
					if (hasCheckedSection) {
						if ($last.next().hasClass('detailed-blocks')) {// if detailed section exists
							$last.next().removeClass("d-none").addClass('d-block'); // showing it
						} else { // if no detailed section
							//ajax call to fetch the detailed HTML
							detailServiceURL = urlBase + "." + cboxValue + ".html";
							$.ajax({
								type: "GET",
								url: detailServiceURL,
								dataType: "text",
								success: function (data) {
									detailHTML = data;

									$last.after('<div class="detailed-blocks">' + detailHTML + '</div>');
									$last.next().find('.columncontrol').wrap('<div class="scrollable-content"></div>');

									$last.next().find('.col-lg-9').addClass('box-size-75').find('.col-12').removeClass("col-12");
									$last.next().find('.col-lg-6').addClass('box-size-50').find('.col-12').removeClass("col-12");
									$last.next().find('.col-lg-3').addClass('box-size-25').find('.col-12').removeClass("col-12");

									//addCustomScroll();														   
									if ($(window).width() < 768) {
										$(".scrollable-content").mCustomScrollbar("destroy"); //destroy scrollbar 
									} else {
										addCustomScroll();
										$(".mCustomScrollBox").removeAttr("tabindex");
									}

								},
								error: function () {
									console.log('Service fetch failed.Please check the service url');
								}
							});

						}
						$(window).resize(function () {
							if (($(this).width() < 768) && ($last.next().hasClass('d-block'))) {
								$(".scrollable-content").mCustomScrollbar("destroy"); //destroy scrollbar 
							} else {
								addCustomScroll();
								$(".mCustomScrollBox").removeAttr("tabindex");
							}
						}).trigger("resize");
					}
				}
			});
		}
	}

	/* Function to invoke custom scroll */
	function addCustomScroll() {
		$(".scrollable-content").mCustomScrollbar({
			axis: "x",
			alwaysShowScrollbar: false,
			advanced: { autoExpandHorizontalScroll: true },
			contentTouchScroll: true,
			documentTouchScroll: true,
			autoScrollOnFocus: true,
			theme: "dark"
		});
	}

	$(document).on('click', '.check_box' , function () {
		let check_box_state={};
		$('input[type="checkbox"]').each(function () {
			let itemObj={};
			let key = $(this).val();
			let value = $(this).is(':checked');
			check_box_state[key]=value;
		});
		updateURL(check_box_state);
	});

     //filter title accordion
    $(".learnmorefilter .filter-wrapper .accordion-filter .heading-filter").keypress(function(key) {
        var key = key.which;
        if (13 == key || 32 == key) return $(this).toggleClass("collapsed").parent().toggleClass("active").next().toggleClass("show"), $(this).hasClass("collapsed") ? $(this).attr("aria-expanded", "false") : $(this).attr("aria-expanded", "true"), !1
    });

    //filtercheckbox
    $(".learnmorefilter .container-checkbox :checkbox.check_box").focusin(function(){$(this).parent().children(".checkmark").css("border","2px solid #000")}),$(".container-checkbox :checkbox.check_box").focusout(function(){$(this).parent().children(".checkmark").css("border","1px solid #004A98")}),
    $(".learnmorefilter .container-checkbox :checkbox.check_box").focusin(function(){$(this).parent().children("body.using-mouse .checkmark").css("border","1px solid #004A98")}),$(".container-checkbox :checkbox.check_box").focusout(function(){$(this).parent().children("body.using-mouse .checkmark").css("border","1px solid #004A98")});

    setTimeout(function() {
        $('.scrollable-content').children('.mCustomScrollBox ').removeAttr('tabindex');
    }, 1000);
});