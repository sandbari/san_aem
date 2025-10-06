$(document).ready(function() {
    var CheckUser = sharedJS.getCookie("subdomain_user_entity_type");
    if(CheckUser == "financial_professional"){
		$('.usaa_member').hide();
        $('.financial_professional').show();
    }else{
        $('.usaa_member').show();
        $('.financial_professional').hide();
    }
});