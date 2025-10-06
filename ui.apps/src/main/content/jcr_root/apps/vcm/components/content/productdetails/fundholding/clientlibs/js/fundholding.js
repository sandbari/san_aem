var fund_holding_body = function(data){
	var tBody = $("<tbody/>");
    if (data) {
		$(".fund-holding-asof").append(data.as_of_date);
		let fundholdingData = data.fund_holdings;
        fundholdingData.forEach(fundData =>{
				if ((fundData === null) || (fundData === undefined) || (fundData === "")) {
                    fundData = "N/A"
					}else{
						var bodyRow = $("<tr/>");
						bodyRow.append($(`<td class='text-left'>${fundData.underlying_style.replace(/[\[\]']+/g, '')}</td>`));
						bodyRow.append($(`<td>${parseFloat(fundData.underlying_style_percentage).toFixed(2)}</td>`));
					}
			tBody.append(bodyRow);
		});
        $(".fund-holding-table").append(tBody);
    }
}


var fund_holding_head = function(data){
	var tHead = $("<thead/>");
	var headRow = $("<tr/>");
	if (data) {
        
        $(".fundHoldingHead").prepend(data.fundholdings);
        let fundLables = Object.values(data.FundHoldings);
        fundLables.forEach(item=>{

            headRow.append(`<th scope='col'>${item}</th>`);
        })
		}
        tHead.append(headRow);
		$(".fund-holding-table").append(tHead);
}

var beforeFundHoldingLoadFuc = function () {
    $('.dynamic-loader-fundholdings').css("display", "block");
    $('.error-message-fundholdings').css("display", "none");
}

var fundHoldingErrorHandlingFuc = function () {
	$('.fund-holding-component').css("visibility", "visible");
	$(".fundHoldingHead").prepend(ajaxData_productLabels.fundholdings);
	$(".error-message-fundholdings p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-fundholdings').css("display", "none");
    $('.error-message-fundholdings').css("display", "block");
}