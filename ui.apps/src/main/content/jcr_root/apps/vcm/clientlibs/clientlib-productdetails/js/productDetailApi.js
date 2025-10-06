var ajaxData;

var ajaxData_productLabels;
var ajaxData_calendarreturns;
var ajaxData_riskmeasures;
var ajaxData_annualizedreturns;
var ajaxData_annualexpense;
var ajaxData_toptenholdings;
var ajaxData_toptenholdings_four;
var ajaxData_characteristics;
var ajaxData_characteristics_two;
var ajaxData_characteristics_three;
var ajaxData_characteristics_four;
var ajaxData_characteristics_five;
var ajaxData_assetallocation;
var ajaxData_durationschedule;
var ajaxData_fundholding;
var ajaxData_qualitystructure;
var ajaxData_qualitystructure_two;
var ajaxData_bottomtenholdings;
var ajaxData_countrydiversification;
var ajaxData_regionaldiversification;
var ajaxData_sectordiversification;
var ajaxData_allocationassetclass;
var ajaxData_history;
var ajaxData_distribution;
var ajaxData_recentdistribution;
var ajaxData_yields;
var ajaxData_yields_two;
var ajaxData_yields_three;
var ajaxData_minimuminvestment;
var ajaxData_averagelifestructure;
var ajaxData_fundexpensecalculator;
var ajaxData_fundoperatingexpense;
var ajaxData_productoverview;
var ajaxData_rightrailmf;
var ajaxData_rightrailsummary;
var ajaxData_sectorallocation;
var ajaxData_regionallocation;
var ajaxData_fundstatistics;
var selectShareClassDataUser;
var fundApiUrl=$("#fundListEndpoint").val();
var fundApiKey=$("#fundApiKey").val();

var headername = "";
var row;
var lableValue;

var loadClass;
var localStoreValues = JSON.parse(localStorage.getItem("listingPageLS"));

var jsonStr = $("#productDetailConfigJson").val();
var json = JSON.parse(jsonStr);
var urlVal= json["investment"];
var fundIdVal = urlVal.split("product/")[1].split("/")[0];
console.log("fundId "+ fundIdVal + "local storage "+ localStoreValues);
if (localStoreValues === null || localStoreValues === 'null' || (typeof localStoreValues === 'undefined'))  {
    console.log("fundApiUrl "+ fundApiUrl+": fundApiKey "+ fundApiKey)
    loadApiData(fundApiUrl,fundIdVal, fundApiKey)
}
var userValue = sharedJS.getCookie("subdomain_user_entity_type");
if (localStoreValues != null && (typeof localStoreValues != 'undefined') && $("#fundID").val() == localStoreValues[1])  {
    if(userValue == "financial_professional"){
        loadClass = "A";
    } else {
        loadClass = localStoreValues[0];
    }
} else {
    if(userValue == "financial_professional"){
        loadClass = "A";
    } else {
        loadClass = "Fund";
    }
}

var colorList = [];

