var characteristics_two = function(data,labels){
	characteristics_two_tablelabelData(labels);
    characteristics_two_tableData(data);
	characteristics_two_tablehead(data);
}

// Fetching data from AEM JSON and appending to table heading
function characteristics_two_tablelabelData(data) {
    if (data) {
        $(".charTwoHead").prepend(data.characteristics);
        headername = data.Characteristics.fund;
    }
}

// Fetching data from API and appending to table head
function characteristics_two_tablehead(data) {
    $(".characteristicsvariant-two-asof").html(data.characteristics_fund_for_income.as_of_date);
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        headRow.append(`<th scope='col'><span class="sr-only">Characteristic</span></th>`);
        let results = data.characteristics_fund_for_income.data;
        results.forEach((listTitle, index) => {
            if (index === 0) {
                headRow.append(`<th scope='col'>${headername}</th>`);
            } else {
                headRow.append(`<th scope='col'>${listTitle['benchmark_name']} (%)</th>`);
            }
        });
        tHead.append(headRow);
        $(".characteristicsvariant-two-table").append(tHead);
    }
}

function characteristics_two_tableData(data) {
	var checkData = data.characteristics_fund_for_income.data;
    var benchmarkData = checkData.filter(item => {
        if (('benchmark_name' in item)) {
            return item;
        }
    })
    if (benchmarkData.length <= 0) {
        $('.charvar-two').hide();
    }
    var tBody = $("<tbody/>");
    let keys = Object.keys(data.characteristics_fund_for_income.data[0]);
    keys.forEach((key, index) => {
        if (index != keys.length - 1) {
        	let lableKeys = Object.keys(ajaxData_productLabels.Characteristicsvartwo);
        	lableKeys.forEach((lablekey, indexvalue) => {
				if (key == lablekey) {
					lableValue = ajaxData_productLabels.Characteristicsvartwo[lablekey];
					characteristicsTableRender(data.characteristics_fund_for_income.data, key, lableValue);
				}
			});
        }
        tBody.append(row);
    })
    $('.characteristicsvariant-two-table').append(tBody);
    hideGradient();
}

var beforeCharVarTwoLoadFuc = function () {
    $('.dynamic-loader-charVarTwo').css("display", "block");
    $('.error-message-charVarTwo').css("display", "none");
}

var charVarTwoErrorHandlingFuc = function () {
	$('.charvar-two').css("visibility", "visible");
	$(".charTwoHead").html("").prepend(ajaxData_productLabels.characteristics);
	$(".error-message-charVarTwo p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-charVarTwo').css("display", "none");
    $('.error-message-charVarTwo').css("display", "block");
}

$(document).ready(function() {
    $(".characteristicsvariant-two-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".characteristicsvariant-two-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});