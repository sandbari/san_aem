sharedJS = {
	 
	globalPopupContent:"",
	
	openSearchNav : function() {
		document.getElementById("overlayNav").style.display = "block";
		$('#nav-txt-search').focus();
		SearchJS.doOverlaySuggestion();
	},

	closeSearchNav : function() {
		document.getElementById("overlayNav").style.display = "none";
		$('.searchIcon').focus();
	},
	
   setCookie: function(cname, cvalue, exdays = 90) {
        var d = new Date();
        var baseDomain = '.vcm.com';
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";domain="+baseDomain+";"+expires+";path=/;secure";
    },
    
   getCookie: function(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    },
    
    nonNullCheck : function(item){
   	 if(typeof item != "undefined" && item != null && item != ""){
   		 return true;
   	 }
   	 else{
   		 return false;
   	 }
    },
  	
    isExternalLink: function(link_element){ 	
  		    if( typeof $(link_element)[0] != 'undefined' && typeof $(link_element)[0].host != 'undefined' && $(link_element)[0].host !== window.location.host){
  		    	return true;
  		    }
  		    else{
  		    	return false;
  		    }
  	 },
   
  	getParameterByName: function(name, url) {
 		if (!url) {
 			url = window.location.href;
 		}
 		name = name.replace(/[\[\]]/g, '\\$&');
 		var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
 			results = regex.exec(url);
 		if (!results) return null;
 		if (!results[2]) return '';
 		return decodeURIComponent(results[2].replace(/\+/g, ' '));
 	},
 	
 	updatePreviousProfile : function(){
 		let previousProfile ="";
 		let cookieValue = sharedJS.getCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE);
 		let subDomainUserTypeValue = sharedJS.getCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE);
 		if(sharedJS.nonNullCheck(cookieValue)){
 			previousProfile=cookieValue;
 		}
 		if(sharedJS.nonNullCheck(cookieValue)){
 			previousProfile = previousProfile +','+subDomainUserTypeValue;
 		}
 	    sharedJS.setCookie(COOKIEKEYCONFIG.PREVIOUSPROFILE,previousProfile);
 	    sharedJS.setCookie(COOKIEKEYCONFIG.USERTYPENAVIGATION, true);
 	},
 	
 	getpreviouseProfile : function(){
 		//Set previous profile
 		let previousProfileObj={};
 	    let previousProfile= sharedJS.getCookie(COOKIEKEYCONFIG.PREVIOUSPROFILE);
 	    let previousCookie=null;
 	    let previousSubDomainCookie=null;
 	    
 		 if(sharedJS.nonNullCheck(previousProfile)){
 			 let profileArr=previousProfile.split(",");
 			 if(profileArr.length == 2){
 				previousProfileObj.previousCookie = profileArr[0];
 				previousProfileObj.previousSubDomainCookie =  profileArr[1]
 			 }
 			 else{
 				previousProfileObj.previousCookie = profileArr[0];
 				previousProfileObj.previousSubDomainCookie = profileArr[0];
 			 }
 		 }
 		return previousProfileObj;
 	},
 	
 	setpreviouseProfile : function(){
 		 let previouseProfile=sharedJS.getpreviouseProfile();
		 if(!$.isEmptyObject(previouseProfile)){
			sharedJS.setCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE,previouseProfile.previousSubDomainCookie);
	 		sharedJS.setCookie(COOKIEKEYCONFIG.USER_ENTITY_TYPE, previouseProfile.previousCookie); 
		 }
 	}
}