$( document ).ready(function() {
	var chartColors = $("#chartColor").val(); //['#004B98', '#9D6915', '#801675', '#252527'];
    var colorVal = chartColors.split(",");
    var arrayLength = colorVal.length;
    for (var i = 0; i < arrayLength; i++) {
        colorList.push(colorVal[i]);
    }
	var labelsJsonStr = $("#productLabels").val();
	ajaxData_productLabels = JSON.parse(labelsJsonStr);
	var jsonStr = $("#productDetailConfigJson").val();
	var json = JSON.parse(jsonStr);
    var productapiKey = $("#productDetailKey").val();
    function firstLoad() {
    	if($('.rightrailmf').length > 0 && $.trim($("div.rightrailmf").html()).length > 0) {
            	$.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    context: this,
                    type: 'GET',
                    url: json["investment"],
                    dataType: "json",
                    async:false,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("x-api-key", productapiKey);
                        $('.rightrail-mf').css("visibility", "hidden");
                        beforeRightRailLoadFuc();
                    },
                    success: function (response) {
                    	try {
                            ajaxData_rightrailmf = response;
                            var count = 0;
                            if (response.length) {
                                response.filter(resp => {
                                    if (jQuery.isEmptyObject(resp)) {
                                        count++;
                                    }
                                });
                                if (count == response.length) {
                                    $('.rightrail-mf').css("display", "none");
                                    $('.error-message-rightrailmf').css("display", "block");
                                } else {
                                    loadmfFundsDetails(ajaxData_rightrailmf);
                                    labelmfHeader(ajaxData_productLabels);
                                    $('.dynamic-loader-rightrailmf').css("display", "none");
                                    $('.rightrail-mf').css("visibility", "visible");

                                }
                            } else {
                                $('.rightrail-mf').css("display", "none");
                                $('.error-message-rightrailmf').css("display", "block");
                            }
                        } catch(err) {
                            rightRailMfErrorHandlingFuc();
                        }
                    },
                    error: function (error) {
                    	$('.rightrail-mf').css("visibility", "hidden");
                    	rightRailMfErrorHandlingFuc();
                    	$('.rightrail-mf').css("display", "none");
                    }

                })
    	}
    	if($('.rightrailetf').length > 0 && $.trim($("div.rightrailetf").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["summary"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.rightrail-etf').css("visibility", "hidden");
                    beforeSummaryLoadFuc();
                },
                success: function (response) {
                    try {
                        ajaxData_rightrailsummary = response;
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.rightrail-etf').css("display", "none");
                                $('.error-message-rightrailetf').css("display", "block");
                            } else {
                                summaryFieldData(response);
                                $('.dynamic-loader-rightrailetf').css("display", "none");
                                $('.rightrail-etf').css("visibility", "visible");

                            }
                        } else {
                            $('.rightrail-etf').css("display", "none");
                            $('.error-message-rightrailetf').css("display", "block");
                        }
                    } catch(err) {
                        summaryErrorHandlingFuc();
                    }
                },
                error: function (error) {
                    $('.rightrail-etf').css("visibility", "hidden");
                    summaryErrorHandlingFuc();
                }
            })

            /*fetching API data for Fund details field*/
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["funddetails"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.rightrail-etf').css("visibility", "hidden");
                    beforeFundDetailsLoadFuc();
                },
                success: function (response) {
                    try {
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.rightrail-etf').css("display", "none");
                                $('.error-message-rightrailetf').css("display", "block");
                            } else {
                                fundDetailsData(response);
                                $('.dynamic-loader-rightrailetf').css("display", "none");
                                $('.rightrail-etf').css("visibility", "visible");

                            }
                        } else {
                            $('.rightrail-etf').css("display", "none");
                            $('.error-message-rightrailetf').css("display", "block");
                        }
                    } catch(err) {
                        fundDetailsErrorHandlingFuc();
                    }
                },
                error: function (error) {
                    $('.rightrail-etf').css("visibility", "hidden");
                    fundDetailsErrorHandlingFuc();
                }
            });


            /*fetching API data for Fund yield field*/
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["yields"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.rightrail-etf').css("visibility", "hidden");
                    beforeFundYieldLoadFuc();
                },
                success: function (response) {
                    try {
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.rightrail-etf').css("display", "none");
                                $('.error-message-rightrailetf').css("display", "block");
                            } else {
                                fundyieldData(response);
                                $('.dynamic-loader-rightrailetf').css("display", "none");
                                $('.rightrail-etf').css("visibility", "visible");

                            }
                        } else {
                            $('.rightrail-etf').css("display", "none");
                            $('.error-message-rightrailetf').css("display", "block");
                        }
                    } catch(err) {
                        fundYieldErrorHandlingFuc();
                    }
                },
                error: function (error) {
                    $('.rightrail-etf').css("visibility", "hidden");
                    fundYieldErrorHandlingFuc();
                }
            });


            /*fetching API data for Fees field*/
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["fees"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.rightrail-etf').css("visibility", "hidden");
                    beforeFeesDataLoadFuc();
                },
                success: function (response) {
                    try {
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.rightrail-etf').css("display", "none");
                                $('.error-message-rightrailetf').css("display", "block");
                            } else {
                                feesData(response);
                                fetchetfLabel(ajaxData_productLabels);
                                $('.dynamic-loader-rightrailetf').css("display", "none");
                                $('.rightrail-etf').css("visibility", "visible");

                            }
                        } else {
                            $('.rightrail-etf').css("display", "none");
                            $('.error-message-rightrailetf').css("display", "block");
                        }
                    } catch(err) {
                        FeesDataErrorHandlingFuc();
                    }
                },
                error: function (error) {
                    $('.rightrail-etf').css("visibility", "hidden");
                    FeesDataErrorHandlingFuc();
                }
            });

        }
    	if($('.rightrailstrategy').length > 0 && $.trim($("div.rightrailstrategy").html()).length > 0) {
                $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    context: this,
                    type: 'GET',
                    url: $("#strategyEndPoint").val(),
                    dataType: "json",
                    beforeSend: function(xhr) {
                         $('.dynamic-loader').css("display", "block");
                         xhr.setRequestHeader("x-api-key", $('#productDetailKey').val());
                    },
                    success: function(response) {
                        loadstrategyFundsDetails(response);
                        labelStrategyHeader(ajaxData_productLabels);
                        $('.dynamic-loader').css("display", "none");
                    },
                    error: function(error) {
                         $('.dynamic-loader').css("display", "none");
                        console.log(error);
                    }
                })
    	}
    	if(($("#fund-category").val() != "ST") && $.trim($("div.productdetaildatainfo").html()).length > 0) {
            loadFundsDetails_head(ajaxData_productLabels);
               $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    context: this,
                    type: 'GET',
                    url: json["productdetaildatainfo"],
                    dataType: "json",
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("x-api-key", productapiKey);
                        //$('.product-details-component').css("visibility", "hidden");
                        beforeServiceLoadFuc();
                   },
                    success: function (response) {
						$('.product-details-component .fund-data').show();
                    	ajaxData_productoverview = response;
                    	loadFundsDetails(ajaxData_productoverview);
                        $('.dynamic-loader-productoverview').css("display", "none");
                        $('.product-details-component').css("visibility", "visible");
                    },
                    error: function (error) {
                    	serviceErrorHandlingFuc();
                    }

               });

    		}
			if(($("#fund-category").val() == "ETF") && $.trim($("div.productdetaildatainfo").html()).length > 0) {
				var CSV = '';
				$.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    context: this,
                    type: 'GET',
                    url: json["allholdings"],
                    dataType: "json",
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("x-api-key", productapiKey);
                        //$('.product-details-component').css("visibility", "hidden");
                        beforeServiceLoadFuc();
                    },
                    success: function(data) {
                        var productLabel = $("#productLabels").val();
                        var etfPassiveAllholdingsJson;
                        var etfActiveAllholdingsJson;
                        var jsonRow;
                        var JSONData = data;
                        var activeTemplate = $('.etf-fund-data').attr('data-active-etf-template');
                        var fileName = $('.product-details-component .fund-name').text();
                        productLabel = $.parseJSON(productLabel);
                        $.each(productLabel, function(key, value) {
                            //display the key and value pair
                            if (key === 'etfPassiveAllholdings') {
                                etfPassiveAllholdingsJson = value;
                            }
                            if (key === 'etfActiveAllholdings') {
                                etfActiveAllholdingsJson = value;
                            }
                        });
                        $.each(JSONData, function(i) {
                            JSONData[i].etfname = fileName;
                        });
                        //var jsonRow = {'holding_name':'Holdings','stock_symbol':'Stock Symbol','as_of_date':'Date','isin':'ASIN','security_type':'Security Type','market_value':'Market Value','coupon_rate':'Coupon Rate','maturity_date':'Maturity Date','portfolio_percentage':'Portfolio %','shares':'Shares'};
                        if (activeTemplate == 'true') {
                            jsonRow = etfActiveAllholdingsJson;
                        } else {
                            jsonRow = etfPassiveAllholdingsJson;
                        }
                        var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
                        //Set Report title in first row or line
                        //This condition will generate the Label/Header
                        var row = "";
                        //This loop will extract the label from 1st index of on array
                        for (var index in jsonRow) {
                            var columnName = jsonRow[index];
                                row += columnName + ',';
                        }

                        row = row.slice(0, -1);

                        //append Label row with line break
                        CSV += row + '\r\n';

                        //1st loop is to extract each row
                        for (var i = 0; i < arrData.length; i++) {
                            var rowData = "";

                            for (var index in jsonRow) {
                                var arrayData = arrData[i][index];
                                if (arrData[i][index] == null) {
                                    arrayData = "";
                                }
                                rowData += '"' + arrayData + '",';
                            }

                            rowData.slice(0, rowData.length - 1);

                            //add a line break after each row
                            CSV += rowData + '\r\n';
                        }

                        if (CSV == '') {
                            alert("Invalid data");
                            return;
                        }
                        if (!navigator.msSaveBlob) {
                            //Initialize file format you want csv or xls
                            var blob = new Blob([CSV], { type: 'text/csv;charset=utf-8;' });
                            var uri = URL.createObjectURL(blob);

                            FDate = new Date($.now());
                            FDformat = {
                                year: 'numeric',
                                month: '2-digit',
                                day: '2-digit'
                            };
                            var link = document.getElementById('allholdingsCSVExport');
                            link.href = uri;
                            fileName = fileName.replace(/ /g, "");
                            link.download = fileName + "_" + FDate.toLocaleDateString('en-US', FDformat) + ".csv";
                        }
                    },
                    error: function(data) {
                        serviceErrorHandlingFuc();
                    }
                });
                $('#allholdingsCSVExport').click(function(){
                    if (navigator.msSaveBlob) { // IE 10+
                        FDate = new Date($.now());
                        FDformat = {
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit'
                        };
                        var link = document.getElementById('allholdingsCSVExport');
                        link.href = "javascript:void(0)";
                        var exportedFilenmae = $('.product-details-component .fund-name').text();
                        exportedFilenmae = exportedFilenmae.replace(/ /g, "");
                        exportedFilenmae = exportedFilenmae + "_" + FDate.toLocaleDateString('en-US', FDformat) + ".csv";
                        var blob = new Blob([CSV], { type: 'text/csv;charset=utf-8;' });
                        navigator.msSaveBlob(blob, exportedFilenmae);
                    }
               });
			}

    	}
    	$.when(
    		firstLoad()
    	).always(function() {

        if($('.riskmeasures').length > 0 && $.trim($("div.riskmeasures").html()).length > 0) {
        	var riskActiveEle = $(".time-tabs.active");
            var riskActiveTab;
            if (riskActiveEle.hasClass("three-year-tab")) {
                riskActiveTab = "three_year";
            } else if(riskActiveEle.hasClass("five-year-tab")) {
                riskActiveTab = "five_year";
            }else{
                riskActiveTab = "ten_year";
            }
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["riskmeasures"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.risk-measures-component').css("visibility", "hidden");
                    beforeRiskMeasuresLoadFuc();
                },
                success: function(response) {
                    try {
                        var selectedClassRisk = loadClass;
                        var apiClasses = [];
                        response.forEach(element => {
                         apiClasses.push(element.share_class);
                        });

                        if (!(apiClasses.indexOf(selectedClassRisk) > -1)) {
                            selectedClassRisk = apiClasses[0];
                        }
                        ajaxData_riskmeasures = response;
                        var count = 0;
                        var one_year_actuals;
                        var one_year_benchmark;
                        var three_year_actuals;
                        var three_year_benchmark;
                        var five_year_actuals;
                        var five_year_benchmark;
                        var ten_year_actuals;
                        var ten_year_benchmark;
                        var one_year_flag = false;
                        var three_year_flag = false;
                        var five_year_flag = false;
                        var ten_year_flag = false;
                        if (response.length) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == response.length) {
                                $('.risk-measures-component').css("display", "none");
                                $('.error-message-riskmeasures').css("display", "block");
                            } else {
                                if (response.length) {
                                    response.map(item => {
                                        if (item.share_class == selectedClassRisk) {

                                        	three_year_actuals = item.three_year.data.filter(key => {
                                                if (('entity_long_name' in key)) {
                                                    return key;
                                                }
                                            });
                                            three_year_benchmark = item.three_year.data.filter(key => {
                                                if (('benchmark_name' in key)) {
                                                    return key;
                                                }
                                            });
                                            // 5 year data
                                            five_year_actuals = item.five_year.data.filter(key => {
                                                if (('entity_long_name' in key)) {
                                                    return key;
                                                }
                                            });
                                            five_year_benchmark = item.five_year.data.filter(key => {
                                                if (('benchmark_name' in key)) {
                                                    return key;
                                                }
                                            });
                                            // 10 year data
                                            ten_year_actuals = item.ten_year.data.filter(key => {
                                                if (('entity_long_name' in key)) {
                                                    return key;
                                                }
                                            });
                                            ten_year_benchmark = item.ten_year.data.filter(key => {
                                                if (('benchmark_name' in key)) {
                                                    return key;
                                                }
                                            });
                                            // check for valid data
                                            if(three_year_actuals.length && three_year_benchmark.length >=1){
                                                three_year_flag = true;
                                            }

                                            if(five_year_actuals.length && five_year_benchmark.length >=1){
                                                five_year_flag = true;
                                            }

                                            if(ten_year_actuals.length && ten_year_benchmark.length >=1){
                                                ten_year_flag = true;
                                            }

                                            if(three_year_flag || five_year_flag || ten_year_flag){
                                                $(".one-year-tab").hide().removeClass('active');
                                                // hide tabs if no data
                                                if(three_year_flag == false){ $(".three-year-tab").hide() }
                                                if(five_year_flag == false){ $(".five-year-tab").hide() }
                                                if(ten_year_flag == false){ $(".ten-year-tab").hide() }

                                                // hide component if no 3,5, 10 tabs data
                                                if(three_year_flag == false && five_year_flag == false && ten_year_flag == false){
                                                    $('.risk-measures-component').hide();
                                                }else{
                                                    $('.risk-measures-component').show();
                                                }

                                                // check for default selected tab data
                                                if(three_year_flag){
                                                    $(".time-tabs").removeClass('active');
                                                    $(".three-year-tab").show().addClass('active');
                                                    riskActiveTab = "three_year";
                                                }else if(five_year_flag){
                                                    $(".time-tabs").removeClass('active');
                                                    $(".five-year-tab").show().addClass('active');
                                                    riskActiveTab = "five_year";
                                                }else if(ten_year_flag){
                                                    $(".time-tabs").removeClass('active');
                                                    $(".ten-year-tab").show().addClass('active');
                                                    riskActiveTab = "ten_year";
                                                }
                                            }else{
                                                // 1 year data
                                                one_year_actuals = item.one_year.data.filter(key => {
                                                    if (('entity_long_name' in key)) {
                                                        return key;
                                                    }
                                                });
                                                one_year_benchmark = item.one_year.data.filter(key => {
                                                    if (('benchmark_name' in key)) {
                                                        return key;
                                                    }
                                                });

                                                if(one_year_actuals.length && one_year_benchmark.length >=1){
                                                    one_year_flag = true;
                                                }
                                                if(one_year_flag == false){
                                                    $(".one-year-tab").hide();
                                                    $('.risk-measures-component').hide();
                                                }else{
                                                    $('.risk-measures-component').show();
                                                }

                                                $(".time-tabs").hide();
                                                $(".one-year-tab").show().addClass('active');
                                                riskActiveTab = "one_year";
                                            }
                                        }
                                    })
                                }
                                risk_measures(ajaxData_productLabels, ajaxData_riskmeasures,riskActiveTab);
                                $('.dynamic-loader-riskmeasures').css("display", "none");
                                $('.risk-measures-component').css("visibility", "visible");

                            }
                        } else {
                            $('.risk-measures-component').css("display", "none");
                            $('.error-message-riskmeasures').css("display", "block");
                        }
                    } catch (err) {
                        riskMeasuresErrorHandlingFuc();
                    }
                },
                error: function(error) {
                	$('.risk-measures-data').css("visibility", "hidden");
                	riskMeasuresErrorHandlingFuc();
                	$('.risk-measures-data').css("display", "none");
                }
            });
        }

        if($('.annualizedreturns').length > 0 && $.trim($("div.annualizedreturns").html()).length > 0) {
            let activeTab;
            let activeEle = $(".annualized-time-tabs.active");
            if (activeEle.hasClass("monthly-tab")) {
                activeTab = "monthly";
            } else {
                activeTab = "quarterly";
            }

            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["annualreturns"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.annual-returns-component').css("visibility", "hidden");
                    beforeAnnualReturnsLoadFuc();
                },
                success: function(response) {
                    try {
                        ajaxData_annualizedreturns = response;
                        var count = 0;
                        if (response.length) {
                            response.filter(resp => {
                                if (jQuery.isEmptyObject(resp)) {
                                    count++;
                                }
                            });
                            if (count == response.length) {
                                $('.annual-returns-component').css("display", "none");
                                $('.error-message-annualreturns').css("display", "block");
                            } else {
                                annualized_returns(ajaxData_productLabels,ajaxData_annualizedreturns,activeTab);
                                $('.dynamic-loader-annualreturns').css("display", "none");
                                $('.annual-returns-component').css("visibility", "visible");

                            }
                        } else {
                            $('.annual-returns-component').css("display", "none");
                            $('.error-message-annualreturns').css("display", "block");
                        }
                    } catch (err) {
                          annualreturnsErrorHandlingFuc();
                    }

                },
                error: function(error) {
                	$('.annual-returns-data').css("visibility", "hidden");
                	annualreturnsErrorHandlingFuc();
                	$('.annual-returns-data').css("display", "none");
                }
            });
        }

        if($('.annualexpense').length > 0 && $.trim($("div.annualexpense").html()).length > 0) {
            var activeTab = $(".time-tabs.active").text().toLowerCase();
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["annualexpense"],
                dataType: "json",
				beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.annual-expense').css("visibility", "hidden");
                    beforeAnnualExpenseLoadFuc();
                },
                success: function (response) {
                    try {
                        ajaxData_annualexpense = response;
                        var count = 0;
                        if (response.length) {
                            response.filter(resp => {
                                if (jQuery.isEmptyObject(resp)) {
                                    count++;
                                }
                            });
                            if (count == response.length) {
                                $('.annual-expense').css("display", "none");
                                $('.error-message-annualexpense').css("display", "block");
                            } else {
                                annual_expense_body(ajaxData_annualexpense);
                                annual_expense_head(ajaxData_productLabels);
                                $('.dynamic-loader-annualexpense').css("display", "none");
                                $('.annual-expense').css("visibility", "visible");

                            }
                        } else {
                            $('.annual-expense').css("display", "none");
                            $('.error-message-annualexpense').css("display", "block");
                        }
                    } catch(err) {
                        annualExpenseErrorHandlingFuc();
                    }
                },
                error: function (error) {
                	$('.annual-table-data').css("visibility", "hidden");
                	annualExpenseErrorHandlingFuc();
                	$('.annual-table-data').css("display", "none");
                }

            });
        }

        if($('.calendarreturns').length > 0 && $.trim($("div.calendarreturns").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["calendarreturns"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.calendar-returns-component').css("visibility", "hidden");
                    beforeCalenderReturnsLoadFuc();
                },
                success: function(response) {
                    try {
                        ajaxData_calendarreturns = response;
                        var count = 0;
                        if (response.length) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == response.length) {
                                $('.calendar-returns-component').css("display", "none");
                                $('.error-message-calenderreturns').css("display", "block");
                            } else {
                                calendar_returns(ajaxData_productLabels,ajaxData_calendarreturns);
                                $('.dynamic-loader-calenderreturns').css("display", "none");
                                $('.calendar-returns-component').css("visibility", "visible");
                            }
                        } else {
                            $('.calendar-returns-component').css("display", "none");
                            $('.error-message-calenderreturns').css("display", "block");
                        }
                    } catch (err) {
                        calenderReturnsErrorHandlingFuc();
                    }
                },
                error: function(error) {
                    $('.calendar-time-tabs-data').css("visibility", "hidden");
                    calenderReturnsErrorHandlingFuc();
                    $('.calendar-time-tabs-data').css("display", "none");
                }
            });
        }

         if($('.top-10-4col-holdings-table').length > 0) {
          $.ajax({
              contentType: "application/x-www-form-urlencoded; charset=UTF-8",
              context: this,
              type: 'GET',
              url: json["toptenholdings"],
              dataType: "json",
              beforeSend: function (xhr) {
                  xhr.setRequestHeader("x-api-key", productapiKey);
                  $('.topten-holdings-variant-two-component').css("visibility", "hidden");
                  beforeTopTenHoldingsTwoLoadFuc();

              },
              success: function(response) {
                  try {
                      ajaxData_toptenholdings_four = response;
                      var keys = Object.keys(response);
                      var count = 0;
                      if (keys) {
                          if (jQuery.isEmptyObject(response)) {
                              count++;
                          }
                          if (count == keys.length) {
                              $('.topten-holdings-variant-two-component').css("display", "none");
                              $('.error-message-toptenholdingtwo').css("display", "block");
                          } else {
                              top_ten_holdings_fourcolumn(ajaxData_productLabels, ajaxData_toptenholdings_four);
                              $('.dynamic-loader-toptenholdingtwo').css("display", "none");
                              $('.topten-holdings-variant-two-component').css("visibility", "visible");

                          }
                      } else {
                          $('.topten-holdings-variant-two-component').css("display", "none");
                          $('.error-message-toptenholdingtwo').css("display", "block");
                      }
                  } catch (err) {
                      topTenHoldingsTwoErrorHandlingFuc();
                  }
              },
              error: function(error) {
            	  $('.top-10-table-data-4col').css("visibility", "hidden");
            	  topTenHoldingsTwoErrorHandlingFuc();
            	  $('.top-10-table-data-4col').css("display", "none");
              }

          });
       }

       if($('.top-10-holdings-table').length > 0) {
          $.ajax({
              contentType: "application/x-www-form-urlencoded; charset=UTF-8",
              context: this,
              type: 'GET',
              url: json["toptenholdings"],
              dataType: "json",
              beforeSend: function (xhr) {
                  xhr.setRequestHeader("x-api-key", productapiKey);
                  $('.topten-holdings-variant-one-component').css("visibility", "hidden");
                  beforeTopTenholdingsLoadFuc();
              },
              success: function(response) {
                  try {
                      ajaxData_toptenholdings = response;
                      var keys = Object.keys(response);
                      var count = 0;
                      if (keys) {
                          if (jQuery.isEmptyObject(response)) {
                              count++;
                          }
                          if (count == keys.length) {
                              $('.topten-holdings-variant-one-component').css("display", "none");
                              $('.error-message-toptenholdingone').css("display", "block");
                          } else {
                              top_ten_holdings(ajaxData_productLabels,ajaxData_toptenholdings);
                              $('.dynamic-loader-toptenholdingone').css("display", "none");
                              $('.topten-holdings-variant-one-component').css("visibility", "visible");
                          }
                      } else {
                          $('.topten-holdings-variant-one-component').css("display", "none");
                          $('.error-message-toptenholdingone').css("display", "block");
                      }
                  } catch (err) {
                        topTenHoldingsErrorHandlingFuc();
                  }
              },
              error: function(error) {
            	  $('.toptenholdingone').css("visibility", "hidden");
            	  topTenHoldingsErrorHandlingFuc();
            	  $('.toptenholdingone').css("display", "none");
              }
          });
       }

        if($('.characteristics-table-data').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["characteristics"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.characterstics-table').css("visibility", "hidden");
                    beforeCharOneLoadFuc();
                },
                success: function(response) {
                try{
                    var modifiedAarry = [];
                    response.characteristics.data.map(item => {
                        var orderObj = {
                            "weighted_avg_market_capinmillions": item.weighted_avg_market_capinmillions,
                            "epsgrowth_lastthreeyearspercentage": item.epsgrowth_lastthreeyearspercentage,
                            "epsgrowth_lastfiveyearspercentage": item.epsgrowth_lastfiveyearspercentage,
                            "longtermdebt_to_capitalratiopercentage": item.longtermdebt_to_capitalratiopercentage,
                            "price_to_book_ratio": item.price_to_book_ratio,
                            "price_to_estimate_ratio_lasttwelvemonths": item.price_to_estimate_ratio_lasttwelvemonths,
                            "return_On_equity_last_twelvemonthspercentage": item.return_On_equity_last_twelvemonthspercentage,
                            "number_of_holdings": item.number_of_holdings,
                            "turn_over_ratio": item.turn_over_ratio,
                            "dollar_wtd_ave_maturity": item.dollar_wtd_ave_maturity,
                            "ending_effective_duration": item.ending_effective_duration,
                            "yield_to_maturity": item.yield_to_maturity,
                            "benchmark_name": item.benchmark_name
                        }
                        modifiedAarry.push(orderObj);
                    });
                    response.characteristics.data = modifiedAarry;
                    ajaxData_characteristics = response;
                    var keys = Object.keys(response);
        			var count = 0;
        			if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.characterstics-table').css("display", "none");
                        $('.error-message-charone').css("display", "block");
                    } else {
                    	characteristics(ajaxData_characteristics,ajaxData_productLabels);
						$('.dynamic-loader-charone').css("display", "none");
                        $('.characterstics-table').css("visibility", "visible");

                    }
                } else {
                    $('.characterstics-table').css("display", "none");
                    $('.error-message-charone').css("display", "block");
                	}
                }
                catch(err) {
                	charOneErrorHandlingFuc();

                }
                },
                error: function(error) {
                	$('.characteristics-table-data').css("visibility", "hidden");
                	charOneErrorHandlingFuc();
                	$('.characteristics-table-data').css("display", "none");
                }
            });
        }
        if($('.characteristicsvariant-two-table-data').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["characteristics"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.charvar-two').css("visibility", "hidden");
                    beforeCharVarTwoLoadFuc();
                },
                success: function(response) {
                try{
                    ajaxData_characteristics_two = response;
                    var keys = Object.keys(response);
        			var count = 0;
        			if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.charvar-two').css("display", "none");
                        $('.error-message-charVarTwo').css("display", "block");
                    } else {
                    	characteristics_two(ajaxData_characteristics_two,ajaxData_productLabels);
                    	sortTable(".charvar-two ");
						$('.dynamic-loader-charVarTwo').css("display", "none");
                        $('.charvar-two').css("visibility", "visible");

                    }
                } else {
                	$('.charvar-two').css("visibility", "hidden");
                    $('.error-message-charVarTwo').css("display", "block");
                }
                }
                catch(err) {
                	charVarTwoErrorHandlingFuc();

                }
                },
                error: function(error) {
                	$('.characteristicsvariant-two-table-data').css("visibility", "hidden");
                	charVarTwoErrorHandlingFuc();
                	$('.characteristicsvariant-two-table-data').css("display", "none");
                }
            });
        }
        if($('.characteristicsvariant-three-table-data').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["characteristics"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.charvar-three').css("visibility", "hidden");
                    beforeCharVarThreeLoadFuc();
                },
                success: function(response) {
                try{
                    ajaxData_characteristics_three = response;
                    var keys = Object.keys(response);
        			var count = 0;
        			if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.charvar-three').css("display", "none");
                        $('.error-message-charVarThree').css("display", "block");
                    } else {
                    	characteristics_three(ajaxData_characteristics_three,ajaxData_productLabels);
                    	sortTable(".charvar-three ");
						$('.dynamic-loader-charVarThree').css("display", "none");
                        $('.charvar-three').css("visibility", "visible");

                    }
                } else {
                    $('.charvar-three').css("display", "none");
                    $('.error-message-charVarThree').css("display", "block");
                }
                }
        		catch(err) {
        			charVarThreeErrorHandlingFuc();

                 }
                },
                error: function(error) {
                	$('.characteristicsvariant-three-table-data').css("visibility", "hidden");
                	charVarThreeErrorHandlingFuc();
                	$('.characteristicsvariant-three-table-data').css("display", "none");
                }
            });
        }
        /*if($('.characteristicsvariant-four-table-data').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["characteristics"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.charvar-four').css("visibility", "hidden");
                    beforeCharVarFourLoadFuc();
                },
                success: function(response) {
                    ajaxData_characteristics_four = response;
                    var keys = Object.keys(response);
        			var count = 0;
                        if (keys) {
                                if (jQuery.isEmptyObject(response)) {
                                    count++;
                                }
                            if (count == keys.length) {
                                $('.charvar-four').css("display", "none");
                                $('.error-message-charVarFour').css("display", "block");
                            } else {
                            	 characteristics_four(ajaxData_characteristics_four,ajaxData_productLabels);
        						$('.dynamic-loader-charVarFour').css("display", "none");
                                $('.charvar-four').css("visibility", "visible");

                            }
                        } else {
                            $('.charvar-four').css("display", "none");
                            $('.error-message-charVarFour').css("display", "block");
                        }

                },
                error: function(error) {
                	$('.charvar-four').css("visibility", "hidden");
                	charVarFourErrorHandlingFuc();
                	$('.charvar-four').css("display", "none");
                }
            });
        }
        if($('.characteristicsvariant-five-table-data').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["characteristics"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.charvar-five').css("visibility", "hidden");
                    beforeCharVarFiveLoadFuc();
                },
                success: function(response) {
                    ajaxData_characteristics_five = response;
                    var keys = Object.keys(response);
        			var count = 0;
                        if (keys) {
                                if (jQuery.isEmptyObject(response)) {
                                    count++;
                                }
                            if (count == keys.length) {
                                $('.charvar-five').css("display", "none");
                                $('.error-message-charVarFive').css("display", "block");
                            } else {
                            	characteristics_five(ajaxData_characteristics_five,ajaxData_productLabels);
        						$('.dynamic-loader-charVarFive').css("display", "none");
                                $('.charvar-five').css("visibility", "visible");

                            }
                        } else {
                            $('.charvar-five').css("display", "none");
                            $('.error-message-charVarFive').css("display", "block");
                        }

                },
                error: function(error) {
                	$('.charvar-five').css("visibility", "hidden");
                	charVarFiveErrorHandlingFuc();
                	$('.charvar-five').css("display", "none");
                }
            });
        }*/

        if($('.assetallocation').length > 0 && $.trim($("div.assetallocation").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["assetallocation"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.asset-allocation-component').css("visibility", "hidden");
                    beforeassetallocLoadFuc();
                },
                success: function(response) {
					try{
                    ajaxData_assetallocation = response;
                    var count = 0;
                    var keys = Object.keys(response);
                    if (keys.length) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.asset-allocation-component').css("display", "none");
                        $('.error-message-assetallocation').css("display", "block");
                    } else {
                    	asset_allocation_body(ajaxData_assetallocation);
                        asset_allocation_head(ajaxData_productLabels);
                        $('.dynamic-loader-assetallocation').css("display", "none");
                        $('.asset-allocation-component').css("visibility", "visible");

                    }
                } else {
                	$('.asset-allocation-component').css("visibility", "hidden");
                    $('.error-message-assetallocation').css("display", "block");
                }
				}
        		catch(err) {
        			assetallocErrorHandling();

                 }
                },
                error: function(error) {
                	$('.asset-allocation-table-data').css("visibility", "hidden");
                	assetallocErrorHandling();
                	$('.asset-allocation-table-data').css("display", "none");
                }
            });
        }
        if($('.durationschedule').length > 0 && $.trim($("div.durationschedule").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["durationschedule"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.duration-schedule-component').css("visibility", "hidden");
                    beforeDurationScheduleLoadFuc();
                },
                success: function(response) {
					try{
                    ajaxData_durationschedule = response;
                    var keys = Object.keys(response);
                    var count = 0;
                    if (keys.length) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.duration-schedule-component').css("display", "none");
                        $('.error-message-durationschedule').css("display", "block");
                    } else {
                    	duration_schedule_body(ajaxData_durationschedule);
                        duration_schedule_head(ajaxData_productLabels);
                        $('.dynamic-loader-durationschedule').css("display", "none");
                        $('.duration-schedule-component').css("visibility", "visible");

                    }
                } else {
                    $('.duration-schedule-component').css("display", "none");
                    $('.error-message-durationschedule').css("display", "block");
                }
                }
        		catch(err) {
        			durationScheduleErrorHandlingFuc();

                 }
                },
                error: function(error) {
                	$('.Duration-Schedule-table-data').css("visibility", "hidden");
                	durationScheduleErrorHandlingFuc();
                	$('.Duration-Schedule-table-data').css("display", "none");
                }
            });
        }
        if($('.fundholding').length > 0 && $.trim($("div.fundholding").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["fundholding"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.fund-holding-component').css("visibility", "hidden");
                    beforeFundHoldingLoadFuc();
                },
                success: function(response) {
                    try {
                        ajaxData_fundholding = response;
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys.length) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                        if (count == keys.length) {
                            $('.fund-holding-component').css("display", "none");
                            $('.error-message-fundholdings').css("display", "block");
                        } else {
                            fund_holding_body(ajaxData_fundholding);
                            fund_holding_head(ajaxData_productLabels);
                            sortTable(".fund-holding-component ");
                            $('.dynamic-loader-fundholdings').css("display", "none");
                            $('.fund-holding-component').css("visibility", "visible");
                        }
                    } else {
                        $('.fund-holding-component').css("display", "none");
                        $('.error-message-fundholdings').css("display", "block");
                    }
                    }
                    catch(err) {
                		fundHoldingErrorHandlingFuc();
                	}
                },
                error: function(error) {
                	$('.fund-holding-table-data').css("visibility", "hidden");
                	fundHoldingErrorHandlingFuc();
                	$('.fund-holding-table-data').css("display", "none");
                }
            });
        }

        if($('.Quality-Structure-table-data').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["qualitystructure"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.qualitystructure-one').css("visibility", "hidden");
                    beforeQualityStrucOneLoadFuc();
                },
                success: function(response) {
                    try{
                    ajaxData_qualitystructure = response;
                    var keys = Object.keys(response);
                    var count = 0;
                    if (keys.length) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.quality-structure-component').css("display", "none");
                        $('.error-message-qualitystructureone').css("display", "block");
                    } else {
                        qualitystructure_body(ajaxData_qualitystructure);
                        qualitystructure_head(ajaxData_productLabels);
                        $('.dynamic-loader-qualitystructureone').css("display", "none");
                        $('.qualitystructure-one').css("visibility", "visible");

                    }
                } else {
                    $('.qualitystructure-one').css("display", "none");
                    $('.error-message-qualitystructureone').css("display", "block");
                }
                    }
                catch(err) {
                qualityStrucOneErrorHandlingFuc();

                }
                },
                error: function(error) {
                    $('.Quality-Structure-table-data').css("visibility", "hidden");
                    qualityStrucOneErrorHandlingFuc();
                    $('.Quality-Structure-table-data').css("display", "none");
                }
            });
        }

        if($('.bottomtenholdings').length > 0 && $.trim($("div.bottomtenholdings").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["bottomtenholdings"],
                dataType: "json",
				beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.bottom-holding-component').css("visibility", "hidden");
                    beforebottomHoldingsLoadFuc();
                },
                success: function(response) {
                    try {
                        ajaxData_bottomtenholdings = response;
                        var count = 0;
                        var keys = Object.keys(response);
                        if (keys.length) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.bottom-holding-component').css("display", "none");
                                $('.error-message-bottomHoldings').css("display", "block");
                            } else {
                                bottom_ten_holdings_body(ajaxData_bottomtenholdings);
                                bottom_ten_holdings_head(ajaxData_productLabels);
                                $('.dynamic-loader-bottomHoldings').css("display", "none");
                                $('.bottom-holding-component').css("visibility", "visible");
                            }
                        } else {
                            $('.bottom-holding-component').css("display", "none");
                            $('.error-message-bottomHoldings').css("display", "block");
                        }
                    } catch (err) {
                        bottomHoldingsErrorHandlingFuc();
                    }
                },
                error: function(error) {
                	$('.bottom-ten-holdings-table-data').css("visibility", "hidden");
                	bottomHoldingsErrorHandlingFuc();
                	$('.bottom-ten-holdings-table-data').css("display", "none");
                }
            });
        }
        if($('.countrydiversification').length > 0 && $.trim($("div.countrydiversification").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["countrydiversification"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.country-table').css("visibility", "hidden");
                    beforeContryDiverseLoadFuc();
        		},
                success: function (response) {
					try{
                    ajaxData_countrydiversification = response;
                    var keys = Object.keys(response);
                    var count = 0;
                    if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.country-table').css("display", "none");
                        $('.error-message-countrydiverse').css("display", "block");
                    } else {
                    	country_diversification(ajaxData_countrydiversification,ajaxData_productLabels);
						$('.dynamic-loader-countrydiverse').css("display", "none");
                        $('.country-table').css("visibility", "visible");

                    }
                } else {
                    $('.country-table').css("display", "none");
                    $('.error-message-countrydiverse').css("display", "block");
                }
				}
				catch(err) {
                	countrydiverseErrorHandlingFuc();
				}
                },
                error: function (error) {
                	$('.Country-Diversification-table-data').css("visibility", "hidden");
                	countrydiverseErrorHandlingFuc();
                	$('.Country-Diversification-table-data').css("display", "none");
                }
			});
        }
        if($('.regionaldiversification').length > 0 && $.trim($("div.regionaldiversification").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["regionaldiversification"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.regional-table').css("visibility", "hidden");
                    beforeRegionalDiverseLoadFuc();
                },
                success: function(response) {
					try{
                	ajaxData_regionaldiversification = response;
                	var keys = Object.keys(response);
        			var count = 0;
        			if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.regional-table').css("display", "none");
                        $('.error-message-regionaldiverse').css("display", "block");
                    } else {
                    	regional_diversification(ajaxData_regionaldiversification,ajaxData_productLabels);
						$('.dynamic-loader-regionaldiverse').css("display", "none");
                        $('.regional-table').css("visibility", "visible");

                    }
                } else {
                    $('.regional-table').css("display", "none");
                    $('.error-message-regionaldiverse').css("display", "block");
                }
                }
				catch(err) {
                	regionalDiverseErrorHandlingFuc();
				}
                },
                error: function(error) {
                	$('.Regional-Diversification-table-data').css("visibility", "hidden");
                	regionalDiverseErrorHandlingFuc();
                	$('.Regional-Diversification-table-data').css("display", "none");
                }
            });
        }
        if($('.sectordiversification').length > 0 && $.trim($("div.sectordiversification").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["sectordiversification"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.sector-table').css("visibility", "hidden");
                    beforeSectorDiverseLoadFuc();
                },
                success: function(response) {
					try{
                	ajaxData_sectordiversification = response;
                	var keys = Object.keys(response);
        			var count = 0;
        			if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.sector-table').css("display", "none");
                        $('.error-message-sectordiverse').css("display", "block");
                    } else {
                    	sector_diversification(ajaxData_sectordiversification,ajaxData_productLabels);
						$('.dynamic-loader-sectordiverse').css("display", "none");
                        $('.sector-table').css("visibility", "visible");

                    }
                } else {
                    $('.sector-table').css("display", "none");
                    $('.error-message-sectordiverse').css("display", "block");
                }
                }
				catch(err) {
                	sectorDiverseErrorHandlingFuc();
				}
                },
                error: function(error) {
                	$('.Sector-Diversification-table-data').css("visibility", "hidden");
                	sectorDiverseErrorHandlingFuc();
                	$('.Sector-Diversification-table-data').css("display", "none");
                }
            });
        }
        if($('.allocationassetclass').length > 0 && $.trim($("div.allocationassetclass").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["allocationassetclass"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.allocation-asset-class').css("visibility", "hidden");
        			beforeAssetAllocClassLoadFuc();
                },
                success: function (response) {
                    try {
                        ajaxData_allocationassetclass = response;
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.allocation-asset-class').css("display", "none");
                                $('.error-message-assetallocationclass').css("display", "block");
                            } else {
                                allocation_assetclass_body(ajaxData_allocationassetclass);
                                allocation_assetclass_tablebody(ajaxData_allocationassetclass);
                                $(".alloaction-asset-asof").html(response.as_of_date);
                                allocation_assetclass_head(ajaxData_productLabels);
                                allocation_assetclass_tablehead(ajaxData_productLabels);
                                $(".alloaction-asset-asof").html(response.as_of_date);
                                $('.dynamic-loader-assetallocationclass').css("display", "none");
                                $('.allocation-asset-class').css("visibility", "visible");

                            }
                        } else {
                            $('.allocation-asset-class').css("display", "none");
                            $('.error-message-assetallocationclass').css("display", "block");
                        }
                    } catch(err) {
                        assetAllocClassErrorHandling();
                    }
                },
                error: function (error) {
                	$('.allocation-asset-data').css("visibility", "hidden");
                    assetAllocClassErrorHandling();
                    $('.allocation-asset-data').css("display", "none");
                }
            });
        }
        if($('.history').length > 0 && $.trim($("div.history").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["history"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                },
                success: function(response) {
                	try {
                    ajaxData_history = response;
                    history_component(ajaxData_productLabels,ajaxData_history);
                    $('.error-message-history').css("display", "none");
                	} catch (err) {
                		historyErrorHandling();
                    }
                },
                error: function(error) {
                	historyErrorHandling();
                }
            });
        }
        if($('.distribution').length > 0 && $.trim($("div.distribution").html()).length > 0) {
        	var checkFundCat = $("#fund-category").attr('value');
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["distribution"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.distributions-component').css("visibility", "hidden");
                    beforeDistributionLoadFuc();
                },
                success: function(response) {
                    try {
                        ajaxData_distribution = response;
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.distributions-component').css("display", "none");
                                $('.error-message-distribution').css("display", "block");
                            } else {
                                distribution_component(ajaxData_distribution,ajaxData_productLabels,checkFundCat);
                                $('.dynamic-loader-distribution').css("display", "none");
                                $('.distributions-component').css("visibility", "visible");
                            }
                        } else {
                            $('.distributions-component').css("display", "none");
                            $('.error-message-distribution').css("display", "block");
                        }
                    } catch(err) {
                        distributionErrorHandlingFuc();
                    }
                },
                error: function(error) {
                	$('.distribution-table-data').css("visibility", "hidden");
                	distributionErrorHandlingFuc();
                	$('.distribution-table-data').css("display", "none");
                }
            });
        }

         if($('.yields_fixed-income_table').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["yields"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.yields-fixed-income').css("visibility", "hidden");
                    beforeYieldsLoadFuc();
                },
                success: function(response) {
                    ajaxData_yields = response;
                    var keys = Object.keys(response);
                    var count = 0;
                    if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                        if (count == keys.length) {
                            $('.yields-fixed-income').css("display", "none");
                            $('.error-message-yieldsfixdinc').css("display", "block");
                        } else {
                        	yields_one_head(ajaxData_productLabels);
                            yields_one_body(ajaxData_yields);
                            $('.dynamic-loader-yieldsfixdinc').css("display", "none");
                            $('.yields-fixed-income').css("visibility", "visible");

                        }
                    }
                    else {
                        $('.yields-fixed-income').css("display", "none");
                        $('.error-message-yieldsfixdinc').css("display", "block");
                    }
                },
                error: function(error) {
                	$('.yields_fixed-income_table-data').css("visibility", "hidden");
                	yieldFixedIncErrorHandlingFuc();
                	$('.yields_fixed-income_table-data').css("display", "none");
                }

            });
         }
         if($('.yields_money-market_table').length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["yields"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.yields-money-market').css("visibility", "hidden");
                    beforeYieldsmoneymarketLoadFuc();
                },
                success: function(response) {
                    ajaxData_yields_two = response;
                    var count = 0;
                    var keys = Object.keys(response);
                    if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                        if (count == keys.length) {
                            $('.yields-money-market').css("display", "none");
                            $('.error-message-yieldsmoneymkt').css("display", "block");
                        } else {
                        	 yields_two_head(ajaxData_productLabels);
                             yields_two_body(ajaxData_yields_two);
                            $('.dynamic-loader-yieldsmoneymkt').css("display", "none");
                            $('.yields-money-market').css("visibility", "visible");

                        }
                    } else {
                        $('.yields-money-market').css("display", "none");
                        $('.error-message-yieldsmoneymkt').css("display", "block");
                    }
                },
                error: function(error) {
                	$('.yields_money-market_table-data').css("visibility", "hidden");
                	yieldMoneyMktErrorHandlingFuc();
                	$('.yields_money-market_table-data').css("display", "none");
                }

            });
         }
        if($('.yields_taxable-equivalent_table').length > 0) {
             var yield_taxable_endpoint;
             var yield_cusomtjson = $('body').find('div.taxTable').data('yieldcustomjson');
             if(yield_cusomtjson){
                yield_taxable_endpoint = $('body').find('div.taxTable').data('jsonpath');
             } else {
                yield_taxable_endpoint = json["yieldstaxable"];
             }
              $.ajax({
                 contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                 context: this,
                 type: 'GET',
                 url: yield_taxable_endpoint,
                 dataType: "json",
                 success: function(response) {
                     ajaxData_yields_three = response;
                     yields_three(ajaxData_yields_three,ajaxData_productLabels);
                     $('.dynamic-loader-yieldstaxeqv').css("display", "none");
                 },
                 error: function(error) {
                	 $('.yields_taxable-equivalent_table').css("visibility", "hidden");
                	 yieldTaxEqvErrorHandlingFuc();
                 }
             });
         }

        if($('.minimuminvestment').length > 0 && $.trim($("div.minimuminvestment").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["minimuminvestment"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.min-invest').css("visibility", "hidden");
                    beforeMinimumInvestLoadFuc();
        		},
                success: function(response) {
					try{
                    ajaxData_minimuminvestment = response;
                    var keys = Object.keys(response);
        			var count = 0;
        			if (keys) {
                        if (jQuery.isEmptyObject(response)) {
                            count++;
                        }
                    if (count == keys.length) {
                        $('.min-invest').css("display", "none");
                        $('.error-message-minimuminvest').css("display", "block");
                    } else {
                    	minimum_investment(ajaxData_productLabels, ajaxData_minimuminvestment);
						$('.dynamic-loader-minimuminvest').css("display", "none");
                        $('.min-invest').css("visibility", "visible");

                    }
                } else {
                    $('.min-invest').css("display", "none");
                    $('.error-message-minimuminvest').css("display", "block");
                }
        		}
				catch(err) {
                	minimumInvestErrorHandlingFuc();
				}
                },
                error: function(error) {
                	$('.minimum-investment_data').css("visibility", "hidden");
                	minimumInvestErrorHandlingFuc();
                	$('.minimum-investment_data').css("display", "none");
                }
            });
        }
        if($('.averagelifestructure').length > 0 && $.trim($("div.averagelifestructure").html()).length > 0) {
            $.ajax({
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                context: this,
                type: 'GET',
                url: json["averagelifestructure"],
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-api-key", productapiKey);
                    $('.average-life-structure').css("visibility", "hidden");
                    beforeAverageLifeLoadFuc();
                },
                success: function (response) {
                    try {
                        ajaxData_averagelifestructure = response;
                        var keys = Object.keys(response);
                        var count = 0;
                        if (keys) {
                            if (jQuery.isEmptyObject(response)) {
                                count++;
                            }
                            if (count == keys.length) {
                                $('.average-life-structure').css("display", "none");
                                $('.error-message-averagelife').css("display", "block");
                            } else {
                                averagelifestructure_body(ajaxData_averagelifestructure);
                                averagelifestructuretable_body(ajaxData_averagelifestructure);
                                $(".averagelife-asof").html(response.as_of_date);
                                averagelifestructure_head(ajaxData_productLabels);
                                averagelifestructuretable_head(ajaxData_productLabels);
                                $(".alloaction-asset-asof").html(response.as_of_date);
                                $('.dynamic-loader-averagelife').css("display", "none");
                                $('.average-life-structure').css("visibility", "visible");
                            }
                        } else {
                            $('.average-life-structure').css("display", "none");
                            $('.error-message-averagelife').css("display", "block");
                        }
                    } catch(err) {
                        averageLifeErrorHandlingFuc();
                    }
                },
                error: function (error) {
                	$('.average-asset-data').css("visibility", "hidden");
                	averageLifeErrorHandlingFuc();
                	$('.average-asset-data').css("display", "none");
                }
            });
        }
        if($('.fundexpensecalculator').length > 0 && $.trim($("div.fundexpensecalculator").html()).length > 0) {
            $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            context: this,
            type: 'GET',
            url: json["fees"],
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("x-api-key", productapiKey);
                $('.expense-calculator').css("visibility", "hidden");
                beforeCalculateExpenseLoadFuc();
            },
            success: function (response) {
				try{
            	ajaxData_fundexpensecalculator = response;
            	let amountInvested = parseFloat($('.foundCont').val());
            	var keys = Object.keys(response);
    			var count = 0;
    			if (keys) {
                    if (jQuery.isEmptyObject(response)) {
                        count++;
                    }
                if (count == keys.length) {
                    $('.expense-calculator').css("display", "none");
                    $('.error-message-calculateexpense').css("display", "block");
                } else {
                	fundexpensecalculator_body(ajaxData_fundexpensecalculator,amountInvested,ajaxData_productLabels);
                	fundexpensecalculator_head(ajaxData_productLabels);
					$('.dynamic-loader-calculateexpense').css("display", "none");
                    $('.expense-calculator').css("visibility", "visible");

                }
            } else {
                $('.expense-calculator').css("display", "none");
                $('.error-message-calculateexpense').css("display", "block");
            }
            }
				catch(err) {
                	calculateExpenseErrorHandlingFuc();
				}
            },
            error: function (error) {
            	$('.AnnualcalculateFund-calculator').css("visibility", "hidden");
            	calculateExpenseErrorHandlingFuc();
            	$('.AnnualcalculateFund-calculator').css("display", "none");
            }
			});
         }
        if($('.fundoperatingexpense').length > 0 && $.trim($("div.fundoperatingexpense").html()).length > 0) {
            $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            context: this,
            type: 'GET',
            url: json["fees"],
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("x-api-key", productapiKey);
                $('.fund-expense').css("visibility", "hidden");
                beforeFundExpenseLoadFuc();
            },
            success: function (response) {
				try{
            	ajaxData_fundoperatingexpense = response;
            	var keys = Object.keys(response);
    			var count = 0;
    			if (keys) {
                    if (jQuery.isEmptyObject(response)) {
                        count++;
                    }
                if (count == keys.length) {
                    $('.fund-expense').css("display", "none");
                    $('.error-message-fundexpense').css("display", "block");
                } else {
                	fundoperatingexpense(ajaxData_fundoperatingexpense,ajaxData_productLabels);
					$('.dynamic-loader-fundexpense').css("display", "none");
                    $('.fund-expense').css("visibility", "visible");

                }
            } else {
                $('.fund-expense').css("display", "none");
                $('.error-message-fundexpense').css("display", "block");
            }
    		}
				catch(err) {
                	fundExpenseErrorHandlingFuc();
				}
            },
            error: function (error) {
            	$('.Annual-FundExpense-table-data').css("visibility", "hidden");
            	fundExpenseErrorHandlingFuc();
            	$('.Annual-FundExpense-table-data').css("display", "none");
            }
			});
         }
         if($('.premiumdiscount').length > 0 && $.trim($("div.premiumdiscount").html()).length > 0) {
             $.ajax({
                   contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                   context: this,
                   type: 'GET',
                   url: json["premiumdiscount"],
                   dataType: "json",
                   beforeSend: function(xhr) {
                       xhr.setRequestHeader("x-api-key", productapiKey);
                       $('.premium-discount-class').css("visibility", "hidden");
                       beforePremiumDiscountLoadFuc();
                   },
                   success: function(response) {
                       try {
                           ajaxData_premiumdiscount = response;
                           var keys = Object.keys(response);
                           var count = 0;
                           if (keys) {
                               if (jQuery.isEmptyObject(response)) {
                                   count++;
                               }
                               if (count == keys.length) {
                                   $('.premium-discount-class').css("display", "none");
                                   $('.error-message-premiumdiscount').css("display", "block");
                               } else {
                                   createLineChart(ajaxData_premiumdiscount);
                                   premiumdiscount_tableHead(ajaxData_productLabels);
                                   premiumdiscount_tableData(ajaxData_premiumdiscount);
                                   //$(".alloaction-asset-asof").html(response.as_of_date);
                                   $('.dynamic-loader-premiumdiscount').css("display", "none");
                                   $('.premium-discount-class').css("visibility", "visible");

                               }
                           } else {
                               $('.premium-discount-class').css("display", "none");
                               $('.error-message-premiumdiscount').css("display", "block");
                           }
                       } catch (err) {
                           $('.error-message-premiumdiscount').css("display", "block");
                           $('.dynamic-loader-premiumdiscount').css("display", "none");
                           $(".as_of").css("visibility", "hidden");
                       }

                   },
                   error: function(error) {
                       $('.premium-discount-class').css("visibility", "hidden");
                       $(".as_of").css("visibility", "hidden");
                       PremiumDiscountErrorHandlingFuc();
                   }
               });
             }
              if($('.fundstatistics').length > 0 && $.trim($("div.fundstatistics").html()).length > 0) {
                  $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    context: this,
                    type: 'GET',
                    url: json["fundstatistics"],
                    dataType: "json",
                    beforeSend: function(xhr) {
                  	  xhr.setRequestHeader("x-api-key", productapiKey);
                  	  $('.fund-stats-component').css("visibility", "hidden");
                  	  beforeFundStatisticsFuc();
                    },
                    success: function(response) {
                  	  try {
                  		  ajaxData_fundstatistics = response;
                  		  var keys = Object.keys(response);
                  		  var count = 0;
                  		  if (keys) {
                  			  if (jQuery.isEmptyObject(response)) {
                  				  count++;
                  			  }
                  			  if (count == keys.length) {
                  				  $('.fund-stats-component').css("display", "none");
                  				  $('.error-message-fundstatistics').css("display", "block");
                  			  } else {
                  				  fund_statistics(ajaxData_productLabels,ajaxData_fundstatistics);
                  				  $('.dynamic-loader-fundstatistics').css("display", "none");
                  				  $('.fund-stats-component').css("visibility", "visible");

                  			  }
                  		  } else {
                  			  $('.fund-stats-component').css("display", "none");
                  			  $('.error-message-fundstatistics').css("display", "block");
                  		  }
                  	  } catch (err) {
                  		  $('.error-message-fundstatistics').css("display", "block");
                  		  $('.dynamic-loader-fundstatistics').css("display", "none");
                  		  $(".as_of").css("visibility", "hidden");
                  	  }

                    },
                    error: function(error) {
                  	  $('.fund-stats-component').css("visibility", "hidden");
                  	  $(".as_of").css("visibility", "hidden");
                  	  fundStatisticsErrorHandlingFuc();
                    }
                  });
              }
              if($('.sectorallocation').length > 0 && $.trim($("div.sectorallocation").html()).length > 0) {
                  $.ajax({
                      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                      context: this,
                      type: 'GET',
                      url: json["sectorallocation"],
                      dataType: "json",
                      beforeSend: function(xhr) {
                          xhr.setRequestHeader("x-api-key", productapiKey);
                          $('.sec-allocation-component').css("visibility", "hidden");
                          beforeSectorAllocationFuc();
                      },
                      success: function(response) {
                          try {
                              ajaxData_sectorallocation = response;
                              var keys = Object.keys(response);
                              var count = 0;
                              if (keys) {
                                  if (jQuery.isEmptyObject(response)) {
                                      count++;
                                  }
                                  if (count == keys.length) {
                                      $('.sec-allocation-component').css("display", "none");
                                      $('.error-message-sectorallocation').css("display", "block");
                                  } else {
                                      sector_allocation(ajaxData_productLabels,ajaxData_sectorallocation);
                                      $('.dynamic-loader-sectorallocation').css("display", "none");
                                      $('.sec-allocation-component').css("visibility", "visible");

                                  }
                              } else {
                                  $('.sec-allocation-component').css("display", "none");
                                  $('.error-message-sectorallocation').css("display", "block");
                              }
                          } catch (err) {
                              $('.error-message-sectorallocation').css("display", "block");
                              $('.dynamic-loader-sectorallocation').css("display", "none");
                              $(".as_of").css("visibility", "hidden");
                          }

                      },
                      error: function(error) {
                          $('.sec-allocation-component').css("visibility", "hidden");
                          $(".as_of").css("visibility", "hidden");
                          sectorAllocationErrorHandlingFuc();
                      }
                  });
              }
              if($('.regionallocation').length > 0 && $.trim($("div.regionallocation").html()).length > 0) {
                  $.ajax({
                        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                        context: this,
                        type: 'GET',
                        url: json["regionallocation"],
                        dataType: "json",
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader("x-api-key", productapiKey);
                            $('.region-allocation-component').css("visibility", "hidden");
                            beforeRegionAllocationFuc();
                        },
                        success: function(response) {
                            try {
                                ajaxData_regionallocation = response;
                                var keys = Object.keys(response);
                                var count = 0;
                                if (keys) {
                                    if (jQuery.isEmptyObject(response)) {
                                        count++;
                                    }
                                    if (count == keys.length) {
                                        $('.region-allocation-component').css("display", "none");
                                        $('.error-message-regionallocation').css("display", "block");
                                    } else {
                                        region_allocation(ajaxData_productLabels,ajaxData_regionallocation);
                                        //$(".alloaction-asset-asof").html(response.as_of_date);
                                        $('.dynamic-loader-regionallocation').css("display", "none");
                                        $('.region-allocation-component').css("visibility", "visible");

                                    }
                                } else {
                                    $('.region-allocation-component').css("display", "none");
                                    $('.error-message-regionallocation').css("display", "block");
                                }
                            } catch (err) {
                                $('.error-message-regionallocation').css("display", "block");
                                $('.dynamic-loader-regionallocation').css("display", "none");
                                $(".as_of").css("visibility", "hidden");
                            }

                        },
                        error: function(error) {
                            $('.region-allocation-component').css("visibility", "hidden");
                            $(".as_of").css("visibility", "hidden");
                            regionAllocationErrorHandlingFuc();
                        }
                  });
              }
    });

    function toggleTableBoxshadow() {
        $(".tableShadowLeft").hide();
        let size = $(window).width();

        $(".table-wrap").scroll(function() {
            if (size <= 768) {
                if ($(this)[0].scrollWidth - Math.round($(this)[0].scrollLeft) == $(this)[0].offsetWidth) {
                    $(this).siblings(".tableShadowRight").hide();
                } else {
                    $(this).siblings(".tableShadowRight").show();
                    $(this).siblings(".tableShadowLeft").show();
                }
                if ($(this)[0].scrollLeft == 0) {
                    $(this).siblings(".tableShadowLeft").hide();
                }
            }

        });
    }

    setTimeout(function(){
        toggleTableBoxshadow();
    }, 1000);


    // on window resize
    $(window).resize(function () {
        toggleTableBoxshadow();
    });

    $('.sticky-tab-container .collapse').on('shown.bs.collapse', function() {
        hideGradient();
    })

    let windowsize = $(window).width();
    if (windowsize <= 768) {
        $('[data-toggle="tooltip"]').tooltip({
            trigger: 'click',
            placement: 'bottom'
        })

    }
    else{
        $('[data-toggle="tooltip"]').tooltip({
            trigger: 'hover'
        })
    }
    $('.time-tabs, .annual-time-tab', '.annualized-time-tabs a').attr('aria-selected', 'false');
    $('.time-tabs:first-child, .annual-time-tab:first-child', '.annualized-time-tabs a').attr('aria-selected', 'true');

    $('.time-tabs a, .annualized-time-tabs a').on('keydown', function(e){

        if ((e.which == 39) || (e.which == 40)) {
            $(this).parent().next().find('a').attr('tabindex', '0').focus();
            return;
         }
         if ((e.which == 37) || (e.which == 38)) {
            $(this).parent().prev().find('a').attr('tabindex', '0').focus();
            return;
         }
         if( (e.which == 32) || (e.which == 13) ){
            $('.time-tabs a, .annual-time-tab a').attr('aria-selected', 'false');
            setTimeout(function() {
                $('.time-tabs a').attr('aria-selected','false');
                $('.time-tabs.active a').attr('aria-selected', 'true');
            }, 100);
            setTimeout(function() {
                $('.annualized-time-tabs a').attr('aria-selected','false');
                $('.annualized-time-tabs.active a').attr('aria-selected', 'true');
            }, 100);
            $(this).parents('ul').find('li.active a').attr('tabindex', '0');
         }
    })
     $('.annualized-time-tabs').click(function() {
        $(".annualized-time-tabs a").attr('aria-selected', 'false');
        $(".annualized-time-tabs.active a").attr('aria-selected', 'true');
    });
    $('.time-tabs a, .annualized-time-tabs a').on('keydown', function(e){
        if( e.which == 9 ){
            $(this).parent().siblings('li').find('a').attr('tabindex', '-1');
            $(this).parents('ul').find('li.active a').attr('tabindex', '0');
         }
    });
    $('a.list-view, a.table-view, a.chart-view').on('keydown', function(e){
        if((e.which==40) || (e.which==38)){
         $(this).siblings('a').focus().addClass('active');
         $(this).removeClass('active');
        }
    })

    var $info = $('.icon-tooltip');
    $info.on('focus', function() {
        var tooltipContent = $(this).parents('.heading').find('a.icon-tooltip').attr('data-title');
        $(this).parents('.heading').find('.tooltip-content').append(tooltipContent);
        $(this).tooltip('show');
        }).on('blur', function(){
            $(this).parents('.heading').find('.tooltip-content').empty();
            $(this).tooltip('hide');
    });
});

