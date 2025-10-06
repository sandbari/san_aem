var recent_distribution = function(selectedTab, data){
      	var headRow = $("<tr/>");
        var tHead = $("<thead/>");
        var tBody = $("<tbody/>");
        if (data.length) {
            data.filter((item, index) => {

                $(".distributions-table").html("");
                item.categories.map(ele => {
                    headRow.append(`<th scope='col'>${ele}</th>`);
                });

                tHead.append(headRow);
                $(".distributions-table").append(tHead);

                item.series.map((ele, ind) => {
                    var bodyRow = $("<tr/>");
                    ele.data.map((element, i) => {
                        bodyRow.append($(`<td>${(element)}</td>`));
                    });
                    tBody.append(bodyRow);

                });
               
                $(".distributions-table").append(tBody);

            })
         
        }
}

$(document).ready(function() {
  	if($('.recentdistribution').length > 0) {
        $(".distribution-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
        $(".distribution-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
    }
});