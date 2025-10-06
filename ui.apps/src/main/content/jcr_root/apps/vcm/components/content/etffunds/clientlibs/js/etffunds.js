$(document).ready(function() {
    var etfApiUrl=$("#etfListEndpoint").val();
    var etfApiKey=$("#etfListApiKey").val();
    var largeCap=[];
    var midCap=[];
    var smallCap=[];
    var emergingMkts=[];
    var internationalEquity=[];
    var fixedIncome=[];

    $('body').on('mousedown', function() {
        $('body').addClass('using-mouse');
    });
    // Re-enable focus styling when Tab is pressed
    $('body').on('keydown', function(event) {
        if (event.keyCode === 9) {
            $('body').removeClass('using-mouse');
        }
    });
    if(document.getElementById('etfListData').innerHTML !== ''){
        var aemData = JSON.parse(document.getElementById('etfListData').innerHTML);
    }
    ETFLoadApiDataForFinance();
    function ETFLoadApiDataForFinance() {
                $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    context: this,
                    type: 'GET',
                    url: etfApiUrl,
                    dataType: "json",
                    beforeSend: function(xhr) {
                        $('.tab-content').css("visibility", "hidden");
                        $('.dynamic-loader').css("display", "block");
                        $('.error-message').css("display", "none");
                        xhr.setRequestHeader("x-api-key", etfApiKey);
                    },
                    success: function(response) {
                        var count = 0;
                        if (response.length) {
                            response.filter(resp => {
                                if (jQuery.isEmptyObject(resp)) {
                                    count++;
                                }
                            });
                            if (count == response.length) {
                                $('.tab-content').css("display", "none");
                                $('.error-message').css("display", "block");

                    } else {

                        var filtered = response.filter(function(element) {
                            if(element.category === 'Large Blend' || element.category === 'Large Value'){
                                largeCap.push(element);
                            }else if(element.category === 'Mid-Cap Blend' || element.category === 'Mid-Cap Growth'){
                                midCap.push(element);
                            }else if(element.category === 'Small Blend' || element.category === 'Small Value'){
                                smallCap.push(element);
                            }else if(element.category === 'Diversified Emerging Mkts'){
                                emergingMkts.push(element);
                            }else if(element.category === 'Foreign Large Blend' || element.category === 'Foreign Large Value'){
                                internationalEquity.push(element);
                            }else if(element.category === 'Short-Term Bond' || element.category === 'Intermediate Core Bond'||
                               element.category === 'Intermediate Core-Plus Bond' || element.category === 'Corporate Bond'){
                                fixedIncome.push(element);
                            }
                        });

                        if(largeCap.length > 0 ){
                            let subArrLargeCap=[];
                            for(let j=0; j < aemData.length; j++) {

                           		for (let i = 0; i < largeCap.length; i++){

                                    if(largeCap[i].entity_id == aemData[j].fundId){

                                        let subArrLargeCapObj={
                                           "entity_id":largeCap[i].entity_id,
                                           "ticker" : largeCap[i].ticker,
                                           "entity_long_name" : largeCap[i].entity_long_name,
                                           "asset_class" : largeCap[i].asset_class,
                                           "inception_date" : largeCap[i].inception_date,
                                           "gross_exp_ratio" : largeCap[i].gross_exp_ratio,
                                           "net_expense_ratio" : largeCap[i].net_expense_ratio,
                                           "solution_type" : largeCap[i].filter.solution_type,
                                           "detailpageurl" : aemData[j].detailpageurl,
                                           "factsheeturl" : aemData[j].factsheeturl
                                       };
                                       subArrLargeCap.push(subArrLargeCapObj);

                                    }


                               }
                            }
                            createTable(subArrLargeCap, 'etf-table-advisor-large-cap');
                        }
                        if(midCap.length > 0 ){
                            let subArrMidCap=[];
                            for(let j=0; j < aemData.length; j++) {

                                for (let i = 0; i < midCap.length; i++){

                                    if(midCap[i].entity_id == aemData[j].fundId){

                                        let subArrMidCapObj={
                                           "entity_id":midCap[i].entity_id,
                                           "ticker" : midCap[i].ticker,
                                           "entity_long_name" : midCap[i].entity_long_name,
                                           "asset_class" : midCap[i].asset_class,
                                           "inception_date" : midCap[i].inception_date,
                                           "gross_exp_ratio" : midCap[i].gross_exp_ratio,
                                           "net_expense_ratio" : midCap[i].net_expense_ratio,
                                           "solution_type" : midCap[i].filter.solution_type,
                                           "detailpageurl" : aemData[j].detailpageurl,
                                           "factsheeturl" : aemData[j].factsheeturl
                                       };
                                       subArrMidCap.push(subArrMidCapObj);

                                    }


                               }
                            }
                            createTable(subArrMidCap, 'etf-table-advisor-mid-cap');
                        }
                        if(smallCap.length > 0 ){
                            let subArrSmallCap=[];
                            for(let j=0; j < aemData.length; j++) {

                                for (let i = 0; i < smallCap.length; i++){

                                    if(smallCap[i].entity_id == aemData[j].fundId){

                                        let subArrSmallCapObj={
                                           "entity_id":smallCap[i].entity_id,
                                           "ticker" : smallCap[i].ticker,
                                           "entity_long_name" : smallCap[i].entity_long_name,
                                           "asset_class" : smallCap[i].asset_class,
                                           "inception_date" : smallCap[i].inception_date,
                                           "gross_exp_ratio" : smallCap[i].gross_exp_ratio,
                                           "net_expense_ratio" : smallCap[i].net_expense_ratio,
                                           "solution_type" : smallCap[i].filter.solution_type,
                                           "detailpageurl" : aemData[j].detailpageurl,
                                           "factsheeturl" : aemData[j].factsheeturl
                                       };
                                       subArrSmallCap.push(subArrSmallCapObj);

                                    }


                               }
                            }
                            createTable(subArrSmallCap,'etf-table-advisor-small-cap');
                        }
                        if(emergingMkts.length > 0 ){
                            let subArrEmergingMkts=[];
                            for(let j=0; j < aemData.length; j++) {

                                for (let i = 0; i < emergingMkts.length; i++){

                                    if(emergingMkts[i].entity_id == aemData[j].fundId){

                                        let subArrEmergingMktsObj={
                                           "entity_id":emergingMkts[i].entity_id,
                                           "ticker" : emergingMkts[i].ticker,
                                           "entity_long_name" : emergingMkts[i].entity_long_name,
                                           "asset_class" : emergingMkts[i].asset_class,
                                           "inception_date" : emergingMkts[i].inception_date,
                                           "gross_exp_ratio" : emergingMkts[i].gross_exp_ratio,
                                           "net_expense_ratio" : emergingMkts[i].net_expense_ratio,
                                           "solution_type" : emergingMkts[i].filter.solution_type,
                                           "detailpageurl" : aemData[j].detailpageurl,
                                           "factsheeturl" : aemData[j].factsheeturl
                                       };
                                       subArrEmergingMkts.push(subArrEmergingMktsObj);

                                    }
                               }
                            }
                            createTable(subArrEmergingMkts, 'etf-table-advisor-emrg-mrkt');
                        }
                        if(internationalEquity.length > 0 ){
                            let subArrInternationalEquity=[];
                            for(let j=0; j < aemData.length; j++) {

                                for (let i = 0; i < internationalEquity.length; i++){

                                    if(internationalEquity[i].entity_id == aemData[j].fundId){

                                        let subArrInternationalEquityObj={
                                           "entity_id":internationalEquity[i].entity_id,
                                           "ticker" : internationalEquity[i].ticker,
                                           "entity_long_name" : internationalEquity[i].entity_long_name,
                                           "asset_class" : internationalEquity[i].asset_class,
                                           "inception_date" : internationalEquity[i].inception_date,
                                           "gross_exp_ratio" : internationalEquity[i].gross_exp_ratio,
                                           "net_expense_ratio" : internationalEquity[i].net_expense_ratio,
                                           "solution_type" : internationalEquity[i].filter.solution_type,
                                           "detailpageurl" : aemData[j].detailpageurl,
                                           "factsheeturl" : aemData[j].factsheeturl
                                       };
                                       subArrInternationalEquity.push(subArrInternationalEquityObj);

                                    }
                               }
                            }
                            createTable(subArrInternationalEquity,'etf-table-advisor-inter-equity');
                        }
                        if(fixedIncome.length > 0 ){

                            let subArrFixedIncome=[];
                            for(let j=0; j < aemData.length; j++) {

                                for (let i = 0; i < fixedIncome.length; i++){

                                    if(fixedIncome[i].entity_id == aemData[j].fundId){

                                        let subArrFixedIncomeObj={
                                           "entity_id":fixedIncome[i].entity_id,
                                           "ticker" : fixedIncome[i].ticker,
                                           "entity_long_name" : fixedIncome[i].entity_long_name,
                                           "asset_class" : fixedIncome[i].asset_class,
                                           "inception_date" : fixedIncome[i].inception_date,
                                           "gross_exp_ratio" : fixedIncome[i].gross_exp_ratio,
                                           "net_expense_ratio" : fixedIncome[i].net_expense_ratio,
                                           "solution_type" : fixedIncome[i].filter.solution_type,
                                           "detailpageurl" : aemData[j].detailpageurl,
                                           "factsheeturl" : aemData[j].factsheeturl
                                       };
                                       subArrFixedIncome.push(subArrFixedIncomeObj);
                                    }
                               }
                            }
                            createTable(subArrFixedIncome, 'etf-table-advisor-fixed');
                        }

                               // tableInfo();
                                $('.dynamic-loader').css("display", "none");
                                $('.tab-content').css("visibility", "visible");
                                $('.funds-list .fixedTabs').css("display", "block");
                                if (screen.width >= 768)
                                {
                                 $('.funds-list .filter-sidebar-collapse').css("display", "block");
                                }
                            }
                        } else {
                            $('.tab-content').css("display", "none");
                            $('.error-message').css("display", "block");
                        }
                    },
                    error: function(jqXhr, textStatus, errorMessage) {
                        $(".etf-errormsg").append(ajaxData_etfLabels.etflistingLabels.errorMsg);
                        $('.tab-content').css("display", "none");
                        $('.dynamic-loader').css("display", "none");
                        $('.error-message').css("display", "block");
                    }
                });
            }

    function createTable(tableValues, tableId){
		var tableHead = '<thead> <tr> <th> <span aria-hidden="true">Ticker</span> </th> <th>Fund Name</th> <th>Asset Class</th> <th>Inception Date</th>  <th>Exp Ratio (Gross)</th><th>Exp Ratio (Net)</th> <th>Solution Type</th> </tr> </thead>';
		tableId = '#'+tableId;
        $(tableId).append(tableHead);
        var eftTableData ='';
        for (var i = 0; i < tableValues.length; i++) {
            eftTableData = '<tbody><tr><td><p><b>'+
                			tableValues[i].ticker+
                                '</b></p> </td>'+
                                    ' <td> <a href='+tableValues[i].detailpageurl+
                                            '>'+tableValues[i].entity_long_name+
                                            '</a></td>'+
                                        '<td>'+tableValues[i].asset_class+'</td>'+
                                        '<td>'+tableValues[i].inception_date+'</td>'+
                                        '<td>'+Math.round (tableValues[i].gross_exp_ratio * 100) / 100 +'%</td>'+
                                        '<td>'+Math.round (tableValues[i].net_expense_ratio * 100) / 100 +'%</td>'+
                                            '<td>'+tableValues[i].solution_type+'</td> </tr></tbody>';
            $(tableId).append(eftTableData);

        }

    }

   /* function tableInfo() {
        var sorttxt;
        if ($('.fundslistTable thead th').hasClass('sorting_asc')) {
            sorttxt = $('.sorting_asc').first().text() + " in ascending order";
        } else if ($('.fundslistTable thead th').hasClass('sorting_desc')) {
            sorttxt = $('.sorting_desc').first().text() + " in descending order";
        }
        $(".sr-only.table-info").html($(".dataTables_info").text() + " of " + $(".lblTblCaption").text() + " table sorted by " + sorttxt);
    }*/

});