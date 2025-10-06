$(document).ready(function() {

    var userFname = readCookie('userFirstName');
    var guestUser = readCookie('isMemberRole');
    if(sharedJS.nonNullCheck(userFname)){

        $(".breadcrumb-item").each(function (index, item) {
            let guesturl = $(item).children('a').data("guesturl");
            let memberurl = $(item).children('a').data("memberurl");
            if(guestUser === "false"){
            $(item).children('a').attr("href",guesturl);
            } else if (guestUser === "true") {
                $(item).children('a').attr("href",memberurl);
            }
        })

    }

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