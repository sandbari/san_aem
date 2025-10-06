var JSONDATA;
var checkList;
var shownUptoIndex = 0;
var filterList = [];
var baseURL = '';
var isFilterAppliedFlag = false;
var isSearchAppliedFlag = false;
/*var topicColor = [
    { "topic": "Investing", "class": "trapezium-purple" },
    { "topic": "ETFs", "class": "trapezium-lightblue" },
    { "topic": "Retirement", "class": "trapezium-blue " },
    { "topic": "Practice Management", "class": null }
]*/

$(document).ready(function () {

    $('body').on('mousedown', function() {
        $('body').addClass('using-mouse');
    });
    // Re-enable focus styling when Tab is pressed
    $('body').on('keydown', function(event) {
        if (event.keyCode === 9) {
            $('body').removeClass('using-mouse');
        }
    });

    /* Expand/collapse filter accordion */
    $('.panel-collapse').on('show.bs.collapse', function () {
        $(this).siblings('.panel-heading').addClass('active');
    });
    $('.panel-collapse').on('hide.bs.collapse', function () {
        $(this).siblings('.panel-heading').removeClass('active');
    });

    fetchCheckBoxValues();
    fetchJSONData();
    //secondlineUnderline();

    //click on load more
    $('.blog-button').on('click', function () {
        loadMoreBlogs();
    });


    //click on filter box
    $('.check_box').change(function () {
        filterBlogs($(this));
    });

    //Search input field enter key press
    $('#blog-search').keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            var selectValue = $(this).text();
            $('.check_box').each(function () {

                $(this).prop('checked', false)
            })
            searchBlogs();
        }
    });

     $(".blog-trapezium-text").keypress(function(ke) {
        var ke = ke.which;
        if (13 == ke || 32 == ke) {
            $('.blog-trapezium-text').click()
        }
     });

    /* $('#blog-search').keyup(debounce(function() {
         searchBlogs();
     }, 1000));*/
   $(document).on('click keydown', '.blog-trapezium-text', function (e) {
       if( ( e.type == 'click' ) || ( e.type == 'keydown' && e.which  == 13 ) ) {
           e.preventDefault();
           var selectValue = $(this).text();
           $('.check_box').each(function () {

               if (selectValue === this.value) {
                   $(this).prop('checked', true)
               }
           })
           filterList.push(e.target.innerHTML);
           isFilterAppliedFlag = true;
           applyInitConditions()
       }
    });

});

function alltopics(){
    var getFilterTopic = localStorage.getItem('selectedFilterTopic');
    if (typeof getFilterTopic !== 'undefined' && getFilterTopic !== null) {
        $('.check_box').each(function (e) {
            if (this.value === getFilterTopic) {
                $(this).prop('checked', true)
                filterBlogs($(this));
                localStorage.removeItem('selectedFilterTopic')
            }
        });
    }
}
/**Function called for debounce */
function debounce(func, wait, immediate) {
    var timeout;
    return function () {
        var context = this,
            args = arguments;
        var later = function () {
            timeout = null;
            if (!immediate) func.apply(context, args);
        };
        var callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func.apply(context, args);
    };
};

/**Function called on load more button click */
function loadMoreBlogs() {
    var configuredItemsCount = JSONDATA.itemsOnLoadMore;
    var itemsToLoad = configuredItemsCount != null && configuredItemsCount != undefined &&
        configuredItemsCount != '' && configuredItemsCount != '0' ? configuredItemsCount : '9';
    if (isFilterAppliedFlag) {
        loadBlogItems(JSONDATA, parseInt(itemsToLoad), filterList);
    } else {
        loadBlogItems(JSONDATA, parseInt(itemsToLoad), checkList);
    }
    //secondlineUnderline();
}

/**Function called while filtering the blog items */
function filterBlogs(event) {
    var searchValue = $('#blog-search')[0].value;
    if (searchValue !== null && searchValue !== '') {
        isSearchAppliedFlag = false;
        $('#blog-search')[0].value = '';
    }
    if (event.is(":checked")) {
        isFilterAppliedFlag = true;
        filterList.push(event[0].value);
    } else {
        if (filterList.indexOf(event[0].value) > -1) {
            filterList.splice(filterList.indexOf(event[0].value), 1);
        }
        if (filterList.length < 1) {
            isFilterAppliedFlag = false;
        }
    }
    applyInitConditions();
}

