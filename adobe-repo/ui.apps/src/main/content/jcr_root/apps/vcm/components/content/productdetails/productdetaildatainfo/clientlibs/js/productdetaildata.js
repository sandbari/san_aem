var selectedProductOverview = loadClass;
var SelectOpen = false;
var loadFundsDetails = function(data){
    if ($("#fund-category").val() == "MF") {
        var apiClasses = [];
        data.forEach(element => {
            apiClasses.push(element.share_class);
        });
        if (!(apiClasses.indexOf(selectedProductOverview) > -1)) {
            selectedProductOverview = apiClasses[0];
        }
    }
	loadFundsDetail_class(data);
	loadFundsDetails_body(data);
}

var loadFundsDetail_class = function(data){
	if (data.length) {
	data.forEach(element => {
        let slectOption = '<option> Class ' + element.share_class +" "+ element.ticker +'</option>';
        $('.productSelectBox select').append(slectOption);
    })
    var liArr = $('.productSelectBox select').find('option')
    var arr = new Array()
    for (var i = 0; i < liArr.length; i++) {
        arr.push(liArr[i].textContent)
    }
    arr.sort()

    $('.productSelectBox select').html('');
    arr.forEach(function(content, index) {
        let slectOption = '<option aria-hidden="true">'+ content +'</option>';
        $('.productSelectBox select').append(slectOption);
    })

    var firstOpt =  $('select.fundDropDown option:first').val();
    $('.productSelectBox ').append('<span class="sr-only firstOption" >'+ firstOpt +'</span>');

    $("select option:contains(Class "+selectedProductOverview+" )").prop('selected',true);

    loadFundsDetails_body(ajaxData_productoverview);
	}
	else {
		loadFundsDetails_body(ajaxData_productoverview);
    }
}
var loadFundsDetails_body = function(data){
    var CheckUser = sharedJS.getCookie("subdomain_user_entity_type");
    if(CheckUser !== "financial_professional"){
        //$('.fundDropDown').attr( "disabled", "true" ).attr('tabindex', '-1');
        /*$('.fundDropDown').attr({
            "disabled": "true",
            'tabindex': '-1',
            'role': 'presentation',
            'aria-hidden': 'true'
        });*/
        $('.fundDropDown').removeAttr("aria-label");
        $('.firstOption').remove();
        $('.productSelectBox').show();
        //$('.productSelectBox').parent().parent().addClass("memberUsers");
        $('.productSelectBox').addClass('ax-tabfocus');
        $('.mem-factsheet').show();
        $('.advisor-factsheet').hide();
        $('.mem-prospectus').show();
        $('.advisor-prospectus').hide();
        if($('.mem-commentary').length > 0) {
            $('.mem-commentary').show();
            $('.advisor-commentary').hide();
        }
        $('.fund-buttons').show();
        $('.fundDropDown option').removeAttr('aria-hidden');
    }else{
        $('.firstOption').remove();
    	$('.productSelectBox').show();
    	$('.productSelectBox').addClass('ax-tabfocus');
        $('.mem-factsheet').hide();
        $('.advisor-factsheet').show();
        if($('.advisor-prospectus').length > 0) {
            $('.mem-prospectus').hide();
            $('.advisor-prospectus').show();
        }
        $('.mem-commentary').hide();
        $('.advisor-commentary').show();
        $('.fund-buttons').hide();
        $('.fundDropDown option').removeAttr('aria-hidden');
    }

    $(".rating-stars-container .sr-only").remove();
	var overviewData =  $(".rating-count").remove();
	if (data.length) {
        data.map(item => {
            if (item.share_class == selectedProductOverview) {
                overviewData = item;
            }
        })
    } else {
        overviewData = data;
    }
	 let ratingvalue;
     let totalRatings;
     let totalRatingsTemplate;
	 let apiDataTitle = $('#api-data-title').val();
    if(apiDataTitle.length && apiDataTitle=="true"){
        //console.log(apiDataTitle);
        $('.breadcrumb-item.active a').text(overviewData.entity_long_name);
        $(".fund-name").html(overviewData.entity_long_name);
    }

     $(".fund-ticker").html("").append(overviewData.ticker);
     if ($("#fund-category").val() == 'ETF') {
         $(".morning-star-asof-value").html("").append("As of " + overviewData.morning_star_as_of_date);
     }else{
        $(".nav-asof-value").html("").append(overviewData.as_of);
     }
     $(".nav-change-lable").html("").prepend(overviewData.latest_nav ? parseFloat(overviewData.latest_nav).toFixed(2) : 'N/A');
     $(".nav-range-value").html("").append(overviewData.nav_52_week_range);
     $(".category-value").html("").append(overviewData.category);

     if ($("#fund-category").val() == "MF") {
        if(overviewData.share_class == "Member" || overviewData.share_class == "Fund"){
            $(".mf-buy-btn").show();
            let myUrlWithParams = new URL($('.fund-buttons a').attr('href'));
            myUrlWithParams.searchParams.set("fundid", overviewData.ticker);
            myUrlWithParams.searchParams.set("flow", "prelogin");
            $('.fund-buttons a').attr("href", myUrlWithParams);
        }else{
            $(".mf-buy-btn").hide();
        }
     }else if ($("#fund-category").val() == "ETF") {
         let myUrlWithParams = new URL($('.fund-buttons a').attr('href'));
         myUrlWithParams.searchParams.set("fundid", overviewData.ticker);
         myUrlWithParams.searchParams.set("flow", "prelogin");
         $('.fund-buttons a').attr("href", myUrlWithParams);
     }
     
     ratingvalue = overviewData.rating;
     totalRatings = overviewData.morning_stargroup_overall;
     if (overviewData.nav_change_percentage != null) {
            if (overviewData.nav_change_percentage > 0) {
            	$(".nav-change-value").html("").append(parseFloat(overviewData.nav_change_percentage).toFixed(2) + "%");
                $(".nav-change-value").addClass("upArrow").prepend('<i class="fa fa-angle-up" aria-hidden="true"></i>');
                $(".upArrow").prepend('<span class="sr-only">increase by</span>');
            } else {
            	var negValue = (overviewData.nav_change_percentage * (-100)) / 100;
                $(".nav-change-value").html("").append(parseFloat(negValue).toFixed(2) + "%");
                $(".nav-change-value").addClass("downArrow").prepend('<i class="fa fa-angle-down " aria-hidden="true"></i>');
                $(".downArrow").prepend('<span class="sr-only">decrease by</span>');
            }
        } else {
            $(".nav-change-value").html("").append("0.00%");
        }
     totalRatingsTemplate = `<span class="sr-only">${ratingvalue} out of 5 stars</span><span class="rating-count">(out of ${totalRatings} funds)</span>`;
     let stariconTemplate = '';
     for (let i = 1; i <= 5; i++) {
         if (i <= ratingvalue) {
             stariconTemplate = stariconTemplate + '<i class="fa fa-star fa-lg active" aria-hidden="true"></i>'
         } else {
             stariconTemplate = stariconTemplate + '<i class="fa fa-star fa-lg" aria-hidden="true"></i>'
         }
     }
     if (ratingvalue == null || ratingvalue === " " || ratingvalue == 0) {
         //$(".rating-stars").html("").append("N/A");
         //$(".rating-stars-container").append("");
         //$(".fund-data-container .dotted-text a").css("cursor", "default");
         //$(".fund-data-container .dotted-text").addClass("noData");
         //$(".fund-data-container .dotted-text a").remove();
         //$(".fund-data-container .dotted-text").text("Morningstar Rating®:");
         $(".rating-stars-container").hide();
         $(".fund-data-container .dotted-text").hide();
         $(".fund-data-container .populinkaxreader").hide();
     } else {
         $(".rating-stars-container").show();
         $(".fund-data-container .dotted-text").show();
         $(".fund-data-container .populinkaxreader").show();
         $(".rating-stars").html("").append(stariconTemplate);
         $(".rating-stars-container").append(totalRatingsTemplate);
         $(".dotted-text a").css("cursor", "pointer")
         $(".dotted-text").removeClass("noData");
     }
     let navrange = $('.nav-range-value').text();
     let axnavrange = navrange.replace("-", " to ");
     $('.ax-nav-range-value').text(axnavrange);

     overviewData['Fund-Name'] = $("#currentPageTitle").val();
     overviewData['morning_star_as_of_date_formatted'] = formateDateString(overviewData['morning_star_as_of_date']);
     var overviewJsonString= JSON.stringify(overviewData);
 	 $('div.fund-data-rating #morningstar-popup').attr("data-dynamicpopup",overviewJsonString.replace(/^\[([\s\S]*)]$/,'$1'));
     
	
}

