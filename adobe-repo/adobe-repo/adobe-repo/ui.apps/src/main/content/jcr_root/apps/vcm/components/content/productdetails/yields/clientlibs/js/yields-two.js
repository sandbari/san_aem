var getMarketLabel;
var selectedYieldMoneyMarket = loadClass;
var yieldmoneytHead = $("<thead/>");
var yields_two_head = function(data){
    	$(".header-label-monymarkt").prepend(data.yields);
        var headRow = $("<tr/>");
        headRow.append(`<th scope='col'><span class="sr-only">Yield</span></th>`);
        headRow.append(`<th scope='col'>(%)</th>`);
        yieldmoneytHead.append(headRow);
        getMarketLabel = data.Yields;
}

var yields_two_body = function(data){
    var tBody = $("<tbody>");
    var datatr = [];
    var apiClasses = [];
    data.forEach(element => {
        apiClasses.push(element.share_class);
    });

    if (!(apiClasses.indexOf(selectedYieldMoneyMarket) > -1)) {
        selectedYieldMoneyMarket = apiClasses[0];
    }
    if (data) {
        data.map(item => {
            if (item.share_class == selectedYieldMoneyMarket) {
                $(".yields_money-market-asof").html("").append(item.yields_money_market.as_of_date);
                var sevenDay = item.yields_money_market.sevenday_yield;
                if (sevenDay == null) {
                    sevenDay = "API";
                    tBody.append($(`<tr><td>${(getMarketLabel.sevenday_yield).replace(":", '')}</td><td>N/A</td></tr>`));
                } else {
                    tBody.append($(`<tr><td>${(getMarketLabel.sevenday_yield).replace(":", '')}</td><td>${parseFloat(sevenDay).toFixed(2)}</td></tr>`));
                }
                tBody.append("</tbody>");
                $(".yields_money-market_table").html("").append(yieldmoneytHead).append(tBody);
            }
        });
    }
}

function onChangeYieldMoneyClass(selectClass){
	selectedYieldMoneyMarket = selectClass;
	if($('.yields_money-market_table').length > 0) {
    	yields_two_body(ajaxData_yields);
    }
}
selectboxChange(onChangeYieldMoneyClass);

var beforeYieldsmoneymarketLoadFuc = function () {
	$('.dynamic-loader-yieldsmoneymkt').css("display", "block");
    $('.error-message-yieldsmoneymkt').css("display", "none");
}

var yieldMoneyMktErrorHandlingFuc = function () {
	$('.yields-money-market').css("visibility", "visible");
	$(".header-label-monymarkt").html("").prepend(ajaxData_productLabels.yields);
	$(".error-message-yieldsmoneymkt p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-yieldsmoneymkt').css("display", "none");
    $('.error-message-yieldsmoneymkt').css("display", "block");
}