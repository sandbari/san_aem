$(window).on('popupAjaxCompleted', function (e) {
	
	var isAuthor = $("#isAuthor").val() || "false";
	
	var cookieBannerConfig={
			cookie_compliance_id : "#cookie-compliance",
			popupIdDesktop:"cookieBanner",
			popupIdMobile:"cookieBannerMobile"
	}
    var cookieBannerHtml="<!-- cookie banner div starts --->" + 
	"	<div class=\"cookie_wrapper\">" + 
	"	  <div class=\"cookie_content_wrapper\" id=\"cookie-compliance\">" + 
	"		<div class=\"row content-wrapper\">" +
	"			<div class=\"col-10 col-md-11 col-lg-11 cookie_txt\">" + 
	"			<div class=\"d-none d-md-block\">" + 
	"		    </div>" + 
	"			<div class=\"d-block d-md-none mobile_cookie\">" + 
	"		    </div>" + 
	"		    </div>" + 
	"			<div class=\"col-2 col-md-1 col-lg-1 cookie_btn_wrapper\">" +
	"			<a href=\"javascript:void(0);\" aria-label=\"close\" role=\"button\">" +
	"				<img src=\"../../../../../etc.clientlibs/vcm/clientlibs/clientlib-site/resources/cross-icon.png\" class=\"cookie_close\" alt=\"close icon\">" + 
	"			</a>" +
	"			</div>" +
	"		</div>" + 
	"	  </div>" +
	"	</div>" + 
	"	<!-- cookie banner div ends -->"
	
	if(isAuthor == "false"){
		var accept_cookies = sharedJS.getCookie(GLOBALCOOKIEKEYCONFIG.ACCEPT_COOKIE);
		if (accept_cookies == "true") {	
			if($(cookieBannerConfig.cookie_compliance_id).length > 0)
				$('body').remove(cookieBannerConfig.cookie_compliance_id);
		}
		else{
			if(sharedJS.nonNullCheck(sharedJS.globalPopupContent)){
				$.each(sharedJS.globalPopupContent, function(index, item) {
	                if (item.popupId == cookieBannerConfig.popupIdDesktop) {
	                	if($(cookieBannerConfig.cookie_compliance_id).length <= 0)
	                	{
	                		$('body').append($(cookieBannerHtml));
	                	}
	                	$('.cookie_wrapper .cookie_txt .d-none.d-md-block').html(item.popuptext);
	                }
	                if (item.popupId == cookieBannerConfig.popupIdMobile) {
	                	if($(cookieBannerConfig.cookie_compliance_id).length <= 0)
	                	{
	                		$('body').append($(cookieBannerHtml));
	                	}
	                	$('.cookie_wrapper .cookie_txt .d-block.d-md-none.mobile_cookie').html(item.popuptext);
	                }
	            });
			}
		}    
		$(".cookie_close").click(function() {
				$("#cookie-compliance").hide();
				sharedJS.setCookie(GLOBALCOOKIEKEYCONFIG.ACCEPT_COOKIE,"true");
		});
	}
});