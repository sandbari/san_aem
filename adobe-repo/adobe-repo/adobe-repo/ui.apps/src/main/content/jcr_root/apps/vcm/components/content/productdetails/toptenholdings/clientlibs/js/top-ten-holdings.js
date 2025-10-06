var top_ten_holdings=function(labels, data) {

    var top10holdingsVariantOneTableLabels;
    var top10holdingsVariantOneTableData;
    var top10holdingsVariantOneHeadRow = $("<tr/>");
    var top10holdingsVariantOnetHead = $("<thead/>");
    var top10holdingsVariantOnetBody = $("<tbody/>");
    var portfolioData = [];

    // bind labels
    $(".topten-holdings-variant-one-component .topten-holdings-title").append(labels.top10holdings);
    $(".topten-holdings-variant-one-component .topten-holdings-asof-label").append(labels.asof);
    $(".topten-holdings-variant-one-component .topten-holdings-portfolio-label").append(labels.totalprotfolio);
    top10holdingsVariantOneTableLabels = Object.values(labels.TopHoldings);

    // bind values
    if(Object.values(data).length){
        $(".topten-holdings-asof-value").append(data.as_of_date);

        // bind table heading
        top10holdingsVariantOneTableLabels.map(item => {
            top10holdingsVariantOneHeadRow.append(`<th scope='col'>${item}</th>`);
        });
        top10holdingsVariantOnetHead.append(top10holdingsVariantOneHeadRow);

        //bind table body data
        data.holdings.sort((a, b) => {
            return parseFloat(b.portfolio_percentage) - parseFloat(a.portfolio_percentage);
        })
        
        data.holdings.map(item => {
            var top10holdingsVariantOneObject = {
                "ticker": item.holding_name,
                "portfolio": parseFloat(item.portfolio_percentage).toFixed(2)
            };

            top10holdingsVariantOneTableData = Object.values(top10holdingsVariantOneObject);
            var top10holdingsVariantOneBodyRow = $("<tr/>");
            top10holdingsVariantOneTableData.map(item => {
                top10holdingsVariantOneBodyRow.append($(`<td>${item}</td>`));
                top10holdingsVariantOnetBody.append(top10holdingsVariantOneBodyRow);
            });
            top10holdingsVariantOnetBody.append(top10holdingsVariantOneBodyRow);
            $(".top-10-holdings-table").append(top10holdingsVariantOnetHead).append(top10holdingsVariantOnetBody);

            portfolioData.push(parseFloat(item.portfolio_percentage));
        });

        var portfolioIntegerValue = portfolioData.reduce((a, b) => a + b);
        $(".topten-holdings-portfolio-value").append(portfolioIntegerValue.toFixed(2));
    }
}

var beforeTopTenholdingsLoadFuc = function () {
    $('.dynamic-loader-toptenholdingone').css("display", "block");
    $('.error-message-toptenholdingone').css("display", "none");
}

var topTenHoldingsErrorHandlingFuc = function () {
	$('.topten-holdings-variant-one-component').css("visibility", "visible");
	$(".topten-holdings-variant-one-component .topten-holdings-title").append(ajaxData_productLabels.top10holdings);
	$(".error-message-toptenholdingone p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-toptenholdingone').css("display", "none");
    $('.error-message-toptenholdingone').css("display", "block");
}