function formateDateString(date){
	
	let formattedDate = date;
	
	if(date != null && typeof date!= "undefined" && date != ""){
		
		 formattedDate = new Date(date).toLocaleDateString({},{month:"long", day:"2-digit", year:"numeric"})
	}
	else{
		formattedDate=null;
	}
	
	return formattedDate;
}

function isOpen() {
    if (SelectOpen)
        $(".productSelectBox").addClass("menuExpanded");
    else
        $(".productSelectBox").removeClass("menuExpanded");
}

$(".productSelectBox .fundDropDown").on("click", function() {
    opeSelectOpenn = !SelectOpen;
    isOpen();
});

function onChangeProductOverViewClass(selectClass){
	selectedProductOverview = selectClass;
	if(($("#fund-category").val() != "ST") && $.trim($("div.productdetaildatainfo").html()).length > 0) {
	loadFundsDetails_body(ajaxData_productoverview);
	}
}
selectboxChange(onChangeProductOverViewClass);

var loadFundsDetails_head = function(data){
	$(".nav-asof").prepend(data.navasof);
    $(".nav-change").prepend(data.navchange);
    let wkchange = data.wkchange;
    let wkrange = `<span aria-hidden="true">${wkchange}</span>`;
    $(".nav-range").prepend(wkrange);
    $(".category").prepend(data.category);
}

