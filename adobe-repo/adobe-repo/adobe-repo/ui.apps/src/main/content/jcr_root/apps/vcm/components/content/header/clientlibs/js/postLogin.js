 $(document).ready(function() {   

        var userFname = readCookie('userFirstName');
        //var userLname = readCookie('userLastName');
        var unreadMsg = readCookie('unreadMessages');
        var guestUser = readCookie('isMemberRole');

        if(unreadMsg <=0 ){
            $('.badge-info').hide();
        }

        //$('button.menuDropIcon').text(userFname + ' ' + userLname);
        $('button.menuDropIcon').text(decodeURI(userFname));
        $('.badge-info').text(unreadMsg);
        if(sharedJS.nonNullCheck(userFname)){
			$('.preLogin-header').css('display','none');
            $('.postLogin-header').css('display','block');
        }

        if(guestUser === "false"){
            setTimeout(function() {
                $('.non-guest-user').css('display','none');
            }, 1000);
            let guesturl = $(".home-url").data("guesturl");
            $(".home-url").attr("href",guesturl);
            $(".postlogin-logo").attr("href",guesturl);
            let guestinfourl = $(".user-info-icon").data("guesturl");
            $(".user-info-icon").attr("href",guestinfourl);
        }
        if(guestUser === "true"){
            let memberurl = $(".postlogin-logo").data("memberurl");
            $(".postlogin-logo").attr("href",memberurl);
        }
        $('.godZyJ').hide();
         $('.pl-dropdown-wrap').on('click', function(){
            //$(this).parents('.pl-dropdown-wrap').find(ul).toggle();
            $('.post-dropMenu').toggle();
            $('.mask').hide();
            $('.nav-item a.nav-link').removeClass('active');
            $('.nav-item div.overlay').hide();
         })
         $(document).click(function(e){

                // Check if click was triggered on or within #menu_content
                if( $(e.target).closest(".pl-dropdown-wrap").length > 0 ) {
                   
                   return ;
                }else{
                      $('.godZyJ').hide();
                   }
          });


$(".target-link").click(function() {
     $(".mask").hide();
	window.location.href = $(this).attr('href');
})

 function readCookie(name) {
                var nameEQ = name + "=";
                var ca = document.cookie.split(';');
                for(var i=0;i < ca.length;i++) {
                    var c = ca[i];
                    while (c.charAt(0)==' ') c = c.substring(1,c.length);
                    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
                }
                return null;
            }
 });