//Gradient Show and Hide FUnction Starts
function hideGradient() {
    let windowsize = $(window).width();
    if (windowsize <= 768) {
        $(".table-wrap").each(function() {
            let tableWidth = $(this).find('table').width();
            let tableWrapperWidth = $(this).width();
            if(tableWidth > tableWrapperWidth) {
                $(this).siblings(".tableShadowRight").show();
                $(this).siblings(".tableShadowLeft").hide();
            }else{
                $(this).siblings(".tableShadowLeft").hide();
                $(this).siblings(".tableShadowRight").hide();
            }
        });

    }
}
function toTitleCase(str) {
		return str.replace(/(?:^|\s)\w/g, function (match) {
			return match.toUpperCase();
		});
}

function tableRender(data, index) {
    let x = index;
    row = $("<tr/>");
    let td = '<td class="text-left">' + toTitleCase(index.replace(/[\[\]']+/g, '')) + '</td>';
    data.forEach((ele, ind) => {
    	let className = '';
        if (ind != data.length) {
        let element = parseFloat(ele[index]).toFixed(2);
        if(element == 'NaN'){
        	className = 'isNan';
            element = 'N/A';
        }else{
            element = element;
        }
        td = td + '<td class="' + className + '">' + element + '</td>';
    }
	});
	row.append(td);
}

function selectboxChange(cbk){
    $('.productSelectBox select').on('change', function() {
        if(sharedJS.getCookie("subdomain_user_entity_type")!== "ussa_member") {
            var slectedClass = $(this).val().split(' ')[1];
            cbk(slectedClass);
        }

    })
}

function sortTable(parentClass){
    tablerows = parentClass + ".tableSort tbody  tr";
    tablesort = parentClass + ".tableSort";
    var rows = $(tablerows).get();
    rows.sort(function(a, b) {
    var A = $(a).children('td').eq(0).text().toUpperCase();
    var B = $(b).children('td').eq(0).text().toUpperCase();
    if(A < B) {
      return -1;
    }
    if(A > B) {
      return 1;
    }
    return 0;
    });
    $.each(rows, function(index, row) {
      $(tablesort).children('tbody').append(row);
    });
  }

function characteristicsTableRender(data, index, indexvalue) {
    let x = index;
		let increment = 0;
    row = $("<tr/>");
    let td = '<td class="text-left">' + indexvalue + '</td>';
    data.forEach((ele, ind) => {
    	let className = '';
        if (ind != data.length) {
            let element = parseFloat(ele[index]).toFixed(2);
            if (element == 'NaN') {
            	className = 'isNan';
                element = 'N/A';
				increment++;
            } else if (element % 1 == 0) {
            	element = Math.floor(element).toString().replace(/(.)(?=(.{3})+$)/g, "$1,");
            } else {
                element = element;
            }
            td = td + '<td class="' + className + '">' + element + '</td>';

        }
    });
    if (increment != data.length) {
		row.append(td);
	}
}


function hideNullColumns(tableclass) {
	var columns = $(tableclass +" > tbody > tr:last > td").length;
	for (var i = 0; i < columns; i++) {
		if ($(tableclass + " > tbody > tr > td:nth-child(" + (i + 1) + ")").filter(function () {
				return $(this).text() !== 'N/A';
			}).length == 0) {
			$(tableclass +" > tbody > tr > td:nth-child(" + (i + 1) + "), "  + tableclass + " > thead > tr > th:nth-child(" + (i + 1) + ")").hide();
		}
	}

	var rows = $(tableclass +" > tbody > tr");
	$(rows).each(function (i, item) {
		if($(item).children('td').length<=1){
            $(this).remove();
        }
		$(item).find('td').each(function () {
			if ($(this).is(":visible") && $(this).text() === 'N/A') {
				$(this).closest('tr').hide();
			}
		});
	});
}

function loadApiData(fundApiUrl, fundIdVal, fundApiKey) {
    $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        context: this,
        type: 'GET',
        async: false,
        cache: false,
        timeout: 30000,
        url: fundApiUrl,
        dataType: "json",
        beforeSend: function(xhr) {
            $('.tab-content').css("visibility", "hidden");
            $('.dynamic-loader').css("display", "block");
            $('.error-message').css("display", "none");
            xhr.setRequestHeader("x-api-key", fundApiKey);
        },
        success: function(response) {
            if (response.length) {
                var apiData = response;

                apiData = apiData.filter(res => res.fundId == fundIdVal);
                var scl='';
                for(var j=0; j < apiData[0].classes.length; j++) {
                    scl=apiData[0].classes[j].share_class;
                    var classes = apiData[0].classes[j];
                    classes.fundId = apiData[0].fundId;
                    if( (scl=='Fund')||(scl=='Reward')||(scl=='Member') ){  /* User: Member => API Data categoried with share class */
                        var listingPageLS = [];
                        listingPageLS[0] = scl;
                        listingPageLS[1] = fundIdVal;
                        listingPageLS[2] = "false";
                        localStorage.setItem("listingPageLS", JSON.stringify(listingPageLS));
                        localStoreValues = JSON.parse(localStorage.getItem("listingPageLS"));
                    }
                }
            }
        },
        error: function(jqXhr, textStatus, errMessage) {
            console.log("error :"+ errMessage);
        }
    });
}