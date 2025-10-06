//Setting cookie and localstorage on interaction with header nav or audience selector 
$(document).on('click', '.investment-popup a, .corporate-head.hide-desktop a, .investor-selector a.text-rightArrow, .investor-selector a.text-rightArrow', function (e) {

	e.preventDefault();

	let userType = $(this).attr('data-userType');

	sharedJS.updatePreviousProfile();

	if(userType == USERTYPES.INSTITUTIONAL_INVESTOR){

		sharedJS.setCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN, 'false');

	}

	sharedJS.setCookie(GLOBALCOOKIEKEYCONFIG.ACCEPT_COOKIE, "", -1);

	sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, $(this).attr('data-userType'));

	sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, $(this).attr('data-userType'));

	if(userType == '' || userType == "undefined" || userType == null){
        window.location.href = HOMEPAGECONFIG["ALL"]; //$(this).attr('href');
    }else{
        window.location.href = HOMEPAGECONFIG[$(this).attr('data-userType')]; //$(this).attr('href');
    }
});

$(window).on('popupAjaxCompleted', function (e) {

    //Header Content Personalization
    let isCorpHome = $("#isCorpHome").val() || "false";

    let isAuthor = $("#isAuthor").val() || "false";

    let userType = sharedJS.getCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE);

    let subDomainUserTypeValue = sharedJS.getCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE);

    let contactUsAttr = "data-" + subDomainUserTypeValue + "-url"

    let contactUs_url = $(".header_contact_us").attr(contactUsAttr);

    $(".header_contact_us").attr("href", contactUs_url);

    //Check non null else consider it as Member
    if (!sharedJS.nonNullCheck(subDomainUserTypeValue)) {

    	subDomainUserTypeValue = COOKIEKEYCONFIG.DEFAULT;
    }

    //Execute any audience selector logic only when crop home page
    if (isCorpHome == "true" && isAuthor == "false") {

        //Logic to handle redirection if cookie exists & user lands on crop home page

    	if (userType != null && userType != "" && typeof userType != "undefined") {

            window.location.href = HOMEPAGECONFIG[userType];

        }

    }

    //Set Header menu item based on cookie item
    if (subDomainUserTypeValue != null && subDomainUserTypeValue != "" && typeof subDomainUserTypeValue != "undefined") {

    	swapInverstorLinks(subDomainUserTypeValue);

    	swapInverstorDataMobile(subDomainUserTypeValue);
    }
    else {

    	swapInverstorLinks("usaa_member");

    	swapInverstorDataMobile(subDomainUserTypeValue)
    }

    //Logic for updating Navigation Links
    if (isCorpHome == "true") {

    	$(".header .container").addClass("corporate-header");
    }
    else if (subDomainUserTypeValue == null || subDomainUserTypeValue == "" || typeof subDomainUserTypeValue == "undefined" || subDomainUserTypeValue == "usaa_member") {

    	$(".header .container").addClass("member");

    	updateNavigationLinks(subDomainUserTypeValue);
    }
    else if (subDomainUserTypeValue == "institutional_investor") {

    	$(".header .container").addClass("investor");

    	updateNavigationLinks(subDomainUserTypeValue);
    }
    else if (subDomainUserTypeValue == "financial_professional") {

    	$(".header .container").addClass("financial");

    	updateNavigationLinks(subDomainUserTypeValue);
    }
    else {

    	$(".header .container").addClass("member");

    	updateNavigationLinks(subDomainUserTypeValue);
    }

    //Personalize home page
    $('header .logo a, .footer .footer__logoContainer  a').each(function (index, item) {

    	if (sharedJS.nonNullCheck(HOMEPAGECONFIG[userType])) {

    		$(this).attr("href", HOMEPAGECONFIG[userType]);
        }

    });

});

function swapInverstorLinks(subDomainUserTypeValue) {

	let anchorElement = $("ul.showinDesktop.toplinks .linksDropdown a.drop-icon");

	$("header .investment-popup ul li").css('display', 'block');

	let userTypeElement = $(".investment-popup").find(("a[data-usertype='" + subDomainUserTypeValue + "']"));

	if (userTypeElement.length > 0) {

		$(userTypeElement).parent().css('display', 'none');

		let href = sharedJS.nonNullCheck($(userTypeElement).attr("href")) ? $(userTypeElement).attr("href") : "#";

		let userType = $(userTypeElement).attr("data-userType");

		let ariaLabel = $(userTypeElement).attr("aria-label");

		let userTypeElementHtml = $(userTypeElement).html();

        let hostNameUrl = window.location.hostname;

		if(userType == 'usaa_member' &&( hostNameUrl == 'stage.investor.vcm.com'
                || hostNameUrl == 'qa.investor.vcm.com'
                || hostNameUrl == 'dev.investor.vcm.com'
                || hostNameUrl == 'investor.vcm.com'
                || hostNameUrl == 'qa.mysecure.vcm.com'
                || hostNameUrl == 'dev.mysecure.vcm.com'
                || hostNameUrl == 'mysecure.vcm.com')){
		    $('.home-logo').attr("src", $('#insLogoPath').val());
		   /* $('.footerLogo').attr("src", $('#insFooterLogo').val());
		    $('.footerLogoMob').attr("src", $('#insFooterMobLogoImg').val());*/
		}else{
		    $('.home-logo').attr("src", $('#logoImagePath').val())
		}

		$(anchorElement).attr("data-href", href)
        
		$(anchorElement).attr("data-userType", userType)
        
		$(anchorElement).attr("aria-label", ariaLabel)
        
		$(anchorElement).html(userTypeElementHtml);
    }
	
}

function swapInverstorDataMobile(subDomainUserTypeValue) {
    
	let anchorElement = $("header .investor-selector a.nav-main-head");
    
	let userTypeElement = $("header .investor-selector .overlay-content").find(("a[data-usertype='" + subDomainUserTypeValue + "']"));
    
	$("header li.nav-item.investor-type.investor-selector.hide-desktop li").css('display', 'block');
    
	if (userTypeElement.length > 0) {
    
		$(userTypeElement).parent().css('display', 'none');
        
		$('header .nav-main-head-text').html($(userTypeElement).html());
        
		$(anchorElement).attr("data-href", $(userTypeElement).attr('href'))
        
		$(anchorElement).attr("data-userType", $(userTypeElement).attr('data-userType'))
        
		$(anchorElement).attr("aria-label", $(userTypeElement).attr("aria-label"));
        
		$(anchorElement).html($(userTypeElement).html());
    }
}



function updateNavigationLinks(subDomainUserTypeValue) {
    
	let listItems = $(".header .container:not(.corporate-header) .mobileCls .navbar-nav li.nav-item:not(.hide-desktop)");
    
	for (let li of listItems) {
    
		let navsAttr = $(li).data("show-this-menu-for");
        
		if (sharedJS.nonNullCheck(navsAttr) && navsAttr.includes(subDomainUserTypeValue) || navsAttr == "ALL") {
        
			$(li).show();
        }
    }
}