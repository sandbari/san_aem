$(window).on('popupAjaxCompleted', function (e) {

    let isAuthor = $("#isAuthor").val() || "false";

    let isCorpHome = $("#isCorpHome").val() || "false";

    let iiUserParam = "institutional";

    let hostName = window.location.hostname;

    let currentDomain = "vcm";
    
    let USER_ENTITY_TYPE_COOKIE_VALUE = sharedJS.getCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE) ? sharedJS.getCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE) : "";

    let usertypenavigationflag = sharedJS.getCookie(COOKIEKEYCONFIG.USERTYPENAVIGATION);

    $.each(window.DOMAINCONFIG, function (k, v) {
        if (hostName.includes(k)) {
            currentDomain = k;
            console.log(currentDomain);
        }
    });

    let incomeDomainUserType = sharedJS.nonNullCheck(sharedJS.getParameterByName('user')) ? sharedJS.getParameterByName('user') : currentDomain;

    let subDomainUserTypeValue = sharedJS.getCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE);

    if (isCorpHome == "false" && isAuthor == "false") {

        if (sharedJS.nonNullCheck(incomeDomainUserType) && incomeDomainUserType != iiUserParam && sharedJS.nonNullCheck(DOMAINCONFIG[incomeDomainUserType])) {
         
        	console.log("usertype flag"+usertypenavigationflag);
        	
        	if(usertypenavigationflag == "false" &&  subDomainUserTypeValue != DOMAINCONFIG[incomeDomainUserType]){
        		
        		sharedJS.updatePreviousProfile();
        		
        		console.log("usertype flag"+usertypenavigationflag);
        	}
        	
        	subDomainUserTypeValue = DOMAINCONFIG[incomeDomainUserType];
            
        	sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, DOMAINCONFIG[incomeDomainUserType]);
            
        	if (!sharedJS.nonNullCheck(USER_ENTITY_TYPE_COOKIE_VALUE)) {
            
        		sharedJS.setCookie(GLOBALCOOKIEKEYCONFIG.ACCEPT_COOKIE, "", -1);
                
        		sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, DOMAINCONFIG[incomeDomainUserType]);
            }
        } 
        
        else if (incomeDomainUserType == iiUserParam && USER_ENTITY_TYPE_COOKIE_VALUE == USERTYPES.INSTITUTIONAL_INVESTOR) {
           
        	sharedJS.setCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN, 'true');
    		
        	if(usertypenavigationflag == "false" &&  subDomainUserTypeValue != DOMAINCONFIG[incomeDomainUserType]){
        		
        		sharedJS.updatePreviousProfile();
        		
        		console.log("usertype flag"+usertypenavigationflag);
        	}
        	
        	subDomainUserTypeValue = DOMAINCONFIG[incomeDomainUserType];
            
        	sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, DOMAINCONFIG[incomeDomainUserType]);
        }
        
        else if (sharedJS.nonNullCheck(incomeDomainUserType) && incomeDomainUserType == iiUserParam) {
            
        	let mappedDomainCookieKey = DOMAINCONFIG[incomeDomainUserType];
            
        	if (mappedDomainCookieKey != USER_ENTITY_TYPE_COOKIE_VALUE) {
            

            	if(usertypenavigationflag == "false" && subDomainUserTypeValue != DOMAINCONFIG[incomeDomainUserType]){
            		
            		sharedJS.updatePreviousProfile();
            		
            		console.log("usertype flag"+usertypenavigationflag);
            	}
        		
        		sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, USERTYPES.INSTITUTIONAL_INVESTOR);
                
        		sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE, USERTYPES.INSTITUTIONAL_INVESTOR);
               
        		popupVariations.showGlobalPopup(null , "#", "_self", "institutionalPopup", HOMEPAGECONFIG[USER_ENTITY_TYPE_COOKIE_VALUE], "_self");
            }
        }

        //Show II Popup in case of ii navigation
        if ((USER_ENTITY_TYPE_COOKIE_VALUE == USERTYPES.INSTITUTIONAL_INVESTOR) &&
            (!sharedJS.nonNullCheck(ISIIPOPUPSHOWN) || ISIIPOPUPSHOWN == "false")) {
            
        	popupVariations.showGlobalPopup(null , "#", "_self", "institutionalPopup", HOMEPAGECONFIG[USER_ENTITY_TYPE_COOKIE_VALUE], "_self");
        }

        //Check non null else consider it as Member
        if (!sharedJS.nonNullCheck(subDomainUserTypeValue)) {
            
        	subDomainUserTypeValue = COOKIEKEYCONFIG.DEFAULT;
        }
        
        $("[data-audiencetype]").each(function (index, item) {
            
        	let audienceType = $(item).attr('data-audiencetype');
            
        	if (sharedJS.nonNullCheck(audienceType) && audienceType != COOKIEKEYCONFIG.ALL) {
            
        		let audienceTypeArray = audienceType.split(",");
                
        		if (!audienceTypeArray.includes(subDomainUserTypeValue)) {
                
        			$(item).css("display", "none");
                    
        			console.log("Filtered Items are :");
                    
        			console.log($(item))
                }
            }
        });
    }
});
$(window).on('load', function () {
	
	sharedJS.setCookie(COOKIEKEYCONFIG.USERTYPENAVIGATION, false);

});