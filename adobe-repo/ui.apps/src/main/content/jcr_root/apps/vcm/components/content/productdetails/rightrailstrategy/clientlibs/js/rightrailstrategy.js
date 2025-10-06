var loadstrategyFundsDetails = function(data){
    if (data) {
        $(".strategy-glance-asof").append(data.effectiveDate);
        $(".strategy_inception_date").append(data.inceptionDate);
        $(".strategy_bench_mark").append(data.benchmark);
        $(".strategy_active_shares").append(data.activeSharePercentage);
        $(".strategy_eVestment_category").append(data.category);
        $(".strategy_assets").append("$" + ReplaceNumberWithCommas(data.strategyAssets));
    }
}

var labelStrategyHeader = function(data){
    if(data){
        $("#strategy .glance-header-label").append(data.ataglance);
        var RightRailStrategy = data.RightRailStrategy;
        if(typeof data.RightRailStrategy != 'undefined'){
            $(".strategy_label_inception").append(RightRailStrategy.inceptiondate);
            $(".strategy_label_benckmark").append(RightRailStrategy.benchmark);
            $(".strategy_label_activeshares").append(RightRailStrategy.activeshares);
            $(".strategy_label_evestment").append(RightRailStrategy.evestmentcategory);
            $(".strategy_label_assets").append(RightRailStrategy.strategyAssets);
        }
    }
}

function ReplaceNumberWithCommas(yourNumber) {
    //Seperates the components of the number
    var n= yourNumber.toString().split(".");
    //Comma-fies the first part
    n[0] = n[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    //Combines the two sections
    return n.join(".");
}