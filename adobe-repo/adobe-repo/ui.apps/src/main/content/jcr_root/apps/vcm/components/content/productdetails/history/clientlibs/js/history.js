var selectedClassHistory = loadClass;
var historyData;
var historyLables;
var historyAjaxData;
var history_component = function(labels, data){
        var CheckUser_history = sharedJS.getCookie("subdomain_user_entity_type");
        if (CheckUser_history !== "usaa_member") {
        	$('.alltime_container').hide();
        	$(".best-worst_container").hide();
        } else {
        	$('.alltime_container').show();
        	$(".best-worst_container").show();
        }
        var headRow = $("<tr/>"),
        tHead = $("<thead/>"),
        tBody = $("<tbody/>"),
        bestworstheadRow = $("<tr/>"),
        bestworsttHead = $("<thead/>"),
        bestworsttBody = $("<tbody/>");
        var apiClasses = [];
        data.forEach(element => {
         apiClasses.push(element.shareClass);
        });

        if (!(apiClasses.indexOf(selectedClassHistory) > -1)) {
            selectedClassHistory = apiClasses[0];
        }

        // bind labels
        $(".alltime_container .heading").append(labels.historyall);
        $(".best-worst_container .heading").append(labels.historybest);

        // bind data
        if (data.length) {
            data.map(item => {
                if (item.shareClass == selectedClassHistory) {
                    var historyTimeframe = labels.History.timeframe;
                    var historyNav = labels.History.nav;
                    var historyReturn = labels.History.return;
                    // all time data
                    var all_time_categories_labels = {
                        "high": labels.History.high,
                        "low": labels.History.low
                    }
                    var all_time_categories = Object.values(all_time_categories_labels);
                    var all_time_labels = [historyTimeframe, historyNav, historyTimeframe, historyNav];
                    var all_time_data_object = {
                        "high_timeframe": item.history.all_time_high_timeframe == null ? 'N/A': item.history.all_time_high_timeframe,
                        "high_nav": item.history.all_time_high_nav == null ? 'N/A' : "$" + parseFloat(item.history.all_time_high_nav).toFixed(2),
                        "low_timeframe": item.history.all_time_low_timeframe == null ? 'N/A': item.history.all_time_low_timeframe,
                        "low_nav": item.history.all_time_low_nav == null ? 'N/A' : "$" + parseFloat(item.history.all_time_low_nav).toFixed(2)
                    }
                    var all_time_data = Object.values(all_time_data_object);

                    // best worst data
                    var best_worst_categories_labels = {
                        "best_qtr": labels.History.bestquarter,
                        "worst_qtr": labels.History.worstquarter,
                        "best_year": labels.History.bestyear,
                        "worst_year": labels.History.worstyear
                    }
                    var best_worst_categories = Object.values(best_worst_categories_labels);
                    var best_worst_labels = [historyTimeframe, historyReturn, historyTimeframe, historyReturn, historyTimeframe, historyReturn, historyTimeframe, historyReturn];
                    var best_worst_data_object = {
                        "best_qtr_timeframe": item.history.all_time_best_qtr_timeframe == null ? 'N/A' :item.history.all_time_best_qtr_timeframe,
                        "best_qtr_return": item.history.all_time_best_qtr_returns == null ? 'N/A' : parseFloat(item.history.all_time_best_qtr_returns).toFixed(2) + "%",
                        "worst_qtr_timeframe": item.history.all_time_worst_qtr_timeframe == null ? 'N/A' : item.history.all_time_worst_qtr_timeframe,
                        "worst_qtr_return":item.history.all_time_worst_qtr_returns == null ? 'N/A' : parseFloat(item.history.all_time_worst_qtr_returns).toFixed(2) + "%",
                        "best_year_timeframe": item.history.all_time_best_year_timeframe == null ? 'N/A' : item.history.all_time_best_year_timeframe,
                        "best_year_return": item.history.all_time_best_year_returns == null ? 'N/A' : parseFloat(item.history.all_time_best_year_returns).toFixed(2) + "%",
                        "worst_year_timeframe": item.history.all_time_worst_year_timeframe == null ? 'N/A' : item.history.all_time_worst_year_timeframe,
                        "worst_year_return": item.history.all_time_worst_year_returns == null ? 'N/A' : parseFloat(item.history.all_time_worst_year_returns).toFixed(2) + "%"
                    }
                    var best_worst_data = Object.values(best_worst_data_object);

                    historyData = {
                        "all_time_data": {
                            "categories": all_time_categories,
                            "labels": all_time_labels,
                            "data": all_time_data
                        },
                        "best_worst_data": {
                            "categories": best_worst_categories,
                            "labels": best_worst_labels,
                            "data": best_worst_data
                        }
                    };

                    // all time table data
                    historyData.all_time_data.categories.forEach(ele => {
                        headRow.append(`<th scope='col' colspan="2">${ele}</th>`);
                    });
                    tHead.append(headRow);

                    var bodylabelRow = $("<tr/>");
                    historyData.all_time_data.labels.forEach(label => {
                        bodylabelRow.append(`<td>${label}</td>`);
                    });

                    var bodyDataRow = $("<tr/>");
                    historyData.all_time_data.data.forEach(ele => {
                        bodyDataRow.append(`<td>${ele}</td>`);
                    });
                    tBody.append(bodylabelRow).append(bodyDataRow);

                    $(".history-alltime-table").append(tHead).append(tBody);

                    // best-worst table data
                    historyData.best_worst_data.categories.forEach(ele => {
                        bestworstheadRow.append(`<th scope='col' colspan="2">${ele}</th>`);
                    });
                    bestworsttHead.append(bestworstheadRow);

                    var bestworstlabelRow = $("<tr/>");
                    historyData.best_worst_data.labels.forEach(label => {
                        bestworstlabelRow.append(`<td>${label}</td>`);
                    });

                    var bestworstDataRow = $("<tr/>");
                    historyData.best_worst_data.data.forEach(ele => {
                        bestworstDataRow.append(`<td>${ele}</td>`);
                    });
                    bestworsttBody.append(bestworstlabelRow).append(bestworstDataRow);

                    $(".history-best-worst-table").append(bestworsttHead).append(bestworsttBody);
                }
            })
        }
}

var historyErrorHandling = function () {
    $('.history-table .alltime_container').css("margin-bottom", "0");
    $('.history-alltime-table_wrapper').css("display", "none");
	$('.history-best-worst-table_wrapper').css("display", "none");
	$(".alltime_container .heading").append(ajaxData_productLabels.historyall);
    $(".best-worst_container .heading").append(ajaxData_productLabels.historybest);
	$(".error-message-history p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.error-message-history').css("display", "block");
}

$(document).ready(function() {
    $(".history-best-worst-table_wrapper").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".history-best-worst-table_wrapper").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
    
    $(".history-alltime-table_wrapper").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".history-alltime-table_wrapper").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});