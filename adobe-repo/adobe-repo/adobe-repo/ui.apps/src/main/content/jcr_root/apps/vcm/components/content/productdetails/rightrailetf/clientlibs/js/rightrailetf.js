var ajaxData, labelSeries = [],
    summaryRelated;
var fetchetfLabel = function(data){
	if (data) {
        var etfLabel = data.RightRailetf;
        /*label for summary*/
        $(".summary_header").append(etfLabel.summary);
        $(".incept_label").append(etfLabel.inceptiondate);
        $(".ticker_label").append(etfLabel.ticker);
        $(".opv_label").append(etfLabel.IOPVTicker);
        $(".cusip_label").append(etfLabel.CUSIP);
        $(".category_label").append(etfLabel.category);
        $(".isin_label").append(etfLabel.ISIN);
        $(".exchng_label").append(etfLabel.exchange);
        $(".relatedInd_label").append(etfLabel.relatedindex);

        /*label for fund deatails*/
        $(".fundDetails_header").append(etfLabel.funddetails);
        $(".asof_label").append(etfLabel.AsOf);
        $(".navChange_label").append(etfLabel.navChange);
        $(".navPercentChange_label").append(etfLabel.navPercentChange);
        $(".latestNav_label").append(etfLabel.latestNav);
        $(".bidAsk_label").append(etfLabel.bidaskmidpoint);
        $(".bidAsk_median_label").append(etfLabel.bidaskmedian);
        $(".marketClose_label").append(etfLabel.marketprice);
        $(".premdisc_label").append(etfLabel.premiumdiscountpercent);
        $(".volume_label").append(etfLabel.volume);
        $(".share_label").append(etfLabel.sharesoutstanding);
        $(".netasset_label").append(etfLabel.netassets);
        $(".longCash_label").append(etfLabel.longcashinvested);

        /*fund yield labels*/
        $(".fundYield_header").append(etfLabel.fundyield);
        $(".yield_asof_label").append(etfLabel.AsOf);
        $(".secyild_label").append(etfLabel.thirtyday_sec_yield);
        $(".unsbcb_label").append(etfLabel.thirtyday_sec_unsubsidized_yield);
        $(".distrbn_label").append(etfLabel.twelvemonth_distribution_rate);
        $(".dividen_label").append(etfLabel.dividend_yield_percentage);
        $(".dayyield_label").append(etfLabel.sevenday_yield);

        /*labels for fees field*/
        $(".feedHeader").append(etfLabel.fees);
        $(".fee_label_asof").append(etfLabel.FeesAsOf);
        $(".feeNet_label").append(etfLabel.netexpenseratio);
        $(".feeGross_label").append(etfLabel.grossexpenseratio);
    }
}

var displayData = function(value,div){
    if( !value || value == "N/A" || value == "NaN%" ){
        $(div).parent().parent().hide();
    }else{
        $(div).append(value);
    }
}

var displayMedianData = function(value,div){
    if( !value || value == "N/A" || value == "NaN%" || value == "NaN"){
        $(div).parent().parent().hide();
    }else{
        $(div).append(value);
    }
}

var fundInfo = $('#etf');
/*function to display summary field data*/

var summaryFieldData = function(data){
	var seriesData = [];
	//displayData(data.as_of, '.as_of_summary');
    displayData(data.inception_date,'.sumry_inception_date');
    displayData(data.ticker,'.sumry_ticker');
    displayData(data.iopv,'.sumry_iopv_ticker');
    displayData(data.cusip,'.sumry_cusip');
    displayData(data.category,'.sumry_category');
    displayData(data.isin,'.sumry_isin');
    displayData(data.exchange,'.sumry_exchange');
}


/*function to display fund details field data*/

