var allocation_assetclass_tablehead = function(data){
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        var headerValue = data.allocationAssetClass;
        var values = Object.values(headerValue)
        values.forEach(item => {
            headRow.append(`<th>${item}</th>`);
        })
    }
    tHead.append(headRow);
    $(".allocation-class-table").append(tHead);
}

/* Appending api data to asset-allocation table */

var allocation_assetclass_tablebody = function(data){
    var tBody = $("<tbody/>");
    if (data) {
        //$(".asset-allocation-asof").append(data.as_of_date);
        let assetAllocationData = data.asset_class_allocation;
        assetAllocationData.forEach(assetData => {
            if ((assetData === null) || (assetData === undefined) || (assetData === "")) {
                assetData = "N/A"
            } else {
                var bodyRow = $("<tr/>");
                bodyRow.append($(`<td class='text-left'>${assetData.asset_class}</td>`));
                bodyRow.append($(`<td>${parseFloat(assetData.asset_class_percentage).toFixed(2)}</td>`));
            }
            tBody.append(bodyRow);
        });
    }
    $(".allocation-class-table").append(tBody);
}

var allocation_assetclass_body = function(data){
    var updateData = [];
	var asofdate, aemLabels;
    if (data) {
    var seriesData = [];
    var finalData = {
    "title": {
        "text": ""
    },
    "displayTable": true,
    "chart": {
        "type": "pie"
    },
    "xAxis": {
        "categories": [
            aemLabels
        ]
    },
    "yAxis": {
            "title": {
            "text": ""
        },
        "labels": {
            "formatter": ""
        }
    },
    "credits": {
        "enabled": false
    },
    "tooltip": {
        "valueSuffix": "%"
    },
    tooltip: {
        formatter: function() {
            return "<b>Asset Class Allocation</b> %: <b>" + this.y + "</b>"
        }
    },
    plotOptions: {
        pie: {
            allowPointSelect: false,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                style: {
                    fontSize: 14   
                }
            }
        }
    },
    "series": seriesData,
    "asOfDate": asofdate
    };
    data.asset_class_allocation.forEach(item => {
        updateData.push({y:parseFloat(item.asset_class_percentage), name:item.asset_class});
    });
    finalData.series = [{
        "data": updateData
    }];
    Highcharts.setOptions({
        colors: colorList
    });
    Highcharts.chart('allocation-chart', finalData);
    }
}
var allocation_assetclass_head = function(data){
    if (data) {
        $(".assetClassHead").prepend(data.allocationassetclasses);
    }
}

var beforeAssetAllocClassLoadFuc = function () {
    $('.dynamic-loader-assetallocationclass').css("display", "block");
    $('.error-message-assetallocationclass').css("display", "none");
}

var assetAllocClassErrorHandling = function () {
	$('.allocation-asset-class').css("visibility", "visible");
	$(".assetClassHead").prepend(ajaxData_productLabels.allocationassetclasses);
	$(".error-message-assetallocationclass p").html(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-assetallocationclass').css("display", "none");
    $('.error-message-assetallocationclass').css("display", "block");
}

$(document).ready(function() {
// Toggle listview and chart view for annualized returns
	$('.allocation-asset-class-table').addClass('hidden');
    $(".allocation-toggle-view").click(function() {
        $(".allocation-toggle-view").removeClass('active');
        $(this).addClass('active');
        $(".allocation-toggle-view.active").attr("tabindex","0");
        $(".allocation-toggle-view.active").attr("aria-checked","true");
        $(".allocation-toggle-view:not(.active)").attr("tabindex","-1");
        $(".allocation-toggle-view:not(.active)").attr("aria-checked","false");
        if ($(this).hasClass('table-view')) {
            $('.allocation-asset-class-chart').addClass('hidden');
            $('.allocation-asset-class-table').removeClass('hidden');
            $(this).attr('aria-checked', 'false');
            $(this).parent('div').find('.table-view').attr('aria-checked', 'true');
        } else {
            $('.allocation-asset-class-table').addClass('hidden');
            $('.allocation-asset-class-chart').removeClass('hidden');
            $(this).attr('aria-checked', 'false');
            $(this).parent('div').find('.chart-view').attr('aria-checked', 'true ');
        }
    });



     $('.allocationassetclass a.table-view, .allocationassetclass  a.chart-view').on('keydown', function(e){
        if((e.which==40) || (e.which==38)){
            if ($(this).hasClass('chart-view')  ) {
                $('.allocation-asset-class-chart').addClass('hidden');
                $('.allocation-asset-class-table').removeClass('hidden');
            } else {
                $('.allocation-asset-class-table').addClass('hidden');
                $('.allocation-asset-class-chart').removeClass('hidden');
            }
        }
    })
});