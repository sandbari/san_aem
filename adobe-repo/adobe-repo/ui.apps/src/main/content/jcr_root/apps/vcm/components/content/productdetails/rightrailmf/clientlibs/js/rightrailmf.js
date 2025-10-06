var selectedRightRailMf = loadClass;
var loadmfFundsDetails = function(data){
    var apiClasses = [];
    var  totalAUM=0;
    data.forEach(element => {
     apiClasses.push(element.shareClass);
      totalAUM = parseFloat(totalAUM) + parseFloat(element.total_fund_aum);
    });

    if (!(apiClasses.indexOf(selectedRightRailMf) > -1)) {
        selectedRightRailMf = apiClasses[0];
    }
    /*check for memeber from cookie*/
    var cookieValue = sharedJS.getCookie('subdomain_user_entity_type');
	$(".inv_min_div,.ff_volatity,.initauto").show();
	if(cookieValue == "financial_professional"){
        $(".inv_min_div, .ff_volatity,.initauto").hide();
    }
	if (localStoreValues != null && (typeof localStoreValues != 'undefined') && $("#fundID").val() == localStoreValues[1] && localStoreValues[2] == "true")  {
        $(".inv_min_div").hide();
    }

    if (data.length) {
    	data.forEach(item => {
    		if (item.shareClass == selectedRightRailMf) {
                let volatility;
                displayMFData("$" + Math.ceil(item.init_invest).toString().replace(/(.)(?=(.{3})+$)/g, "$1,"),'.invest_init');
                displayMFData("$" + parseFloat(item.subsequent_invest_min).toFixed(2),'.auto_invest_min');
                displayMFData("$" + Math.ceil(item.auto_invest_min).toString().replace(/(.)(?=(.{3})+$)/g, "$1,"),'.invest_init_auto');
                displayMFData(item.as_of_date,'.mf_as_of');
                displayMFData("$" + Math.ceil(totalAUM).toString().replace(/(.)(?=(.{3})+$)/g, "$1,"),'.aum');
                displayMFData(item.volatility,'.volatility');
                displayMFData(parseFloat(item.net_expense_ratio).toFixed(2) + "%",'.mf_net_expense_ratio');
                displayMFData(parseFloat(item.gross_exp_ratio).toFixed(2) + "%",'.mf_gross_expense_ratio');
                displayMFData(item.incept_date,'.inception_date');
                displayMFData(item.cusip,'.category-cusip');
                displayMFData(item.category,'.categorymf-value');
                volatility = item.volatility;

                var riskvalue = 0;
                var riskfieldTemplate = '';
                if (volatility == "Preservation of Capital") {
                    riskvalue = 1;
                } else if (volatility == "Conservative") {
                    riskvalue = 2;
                } else if (volatility == "Moderately Conservative") {
                    riskvalue = 3;
                } else if (volatility == "Moderate") {
                    riskvalue = 4;
                } else if (volatility == "Moderately Aggressive") {
                    riskvalue = 5;
                } else if (volatility == "Aggressive") {
                    riskvalue = 6;
                } else {
                    riskvalue = 7;
                }

                for (var i = 1; i <= 7; i++) {
                    if (i <= riskvalue) {
                        riskfieldTemplate = riskfieldTemplate + ' <span class="risk-field active"></span>'
                    } else {
                        riskfieldTemplate = riskfieldTemplate + '<span class="risk-field"></span>'
                    }
                }
    		}
        $(".riskGraph").html( riskfieldTemplate );
    	});

    	var investVal1, investVal2, investVal3;

        investVal1 = $('.invest_init_auto').html();
        investVal2 = $('.invest_init').html();
        investVal3 = $('.auto_invest_min').html();

        if (investVal1 == "" && investVal2 == "" && investVal3 == ""){
            $('.inv_min_div').hide();
        }

    }
}

function displayMFData(value, div) {
    if( !value || value == "N/A" || value == "NaN%" || value == "NaN" || value == "$NaN" || value == "$0" || value.match(/^0(?:\.0+)?(?:%)?$/)){
        $(div).parent().parent().hide();
    } else {
        $(div).parent().parent().show();
        $(div).html("").append(value);
    }
}
if($(".total_aum_label").is(":hidden")){
    $(".dateSpan").hide()
} else if($(".total_aum_label").is(":visible")){
    $(".dateSpan").show()
}

var labelmfHeader = function(data){
    if(data){
        var rightRailMf = data.RightRailmf;
        $(".investMinHeader").append(rightRailMf.intialinvestmentheading);
        $(".init_invest_min").append(rightRailMf.initialinvestment);
        $(".init_auto_invest").append(rightRailMf.subsequent_invest_min);
        $(".auto_invest_label").append(rightRailMf.automaticinvestment);
        $(".fund_fact_header").prepend(rightRailMf.fundfacts);
        $(".total_aum_label").append(rightRailMf.totalfund);
        $(".volatility_label").append(rightRailMf.volatility);
        $(".gross_exp_label").append(rightRailMf.grossexpenseratio);
        $(".net_exp_label").append(rightRailMf.netexpenseratio);
        $(".category_label").append(rightRailMf.category);
        $(".incept_Date_label").append(rightRailMf.inceptiondate);
        $(".cusip_label").append(rightRailMf.CUSIP);
        $(".fee_mflabel_asof").append(rightRailMf.feesprospectus);
    }
}

var beforeRightRailLoadFuc = function () {
    $('.dynamic-loader-rightrailmf').css("display", "block");
    $('.error-message-rightrailmf').css("display", "none");
}

var rightRailMfErrorHandlingFuc = function () {
    $('.dynamic-loader-rightrailmf').css("display", "none");
    $(".error-message-rightrailmf p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.error-message-rightrailmf').css("display", "block");
}

function onChangeRightRailMf(selectClass) {
    selectedRightRailMf = selectClass;
    if($('.rightrailmf').length > 0 && $.trim($("div.rightrailmf").html()).length > 0) {
    loadmfFundsDetails(ajaxData_rightrailmf);
    }
}
selectboxChange(onChangeRightRailMf);