var fundDetailsData = function(data){
	var seriesData = [];
	var navChangeData = parseFloat(data.nav_change).toFixed(2);
    displayData(data.as_of,'.as_of_fund-details');
	if (navChangeData < 0){
		displayData("($" +  navChangeData.toString().replace(/[-]/g, '')+ ")",'.fd_nav_change');
		}
	else{
		displayData("$" + navChangeData,'.fd_nav_change');
		}
    displayData(parseFloat(data.nav_change_percentage).toFixed(2) + "%",'.fd_nav_percent_change');
    displayData("$" + parseFloat(data.latest_nav).toFixed(2),'.fd_nav');
    displayData(parseFloat(data.bid_ask_midpoint).toFixed(2),'.fd_bid_ask_midpoint');
    displayMedianData(parseFloat(data.thirty_day_median_bid_ask_spread).toFixed(2) + "%",'.fd_bid_ask_median');
    displayData(parseFloat(data.market_close).toFixed(2),'.fd_market_price');
    displayData(parseFloat(data.premium_discount_percentage).toFixed(2) + "%",'.fd_premium_discount_percent');
    displayData(data.volume,'.fd_volume');
    displayData(data.shares_outstanding,'.fd_shares_outstanding');
    displayData("$" + Math.ceil(data.net_assets).toString().replace(/(.)(?=(.{3})+$)/g, "$1,"),'.fd_net_assets');
    displayData(parseFloat(data.long_cash_invested_percentage) + "%",'.fd_percent_long_cash_invested');
}

//function to display fund yield field data

var fundyieldData = function(data){
	//displayData(data.as_of_date,'.as_of');
	displayData(data.dividend_yield_percentage_as_of_date,'.as_of');
    displayData(parseFloat(data.thirtyday_sec_yield).toFixed(2) + "%",'.thirty_day_sec_yield');
    displayData(parseFloat(data.thirtyday_sec_unsubsidized_yield).toFixed(2) + "%",'.thirty_day_sec_unsubscribed_yield');
    displayData(parseFloat(data.twelvemonth_distribution_rate).toFixed(2) + "%",'.twelve_month_distribution_rate');
    displayData(parseFloat(data.dividend_yield_percentage).toFixed(2) + "%",'.dividend_yield');
}

var feesData = function(data){
    //displayData(data.as_of_date,'.as_of_fee');
    displayData(parseFloat(data.net_expense_ratio).toFixed(2) + "%",'.net_expense_ratio');
    displayData(parseFloat(data.gross_exp_ratio).toFixed(2) + "%",'.gross_expense_ratio');
}

var toTitleCase = function(inputstr){
	var str = inputstr.replace(/\_/g, ' ');
    return str.replace(/\w\S*/g, function (txt) {
        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
    });
}

var beforeSummaryLoadFuc = function () {
    $('.dynamic-loader-rightrailetf').css("display", "block");
    $('.error-message-rightrailetf').css("display", "none");
}

var summaryErrorHandlingFuc = function () {
    $('.dynamic-loader-rightrailetf').css("display", "none");
    $(".error-message-rightrailetf p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.error-message-rightrailetf').css("display", "block");
}
var beforeFundDetailsLoadFuc = function () {
    $('.dynamic-loader-rightrailetf').css("display", "block");
    $('.error-message-rightrailetf').css("display", "none");
}

var fundDetailsErrorHandlingFuc = function () {
	$(".error-message-rightrailetf p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-rightrailetf').css("display", "none");
    $('.error-message-rightrailetf').css("display", "block");
}
var beforeFundYieldLoadFuc = function () {
    $('.dynamic-loader-rightrailetf').css("display", "block");
    $('.error-message-rightrailetf').css("display", "none");
}

var fundYieldErrorHandlingFuc = function () {
	$(".error-message-rightrailetf p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-rightrailetf').css("display", "none");
    $('.error-message-rightrailetf').css("display", "block");
}
var beforeFeesDataLoadFuc = function () {
    $('.dynamic-loader-rightrailetf').css("display", "block");
    $('.error-message-rightrailetf').css("display", "none");
}

var FeesDataErrorHandlingFuc = function () {
	$(".error-message-rightrailetf p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-rightrailetf').css("display", "none");
    $('.error-message-rightrailetf').css("display", "block");
}