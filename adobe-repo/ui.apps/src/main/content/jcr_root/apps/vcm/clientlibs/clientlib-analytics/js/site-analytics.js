$(document).ready(function() {
    let isAuthor = $("#isAuthor").val() || "false";
    var baseUrl = document.location.origin;
    var currentPageName = $("#currentPageName").val() || "";
    var parentPageName = $("#currentPageParentName").val() || "";
    var pageHeirarchy = $("#pageHeirarchy").val() || "";
    var pageType = $("#pageType").val() || "";
    var sectionName = parentPageName + '|' + currentPageName;
    var currentPageTitle = $("#currentPageTitle").val() || "";
    var currentPagePath = window.location.pathname.slice(1).replace(/\//g, '|').replace('.html', '');
    var currentURL = window.location.href.split('?')[0];
    var previousPageName = localStorage.getItem("currentPageName");
    var pagePercent = "";
    var searchTerm = "";
    var searchCount = "";
    var pagePercentage = localStorage.getItem("pagePercentage") || "";
    var siteError = "";
    var searchInput = $('.has-search').find('.form-control');
    var scrolled = false;
    var pageReferrer = localStorage.getItem("currentPageUrl");
    var lastScrollTop = 0;
    var roundScroll = $(this).scrollTop();
    currentPagePath = titleCase(titleCase(currentPagePath, "|"), "-");

    //setting page percentage on scroll where default value is 25%
    if (!scrolled) {
        localStorage.setItem("pagePercentage", "25%");
    }
    $(document).scroll(function(e) {

        scrolled = true;
        var scrollAmount = $(window).scrollTop();
        var documentHeight = $(document).height();
        var windowHeight = $(window).height();
        var scrollPercent = (scrollAmount / (documentHeight - windowHeight)) * 100;
        var roundScroll = Math.round(scrollPercent);

        $(".scroll").css("width", (scrollPercent + '%'));
        if (roundScroll > lastScrollTop) {
            if (roundScroll >= 0 && roundScroll <= 25) {
                localStorage.setItem("pagePercentage", "25%");
            } else if (roundScroll >= 26 && roundScroll <= 50) {
                localStorage.setItem("pagePercentage", "50%");
            } else if (roundScroll >= 51 && roundScroll <= 75) {
                localStorage.setItem("pagePercentage", "75%");
            } else {
                localStorage.setItem("pagePercentage", "100%");
            }
            lastScrollTop = roundScroll;
        }
    });

    //setting up current page name
    if (!currentPagePath.length) {
        currentPagePath = currentPageTitle;
    } else if (sectionName.indexOf("errors") != -1) {
        currentPagePath = "Error Page";
    }

    //setting up current page type
    if (currentURL.indexOf("about-victory") !== -1) {
        pageType = "About Victory Capital";
    } else if (currentURL.indexOf("products") !== -1) {
        pageType = "Products";
    } else if (currentURL.indexOf("investment-franchise") !== -1) {
        pageType = "Investment Franchise";
    } else if (currentURL.indexOf("advice-tools") !== -1) {
        pageType = "Advice and Tools";
    } else {
        pageType = "General";
    }
    //setting the previouse page name to typed/bookmarked when not referred from any other page.
    if (currentPagePath.length) {
        localStorage.setItem("currentPageName", currentPagePath);
    }
    if (currentURL.length) {
        localStorage.setItem("currentPageUrl", currentURL);
    }

    //uppercasing for pagename
    function titleCase(str, delimiter) {
        var splitStr = str.split(delimiter);
        for (var i = 0; i < splitStr.length; i++) {
            // You do not need to check if i is larger than splitStr length, as your for does that for you
            // Assign it back to the array
            splitStr[i] = splitStr[i].charAt(0).toUpperCase() + splitStr[i].substring(1);
        }
        // Directly return the joined string
        return splitStr.join(delimiter);
    }

	//setting up page referred to default value on page reload and on no click for navigation from previous page.
    if (performance.navigation.type == performance.navigation.TYPE_RELOAD || (previousPageName == null && pageReferrer == null)) {
        previousPageName = "";
        pageReferrer = "Typed/Bookmarked";
    }

    //digital data json 
    digitalData = {
        version: "1.0",
        page: {
            pageInfo: {
                PageName: currentPagePath,
                Section: sectionName,
                pageDocumenttitle: currentPageTitle,
                pageURL: currentURL,
                previousPagename: previousPageName,
                pagePercentage: pagePercentage,
                pageRef: pageReferrer,
                pageType: pageType,
                pageHier: pageHeirarchy,
                siteError: siteError,
                modulelog: 'prelogin'
            },
            search: {
                internalSearchresults: searchCount,
                internalSearchterm: searchTerm
            }
        },
        user: {
            userInfo: {
                customerType: "prospect"
            }
        }

    }

    /*search tracking functionality
    if ($('#search-term').length) {
        setSearchResult();
    }
    searchInput.keypress(function() {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            setSearchResult();
        }
    }); 
    $('#txt-search').on('autocompleteselect', function(e, ui) {
        setSearchResult();

    });*/

    function setSearchResult() {
        setTimeout(function() {
            if ($('#search-term').length) {
                searchTerm = $('#search-term').text();
                searchCount = $(".search-results").length;
                $(digitalData).each(function() {
                    this.page.search.internalSearchterm = searchTerm;
                    this.page.search.internalSearchresults = searchCount;
                });
            }
        }, 1000);
    }

    //getting link name on click of the link
    $(".iracomparison .st-green-badge-btn, .subnav, .top_menu_cont a, .menubottom a, .signinBox a, .investor-type .nav-link, .corporate-head a, .piechart .st-arrow-link-blue, .contactVC__container a, .investmentoptions .st-arrow-right, #performance-tabs ul li, .learningresource .navigation-right a, .timeline-navigation-btn a, .footer__text a").on('click', function() {
        var signattr = $(this).attr('aria-label');
        var linkname = $(this).text();
        var href = $(this).attr('href');
        if(href != null && typeof href != "undefined" && href.indexOf(".pdf")>-1){
            digitalData.download = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('download');
        }else{
            if (undefined != signattr && signattr == "Sign In / Register") {
                linkname = signattr;
            }
            digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    });

    $('.etfleavingpopup .accept').click(function(){
        var linkname = "EFT_leaving_" +"_"+ $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
    $(document).on("click",'.institutionalPopup .accept' , function(){
        var linkname = "Institutional Investor" +"_"+ $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
    $(document).on("click",'.institutionalPopup .cancel' , function(){
        var linkname = "Institutional Investor" +"_"+ $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
    $('.vcmleavingpopup .accept').click(function(){
        var linkname = "VCM_leaving_" +"_"+ $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
	setTimeout(function(){
        $('.dt-buttons .dt-button').click(function(){
            var linkname = "Index Daily Highest Value" +"_"+ $(this).text();
            digitalData.download = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('download');
        });
    }, 1500);

    $('.herobanner .navigation-right a, .learningresource .card-image-hdng-small a').click(function(){
		var label = $(this).attr('aria-label');
		var href = $('.herobanner .navigation-right a').attr('href');

        if(href.indexOf(".pdf")>-1){
            var pdfname = href.substring(href.lastIndexOf("/") + 1, href.length).replace('.pdf', '');
            var linkname = "banner_"+ pdfname + '_' +$(this).children().not( ".sr-only" ).text();
            digitalData.download = {
                 name: linkname,
                 pageName: currentPagePath
             }
             _satellite.track('download');
        } else {
            var linkname = "banner_"+$(this).children().not( ".sr-only" ).text();
            digitalData.link = {
                    name: linkname,
                    pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    });
    
    $(document).on("click", ".rawHtml .tableFunds a", function(){
        if ($('.flex.dt-ctnt-kid').length > 0) {
            var row = $(this).parents('.flex.dt-ctnt');
            var rowName = $(row).find('.flex.dt-ctnt-kid a')[0].text;
            var linkname = rowName+'_'+$(this).attr('title');
            console.log(linkname);
            if($(this).attr('href').indexOf(".pdf")>-1){
                 digitalData.download = {
                     name: linkname,
                     pageName: currentPagePath
                 }
                 _satellite.track('download');
            }
        } else {
            var linkname = $(this).text();
            digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    	/*var linkname = $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');   */
    });
    
   /* $(document).on("click", ".rawHtml .tableFunds .flex.dt-ctnt-kid a", function(){
    	var row = $(this).parents('.flex.dt-ctnt');
    	var rowName = $(row).find('.flex.dt-ctnt-kid a')[0].text;
    	var linkname = rowName+'_'+$(this).attr('title');
    	console.log(linkname);
    	if($(this).attr('href').indexOf(".pdf")>-1){
             digitalData.download = {
                 name: linkname,
                 pageName: currentPagePath
             }
             _satellite.track('download');
        } else {
            digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    });*/
    
    $('.herobanner .investortype a').click(function() {
        var linkname = "hero_" + $(this).text();
        digitalData.link = {
            name: linkname,
        }
        _satellite.track('link click');
    });
    $('.news-media-block .news-details a, #loadMoreInTheMedia').click(function() {
        var linkname = "media_" + $(this).text();
        digitalData.link = {
            name: linkname,
        }
        _satellite.track('link click');
    });
    //getting links for audience selector popup
    $('#audience-selector-popup a').each(function() {
        $(this).click(function() {
            var userType = $(this).attr('data-usertype');
            var str = $('.popup').attr('data-target-link').replace('.html', '');
            if (str.indexOf("[{") >= 0) {
                var linkname = str.substring(str.lastIndexOf("/") + 1, str.lastIndexOf("\""));
            } else {
                var linkname = str.substring(str.lastIndexOf("/") + 1, str.length);
            }
            var linkDocumentName = currentPageTitle + "_" + linkname + "_investor_type_" + $(this).text();
            digitalData.link = {
                name: linkDocumentName,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        });

    });
    $('.st-product-box').click(function() {
        var linkname = "RetirementFunds_" + $(this).attr('data-bx-value');
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });


    $('.alltopics .list-group-item a').click(function(){
		var linkname = "blog_alltopic_" + $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

    $('.st-retirement-overlay .st-green-badge-btn').click(function() {
        var overlayText = $('.st-retirement-overlay').attr('data-bx-ol-value').replace('_overlay', '');
        var linkname = "RetirementFunds_" + overlayText + "_" + $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

    $('.timeline--contentBlock #prev.navigationLinksButton a').click(function() {
        var linkname = "previous_timeline_" + $('#timeline .selected').text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
    $('.timeline--contentBlock #next.navigationLinksButton a').click(function() {
        var linkname = "next_timeline_" + $('#timeline .selected').text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

    $('.fund-buttons a').click(function(){
        //var dropdown = $('.productSelectBox .fundDropDown').text();
        var heading = $('.productdetaildatainfo .fund-name').text();
        var linkname = heading + "_" + $(this).text().trim();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
    $('input[type="search"], .searchIcon .call-image, .search-form-group input, .searchfunds').click(function() {
        var linkname = "search";
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

    $('#KJECommandButtons input').click(function() {
        var linkname = $(this).val();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

    $('.st-cmp-social-media li a').click(function() {
        var linkname = 'share_' + $(this).children("img").attr('alt');
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

	$('#blog-search').keypress(function() {
        var searchText = "search_blog_" + $(this).val();
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            digitalData.link = {
            name: searchText,
            pageName: currentPagePath
        }
        _satellite.track('link click');
        }
    });

    $('.cmp-text a').click(function() {
        var href = $(this).attr('href');
        if(href.indexOf('contact-us') > -1){
            var linkname = $.trim($(this).text());
			digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    });

    $('.franchiseBlock .navigation-right a').click(function() {
        var linkname = "home_investment_franchise_learnmore" + "_" + $(this).text();
		digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

	$('.flex.dt-ctnt-kid a').click(function(){
        if($(this).attr('link_type') != undefined && $(this).attr('link_type') == 'fund_link'){
            var linkname = $(this).text();
            digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }else{
            var ticker = $(this).attr('ticker'); 
            var linkname = $('#_'+ticker).text() + "_" + $(this).attr('content_type') + "_pdf";
            console.log(linkname);
            digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    });
  
    $(document).on('click','.productAccordion .card-body.show a', function(e) {
    	var accordionParent = $(this).parents('.productAccordion');
    	var title = $(accordionParent).find('div.card-header a.card-title')[0].text;
		var linkname = currentPageName + "_" + title +"_"+$.trim($(this).text());
		console.log(linkname);
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });
    $('.franchiseBlock .intro-bg-content-wrapper a').click(function(){
        var franchiseName = "";
        franchiseName = $(this).attr('aria-label');
        var str = franchiseName.replace('View ', '');
        var linkname = str + "_" + $(this).text();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
       _satellite.track('link click');
    });

    //getting download name on click of download link
    $('.fundFactSheet, .st-policy-pdf-link a, .pdf-icon').click(function() {

        var downloadname = $(this)[0].childNodes[0].nodeValue;
        var fundTitle = $('.product-details-component .fund-name');
        if(fundTitle.length){
			downloadname = fundTitle.text() +"_"+downloadname;
        }
        digitalData.download = {
            name: downloadname,
            pageName: currentPagePath
        }
        _satellite.track('download');
    });
    $(document).on("change", ".etflist .filter-body input.styled-checkbox", function(){
		var linkname = "products_fa_victoryshares_etfs_list_filter_" + $(this).val();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');

	});
    $(document).on("change",  ".strategylist .filter-body input.styled-checkbox", function(){
		var linkname = "solutions_strategies_list_filter_" + $(this).val();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
	});
    $(document).on("click",  ".fundlist .mutualfunds-container .accordion .filter-list li.filter-text label", function(){
    	 
    	var linkname = "mutual_fund_list_filter_" + $(this).text();
    	
    	if(window.location.pathname.includes('/products-fa/')){
    		linkname = "mutual_fund_filter_" + $(this).text();
		}
        
    	digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
	});
    

    $(window).on('searchAjaxCompleted', function (e) {
        if ($('#search-term').length) {
            searchTerm = $('#search-term').text();
            searchCount = $(".search-results").length;
            $(digitalData).each(function() {
                this.page.search.internalSearchterm = searchTerm;
                this.page.search.internalSearchresults = searchCount;
            });
        }
    });
    
});

$(window).on("load", function() {

    var checkboxVal = "";

   /* if ($('#search-term').length) {
        var searchTerm = $('#search-term').text();
        var searchCount = $(".search-results").length;
        $(digitalData).each(function() {
            this.page.search.internalSearchterm = searchTerm;
            this.page.search.internalSearchresults = searchCount;
        });
    }*/
     $('.st-donut-desc-wrap a').click(function(){
		var linkname = $(this).parents('.st-chart-container .donut_content').find('.donut_header').text() + "_" + $(this).text();
		digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    });

    $("select.fundDropDown").change(function(){
        var fundName = $('.productdetaildatainfo .fund-name').text();
        var linkname = fundName + "_" + $(this).children("option:selected").val();
        digitalData.link = {
            name: linkname,
            pageName: currentPagePath
        }
        _satellite.track('link click');

    });
    setTimeout(function(){
				if($('.learnmorefilter').length > 0 || $('.bloglisting').length > 0 ){
			        if(typeof digitalData !== 'undefined'){
			         $(digitalData).each(function() {
			             if(window.location.href.indexOf("/insights") > -1){
			                 this.page.pageInfo.bloginsightsfilter = "default";
			             }
			             else if(window.location.href.indexOf("/resources") > -1){
							 this.page.pageInfo.blogresourcefilter = "default";
			             }
			             else if(window.location.href.indexOf("/blog") > -1){
							 this.page.pageInfo.blogfilter = "default";
			             }
			             else{
			             	this.page.pageInfo.learningcenter = "default";
			             }
			         });
			
			        $('input[type="checkbox"]').each(function() {
			            if ($(this).is(':checked')) {
			                checkboxVal += $(this).val() + "|";
			            }
			        });
			    
			        checkboxVal = checkboxVal.substr(0, checkboxVal.length - 1);
			        if (checkboxVal.length) {
			            $(digitalData).each(function() {
			                if(window.location.href.indexOf("/insights") > -1){
			                 	this.page.pageInfo.bloginsightsfilter = checkboxVal;
			                }
			                else if(window.location.href.indexOf("/resources") > -1){
								this.page.pageInfo.blogresourcefilter = checkboxVal;
			             	}
			                else if(window.location.href.indexOf("/blog") > -1){
								this.page.pageInfo.blogfilter = checkboxVal;
			             	}else{
			                    this.page.pageInfo.learningcenter = checkboxVal;
			                }
			            });
			        }
			        }
			    }
    }, 2000);

   /*  $('.bloglisting .check_box').click(function(){
    	
    	var checkval = "blog_filterbytopic";
    		
    	$('.bloglisting input[type="checkbox"]').each(function (index,item) {
			let key = $(item).val();
			let value = $(item).is(':checked');
			if(value == true){
				checkval = checkval+'_'+key;
			}
		});
    	console.log(checkval);
        digitalData.link = {
            name: checkval,
            pageName: currentPagePath
        }
        _satellite.track('link click');
    }); */
    $(document).on("click", ".textimageCTA .btn-primary, .card__content a, .textimageCTA a", function(){
        var arialabel = $(this).attr('aria-label');
        var href = $(this).attr('href');
        var link_text  =  $(this).clone().children().remove().end().text();
        if (link_text == "" || link_text == null) {
            link_text = $(this).children().not( ".sr-only" ).text();
        }
        var heading_without_greenborder = $(this).siblings('.blueHdng-without-greenBorder');
        var heading_greenborder = $(this).siblings('.blueHdng-multiLine-greenBorder');
        var heading_blog = $(this).parent().prevAll("h3:first");
        if (undefined != heading_without_greenborder && heading_without_greenborder.text().length) {
            linkname = heading_without_greenborder.text() + '_' + link_text;
        }else if(undefined != heading_greenborder && heading_greenborder.text().length){
            linkname = heading_greenborder.text() + '_' + link_text;
        }
        else if(undefined != heading_blog && heading_blog.text().length){
			linkname = heading_blog.text() + "_" + link_text;
        }else {
            linkname = link_text;
        }
        if(href.indexOf(".pdf")>-1){
            digitalData.download = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('download');
        }
        else{

            digitalData.link = {
                name: linkname,
                pageName: currentPagePath
            }
            _satellite.track('link click');
        }
    });

    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)')
                          .exec(decodeURI(window.location.search));
        return (results !== null) ? results[1] || 0 : false;
	}

});