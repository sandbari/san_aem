var selectedCalenderyear = loadClass;
var incept_date;
var calendar_returns = function(labels, data){

    var categories = [];
    var seriesData = [];
    var calendarFinalData;
    var headRow = $("<tr/>");
    var tHead = $("<thead/>");
    var tBody = $("<tbody/>");
    var apiClasses = [];
    data.forEach(element => {
     apiClasses.push(element.share_class);
    });

    if (!(apiClasses.indexOf(selectedCalenderyear) > -1)) {
        selectedCalenderyear = apiClasses[0];
    }
	if ($("#fund-category").val() == "MF") {
		getInceptDate(ajaxData_rightrailmf);
    } else if ($("#fund-category").val() == "ETF") {
			getInceptDate(ajaxData_rightrailsummary);
    }
    //binding data

    if(data.length){
        data.map(item => {
            if (item.share_class == selectedCalenderyear) {
                $(".calendar-table").html("");
                 $(".calendar-returns.header-label").html(labels.calendaryearreturns);
                 $(".calender-returns-asof-label").html(labels.asof);
                 $(".calender-returns-asof-value").html(item.as_of_date);
            	var asofDate = new Date(item.as_of_date);
                    var currentYear = asofDate.getFullYear();
                    var yearRange, incetYear, year, yeardiff;
                    year = new Date(incept_date).getFullYear();
                    yeardiff = currentYear - year;
                    if (yeardiff < 10) {
                        yearRange = yeardiff;
                    }
                    else if (yeardiff >= 10) {
                        yearRange = 10;
                    }
                // for categories ()
                if (item.as_of_date == " " || item.as_of_date == null || item.as_of_date == undefined) {
                    $('.calendar-returns-component').hide();
                } else {
                    $('.calendar-returns-component').show();
                var asofDate = new Date(item.as_of_date);
                if (yearRange > 1) {
                    if (currentYear = asofDate.getFullYear(), yearRange, categories.length < yearRange - 1) {
                        for (var a = 0; a < yearRange; a++)
                            categories.push(currentYear - a);
                    }
                } else {
                    categories.push(currentYear);
                }
                item.calendar_year_returns.map(item => {
                    var dataObject = [];
                    var initialDataObject = {
                        "prev_year": item.prev_year_performance == null ? "N/A" : parseFloat(item.prev_year_performance),
                        "prev_year_minus1": item.prev_minus1_performance == null ? "N/A" : parseFloat(item.prev_minus1_performance),
                        "prev_year_minus2": item.prev_minus2_performance == null ? "N/A" : parseFloat(item.prev_minus2_performance),
                        "prev_year_minus3": item.prev_minus3_performance == null ? "N/A" : parseFloat(item.prev_minus3_performance),
                        "prev_year_minus4": item.prev_minus4_performance == null ? "N/A" : parseFloat(item.prev_minus4_performance),
                        "prev_year_minus5": item.prev_minus5_performance == null ? "N/A" : parseFloat(item.prev_minus5_performance),
                        "prev_year_minus6": item.prev_minus6_performance == null ? "N/A" : parseFloat(item.prev_minus6_performance),
                        "prev_year_minus7": item.prev_minus7_performance == null ? "N/A" : parseFloat(item.prev_minus7_performance),
                        "prev_year_minus8": item.prev_minus8_performance == null ? "N/A" : parseFloat(item.prev_minus8_performance),
                        "prev_year_minus9": item.prev_minus9_performance == null ? "N/A" : parseFloat(item.prev_minus9_performance)
                    }
                    var values = Object.values(initialDataObject);
                            for (var i = 0; i < yearRange; i++) {
                                dataObject.push(values[i]);
                            }
                    var actualFundName;
                        var benchMarkFundName;
                        if(item.entity_long_name != undefined){
                            if(selectedCalenderyear != "N/A"){
                                actualFundName = item.entity_long_name + " Class " + selectedCalenderyear;
                                var span1 = document.createElement("span");
                                span1 = actualFundName;
                                actualFundName = span1;
                                var parsedVal = $.parseHTML(actualFundName);
                                actualFundName = parsedVal[0].wholeText;
                            } else {
                                actualFundName = item.entity_long_name;
                                var span1 = document.createElement("span");
                                span1 = actualFundName;
                                actualFundName = span1;
                                var parsedVal = $.parseHTML(actualFundName);
                                actualFundName = parsedVal[0].wholeText;
                            }

                        }if(item.benchmark_name != undefined){

                            benchMarkFundName = item.benchmark_name;
                            var span1 = document.createElement("span");
                            span1 = benchMarkFundName;
                            benchMarkFundName = span1;
                            var parsedVal = $.parseHTML(benchMarkFundName);
                            benchMarkFundName = parsedVal[0].wholeText;
                        }
                        var fundName = actualFundName || benchMarkFundName;
                    var formattedObj = {
                        "name": fundName,
                        "data": dataObject
                    }
                    seriesData.push(formattedObj);
                });
            }
            }
        });
    }

    calendarFinalData = {
        "title": {
            "text": ""
        },
        "displayTable": true,
        "chart": {
            "type": "column"
        },
        "xAxis": {
            "categories": categories
        },
        "yAxis": {
            "title": {
                "text": ""
            },
            "labels": {
            	"formatter": function() {
                    return this.value + "%";
                }
            }
        },
        "plotOptions": {
            // "series": {
            //     "events": {
            //         legendItemClick: function() {
            //             return false;
            //         }
            //     }
            // }
        },
        "credits": {
            "enabled": false
        },
        "tooltip": {
            "valueSuffix": "%"
        },
        "legend": {
            "layout": "vertical",
           " align": 'center',
            "symbolWidth": 14,
            "symbolHeight": 14,
            "symbolRadius": 0,
            "verticalAlign": 'bottom',
            "itemMarginTop": 10,
            "float": 'left',
            "x":'left'
        },
        "series": seriesData
    };
    // highchart
    Highcharts.setOptions({
        colors: colorList
    });
	Highcharts.chart('product-chart-calender', calendarFinalData);
    // table
    headRow.append(`<th scope='col'><span class="sr-only">Fund or Benchmark name</span></th>`);
    calendarFinalData.xAxis.categories.map(ele => {
        headRow.append(`<th scope='col'>${ele}</th>`);
    });
    tHead.append(headRow);
    calendarFinalData.series.map(ele => {
        var bodyRow = $("<tr/>");
        bodyRow.append($(`<td class='text-left'>${ele.name}</td>`));
        ele.data.map((dataItem) => {
            bodyRow.append($(`<td>${parseFloat(dataItem).toFixed(2)}</td>`));
        });
        tBody.append(bodyRow);
    });
    $(".calendar-table").append(tHead).append(tBody);
}

