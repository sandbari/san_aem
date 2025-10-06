$(document).ready(function() {
    let jsonEndpoint = $('#contactHoursJson').val();
	function getContactHours() {
		
			let deferred = $.Deferred();
	    
			$.ajax({
	        
				contentType: "application/json",
	        
				context: this,
	        
				type: 'GET',
	        
				url: jsonEndpoint,
	        
				dataType: "json",
				
				 beforeSend: function(xhr) {
					 $('.planTableLoader').css("display", "block");
	             },
				success: function (response) {
					deferred.resolve(response);
					$('.planTableLoader').css("display", "none");
					$('.st-contact-container').show();
					$('.st-contact-container-short-view').show();
				},
				error: function(jqXhr, textStatus, errorMessage) {
	                    $('.planTableLoader').css("display", "none");
	                    $('.error-message').css("display", "block");
	           }
	    });
	    
			return deferred.promise();
	}
	function updateContactHours(response){
	    let stdays = $('.st-days').text();
        let axstdays = stdays.replace("-", " to ");
        $('.ax-st-days').text(axstdays);
        let sttiming = $('.st-content-left .st-timing').text();
        let axsttiming = sttiming.replace("-", " to ");
        $('.ax-st-timing').text(axsttiming);
		
		 if(sharedJS.nonNullCheck(response)){

		    let str = response.open_hours;

            let axhours = str.replace("-", " to ");
			 
			if(response.isOpenNow){
				
				$('.st-open').text($('.open-info-text').text());
				
				$('.st-tf-open').text('(' + $('.open-info-text').text() + ')');
				
				$('.open-timing').contents().get(1).nodeValue = ' ' + response.open_hours;
				
				$('.open-timing').show();

				$('.ax-open-timing').contents().get(1).nodeValue = ' ' + axhours;
				
				$('.wait-timing').contents().get(1).nodeValue = ' ' + response.current_waittime;
				
				$('.wait-timing').show();
			}
			else{
				
				$('.st-open').text($('.closed-info-text').text());
				
				$('.st-tf-open').text('(' + $('.closed-info-text').text() + ')');
				
				if(!response.isHoliday){
					
					$('.open-timing').contents().get(1).nodeValue = ' ' + response.open_hours;
					
					$('.open-timing').show();

					$('.ax-open-timing').contents().get(1).nodeValue = ' ' + axhours;
				}
				else{
					
					$('.open-timing').hide();
				}
				
				$('.wait-timing').hide();
				
			}
		 }
		
		
	}
	if($('.contactdetails').length > 0 && $('.contactdetails .staticView').length <= 0) {
    	  
    	$.when(getContactHours()).done(function (response) {
    
    		updateContactHours(response);
    	
    	});
    }
});