var averagelifestructure_body= function(data){
	var options = averageLife_chart.options;
	var portfolio_percentage = [];
	var benchmark_percentage = [];
	$.each(data.data, function (k, v) {
		options.xAxis.categories.push(v.name);
		portfolio_percentage.push(Number(parseFloat(v.portfolio_percentage).toFixed(2)));
		benchmark_percentage.push(Number(parseFloat(v.benchmark_percentage).toFixed(2)));
	});
	options.series[0].data = portfolio_percentage;
	options.series[1].data = benchmark_percentage;
	Highcharts.setOptions({
        colors: colorList
    });
	Highcharts.chart('average-chart', options);
}

var averagelifestructuretable_body= function(data){
    var tBody = $("<tbody/>");
    if (data) {
        let averageLife = data.data;
        averageLife.forEach(lifeData => {
            if ((lifeData === null) || (lifeData === undefined) || (lifeData === "")) {
                lifeData = "N/A"
            } else {
                var bodyRow = $("<tr/>");
                bodyRow.append($(`<td class='text-left'>${lifeData.name}</td>`));
                bodyRow.append($(`<td>${parseFloat(lifeData.portfolio_percentage).toFixed(2)}</td>`));
                bodyRow.append($(`<td>${parseFloat(lifeData.benchmark_percentage).toFixed(2)}</td>`));
            }
            tBody.append(bodyRow);
        });
    }
    $("table.average-life-table").append(tBody);

}

averageLife_chart = {
		options: {
			chart: {
				type: 'column'
			},
			title: {
				text: ''
			},
			"displayTable": true,
			xAxis: {
				categories: []
			},
			yAxis:{
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
			plotOptions: {
				column: {
					dataLabels: {
						enabled: true
					}
				}
			},
			series: [{
				name: 'Fund (%)',
                data: [],
                borderColor: 'transparent',
                borderWidth: 0
			}, {
				name: 'index (%)',
				data: [],
                borderColor: 'transparent',
                borderWidth: 0
			}]
		}
	};
var averagelifestructure_head = function(data){
    if (data) {
        $(".averageClassHead").prepend(data.averagelifestructure);
    }
}
var averagelifestructuretable_head = function(data){
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        var headerValue = data.AveregeLifeStructure;
        var values = Object.values(headerValue);
        headRow.append(`<th scope='col'></th>`);
        values.forEach(item => {
            headRow.append(`<th scope='col'>${item}</th>`);
        })
    }
    tHead.append(headRow);
    $("table.average-life-table").append(tHead);
}

var beforeAverageLifeLoadFuc = function () {
    $('.dynamic-loader-averagelife').css("display", "block");
    $('.error-message-averagelife').css("display", "none");
}

var averageLifeErrorHandlingFuc = function () {
	$('.average-life-structure').css("visibility", "visible");  
	$(".averageClassHead").prepend(ajaxData_productLabels.averagelifestructure);
	$(".error-message-averagelife p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-averagelife').css("display", "none");
    $('.error-message-averagelife').css("display", "block");
}
