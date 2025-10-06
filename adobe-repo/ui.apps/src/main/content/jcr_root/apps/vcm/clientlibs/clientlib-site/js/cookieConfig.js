var getDomainConfig = function(){
	
	let userdomainmap = {};
	
	if(sharedJS.nonNullCheck(HOMEPAGECONFIG)){
		
		  $.each(window.HOMEPAGECONFIG, function (index, value) {
			  
			  if(sharedJS.nonNullCheck(value) && sharedJS.nonNullCheck(index)){
				  let COMString= ".com";
				let startIndex = value.lastIndexOf(COMString);	
				 if(startIndex > -1) {
					  
					  let endIndex = startIndex + COMString.length;
					  userdomainmap[index]=value.substring(0,endIndex);
				  }
				  else{
					  userdomainmap[index]=value;
				  }
			  }
			  
		  });
	}
	return userdomainmap;
}

window.COOKIEKEYCONFIG = {
    ALL: "ALL",
    USER_ENTITY_TYPE: "user_entity_type",
    SUBDOMAIN_USER_ENTITY_TYPE: "subdomain_user_entity_type",
    DEFAULT: "usaa_member",
    ISIIPOPUPSHOWN: "isIIPopupShown",
    PREVIOUSPROFILE: "previousProfile",
    USERTYPENAVIGATION: "usertypenavigation",
    PREVIOUSURL:"prevUrl"
}

/*window.USERTYPES = {
    USSA_MEMBER: "usaa_member",
    FINANCIAL_PROFESSIONAL: "financial_professional",
    INSTITUTIONAL_INVESTOR: "institutional_investor"
} */


window.GLOBALCOOKIEKEYCONFIG = {
    ACCEPT_COOKIE: "accept_cookies",
    PREVIOUSURL : "prevUrl",
    POPUP_CONTENT : "popup_content"
}
window.USERTYPES = sharedJS.nonNullCheck($("#userTypeCookieConfig").val()) ? JSON.parse($("#userTypeCookieConfig").val()) : "";

window.DOMAINCONFIG = sharedJS.nonNullCheck($("#domainKeyConfig").val()) ? JSON.parse($("#domainKeyConfig").val()) : "";

window.ISIIPOPUPSHOWN = sharedJS.nonNullCheck(sharedJS.getCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN)) ? sharedJS.getCookie(COOKIEKEYCONFIG.ISIIPOPUPSHOWN) : "false"; 

window.HOMEPAGECONFIG = sharedJS.nonNullCheck($("#homePageConfig").val()) ? JSON.parse($("#homePageConfig").val()) : "";

window.USERDOMAINMAP = getDomainConfig();