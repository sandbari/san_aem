var json;
$(document).ready(function() {
    if($('.contactdetails').length > 0) {
    	let chartJson = $('#chartJson').val() || "/content/dam/vcm/configs/chart-data.json";
        if(sharedJS.nonNullCheck(chartJson)){
	    	$.getJSON(chartJson, function(data) {
	            json = data;
	            ContactChartJS.init();
	            var weekday = ["SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"];
	            var d = new Date();
	            var n = d.getDay();
	            if(!(n) || n == 0 || n == 6) {
	                n = 1;
	            }
	            ContactChartJS.createChart(weekday[n]);
	            $(".dropdown").find('a span').text(weekday[n].capitalize());
	
	            firstChild = $("#days").find("a:first-child").text();
	            $("#days a").click(function() {
	                $(this).parents().find('.dropdown').find('i').toggleClass('fa-chevron-circle-up fa-chevron-circle-down');
	                var storeVal = $(this).text();
	                $(".dropdown").find('a span').text(storeVal);
	                ContactChartJS.redrawChart(storeVal.toUpperCase())
	            })
	        });
    	}
    }
    String.prototype.capitalize = function() {
    	let lowercase = this.toLowerCase();
    	let capitalized = lowercase.charAt(0).toUpperCase() + lowercase.slice(1);
        return capitalized;
    }
});
ContactChartJS = {
    defaultChartElement: 'contact_container',
    defaultDescElement: 'descp',
    mergeDataValues: 2,
    displayDataValue: 1,
    options: {
        chart: {
            type: 'column',
            height: 200
        },
        title: {
            text: ''
        },
        plotOptions: {
            column: {
                colorByPoint: true
            },
            series: {
                gapUnit: 2
            }
        },
        colors: [
            '#5E8AB4'
        ],
        tooltip: {
            enabled: false
        },
        xAxis: {
            categories: [],
            crosshair: true,
            title: {
                text: ''
            },
            labels: {
                style: {
                    color: '#49494A',
                    fontSize: '14px'
                },

                formatter() {
                    let label = this;
                    if (label.pos % 3 === 0 || label.isLast) {
                        return label.value
                    }
                }
            },
        },
        yAxis: {
            min: 0,
            gapUnit: 2,
            gapSize: 2,
            gridLineDashStyle: 'longdash',
            labels: {
                style: {
                    color: '#49494A',
                    fontSize: '14px'
                },
                format: '{value}m',

            },
            tickInterval: 5,
            title: {
                text: '',
                format: '{value}m'
            },
            max: 10
        },
        credits: {
            enabled: false
        },
        plotOptions: {
            column: {
                pointPadding: 0,
                groupPadding: 0,
                borderWidth: 0.9

            },

        },
        series: [{
            name: '',
            data: []
        }]

    },
    init: function() {
        //headings and descriptions
        var timings = '';
        $.each(json.description, function(i, data) {
            timings = timings + '<p class="day">' + data.day + '</p>';
            timings = timings + '<p class="time">' + data.time + '</p>';
        });
        $('#timings').html(timings);

        //drop down
        var options = '';
        $.each(json, function(i, data) {
            options = options + '<a class="dropdown-item" role="option" href="#/" value="' + data.TimeBucket + '">' + data.TimeBucket.capitalize() + '</a>';
        });
        $('#days').html(options);
        $('#days').append('<a  class="dropdown-item disabled" role="option" href="#/" value="Saturday" class="disbaledCLass" disabled>Saturday</a>');
        $('#days').append('<a  class="dropdown-item disabled" role="option" href="#/" value="Sunday" class="disbaledCLass" disabled>Sunday</a>');

    },
    createChartByData: function(xAxis, dataArray) {
        var chartOptions = ContactChartJS.options;
        chartOptions.xAxis.categories = xAxis;
        chartOptions.series[0].data = dataArray;
        $('#contact_container').highcharts(chartOptions);
    },
    createChart: function(indexName) {
        ContactChartJS.plotChart(indexName, 'new');
    },
    redrawChart: function(indexName) {
        ContactChartJS.plotChart(indexName);
    },
    redrawChartByData: function(xAxis, dataArray) {
        var chart = $('#contact_container').highcharts();
        chart.xAxis.categories = [];
        chart.series[0].update({
            data: dataArray
        }, false);
        chart.xAxis[0].setCategories(xAxis);
        chart.redraw();
    },
    plotChart: function(indexName, type) {
        var xAxis = [];
        var dataArray = [];
        $.each(json, function(i, data) {
            if (indexName == data.TimeBucket) {
                var i = 0;
                var value = 0;
                var length = Object.keys(data).length;
                $.each(data, function(k, v) {
                    length--;
                    if (k != 'TimeBucket') {
                        i++;
                        value = value + v;
                        if (i == ContactChartJS.displayDataValue) {
                            xAxis.push(tConvert(k));

                            function tConvert(time) {
                                time = time.toString().match(/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time + 'am'];
                                if (time.length > 1) {
                                    time = time.slice(1);
                                    time[5] = +time[0] < 12 ? 'am' : 'pm';
                                    time[0] = +time[0] % 12 || 12;
                                }
                                return time.join('');
                            }

                        }
                        if (i == ContactChartJS.mergeDataValues) {
                            dataArray.push(value / ContactChartJS.mergeDataValues);
                            i = 0;
                            value = 0;
                        }
                        if (length == 0 && i != ContactChartJS.mergeDataValues) {
                            dataArray.push(value / i);
                            i = 0;
                            value = 0;
                        }
                    }
                });
            }
        });
        if (type == 'new') {
            ContactChartJS.createChartByData(xAxis, dataArray);
        } else {
            ContactChartJS.redrawChartByData(xAxis, dataArray);
        }
    }
};