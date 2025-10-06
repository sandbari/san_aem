$(document).ready(function(){
    
	$(document).on("click", ".fb-share-social", function(e) {
	// facebook share
		e.preventDefault();
		var base_url = "https://www.facebook.com/sharer/sharer.php?u=";
		var url = base_url + encodeURIComponent(window.location.href) + "&t=" + document.title; 
    	$(this).attr('target', '_blank');
		window.open(url, "","menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=350,width=500");
		return false;
	});
	
	//twitter share
	$(document).on("click", ".twitter-share-social", function(e) {
		e.preventDefault();
		var base_url = "https://twitter.com/intent/tweet?url=";
		var url = base_url + encodeURIComponent(window.location.href) + ";text="+"Check out this blog post from Victory Capital Management.";
    	$(this).attr('target', '_blank');
		window.open(url, "","menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=350,width=500");
		return false;
	});
	
	//linkedIn share
	$(document).on("click", ".linkedIn-share-social", function(e) {
		e.preventDefault();
		var base_url = "https://www.linkedin.com/shareArticle?mini=true&url=";
		var url = base_url + encodeURIComponent(window.location.href) + ";text="+"Check out this blog post from Victory Capital Management.";
    	$(this).attr('target', '_blank');
		window.open(url, "","menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=350,width=500");
		return false;
	});
	
	//mail share
	$(document).on("click", ".mail-share-social", function(e) {
		e.preventDefault();
		var url = "mailto:?subject=" + encodeURIComponent("Check out this blog post from www.vcm.com") + "&body=" + "Click " + encodeURIComponent(window.location.href);
		window.open(url);
		return false;
	});
	
});
