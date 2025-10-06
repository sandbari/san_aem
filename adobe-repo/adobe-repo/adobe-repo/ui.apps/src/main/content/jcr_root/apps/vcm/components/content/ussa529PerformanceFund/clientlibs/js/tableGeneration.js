var jsonData = [];

$(function() {

    let ussa529PerformanceFundEndPoint = $("#ussa529PerformanceFundEndPoint").val();
    let ussa529PerformanceFundKey = $("#ussa529PerformanceFundKey").val();
    let plantable = $('.planTable')
    if (plantable.length > 0 && ussa529PerformanceFundEndPoint != "undefined" && ussa529PerformanceFundEndPoint != null) {
        $.ajax({
        	contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            type: "GET",
            url: ussa529PerformanceFundEndPoint,
            dataType: "json",
            beforeSend: function(xhr) {
				 $('.planTableLoader').css("display", "block");
				 xhr.setRequestHeader("x-api-key",ussa529PerformanceFundKey);
            },
            success: function(data) {
            	 $(".planTableLoader").hide();
                 jsonData = data;
                 // quarterlyTable
                 var $quarterlyTable = $('<table>', { class: 'table table-striped' });
                 $quarterlyTable.append('<caption><h2 class="planTableHeading">Average Annual Total Returns (Reported Quarterly) <span>(As of ' + jsonData[0].performance.quarterly.as_of + ')</span></h2></caption>').append('<thead>').children('thead').append('<tr />').children('tr').append('<th><span aria-hidden="true">Portfolio | Age Based</span><span class="sr-only"> portfolio & Age Based</span></th><th><span aria-hidden="true">1 yr</span><span class="sr-only">1 year </span></th><th><span aria-hidden="true">3 yr</span><span class="sr-only">3 year </span></th><th><span aria-hidden="true">5 yr</span><span class="sr-only">5 year </span></th><th><span aria-hidden="true">10 yr</span><span class="sr-only">10 year </span></th><th>Since Inception</th><th>Inception Date</th><th>Total Annual Fees<sup>a</sup></th>');
                 var $quarterlyTableTbody = $quarterlyTable.append('<tbody />').children('tbody');
                 $quarterlyTable.appendTo('#quarterlyTableContainer');

                 // monthlyTable
                 var $monthlyTable = $('<table>', { class: 'table table-striped' });
                 $monthlyTable.append('<caption><h2 class="planTableHeading">Average Annual Total Returns (Reported Monthly) <span>(As of ' + jsonData[0].performance.monthly.as_of + ')</span></h2></caption>').append('<thead>').children('thead').append('<tr />').children('tr').append('<th><span aria-hidden="true">Portfolio | Age Based</span><span class="sr-only"> portfolio & Age Based</span></th><th><span aria-hidden="true">1 mo</span><span class="sr-only">1 month </span></th><th><span aria-hidden="true">3 mo</span><span class="sr-only">3 month </span></th><th><span aria-hidden="true">1 yr</span><span class="sr-only">1 year </span></th><th><span aria-hidden="true">3 yr</span><span class="sr-only">3 year </span></th><th><span aria-hidden="true">5 yr</span><span class="sr-only">5 year </span></th><th><span aria-hidden="true">10 yr</span><span class="sr-only">10 year </span></th><th>Since Inception</th><th>Inception Date</th>');
                 var $monthlyTableTbody = $monthlyTable.append('<tbody />').children('tbody');
                 $monthlyTable.appendTo('#monthlyTableContainer');

                 // dailyTable
                 var $dailyTable = $('<table>', { class: 'table table-striped' });
                 $dailyTable.append('<caption><h2 class="planTableHeading">Daily Prices and Year-to-Date Returns <span>(As of ' + jsonData[0].daily_as_of_date +')</span></h2></caption>' ).append('<thead>').children('thead').append('<tr />').children('tr').append('<th><span aria-hidden="true">Portfolio | Age Based</span><span class="sr-only"> portfolio & Age Based</span></th><th>Price</th><th>$ Change</th><th>% Change</th><th>YTD Return</th>');
                 var $dailyTableTbody = $dailyTable.append('<tbody />').children('tbody');
                 $dailyTable.appendTo('#dailyTableContainer');

                 // feeTable
                 var $feeTable = $('<table>', { class: 'table table-striped' });
                 $feeTable.append('<caption><h2 class="planTableHeading">Plan Fees and Expenses</h2></caption>' ).append('<thead>').children('thead').append('<tr />').children('tr').append('<th><span aria-hidden="true">Investment options | Age Based</span><span class="sr-only"> Investment options & Age Based</span></th><th>Estimated Underlying Funds Weighted-Average Expense Ratio<sup>a</sup></th><th>Program Management Fee<sup>b</sup></th><th>Total Annual Asset-Based Expense</th><th>Annual Minimum Balance Fee<sup>c</sup></th>');
                 var $feeTableTbody = $feeTable.append('<tbody />').children('tbody');
                 $feeTable.appendTo('#feeTableContainer');
                 jsonData.sort((a, b) => {
                	 
                     return parseFloat(a.entity_id.slice(4, 12)) - (b.entity_id.slice(4, 12));
      
                 })
                 function parseFloatValue(value) {
                     if (!value || value == "N/A") {
                         value = "N/A";
                     } else
                         value = parseFloat(value).toFixed(2) + "%";
                     return value;
                 }
                 function parseValueDollar(value) {
                     if (!value || value == "N/A") {
                         value = "N/A";
                     } else
                         value = parseFloat(value).toFixed(2);
                     return value;
                 }
                 //parseFloat(subTotal || 0).toFixed(2);
                 jsonData.forEach(function(tdata) {

                    var portfolioName = tdata.portfolio_name;
                    var newStrSecond;
                    var newStrFirst;
                    if (portfolioName.includes("|")) {
                        newStrSecond = portfolioName.split('|')[1];
                        newStrFirst = portfolioName.split('|')[0];
                    } else {
                        newStrFirst = tdata.portfolio_name;
                        newStrSecond = "";
                    }

                     //Quarterly
                     $('<tr><td><p><b>' + newStrFirst + '</b></p><p>' + newStrSecond + '</p></td><td>' + parseFloatValue(tdata.performance.quarterly.oneyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.quarterly.threeyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.quarterly.fiveyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.quarterly.tenyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.quarterly.since_inception_nav) + '</td><td>' + tdata.inception_date + '</td><td>' + parseFloat(tdata.total_annual_fees).toFixed(2) + '%</td></tr>').appendTo($quarterlyTableTbody);

                     //Monthly
                     $('<tr><td><p><b>' + newStrFirst + '</b></p><p>' + newStrSecond + '</p></td><td>' + parseFloatValue(tdata.performance.monthly.onemonth_nav) + '</td><td>' + parseFloatValue(tdata.performance.monthly.threemonth_nav) + '</td><td>' + parseFloatValue(tdata.performance.monthly.oneyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.monthly.threeyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.monthly.fiveyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.monthly.tenyear_nav) + '</td><td>' + parseFloatValue(tdata.performance.monthly.since_inception_nav) + '</td><td>' + tdata.inception_date + '</td></tr>').appendTo($monthlyTableTbody);

                     //daily
                     var change = parseValueDollar(tdata.daily_dollar_change);
                     var actualChnageValue;
                     if (change.includes("-")) {
                         var res = change.split("-");
                         var actualValue = res[1];
                         actualChnageValue = "-$" + actualValue;
                     } else {
                         actualChnageValue = "$" + change;
                     }
                     if (change == "-0.00") {
                         actualChnageValue = "$0.00";
                     }
                     $('<tr><td><p><b>' + newStrFirst + '</b></p><p>' + newStrSecond + '</p></td><td>$' + parseValueDollar(tdata.price) + '</td><td>' + actualChnageValue + '</td><td>' + parseFloatValue(tdata.daily_percentage_change) + '</td><td>' + parseFloatValue(tdata.performance.monthly.ytd_nav) + '</td></tr>').appendTo($dailyTableTbody);

                     //fee&expenses
                     if (newStrFirst == "Preservation of Capital Portfolio") {
                         newStrFirst = newStrFirst + "<sup>d</sup>";

                     } 
                     $('<tr><td><p><b>' + newStrFirst + '</b></p><p>' + newStrSecond + '</p></td><td>' + parseFloatValue(tdata.net_expense_ratio) + '</td><td>' + parseFloatValue(tdata.management_fees) + '</td><td>' + parseFloatValue(tdata.total_annual_asset_based_expenses) + '</td><td>$' + parseValueDollar(tdata.minimum_balance_fees) + '</td></tr>').appendTo($feeTableTbody);

                 });
            },
            error: function(data) {
                console.log('API Call Failed');
                $('.planTableLoader').css("display", "none");
            }
        });

    }
});