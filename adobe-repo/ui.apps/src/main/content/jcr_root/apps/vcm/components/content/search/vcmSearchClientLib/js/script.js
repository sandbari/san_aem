
$(document).ready(function() {
	
SearchJS ={		
	subdomain_user_entity_type:sharedJS.getCookie(COOKIEKEYCONFIG.SUBDOMAIN_USER_ENTITY_TYPE),
	suggesstionFileName:"suggesstions-%usertype%.json",
	searchUrl: $("#searchUrl").val(),
	suggesstionUrl: $("#suggestionsUrl").val(),
	suggestionLimit:$("#suggestionsLimit").val(),
	rootPagePath:$("#suggestionsRootPath").val(),
	searchResultsPagePath:$("#searchResultsPagePath").val(),
	searchIgnoreTag:$('#searchIgnoreTag').val(),
	searchLimit: $('#searchLimit').val() || '-1',
	recentSearchLimit: 6,
	groups: getTabDetails(),
	products:[],
	advicetools:[],
	general:[],
	productsCount : 0,
	adviceCount : 0,
	generalCount : 0,
	count : 0,
	autoSuggestData:[],
	prevSearch:[],
	searchTerm:'',
	SeeAllText: $('#SeeAllText').val(),
	seeAllLinkDescription: $('#seeAllLinkDescription').val(),
	populateDetails : function(dataObj,indCount,divName){
		var desc="";
		var length = 4;
		var displayClass= '';
		$.each(dataObj, function(i, data) {

			if(!!data.description){
				desc=(data.description).replace(/\"/g, "")
			}
			else
			{
				desc="";
			}

			if(i>=length){
				displayClass = 'extra';
			}

			$(".dropdown-item[data-selected='"+divName+"']").parents('li').removeClass('hide_tab');
			$(".data_list[data-tab='"+divName+"']").removeClass('hide_tab');

			var tot_searchTabs = $('.searchResultsWrapper .dropdown-menu li:not(.hide_tab)').length;
            $('.searchResultsWrapper .dropdown-menu li:not(.hide_tab) a').attr('aria-setsize', tot_searchTabs);
            var visibleSearchTabs = $('.searchResultsWrapper .dropdown-menu li:not(.hide_tab)');
            $(visibleSearchTabs).each(function(el, list) {
                var $thisSearchlList = $(list);
                var $posSearchlinset = el+1;
                $thisSearchlList.find('a').attr('aria-posinset', $posSearchlinset);

            });

			$('<div class="mb-3 search-results '+displayClass+'" role="listitem"><div class="title" role="listitem"><a href='+(data.pagePath)+' >'+(data.pageTitle).replace(/\"/g, "")+
			'</a></div><div class="descp">'+desc+'</div></div>')
			.appendTo($(".data_list[data-tab='"+divName+"']").children(".list_content"));
			});

			$(".dropdown-item[data-selected='"+divName+"'] .count").html('('+indCount+')');

			if(dataObj.length>4){
				var finalLength;
				$('li.active a').attr('data-selected')!="all" ? finalLength = indCount : finalLength = length+' of '+indCount
						$('<div class="see_All '+divName+'"> <a href="javascript:SearchJS.activeTab(\'' + divName + '\',\'' + indCount + '\');" aria-label="'+SearchJS.seeAllLinkDescription+'"><span>'+SearchJS.SeeAllText+'</span>  <img class="searchimg" src="/content/dam/vcm/search/seeAll.svg" alt=""></a> </div> ').appendTo($(".data_list[data-tab='"+divName+"']").children(".list_content"));
				$(".data_list[data-tab='"+divName+"'] .count").html('('+finalLength+')');
			}

	},
 
	searchResults : function(url,searchTerm){
		$.ajax({
			url: url,
			dataType: "json",
			data: {
				searchtext: searchTerm,
				tabTypes: SearchJS.groups.toString(),
				searchIgnoreTag: SearchJS.searchIgnoreTag,
				searchLimit: SearchJS.searchLimit
				
			},
			beforeSend: function() {
				 $('.planTableLoader').css("display", "block");
             },
			success: function( data ) {
				$('.planTableLoader').css("display", "none");
				SearchJS.resetSearchUI();
				SearchJS.displaySearchResults(data,searchTerm);
			},
			error: function(jqXhr, textStatus, errorMessage) {			 
				 $('.searchResultsWrapper').hide();
				 $('.no-results-set').show();
				 $('.planTableLoader').css("display", "none");
            },
			complete: function() {
				 var evt = $.Event('searchAjaxCompleted');
                 $('.searchResultsWrapper').removeClass('hide');
                 $('.planTableLoader').css("display", "none");
				 $(window).trigger(evt);
            },
		});
	},

	resetSearchUI: function(){
		 $('.searchResultsWrapper').show();
		 $('.no-results-set').hide();
		 $('.list_content').html('');
		 $('.data_list .count').html('');
		 $(".dropdown-item").not(".all-tab").parents('li').addClass('hide_tab');
         $(".data_list").addClass('hide_tab');
			
	},
	
	displaySearchResults : function(data,searchTerm){
		if(typeof data != "undefined" && data != null && data != ""){
			SearchJS.searchTerm=searchTerm;
			SearchJS.count=0;
			$('#search-term').text(searchTerm);
			 for (var key in data) {
				    if (data.hasOwnProperty(key) && data[key].length > 0) {
				    	SearchJS.count=parseInt(SearchJS.count)+parseInt(data[key].length);
				    	SearchJS.populateDetails(data[key],data[key].length,key);
				    }
				}
			 if(parseInt(SearchJS.count) <= 0){
				 $('.searchResultsWrapper').hide();
				 $('.no-results-set').show();
			 }
		}
		else{
			 $('.searchResultsWrapper').hide();
			 $('.no-results-set').show();
		}
		$('.search-results.extra').hide();
	},
	
	
	autocomplete : function(url,request,response){
		$.ajax({
			url: url,
			dataType: "json",
			success: function( data ) {
				console.log(data);
				$('.list_content').html('');
				SearchJS.searchTerm=request.term;
				SearchJS.autoSuggestData=data.suggestion; 
				response(SearchJS.autoSuggestData);
				$('.search-results.extra').hide();
			},
		});
	},
	
	
	activeTab:function (tag,indCount){
		length = 4
		$('.ul_list li.active').removeClass('active');
		if(!tag || tag ==="all"){
			$('.data_list,.see_All').show();   
			$('.search-results.extra').hide();
			for(var i=0;i<$('.ul_list li').length;i++){
				if($($('.ul_list li')[i]).children('a').attr('data-selected')== tag){
					$($('.ul_list li')[i]).addClass('active');
				}  
			}
		}else{     
			$('.data_list').hide();
			for(var i=0;i<$('.ul_list li').length;i++){
				if($($('.ul_list li')[i]).children('a').attr('data-selected')== tag){
					$($('.ul_list li')[i]).addClass('active');
					$(".data_list[data-tab='"+tag+"']").show();
				}                
			}
			$('.search-results.extra').show();
			$('.see_All').hide();
		}
		var finalLength
		$('li.active a').attr('data-selected')!="all" ? finalLength = indCount : finalLength = length+' of '+indCount;
		
		if($('li.active a').attr('data-selected')=="all"){
			for(var i=0;i<SearchJS.groups.length;i++){
				indCount = $(".dropdown-item[data-selected='"+SearchJS.groups[i]+"'] .count").text().replace(/[()]/g,"");
				if(indCount<4){
					finalLength = indCount
				}
				else{
					finalLength = length+' of '+indCount
				}
				$(".data_list[data-tab='"+SearchJS.groups[i]+"'] .count").html('('+finalLength+')');
			}
		}
		else{
			$(".data_list[data-tab='"+tag+"'] .count").html('('+finalLength+')');
		} 
	},
	
	
	searchHistory(){
		$('#searchHistory').html('');
		var stored_value =getCookie('searches');
		if(typeof stored_value != "undefined" && stored_value != null && stored_value != "")
		{
			var splitvalue = stored_value.split(',');
			var list = $("#searchHistory").append('<ul></ul>').find('ul');
			for(var i=0;i<splitvalue.length;i++){ 
				list.append('<li><a href="javascript:SearchJS.recentSearchNavigate(\''+(splitvalue[i])+'\');">'+(splitvalue[i])+'</a></li>');			
			}
		}
		
	},
	
	
	addToSearchHistory(searchedValue){
		var searchedItems = getCookie('searches');
		if(typeof searchedItems != undefined  
				&& searchedItems != null
				&& searchedItems != ""){
			let searchedItemsArr=searchedItems.split(',');
			if(!searchedItemsArr.includes(searchedValue)){
				if(searchedItemsArr.length < SearchJS.recentSearchLimit){
					var searchKeyworkds=searchedItems+','+searchedValue;
					sharedJS.setCookie("searches",searchKeyworkds);
					//document.cookie = "searches="+searchedItems+','+searchedValue;
				}
				else
				{
					searchedItemsArr=searchedItemsArr.slice(1,searchedItemsArr.length);
					var searchKeyworkds=searchedItemsArr.join(',')+','+searchedValue;
					sharedJS.setCookie("searches",searchKeyworkds);
					
					//document.cookie = "searches="+searchedItemsArr.join(',')+','+searchedValue;
				}
			}
		}
		else{
			sharedJS.setCookie("searches",searchedValue);
			//document.cookie = "searches="+searchedValue;
		}
		 
	},
	
	
	getUrlParameter:function(sParam) {
		var sPageURL = window.location.search.substring(1),
		sURLVariables = sPageURL.split('&'),
		sParameterName,
		i;
		for (i = 0; i < sURLVariables.length; i++) {
			sParameterName = sURLVariables[i].split('=');
			if (sParameterName[0] === sParam) {
				return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
			}
		}
	},
	
	recentSearchNavigate:function(value){
		window.location.href= SearchJS.searchResultsPagePath+"?searchTerm="+ value;
	},
	
	getURI :function() {
		var uri = window.location.protocol + "//" + window.location.hostname;
		if (!!window.location.port) {
			uri = uri + ":" + window.location.port;
		}
		return uri;
	},
	
	doOverlaySuggestion: function(){
		
		$('.overlay-sugessions-menu ul').empty();
		
		 $('#nav-txt-search').val('');
		
		var suggestionList = new Bloodhound({
		    
			datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		    
			queryTokenizer: Bloodhound.tokenizers.whitespace,
			 
			prefetch: {
		    
				url : SearchJS.getSuggestUrl(),//'/content/dam/vcm/configs/suggestionList.json',
		    	
				cacheKey:'suggestionIndex',
		    	
				cache : false
		    }
		
		 });
		
	   $('#nav-txt-search,#txt-search').typeahead({
		  
		   hint: false,
			
		   highlight: true,
			
		   minLength: 2,
			
		   menu: $('.overlay-sugessions-menu ul')
		  	
	   },
	   {
	    
	  		name: 'suggestionList',
	    
	  		display: 'value',
	    
	  		source: suggestionList,
	    
	  		limit: parseInt(SearchJS.suggestionLimit),
			
	  		templates: {
			  
	  			suggestion: function(data) {
	    		
	  				return '<li class="ui-menu-item" role="option"><a tabindex="-1" class="ui-menu-item-wrapper">' + data.value + '</a></li>';
				
	  			}
	  	  }
		  }).on('typeahead:selected', function(event, item) {
			  
				  window.location.href= SearchJS.searchResultsPagePath+"?searchTerm="+ item.value;
				  
				  SearchJS.addToSearchHistory(item.value); 
			   
		  });
	},
   doSearchPageSuggestion: function(){
	   
	   $('.search-suggession-menu ul').empty();
		
		var suggestionList = new Bloodhound({
		    
			datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		    
			queryTokenizer: Bloodhound.tokenizers.whitespace,
		 
			prefetch: {
		    
				url : SearchJS.getSuggestUrl(),//'/content/dam/vcm/configs/suggestionList.json',
		    	
				cacheKey:'suggestionIndex',
		    	
				cache : false
		    }
		
		 });
		
	   $('#txt-search').typeahead({
			 
		   hint: false,
			
		   highlight: true,
			
		   minLength: 2,
			
		   menu: $('.search-suggession-menu ul')
		  	
	   },
	   {
	    
	  		name: 'suggestionList',
	    
	  		display: 'value',
	    
	  		source: suggestionList,
	    
	  		limit: parseInt(SearchJS.suggestionLimit),
			
	  		templates: {

	  			suggestion: function(data) {
	    		
	  				return '<li class="ui-menu-item" role="option"><a tabindex="-1" class="ui-menu-item-wrapper">' + data.value + '</a></li>';
				
	  			}
	  	  }
		  }).on('typeahead:selected', function(event, item) {
			  
			      SearchJS.searchResults(SearchJS.searchUrl,item.value);
					
				   SearchJS.addToSearchHistory(item.value);
				   
				   $('.search-suggession-menu ul').hide();
		  });
	},
	getSuggestUrl: function(){
		
		//Check non null else consider it as Member
        if (!sharedJS.nonNullCheck(SearchJS.subdomain_user_entity_type)) {
            
        	SearchJS.subdomain_user_entity_type = COOKIEKEYCONFIG.DEFAULT;
        }
        let url = SearchJS.suggesstionUrl + SearchJS.suggesstionFileName.replace("%usertype%", SearchJS.subdomain_user_entity_type);
       
        return url;

	}

};


let sameDomain = false;
$.each(USERDOMAINMAP, function (index, item) {
	if(window.location.origin.includes(item)){
		sameDomain = true;
	}
});
if(sameDomain == false){
	SearchJS.searchUrl = USERDOMAINMAP.usaa_member + SearchJS.searchUrl;
	SearchJS.suggesstionUrl = USERDOMAINMAP.usaa_member + SearchJS.suggesstionUrl;
	SearchJS.searchResultsPagePath = USERDOMAINMAP.usaa_member + SearchJS.searchResultsPagePath;
	
	
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
	var c = ca[i];
	while (c.charAt(0)==' ') c = c.substring(1);
	if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
	}
	return "";
}


function getTabDetails() {
	 let tabTypes = [];
	  $('input[name="tabTypes"]').each(function(){
		  tabTypes.push($(this).val());
	  });
	  return tabTypes;
}

	
	$(".clear_search").hide();
	
	$('.no-results-set').hide();
	
	SearchJS.searchHistory();
	
	if(typeof SearchJS.getUrlParameter("searchTerm") != undefined && 
			  SearchJS.getUrlParameter("searchTerm") != "" && 
			  SearchJS.getUrlParameter("searchTerm") != null){
		
		SearchJS.searchResults(SearchJS.searchUrl,SearchJS.getUrlParameter("searchTerm"));
	}
	
	$(".dropdown a.dropdown-toggle").click(function(){
	
		$(this).find('i').toggleClass('fa-chevron-circle-down fa-chevron-circle-up');  
	});
	
	$(".dropdown-menu").on('click', 'li a', function(){
		
		$(".btn:first-child").find('span').text($(this).text());
		
		$(".btn:first-child").find('span').val($(this).text());
	
		$(this).parents(".dropdown").find('i').toggleClass('fa-chevron-circle-down fa-chevron-circle-up');
	});
	
	
	$('#txt-search').keypress(function (e) {
		
		var uri = window.location.toString();
		
		$(".searchResultsWrapper").addClass("hide");
		
		if (uri.indexOf("?") > 0) {
		
			var clean_uri = uri.substring(0, uri.indexOf("?"));
			
			window.history.replaceState({}, document.title, clean_uri);
		}	
		
		var key = e.keyCode || e.which;
		
		var regex = /^([a-zA-Z0-9 _-\s]+)$/
		
		var isValid = regex.test(String.fromCharCode(key));
		
		if(isValid){
		
			if(key == 13){	
				var inputValue = $("#txt-search").val();
				
				SearchJS.addToSearchHistory(inputValue);
				
				SearchJS.searchResults(SearchJS.searchUrl,inputValue);
				
				$('#autcomplete_list ul').removeClass('tt-open');
				
				
			}
		}
		else{
		   
			e.preventDefault();
		}
		
	}); 
	
	$('#nav-txt-search').keypress(function (e) {
		
		var key = e.keyCode || e.which;
		
		var regex = /^([a-zA-Z0-9 & _-\s]+)$/
		
		var isValid = regex.test(String.fromCharCode(key));
		
		if(isValid){
		
			if(key == 13)	{	
				
				var searchedValue = $("#nav-txt-search").val();
				
				SearchJS.addToSearchHistory(searchedValue);
				
				window.location.href= SearchJS.searchResultsPagePath+"?searchTerm="+ searchedValue;	
				
			}
		}
		else{
		
			e.preventDefault();
		}
		
	});
	
	$('#nav-txt-search').keyup(function (){
		
		if($(this).val() == ''){
		
			$(".clear_search").hide();
		}
		else{
		
			$(".clear_search").show();
		}
	});
	
	$('#txt-search').keyup(function (e){
		
		var key = e.keyCode || e.which;
		
		if($(this).val() == ''){
		
			$(".clear_search").hide();
		}
		else{
		
			$(".clear_search").show();
		}
	});
	
	if(window.location.pathname.includes( SearchJS.searchResultsPagePath ) ){
		
		SearchJS.doSearchPageSuggestion();
		
	 }
	//AX Fixes
	$('.form-group.has-search').append('<span class="numofRecords sr-only" aria-live="polite"></span>');
	$('#nav-txt-search,#txt-search').on('keydown', function (e) {
        if(e.keyCode==9){
            if($("#searchHistory ul").length){
                $("#searchHistory ul li:first-child").focus();
                return
            }
            if($('#nav-txt-search').val() != ""){
                $(this).parents('.has-search').find('btn.clear_search').focus();

            }
            else {
                $('#overlayNav .logoLink').focus();
                setTimeout(function(){
                    $('#overlayNav a.logoLink').focus();},0);
                    return
            }
        }
        if(!((e.keyCode==38) || (e.keyCode==40))){
             $('.ui-menu-item').each(function(el, list) {
                 setTimeout(function () {
                     var totalSearchSug = $('.ui-menu-item').length;
                     $('.numofRecords').empty().append(totalSearchSug +' records found based on your keywords');
                 }, 200)
             });
        }
    });

    $('#nav-txt-search,#txt-search').on('keydown', function (e) {
        setTimeout(function(){
            $('#autcomplete_list .ui-menu-item').each(function(el, list) {
                var totalSearchSug = $('.ui-menu-item').length;
                var thisElement =   $(list);
                thisElement.attr('aria-setsize', 0);
                thisElement.attr('aria-posinset', 0);
                thisElement.attr('aria-setsize', totalSearchSug);
                thisElement.attr('aria-posinset', el+1);
                console.log(list);
            });
        }, 1000)
    });

    $('.clear_search').on('keydown', function (e) {
        if((e.keyCode==32) || (e.keyCode==13)){
            $('#nav-txt-search').val('').focus();
        }
        if(e.keyCode==9){
            if($("#searchHistory ul").length == 0){
                $('#overlayNav .logoLink').focus();
                setTimeout(function(){
                    $('#overlayNav a.logoLink').focus();},0);
            }
        }
    });
    $('#searchHistory li:last-child a ').on('keydown', function (e) {
        if(e.keyCode==9 ){
            $('#overlayNav a.logoLink').focus();
            setTimeout(function(){
                $('#overlayNav a.logoLink').focus();},0);

        }
    });

	$('.searchResultsWrapper .dropdown-menu li a').on('keydown', function(e){
        var nextLi = $(this).parent().next('li');
        var prevLi = $(this).parent().prev('li');
        if (e.which == 39) {
            if(nextLi.hasClass('hide_tab')){
                $('li.hide_tab').next('li').find('a').focus();
                return;
            }
            else{
                nextLi.find('a').focus();
            }
         }
         if (e.which == 37) {
            if(prevLi.hasClass('hide_tab')){
                $('li.hide_tab').prev('li').find('a').focus();
                return;
            }
            else{
                prevLi.find('a').focus();
                return;
            }
         }

         if( (e.which == 32) || (e.which == 13) ){
            $(this).trigger("click");
            $(this).parents('ul').find('li.active a').attr('tabindex', '0');
            $('.searchResultsWrapper ul.ul_list li.active a').attr('aria-selected', 'true');
            setTimeout(function(){ $(this).parents('ul').find('li.active a').attr('aria-selected', 'true'); }, 100);
            e.preventDefault();
         }
         if( e.which == 9){
            $(this).parent().siblings('li').find('a').attr('tabindex', '-1');
            $(this).parents('ul').find('li.active a').attr('tabindex', '0');
         }
    });
    $('.searchResultsWrapper ul.ul_list li a').attr('aria-selected', 'false');
    $('.searchResultsWrapper ul.ul_list li.active a').attr('aria-selected', 'true');
    $('.searchResultsWrapper ul.ul_list li a').on('click', function(){
        $('.searchResultsWrapper ul.ul_list li a').attr('aria-selected', 'false');
        $(this).parent('li').find('a').attr('aria-selected', 'true');
    });

    $('#overlayNav .logoLink,#overlayNav .closebtn, #overlayNav #searchHistory a ').on('keydown', function(e){
        if(e.which==27){
            $('#overlayNav').hide();
            $('span.searchIcon').focus();
        }
    })
	
}); 



$(document).on("click",".ul_list li a",function(){
	
	var attr =$(this).attr("data-selected");
	
	var count = $(this).children('.count').text().replace(/[()]/g,"");
	
	SearchJS.activeTab(attr,count);
});


$(document).on("click",".clear_search",function(){ 

	$("#txt-search").val(null);
	
	$("#nav-txt-search").val(null);
	
	$('.list_content').html('');
	
	$(".data_list").find('.count').html('');
	
	$('.clear_search').hide();
	
});
