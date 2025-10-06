$(document).ready(function() {
	
	if($('.productAccordion').length > 0){
	
		$('.card-body').each(function(index, item) {
		   
			let parentGenericContainer = $(item).parents('.accordion');
			
			if(parentGenericContainer.length > 0){
				
				let id = '#'+$(parentGenericContainer).attr('id');
				
				$(item).attr('data-parent',id);
				
		   }
			
		});
		
	}	
	
});