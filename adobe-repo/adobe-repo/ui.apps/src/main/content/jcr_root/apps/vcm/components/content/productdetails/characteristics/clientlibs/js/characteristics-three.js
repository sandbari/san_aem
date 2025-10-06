var characteristics_three = function(data,labels){
	characteristics_three_tablelabelData(labels);
    characteristics_three_tableData(data);
	characteristics_three_tablehead(data);
}

// Fetching data from AEM JSON and appending to table heading
function characteristics_three_tablelabelData(data) {
    if (data) {
        $(".charThreeHead").prepend(data.characteristics);
        headername = data.Characteristics.fund;
    }
}

// Fetching data from API and appending to table head
function characteristics_three_tablehead(data) {
    $(".characteristicsvariant-three-asof").append(data.characteristics_convertible.as_of_date);
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        headRow.append(`<th scope='col'><span class="sr-only">Characteristic</span></th>`);
        let results = data.characteristics_convertible.data;
        results.forEach((listTitle, index) => {
            if (index === 0) {
                headRow.append(`<th scope='col'>${headername}</th>`);
            } else {
                headRow.append(`<th scope='col'>${listTitle['benchmark_name']} (%)</th>`);
            }
        });
        tHead.append(headRow);
        $(".characteristicsvariant-three-table").append(tHead);
    }
}

function characteristics_three_tableData(data) {
	var checkData = data.characteristics_convertible.data;
    var benchmarkData = checkData.filter(item => {
        if (('benchmark_name' in item)) {
            return item;
        }
    })
    if (benchmarkData.length <= 0) {
        $('.charvar-three').hide();
    }
    var tBody = $("<tbody/>");
    let keys = Object.keys(data.characteristics_convertible.data[0]);
    keys.forEach((key, index) => {
        if (index != keys.length - 1) {
        	let lableKeys = Object.keys(ajaxData_productLabels.Characteristicsvarthree);
        	lableKeys.forEach((lablekey, indexvalue) => {
				if (key == lablekey) {
					lableValue = ajaxData_productLabels.Characteristicsvarthree[lablekey];
					characteristicsTableRender(data.characteristics_convertible.data, key, lableValue);
				}
			});
        }
        tBody.append(row);
    })
    $('.characteristicsvariant-three-table').append(tBody);
    hideGradient();
}

var beforeCharVarThreeLoadFuc = function () {
    $('.dynamic-loader-charVarThree').css("display", "block");
    $('.error-message-charVarThree').css("display", "none");
}

var charVarThreeErrorHandlingFuc = function () {
	$('.charvar-three').css("visibility", "visible");
	$(".charThreeHead").html("").prepend(ajaxData_productLabels.characteristics);
	$(".error-message-charVarThree p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-charVarThree').css("display", "none");
    $('.error-message-charVarThree').css("display", "block");
}

$(document).ready(function() {
    $(".characteristicsvariant-three-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".characteristicsvariant-three-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});