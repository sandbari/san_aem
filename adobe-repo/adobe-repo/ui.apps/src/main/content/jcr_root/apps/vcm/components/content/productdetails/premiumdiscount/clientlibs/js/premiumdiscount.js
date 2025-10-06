var premiumdiscount_tableData = function( data){
    if (data) {
        var tBody = $("<tbody/>");
        $(".premium-discount-asof").append(data.data[data.data.length-1].effectiveDate);
        //$(".premium-discount-asof").append(data.premiumDiscountHistory.effectiveDate);
        tBody.append(`<tr><td>Premium</td><td>${data.premiumDiscountHistory.previousYearPremium}</td> <td>${data.premiumDiscountHistory.quarter1Premium}</td> <td>${data.premiumDiscountHistory.quarter2Premium}</td> <td>${data.premiumDiscountHistory.quarter3Premium}</td></tr>`);
        tBody.append(`<tr><td>Discount</td><td>${data.premiumDiscountHistory.previousYearDiscount}</td> <td>${data.premiumDiscountHistory.quarter1Discount}</td> <td>${data.premiumDiscountHistory.quarter2Discount}</td> <td>${data.premiumDiscountHistory.quarter3Discount}</td></tr>`);
        $(".premium-table").append(tBody);
        $('.premium-table tr td:not(:first-child)').each(function() {
            var cellText = $(this).html();
            if((isNaN(cellText)) || (cellText==''))  {
                $(this).html('N/A')
            }
        });
    }

}
var premiumdiscount_tableHead = function(data){
	var tHead = $("<thead/>");
	var headRow = $("<tr/>");
	if (data) {
        $(".premium-discount.header-label").prepend(data.premiumdiscount);
        $('.num-of-days').append(data.premiumdiscounttable);
        headRow.append(`<th scope='col'><span class="sr-only">premium or discount</span></th>`);
        let fundLables = Object.values(data.PremiumDiscount);
        fundLables.forEach((item, index)=>{
            headRow.append(`<th scope='col'>${item}</th>`);
        })
    }
    tHead.append(headRow);
    $(".premium-table").append(tHead);
}
var createLineChart = function( data){
    var updateData = [];
    function comp(a, b) {
      return new Date(a.effectiveDate).getTime() - new Date(b.effectiveDate).getTime();
    }
    data.data.sort(comp);

    var dataAllDaysTs = data.data.map(el => {
        return [Date.parse(`${el.effectiveDate} 00:00:00 GMT`), parseFloat(`${el.premiumDiscountPercentage}`)];
    });
    //dataAllDaysTs.map(el => console.log(new Date(el[0]).toUTCString()))

    var finalData = {
        xAxis: {
            endOnTick: false,
            startOnTick: false,
            minPadding: 0,
            min: dataAllDaysTs[0][0],
            max: dataAllDaysTs[dataAllDaysTs.length - 1][0],
            labels: {
                zIndex: 7,
                align: "center"
            },
            type: "datetime",
            crosshair: {
                color: "blue",
                dashStyle: "Solid",
                snap: "true",
                width: 1,
                zIndex: 2
            },
            labels: {
                formatter: function() {
                    var h = new Date(this.value)
                      , l = h.getUTCDate()
                      , q = h.getUTCMonth();
                    h = h.getUTCFullYear();
                    return Highcharts.dateFormat("%b-%y", Date.UTC(h, q, l))
                },
                staggerLines: 2
            },
            tickPositioner: function() {
                for (var h = [], l = 0; l < this.tickPositions.length; l++)
                    h.push(this.tickPositions[l] - 864E5);
                return h
            },
            events: {
                // afterSetExtremes: event => {
                //     console.log(event);
                //     console.log(new Date(event.min).toUTCString());
                //     // event.target.chart.setOptions(xAxis:{min :event.min});
                //     console.log(new Date(event.target.chart.xAxis.min).toUTCString());
                // }
                afterSetExtremes: function(h) {
                    if ("rangeSelectorButton" === h.trigger) {

                        var l = this.chart;
                        "previousYear" === h.rangeSelectorButton.type ? setTimeout(function() {
                            l.xAxis[0].setExtremes(Math.max(b[0].x, (new Date(Date.UTC((new Date).getFullYear() - 1, 0, 1))).getTime()), (new Date(Date.UTC((new Date).getFullYear() - 1, 11, 31))).getTime())
                        }, 1) : "previousQuarter" === h.rangeSelectorButton.type && setTimeout(function() {
                            l.xAxis[0].setExtremes(Math.max(b[0].x, lastQuarterStartDate.getTime()), lastQuarterEndDate.getTime())
                        }, 1)
                    }
                }
            }
        },
      rangeSelector: {

            allButtonsEnabled: true,
          selected: 4
        },
        yAxis: {
            opposite: false,
            title: {
                text: 'Premium/Discount (%)'
            }
        },


        navigator: {
          enabled: true
        },
        scrollbar: {
        enabled: false
         },
         credits: {
          enabled: false
        },

        series: [{
                   name: 'Premium/Discount:',

                   data:dataAllDaysTs
               }]
        };


      Highcharts.setOptions({
          colors: colorList
      });
      Highcharts.stockChart('premium-discount-chart', finalData);
}

var beforePremiumDiscountLoadFuc = function () {
    $('.dynamic-loader-premiumdiscount').css("display", "block");
    $('.error-message-premiumdiscount').css("display", "none");
}

var PremiumDiscountErrorHandlingFuc = function () {
	$('.premium-discount-component').css("visibility", "visible");
    $('.dynamic-loader-premiumdiscount').css("display", "none");
    $('.error-message-premiumdiscount').css("display", "block");
}

$(document).ready(function() {
      $(".premium-discount-table").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
      $(".premium-discount-table").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});