var selectedClassFundStats = loadClass;
var fund_statistics = function(labels,data){
    var apiClasses = [];
    data.forEach(element => {
        apiClasses.push(element.shareClass);
    });

    if (!(apiClasses.indexOf(selectedClassFundStats) > -1)) {
        if (selectedClassFundStats == "Member") {
    		selectedClassFundStats = "I";
        } else {
        	selectedClassFundStats = apiClasses[0];
        }
    }

	$(".fund-stats-component h3.heading .fund-statistics.header-label").html("").append(labels.fundstatistics);

	jsonData = data.filter((e) => {
        return (e.shareClass === selectedClassFundStats);
    });

    let fundStatsLabel = labels.FundStats;
    const getFundStatsLabel = (label) => {
        let fsLabel = label;
        if (fundStatsLabel.hasOwnProperty(label)) {
            fsLabel = fundStatsLabel[label]
        }
        return fsLabel
    }

    jsonData.forEach((item) => {
        $(".fund-statistics-asof").html("").append(item.as_of);
        let fundStats = item.fundStatistics;
        let fundStatsVal = {}

        fundStats.forEach((fundStatistics) => {
            let fundStatistics_keys = Object.keys(fundStatistics);
            fundStatistics_keys.forEach((ele) => {
                if (fundStatsVal[ele] == undefined) {
                    fundStatsVal[ele] = {}
                }
                let fundStatsKeys = Object.keys(fundStatistics[ele])
                fundStatsKeys.forEach((fundStatsKey) => {
                    fundStatsVal[ele][fundStatsKey] = fundStatistics[ele][fundStatsKey] //first col title
                })
            })
        });
        // thead
        let all_fundStats = Object.keys(fundStatsVal);
        let all_fundStatsData = [...new Set(all_fundStats.map(fsVal => Object.keys(fundStatsVal[fsVal])).flat())]
        let theads = all_fundStats.map((headRow) => {
            return `<th>${headRow}</th>`;
        })

        let headRowTitle = labels.FundStats.FundStatsTitle
        let tableHeader = `<thead><tr><th>${headRowTitle}</th>${theads.join("")}</tr></thead>`;

        //tbody
        let tbodies = []
        all_fundStatsData.forEach((fundStatsKey) => {
            let fundStatslabel = getFundStatsLabel(fundStatsKey);
            let fundStats_td = all_fundStats.map(fundStatisticsVal => {
                var tdValue = fundStatsVal[fundStatisticsVal][fundStatsKey];
                if ((isNaN(tdValue)) || (tdValue == '')) {
                    var tdVal = "N/A"
                } else {
                    var tdVal = parseFloat(tdValue).toFixed(2);
                }
                return `<td>${tdVal}</td>`
            })
            tbodies.push(`<tr><td>${fundStatslabel}</td>${fundStats_td.join("")}</tr>`)
        })
        let tableBody = `<tbody>${tbodies.join("")}</tbody>`
        let tableStruct = tableHeader + tableBody
        $('.fund-stats-table').html(tableStruct);
        hideGradient();
    })
}

function onChangeRegAlloClass(selectClass) {
    selectedClassFundStats = selectClass;
    fund_statistics(ajaxData_productLabels,ajaxData_fundstatistics);
}
selectboxChange(onChangeRegAlloClass);

var beforeFundStatisticsFuc = function () {
    $('.dynamic-loader-fundstatistics').css("display", "block");
    $('.error-message-fundstatistics').css("display", "none");
}

var fundStatisticsErrorHandlingFuc = function () {
	$('.fund-stats-component').css("visibility", "visible");
	$(".fund-stats-component h3.heading .fund-statistics.header-label").append(ajaxData_productLabels.fundstatistics);
	$(".error-message-fundstatistics p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-fundstatistics').css("display", "none");
    $('.error-message-fundstatistics').css("display", "block");
}


$(document).ready(function() {

	 $(".fund-stats-table-wrapper").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
     $(".fund-stats-table-wrapper").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));

});