function onChangeCalendarClass(selectClass){
	selectedCalenderyear = selectClass;
	if($('.calendarreturns').length > 0 && $.trim($("div.calendarreturns").html()).length > 0) {
    calendar_returns(ajaxData_productLabels, ajaxData_calendarreturns);
	}
}
selectboxChange(onChangeCalendarClass);


var beforeCalenderReturnsLoadFuc = function () {
    $('.dynamic-loader-calenderreturns').css("display", "block");
    $('.error-message-calenderreturns').css("display", "none");
}

var calenderReturnsErrorHandlingFuc = function () {
	$('.calendar-returns-component').css("visibility", "visible");
	$(".calendar-returns.header-label").html(ajaxData_productLabels.calendaryearreturns);
	$(".error-message-calenderreturns p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-calenderreturns').css("display", "none");
    $('.error-message-calenderreturns').css("display", "block");
}

function getInceptDate(data) {
    if ($("#fund-category").val() == "MF") {
        data.map(item => {
            if (item.shareClass == selectedCalenderyear) {
                incept_date = item.incept_date;
            }
    	})
    } else if ($("#fund-category").val() == "ETF") {
        incept_date = data.inception_date;
    }
};

$(document).ready(function() {
    $(".calendar-list-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".calendar-list-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));

    // Toggle listview and chart view for annualized returns
    $(".calendar-toggle-view").click(function() {
        $(".calendar-toggle-view").removeClass('active');
        $(this).addClass('active');
        $(".calendar-toggle-view.active").attr("tabindex","0");
        $(".calendar-toggle-view.active").attr("aria-checked","true");
        $(".calendar-toggle-view:not(.active)").attr("tabindex","-1");
        $(".calendar-toggle-view:not(.active)").attr("aria-checked","false");
        if ($(this).hasClass('table-view')) {
            $('.calendar-chart-data').addClass('hidden');
            $('.calendar-list-data').removeClass('hidden');
            $(this).attr('aria-checked', 'false');
            $(this).parent('div').find('.table-view').attr('aria-checked', 'true');
        } else {
            $('.calendar-list-data').addClass('hidden');
            $('.calendar-chart-data').removeClass('hidden');
            $(this).attr('aria-checked', 'false');
            $(this).parent('div').find('.chart-view').attr('aria-checked', 'true ');
        }
    });


    $('.calendar-returns-component a.table-view, .calendar-returns-component  a.chart-view').on('keydown', function(e){
        if((e.which==40) || (e.which==38)){
            if ($(this).hasClass('chart-view')  ) {
                $('.calendar-chart-data').addClass('hidden');
                $('.calendar-list-data').removeClass('hidden');
            } else {
                $('.calendar-list-data').addClass('hidden');
                $('.calendar-chart-data').removeClass('hidden');
            }
        }
    })
    // Tooltip info render
    $('[data-toggle="tooltip"]').tooltip();

});