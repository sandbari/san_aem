var json = {};
var flag = true;
var actualData = [];
var pointValue = [];
ChartJS = {
	colorMap: {
		'stock': '#00ADEE',
		'bonds': '#792C7A',
		'cash': '#8BC105'
	},
	colorList: ['#FFD700', '#0070ba', '#FFA726', '#486d90', '#333', '#C4C4C4'],
	defaultChartElement: 'donut_container',
	defaultDescElement: 'descp',
	options: {
		chart: {
			type: 'pie'
		},
		colors: ['#00ADEE', '#792C7A', '#8BC105'],
		title: {
			text: '',
		},
		legend: {
			align: 'left',
			layout: 'vertical',
			verticalAlign: 'middle'
		},
		plotOptions: {
			pie: {
				shadow: false,
				borderWidth: 0,
				borderColor: '#000000',
				center: ['50%', '50%'],
				name: 'y',
				innerSize: '50%',
				dataLabels: {
					color: '#000',
					enabled: true,
					align: 'center',
					crop: 'false',
					overflow: 'none',
					formatter: function () {
						var checkName = (this.point.name+"").toLowerCase();
						return this.point.name + "<br/><p class='yLabel' style='color:" + ChartJS.colorMap[checkName] + "'>" + this.point.y + "%</p>";
					},
					style: {
						fontSize: '14px',
						textOverflow: 'none',
						crop: 'false'
					},
					connectorWidth: 0,
					connectorPadding: -10
				}
			}
		},
		responsive: {
			rules: [{
				condition: {
					maxWidth: 1024
				},
				chartOptions: {
					plotOptions: {
						pie: {
							innerSize: 120,
							size: 220
						}

					}
				}
			}]
		},
		credits: {
			enabled: false
		},
		series: [{
			data: [{}],
			dataLabels: {
				distance: 30
			}
		}, {
			data: [{}],
			dataLabels: {
				distance: -110
			}
		}]
	},
	updateContent: function (index, divName) {
		if (!divName) {
			divName = ChartJS.defaultDescElement;
		}
		$('#' + divName).html('<div class="donut_content st-chart-header"><h5 class="donut_header st-ptext-heading">' + json[index].title +
			'</h5><span class="donut_age">' + json[index].age +
'</span><div class="st-donut-desc-wrap"><p class="donut_descp">' + json[index].description + '</p><div class="st-teaser-link"><a  href="'+ $(".view-plan-perfo-link").attr("href")+'" class="viewData st-arrow-link st-arrow-link-blue">' + $('.view-plan-perfo-link').text() + '</a></div></div></div><div class="borderGreen"></div>');
	},
	createPieTable:function(index){
        $('.donut-table-body').append('<tr><td>' + json[index].title +'</td><td>' + json[index].age +'</td><td>' + json[index].description + '</td></tr>');
    },
	createPieChart: function (index, chartDiv, descDiv) {
		if (!chartDiv) {
			chartDiv = ChartJS.defaultChartElement;
		}
		var data = $.extend(true, [], json[index].data);
		ChartJS.createPieChartByData(chartDiv, data);
		ChartJS.updateContent(index, descDiv);
	}, createPieChart2: function (chartDiv, dataStr) {
		try {
			var data = $.extend(true, [], $.parseJSON(dataStr)[0].data);
			ChartJS.createPieChartByData(chartDiv, data);
		} catch (err) {
			console.error(err.message);
		}
	}, createPieChartByData(chartDiv, data) {
		$('#' + chartDiv).highcharts(ChartJS.populateChartSeries(data));
	}, populateChartSeries: function (data) {
		var chartOptions = ChartJS.options;
		var highDataArray = [];
		var dataArray = [];
		var highestValue = 0;
		for (var i = data.length - 1; i >= 0; i--) {
			if (data[i].y > highestValue) {
				highestValue = data[i].y;
			}
		};
		for (var i = data.length - 1; i >= 0; i--) {
			if (data[i].y == highestValue) {
				highDataArray.push(data[i]);
				dataArray.push({
					y: data[i].y,
					name: data[i].name,
					dataLabels: {
						enabled: false
					}
				});
			} else {
				dataArray.push(data[i]);
				highDataArray.push({
					y: data[i].y,
					name: data[i].name,
					dataLabels: {
						enabled: false
					}
				});
			}
		};

		for (var i = 0; i < dataArray.length; i++) {
			this.options.colors[i] = ChartJS.colorMap[dataArray[i].name.toLowerCase()];
		}
		chartOptions.series[0].data = dataArray;
		chartOptions.series[1].data = highDataArray;
		return chartOptions;
	}, redrawChart: function (index) {
		var chart = $('#' + ChartJS.defaultChartElement).highcharts();
		var data = $.extend(true, [], json[index].data);
		var chartOptions = ChartJS.populateChartSeries(data);
		chart.series[0].update({
    data: []
}, false);
chart.series[1].update({
    data: []
}, false);
chart.series[0].update({
    data: chartOptions.series[0].data
}, false);
chart.series[1].update({
    data: chartOptions.series[1].data
}, false);
		chart.redraw();
		ChartJS.updateContent(index);
	},
	viewAll: function () {
        $('#donut-container').removeClass('withSlider').addClass('noSlider');
		$("#slider_wrap,#slider-range,#donut_wrapper,.st-graph-view-link, #sliderChartTableIcon").hide();
		$(".piechart-wrapper, .borderGreen, .viewDonut").show();
		$("#donut_wrapper").removeClass('noViewmargin');
         var piechartLength =$(".piechart-wrapper");
        $.each(piechartLength, function(i) {
				var chartId='chart_'+i;	
                ChartJS.reSizeChart(chartId);
			});

        $(".piechart").removeClass("hidePiechart");
        $('.sliderContainer h3.st-section-heading').attr('tabindex', '-1').focus();
 	}, showSlider: function () {
		$("#slider_wrap,#slider-range,#donut_wrapper, #sliderChartTableIcon").show();
		$(".piechart-wrapper").hide();
		$("#donut_wrapper").addClass('noViewmargin');
		$(".viewDonut, .borderGreen").hide();
        $('#donut-container').addClass('withSlider').removeClass('noSlider');
		$(".st-graph-view-link").show();
		$('.sliderContainer h3.st-section-heading').attr('tabindex', '-1').focus();
	},
	reSizeChart: function (chartId) {
		if(!chartId){
			chartId = ChartJS.defaultChartElement;			
		}
		var chart = $('#'+chartId).highcharts();
        if(chart){
            var viewportWidth = $(window).width();
            if(viewportWidth < 767){
                chart.setSize(400, 300);
            }else if(viewportWidth >= 768 && viewportWidth <= 1024 ){
                chart.setSize(400, 400);
            }else if(viewportWidth >= 1024 ){
                chart.setSize(400, 400);
            }
        }
	}, populateColors: function () {
		var maxValue = Object.keys(json).length;
		for (var i = 0; i < maxValue; i++) {
			var resultData = json[i];
			for (var j = 0; j < resultData.data.length; j++) {
				actualData.push(resultData.data[j].name);
			}
		}
		var colorKey = 0;
		for (i = 0; i < actualData.length; i++) {
			var type = actualData[i];
			if (!ChartJS.colorMap[type]) {
				ChartJS.colorMap[type] = ChartJS.colorList[colorKey];
				colorKey++;
			}
		}
	}, init: function () {
		if ($(".piechart-wrapper .st-view-chart-wrap").length > 0) {

			$(".st-graph-view-link").addClass('padR0');
			var dataChartId = 0;
			$(".piechart-wrapper .st-view-chart-wrap").each(function () {
				$('.parsys-container').addClass("row col-sm-12 viewDonut");
				$(".parsys-container .piechart").addClass("col-md-6 col-lg-4 col-xs-12 col-sm-6 viewbox hidePiechart");
				$(this).attr('data-jsonstr');
				$(".parsys-container").show();
				var storedata = $(this).attr('data-jsonstr');
				try {
					json[dataChartId] = $.parseJSON(storedata)[0];
				} catch (err) {
					console.error(err.message);
				}
				var main_chartID = "main_chart_" + dataChartId;
				$(this).attr("id",main_chartID);
                var chartID = "chart_" + dataChartId;
                ChartJS.populateColors();
                ChartJS.updateContent(dataChartId, main_chartID);
                $('#' + main_chartID).append('<div id="' + chartID + '" style="clear:both;"></div>');
				ChartJS.createPieChart2(chartID, $(this).attr('data-jsonstr'));
                var strngArr =  $.parseJSON(storedata)[0];
                var cashVal;
                var bondVal;
                if(strngArr.data.length> 1) {
                    cashVal = strngArr.data[1].y
                }
                 else cashVal = "N/A"
                 if(strngArr.data.length> 2) {
                    bondVal = strngArr.data[2].y
                    }
                 else bondVal = "N/A"
                $('.donut-table-body').append('<tr><td>' + strngArr.title +'</td><td>' + strngArr.age +'</td><td>' + strngArr.description + '</td><td>' + strngArr.data[0].y + '</td><td>' + cashVal + '</td><td>' + bondVal + '</td></tr>');
                $('#donut-table').addClass('hidden');
				dataChartId++;
			});

			if (!$(".sliderContainer").length > 0 && $(".piechart-wrapper").length > 0) {
				if ($(".piechart-wrapper").length >= 1 && $(".piechart-wrapper").length <= 2) {
					$(".piechart-wrapper").parent('.parsys-container').addClass("centerTile");
				}
			}


			ChartJS.populateColors();
            if ($(".sliderContainer").length > 0) {
				ChartJS.showSlider();
			}
            var maxValue = Object.keys(json).length;
			if (flag) {
				if (maxValue >= 3) {
					ChartJS.createPieChart(0);
				}
			}

			$("#slider_wrap,#slider-range,.view_all").hide();
			if (maxValue == 1) {
				$("#donut_wrapper").addClass('noViewmargin');
			} else if (maxValue == 2) {
				$("#slider_wrap,#slider-range").show();
				$(".view_all").hide();
				$("#donut_wrapper").removeClass('noViewmargin');
			} else if (maxValue >= 3) {
				$("#slider_wrap,#slider-range,.view_all").show();
				$("#donut_wrapper").removeClass('noViewmargin');
			}

			$("#slider-range").slider({
				orentiation: "horizontal",
				range: "min",
				min: 0,
				max: maxValue - 1,
				value: 0,
				slide: function (event, ui) {
					ChartJS.redrawChart(ui.value);
				}
			});

			$(document).on("click", ".view_all", function (e) {
				e.preventDefault();
				ChartJS.viewAll();

			});
			$(window).resize(function () {
				ChartJS.reSizeChart();

			});
            $(document).on("click", ".goback-slider", function (e) {
				e.preventDefault();
				ChartJS.showSlider();
			});
		}
	}
};
$(document).ready(function () {
    flag=true;
    var collgeDetailLabelsJsonStr = $("#collegeDetailLabels").val();
    if (collgeDetailLabelsJsonStr != null) {
        var ajaxData_collegeDetailLabels = JSON.parse(collgeDetailLabelsJsonStr);
        donuttableheadData(ajaxData_collegeDetailLabels);
    }
    function donuttableheadData(data) {
        var tHead = $("<thead/>");
        var tBody = $("<tbody/>");
        var headRow = $("<tr/>");
        if (data) {
            var values = Object.values(data)
            values.forEach(item => {
                headRow.append(`<th scope="col">${item}</th>`);
            })
        }
        tHead.append(headRow);
        $(tHead).insertBefore( $( ".donut-table-body" ) );
        //$(".donut-table").prepend(tHead);
    }
    $(".sliderContainer .college-toggle-view").click(function() {
        $(".sliderContainer .college-toggle-view").removeClass('active');
        $(this).addClass('active');
        if ($(this).hasClass('table-view')) {
            $('#donut_wrapper, #slider_wrap').addClass('hidden');
            $('#donut-container .st-teaser-link').addClass('hidden');
            $('#donut-table').removeClass('hidden');
        } else {
            $('#donut-table').addClass('hidden');
            $('#donut_wrapper, #slider_wrap').removeClass('hidden');
            $('#donut-container .st-teaser-link').removeClass('hidden');
        }
    });
    $('.sliderContainer a.table-view, .sliderContainer  a.chart-view').on('keydown', function(e){
        if((e.which==40) || (e.which==38)){
            e.preventDefault();
            $(this).parents('div.sliderContainer').find('.table-view, .chart-view').attr('aria-checked', 'false');
            $(".college-toggle-view").removeClass('active');
            $(".college-toggle-view").removeClass('active');
            if ($(this).hasClass('chart-view')  ) {
                $('#donut-table').removeClass('hidden');
                $('#donut_wrapper, #slider_wrap').addClass('hidden');
                $('.st-graph-view-link').hide();
                $(this).attr('aria-checked', 'false');
                $(this).parent('div').find('.table-view').attr('aria-checked', 'true');
                $(this).parents('div.sliderContainer').find('.table-view.college-toggle-view').addClass('active').focus();
            } else {
                $('#donut_wrapper, #slider_wrap').removeClass('hidden');
                $('#donut-table').addClass('hidden');
                $('.st-graph-view-link').show();
                $(this).attr('aria-checked', 'false');
                $(this).parent('div').find('.chart-view').attr('aria-checked', 'true ');
                $(this).parents('div.sliderContainer').find('.chart-view.college-toggle-view').addClass('active').focus();
            }
        }
    });

    $("#donut-table").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $("#donut-table").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));

    function toggleTableBoxshadow() {
        $(".tableShadowLeft").hide();
        let size = $(window).width();
        hideGradient(); //call hideGradient on resize
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
    //Gradient Show and Hide FUnction Starts
    function hideGradient() {
        let windowsize = $(window).width();
        if (windowsize <= 768) {
            $(".table-wrap").each(function() {
                let tableWidth = $(this).find('table').width();
                let tableWrapperWidth = $(this).width();
                if (tableWidth > tableWrapperWidth) {
                    $(this).siblings(".tableShadowRight").show();
                    $(this).siblings(".tableShadowLeft").hide();
                } else {
                    $(this).siblings(".tableShadowLeft").hide();
                    $(this).siblings(".tableShadowRight").hide();
                }
            });
        }
    }
    //Gradient Show and Hide Function Ends
    toggleTableBoxshadow();
    $(window).resize(function() {
        toggleTableBoxshadow();
    });
    $(".tableShadowRight").show();
	ChartJS.init();
});