var characteristics_four = function(data,labels){
	characteristics_four_tablelabelData(labels);
    characteristics_four_tableData(data);
	characteristics_four_tablehead(data);
}
// Fetching data from AEM JSON and appending to table heading
function characteristics_four_tablelabelData(data) {
    if (data) {
        $(".charFourHead").prepend(data.characteristics);
        headername = data.Characteristics.fund;
    }
}

// Fetching data from API and appending to table head
function characteristics_four_tablehead(data) {
    $(".characteristicsvariant-four-asof").append(data.characteristics.as_of_date);
    var tHead = $("<thead/>");
    var headRow = $("<tr/>");
    if (data) {
        headRow.append(`<th scope='col'><span class="sr-only">Characteristic</span></th>`);
        let results = data.characteristics.data;
        results.forEach((listTitle, index) => {
            if (index === 0) {
                headRow.append(`<th scope='col'>${headername}</th>`);
            } else {
                headRow.append(`<th scope='col'>${listTitle['benchmark_name']}(%)</th>`);
            }
        });
        tHead.append(headRow);
        $(".characteristicsvariant-four-table").append(tHead);
    }
}

function characteristics_four_tableData(data) {
    var tBody = $("<tbody/>");
    let keys = Object.keys(data.characteristics.data[0]);
    keys.forEach((key, index) => {
        if (index != keys.length - 1) {
            tableRender(data.characteristics.data, key);
        }
        tBody.append(row);
    })
    $('.characteristicsvariant-four-table').append(tBody);
    hideGradient();
}

var beforeCharVarFourLoadFuc = function () {
    $('.dynamic-loader-charVarFour').css("display", "block");
    $('.error-message-charVarFour').css("display", "none");
}

var charVarFourErrorHandlingFuc = function () {
    $('.dynamic-loader-charVarFour').css("display", "none");
    $('.error-message-charVarFour').css("display", "block");
}

$(document).ready(function() {
    $(".characteristicsvariant-four-table-data").append($("<div class='tableShadowLeft'>").append("<div class='actualShadow'>"));
    $(".characteristicsvariant-four-table-data").append($("<div class='tableShadowRight'>").append("<div class='actualShadow'>"));
});