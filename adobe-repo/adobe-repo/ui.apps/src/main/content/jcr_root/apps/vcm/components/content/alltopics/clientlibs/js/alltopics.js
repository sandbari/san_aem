
$(document).ready(function() {

    $('.alltopics .article-filter-list .list-group-item').each(function (index, item) {
        var pageUrl = $(this).children('a').data("url");
        var selectedFilterTopic = $(this).children('a').text();
        pageUrl = updateQueryStringParameter(pageUrl,'selected', selectedFilterTopic);
        $(this).children('a').attr("href", pageUrl);
    })

    function updateQueryStringParameter(uri, key, value) {
      var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
      var separator = uri.indexOf('?') !== -1 ? "&" : "?";
      if (uri.match(re)) {
        return uri.replace(re, '$1' + key + "=" + value + '$2');
      }
      else {
        return uri + separator + key + "=" + value;
      }
    }

});

