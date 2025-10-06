var JSONDATA;
var authorUrl;
var firtstIndex = 0, lastIndex =0, currentIndex=0, noOfBlogs=0, currentPageTitle='';
/**Function to fetch the json data for loading the blog items */
$(document).ready(function() {
    if($('.blog-post-content-wrapper').length){
        var blogRootPath = $('.blog-post-content-wrapper').attr('data-blog-path');

        var urlBase = $('#blogUrl').val();
         $.ajax({
            type: "GET",
            url: urlBase,
            dataType: "json",
            data: {blogRootPath:blogRootPath},	
            success: function(data) {
                JSONDATA = data;
                authorUrl = data.authorLink;
                if(!!JSONDATA & !!JSONDATA.blogList){
                    if(!!JSONDATA.featureBlog){
                        JSONDATA.blogList.push(JSONDATA.featureBlog);    
                        sortData();
                    }
                    setAuthorNameLink();
                    noOfBlogs = JSONDATA.blogList.length;
                    lastIndex = noOfBlogs-1;
                    $.each(JSONDATA.blogList, function (k,v) {             
                        if(v.blogPagePath.indexOf(window.location.pathname)>=0) {
                            currentIndex = k;
                        }
                    });
                }
                },
            error: function() {
                console.log('JSON fetch failed.Please check the JSON url');
            }
         });
    }

});
function sortData(){
	JSONDATA.blogList.sort(function(a, b){return a.blogId.localeCompare(b.blogId)});
}
function setAuthorNameLink() {
    var authorKeys =$('.authorname').length;
    var counter = authorKeys;
    $('.authorname').each(function() {
        var authorURL = "";
        var authorName = $(this).text().trim().replace(/[^a-z0-9\s]/gi, '').replace(/[ ]/g, "-").toLowerCase();
        if (authorUrl != null && typeof authorUrl != 'undefined') {
            if (authorUrl.lastIndexOf('.html') > 0) {
                authorURL = authorUrl.replace(".html", "." + authorName + ".html");
            } else {
                authorURL = authorUrl + "." + authorName;
            }
        }
        $(this).attr('href', authorURL);
        if (counter > 1) {
            if ((authorKeys > counter - 1) && (counter > 2)) {
                $(this).append(', ');
            }
            if (counter <= 2) {
                $(this).append(' & ');
            }
            counter--;
        }

    });
}
function goToPage(index){	
	if(!!index){ 
		if(index<0 ) { currentIndex =index = lastIndex	}
		else if(index>lastIndex){ currentIndex =index =0;}
    }else {
		index=0;
    }
    window.location.href = JSONDATA.blogList[index].blogPagePath;
 }
function nextBlogId(){
	goToPage(++currentIndex);
}
function prevBlogID(){
	goToPage(--currentIndex);
}
 $(document).on("click",".blog-post-arrow-right",function() {
	 nextBlogId();	 

 }); 
  $(document).on("click",".blog-post-arrow-left",function() {
	  prevBlogID();
 });
