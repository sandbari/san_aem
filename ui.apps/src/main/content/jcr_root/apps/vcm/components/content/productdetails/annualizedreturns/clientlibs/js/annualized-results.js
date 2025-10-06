var selectedAnnualReturns = loadClass;
var annualized_returns = function(labels, data, activeTab){
		var userType = sharedJS.getCookie("subdomain_user_entity_type");
        var responseData;
        var labelData;
        var annualFinal;
        var asofdate
        var labelsData;
        var selectedTabData;
        var seriesData = [];
        var headRow = $("<tr/>");
        var tHead = $("<thead/>");
        var tBody = $("<tbody/>");
        var fundType = $("#fund-category").val();
        var annualDataObject;
        var userData;
        var seriesData_table = [];
        var annualDataObject_table;
        var apiClasses = [];
        data.forEach(element => {
         apiClasses.push(element.share_class);
        });

        if (!(apiClasses.indexOf(selectedAnnualReturns) > -1)) {
                selectedAnnualReturns = apiClasses[0];
        }
       // bind labels
       $(".annualized-returns-title").html("").prepend(labels.annualreturns);
       $(".annualized-returns-asof-label").html("").append(labels.asof);
       $(".annual-returns-component .monthly-tab a").html("").append(labels.annualmonthly);
       $(".annual-returns-component .quarterly-tab a").html("").append(labels.annualquarterly);
       
       var annual_mf_labels = {
               "yeartodate": labels.AnnualizedReturns.yeartodate,
               "oneyearlabel": labels.AnnualizedReturns.oneyearlabel,
               "threeyearlabel": labels.AnnualizedReturns.threeyearlabel,
               "fiveyearlabel": labels.AnnualizedReturns.fiveyearlabel,
               "tenyearlabel": labels.AnnualizedReturns.tenyearlabel,
               "inception": labels.AnnualizedReturns.inception
          }
          
          var annual_etf_labels = {
               "threemonthlabel": labels.AnnualizedReturns.threemonthlabel,
               "yeartodate": labels.AnnualizedReturns.yeartodate,
               "oneyearlabel": labels.AnnualizedReturns.oneyearlabel,
               "threeyearlabel": labels.AnnualizedReturns.threeyearlabel,
               "fiveyearlabel": labels.AnnualizedReturns.fiveyearlabel,
               "inception": labels.AnnualizedReturns.inception
          }
       
       if(fundType == "ETF"){
           labelsData = Object.values(annual_etf_labels);
       }else{
           labelsData = Object.values(annual_mf_labels);
       }

       // bind data
       if(data.length){
        data.map(item => {
            if (item.share_class == selectedAnnualReturns) {
                if(activeTab == "monthly"){
                    selectedTabData = item.monthly;
                }else{
                    selectedTabData = item.quarterly;
                }
                // console.log(selectedTabData);
                asofdate = selectedTabData.as_of_date;
                $(".annualized-returns-asof-value").html("").append(selectedTabData.as_of_date);
                if(fundType != "ETF"){
                	if (userType == "usaa_member") {
                        /*userData = selectedTabData.data.filter(userTypeData => {
                            var dataUser = Object.keys(userTypeData);
                            if (dataUser.includes("oneyear_nav")) {
                                return userTypeData;
                            }
                        })*/
                        if (item.share_class != "A" && item.share_class != "C") {
                            userData = selectedTabData.data.filter(userTypeData => {
                                var dataUser = Object.keys(userTypeData);
                                if (dataUser.includes("oneyear_nav")) {
                                    return userTypeData;
                                }
                            })
                        }
                        else {
                            userData = selectedTabData.data;
                        }
                    }else{
                    	if (item.share_class != "A" && item.share_class != "C") {
                            userData = selectedTabData.data.filter(userTypeData => {
                                var dataUser = Object.keys(userTypeData);
                                if (dataUser.includes("oneyear_nav")) {
                                    return userTypeData;
                                }
                            })
                        } 
                    	else {
                            userData = selectedTabData.data;
                        }
                    }
                }
                else {
                    userData = selectedTabData.data;
                }
                userData.map(item => {
                	var ytd_nav_table = item.ytd_nav === null ? "N/A" : parseFloat(item.ytd_nav);
                    var ytd_mop_table = item.ytd_mop === null ? "N/A" : parseFloat(item.ytd_mop);
                    var ytd_nav = ytd_nav_table;
                    var ytd_mop = ytd_mop_table;
                    var threemonth_nav = item.threemonth_nav === null ? "N/A" : parseFloat(item.threemonth_nav);
                    var threemonth_mop = item.threemonth_mop === null ? "N/A" : parseFloat(item.threemonth_mop);
                    var oneyear_nav = item.oneyear_nav === null ? "N/A" : parseFloat(item.oneyear_nav);
                    var oneyear_mop = item.oneyear_mop === null ? "N/A" : parseFloat(item.oneyear_mop);
                    var threeyear_nav = item.threeyear_nav === null ? "N/A" : parseFloat(item.threeyear_nav);
                    var threeyear_mop = item.threeyear_mop === null ? "N/A" : parseFloat(item.threeyear_mop);
                    var fiveyear_nav = item.fiveyear_nav === null ? "N/A" : parseFloat(item.fiveyear_nav);
                    var fiveyear_mop = item.fiveyear_mop === null ? "N/A" : parseFloat(item.fiveyear_mop);
                    var tenyear_nav = item.tenyear_nav === null ? "N/A" : parseFloat(item.tenyear_nav);
                    var tenyear_mop = item.tenyear_mop === null ? "N/A" : parseFloat(item.tenyear_mop);
                    var si_nav = item.since_inception_nav === null ? "N/A" : parseFloat(item.since_inception_nav);
                    var si_mop = item.since_inception_mop === null ? "N/A" : parseFloat(item.since_inception_mop);
                	var navData = Object.keys(item);
                	if(fundType == "ETF"){
                        annualDataObject = {
                            "three_month": item.threemonth_mop || item.threemonth_nav,
                            "ytd": item.ytd_mop || item.ytd_nav,
                            "one_year": item.oneyear_mop || item.oneyear_nav,
                            "three_year": item.threeyear_mop || item.threeyear_nav,
                            "five_year": item.fiveyear_mop || item.fiveyear_nav,
                            "si":  item.since_inception_mop || item.since_inception_nav
                        }
                        annualDataObject_table = {

                        		"three_month": threemonth_mop || threemonth_nav,
                        		"ytd": ytd_mop || ytd_nav,
                                "one_year": oneyear_mop || oneyear_nav,
                                "three_year": threeyear_mop || threeyear_nav,
                                "five_year": fiveyear_mop || fiveyear_nav,
                                "si": si_mop || si_nav
                            }
                    }else {
                       annualDataObject = {
                            "ytd": item.ytd_mop || item.ytd_nav,
                            "one_year": item.oneyear_mop || item.oneyear_nav,
                            "three_year": item.threeyear_mop || item.threeyear_nav,
                            "five_year": item.fiveyear_mop || item.fiveyear_nav,
                            "ten_year": item.tenyear_mop || item.tenyear_nav,
                            "si":  item.since_inception_mop || item.since_inception_nav
                        }
                       annualDataObject_table = {

                    		   "ytd": ytd_mop || ytd_nav,
                               "one_year": oneyear_mop || oneyear_nav,
                               "three_year": threeyear_mop || threeyear_nav,
                               "five_year": fiveyear_mop || fiveyear_nav,
                               "ten_year": tenyear_mop || tenyear_nav,
                               "si": si_mop || si_nav

                           }
                    }
                    
                    var interValues = Object.values(annualDataObject).map(item => {
                    	var errorSen;
                        if (item == null) {
                            errorSen = 'N/A';
                        } else errorSen = parseFloat(item)
                        return errorSen;
                    })
                    
                    if(interValues.every(item => item == "N/A")){
                        interValues = [];
                    } else {
                        interValues = interValues;
                    }

                    for (var key in annualDataObject_table) {
                        if (annualDataObject_table[key] == "N/A") {
                            annualDataObject_table[key] = "N/A";
                        } else {
                            annualDataObject_table[key] = parseFloat(annualDataObject_table[key]).toFixed(2);
                        }
                        if (annualDataObject_table["ytd"] == "N/A") {
                            annualDataObject_table["ytd"] = "N/A";
                        } else {
                            annualDataObject_table["ytd"] = parseFloat(annualDataObject_table["ytd"]).toFixed(2);
                            break;
                        }
                    }
                    var actualFundName;
                       var benchMarkFundName;
                        if (item.entity_long_name != undefined) {
                            if(navData.includes("ytd_mop")) {
                                if(selectedAnnualReturns != "N/A"){
                                    actualFundName = `${item.entity_long_name}  Class   ${selectedAnnualReturns}  @ Load (%) `;
                                    var span1 = document.createElement("span");
                                    span1 = actualFundName;
                                    actualFundName = span1;
                                    var parsedVal = $.parseHTML(actualFundName);
                                    actualFundName = parsedVal[0].wholeText;
                                } else {
                                    actualFundName = `${item.entity_long_name}  @ Market Price (%) `;
                                    var span1 = document.createElement("span");
                                    span1 = actualFundName;
                                    actualFundName = span1;
                                    var parsedVal = $.parseHTML(actualFundName);
                                    actualFundName = parsedVal[0].wholeText;
                                }
                            }
                            else{
                                if(selectedAnnualReturns != "N/A"){
                                    actualFundName = `${item.entity_long_name} Class  ${selectedAnnualReturns}  @ NAV (%) `;
                                    var span1 = document.createElement("span");
                                    span1 = actualFundName;
                                    actualFundName = span1;
                                    var parsedVal = $.parseHTML(actualFundName);
                                    actualFundName = parsedVal[0].wholeText;
                                } else {
                                    actualFundName = `${item.entity_long_name}  @ NAV (%) `;
                                    var span1 = document.createElement("span");
                                    span1 = actualFundName;
                                    actualFundName = span1;
                                    var parsedVal = $.parseHTML(actualFundName);
                                    actualFundName = parsedVal[0].wholeText;
                                }
                            }

                        }if(item.benchmark_name != undefined){
                            benchMarkFundName = item.benchmark_name;
                            var span1 = document.createElement("span");
                            span1 = benchMarkFundName;
                            benchMarkFundName = span1;
                            var parsedVal = $.parseHTML(benchMarkFundName);
                            benchMarkFundName = parsedVal[0].wholeText + " (%)";
                       }
                    var fundName = actualFundName || benchMarkFundName;
                    var formattedObj = {
                        "name": fundName,
                        "data": interValues
                    }
                    var formattedObj_table = {
                            "name": fundName,
                            "data": annualDataObject_table
                        }
                    seriesData.push(formattedObj);
                    seriesData_table.push(formattedObj_table);

                });

            }
        });
       }

        annualFinal  = {
            "title": {
                "text": ""
            },
            "displayTable": true,
            "chart": {
                "type": "column",
                "events": {
                    "load": function() {
                      this.series.forEach(function(s) {
                        s.update({
                          showInLegend: s.points.length
                        });
                      });
                    }
                  }
            },
            "xAxis": {
                "categories": labelsData
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
                	"x": "left"
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
            "series": seriesData,
            "asof": asofdate
        };

        // highchart
        Highcharts.setOptions({
            colors: colorList
        });
        
		Highcharts.chart('product-chart-annual', annualFinal);

        // table
		headRow.append(`<th scope='col'>&nbsp;<span class="sr-only">Fund or Benchmark name</span></th>`);
        annualFinal.xAxis.categories.map(ele => {
            headRow.append(`<th scope='col'>${ele}</th>`);
        });
        tHead.append(headRow);
        seriesData_table.map(ele => {
            var bodyRow = $("<tr/>");
            bodyRow.append($(`<td class='text-left'>${ele.name}</td>`));
            Object.values(ele.data).map(item => {
                if(item == "N/A"){
                    bodyRow.append($(`<td>${item}</td>`));
                }
                else{
                    bodyRow.append($(`<td>${parseFloat(item).toFixed(2)}</td>`));
                }
            });
            tBody.append(bodyRow);
        });

        $(".annualized-table").html("").append(tHead).append(tBody);
    }
function onChangeAnnualReturnsClass(selectClass){
	let activeTab;
    let activeEle = $(".annualized-time-tabs.active");
    if (activeEle.hasClass("monthly-tab")) {
        activeTab = "monthly";
    } else {
        activeTab = "quarterly";
    }
    selectedAnnualReturns = selectClass;
    if($('.annualizedreturns').length > 0 && $.trim($("div.annualizedreturns").html()).length > 0) {
    annualized_returns(ajaxData_productLabels, ajaxData_annualizedreturns, activeTab);
    }

}

selectboxChange(onChangeAnnualReturnsClass);

var beforeAnnualReturnsLoadFuc = function () {
    $('.dynamic-loader-annualreturns').css("display", "block");
    $('.error-message-annualreturns').css("display", "none");
}

var annualreturnsErrorHandlingFuc = function () {
	$('.annual-returns-component').css("visibility", "visible");
	$(".annualized-returns-title").html("").prepend(ajaxData_productLabels.annualreturns);
	$(".error-message-annualreturns p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-annualreturns').css("display", "none");
    $('.error-message-annualreturns').css("display", "block");
    $('.know-about-tooltip').attr('aria-label', 'Know more about '+$(".annualized-returns-title").html());
}

$(document).ready(function() {

    // Toggle listview and chart view for annualized returns
    $(".annual-toggle-view").click(function() {
        $(".annual-toggle-view").removeClass('active');
        $(this).addClass('active');
        $(".annual-toggle-view.active").attr("tabindex","0");
        $(".annual-toggle-view.active").attr("aria-checked","true");
        $(".annual-toggle-view:not(.active)").attr("tabindex","-1");
        $(".annual-toggle-view:not(.active)").attr("aria-checked","false");

        if ($(this).hasClass('list-view')) {
            $('.annual-chart-data').addClass('hidden');
            $('.annual-list-data').removeClass('hidden');
            $(this).attr('aria-checked', 'false');
            $(this).parent('div').find('.list-view').attr('aria-checked', 'true');
        } else {
            $('.annual-list-data').addClass('hidden');
            $('.annual-chart-data').removeClass('hidden');
            $(this).attr('aria-checked', 'false');
            $(this).parent('div').find('.chart-view').attr('aria-checked', 'true ');
        }
    });

    $('.annual-returns-component a.list-view, .annual-returns-component  a.chart-view').on('keydown', function(e){
        if((e.which==40) || (e.which==38)){
            if ($(this).hasClass('chart-view')  ) {
                $('.annual-chart-data').addClass('hidden');
                $('.annual-list-data').removeClass('hidden');
            } else {
                $('.annual-list-data').addClass('hidden');
                $('.annual-chart-data').removeClass('hidden');
            }
        }
    })

    var totalDetailTabs = $('.annual-time-tabs li').length;
    $('.annual-time-tabs li a').attr('aria-setsize', totalDetailTabs);


    $('.annual-time-tabs li').each(function(el, list) {
        var $thisDtlList = $(list);
        var $posDtlinset = el+1;
        $thisDtlList.find('a').attr('aria-posinset', $posDtlinset);

    });

    // annual data  monthly quarterly tabs
    $(".annualized-time-tabs").click(function() {
        //var selectedTab = $(this).text().toLowerCase();
        let selectedTab;
        let currentEle = $(this);
        if (currentEle.hasClass("monthly-tab")) {
            selectedTab = "monthly";
        } else {
            selectedTab = "quarterly";
        }
        $(this).parents('.annual-time-tabs').find('.annualized-time-tabs a').attr('aria-selected', 'false');
        $(this).find('a').attr('aria-selected', 'true');
        $(".annualized-time-tabs").removeClass('active');
        $(this).addClass('active');

        annualized_returns(ajaxData_productLabels, ajaxData_annualizedreturns, selectedTab);
    });
    $(".annual-list-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".annual-list-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
    // Tooltip info render
    $('[data-toggle="tooltip"]').tooltip();

    // show only table in mobile
    $(window).resize(function () {
        toggleDataInMobile();
    });
    toggleDataInMobile();
    function toggleDataInMobile() {
        if ($(window).width() < 768) {
            $(".annual-chart-data").addClass('hidden');
            $(".annual-list-data").removeClass('hidden');
        } else {
            $(".annual-chart-data").removeClass('hidden');
            $(".annual-list-data").addClass('hidden');
        }
    }
});