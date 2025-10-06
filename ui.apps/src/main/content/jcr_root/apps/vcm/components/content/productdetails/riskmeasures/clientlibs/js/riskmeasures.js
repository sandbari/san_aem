var selectedRiskMeasures = loadClass;
var risk_measures = function(labelsdata, data, selectedTab){
    var riskTableLabels;
    var riskTableData;
    var selectedTabData;
    var headRow = $("<tr/>");
    var tHead = $("<thead/>");
    var tBody = $("<tbody/>");
    var apiClasses = [];
    data.forEach(element => {
     apiClasses.push(element.share_class);
    });

    if (!(apiClasses.indexOf(selectedRiskMeasures) > -1)) {
        selectedRiskMeasures = apiClasses[0];
    }
    
    // bind labels
   $(".risk-measures-title").html("").prepend(labelsdata.riskmeasures);
   $(".risk-measures-asof-label").html("").append(labelsdata.asof);
   $(".risk-measures-component .one-year-tab a").html("").append(labelsdata.oneyear);
   $(".risk-measures-component .three-year-tab a").html("").append(labelsdata.threeyear);
   $(".risk-measures-component .five-year-tab a").html("").append(labelsdata.fiveyear);
   $(".risk-measures-component .ten-year-tab a").html("").append(labelsdata.tenyear);
   riskTableLabels = Object.values(labelsdata.RiskMeasures);
       
    // bind data
    if(data.length){
        data.map(item => {
            if (item.share_class == selectedRiskMeasures) {
                if (selectedTab == "one_year") {
                    selectedTabData = item.one_year;
                }else if(selectedTab == "three_year"){
                    selectedTabData = item.three_year;
                }else if (selectedTab == "five_year"){
                    selectedTabData = item.five_year;
                }else {
                    selectedTabData = item.ten_year;
                }
                
                $(".risk-measures-asof-value").html("").append(item.as_of_date);
                
                headRow.append(`<th scope='col'><span class="sr-only">Fund or Benchmark name</span></th>`);
                 riskTableLabels.map(ele => {
                     headRow.append(`<th scope='col'>${ele}</th>`);
                 });
                 tHead.append(headRow);

                selectedTabData.data.map(item => {
                    var dataObject = {
                            "alpha": item.alpha == null ? "N/A" : parseFloat(item.alpha).toFixed(2) + "%",
                            "beta": item.beta == null ? "N/A" : parseFloat(item.beta).toFixed(2),
                            "r2": item.r2 == null ? "N/A" : parseFloat(item.r2).toFixed(2) + "%",
                            "sd": item.standard_deviation == null ? "N/A" : parseFloat(item.standard_deviation).toFixed(2) + "%",
                            "sr": item.sharpe_ratio == null ? "N/A" : parseFloat(item.sharpe_ratio).toFixed(2),
                            "ir": item.information_ratio == null ? "N/A" : parseFloat(item.information_ratio).toFixed(2)
                    };
                    var integerArray = Object.values(dataObject);
                    var bodyRow = $("<tr/>");
                    var actualFundName;
                        var benchMarkFundName;
                        if(item.entity_long_name != undefined){
                            if(selectedRiskMeasures != "N/A"){
                                actualFundName = item.entity_long_name + " Class " + selectedRiskMeasures;
                            } else {
                                actualFundName = item.entity_long_name;
                            }


                        }if(item.benchmark_name != undefined){

                            benchMarkFundName = item.benchmark_name;
                        }
                        var fundName = actualFundName || benchMarkFundName;
                        var name = fundName;
                    bodyRow.append($(`<td class='text-left'>${name}</td>`));
                    integerArray.map(ele => {
                        bodyRow.append($(`<td>${ele}</td>`));
                    })
                     tBody.append(bodyRow);
                });
                 $(".risk-table").html("").append(tHead).append(tBody);
            }
        });
    }

}