var beforeServiceLoadFuc = function () {
    $('.dynamic-loader-productoverview').css("display", "block");
    $('.error-message-productoverview').css("display", "none");
}

var serviceErrorHandlingFuc = function () {
	$('.productSelectBox').hide();
	$(".fund-data-container .dotted-text a").css("cursor", "default");
    $(".fund-data-container .dotted-text").addClass("noData");
    $(".fund-data-container .dotted-text a").remove();
    $(".fund-data-container .dotted-text").text("Morningstar Rating®: N/A");
    $('.product-details-component .fund-data').show();
    $('.nav-change').text('N/A');
    $('.nav-asof-value').text('N/A');
    $('.nav-range-value').text('N/A');
    $('.category-value').text('N/A');
    $('.fund-ticker').hide();
    $(".error-message-productoverview p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-productoverview').css("display", "none");
    $('.error-message-productoverview').css("display", "block");
}

    if($(".theme-color-code").length){
        var themeColor = $(".theme-color-code").val();
        //console.log(themeColor);
        $('.left-bar, .right-bar').css({ 'background-color': themeColor });
        $('.fund-data-container .btn-triangle-rt').css({ 'border-top-color': themeColor }); 

    }

$(document).ready(function() {
    var isFundDetail = $('.productdetaildatainfo').find('.fund-list');
    if(isFundDetail.length){
        $('.breadcrumb').css('background-color','#f7f7f7');
         $('.breadcrumb').css('margin','0');
         $('.breadcrumb').css('padding','20px 0');
    }

});

$(document).on('click', 'body *', function(e) {
    var target = $(e.target);
    var targetClass = target.attr("class");
    if (targetClass != "productSelectBox" && targetClass != "fundDropDown") {
        if ($(".productSelectBox .fundDropDown").length && SelectOpen === true) {
            $(".productSelectBox").removeClass("menuExpanded");
            SelectOpen = !SelectOpen;
        }
    }

});
