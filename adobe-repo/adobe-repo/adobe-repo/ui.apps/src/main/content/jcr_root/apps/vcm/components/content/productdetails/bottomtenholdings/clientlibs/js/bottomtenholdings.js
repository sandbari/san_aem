var bottom_ten_holdings_body = function(data){
    var tBody = $("<tbody/>");
    var portfolioData = [];
    if (data) {
		$(".bottom-ten-holdings-asof").append(data.as_of_date);
        let bottomHoldingData = data.holdings;
        bottomHoldingData.sort((a, b) => {
            return parseFloat(a.portfolio_percentage) - parseFloat(b.portfolio_percentage);
        })
        bottomHoldingData.forEach(holdingsData =>{
				if ((holdingsData === null) || (holdingsData === undefined) || (holdingsData === "")) {
                    holdingsData = "N/A"
					}else{
						var bodyRow = $("<tr/>");
						bodyRow.append($(`<td>${holdingsData.holding_name}</td>`));
                        bodyRow.append($(`<td>${parseFloat(holdingsData.portfolio_percentage).toFixed(2)}</td>`));
					}
			tBody.append(bodyRow);
			portfolioData.push(parseFloat(holdingsData.portfolio_percentage));
		});
        var portfolioIntegerValue = portfolioData.reduce((a, b) => a + b);
        $(".bottomten-holdings-portfolio-value").append(portfolioIntegerValue.toFixed(2));
        $(".bottom-ten-holdings-table").append(tBody);
	}
}

var bottom_ten_holdings_head = function(data){
	var tHead = $("<thead/>");
    var headRow = $("<tr/>");
	if (data) {
            $(".bottom-head-label").prepend(data.bottomtenholdings);
            let bottomLabels = Object.values(data.BottomHoldings);
            bottomLabels.forEach(headings =>{
                headRow.append(`<th scope='col'>${headings}</th>`);
            })
			}
            tHead.append(headRow);
			$(".bottom-ten-holdings-table").append(tHead);
			$(".bottomten-holdings-portfolio-label").append(data.totalprotfolio);
}

var beforebottomHoldingsLoadFuc = function () {
    $('.dynamic-loader-bottomHoldings').css("display", "block");
    $('.error-message-bottomHoldings').css("display", "none");
}

var bottomHoldingsErrorHandlingFuc = function () {
	$('.bottom-holding-component').css("visibility", "visible");
	$(".bottom-head-label").prepend(ajaxData_productLabels.bottomtenholdings);
	$(".error-message-bottomHoldings p").html(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-bottomHoldings').css("display", "none");
    $('.error-message-bottomHoldings').css("display", "block");
}