function onChangeRiskClass(selectClass){
	var riskActiveEle = $(".time-tabs.active");
    if (riskActiveEle.hasClass("three-year-tab")) {
        selectedTab = "three_year";
    } else if(riskActiveEle.hasClass("five-year-tab")) {
        selectedTab = "five_year";
    }else{
        selectedTab = "ten_year";
    }
    selectedRiskMeasures = selectClass;

    ajaxData_riskmeasures.map(item => {
        if (item.share_class == selectedRiskMeasures ) {
           if (item.five_year.data.length < 2 && item.three_year.data.length < 2 && item.ten_year.data.length < 2) {
                $('.risk-measures-component').hide();
                if (item.one_year.data.length) {
                    $('.risk-measures-component').show();
                    $('.one-year-tab').show();
                    $('.one-year-tab').addClass('active');
                    riskActiveTab = "one_year";
                }
            } else {
                 $('.risk-measures-component').show();
                 $('.one-year-tab').removeClass('active');
                 $('.one-year-tab').hide();
            }
            if (item.three_year.data.length < 2) {
                $('.three-year-tab').hide();
                if (item.five_year.data.length > 1) {
                    $('.five-year-tab').addClass('active');
                    riskActiveTab = "five_year";
                }
            } else {
                $('.three-year-tab').show();
                $('.five-year-tab').removeClass('active');
                $('.ten-year-tab').removeClass('active');
                riskActiveTab = "three_year";
                $('.three-year-tab').addClass('active');
            }
            if (item.five_year.data.length < 2) {
                $('.five-year-tab').hide();
                if (item.ten_year.data.length >= 1 && item.three_year.data.length <= 0) {
                    $('.ten-year-tab').addClass('active');
                    riskActiveTab = "ten_year";
                }
            } else {
                $('.five-year-tab').show();
                if (item.three_year.data.length < 2) {
                    $('.three-year-tab').removeClass('active');
                    $('.ten-year-tab').removeClass('active');
                    riskActiveTab = "five_year";
                    $('.five-year-tab').addClass('active');
                }
            }
            if (item.ten_year.data.length < 2) {
                $('.ten-year-tab').hide();
            } else {
                $('.ten-year-tab').show();
                if (item.three_year.data.length < 2 && item.five_year.data.length < 2) {
                    $('.three-year-tab').removeClass('active');
                    $('.five-year-tab').removeClass('active');
                    riskActiveTab = "ten_year";
                    $('.ten-year-tab').addClass('active');
                }
            }
        }
    })

    if($('.riskmeasures').length > 0 && $.trim($("div.riskmeasures").html()).length > 0) {
    risk_measures(ajaxData_productLabels, ajaxData_riskmeasures, selectedTab);
    }
}

selectboxChange(onChangeRiskClass);

var beforeRiskMeasuresLoadFuc = function () {
    $('.dynamic-loader-riskmeasures').css("display", "block");
    $('.error-message-riskmeasures').css("display", "none");
}

var riskMeasuresErrorHandlingFuc = function () {
	$('.risk-measures-component').css("visibility", "visible");
	$(".risk-measures-title").html("").prepend(ajaxData_productLabels.riskmeasures);
	$(".error-message-riskmeasures p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-riskmeasures').css("display", "none");
    $('.error-message-riskmeasures').css("display", "block");
}

$(document).ready(function() {

    setTimeout(function () {
        $('.risk-measures-data .time-tabs:hidden').addClass('hiddenTabs');
        $('.risk-measures-data .risk-time-tabs .time-tabs:not(".hiddenTabs")').addClass('visibleTabs');
         $('.risk-time-tabs .visibleTabs').find('a').attr('aria-selected', 'false');
        $('.risk-time-tabs .visibleTabs').first().find('a').attr('aria-selected', 'true');
        var totalriskDetailTabs = $('.risk-time-tabs .visibleTabs').length;
        $('.risk-time-tabs .visibleTabs a').attr('aria-setsize', totalriskDetailTabs);
        $('.risk-time-tabs .visibleTabs').each(function(el, list) {
            var $thisRiskDtlList = $(list);
            var $posRiskDtlinset = el+1;
            $thisRiskDtlList.find('a').attr('aria-posinset', $posRiskDtlinset);
        });
    }, 3000);

	$(".risk-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".risk-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
    // annual data  monthly quarterly tabs
    $(".time-tabs").click(function() {
        var selectedTab;

        $(".time-tabs").removeClass('active');
        $(this).addClass('active');
        $(".risk-time-tabs .time-tabs a").attr('aria-selected', 'false');
        $(this).find('a').attr('aria-selected', 'true');
        var riskActiveEle = $(".time-tabs.active");
        if (riskActiveEle.hasClass("one-year-tab")) {
            selectedTab = "one_year";
        } else if (riskActiveEle.hasClass("three-year-tab")) {
            selectedTab = "three_year";
        } else if(riskActiveEle.hasClass("five-year-tab")) {
            selectedTab = "five_year";
        }else{
            selectedTab = "ten_year";
        }
        
        risk_measures(ajaxData_productLabels, ajaxData_riskmeasures, selectedTab);
    });
});