(function($) {
    "use strict";
    var jsonData = [],
        searchData = [],
        selectedTag = "",
        resourcePath = $('.productmenu-comp').attr('data-resource-path');
    $(document).ready(function() {
        $("#subMenu").css('display', 'none');
        $('#errorMsg').hide();
        if ($('.productmenu').length) {
            $.ajax({
                contentType: "application/json",
                context: this,
                type: 'GET',
                url: resourcePath,
                dataType: "json",
                success: function(data) {
                    jsonData = data;
                    var tabLinkCount = $('#tabs > div').length;
                    $('#tabs > div').first().addClass("tabLink_1");
                    data.forEach(function (tab, i) {
                    	var tabIndex = i;
                        var listblock = $("<div id='listblock" + i + "' class='list-block'></div>").appendTo("#tags");
                        var ul = $("<ul id='tags" + i + "' class='list-group'  role='tablist'></ul>").appendTo(listblock);
                        var prodBtn = $("<a href='" + data[i].buttonLink + "' class='btn-primary'>" + data[i].buttonText + "<span class='btn-triangle-rt'></span></a>").appendTo(listblock);
                        //var thisTab = $("<div class='col-sm-12 col-lg-6' tabindex='0' aria-expanded='false' role='button' id='tab" + i + "' data-id='#listblock" + i + "'><h3>" + tab.tabTitle + "</h3><a class='productmenu-header-link' href='" + tab.tabLink + "'>" + tab.tabTitle + "</a></div>").appendTo("#tabs");

                        var thisTab = $("<div tabindex='0' aria-expanded='false' role='button' id='tab" + i + "' data-id='#listblock" + i + "'><h3>" + tab.tabTitle + "</h3><a class='productmenu-header-link' href='" + tab.tabLink + "'>" + tab.tabTitle + "</a></div>");//.appendTo("#tabs");

                        if (tabLinkCount === 0) {
                            thisTab.appendTo("#tabs");
                            $('#tabs > div').addClass("col-sm-12 col-lg-6");
                        } else if (tabLinkCount === 1) {
                            $(thisTab).insertBefore(".tabLink_1");
                            $('#tabs > div').addClass("col-sm-12 col-lg-4");
                        } else if (tabLinkCount === 2) {
                            $(thisTab).insertBefore(".tabLink_1");
                            $('#tabs > div').addClass("col-sm-12 col-lg-3");
                        } else if (tabLinkCount === 3) {
                            $(thisTab).insertBefore(".tabLink_1");
                            $('#tabs > div').addClass("column5Tab");
                        }
                        //linkTabs append via json
                        //$("<div class='col-sm-12 col-lg-6' data-id='tags" + i + "'><a class='productmenu-header-link' href='"+ tab.tabLink +"'>" + tab.tabTitle + "</a></div>").appendTo("#linkTabs");
            
                        thisTab.click(function () {
                            if ($(this).hasClass("active")) {
                                var ulli1 = $('#tabs>div.active').data('id')
                                $('.list-block').css('display', 'none');
                                $(this).removeClass("active");
                                $(this).attr( 'aria-expanded', 'false');
            
                            } else {
                                $("#tabs>div").removeClass("active");
                                $("#tabs>div").attr( 'aria-expanded', 'false');
                                $('.list-block').css('display', 'none');
                                $(this).addClass("active");
                                $(this).attr( 'aria-expanded', 'true');
                            }
            
                            if ($("#tabs>div").hasClass("active")) {
                                $("#subMenu").css('display', 'flex');
                                var ulli2 = $('#tabs>div.active').data('id');
                                $(ulli2).css('display', 'block');
            
                            } else {
                                $("#subMenu").css('display', 'none');
            
                            }
            
                            ul.find("li").removeClass("active");
                            ul.find("li").first().trigger("click");
                            $('.arrow_box.list-group-item a').attr('aria-selected', 'false');
                            $('.arrow_box.list-group-item.active a').attr('aria-selected', 'true');
                        });

                        $(thisTab).keypress(function(r) {
                            var r = r.which;
                            if (13 == r ) {
                                thisTab.click();
                                $('li.arrow_box.list-group-item a').attr('tabindex', -1);
                                $('li.arrow_box.list-group-item.active a').attr('tabindex', '0').focus();
                                $('.arrow_box.list-group-item a').attr('aria-selected', 'false');
                                $('.arrow_box.list-group-item.active a').attr('aria-selected', 'true');
                            }
                        });

                        tab.childTagList.forEach(function (tag,index) {
                        	var liIndex = "tabChildListIndex"+tabIndex+index;
                            var li = $("<li id='" + liIndex + "' aria-controls='aria_" + liIndex + "' class='arrow_box list-group-item'><a href='#/'role='tab' class='prod-menu-tabLink' aria-selected='false'>" + tag.tagTitle + "</a></li>").appendTo(ul);
            
                            li.on('click keydown', function () {
                                selectedTag = tag.tagTitle;
                                $("#search").val("");
                                ul.find("li").removeClass("active");
                                $(this).addClass("active");
                                $('#errorMsg').hide();
                                searchData = tag.taggedPages;
                                $("#childTags1").html("");
                                $("#childTags2").html("");
                                $("#searchResults").html("");
                                $('.col-md-9.mf-rt-col').attr('aria-labelledby', $(this).attr('id'));
                                $('.col-md-9.mf-rt-col').attr('id', $(this).attr('aria-controls'));
                                searchData.forEach(function (childTag, ind) {
                                    var childTagsID = (ind < (searchData.length / 2) ? "#childTags1" : "#childTags2");
                                    var childTag = $("<li class='list-group-item'><a href='" + childTag.pageLink + "'>" + childTag.pageTitle + "</a></li>").appendTo(childTagsID);
                                });
                                $('.prod-menu-tabLink').on(' keydown', function(e){
                                    if ( (e.which == 32) || (e.which == 13) ) {
                                        $('.prod-menu-tabLink').attr('aria-selected', 'false');
                                        $(this).attr('aria-selected', 'true');
                                    }
                                })
                                $('.prod-menu-tabLink').on(' click', function(e){
                                        $('.prod-menu-tabLink').attr('aria-selected', 'false');
                                        $(this).attr('aria-selected', 'true');
                                })
                                /*searchData.forEach(function(childTag, ind) {
                                    var childTagsID = (ind < (searchData.length / 2) ? "#childTags1" : "#childTags2");
                                    var childTag = $("<li class='list-group-item'><a href='" + childTag.pageLink + "'>" + childTag.pageTitle + "</a></li>").appendTo(childTagsID);
                                });*/
                                $('.productmenu-content .list-block').each(function(){
                                    var currList = $(this).find('.list-group li');
                                    var totalList = $(currList).length;
                                    $('.prod-menu-tabLink').attr('aria-setsize', totalList);
                                    $(currList).each(function(i, item){
                                        var $currItem = $(item).find('a');
                                        console.log($currItem);
                                        var $posNavlinset = i+1;
                                        $currItem.attr('aria-posinset', $posNavlinset);
                                    })
                                })
                            });
                        });
                    });
                        $('li.arrow_box.list-group-item a').on('keydown', function(e) {
                            var ke = e.which;
                            if (40 == ke) {
                                $('li.arrow_box.list-group-item a').attr('tabindex', -1);
                                $(this).parent().next().find('a').focus();
                                return false;
                            }
                            if (38 == ke) {
                                $('li.arrow_box.list-group-item a').attr('tabindex', -1);
                                $(this).parent().prev().find('a').focus();
                                return false;
                            }
                            if (9 == ke) {
                                $(this).parents('.list-block').find('a.btn-primary').focus();
                            }
                            e.preventDefault();
                        });
                        $('.list-block a.btn-primary').on('keydown', function(e) {
                            if (e.which === 9 && e.shiftKey) {
                                $(this).parent().children('ul').children('li.arrow_box.list-group-item.active').find('a').attr('tabindex', '0');
                            }
                        })

                        $('#tags0 li.arrow_box.list-group-item a:first').on('keydown', function(e) {
                            if (e.which == 38) {
                                $('#tags0 li.arrow_box.list-group-item a:last').focus();
                            }
                        });
                        $('#tags0 li.arrow_box.list-group-item a:last').on('keydown', function(e) {
                            if (e.which == 40) {
                                $('#tags0 li.arrow_box.list-group-item a:first').focus();
                            }
                        });
                        $('#tags1 li.arrow_box.list-group-item a:first').on('keydown', function(e) {
                            if (e.which == 38) {
                                $('#tags1 li.arrow_box.list-group-item a:last').focus();
                            }
                        });
                        $('#tags1 li.arrow_box.list-group-item a:last').on('keydown', function(e) {
                            if (e.which == 40) {
                                $('#tags1 li.arrow_box.list-group-item a:first').focus();
                            }
                        });
                    	$("#search").keyup(function () {
                            var val = $(this).val().toLowerCase();
                            $("#tags .list-group").find("li").removeClass("active");
                            $("#childTags li").hide();
							var errorMsg = $('#errorMsg').text();
                            var matchfound = false;
                            $("#childTags1").html("");
                            $("#childTags2").html("");
                            jsonData.forEach(function (tab, i) {
                                var selectedTab = $('#tabs>div.active>h3').text();
                                if (selectedTab == tab.tabTitle) {
                                    tab.childTagList.forEach(function (tagList) {
                                        tagList.taggedPages.forEach(function (pageLinkList) {
                                            if (pageLinkList.pageTitle.toLowerCase().indexOf(val) >= 0) {
                    
                                                $("<li><a href='" + pageLinkList.pageLink + "'>" + pageLinkList.pageTitle + "</a></li>").appendTo("#searchResults");
                                                matchfound = true;
                                                $('#errorMsg').hide();
                                            }
                                        })
                                    })
                                }
                                if (!matchfound) {
                                    $('#errorMsg').show().html(errorMsg);
                                    $("#childTags1").html("");
                                    $("#childTags2").html("");
                                    $("#searchResults").html("");
                                }
                    
                            })
                            if ($(this).val() == "") {
                                $("#childTags1").html("");
                                $("#childTags2").html("");
                                $("#searchResults").html("");
                            }
                        });
                },
                error: function(jqXhr, textStatus, errorMessage) {
                    var errorHtm = "Oops!! Something went wrong. Please try again later.";
                    $('.productmenu-comp').html(errorHtm);
                }

            });
        }
        var linkTabsCount = $('#linkTabs > div').length;
        if (linkTabsCount === 2) {
            $('#linkTabs > div').addClass("col-sm-12 col-lg-6");
        } else if (linkTabsCount === 3) {
            $('#linkTabs > div').addClass("col-sm-12 col-lg-4");
        } else if (linkTabsCount === 4) {
            $('#linkTabs > div').addClass("col-sm-12 col-lg-3");
        } else if (linkTabsCount === 5) {
            $('#linkTabs > div').addClass("column5Tab");
        }

    });




    function showtags(ind) {
        $("#tags" + ind).css('display', 'block');
    }
})(jQuery);