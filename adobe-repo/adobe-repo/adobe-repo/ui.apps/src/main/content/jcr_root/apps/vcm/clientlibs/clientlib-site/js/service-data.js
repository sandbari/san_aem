(function($, Granite, ns, $document) {
    "use strict";

    //alert("welcome 1");

	var GetFundTopHoldingDetails =    [{"classes":false,"displayTable":true,"timeFrame":{"type":""},"chart":{"type":""},"xAxis":{"categories":["Ticker","Name"],"initialCategories":""},"yAxis":{"title":{"text":""},"labels":{"formatter":false}},"credits":{"enabled":""},"tooltip":{"valueSuffix":""},"series":[{"name":"ZBH","initialName":"","data":["Zimmer Biomet Holdings Inc."],"afterClassTxt":""},{"name":"PPL","initialName":"","data":["PPL Corporation"],"afterClassTxt":""},{"name":"FITB","initialName":"","data":["Fifth Third Bancorp"],"afterClassTxt":""},{"name":"DRE","initialName":"","data":["Duke Realty Corporation"],"afterClassTxt":""},{"name":"ZION","initialName":"","data":["Zions Bancorporation N.A."],"afterClassTxt":""},{"name":"PEAK","initialName":"","data":["Healthpeak Properties Inc."],"afterClassTxt":""},{"name":"LH","initialName":"","data":["Laboratory Corporation of America Holdings"],"afterClassTxt":""},{"name":"AIV","initialName":"","data":["Apartment Investment and Management Company Class A"],"afterClassTxt":""},{"name":"CPT","initialName":"","data":["Camden Property Trust"],"afterClassTxt":""},{"name":"TSN","initialName":"","data":["Tyson Foods Inc. Class A"],"afterClassTxt":""}],"asOf":"11/30/2019","timeFrameAsOf":"","link":"https://www.vcm.com/docs/default-source/holdings/victory_integrity_mid_cap_value_fund_holdings.pdf?sfvrsn=8a5dc701_4","portfolioPercent":14.120000000000}];
	var GetFundCharacteristicDetails = [{"classes":null,"title":{"text":""},"displayTable":true,"timeFrame":{"type":""},"chart":{"type":""},"xAxis":{"initialCategories":[{"name":"","afterClassTxt":""},{"name":"Fund","afterClassTxt":""},{"name":"Benchmark","afterClassTxt":""}],"categories":[]},"yAxis":{"title":{"text":""},"labels":{"formatter":false}},"credits":{"enabled":false},"tooltip":{"valueSuffix":""},"series":[{"name":"Weighted Average Market Cap ($ Millions)","initialName":"","data":[13579,"15272"],"afterClassTxt":"","dataOrderBy":0},{"name":"EPS Growth - Last 3 years (%)","initialName":"","data":[15.970000000000,"9.830000000000"],"afterClassTxt":"","dataOrderBy":1},{"name":"EPS Growth - Last 5 years (%)","initialName":"","data":[9.480000000000,"7.030000000000"],"afterClassTxt":"","dataOrderBy":2},{"name":"Long-Term Debt/Capital Ratio (%)","initialName":"","data":[41.300000000000,"41.670000000000"],"afterClassTxt":"","dataOrderBy":8},{"name":"Price/Book Ratio (x)","initialName":"","data":[2.060000000000,"2.000000000000"],"afterClassTxt":"","dataOrderBy":9},{"name":"Price to Earnings Ratio - Last 12 months (x)","initialName":"","data":[20.580000000000,"20.200000000000"],"afterClassTxt":"","dataOrderBy":10},{"name":"Return on Equity - Last 12 months (%)","initialName":"","data":[9.750000000000,"11.030000000000"],"afterClassTxt":"","dataOrderBy":11},{"name":"Number of Holdings","initialName":"","data":[109,"633"],"afterClassTxt":"","dataOrderBy":12},{"name":"Turnover Ratio (%)","initialName":"","data":[67.820000000000,"15.930000000000"],"afterClassTxt":"","dataOrderBy":13}],"asOf":"11/30/2019","timeFrameAsOf":"","link":"","portfolioPercent":""}];
    var GetFundSectorDiversificationDetails = [{"classes":false,"title":{"text":""},"displayTable":true,"timeFrame":{"type":""},"chart":{"type":""},"xAxis":{"initialCategories":[{"name":"","afterClassTxt":""},{"name":"Fund","afterClassTxt":"(%)"},{"name":"Benchmark","afterClassTxt":"(%)"}],"categories":[]},"yAxis":{"title":{"text":""},"labels":{"formatter":""}},"credits":{"enabled":false},"tooltip":{"valueSuffix":""},"series":[{"name":"COMMUNICATION SERVICES","initialName":"","data":["1.530000000000","3.840000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"CONSUMER DISCRETIONARY","initialName":"","data":["8.820000000000","9.100000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"CONSUMER STAPLES","initialName":"","data":["4.740000000000","4.510000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"ENERGY","initialName":"","data":["5.360000000000","5.140000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"FINANCIALS","initialName":"","data":["18.230000000000","19.320000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"HEALTH CARE","initialName":"","data":["7.890000000000","6.930000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"INDUSTRIALS","initialName":"","data":["14.720000000000","11.890000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"INFORMATION TECHNOLOGY","initialName":"","data":["10.790000000000","7.380000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"MATERIALS","initialName":"","data":["7.040000000000","6.600000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"REAL ESTATE","initialName":"","data":["11.380000000000","14.300000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"UTILITIES","initialName":"","data":["8.750000000000","10.990000000000"],"afterClassTxt":"","dataOrderBy":0},{"name":"[CASH \u0026 CASH EQUIVALENTS]","initialName":"","data":["0.740000000000","0.000000000000"],"afterClassTxt":"","dataOrderBy":0}],"asOf":"11/30/2019","timeFrameAsOf":"","link":"","portfolioPercent":""}];
	var GetFundAssetAllocationDetails = [{"classes":false,"displayTable":true,"timeFrame":{"type":""},"chart":{"type":""},"xAxis":{"categories":["Ticker","Name"],"initialCategories":""},"yAxis":{"title":{"text":""},"labels":{"formatter":false}},"credits":{"enabled":""},"tooltip":{"valueSuffix":""},"series":[{"name":"ZBH","initialName":"","data":["Zimmer Biomet Holdings Inc."],"afterClassTxt":""},{"name":"PPL","initialName":"","data":["PPL Corporation"],"afterClassTxt":""},{"name":"FITB","initialName":"","data":["Fifth Third Bancorp"],"afterClassTxt":""},{"name":"DRE","initialName":"","data":["Duke Realty Corporation"],"afterClassTxt":""},{"name":"ZION","initialName":"","data":["Zions Bancorporation N.A."],"afterClassTxt":""},{"name":"PEAK","initialName":"","data":["Healthpeak Properties Inc."],"afterClassTxt":""},{"name":"LH","initialName":"","data":["Laboratory Corporation of America Holdings"],"afterClassTxt":""},{"name":"AIV","initialName":"","data":["Apartment Investment and Management Company Class A"],"afterClassTxt":""},{"name":"CPT","initialName":"","data":["Camden Property Trust"],"afterClassTxt":""},{"name":"TSN","initialName":"","data":["Tyson Foods Inc. Class A"],"afterClassTxt":""}],"asOf":"11/30/2019","timeFrameAsOf":"","link":"https://www.vcm.com/docs/default-source/holdings/victory_integrity_mid_cap_value_fund_holdings.pdf?sfvrsn=8a5dc701_4","portfolioPercent":14.120000000000}];


    $( document ).ready(function() {
        getFundTopHoldingDetails();
        getFundCharacteristicDetails();
	});

	function getFundTopHoldingDetails()
    {
		var item = GetFundTopHoldingDetails[0];
        var series = item.series;

		$(series).each(function (index, item) {

            var newRow = '<tr><td>' + item.name + '</td><td>' + item.data + '</td></tr>';
        	$('#top10 tbody').append(newRow);
        });

    }

    function getFundCharacteristicDetails()
    {
		var item = GetFundCharacteristicDetails[0];
        var series = item.series;

		$(series).each(function (index, item) {

            var newRow = '<tr><td>' + item.name + '</td><td>' + item.data[0] + '</td><td>' + item.data[1] + '</td></tr>';
        	$('#char-is-tics tbody').append(newRow);
        });

    }



}(jQuery, Granite, Granite.author, jQuery(document)));
