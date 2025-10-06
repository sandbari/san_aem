var top_ten_holdings_fourcolumn=function(labels, data) {

    var top10holdingsVariantTwoTableLabels;
    var top10holdingsVariantTwoTableData;
    var top10holdingsVariantTwoHeadRow = $("<tr/>");
    var top10holdingsVariantTwotHead = $("<thead/>");
    var top10holdingsVariantTwotBody = $("<tbody/>");
    var portfolioData = [];

    // bind labels
    $(".topten-holdings-variant-two-component .topten-holdings-title").append(labels.top10holdings);
    $(".topten-holdings-variant-two-component .topten-holdings-asof-label").append(labels.asof);
    $(".topten-holdings-variant-two-component .topten-holdings-portfolio-label").append(labels.totalprotfolio);
    top10holdingsVariantTwoTableLabels = Object.values(labels.TopHoldings_variant_two);

    // bind values
    if(Object.values(data).length){
        $(".topten-holdings-variant-two-asof-value").append(data.as_of_date);

        // bind table heading
        top10holdingsVariantTwoTableLabels.map(item => {
         top10holdingsVariantTwoHeadRow.append(`<th scope='col'>${item}</th>`);
        });
        top10holdingsVariantTwotHead.append(top10holdingsVariantTwoHeadRow);

        //bind table body data
        data.holdings.sort((a, b) => {
            return parseFloat(b.portfolio_percentage) - parseFloat(a.portfolio_percentage);
        })
        
        data.holdings.map(item => {
         var top10holdingsVariantTwoObject = {
             "security_des": item.holding_name,
             "maturity_date": item.maturity_date,
             "base_value": parseFloat(item.market_value).toFixed(2),
             "base_percent": parseFloat(item.portfolio_percentage).toFixed(2)
         };

         top10holdingsVariantTwoTableData = Object.values(top10holdingsVariantTwoObject);
         var top10holdingsVariantTwoBodyRow = $("<tr/>");
         top10holdingsVariantTwoTableData.map(item => {
             top10holdingsVariantTwoBodyRow.append($(`<td>${item}</td>`));
             top10holdingsVariantTwotBody.append(top10holdingsVariantTwoBodyRow);
         });
         top10holdingsVariantTwotBody.append(top10holdingsVariantTwoBodyRow);
         $(".top-10-4col-holdings-table").append(top10holdingsVariantTwotHead).append(top10holdingsVariantTwotBody);


         portfolioData.push(parseFloat(item.portfolio_percentage));
        });

        var portfolioIntegerValue = portfolioData.reduce((a, b) => a + b);
        $(".topten-holdings-variant-two-portfolio-value").append(portfolioIntegerValue.toFixed(2));
    }
}


var beforeTopTenHoldingsTwoLoadFuc = function () {
    $('.dynamic-loader-toptenholdingtwo').css("display", "block");
    $('.error-message-toptenholdingtwo').css("display", "none");
}

var topTenHoldingsTwoErrorHandlingFuc = function () {
	$('.topten-holdings-variant-two-component').css("visibility", "visible");
	$(".topten-holdings-variant-two-component .topten-holdings-title").append(ajaxData_productLabels.top10holdings);
	$(".error-message-toptenholdingtwo p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-toptenholdingtwo').css("display", "none");
    $('.error-message-toptenholdingtwo').css("display", "block");
}