/**Function to search the data */
function searchBlogs() {
    var searchValue = $('#blog-search')[0].value;

    if (searchValue !== null && searchValue !== '' && searchValue.length > 3) {
        isSearchAppliedFlag = true;
        isFilterAppliedFlag = false;
    } else {
        isSearchAppliedFlag = false;
    }
    applyInitConditions();
}

/**Function to draw the green line under the header especially when it comes in two lines*/
function secondlineUnderline() {
    var parentEl = document.getElementsByClassName('section-heading-container');
    for (var i = 0; i < parentEl.length; i++) {
        var currentEl = parentEl[i];
        var button = currentEl.childNodes[1];
        var words = button.innerText.split(/[\s]+/); // An array of allthe words split by spaces, since that's where text breaks by default. var
        var lastLine = []; // Putall words that don't change the height here.
        var currentHeight = currentEl.clientHeight; // The starting height.
        while (1) {
            lastLine.push(words.pop());
            button.innerText = words.join(' ');
            if (currentEl.clientHeight < currentHeight) {
                parentEl[i].querySelector('.section-heading').classList.add("border-custom");
                var span = document.createElement('span');
                span.classList = ['underline'];
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
}

/**Function to fetch the checkbox/categories that are displayed on the screen */
function fetchCheckBoxValues() {
    var list = $('.check_box');
    checkList = [];
    $.each(list, function (item, obj) {
        checkList.push(obj.value);
    });
}

/**Function to fetch the json data for loading the blog items */
    function fetchJSONData() {
        var urldom = $('#blogContainer').attr('data-resource-path');
        //var urldom = '/bin/demo/insights';
        //JSONDATA = blog_data;
        //JSONDATA.blogList.unshift(JSONDATA.featureBlog);
        //loadBlogItems(blog_data,11,checkList);
        if (urldom != null && (typeof urldom != 'undefined'))  {
         $.ajax({
            type: "GET",
            url: urldom,
            dataType: "json",
            success: function(data) {
                JSONDATA = data;
                baseURL = JSONDATA.authorLink;
                if(JSONDATA.featureBlog){
                    JSONDATA.blogList.unshift(JSONDATA.featureBlog);
                }
                applyAuthorFilter();
                //alltopics();
                var url_string = window.location.href;
                var url = new URL(url_string);
                var paramVal = url.searchParams.get("selected");
                if(paramVal!==null){
                    var paramVal = paramVal.split(",");
                    var arrayLength = paramVal.length;
                    for (var i = 0; i < arrayLength; i++) {
                        $("input[type=checkbox][value='"+paramVal[i]+"']").prop("checked",true);
                        isFilterAppliedFlag = true;
                        filterList.push(paramVal[i]);
                        applyInitConditions();
                    }

                } else {
                    loadBlogItems(JSONDATA, 11, checkList);
                }
                        //click on load more

                },
            error: function() {
                console.log('JSON fetch failed.Please check the JSON url');
            }
         });
         }
    }


function applyAuthorFilter() {
    if (window.location.href.indexOf("author.") > -1) {
        $(".container").attr('data-show-filter', 'false')
        // if ($(".container").attr('data-show-filter') == "false") {
        //}

        var authrStr = window.location.href;
        authrStr = authrStr.match("/blog/author.(.*)");
        var authrStr = authrStr[1];
        authrStr=authrStr.replace(".html","");
        var updateBreadCrumb  = authrStr.replace(/-/g," ").toUpperCase();
        $(".breadcrumb").find('li.active a').text(updateBreadCrumb);
        var blogItemlistTemp = [];
        if (!!JSONDATA.blogList) {
            var getAuthor = JSONDATA.blogList;
            for (var i = 0; i < getAuthor.length; i++) {
                if (!!getAuthor[i].authorName) {
                   var authorArray = $.makeArray(getAuthor[i].authorName);
                    var keys = [];
                    for(key in getAuthor[i].authorName) {
                            if(getAuthor[i].authorName.hasOwnProperty(key)) {
                                    keys.push(key);
                            }
                        }
  				for (var j = 0; j < keys.length; j++) {
                      var authorURL =authorArray[0][keys[j]].trim();
                    authorURL = authorURL.replace(/[^a-z0-9\s]/gi, '').replace(/[ ]/g, "-").toLowerCase();
                    if (authorURL === authrStr) {
                        blogItemlistTemp.push(getAuthor[i]);
                    }
                    };

                }

            }
        }

        JSONDATA.blogList = blogItemlistTemp;
    }
}

/**Function to apply the initial conditions like to clear the dom, remove blog-hide class,reset shownUptoIndex */
function applyInitConditions() {
    var DomList = $('#blogContainer');
    $($($(DomList).parent()).children().find('.blog-hide')).removeClass('blog-hide');
    $(DomList).empty();
    shownUptoIndex = 0;
    if (isFilterAppliedFlag) {

        loadBlogItems(JSONDATA, 11, filterList);
    } else {
        loadBlogItems(JSONDATA, 11, checkList);
    }
    //secondlineUnderline();
}

/**Function to load the blog items
 * data  -  the JSON data
 * count - the nimber of data to be shown at a time. Initially 11. On load more its 9
 * filterListArray - array of filter applied. On initial all the check list is passed which is in checkList.
 * 					When filter is applied by checking the checkbox, the filterList is passed.
 */
function loadBlogItems(data, count, filterListArray) {
    var loadBlogFlag = false;
    var flag = 0;
    var authorlink = data.authorLink;
    var topicLink = data.topicLink;
	$('.no-search-result').hide();
    $.each(data.blogList, function (index, obj) {
        //loadBlogFlag = false;
        if (isSearchAppliedFlag) {
            var searchValue = $('#blog-search')[0].value.toLowerCase();
            if ((obj.title && obj.title.toLowerCase().indexOf(searchValue) > -1) ||
                (obj.description && obj.description.toLowerCase().indexOf(searchValue) > -1)) {
                loadBlogFlag = true;
            }
            else if (shownUptoIndex <= index && flag < count) {
                    shownUptoIndex++;
                }
        } else {
            loadBlogFlag = true;
        }
        if (loadBlogFlag) {
            var topicList = obj.topic.split(', ');
            var res = filterListArray.filter( function(n) { return this.has(n) }, new Set(topicList) );
			if (res !== null && res.length > 0) {

                if (flag < count) {

                    if (index == shownUptoIndex) {
                        flag++;
                        shownUptoIndex++;

                        var blogTemp = $('#blogTemplate').clone();
                        var blogItem = $(this)[0];

                        //trapezium color
                        /*var color = topicColor.filter(function(objClass) {
                            return objClass.topic.indexOf(blogItem.topic) > -1;
                        });
                        if (color[0].class != null) {
                            $(blogTemp.find('.blog-trapezium')).addClass(color[0].class);
                        }*/

                        $(blogTemp.find('.blog-trapezium-text')[0]).html(blogItem.topic);
                        $(blogTemp.find('.blog-trapezium-text')[0]).attr('href', topicLink);
                        $(blogTemp.find('.blog-img')[0]).attr('src', blogItem.imagePath);
                        $(blogTemp.find('.section-heading')[0]).html(blogItem.title);

                        if (blogItem.description.includes('href')) {
                            var pdfDescription = blogItem.description;
                            var startChar = pdfDescription.indexOf('href');
                            var endChar = pdfDescription.indexOf('.pdf');
							var hrefValue = pdfDescription.slice(startChar+6,endChar+4);
                        	$(blogTemp.find('.blog-img')[0]).wrap('<a target="_blank" href="' + hrefValue + '" />');
                            $(blogTemp.find('.section-heading')[0]).attr('href', hrefValue);
                            $(blogTemp.find('.section-heading')[0]).attr('target', '_blank');
                        }
                        else {
							$(blogTemp.find('.blog-img')[0]).wrap('<a href="' + blogItem.blogPagePath + '" />');
							$(blogTemp.find('.section-heading')[0]).attr('href', blogItem.blogPagePath);
                        }


                        $(blogTemp.find('.blog-date')[0]).html(blogItem.authorDate);
                        if (blogItem.hideAuthorDate) {
                            $(blogTemp.find('.blog-date')[0]).css('opacity', '0');
                        }
                        $(blogTemp.find('.blog-desc-text')[0]).html(blogItem.description);

                        //Author link
                        var authorArray = blogItem.authorName;
                        if (authorArray) {
                            var authorhtml = $(blogTemp.find('.blog-author-name')).clone();
                            var authorKeys=Object.keys(blogItem.authorName).length;
                            $(blogTemp.find('.blog-author-container')).empty();
                            var counter = authorKeys;
                            var authorUrl = data.authorLink;
                            $.each(authorArray, function(index, obj) {
                                var authorName = obj.trim().replace(/[^a-z0-9\s]/gi, '').replace(/[ ]/g, "-").toLowerCase();
                                var authorURL = "";
                                if (authorUrl != null && typeof authorUrl != 'undefined') {
                                    if (authorUrl.lastIndexOf('.html') > 0) {
                                        authorURL = authorUrl.replace(".html", "." + authorName + ".html");
                                    } else {
                                        authorURL = authorUrl + "." + authorName;
                                    }
                                }
                                var authorhtmlTemp = authorhtml.clone();
                                authorhtmlTemp[0].innerText = obj;
                                $(authorhtmlTemp[0]).attr('href', authorURL);
                                $(blogTemp.find('.blog-author-container')).append(authorhtmlTemp);
                                if(counter > 1){
                                    if((authorKeys > counter-1) && (counter > 2)){
                                        $(authorhtmlTemp[0]).append(', ');
                                    }
                                    if(counter <= 2){
                                        $(authorhtmlTemp[0]).append(' & ');
                                    }
                                    counter--;
                                }
                                if (index < authorArray.length - 1) {
                                    $(blogTemp.find('.blog-author-container')).append(',')
                                }
                            });
                        }
                        else {
								$(blogTemp.find('.blog-author-name')).remove();
                        }

                        var colWiderFlag = false;
                        var DomList = $('#blogContainer');
                        if (DomList.children().length == 0) {
                            colWiderFlag = true;
                            var blogRow = $('#blog-row').children().clone();
                            DomList.append(blogRow);
                            $(blogTemp.find('.blog-box-border')[0]).addClass('blog-wider');
                        }

                        var rowindex = $(DomList).children().length;
                        var ParentRowdiv = $($(DomList).children()[rowindex - 1]); //row
                        var colIndex = $(ParentRowdiv).children().length;
                        if ((DomList.children().length > 1 && colIndex < 3) || (DomList.children().length == 1 && colIndex < 2)) {
                            if (colWiderFlag) {
                                var colRow = $('#blog-col8').children().clone();
                            } else {
                                var colRow = $('#blog-col-def').children().clone();
                            }
                            ParentRowdiv.append(colRow);
                            var colIndex = $(ParentRowdiv).children().length;
                            var ParentColdiv = $($(ParentRowdiv).children()[colIndex - 1]); //col
                            $(ParentColdiv).append(blogTemp.children());
                        } else {
                            var blogRow = $('#blog-row').children().clone();
                            DomList.append(blogRow);
                            var rowindex = $(DomList).children().length;
                            var ParentRowdiv = $($(DomList).children()[rowindex - 1]); //row
                            var colRow = $('#blog-col-def').children().clone();
                            ParentRowdiv.append(colRow);
                            var colIndex = $(ParentRowdiv).children().length;
                            var ParentColdiv = $($(ParentRowdiv).children()[colIndex - 1]); //col
                            $(ParentColdiv).append(blogTemp.children());
                        }
                    }
                }
            } else {
                if (shownUptoIndex <= index && flag < count) {
                    shownUptoIndex++;
                }
            }
        }

    });
    if (loadBlogFlag===true) {
        $('.no-search-result').hide();
	}
	else{
        $('.no-search-result').show();
	}
	if (data.blogList.length == shownUptoIndex) {
        var DomListData = $('#blog-load-more');
        $(DomListData).children().addClass('blog-hide');
    }
}