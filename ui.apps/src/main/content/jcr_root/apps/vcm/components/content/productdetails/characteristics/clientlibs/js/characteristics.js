var characteristics = function(data,labels){
    characteristics_tablelabelData(labels);
    characteristics_tablehead(data);
    characteristics_tableData(data);
}

// Fetching data from AEM JSON and appending to table heading
function characteristics_tablelabelData(data) {
    if (data) {
        $(".charOneHead").prepend(data.characteristics);
        headername = data.Characteristics.fund;
    }
}

// Fetching data from API and appending to table head
function characteristics_tablehead(data) {
    $(".characteristics-asof").append(data.characteristics.as_of_date);
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        headRow.append(`<th scope='col'><span class="sr-only">Characteristic</span></th>`);
        let results = data.characteristics.data;
        results.forEach((listTitle, index) => {
            if (index === 0) {
                headRow.append(`<th scope='col'>${headername}</th>`);
            } else {
                headRow.append(`<th scope='col'>${listTitle['benchmark_name']}</th>`);
            }
        });
        tHead.append(headRow);
        $(".characteristics-table").append(tHead);
    }
}

function characteristics_tableData(data) {
	var checkData = data.characteristics.data;
    var benchmarkData = checkData.filter(item => {
        if (('benchmark_name' in item)) {
            return item;
        }
    })
    if (benchmarkData.length <= 0) {
            $('.characteristics').hide();
   }
    var tBody = $("<tbody/>");
    let keys = Object.keys(data.characteristics.data[0]);
    keys.forEach((key, index) => {
        if (index != keys.length - 1) {
        	let lableKeys = Object.keys(ajaxData_productLabels.Characteristics);
        	lableKeys.forEach((lablekey, indexvalue) => {
				if (key == lablekey) {
					lableValue = ajaxData_productLabels.Characteristics[lablekey];
					characteristicsTableRender(data.characteristics.data, key, lableValue);


				}
			});
        }
        tBody.append(row);
    })
    $('.characteristics-table').append(tBody);
    hideNullColumns('.characteristics-table');
    hideGradient();
}

var beforeCharOneLoadFuc = function () {
    $('.dynamic-loader-charone').css("display", "block");
    $('.error-message-charone').css("display", "none");
}

var charOneErrorHandlingFuc = function () {
	$('.characterstics-table').css("visibility", "visible");
	$(".charOneHead").html("").prepend(ajaxData_productLabels.characteristics);
	$(".error-message-charone p").html("").prepend(ajaxData_productLabels.apiCallFailureMessage);
    $('.dynamic-loader-charone').css("display", "none");
    $('.error-message-charone').css("display", "block");
}

$(document).ready(function() {
    $(".characteristics-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".characteristics-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});